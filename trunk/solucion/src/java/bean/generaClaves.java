/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Inscripciones;
import jcinform.persistencia.Representante;
import jcinform.procesos.Administrador;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author Ismael Jadan
 */
public class generaClaves {

    public static Estudiantes existe(List<Estudiantes> estu, String apellido, String nombre) {
        for (Iterator<Estudiantes> it = estu.iterator(); it.hasNext();) {
            Estudiantes estudiantes = it.next();
            if (estudiantes.getApellido().contains(apellido) && estudiantes.getNombre().contains(nombre)) {
                return estudiantes;
            }
        }
        return null;

    }

    public static void copiarInscritosaEstudiantes() {
        Administrador adm = new Administrador();
//        Permisos c = new Permisos();
        List<Inscripciones> emp = adm.query("SELECT o FROM Inscripciones as o WHERE o.periodo = 8 AND o.preaprobado = TRUE  ");
        List<Estudiantes> estu = adm.query("Select o from Estudiantes as o ");

        int i = 1;
String codigo ="";
        for (Iterator<Inscripciones> it = emp.iterator(); it.hasNext();) {
            Inscripciones in = it.next();
            Estudiantes estudianteEncontrado = existe(estu, in.getApellido(), in.getNombre());
            
            if (estudianteEncontrado != null) {
                System.out.println(i + " " + in.getApellido() + " " + in.getNombre());
                Representante repreEst = estudianteEncontrado.getRepresentante();
                codigo += ", "+repreEst.getCodigorep();
                i++;
            } else {
                Representante repreEst = new Representante(adm.getNuevaClave("Representante", "codigorep"));

                repreEst.setIdentificacionrepre(in.getCedular());
                repreEst.setNombres(in.getNombrer());
                repreEst.setApellidos(in.getApellidor());
                repreEst.setDireccion(in.getDireccionr());
                repreEst.setTelefono(in.getTelefonor());
                repreEst.setEmail(in.getMailr());

                repreEst.setTipoidentificacion("C");
                repreEst.setIdentificacionfactura(in.getCedula());
                repreEst.setNombrefactura("");
                repreEst.setDirfactura("");
                repreEst.setTelfactura("");

                repreEst.setIdentificacionpadre(in.getCedulap());
                repreEst.setApepadre(in.getApellidopa());
                repreEst.setNompadre(in.getNombrepa());
                repreEst.setTelpadre(in.getTelefonopa());
                repreEst.setDirpadre(in.getDireccionpa());
                repreEst.setMailpadre(in.getMail());
                repreEst.setParentesco("");
                repreEst.setProfesionpadre(in.getTrabajopa());
                repreEst.setOcupacionpadre(in.getLugarpa());

                repreEst.setIdentificacionmadre(in.getCedulam());
                repreEst.setApemadre(in.getApellidma());
                repreEst.setNommadre(in.getNombrema());
                repreEst.setTelmadre(in.getTelefonoma());
                repreEst.setDirmadre(in.getDireccionma());
                repreEst.setMailmadre(in.getMail());
                repreEst.setProfesionmadre(in.getTrabajoma());
                repreEst.setOcupacionmadre(in.getLugarma());
                repreEst.setUsuario(in.getUsuario());
                repreEst.setClave(in.getClave());
                repreEst.setEstado(false);
                adm.guardar(repreEst);

                Estudiantes nuevoEst = new Estudiantes(adm.getNuevaClave("Estudiantes", "codigoest"));
                nuevoEst.setRepresentante(repreEst);
                nuevoEst.setCedula(in.getCedula());
                nuevoEst.setNombre(in.getNombre());
                nuevoEst.setApellido(in.getApellido());
                nuevoEst.setDireccion(in.getDireccion());
                nuevoEst.setBarrio("");
                nuevoEst.setTelefono(in.getTelefono());
                nuevoEst.setMail(in.getMail());
                nuevoEst.setFechanacimiento(in.getFechanacimiento());
                nuevoEst.setEstado(true);
                nuevoEst.setAdventista(false);
                nuevoEst.setTransporte(false);
                nuevoEst.setNobus("");
                nuevoEst.setAseguradora("");
                nuevoEst.setUsuario(null);
                nuevoEst.setClave(null);
                nuevoEst.setGenero(in.getGenero());

                nuevoEst.setReligion("");


                nuevoEst.setPendientes(false);
                nuevoEst.setBloquear(false);

                nuevoEst.setVivecon(in.getVivecon());
                nuevoEst.setHermanos(0);
                nuevoEst.setLugar(1);
                nuevoEst.setLugarnacimiento(in.getLugarnacimiento());
                nuevoEst.setProvincia("");
                nuevoEst.setCanton("");
                nuevoEst.setNacionalidad("Ecuatoriana");
                nuevoEst.setDiscapacidad(in.getDiscapacidad());
                nuevoEst.setTipodiscapacidad(in.getTdiscapacidad());
                nuevoEst.setIngpadre(new Double(0));
                nuevoEst.setIngmadre(new Double(0));
                nuevoEst.setIngotros(new Double(0));
                nuevoEst.setCasa("");
                nuevoEst.setEstadocivil("");
                nuevoEst.setAgua(true);
                nuevoEst.setLuz(true);
                nuevoEst.setSshh(true);
                nuevoEst.setPozo(false);
                nuevoEst.setTelefonob(false);
                nuevoEst.setInternet(false);
                nuevoEst.setMatriculado(false);
                nuevoEst.setAplico(false);
                adm.guardar(nuevoEst);


            }

        }
        System.out.println("CODIGOS: "+codigo);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Clients.showBusy("Procediendo", true);
//        int val = Messagebox.show("¿Seguro de eliminar, puede causar la pérdida de notas, si ya tiene registrado?", "Seguridad", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
        // TODO code application logic here
        String tipo = "COPIAR";
        Administrador adm = new Administrador();
        Permisos c = new Permisos();
        if (tipo.contains("COPIAR")) {
            copiarInscritosaEstudiantes();
            return;

        } else if (tipo.contains("EMP")) {
            List<Empleados> emp = adm.query("Select o from Empleados as o where  o.usuario is null ");

            for (Iterator<Empleados> it = emp.iterator(); it.hasNext();) {
                Empleados empleados = it.next();
                empleados.setUsuario(empleados.getIdentificacion());
                empleados.setClave(c.encriptar(empleados.getIdentificacion()));
                
                System.out.println("1." + (empleados.getUsuario()));
                adm.actualizar(empleados);

            }
        } else if (tipo.contains("EST")) {
            try {
                //List<Estudiantes> emp = adm.query("Select o from Estudiantes as o where  o.usuario is null  or o.usuario = ''  ");
                List<Estudiantes> emp = adm.query("Select o from Estudiantes as o where o.usuario is null ");


                String user = "";
                String caracter = "";
                caracter += "A";
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
                String caracterNumeros = "012345678901234567890123456789012345678901234567890123456789";
                int numero_caracteres = 10;
                int total = caracter.length();
                int contando = 1;
                for (Iterator<Representante> it = emp.iterator(); it.hasNext();) {
                    Representante estudiantes = it.next();

                    String clave2 = "";

                    for (int a = 0; a < numero_caracteres; a++) {
                        clave2 += caracterNumeros.charAt(((Double) (total * Math.random())).intValue());
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
        } else if (tipo.contains("INSC")) {
            try {
                //List<Estudiantes> emp = adm.query("Select o from Estudiantes as o where  o.usuario is null  or o.usuario = ''  ");
                List<Inscripciones> emp = adm.query("SELECT o FROM Inscripciones as o WHERE o.periodo = 8 AND o.preaprobado = TRUE  ");


                String user = "";
                String caracter = "";
                caracter += "A";
                String caracterNumeros = "012345678901234567890123456789012345678901234567890123456789";
                //caracterNumeros += "0123456789";
//                String numericos = "012345678901234567890123456789";
                int numero_caracteres = 4;
                int total = caracter.length();
                int contando = 1;
                for (Iterator<Inscripciones> it = emp.iterator(); it.hasNext();) {
                    Inscripciones estudiantes = it.next();

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
        }
    }
}
