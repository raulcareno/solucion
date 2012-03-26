/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jcinform.persistencia;

 
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Familia Jadan Cahueñ
 */
@Entity
@Table(name = "soporte")
@NamedQueries({
    @NamedQuery(name = "Soporte.findAll", query = "SELECT s FROM Soporte s")})
public class Soporte implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "fechaorden")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaorden;
    
    @Column(name = "fechafin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafin;
    
    @Column(name = "fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodifica;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Lob
    @Column(name = "observacion2")
    private String observacion2;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "actividad")
    private Integer actividad;
    @Lob
    @Column(name = "observacion3")
    private String observacion3;
    
    @Column(name = "direccion")
    private String direccion;
    
    @Column(name = "telefono")
    private String telefono;
    
    @Column(name = "auditoria")
    private String auditoria;
    @Column(name = "generada")
    private Boolean generada = false;
    @Column(name = "escalar")
    private Boolean escalar = false;
    @Column(name = "revisoescalar")
    private Boolean revisoescalar = false;
    @Transient
    String clientesNombre;
    
    @Transient
    String empleadosNombre;
      
    @Transient
    String tecnicoNombre;
    
    @Column(name = "noorden")
    private Integer noorden;
    @JoinColumn(name = "clientes", referencedColumnName = "codigo")
    @ManyToOne
    private Clientes clientes;
    
    @JoinColumn(name = "empleados", referencedColumnName = "codigo")
    @ManyToOne
    private Empleados empleados;
    @JoinColumn(name = "empleadoescalar", referencedColumnName = "codigo")
    @ManyToOne
    private Empleados empleadoescalar;
    
    @JoinColumn(name = "tecnico", referencedColumnName = "codigo")
    @ManyToOne
    private Empleados tecnico;

    public Soporte() {
    }

    public Soporte(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getObservacion2() {
        return observacion2;
    }

    public void setObservacion2(String observacion2) {
        this.observacion2 = observacion2;
    }

    public String getObservacion3() {
        return observacion3;
    }

    public void setObservacion3(String observacion3) {
        this.observacion3 = observacion3;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Empleados getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleados empleados) {
        this.empleados = empleados;
    }

    public Boolean getGenerada() {
        return generada;
    }

    public void setGenerada(Boolean generada) {
        this.generada = generada;
    }

    public Integer getNoorden() {
        return noorden;
    }

    public void setNoorden(Integer noorden) {
        this.noorden = noorden;
    }

    public Empleados getTecnico() {
        return tecnico;
    }

    public void setTecnico(Empleados tecnico) {
        this.tecnico = tecnico;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public Date getFechamodifica() {
        return fechamodifica;
    }

    public void setFechamodifica(Date fechamodifica) {
        this.fechamodifica = fechamodifica;
    }

    public Date getFechaorden() {
        return fechaorden;
    }

    public void setFechaorden(Date fechaorden) {
        this.fechaorden = fechaorden;
    }

    public Integer getActividad() {
        return actividad;
    }

    public void setActividad(Integer actividad) {
        this.actividad = actividad;
    }

    public String getClientesNombre() {
        if(clientes!=null)
        return clientes.getApellidos()+" "+clientes.getNombres();
        else
            return "";
                
    }

    public void setClientesNombre(String clientesNombre) {
        this.clientesNombre = clientesNombre;
    }                                                                                                                                                                                                                                                                                                                                                                                                                                 

    public String getEmpleadosNombre() {
        if(empleados!=null)
        return empleados.getApellidos()+" "+empleados.getNombres();
        else
            return "";
    }

    public void setEmpleadosNombre(String empleadosNombre) {
        this.empleadosNombre = empleadosNombre;
    }

    public String getTecnicoNombre() {
        if(tecnico!=null)
            return tecnico.getApellidos()+" "+tecnico.getNombres();
        else
            return "";
    }

    public void setTecnicoNombre(String tecnicoNombre) {
        this.tecnicoNombre = tecnicoNombre;
    }

        public String getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(String auditoria) {
        this.auditoria = auditoria;
    }

    public Empleados getEmpleadoescalar() {
        return empleadoescalar;
    }

    public void setEmpleadoescalar(Empleados empleadoescalar) {
        this.empleadoescalar = empleadoescalar;
    }

    public Boolean getEscalar() {
        return escalar;
    }

    public void setEscalar(Boolean escalar) {
        this.escalar = escalar;
    }

    public Boolean getRevisoescalar() {
        return revisoescalar;
    }

    public void setRevisoescalar(Boolean revisoescalar) {
        this.revisoescalar = revisoescalar;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Soporte)) {
            return false;
        }
        Soporte other = (Soporte) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Soporte[codigo=" + codigo + "]";
    }

}
