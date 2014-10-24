/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siscontrol;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Geovanny
 */
@Entity
@Table(name = "empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e"),
    @NamedQuery(name = "Empresa.findByRuc", query = "SELECT e FROM Empresa e WHERE e.ruc = :ruc"),
    @NamedQuery(name = "Empresa.findByRazonsocial", query = "SELECT e FROM Empresa e WHERE e.razonsocial = :razonsocial"),
    @NamedQuery(name = "Empresa.findByRepresentante", query = "SELECT e FROM Empresa e WHERE e.representante = :representante"),
    @NamedQuery(name = "Empresa.findByDireccion", query = "SELECT e FROM Empresa e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Empresa.findByTelefono", query = "SELECT e FROM Empresa e WHERE e.telefono = :telefono"),
    @NamedQuery(name = "Empresa.findByTelefono2", query = "SELECT e FROM Empresa e WHERE e.telefono2 = :telefono2"),
    @NamedQuery(name = "Empresa.findByMovil", query = "SELECT e FROM Empresa e WHERE e.movil = :movil"),
    @NamedQuery(name = "Empresa.findByDocumento1", query = "SELECT e FROM Empresa e WHERE e.documento1 = :documento1"),
    @NamedQuery(name = "Empresa.findByEmail", query = "SELECT e FROM Empresa e WHERE e.email = :email"),
    @NamedQuery(name = "Empresa.findByWeb", query = "SELECT e FROM Empresa e WHERE e.web = :web"),
    @NamedQuery(name = "Empresa.findByReportes", query = "SELECT e FROM Empresa e WHERE e.reportes = :reportes"),
    @NamedQuery(name = "Empresa.findByFotos", query = "SELECT e FROM Empresa e WHERE e.fotos = :fotos"),
    @NamedQuery(name = "Empresa.findByUsuariomail", query = "SELECT e FROM Empresa e WHERE e.usuariomail = :usuariomail"),
    @NamedQuery(name = "Empresa.findByClavemail", query = "SELECT e FROM Empresa e WHERE e.clavemail = :clavemail"),
    @NamedQuery(name = "Empresa.findByAutorizacion", query = "SELECT e FROM Empresa e WHERE e.autorizacion = :autorizacion"),
    @NamedQuery(name = "Empresa.findBySmtp", query = "SELECT e FROM Empresa e WHERE e.smtp = :smtp"),
    @NamedQuery(name = "Empresa.findByPuerto", query = "SELECT e FROM Empresa e WHERE e.puerto = :puerto"),
    @NamedQuery(name = "Empresa.findByLogo", query = "SELECT e FROM Empresa e WHERE e.logo = :logo"),
    @NamedQuery(name = "Empresa.findByStar", query = "SELECT e FROM Empresa e WHERE e.star = :star"),
    @NamedQuery(name = "Empresa.findByInstalacion", query = "SELECT e FROM Empresa e WHERE e.instalacion = :instalacion"),
    @NamedQuery(name = "Empresa.findByIva", query = "SELECT e FROM Empresa e WHERE e.iva = :iva"),
    @NamedQuery(name = "Empresa.findByMora", query = "SELECT e FROM Empresa e WHERE e.mora = :mora"),
    @NamedQuery(name = "Empresa.findBySuspension", query = "SELECT e FROM Empresa e WHERE e.suspension = :suspension"),
    @NamedQuery(name = "Empresa.findByAplicamora", query = "SELECT e FROM Empresa e WHERE e.aplicamora = :aplicamora"),
    @NamedQuery(name = "Empresa.findByAplicasuspension", query = "SELECT e FROM Empresa e WHERE e.aplicasuspension = :aplicasuspension"),
    @NamedQuery(name = "Empresa.findBySolofactura", query = "SELECT e FROM Empresa e WHERE e.solofactura = :solofactura"),
    @NamedQuery(name = "Empresa.findByDiasminima", query = "SELECT e FROM Empresa e WHERE e.diasminima = :diasminima"),
    @NamedQuery(name = "Empresa.findByDocumento", query = "SELECT e FROM Empresa e WHERE e.documento = :documento")})
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ruc")
    private String ruc;
    @Column(name = "code")
    private String code;
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
    @Column(name = "documento1")
    private String documento1;
    @Column(name = "email")
    private String email;
    @Column(name = "web")
    private String web;
    @Column(name = "reportes")
    private String reportes;
    @Column(name = "fotos")
    private String fotos;
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "instalacion")
    private BigDecimal instalacion;
    @Column(name = "IVA")
    private BigDecimal iva;
    @Column(name = "mora")
    private Integer mora;
    @Column(name = "suspension")
    private Integer suspension;
    @Column(name = "aplicamora")
    private Boolean aplicamora;
    @Column(name = "aplicasuspension")
    private Integer aplicasuspension;
    @Column(name = "solofactura")
    private Integer solofactura;
    @Column(name = "diasminima")
    private Integer diasminima;
    @Column(name = "documento")
    private String documento;

    public Empresa() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getDocumento1() {
        return documento1;
    }

    public void setDocumento1(String documento1) {
        this.documento1 = documento1;
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

    public Integer getAplicasuspension() {
        return aplicasuspension;
    }

    public void setAplicasuspension(Integer aplicasuspension) {
        this.aplicasuspension = aplicasuspension;
    }

    public Integer getSolofactura() {
        return solofactura;
    }

    public void setSolofactura(Integer solofactura) {
        this.solofactura = solofactura;
    }

    public Integer getDiasminima() {
        return diasminima;
    }

    public void setDiasminima(Integer diasminima) {
        this.diasminima = diasminima;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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
        return "siscontrol.Empresa[ ruc=" + ruc + " ]";
    }
    
}
