package bean;


import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

public class pendientes extends Rows {
//ArrayList listad = new ArrayList();

    public pendientes() {
       
    }

    public void addRow(Cursos p) {

        Administrador adm = new Administrador();
         List accesosList = adm.query("Select o.estudiante from Matriculas as o where o.curso.codigocur = '"+p.getCodigocur()+"' order by o.estudiante.apellido ");
  

        Checkbox label = null;
        Label label3 = null;

        getChildren().clear();
        Row row = new Row();

        for (Iterator itna = accesosList.iterator(); itna.hasNext();) {
            Estudiantes vec = (Estudiantes) itna.next();
            row = new Row();

            label3 = new Label();
            label3.setValue("" + vec.getCodigoest());
            label3.setParent(row);

            label3 = new Label();
            label3.setValue("" + vec.getApellido()+" "+vec.getNombre());
            label3.setParent(row);

            label = new Checkbox();
            label.setChecked(vec.getPendientes());
            label.setParent(row);

            row.setParent(this);

        }




    }

    @SuppressWarnings("static-access")
    public String guardar(List col) {

        Administrador adm = new Administrador();
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                Estudiantes nota = new Estudiantes();
                List labels = object.getChildren();
                String valorCodigo = ((Label) labels.get(0)).getValue();
                
                nota = (Estudiantes) adm.buscarClave(new Integer(valorCodigo), Estudiantes.class);
                nota.setPendientes(((Checkbox) labels.get(2)).isChecked());
                adm.actualizar(nota);
                nota = null;

            } catch (Exception ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
                return "false";
            }
        }
        return "ok";

    }
}
