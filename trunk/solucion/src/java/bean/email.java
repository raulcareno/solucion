package bean;

import java.util.*;
import javax.faces.model.SelectItem;
import javax.mail.*;
import javax.mail.internet.*;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Periodo;

public class email {
    public String correos;
    public String tema;
    public Cursos curso;
    public String mensaje;
    public email(){
        curso = new Cursos();
        mensaje = "";
        correos="";
    }

    public void enviarEmail(Periodo periodo) {
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
                        if(!str.equals("")){
                            matriculados2.add(str.replace(" ", ""));
                            System.out.println(str);
                        }
                        i++;
                    }
                    if(matriculados2.size()>0)
                           EnviarAutenticacion.EnviarCorreo(matriculados2,mensaje,obj.getInspector(),periodo.getInstitucion().getUsuariomail(),periodo.getInstitucion().getClavemail(),periodo.getInstitucion().getSmtp(),periodo.getInstitucion().getPuerto());
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
    public static void EnviarCorreo(ArrayList email,String mensaje,String tema,String emailInstitucion,String clave,String host, String puerto){
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
            Message msg = getTraerMensaje(session, tema, emailsA,mensaje,emailInstitucion);
            Transport.send(msg);
        }

        catch (Exception e){
            System.out.println("ERROR AL ENVIAR "+e);
            ExceptionManager.ManageException(e);
        }

    }

    private static MimeMessage getTraerMensaje(Session session, String from,  InternetAddress[] to,String mensaje,String emailInstitucion)   {
        try{

            MimeMessage msg = new MimeMessage(session);
            msg.setSubject(""+from );
            msg.setText(mensaje,"ISO-8859-1","html");
            //msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //msg.addRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress("geovanny1781@yahoo.com"), new InternetAddress("jcinform@gmail.com"),new InternetAddress("geovanny1781@hotmail.com") });
            msg.addRecipients(Message.RecipientType.BCC, to );
            msg.setFrom(new InternetAddress(""+emailInstitucion));

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
