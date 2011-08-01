/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jcinform.persistencia;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author Familia Jadan Cahueñ
 */
@Entity
@Table(name = "factura")
@NamedQueries({})
public class Factura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "numero")
    private String numero;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "observacion")
    private String observacion;
       @Column(name = "subtotal")
    private BigDecimal subtotal;
    @Column(name = "descuento")
    private BigDecimal descuento;
    @Column(name = "baseiva")
    private BigDecimal baseiva;
    @Column(name = "porcentajeiva")
    private BigDecimal porcentajeiva;
    @Column(name = "valoriva")
    private BigDecimal valoriva;
    @JoinColumn(name = "sucursal", referencedColumnName = "codigo")
    @ManyToOne
    private Sucursal sucursal;
    @JoinColumn(name = "sector", referencedColumnName = "codigo")
    @ManyToOne
    private Sector sector;
    @JoinColumn(name = "clientes", referencedColumnName = "codigo")
    @ManyToOne
    private Clientes clientes;
       @JoinColumn(name = "contratos", referencedColumnName = "codigo")
    @ManyToOne
    private Contratos contratos;
    @OneToMany(mappedBy = "factura")
    private Collection<Detalle> detalleCollection;
    @OneToMany(mappedBy = "factura")
    private Collection<Cxcobrar> cxcobrarCollection;
@Column(name = "impresa")
    private Boolean impresa;
    public Factura() {
    }

    public Factura(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

  
    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public BigDecimal getBaseiva() {
        return baseiva;
    }

    public void setBaseiva(BigDecimal baseiva) {
        this.baseiva = baseiva;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getPorcentajeiva() {
        return porcentajeiva;
    }

    public void setPorcentajeiva(BigDecimal porcentajeiva) {
        this.porcentajeiva = porcentajeiva;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getValoriva() {
        return valoriva;
    }

    public void setValoriva(BigDecimal valoriva) {
        this.valoriva = valoriva;
    }

    public Contratos getContratos() {
        return contratos;
    }

    public void setContratos(Contratos contratos) {
        this.contratos = contratos;
    }

    public Boolean getImpresa() {
        return impresa;
    }

    public void setImpresa(Boolean impresa) {
        this.impresa = impresa;
    }

    public Collection<Detalle> getDetalleCollection() {
        return detalleCollection;
    }

    public void setDetalleCollection(Collection<Detalle> detalleCollection) {
        this.detalleCollection = detalleCollection;
    }

    public Collection<Cxcobrar> getCxcobrarCollection() {
        return cxcobrarCollection;
    }

    public void setCxcobrarCollection(Collection<Cxcobrar> cxcobrarCollection) {
        this.cxcobrarCollection = cxcobrarCollection;
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
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Factura[codigo=" + codigo + "]";
    }

}
