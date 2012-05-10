
package sources;

 
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.MateriaProfesor;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;


/**
 *
 * @author GEOVANNY
 */
public class PromediosDataSource implements JRDataSource{
    private Iterator itrAlumnos;
     private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;

    public PromediosDataSource(List lista) {
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
        NotasPromedios  nodo = (NotasPromedios) valorAtual;
        String fieldName = campo.getName();
        if ("aniolectivo".equals(fieldName)) {
            valor = nodo.getAnioLectivo();
        }else if ("aniobasica".equals(fieldName)) {
            valor = nodo.getAnioBasica();
        }else if ("cantidad".equals(fieldName)) {
            valor = nodo.getCantidad();
        }else if ("rango".equals(fieldName)) {
            valor = nodo.getRango();
        } else if ("subtitulo".equals(fieldName)) {
            valor = nodo.getSubtitulo();
        }else if ("titulo".equals(fieldName)) {
            valor = nodo.getTitulo();
        }   

        return valor;
    }
 
}
