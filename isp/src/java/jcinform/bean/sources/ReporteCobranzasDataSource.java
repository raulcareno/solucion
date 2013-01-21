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
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author GEOVANNY
 */
public class ReporteCobranzasDataSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ReporteCobranzasDataSource(List lista) {
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

        Permisos permiso = new Permisos();
//        Matriculas nodo = (Matriculas) valorAtual;
        String fieldName = campo.getName();

        try {

//
//            if ("curso".equals(fieldName)) {
//                valor = nodo.getCurso().toString();
//            } else if ("estudiante".equals(fieldName)) {
//                String estado = (nodo.getEstado().equals("Retirado") ? "(R)" : (nodo.getEstado().equals("Emitir Pase") ? "(PE)" : ""));
//                valor = nodo.getEstudiante().getApellido() + " " + nodo.getEstudiante().getNombre() + " " + estado;
//            } else if ("sello".equals(fieldName)) {
//                try {
//                    byte[] bImage = nodo.getCurso().getPeriodo().getInstitucion().getEscudo();
//                    if (bImage != null) {
//                        InputStream is = new ByteArrayInputStream(bImage);
//                        valor = is;
//                    } else {
//                    }
//                } catch (Exception ex) {
//                    System.out.println("Error en foto:" + ex);
//                }
//            }

        } catch (Exception e) {
            //System.out.println("en datasource Acta " + e);
        }

        return valor;
    }
     
}
