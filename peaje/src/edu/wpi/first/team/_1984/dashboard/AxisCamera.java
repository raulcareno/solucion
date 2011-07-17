package edu.wpi.first.team._1984.dashboard;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author sid
 */
public class AxisCamera
{
    protected String host;
    protected String res = "320x240";
    protected String grabPage = "/axis-cgi/jpg/image.cgi?resolution=";
    protected String streamPage = "/mjpg/video.mjpg";

    protected BufferedImage lastImg;
    protected int[] lastImgArr;

    public AxisCamera(String h)
    {
        host = h;
        grabPage += res;
    }

    public AxisCamera(String h, String r)
    {
        host = h;
        grabPage += r;

        lastImg = null;
        lastImgArr = null;
    }

    public AxisCamera(String h, String gp, String r, String sp)
    {
        host = h;
        grabPage = gp + r;
        streamPage = sp;

        lastImg = null;
        lastImgArr = null;
    }

    public BufferedImage grab() throws IOException
    {
        return lastImg = ImageIO.read(new URL(host + grabPage));
    }

    public int[] grabArray() throws IOException
    {
        BufferedImage img = grab();

        int[] data = new int[img.getWidth() * img.getHeight()];
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), data, 0, img.getWidth());

        lastImg = img;
        lastImgArr = data;

        return data;
    }

    public InputStream openMjpgStream() throws IOException
    {
        return new URL(host + streamPage).openStream();
    }
}
