/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miniaturas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Ismael Jadan
 */
public class CrearMiniaturas {

    public void CrearMiniaturas() {
    }

    /**
     * @param args the command line arguments
     */
    public static void reducir(String ubicacion, String nombre, String formato) throws IOException {
        // TODO code application logic here
        ProcesadorImagenes p = new ProcesadorImagenes();
        File f = new File(ubicacion + nombre + "." + formato);
        BufferedImage bf = p.escalarATamanyo(f, 600, 400, formato);
        try {
            ImageIO.write(bf, formato, new File(ubicacion + nombre + "_t." + formato));
        } catch (IOException e) {
            System.out.println("Error de escritura");
        }



    }
}
