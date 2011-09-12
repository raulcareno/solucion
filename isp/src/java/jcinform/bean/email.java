package jcinform.bean;

import java.util.*;
 
import javax.mail.*;
import javax.mail.internet.*;
import jcinform.conexion.Administrador;
import jcinform.conexion.claves;
import jcinform.persistencia.*;
 


public class email {

    public String correos;
    public String tema;
 
    public String mensaje;

    public String recuperarClave(String cedula) {
        claves val = new claves();
        Administrador adm = new Administrador();
        Empleados emp = (Empleados) adm.querySimple("Select o from Empleados as o where o.identificacion = '" + cedula + "' ");
        if (emp != null) {
         
        List<Empresa> empresas = adm.query("Select o from Empresa as o");
        Empresa empre =  empresas.get(0);
            mensaje = "<html><p> <b> Su usuario es: </b>" + emp.getUsuario() + "<p> "
                    + "<b> Su password es:</b> " + val.desencriptar(emp.getClave()) + " <p> "
                    + "Le recordamos que debe cambiar su clave periodicamente para su mayor seguridad"
                    + "<p>."
                    + "<p>"
                    + "<p>"
                    + "<p>."
                    + "LA ADMINISTRACION "
                    + "<p>"
                    + "<p>."
                    + "<p>"
                    + "<p>."
                    + "<p>"
                    + "<p>."
                    + "<hr>."
                    + "Desarrollado por JCINFORM fono: 080162 211 "
                    + "<p>"
                    + "<hr>"
                    + "</html> ";
            Boolean estado = EnviarAutenticacion.RecuperarClave(emp.getEmail().trim(), mensaje,
                    "RECUPERACION DE CLAVE " + emp.getApellidos() + " " + emp.getNombres(), empre.getUsuariomail(), empre.getClavemail(), empre.getSmtp(), empre.getPuerto(),empre.getAutorizacion(),empre.getStar());
            if (estado) {
                return "[OK] " + emp.getEmail();
            } else {
                return "no";

            }

        } else {
            return "no";
        }
    }
 
    
    public email() {
 
        mensaje = "";
        correos = "";
    }

    private boolean validaEmail(String strText) {
        int iSw = 0;
        for (int i = 0; i < strText.length(); i++) {
            if (strText.charAt(i) == ' ') {
                //   strMsg = "ERROR: El texto no debe contener espacios en blanco";
                // JOptionPane.showMessageDialog(new JFrame(), strMsg);
                return false;
            }
            if (strText.charAt(i) == '@') {
                iSw++;
            }
        }
        if (iSw != 1) {
            //  strMsg = "ERROR: La direccion email no es correcta" ;
            //  JOptionPane.showMessageDialog(new JFrame(), strMsg);
            return false;
        }
        return true;
    }

//    public void enviarEmail(Periodo periodo, Empleados empleado) {
////        Cursos obj = curso;
//        try {
//            Administrador adm = new Administrador();
//            List parame = adm.query("Select o from ParametrosGlobales as o where o.variable= 'IPPUBLICA'");
//            ParametrosGlobales para = (ParametrosGlobales) parame.get(0);
//            String ip = para.getCvalor();
//            mensaje = mensaje.replace("/solucion", "http://" + ip + "/solucion");
//            String direcciones = correos;
//            StringTokenizer tokens = new StringTokenizer(direcciones, ";");
//            ArrayList matriculados2 = new ArrayList();
//            int i = 0;
//            while (tokens.hasMoreTokens()) {
//                String str = tokens.nextToken();
//                str = str.replace(" ", "");
//                if (!str.equals("") && str.contains("@")) {
//                    if (!matriculados2.contains(str)) {
//                        if (validaEmail(str.replace(" ", ""))) {
//                            matriculados2.add(str.replace(" ", ""));
//                            System.out.println(str);
//                        }
//                    }
//
//
//                }
//                i++;
//            }
//            if (matriculados2.size() > 0) {
//                EnviarAutenticacion.EnviarCorreo(matriculados2, mensaje, tema, periodo.getInstitucion().getUsuariomail(), periodo.getInstitucion().getClavemail(), periodo.getInstitucion().getSmtp(), periodo.getInstitucion().getPuerto(), empleado.getEmail(),periodo.getInstitucion().getAutorizacion(),periodo.getInstitucion().getStar());
//            }
//        } catch (Exception e) {
//            System.out.println("" + e);
//        }
//
//    }

    
    public String getTema() {
        return tema;
    }

    public void setTema(String asunto) {
        this.tema = asunto;
    }

  

    public String getCorreos() {
        return correos;
    }

