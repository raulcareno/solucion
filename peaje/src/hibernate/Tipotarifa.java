/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Geovanny
 */
@Entity
@Table(name = "tipotarifa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipotarifa.findAll", query = "SELECT t FROM Tipotarifa t"),
    @NamedQuery(name = "Tipotarifa.findByCodigo", query = "SELECT t FROM Tipotarifa t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "Tipotarifa.findByNombre", query = "SELECT t FROM Tipotarifa t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tipotarifa.findByDesde", query = "SELECT t FROM Tipotarifa t WHERE t.desde = :desde"),
    @NamedQuery(name = "Tipotarifa.findByHasta", query = "SELECT t FROM Tipotarifa t WHERE t.hasta = :hasta"),
    @NamedQuery(name = "Tipotarifa.findByLunes", query = "SELECT t FROM Tipotarifa t WHERE t.lunes = :lunes"),
    @NamedQuery(name = "Tipotarifa.findByMartes", query = "SELECT t FROM Tipotarifa t WHERE t.martes = :martes"),
    @NamedQuery(name = "Tipotarifa.findByMiercoles", query = "SELECT t FROM Tipotarifa t WHERE t.miercoles = :miercoles"),
    @NamedQuery(name = "Tipotarifa.findByJueves", query = "SELECT t FROM Tipotarifa t WHERE t.jueves = :jueves"),
    @NamedQuery(name = "Tipotarifa.findByViernes", query = "SELECT t FROM Tipotarifa t WHERE t.viernes = :viernes"),
    @NamedQuery(name = "Tipotarifa.findBySabado", query = "SELECT t FROM Tipotarifa t WHERE t.sabado = :sabado"),
    @NamedQuery(name = "Tipotarifa.findByDomingo", query = "SELECT t FROM Tipotarifa t WHERE t.domingo = :domingo")})
public class Tipotarifa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "desde")
    @Temporal(TemporalType.TIMESTAMP)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hasta;
    @Column(name = "lunes")
    private Boolean lunes;
    @Column(name = "martes")
    private Boolean martes;
    @Column(name = "miercoles")
    private Boolean miercoles;
    @Column(name = "jueves")
    private Boolean jueves;
    @Column(name = "viernes")
    private Boolean viernes;
    @Column(name = "sabado")
    private Boolean sabado;
    @Column(name = "domingo")
    private Boolean domingo;
    @Column(name = "Nohoras")
    private Integer Nohoras;

    public Tipotarifa() {
    }

    public Tipotarifa(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    public Integer getNohoras() {
        return Nohoras;
    }

    public void setNohoras(Integer Nohoras) {
        this.Nohoras = Nohoras;
    }
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipotarifa)) {
            return false;
        }
        Tipotarifa other = (Tipotarifa) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + nombre + " D:"+getDesde().toLocaleString().substring(10) + " H:"+getHasta().toLocaleString().substring(10);
    }
    
}
