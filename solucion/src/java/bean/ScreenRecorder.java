/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author JCINFORM
 */
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Robot;
import java.io.File;
import javax.imageio.ImageIO;
 
class ScreenRecorder {
public static void main(String args[]) {
   try {
       Toolkit tool = Toolkit.getDefaultToolkit();
       Dimension d = tool.getScreenSize();
       Rectangle rect = new Rectangle(100, 100,640,480);
       //Rectangle rect = new Rectangle(d);
       Robot robot = new Robot();
       Thread.sleep(2000);
       File f = new File("f:\\FOTOS\\screenshot.jpg");
       BufferedImage img = robot.createScreenCapture(rect);
       ImageIO.write(img,"jpeg",f);
       tool.beep();
       
//        BufferedImage screenCapture = robot.createScreenCapture(new Rectangle(screenSize));  
//        JLabel label = new JLabel(new ImageIcon(screenCapture));  
//        contentPane.add(label, BorderLayout.CENTER);  
       } catch(Exception e){
        e.printStackTrace();
      }
    }
}

