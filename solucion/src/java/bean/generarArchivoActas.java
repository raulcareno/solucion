package bean;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Notasacta;
import jcinform.persistencia.Periodo;
import jcinform.procesos.Administrador;
import org.xBaseJ.DBF;
import org.xBaseJ.fields.CharField;
import org.xBaseJ.fields.DateField;
import org.xBaseJ.fields.Field;
import org.xBaseJ.fields.NumField;
import org.xBaseJ.xBaseJException;

public class generarArchivoActas {

    Administrador adm = new Administrador();
    Periodo per = new Periodo();
    CharField CODCOL = null;
    CharField CODTIT = null;
    CharField SECCION = null;
    CharField LECTIVO = null;
    NumField ACTA = null;
    CharField CEDULA = null;
    CharField APELLIDOS = null;
    CharField NOMBRES = null;
    CharField SEXO = null;
    NumField CALIFICA = null;
    DateField FECHAG = null;
    CharField ACUERDO = null;
    DateField FECHA = null;
    DBF db;

    public generarArchivoActas() {
        try {
//            DBF db2 = new DBF("/home/geovanny/Escritorio/base1.dbf");
            db = new DBF("F:\\alumnos.DBF");

        } catch (xBaseJException ex) {
            Logger.getLogger(generarArchivoActas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(generarArchivoActas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generar(Boolean repreNuevo, Boolean estuNuevo, Notasacta matricula, Periodo periodo) {
        try {
            //concatenarPdf();
            per = periodo;
            concatenarPdf();
              todos();
            if (estuNuevo) {
                if (db.getRecordCount() == 0) {
                    todos();
                    return;
                }
                agregar(matricula);
            } else {
                actualizar(matricula);
            }
            db.close();
        } catch (IOException ex) {
            Logger.getLogger(generarArchivoActas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String rFecha(Date fecha) {
        String dia = fecha.getDate() + "";
        String mes = "" + (fecha.getMonth() + 1);
        String anio = "" + (fecha.getYear() + 1900);

        if (dia.length() < 2) {
            dia = "0" + dia;
        }
        if (mes.length() < 2) {
            mes = "0" + mes;
        }
        return anio + mes + dia;


    }

    void llenar(Notasacta nota) {
        try {

            //            CharField CODCOL = null;
//            CharField CODTIT = null;
//            CharField SECCION = null;
//            CharField LECTIVO = null;
//            NumField ACTA = null;
//            CharField CEDULA = null;
//            CharField APELLIDOS = null;
//            CharField NOMBRES = null;
//            CharField SEXO = null;
//            NumField CALIFICA = null;
//            DateField FECHAG = null;
//            CharField ACUERDO = null;
//            DateField FECHA = null;
            
            try {
                CODCOL.put(nota.getMatricula().getCurso().getPeriodo().getInstitucion().getCodigo());
            } catch (Exception asdx) {
            }
            try {
                CODTIT.put("1.000.");
            } catch (Exception asdx) {
            }
            try {
                if(nota.getMatricula().getCurso().getPeriodo().getSeccion().getDescripcion().toUpperCase().contains("MATU"))
                    SECCION.put("1");
                else if(nota.getMatricula().getCurso().getPeriodo().getSeccion().getDescripcion().toUpperCase().contains("VESP"))
                    SECCION.put("2");
                else if(nota.getMatricula().getCurso().getPeriodo().getSeccion().getDescripcion().toUpperCase().contains("NOC"))
                    SECCION.put("3");
            } catch (Exception asdx) {
            }
            try {
                LECTIVO.put(nota.getMatricula().getCurso().getPeriodo().getCodigomin());
            } catch (Exception asdx) {
            }
            try {
                ACTA.put(nota.getNumeroacta());
            } catch (Exception asdx) {
            }
            try {
                CEDULA.put(nota.getMatricula().getEstudiante().getCedula());
            } catch (Exception asdx) {
            }
            try {
                APELLIDOS.put(nota.getMatricula().getEstudiante().getApellido());
            } catch (Exception asdx) {
            }
            try {
                NOMBRES.put(nota.getMatricula().getEstudiante().getNombre());
            } catch (Exception asdx) {
            }
            try {
                if(nota.getMatricula().getEstudiante().getGenero().toUpperCase().contains("MASC")){
                    SEXO.put("M");
                }else{
                    SEXO.put("F");
                }
            } catch (Exception asdx) {
            }
            try {
                CALIFICA.put(nota.getNota1());
            } catch (Exception asdx) {
                
            }
            try {
                FECHAG.put(rFecha(nota.getFecha()));
            } catch (Exception asdx) {
            }
             try {
                ACUERDO.put("NOP SE");
            } catch (Exception asdx) {
            }
             try {
                FECHA.put(rFecha(nota.getFecha()));
            } catch (Exception asdx) {
            }
           
            


        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(generarArchivoActas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

      void todos() {
        Administrador adm = new Administrador();
        Notasacta reg = new Notasacta();
        Matriculas mat = (Matriculas) adm.buscarClave(new Integer(1),Matriculas.class);
        reg.setMatricula(mat);
        reg.setFecha(new Date());
        reg.setNota1(19.0);
        reg.setNumeroacta(10);
        agregar(reg);
//        List estu = adm.query("Select o from Matriculas as o where o.estado = 'Matriculado' ");
//        System.out.println("" + estu);
//        for (Iterator<Matriculas> it = estu.iterator(); it.hasNext();) {
//            Matriculas matriculas = it.next();
//            agregar(matriculas);
//        }
    }

    void actualizar(Notasacta matricula) {
        try {

            Field f;
            String codigo = matricula.getMatricula().getEstudiante().getCodigoest() + "";
            System.out.println("NO DE REGISTROS" + db.getRecordCount());
            if (db.getRecordCount() == 0) {
                todos();
                return;
            }
            db.read();
//            if(matricula.getEstudiante().getCodigoest().equals(new Integer(585))){
//                System.out.println("AGUANTATE UN CHANCE.....................");
//            }
            for (int i = 1; i <= db.getRecordCount(); i++) {
                db.gotoRecord(i);
//                for (int j = 1; j <= db.getFieldCount(); j++) {
                f = db.getField(1);

                if (codigo.equals("" + f.get().trim())) {
                    db.getCurrentRecordNumber();
//                    concatenarPdf();
                    llenar(matricula);
                    db.undelete();
                    db.update();
                    return;
//                    }
//                    System.out.print(f.getName() + "(" + f.get().trim() + ")");
                }

            }
            agregar(matricula);

        } catch (xBaseJException ex) {
            System.out.println("" + ex);
            Logger.getLogger(generarArchivoActas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("" + ex);
            Logger.getLogger(generarArchivoActas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void agregar(Notasacta matricula) {
        try {
//            concatenarPdf();
//             if(matricula.getEstudiante().getCodigoest().equals(new Integer(585))){
//                System.out.println("AGUANTATE UN CHANCE.....................");
//            }
            concatenarPdf();
            llenar(matricula);
            db.write();
//            db.close();
        } catch (xBaseJException ex) {
            Logger.getLogger(generarArchivoActas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(generarArchivoActas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void concatenarPdf() {
        try {

//            System.out.println("Iniciamos asignacion ..");
            CODCOL = (CharField) db.getField("CODCOL");
            CODTIT = (CharField) db.getField("CODTIT");
            SECCION = (CharField) db.getField("SECCION");
            LECTIVO = (CharField) db.getField("LECTIVO");
            ACTA = (NumField) db.getField("ACTA");
            CEDULA = (CharField) db.getField("CEDULA");
            APELLIDOS = (CharField) db.getField("APELLIDOS");
            NOMBRES = (CharField) db.getField("NOMBRES");
            SEXO = (CharField) db.getField("SEXO");
            CALIFICA = (NumField) db.getField("CALIFICA");
            FECHAG = (DateField) db.getField("FECHAG");
            ACUERDO = (CharField) db.getField("ACUERDO");
            FECHA = (DateField) db.getField("FECHA");
        } catch (xBaseJException ex) {
            Logger.getLogger(generarArchivoActas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(generarArchivoActas.class.getName()).log(Level.SEVERE, null, ex);
        }





    }
   
}
