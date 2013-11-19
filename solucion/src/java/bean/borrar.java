/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Estudiantes;
import jcinform.procesos.Administrador;

//import org.zkoss.util.media.Media;
//import org.zkoss.zul.Fileupload;
//import org.zkoss.zul.Listbox;
//import org.zkoss.zul.Listcell;
/**
 * t
 *
 * @author geovanny
 */
public class borrar {
//Panel p;

//Progressmeter p;
//p.set
    public static void main(String[] args) {
        HashSet a = new HashSet();
        System.gc();
//        generalMatriculaMateria ab = new generalMatriculaMateria(1,1);
//        a.add(ab);
//        //ab = new generalMatriculaMateria(1,1);
//        ab.materia = 1;
//        ab.matricula = 1;
//        a.add(ab);
//        
//        ab.materia = 2;
//        ab.matricula = 1;
//        a.add(ab);
//        //a.add(ac);
//        //a.add(ac);
//        
//        for( Iterator it2 = a.iterator(); it2.hasNext();) { 
//	    
////	    generalMatriculaMateria x = (generalMatriculaMateria)it2.next();
//	    System.out.println(x.matricula + " : " + x.materia);
//
//	}
        
        DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
    //simbolo.setDecimalSeparator('.');
    
        System.out.println("SEPARADOR: "+simbolo.getDecimalSeparator());
    //simbolo.setGroupingSeparator(',');
    
    
    /**
      
        DecimalFormat formateador = new DecimalFormat("###,###.##",simbolo);
//break;
        
        Administrador adm = new Administrador();
        List estudiantesEncontrados = adm.query("Select o from Estudiantes as o where o.cedula = '1727351353' ");
        if (estudiantesEncontrados.size() > 0) {

            Estudiantes estActual = (Estudiantes) estudiantesEncontrados.get(0);
            estudiantesEncontrados = adm.query("Select o from Estudiantes as o where o.apellido like '%A%' and o.codigoest not in (" + estActual.getCodigoest() + ") ", 0, 6);
            estudiantesEncontrados.add(estActual);
            System.out.println("NORMA:******************");
            for (Iterator itCan = estudiantesEncontrados.iterator(); itCan.hasNext();) {
                Estudiantes tCandidato = (Estudiantes) itCan.next();
                System.out.println("EST" + tCandidato.getCodigoest());

            }
            //ORDENO
            for (int i = estudiantesEncontrados.size() - 1; i > 0; i--) {
                int rand = (int) (Math.random() * (i + 1));
                Estudiantes temp = (Estudiantes) estudiantesEncontrados.get(i);
                estudiantesEncontrados.set(i, estudiantesEncontrados.get(rand));
                estudiantesEncontrados.set(rand, temp);
            }
            System.out.println("DESORDENADA");
            for (Iterator itCan = estudiantesEncontrados.iterator(); itCan.hasNext();) {
                Estudiantes tCandidato = (Estudiantes) itCan.next();
                System.out.println("EST" + tCandidato.getCodigoest());

            }


            // buscarCedulaPanel.visible = false; 
            //listadoListass.visible=true;
            // cargar();
        } else {
        }

     */
//        resumen(new Cursos(134));
//        Grid datos = new Grid();
//        datos.getChildren().clear();
//        Auxhead auh = new Auxhead();
//        Auxheader aud = new Auxheader();
//        Textbox est = new Textbox("");
//         est.setParent(aud);
//         aud.setParent(auh);
//         auh.setParent(datos);
// 
//         Columns cols = new Columns();
//              datos.removeChild(cols);
//         Column col = new Column("A");
//         cols.appendChild(col);
//         col.setParent(cols);
//         cols.setParent(datos); 
//    String[][] cabes = new String[22][2];
//         for (String[] strings : cabes) {
//             System.out.println(""+strings[0]);
//        }
//         Rows filasR = new notasEvaluacion();
//           filasR.setId("filas");
//           filasR.setParent(datos);
//            
//        <grid   id="datos" >
//                <auxhead>
//                    <auxheader colspan="2">
//                        <textbox cols="50" style="background: transparent;border:0px" readonly = "true"  />
//                    </auxheader>
//                    <auxheader forEach="${cabes}"  label="${each[0]}" colspan="${each[1]}"/>
//                </auxhead>
//                <columns>
//                    <column width="10px"   label ="Mat."/>
//                    <column width="300px" label ="Estudiante"/>
//                    <column forEach="${values}"  width="25px" label="${each[0]}" align="right"  />
//                </columns>
//                <rows id="filas" use="bean.notasEvaluacion">
//                </rows>
//            </grid>
//DecimalFormat formateador = new DecimalFormat("####.00#");
////DecimalFormat f = new DecimalFormat().applyPattern("####.00#");
//// Esto sale en pantalla con cuatro decimales, es decir, 3,4324
//DecimalFormat f = new DecimalFormat("");
//           System.out.println(""+
//           new java.text.DecimalFormat("####.00").format(3.40));

    }

 
//    public static void resumen(Cursos curso) {
//        Administrador adm = new Administrador();
//        //     Session ses = Sessions.getCurrent();
//        //     List<Nota> lisNotas = new ArrayList();
//        List<Pregunta> preg = adm.query("Select o from Pregunta as o ");
//        int i = 1;
//        for (Iterator<Pregunta> it = preg.iterator(); it.hasNext();) {
//            Pregunta pregunta = it.next();
//            System.out.println(i + ") " + pregunta.getPregunta());
//            List<Detallepregunta> detall = adm.query("Select o from Detallepregunta as o where o.pregunta.codigo = '" + pregunta.getCodigo() + "' ");
//            int a = 1;
//            for (Iterator<Detallepregunta> it1 = detall.iterator(); it1.hasNext();) {
//                Detallepregunta detallepregunta = it1.next();
//                
//                Object respuestas = adm.querySimple("Select count(o) from Respuestasencuesta as o "
//                        + "where o.detallepregunta.codigo = '" + detallepregunta.getCodigo() + "' and o.matricula.curso.codigocur  = '" + curso.getCodigocur() + "' ");
//                System.out.println("\t *  " + detallepregunta.getOpcion() + "\t" + respuestas);
//          
//
////                a++;
//            }
//            System.out.println("");
//            System.out.println("-----------------------------------------------------------------------");
//            i++;
//
//        }
//
//
////        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
////        return ds;
//
//
//    }
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
