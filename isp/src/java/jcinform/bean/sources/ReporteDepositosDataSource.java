/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean.sources;

import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Depositos;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author GEOVANNY
 */
public class ReporteDepositosDataSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ReporteDepositosDataSource(List lista) {
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
        Depositos nodo = (Depositos) valorAtual;
        String fieldName = campo.getName();

        try {
            if ("cliente".equals(fieldName)) {
                valor = nodo.getCliente();
            }else if ("ruc".equals(fieldName)) {
                valor = nodo.getRuc();
            }else if ("codigo".equals(fieldName)) {
                valor = nodo.getCodigo();
            }else if ("comentario".equals(fieldName)) {
                valor = nodo.getComentario();
            }else if ("contable".equals(fieldName)) {
                valor = nodo.getContable();
            }else if ("contrato".equals(fieldName)) {
                valor = nodo.getContrato();
            }else if ("cxcobrar".equals(fieldName)) {
                valor = nodo.getCxcobrar();
            }else if ("detalle".equals(fieldName)) {
                valor = nodo.getDetalle();
            }else if ("emision".equals(fieldName)) {
                valor = nodo.getEmision();
            }else if ("apellidos".equals(fieldName)) {
                valor = nodo.getEmpleados().getApellidos();
            }else if ("fecha".equals(fieldName)) {
                valor = nodo.getFecha();
            }else if ("factura".equals(fieldName)) {
                valor = nodo.getFactura();
            }else if ("fechacxc".equals(fieldName)) {
                valor = nodo.getFechacxc();
            }else if ("monto".equals(fieldName)) {
                valor = nodo.getMonto();
            }else if ("nodeposito".equals(fieldName)) {
                valor = nodo.getNodeposito();
            }else if ("nopago".equals(fieldName)) {
                valor = nodo.getNopago();
            }else if ("pago".equals(fieldName)) {
                valor = nodo.getPago();
            }else if ("tipo".equals(fieldName)) {
                valor = nodo.getTipo();
            }else if ("vence".equals(fieldName)) {
                valor = nodo.getVence();
            }     


        } catch (Exception e) {
//            System.out.println("en datasource Acta " + e);
        }

        return valor;
    }
 
}
