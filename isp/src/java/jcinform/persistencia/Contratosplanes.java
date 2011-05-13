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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Familia Jadan Cahueñ
 */
@Entity
@Table(name = "contratosplanes")
@NamedQueries({
    @NamedQuery(name = "Contratosplanes.findAll", query = "SELECT c FROM Contratosplanes c")})
public class Contratosplanes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "plan")
    private Integer plan;

    public Contratosplanes() {
    }

    public Contratosplanes(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getPlan() {
        return plan;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
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
        if (!(object instanceof Contratosplanes)) {
            return false;
        }
        Contratosplanes other = (Contratosplanes) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Contratosplanes[codigo=" + codigo + "]";
    }

}
