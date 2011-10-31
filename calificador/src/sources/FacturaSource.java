/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

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
public class FacturaSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public FacturaSource(List lista) {
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
//        Factura nodo = (Factura) valorAtual;
//        String fieldName = campo.getName();
//        try {
//
//
//            
//            if ("factura".equals(fieldName)) {
//                valor = nodo.getNumero();
//            }else if("numero".equals(fieldName)) {
//                valor = nodo.getNumero();
//            }else if("codigo".equals(fieldName)) {
//                valor = nodo.getCodigo();
//            } else if ("fecha".equals(fieldName)) {
//                try {
//                    valor = nodo.getFecha();
//                } catch (Exception e) {
//                    System.out.println("ERROR EN FECHA" + e);
//                }
//            } else if ("fechafin".equals(fieldName)) {
//                try {
//                    valor = nodo.getFechafin();
//                } catch (Exception e) {
//                    System.out.println("ERROR EN FECHA" + e);
//                }
//            } else if ("fechaingreso".equals(fieldName)) {
//                try {
//                    valor = nodo.getFechaini();
//                } catch (Exception e) {
//                    System.out.println("ERROR EN FECHA" + e);
//                }
//            }else if ("fecha2".equals(fieldName)) {
//                try {
//                    valor = meses(nodo.getFecha().getMonth())+ (nodo.getFecha().getYear()+1900);
//                } catch (Exception e) {
//                    System.out.println("ERROR EN FECHA" + e);
//                }
//            } else if ("ticket".equals(fieldName)) {
//                try {
//                String codigo = nodo.getTicket();
//                while (codigo.length() < 8) {
//                    codigo = "0" + codigo;
//                }
//                valor = codigo;    
//                } catch (Exception e) {
//                    valor ="   s/t";
//                }
//
//            } else if ("cliente".equals(fieldName)) {
//                valor = nodo.getClientes().getNombres();
//            } else if ("clientenombre".equals(fieldName)) {
//                try {
//                        valor = nodo.getTarjetas().getClientes().getNombres();    
//                } catch (Exception e) {
//                }
//                
//            } else if ("ruc".equals(fieldName)) {
//                valor = nodo.getClientes().getIdentificacion();
//            } else if ("ingreso".equals(fieldName)) {
//                valor = nodo.getFechaini();
//            } else if ("telefono".equals(fieldName)) {
//                valor = nodo.getClientes().getTelefono();
//            } else if ("direccion".equals(fieldName)) {
//                valor = nodo.getClientes().getDireccion();
//            } else if ("salida".equals(fieldName)) {
//                valor = nodo.getFechafin();
//            } else if ("tiempo".equals(fieldName)) {
//                valor = nodo.getTiempo();
//            } else if ("total".equals(fieldName)) {
//                valor = nodo.getTotal();
//            } else if ("placa".equals(fieldName)) {
//                valor = nodo.getPlaca();
//            }else if ("subtotal".equals(fieldName)) {
//                valor = nodo.getSubtotal();
//            } else if ("iva".equals(fieldName)) {
//                valor = nodo.getIva();
//            } else if ("motivo".equals(fieldName)) {
//                valor = nodo.getObservacion();
//            }
//        } catch (Exception e) {
//            Logger.getLogger(FacturaSource.class.getName()).log(Level.SEVERE, null, e);
//        }
        return valor;
    }
    public String meses(int a){
        if(a==0)
          return "ENERO";
        else if(a ==1)
          return "FEBRERO";
        else if(a ==2)
          return "MARZO";
        else if(a ==3)
          return "ABRIL";
        else if(a ==4)
          return "MAYO";
        else if(a ==5)
          return "JUNIO";
        else if(a ==6)
          return "JULIO";
        else if(a ==7)
          return "AGOSTO";
        else if(a ==8)
          return "SEPTIEMBRE";
        else if(a ==9)
          return "OCTUBRE";
        else if(a ==10)
          return "NOVIEMBRE";
        else if(a ==1)
          return "DICIEMBRE";
        else
            return "";

  
    }
}
