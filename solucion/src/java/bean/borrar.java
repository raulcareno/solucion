/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;
import org.zkoss.image.AImage;

 
import org.zkoss.util.media.Media;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;


/**
 *t
 * @author geovanny
 */
public class borrar {
//Panel p;
void func() throws InterruptedException{
    Media media = Fileupload.get();
    Listcell c = null;
    AImage a= null;

//    c.setImageContent(a);
    
    
    //c.setImageContent(null);

//    Listbox l;
//    l.getItems().get(0);
//    Iterator a = l.getItems().size();
//    a.hasNext();
//    Listitem ite = l.getItems().get(index);
//    ite.getValue()


 

//    media.getByteData();
//    AImage a;
//    a.getByteData()
//    Combobox c;
//    c.getSelectedItem().getValue()
     
//			byte[] archivo=(((AMedia) media).getStringData());
//    FileOutputStream a;
//    String ads;
//    ads.getBytes()
//
    

}
 

        public static void bandejaEstudiante(Estudiantes est){

//            Tablechildren sds;
//                    sds.se;

//
//        List<Correos> a = adm.query("Select o from Correos as o where o.destinatario = '"+est.getCodigoest()+"' " +
//        "and  o.tiporemitente = 'P' and o.eliminado = false and o.archivado = false ");
            //System.out.println(""+a.get(0).getTema() + " "+ a.get(0).getRemitenten());
         
        Listbox b;
//        b.getSelectedIndex();
//b.getRows()
//
//        List al = new ArrayList(items);
//							for (Iterator it = al.iterator(); it.hasNext();) {
//								Listitem li = (Listitem)it.next();
//                                li.
//								li.setSelected(false);
//


//							}

    }
static claves cla = new claves();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Administrador adm = new Administrador();
        List<Representante> estu = adm.query("Select o from Representante as o");
        for (Iterator<Representante> it = estu.iterator(); it.hasNext();) {
            Representante estudiantes = it.next();
            estudiantes.setClave(encriptar(estudiantes.getUsuario()));
            adm.actualizar(estudiantes);

        }
        
   
    }
       public static String encriptar(String clave) {
        try {

            if (clave.equals(null) || clave.equals("")) {
                return "";
            }
            return cla.encriptar(clave);
        } catch (Exception e) {


            return "";
        }
    }

}
