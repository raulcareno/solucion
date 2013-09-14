 
package sources;
import bean.NotaCollection;
import java.math.BigDecimal;
import java.util.ArrayList;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Global;
import jcinform.persistencia.MateriaProfesor;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Sistemacalificacion;
/**
 *
 * @author geovanny
 */
public class Nota  implements Comparable {
public Nota(){}

private Matriculas matricula;
private Estudiantes estudiante;
private Global materia;
private MateriaProfesor mprofesor;
private Sistemacalificacion sistema;
private Object nota;
private String tipoSi;
private String sistemaSi;

private Object optativa;

private Object promedioFinal;
private Object disciplinaFinal;

private Double aprovechamiento;
private Double sumatoria;
private Integer diasAsistidos;
private Double disciplina;
private String disciplinaS;

//para examenes de grado
private Cursos curso;
private String profesor;
private String observacion;

private String firma1,firma2,firma3;
private String cargo1,cargo2,cargo3;
private Integer contador;
private BigDecimal p1;
private BigDecimal p2;
private BigDecimal p3;
private BigDecimal p4;
private BigDecimal p5;
private BigDecimal p6;
private BigDecimal d1;
private BigDecimal d2;
private BigDecimal d3;
private BigDecimal d4;
private BigDecimal d5;
private BigDecimal d6;
private BigDecimal promedio2;
private BigDecimal disciplina2;

    public MateriaProfesor getMprofesor() {
        return mprofesor;
    }





    public void setMprofesor(MateriaProfesor mprofesor) {
        this.mprofesor = mprofesor;
    }


    public Global getMateria() {
        return materia;
    }

    public void setMateria(Global materia) {
        this.materia = materia;
    }

    public Matriculas getMatricula() {
        return matricula;
    }

    public void setMatricula(Matriculas matricula) {
        this.matricula = matricula;
    }

    public Object getNota() {
        return nota;
    }

    public void setNota(Object nota) {
        this.nota = nota;
    }

    public Sistemacalificacion getSistema() {
        return sistema;
    }

    public void setSistema(Sistemacalificacion sistema) {
        this.sistema = sistema;
    }

    public ArrayList<NotaCollection> notas;

    public ArrayList<NotaCollection> getNotas() {
        return notas;
    }

    public void setNotas(ArrayList<NotaCollection> notas) {
        this.notas = notas;
    }

     public ArrayList<NotaCollection> faltas;

    public ArrayList<NotaCollection> getFaltas() {
        return faltas;
    }

    public void setFaltas(ArrayList<NotaCollection> faltas) {
        this.faltas = faltas;
    }
    public ArrayList<NotaCollection> autoevaluacion;

    public ArrayList<NotaCollection> getAutoevaluacion() {
        return autoevaluacion;
    }

    public void setAutoevaluacion(ArrayList<NotaCollection> autoevaluacion) {
        this.autoevaluacion = autoevaluacion;
    }
    

    public String getCargo1() {
        return cargo1;
    }

    public void setCargo1(String cargo1) {
        this.cargo1 = cargo1;
    }

    public String getCargo2() {
        return cargo2;
    }

    public void setCargo2(String cargo2) {
        this.cargo2 = cargo2;
    }

    public String getCargo3() {
        return cargo3;
    }

    public void setCargo3(String cargo3) {
        this.cargo3 = cargo3;
    }

    public String getFirma1() {
        return firma1;
    }

    public void setFirma1(String firma1) {
        this.firma1 = firma1;
    }

    public String getFirma2() {
        return firma2;
    }

    public void setFirma2(String firma2) {
        this.firma2 = firma2;
    }

    public String getFirma3() {
        return firma3;
    }

    public void setFirma3(String firma3) {
        this.firma3 = firma3;
    }

