package bean;

import java.io.FileWriter;
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

    public void generar() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("/home/usuario/base/datos.txt");
            pw = new PrintWriter(fichero);
                adm.queryNativo("SELECT est.*,mat.codigomat,cur.descripcion "
                        + "FROM estudiantes est, matriculas mat, cursos cur "
                        + "WHERE mat.estudiante = est.codigoest  "
                        + "and mat.curso = cur.codigocur "
                    + "    INTO OUTFILE 'f:/gente.txt' "
                    + "    FIELDS TERMINATED BY ';' "
                    + "    OPTIONALLY ENCLOSED BY '\"' "
                    + "    LINES TERMINATED BY '\\n\\r'; ");
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
