/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.persistencia;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Geovanny
 */
@Entity
@Table(name = "turnos")
@NamedQueries({
    @NamedQuery(name = "Turnos.findAll", query = "SELECT t FROM Turnos t")})
public class Turnos implements Serializable {
    @Column(name = "fechasolicitado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasolicitado;
    @Column(name = "fechaatendido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaatendido;
    @Column(name = "fechallamada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechallamada;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigotur")
    private Integer codigotur;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "ocupado")
    private Boolean ocupado;
    @Column(name = "escorporativa")
    private Boolean escorporativa =false;
    transient  private Boolean yapedido;
    @Column(name = "llamados")
    private Integer llamados;
    @JoinColumn(name = "ventanilla", referencedColumnName = "codigoven")
    @ManyToOne
    private Ventanillas ventanilla;
    
    @JoinColumn(name = "codigoemp", referencedColumnName = "codigoemp")
    @ManyToOne
    private Empleados codigoemp;
    @OneToMany(mappedBy = "codigotur")
    private Collection<Cola> colaCollection;
    @OneToMany(mappedBy = "turnos")
    private Collection<Calificacion> calificacionCollection;

    transient String codificacion;
    transient String esperando;
    
    public Turnos() {
    }

    public Turnos(Integer codigotur) {
        this.codigotur = codigotur;
    }

    public Integer getCodigotur() {
        return codigotur;
    }

    public void setCodigotur(Integer codigotur) {
        this.codigotur = codigotur;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getLlamados() {
        return llamados;
    }

    public void setLlamados(Integer llamados) {
        this.llamados = llamados;
    }


    public Empleados getCodigoemp() {
        return codigoemp;
    }

    public void setCodigoemp(Empleados codigoemp) {
        this.codigoemp = codigoemp;
    }

    public Collection<Cola> getColaCollection() {
        return colaCollection;
    }

    public void setColaCollection(Collection<Cola> colaCollection) {
        this.colaCollection = colaCollection;
    }

    public Collection<Calificacion> getCalificacionCollection() {
        return calificacionCollection;
    }

    public void setCalificacionCollection(Collection<Calificacion> calificacionCollection) {
        this.calificacionCollection = calificacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigotur != null ? codigotur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turnos)) {
            return false;
        }
        Turnos other = (Turnos) object;
        if ((this.codigotur == null && other.codigotur != null) || (this.codigotur != null && !this.codigotur.equals(other.codigotur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.Persistencia.Turnos[ codigotur=" + codigotur + " ]";
    }

    public Date getFechasolicitado() {
        return fechasolicitado;
    }

    public void setFechasolicitado(Date fechasolicitado) {
        this.fechasolicitado = fechasolicitado;
    }

    public Date getFechaatendido() {
        return fechaatendido;
    }

    public void setFechaatendido(Date fechaatendido) {
        this.fechaatendido = fechaatendido;
    }

    public Date getFechallamada() {
        return fechallamada;
    }

    public void setFechallamada(Date fechallamada) {
        this.fechallamada = fechallamada;
    }

    public Boolean getYapedido() {
        return yapedido;
    }

    public void setYapedido(Boolean yapedido) {
        this.yapedido = yapedido;
    }

    public Ventanillas getVentanilla() {
        return ventanilla;
    }

    public void setVentanilla(Ventanillas ventanilla) {
        this.ventanilla = ventanilla;
    }

    public Boolean getOcupado() {
        return ocupado;
    }

    public void setOcupado(Boolean ocupado) {
        this.ocupado = ocupado;
    }

    public String getEsperando() {
        return esperando;
    }

    public void setEsperando(String esperando) {
        this.esperando = esperando;
    }

    public Boolean getEscorporativa() {
        return escorporativa;
    }

    public void setEscorporativa(Boolean escorporativa) {
        this.escorporativa = escorporativa;
    }
    

    public String getCodificacion() {
        try {
            String num = (numero) + "";
            while (num.length() < 3) {
                num = "0" + num;
            }
            
            return ventanilla.getCodificacion()+num;    
        } catch (Exception e) {
        }
        return "";
        
    }

    public void setCodificacion(String codificacion) {
        this.codificacion = codificacion;
    }

     
}
