/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hibernate;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author geovanny
 */
@Entity
@Table(name = "productos")
@NamedQueries({@NamedQuery(name = "Productos.findAll", query = "SELECT p FROM Productos p"), @NamedQuery(name = "Productos.findByCodigo", query = "SELECT p FROM Productos p WHERE p.codigo = :codigo"), @NamedQuery(name = "Productos.findByDescripcion", query = "SELECT p FROM Productos p WHERE p.descripcion = :descripcion"), @NamedQuery(name = "Productos.findByValor", query = "SELECT p FROM Productos p WHERE p.valor = :valor"), @NamedQuery(name = "Productos.findByBien", query = "SELECT p FROM Productos p WHERE p.bien = :bien")})
public class Productos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "valor")
    private Long valor;
    @Column(name = "bien")
    private Boolean bien;
    

    public Productos() {
    }

    public Productos(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public Boolean getBien() {
        return bien;
    }

    public void setBien(Boolean bien) {
        this.bien = bien;
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
        if (!(object instanceof Productos)) {
            return false;
        }
        Productos other = (Productos) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descripcion + " "+ valor;
    }

}
