/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean.sources;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import jcinform.conexion.Permisos;
import jcinform.persistencia.Detalle;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

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
                valor = nodo.getFactura().getClientes().getApellidos()+" "+nodo.getFactura().getClientes().getNombres();
            }else if ("ruc".equals(fieldName)) {
                valor = nodo.getFactura().getClientes().getIdentificacion();
            }else if ("direccion".equals(fieldName)) {
                valor = nodo.getFactura().getClientes().getDireccion();
            }else if ("numero".equals(fieldName)) {
                valor = nodo.getFactura().getNumero();
            }else if ("fecha".equals(fieldName)) {
                valor = nodo.getFactura().getFecha();
            } else if ("telefono".equals(fieldName)) {
                   valor = nodo.getFactura().getClientes().getTelefono();
            } else if ("rubro".equals(fieldName)) {
                   
                if(nodo.getEquipos()!= null)
                    valor = nodo.getEquipos()+" ";
                else
                    valor = nodo.getPlan()+" ";
                   
            } else if ("cantidad".equals(fieldName)) {
                   valor = nodo.getCantidad();
            } else if ("valor".equals(fieldName)) {
                 if(nodo.getEquipos()!= null)
                   valor = nodo.getEquipos().getPvp1();
                else
                    valor = new BigDecimal(nodo.getPlan().getValor());
                
                   
            } else if ("subtotal".equals(fieldName)) {
                   valor = nodo.getFactura().getSubtotal();
            } else if ("baseiva".equals(fieldName)) {
                   valor = nodo.getFactura().getBaseiva();
            } else if ("iva".equals(fieldName)) {
                   valor = nodo.getFactura().getPorcentajeiva();
            } else if ("valoriva".equals(fieldName)) {
                   valor = nodo.getFactura().getValoriva();
            } else if ("descuento".equals(fieldName)) {
                   valor = nodo.getFactura().getDescuento();
            } else if ("total".equals(fieldName)) {
                   valor = nodo.getFactura().getTotal();
            }


        } catch (Exception e) {
            System.out.println("en datasource Acta " + e);
        }

        return valor;
    }
 
}
