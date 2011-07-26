/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean.sources;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import jcinform.conexion.Permisos;
import jcinform.persistencia.Cxcobrar;
import jcinform.persistencia.Detalle;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author GEOVANNY
 */
public class ReporteReciboDataSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ReporteReciboDataSource(List lista) {
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
        Cxcobrar nodo = (Cxcobrar) valorAtual;
        String fieldName = campo.getName();

        try {
            if ("nombre".equals(fieldName)) {
                valor = nodo.getFactura().getClientes().getApellidos()+" "+nodo.getFactura().getClientes().getNombres();
            }else if ("ruc".equals(fieldName)) {
                valor = nodo.getFactura().getClientes().getIdentificacion();
            }else if ("direccion".equals(fieldName)) {
                valor = nodo.getFactura().getClientes().getDireccion();
            }else if ("numero".equals(fieldName)) {
                valor = nodo.getCodigo()+"";
            }else if ("fecha".equals(fieldName)) {
                valor = nodo.getFactura().getFecha();
            } else if ("telefono".equals(fieldName)) {
                   valor = nodo.getFactura().getClientes().getTelefono();
            } else if ("rubro".equals(fieldName)) {
                    valor = "ABONO FACTURA "+nodo.getFactura().getNumero().substring(9);
                   
            } else if ("cantidad".equals(fieldName)) {
                   valor = 1;
            } else if ("valor".equals(fieldName)) {
                 valor = nodo.getHaber();
                   
            } else if ("efectivo".equals(fieldName)) {
                   valor = nodo.getEfectivo();
            } else if ("cheque".equals(fieldName)) {
                   valor = nodo.getCheque();
            } else if ("debito".equals(fieldName)) {
                   valor = nodo.getDebito();
            } else if ("tarjeta".equals(fieldName)) {
                   valor = nodo.getTarjeta();
            } else if ("descuento".equals(fieldName)) {
                   valor = nodo.getDescuento();
            } else if ("total".equals(fieldName)) {
                    valor = nodo.getHaber();
            }


        } catch (Exception e) {
            System.out.println("en datasource Acta " + e);
        }

        return valor;
    }
 
}
