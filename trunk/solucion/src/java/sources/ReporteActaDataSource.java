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
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;


/**
 *
 * @author GEOVANNY
 */
public class ReporteActaDataSource implements JRDataSource{
    private Iterator itrAlumnos;
     private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;

    public ReporteActaDataSource(List lista) {
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
        Matriculas  nodo = (Matriculas) valorAtual;
        String fieldName = campo.getName();

       try {


        if ("curso".equals(fieldName)){
            valor = nodo.getCurso().toString();
        }else if ("especialidad".equals(fieldName)){
            valor = nodo.getCurso().getEspecialidad().getDescripcion();
        }else if ("cursodescripcion".equals(fieldName)){
            valor = nodo.getCurso().getDescripcion();
        }else if ("paralelo".equals(fieldName)){
            valor = nodo.getCurso().getParalelo().getDescripcion();
        }else if ("estudiante".equals(fieldName)) {
            String estado = (nodo.getEstado().equals("Retirado")?"(R)":(nodo.getEstado().equals("Emitir Pase")?"(PE)":""));
            valor = nodo.getEstudiante().getApellido() + " "+ nodo.getEstudiante().getNombre()+" "+estado;
        }else if ("nombres".equals(fieldName)) {
            String estado = (nodo.getEstado().equals("Retirado")?"(R)":(nodo.getEstado().equals("Emitir Pase")?"(PE)":""));
            valor = nodo.getEstudiante().getApellido() + " "+ nodo.getEstudiante().getNombre()+" "+estado;
        }else if ("edad".equals(fieldName)) {
            Date fecha = nodo.getEstudiante().getFechanacimiento();
                    int y1 = fecha.getYear(); 
                    int m1 = fecha.getMonth(); 
                    int d1 = fecha.getDate(); 
                    GregorianCalendar fechaActual2 = new GregorianCalendar(); 
                    int y2 = fechaActual2.get(GregorianCalendar.YEAR); 
                    int m2 = fechaActual2.get(GregorianCalendar.MONTH); 
                    int d2 = fechaActual2.get(GregorianCalendar.DAY_OF_MONTH); 
                    int diffYears = (y2 - y1 - 1) + (m2 == m1 ? (d2 >= d1 ? 1 : 0) : m2 >= m1 ? 1 : 0); 
                valor = diffYears-1900; 
        }else if ("padre".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getApepadre()+" "+nodo.getEstudiante().getRepresentante().getNompadre();
        }else if ("madre".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getApemadre()+" "+nodo.getEstudiante().getRepresentante().getNommadre();
        }else if ("profesionPadre".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getProfesionpadre();
        }else if ("profesionMadre".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getProfesionmadre();
        }else if ("ocupacionMadre".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getOcupacionmadre();
        }else if ("ocupacionPadre".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getOcupacionpadre();
        }else if ("tipoidentificacion".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getTipoidentificacion();
        }else if ("dirfactura".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getDirfactura();
        }else if ("telfactura".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getTelfactura();
        }else if ("nombrefactura".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getNombrefactura();
        }else if ("identificacionfactura".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getIdentificacionfactura();
        }else if ("direccion".equals(fieldName)) {
            valor = nodo.getEstudiante().getDireccion();
        }else if ("repite".equals(fieldName)) {
            valor = nodo.getRepite();
        }else if ("institutoAnterior".equals(fieldName)) {
            valor = nodo.getInstitucion();
        }else if ("representante".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getApellidos()+" "+ nodo.getEstudiante().getRepresentante().getNombres();
        }else if ("observacion".equals(fieldName)) {
            valor = nodo.getObservacion();
        }else if ("fechaNacimiento".equals(fieldName)) {
            valor = nodo.getEstudiante().getFechanacimiento();
        }else if ("lugarNacimiento".equals(fieldName)) {
            valor = (nodo.getEstudiante().getLugarnacimiento()== null?"Quito":nodo.getEstudiante().getLugarnacimiento());
        }else if ("lugfecha".equals(fieldName)) {
            valor = (nodo.getEstudiante().getLugarnacimiento()== null?"Quito":nodo.getEstudiante().getLugarnacimiento())+", "+nodo.getEstudiante().getFechanacimiento().toLocaleString().substring(0,11);
        }else if ("telefonoPa".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getTelpadre();
        }else if ("telefonoMa".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getTelmadre();
        }else if ("emailMa".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getMailmadre();
        }else if ("email".equals(fieldName)) {
            valor = nodo.getEstudiante().getMail();
        }else if ("emailPa".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getMailpadre();
        }else if ("transporte".equals(fieldName)) {
            valor = nodo.getEstudiante().getTransporte();
        }else if ("telefonoRep".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getTelefono();
        }else if ("identificacion".equals(fieldName)) {
            valor = nodo.getEstudiante().getCedula();
        }else if ("direccionRep".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getDireccion();
        }else if ("detalle".equals(fieldName)) {
            valor = nodo.getEstudiante().getNobus();
        }else if ("foto".equals(fieldName)) {
            try{
                byte[] bImage = nodo.getFoto();
                if(bImage!=null){
                    InputStream is = new ByteArrayInputStream(bImage);
                    valor = is;
                }else{
                }
            }catch(Exception ex){
                System.out.println("Error en foto:"+ex);
            }
        }else if ("extension".equals(fieldName)) {
            if(nodo.getExtension()!=null){
                valor = nodo.getCurso().getPeriodo().getInstitucion().getFotos()+nodo.getCodigomat()+"."+nodo.getExtension();
            }else{
                valor = null;
            }

        }else if ("usuario".equals(fieldName)) {
            valor = nodo.getEstudiante().getUsuario();
        }else if ("codigoestudiante".equals(fieldName)) {
            valor = nodo.getEstudiante().getCodigoest();
        }else if ("provincia".equals(fieldName)) {
            valor = nodo.getEstudiante().getProvincia();
        }else if ("canton".equals(fieldName)) {
            valor = nodo.getEstudiante().getCanton();
        }else if ("parroquia".equals(fieldName)) {
            valor = nodo.getEstudiante().getLugarnacimiento();
        }else if ("clave".equals(fieldName)) {
            valor = permiso.decriptar(nodo.getEstudiante().getClave());
        }else if ("usuarior".equals(fieldName)) {
            valor = nodo.getEstudiante().getRepresentante().getUsuario();
        }else if ("claver".equals(fieldName)) {
            valor = permiso.decriptar(nodo.getEstudiante().getRepresentante().getClave());
        }else if ("matricula".equals(fieldName)) {
             String codigo = nodo.getNumero()+"";
                while(codigo.length()<5){
                    codigo = "0"+codigo;
                }
            valor = codigo;
            //valor = String.format("%4d",nodo.getNumero());

        }else if ("folio".equals(fieldName)) {
             String codigo = nodo.getFolio()+"";
                while(codigo.length()<5){
                    codigo = "0"+codigo;
                }
            valor = codigo;
            //valor = String.format("%4d",nodo.getNumero());

        }else if ("numero".equals(fieldName)) {
            valor = nodo.getNumero();
        }else if ("fecha".equals(fieldName)) {
            valor = nodo.getFechamat();
        }else if ("periodo".equals(fieldName)) {
            valor = nodo.getCurso().getPeriodo().getDescripcion();
        }else if ("seccion".equals(fieldName)) {
            valor = nodo.getCurso().getPeriodo().getInstitucion().getTipo();
        }else if ("regimen".equals(fieldName)) {
            valor = nodo.getCurso().getPeriodo().getRegimen();
        }else if ("jornada".equals(fieldName)) {
            valor = nodo.getCurso().getPeriodo().getSeccion().getDescripcion();
        }else if ("genero".equals(fieldName)) {
            valor = nodo.getEstudiante().getGenero();
        }else if ("anios".equals(fieldName)) {
            calcularEdad(nodo.getEstudiante().getFechanacimiento());
            valor = this.year;
        } else if ("meses".equals(fieldName)) {
            calcularEdad(nodo.getEstudiante().getFechanacimiento());
            valor = this.month;
        } else if ("dias".equals(fieldName)) {
            calcularEdad(nodo.getEstudiante().getFechanacimiento());
            valor = this.day;
        }else if ("bus".equals(fieldName)) {
            valor = nodo.getEstudiante().getNobus();
        }else if ("ruc".equals(fieldName)) {
            valor = nodo.getCurso().getPeriodo().getInstitucion().getRuc();
        }else if ("codigo".equals(fieldName)) {
            valor = nodo.getCurso().getPeriodo().getInstitucion().getCodigo();
        }else if ("discapacidad".equals(fieldName)) {
            valor = nodo.getEstudiante().getDiscapacidad();
        }else if ("nacionalidad".equals(fieldName)) {
            valor = nodo.getEstudiante().getNacionalidad();
        }else if ("tipodiscapacidad".equals(fieldName)) {
            valor = nodo.getEstudiante().getTipodiscapacidad();
        }else if ("lugar".equals(fieldName)) {
            valor = nodo.getEstudiante().getLugar();
        }else if ("hermanos".equals(fieldName)) {
            valor = nodo.getEstudiante().getHermanos();
        }else if ("vivecon".equals(fieldName)) {
            valor = nodo.getEstudiante().getVivecon();
        }else if ("tipofamilia".equals(fieldName)) {
            valor = nodo.getEstudiante().getTipofamilia();
        }else if ("ingpadre".equals(fieldName)) {
            valor = nodo.getEstudiante().getIngpadre();
        }else if ("ingmadre".equals(fieldName)) {
            valor = nodo.getEstudiante().getIngmadre();
        }else if ("ingotros".equals(fieldName)) {
            valor = nodo.getEstudiante().getIngotros();
        }else if ("casa".equals(fieldName)) {
            valor = nodo.getEstudiante().getCasa();
        }else if ("luz".equals(fieldName)) {
            valor = nodo.getEstudiante().getLuz();
        }else if ("agua".equals(fieldName)) {
            valor = nodo.getEstudiante().getAgua();
        }else if ("sshh".equals(fieldName)) {
            valor = nodo.getEstudiante().getSshh();
        }else if ("pozo".equals(fieldName)) {
            valor = nodo.getEstudiante().getPozo();
        }else if ("economia".equals(fieldName)) {
            valor = nodo.getEstudiante().getEconomia();
        }else if ("telefonob".equals(fieldName)) {
            valor = nodo.getEstudiante().getTelefonob();
        }else if ("internet".equals(fieldName)) {
            valor = nodo.getEstudiante().getInternet();
        }else if ("religion".equals(fieldName)) {
            valor = nodo.getEstudiante().getReligion();
        } else if ("nombreministro".equals(fieldName)) {
            valor = nodo.getCurso().getPeriodo().getInstitucion().getMinistronombre();
        } else if ("barrio".equals(fieldName)) {
            valor = nodo.getEstudiante().getBarrio();
        }else if ("firmaministro".equals(fieldName)) {
            try{
                byte[] bImage = nodo.getCurso().getPeriodo().getInstitucion().getMinistrofirma();
                if(bImage!=null){
                    InputStream is = new ByteArrayInputStream(bImage);
                    valor = is;
                }else{
                }
            }catch(Exception ex){
                System.out.println("Error en foto:"+ex);
            }
        }else if ("logo".equals(fieldName)) {
            try{
                byte[] bImage = nodo.getCurso().getPeriodo().getInstitucion().getMinisteriologo();
                if(bImage!=null){
                    InputStream is = new ByteArrayInputStream(bImage);
                    valor = is;
                }else{
                }
            }catch(Exception ex){
                System.out.println("Error en foto:"+ex);
            }
        }else if ("logo2".equals(fieldName)) {
            try{
                byte[] bImage = nodo.getCurso().getPeriodo().getInstitucion().getMinisteriologo();
                if(bImage!=null){
                    InputStream is = new ByteArrayInputStream(bImage);
                    valor = is;
                }else{
                }
            }catch(Exception ex){
                System.out.println("Error en foto:"+ex);
            }
        }else if ("firmarector".equals(fieldName)) {
            try{
                byte[] bImage = nodo.getCurso().getPeriodo().getInstitucion().getFirmarector();
                if(bImage!=null){
                    InputStream is = new ByteArrayInputStream(bImage);
                    valor = is;
                }else{
                }
            }catch(Exception ex){
                System.out.println("Error en foto:"+ex);
            }
        }else if ("sello".equals(fieldName)) {
            try{
                byte[] bImage = nodo.getCurso().getPeriodo().getInstitucion().getEscudo();
                if(bImage!=null){
                    InputStream is = new ByteArrayInputStream(bImage);
                    valor = is;
                }else{
                }
            }catch(Exception ex){
                System.out.println("Error en foto:"+ex);
            }
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
