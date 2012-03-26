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
@Table(name = "sucursal")
@NamedQueries({
    @NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s")})
public class Sucursal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "serie1")
    private String serie1;
    @Column(name = "serie2")
    private String serie2;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "secfactura")
    private Integer secfactura;
    @Column(name = "seccontrato")
    private Integer seccontrato;
    @Column(name = "seccompras")
    private Integer seccompras;
    @Column(name = "secrecibos")
    private Integer secrecibos;
    @Column(name = "autorizacion")
    private String autorizacion;
    @Column(name = "desde")
    private Integer desde;
    @Column(name = "hasta")
    private Integer hasta;
        
    @JoinColumn(name = "empresa", referencedColumnName = "ruc")
    @ManyToOne
    private Empresa empresa;
    @OneToMany(mappedBy = "sucursal")
    private Collection<Factura> facturaCollection;
    @OneToMany(mappedBy = "sucursal")
    private Collection<Cabeceracompra> cabeceracompraCollection;
    @OneToMany(mappedBy = "sucursal")
    private Collection<Empleadossucursal> empleadossucursalCollection;
    @OneToMany(mappedBy = "sucursal")
    private Collection<Contratos> contratosCollection;

    public Sucursal() {
    }

    public Sucursal(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getSerie1() {
        return serie1;
    }

    public void setSerie1(String serie1) {
        this.serie1 = serie1;
    }

    public String getSerie2() {
        return serie2;
    }

    public void setSerie2(String serie2) {
        this.serie2 = serie2;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getSecfactura() {
        return secfactura;
    }

    public void setSecfactura(Integer secfactura) {
        this.secfactura = secfactura;
    }

    public Integer getSeccontrato() {
        return seccontrato;
    }

    public void setSeccontrato(Integer seccontrato) {
        this.seccontrato = seccontrato;
    }

    public Integer getSeccompras() {
        return seccompras;
    }

    public void setSeccompras(Integer seccompras) {
        this.seccompras = seccompras;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public Integer getDesde() {
        return desde;
    }

    public void setDesde(Integer desde) {
        this.desde = desde;
    }

    public Integer getHasta() {
        return hasta;
    }

    public void setHasta(Integer hasta) {
        this.hasta = hasta;
    }

    
    
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    public Collection<Cabeceracompra> getCabeceracompraCollection() {
        return cabeceracompraCollection;
    }

    public void setCabeceracompraCollection(Collection<Cabeceracompra> cabeceracompraCollection) {
        this.cabeceracompraCollection = cabeceracompraCollection;
    }

    public Collection<Empleadossucursal> getEmpleadossucursalCollection() {
        return empleadossucursalCollection;
    }

    public void setEmpleadossucursalCollection(Collection<Empleadossucursal> empleadossucursalCollection) {
        this.empleadossucursalCollection = empleadossucursalCollection;
    }

    public Collection<Contratos> getContratosCollection() {
        return contratosCollection;
    }

    public void setContratosCollection(Collection<Contratos> contratosCollection) {
        this.contratosCollection = contratosCollection;
    }

    public Integer getSecrecibos() {
        return secrecibos;
    }

    public void setSecrecibos(Integer secrecibos) {
        this.secrecibos = secrecibos;
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
        if (!(object instanceof Sucursal)) {
            return false;
        }
        Sucursal other = (Sucursal) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Sucursal[codigo=" + codigo + "]";
    }

}
