/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modcapture;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author 107310
 */
class Refresher extends Thread {
    boolean live=true;
    Details det;
    
    public Refresher (Details a) {
        det=a;
    }
    
        @Override
         public void run()  {
         while (live) {
        ImageIcon image = new ImageIcon("D:\\Screenshots\\0\\LIVE\\live.jpg"); 
        Image img1=image.getImage();
        Image img2=img1.getScaledInstance(det.jLabel1.getWidth(),det.jLabel1.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon i=new ImageIcon(img2);        
        det.jLabel1.setIcon(i); 
        System.out.println("entrato refresh");
        try {
            Thread.sleep(2000);
            }
            catch (InterruptedException ex) {
            System.out.println(ex);
                }            
            }
        }
    }