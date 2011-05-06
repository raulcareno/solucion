/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Familia Jadan Cahueñ
 */
@Entity
@Table(name = "dientes")
@NamedQueries({
    @NamedQuery(name = "Dientes.findAll", query = "SELECT d FROM Dientes d"),
    @NamedQuery(name = "Dientes.findByCodigo", query = "SELECT d FROM Dientes d WHERE d.codigo = :codigo"),
    @NamedQuery(name = "Dientes.findByReferencia", query = "SELECT d FROM Dientes d WHERE d.referencia = :referencia")})
public class Dientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "referencia")
    private String referencia;
    @OneToMany(mappedBy = "dientes")
    private Collection<Dientesestado> dientesestadoCollection;

    public Dientes() {
    }

    public Dientes(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Collection<Dientesestado> getDientesestadoCollection() {
        return dientesestadoCollection;
    }

    public void setDientesestadoCollection(Collection<Dientesestado> dientesestadoCollection) {
        this.dientesestadoCollection = dientesestadoCollection;
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
        if (!(object instanceof Dientes)) {
            return false;
        }
        Dientes other = (Dientes) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Dientes[codigo=" + codigo + "]";
    }

}
