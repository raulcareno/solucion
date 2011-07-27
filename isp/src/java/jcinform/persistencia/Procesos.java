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
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Geovanny Jadan
 */
@Entity
@Table(name = "procesos")
@NamedQueries({ })
public class Procesos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "fechastring")
    private String fechastring;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "ejecutado")
    private Boolean ejecutado;
    @Column(name = "fechaejecutado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaejecutado;
    @Lob
    @Column(name = "problemas")
    private String problemas;
    @Column(name = "empleado")
    private String empleado;
    

    public Procesos() {
    }

    public Procesos(String fechastring) {
        this.fechastring = fechastring;
    }

    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getEjecutado() {
        return ejecutado;
    }

    public void setEjecutado(Boolean ejecutado) {
        this.ejecutado = ejecutado;
    }

    public Date getFechaejecutado() {
        return fechaejecutado;
    }

    public void setFechaejecutado(Date fechaejecutado) {
        this.fechaejecutado = fechaejecutado;
    }

    public String getProblemas() {
        return problemas;
    }

    public void setProblemas(String problemas) {
        this.problemas = problemas;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getFechastring() {
        return fechastring;
    }

    public void setFechastring(String fechastring) {
        this.fechastring = fechastring;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fechastring != null ? fechastring.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Procesos)) {
            return false;
        }
        Procesos other = (Procesos) object;
        if ((this.fechastring == null && other.fechastring != null) || (this.fechastring != null && !this.fechastring.equals(other.fechastring))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return fechastring;
    }
    
}
