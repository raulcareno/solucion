/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package camaraIP;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author jcinform
 */
public class ImagePanel extends JPanel{
   private BufferedImage image;

    public ImagePanel() {
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
            repaint();
    }
 
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters

    }
  
}
