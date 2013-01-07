package bean;

import java.util.Date;

import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

public class Planificacion extends Rows {
    public Planificacion() {
        //         Grid g;
//         Label l;
//         Row row;
//         row.getZIndex()
    }

   
  public void addFila() {
        System.out.println("TOP INI; " + new Date());
//        int tamanio = 0;
        System.setProperty("java.awt.headless", "true");
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();
        Row row = new Row();
        getChildren().clear();
        Textbox notaTexto = null;
        List<PlanificacionDetalle> detalle = adm.query("Select o from PlanificacionEvaluadores as o  "
                + " where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + " order by o.orden  ");
            row = new Row();
            for (int i = 0; i < detalle.size(); i++) {
                notaTexto = new Textbox();
                notaTexto.setCols(30);
                notaTexto.setRows(30);
                row.appendChild(notaTexto);
            }
            row.setParent(this);
         
    }

  
    public void addRow(jcinform.persistencia.Planificacion plani) {
 
        Administrador adm = new Administrador();
        Row row = new Row();
        getChildren().clear();
        Textbox notaTexto = null;
        List<PlanificacionDetalle> detalle = adm.query("Select o from PlanificacionDetalle as o  "
                + " where o.planificacion.codigo = '" + plani.getCodigo()  + "' "
                + " order by o.evaluador.orden  ");
            row = new Row();
            for (Iterator<PlanificacionDetalle> it = detalle.iterator(); it.hasNext();) {
                PlanificacionDetalle planificacionDetalle = it.next();
                notaTexto = new Textbox();
                notaTexto.setId("detalle"+planificacionDetalle.getCodigo());
                notaTexto.setCols(30);
                notaTexto.setRows(30);
                notaTexto.setValue(planificacionDetalle.getDescripcion()); 
                row.appendChild(notaTexto);
            }
            row.setParent(this);
         
 }

 
     
}
