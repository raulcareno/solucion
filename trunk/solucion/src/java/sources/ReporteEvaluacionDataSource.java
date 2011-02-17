/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import bean.Permisos;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Respuestasencuesta;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author GEOVANNY
 */
public class ReporteEvaluacionDataSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ReporteEvaluacionDataSource(List lista) {
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
        Respuestasencuesta nodo = (Respuestasencuesta) valorAtual;
        String fieldName = campo.getName();
        if ("curso".equals(fieldName)) {
            valor = nodo.getMatricula().getCurso().toString();
        } else if ("estudiante".equals(fieldName)) {
            valor = nodo.getMatricula().getEstudiante().getApellido() + " " + nodo.getMatricula().getEstudiante().getNombre() ;
        } else if ("pregunta".equals(fieldName)) {
            valor = nodo.getDetallepregunta().getPregunta().getOrden()+".-"+nodo.getDetallepregunta().getPregunta().getPregunta();
        } else if ("respuesta".equals(fieldName)) {
            valor = nodo.getDetallepregunta().getOpcion();
        }
        return valor;
    }
   
}
