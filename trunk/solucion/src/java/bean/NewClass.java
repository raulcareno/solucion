/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jcinform.persistencia.Institucion;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Periodo;
import jcinform.procesos.Administrador;
import net.sf.jasperreports.engine.JRDataSource;
import org.zkoss.zk.ui.Executions;
import sources.ReporteActaDataSource;

/**
 *
 * @author Geovanny Jadan
 */
public class NewClass {


    String codigo = "";
public void pdf(){
        Executions.sendRedirect("/592981728937491235M.zul?AASL2KSO348934934="+ codigo +"&TP=html");

}

public ArrayList certificados(Matriculas matricula,String tipo){
      Administrador adm = new Administrador();
       Periodo per  = new Periodo();
        ArrayList detalle = new ArrayList();
        String query = "SELECT mat FROM Matriculas AS mat " +
                "WHERE  mat.estado like '%Matriculado%' " +
                "and mat.codigomat = '" + matricula.getCodigomat() + "' ";
        List hoy = adm.query(query);

        for (Iterator it = hoy.iterator(); it.hasNext();) {
            Matriculas elem = (Matriculas) it.next();
            detalle.add(elem);
            per = elem.getCurso().getPeriodo();
        }
            Institucion insts = per.getInstitucion();
            Map parametros = new HashMap();
            parametros.put("denominacion", insts.getDenominacion());
            parametros.put("nombre", insts.getNombre());
            parametros.put("periodo", per.getDescripcion());
            parametros.put("slogan", insts.getSlogan());
            parametros.put("secretaria", insts.getSecretaria());
            parametros.put("rectora", insts.getRector());
            ArrayList arr = new ArrayList();
            arr.add(new ReporteActaDataSource(detalle));
            arr.add(parametros);
         return  arr;
}
void notasd(){
             Administrador adm = new Administrador();
            Integer valor =  new Integer(Executions.getCurrent().getParameter("AASL2KSO348934934"));
            codigo = valor+"";
            String tipo =  Executions.getCurrent().getParameter("TP");

            JRDataSource datasource = null;
            ArrayList arreglo = certificados(new Matriculas(valor),"AM");
                    datasource = (ReporteActaDataSource) arreglo.get(0);
                    Map parametros = (HashMap) arreglo.get(1);

                    parametros.put("titulo", "Comprobante de Matricula");
//                    report.setSrc("WEB-INF/reportes/actaMatricula2.jasper");
//                    report.setParameters(parametros);
//                    report.setDatasource(datasource);
//                    report.setType(tipo);
//              bimprimir.visible=true;
             if(tipo.contains("html")){
//                    bimprimir.visible = false;
//                    report.setHeight("100%");
             }


            }


}
