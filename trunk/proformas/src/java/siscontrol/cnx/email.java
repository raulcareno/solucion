package siscontrol.cnx;

import java.util.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import siscontrol.cnx.email.SendAuthentication;

/**
 *
 * @author Administrador extends DispatchAction
 */

/*codigo cliente 
 * email a cual fue enviado 
 * fecha de envio
 * archivo adjunto 
 * destinatario 
 * 
 */
public class email {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        SendAuthentication ss = new SendAuthentication();
//                ss.SendMail();
        email e = new email();
        e.enviar();
    }

    public email() {
    }

    public void enviar() {
        SendAuthentication ss = new SendAuthentication();
        ss.SendMail();
    }

    public class SendAuthentication {

        public void SendAuthentication() {
        }

        public void SendMail() {
            String host = "faster.myhostingdomain.net";//Suponiendo que el servidor SMTPsea la propia mÃƒÂ¡quina
            Properties prop = new Properties();
            prop.put("mail.smtp.host", host);
            prop.setProperty("mail.smtp.port", "993");
            prop.setProperty("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.auth", "true");
            
            
            try {
                //SMTPAuthentication auth = new SMTPAuthentication("jcinform@gmail.com","ismael20");
                email.SMTPAuthentication auth = new email.SMTPAuthentication();
                Session session = Session.getInstance(prop, auth);
                String mensaje = "ADJUNTO DOCUMENTO";
                Message msg = new MimeMessage(session);
                //getMessage(session, mensaje);
//            msg.setText(mensaje,"ISO-8859-1","html");
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress("jcinform@gmail.com"));
//            msg.setFrom(new InternetAddress("jcinform@gmail.com"));

                BodyPart texto = new MimeBodyPart();
                texto.setText("Texto del mensaje");

                BodyPart adjunto = new MimeBodyPart();
                adjunto.setText("Adjunto documento de stado");
                adjunto.setDataHandler(new DataHandler(new FileDataSource("d:/notas.pdf")));
                adjunto.setFileName("notas.pdf");
                MimeMultipart multiParte = new MimeMultipart();
                multiParte.addBodyPart(texto);
                multiParte.addBodyPart(adjunto);
                msg.setContent(multiParte);
                msg.setSubject("ESTADO DE CUENTA");
                Transport.send(msg);
                System.out.println("PASO TRANSPORT");
            } catch (Exception e) {
                e.printStackTrace();
                //ExceptionManager.ManageException(e);
            }
        }
        //private static MimeMessage getMessage(Session session, String from,  InternetAddress[] to,String mensaje,String emailInstitucion)   {