    public Cursos getCurso() {
        return curso;
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public Double getAprovechamiento() {
        return aprovechamiento;
    }

    public void setAprovechamiento(Double aprovechamiento) {
        this.aprovechamiento = aprovechamiento;
    }

    public Object getOptativa() {
        return optativa;
    }

    public void setOptativa(Object optativa) {
        this.optativa = optativa;
    }

    public Double getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Double disciplina) {
        this.disciplina = disciplina;
    }

    public Object getDisciplinaFinal() {
        return disciplinaFinal;
    }

    public void setDisciplinaFinal(Object disciplinaFinal) {
        this.disciplinaFinal = disciplinaFinal;
    }

    public Object getPromedioFinal() {
        return promedioFinal;
    }

    public void setPromedioFinal(Object promedioFinal) {
        this.promedioFinal = promedioFinal;
    }

    public BigDecimal getD1() {
        return d1;
    }

    public void setD1(BigDecimal d1) {
        this.d1 = d1;
    }

    public BigDecimal getD2() {
        return d2;
    }

    public void setD2(BigDecimal d2) {
        this.d2 = d2;
    }

    public BigDecimal getD3() {
        return d3;
    }

    public void setD3(BigDecimal d3) {
        this.d3 = d3;
    }

    public BigDecimal getD4() {
        return d4;
    }

    public void setD4(BigDecimal d4) {
        this.d4 = d4;
    }

    public BigDecimal getD5() {
        return d5;
    }

    public void setD5(BigDecimal d5) {
        this.d5 = d5;
    }

    public BigDecimal getD6() {
        return d6;
    }

    public void setD6(BigDecimal d6) {
        this.d6 = d6;
    }

    public BigDecimal getP1() {
        return p1;
    }

    public void setP1(BigDecimal p1) {
        this.p1 = p1;
    }

    public BigDecimal getP2() {
        return p2;
    }

    public void setP2(BigDecimal p2) {
        this.p2 = p2;
    }

    public BigDecimal getP3() {
        return p3;
    }

    public void setP3(BigDecimal p3) {
        this.p3 = p3;
    }

    public BigDecimal getP4() {
        return p4;
    }

    public void setP4(BigDecimal p4) {
        this.p4 = p4;
    }

    public BigDecimal getP5() {
        return p5;
    }

    public void setP5(BigDecimal p5) {
        this.p5 = p5;
    }

    public BigDecimal getP6() {
        return p6;
    }

    public void setP6(BigDecimal p6) {
        this.p6 = p6;
    }

    public Estudiantes getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiantes estudiante) {
        this.estudiante = estudiante;
    }

    public BigDecimal getDisciplina2() {
        return disciplina2;
    }

    public void setDisciplina2(BigDecimal disciplina2) {
        this.disciplina2 = disciplina2;
    }

    public BigDecimal getPromedio2() {
        return promedio2;
    }

    public void setPromedio2(BigDecimal promedio2) {
        this.promedio2 = promedio2;
    }

    public Integer getDiasAsistidos() {
        return diasAsistidos;
    }

    public void setDiasAsistidos(Integer diasAsistidos) {
        this.diasAsistidos = diasAsistidos;
    }

    public Double getSumatoria() {
        return sumatoria;
    }

    public void setSumatoria(Double sumatoria) {
        this.sumatoria = sumatoria;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getSistemaSi() {
        return sistemaSi;
    }

    public void setSistemaSi(String sistemaSi) {
        this.sistemaSi = sistemaSi;
    }

    public String getTipoSi() {
        return tipoSi;
    }

    public void setTipoSi(String tipoSi) {
        this.tipoSi = tipoSi;
    }

    public String getDisciplinaS() {
        return disciplinaS;
    }

    public void setDisciplinaS(String disciplinaS) {
        this.disciplinaS = disciplinaS;
    }
 
    

    @Override
    public int compareTo(Object o) {
        Nota persona = (Nota) o;
        if (this.cargo1.compareToIgnoreCase(persona.cargo1) == 0) {
            return 0;
        } else {
             return this.cargo1.compareToIgnoreCase(persona.cargo1);
        }
        

    }     


}
