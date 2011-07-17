package edu.wpi.first.team._1984.dashboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import javax.swing.JLabel;

import javax.swing.JPanel;

/**
 *
 * @author sid
 */
public class JImagePanel extends JPanel
{
    /**
     * Font used when drawing status text.
     */
    protected Font statFont;

    /**
     * Color used to draw status text.
     */
    protected Color statColor;

    /**
     * Height of each line of status text.
     */
    protected int lineHeight;

    /**
     * Number of rows of pixels to skip between status lines.
     */
    protected int lineSpacing;

    /**
     * ArrayList representing status text. The text is drawn in the bottom-left
     * corner, with the first element on the bottom. Text is drawn OVER any
     * shapes.
     */
    protected ArrayList<String> statusLines;

    /**
     * Array of Graphics2D derived shapes to be drawn over the image. Drawn in
     * FILO order. Each shape must have a corresponding entry in the color and
     * stroke arrays, you can, however, specify "null" to use a default value.
     *
     * I _really_ don't want to try to add Gradients, Patterns, or Transforms
     * into this. I honestly don't think I, or any sane person, would absolutely
     * need to use them.
     */
    protected ArrayList<Shape> shapes;

    protected ArrayList<Boolean> shapeFills;

    protected Color defShapeColor;
    protected ArrayList<Color> shapeColors;

    protected Stroke defShapeStroke;
    protected ArrayList<Stroke> shapeStrokes;

    protected BufferedImage draw;

    protected JLabel drawWrapper;

    /**
     * Initialize a JImagePanel to use a black background and bold 10 point
     * font in red.
     */
    public JImagePanel(int w, int h)
    {
        super(true);

        setBackground(Color.BLACK);

        //statFont = new Font("DejaVu Sans", 0, 11);
        statusLines = new ArrayList<String>();
        
        setTextFont(new Font("Monospaced", Font.BOLD, 10));
        statColor = Color.RED;
        lineSpacing = 1;

        shapes = new ArrayList<Shape>();
        shapeFills = new ArrayList<Boolean>();
        shapeColors = new ArrayList<Color>();
        shapeStrokes = new ArrayList<Stroke>();

        defShapeColor = Color.GREEN;
        defShapeStroke = new BasicStroke(1);

        //draw = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
        draw = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    }

    public void setTextFont(Font f)
    {
        statFont = f;
        lineHeight = getFontMetrics(f).getHeight();
    }

    public void setTextColor(Color c) { statColor = c; }

    public void setDefaultShapeColor(Color c) { defShapeColor = c; }
    public void setDefaultShapeStroke(Stroke s) { defShapeStroke = s; }

    /**
     * Set the number of pixels between lines of text.
     *
     * @param s the number of pixels between lines of text.
     */
    public void setLineSpacing(int s) { lineSpacing = s; }

    public int getTotalStatusLines() { return statusLines.size(); }

    /**
     * Just in case you want to modify the array by yourself. This ArrayList is
     * shared.
     *
     * @return the ArrayList of status lines.
     */
    public ArrayList<String> getStatusLines() { return statusLines; }

    /**
     * Insert a string to be printed in the status array. Using 10 point font,
     * you should get about twenty entries.
     * 
     * @param s string to add to status array.
     */
    public void addStatusLine(String s) { statusLines.add(s); }

    /**
     * Remove status line ind. Make sure it exists! I'm not going to catch this
     * error as it might mask some other problem. Note that when removing from
     * an ArrayList, all the indeces after that index shift downwards.
     *
     * I don't know of a better mechanism for removal than this...
     * 
     * @param ind
     */
    public void removeStatusLine(int ind) { statusLines.remove(ind); }

    /**
     * Delete all status lines.
     */
    public void clearStatusLines() { statusLines.clear(); }

    public int getTotalShapes() { return shapes.size(); }


    /**
     * The getShape*() functions return a shared ArrayList. Make sure you update
     * them in tandem, each entry expects a corresponding other two entries.
     *
     * @return ArrayList of shapes.
     */
    public ArrayList<Shape> getShapes() { return shapes; }

    /**
     * The getShape*() functions return a shared ArrayList. Make sure you update
     * them in tandem, each entry expects a corresponding other two entries.
     *
     * @return ArrayList of booleans denoting solid shapes.
     */
    public ArrayList<Boolean> getShapeFills() { return shapeFills; }

    /**
     * The getShape*() functions return a shared ArrayList. Make sure you update
     * them in tandem, each entry expects a corresponding other two entries.
     *
     * @return ArrayList of shape colors.
     */
    public ArrayList<Color> getShapeColors() { return shapeColors; }

    /**
     * The getShape*() functions return a shared ArrayList. Make sure you update
     * them in tandem, each entry expects a corresponding other two entries.
     *
     * @return ArrayList of shape strokes.
     */
    public ArrayList<Stroke> getShapeStrokes() { return shapeStrokes; }

    /**
     * Insert a shape and its respective color and stroke or to draw it as a
     * solid. null can be specified for Color or Stroke to use the default
     * value.
     *
     * @param sh shape to draw
     * @param f whether shape is solid (filled).
     * @param c color to draw shape in (defaults to green).
     * @param st stroke to draw shape with (defaults to 1px solid).
     */
    public void addShape(Shape sh, boolean f, Color c, Stroke st)
    {
        shapes.add(sh);
        shapeFills.add(f);
        shapeColors.add(c);
        shapeStrokes.add(st);
    }

    /**
     * Remove the shape at index ind. Make sure it exists! I'm not going to catch this
     * error as it might mask some other problem. Note that when removing from
     * an ArrayList, all the indeces after that index shift downwards.
     *
     * I don't know of a better mechanism for removal than this...
     *
     * @param ind index of shape to be removed.
     */
    public void removeShape(int ind)
    {
        shapes.remove(ind);
        shapeFills.remove(ind);
        shapeColors.remove(ind);
        shapeStrokes.remove(ind);
    }

    /**
     * Delete all shapes.
     */
    public void clearShapes()
    {
        shapes.clear();
        shapeFills.clear();
        shapeColors.clear();
        shapeStrokes.clear();
    }

    public void drawImage(int[] dat)
    {
        draw.setRGB(0, 0, draw.getWidth(), draw.getHeight(), dat, 0, draw.getWidth());
        repaint();
    }

    public Graphics2D createGraphics() { return draw.createGraphics(); }

    /**
     * If you're reading this, you shouldn't call this function. Use repaint()
     * instead.
     * 
     * @param graph Graphics object.
     */
    @Override
    public void paint(Graphics graph)
    {
        Graphics2D g = (Graphics2D)graph;

        super.paint(g);

        g.drawImage(draw, 0, 0, getWidth(), getHeight(), this);

        for(int i = 0; i < shapes.size(); i++)
        {
            Color c = shapeColors.get(i);
            Stroke s = shapeStrokes.get(i);

            if(c == null) g.setColor(defShapeColor);
            else g.setColor(c);

            if(s == null) g.setStroke(defShapeStroke);
            else g.setStroke(s);

            if(shapeFills.get(i))
                g.fill(shapes.get(i));
            else
                g.draw(shapes.get(i));
        }

        int h = getHeight() - lineSpacing;
        g.setFont(statFont); g.setColor(statColor);
        for(String s: statusLines)
        {
            g.drawString(s, 0, h);
            h -= lineHeight - lineSpacing;
        }
    }
}
