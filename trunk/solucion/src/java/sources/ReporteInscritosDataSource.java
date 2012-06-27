/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sources;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
public class ReporteInscritosDataSource implements JRDataSource{
    private Iterator itrAlumnos;
     private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;

    public ReporteInscritosDataSource(List lista) {
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
        Matriculas  nodo = (Matriculas) valorAtual;
        
        String fieldName = campo.getName();
       
        if ("curso".equals(fieldName)) {
            valor = nodo.getCurso().getDescripcion()+" "+nodo.getCurso().getEspecialidad().getDescripcion()+" " +
                    ""+nodo.getCurso().getParalelo().getDescripcion();
        }else if ("nombres".equals(fieldName)) {
            valor = nodo.getEstudiante().getApellido()+" "+ nodo.getEstudiante().getNombre();
        }else if ("no".equals(fieldName)) {
            valor = nodo.getEstudiante().getCodigoest();
        }else if ("observacion".equals(fieldName)) {
                valor = nodo.getObservacion();
           
        }else if ("fecha".equals(fieldName)) {
           valor = nodo.getFechamat();
        }else if ("nacimiento".equals(fieldName)) {
           valor = nodo.getEstudiante().getFechanacimiento();
        }else if ("discapacidad".equals(fieldName)) {
           valor = nodo.getEstudiante().getDiscapacidad();
        }else if ("tdiscapacidad".equals(fieldName)) {
           valor = nodo.getEstudiante().getTipodiscapacidad();
        }else if ("abanderado".equals(fieldName)) {
           valor = nodo.getEstudiante().getEconomia();
        }else if ("hermanos".equals(fieldName)) {
           valor = nodo.getEstudiante().getHermanos();
        }else if ("aprovechamiento".equals(fieldName)) {
           valor = nodo.getEstudiante().getIngpadre();
        } else if ("sello".equals(fieldName)) {
            try{//sello del colegio
                byte[] bImage = nodo.getCurso().getPeriodo().getInstitucion().getEscudo();
                if(bImage!=null){
                    InputStream is = new ByteArrayInputStream(bImage);
                    valor = is;
                }else{
                }
            }catch(Exception ex){
                System.out.println("Error en foto:"+ex);
            }
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
        } else if ("fondo".equals(fieldName)) {
            try{//FONDO PARA CARNET
                byte[] bImage =nodo.getCurso().getPeriodo().getInstitucion().getCarnet();
                if(bImage!=null){
                    InputStream is = new ByteArrayInputStream(bImage);
                    valor = is;
                }else{
                }
            }catch(Exception ex){
                //System.out.println("Error en foto:"+ex);
            }
        } else if ("direccion".equals(fieldName)) {
           valor = nodo.getEstudiante().getDireccion();
        }else if ("periodo".equals(fieldName)) {
           valor = nodo.getCurso().getPeriodo().getDescripcion();
        }else if ("numero".equals(fieldName)) {
           valor = nodo.getNumero();
        }else if ("usuario".equals(fieldName)) {
           valor = nodo.getEstudiante().getUsuario();
        }else if ("clave".equals(fieldName)) {
           valor = nodo.getEstudiante().getClave();
        }
        


        return valor;
    }
}
