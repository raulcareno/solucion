/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author GEOVANNY
 */
public class ReportePlanificacionDataSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ReportePlanificacionDataSource(List lista) {
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
     * @see
     * net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
     */

    public Object getFieldValue(JRField campo) throws JRException {
        Object valor = null;
        PlanificacionSource nodo = (PlanificacionSource) valorAtual;
        try {
  
            String fieldName = campo.getName();
            if ("materia".equals(fieldName)) {
                valor = nodo.getMateria();
            } else if ("curso".equals(fieldName)) {
                valor = nodo.getCurso();
            }else if ("duracion".equals(fieldName)) {
                valor = nodo.getDuracion();
            }else if ("eintegrador".equals(fieldName)) {
                valor = nodo.getEintegrador();
            }else if ("eaprendizaje".equals(fieldName)) {
                valor = nodo.getEaprendizaje();
            }else if ("etransversal".equals(fieldName)) {
                valor = nodo.getEtransversal();
            }else if ("objetivos".equals(fieldName)) {
                valor = nodo.getObjetivos();
            }else if ("bloques".equals(fieldName)) {
                valor = nodo.getBloques();
            }else if ("finicio".equals(fieldName)) {
                valor = nodo.getFinicio();
            }else if ("ffin".equals(fieldName)) {
                valor = nodo.getFfin();
            }else if ("planificacion".equals(fieldName)) {
                valor = nodo.getPlanificacion();
            } else if ("detalle1".equals(fieldName)) {
                valor = nodo.getDetalle1();
            } else if ("detalle2".equals(fieldName)) {
                valor = nodo.getDetalle2();
            } else if ("detalle3".equals(fieldName)) {
                valor = nodo.getDetalle3();
            } else if ("detalle4".equals(fieldName)) {
                valor = nodo.getDetalle4();
            } else if ("detalle5".equals(fieldName)) {
                valor = nodo.getDetalle5();
            } else if ("tipo1".equals(fieldName)) {
                valor = nodo.getTipo1();
            } else if ("tipo2".equals(fieldName)) {
                valor = nodo.getTipo2();
            } else if ("tipo3".equals(fieldName)) {
                valor = nodo.getTipo3();
            } else if ("tipo4".equals(fieldName)) {
                valor = nodo.getTipo4();
            } else if ("tipo5".equals(fieldName)) {
                valor = nodo.getTipo5();
            }
        } catch (Exception e) {
            Logger.getLogger(ReportePlanificacionDataSource.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("EN DATASOURCE" + e);
        }
        return valor;
    }
}
