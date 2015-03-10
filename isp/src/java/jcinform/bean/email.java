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
            Empresa empre = empresas.get(0);
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
                    "RECUPERACION DE CLAVE " + emp.getApellidos() + " " + emp.getNombres(), empre.getUsuariomail(), empre.getClavemail(), empre.getSmtp(), empre.getPuerto(), empre.getAutorizacion(), empre.getStar());
            if (estado) {
                return "[OK] " + emp.getEmail();
            } else {
                return "no";

            }

        } else {
            return "no";
        }
    }

    public String soporteTecnico(String cedula,Soporte sop) {
//        claves val = new claves();
        
                Administrador adm = new Administrador();
//                sop = (Soporte)adm.buscarClave(new Integer(4), Soporte.class);
        Empleados emp = (Empleados) adm.querySimple("Select o from Empleados as o where o.identificacion = '" + cedula + "' ");
        if (emp != null) {

            List<Empresa> empresas = adm.query("Select o from Empresa as o");
            Empresa empre = empresas.get(0);
            mensaje = "<!DOCTYPE HTML PUBLIC "
                    + " http://www.w3.org/TR/html4/loose.dtd'> "
                    + " <html>"
                    + " <head>"
                    + " <title>Soporte T&eacute;cnico</title>"
                    + " <meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>"
                    + " <style type='text/css'>"
                    + " <!--"
                    + " .style1 {font-weight: bold}"
                    + " -->"
                    + " </style>"
                    + " </head>"
                    + " <body>"
                    + " <br>"
                    + " <br>"
                    + " <table width='905' border='1' cellpadding='0' cellspacing='0'>"
                    + " <tr>"
                    + " <td colspan='2'><p><strong>Orden / Ticket N&deg; </strong></p></td>"
                    + "     <td colspan='6'>"+sop.getNoorden()+"<br></td>"
                    + "     <td colspan='3'><p><strong>Fecha de Orden / Ticket </strong></p></td>"
                    + "     <td colspan='2'><p><strong>"+sop.getFechaorden()+"</strong></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13'><p><strong>INFORMACION DEL CLIENTE </strong></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2'><p><strong>Nombre </strong></p></td>"
                    + "     <td colspan='11'>"+sop.getClientes().getApellidos()+" "+sop.getClientes().getNombres()+ "</td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2'><p><strong>Contrato </strong></p></td>"
                    + "     <td colspan='4'>"+sop.getContrato().getContrato()+"<br></td>"
                    + "     <td colspan='4'><p ><strong>Fecha de Instalaci&oacute;n </strong></p></td>"
                    + "     <td colspan='3'><p><strong>"+sop.getContrato().getFechainstalacion().toLocaleString()+"<br>"
                    + "     </strong></p></td>"
                    + " </tr>"
                    + "   <tr>"
                    + "     <td colspan='2'><p><strong>Direcci&oacute;n </strong></p></td>"
                    + "     <td colspan='11'>"+sop.getDireccion()+"</td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2'><p><strong>Tel&eacute;fono </strong></p></td>"
                    + "     <td colspan='4'>"+sop.getTelefono()+"<br></td>"
                    + "     <td colspan='4'><p ><strong>Email </strong></p></td>"
                    + "     <td colspan='3'>"+sop.getClientes().getEmail()+"</td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13'><p><strong>RESPONSABLE T&Eacute;CNICO </strong></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2'><p><strong>Nombre </strong></p></td>"
                    + "     <td colspan='11'><p>"+sop.getTecnico().getApellidos()+" "+ sop.getTecnico().getNombres() +"<br>"
                    + "     </p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2'><p><strong>Email </strong></p></td>"
                    + "    <td colspan='5'>"+sop.getTecnico().getEmail()+"<br></td>"
                    + "     <td colspan='4'><p >Tel&eacute;fono </p></td>"
                    + "     <td colspan='3'>"+sop.getTecnico().getTelefono()+"<br></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13'><p><strong>DATOS T&Eacute;CNICOS </strong></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2'><p><strong>GPS </strong></p></td>"
                    + "     <td width='156'><p ><strong>Latitud </strong></p></td>"
                    + "     <td colspan='3'>"+sop.getContrato().getLatitud()+"</td>"
                    + "     <td colspan='3'><p ><strong>Longitud </strong></p></td>"
                    + "     <td width='171'>"+sop.getContrato().getLongitud()+"</td>"
                    + "     <td colspan='2'><p >&nbsp;Altura </p></td>"
                    + "     <td width='196'><p>."
                    + "     </p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2'><p><strong>L&iacute;nea de Vista </strong></p></td>"
                    + "     <td colspan='7'><p><br>"
                    + "     </p></td>"
                    + "     <td width='171'><p ><strong>Nodo/Base </strong></p></td>"
                    + "     <td colspan='3'>"+sop.getContrato().getRadios().getNodos().getNombre()+"</td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2'><p><strong>Direcci&oacute;n IP </strong></p></td>"
                    + "     <td colspan='4'>"+sop.getContrato().getIp()+"<br></td>"
                    + "  "
                    + "     <td colspan='4'><p ><strong>Regulador V </strong></p></td>"
                    + "     <td colspan='3'>.<br></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13'><p><strong>HORARIO ATENDIDO </strong></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2'><p><strong>Fecha de Asistencia </strong></p></td>"
                    + "     <td colspan='4'>$HORAASISTENCIA<br></td>"
                    + "     <td colspan='3'><p ><strong>Hora Inicio </strong></p></td>"
                    + "     <td width='171'><p><strong>$HORAINICIO</strong></p></td>"
                    + "     <td colspan='2'><blockquote>"
                    + "       <p ><strong>Hora Fin </strong></p>"
                    + "     </blockquote></td>"
                    + "     <td width='196'>$HORAFIN</td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13'><strong>VENDEDOR</strong></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13'>"+sop.getContrato().getEmpleados1()+"</td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13'><p><strong>PROBLEMA / REQUERIMIENTO </strong></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13'>"+sop.getObservacion()+"</td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13'><p><strong>SOLUCI&Oacute;N </strong></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13'>"+sop.getObservacion2()+"</td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13'><p><strong>COSTOS </strong></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2'><p><strong>Valor </strong></p></td>"
                    + "     <td colspan='5'>$VALOR<br></td>"
                    + "     <td colspan='3'><p>Factura N&deg; </p></td>"
                    + "     <td colspan='3' class='style1'>$FACTURA</td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2' class='style1'><p>Comentarios </p></td>"
                    + "     <td colspan='11' class='style1'><br></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13' class='style1'><p>EQUIPOS Y MATERIALES UTILIZADOS </p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2' class='style1'><p>Radio/Antena </p></td>"
                    + "     <td colspan='5' class='style1'><br></td>"
                    + "     <td colspan='3' class='style1'><p >Cable </p></td>"
                    + "     <td colspan='3' class='style1'><br></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2' class='style1'><p>Router </p></td>"
                    + "     <td colspan='5' class='style1'><br></td>"
                    + "     <td colspan='3' class='style1'><p >Base Met&aacute;lica </p></td>"
                    + "     <td colspan='3' class='style1'><br></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2' class='style1'><p>Torre </p></td>"
                    + "     <td colspan='5' class='style1'><br></td>"
                    + "     <td colspan='3' class='style1'><p >M&aacute;stil </p></td>"
                    + "     <td colspan='3' class='style1'><br></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='2' class='style1'><p>Otros </p></td>"
                    + "     <td colspan='11' class='style1'>.</td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13' class='style1'><p>OBSERVACIONES / RECOMENDACIONES </p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13' valign='bottom' class='style1'><table cellspacing='0'cellpadding='0'>"
                    + "         <tr>"
                    + "           <td width='751'><p>$OBSERVACIONES<br>"
                    + "           </p></td>"
                    + "         </tr>"
                    + "     </table></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13' class='style1'><p>COMPROBACI&Oacute;N DEL SERVICIO </p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td width='116' class='style1'><p>Nombre </p></td>"
                    + "     <td colspan='3' class='style1'><p>&nbsp;.</p></td>"
                    + "     <td colspan='6' class='style1'><p>Parentesco </p></td>"
                    + "     <td colspan='3' class='style1'><p>.</p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13' class='style1'><p>CONTACTOS CSEDNET </p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='3' class='style1'><p>Oficinas </p></td>"
                    + "     <td colspan='7' class='style1'><p>2763-544 / 2751-382 </p></td>"
                    + "     <td colspan='3' class='style1'></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='3' class='style1'><p>Soporte T&eacute;cnico </p></td>"
                    + "     <td colspan='7' class='style1'><p>Ext.111, 102.&nbsp;M&oacute;vil: 0980-679-373 </p></td>"
                    + "     <td colspan='3' class='style1'><p><a href='mailto:soporte@csed.com.ec'>soporte@csed.com.ec </a></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='3' class='style1'><p>Dto. Comercializaci&oacute;n </p></td>"
                    + "     <td colspan='7' class='style1'><p>Francisco Fiallo, Ext 112 - 0981437757 </p></td>"
                    + "     <td colspan='3' class='style1'><p><a href='mailto:ffiallo@csed.net.ec'>ffiallo@csed.net.ec </a></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='3' class='style1'><p>Juan Mateus, Ext 112 - 0983615050 </p></td>"
                    + " 	    <td  colspan='7' class='style1'><p></p></td>"
                    + "     <td colspan='3' class='style1'><p><a href='mailto:amateus@csed.net.ec'>amateus@csed.net.ec </a></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='3' class='style1'><p>Financiero </p></td>"
                    + "     <td colspan='7' class='style1'><p>Ing. Yessica Ramos, Ext 0 - 0986-971-609 </p></td>"
                    + "     <td colspan='3' class='style1'><p><a href='mailto:yramos@csed.com.ec'>yramos@csed.com.ec </a></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='3' class='style1'><p>Administraci&oacute;n Red </p></td>"
                    + "     <td colspan='7' class='style1'><p>Ing. Kl&eacute;ver Villa, Ext 111 -&nbsp;&nbsp;0999-188-313 </p></td>"
                    + "     <td colspan='3' class='style1'><p><a href='mailto:kvilla@csed.com.ec'>kvilla@csed.com.ec </a></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='3' class='style1'><p>Administraci&oacute;n CSEDNET </p></td>"
                    + "     <td colspan='7' class='style1'><p>Sr. Juan Gualotu&ntilde;a, Ext 110 - 0994-216-186 </p></td>"
                    + "     <td colspan='3' class='style1'><p><a href='mailto:jcgualotuna@csed.com.ec'>jcgualotuna@csed.com.ec </a></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='3' class='style1'><p>Gerencia General </p></td>"
                    + "     <td colspan='7' class='style1'><p>Dra. Emilia V&eacute;lez Castro, Tel&eacute;f.. 2756982 </p></td>"
                    + "     <td colspan='3' class='style1'><p><a href='mailto:emiillescas@yahoo.com'>emiillescas@yahoo.com </a></p></td>"
                    + "   </tr>"
                    + "   <tr>"
                    + "     <td colspan='13' class='style1'><p>GR&Aacute;FICOS </p></td>"
                    + "   </tr>"
                    + " </table>"
                    + " </body> "
                    + " </html> ";
            Boolean estado = EnviarAutenticacion.RecuperarClave(emp.getEmail().trim(), mensaje,
                    "ASISTENCIA CLIENTE: " + sop.getClientes().getApellidos() + " "+sop.getClientes().getNombres(), empre.getUsuariomail(), empre.getClavemail(), empre.getSmtp(), empre.getPuerto(), empre.getAutorizacion(), empre.getStar());
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

    public static Boolean RecuperarClave(String email, String mensaje, String tema, String emailInstitucion, String clave, String host, String puerto, Boolean autorizacion, Boolean star) {
//        String host ="smtp.gmail.com";//Suponiendo que el servidor SMTPsea la propia máquina
        //String from ="setecompu.ec@gmail.com";
        String from = emailInstitucion;
//        String to = email;
        Properties prop = new Properties();
        prop.put("mail.host", host.trim());
        prop.setProperty("mail.smtp.port", puerto.trim());
        prop.setProperty("mail.smtp.starttls.enable", "" + star);
        prop.put("mail.smtp.auth", "" + autorizacion);
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

    public static void EnviarCorreo(ArrayList email, String mensaje, String tema, String emailInstitucion, String clave, String host, String puerto, String respuestaA, Boolean autorizacion, Boolean star) {
//        String host ="smtp.gmail.com";//Suponiendo que el servidor SMTPsea la propia máquina
        //String from ="setecompu.ec@gmail.com";
        String from = emailInstitucion;
//        String to = email;
        Properties prop = new Properties();
        prop.put("mail.host", host.trim());
        prop.setProperty("mail.smtp.port", puerto.trim());
        prop.setProperty("mail.smtp.starttls.enable", "" + star);
        prop.put("mail.smtp.auth", "" + autorizacion);
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
