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
public class ReporteRecordDataSource implements JRDataSource{
    private Iterator itrAlumnos;
     private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;

    public ReporteRecordDataSource(List lista) {
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
           valor = nodo.getEstudiante().getApellido()+" "+nodo.getEstudiante().getNombre();
        }else    if ("curso".equals(fieldName)) {
           valor = nodo.getCurso()+"";
        }else    if ("p1".equals(fieldName)) {
            valor = nodo.getP1();
        }else    if ("p2".equals(fieldName)) {
            valor = nodo.getP2();
        }else    if ("p3".equals(fieldName)) {
            valor = nodo.getP3();
        }else    if ("p4".equals(fieldName)) {
            valor = nodo.getP4();
        }else    if ("p5".equals(fieldName)) {
            valor = nodo.getP5();
        }else    if ("p6".equals(fieldName)) {
            valor = nodo.getP6();
        }else    if ("d1".equals(fieldName)) {
            valor = nodo.getD1();
        }else    if ("d2".equals(fieldName)) {
            valor = nodo.getD2();
        }else    if ("d3".equals(fieldName)) {
            valor = nodo.getD3();
        }else    if ("d4".equals(fieldName)) {
            valor = nodo.getD4();
        }else    if ("d5".equals(fieldName)) {
            valor = nodo.getD5();
        }else    if ("d6".equals(fieldName)) {
            valor = nodo.getD6();
        } else    if ("promedio".equals(fieldName)) {
            valor = nodo.getPromedio2();
        } else    if ("disciplina".equals(fieldName)) {
            valor = nodo.getDisciplina2();
        } else    if ("promedioFinal".equals(fieldName)) {
           valor = nodo.getPromedioFinal();
        }else    if ("disciplinaFinal".equals(fieldName)) {
           valor = nodo.getDisciplinaFinal();
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
