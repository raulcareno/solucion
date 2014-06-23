/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean.sources;

import java.util.Calendar;
import java.util.Date;
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
                String fac = nodo.getFactura();
                if(fac.equals("null")){
                    fac = "000FAC0000";
                }
                while(fac.length()<6){
                    fac = "0"+fac;
                }
                valor = fac;
            } else if ("mes".equals(fieldName)) {
                valor = nodo.getFecha();
            }else if ("empleado".equals(fieldName)) {
                valor = nodo.getEmpleado();
            }  else if ("plan".equals(fieldName)) {
                valor = nodo.getPlan();
            } else if ("valor".equals(fieldName)) {
                valor = nodo.getTotal();
            }   else if ("saldo".equals(fieldName)) {
                valor = nodo.getSaldo();
            }else if ("cliente".equals(fieldName)) {
                if(nodo.getCliente().getRazonsocial() != null && !nodo.getCliente().getRazonsocial().isEmpty()){
                    valor = nodo.getCliente().getRazonsocial() + "" ;
                }else{
                    valor = nodo.getCliente().getApellidos()+" "+nodo.getCliente().getNombres();
                }
            }  else if ("fechaabono".equals(fieldName)) {
                valor = nodo.getFechapago();
            }  else if ("fechacheque".equals(fieldName)) {
                valor = nodo.getFechacheque();
            }  else if ("fechadeposito".equals(fieldName)) {
                valor = nodo.getFechadeposito();
            }  else if ("valorabonoefe".equals(fieldName)) {
                valor = nodo.getValorabonoefe();
            }else if ("bancocheque".equals(fieldName)) {
                valor = nodo.getBancocheque();
            }else if ("bancodeposito".equals(fieldName)) {
                valor = nodo.getBancodeposito();
            }else if ("valorabonodes".equals(fieldName)) {
                valor = nodo.getValorabonodes();
            } else if ("valorabonoche".equals(fieldName)) {
                valor = nodo.getValorabonoche();
            } else if ("valorabonoriva".equals(fieldName)) {
                valor = nodo.getValorabonoriva();
            } else if ("valorabonorfue".equals(fieldName)) {
                valor = nodo.getValorabonorfue();
            } else if ("valorabonotra".equals(fieldName)) {
                valor = nodo.getValorabonotra();
            } else if ("valorabonotot".equals(fieldName)) {
                valor = nodo.getValorabonotot();
            } else if ("notransferencia".equals(fieldName)) {
                valor = nodo.getNumerotransferencia();
            } else if ("valorabonodeb".equals(fieldName)) {
                valor = nodo.getValorabonodep();
            }  else if ("valorabonoban".equals(fieldName)) {
                valor = nodo.getValorabonoban();
            } else if ("valorabonotar".equals(fieldName)) {
                valor = nodo.getValorabonotar();
            }  else if ("valorabonoret".equals(fieldName)) {
                valor = nodo.getValorabonoret();
            } else if ("noabono".equals(fieldName)) {
                valor = nodo.getNoabono();
            } else if ("notarjeta".equals(fieldName)) {
                valor = nodo.getNotarjeta();
            } else if ("nocheque".equals(fieldName)) {
                valor = nodo.getNocheque();
            } else if ("nocuenta".equals(fieldName)) {
                valor = nodo.getNocuenta();
            } else if ("nodeposito".equals(fieldName)) {
                valor = nodo.getNodeposito();
            }  else if ("direccioncontrato".equals(fieldName)) {
                valor = nodo.getDireccion();
            }   else if ("contrato".equals(fieldName)) {
                valor = nodo.getContrato();
            }   else if ("fechainstalacion".equals(fieldName)) {
                valor = nodo.getContratos().getFecha();
            }  else if ("fechafinal".equals(fieldName)) {
                valor = nodo.getContratos().getFechafinal();
            }   else if ("formapago".equals(fieldName)) {
                valor = (nodo.getContratos().getFormapago().equals(1)?"Oficina":nodo.getContratos().getFormapago().equals(2)?"Debito"+nodo.getContratos().getBancos().getNombre():"Domicilio"+nodo.getContratos().getBancos().getNombre());
            }   else if ("empleado".equals(fieldName)) {
                valor = nodo.getContratos().getEmpleados2().toString();
            }    else if ("enplanes".equals(fieldName)) {
                valor = nodo.getEnplanes();
            }       else if ("enequipos".equals(fieldName)) {
                valor = nodo.getEnequipos();
            }       else if ("eninstalaciones".equals(fieldName)) {
                valor = nodo.getEninstalaciones();
            } else if ("telefono".equals(fieldName)) {
                valor = nodo.getTelefono();
            }else if ("emision".equals(fieldName)) {
                valor = nodo.getEmision();
            }else if ("vencimiento".equals(fieldName)) {
                valor = ultimoDia(nodo.getEmision());
            } else if ("subtotal".equals(fieldName)) {
                valor = nodo.getSubtotal();
            } else if ("iva".equals(fieldName)) {
                valor = nodo.getIva();
            }    else if ("ruc".equals(fieldName)) {
                try {
                valor = nodo.getCliente().getIdentificacion();    
                } catch (Exception e) {
                    valor ="";
                }
                
            }             


   

        } catch (Exception e) {
//            System.out.println("en datasource Acta " + e);
        }

        return valor;
    }
  public static Date ultimoDia(Date fecha) {
        //Calendar calInicio = Calendar.getInstance();
        Calendar calFin = Calendar.getInstance();
        String anioFin = (fecha.getYear() + 1900) + "";
        String mesFin = fecha.getMonth() + "";
        calFin.set(Integer.parseInt(anioFin), Integer.parseInt(mesFin), 1);
        calFin.set(Integer.parseInt(anioFin), Integer.parseInt(mesFin), calFin.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date fechaFin = calFin.getTime();
        System.out.println("ULTIMO: " + fechaFin.toLocaleString());
        return fechaFin;

    }
}
