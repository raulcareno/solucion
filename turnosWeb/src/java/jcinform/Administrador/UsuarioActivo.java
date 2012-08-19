package jcinform.Administrador;

import java.io.File;

public class UsuarioActivo {

    private static String nombre;
    private static String contrasenia;
    public static String ip;
    private static String puerto;
    private static String in;
    private static String tipoTerminal;
    private static String out;
    private static String serie;
    private static Boolean listaConexion;
        private static Boolean seCalifica;
    private static String ubicacionDirectorio;
    static String separador = File.separatorChar + "";

    public UsuarioActivo() {
        listaConexion = true;
    }

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

    public static String getTipoTerminal() {
        return tipoTerminal;
    }

    public static void setTipoTerminal(String tipoTerminal) {
        UsuarioActivo.tipoTerminal = tipoTerminal;
    }

    public static Boolean getListaConexion() {
        return listaConexion;
    }

    public static void setListaConexion(Boolean listaConexion) {
        UsuarioActivo.listaConexion = listaConexion;
    }

    public static String getSerie() {
        return serie;
    }

    public static void setSerie(String serie) {
        UsuarioActivo.serie = serie;
    }

    public static Boolean getSeCalifica() {
        return seCalifica;
    }

    public static void setSeCalifica(Boolean seCalifica) {
        UsuarioActivo.seCalifica = seCalifica;
    }

    public static String getUbicacionDirectorio() {
        if (ubicacionDirectorio == null) {
            WorkingDirectory w = new WorkingDirectory();
            String ubi = w.get() + separador;
            if (ubi.contains("build")) {
                int ind = ubi.indexOf("build");
                ubicacionDirectorio = ubi.substring(0, ind);
            }
        }
        return ubicacionDirectorio;
    }

    public static void setUbicacionDirectorio(String ubicacionDirectorio) {
        UsuarioActivo.ubicacionDirectorio = ubicacionDirectorio;
    }
}
