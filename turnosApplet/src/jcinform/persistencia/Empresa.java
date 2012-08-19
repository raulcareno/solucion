/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Geovanny
 */
@Entity
@Table(name = "empresa")
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e")})
public class Empresa implements Serializable {
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ruc")
    private String ruc;
    @Column(name = "empresa")
    private String empresa;
    @Column(name = "razonsocial")
    private String razonsocial;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "slogan")
    private String slogan;
    @Column(name = "impresora1")
    private String impresora1;
    @Column(name = "impresora2")
    private String impresora2;
    @Column(name = "camara1")
    private String camara1;
    @Column(name = "camara2")
    private String camara2;
    @Column(name = "turnos")
    private Boolean turnos;
    @Column(name = "calificador")
    private Boolean calificador;
    @Column(name = "noturnos")
    private Integer noturnos;
    @Column(name = "calificar")
    private Boolean calificar;
    @Column(name = "touch")
    private Boolean touch;
    @Column(name = "reportes")
    private String reportes;
    
    @Column(name = "email")
    private String email;
    @Column(name = "clave")
    private String clave;
    @Column(name = "smtp")
    private String smtp;
    @Column(name = "star")
    private Boolean star;
    @Column(name = "autorizacion")
    private Boolean autorizacion;
    @Column(name = "puertosmtp")
    private String puertosmtp;
transient String puerto;
transient Integer tipoterminal;
transient String ventanilla;
transient String ip;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
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

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getImpresora1() {
        return impresora1;
    }

    public void setImpresora1(String impresora1) {
        this.impresora1 = impresora1;
    }

    public String getImpresora2() {
        return impresora2;
    }

    public void setImpresora2(String impresora2) {
        this.impresora2 = impresora2;
    }

    public String getCamara1() {
        return camara1;
    }

    public void setCamara1(String camara1) {
        this.camara1 = camara1;
    }

    public String getCamara2() {
        return camara2;
    }

    public void setCamara2(String camara2) {
        this.camara2 = camara2;
    }

    public Boolean getTurnos() {
        return turnos;
    }

    public void setTurnos(Boolean turnos) {
        this.turnos = turnos;
    }

    public Boolean getCalificador() {
        return calificador;
    }

    public void setCalificador(Boolean calificador) {
        this.calificador = calificador;
    }

    public Integer getNoturnos() {
        return noturnos;
    }

    public void setNoturnos(Integer noturnos) {
        this.noturnos = noturnos;
    }

    public Boolean getCalificar() {
        return calificar;
    }

    public void setCalificar(Boolean calificar) {
        this.calificar = calificar;
    }

    public String getReportes() {
        return reportes;
    }

    public void setReportes(String reportes) {
        this.reportes = reportes;
    }

    public Boolean getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(Boolean autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPuertosmtp() {
        return puertosmtp;
    }

    public void setPuertosmtp(String puertosmtp) {
        this.puertosmtp = puertosmtp;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public Boolean getStar() {
        return star;
    }

    public void setStar(Boolean star) {
        this.star = star;
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
        return "jcinform.Persistencia.Empresa[ ruc=" + ruc + " ]";
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

  

    public String getVentanilla() {
        return ventanilla;
    }

    public void setVentanilla(String ventanilla) {
        this.ventanilla = ventanilla;
    }

    public Integer getTipoterminal() {
        return tipoterminal;
    }

    public void setTipoterminal(Integer tipoterminal) {
        this.tipoterminal = tipoterminal;
    }

    public Boolean getTouch() {
        return touch;
    }

    public void setTouch(Boolean touch) {
        this.touch = touch;
    }

 
    
}
