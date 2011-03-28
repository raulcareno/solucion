/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import hibernate.Detalle;
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
public class FacturaDetalleSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public FacturaDetalleSource(List lista) {
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
        Detalle nodo = (Detalle) valorAtual;
        String fieldName = campo.getName();
        try {


            if ("numero".equals(fieldName)) {
                valor = nodo.getFactura().getNumero();
            } else if ("fecha".equals(fieldName)) {
                try {
                    valor = nodo.getFactura().getFecha();
                } catch (Exception e) {
                    System.out.println("ERROR EN FECHA" + e);
                }
            } else if ("ticket".equals(fieldName)) {
                String codigo = nodo.getFactura().getTicket();
                while (codigo.length() < 8) {
                    codigo = "0" + codigo;
                }
                valor = codigo;
            } else if ("cliente".equals(fieldName)) {
                valor = nodo.getFactura().getCliente().getNombres();
            } else if ("telefono".equals(fieldName)) {
                valor = nodo.getFactura().getCliente().getTelefono();
            } else if ("direccion".equals(fieldName)) {
                valor = nodo.getFactura().getCliente().getDireccion();
            } else if ("ruc".equals(fieldName)) {
                valor = nodo.getFactura().getCliente().getIdentificacion();
            } else if ("ingreso".equals(fieldName)) {
                valor = nodo.getFactura().getFechaini();
            } else if ("salida".equals(fieldName)) {
                valor = nodo.getFactura().getFechafin();
            } else if ("tiempo".equals(fieldName)) {
                valor = nodo.getFactura().getTiempo();
            } else if ("total".equals(fieldName)) {
                valor = nodo.getFactura().getTotal();
            } else if ("subtotal".equals(fieldName)) {
                valor = nodo.getFactura().getSubtotal();
            } else if ("iva".equals(fieldName)) {
                valor = nodo.getFactura().getIva();
            } else if ("placa".equals(fieldName)) {
                valor = nodo.getFactura().getPlaca();
            }else if ("cantidad".equals(fieldName)) {
                valor = nodo.getCantidad();
            }else if ("producto".equals(fieldName)) {
                valor = nodo.getProducto().getDescripcion();
            } else if ("vt".equals(fieldName)) {
                valor = nodo.getSubtotal();
            }
        } catch (Exception e) {
            Logger.getLogger(FacturaSource.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
}
