/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team._1984.image;

/**
 * Implementation of the Canny edge detection algorithm by Benjamin J. Land.
 * Modified by Sid Spry.
 *
 * @author sid, ben land
 */
public class EdgeDetector
{

    private DetectionType detector;

    private boolean guassian, nonmaxima, trace;
    private int[] kernel;
    private int gaussSize, gaussSum, low, high;

    /**
     * Creates an EdgeDetector that can be reused to save initialization time
     * @param gaussSize The size of the Gaussian kernel (2 or greater) or zero for no Gaussian
     * @param sigma The sigma for the guassian distribution, [0.5,2] works well
     * @param detector The type of detector to use
     * @param nonmaxima True if nonmaxima suppression should be used
     * @param lo The minimum value that can continue an edge (if hi is nonzero)
     * @param hi Nonzero enables tracing, minimum value that can identify an edge
     */
    public EdgeDetector(int gaussSize, double sigma, DetectionType detector, boolean nonmaxima, int low, int high)
    {
        if(gaussSize != 0)
        {
            guassian = true;
            this.gaussSize = gaussSize;

            kernel = new int[gaussSize * 2 - 1];
            double min = gaussian(gaussSize, gaussSize, sigma);

            for(int x = 0; x < gaussSize; x++)
            {
                int val = (int) Math.round(gaussian(gaussSize - x, gaussSize - x, sigma) / min);
                kernel[x] = val;
                kernel[2 * gaussSize - 2 - x] = val;
                gaussSum += x == gaussSize - 1 ? val : val * 2;
            }
        }

        this.detector = detector;
        this.nonmaxima = nonmaxima;

        if(low < high && high != 0)
        {
            trace = true;
            this.low = low;
            this.high = high;
        }
    }

    public static int[] rgbToGrayscale(int[] rgb)
    {
        int[] intensity = new int[rgb.length];
        for(int i = 0; i < rgb.length; i++)
            intensity[i] = (((rgb[i] & 0xFF0000) >> 16) + ((rgb[i] & 0xFF00) >> 8) + (rgb[i] & 0xFF)) / 3;
        return intensity;
    }

    public static int[] grayscaleToRGB(int[] intensity)
    {
        int[] rgb = new int[intensity.length];
        for(int i = 0; i < intensity.length; i++)
            rgb[i] = (((intensity[i] & 0xFF) << 16) | ((intensity[i] & 0xFF) << 8) | (intensity[i] & 0xFF));
        return rgb;
    }

    public static void normalize(int[] data, int max)
    {
        int hi = Integer.MIN_VALUE, lo = Integer.MAX_VALUE;
        for(int i = 0; i < data.length; i++)
        {
            if(hi < data[i]) hi = data[i];
            if(lo > data[i]) lo = data[i];
        }

        int range = hi - lo;
        if(range == 0) return;
        for(int i = 0; i < data.length; i++)
            data[i] = (data[i] - lo) * max / range;
    }

    /**
     * Computes the gaussian distribution value given a point and sigma
     * @param x X coordinate
     * @param y Y coordinate
     * @param sigma Gaussian normal distribution sigma
     * @return Gaussian distribution value
     */
    private double gaussian(int x, int y, double sigma)
    {
        return Math.exp(-Math.sqrt(x * x + y * y) / (2D * sigma * sigma));
    }

    public int[] process(int[] gray, int w, int h)
    {
        int s = w * h;
        if(gray.length != s) throw new RuntimeException("Invalid array size");
        
        if(guassian)
        {
            int[] blur = new int[s];
            int we = w - gaussSize + 1, he = h - gaussSize + 1;
            for(int y = 0, i = gaussSize - 1; y < h; y++, i += (gaussSize - 1) * 2)
                for(int x = gaussSize - 1; x < we; x++, i++)
                {
                    int val = gray[i] * kernel[gaussSize - 1];
                    for(int j = 1; j < gaussSize; j++)
                        val += gray[i - gaussSize + j] * kernel[j - 1];
                    blur[i] = val / gaussSum;
                }

            gray = new int[s];
            for(int y = gaussSize - 1, i = y * w + gaussSize - 1; y < he; y++)
                for(int x = 0; x < w; x++, i++)
                {
                    int val = gray[i] * kernel[gaussSize - 1];
                    for(int j = 1; j < gaussSize; j++)
                        val += blur[i - (gaussSize - j) * w] * kernel[j - 1];
                    gray[i] = val / gaussSum;
                }
        }
        
        int[][] data = detector.process(gray, w, h);
        int[] mags = data[0];
        int[] dirs = data[1];
        int[] maxima;

        if(nonmaxima)
        {
            maxima = new int[s];
            for(int y = 1, i = w + 1; y < h - 1; y++, i += 2)
                for(int x = 1; x < w - 1; x++, i++)
                    if((dirs[i - w] + dirs[i + w] + dirs[i - w - 1] + dirs[i + w + 1] + dirs[i - 1] + dirs[i + 1] + dirs[i - w + 1] + dirs[i + w - 1]) / 8.005 < dirs[i])
                        maxima[i] = mags[i];
        }
        else
            maxima = mags;
        
        normalize(maxima, 255);

        int[] detected;
        if(trace)
        {
            detected = new int[w * h];
            int[] stack = new int[w * h];
            for(int y = 1, i = w + 1; y < h - 1; y++, i += 2)
                for(int x = 1, c = 0; x < w - 1; x++, i++)
                    if(detected[i] == 0 && maxima[i] > high)
                    {
                        stack[c++] = i;
                        while(c > 0)
                        {
                            int n = stack[--c];
                            if(detected[n] == 0 && maxima[n] > low)
                            {
                                detected[n] = 255;
                                stack[c++] = n + 1;
                                stack[c++] = n - w + 1;
                                stack[c++] = n - w;
                                stack[c++] = n - w - 1;
                                stack[c++] = n - 1;
                                stack[c++] = n + w - 1;
                                stack[c++] = n + w;
                                stack[c++] = n + w + 1;
                            }
                        }
                    }
        }
        else
            detected = maxima;
        return detected;
    }
}
