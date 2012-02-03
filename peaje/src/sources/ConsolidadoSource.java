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
public class ConsolidadoSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ConsolidadoSource(List lista) {
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
        General nodo = (General) valorAtual;
        String fieldName = campo.getName();
        try {


            
            if ("dato1".equals(fieldName)) {
                valor = nodo.getDato1();
            }else if("dato2".equals(fieldName)) {
                valor = nodo.getDato2();
            }else if("dato3".equals(fieldName)) {
                valor = nodo.getDato3();
            }else if("dato4".equals(fieldName)) {
                valor = nodo.getDato4();
            }else if("dato5".equals(fieldName)) {
                valor = nodo.getDato5();
            } else if ("valor1".equals(fieldName)) {
                valor = nodo.getValor1();
            }else if("valor2".equals(fieldName)) {
                valor = nodo.getValor2();
            }else if("valor3".equals(fieldName)) {
                valor = nodo.getValor3();
            }else if("valor4".equals(fieldName)) {
                valor = nodo.getValor4();
            }else if("valor5".equals(fieldName)) {
                valor = nodo.getValor5();
            } else if ("numero1".equals(fieldName)) {
                valor = nodo.getNumero1();
            }else if("numero2".equals(fieldName)) {
                valor = nodo.getNumero2();
            }else if("numero3".equals(fieldName)) {
                valor = nodo.getNumero3();
            }else if("numero4".equals(fieldName)) {
                valor = nodo.getNumero4();
            }else if("numero5".equals(fieldName)) {
                valor = nodo.getNumero5();
            }  
        } catch (Exception e) {
            Logger.getLogger(ConsolidadoSource.class.getName()).log(Level.SEVERE, null, e);
        }
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
