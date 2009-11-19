package bean;

import java.util.*;
import javax.faces.model.SelectItem;
import javax.mail.*;
import javax.mail.internet.*;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Empleadoperiodo;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Periodo;
import jcinform.procesos.Administrador;

public class email {
    public String correos;
    public String tema;
    public Cursos curso;
    public String mensaje;

    public Boolean recuperarClave(String cedula){
          claves val = new claves();
            Administrador adm = new Administrador();
            Empleados emp = (Empleados) adm.querySimple("Select o from Empleados as o where o.identificacion = '"+cedula+"' ");
            if(emp != null){
                        List<Empleadoperiodo> per =  adm.query("Select o from Empleadoperiodo as o " +
                        "where o.empleado.codigoemp = '"+emp.getCodigoemp()+"' ");
                        Periodo periodo = new Periodo();
                        for (Iterator<Empleadoperiodo> it = per.iterator(); it.hasNext();) {
                                Empleadoperiodo empleadoperiodo = it.next();
                                periodo = empleadoperiodo.getPeriodo();
                        }
                        if(per.size()<=0){
                            return false;
                        }
                        mensaje = "<html><p> <b> Su usuario es: </b>"+emp.getUsuario() +"<p> " +
                                "<b> Su password es:</b> "+val.desencriptar(emp.getClave()) +" <p> " +
                                "Le recordamos que debe cambiar su clave periodicamente para su mayor seguridad" +
                                "<p>." +
                                "<p>" +
                                "<p>" +
                                "<p>." +
                                "LA ADMINISTRACION " +
                                "<p>" +
                                "<p>." +
                                "<p>" +
                                "<p>." +
                                "<p>" +
                                "<p>." +
                                "<hr>." +
                                "Desarrollado por JCINFORM fono: 080162 211 " +
                                "<p>" +
                                "<hr>" +
                                "</html> ";
                        EnviarAutenticacion.RecuperarClave(emp.getEmail().trim(), mensaje,
                                "RECUPERACION DE CLAVE "+emp.getApellidos() +" "+ emp.getNombres()
                                , periodo.getInstitucion().getUsuariomail(), periodo.getInstitucion().getClavemail(), periodo.getInstitucion().getSmtp(), periodo.getInstitucion().getPuerto());

                        return true;
            }else{
                return false;
            }
    }

    public email(){
        curso = new Cursos();
        mensaje = "";
        correos="";
    }

    public void enviarEmail(Periodo periodo,Empleados empleado) {
        Cursos obj = curso;
                try
                {
                String direcciones = correos;
                StringTokenizer tokens=new StringTokenizer(direcciones, ";");
                ArrayList matriculados2 = new ArrayList();
                    int i=0;
                    while(tokens.hasMoreTokens()){
                        String str=tokens.nextToken();
                        str = str.replace(" ", "");
                        if(!str.equals("") && str.contains("@")){
                            matriculados2.add(str.replace(" ", ""));
                            System.out.println(str);
                        }
                        i++;
                    }
                    if(matriculados2.size()>0)
                           EnviarAutenticacion.EnviarCorreo(matriculados2,mensaje,tema,periodo.getInstitucion().getUsuariomail(),periodo.getInstitucion().getClavemail(),periodo.getInstitucion().getSmtp(),periodo.getInstitucion().getPuerto(),empleado.getEmail());
        }
        catch(Exception e)
        {
            System.out.println(""+e);
        }

    }


    public String getTema() {
        return tema;
    }

    public void setTema(String asunto) {
        this.tema = asunto;
    }


