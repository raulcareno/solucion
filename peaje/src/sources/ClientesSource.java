/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sources;
 
import hibernate.Clientes;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;



/**
 *
 * @author GEOVANNY
 */
public class ClientesSource implements JRDataSource{
    private Iterator itrAlumnos;
     private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;

    public ClientesSource(List lista) {
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
        Clientes    nodo = (Clientes) valorAtual;
        String fieldName = campo.getName();
        if ("cliente".equals(fieldName)) {
            valor = nodo.getNombres();
        } else if ("ruc".equals(fieldName)) {
            valor = nodo.getIdentificacion();
        }  else if ("direccion".equals(fieldName)) {
            valor = nodo.getDireccion();
        }  else if ("telefono".equals(fieldName)) {
            valor = nodo.getTelefono();
        }

        return valor;
    }
}
