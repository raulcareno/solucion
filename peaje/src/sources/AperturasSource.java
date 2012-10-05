/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import hibernate.Control;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author GEOVANNY
 */
public class AperturasSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public AperturasSource(List lista) {
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
        Control nodo = (Control) valorAtual;
        String fieldName = campo.getName();
        try {
            if ("nombres".equals(fieldName)) {
                valor = nodo.getUsuario().getNombres();
            }else if("fecha".equals(fieldName)) {
                valor = nodo.getFecha();
            }else if("puerta".equals(fieldName)) {
                valor = nodo.getPuerta();
            }    
        } catch (Exception e) {
            Logger.getLogger(AperturasSource.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
     
}
