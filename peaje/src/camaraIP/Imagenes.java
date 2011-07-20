/*
 * Imagenes.java
 *
 * Created on 9 de diciembre de 2007, 06:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package camaraIP;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 *
 * @author olga
 */

/**Clase que permite obtener la imagen de la camara utilizando la clase URL, presentandola en un JFrame*/
public class Imagenes extends JFrame  {
   
    public BufferedImage image = null;
    
    public Imagenes() {
    }
    
     public void paint(Graphics g){
         
        
        Graphics2D g2 = (Graphics2D)g; 
        URL nUrl = null;
              
        try {
          nUrl = new URL("http://192.168.10.3/cgi-bin/viewer/video.jpg");
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        
        //public BufferedImage image = null;
        try {
          image = ImageIO.read(nUrl);
        } catch (IOException ex) {
            ex.printStackTrace();
       }
        
        setTitle("Imagen_Camara");   
        g2.drawImage(image,0,10,this);
    
        repaint();
       
     
    }
}
