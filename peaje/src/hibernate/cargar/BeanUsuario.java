package hibernate.cargar;


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
   
    

//    /** Para Obtener el valor de Fecha de Nacimiento */
//    public String getFecNac() {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
//        return formatter.format ( fecnac );
//    }
    
    /** Para Obtener el valor de Salario */
//    public String getSalario() {
//        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.UK);
//        DecimalFormat decimalFormat = (DecimalFormat)numberFormat ;
//        decimalFormat.applyPattern("###,###,###,###.00");
//        return decimalFormat.format ( new Double(salario) ) ;
//    }
    
//    /** Para Obtener el valor de Edad */
//    public String getEdad() {
//        calendar.setTime(fecha);
//        Integer anho = new Integer(calendar.get(Calendar.YEAR));
//        calendar.setTime(fecnac);
//        Integer anhoNac = new Integer(calendar.get(Calendar.YEAR));
//        Integer anhos = anho-anhoNac;
//        return anhos.toString();
//    }
}