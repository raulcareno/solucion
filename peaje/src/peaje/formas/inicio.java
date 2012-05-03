/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peaje.formas;

import hibernate.cargar.GeneraXMLPersonal;
import hibernate.cargar.SerieDisco;
import hibernate.cargar.UsuarioActivo;
import hibernate.cargar.WorkingDirectory;
import java.io.File;

public class inicio {

    static UsuarioActivo datosConecta;
    static String separador = File.separatorChar + "";

    public static Boolean comprobar() {
        WorkingDirectory w = new WorkingDirectory();
         claves cl = new claves();
        String ubicacionDirectorio = w.get() + separador;
        if (ubicacionDirectorio.contains("build")) {
            ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
        }
        String unidad = ubicacionDirectorio.substring(0,1);
        datosConecta.setUbicacionDirectorio(ubicacionDirectorio);
        GeneraXMLPersonal pXml = new GeneraXMLPersonal();
        pXml.inicio();
        pXml.leerXML();
        
        String serie = SerieDisco.getSerialNumber(unidad);
        System.out.println("SERIE DE INSTALACIÓN: "+serie);
        datosConecta = pXml.user;
        String serie2 = cl.encriptar("jc" + serie);
        if (serie2.equals("" + datosConecta.getSerie())) {
            System.out.println("IGUALES");
            //return true;//producto valido
        } else {
            System.out.println("NO IGUAL");
            UsuarioActivo.setSerie(serie);
            return false;
        }
        
        try {
            String nombre = datosConecta.getNombre();
            //System.out.println("NOMB:" + nombre);
            if (nombre.equals("null") || datosConecta.getContrasenia().equals("null")) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        if (datosConecta == null) {
            return false;
        } else {
            return true;
        }

    }

    public static void main(String args[]) {
        if (comprobar()) {
            new frmPrincipal().show();
        } else {
            new frmConfiguracion().show();
        }


//           adios.start();
    }
}
