/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jcinform.persistencia;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Familia Jadan Cahueñ
 */
@Entity
@Table(name = "detallecompra")
@NamedQueries({
    @NamedQuery(name = "Detallecompra.findAll", query = "SELECT d FROM Detallecompra d")})
public class Detallecompra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "costo")
    private Double costo;
    @Column(name = "pvp1")
    private Double pvp1;
    @Column(name = "pvp2")
    private Double pvp2;
    @Column(name = "pvp3")
    private Double pvp3;
    @Column(name = "pvp4")
    private Double pvp4;
    @Column(name = "asignado")
    private Boolean asignado;
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "detallecompra")
    private Collection<Series> seriesCollection;
    @JoinColumn(name = "compra", referencedColumnName = "codigo")
    @ManyToOne
    private Cabeceracompra cabeceracompra;
    @JoinColumn(name = "equipos", referencedColumnName = "codigo")
    @ManyToOne
    private Equipos equipos;
    @JoinColumn(name = "contrato", referencedColumnName = "codigo")
    @ManyToOne
    private Contratos contratos;

    public Detallecompra() {
    }

    public Detallecompra(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getPvp1() {
        return pvp1;
    }

    public void setPvp1(Double pvp1) {
        this.pvp1 = pvp1;
    }

    public Double getPvp2() {
        return pvp2;
    }

    public void setPvp2(Double pvp2) {
        this.pvp2 = pvp2;
    }

    public Double getPvp3() {
        return pvp3;
    }

    public void setPvp3(Double pvp3) {
        this.pvp3 = pvp3;
    }

    public Double getPvp4() {
        return pvp4;
    }

    public void setPvp4(Double pvp4) {
        this.pvp4 = pvp4;
    }

    public Boolean getAsignado() {
        return asignado;
    }

    public void setAsignado(Boolean asignado) {
        this.asignado = asignado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Collection<Series> getSeriesCollection() {
        return seriesCollection;
    }

    public void setSeriesCollection(Collection<Series> seriesCollection) {
        this.seriesCollection = seriesCollection;
    }

    public Cabeceracompra getCabeceracompra() {
        return cabeceracompra;
    }

    public void setCabeceracompra(Cabeceracompra cabeceracompra) {
        this.cabeceracompra = cabeceracompra;
    }

    public Equipos getEquipos() {
        return equipos;
    }

    public void setEquipos(Equipos equipos) {
        this.equipos = equipos;
    }

    public Contratos getContratos() {
        return contratos;
    }

    public void setContratos(Contratos contratos) {
        this.contratos = contratos;
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
        if (!(object instanceof Detallecompra)) {
            return false;
        }
        Detallecompra other = (Detallecompra) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Detallecompra[codigo=" + codigo + "]";
    }

}
