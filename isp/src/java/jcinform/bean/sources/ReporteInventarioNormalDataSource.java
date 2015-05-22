/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean.sources;

import java.util.Iterator;
import java.util.List;
import jcinform.bean.sources.clasestmp.InventarioNormal;
import jcinform.bean.sources.clasestmp.Pendientes;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author GEOVANNY
 */
public class ReporteInventarioNormalDataSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ReporteInventarioNormalDataSource(List lista) {
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
         InventarioNormal nodo = (InventarioNormal) valorAtual;
        String fieldName = campo.getName();

        try {
            if ("producto".equals(fieldName)) {
                valor = nodo.getProducto();
            } else if ("entrada".equals(fieldName)) {
                valor = nodo.getEntrada();
            }  else if ("salida".equals(fieldName)) {
                valor = nodo.getSalida();
            }  else if ("ajuste".equals(fieldName)) {
                valor = nodo.getAjuste();
            }  else if ("prestamo".equals(fieldName)) {
                valor = nodo.getPrestamo();
            } else if ("total".equals(fieldName)) {
                valor = nodo.getTotal();
            }else if ("cantidad".equals(fieldName)) {
                valor = nodo.getCantidad();
            }else if ("documento".equals(fieldName)) {
                valor = nodo.getDocumento();
            }else if ("tipo".equals(fieldName)) {
                valor = nodo.getTipo();
            }else if ("serie".equals(fieldName)) {
                valor = nodo.getSerie();
            }else if ("factura".equals(fieldName)) {
                valor = nodo.getFactura();
            }else if ("proveedor".equals(fieldName)) {
                valor = nodo.getProveedor();
            }else if ("compra".equals(fieldName)) {
                valor = nodo.getCompra();
            } else if ("valor".equals(fieldName)) {
                valor = nodo.getValor();
            } else if ("valorInd".equals(fieldName)) {
                valor = nodo.getValorInd();
            } else if ("cantidadpro".equals(fieldName)) {
                valor = nodo.getCantidadpro();
            }  else if ("fecha".equals(fieldName)) {
                valor = nodo.getFecha();
            }   else if ("contrato".equals(fieldName)) {
                valor = nodo.getContrato();
            }   else if ("direccion".equals(fieldName)) {
                valor = nodo.getDireccion();
            } else if ("empleado".equals(fieldName)) {
                valor = nodo.getEmpleado();
            } 
        } catch (Exception e) {
//            System.out.println("en datasource Acta " + e);
        }

        return valor;
    }

}
