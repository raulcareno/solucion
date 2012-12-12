/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sources;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author GEOVANNY
 */
public class ReporteNoasLibretaDataSource implements JRDataSource{
    private Iterator itrAlumnos;
     private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;

    public ReporteNoasLibretaDataSource(List lista) {
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
        if ("estudiante".equals(fieldName)) {
           valor = nodo.getMatricula().getEstudiante().getApellido()+" "+ nodo.getMatricula().getEstudiante().getNombre();
        }else    if ("matricula".equals(fieldName)) {
           valor = nodo.getMatricula().getCodigomat();
        }else    if ("observacion".equals(fieldName)) {
           valor = nodo.getObservacion();
        }else    if ("fotoestudiante".equals(fieldName)) {
            if(nodo.getMatricula().getExtension()!=null){
            valor = nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getFotos()+""+nodo.getMatricula().getCodigomat()+"."+nodo.getMatricula().getExtension();
            }else{
            }
           
        }else if ("curso".equals(fieldName)) {
           valor = nodo.getMatricula().getCurso().getDescripcion()+" "+nodo.getMatricula().getCurso().getEspecialidad()+" "+nodo.getMatricula().getCurso().getParalelo();
        }else if ("sello".equals(fieldName)) {
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
        }else if ("listaFaltas".equals(fieldName)){
            valor = new JRBeanCollectionDataSource(nodo.getFaltas());
        }else if ("listaAutoevaluacion".equals(fieldName)){
            try {
                valor = new JRBeanCollectionDataSource(nodo.getAutoevaluacion());    
            } catch (Exception e) {
            }
        }else    if ("promedioFinal".equals(fieldName)) {
           valor = nodo.getPromedioFinal();
        }else    if ("disciplinaFinal".equals(fieldName)) {
           valor = nodo.getDisciplinaFinal();
        }else    if ("firma1".equals(fieldName)) {
           valor = nodo.getFirma1();
        }else    if ("firma2".equals(fieldName)) {
           valor = nodo.getFirma2();
        }else    if ("firma3".equals(fieldName)) {
           valor = nodo.getFirma3();
        }else    if ("cargo1".equals(fieldName)) {
           valor = nodo.getCargo1();
        }else    if ("cargo2".equals(fieldName)) {
           valor = nodo.getCargo2();
        }else    if ("cargo3".equals(fieldName)) {
           valor = nodo.getCargo3();
        }
} catch (Exception e) {
            System.out.println("EN DATASOURCE"+e);
        }
        return valor;
    }
}










///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package sources;
//
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.Iterator;
//import java.util.List;
//import net.sf.jasperreports.engine.JRDataSource;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JRField;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//
///**
// *
// * @author GEOVANNY
// */
//public class ReporteNotasDataSource implements JRDataSource{
//    private Iterator itrAlumnos;
//     private Iterator itrNodos;
//   private Object valorAtual;
//   private boolean irParaProximoAlumno = true;
//
//    public ReporteNotasDataSource(List lista) {
//        super();
//        this.itrNodos = lista.iterator();
//    }
//
//    public boolean next() throws JRException {
//        itrAlumnos = itrNodos;
//        valorAtual = itrAlumnos.hasNext() ? itrAlumnos.next() : null;
//        irParaProximoAlumno = (valorAtual != null);
//        return irParaProximoAlumno;
//    }
//    /*
//     * (non-Javadoc)
//     *
//     * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
//     */
//    public Object getFieldValue(JRField campo) throws JRException {
//        Object valor = null;
//        Nota   nodo = (Nota) valorAtual;
//        try {
//            String fieldName = campo.getName();
//        if ("sistema".equals(fieldName)){
//            valor = nodo.getSistema().getAbreviatura();
//        }else if ("tipo".equals(fieldName)) {
//            valor = nodo.getSistema().getTrimestre().getDescripcion();
//        }else if ("nota".equals(fieldName)) {
//            valor = nodo.getNota();
//        }else if ("estudiante".equals(fieldName)) {
//           valor = nodo.getMatricula().getEstudiante().getApellido()+" "+ nodo.getMatricula().getEstudiante().getNombre();
//        }else if ("curso".equals(fieldName)) {
//           valor = nodo.getMatricula().getCurso().getDescripcion()+" "+nodo.getMatricula().getCurso().getEspecialidad()+" "+nodo.getMatricula().getCurso().getParalelo();
//        }else if ("materia".equals(fieldName)) {
//           valor = nodo.getMateria().getDescripcion();
//        }else if ("profesor".equals(fieldName)) {
//           valor = nodo.getMateria().getDescripcion();
//        }else if ("observacion".equals(fieldName)) {
//            try{
//                valor = (nodo.getMatricula().getEstado().equals("Retirado")?"Retirado:"+nodo.getMatricula().getFecharet().toLocaleString().substring(0, 10):"");
//            }catch(Exception e){
//               valor = (nodo.getMatricula().getEstado().equals("Retirado")?"Retirado":"");
//            }
//
//        }else if ("sello".equals(fieldName)) {
//
//
//           try{//FONDO PARA CARNET
//                byte[] bImage =nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getEscudo();
//                if(bImage!=null){
//                    InputStream is = new ByteArrayInputStream(bImage);
//                    valor = is;
//                }else{
//                }
//            }catch(Exception ex){
//                System.out.println("Error en foto:"+ex);
//            }
//        }else if ("listaNotas".equals(fieldName)){
//            valor = new JRBeanCollectionDataSource(nodo.getNotas());
//        }
//} catch (Exception e) {
//            System.out.println("EN DATASOURCE"+e);
//        }
//
//        return valor;
//    }
//}
//
//
//
//
//
//
//
//
