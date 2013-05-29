/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Respuestasencuestar;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author GEOVANNY
 */
public class ReporteEvaluacionDataSourcer implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ReporteEvaluacionDataSourcer(List lista) {
        super();
        this.itrNodos = lista.iterator();
    }

    public boolean next() throws JRException {
        itrAlumnos = itrNodos;
        valorAtual = itrAlumnos.hasNext() ? itrAlumnos.next() : null;
        irParaProximoAlumno = (valorAtual != null);
        return irParaProximoAlumno;
    }

    public Object getFieldValue(JRField campo) throws JRException {
        Object valor = null;
        Respuestasencuestar nodo = (Respuestasencuestar) valorAtual;
        String fieldName = campo.getName();
        if ("curso".equals(fieldName)) {
            valor = nodo.getMatricula().getCurso().toString();
        } else if ("estudiante".equals(fieldName)) {
            valor = nodo.getEmpleado().getApellidos() + " " + nodo.getEmpleado().getNombres() ;
        } else if ("pregunta".equals(fieldName)) {
            valor = nodo.getDetallepreguntar().getPreguntar().getOrden()+".-"+nodo.getDetallepreguntar().getPreguntar().getPregunta();
        } else if ("respuesta".equals(fieldName)) {
            valor = nodo.getDetallepreguntar().getOpcion();
        }
        return valor;
    }
   
}
