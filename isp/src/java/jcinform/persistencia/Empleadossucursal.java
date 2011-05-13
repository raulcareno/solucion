/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jcinform.persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Familia Jadan Cahueñ
 */
@Entity
@Table(name = "empleadossucursal")
@NamedQueries({
    @NamedQuery(name = "Empleadossucursal.findAll", query = "SELECT e FROM Empleadossucursal e")})
public class Empleadossucursal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "empleados", referencedColumnName = "codigo")
    @ManyToOne
    private Empleados empleados;
    @JoinColumn(name = "sucursal", referencedColumnName = "codigo")
    @ManyToOne
    private Sucursal sucursal;

    public Empleadossucursal() {
    }

    public Empleadossucursal(Integer codigo) {
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

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
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
        if (!(object instanceof Empleadossucursal)) {
            return false;
        }
        Empleadossucursal other = (Empleadossucursal) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Empleadossucursal[codigo=" + codigo + "]";
    }

}
