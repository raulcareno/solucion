/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean.sources;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
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
                valor = nodo.getFactura().getClientes().getApellidos() + " " + nodo.getFactura().getClientes().getNombres();
                String razon = nodo.getFactura().getClientes().getRazonsocial();
                //if(!razon.equals("") && !razon.equals("null") && razon.length()>0 )
                if (razon != null && !razon.isEmpty()) {
                    valor = nodo.getFactura().getClientes().getRazonsocial();
                }
            } else if ("ruc".equals(fieldName)) {
                valor = nodo.getFactura().getClientes().getIdentificacion();
            } else if ("contrato".equals(fieldName)) {
                try{
                    valor = nodo.getFactura().getContratos().getContrato();
                }catch(Exception e){
                }
                
            } else if ("direccion".equals(fieldName)) {
                valor = nodo.getFactura().getContratos().getDireccionf();
            } else if ("numero".equals(fieldName)) {
                valor = nodo.getFactura().getNumero();
            } else if ("fecha".equals(fieldName)) {
                valor = nodo.getFactura().getFecha();
            }else if ("emision".equals(fieldName)) {
                valor = nodo.getFactura().getEmision();
            } else if ("telefono".equals(fieldName)) {
                valor = nodo.getFactura().getContratos().getTelefonof();
            } else if ("rubro".equals(fieldName)) {

                if (nodo.getEquipos() != null) {
                    valor = nodo.getEquipos()+"";
                } else {
                    valor = nodo.getPlan()  + " (" + mes(nodo.getFactura().getFecha().getMonth()) + ")";
                }

            } else if ("formapago".equals(fieldName)) {
                valor = nodo.getFactura().getContratos().getFormapago();
                if (valor != null) {
                    if (valor.equals(1)) {
                        valor = "Oficina";
                    } else if (valor.equals(3)) {
                        valor = "Domicilio";
                    } else if (valor.equals(2)) {
                        valor = "Débito";
                    } else {
                        valor = "";
                    }
                } else {
                    valor = "";
                }
            } else if ("cantidad".equals(fieldName)) {
                valor = nodo.getCantidad();
            } else if ("valor".equals(fieldName)) {
                if (nodo.getEquipos() != null) {
                    valor = nodo.getEquipos().getPvp1();
                } else {
                    valor = nodo.getTotal();
                }


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
