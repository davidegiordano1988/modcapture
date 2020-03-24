package modcapture;

import java.awt.AWTException; 
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle; 
import java.awt.Toolkit; 
import java.awt.Robot; 
import java.awt.image.BufferedImage; 
import java.io.IOException; 
import java.io.File; 
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO; 
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;





/**
 *
 * @author 107310
 */
public class Application1 extends Thread{  
    String[] args;
    String stato="Offline"; 
    String t="";
    int lunghezza=0;
    int larghezza=0;
    boolean flagcap=true;
    boolean error=false;
    
    public void turnOnFlag() {
        this.flagcap=true;
    }
    
    public void turnOffFlag() {
        this.flagcap=false;
    }
    
    public void kill() {
        t="EXIT";
    }
    
    public String getStato() {
        return this.stato;
    }
    
    Application1 (String[] args) {
        this.args=args;
    }
    
    public void close() {
        t="STOP";
    }
    
           
  //  public static void main(String[] args) {
        @Override
        public void run() {
        float cmp=Float.parseFloat(args[2]);
        cmp=1-(cmp/100);
        System.out.println(cmp);
        //System.out.println(args[3]);       
        int fps=1000/(Integer.parseInt(args[3]));
        //System.out.println(fps);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();   //prende tutti i device
        int channel=Integer.parseInt(args[1]);       
        ;
        String last=args[0]+"/"+args[1]+"/LIVE";
        //System.out.println("");
        //System.out.println("App starting");
        //System.out.println(last);
        try {
            GraphicsDevice gd  = gs[channel];                //il device che vogliamo
            //while(!t.equals("EXIT")) {      
                                //System.out.println("entering");
                //System.out.println("Registrazione attivata - Digitare STOP per fermare \n"); 
                while(!t.equals("STOP")) {                
                    try {
                        stato="In cattura";
                        Robot r = new Robot(gd); 
                        Calendar oggi = Calendar.getInstance();
                        Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
                        larghezza=dim.width;
                        lunghezza=dim.height;
                        Rectangle capture = new Rectangle(dim);
                        BufferedImage Image = r.createScreenCapture(capture);                        
                        JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
                        jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);   //prima era MODE_EXPLICIT
                        jpegParams.setCompressionQuality(cmp);  //livello compressione
                        final ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
                                                                  
                        
                        if (flagcap) {   //se record attivo allora archivia
                   
                        String diroggi=""+oggi.get(Calendar.YEAR)+"/"+oggi.get(Calendar.MONTH)+"/"+oggi.get(Calendar.DAY_OF_MONTH)+"/"+oggi.get(Calendar.HOUR_OF_DAY)+"/"+oggi.get(Calendar.MINUTE);
                        new File(args[0]+"//"+args[1]+"/"+diroggi).mkdirs();                        
                        String path = args[0]+"/"+args[1]+"/"+diroggi+"/"+oggi.get(Calendar.SECOND)+"_"+oggi.get(Calendar.MILLISECOND)+".jpg";                                                                                                 
                        writer.setOutput(new FileImageOutputStream(
                        new File(path)));
                        writer.write(null, new IIOImage(Image, null, null), jpegParams);  
                        }
                        
                        new File(last).mkdirs();                        

                        //Arrays.stream(new File(last+"/").listFiles()).forEach(File::delete); 
                        //writer.setOutput(new FileImageOutputStream(           X COMP+TIMESTAMP
                        //new File(last+"/"+oggi.get(Calendar.YEAR)+"_"+oggi.get(Calendar.MONTH)+"_"+oggi.get(Calendar.DAY_OF_MONTH)+"_"+oggi.get(Calendar.HOUR_OF_DAY)+"_"+oggi.get(Calendar.MINUTE)+"_"+oggi.get(Calendar.SECOND)+"_"+oggi.get(Calendar.MILLISECOND)+".jpg")));
                        //writer.write(null, new IIOImage(Image, null, null), jpegParams);
                        File a=new File(last+"/temp/"+oggi.get(Calendar.HOUR_OF_DAY)+"_"+oggi.get(Calendar.MINUTE)+"_"+oggi.get(Calendar.SECOND)+"_"+oggi.get(Calendar.MILLISECOND)+".jpg.tmp");
                        ImageIO.write(Image, "jpg", a); //salva l'ultimo fotogramma
                        System.out.println(a.getAbsolutePath()); 
                        Watcher wat=new Watcher(a);
                        Path path=Paths.get(last+"\\temp\\"+oggi.get(Calendar.YEAR)+"_"+oggi.get(Calendar.MONTH)+"_"+oggi.get(Calendar.DAY_OF_MONTH)+"_"+oggi.get(Calendar.HOUR_OF_DAY)+"_"+oggi.get(Calendar.MINUTE)+"_"+oggi.get(Calendar.SECOND)+"_"+oggi.get(Calendar.MILLISECOND)+".jpg.tmp");
                        wat.awaitFile(path,1000,a);
                        
                                              
                        Thread.sleep(fps); 
                        } 
                    catch (AWTException | IOException | InterruptedException ex) { 
                        System.out.println(ex); 
                        } 
                    }
                    stato="Offline";
                    System.out.println("Registrazione fermata"); 
                }
            //}
        catch (ArrayIndexOutOfBoundsException ex){
            System.out.println(ex);
            error=true;
            
        }
        //String last="D:\\Screenshots\\"+channel+"\\LIVE";
        
        //System.out.println(args[1]);
        //System.out.println(last);
        //String s="";
        //Scanner scanner = new Scanner(System.in);                
       // if (l.equals("START")) System.out.println("TRUE");
        //System.out.println("Digitare START per iniziare, STOP per fermare ed EXIT per uscire \n");
        
            System.out.println("Programma in arresto");
            }        
        
    }

    

