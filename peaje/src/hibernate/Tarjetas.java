/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hibernate;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author geovanny
 */
public class Tarjetas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "tarjeta")
    private String tarjeta;
    @Column(name = "Lunes")
    private Boolean lunes;
    @Column(name = "Martes")
    private Boolean martes;
    @Column(name = "Miercoles")
    private Boolean miercoles;
    @Column(name = "Jueves")
    private Boolean jueves;
    @Column(name = "Viernes")
    private Boolean viernes;
    @Column(name = "Sabado")
    private Boolean sabado;
    @Column(name = "Domingo")
    private Boolean domingo;
    @Column(name = "horainicio")
    @Temporal(TemporalType.TIME)
    private Date horainicio;
    @Column(name = "horafin")
    @Temporal(TemporalType.TIME)
    private Date horafin;
    @Column(name = "desde")
    @Temporal(TemporalType.TIMESTAMP)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hasta;
    @Column(name = "dias")
    private Integer dias;
    @Column(name = "habilitada")
    private Boolean habilitada;
    @Column(name = "ingresos")
    private Integer ingresos;
    @Column(name = "placa")
    private String placa;
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "cliente", referencedColumnName = "codigo")
    @ManyToOne
    private Clientes cliente;

    public Tarjetas() {
    }

    public Tarjetas(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Boolean getLunes() {
        return lunes;
    }

    public void setLunes(Boolean lunes) {
        this.lunes = lunes;
    }

    public Boolean getMartes() {
        return martes;
    }

    public void setMartes(Boolean martes) {
        this.martes = martes;
    }

    public Boolean getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(Boolean miercoles) {
        this.miercoles = miercoles;
    }

    public Boolean getJueves() {
        return jueves;
    }

    public void setJueves(Boolean jueves) {
        this.jueves = jueves;
    }

    public Boolean getViernes() {
        return viernes;
    }

    public void setViernes(Boolean viernes) {
        this.viernes = viernes;
    }

    public Boolean getSabado() {
        return sabado;
    }

    public void setSabado(Boolean sabado) {
        this.sabado = sabado;
    }

    public Boolean getDomingo() {
        return domingo;
    }

    public void setDomingo(Boolean domingo) {
        this.domingo = domingo;
    }

    public Date getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(Date horainicio) {
        this.horainicio = horainicio;
    }

    public Date getHorafin() {
        return horafin;
    }

    public void setHorafin(Date horafin) {
        this.horafin = horafin;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Boolean getHabilitada() {
        return habilitada;
    }

    public void setHabilitada(Boolean habilitada) {
        this.habilitada = habilitada;
    }

    public Integer getIngresos() {
        return ingresos;
    }

    public void setIngresos(Integer ingresos) {
        this.ingresos = ingresos;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tarjeta != null ? tarjeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarjetas)) {
            return false;
        }
        Tarjetas other = (Tarjetas) object;
        if ((this.tarjeta == null && other.tarjeta != null) || (this.tarjeta != null && !this.tarjeta.equals(other.tarjeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persisten.Tarjetas[tarjeta=" + tarjeta + "]";
    }

}
