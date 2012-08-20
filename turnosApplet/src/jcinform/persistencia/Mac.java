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
@Table(name = "mac")
@NamedQueries({
    @NamedQuery(name = "Mac.findAll", query = "SELECT m FROM Mac m"),
    @NamedQuery(name = "Mac.findByMac", query = "SELECT m FROM Mac m WHERE m.mac = :mac"),
    @NamedQuery(name = "Mac.findByVentanilla", query = "SELECT m FROM Mac m WHERE m.ventanilla = :ventanilla"),
    @NamedQuery(name = "Mac.findByCom", query = "SELECT m FROM Mac m WHERE m.com = :com"),
    @NamedQuery(name = "Mac.findByConcalificador", query = "SELECT m FROM Mac m WHERE m.concalificador = :concalificador")})
public class Mac implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "mac")
    private String mac;
    @Column(name = "ventanilla")
    private String ventanilla;
    @Column(name = "com")
    private String com;
    @Column(name = "concalificador")
    private Boolean concalificador;

    public Mac() {
    }

    public Mac(String mac) {
        this.mac = mac;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getVentanilla() {
        return ventanilla;
    }

    public void setVentanilla(String ventanilla) {
        this.ventanilla = ventanilla;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public Boolean getConcalificador() {
        return concalificador;
    }

    public void setConcalificador(Boolean concalificador) {
        this.concalificador = concalificador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mac != null ? mac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mac)) {
            return false;
        }
        Mac other = (Mac) object;
        if ((this.mac == null && other.mac != null) || (this.mac != null && !this.mac.equals(other.mac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Mac[ mac=" + mac + " ]";
    }
    
}
