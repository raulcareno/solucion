/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team._1984.dashboard;

import edu.wpi.first.team._1984.image.EdgeDetector;
import edu.wpi.first.team._1984.image.ImageOps;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Grabs and processes an image, and then paints to a JImagePanel. I can't think
 * of a better way to do this...
 *
 * @author sid
 */
public class AxisCameraProcessor implements Runnable
{

    protected AxisCamera cam;
    protected JImagePanel up;

    protected EdgeDetector edged;
    protected Graphics2D g;

    public AxisCameraProcessor(AxisCamera c, EdgeDetector e, JImagePanel u)
    {
        cam = c;
        edged = e;
        up = u;

        g = up.createGraphics();
    }

    public void run()
    {
        int[] last = new int[320*240];

        while(true)
        try
        {
            int[] img = cam.grabArray();
            int[] data = (int[])img.clone();
            //int[] edge = EdgeDetector.grayscaleToRGB(edged.process(EdgeDetector.rgbToGrayscale(img), 320, 240));

            //ImageOps.maskColorTol(data, 0xFFFFFFFF, 60);
            //for(int i = 0; i < img.length; i++)
            //    data[i] = data[i] == 0xFF000000 ? img[i] : 0xFF333333;

            for(int i = 0; i < img.length; i++)
                data[i] = ImageOps.tol1(img[i], last[i], 35) ? img[i] : 0xFFFF0000;

            Point pY;
            ArrayList<Rectangle> blobs = new ArrayList<Rectangle>();

            while((pY = ImageOps.findColorY(data, 320, 240, 0xFFFF0000)) != null)
            {
                blobs.add(ImageOps.floodFill(data, 320, 240, 0xFFFF0000, 0xFF0000FF, pY));
            }

            up.drawImage(data);

            for(Rectangle rect : blobs)
            {
                if(rect.height < 5 || rect.width < 5) continue;

                g.setColor(Color.GREEN);
                g.draw(new Rectangle(rect.x - 1, rect.y - 1, rect.width + 2, rect.height + 2));

                g.setColor(Color.RED);
                g.drawLine(rect.x + rect.width / 2, rect.y, rect.x + rect.width / 2, rect.y + rect.height);
                g.drawLine(rect.x, rect.y + rect.height / 2, rect.x + rect.width, rect.y + rect.height / 2);
            }

            Thread.sleep(10);
            last = img;
        } catch(InterruptedException ex)
        {
            Logger.getLogger(AxisCameraProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex)
        {
            Logger.getLogger(AxisCameraProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
