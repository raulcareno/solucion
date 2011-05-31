/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peaje.formas;

/**
 *
 * @author Familia Jadan Cahueñ
 */
//Paquetes para guardar imagen (Es necesario tener las apis _JMF_)
//By: CyberServer crow_15@hotmail.com
import javax.swing.*;
import java.awt.*;
import java.awt.Component;
import java.util.Date;
import javax.media.*;
import javax.media.control.*;
import javax.media.util.*;
import javax.media.format.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.RenderedImage;

class CamaraWeb {

    public Component componente = null;
    public Player p = null;
    public Component video;
    public MediaLocator ml;

 
    CamaraWeb() {
        Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);
        try {
            ml = new MediaLocator("vfw:Microsoft WDM Image Capture (Win32):0");
            //ml = new MediaLocator("vfw://0");
            //MediaLocator ml = new MediaLocator("vfw:Microsoft WDM Image Capture (Win32):0");
            p = Manager.createRealizedPlayer(ml);
            video = p.getVisualComponent();
            p.start();
            if (video != null) {
                componente = video;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de Comunicacion con la WebCam " + e);
        }
    }

    public Component VerCamara(int x, int y, int alto, int ancho) {
        this.componente.setBounds(x, y, alto, ancho);
        return componente;
    }

    public int Fotografiar(String Carpeta, Boolean NombreAutomatico, String NombreValoNull) {
        Buffer buf = null;
        Image img = null;
        File imagenArch;
        String nombre = null;
        String formato = null;
        Date HoraDate;
        String HoraString = null;
        File CarpetaFotografias;


        CarpetaFotografias = new File(Carpeta);
        if (CarpetaFotografias.exists() == false) {
            CarpetaFotografias.mkdir();
        }
        FrameGrabbingControl fgc = (FrameGrabbingControl) p.getControl("javax.media.control.FrameGrabbingControl");
        buf = fgc.grabFrame();
        BufferToImage btoi = new BufferToImage((VideoFormat) buf.getFormat());
        img = btoi.createImage(buf);
        if (img != null) {
            if (NombreAutomatico == true) {
                HoraDate = new Date();
                HoraString = Integer.toString(HoraDate.getDate()) + "-" + Integer.toString(HoraDate.getMonth()) + "-" + Integer.toString(HoraDate.getYear())
                        + "-" + Integer.toString(HoraDate.getHours()) + "-" + Integer.toString(HoraDate.getMinutes()) + "-" + HoraDate.getSeconds();
                nombre = Carpeta + "\\" + HoraString + ".jpg";
            } else {
                nombre = Carpeta + "\\" + NombreValoNull + ".jpg";
            }

            imagenArch = new File(nombre);
            formato = "JPEG";
            try {
                ImageIO.write((RenderedImage) img, formato, imagenArch);
                return 1;
            } catch (IOException ioe) {
                return 0;
            }
        }
        return 0;
    }
}
//http://foro.elhacker.net/java/manejar_webcam_o_camaraweb_desde_java-t293716.0.html;msg1578566#ixzz1NwQGWfWm

