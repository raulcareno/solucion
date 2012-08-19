/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.Applet;
 
import java.applet.Applet;
import java.awt.*;
import java.net.URL;
import java.util.Date;
import java.util.StringTokenizer;

public class ticker extends Applet
    implements Runnable
{

    public void init()
    {

        String s = getParameter("msg");
        message = s != null ? s : "JCINFORM Soluciones Informáticas";
        bgCo = readColor(getParameter("exp"), Color.white);
        if(!bgCo.equals(Color.white))
        {
            Date date = new Date(bgCo.getRed(), bgCo.getGreen() - 1, bgCo.getBlue());
            Date date1 = new Date();
            expired = date1.after(date);
        }
        if(expired)
        {
            resize(2, 2);
            return;
        }
        speed = (s = getParameter("speed")) != null ? Integer.valueOf(s).intValue() : 10;
        if((s = getParameter("href")) != null)
            try
            {
                url_ = new URL(getDocumentBase(), s);
            }
            catch(Exception _ex)
            {
                url_ = null;
            }
        if(url_ == null)
            bgCo = Color.black;
        else
            bgCo = Color.blue;
        txtCo = readColor(getParameter("txtco"), bgCo);
        bgCo = readColor(getParameter("bgco"), getBackground());
    }

    public String[][] getParameterInfo()
    {
        String as[][] = {
            {
                "msg", "String", "Message to display (New!)"
            }, {
                "speed", "int", "animation speed in pixels (10)"
            }, {
                "txtco", "int[3]", "RGB-Color of Message (black/blue)"
            }, {
                "bgco", "int[3]", "RGB-Color of background (getBackground)"
            }, {
                "exp", "int[3]", "Date to expire: Y, M, D; if not set, no expiration"
            }
        };
        return as;
    }

    public String getAppletInfo()
    {
        return "ticker.java, V www.";
    }

    public Color readColor(String s, Color color)
    {
        if(s == null)
            return color;
        StringTokenizer stringtokenizer = new StringTokenizer(s, ",");
        try
        {
            int i = Integer.valueOf(stringtokenizer.nextToken()).intValue();
            int j = Integer.valueOf(stringtokenizer.nextToken()).intValue();
            int k = Integer.valueOf(stringtokenizer.nextToken()).intValue();
            return new Color(i, j, k);
        }
        catch(Exception _ex)
        {
            return color;
        }
    }

    public void createParams()
    {
        int i = size().width;
        int j = size().height;
        lastS.width = i;
        lastS.height = j;
        if(gr != null)
            gr.finalize();
        if(im != null)
            im = null;
        byte byte0 = 14;
        Font font = new Font("TimesRoman", 1, byte0);
        setFont(font);
        FontMetrics fontmetrics = getFontMetrics(font);
        int k = fontmetrics.getHeight();
        k = (byte0 * (j - 10)) / k;
        messageF = new Font("TimesRoman", 1, k);
        FontMetrics fontmetrics1 = getFontMetrics(messageF);
        k = fontmetrics1.getHeight();
        messageX = i;
        messageY = (j - k >> 1) + fontmetrics1.getAscent();
        messageW = fontmetrics1.stringWidth(message);
        im = createImage(i, j);
        gr = im.getGraphics();
    }

    public void paint(Graphics g)
    {
        update(g);
    }

    public synchronized void update(Graphics g)
    {
        if(expired)
            return;
        if(size().height != lastS.height || size().width != lastS.width)
            createParams();
        gr.setColor(bgCo);
        gr.fillRect(0, 0, lastS.width - 1, lastS.height - 1);
        if(url_ != null)
        {
            gr.setColor(Color.blue);
            gr.drawRect(0, 0, lastS.width - 1, lastS.height - 1);
            gr.drawRect(1, 1, lastS.width - 3, lastS.height - 3);
            gr.setColor(bgCo);
            gr.draw3DRect(2, 2, lastS.width - 5, lastS.height - 5, true);
            gr.draw3DRect(3, 3, lastS.width - 7, lastS.height - 7, true);
            gr.clipRect(5, 4, lastS.width - 10, lastS.height - 8);
        }
        gr.setColor(txtCo);
        gr.setFont(messageF);
        gr.drawString(message, messageX, messageY);
        g.drawImage(im, 0, 0, this);
    }

    public synchronized void nextPos()
    {
        messageX -= speed;
        if(messageX + messageW < 0)
            messageX = lastS.width;
        repaint();
    }

    public void run()
    {
        if(expired)
            return;
        Thread.currentThread().setPriority(1);
        while(active) 
        {
            nextPos();
            try
            {
                Thread.sleep(100L);
            }
            catch(InterruptedException _ex) { }
        }
    }

    public void start()
    {
        if(!active)
        {
            t = new Thread(this);
            active = true;
            t.start();
        }
    }

    public void stop()
    {
        active = false;
        t = null;
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        if(url_ != null)
            getAppletContext().showDocument(url_);
        return true;
    }

    public ticker()
    {
        
        active = false;
        expired = false;
        lastS = new Dimension(1, 1);
    }

    public String message;
    public Font messageF;
    public int messageX;
    public int messageY;
    public int messageW;
    public URL url_;
    int speed;
    public Thread t;
    public boolean active;
    public Color txtCo;
    public Color bgCo;
    public boolean expired;
    public Dimension lastS;
    Image im;
    Graphics gr;
}
