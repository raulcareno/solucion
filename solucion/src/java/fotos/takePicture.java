package fotos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class takePicture {

    private String fileType;

    public takePicture() {
    }

    public byte[] procesar() {

        try {
//            int w = Integer.parseInt(request.getParameter("width"));
//            int h = Integer.parseInt(request.getParameter("height"));
            
            int w = Integer.parseInt(Executions.getCurrent().getParameter("width"));
            int h = Integer.parseInt(Executions.getCurrent().getParameter("height"));

            String fileName = "image2.jpg";
            BufferedImage imageBuffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//             System.out.println(""+Executions.getCurrent().getParameter("diagramId"));
//            int diagramId = Integer.parseInt(request.getParameter("diagramId"));

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    imageBuffer.setRGB(i, j, 0xffffff);
                }
            }

            int rows = 0;
            int cols = 0;

            String rowText = "";
            int countcount = 0;
            for (rows = 0; rows < h; rows++) {

                rowText = Executions.getCurrent().getParameter("px" + rows); //recieving the row of the image thru http
                StringTokenizer st = null;

                st = new StringTokenizer(rowText, ",", true); // separating by comma

                String firstToken = "";
                String secondToken = "";

                String rgbText;
                int rgb = 0;

                cols = -1;
                while (st.hasMoreTokens()) {

                    secondToken = st.nextToken();

                    if (firstToken.equals("") && secondToken.equals(",")) // the first ever token
                    {
                        cols++;
                        rgbText = "ffffff";
                        rgbText = getCorrectedColor(rgbText);

                        int red = getRedInInt(rgbText);
                        int blue = getBlueInInt(rgbText);
                        int green = getGreenInInt(rgbText);

                        rgb = (red << 16 | green << 8 | blue);

                        try {
                            imageBuffer.setRGB(cols, rows, rgb);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else if (firstToken.equals(",") && secondToken.equals(",")) //there is no value between the commas
                    {

                        cols++;
                        rgbText = "ffffff"; // set the color as white
                        rgbText = getCorrectedColor(rgbText);

                        int red = getRedInInt(rgbText);
                        int blue = getBlueInInt(rgbText);
                        int green = getGreenInInt(rgbText);

                        rgb = (red << 16 | green << 8 | blue);

                        try {
                            imageBuffer.setRGB(cols, rows, rgb);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else if (firstToken.equals(",")) // there is some color value
                    {
                        cols++;
                        rgbText = secondToken;
                        rgbText = getCorrectedColor(rgbText);

                        int red = getRedInInt(rgbText);
                        int blue = getBlueInInt(rgbText);
                        int green = getGreenInInt(rgbText);

                        rgb = (red << 16 | green << 8 | blue);

                        try {
                            imageBuffer.setRGB(cols, rows, rgb);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }

                    firstToken = secondToken;
                }
            }


            //ImageIO.write(imageBuffer, fileType, new File(fileName));
            java.io.File f = new java.io.File(fileName);
            ImageIO.write(imageBuffer, "jpg", f);
            FileInputStream fin = null;
            fin = new FileInputStream(f);
            byte[] buffer = new byte[(int) f.length()];
            fin.read(buffer);
            fin.close();
            return buffer;
            //foto0.setContent(new org.zkoss.image.AImage("fotito", buffer));
//            imageBuffer.get
//        }
        } catch (Exception e) {
            System.out.println("Exception in writing image file saveImage(): ");
            e.printStackTrace();
        }
        return null;
    }

    public int getRedInInt(String color1) {
        StringBuilder color = new StringBuilder(color1);
        StringBuilder r = new StringBuilder(new Character(color.charAt(2)).toString());
        r.append(new Character(color.charAt(3)).toString());
        return Integer.decode("0x" + r);
    }

    public int getGreenInInt(String color1) {
        StringBuilder color = new StringBuilder(color1);
        StringBuilder g = new StringBuilder(new Character(color.charAt(4)).toString());
        g.append(new Character(color.charAt(5)).toString());
        return Integer.decode("0x" + g);
    }

    public int getBlueInInt(String color1) {
        StringBuilder color = new StringBuilder(color1);
        StringBuilder b = new StringBuilder(new Character(color.charAt(6)).toString());
        b.append(new Character(color.charAt(7)).toString());
        return Integer.decode("0x" + b);
    }

    public String getCorrectedColor(String color) {
        String newColor = "0x";
        int shortcome = 6 - color.length();
        //pad with zeros
        for (int i = 0; i < shortcome; i++) {
            newColor += "0";
        }
        //the color value always starts at the index 2
        for (int i = 0; i < color.length(); i++) {
            newColor += new Character(color.charAt(i)).toString();
        }
        return newColor;
    }
}
