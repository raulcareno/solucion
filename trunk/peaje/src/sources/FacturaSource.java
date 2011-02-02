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
import hibernate.Factura;


/**
 *
 * @author GEOVANNY
 */
public class FacturaSource implements JRDataSource{
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
        Factura   nodo = (Factura) valorAtual;
        String fieldName = campo.getName();
        try{


        if ("numero".equals(fieldName)){
            valor = nodo.getNumero();
        }else if ("fecha".equals(fieldName)) {
            try {
            valor = nodo.getFecha();
            } catch (Exception e) {
                System.out.println("ERROR EN FECHA"+e);
            }
        } else if ("ticket".equals(fieldName)) {
             String codigo = nodo.getTicket();
                while(codigo.length()<8){
                    codigo = "0"+codigo;
                }
            valor = codigo;
        }  else if ("cliente".equals(fieldName)) {
            valor = nodo.getCliente().getNombres();
        } else if ("ruc".equals(fieldName)) {
            valor = nodo.getCliente().getIdentificacion();
        }  else if ("ingreso".equals(fieldName)) {
            valor = nodo.getFechaini();
        }  else if ("salida".equals(fieldName)) {
            valor = nodo.getFechafin();
        }  else if ("tiempo".equals(fieldName)) {
            valor = nodo.getTiempo();
        }  else if ("total".equals(fieldName)) {
            valor = nodo.getTotal();
        } else if ("placa".equals(fieldName)) {
            valor = nodo.getPlaca();
        }
        }catch(Exception e){
              Logger.getLogger(FacturaSource.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
}
