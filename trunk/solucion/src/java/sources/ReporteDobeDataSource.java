/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Aspectos;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author GEOVANNY
 */
public class ReporteDobeDataSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ReporteDobeDataSource(List lista) {
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
ReporteDobeDataSource ds;
        Aspectos nodo = (Aspectos) valorAtual;
        
        String fieldName = campo.getName();

        try {


            if ("estudiante".equals(fieldName)) {
                valor = nodo.getEstudiante().getApellido() + " " + nodo.getEstudiante().getNombre();
            } else if ("nombres".equals(fieldName)) {
                valor = nodo.getEstudiante().getApellido() + " " + nodo.getEstudiante().getNombre();
            } else if ("edad".equals(fieldName)) {
                Date fecha = nodo.getEstudiante().getFechanacimiento();
                int y1 = fecha.getYear();
                int m1 = fecha.getMonth();
                int d1 = fecha.getDate();
                GregorianCalendar fechaActual2 = new GregorianCalendar();
                int y2 = fechaActual2.get(GregorianCalendar.YEAR);
                int m2 = fechaActual2.get(GregorianCalendar.MONTH);
                int d2 = fechaActual2.get(GregorianCalendar.DAY_OF_MONTH);
                int diffYears = (y2 - y1 - 1) + (m2 == m1 ? (d2 >= d1 ? 1 : 0) : m2 >= m1 ? 1 : 0);
                valor = diffYears - 1900;
            } else if ("padre".equals(fieldName)) {
                valor = nodo.getEstudiante().getRepresentante().getApepadre() + " " + nodo.getEstudiante().getRepresentante().getNompadre();
            } else if ("madre".equals(fieldName)) {
                valor = nodo.getEstudiante().getRepresentante().getApemadre() + " " + nodo.getEstudiante().getRepresentante().getNommadre();
            } else if ("profesionPadre".equals(fieldName)) {
                valor = nodo.getEstudiante().getRepresentante().getProfesionpadre();
            } else if ("profesionMadre".equals(fieldName)) {
                valor = nodo.getEstudiante().getRepresentante().getProfesionmadre();
            } else if ("ocupacionMadre".equals(fieldName)) {
                valor = nodo.getEstudiante().getRepresentante().getOcupacionmadre();
            } else if ("ocupacionPadre".equals(fieldName)) {
                valor = nodo.getEstudiante().getRepresentante().getOcupacionpadre();
            } else if ("direccion".equals(fieldName)) {
                valor = nodo.getEstudiante().getDireccion();
            } else if ("representante".equals(fieldName)) {
                valor = nodo.getEstudiante().getRepresentante().getApellidos() + " " + nodo.getEstudiante().getRepresentante().getNombres();
            } else if ("fechaNacimiento".equals(fieldName)) {
                valor = nodo.getEstudiante().getFechanacimiento();
            }else if ("fecha1".equals(fieldName)) {
                valor = nodo.getFecha();
            } else if ("telefonoPa".equals(fieldName)) {
                valor = nodo.getEstudiante().getRepresentante().getTelpadre();
            } else if ("telefonoMa".equals(fieldName)) {
                valor = nodo.getEstudiante().getRepresentante().getTelmadre();
            } else if ("emailMa".equals(fieldName)) {
                valor = nodo.getEstudiante().getRepresentante().getMailmadre();
            } else if ("email".equals(fieldName)) {
                valor = nodo.getEstudiante().getMail();
            } else if ("emailPa".equals(fieldName)) {
                valor = nodo.getEstudiante().getRepresentante().getMailpadre();
            } else if ("transporte".equals(fieldName)) {
                valor = nodo.getEstudiante().getTransporte();
            } else if ("telefonoRep".equals(fieldName)) {
                valor = nodo.getEstudiante().getRepresentante().getTelefono();
            } else if ("identificacion".equals(fieldName)) {
                valor = nodo.getEstudiante().getCedula();
            } else if ("direccionRep".equals(fieldName)) {
                valor = nodo.getEstudiante().getRepresentante().getDireccion();
            } else if ("detalle".equals(fieldName)) {
                valor = nodo.getEstudiante().getNobus();
            } //        else if ("foto".equals(fieldName)) {
            //            try{
            //                byte[] bImage = nodo.getFoto();
            //                if(bImage!=null){
            //                    InputStream is = new ByteArrayInputStream(bImage);
            //                    valor = is;
            //                }else{
            //                }
            //            }catch(Exception ex){
            //                System.out.println("Error en foto:"+ex);
            //            }
            //        }
            else if ("usuario".equals(fieldName)) {
                valor = nodo.getEstudiante().getUsuario();
            } else if ("genero".equals(fieldName)) {
                valor = nodo.getEstudiante().getGenero();
            } else if ("anios".equals(fieldName)) {
                calcularEdad(nodo.getEstudiante().getFechanacimiento());
                valor = this.year;
            } else if ("meses".equals(fieldName)) {
                calcularEdad(nodo.getEstudiante().getFechanacimiento());
                valor = this.month;
            } else if ("dias".equals(fieldName)) {
                calcularEdad(nodo.getEstudiante().getFechanacimiento());
                valor = this.day;
            } else if ("bus".equals(fieldName)) {
                valor = nodo.getEstudiante().getNobus();
            } else if ("observa1".equals(fieldName)) {
                valor = nodo.getObserva1();
            } else if ("observa2".equals(fieldName)) {
                valor = nodo.getObserva2();
            } else if ("observa3".equals(fieldName)) {
                valor = nodo.getObserva3();
            } else if ("observa4".equals(fieldName)) {
                valor = nodo.getObserva4();
            } else if ("observa5".equals(fieldName)) {
                valor = nodo.getObserva5();
            } else if ("observa6".equals(fieldName)) {
                valor = nodo.getObserva6();
            } else if ("aspecto".equals(fieldName)) {
                String name = nodo.getGrupo();
                if(name.equals("1PESO")){
                    valor = "1. PESO";
                }else if(name.equals("2ESTA")){
                    valor = "2. ESTATURA";
                }else if(name.equals("3AUDI")){
                    valor = "3. AGUDEZA AUDITIVA";
                }else if(name.equals("4VISU")){
                    valor = "4. AGUDEZA VISUAL";
                }else if(name.equals("5MEDI")){
                    valor = "5. INFORME MEDICO";
                }else if(name.equals("6DENT")){
                    valor = "6. INFORME DENTAL";
                }else if(name.equals("7APRO")){
                    valor = "7. APROVECHAMIENTO";
                }else if(name.equals("8DISC")){
                    valor = "8. DISCIPLINA";
                }else if(name.equals("9VISI")){
                        valor = "9. REGISTRO VISITAS";
                }

                
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
