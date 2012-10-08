package correos;

//import com.sun.xml.internal.ws.util.ByteArrayDataSource;
import bean.Permisos;
import bean.claves;
import bean.notas;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import jcinform.persistencia.Correos;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Empleadoperiodo;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Periodo;
import jcinform.persistencia.Representante;
import jcinform.procesos.Administrador;
import org.zkoss.zul.Listbox;


public class electronico {

    public String correos;
    public String tema;
    public Cursos curso;
    public String mensaje;
    public Store store = null;
    public byte[] adjuntoArchivo;
    public String nombreArchivo;

    public ArrayList RefrescarCorreos(Empleados emp) {
        Listbox b;

        // Se obtiene la Session
        Properties prop = new Properties();
        Session sesion = Session.getInstance(prop);
        ArrayList ar = new ArrayList();
        Permisos pe = new Permisos();
        try {
                store = sesion.getStore("imap");
                store.connect("mail.uejjrousseau.edu.ec", emp.getEmail(), pe.decriptar(emp.getClavem()));
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            // Se obtienen los mensajes.
            Message[] mensajes = folder.getMessages();
            Correos c = new Correos();
            // Se escribe from y subject de cada mensaje
            for (int i = 0; i < mensajes.length; i++) {
                c = new Correos();
                Address[] direcciones = mensajes[i].getFrom();
                c.setRemitenten(MimeUtility.decodeText(direcciones[0].toString()));
                c.setTema(mensajes[i].getSubject());
                c.setFecha(mensajes[i].getSentDate());
                c.setCodigo(mensajes[i].getMessageNumber());
                c.setLeido(mensajes[i].isSet(Flag.SEEN));
                if(mensajes[i].getContentType().contains("multipart"))
                    c.setAdjunto(null);
                else
                    c.setAdjunto(new byte[1]);
                ar.add(c);
            }

            folder.close(false);
//            store.close();
        } catch (Exception e) {
            System.out.println("ERROR AL LOGEAR" + e);
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return ar;
    }

    public ArrayList LeerCorreos(Empleados emp) {
        System.out.println("INICIO LEER CORREO: "+new Date());
        // Se obtiene la Session
        Properties prop = new Properties();
        Session sesion = Session.getInstance(prop);
        ArrayList ar = new ArrayList();
        Permisos pe = new Permisos();

        try {
            // Se obtiene el Store y el Folder, para poder leer el
            // correo.
//            if (store == null) {
                store = sesion.getStore("imap");
                store.connect("mail.uejjrousseau.edu.ec", emp.getEmail(), pe.decriptar(emp.getClavem()));
//            }
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            // Se obtienen los mensajes.
            Message[] mensajes = folder.getMessages();
            Correos c = new Correos();
            // Se escribe from y subject de cada mensaje
            for (int i = 0; i < mensajes.length; i++) {
                c = new Correos();
                Address[] direcciones = mensajes[i].getFrom();
                c.setRemitenten(MimeUtility.decodeText(direcciones[0].toString()));
                c.setTema(mensajes[i].getSubject());
                c.setFecha(mensajes[i].getSentDate());
                c.setCodigo(mensajes[i].getMessageNumber());
                c.setLeido(mensajes[i].isSet(Flag.SEEN));
                c.setEliminado(mensajes[i].isSet(Flag.DELETED));
         
                //System.out.println("8888888888888888888888888888: "+mensajes[i].getContentType());
                if(mensajes[i].getContentType().contains("multipart"))
                    c.setAdjunto(null);
                else
                    c.setAdjunto(new byte[1]);

//mensajes[i].isExpunged()
  
//                String[] a = mensajes[i].getFlags().getUserFlags();
//
                ar.add(c);
//                System.out.println(
//                        "From:" + mensajes[i].getFrom()[0].toString());
//                System.out.println("Subject:" + mensajes[i].getSubject());

                // Se visualiza, si se sabe como, el contenido de cada mensaje
//                analizaParteDeMensaje(mensajes[i]);
            }

            folder.close(false);
//            store.close();
        } catch (Exception e) {
            System.out.println("ERROR AL LOGEAR" + e);
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        System.out.println("FINAL LEER CORREO: "+new Date());
        return ar;
    }

public void BorrarMensaje(Empleados emp, Object obj, String tipo) {
        // Se obtiene la Session
        cuenta = 0;
        Properties prop = new Properties();
        Session sesion = Session.getInstance(prop);
        Permisos pe = new Permisos();

        try {
//            if (store == null) {
                store = sesion.getStore("imap");
//           }
//            if (store.isConnected() == false) {
                store.connect("mail.uejjrousseau.edu.ec", emp.getEmail(), pe.decriptar(emp.getClavem()));
//            }
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            Message[] mensajes = folder.getMessages();
            Correos co = (Correos) obj;
            int a = co.getCodigo();

            for (int i = 0; i < mensajes.length; i++) {

                if (a == mensajes[i].getMessageNumber()) {
                    if(tipo.equals("borrar")){
                        mensajes[i].setFlag(Flag.DELETED, true);
                       mensajes =  folder.expunge();
                    }else{
                        mensajes[i].setFlag(Flag.DELETED, false);
                    }
                }

            }

            folder.close(false);
        } catch (Exception e) {
            System.out.println("ERROR AL LOGEAR" + e);
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
   

    }
    public Correos LeerMensaje(Empleados emp, Object obj) {
        // Se obtiene la Session
        cuenta = 0;
        Properties prop = new Properties();
        Session sesion = Session.getInstance(prop);
//        ArrayList ar = new ArrayList();
        Permisos pe = new Permisos();
//        Correos c = new Correos();
        try {
            // Se obtiene el Store y el Folder, para poder leer el
            // correo.
//            if (store == null) {
                store = sesion.getStore("imap");

//            }
//            if (store.isConnected() == false) {
                store.connect("mail.uejjrousseau.edu.ec", emp.getEmail(), pe.decriptar(emp.getClavem()));
//            }
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            // Se obtienen los mensajes.
            Message[] mensajes = folder.getMessages();

            // Se escribe from y subject de cada mensaje
            Correos co = (Correos) obj;
            int a = co.getCodigo();
            correoRegresa = null;
            for (int i = 0; i < mensajes.length; i++) {
 
                if (a == mensajes[i].getMessageNumber()) {
                    correoRegresa = new Correos();
//                    Address[] direcciones = mensajes[i].getFrom();
                       analizaParteDeMensaje(mensajes[i]);
                       String[] head = mensajes[i].getHeader("From");
                       mensajes[i].setFlag(Flag.SEEN, true);
//                       mensajes[i].get;
                        correoRegresa.setRemitenten(MimeUtility.decodeText(head[0])+"");
                        correoRegresa.setTema(mensajes[i].getSubject());
                        correoRegresa.setFecha(mensajes[i].getSentDate());
                        correoRegresa.setCodigo(mensajes[i].getMessageNumber());
                        correoRegresa.setLeido(mensajes[i].isSet(Flag.SEEN));
                 //System.out.println("******************"+);

                    //Address[] ine = mensajes[i].getHeader("From");

//                    correoRegresa.setContenido(correoRegresa.getContenido()+" "+direcciones[0].toString());
                }

            }

            folder.close(false);
//            store.close();
        } catch (Exception e) {
            System.out.println("ERROR AL LOGEAR" + e);
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return correoRegresa;
//    return correoRegresa;
    }
static Correos correoRegresa = new Correos();
public static int cuenta = 0;
    private static void analizaParteDeMensaje(Part unaParte) {

        try {

            // Si es multipart, se analiza cada una de sus partes recursivamente.
            if (unaParte.isMimeType("multipart/*")) {
                Multipart multi;
                multi = (Multipart) unaParte.getContent();

                for (int j = 0; j < multi.getCount(); j++) {
                    analizaParteDeMensaje(multi.getBodyPart(j));
                }
            } else {
//                  byte[] data,data1,data2,data3,data4,data5,data6,data7,data8,data9,data10 = null;
//                  String archivo,archivo1,archivo2,archivo3,archivo4,archivo5,archivo6,archivo7,archivo8,archivo9,archivo10 = null;

                // Si es texto, se escribe el texto.
                if (unaParte.isMimeType("text/*")) {
//                    System.out.println("Texto " + unaParte.getContentType());
//                    System.out.println(unaParte.getContent());
                    correoRegresa.setContenido(unaParte.getContent()+"");
                    System.out.println("---------------------------------");
                } else {
                    // Si es imagen, se guarda en fichero y se visualiza en JFrame
                    if (unaParte.isMimeType("image/*")) {
//                        System.out.println(                                "Imagen " + unaParte.getContentType());
//                        System.out.println("Fichero=" + unaParte.getFileName());
//                        System.out.println("---------------------------------");
                            byte[] datosArchivo = getBytes(unaParte.getInputStream());
//                        byte[] datosArchivo = new byte[5];
                        if(cuenta == 0){
                            correoRegresa.setAdjunto(datosArchivo);
                            correoRegresa.setArchivo(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 1 ){
                            correoRegresa.setAdjunto1(datosArchivo);
                            correoRegresa.setArchivo1(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 2){
                            correoRegresa.setAdjunto2(datosArchivo);
                            correoRegresa.setArchivo2(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 3){
                            correoRegresa.setAdjunto3(datosArchivo);
                            correoRegresa.setArchivo3(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 4){
                            correoRegresa.setAdjunto4(datosArchivo);
                            correoRegresa.setArchivo4(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 5){
                            correoRegresa.setAdjunto5(datosArchivo);
                            correoRegresa.setArchivo5(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 6){
                            correoRegresa.setAdjunto6(datosArchivo);
                            correoRegresa.setArchivo6(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 7){
                            correoRegresa.setAdjunto7(datosArchivo);
                            correoRegresa.setArchivo7(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 8){
                            correoRegresa.setAdjunto8(datosArchivo);
                            correoRegresa.setArchivo8(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 9){
                            correoRegresa.setAdjunto9(datosArchivo);
                            correoRegresa.setArchivo9(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 10){
                            correoRegresa.setAdjunto10(datosArchivo);
                            correoRegresa.setArchivo10(MimeUtility.decodeText(unaParte.getFileName()));
                        }
                        System.out.println("TAMANIO DEL ARCHIVO EN LENG DE BYTES: " + datosArchivo.length);
                        System.out.println("---------------------------------");
                        cuenta ++;

//                        salvaImagenEnFichero(unaParte);
//                        visualizaImagenEnJFrame(unaParte);
                    } else {
                        // Si no es ninguna de las anteriores, se escribe en pantalla
                        // el tipo.
//                        System.out.println("Recibido " + unaParte.getContentType());
                        byte[] datosArchivo = getBytes(unaParte.getInputStream());
//                        byte[] datosArchivo = new byte[5];
                        if(cuenta == 0){
                            correoRegresa.setAdjunto(datosArchivo);
                            correoRegresa.setArchivo(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 1 ){
                            correoRegresa.setAdjunto1(datosArchivo);
                            correoRegresa.setArchivo1(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 2){
                            correoRegresa.setAdjunto2(datosArchivo);
                            correoRegresa.setArchivo2(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 3){
                            correoRegresa.setAdjunto3(datosArchivo);
                            correoRegresa.setArchivo3(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 4){
                            correoRegresa.setAdjunto4(datosArchivo);
                            correoRegresa.setArchivo4(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 5){
                            correoRegresa.setAdjunto5(datosArchivo);
                            correoRegresa.setArchivo5(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 6){
                            correoRegresa.setAdjunto6(datosArchivo);
                            correoRegresa.setArchivo6(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 7){
                            correoRegresa.setAdjunto7(datosArchivo);
                            correoRegresa.setArchivo7(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 8){
                            correoRegresa.setAdjunto8(datosArchivo);
                            correoRegresa.setArchivo8(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 9){
                            correoRegresa.setAdjunto9(datosArchivo);
                            correoRegresa.setArchivo9(MimeUtility.decodeText(unaParte.getFileName()));
                        }else if(cuenta == 10){
                            correoRegresa.setAdjunto10(datosArchivo);
                            correoRegresa.setArchivo10(MimeUtility.decodeText(unaParte.getFileName()));
                        }
//                        System.out.println("TAMANIO DEL ARCHIVO EN LENG DE BYTES: " + datosArchivo.length);
//                        System.out.println("---------------------------------");
                        cuenta ++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return correoRegresa;
    }

    public static byte[] getBytes(InputStream is) throws IOException {

        int len;
        int size = 1024;
        byte[] buf;

        if (is instanceof ByteArrayInputStream) {
            size = is.available();
            buf = new byte[size];
            len = is.read(buf, 0, size);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            buf = new byte[size];
            while ((len = is.read(buf, 0, size)) != -1) {
                bos.write(buf, 0, len);
            }
            buf = bos.toByteArray();
        }
        return buf;
    }

    private static void salvaImagenEnFichero(Part unaParte)
            throws FileNotFoundException, MessagingException, IOException {
        FileOutputStream fichero = new FileOutputStream(
                "d:/" + unaParte.getFileName());
        InputStream imagen = unaParte.getInputStream();
        byte[] bytes = new byte[1000];
        int leidos = 0;

        while ((leidos = imagen.read(bytes)) > 0) {
            fichero.write(bytes, 0, leidos);
        }
    }

    private static void visualizaImagenEnJFrame(Part unaParte)
            throws IOException, MessagingException {
        JFrame v = new JFrame();
        ImageIcon icono = new ImageIcon(
                ImageIO.read(unaParte.getInputStream()));
        JLabel l = new JLabel(icono);
        v.getContentPane().add(l);
        v.pack();
        v.setVisible(true);
    }

    public String recuperarClave(String cedula) {
        claves val = new claves();
        Administrador adm = new Administrador();
        Empleados emp = (Empleados) adm.querySimple("Select o from Empleados as o where o.identificacion = '" + cedula + "' ");
        if (emp != null) {
            List<Empleadoperiodo> per = adm.query("Select o from Empleadoperiodo as o "
                    + "where o.empleado.codigoemp = '" + emp.getCodigoemp() + "' ");
            Periodo periodo = new Periodo();
            for (Iterator<Empleadoperiodo> it = per.iterator(); it.hasNext();) {
                Empleadoperiodo empleadoperiodo = it.next();
                periodo = empleadoperiodo.getPeriodo();
            }
            if (per.size() <= 0) {
                return "no";
            }
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
                    "RECUPERACION DE CLAVE " + emp.getApellidos() + " " + emp.getNombres(), periodo.getInstitucion().getUsuariomail(), periodo.getInstitucion().getClavemail(), periodo.getInstitucion().getSmtp(), periodo.getInstitucion().getPuerto(), periodo.getInstitucion().getAutorizacion(), periodo.getInstitucion().getStar(),adjuntoArchivo,nombreArchivo);
            if (estado) {
                return "[OK] " + emp.getEmail();
            } else {
                return "no";

            }

        } else {
            return "no";
        }
    }
    /*mi funcion */
 public String recuperarClavetodas(String cedula) {
        claves val = new claves();
        Administrador adm = new Administrador();
       Representante emp = (Representante) adm.querySimple("Select o from Representante as o "
                + " where o.codigorep in (907,98,909,910,911,912,210,380,915,916,393,252,373,920,867,788)");

        if (emp != null) {
return "no";
        

        } else {
            return "no";
        }
    }
 

    public void notificar(String empleados, Periodo periodo) {
//        Administrador adm = new Administrador();
        mensaje = "<html>"
                    + "<table width='653' border='1' align='center' cellpadding='0' cellspacing='0'>"
                    + "  <tr>    <td width='649'><p><a href='http://www.colegioterranova.com.ec'> "
                   +" <img src='http://school.colegioterranova.com.ec/images/stories/logo.jpg' width='233' height='94' border='0'></a></p>"
                   + "      <p>Hola, </p>"
                   + "      <p>Tienes actividad en tu cuenta de CORREO INTERNO para revisarlo"
                   + "    visita <a href='http://www.colegioterranova.com.ec'>www.colegioterranova.com.ec</a> e ingresa al PORTAL EDUCATIVO </p> "
                   + "      <p>&nbsp;</p>"
                   + "      <p>Gracias por ser parte de nuestra comunidad </p>"
                   + "    <p>La Administraci&oacute;n </p>"
                   + "    <p>&nbsp;</p></td> "
                   + "  </tr>"
                   + "</table>"
                    + "</html> ";
 
        try {
            
            String direcciones = empleados;
            StringTokenizer tokens = new StringTokenizer(direcciones, ";");
            ArrayList matriculados2 = new ArrayList();
            int i = 0;
            while (tokens.hasMoreTokens()) {
                String str = tokens.nextToken();
                str = str.replace(" ", "");
                if (!str.equals("") && str.contains("@")) {
                    if (!matriculados2.contains(str)) {
                        if (validaEmail(str.replace(" ", ""))) {
                            matriculados2.add(str.replace(" ", ""));
                            System.out.println(str);
                        }
                    }
                }
                i++;
            }
            if (matriculados2.size() > 0) {
                EnviarAutenticacion.EnviarCorreoSimple(matriculados2, mensaje, "Actividad en su Cuenta", periodo.getInstitucion().getUsuariomail(), periodo.getInstitucion().getClavemail(), periodo.getInstitucion().getSmtp(), periodo.getInstitucion().getPuerto(), periodo.getInstitucion().getAutorizacion(), periodo.getInstitucion().getStar());
            }
        } catch (Exception e) {
            System.out.println("" + e);

        }

              
          
 

    }

    public electronico() {
        curso = new Cursos();
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
            //  strMsg = "ERROR: La direccion electronico no es correcta" ;
            //  JOptionPane.showMessageDialog(new JFrame(), strMsg);
            return false;
        }
        return true;
    }

    public void enviarEmail(Periodo periodo, Empleados empleado) {
        Cursos obj = curso;
        try {
            String direcciones = correos;
            StringTokenizer tokens = new StringTokenizer(direcciones, ";");
            ArrayList matriculados2 = new ArrayList();
            int i = 0;
            while (tokens.hasMoreTokens()) {
                String str = tokens.nextToken();
                str = str.replace(" ", "");
                if (!str.equals("") && str.contains("@")) {
                    if (!matriculados2.contains(str)) {
                        if (validaEmail(str.replace(" ", ""))) {
                            matriculados2.add(str.replace(" ", ""));
                            System.out.println(str);
                        }
                    }
                }
                i++;
            }
            if (matriculados2.size() > 0) {
                EnviarAutenticacion.EnviarCorreo(matriculados2, mensaje, tema, periodo.getInstitucion().getUsuariomail(), periodo.getInstitucion().getClavemail(), periodo.getInstitucion().getSmtp(), periodo.getInstitucion().getPuerto(), empleado.getEmail(), periodo.getInstitucion().getAutorizacion(), periodo.getInstitucion().getStar(),adjuntoArchivo,nombreArchivo);
            }
        } catch (Exception e) {
            System.out.println("" + e);
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

    public byte[] getAdjuntoArchivo() {
        return adjuntoArchivo;
    }

    public void setAdjuntoArchivo(byte[] adjuntoArchivo) {
        this.adjuntoArchivo = adjuntoArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
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
class EnviarAutenticacion {

    public static Boolean RecuperarClave(String email, String mensaje, String tema, String emailInstitucion, String clave, String host, String puerto, Boolean autorizacion, Boolean star,byte[] adjuntoArchivo,String nombreArchivo) {
//        String host ="smtp.gmail.com";//Suponiendo que el servidor SMTPsea la propia máquina
        //String from ="setecompu.ec@gmail.com";
        String from = emailInstitucion;

       String[] arraymail = email.split(";");

// En este momento tenemos un array en el que cada elemento es un color.

	System.out.println(arraymail[0]);
        Properties prop = new Properties();
        prop.put("mail.host", host.trim());
        prop.setProperty("mail.smtp.port", puerto.trim());
        prop.setProperty("mail.smtp.starttls.enable", "" + autorizacion);
        prop.put("mail.smtp.auth", "" + star);
        try {
            SMTPAutenticacion auth = new SMTPAutenticacion(emailInstitucion, clave);
            Session session = Session.getInstance(prop, auth);
            InternetAddress[] emailsA = new InternetAddress[1];
            int i = 0;
            emailsA[i] = new InternetAddress(arraymail[0] + "");
            System.out.println("***********************" + emailInstitucion);
            Message msg = getTraerMensaje(session, tema, emailsA, mensaje, emailInstitucion, emailInstitucion,adjuntoArchivo,nombreArchivo);
            
            Transport t = session.getTransport("smtp");
            t.connect("" + emailInstitucion, clave);
            t.sendMessage(msg, emailsA);
            //Transport.send(msg);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR AL ENVIAR " + e);
            ExceptionManager.ManageException(e);
            return false;
        }

    }
 public static void EnviarCorreo(ArrayList email, String mensaje, String tema, String emailInstitucion, String clave, String host, String puerto, String respuestaA, Boolean autorizacion, Boolean star,byte[] adjuntoArchivo,String nombreArchivo) {
//        String host ="smtp.gmail.com";//Suponiendo que el servidor SMTPsea la propia máquina
        //String from ="setecompu.ec@gmail.com";
        String from = emailInstitucion;
//        String to = electronico;
        Properties prop = new Properties();
        prop.put("mail.smtp.host", host.trim());
        prop.setProperty("mail.smtp.port", puerto.trim());
        prop.setProperty("mail.smtp.starttls.enable", "" + star);
        prop.put("mail.smtp.auth", "" + autorizacion);
        prop.setProperty("mail.smtp.user", "school@colegioterranova.edu.ec");
        prop.put("mail.debug", "true");

//    props.setProperty("mail.smtp.host", "mail.uejjrousseau.edu.ec");
//            props.setProperty("mail.smtp.starttls.enable", "true");
//            props.setProperty("mail.smtp.port", "25");
//            props.put("mail.debug", "true");
//            props.setProperty("mail.smtp.user", "school@colegioterranova.edu.ec");
//            props.setProperty("mail.smtp.auth", "true");

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
            Message msg = getTraerMensaje(session, tema, emailsA, mensaje, emailInstitucion, respuestaA,adjuntoArchivo,nombreArchivo);

            msg.setSentDate(new Date());
            Transport t = session.getTransport("smtp");
            t.connect("" + emailInstitucion, clave);
            t.sendMessage(msg, emailsA);
//            Transport.send(msg);
        } catch (Exception e) {
            System.out.println("ERROR AL ENVIAR " + e);
            ExceptionManager.ManageException(e);
        }

    }
    public static void EnviarCorreoSimple(ArrayList email, String mensaje, String tema, String emailInstitucion, String clave, String host, String puerto, Boolean autorizacion, Boolean star) {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", host.trim());
        prop.setProperty("mail.smtp.port", puerto.trim());
        prop.setProperty("mail.smtp.starttls.enable", "" + star);
        prop.put("mail.smtp.auth", "" + autorizacion);
        prop.setProperty("mail.smtp.user", "school@colegioterranova.edu.ec");
        prop.put("mail.debug", "true");

        try {
            SMTPAutenticacion auth = new SMTPAutenticacion(emailInstitucion, clave);
            Session session = Session.getInstance(prop, auth);
            InternetAddress[] emailsA = new InternetAddress[email.size()];
            int i = 0;
            for (Iterator it = email.iterator(); it.hasNext();) {
                String object = (String) it.next();
                String to = object.replace(" ", "");
                System.out.println("PARA: "+to);
                emailsA[i] = new InternetAddress(to + "");
                i++;
            }
            System.out.println("***********************" + emailInstitucion);
             MimeMessage msg = new MimeMessage(session);
            msg.setSubject("" + tema);
            msg.setSentDate(new Date());
            msg.setFrom(new InternetAddress(emailInstitucion));
            msg.setText(mensaje, "ISO-8859-1", "html");
            msg.addRecipients(Message.RecipientType.BCC, emailsA);
            msg.setSentDate(new Date());

            //Message msg = getTraerMensajeSimple(session, tema, emailsA, mensaje, emailInstitucion);
//msg.setHeader("Header1", "Header2");
            msg.setSentDate(new Date());
//            Transport t = session.getTransport("smtp");
//            t.connect("" + emailInstitucion, clave);
//            t.send(msg, emailsA);
            Transport.send(msg);
//            t.send(msg);
        } catch (Exception e) {
            System.out.println("ERROR AL ENVIAR " + e);
            ExceptionManager.ManageException(e);
        }

    }

    private static MimeMessage getTraerMensaje(Session session, String from, InternetAddress[] to, String mensaje, String emailInstitucion, String respuestaA,byte[] adjuntoArchivo,String nombreArchivo) {
        try {
  // Se compone la parte del texto
            MimeBodyPart texto = new MimeBodyPart();
             //texto.setContent(mensaje,"text/html");
             texto.setText(mensaje, "ISO-8859-1", "html");
//            texto.setContent(mensaje, from);

            // Se compone el adjunto con la imagen
            BodyPart adjunto = new MimeBodyPart();
            DataSource dataSource = new ByteArrayDataSource(adjuntoArchivo, "*/*");
            DataHandler dataHandler = new DataHandler(dataSource);
            try {

            
                adjunto.setDataHandler(dataHandler);
                adjunto.setFileName(nombreArchivo);
            } catch (Exception e) {
                System.out.println("errir; adjunto"+e);
            }
//            adjunto.setText(mensaje);
            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            try {

            
            multiParte.addBodyPart(adjunto);
            } catch (Exception e) {
                System.out.println("errir; adjunto 785 "+e);
            }



            MimeMessage msg = new MimeMessage(session);
            msg.setSubject("" + from);
            if (!respuestaA.equals(null) && !respuestaA.equals("")) {
                InternetAddress[] emailsA = new InternetAddress[1];
                int i = 0;
                emailsA[i] = new InternetAddress(respuestaA + "");
                msg.setReplyTo(emailsA);
                msg.setSender(new InternetAddress(respuestaA));
                msg.setFrom(new InternetAddress("" + respuestaA));

            } else {
                msg.setFrom(new InternetAddress("" + emailInstitucion));
            }
             //msg.setText(mensaje, "ISO-8859-1", "html");
            if(adjuntoArchivo != null){
                    msg.setContent(multiParte);
            }else{
               msg.setText(mensaje, "ISO-8859-1", "html");
            }
            msg.addRecipients(Message.RecipientType.BCC, to);
            msg.addRecipients(Message.RecipientType.CC, respuestaA);
            msg.setSentDate(new Date());
            return msg;
        } catch (MessagingException ex) {

            ExceptionManager.ManageException(ex);
            return null;

        }


    }
    private static MimeMessage getTraerMensajeSimple(Session session, String from, InternetAddress[] to, String mensaje, String emailInstitucion) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setSubject("" + from);
            msg.setSentDate(new Date());
            msg.setFrom(new InternetAddress(emailInstitucion));
            msg.setText(mensaje, "ISO-8859-1", "html");
            msg.addRecipients(Message.RecipientType.BCC, to);
            msg.setSentDate(new Date());
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