//        private MimeMessage getMessage(Session session, String mensaje)   {
//        try{
//
//            MimeMessage msg = new MimeMessage(session);
////            msg.setSubject(""+from );
//            msg.setText(mensaje,"ISO-8859-1","html");
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("geovanny1781@hotmail.com"));
//            //msg.addRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress("geovanny1781@yahoo.com"), new InternetAddress("jcinform@gmail.com"),new InternetAddress("geovanny1781@hotmail.com") });
////            msg.addRecipients(Message.RecipientType.BCC, new InternetAddress("geovanny1781@hotmail.com"));
//            msg.setFrom(new InternetAddress("jcinform@gmail.com"));
//              BodyPart adjunto = new MimeBodyPart();
//            adjunto.setDataHandler(new DataHandler(new FileDataSource("c:/notas.pdf")));
//            adjunto.setFileName("futbol.pdf");
//            //adjunto.setText("Adjunto documento de stado");
//            MimeMultipart multiParte = new MimeMultipart();
//            //multiParte.addBodyPart("ADJUNTO DOCUMENTO DE ESTADO DE CUENTA");
//            multiParte.addBodyPart(adjunto);
//            msg.setContent(multiParte);
//            return msg;
//
//        }catch (MessagingException ex){
////            ExceptionManager.ManageException(ex);
//            return null;
//
//            } 
//
//
//    }
    }

    static class SMTPAuthentication extends javax.mail.Authenticator {
//    public SMTPAuthentication(String user, String pass){
//        this.user = user;
//        this.pass = pass;
//    }

        public PasswordAuthentication getPasswordAuthentication() {
//        AdministradorSistema bs = new AdministradorSistema();
            String username = "info@siscontrol.com.ec";
            String password = "siscontrol123";
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

    public static class ExceptionManager {

        public void ManageException(Exception e) {
            System.out.println("Se ha producido una exception");
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
    }
}
//            BodyPart texto = new MimeBodyPart();
//            texto.setText("<table border=0 bgcolor=#D9F2FF> <tr><td colspan=2>POR FAVOR NO REVELE SUS NOMBRES DE USUARIO NI CONTRASEÃ‘AS " +
//                    "<tr><td colspan = 2>RECUERDE QUE USTED ES RESPONSABLE POR EL MAL USO DE LAS MISMAS " +
//                    "<tr><td> SUS NOMBRES:</td> <td> <b>"+profe.getNombres()+"</b> </td></tr>" +
//                    "<tr><td> SUS USUARIO:</td> <td> <b>" +profe.getProUsuario()+" </b></td></tr>"+
//                    "<tr><td> SU CLAVE ES:</td> <td> <b>"+profe.getProClave()+"</b></td></tr> " +
//                    "</table> ISO-8859-1 html");
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            msg.setFrom(new InternetAddress(from,"setecompu"));
//
//            BodyPart adjunto = new MimeBodyPart();
//            adjunto.setDataHandler(new DataHandler(new FileDataSource("c:/file.txt")));
//            adjunto.setFileName("futbol.gif");
//            MimeMultipart multiParte = new MimeMultipart();
//            multiParte.addBodyPart(texto);
//            multiParte.addBodyPart(adjunto);
//            msg.setContent(multiParte);
//    MimeMultipart mm = new MimeMultipart();
//    MimeBodyPart mbp = new MimeBodyPart();
//
//    DataHandler dh = new DataHandler("Probando un DataHanler", "text/plain");
//
//    mbp.setFileName("mensaje.txt");
//    mbp.setDataHandler(dh);
//    mm.addBodyPart(mbp);
//
//    mbp = new MimeBodyPart();
//    URLDataSource uds = new URLDataSource(new URL("una URL"));
//
//    dh = new DataHandler(uds);
//    mbp.setDataHandler(dh);
//    mbp.setFileName("url.txt");
//    mm.addBodyPart(mbp);
//
//    mbp = new MimeBodyPart();
//    FileDataSource fds = new FileDataSource("C:/apache-tomcat-5.5.20/webapps/begjsp-ch17/test.txt");
//    dh = new DataHandler(fds);
//    mbp.setDataHandler(dh);
//    mbp.setFileName("test.txt");
//    mm.addBodyPart(mbp);
//
//    message.setContent(mm);
//
//             MimeBodyPart messageBodyPart = new MimeBodyPart();
//             //fill message           
//             messageBodyPart.setText(msgText);           
//             Multipart multipart = new MimeMultipart();       
//             multipart.addBodyPart(messageBodyPart);          
//             // Part two is attachment        
//             if ((attachmentStreamToFileNameMap != null) && !attachmentStreamToFileNameMap.isEmpty()) 
//             {           
//             for (Iterator iter = attachmentStreamToFileNameMap.keySet().iterator(); iter.hasNext()    
//             {                    InputStream attach = (InputStream) iter.next();  
//                 messageBodyPart = new MimeBodyPart();				
//                 messageBodyPart.setDataHandler(					
//                 new DataHandler(new InputStreamDataSource(attach,			
//                 (String) attachmentStreamToFileNameMap.get(attach), "application.pdf")));    
//                 messageBodyPart.setFileName((String) attachmentStreamToFileNameMap.get(attach)); 
//                 multipart.addBodyPart(messageBodyPart);             
//             }           
//             }       
//             // Put parts in message      
//             msg.setContent(multipart); 
