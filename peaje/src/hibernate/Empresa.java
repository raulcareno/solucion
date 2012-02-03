/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Familia Jadan Cahueñ
 */
@Entity
@Table(name = "empresa")
@NamedQueries({})
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ruc")
    private String ruc;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "razon")
    private String razon;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefonos")
    private String telefonos;
    @Column(name = "documentofac")
    private String documentofac;
    @Column(name = "documentonota")
    private String documentonota;
    @Column(name = "documentoticket")
    private String documentoticket;
    @Column(name = "parqueaderos")
    private Integer parqueaderos;
    @Column(name = "impticket")
    private String impticket;
    @Column(name = "impfactura")
    private String impfactura;
    @Column(name = "impresora")
    private String impresora;
    @Column(name = "impresora2")
    private String impresora2;
    @Column(name = "puerto")
    private String puerto;
    @Column(name = "iva")
    private Double iva;
    @Column(name = "gracia")
    private Integer gracia;
    @Column(name = "salida")
    private Integer salida;
    @Column(name = "led")
    private String led;
    @Column(name = "barras")
    private String barras;
    @Column(name = "barras2")
    private String barras2;
    @Column(name = "sale")
    private String sale;
    @Column(name = "sale2")
    private String sale2;
    @Column(name = "puerto1")
    private String puerto1;
    @Column(name = "puerto2")
    private String puerto2;
    @Column(name = "puerto3")
    private String puerto3;
    @Column(name = "puerto4")
    private String puerto4;
    @Column(name = "puerto5")
    private String puerto5;
    @Column(name = "puerto6")
    private String puerto6;
    @Column(name = "puerto7")
    private String puerto7;
    @Column(name = "salida1")
    private String salida1;
    @Column(name = "salida2")
    private String salida2;
    @Column(name = "salida3")
    private String salida3;
    @Column(name = "salida4")
    private String salida4;
    @Column(name = "salida5")
    private String salida5;
    @Column(name = "salida6")
    private String salida6;
    @Column(name = "salida7")
    private String salida7;
    @Column(name = "activa1")
    private Boolean activa1;
    @Column(name = "activa2")
    private Boolean activa2;
    @Column(name = "activa3")
    private Boolean activa3;
    @Column(name = "activa4")
    private Boolean activa4;
    @Column(name = "activa5")
    private Boolean activa5;
    @Column(name = "activa6")
    private Boolean activa6;
    @Column(name = "activa7")
    private Boolean activa7;
    @Column(name = "activa8")
    private Boolean activa8;
    @Column(name = "activa9")
    private Boolean activa9;
    @Column(name = "activa10")
    private Boolean activa10;
    @Column(name = "activa11")
    private Boolean activa11;
    @Column(name = "activa12")
    private Boolean activa12;
    @Column(name = "activa13")
    private Boolean activa13;
    @Column(name = "activa14")
    private Boolean activa14;
    @Column(name = "puerta1")
    private String puerta1;
    @Column(name = "puerta2")
    private String puerta2;
    @Column(name = "puerta3")
    private String puerta3;
    @Column(name = "puerta4")
    private String puerta4;
    @Column(name = "puerta5")
    private String puerta5;
    @Column(name = "puerta6")
    private String puerta6;
    @Column(name = "puerta7")
    private String puerta7;
    @Column(name = "puerta8")
    private String puerta8;
    @Column(name = "puerta9")
    private String puerta9;
    @Column(name = "puerta10")
    private String puerta10;
    @Column(name = "puerta11")
    private String puerta11;
    @Column(name = "puerta12")
    private String puerta12;
    @Column(name = "puerta13")
    private String puerta13;
    @Column(name = "puerta14")
    private String puerta14;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getDocumentofac() {
        return documentofac;
    }

    public void setDocumentofac(String documentofac) {
        this.documentofac = documentofac;
    }

    public String getDocumentonota() {
        return documentonota;
    }

    public void setDocumentonota(String documentonota) {
        this.documentonota = documentonota;
    }

    public String getDocumentoticket() {
        return documentoticket;
    }

    public void setDocumentoticket(String documentoticket) {
        this.documentoticket = documentoticket;
    }

    public Integer getParqueaderos() {
        return parqueaderos;
    }

    public void setParqueaderos(Integer parqueaderos) {
        this.parqueaderos = parqueaderos;
    }

    public String getImpticket() {
        return impticket;
    }

    public void setImpticket(String impticket) {
        this.impticket = impticket;
    }

    public String getImpfactura() {
        return impfactura;
    }

    public void setImpfactura(String impfactura) {
        this.impfactura = impfactura;
    }

    public String getImpresora() {
        return impresora;
    }

    public void setImpresora(String impresora) {
        this.impresora = impresora;
    }

    public String getImpresora2() {
        return impresora2;
    }

    public void setImpresora2(String impresora2) {
        this.impresora2 = impresora2;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Integer getGracia() {
        return gracia;
    }

    public void setGracia(Integer gracia) {
        this.gracia = gracia;
    }

    public Integer getSalida() {
        return salida;
    }

    public void setSalida(Integer salida) {
        this.salida = salida;
    }

    public String getBarras() {
        return barras;
    }

    public void setBarras(String barras) {
        this.barras = barras;
    }

    public String getBarras2() {
        return barras2;
    }

    public void setBarras2(String barras2) {
        this.barras2 = barras2;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getSale2() {
        return sale2;
    }

    public void setSale2(String sale2) {
        this.sale2 = sale2;
    }

    public String getPuerto1() {
        return puerto1;
    }

    public void setPuerto1(String puerto1) {
        this.puerto1 = puerto1;
    }

    public String getPuerto2() {
        return puerto2;
    }

    public void setPuerto2(String puerto2) {
        this.puerto2 = puerto2;
    }

    public String getPuerto3() {
        return puerto3;
    }

    public void setPuerto3(String puerto3) {
        this.puerto3 = puerto3;
    }

    public String getPuerto4() {
        return puerto4;
    }

    public void setPuerto4(String puerto4) {
        this.puerto4 = puerto4;
    }

    public String getPuerto5() {
        return puerto5;
    }

    public void setPuerto5(String puerto5) {
        this.puerto5 = puerto5;
    }

    public String getPuerto6() {
        return puerto6;
    }

    public void setPuerto6(String puerto6) {
        this.puerto6 = puerto6;
    }

    public String getPuerto7() {
        return puerto7;
    }

    public void setPuerto7(String puerto7) {
        this.puerto7 = puerto7;
    }

    public String getSalida1() {
        return salida1;
    }

    public void setSalida1(String salida1) {
        this.salida1 = salida1;
    }

    public String getSalida2() {
        return salida2;
    }

    public void setSalida2(String salida2) {
        this.salida2 = salida2;
    }

    public String getSalida3() {
        return salida3;
    }

    public void setSalida3(String salida3) {
        this.salida3 = salida3;
    }

    public String getSalida4() {
        return salida4;
    }

    public void setSalida4(String salida4) {
        this.salida4 = salida4;
    }

    public String getSalida5() {
        return salida5;
    }

    public void setSalida5(String salida5) {
        this.salida5 = salida5;
    }

    public String getSalida6() {
        return salida6;
    }

    public void setSalida6(String salida6) {
        this.salida6 = salida6;
    }

    public String getSalida7() {
        return salida7;
    }

    public void setSalida7(String salida7) {
        this.salida7 = salida7;
    }

    public Boolean getActiva1() {
        return activa1;
    }

    public void setActiva1(Boolean activa1) {
        this.activa1 = activa1;
    }

    public Boolean getActiva2() {
        return activa2;
    }

    public void setActiva2(Boolean activa2) {
        this.activa2 = activa2;
    }

    public Boolean getActiva3() {
        return activa3;
    }

    public void setActiva3(Boolean activa3) {
        this.activa3 = activa3;
    }

    public Boolean getActiva4() {
        return activa4;
    }

    public void setActiva4(Boolean activa4) {
        this.activa4 = activa4;
    }

    public Boolean getActiva5() {
        return activa5;
    }

    public void setActiva5(Boolean activa5) {
        this.activa5 = activa5;
    }

    public Boolean getActiva6() {
        return activa6;
    }

    public void setActiva6(Boolean activa6) {
        this.activa6 = activa6;
    }

    public Boolean getActiva7() {
        return activa7;
    }

    public void setActiva7(Boolean activa7) {
        this.activa7 = activa7;
    }

    public Boolean getActiva8() {
        return activa8;
    }

    public void setActiva8(Boolean activa8) {
        this.activa8 = activa8;
    }

    public Boolean getActiva9() {
        return activa9;
    }

    public void setActiva9(Boolean activa9) {
        this.activa9 = activa9;
    }

    public Boolean getActiva10() {
        return activa10;
    }

    public void setActiva10(Boolean activa10) {
        this.activa10 = activa10;
    }

    public Boolean getActiva11() {
        return activa11;
    }

    public void setActiva11(Boolean activa11) {
        this.activa11 = activa11;
    }

    public Boolean getActiva12() {
        return activa12;
    }

    public void setActiva12(Boolean activa12) {
        this.activa12 = activa12;
    }

    public Boolean getActiva13() {
        return activa13;
    }

    public void setActiva13(Boolean activa13) {
        this.activa13 = activa13;
    }

    public Boolean getActiva14() {
        return activa14;
    }

    public void setActiva14(Boolean activa14) {
        this.activa14 = activa14;
    }

    public String getPuerta1() {
        return puerta1;
    }

    public void setPuerta1(String puerta1) {
        this.puerta1 = puerta1;
    }

    public String getPuerta2() {
        return puerta2;
    }

    public void setPuerta2(String puerta2) {
        this.puerta2 = puerta2;
    }

    public String getPuerta3() {
        return puerta3;
    }

    public void setPuerta3(String puerta3) {
        this.puerta3 = puerta3;
    }

    public String getPuerta4() {
        return puerta4;
    }

    public void setPuerta4(String puerta4) {
        this.puerta4 = puerta4;
    }

    public String getPuerta5() {
        return puerta5;
    }

    public void setPuerta5(String puerta5) {
        this.puerta5 = puerta5;
    }

    public String getPuerta6() {
        return puerta6;
    }

    public void setPuerta6(String puerta6) {
        this.puerta6 = puerta6;
    }

    public String getPuerta7() {
        return puerta7;
    }

    public void setPuerta7(String puerta7) {
        this.puerta7 = puerta7;
    }

    public String getPuerta8() {
        return puerta8;
    }

    public void setPuerta8(String puerta8) {
        this.puerta8 = puerta8;
    }

    public String getPuerta9() {
        return puerta9;
    }

    public void setPuerta9(String puerta9) {
        this.puerta9 = puerta9;
    }

    public String getPuerta10() {
        return puerta10;
    }

    public void setPuerta10(String puerta10) {
        this.puerta10 = puerta10;
    }

    public String getPuerta11() {
        return puerta11;
    }

    public void setPuerta11(String puerta11) {
        this.puerta11 = puerta11;
    }

    public String getPuerta12() {
        return puerta12;
    }

    public void setPuerta12(String puerta12) {
        this.puerta12 = puerta12;
    }

    public String getPuerta13() {
        return puerta13;
    }

    public void setPuerta13(String puerta13) {
        this.puerta13 = puerta13;
    }

    public String getPuerta14() {
        return puerta14;
    }

    public void setPuerta14(String puerta14) {
        this.puerta14 = puerta14;
    }
    @Transient
    public Boolean webcam = false;

    public Boolean getWebcam() {
        return webcam;
    }

    public void setWebcam(Boolean webcam) {
        this.webcam = webcam;
    }
    @Transient
    public Boolean ipcam = false;

    public Boolean getIpcam() {
        return ipcam;
    }

    public void setIpcam(Boolean ipcam) {
        this.ipcam = ipcam;
    }
    @Transient
    public String url = "";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @Transient
    public String entra1 = "";
    @Transient
    public String entra2 = "";
    
    @Transient
    public String barreras="";
    @Transient
    public Boolean seabretic = false;

    public String getEntra1() {
        return entra1;
    }

    public void setEntra1(String entra1) {
        this.entra1 = entra1;
    }

    public String getEntra2() {
        return entra2;
    }

    public void setEntra2(String entra2) {
        this.entra2 = entra2;
    }

    public String getBarreras() {
        return barreras;
    }

    public void setBarreras(String barreras) {
        this.barreras = barreras;
    }
    
    @Transient
    public Boolean seabrefac = false;
    @Transient
    public Double multa;
    @Transient
    public Boolean bloquear = false;
    @Transient
    public Boolean bloquearsalida = false;
    
    @Transient
    public String puertatic;
    @Transient
    public String puertafac;

    @Transient
    public String ipBarras1="";
    @Transient
    public String ipBarras2="";
    
    @Transient
    public String puertoBarras1="";
    @Transient
    public String puertoBarras2="";
    @Transient
    public String retardoEntrada="0";
    @Transient
    public String retardoSalida="1";
    
    public Boolean getBloquearsalida() {
        return bloquearsalida;
    }

    public void setBloquearsalida(Boolean bloquearsalida) {
        this.bloquearsalida = bloquearsalida;
    }

    public Boolean getBloquear() {
        return bloquear;
    }

    public void setBloquear(Boolean bloquear) {
        this.bloquear = bloquear;
    }

    public Boolean getSeabrefac() {
        return seabrefac;
    }

    public void setSeabrefac(Boolean seabrefac) {
        this.seabrefac = seabrefac;
    }

    public Boolean getSeabretic() {
        return seabretic;
    }

    public void setSeabretic(Boolean seabretic) {
        this.seabretic = seabretic;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public String getPuertafac() {
        return puertafac;
    }

    public void setPuertafac(String puertafac) {
        this.puertafac = puertafac;
    }

    public String getPuertatic() {
        return puertatic;
    }

    public void setPuertatic(String puertatic) {
        this.puertatic = puertatic;
    }

    public String getIpBarras1() {
        return ipBarras1;
    }

    public void setIpBarras1(String ipBarras1) {
        this.ipBarras1 = ipBarras1;
    }

    public String getIpBarras2() {
        return ipBarras2;
    }

    public void setIpBarras2(String ipBarras2) {
        this.ipBarras2 = ipBarras2;
    }

    public String getPuertoBarras1() {
        return puertoBarras1;
    }

    public void setPuertoBarras1(String puertoBarras1) {
        this.puertoBarras1 = puertoBarras1;
    }

    public String getPuertoBarras2() {
        return puertoBarras2;
    }

    public void setPuertoBarras2(String puertoBarras2) {
        this.puertoBarras2 = puertoBarras2;
    }

    public String getRetardoEntrada() {
        return retardoEntrada;
    }

    public void setRetardoEntrada(String retardoEntrada) {
        this.retardoEntrada = retardoEntrada;
    }

    public String getRetardoSalida() {
        return retardoSalida;
    }

    public void setRetardoSalida(String retardoSalida) {
        this.retardoSalida = retardoSalida;
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
        return "hibernate.Empresa[ruc=" + ruc + "]";
    }
}
