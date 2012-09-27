/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Geovanny
 */
@Entity
@Table(name = "cxcobrar")
@NamedQueries({
    @NamedQuery(name = "Cxcobrar.findAll", query = "SELECT c FROM Cxcobrar c")})
public class Cxcobrar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @JoinColumn(name = "clientes", referencedColumnName = "codigo")
    @ManyToOne
    private Clientes clientes;
    @JoinColumn(name = "usuario", referencedColumnName = "codigo")
    @ManyToOne
    private Usuarios usuario;
       @JoinColumn(name = "factura", referencedColumnName = "codigo")
    @ManyToOne
    private Factura factura;
    @Column(name = "pagada")
    private Boolean pagada;

    public Cxcobrar() {
    }

    public Cxcobrar(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }


    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Boolean getPagada() {
        return pagada;
    }

    public void setPagada(Boolean pagada) {
        this.pagada = pagada;
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
        if (!(object instanceof Cxcobrar)) {
            return false;
        }
        Cxcobrar other = (Cxcobrar) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "hibernate.Cxcobrar[ codigo=" + codigo + " ]";
    }
}
