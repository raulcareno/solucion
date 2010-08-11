/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Locale;
import jcinform.persistencia.Inscripciones;
import jcinform.procesos.Administrador;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;


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
           String a = "ms";
            Radiogroup ras = null;
            Radio ra = new Radio(a);
             ra.setSelected(true);

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
//        fields[0].setDataType(DBFField.FIELD_TYPE_N);
//        fields[0].setFieldLength(10);
//
//        fields[1] = new DBFField();
//        fields[1].setFieldName("CODIGOM");
//        fields[1].setDataType(DBFField.FIELD_TYPE_C);
//        fields[1].setFieldLength(100);
//
//
//        fields[2] = new DBFField();
//        fields[2].setFieldName("CEDULA");
//        fields[2].setDataType(DBFField.FIELD_TYPE_C);
//        fields[2].setFieldLength(100);
//
//
//        fields[3] = new DBFField();
//        fields[3].setFieldName("APELLIDOS");
//        fields[3].setDataType(DBFField.FIELD_TYPE_C);
//        fields[3].setFieldLength(100);
//
//        fields[4] = new DBFField();
//        fields[4].setFieldName("NOMBRES");
//        fields[4].setDataType(DBFField.FIELD_TYPE_C);
//        fields[4].setFieldLength(100);
//
//        fields[5] = new DBFField();
//        fields[5].setFieldName("SEXO");
//        fields[5].setDataType(DBFField.FIELD_TYPE_C);
//        fields[5].setFieldLength(100);
//
//        fields[6] = new DBFField();
//        fields[6].setFieldName("DIRECCION");
//        fields[6].setDataType(DBFField.FIELD_TYPE_C);
//        fields[6].setFieldLength(100);
//
//        fields[7] = new DBFField();
//        fields[7].setFieldName("TELEFONO");
//        fields[7].setDataType(DBFField.FIELD_TYPE_C);
//        fields[7].setFieldLength(100);
//
//        fields[8] = new DBFField();
//        fields[8].setFieldName("CELULAR");
//        fields[8].setDataType(DBFField.FIELD_TYPE_C);
//        fields[8].setFieldLength(100);
//
//        fields[9] = new DBFField();
//        fields[9].setFieldName("FECHANAC");
//        fields[9].setDataType(DBFField.FIELD_TYPE_D);
//
//
//        fields[10] = new DBFField();
//        fields[10].setFieldName("INSANT");
//        fields[10].setDataType(DBFField.FIELD_TYPE_C);
//        fields[10].setFieldLength(100);
//
//        fields[11] = new DBFField();
//        fields[11].setFieldName("NIVEL");
//        fields[11].setDataType(DBFField.FIELD_TYPE_C);
//        fields[11].setFieldLength(100);
//
//        fields[12] = new DBFField();
//        fields[12].setFieldName("FECHAING");
//        fields[12].setDataType(DBFField.FIELD_TYPE_D);
////        fields[12].setFieldLength(100);
//
//        fields[13] = new DBFField();
//        fields[13].setFieldName("NOMFAC");
//        fields[13].setDataType(DBFField.FIELD_TYPE_C);
//        fields[13].setFieldLength(100);
//
//        fields[14] = new DBFField();
//        fields[14].setFieldName("DIRECFAC");
//        fields[14].setDataType(DBFField.FIELD_TYPE_C);
//        fields[14].setFieldLength(100);
//
//        fields[15] = new DBFField();
//        fields[15].setFieldName("RUCFAC");
//        fields[15].setDataType(DBFField.FIELD_TYPE_C);
//        fields[15].setFieldLength(100);
//
//        fields[16] = new DBFField();
//        fields[16].setFieldName("TELFAC");
//        fields[16].setDataType(DBFField.FIELD_TYPE_C);
//        fields[16].setFieldLength(100);
//
//        return fields;
//    }

//    public static void crearDBF(List<Matriculas> matriculas) throws DBFException, IOException {
//        DBFField fields[] = estructura();
//        DBFWriter writer = new DBFWriter();
//        writer.setFields(fields);
//
//// now populate DBFWriter
////for
//        for (Iterator<Matriculas> it = matriculas.iterator(); it.hasNext();) {
//            Matriculas matricula = it.next();
//            Object rowData[] = new Object[17];
//            rowData[0] = new Double(matricula.getEstudiante().getCodigoest());
//            rowData[1] = matricula.getCodigomat() + "";
//            rowData[2] = matricula.getEstudiante().getCedula();
//            rowData[3] = matricula.getEstudiante().getApellido();
//            rowData[4] = matricula.getEstudiante().getNombre();
//            rowData[5] = matricula.getEstudiante().getGenero();
//            rowData[6] = matricula.getEstudiante().getDireccion();
//            rowData[7] = matricula.getEstudiante().getTelefono();
//            rowData[8] = matricula.getEstudiante().getTelefono();
//            rowData[9] = matricula.getEstudiante().getFechanacimiento();
//            rowData[10] = matricula.getInstitucion();
//            rowData[11] = matricula.getCurso() + "";
//            rowData[12] = matricula.getFechains();
//            rowData[13] = matricula.getEstudiante().getRepresentante().getNombrefactura();
//            rowData[14] = matricula.getEstudiante().getRepresentante().getDirfactura();
//            rowData[15] = matricula.getEstudiante().getRepresentante().getIdentificacionfactura();
//            rowData[16] = matricula.getEstudiante().getRepresentante().getTelfactura();
//            writer.addRecord(rowData);
//        }
//        FileOutputStream fos = new FileOutputStream("/home/geovanny/Escritorio/base.dbf");
//        writer.write(fos);
//
//        fos.close();
//    }
}
