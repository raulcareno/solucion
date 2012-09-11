/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 
package bean;

import java.awt.Graphics2D;  
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.imageio.ImageIO;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;  
 @ManagedBean
@RequestScoped 
public class DynamicImageController {  
  
    private StreamedContent graphicText;  
 
  
    public DynamicImageController() {  
        try {  
            //Graphic Text  
            BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);  
            Graphics2D g2 = bufferedImg.createGraphics();  
            g2.drawString("This is a text", 0, 10);  
            ByteArrayOutputStream os = new ByteArrayOutputStream();  
            ImageIO.write(bufferedImg, "png", os);  
            graphicText = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");   
   
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

    public void cargar() {
         try {  
            //Graphic Text  
            BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);  
            Graphics2D g2 = bufferedImg.createGraphics();  
            g2.drawString("This is a text", 0, 10);  
            ByteArrayOutputStream os = new ByteArrayOutputStream();  
            ImageIO.write(bufferedImg, "png", os);  
            graphicText = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");   
   
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
    
    public StreamedContent getGraphicText() {  
       
        return graphicText;  
    }  
          
    
   
}  