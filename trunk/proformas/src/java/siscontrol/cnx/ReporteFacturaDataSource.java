/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siscontrol.cnx;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import siscontrol.Detalle;

/**
 *
 * @author GEOVANNY
 */
public class ReporteFacturaDataSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ReporteFacturaDataSource(List lista) {
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

        //Detalle permiso = new Detalle();
        Detalle nodo = (Detalle) valorAtual;
        String fieldName = campo.getName();

        try {
            if ("nombre".equals(fieldName)) {
                valor = nodo.getFactura().getCliente().getNombres();
                String razon = nodo.getFactura().getCliente().getRazonsocial();
                //if(!razon.equals("") && !razon.equals("null") && razon.length()>0 )
                if (razon != null && !razon.isEmpty()) {
                    valor = nodo.getFactura().getCliente().getRazonsocial()+" | "+nodo.getFactura().getCliente().getNombres();
                }
            } else if ("ruc".equals(fieldName)) {
                valor = nodo.getFactura().getCliente().getCedula();
            } else if ("email".equals(fieldName)) {
                valor = nodo.getFactura().getCliente().getEmail();
            }  else if ("direccion".equals(fieldName)) {
                valor = nodo.getFactura().getCliente().getDireccion();
            } else if ("numero".equals(fieldName)) {
                valor = nodo.getFactura().getNumero();
            } else if ("fecha".equals(fieldName)) {
                valor = nodo.getFactura().getFecha();
            }else if ("telefono".equals(fieldName)) {
                valor = nodo.getFactura().getCliente().getTelefono();
            }   else if ("cantidad".equals(fieldName)) {
                valor = nodo.getCantidad();
            }  else if ("descripcion".equals(fieldName)) {
                valor = nodo.getDescripcion();
            }  else if ("vu".equals(fieldName)) {
                valor = nodo.getSubtotal();
            }  else if ("vt".equals(fieldName)) {
                valor = nodo.getValor();
            }  else if ("observacion".equals(fieldName)) {
                valor = nodo.getProducto().getDescripcion();
            }  else if ("item".equals(fieldName)) {
                valor = nodo.getProducto().getCode();
            }else if ("subtotal".equals(fieldName)) {
                valor = nodo.getFactura().getSubtotal();
            } else if ("baseiva".equals(fieldName)) {
                valor = nodo.getFactura().getSubtotal1();
            }  else if ("iva".equals(fieldName)) {
                valor = nodo.getFactura().getIva();
            } else if ("descuento".equals(fieldName)) {
                valor = nodo.getFactura().getDescuento();
            } else if ("total".equals(fieldName)) {
                valor = nodo.getFactura().getTotal();
            }


        } catch (Exception e) {
//            System.out.println("en datasource Acta " + e);
        }

        return valor;
    }

    public String mes(int mes) {
        switch (mes) {
            case 0:
                return "Enero";
            case 1:
                return "Febrero";
            case 2:
                return "Marzo";
            case 3:
                return "Abril";
            case 4:
                return "Mayo";
            case 5:
                return "Junio";
            case 6:
                return "Julio";
            case 7:
                return "Agosto";
            case 8:
                return "Septiembre";
            case 9:
                return "Octubre";
            case 10:
                return "Noviembre";
            case 11:
                return "Diciembre";
        }
        return "";

    }
}
