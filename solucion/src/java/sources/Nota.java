 
package sources;
import bean.NotaCollection;
import java.util.ArrayList;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Global;
import jcinform.persistencia.MateriaProfesor;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Sistemacalificacion;
/**
 *
 * @author geovanny
 */
public class Nota {
public Nota(){}

private Matriculas matricula;
private Global materia;
private MateriaProfesor mprofesor;
private Sistemacalificacion sistema;
private Object nota;

private Object optativa;

private Object promedioFinal;
private Object disciplinaFinal;

private Double aprovechamiento;
private Double disciplina;

//para examenes de grado
private Cursos curso;
private String profesor;

private String firma1,firma2,firma3;
private String cargo1,cargo2,cargo3;
private Integer contador;

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


    
     


}