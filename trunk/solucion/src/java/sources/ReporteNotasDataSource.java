/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sources;


import bean.notas;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author GEOVANNY
 */
public class ReporteNotasDataSource implements JRDataSource{
    private Iterator itrAlumnos;
     private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;

    public ReporteNotasDataSource(List lista) {
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
        Nota   nodo = (Nota) valorAtual;
//        NumerosaLetras num = new NumerosaLetras();
       try {
            String fieldName = campo.getName();
        if ("sistema".equals(fieldName)){
            valor = nodo.getSistema().getAbreviatura();
        }else if ("tipo".equals(fieldName)) {
            valor = nodo.getSistema().getTrimestre().getDescripcion();
        }else if ("contador".equals(fieldName)) {
            valor = nodo.getContador();
        }else if ("nota".equals(fieldName)) {

            try {
            valor = nodo.getNota();
                System.out.println("NOTA: "+valor);
            } catch (Exception e) {
                System.out.println("fieldcargar: "+e);
            }

        }else
            if ("estudiante".equals(fieldName)) {
           //valor = nodo.getMatricula().getEstudiante().getApellido()+" "+ nodo.getMatricula().getEstudiante().getNombre();

           String estado = (nodo.getMatricula().getEstado().equals("Retirado")?"(R)":(nodo.getMatricula().getEstado().equals("Emitir Pase")?"(PE)":""));
            valor = nodo.getMatricula().getEstudiante().getApellido() + " "+ nodo.getMatricula().getEstudiante().getNombre()+" "+estado;
            System.out.println("estudiante: "+valor);
        }else    if ("matricula".equals(fieldName)) {
           valor = nodo.getMatricula().getCodigomat();
        }else if ("curso".equals(fieldName)) {
           valor = nodo.getMatricula().getCurso().getDescripcion()+" "+nodo.getMatricula().getCurso().getEspecialidad()+" "+nodo.getMatricula().getCurso().getParalelo();
        }else if ("regimen".equals(fieldName)) {
           valor = nodo.getMatricula().getCurso().getPeriodo().getRegimen();
        }else if ("jornada".equals(fieldName)) {
           valor = nodo.getMatricula().getCurso().getPeriodo().getSeccion().getDescripcion();
        }else if ("materia".equals(fieldName)) {
           valor = nodo.getMateria().getDescripcion();
     
        }else if ("profesor".equals(fieldName)) {
           valor = nodo.getMprofesor().getEmpleado().getApellidos() +" "+nodo.getMprofesor().getEmpleado().getNombres()+" ";
        }
        else if ("observacion".equals(fieldName)) {
            try{
                valor = (nodo.getMatricula().getEstado().equals("Retirado")?"Retirado:"+nodo.getMatricula().getFecharet().toLocaleString().substring(0, 10):"");
            }catch(Exception e){
               valor = (nodo.getMatricula().getEstado().equals("Retirado")?"Retirado":"");
            }

        }
       else if ("sello".equals(fieldName)) {


           try{//FONDO PARA CARNET
                byte[] bImage =nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getEscudo();
                if(bImage!=null){
                    InputStream is = new ByteArrayInputStream(bImage);
                    valor = is;
                }else{
                }
            }catch(Exception ex){
                System.out.println("Error en foto:"+ex);
            }
        }
        else if ("listaNotas".equals(fieldName)){
            valor = new JRBeanCollectionDataSource(nodo.getNotas());
        }
} catch (Exception e) {
       Logger.getLogger(ReporteNotasDataSource.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("EN DATASOURCE"+e);
        }
        return valor;
    }
}

