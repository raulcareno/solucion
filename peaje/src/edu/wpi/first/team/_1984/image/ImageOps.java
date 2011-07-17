/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team._1984.image;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Stack;

/**
 *
 * @author sid
 */
public class ImageOps
{
    /**
     * Finds a color in the image data array by searching left-right top-down.
     *
     * @param img image data array
     * @param w width
     * @param h height
     * @param c color
     * @return the Point where the color was found, if nonexistent, null.
     */
    public static Point findColorY(int[] img, int w, int h, int c)
    {
        for(int y = 0; y < h; y++)
            for(int x = 0; x < w; x++)
                if(img[(y * w) + x] == c) return new Point(x, y);
        
        return null;
    }

    /**
     * Finds a color in the image data array by searching top-down left-right.
     *
     * @param img image data array
     * @param w width
     * @param h height
     * @param c color
     * @return the Point where the color was found, if nonexistent, null
     */
    public static Point findColorX(int[] img, int w, int h, int c)
    {
        for(int x = 0; x < w; x++)
            for(int y = 0; y < h; y++)
                if(img[(y * w) + x] == c) return new Point(x, y);
        
        return null;
    }

    /**
     * Flood-fills a contiguous area of an image of color c, starting at Point
     * p with color r.
     *
     * @param img image data array
     * @param w width
     * @param h height
     * @param c color
     * @param r replacement color
     * @param p point
     * @return bounding rectangle of the area filled
     */
    public static Rectangle floodFill(int[] img, int w, int h, int c, int r, Point p)
    {
        if(p.x < 0 || p.y < 0 || p.x > w || p.y > h) return null;
        if(c == r || img[(p.y * w) + p.x] != c) return null;

        Rectangle ret = new Rectangle(p.x, p.y, 0, 0);

        Stack<Point> s = new Stack<Point>();
        s.push(p);

        Point t;
        int y1;
        boolean spanLeft, spanRight;

        while(!s.isEmpty())
        {
            t = s.pop();

            y1 = t.y;
            while(y1 >= 0 && img[(y1 * w) + t.x] == c) y1--;
            y1++;

            spanLeft = spanRight = false;

            while(y1 < h && img[(y1 * w) + t.x] == c)
            {
                img[(y1 * w) + t.x] = r;

                // Push the upper-left corner up if necessary.
                // We might need to expand the height, as it is relative.
                if(y1 < ret.y)
                {
                    ret.height += y1 - ret.y;
                    ret.y = y1;
                } // Expand the height if necessary.
                else if(y1 > ret.y + ret.height)
                    ret.height = y1 - ret.y;

                if(!spanLeft && t.x > 0 && img[(y1 * w) + t.x - 1] == c)
                {
                    s.push(new Point(t.x - 1, y1));

                    // Push the upper-left corner left if necessary.
                    // We might need to expand the width, as it is relative.
                    if(t.x < ret.x)
                    {
                        ret.width += ret.x - t.x;
                        ret.x = t.x;
                    }

                    spanLeft = true;
                } else if(spanLeft && t.x > 0 && img[(y1 * w) + t.x - 1] != c)
                {
                    spanLeft = false;
                }

                if(!spanRight && t.x < w - 1 && img[(y1 * w) + t.x + 1] == c)
                {
                    s.push(new Point(t.x + 1, y1));

                    // Expand height if necessary.
                    if(t.x > ret.x + ret.width)
                        ret.width = t.x - ret.x;

                    spanRight = true;
                } else if(spanRight && t.x < w - 1 && img[(y1 * w) + t.x + 1] != c)
                {
                    spanRight = false;
                }

                y1++;
            }
        }

        ret.width++;
        return ret;
    }

    /**
     * Convert an image into a binary image (but still in INT_ARGB format) with
     * all occurrences of c as white.
     * 
     * @param img image data array
     * @param c color
     */
    public static void maskColor(int[] img, int c)
    {
        for(int i = 0; i < img.length; i++)
            img[i] = img[i] == c ? 0xFFFFFFFF : 0xFF000000;
    }

    /**
     * Convert an image into a binary image (but still in INT_ARGB format) with
     * all occurrences of c within tolerance t as white.
     * 
     * @param img image data array
     * @param c color
     * @param t tolerance
     */
    public static void maskColorTol(int[] img, int c, int t)
    {
        for(int i = 0; i < img.length; i++)
            img[i] = tol1(img[i], c, t) ? 0xFFFFFFFF : 0xFF000000;
    }

    /**
     * Adds the pixels of src with dst, storing them in dst.
     * 
     * @param src source image
     * @param dst destination image
     */
    public static void add(int[] src, int[] dst)
    {
        if(src.length != dst.length)
            throw new IllegalArgumentException("Images must be of same dimension.");

        for(int i = 0; i < src.length; i++)
            dst[i] += src[i];
    }

    /**
     * Subtract the pixels of src from dst, storing the results in dst.
     *
     * @param src source image
     * @param dst destination image
     */
    public static void sub(int[] src, int[] dst)
    {
        if(src.length != dst.length)
            throw new IllegalArgumentException("Images must be of same dimension.");

        for(int i = 0; i < src.length; i++)
            dst[i] = src[i] - dst[i];
    }

    /**
     * Multiply the pixels of src with those of dst, storing the results in dst.
     *
     * @param src source image
     * @param dst destination image
     */
    public static void mul(int[] src, int[] dst)
    {
        if(src.length != dst.length)
            throw new IllegalArgumentException("Images must be of same dimension.");

        for(int i = 0; i < src.length; i++)
            dst[i] *= src[i];
    }

    /**
     * Exclusive-or the pixels of src and dst, storing the results in dst.
     *
     * @param src source image
     * @param dst destination image
     */
    public static void xor(int[] src, int[] dst)
    {
        if(src.length != dst.length)
            throw new IllegalArgumentException("Images must be of same dimension.");

        for(int i = 0; i < src.length; i++)
            dst[i] ^= src[i];
    }

    // cts0: rdif < tol & bdif < tol & gdif < tol
    // cts1: sqrt(rdif²+gdif²+bdif²)
    // cts2: H < cts2mod1*tol & S < cts2mod2*tol & L < tol; def mod = .2, .2
    // cts3: xyz (unknown)
    
    // Fastest, most inclusive.
    public static boolean tol1(int p1, int p2, int t)
    {
        return Math.abs(((p1 & 0xFF0000) >> 16) - ((p2 & 0xFF0000) >> 16)) <= t
                && Math.abs(((p1 & 0xFF00) >> 8) - ((p2 & 0xFF00) >> 8)) <= t
                && Math.abs(((p1 & 0xFF)) - ((p2 & 0xFF))) <= t;
    }

    // Second fastest, less inclusive than above RGB method.
    public static boolean tol2(int p1, int p2, int t)
    {
        return Math.sqrt(
                Math.pow(Math.abs(((p1 & 0xFF0000) >> 16) - ((p2 & 0xFF0000) >> 16)), 2)
                + Math.pow(Math.abs(((p1 & 0xFF00) >> 8) - ((p2 & 0xFF00) >> 8)), 2)
                + Math.pow(Math.abs(((p1 & 0xFF)) - ((p2 & 0xFF))), 2)) <= t;
    }
}
