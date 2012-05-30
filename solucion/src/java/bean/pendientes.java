package bean;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

public class pendientes extends Rows {
//ArrayList listad = new ArrayList();

    public pendientes() {
    }

    public void addRow(Cursos p) {

        Administrador adm = new Administrador();
        List accesosList = adm.query("Select o from Matriculas as o where o.curso.codigocur = '" + p.getCodigocur() + "' order by o.estudiante.apellido ");


        Checkbox label = null;
        Label label3 = null;

        getChildren().clear();
        Row row = new Row();

        for (Iterator itna = accesosList.iterator(); itna.hasNext();) {
            Matriculas vec = (Matriculas) itna.next();
            row = new Row();

            label3 = new Label();
            label3.setValue("" + vec.getCodigomat());
            label3.setParent(row);

            label3 = new Label();
            label3.setValue("" + vec.getEstudiante().getApellido() + " " + vec.getEstudiante().getNombre());
            label3.setParent(row);

            label = new Checkbox();
            try {
                label.setChecked(vec.getEstudiante().getBloquear());
            } catch (Exception e) {
                label.setChecked(false);
            }
            label.setParent(row);
            row.setParent(this);
        }
    }

    public void addRow2(Cursos p) {

        Administrador adm = new Administrador();
        List accesosList = adm.query("Select o from Matriculas as o "
                + "where o.curso.codigocur = '" + p.getCodigocur() + "' "
                + "order by o.estudiante.apellido ");
        Textbox cedulaText = null;

        Checkbox radioPerdio = null;
        Checkbox radioSuspenso = null;
//        radio.setParent(grupo);
        Label label3 = null;
        getChildren().clear();
        Row row = new Row();
        try {


        for (Iterator itna = accesosList.iterator(); itna.hasNext();) {
            Matriculas vec = (Matriculas) itna.next();
            row = new Row();

            label3 = new Label();
            label3.setValue("" + vec.getCodigomat());
            label3.setParent(row);

            label3 = new Label();
            label3.setValue("" + vec.getEstudiante().getApellido() + " " + vec.getEstudiante().getNombre());
            label3.setParent(row);

            cedulaText = new Textbox();
            cedulaText.setMaxlength(10);
            cedulaText.setCols(15);
            try {

                cedulaText.setValue(vec.getEstudiante().getCedula());
            } catch (Exception e) {
                cedulaText.setValue("");
            }
            cedulaText.setParent(row);

            radioPerdio = new Checkbox("Perdio");
            try {
                
            
            radioPerdio.setChecked(vec.getPerdio());
            } catch (Exception e) {
                radioPerdio.setChecked(false);
            }
            radioPerdio.setParent(row);

            radioSuspenso = new Checkbox("Suspenso");
            try {
            radioSuspenso.setChecked(vec.getSuspenso());    
            } catch (Exception e) {
                radioSuspenso.setChecked(false);
            }
            
            radioSuspenso.setParent(row);

            row.setParent(this);

        }
            } catch (Exception e) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, e);
        }



    }

    @SuppressWarnings("static-access")
    public String guardar(List col) {

        Administrador adm = new Administrador();
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                Matriculas nota = new Matriculas();
                List labels = object.getChildren();
                String valorCodigo = ((Label) labels.get(0)).getValue();

                nota = (Matriculas) adm.buscarClave(new Integer(valorCodigo), Matriculas.class);
                Estudiantes est = nota.getEstudiante();
                est.setBloquear(((Checkbox) labels.get(2)).isChecked());
                Representante rep = est.getRepresentante();
                rep.setEstado(((Checkbox) labels.get(2)).isChecked());
                adm.actualizar(rep);
                adm.actualizar(est);
                nota = null;

            } catch (Exception ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
                return "false";
            }
        }
        return "ok";

    }

    @SuppressWarnings("static-access")
    public String guardar2(List col) {
        Administrador adm = new Administrador();
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                Matriculas nota = new Matriculas();
                List labels = object.getChildren();
                String valorCodigo = ((Label) labels.get(0)).getValue();
                nota = (Matriculas) adm.buscarClave(new Integer(valorCodigo), Matriculas.class);
                Estudiantes est = nota.getEstudiante();
                est.setCedula(((Textbox) labels.get(2)).getValue());
                adm.actualizar(est);

                nota.setPerdio(((Checkbox) labels.get(3)).isChecked());
                nota.setSuspenso(((Checkbox) labels.get(4)).isChecked());
                adm.actualizar(nota);

                nota = null;


            } catch (Exception ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
                return "false";
            }
        }
        return "ok";

    }

    public void buscarPerdidos(Cursos curso) {
        Administrador adm = new Administrador();
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + curso.getPeriodo().getCodigoper() + "'  "
                + "and o.sistema.promediofinal = 'PF' ");
        try {
            if (notas.size() <= 0) {
                Messagebox.show("No se ha parametrizado el PROMEDIO FINAL en los APORTES \n Puede obtener resultados no esperados", "Administrador Educativo", Messagebox.OK, Messagebox.ERROR);
                return;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Matriculas> matriculas = adm.query("Select o from Matriculas as o where o.curso.codigocur = '" + curso.getCodigocur() + "' ");
        for (Iterator<Matriculas> it = matriculas.iterator(); it.hasNext();) {
            Matriculas matriculas1 = it.next();
            String q = "Select  CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,4) ) from notas "
                    + " where matricula = '" + matriculas1.getCodigomat() + "' and seimprime = true and promedia = true and disciplina = false "
                    + " and " + notas.get(0).getNota() + " < " + matriculas1.getCurso().getAprobacion() + ""
                    + "     ";
            System.out.println("" + q);
            List nativo = adm.queryNativo(q);
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector dos = (Vector) itna.next();
                if (((BigDecimal) dos.get(0)).doubleValue() >= matriculas1.getCurso().getAprobacion()) {
                    //not.setEstadoMateria("APROBADO");
                    matriculas1.setPerdio(false);
                    adm.actualizar(matriculas1);
                } else {//REPROBO UNA MATERIA
                    matriculas1.setPerdio(true);
                    adm.actualizar(matriculas1);
                    //not.setEstadoMateria("REPROBADO");
                    //estadoEstudiante = false;
                }
            }



        }



    }
}
