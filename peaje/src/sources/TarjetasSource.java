/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sources;
 
import hibernate.Clientes;
import hibernate.Tarjetas;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;



/**
 *
 * @author GEOVANNY
 */
public class TarjetasSource implements JRDataSource{
    private Iterator itrAlumnos;
     private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;

    public TarjetasSource(List lista) {
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
        Tarjetas    nodo = (Tarjetas) valorAtual;
        String fieldName = campo.getName();
        if ("cliente".equals(fieldName)) {
            valor = nodo.getClientes().getNombres();
        } else if ("ruc".equals(fieldName)) {
            valor = nodo.getClientes().getIdentificacion();
        }  else if ("direccion".equals(fieldName)) {
            valor = nodo.getClientes().getDireccion();
        }  else if ("telefono".equals(fieldName)) {
            valor = nodo.getClientes().getTelefono();//NO DE INGRESOS
        } else if ("ultimoacceso".equals(fieldName)) {
            valor = nodo.getClientes().getUltimoacceso();
        } else if ("tarjeta".equals(fieldName)) {
            valor = nodo.getTarjeta();
        } else if ("desde".equals(fieldName)) {
            valor = nodo.getDesde();
        } else if ("hasta".equals(fieldName)) {
            valor = nodo.getHasta();
        } else if ("desdeh".equals(fieldName)) {
            valor = nodo.getHorainicio();
        } else if ("hastah".equals(fieldName)) {
            valor = nodo.getHorafin();
        } else if ("descripcion".equals(fieldName)) {
            valor = nodo.getDescripcion();
        } else if ("lunes".equals(fieldName)) {
            valor = nodo.getLunes()?"SI":"NO";
        } else if ("martes".equals(fieldName)) {
            valor = nodo.getMartes()?"SI":"NO";
        } else if ("miercoles".equals(fieldName)) {
            valor = nodo.getMiercoles()?"SI":"NO";
        } else if ("jueves".equals(fieldName)) {
            valor = nodo.getJueves()?"SI":"NO";
        } else if ("viernes".equals(fieldName)) {
            valor = nodo.getViernes()?"SI":"NO";
        } else if ("sabado".equals(fieldName)) {
            valor = nodo.getSabado()?"SI":"NO";
        } else if ("domingo".equals(fieldName)) {
            valor = nodo.getDomingo()?"SI":"NO";
        }

        return valor;
    }
}
