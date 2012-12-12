/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import bean.Utilerias;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jcinform.persistencia.Aspirantes;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Institucion;
import jcinform.persistencia.Matriculas;
import jcinform.procesos.Administrador;
import jcinform.procesos.claves;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class RecuperarBean {

    public String correos;
    public String tema;
    public String mensaje;
    public Institucion institucion;
    public String cedula;
    public Administrador adm;
    /**
     * Creates a new instance of RecuperarBean
     */
    public RecuperarBean() {
            mensaje = "";
            correos = "";
            adm = new Administrador(); 
         try {
                List<Institucion> user = adm.query("Select o from Institucion as o ");    
                institucion = user.get(0);
        } catch (Exception e) {
             System.out.println("recuperarBean, NO EXISTEN INSTITUCION");
        }
            
    }
    

    public String recuperarClave() {
        claves val = new claves();
                   FacesContext context = FacesContext.getCurrentInstance();
         Empleados emp;
         try {
         emp = (Empleados) adm.querySimple("Select o from Empleados as o where o.idEmpleados = '" + cedula + "' ");   
        } catch (Exception e) {
            emp = null;
        }
         
        
        if (emp != null) {
             mensaje = "<html><p> <b> Su usuario es: </b>" + emp.getUsuario() + "<p> "
                    + "<b> Su password es:</b> " + val.desencriptar(emp.getClave()) + " <p> "
                    + "Le recordamos que debe cambiar su clave periodicamente para su mayor seguridad"
                    + "<p>."
                    + "<p>"
                    + "<p>"
                    + "<br/>"
                    + "LA ADMINISTRACION "
                    + "<p>"
                    + "<br/>" 
                    + "<p>"
                    + "<br/>"
                    + "<p>"
                    + "<br/>"
                    + "<hr>"
                    + "Desarrollado por <a href=http://www.jcinform.com> JC INFORM </a> "
                    + "<p>"
                    + "<hr>"
                    + "</html> ";
             try {
            System.out.println(""+emp.getEmail().trim()+ mensaje+
                    "RECUPERACION DE CLAVE " + emp.getApellidoPaterno() + " " + emp.getNombre()+ 
                    institucion.getEmail()+ 
                    institucion.getClave()+ 
                    institucion.getSmtp()+ 
                    institucion.getPuerto()+
                    institucion.getAutorizacion()+
                    institucion.getStar());                  
            } catch (Exception e) {
                 System.out.println("error: "+e.getMessage());
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ud. no tiene registrado un email en su cuenta...!","Ud. no tiene registrado un email en su cuenta...!"));
                return "no";
            }
            
            Boolean estado = EnviarAutenticacion.RecuperarClave(emp.getEmail().trim(), mensaje,
                    "RECUPERACION DE CLAVE " + emp.getApellidoPaterno() + " " + emp.getNombre(), institucion.getEmail(), institucion.getClave(), institucion.getSmtp(), institucion.getPuerto(),institucion.getAutorizacion(),institucion.getStar());
 
 
            if (estado) {
                
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_INFO,"Se ha enviado un email a: "+emp.getEmail()+" con sus datos para el ingreso","Se ha enviado un email a: "+emp.getEmail()+" con sus datos para el ingreso"));
                return "[OK] " + emp.getEmail();
            } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha enviado el email...!","No se ha enviado el email...!"));
                return "no";

            }

        } else {
                                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha encontrado el estudiante con la cédula ingresada...!","No se ha encontrado el estudiante con la cédula ingresada...!"));
            return "no";
        }
    }
     
    public String confirmarAspirante(String cedula) {
            claves val = new claves();
                   FacesContext context = FacesContext.getCurrentInstance();
         Aspirantes emp;
         try {
         emp = (Aspirantes) adm.querySimple("Select o from Aspirantes as o where o.idAspirantes= '" + cedula + "' ");   
        } catch (Exception e) {
            emp = null;
        }
         
        
        if (emp != null) {
             mensaje = "<html><p> Gracias  " + emp.getNombres() + " "
                     + " por confiar en nosotros. </b></p> <p>Nos estaremos comunicando contigo <p> "
                    + " "
                    + "<p>."
                    + "<p>"
                    + "<p>"
                    + "<br/>"
                    + "LA ADMINISTRACION "
                    + "<p>"
                    + "<br/>" 
                    + "<p>"
                    + "<br/>"
                    + "<p>"
                    + "<br/>"
                    + "<hr>"
                    + "Desarrollado por <a href=http://www.jcinform.com> JC INFORM </a> "
                    + "<p>"
                    + "<hr>"
                    + "</html> ";
             try {
            System.out.println(""+emp.getEmail().trim()+ mensaje+
                    "PRE-INSCRIPCION " + emp.getNombres()+ 
                    institucion.getEmail()+ 
                    institucion.getClave()+ 
                    institucion.getSmtp()+ 
                    institucion.getPuerto()+
                    institucion.getAutorizacion()+
                    institucion.getStar());                  
            } catch (Exception e) {
                 System.out.println("error: "+e.getMessage());
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ud. no tiene registrado un email en su cuenta...!","Ud. no tiene registrado un email en su cuenta...!"));
                return "no";
            }
            
            Boolean estado = EnviarAutenticacion.RecuperarClave(emp.getEmail().trim(), mensaje,
                    "PRE-INSCRIPCION " + emp.getNombres(), institucion.getEmail(), institucion.getClave(), institucion.getSmtp(), institucion.getPuerto(),institucion.getAutorizacion(),institucion.getStar());
 
 
            if (estado) {
                
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_INFO,"Se ha enviado un email a: "+emp.getEmail()+" con sus datos para el ingreso","Se ha enviado un email a: "+emp.getEmail()+" con sus datos para el ingreso"));
//                return "[OK] " + emp.getEmail();
            } else {
//                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha enviado el email...!","No se ha enviado el email...!"));
//                return "no";

            }
            return "ok";

        } else {
//                                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha encontrado el estudiante con la cédula ingresada...!","No se ha encontrado el estudiante con la cédula ingresada...!"));
            return "no";
        }
    }
    public String recuperarClave(String cedula) {
            claves val = new claves();
                   FacesContext context = FacesContext.getCurrentInstance();
         Empleados emp;
         try {
         emp = (Empleados) adm.querySimple("Select o from Empleados as o where o.idEmpleados = '" + cedula + "' ");   
        } catch (Exception e) {
            emp = null;
        }
         
        
        if (emp != null) {
             mensaje = "<html><p> <b> Su usuario es: </b>" + emp.getUsuario() + "<p> "
                    + "<b> Su password es:</b> " + val.desencriptar(emp.getClave()) + " <p> "
                    + "Le recordamos que debe cambiar su clave periodicamente para su mayor seguridad"
                    + "<p>."
                    + "<p>"
                    + "<p>"
                    + "<br/>"
                    + "LA ADMINISTRACION "
                    + "<p>"
                    + "<br/>" 
                    + "<p>"
                    + "<br/>"
                    + "<p>"
                    + "<br/>"
                    + "<hr>"
                    + "Desarrollado por <a href=http://www.jcinform.com> JC INFORM </a> "
                    + "<p>"
                    + "<hr>"
                    + "</html> ";
             try {
            System.out.println(""+emp.getEmail().trim()+ mensaje+
                    "RECUPERACION DE CLAVE " + emp.getApellidoPaterno() + " " + emp.getNombre()+ 
                    institucion.getEmail()+ 
                    institucion.getClave()+ 
                    institucion.getSmtp()+ 
                    institucion.getPuerto()+
                    institucion.getAutorizacion()+
                    institucion.getStar());                  
            } catch (Exception e) {
                 System.out.println("error: "+e.getMessage());
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ud. no tiene registrado un email en su cuenta...!","Ud. no tiene registrado un email en su cuenta...!"));
                return "no";
            }
            
            Boolean estado = EnviarAutenticacion.RecuperarClave(emp.getEmail().trim(), mensaje,
                    "RECUPERACION DE CLAVE " + emp.getApellidoPaterno() + " " + emp.getNombre(), institucion.getEmail(), institucion.getClave(), institucion.getSmtp(), institucion.getPuerto(),institucion.getAutorizacion(),institucion.getStar());
 
 
            if (estado) {
                
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_INFO,"Se ha enviado un email a: "+emp.getEmail()+" con sus datos para el ingreso","Se ha enviado un email a: "+emp.getEmail()+" con sus datos para el ingreso"));
                return "[OK] " + emp.getEmail();
            } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha enviado el email...!","No se ha enviado el email...!"));
                return "no";

            }

        } else {
                                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha encontrado el estudiante con la cédula ingresada...!","No se ha encontrado el estudiante con la cédula ingresada...!"));
            return "no";
        }
    }

    public String recuperarClaveEstudiante(String usuario) {
        claves val = new claves();
        Administrador adm = new Administrador();
        List<Matriculas> llista = adm.query("Select o from Matriculas as o where o.estudiante.usuario = '" + usuario + "' ");
        if (llista.size() <= 0) {
            return "nouser";
        }

        Matriculas matriculaNo = llista.get(0);
        if (matriculaNo != null) {
            
            mensaje = "<html><p> <b> Su usuario es: </b>" + matriculaNo.getIdEstudiantes().getUsuario() + "<p> "
                    + "<b> Su password es:</b> " + val.desencriptar(matriculaNo.getIdEstudiantes().getClave()) + " <p> "
                    + "Le recordamos que debe cambiar su clave periodicamente para su mayor seguridad"
                    + "<br/>"
                    + "<p>"
                    + "<p>"
                    + "<br/>"
                    + "LA ADMINISTRACION "
                    + "<p>"
                    + "<br/>"
                    + "<p>"
                    + "<br/>"
                    + "<p>"
                    + "<br/>"
                    + "<hr>"
                    + "Desarrollado por <a href='www.jcinform.com'>JC INFORM</a>"
                    + "<p>"
                    + "<hr>"
                    + "</html> ";
            Boolean estado = EnviarAutenticacion.RecuperarClave(matriculaNo.getIdEstudiantes().getEmail().trim(), mensaje,
                    "RECUPERACION DE CLAVE " + matriculaNo.getIdEstudiantes().getApellidoPaterno() + " " + matriculaNo.getIdEstudiantes().getNombre(), institucion.getEmail(), institucion.getClave(), institucion.getSmtp(), institucion.getPuerto(),institucion.getAutorizacion(),institucion.getStar());
            if (estado) {
                return "[OK] " + matriculaNo.getIdEstudiantes().getEmail();
            } else {
                return "no";

            }

        } else {
            return "no";
        }
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

    public void enviarEmail(Empleados empleado) {
//        Cursos obj = curso;
        try {
            Administrador adm = new Administrador();
             
 
            String ip = institucion.getIp();
            mensaje = mensaje.replace("/solucion", "http://" + ip + "/solucion");
            String direcciones = correos;
            StringTokenizer tokens = new StringTokenizer(direcciones, ";");
            ArrayList matriculados2 = new ArrayList();
            int i = 0;
            int k = 1;
            while (tokens.hasMoreTokens()) {
                String str = tokens.nextToken();
                str = str.replace(" ", "");
                if (!str.equals("") && str.contains("@")) {
                    if (!matriculados2.contains(str)) {
                        if (validaEmail(str.replace(" ", ""))) {
                            matriculados2.add(str.replace(" ", ""));
                            System.out.println(""+str);
                            k++;
                        }
                    }


                }
                i++;
            }
            System.out.println("enviado a: ("+k+") emails");
            if (matriculados2.size() > 0) {
                EnviarAutenticacion.EnviarCorreo(matriculados2, mensaje, tema, institucion.getEmail(), institucion.getClave(), institucion.getSmtp(), institucion.getPuerto(), empleado.getEmail(),institucion.getAutorizacion(),institucion.getStar());
            }
        } catch (Exception e) {
            System.out.println("" + e);
        }

    }

    public String getCorreos() {
        return correos;
    }

    public void setCorreos(String correos) {
        this.correos = correos;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
  protected UIComponent findComponent(UIComponent c, String id) {
        if (id.equals(c.getId())) {
            return c;
        }
        Iterator<UIComponent> kids = c.getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent found = findComponent(kids.next(), id);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    
}
class EnviarAutenticacion {

    public static Boolean RecuperarClave(String email, String mensaje, String tema, String emailInstitucion, String clave, String host, String puerto,Boolean autorizacion, Boolean star) {
//        String host ="smtp.gmail.com";//Suponiendo que el servidor SMTPsea la propia máquina
        //String from ="jcinform@gmail.com";
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

//    public void enviarEmailInscripcion(String direcciones, String codigo) {
//        Cursos obj = curso;
//        try {
//            Administrador adm = new Administrador();
//            List parame = adm.query("Select o from ParametrosGlobales as o where o.variable= 'IPPUBLICA'");
//            Inscripciones estudent = (Inscripciones) adm.buscarClave(codigo, Inscripciones.class);
//            ParametrosGlobales para = (ParametrosGlobales) parame.get(0);
//            String ip = para.getCvalor();
//
//            Periodo periodo = (Periodo) adm.buscarClave(estudent.getPeriodo(), Periodo.class);
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
//                }
//                i++;
//            }
//            String mensaj = "<html> Gracias <b> " + estudent.getApellido() + " " + estudent.getNombre() + " "
//                    + "<p> Su inscripción se ha realizado con "
//                    + "éxito, para poder reimprimir el "
//                    + "comprobante puede acceder desde "
//                    + "el siguiente enlace: <a href = \"http://" + ip + "/solucion" + "/592981728937491235.zul?AASL2KSO348934934=" + estudent.getCodigoest() + "\"> http://" + ip + "/solucion" + "/592981728937491235.zul?AASL2KSO348934934=" + estudent.getCodigoest() + "  </a> "
//                    + "si no funcionará por favor copie y pegue la dirección"
//                    + " en su navegador preferido. </html>";
//            if (matriculados2.size() > 0) {
//                EnviarAutenticacion.EnviarCorreo(matriculados2, mensaj, institucion.getNombre() + " - INSCRIPCION", institucion.getUsuariomail(), institucion.getClavemail(), institucion.getSmtp(), institucion.getPuerto(), "jcinform@gmail.com",institucion.getAutorizacion(),institucion.getStar());
//            }
//        } catch (Exception e) {
//            System.out.println("" + e);
//        }
//
//    }
