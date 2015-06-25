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
import jcinform.persistencia.Contratos;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author GEOVANNY
 */
public class ReporteContratoDataSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ReporteContratoDataSource(List lista) {
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

        Contratos nodo = (Contratos) valorAtual;
        String fieldName = campo.getName();
        try {
            if ("cliente".equals(fieldName)) {
                valor = nodo.getClientes() + "";
                String razon = nodo.getClientes().getRazonsocial() + "";
                if (razon != null && !razon.isEmpty()) {
                    valor = nodo.getClientes().getRazonsocial();
                }
            } else if ("ruc".equals(fieldName)) {
                valor = nodo.getClientes().getIdentificacion();
            } else if ("tipoidentificacion".equals(fieldName)) {
                valor = nodo.getClientes().getTipoidentificacion();
            } else if ("tipoenlace".equals(fieldName)) {
                valor = nodo.getTipoplan();
            }else if ("email".equals(fieldName)) {
                valor = nodo.getClientes().getEmail();
            } else if ("tipoplan".equals(fieldName)) {
                valor = nodo.getPlan().getTipo();
            } else if ("upload".equals(fieldName)) {
                valor = nodo.getPlan().getPvp4()+"";
            } else if ("download".equals(fieldName)) {
                valor = nodo.getPlan().getPvp4()+"";
            } else if ("nivelcomparticion".equals(fieldName)) {
                valor =nodo.getPlan().getNivelcomparticion()+"";
            } else if ("direccion".equals(fieldName)) {
                valor = nodo.getClientes().getDireccion();
            } else if ("telefono".equals(fieldName)) {
                valor = nodo.getClientes().getTelefono();
            } else if ("contrato".equals(fieldName)) {
                valor = nodo.getContrato();
            } else if ("cedula".equals(fieldName)) {
                valor = nodo.getClientes().getIdentificacion();
            } else if ("usuario".equals(fieldName)) {
                valor = nodo.getUsuario();
            } else if ("clave".equals(fieldName)) {
                valor = nodo.getClave();
            } else if ("plan".equals(fieldName)) {
                valor = nodo.getPlan() + "";
            } else if ("valor".equals(fieldName)) {
                valor = nodo.getPlan().getValor();
            } else if ("descuento".equals(fieldName)) {
                valor = nodo.getDescuento();
            } else if ("sector".equals(fieldName)) {
                valor = nodo.getSector().getNombre() + "";
            } else if ("radio".equals(fieldName)) {
                valor = nodo.getRadios().getNombre() + "";
            } else if ("nodo".equals(fieldName)) {
                valor = nodo.getRadios().getNodos().getNombre() + "";
            } else if ("provincia".equals(fieldName)) {
                valor = nodo.getSector().getCanton().getProvincia().getNombre() + "";
            } else if ("canton".equals(fieldName)) {
                valor = nodo.getSector().getCanton().getNombre() + "";
            } else if ("fecha".equals(fieldName)) {
                valor = nodo.getFecha();
            } else if ("fechainstalacion".equals(fieldName)) {
                valor = nodo.getFechainstalacion();
            }  else if ("fechafinal".equals(fieldName)) {
                valor = nodo.getFechafinal();
            } else if ("equipo".equals(fieldName)) {
                valor = nodo.getSerie1();
            } else if ("nofactura".equals(fieldName)) {
                valor = nodo.getSerie2();
            } else if ("serie".equals(fieldName)) {
                valor = nodo.getSerie3();
            } else if ("factura".equals(fieldName)) {
                valor = nodo.getFactura();
            }else if ("saldo".equals(fieldName)) {
                valor = nodo.getSaldo();
            }else if ("valorPago".equals(fieldName)) {
                valor = nodo.getValorPago();
            }else if ("empleado".equals(fieldName)) {
                valor = nodo.getEmpleados2().toString();
            }else if ("formapago".equals(fieldName)) {
                valor = (nodo.getFormapago().equals(1)?"Oficina":nodo.getFormapago().equals(2)?"Debito":"Domicilio");
            }else if ("estado".equals(fieldName)) {
                valor = nodo.getEstado();
            }


        } catch (Exception e) {
//            System.out.println("en datasource Acta " + e);
        }

        return valor;
    }
    public int year = 0;
    public int month = 0;
    public int day = 0;

    public void calcularEdad(Date nacimiento) {
//Date yourDate = (Date) Calendar.getInstance().getTime();
        java.sql.Timestamp birth = new java.sql.Timestamp(nacimiento.getTime());
        Date d = new Date();


        SimpleDateFormat sdfDia = new SimpleDateFormat("dd");
        SimpleDateFormat sdfMes = new SimpleDateFormat("MM");
        SimpleDateFormat sdfAño = new SimpleDateFormat("yyyy");

        int a = Integer.parseInt(sdfAño.format(d)) - Integer.parseInt(sdfAño.format(birth));
        int b = Integer.parseInt(sdfMes.format(d)) - Integer.parseInt(sdfMes.format(birth));
        int c = Integer.parseInt(sdfDia.format(d)) - Integer.parseInt(sdfDia.format(birth));

        if (b < 0) {
            a = a - 1;
            b = 12 + b;
        }

        if (c < 0) {
            b = b - 1;
            switch (Integer.parseInt(sdfMes.format(d))) {
                case 2:
                    int año = Integer.parseInt(sdfAño.format(d));
                    if ((año % 4 == 0) && ((año % 100 != 0) || (año % 400 == 0))) {
                        c = 29 + c;
                    } else {
                        c = 28 + c;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 10:
                case 11:
                    c = 30 + c;
                    break;
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 12:
                    c = 31 + c;
                    break;
            }
        }


        this.day = c;
        this.month = (b < 0 ? 0 : b);
        this.year = a;

    }
}
