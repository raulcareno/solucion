/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team._1984.image;

/**
 * Implementation of the Sobel and Scharr processes along with one custom
 * filter.
 *
 * @author sid, ben land
 */
public enum DetectionType
{

    Sobel
    {
        int[][] process(int[] intensity, int w, int h)
        {
            int we = w - 1, he = h - 1;
            int[] dx = new int[w * h];
            int[] dy = new int[w * h];
            for(int y = 0, i = 1; y < h; y++, i += 2)
                for(int x = 1; x < we; x++, i++)
                {
                    dx[i] = intensity[i + 1] - intensity[i - 1];
                    dy[i] = intensity[i - 1] + 2 * intensity[i] + intensity[i + 1];
                }

            int[] mags = new int[w * h];
            int[] dir = new int[w * h];
            int xmag, ymag;
            for(int y = 1, i = w; y < he; y++)
            {
                for(int x = 0; x < w; x++, i++)
                {
                    xmag = dx[i - w] + 2 * dx[i] + dx[i + w];
                    ymag = dy[i + w] - dy[i - w];
                    mags[i] = Math.abs(xmag) + Math.abs(ymag);
                    if(ymag > 0)
                    {
                        if(xmag > 0)
                        {
                            if(ymag > 0.414 * xmag)
                            {
                                dir[i] = 0;
                            } else if(ymag < 2.414 * xmag)
                            {
                                dir[i] = 2;
                            } else
                            {
                                dir[i] = 1;
                            }
                        } else
                        {
                            if(ymag < 0.414 * xmag)
                            {
                                dir[i] = 4;
                            } else if(ymag > 2.414 * xmag)
                            {
                                dir[i] = 2;
                            } else
                            {
                                dir[i] = 3;
                            }
                        }
                    } else
                    {
                        if(xmag > 0)
                        {
                            if(ymag < 0.414 * xmag)
                            {
                                dir[i] = 0;
                            } else if(ymag > 2.414 * xmag)
                            {
                                dir[i] = 6;
                            } else
                            {
                                dir[i] = 7;
                            }
                        } else
                        {
                            if(ymag > 0.414 * xmag)
                            {
                                dir[i] = 4;
                            } else if(ymag < 2.414 * xmag)
                            {
                                dir[i] = 6;
                            } else
                            {
                                dir[i] = 5;
                            }
                        }
                    }
                }
            }
            return new int[][] { mags, dir };
        }
    },

    Fast
    {
        int[][] process(int[] intensity, int w, int h)
        {
            int[] mags = new int[w * h];
            int[] dir = new int[w * h];

            int we = w - 1, he = h - 1;
            int i = w + 1;
            int xmag, ymag;

            for(int y = 1; y < he; y++, i += 2)
                for(int x = 1; x < we; x++, i++)
                {
                    xmag = (intensity[i + 1] - intensity[i - 1]);
                    ymag = (intensity[i + w] - intensity[i - w]);
                    mags[i] = Math.abs(xmag) + Math.abs(ymag);
                    if(ymag > 0)
                    {
                        if(xmag > 0)
                        {
                            if(ymag > 0.414 * xmag)
                            {
                                dir[i] = 0;
                            } else if(ymag < 2.414 * xmag)
                            {
                                dir[i] = 2;
                            } else
                            {
                                dir[i] = 1;
                            }
                        } else
                        {
                            if(ymag < 0.414 * xmag)
                            {
                                dir[i] = 4;
                            } else if(ymag > 2.414 * xmag)
                            {
                                dir[i] = 2;
                            } else
                            {
                                dir[i] = 3;
                            }
                        }
                    } else
                    {
                        if(xmag > 0)
                        {
                            if(ymag < 0.414 * xmag)
                            {
                                dir[i] = 0;
                            } else if(ymag > 2.414 * xmag)
                            {
                                dir[i] = 6;
                            } else
                            {
                                dir[i] = 7;
                            }
                        } else
                        {
                            if(ymag > 0.414 * xmag)
                            {
                                dir[i] = 4;
                            } else if(ymag < 2.414 * xmag)
                            {
                                dir[i] = 6;
                            } else
                            {
                                dir[i] = 5;
                            }
                        }
                    }
                }
            return new int[][] { mags, dir };
        }
    },

    Scharr
    {
        int[][] process(int[] intensity, int w, int h)
        {
            int we = w - 1, he = h - 1;

            int[] dx = new int[w * h];
            int[] dy = new int[w * h];

            for(int y = 0, i = 1; y < h; y++, i += 2)
                for(int x = 1; x < we; x++, i++)
                {
                    dx[i] = intensity[i + 1] - intensity[i - 1];
                    dy[i] = 3 * intensity[i - 1] + 10 * intensity[i] + 3 * intensity[i + 1];
                }

            int[] mags = new int[w * h];
            int[] dir = new int[w * h];
            int xmag, ymag;

            for(int y = 1, i = w; y < he; y++)
                for(int x = 0; x < w; x++, i++)
                {
                    xmag = 3 * dx[i - w] + 10 * dx[i] + 3 * dx[i + w];
                    ymag = dy[i + w] - dy[i - w];
                    mags[i] = Math.abs(xmag) + Math.abs(ymag);
                    if(ymag > 0)
                    {
                        if(xmag > 0)
                        {
                            if(ymag > 0.414 * xmag)
                            {
                                dir[i] = 0;
                            } else if(ymag < 2.414 * xmag)
                            {
                                dir[i] = 2;
                            } else
                            {
                                dir[i] = 1;
                            }
                        } else
                        {
                            if(ymag < 0.414 * xmag)
                            {
                                dir[i] = 4;
                            } else if(ymag > 2.414 * xmag)
                            {
                                dir[i] = 2;
                            } else
                            {
                                dir[i] = 3;
                            }
                        }
                    } else if(xmag > 0)
                    {
                        if(ymag < 0.414 * xmag)
                        {
                            dir[i] = 0;
                        } else if(ymag > 2.414 * xmag)
                        {
                            dir[i] = 6;
                        } else
                        {
                            dir[i] = 7;
                        }
                    } else if(ymag > 0.414 * xmag)
                    {
                        dir[i] = 4;
                    } else if(ymag < 2.414 * xmag)
                    {
                        dir[i] = 6;
                    } else
                    {
                        dir[i] = 5;
                    }

                }
            return new int[][] { mags, dir };
        }
    };

    abstract int[][] process(int[] intensity, int w, int h);
}
