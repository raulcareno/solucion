package bean;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Estudiantes;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Periodo;
import jcinform.procesos.Administrador;

public class matriculasBean {

    Administrador adm = new Administrador();
    Periodo per = new Periodo();

    public matriculasBean() {
//        try {
//            DBF db2 = new DBF("/home/geovanny/Escritorio/base1.dbf");
////            db = new DBF("/home/rousseau/base/interface.dbf"); ESTO DEJAR

//        } catch (xBaseJException ex) {
//            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
//     public static void export_to_csv(Listbox listbox) {
        //exp.exportar(listbox);
        //return;
      
//        Filedownload.save(sb.toString().getBytes(), "application/vnd.ms-excel", "auditoria.csv");
//        }
    public void generar() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String nombre = "/home/usuario/base/datos.txt";
              nombre = "F:/datos.csv";
            fichero = new FileWriter(nombre);
            
            pw = new PrintWriter(fichero);
              List listado = adm.queryNativo("SELECT est.*,mat.codigomat,cur.descripcion "
                        + "FROM estudiantes est, matriculas mat, cursos cur "
                        + "WHERE mat.estudiante = est.codigoest  "
                        + "and mat.curso = cur.codigocur ");
            
            
              
                 for (Iterator it = listado.iterator(); it.hasNext();) {
                        Object object = it.next(); 
                  }
                
                  String s = ";\t";
        StringBuffer sb = new StringBuffer();
        //Field[] campos = Estudiantes.class.getDeclaredFields();
        List campos = adm.queryNativo("desc estudiantes");
        String h = "";
            for (Iterator it = campos.iterator(); it.hasNext();) {
                Field[] object = (Field[]) it.next();
                         Field field = object[0];
                  h+=field.getName()+s;
                  System.out.print(""+h);
              }  
            System.out.println(""+h);
                    sb.append(h+"\n");
//            for (int i = 0; i < campos.length; i++) {
//                String h = "";
//                Field field = campos[i];
//                    h+=field.getName()+s;
//                    System.out.println(""+h);
//                    sb.append(h+"\n");
//            }
        
//        for (Object head : listbox.getHeads()) {
//            String h = "";
//            for (Object header : ((Listhead) head).getChildren()) {
//                h += ((Listheader) header).getLabel() + s;
//            }
//            sb.append(h + "\n");
//        }
//            for (Object item : listbox.getItems()) {
//                String i = "";
//                for (Object cell : ((Listitem) item).getChildren()) {
//                    i += ((Listcell) cell).getLabel() + s;
//                }
//                sb.append(i + "\n");
//            }
            OutputStream out = new FileOutputStream(nombre);
            out.write(sb.toString().getBytes());
            out.close();
            

            //List<Matriculas> estu = adm.query("Select o from Matriculas as o where o.estado = 'Matriculado' ");
//            for (Iterator<Matriculas> it = estu.iterator(); it.hasNext();) {
//                Matriculas matricula = it.next();
//                    Integer numero  = matricula.getNumero();
//                    String cedula = matricula.getEstudiante().getCedula();
//                        + matricula.getEstudiante().getApellido() + ";"
//                        + matricula.getEstudiante().getNombre() + ";"
//                        + matricula.getEstudiante().getGenero() + ";"
//                        + matricula.getEstudiante().getDireccion().replace(";"," ") + ";"
//                        + matricula.getEstudiante().getTelefono().replace(";"," ") + ";"
//                        + matricula.getEstudiante().getTelefono().replace(";"," ") + ";"
//                        + matricula.getEstudiante().getFechanacimiento() + ";"
//                        + matricula.getInstitucion() + ";"
//                        + matricula.getFechamat() + ";"
//                        + matricula.getCurso() + ";"
//                        + matricula.getEstudiante().getRepresentante().getNombrefactura() + ";"
//                        + matricula.getEstudiante().getRepresentante().getDirfactura().replace(";"," ") + ";"
//                        + matricula.getEstudiante().getRepresentante().getIdentificacionfactura() + ";"
//                        + matricula.getEstudiante().getRepresentante().getTelfactura().replace(";"," ") + ";"
//                        + matricula.getCurso().getCodigocur() + ";");
//
//                pw.println("" + matricula.getEstudiante().getCodigoest() + ";"
//                        + matricula.getNumero() + ";"
//                        + matricula.getEstudiante().getCedula() + ";"
//                        + matricula.getEstudiante().getApellido() + ";"
//                        + matricula.getEstudiante().getNombre() + ";"
//                        + matricula.getEstudiante().getGenero() + ";"
//                        + matricula.getEstudiante().getDireccion().replace(";"," ") + ";"
//                        + matricula.getEstudiante().getTelefono().replace(";"," ") + ";"
//                        + matricula.getEstudiante().getTelefono().replace(";"," ") + ";"
//                        + matricula.getEstudiante().getFechanacimiento() + ";"
//                        + matricula.getInstitucion() + ";"
//                        + matricula.getFechamat() + ";"
//                        + matricula.getCurso() + ";"
//                        + matricula.getEstudiante().getRepresentante().getNombrefactura() + ";"
//                        + matricula.getEstudiante().getRepresentante().getDirfactura().replace(";"," ") + ";"
//                        + matricula.getEstudiante().getRepresentante().getIdentificacionfactura() + ";"
//                        + matricula.getEstudiante().getRepresentante().getTelfactura().replace(";"," ") + ";"
//                        + matricula.getCurso().getCodigocur() + ";");
//
//            }



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }


    }

    void todos() {

    }

    void actualizar(Matriculas matricula) {


    }

    void agregar(Matriculas matricula) {

    }

    public void concatenarPdf() {
         




    }
}
