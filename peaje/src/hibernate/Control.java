/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Geovanny
 */
@Entity
@Table(name = "control")
@NamedQueries({
    @NamedQuery(name = "Control.findAll", query = "SELECT c FROM Control c")})
public class Control implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "puerta")
    private Integer puerta;
    @Column(name = "pulsador")
    private Boolean pulsador;
    @Column(name = "ticket")
    private Boolean ticket;
    @Column(name = "manual")
    private Boolean manual;
    
    @JoinColumn(name = "usuario", referencedColumnName = "codigo")
    @ManyToOne
    private Usuarios usuario;

    public Control() {
    }

    public Control(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getPuerta() {
        return puerta;
    }

    public void setPuerta(Integer puerta) {
        this.puerta = puerta;
    }

    public Boolean getPulsador() {
        return pulsador;
    }

    public void setPulsador(Boolean pulsador) {
        this.pulsador = pulsador;
    }

    public Boolean getTicket() {
        return ticket;
    }

    public void setTicket(Boolean ticket) {
        this.ticket = ticket;
    }

    public Boolean getManual() {
        return manual;
    }

    public void setManual(Boolean manual) {
        this.manual = manual;
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
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Control)) {
            return false;
        }
        Control other = (Control) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.Control[ codigo=" + codigo + " ]";
    }
    
}
