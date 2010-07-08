package sources;
import bean.NotaActaGeneral;
import bean.NotaDisciplina;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
/***
 * @author GEOVANNY
 ***/
public class ActaGeneralDataSource implements JRDataSource {
    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;
    public int year = 0;
    public int month = 0;
    public int day = 0;
    public ActaGeneralDataSource(List lista) {
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
        NotaActaGeneral nodo = (NotaActaGeneral) valorAtual;
        String fieldName = campo.getName();

        if ("nombres".equals(fieldName)) {
            valor = nodo.getMatricula().getEstudiante().getApellido()+" "+nodo.getMatricula().getEstudiante().getNombre();
        } else if ("cedula".equals(fieldName)) {
            valor = nodo.getMatricula().getEstudiante().getCedula();
        } else if ("curso".equals(fieldName)) {
            valor = nodo.getCurso();
        } else if ("f1".equals(fieldName)) {
            valor = nodo.getJ1();
        } else if ("f2".equals(fieldName)) {
            valor = nodo.getJ2();
        } else if ("f3".equals(fieldName)) {
            valor = nodo.getJ3();
        } else if ("f4".equals(fieldName)) {
            valor = nodo.getJ4();
        } else if ("f5".equals(fieldName)) {
            valor = nodo.getJ5();
        } else if ("f6".equals(fieldName)) {
            valor = nodo.getJ6();
        } else if ("f7".equals(fieldName)) {
            valor = nodo.getJ7();
        } else if ("f8".equals(fieldName)) {
            valor = nodo.getJ8();
        } else if ("f9".equals(fieldName)) {
            valor = nodo.getJ9();
        } else if ("f10".equals(fieldName)) {
            valor = nodo.getJ10();
        } else if ("n1".equals(fieldName)) {
            valor = nodo.getN1();
        } else if ("n2".equals(fieldName)) {
            valor = nodo.getN2();
        } else if ("n3".equals(fieldName)) {
            valor = nodo.getN3();
        } else if ("n4".equals(fieldName)) {
            valor = nodo.getN4();
        } else if ("n5".equals(fieldName)) {
            valor = nodo.getN5();
        } else if ("n6".equals(fieldName)) {
            valor = nodo.getN6();
        } else if ("n7".equals(fieldName)) {
            valor = nodo.getN7();
        } else if ("n8".equals(fieldName)) {
            valor = nodo.getN8();
        } else if ("n9".equals(fieldName)) {
            valor = nodo.getN9();
        } else if ("n10".equals(fieldName)) {
            valor = nodo.getN10();
        } else if ("n11".equals(fieldName)) {
            valor = nodo.getN11();
        } else if ("n12".equals(fieldName)) {
            valor = nodo.getN12();
        } else if ("n13".equals(fieldName)) {
            valor = nodo.getN13();
        } else if ("n14".equals(fieldName)) {
            valor = nodo.getN14();
        } else if ("n15".equals(fieldName)) {
            valor = nodo.getN15();
        } else if ("n16".equals(fieldName)) {
            valor = nodo.getN16();
        } else if ("n17".equals(fieldName)) {
            valor = nodo.getN17();
        } else if ("n18".equals(fieldName)) {
            valor = nodo.getN18();
        } else if ("n19".equals(fieldName)) {
            valor = nodo.getN19();
        } else if ("n20".equals(fieldName)) {
            valor = nodo.getN20();
        } else if ("profesores".equals(fieldName)) {
            valor = nodo.getProfesores();
        } else if ("inspectores".equals(fieldName)) {
            valor = nodo.getInspector();
        } else if ("final".equals(fieldName)) {
            valor = nodo.getFinali();
        } else if ("estado".equals(fieldName)) {
            valor = nodo.getEstado();
        }else if ("equivalencia".equals(fieldName)) {
            valor = nodo.getEquivalencia();
        }
        return valor;
    }
}
