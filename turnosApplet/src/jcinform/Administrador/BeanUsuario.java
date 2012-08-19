package jcinform.Administrador;


import java.util.*;
import java.text.*;

/** Clase abstracta para implementar un objeto empleado. */
public class BeanUsuario {

    private Calendar calendar;
    private Date fecha;
    
    private String dni;
    private String usuario;
    private String clave;
    private String ip;
    private String puerto;
    private String in;
    private String out;
    private String tipoTerminal;
    private String serie;
    private Boolean seCalifica;

    public BeanUsuario() {
        fecha = new Date();
        calendar = Calendar.getInstance();
    }

    /** Para Establecer el valor de DNI */
    public void setDNI(String dni) { 
        this.dni = dni; 
    }
    

    
    /** Para Obtener el valor de DNI */
    public String getDNI() { 
        return dni; 
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getTipoTerminal() {
        return tipoTerminal;
    }

    public void setTipoTerminal(String tipoTerminal) {
        this.tipoTerminal = tipoTerminal;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Boolean getSeCalifica() {
        return seCalifica;
    }

    public void setSeCalifica(Boolean seCalifica) {
        this.seCalifica = seCalifica;
    }
 
    
    
}