    public void setCorreos(String correos) {
        this.correos = correos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
 

 
}
class EnviarAutenticacion {

    public static Boolean RecuperarClave(String email, String mensaje, String tema, String emailInstitucion, String clave, String host, String puerto,Boolean autorizacion, Boolean star) {
//        String host ="smtp.gmail.com";//Suponiendo que el servidor SMTPsea la propia máquina
        //String from ="setecompu.ec@gmail.com";
        String from = emailInstitucion;
//        String to = email;
        Properties prop = new Properties();
        prop.put("mail.host", host.trim());
        prop.setProperty("mail.smtp.port", puerto.trim());
        prop.setProperty("mail.smtp.starttls.enable", ""+star);
        prop.put("mail.smtp.auth", ""+autorizacion);
        try {
            SMTPAutenticacion auth = new SMTPAutenticacion(emailInstitucion, clave);
            Session session = Session.getInstance(prop, auth);
            InternetAddress[] emailsA = new InternetAddress[1];
            int i = 0;
            emailsA[i] = new InternetAddress(email + "");
            System.out.println("***********************" + emailInstitucion);
            Message msg = getTraerMensaje(session, tema, emailsA, mensaje, emailInstitucion, emailInstitucion);
            Transport.send(msg);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR AL ENVIAR " + e);
            ExceptionManager.ManageException(e);
            return false;
        }

    }

    public static void EnviarCorreo(ArrayList email, String mensaje, String tema, String emailInstitucion, String clave, String host, String puerto, String respuestaA,Boolean autorizacion, Boolean star) {
//        String host ="smtp.gmail.com";//Suponiendo que el servidor SMTPsea la propia máquina
        //String from ="setecompu.ec@gmail.com";
        String from = emailInstitucion;
//        String to = email;
        Properties prop = new Properties();
        prop.put("mail.host", host.trim());
        prop.setProperty("mail.smtp.port", puerto.trim());
        prop.setProperty("mail.smtp.starttls.enable", ""+star);
        prop.put("mail.smtp.auth", ""+autorizacion);
        try {
            SMTPAutenticacion auth = new SMTPAutenticacion(emailInstitucion, clave);
            Session session = Session.getInstance(prop, auth);
            InternetAddress[] emailsA = new InternetAddress[email.size()];
            int i = 0;
            for (Iterator it = email.iterator(); it.hasNext();) {
                String object = (String) it.next();
                String to = object.replace(" ", "");
                emailsA[i] = new InternetAddress(to + "");
                i++;
            }
            System.out.println("***********************" + emailInstitucion);
            Message msg = getTraerMensaje(session, tema, emailsA, mensaje, emailInstitucion, respuestaA);
            System.out.println("I: " + (new Date().toLocaleString()));
            Transport.send(msg);
            System.out.println("F: " + (new Date().toLocaleString()));
        } catch (Exception e) {
            System.out.println("ERROR AL ENVIAR " + e);
            ExceptionManager.ManageException(e);
        }

    }

    private static MimeMessage getTraerMensaje(Session session, String from, InternetAddress[] to, String mensaje, String emailInstitucion, String respuestaA) {
        try {

            MimeMessage msg = new MimeMessage(session);
            msg.setSubject("" + from);
            if (!respuestaA.equals(null) && !respuestaA.equals("")) {
                InternetAddress[] emailsA = new InternetAddress[1];
                int i = 0;
                emailsA[i] = new InternetAddress(respuestaA + "");
                msg.setReplyTo(emailsA);
                msg.setSender(new InternetAddress(emailInstitucion));
                msg.setFrom(new InternetAddress("" + emailInstitucion));
            } else {
                msg.setFrom(new InternetAddress("" + emailInstitucion));
            }

            msg.setText(mensaje, "ISO-8859-1", "html");
            //msg.set
            //msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //msg.addRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress("geovanny1781@yahoo.com"), new InternetAddress("jcinform@gmail.com"),new InternetAddress("geovanny1781@hotmail.com") });
            msg.addRecipients(Message.RecipientType.BCC, to);
            return msg;
        } catch (MessagingException ex) {

            ExceptionManager.ManageException(ex);
            return null;

        }


    }
}

class SMTPAutenticacion extends javax.mail.Authenticator {

    public SMTPAutenticacion(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public PasswordAuthentication getPasswordAuthentication() {
        String username = "" + this.user;
        String password = "" + this.pass;
        return new PasswordAuthentication(username, password);
    }
    public String user;
    public String pass;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

class ExceptionManager {

    public static void ManageException(Exception e) {
//        System.out.println ("Se ha producido una exception");
//        System.out.println (e.getTraerMensaje());
        e.printStackTrace(System.out);
    }
}
