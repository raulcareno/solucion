package sources;

public class PlanificacionCollection {

    public PlanificacionCollection() {
    }
    String tipo;
    String detalle;
    Integer planificacion;

    public PlanificacionCollection(String tipo, String detalle, Integer planificacion) {
        this.tipo = tipo;
        this.detalle = detalle;
        this.planificacion = planificacion;
    }

    
    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getPlanificacion() {
        return planificacion;
    }

    public void setPlanificacion(Integer planificacion) {
        this.planificacion = planificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
}
