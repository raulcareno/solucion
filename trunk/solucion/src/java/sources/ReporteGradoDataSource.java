package sources;

import bean.NotaCollection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
 

/**
 *
 * @author GEOVANNY
 */
public class ReporteGradoDataSource implements JRDataSource{
    private Iterator itrAlumnos;
     private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;
    private ArrayList mensajes = new ArrayList();

    public ReporteGradoDataSource(List lista) {
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
       NotaCollection  nodo = (NotaCollection) valorAtual;
        String fieldName = campo.getName();

        if ("nombres".equals(fieldName)) {
            valor = nodo.getEstudiante();
        }else if ("especialidad".equals(fieldName)) {
            valor = nodo.getMatriculas().getCurso().getEspecialidad().getDescripcion();
        }else if ("numero".equals(fieldName)) {
             String codigo = nodo.getNoActa();
                while(codigo.length()<4){
                    codigo = "0"+codigo;
                }
            valor = codigo;
            
        }else if ("fecha".equals(fieldName)) {
            valor =nodo.getFecha();
        }else if ("materia".equals(fieldName)){
            valor =nodo.getMateria();
        }else if ("valor".equals(fieldName)) {
            valor =nodo.getNota();
        }else if ("promedio".equals(fieldName)) {
            valor = nodo.getNota();
        }else if ("matricula".equals(fieldName)) {
            valor = nodo.getMatriculas().getCodigomat();
        }else if ("estudiante".equals(fieldName)) {
            valor = nodo.getMatriculas().getEstudiante().getCodigoest()+"";
        }else if ("estudiantenombre".equals(fieldName)) {
            valor = nodo.getEstudiante();
        }else if ("genero".equals(fieldName)) {
            valor = nodo.getMatriculas().getEstudiante().getGenero();
        }else if ("cabecera1".equals(fieldName)) {
            valor = nodo.getCabecera1();
        }else if ("cabecera2".equals(fieldName)) {
            valor = nodo.getCabecera2();
        }else if ("pie1".equals(fieldName)) {
            valor = nodo.getPie1();
        }else if ("pie2".equals(fieldName)) {
            valor = nodo.getPie2();
        }else if ("jornada".equals(fieldName)) {
            valor = nodo.getMatriculas().getCurso().getPeriodo().getSeccion().getDescripcion();
        }else if ("car1".equals(fieldName)) {
            valor = nodo.getCar1();
        }else if ("car2".equals(fieldName)) {
            valor = nodo.getCar2();
        }else if ("car3".equals(fieldName)) {
            valor = nodo.getCar3();
        }else if ("car4".equals(fieldName)) {
            valor = nodo.getCar4();
        }else if ("car5".equals(fieldName)) {
            valor = nodo.getCar5();
        }else if ("car6".equals(fieldName)) {
            valor = nodo.getCar6();
        }else if ("car7".equals(fieldName)) {
            valor = nodo.getCar7();
        }else if ("car8".equals(fieldName)) {
            valor = nodo.getCar8();
        }else if ("nom1".equals(fieldName)) {
            valor = nodo.getNom1();
        }else if ("nom2".equals(fieldName)) {
            valor = nodo.getNom2();
        }else if ("nom3".equals(fieldName)) {
            valor = nodo.getNom3();
        }else if ("nom4".equals(fieldName)) {
            valor = nodo.getNom4();
        }else if ("nom5".equals(fieldName)) {
            valor = nodo.getNom5();
        }else if ("nom6".equals(fieldName)) {
            valor = nodo.getNom6();
        }else if ("nom7".equals(fieldName)) {
            valor = nodo.getNom7();
        }else if ("nom8".equals(fieldName)) {
            valor = nodo.getNom8();
        }else if ("titulo".equals(fieldName)) {
            valor = nodo.getTitulo();
        }

        return valor;
    }
}
