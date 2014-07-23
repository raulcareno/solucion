package siscontrol.cnx;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
public class respaldosEmail  {

    /**
     * @param args the command line arguments
     */
//      public static void main(String[] args) {
//        // TODO code application logic here
//        SendAuthentication ss = new SendAuthentication();
//                ss.SendMail("geovanny1781@hotmail.com","c:/respaldos/vacio.zip");
//    }
    public respaldosEmail() {
        
    }
    public void enviar(String para,String archivo,String institucion){
        SendAuthentication ss = new SendAuthentication();
        ss.SendMail(para,archivo,institucion);
    }

public static class SendAuthentication
{
    public void SendAuthentication(){
        
    }

    public  void SendMail(String para,String archivo,String institucion){
        Comprimir comp = new Comprimir();
        comp.comprimir(archivo);
        
        String host ="smtp.gmail.com";//Suponiendo que el servidor SMTPsea la propia mÃ¡quina
        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.setProperty("mail.smtp.port", "587");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.auth", "true");
         try{
            SMTPAuthentication auth = new SMTPAuthentication();
            Session session = Session.getInstance(prop , auth );
            Message msg = new MimeMessage(session);
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
             BodyPart texto = new MimeBodyPart();
            texto.setText("Se adjunta respaldo de base de datos academico con fecha:  "+new Date().toLocaleString());
            
              BodyPart adjunto = new MimeBodyPart();
            adjunto.setText("Adjunto documento de stado");
            adjunto.setDataHandler(new DataHandler(new FileDataSource(archivo.replace("sql", "zip"))));
            adjunto.setFileName("academico "+(new Date()).toLocaleString().replaceAll("/","").replace(":", "")+".zip");
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);
            msg.setContent(multiParte);
            msg.setSubject(institucion+" ACADEMICO:"+new Date().toLocaleString());
            Transport.send(msg);     
        }catch (Exception e){
//            ExceptionManager.ManageException(e);
        }
    }
  
}


static class SMTPAuthentication extends javax.mail.Authenticator{
//    public SMTPAuthentication(String user, String pass){
//        this.user = user;
//        this.pass = pass;
//    }
    public PasswordAuthentication getPasswordAuthentication(){ 
//        AdministradorSistema bs = new AdministradorSistema();
        String username = "jcinform@gmail.com";
        String password = "samiismael@2020";
//        String username = ""+this.user;
//        String password = ""+this.pass;
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

    public  void ManageException (Exception e){
        System.out.println ("Se ha producido una exception");
        System.out.println (e.getMessage());
        e.printStackTrace(System.out);
    }
}


}


 