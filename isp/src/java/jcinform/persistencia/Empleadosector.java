/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.persistencia;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author inform
 */
@Entity
@Table(name = "empleadosector")
@NamedQueries({
    @NamedQuery(name = "Empleadosector.findAll", query = "SELECT e FROM Empleadosector e"),
    @NamedQuery(name = "Empleadosector.findByCodigo", query = "SELECT e FROM Empleadosector e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "Empleadosector.findByEstado", query = "SELECT e FROM Empleadosector e WHERE e.estado = :estado")})
public class Empleadosector implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "estado")
    private Boolean estado;
 @JoinColumn(name = "empleados", referencedColumnName = "codigo")
    @ManyToOne
    private Empleados empleados;
    public Empleadosector() {
    }

    @JoinColumn(name = "sector", referencedColumnName = "codigo")
    @ManyToOne
    private Sector sector;
    public Empleadosector(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Empleados getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleados empleados) {
        this.empleados = empleados;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
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
        if (!(object instanceof Empleadosector)) {
            return false;
        }
        Empleadosector other = (Empleadosector) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Empleadosector[ codigo=" + codigo + " ]";
    }
    
}
