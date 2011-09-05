package hibernate.cargar;

public class UsuarioActivo {
    private static String nombre;
    private static String contrasenia;
    private static String ip;
    private static String puerto;
    private static String in;
    private static String out;
        
    public UsuarioActivo() {}
    
    public static void setNombre(String nombre) {
        UsuarioActivo.nombre = nombre;
    }

    public static void setContrasenia(String contrasenia) {
        UsuarioActivo.contrasenia = contrasenia;
    }        

    public static String getNombre() {
        return nombre;
    }

    public static String getContrasenia() {
        return contrasenia;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        UsuarioActivo.ip = ip;
    }

    public static String getPuerto() {
        return puerto;
    }

    public static void setPuerto(String puerto) {
        UsuarioActivo.puerto = puerto;
    }

    public static String getIn() {
        return in;
    }

    public static void setIn(String in) {
        UsuarioActivo.in = in;
    }

    public static String getOut() {
        return out;
    }

    public static void setOut(String out) {
        UsuarioActivo.out = out;
    }

    
    

}
