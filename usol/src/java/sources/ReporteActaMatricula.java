package sources;
 
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Detalles;
import jcinform.persistencia.MateriasMatricula;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
//import persistencia.Detallefactura;

/**
 *
 * @author Francisco
 */
public class ReporteActaMatricula implements JRDataSource{

   private Iterator itrAlumnos;
   private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;

    public ReporteActaMatricula(List lista) {
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
        MateriasMatricula nodo = (MateriasMatricula) valorAtual;

        if ("cedula".equals(campo.getName())) {
            valor = nodo.getIdMatriculas().getIdEstudiantes().getIdEstudiantes();
        }else if ("numero".equals(campo.getName())) {
            valor = nodo.getIdMatriculas().getNumero();
        }else if ("fecha_inicio".equals(campo.getName())) {
            valor = nodo.getIdMatriculas().getIdPeriodos().getFechaInicio();
        }else if ("fecha_fin".equals(campo.getName())) {
            valor = nodo.getIdMatriculas().getIdPeriodos().getFechaFin();
        }else if ("apellido_paterno".equals(campo.getName())) {
            valor = nodo.getIdMatriculas().getIdEstudiantes().getApellidoPaterno();
        }else if ("apellido_materno".equals(campo.getName())) {
            valor = nodo.getIdMatriculas().getIdEstudiantes().getApellidoMaterno();
        }else if ("nombre".equals(campo.getName())) {
            valor = nodo.getIdMatriculas().getIdEstudiantes().getNombre();
        }else if ("materia".equals(campo.getName())) {
            valor = nodo.getIdMaterias().getNombre();
        }else if ("carrera".equals(campo.getName())) {
            valor = nodo.getIdMatriculas().getIdCarreras().getNombre()+"";
        }else if ("nivel".equals(campo.getName())) {
             valor="";
        }else if ("arrastre".equals(campo.getName())) {
            valor = nodo.getValor();
        }else if ("numero_creditos".equals(campo.getName())) {
            valor = nodo.getNoCreditos();
        }else if ("numero_horas".equals(campo.getName())) {
            valor = nodo.getNoHoras();
        }else if ("valor".equals(campo.getName())) {
            valor = nodo.getValorCredito();
        }else if ("valorTotal".equals(campo.getName())) {
            valor = nodo.getValorCreditoTotal();
        }else if ("convalidada".equals(campo.getName())) {
            try {
            valor = nodo.getConvalidado()?"SI":"NO";    
            } catch (Exception e) {
                valor = "NO";
            }
            
        }else if ("pagada".equals(campo.getName())) {
            try {
                valor = nodo.getPagado()?"SI":"NO";    
            } catch (Exception e) {
                valor = "NO";
            }
            
            
        }else if ("mes".equals(campo.getName())) {
             valor = "";    

        }
 
        return valor;
    }
}

