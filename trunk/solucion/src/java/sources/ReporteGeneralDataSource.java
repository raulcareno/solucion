/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author GEOVANNY
 */
public class ReporteGeneralDataSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ReporteGeneralDataSource(List lista) {
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
        General nodo = (General) valorAtual;
        String fieldName = campo.getName();

        try {
            if ("valor1".equals(fieldName)) {
                valor = nodo.getValor1();
            } else if ("valor2".equals(fieldName)) {
                valor = nodo.getValor2();
            } else if ("valor3".equals(fieldName)) {
                valor = nodo.getValor3();
            } else if ("valor4".equals(fieldName)) {
                valor = nodo.getValor4();
            } else if ("valor5".equals(fieldName)) {
                valor = nodo.getValor5();
            } else if ("valor6".equals(fieldName)) {
                valor = nodo.getValor6();
            } else if ("valor7".equals(fieldName)) {
                valor = nodo.getValor7();
            }


        } catch (Exception e) {
            System.out.println("ERROR EN ITERAR REPORTE GENERAL DATASOURCE" + e);
        }

        return valor;

    }
 
}
