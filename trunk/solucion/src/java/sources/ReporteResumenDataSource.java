/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sources;

import bean.Permisos;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Respuestasencuesta;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;


/**
 *
 * @author GEOVANNY
 */
public class ReporteResumenDataSource implements JRDataSource{
    private Iterator itrAlumnos;
     private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;

    public ReporteResumenDataSource(List lista) {
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


        ResumenClase  nodo = (ResumenClase) valorAtual;
        String fieldName = campo.getName();

       try {


        if ("curso".equals(fieldName)){
            valor = nodo.getCurso();
        }else if ("pregunta".equals(fieldName)) {
            valor = nodo.getPregunta()+"";
        }else if ("id".equals(fieldName)) {
            valor = nodo.getId()+"";
        }else if ("respuesta".equals(fieldName)) {
            valor = nodo.getRespuesta();
        }else if ("valor".equals(fieldName)) {
            valor = nodo.getValor();
        }
         } catch (Exception e) {
             System.out.println("en datasource Acta "+e);
        }

        return valor;
    }
      public int year=0;
    public int month=0;
    public int day=0;
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
        this.month = (b < 0?0:b);
        this.year = a;

    }

}
