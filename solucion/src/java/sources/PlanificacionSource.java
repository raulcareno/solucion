 
package sources;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author geovanny
 */
public class PlanificacionSource   {
public PlanificacionSource(){}
  
   String tipo1,tipo2,tipo3,tipo4,tipo5;
   String detalle1,detalle2,detalle3,detalle4,detalle5;
	  String materia,   curso ,  duracion , 
	  eintegrador , 	  eaprendizaje  ,
	  etransversal ,	  objetivos,  	  bloques;
	  Date finicio,ffin;
	  Integer planificacion;
	  ArrayList<PlanificacionCollection> listaDetalles;

    public String getBloques() {
        return bloques;
    }

    public void setBloques(String bloques) {
        this.bloques = bloques;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDetalle1() {
        return detalle1;
    }

    public void setDetalle1(String detalle1) {
        this.detalle1 = detalle1;
    }

    public String getDetalle2() {
        return detalle2;
    }

    public void setDetalle2(String detalle2) {
        this.detalle2 = detalle2;
    }

    public String getDetalle3() {
        return detalle3;
    }

    public void setDetalle3(String detalle3) {
        this.detalle3 = detalle3;
    }

    public String getDetalle4() {
        return detalle4;
    }

    public void setDetalle4(String detalle4) {
        this.detalle4 = detalle4;
    }

    public String getDetalle5() {
        return detalle5;
    }

    public void setDetalle5(String detalle5) {
        this.detalle5 = detalle5;
    }

    public String getTipo1() {
        return tipo1;
    }

    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    public String getTipo2() {
        return tipo2;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }

    public String getTipo3() {
        return tipo3;
    }

    public void setTipo3(String tipo3) {
        this.tipo3 = tipo3;
    }

    public String getTipo4() {
        return tipo4;
    }

    public void setTipo4(String tipo4) {
        this.tipo4 = tipo4;
    }

    public String getTipo5() {
        return tipo5;
    }

    public void setTipo5(String tipo5) {
        this.tipo5 = tipo5;
    }

    

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getEaprendizaje() {
        return eaprendizaje;
    }

    public void setEaprendizaje(String eaprendizaje) {
        this.eaprendizaje = eaprendizaje;
    }

    public String getEintegrador() {
        return eintegrador;
    }

    public void setEintegrador(String eintegrador) {
        this.eintegrador = eintegrador;
    }

    public String getEtransversal() {
        return etransversal;
    }

    public void setEtransversal(String etransversal) {
        this.etransversal = etransversal;
    }

    public Date getFfin() {
        return ffin;
    }

    public void setFfin(Date ffin) {
        this.ffin = ffin;
    }

    public Date getFinicio() {
        return finicio;
    }

    public void setFinicio(Date finicio) {
        this.finicio = finicio;
    }

    public ArrayList<PlanificacionCollection> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(ArrayList<PlanificacionCollection> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public Integer getPlanificacion() {
        return planificacion;
    }

    public void setPlanificacion(Integer planificacion) {
        this.planificacion = planificacion;
    }

    
      

}
