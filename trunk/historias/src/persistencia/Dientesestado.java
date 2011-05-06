/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

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
@Table(name = "dientesestado")
@NamedQueries({
    @NamedQuery(name = "Dientesestado.findAll", query = "SELECT d FROM Dientesestado d"),
    @NamedQuery(name = "Dientesestado.findByCodigo", query = "SELECT d FROM Dientesestado d WHERE d.codigo = :codigo")})
public class Dientesestado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @JoinColumn(name = "sup", referencedColumnName = "codigo")
    @ManyToOne
    private Estados sup;
    @JoinColumn(name = "izq", referencedColumnName = "codigo")
    @ManyToOne
    private Estados izq;
    @JoinColumn(name = "inf", referencedColumnName = "codigo")
    @ManyToOne
    private Estados inf;
    @JoinColumn(name = "cen", referencedColumnName = "codigo")
    @ManyToOne
    private Estados cen;
    @JoinColumn(name = "dientes", referencedColumnName = "codigo")
    @ManyToOne
    private Dientes dientes;
    @JoinColumn(name = "pacientes", referencedColumnName = "codigo")
    @ManyToOne
    private Pacientes  pacientes;
    @JoinColumn(name = "der", referencedColumnName = "codigo")
    @ManyToOne
    private Estados der;

    public Dientesestado() {
    }

    public Dientesestado(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }



    public Dientes getDientes() {
        return dientes;
    }

    public void setDientes(Dientes dientes) {
        this.dientes = dientes;
    }

    public Pacientes getPacientes() {
        return pacientes;
    }

    public void setPacientes(Pacientes pacientes) {
        this.pacientes = pacientes;
    }

    public Estados getCen() {
        return cen;
    }

    public void setCen(Estados cen) {
        this.cen = cen;
    }

    public Estados getDer() {
        return der;
    }

    public void setDer(Estados der) {
        this.der = der;
    }

    public Estados getInf() {
        return inf;
    }

    public void setInf(Estados inf) {
        this.inf = inf;
    }

    public Estados getIzq() {
        return izq;
    }

    public void setIzq(Estados izq) {
        this.izq = izq;
    }

    public Estados getSup() {
        return sup;
    }

    public void setSup(Estados sup) {
        this.sup = sup;
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
        if (!(object instanceof Dientesestado)) {
            return false;
        }
        Dientesestado other = (Dientesestado) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Dientesestado[codigo=" + codigo + "]";
    }

}
