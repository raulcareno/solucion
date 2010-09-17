/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import jcinform.persistencia.Inscripciones;
import jcinform.procesos.Administrador;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Tab;


//import org.zkoss.util.media.Media;
//import org.zkoss.zul.Fileupload;
//import org.zkoss.zul.Listbox;
//import org.zkoss.zul.Listcell;

/**
 *t
 * @author geovanny
 */
public class borrar {
//Panel p;

//Progressmeter p;
//p.set
       public static void main(String[] args) {
ArrayList ar = new ArrayList();

          
          

           String a = "ms";
            Radiogroup ras = null;
            List l = ras.getChildren();
            for (Iterator it = l.iterator(); it.hasNext();) {
               Radio object = (Radio)it.next();
               if(object.getLabel().equals(object)){
                   object.setSelected(true);
                   break;
               }

           }
            Radio ra = new Radio(a);
             ra.setSelected(true);
ras.setSelectedItem(ra);
             ras.getSelectedItem().getLabel();

             
            //ra.setSelectedItem(null)

           StringBuffer st = new StringBuffer();
                st.append("m");
                st.append("s");
Administrador adm = new Administrador();
Inscripciones inscrp = (Inscripciones) adm.buscarClave(adm, null);
           if(a.contains(st))
               System.out.println("ENTRO");
           else
               System.out.println("NO ");
Session ses = Sessions.getCurrent();
                    ses.setAttribute("userEstudiante","");
                    ses.setAttribute(org.zkoss.web.Attributes.PREFERRED_LOCALE, Locale.US);
//           matriculasBean matricula = new matriculasBean();
//           Administrador adm = new Administrador();
//
//           //Matriculas matri = (Matriculas) adm.buscarClave(1, Matriculas.class);
//           //matricula.actualizar(matri);
//            List<Matriculas> estu = adm.query("Select o from Matriculas as o where o.curso.periodo.codigoper = 1 ");
//            for (Iterator<Matriculas> it = estu.iterator(); it.hasNext();) {
//               Matriculas matriculas = it.next();
//                matricula.actualizar(matriculas);
//           }
           //matricula.agregar(matri);

//        try {
//

//            List<Matriculas> estu = adm.query("Select o from Matriculas as o where o.codigomat < 10  ");
//            crearDBF(estu);
//            Object[] ab = leerPdf(ubicacion2, new Double(5));
//            System.out.println("" + ab[0] + " " + ab[3]);
//        } catch (DBFException ex) {
//            Logger.getLogger(borrar.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(borrar.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }


 
    static claves cla = new claves();
        static String ubicacion2 = new String("/home/geovanny/Escritorio/base.dbf");

    /**
     * @param args the command line arguments
     */
 


//    public static Object[] leerPdf(String ubicacion, Double codigo) {
//        try {
//            System.out.println("Iniciamos proceso");
//            InputStream inputStream = new FileInputStream(ubicacion);
////            InputStream inputStream = new FileInputStream(new String("/home/geovanny/Escritorio/interfase.dbf"));
//            DBFReader reader = new DBFReader(inputStream);
//            Object[] rowObjects;
//
//            while ((rowObjects = reader.nextRecord()) != null) {
//                if (codigo.equals(rowObjects[0])) {
//                    inputStream.close();
//                    return rowObjects;
//                }
////                System.out.print(rowObjects[0].toString() + " ");
////                System.out.println(((String) rowObjects[1]).trim());
//
//
//            }
//            inputStream.close();
//
//        } catch (IOException ex) {
//            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//
//    }

//    public static DBFField[] estructura() {
//        DBFField fields[] = new DBFField[17];
//        fields[0] = new DBFField();
//        fields[0].setName("CODIGO");
//        fields[0].setFieldName("CODIGO");
//        writer.write(fos);
//
//        fos.close();
//    }
}
