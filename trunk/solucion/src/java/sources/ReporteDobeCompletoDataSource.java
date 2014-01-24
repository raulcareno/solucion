/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import bean.Permisos;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.DobeEstudiantes;
import jcinform.persistencia.Matriculas;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author GEOVANNY
 */
public class ReporteDobeCompletoDataSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ReporteDobeCompletoDataSource(List lista) {
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

        //Permisos permiso = new Permisos();
        DobeEstudiantes nodo = (DobeEstudiantes) valorAtual;
        String fieldName = campo.getName();

        try {


//            if ("listaNotas".equals(fieldName)) {
//                ArrayList<Estudiantes> matriculas = new ArrayList<Estudiantes>();
//                matriculas.add(nodo.getEstudiante());
//                valor = new JRBeanCollectionDataSource(matriculas);
//
//            } else if ("listaNotas2".equals(fieldName)) {
            if ("apellidos".equals(fieldName)) {
                valor = nodo.getApellidos();
            } else if ("aniolectivo".equals(fieldName)) {
                valor = nodo.getAniolectivo();
            } else if ("elular".equals(fieldName)) {
                valor = nodo.getCelular();
            } else if ("codigo".equals(fieldName)) {
                valor = nodo.getCodigo();
            } else if ("curso".equals(fieldName)) {
                valor = nodo.getCurso();
            } else if ("deserto".equals(fieldName)) {
                valor = nodo.getDeserto();
            } else if ("direccion".equals(fieldName)) {
                valor = nodo.getDireccion();
            } else if ("direccion1".equals(fieldName)) {
                valor = nodo.getDireccion1();
            } else if ("direccion2".equals(fieldName)) {
                valor = nodo.getDireccion2();
            } else if ("explique".equals(fieldName)) {
                valor = nodo.getExplique();
            } else if ("habitaciones".equals(fieldName)) {
                valor = nodo.getHabitaciones();
            } else if ("hermanos".equals(fieldName)) {
                valor = nodo.getHermanos();
            } else if ("hombres".equals(fieldName)) {
                valor = nodo.getHombres();
            } else if ("lugarnacimiento".equals(fieldName)) {
                valor = nodo.getLugarnacimiento();
            } else if ("mujeres".equals(fieldName)) {
                valor = nodo.getMujeres();
            } else if ("nacionalidad".equals(fieldName)) {
                valor = nodo.getNacionalidad();
            } else if ("nombres".equals(fieldName)) {
                valor = nodo.getNombres();
            } else if ("observacion".equals(fieldName)) {
                valor = nodo.getObservacion();
            } else if ("observaciondeserto".equals(fieldName)) {
                valor = nodo.getObservaciondeserto();
            } else if ("observacionrepitio".equals(fieldName)) {
                valor = nodo.getObservacionrepitio();
            } else if ("proviene".equals(fieldName)) {
                valor = nodo.getProviene();
            } else if ("raza".equals(fieldName)) {
                valor = nodo.getRaza();
            } else if ("repitio".equals(fieldName)) {
                valor = nodo.getRepitio();
            } else if ("sshh".equals(fieldName)) {
                valor = nodo.getSshh();
            } else if ("telefono".equals(fieldName)) {
                valor = nodo.getTelefono();
            } else if ("vivenjuntos".equals(fieldName)) {
                valor = nodo.getVivenjuntos();
            } else if ("representantes".equals(fieldName)) {
                valor = new JRBeanCollectionDataSource(nodo.getRepresentantes());
            } else if ("estructura".equals(fieldName)) {
                valor = new JRBeanCollectionDataSource(nodo.getEstructura());
            } else if ("estructura1".equals(fieldName)) {
                valor = new JRBeanCollectionDataSource(nodo.getEstructura1());
            } else if ("economicas".equals(fieldName)) {
                valor = new JRBeanCollectionDataSource(nodo.getEconomicas());
            } else if ("ingresos".equals(fieldName)) {
                valor = new JRBeanCollectionDataSource(nodo.getIngresos());
            } else if ("egresos".equals(fieldName)) {
                valor = new JRBeanCollectionDataSource(nodo.getEgresos());
            } else if ("clubes".equals(fieldName)) {
                valor = new JRBeanCollectionDataSource(nodo.getClubes());
            } else if ("psicopedagogicos".equals(fieldName)) {
                valor = new JRBeanCollectionDataSource(nodo.getPsicopedagogicos());
            } else if ("aprendizajes".equals(fieldName)) {
                valor = new JRBeanCollectionDataSource(nodo.getAprendizajes());
            }  else if ("caracteristicas".equals(fieldName)) {
                valor = new JRBeanCollectionDataSource(nodo.getCaracteristicas());
            }  else if ("social".equals(fieldName)) {
                valor = new JRBeanCollectionDataSource(nodo.getSocial());
            }  else if ("edad".equals(fieldName)) {
                Date fecha = new Date();
                int y1 = fecha.getYear();
                int m1 = fecha.getMonth();
                int d1 = fecha.getDate();
                GregorianCalendar fechaActual2 = new GregorianCalendar();
                int y2 = fechaActual2.get(GregorianCalendar.YEAR);
                int m2 = fechaActual2.get(GregorianCalendar.MONTH);
                int d2 = fechaActual2.get(GregorianCalendar.DAY_OF_MONTH);
                int diffYears = (y2 - y1 - 1) + (m2 == m1 ? (d2 >= d1 ? 1 : 0) : m2 >= m1 ? 1 : 0);
                valor = diffYears - 1900;
            } else if ("fechaFormat".equals(fieldName)) {
                Date fecha = new Date();
                String mes = "";
                switch (fecha.getMonth()) {
                    case 0:
                        mes = "Enero";
                        break;
                    case 1:
                        mes = "Febreo";
                        break;
                    case 2:
                        mes = "Marzo";
                        break;
                    case 3:
                        mes = "Abril";
                        break;
                    case 4:
                        mes = "Mayo";
                        break;
                    case 5:
                        mes = "Junio";
                        break;
                    case 6:
                        mes = "Julio";
                        break;
                    case 7:
                        mes = "Agosto";
                        break;
                    case 8:
                        mes = "Septiembre";
                        break;
                    case 9:
                        mes = "Octubre";
                        break;
                    case 10:
                        mes = "Noviembre";
                        break;
                    case 11:
                        mes = "Diciembre";
                        break;

                }

                String f = " día " + fecha.getDate() + " del mes de " + mes + " del año " + (fecha.getYear() + 1900) + "";
                valor = f;
            }

        } catch (Exception e) {
            System.out.println("en datasource Acta " + e);
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
