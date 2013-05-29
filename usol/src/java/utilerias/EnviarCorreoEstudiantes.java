/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

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
import jcinform.persistencia.Aspirantes;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Institucion;
import jcinform.persistencia.Parametros;
import jcinform.procesos.Administrador;
import jcinform.procesos.claves;
import org.primefaces.event.UnselectEvent;



    
    /**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class EnviarCorreoEstudiantes {

    public String correos;
    public String tema;
    public String mensaje;
    public Institucion institucion;
    public String cedula;
    public Administrador adm;

    /**
     * Creates a new instance of RecuperarBean
     */
    
    public EnviarCorreoEstudiantes() {
        mensaje = "";
        correos = "";
        adm = new Administrador();
        selectedEmpleados = new ArrayList<Empleados>();
        
        try {
            List<Institucion> user = adm.query("Select o from Institucion as o ");
            institucion = user.get(0);
        } catch (Exception e) {
            System.out.println("recuperarBean, NO EXISTEN INSTITUCION");
        }

    }
 
    private List<Empleados> selectedEmpleados;  
    private List<String> selectedTexts;  
    public List<Empleados> completeEmpleados(String query) {  
        List<Empleados> empleados = new ArrayList<Empleados>();
         if(query.length()>1){
              empleados = adm.query("Select o from Empleados as o "
                      + " where o.apellidoPaterno like '%"+query+"%' and o.email is not null "
                      + " order by o.apellidoPaterno ");
              if(empleados.size()<=0){
                empleados = adm.query("Select o from Empleados as o "
                        + " where o.email like '%"+query+"%' "
                        + " order by o.email ");
              }
         }
        return empleados;  
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
  

    public List<Empleados> getSelectedEmpleados() {
        return selectedEmpleados;
    }

    public void setSelectedEmpleados(List<Empleados> selectedEmpleados) {
        this.selectedEmpleados = selectedEmpleados;
    }
  
  
    public List<String> getSelectedTexts() {  
        return selectedTexts;  
    }  
    public void setSelectedTexts(List<String> selectedTexts) {  
        this.selectedTexts = selectedTexts;  
    }  
  
    public void handleUnselect(UnselectEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected:" + event.getObject().toString(), null);  
          
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }  
    
    public String enviarEmail() {
        Estudiantes emp = (Estudiantes) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        FacesContext context = FacesContext.getCurrentInstance();
        ArrayList listadoCorreos = new ArrayList();
         for (Iterator<Empleados> it = selectedEmpleados.iterator(); it.hasNext();) {
            Empleados empleados = it.next();
            empleados =(Empleados) adm.buscarClave(empleados.getIdEmpleados(), Empleados.class);
             String str = empleados.getEmail();
                str = str.replace(" ", "");
                if (!str.equals("") && str.contains("@")) {
                    if (!listadoCorreos.contains(str)) {
                        if (validaEmail(str.replace(" ", ""))) {
                            listadoCorreos.add(str.replace(" ", ""));
                        }
                    }
                }
        }
           EnviarAutenticacionEstudiante.EnviarCorreo(listadoCorreos, mensaje, tema,  institucion.getEmail(), institucion.getClave(), institucion.getSmtp(), institucion.getPuerto(),emp.getEmail() ,institucion.getAutorizacion(), institucion.getStar());
           tema = "";
           mensaje= "";
           selectedEmpleados = new ArrayList<Empleados>();
           context.addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre de Usuario o Contraseña están incorrectas...!", "Email Enviado Correctamente...!"));
         
            return "ok";
  
    }
/**
     * DATOS PARA ENVIAR CORREO
     * @param cedula
     * @return 
     */
    
    public String enviarCorreo(String cedula) {
        claves val = new claves();
        FacesContext context = FacesContext.getCurrentInstance();
        Aspirantes emp;

        Parametros pa = null;
        try {
            pa = (Parametros) adm.querySimple("Select o from Parametros as o where o.variable = 'EMAILASPIRANTES' ");
        } catch (Exception e) {
            System.out.println("" + e);
        }

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
                System.out.println("" + emp.getEmail().trim() + mensaje
                        + "PRE-INSCRIPCION " + emp.getNombres()
                        + institucion.getEmail()
                        + institucion.getClave()
                        + institucion.getSmtp()
                        + institucion.getPuerto()
                        + institucion.getAutorizacion()
                        + institucion.getStar());
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ud. no tiene registrado un email en su cuenta...!", "Ud. no tiene registrado un email en su cuenta...!"));
                return "no";
            }
            String correos = "" + emp.getEmail().trim() + ";" + (pa != null ? pa.getVCaracter() : "");
            ArrayList matriculados2 = new ArrayList();
            correos = correos + ";" + pa.getVCaracter();
            StringTokenizer tokens = new StringTokenizer(correos, ";");

            int i = 0;
            int k = 1;
            while (tokens.hasMoreTokens()) {
                String str = tokens.nextToken();
                str = str.replace(" ", "");
                if (!str.equals("") && str.contains("@")) {
                    if (!matriculados2.contains(str)) {
                        if (validaEmail(str.replace(" ", ""))) {
                            matriculados2.add(str.replace(" ", ""));
                            System.out.println("" + str);
                            k++;
                        }
                    }


                }
                i++;
            }
            System.out.println("enviado a: (" + k + ") emails");

            Boolean estado = EnviarAutenticacionEstudiante.RecuperarClave(matriculados2, mensaje,
                    "PRE-INSCRIPCION " + emp.getNombres(), institucion.getEmail(), institucion.getClave(), institucion.getSmtp(), institucion.getPuerto(), institucion.getAutorizacion(), institucion.getStar());


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
                            System.out.println("" + str);
                            k++;
                        }
                    }


                }
                i++;
            }
            System.out.println("enviado a: (" + k + ") emails");
            if (matriculados2.size() > 0) {
                EnviarAutenticacionEstudiante.EnviarCorreo(matriculados2, mensaje, tema, institucion.getEmail(), institucion.getClave(), institucion.getSmtp(), institucion.getPuerto(), empleado.getEmail(), institucion.getAutorizacion(), institucion.getStar());
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
class EnviarAutenticacionEstudiante {

    public static Boolean RecuperarClave(ArrayList email, String mensaje, String tema, String emailInstitucion, String clave, String host, String puerto, Boolean autorizacion, Boolean star) {
//        String host ="smtp.gmail.com";//Suponiendo que el servidor SMTPsea la propia máquina
        //String from ="jcinform@gmail.com";
        String from = emailInstitucion;
//        String to = email;
        Properties prop = new Properties();
        prop.put("mail.host", host.trim());
        prop.setProperty("mail.smtp.port", puerto.trim());
        prop.setProperty("mail.smtp.starttls.enable", "" + star);
        prop.put("mail.smtp.auth", "" + autorizacion);
        try {
            SMTPAutenticacionEstudiante auth = new SMTPAutenticacionEstudiante(emailInstitucion, clave);
            Session session = Session.getInstance(prop, auth);

            InternetAddress[] emailsA = new InternetAddress[email.size()];
            int i = 0;
            for (Iterator it = email.iterator(); it.hasNext();) {
                String object = (String) it.next();
                String to = object.replace(" ", "");
                emailsA[i] = new InternetAddress(to + "");
                i++;
            }
//            InternetAddress[] emailsA = new InternetAddress[1];
//            int i = 0;
//            emailsA[i] = new InternetAddress(email + "");
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
            SMTPAutenticacionEstudiante auth = new SMTPAutenticacionEstudiante(emailInstitucion, clave);
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

class SMTPAutenticacionEstudiante extends javax.mail.Authenticator {

    public SMTPAutenticacionEstudiante(String user, String pass) {
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

class ExceptionManagerEstudiantes {

    public static void ManageException(Exception e) {
//        System.out.println ("Se ha producido una exception");
//        System.out.println (e.getTraerMensaje());
        e.printStackTrace(System.out);
    }
}
 