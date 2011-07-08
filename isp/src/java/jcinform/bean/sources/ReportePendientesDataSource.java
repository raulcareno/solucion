/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean.sources;

import java.util.Iterator;
import java.util.List;
import jcinform.bean.sources.clasestmp.Pendientes;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author GEOVANNY
 */
public class ReportePendientesDataSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ReportePendientesDataSource(List lista) {
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
         Pendientes nodo = (Pendientes) valorAtual;
        String fieldName = campo.getName();

        try {
            if ("factura".equals(fieldName)) {
                valor = nodo.getFactura();
            } else if ("mes".equals(fieldName)) {
                valor = nodo.getFecha();
            }  else if ("plan".equals(fieldName)) {
                valor = nodo.getPlan();
            } else if ("valor".equals(fieldName)) {
                valor = nodo.getTotal();
            }   else if ("saldo".equals(fieldName)) {
                valor = nodo.getSaldo();
            }else if ("cliente".equals(fieldName)) {
                valor = nodo.getCliente().getApellidos()+" "+nodo.getCliente().getNombres();
            }  else if ("fechaabono".equals(fieldName)) {
                valor = nodo.getFechapago();
            }  else if ("formapago".equals(fieldName)) {
                valor = nodo.getFormapago();
            } else if ("valorabonoefe".equals(fieldName)) {
                valor = nodo.getValorabonoefe();
            } else if ("valorabonoche".equals(fieldName)) {
                valor = nodo.getValorabonoche();
            } else if ("valorabonodeb".equals(fieldName)) {
                valor = nodo.getValorabonodeb();
            } else if ("valorabonotar".equals(fieldName)) {
                valor = nodo.getValorabonotar();
            } else if ("noabono".equals(fieldName)) {
                valor = nodo.getNoabono();
            } else if ("notarjeta".equals(fieldName)) {
                valor = nodo.getNotarjeta();
            } else if ("nocheque".equals(fieldName)) {
                valor = nodo.getNocheque();
            } else if ("nocuenta".equals(fieldName)) {
                valor = nodo.getNocuenta();
            }   

        } catch (Exception e) {
            System.out.println("en datasource Acta " + e);
        }

        return valor;
    }

}