    public Cursos getCurso() {
        return curso;
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
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


public List<SelectItem> matriculados = new ArrayList<SelectItem>();

    public List<SelectItem> getMatriculados() {
        return matriculados;
    }

    public void setMatriculados(List<SelectItem> matriculados) {
        this.matriculados = matriculados;
    }
}
class EnviarAutenticacion
{
        public static void RecuperarClave(String email,String mensaje,String tema,String emailInstitucion,String clave,String host, String puerto){
//        String host ="smtp.gmail.com";//Suponiendo que el servidor SMTPsea la propia máquina
        //String from ="setecompu.ec@gmail.com";
        String from = emailInstitucion;
//        String to = email;
        Properties prop = new Properties();
        prop.put("mail.host", host.trim());
        prop.setProperty("mail.smtp.port",puerto.trim());
        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.auth", "true");
         try{
            SMTPAutenticacion auth = new SMTPAutenticacion(emailInstitucion,clave);
            Session session = Session.getInstance(prop , auth );
            InternetAddress[] emailsA = new InternetAddress[1];
            int i=0;
            emailsA[i] = new InternetAddress(email+"");
            System.out.println("***********************"+emailInstitucion);
            Message msg = getTraerMensaje(session, tema, emailsA,mensaje,emailInstitucion,emailInstitucion);
            Transport.send(msg);
        }

        catch (Exception e){
            System.out.println("ERROR AL ENVIAR "+e);
            ExceptionManager.ManageException(e);
        }

    }

    public static void EnviarCorreo(ArrayList email,String mensaje,String tema,String emailInstitucion,String clave,String host, String puerto,String respuestaA){
//        String host ="smtp.gmail.com";//Suponiendo que el servidor SMTPsea la propia máquina
        //String from ="setecompu.ec@gmail.com";
        String from = emailInstitucion;
//        String to = email;
        Properties prop = new Properties();
        prop.put("mail.host", host.trim());
        prop.setProperty("mail.smtp.port",puerto.trim());
        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.auth", "true");
         try{
            SMTPAutenticacion auth = new SMTPAutenticacion(emailInstitucion,clave);
            Session session = Session.getInstance(prop , auth );
            InternetAddress[] emailsA = new InternetAddress[email.size()];
            int i=0;
             for (Iterator it = email.iterator(); it.hasNext();) {
                     String object = (String) it.next();
                     String to = object.replace(" ", "");
                     emailsA[i] = new InternetAddress(to+"");
                     i++;
             }
            System.out.println("***********************"+emailInstitucion);
            Message msg = getTraerMensaje(session, tema, emailsA,mensaje,emailInstitucion,respuestaA);
            Transport.send(msg);
        }

        catch (Exception e){
            System.out.println("ERROR AL ENVIAR "+e);
            ExceptionManager.ManageException(e);
        }

    }

    private static MimeMessage getTraerMensaje(Session session, String from,  InternetAddress[] to,String mensaje,String emailInstitucion,String respuestaA)   {
        try{

            MimeMessage msg = new MimeMessage(session);
            msg.setSubject(""+from );
            if(!respuestaA.equals(null) && !respuestaA.equals("")){
                InternetAddress[] emailsA = new InternetAddress[1];
                int i=0;
                emailsA[i] = new InternetAddress(respuestaA+"");
                msg.setReplyTo(emailsA);
                //msg.setSender(new InternetAddress(respuestaA));
                msg.setFrom(new InternetAddress(""+respuestaA));
            }else{
                msg.setFrom(new InternetAddress(""+emailInstitucion));
            }
            msg.setText(mensaje,"ISO-8859-1","html");
            //msg.set
            //msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //msg.addRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress("geovanny1781@yahoo.com"), new InternetAddress("jcinform@gmail.com"),new InternetAddress("geovanny1781@hotmail.com") });
            msg.addRecipients(Message.RecipientType.BCC, to );
            return msg;
        }catch (MessagingException ex)
            {

            ExceptionManager.ManageException(ex);
            return null;

            }


    }

}
class SMTPAutenticacion extends javax.mail.Authenticator{
    public SMTPAutenticacion(String user, String pass){
        this.user = user;
        this.pass = pass;
    }
    public PasswordAuthentication getPasswordAuthentication(){
        String username = ""+this.user;
        String password = ""+this.pass;
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
class ExceptionManager{

    public static void ManageException (Exception e){
//        System.out.println ("Se ha producido una exception");
//        System.out.println (e.getTraerMensaje());
        e.printStackTrace(System.out);
    }
}
