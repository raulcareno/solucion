/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Representante;
import jcinform.procesos.Administrador;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author Ismael Jadan
 */
public class generaClaves {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        Clients.showBusy("Procediendo", true);
//        int val = Messagebox.show("¿Seguro de eliminar, puede causar la pérdida de notas, si ya tiene registrado?", "Seguridad", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
        // TODO code application logic here
        String tipo = "EST";
        Administrador adm = new Administrador();
        Permisos c = new Permisos();

        if (tipo.contains("EMP")) {
            List<Empleados> emp = adm.query("Select o from Empleados as o where  o.clave is null ");

            for (Iterator<Empleados> it = emp.iterator(); it.hasNext();) {
                Empleados empleados = it.next();
                empleados.setClave(c.encriptar(empleados.getUsuario()));
                //System.out.println("1."+c.encriptar(empleados.getUsuario()));
                adm.actualizar(empleados);

            }
        } else if (tipo.contains("EST")) {
            try {
                //List<Estudiantes> emp = adm.query("Select o from Estudiantes as o where  o.usuario is null  or o.usuario = ''  ");
                List<Estudiantes> emp = adm.query("Select o from Estudiantes as o ");


                String user = "";
                String caracter = "JCINFORM";
                caracter += "JCQWERTYUIOPASDFGHJKLZXCVBNM";
                String caracterNumeros = "012345678901234567890123456789012345678901234567890123456789";
                //caracterNumeros += "0123456789";
//                String numericos = "012345678901234567890123456789";
                int numero_caracteres = 4;
                int total = caracter.length();
                int contando = 1;
                for (Iterator<Estudiantes> it = emp.iterator(); it.hasNext();) {
                    Estudiantes estudiantes = it.next();

                    String clave2 = "";

                    for (int a = 0; a < numero_caracteres; a++) {
                        clave2 += caracterNumeros.charAt(((Double) (total * Math.random())).intValue());
                    }

                    String clave = clave2.toUpperCase();

                    caracter = "" + estudiantes.getApellido().trim().replace(" ", "").toUpperCase();
                    caracter += "JC" + estudiantes.getNombre().trim().replace(" ", "").toUpperCase();
                    caracter = caracter.replace("Ñ", "").replace("Á", "").replace("É", "").replace("Í", "").replace("Ó", "").replace("Ú", "").replace("-", "");
                    numero_caracteres = 5;
                    total = caracter.length();
                    user = "";
                    for (int a = 0; a < numero_caracteres; a++) {
                        user += caracter.charAt(((Double) (total * Math.random())).intValue());
                    }
                    String usuario = user.toUpperCase();
                    estudiantes.setUsuario(usuario);
                    estudiantes.setClave(c.encriptar(clave));
                    adm.actualizar(estudiantes);
                    System.out.println("" + contando);
                    contando++;
                }
            } catch (Exception e) {
                System.out.println("ERROR EN GENERAR CLAVE" + e);
            }
        } else if (tipo.contains("REP")) {
            try {
                List<Representante> emp = adm.query("Select o from Representante as o where  o.usuario is null or o.usuario = '' ");


                String user = "";
                String caracter = "JCINFORM";
                caracter += "JCQWERTYUIOPASDFGHJKLZXCVBNM";
//                String numericos = "012345678901234567890123456789";
                int numero_caracteres = 10;
                int total = caracter.length();
                int contando = 1;
                for (Iterator<Representante> it = emp.iterator(); it.hasNext();) {
                    Representante estudiantes = it.next();

                    String clave2 = "";

                    for (int a = 0; a < numero_caracteres; a++) {
                        clave2 += caracter.charAt(((Double) (total * Math.random())).intValue());
                    }

                    String clave = clave2.toUpperCase();

                    caracter = "" + estudiantes.getApellidos().trim().replace(" ", "").toUpperCase();
                    caracter += "JC" + estudiantes.getNombres().trim().replace(" ", "").toUpperCase();
                    caracter = caracter.replace("Ñ", "").replace("Á", "").replace("É", "").replace("Í", "").replace("Ó", "").replace("Ú", "").replace("-", "");
                    numero_caracteres = 6;
                    total = caracter.length();
                    user = "";
                    for (int a = 0; a < numero_caracteres; a++) {
                        user += caracter.charAt(((Double) (total * Math.random())).intValue());
                    }
                    String usuario = user.toUpperCase();
                    estudiantes.setUsuario(usuario);
                    estudiantes.setClave(c.encriptar(clave));
                    adm.actualizar(estudiantes);
                    System.out.println("" + contando);
                    contando++;
                }
            } catch (Exception e) {
                System.out.println("ERROR EN GENERAR CLAVE" + e);
            }
        }
    }
}
