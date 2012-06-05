package bean;

import bsh.EvalError;
import bsh.Interpreter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.*;

public class recomendaciones extends Rows {

    public recomendaciones() {
    }

    public void addPerfil(Cursos curso, Sistemacalificacion sistema) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();

        getChildren().clear();
        Label label3 = null;

        Textbox combo = new Textbox();
        Listitem item = new Listitem("USUALMENTE");


        List<Matriculas> matriculas = adm.query("Select o from Matriculas as o "
                + "where o.curso.codigocur = '" + curso.getCodigocur() + "' order by o.estudiante.apellido, o.estudiante.nombre ");
        Row row = new Row();
        try {
            for (Iterator<Matriculas> it = matriculas.iterator(); it.hasNext();) {
                Matriculas matriculas1 = it.next();
                row = new Row();
                String q = "SELECT  o from Recomendaciones as o where o.matricula.codigomat = '" + matriculas1.getCodigomat() + "' "
                        + " AND o.sistema.codigosis = '" + sistema.getCodigosis() + "' ";
                List<Recomendaciones> recom = adm.query(q);
                 combo = new Textbox();
                 combo.setCols(70);
                 combo.setRows(2);
                 //combo.setWidth("300px");
                if (recom.size() <= 0) {
                    label3 = new Label("" + matriculas1.getCodigomat());
                    row.appendChild(label3);
                    label3 = new Label("" + matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                    row.appendChild(label3);
                    row.appendChild(combo);
                } else {
                    label3 = new Label("" + matriculas1.getCodigomat());
                    row.appendChild(label3);
                    label3 = new Label("" + matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                    row.appendChild(label3);
                    combo.setValue(recom.get(0).getRecomendacion() + "");
                    row.appendChild(combo);
                }
                row.setParent(this);

            }


        } catch (Exception ex) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void guardarPerfil(List col, Cursos curso, Sistemacalificacion sistema) {
        System.out.println("INICIO EN: " + new Date());
        Interpreter inter = new Interpreter();
        Administrador adm = new Administrador();
        String del = "Delete from Recomendaciones "
                + "where matricula.curso.codigocur = '" + curso.getCodigocur() + "' "
                + "and sistema.codigosis = '" + sistema.getCodigosis() + "' ";
        adm.ejecutaSql(del);
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                Recomendaciones nota = new Recomendaciones();

                List labels = object.getChildren();
                nota.setMatricula(new Matriculas(new Integer(((Label) labels.get(0)).getValue())));
                nota.setSistema(sistema);

                inter.set("nota", nota);

                for (int j = 2; j < labels.size(); j++) {
                    Textbox object1 = (Textbox) labels.get(j);
                    String vaNota = object1.getValue();
                    nota.setRecomendacion(vaNota);
                    adm.guardar(nota);
                }

            } catch (EvalError ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("FINALIZO EN: " + new Date());


    }
}
