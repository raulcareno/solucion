/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hibernate;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author geovanny
 */
public class Auditoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "auditoria")
    private Integer auditoria;
    @Column(name = "tabla")
    private String tabla;
    @Column(name = "maquina")
    private String maquina;
    @Column(name = "accion")
    private String accion;
    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "campo")
    private String campo;
    @JoinColumn(name = "usuario", referencedColumnName = "codigo")
    @ManyToOne
    private Usuarios usuario;

    public Auditoria() {
    }

    public Auditoria(Integer auditoria) {
        this.auditoria = auditoria;
    }

    public Integer getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Integer auditoria) {
        this.auditoria = auditoria;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (auditoria != null ? auditoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auditoria)) {
            return false;
        }
        Auditoria other = (Auditoria) object;
        if ((this.auditoria == null && other.auditoria != null) || (this.auditoria != null && !this.auditoria.equals(other.auditoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persisten.Auditoria[auditoria=" + auditoria + "]";
    }

}
