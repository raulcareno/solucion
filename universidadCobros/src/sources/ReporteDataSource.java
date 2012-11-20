package sources;

/*
 * ReporteDataSource.java
 *
 * Created on 11 de noviembre de 2007, 10:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

 
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Detalles;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
//import persistencia.Detallefactura;

/**
 *
 * @author Francisco
 */
public class ReporteDataSource implements JRDataSource{

   private Iterator itrAlumnos;
   private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;

    public ReporteDataSource(List lista) {
        super();
        this.itrNodos = lista.iterator();
    }

    public boolean next() throws JRException {
        itrAlumnos = itrNodos;
        valorAtual = itrAlumnos.hasNext() ? itrAlumnos.next() : null;
        irParaProximoAlumno = (valorAtual != null);
        return irParaProximoAlumno;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
     */
    public Object getFieldValue(JRField campo) throws JRException {
        Object valor = null;
        Detalles nodo = (Detalles) valorAtual;

        if ("item".equals(campo.getName())) {
            valor = nodo.getCantidad();
        }else if ("estudiante".equals(campo.getName())) {
            valor = nodo.getIdFacturas().getIdMatriculas().getIdEstudiantes().getApellidoPaterno()+" "+nodo.getIdFacturas().getIdMatriculas().getIdEstudiantes().getNombre();
        }else if ("rubro".equals(campo.getName())) {
            valor = nodo.getIdRubros().getNombre();
        }else if ("valor".equals(campo.getName())) {
            valor = nodo.getValorTotal();
        }else if ("numero".equals(campo.getName())) {
            valor = nodo.getIdFacturas().getIdFacturas().substring(3);
        }
        else if ("mes".equals(campo.getName())) {
             valor = "";    

        }
 
        return valor;
    }
}

