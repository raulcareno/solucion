/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.persistencia;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Geovanny
 */
@Entity
@Table(name = "calificacion")
@NamedQueries({
    @NamedQuery(name = "Calificacion.findAll", query = "SELECT c FROM Calificacion c")})
public class Calificacion implements Serializable {
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Column(name = "fechahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "secuencial")
    private Integer secuencial;
    @Column(name = "clientes")
    private Integer clientes;
    @Column(name = "consola")
    private Integer consola;
    @JoinColumn(name = "turnos", referencedColumnName = "codigotur")
    @ManyToOne
    private Turnos turnos;
    @JoinColumn(name = "tipos", referencedColumnName = "codigo")
    @ManyToOne
    private Tipos tipos;
    @JoinColumn(name = "empleados", referencedColumnName = "codigoemp")
    @ManyToOne
    private Empleados empleados;

    public Calificacion() {
    }

    public Calificacion(Integer secuencial) {
        this.secuencial = secuencial;
    }

    public Integer getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(Integer secuencial) {
        this.secuencial = secuencial;
    }

    public Integer getClientes() {
        return clientes;
    }

    public void setClientes(Integer clientes) {
        this.clientes = clientes;
    }

    public Integer getConsola() {
        return consola;
    }

    public void setConsola(Integer consola) {
        this.consola = consola;
    }

    public Turnos getTurnos() {
        return turnos;
    }

    public void setTurnos(Turnos turnos) {
        this.turnos = turnos;
    }

    public Tipos getTipos() {
        return tipos;
    }

    public void setTipos(Tipos tipos) {
        this.tipos = tipos;
    }

    public Empleados getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleados empleados) {
        this.empleados = empleados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secuencial != null ? secuencial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calificacion)) {
            return false;
        }
        Calificacion other = (Calificacion) object;
        if ((this.secuencial == null && other.secuencial != null) || (this.secuencial != null && !this.secuencial.equals(other.secuencial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.Persistencia.Calificacion[ secuencial=" + secuencial + " ]";
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }
    
}
