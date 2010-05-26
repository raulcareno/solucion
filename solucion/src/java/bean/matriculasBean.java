 
package bean;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Periodo;
import jcinform.procesos.Administrador;
import org.xBaseJ.DBF;
import org.xBaseJ.fields.CharField;
import org.xBaseJ.fields.DateField;
import org.xBaseJ.fields.Field;
import org.xBaseJ.fields.NumField;
import org.xBaseJ.xBaseJException;

public class matriculasBean {
    Administrador adm = new Administrador();
    Periodo per = new Periodo();
    NumField CODIGO = null;
    CharField CODIGOM = null;
    CharField CEDULA = null;
    CharField APELLIDOS = null;
    CharField NOMBRES = null;
    CharField SEXO = null;
    CharField DIRECCION = null;
    CharField TELEFONO = null;
    CharField CELULAR = null;
    DateField FECHANAC = null;
    CharField INSANT = null;
    CharField NIVEL = null;
    DateField FECHAING = null;
    CharField NOMFAC = null;
    CharField DIRECFAC = null;
    CharField RUCFAC = null;
    CharField TELFAC = null;
    NumField CNIVEL = null;
    DBF db;

    public matriculasBean() {
        try {
//            DBF db2 = new DBF("/home/geovanny/Escritorio/base1.dbf");
            db = new DBF("/home/rousseau/base/interface.dbf");

        } catch (xBaseJException ex) {
            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generar(Boolean repreNuevo, Boolean estuNuevo, Matriculas matricula, Periodo periodo) {
        try {
            //concatenarPdf();
            per = periodo;
            concatenarPdf();
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
            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
        }

   }
public String rFecha (Date fecha){
    String dia = fecha.getDate()+"";
    String mes = ""+ (fecha.getMonth()+1);
    String anio = ""+(fecha.getYear()+1900);

    if(dia.length()<2){
        dia = "0"+dia;
    }
    if(mes.length()<2){
        mes = "0"+mes;
    }
    return anio+mes+dia;

    
}

    void llenar(Matriculas matricula) {
        try {

            try {CODIGO.put(matricula.getEstudiante().getCodigoest());} catch (Exception asdx) {}
            try {CODIGOM.put(matricula.getNumero() + "");} catch (Exception asdx) {}
            try {CEDULA.put(matricula.getEstudiante().getCedula());} catch (Exception asdx) {}
            try {APELLIDOS.put(matricula.getEstudiante().getApellido());} catch (Exception asdx) {}
            try {NOMBRES.put(matricula.getEstudiante().getNombre());} catch (Exception asdx) {}
            try {SEXO.put(matricula.getEstudiante().getGenero());} catch (Exception asdx) {}
            try {DIRECCION.put(matricula.getEstudiante().getDireccion());} catch (Exception asdx) {}
            try {TELEFONO.put(matricula.getEstudiante().getTelefono());} catch (Exception asdx) {}
            try {CELULAR.put(matricula.getEstudiante().getTelefono());} catch (Exception asdx) {}
            try {
                FECHANAC.put(rFecha(matricula.getEstudiante().getFechanacimiento()));
                System.out.println("___________________________---");
            } catch (Exception asdx) {Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, asdx);}
            try {INSANT.put(matricula.getInstitucion());} catch (Exception asdx) {}
            try {FECHAING.put(matricula.getFechamat());} catch (Exception asdx) {}
            try {NIVEL.put(matricula.getCurso() + "");} catch (Exception asdx) {}
            try {NOMFAC.put(matricula.getEstudiante().getRepresentante().getNombrefactura());} catch (Exception asdx) {}
            try {DIRECFAC.put(matricula.getEstudiante().getRepresentante().getDirfactura());} catch (Exception asdx) {}
            try {RUCFAC.put(matricula.getEstudiante().getRepresentante().getIdentificacionfactura());} catch (Exception asdx) {}
            try {TELFAC.put(matricula.getEstudiante().getRepresentante().getTelfactura());} catch (Exception asdx) {}
            try {CNIVEL.put(matricula.getCurso().getCodigocur());} catch (Exception asdx) {}
            

        }  catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
void todos(){
    Administrador adm = new Administrador();
          List estu = adm.query("Select o from Matriculas as o where o.estado = 'Matriculado' ");
          System.out.println(""+estu);
            for (Iterator<Matriculas> it = estu.iterator(); it.hasNext();) {
               Matriculas matriculas = it.next();
                agregar(matriculas);
           }
}
    void actualizar(Matriculas matricula) {
        try {

            Field f;
            String codigo = matricula.getEstudiante().getCodigoest() + "";
            System.out.println("NO DE REGISTROS"+db.getRecordCount());
              if(db.getRecordCount() ==0 ){
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
            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("" + ex);
            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void agregar(Matriculas matricula) {
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
            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void concatenarPdf() {
        try {
            System.out.println("Iniciamos asignacion ..");
            CODIGO = (NumField) db.getField("CODIGO");
            CODIGOM = (CharField) db.getField("CODIGOM");
            CEDULA = (CharField) db.getField("CEDULA");
            APELLIDOS = (CharField) db.getField("APELLIDOS");
            NOMBRES = (CharField) db.getField("NOMBRES");
            SEXO = (CharField) db.getField("SEXO");
            DIRECCION = (CharField) db.getField("DIRECCION");
            TELEFONO = (CharField) db.getField("TELEFONO");
            CELULAR = (CharField) db.getField("CELULAR");
            FECHANAC = (DateField) db.getField("FECHANAC");
            INSANT = (CharField) db.getField("INSANT");
            NIVEL = (CharField) db.getField("NIVEL");
            FECHAING = (DateField) db.getField("FECHAING");
            NOMFAC = (CharField) db.getField("NOMFAC");
            DIRECFAC = (CharField) db.getField("DIRECFAC");
            RUCFAC = (CharField) db.getField("RUCFAC");
            TELFAC = (CharField) db.getField("TELFAC");
            CNIVEL = (NumField) db.getField("CNIVEL");
            
        } catch (xBaseJException ex) {
            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
        }





    }
}
