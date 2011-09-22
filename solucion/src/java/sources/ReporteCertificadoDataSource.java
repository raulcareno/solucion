/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sources;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Periodo;
import jcinform.persistencia.Textos;
import jcinform.procesos.Administrador;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;


/**
 *
 * @author GEOVANNY
 */
public class ReporteCertificadoDataSource implements JRDataSource{
    private Iterator itrAlumnos;
     private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;

    public ReporteCertificadoDataSource(List lista) {
        super();
        this.itrNodos = lista.iterator();
    }

    public boolean next() throws JRException {
        itrAlumnos = itrNodos;
        valorAtual = itrAlumnos.hasNext() ? itrAlumnos.next() : null;
        irParaProximoAlumno = (valorAtual != null);
        return irParaProximoAlumno;
    }
    public String convertir(Date fecha){
        String mes = "";
            switch(fecha.getMonth()){
                case 0: mes = "Enero"; break;
                case 1: mes = "Febrero"; break;
                case 2: mes = "Marzo"; break;
                case 3: mes = "Abril"; break;
                case 4: mes = "Mayo"; break;
                case 5: mes = "Junio"; break;
                case 6: mes = "Julio"; break;
                case 7: mes = "Agosto"; break;
                case 8: mes = "Septiembre"; break;
                case 9: mes = "Octubre"; break;
                case 10: mes = "Noviembre"; break;
                case 11: mes = "Diciembre"; break;
            }
        
    return fecha.getDate()+ " de "+ mes+ " del "+ (fecha.getYear()+1900);
    }
    public String valor(Periodo periodoAct){
        List aa = new ArrayList();
        
          Administrador bs = new Administrador();
          List<Textos> mensajes = bs.query("Select o from Textos o " +
                  "where o.periodo.codigoper= '"+periodoAct.getCodigoper()+"' ");
                String men="";
                for (Iterator<Textos> itMens = mensajes.iterator(); itMens.hasNext();) {
                        Textos mensajes1 = itMens.next();
                            if(mensajes1.getVariable().equals("CERTMATR")){
                                men = mensajes1.getMensaje();
                              }
                }
                
        return men;
    }
    /*
     * (non-Javadoc)
     *
     * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
     */
    public Object getFieldValue(JRField campo) throws JRException {
        Object valor = null;
        Matriculas  nodo = (Matriculas) valorAtual;
        String fieldName = campo.getName();
        if ("texto".equals(fieldName)) {
                String men=valor(nodo.getCurso().getPeriodo());
//                System.out.println("MENSAJE"+men);
                men = men.replace("[estudiante]",nodo.getEstudiante().getApellido()+" "+nodo.getEstudiante().getNombre());
                men = men.replace("[curso]",nodo.getCurso().getDescripcion());
                men = men.replace("[jornada]",nodo.getCurso().getPeriodo().getSeccion().getDescripcion());
                men = men.replace("[especialidad]",nodo.getCurso().getEspecialidad().getDescripcion());
                men = men.replace("[paralelo]",nodo.getCurso().getParalelo().getDescripcion());
                men = men.replace("[matricula]",nodo.getNumero()+"");
                men = men.replace("[folio]",nodo.getFolio()+"");
                men = men.replace("[provincia]",nodo.getEstudiante().getProvincia()+"");
                men = men.replace("[canton]",nodo.getEstudiante().getCanton()+"");
                men = men.replace("[parroquia]",nodo.getEstudiante().getLugarnacimiento()+"");
                men = men.replace("[fecha]",convertir(nodo.getFechamat()));
                men = men.replace("[institucion]",nodo.getCurso().getPeriodo().getInstitucion().getDenominacion()+" "+nodo.getCurso().getPeriodo().getInstitucion().getNombre());
                valor = men;
                //ocupacionPadre
        }else if ("matricula".equals(fieldName)) {
            valor = nodo.getNumero() ;
        }else  if ("curso".equals(fieldName)){
            valor = nodo.getCurso().toString();
        }else if ("folio".equals(fieldName)) {
            valor = nodo.getNumero() ;
        }else if ("estudiante".equals(fieldName)) {
            valor = nodo.getEstudiante().getApellido() + " "+  nodo.getEstudiante().getApellido() ;
        }else if ("fecha".equals(fieldName)) {
            valor = nodo.getFechamat();
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

        return valor;
    }
}
