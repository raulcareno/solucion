/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import bean.NotaCollection;
import bean.Permisos;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.ServiciosComplementarios;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author GEOVANNY
 */
public class ReporteActaDataSourceServicios implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;

    public ReporteActaDataSourceServicios(List lista) {
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
        ServiciosComplementarios nodo = (ServiciosComplementarios) valorAtual;
        String fieldName = campo.getName();
        try {
            
            if ("codigomat".equals(fieldName)) {
                valor = nodo.getMatricula().getCodigomat();
            }else if ("proCodigo".equals(fieldName)) {
                valor = nodo.getProducto().getCodigo()+"";
            } else if ("producto".equals(fieldName)) {
                valor = nodo.getProducto().getDescripcion();
            } else if ("precio".equals(fieldName)) {
                valor = nodo.getProducto().getPrecio();
            } else if ("observacion2".equals(fieldName)) {
                valor = nodo.getProducto().getObservacion();
            }  else if ("tarjeta".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getTarjeta();
            }  else if ("alimentacion".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getAlimentacion();
            }  else if ("lunes".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getLunes();
            }  else if ("martes".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getMartes();
            }  else if ("miercoles".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getMiercoles();
            }  else if ("jueves".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getJueves();
            }   else if ("viernes".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getViernes();
            }   else if ("ida".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getIda();
            }   else if ("vuelta".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getVuelta();
            }   else if ("recorrido".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRecorrido();
            }    else if ("sector".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getSector();
            }   else if ("curso".equals(fieldName)) {
                valor = nodo.getMatricula().getCurso().toString();
            } else if ("especialidad".equals(fieldName)) {
                valor = nodo.getMatricula().getCurso().getEspecialidad().getDescripcion();
            } else if ("cursodescripcion".equals(fieldName)) {
                valor = nodo.getMatricula().getCurso().getDescripcion();
            } else if ("paralelo".equals(fieldName)) {
                valor = nodo.getMatricula().getCurso().getParalelo().getDescripcion();
            } else if ("estudiante".equals(fieldName)) {
                String estado = (nodo.getMatricula().getEstado().equals("Retirado") ? "(R)" : (nodo.getMatricula().getEstado().equals("Emitir Pase") ? "(PE)" : ""));
                valor = nodo.getMatricula().getEstudiante().getApellido() + " " + nodo.getMatricula().getEstudiante().getNombre() + " " + estado;
            } else if ("nombres".equals(fieldName)) {
                String estado = (nodo.getMatricula().getEstado().equals("Retirado") ? "(R)" : (nodo.getMatricula().getEstado().equals("Emitir Pase") ? "(PE)" : ""));
                valor = nodo.getMatricula().getEstudiante().getApellido() + " " + nodo.getMatricula().getEstudiante().getNombre() + " " + estado;
            } else if ("edad".equals(fieldName)) {
                Date fecha = nodo.getMatricula().getEstudiante().getFechanacimiento();
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

                String f = " día " + fecha.getDay() + " del mes de " + mes + " del año " + (fecha.getYear() + 1900) + "";
                valor = f;
            } else if ("padre".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getApepadre() + " " + nodo.getMatricula().getEstudiante().getRepresentante().getNompadre();
            } else if ("madre".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getApemadre() + " " + nodo.getMatricula().getEstudiante().getRepresentante().getNommadre();
            } else if ("profesionPadre".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getProfesionpadre();
            } else if ("profesionMadre".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getProfesionmadre();
            } else if ("ocupacionMadre".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getOcupacionmadre();
            } else if ("ocupacionPadre".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getOcupacionpadre();
            } else if ("tipoidentificacion".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getTipoidentificacion();
            } else if ("dirfactura".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getDirfactura();
            } else if ("telfactura".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getTelfactura();
            } else if ("nombrefactura".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getNombrefactura();
            } else if ("identificacionfactura".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getIdentificacionfactura();
            } else if ("direccion".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getDireccion();
            } else if ("repite".equals(fieldName)) {
                valor = nodo.getMatricula().getRepite();
            } else if ("institutoAnterior".equals(fieldName)) {
                valor = nodo.getMatricula().getInstitucion();
            } else if ("representante".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getApellidos() + " " + nodo.getMatricula().getEstudiante().getRepresentante().getNombres();
            } else if ("observacion".equals(fieldName)) {
                valor = nodo.getMatricula().getObservacion();
            } else if ("fechaNacimiento".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getFechanacimiento();
            } else if ("lugarNacimiento".equals(fieldName)) {
                valor = (nodo.getMatricula().getEstudiante().getLugarnacimiento() == null ? "Quito" : nodo.getMatricula().getEstudiante().getLugarnacimiento());
            } else if ("lugfecha".equals(fieldName)) {
                valor = (nodo.getMatricula().getEstudiante().getLugarnacimiento() == null ? "Quito" : nodo.getMatricula().getEstudiante().getLugarnacimiento()) + ", " + nodo.getMatricula().getEstudiante().getFechanacimiento().toLocaleString().substring(0, 11);
            } else if ("telefonoPa".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getTelpadre();
            } else if ("telefonoMa".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getTelmadre();
            } else if ("emailMa".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getMailmadre();
            } else if ("email".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getMail();
            } else if ("emailPa".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getMailpadre();
            } else if ("emailRe".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getEmail();
            } else if ("transporte".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getTransporte();
            } else if ("asegurado".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getAsegurado();
            } else if ("aseguradora".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getAseguradora();
            } else if ("telefonoRep".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getTelefono();
            } else if ("identificacion".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getCedula();
            } else if ("identificacionRepre".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getIdentificacionrepre();
            } else if ("direccionRep".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getDireccion();
            } else if ("detalle".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getNobus();
            }else if ("bus".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getNobus();
            } else if ("foto".equals(fieldName)) {
                try {
                    byte[] bImage = nodo.getMatricula().getFoto();
                    if (bImage != null) {
                        InputStream is = new ByteArrayInputStream(bImage);
                        valor = is;
                    } else {
                    }
                } catch (Exception ex) {
                    System.out.println("Error en foto:" + ex);
                }
            } else if ("extension".equals(fieldName)) {
                if (nodo.getMatricula().getExtension() != null) {
                    valor = nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getFotos() + nodo.getMatricula().getCodigomat() + "." + nodo.getMatricula().getExtension();
                } else {
                    valor = null;
                }

            } else if ("usuario".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getUsuario();
            } else if ("codigoestudiante".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getCodigoest();
            } else if ("provincia".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getProvincia();
            } else if ("canton".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getCanton();
            } else if ("parroquia".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getLugarnacimiento();
            } else if ("clave".equals(fieldName)) {
                valor = permiso.decriptar(nodo.getMatricula().getEstudiante().getClave());
            } else if ("usuarior".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getUsuario();
            } else if ("claver".equals(fieldName)) {
                valor = permiso.decriptar(nodo.getMatricula().getEstudiante().getRepresentante().getClave());
            } else if ("matricula".equals(fieldName)) {
                String codigo = nodo.getMatricula().getNumero() + "";
                while (codigo.length() < 5) {
                    codigo = "0" + codigo;
                }
                valor = codigo;
                //valor = String.format("%4d",nodo.getMatricula().getNumero());

            } else if ("folio".equals(fieldName)) {
                String codigo = nodo.getMatricula().getFolio() + "";
                while (codigo.length() < 5) {
                    codigo = "0" + codigo;
                }
                valor = codigo;
                //valor = String.format("%4d",nodo.getMatricula().getNumero());

            } else if ("numero".equals(fieldName)) {
                valor = nodo.getMatricula().getNumero();
            } else if ("fecha".equals(fieldName)) {
                valor = nodo.getMatricula().getFechamat();
            } else if ("periodo".equals(fieldName)) {
                valor = nodo.getMatricula().getCurso().getPeriodo().getDescripcion();
            } else if ("seccion".equals(fieldName)) {
                valor = nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getTipo();
            } else if ("regimen".equals(fieldName)) {
                valor = nodo.getMatricula().getCurso().getPeriodo().getRegimen();
            } else if ("jornada".equals(fieldName)) {
                valor = nodo.getMatricula().getCurso().getPeriodo().getSeccion().getDescripcion();
            } else if ("genero".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getGenero();
            } else if ("anios".equals(fieldName)) {
                calcularEdad(nodo.getMatricula().getEstudiante().getFechanacimiento());
                valor = this.year;
            } else if ("meses".equals(fieldName)) {
                calcularEdad(nodo.getMatricula().getEstudiante().getFechanacimiento());
                valor = this.month;
            } else if ("dias".equals(fieldName)) {
                calcularEdad(nodo.getMatricula().getEstudiante().getFechanacimiento());
                valor = this.day;
            } else if ("bus".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getNobus();
            } else if ("ruc".equals(fieldName)) {
                valor = nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getRuc();
            } else if ("codigo".equals(fieldName)) {
                valor = nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getCodigo();
            } else if ("discapacidad".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getDiscapacidad();
            } else if ("nacionalidad".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getNacionalidad();
            } else if ("tipodiscapacidad".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getTipodiscapacidad();
            } else if ("lugar".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getLugar();
            } else if ("hermanos".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getHermanos();
            } else if ("vivecon".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getVivecon();
            } else if ("tipofamilia".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getTipofamilia();
            } else if ("ingpadre".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getIngpadre();
            } else if ("ingmadre".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getIngmadre();
            } else if ("ingotros".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getIngotros();
            } else if ("casa".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getCasa();
            } else if ("luz".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getLuz();
            } else if ("agua".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getAgua();
            } else if ("sshh".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getSshh();
            } else if ("pozo".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getPozo();
            } else if ("economia".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getEconomia();
            } else if ("telefonob".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getTelefonob();
            } else if ("internet".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getInternet();
            } else if ("religion".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getReligion();
            } else if ("nombreministro".equals(fieldName)) {
                valor = nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getMinistronombre();
            } else if ("barrio".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getBarrio();
            } else if ("celular".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getRepresentante().getCelular();
            } else if ("firmaministro".equals(fieldName)) {
                try {
                    byte[] bImage = nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getMinistrofirma();
                    if (bImage != null) {
                        InputStream is = new ByteArrayInputStream(bImage);
                        valor = is;
                    } else {
                    }
                } catch (Exception ex) {
                    System.out.println("Error en foto:" + ex);
                }
            } else if ("logo".equals(fieldName)) {
                try {
                    byte[] bImage = nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getMinisteriologo();
                    if (bImage != null) {
                        InputStream is = new ByteArrayInputStream(bImage);
                        valor = is;
                    } else {
                    }
                } catch (Exception ex) {
                    System.out.println("Error en foto:" + ex);
                }
            } else if ("logo2".equals(fieldName)) {
                try {
                    byte[] bImage = nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getMinisteriologo();
                    if (bImage != null) {
                        InputStream is = new ByteArrayInputStream(bImage);
                        valor = is;
                    } else {
                    }
                } catch (Exception ex) {
                    System.out.println("Error en foto:" + ex);
                }
            } else if ("firmarector".equals(fieldName)) {
                try {
                    byte[] bImage = nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getFirmarector();
                    if (bImage != null) {
                        InputStream is = new ByteArrayInputStream(bImage);
                        valor = is;
                    } else {
                    }
                } catch (Exception ex) {
                    System.out.println("Error en foto:" + ex);
                }
            } else if ("sello".equals(fieldName)) {
                try {
                    byte[] bImage = nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getEscudo();
                    if (bImage != null) {
                        InputStream is = new ByteArrayInputStream(bImage);
                        valor = is;
                    } else {
                    }
                } catch (Exception ex) {
                    System.out.println("Error en foto:" + ex);
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
