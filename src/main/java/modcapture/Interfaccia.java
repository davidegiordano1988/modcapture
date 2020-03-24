/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modcapture;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author 107310
 */
public class Interfaccia extends javax.swing.JFrame {
    
    Application uno;
    int num=0;
    String directory="";
    String[] listapp=new String[10];
    String[][] alfa ={
                             {null,null,null,null,null},
                             {null,null,null,null,null},                             
                             {null,null,null,null,null},
                             {null,null,null,null,null},
                             {null,null,null,null,null},
                             {null,null,null,null,null},
                             {null,null,null,null,null},
                             {null,null,null,null,null},
                             {null,null,null,null,null},
                             {null,null,null,null,null},
                          };
       
    public String checkStato(String p) {
                
        Set<Thread> set = Thread.getAllStackTraces().keySet();
        for (Iterator<Thread> it = set.iterator(); it.hasNext(); ) {
        Thread f =it.next();
        //System.out.println(f.getName());
        if (f.getName().equals(p)) {
            Application test= (Application) f;            
            return test.getStato();
            }
        };
        return "Offline";
    }
    
    public int checkProcesso(String p) {
         Set<Thread> set = Thread.getAllStackTraces().keySet();
        for (Iterator<Thread> it = set.iterator(); it.hasNext(); ) {
        Thread f =it.next();
        //System.out.println(f.getName());
        if (f.getName().equals(p)) {
            return 1;
            }
        };
        return 0;
    }
    
    public static Application getApp(String p) {
        Set<Thread> set = Thread.getAllStackTraces().keySet();
        for (Iterator<Thread> it = set.iterator(); it.hasNext(); ) {
        Thread f =it.next();
        //System.out.println(f.getName());
        if (f.getName().equals(p)) {
            Application test= (Application) f;            
            return test;
            }
        };
        return null;
    }
    
    public void updateStato (int row, Application app) {                      
        try {
            Thread.sleep(2500);
        }
        catch (InterruptedException ex) { 
                        System.out.println(ex);
        }
        alfa[row][4]=app.getStato();
        jTable1.setValueAt(app.getStato(), row, 4); 
        System.out.println(app.getStato());
    }
    
    
    public void updateValues() {
        
      System.out.println("aggiornando!");
      String thisLine = null;
      String[] argst= new String[8];
      
      try {
         // open input stream test.txt for reading purpose.
         BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\107310\\Documents\\NetBeansProjects\\modcapture\\src\\main\\java\\modcapture\\Config.txt"));
         int i=0;       
         while ((thisLine = br.readLine()) != null) {
            //System.out.println(thisLine); 
            //{"D://Screenshots","0","0.15","1"};
            System.out.println(i);
            if (i==0) {                
                argst[i]=thisLine;
                if (num==0)directory=thisLine;
                i++;
            }
            else if(i==1) {
                argst[i]=thisLine;               // canale
                //alfa[num][i-1]=argst[i];
                i++;
            }
            else if(i<4) {
                argst[i]=thisLine;          //1 qualita   2 fps
                alfa[num][i-1]=thisLine;
                i++;
            }
            else if (i==4) {
                argst[i]=thisLine;
                i++;
            }
            else if(i==5) {
                argst[i]=thisLine;                   
                alfa[num][i-2]=argst[i-1]+"x"+argst[i];         //3 res  
                i++;
            }
            else if(i==6) {                                 
                argst[i]=thisLine;
                alfa[num][0]=thisLine;
                i++;
            }
            else if(i==7) {
                argst[i]=thisLine; 
                i++;
            }
            else {  
                
                String[] argcopy = new String[8];
                System.arraycopy(argst,0, argcopy, 0, 8);
                if (checkProcesso(argcopy[7])==0) {
                     argst=new String[argst.length];
                     Application app=new Application(argcopy); 
                     //System.out.println("valore");
                     //System.out.println(argcopy[7]);
                      listapp[num]=argcopy[7];      //processi attivi
                      app.setName(argcopy[7]);
                      if (checkStato(argcopy[7]).equals("Offline")) {                    
                      app.start();
                      }
                 
                //test=app;                
                i=0;         //occhio qua
                //app.kill();
                String blank=br.readLine();
                Thread.sleep(1000);
                alfa[num][4]=checkStato(argcopy[7]);              //4 stato
                num++;
                }
            }            
            
            
          } 
        br.close();
       }      
       catch(Exception e) {
         e.printStackTrace();
      }
    }  
    
    public void changedValues(String p, String argst[]) {
        String[] args=new String[8];
        System.arraycopy(argst,0, args, 0, 8);
        System.out.println("valore ingresso");
        System.out.println(p);
          Set<Thread> set = Thread.getAllStackTraces().keySet();
        for (Iterator<Thread> it = set.iterator(); it.hasNext(); ) {
        Thread f =it.next();
        //System.out.println("LEGGENDO I THREAD");
        //System.out.println(f.getName());
        if (f.getName().equals(p)) {
            Application test= (Application) f;            
            test.close();
            System.out.println("CLOSIIIIIIIIIINGGGGGGGGGGGGGGGGGG");
            //updateValues();
            }
        };
        for (int i=0;i<8;i++) {
            System.out.println("valore array");
            System.out.println(args[i]);
        }
        //Interfaccia t=new Interfaccia();
        //t.setVisible(true);
        //dispose();
        Application app=new Application(args); 
        app.start();
        int row=Integer.parseInt(args[1]);
        for (int i=0;i<8;i++) {
            System.out.println("VALORE DI I NEL CICLO");
            System.out.println(i);
            if(i==2 || i==3) {
                         //1 qualita   2 fps
                alfa[row][i-1]=args[i];
                jTable1.setValueAt(args[i], row, i-1);               
                
            }           
            else if(i==5) {
                //System.out.println("ENTERING");
                String res=args[i-1]+"x"+args[i]; 
                alfa[row][i-2]=res;
                jTable1.setValueAt(res, row, i-2);               
            }
            else if(i==6) {
                alfa[row][0]=args[i];
                jTable1.setValueAt(args[i], row, 0);  
                
            }          
              }
        alfa[row][4]=app.getStato();
        jTable1.setValueAt(app.getStato(), row, 4);
        
        String[] ultra=alfa[row];
        System.out.println(app.getStato());
        Details u=new Details(ultra,row,listapp[row],directory,this);
        u.setVisible(true);

        
        
        
    }
    
    
    
    public Interfaccia() {       
        
        updateValues();
        initComponents();
        _2DArrayToTable();
        
        //this.start();
    }

    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Interfaccia Monitoraggio Video");

        jButton2.setText("Dettagli");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Sorgente", "Qualità", "FPS", "Risoluzione", "Stato"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jButton1.setText("Nascondi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(38, 38, 38)
                        .addComponent(jButton2)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      //System.out.println(uno.stato);
     int source=jTable1.getSelectedRow();
     if (source<0) JOptionPane.showMessageDialog(null, "Nessun dispositivo selezionato! ");    // not an integer!
     else {
     //System.out.println("source");
     //System.out.println(source);
     String[] ultra=alfa[source];
     Details u=new Details(ultra,source,listapp[source],directory,this);
     u.setVisible(true);
     }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setExtendedState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jButton1ActionPerformed

        public void _2DArrayToTable()
    {
   
       
        //data[11]=add;
                 DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        
        alfa[3][2]="ah!";
         for(String[] row : alfa){
             model.addRow(row);
         }
        
    }
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaccia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaccia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaccia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaccia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaccia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
