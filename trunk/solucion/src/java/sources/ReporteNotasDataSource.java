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
                if(nodo.getNota().toString().contains(".0")){
                    String vale = nodo.getNota().toString();
                    if(!vale.contains(".01") && !vale.contains(".02") && !vale.contains(".03") && !vale.contains(".04") &&
                            !vale.contains(".06") && !vale.contains(".07") && !vale.contains(".08") && !vale.contains(".05") &&
                            !vale.contains(".09") ){
                        nodo.setNota(nodo.getNota().toString().replace(".0",""));
                    }
                }
              String vale = nodo.getNota().toString();
              if(vale.contains("0") || vale.contains("1") || vale.contains("2") || vale.contains("3") || vale.contains("4") || vale.contains("5") ||
                      vale.contains("6") || vale.contains("7") || vale.contains("8") || vale.contains("9") ){
                   String codigo = vale;
                      while(codigo.length()<2){
                            codigo = "0"+codigo;
                        }
                    nodo.setNota(codigo);
              }

              
            valor = nodo.getNota().toString();
            if(nodo.getNota().toString().equals("00"))
                valor = "";
//            if(valor.equals(""))
//                valor = "0";
//            System.out.println("NOTA: "+valor);
//            if(nodo.getNota().toString().matches("[0-9]"))
//                    System.out.println("**-CONTIENE NUMEROS");
//            if(nodo.getNota().toString().matches("[A-Z]"))
//                System.out.println("CONTIENE LETRAS**");

            //valor = new Double(nodo.getNota().toString());
            
//                System.out.println("NOTA: "+valor);

            } catch (Exception e) {
//                System.out.println("fieldcargar: "+e);
            }

        }else
            if ("estudiante".equals(fieldName)) {
           //valor = nodo.getMatricula().getEstudiante().getApellido()+" "+ nodo.getMatricula().getEstudiante().getNombre();

           String estado = (nodo.getMatricula().getEstado().equals("Retirado")?"(R)":(nodo.getMatricula().getEstado().equals("Emitir Pase")?"(PE)":""));
            valor = nodo.getMatricula().getEstudiante().getApellido() + " "+ nodo.getMatricula().getEstudiante().getNombre()+" "+estado;
//            System.out.println("estudiante: "+valor);
        }else    if ("matricula".equals(fieldName)) {
           valor = nodo.getMatricula().getCodigomat();
        }else if ("curso".equals(fieldName)) {
           valor = nodo.getMatricula().getCurso().getDescripcion()+" "+nodo.getMatricula().getCurso().getEspecialidad()+" "+nodo.getMatricula().getCurso().getParalelo();
        }else if ("cursos".equals(fieldName)) {
            try{
           valor = nodo.getCurso().getDescripcion()+" "+nodo.getCurso().getEspecialidad()+" "+nodo.getCurso().getParalelo();
                }catch(Exception a){
                    System.out.println("CURSO AGREGADO TOTALIZADO"+a);
                }
        }else if ("secuencia".equals(fieldName)) {
            try{
                valor = nodo.getCurso().getSecuencia();
                }catch(Exception a){
                    System.out.println("CURSO AGREGADO TOTALIZADO"+a);
                }
        }else if ("regimen".equals(fieldName)) {
           valor = nodo.getMatricula().getCurso().getPeriodo().getRegimen();
        }else if ("jornada".equals(fieldName)) {
           valor = nodo.getMatricula().getCurso().getPeriodo().getSeccion().getDescripcion();
        }else if ("materia".equals(fieldName)) {
           valor = nodo.getMateria().getDescripcion();
     
        }else if ("profesor".equals(fieldName)) {
           valor = nodo.getMprofesor().getEmpleado().getApellidos() +" "+nodo.getMprofesor().getEmpleado().getNombres()+" ";
           if(valor.equals("")){
           valor = null;
           }
        }else if ("optativa".equals(fieldName)) {
           valor = nodo.getMprofesor().getEmpleado().getApellidos() +" "+nodo.getMprofesor().getEmpleado().getNombres()+" ";
        }else if ("aprovechamiento".equals(fieldName)) {
           valor = nodo.getAprovechamiento();
        }else if ("disciplina".equals(fieldName)) {
           valor = nodo.getDisciplina();
        }
        else if ("observacion".equals(fieldName)) {
            try{
                if(nodo.getMatricula().getEstado().equals("Retirado")){
                    valor = ("Retirado: "+nodo.getMatricula().getFecharet().toLocaleString().substring(0, 10))+" "+nodo.getMatricula().getObservacion();
                }else if(nodo.getMatricula().getEstado().equals("Emitir Pase")){
                    valor = "Pase Emitido: "+nodo.getMatricula().getFecharet().toLocaleString().substring(0, 10)+" "+nodo.getMatricula().getObservacion();
                }else if(nodo.getMatricula().getEstado().equals("Recibir Pase")){
                    valor = "Pase Recibido: "+nodo.getMatricula().getFechamat().toLocaleString().substring(0, 10)+" "+nodo.getMatricula().getObservacion();
                }else{
                    valor="";
                }
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

