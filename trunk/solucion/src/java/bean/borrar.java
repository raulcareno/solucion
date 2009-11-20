/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.util.List;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;

 
import org.zkoss.util.media.AMedia;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zul.Fileupload;


/**
 *
 * @author geovanny
 */
public class borrar {
//Panel p;
void func() throws InterruptedException{
//    Media media = Fileupload.get();
//    media.getByteData();
//    AImage a;
//    a.getByteData()
//			byte[] archivo=((AMedia) media).get;

}
 

        public static void bandejaEstudiante(Estudiantes est){
        Administrador adm = new Administrador();
        List<Correos> a = adm.query("Select o from Correos as o where o.destinatario = '"+est.getCodigoest()+"' " +
        "and  o.tiporemitente = 'P' and o.eliminado = false and o.archivado = false ");
            //System.out.println(""+a.get(0).getTema() + " "+ a.get(0).getRemitenten());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        bandejaEstudiante(new Estudiantes(1));
    }

}
