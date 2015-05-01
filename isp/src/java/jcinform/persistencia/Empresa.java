/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jcinform.persistencia;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.*;

/**
 *
 * @author Familia Jadan Cahueñ
 */
@Entity
@Table(name = "empresa")
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e")})
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ruc")
    private String ruc;
    @Column(name = "razonsocial")
    private String razonsocial;
    @Column(name = "representante")
    private String representante;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "telefono2")
    private String telefono2;
    @Column(name = "movil")
    private String movil;
    @Column(name = "documento")
    private String documento;
    @Column(name = "email")
    private String email;
    @Column(name = "web")
    private String web;
    @Column(name = "reportes")
    private String reportes;
    @Column(name = "fotos")
    private String fotos;
    @Column(name = "url")
    private String url;
    @Column(name = "latitud")
    private String latitud;
    @Column(name = "longitud")
    private String longitud;
    @Column(name = "usuariomail")
    private String usuariomail;
    @Column(name = "clavemail")
    private String clavemail;
    @Column(name = "autorizacion")
    private Boolean autorizacion;
    @Column(name = "smtp")
    private String smtp;
    @Column(name = "puerto")
    private String puerto;
    @Column(name = "logo")
    private String logo;
    @Column(name = "star")
    private Boolean star;
    @Column(name = "instalacion")
    private BigDecimal instalacion;
    @Column(name = "iva")
    private BigDecimal iva;
    @Column(name = "mora")
    private Integer mora;
    @Column(name = "suspension")
    private Integer suspension;
    @Column(name = "aplicamora")
    private Boolean aplicamora;
    @Column(name = "aplicasuspension")
    private Boolean aplicasuspension;
    @Column(name = "solofactura")
    private Boolean solofactura;
    @Column(name = "diasminima")
    private Integer diasminima;
    @Column(name = "diasgraciafinal")
    private Integer diasgraciafinal;
    
     
    
    @OneToMany(mappedBy = "empresa")
    private Collection<Sucursal> sucursalCollection;
   
    public Empresa() {
    }

    public Empresa(String ruc) {
        this.ruc = ruc;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getReportes() {
        return reportes;
    }

    public void setReportes(String reportes) {
        this.reportes = reportes;
    }

    public String getFotos() {
        return fotos;
    }

    public void setFotos(String fotos) {
        this.fotos = fotos;
    }

    public String getUsuariomail() {
        return usuariomail;
    }

    public void setUsuariomail(String usuariomail) {
        this.usuariomail = usuariomail;
    }

    public String getClavemail() {
        return clavemail;
    }

    public void setClavemail(String clavemail) {
        this.clavemail = clavemail;
    }

    public Boolean getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(Boolean autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getStar() {
        return star;
    }

    public void setStar(Boolean star) {
        this.star = star;
    }

    public BigDecimal getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(BigDecimal instalacion) {
        this.instalacion = instalacion;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public Integer getMora() {
        return mora;
    }

    public void setMora(Integer mora) {
        this.mora = mora;
    }

    public Integer getSuspension() {
        return suspension;
    }

    public void setSuspension(Integer suspension) {
        this.suspension = suspension;
    }

    
    public Boolean getAplicamora() {
        return aplicamora;
    }

    public void setAplicamora(Boolean aplicamora) {
        this.aplicamora = aplicamora;
    }

    public Boolean getAplicasuspension() {
        return aplicasuspension;
    }

    public void setAplicasuspension(Boolean aplicasuspension) {
        this.aplicasuspension = aplicasuspension;
    }

 

    public Boolean getSolofactura() {
        return solofactura;
    }

    public void setSolofactura(Boolean solofactura) {
        this.solofactura = solofactura;
    }

    
    
    public Collection<Sucursal> getSucursalCollection() {
        return sucursalCollection;
    }

    public void setSucursalCollection(Collection<Sucursal> sucursalCollection) {
        this.sucursalCollection = sucursalCollection;
    }

    public Integer getDiasminima() {
        return diasminima;
    }

    public void setDiasminima(Integer diasminima) {
        this.diasminima = diasminima;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Integer getDiasgraciafinal() {
        return diasgraciafinal;
    }

    public void setDiasgraciafinal(Integer diasgraciafinal) {
        this.diasgraciafinal = diasgraciafinal;
    }

 
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ruc != null ? ruc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.ruc == null && other.ruc != null) || (this.ruc != null && !this.ruc.equals(other.ruc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Empresa[ruc=" + ruc + "]";
    }

}
