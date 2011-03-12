/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hibernate;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 *
 * @author geovanny
 */
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
    @Column(name = "puerto")
    private String puerto;
    @Column(name = "impresora")
    private String impresora;
    @Column(name = "impresora2")
    private String impresora2;

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

   


    public String getPuerto7() {
        return puerto7;
    }

    public void setPuerto7(String puerto7) {
        this.puerto7 = puerto7;
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
        return nombre;
    }

}
