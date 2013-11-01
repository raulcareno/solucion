/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peaje.formas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author jcinform
 */
public class tomarImage {

    public tomarImage() {
    }

    public void tomarFotoIp(String Carpeta, frmPrincipal frm) {
        URL nUrl = null;
        BufferedImage image = null;
        try {
            //"http://192.168.10.3/cgi-bin/viewer/video.jpg"
            nUrl = new URL(frm.empresaObj.getUrl());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        //public BufferedImage image = null;
        try {
            image = ImageIO.read(nUrl);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {

            File outputfile = new File(Carpeta + "");
            ImageIO.write(image, "jpg", outputfile);
            frm.camaraVista1.setIcon(new ImageIcon(image));

        } catch (IOException e) {
        }
        image = null;
        

    }

    public void mostrarFotop(JLabel frm,String url) {
        URL nUrl = null;
        BufferedImage image = null;
        try {
            nUrl = new URL(url);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        //public BufferedImage image = null;
        try {
            image = ImageIO.read(nUrl);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        try {

//            File outputfile = new File(Carpeta+"");
//            ImageIO.write(image, "jpg", outputfile);
                frm.setIcon(new ImageIcon(image));

//        } catch (IOException e) {
//        }

    }
}
