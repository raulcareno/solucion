/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author GEOVANNY
 */
@Entity
@Table(name = "documentos")
@NamedQueries({
    @NamedQuery(name = "Documentos.findAll", query = "SELECT d FROM Documentos d"),
    @NamedQuery(name = "Documentos.findByCodigo", query = "SELECT d FROM Documentos d WHERE d.codigo = :codigo"),
    @NamedQuery(name = "Documentos.findByFactura", query = "SELECT d FROM Documentos d WHERE d.factura = :factura"),
    @NamedQuery(name = "Documentos.findByRecibo", query = "SELECT d FROM Documentos d WHERE d.recibo = :recibo")})
public class Documentos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "factura")
    private String factura;
    @Column(name = "recibo")
    private String recibo;

    public Documentos() {
    }

    public Documentos(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
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
        if (!(object instanceof Documentos)) {
            return false;
        }
        Documentos other = (Documentos) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.Documentos[ codigo=" + codigo + " ]";
    }
    
}
