/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Detallepregunta;
import jcinform.persistencia.Pregunta;
import jcinform.persistencia.Respuestasencuesta;
import jcinform.procesos.Administrador;
import net.sf.jasperreports.engine.JRDataSource;
import sources.ReporteNotasDataSource;

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
        resumen(new Cursos(134));
//DecimalFormat formateador = new DecimalFormat("####.00#");
////DecimalFormat f = new DecimalFormat().applyPattern("####.00#");
//// Esto sale en pantalla con cuatro decimales, es decir, 3,4324
//DecimalFormat f = new DecimalFormat("");
//           System.out.println(""+
//           new java.text.DecimalFormat("####.00").format(3.40));

    }

    public static void resumen(Cursos curso) {
        Administrador adm = new Administrador();
        //     Session ses = Sessions.getCurrent();
        //     List<Nota> lisNotas = new ArrayList();
        List<Pregunta> preg = adm.query("Select o from Pregunta as o ");
        int i = 1;
        for (Iterator<Pregunta> it = preg.iterator(); it.hasNext();) {
            Pregunta pregunta = it.next();
            System.out.println(i + ") " + pregunta.getPregunta());
            List<Detallepregunta> detall = adm.query("Select o from Detallepregunta as o where o.pregunta.codigo = '" + pregunta.getCodigo() + "' ");
            int a = 1;
            for (Iterator<Detallepregunta> it1 = detall.iterator(); it1.hasNext();) {
                Detallepregunta detallepregunta = it1.next();
                
                Object respuestas = adm.querySimple("Select count(o) from Respuestasencuesta as o "
                        + "where o.detallepregunta.codigo = '" + detallepregunta.getCodigo() + "' and o.matricula.curso.codigocur  = '" + curso.getCodigocur() + "' ");
                System.out.println("\t *  " + detallepregunta.getOpcion() + "\t" + respuestas);
          

//                a++;
            }
            System.out.println("");
            System.out.println("-----------------------------------------------------------------------");
            i++;

        }


//        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
//        return ds;


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
