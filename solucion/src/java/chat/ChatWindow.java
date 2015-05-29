package chat;

//import org.zkforge.chat.ChatRoom;
//import org.zkforge.chat.Chatter;
import java.util.Date;
import jcinform.persistencia.Chat;
import jcinform.persistencia.Empleados;
import jcinform.procesos.Administrador;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

public class ChatWindow extends Window {

    private String sender;
    private ChatRoom chatroom;
    private Chatter chatter;
    private Desktop desktop;
    private boolean isLogin;
    Administrador adm = new Administrador();
    /**
     * setup initilization
     *
     */
    public void init() {
        desktop = Executions.getCurrent().getDesktop();
        String codigo = ((Textbox) getFellow("codigo")).getValue();
        chatroom = (ChatRoom) desktop.getWebApp().getAttribute("chatroom" + codigo);
        if (chatroom == null) {
            chatroom = new ChatRoom();
            desktop.getWebApp().setAttribute("chatroom" + codigo, chatroom);
        }
        onLogin();
    }

    public void onCreate() {
        init();
    }

    public void onOK() {
        if (isLogin()) {
            onSendMsg();
        } else {
            onLogin();
        }
    }

    /**
     * used for longin
     *
     */
    public void onLogin() {
        // enable server push for this desktop
        desktop.enableServerPush(true);

        sender = ((Textbox) getFellow("nickname")).getValue();

        // start the chatter thread
        chatter = new Chatter(chatroom, sender, getFellow("msgBoard"));
        chatter.start();

        // chage state of user
        setLogin(true);

        // refresh UI
        ((Textbox) getFellow("nickname")).setRawValue("");
        getFellow("login").setVisible(false);
        getFellow("dv").setVisible(true);
        getFellow("input").setVisible(true);
//        cargarMensajesAnteriores();
        //((Vbox)getFellow("msgBoard")).appendChild(new Label("mensaje"));
    }

    /**
     * used for exit
     *
     */
    public void onExit() {
        // clean up
        chatter.setDone();

        //disable server push
        desktop.enableServerPush(false);
        Session ses = Sessions.getCurrent();
        Empleados user =  (Empleados)ses.getAttribute("user");
        try {
            ((ContactosChat) desktop.getWebApp().getAttribute("contactoschat")).unsubscribe(new Contactos(user.getCodigoemp()+"",user.getApellidos()+" "+user.getNombres(),true));
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        setLogin(false);

        // refresh the UI
        getFellow("msgBoard").getChildren().clear();
        getFellow("login").setVisible(true);
        getFellow("dv").setVisible(false);
        getFellow("input").setVisible(false);
    }

    /**
     * used to send messages
     *
     */
    public void onSendMsg() {
        // add comment
         
        Label message = new Label();
        Label fecha = new Label();
        Div abc = new Div();
         
        abc.setStyle("border: 1px solid #999999; background-color:#F2F2F2; height:100%; "
                + "-moz-border-radius: 15px 15px 15px 15px; "
                + "/*para Safari y Chrome*/ "
                + "-webkit-border-radius: 5px 5px 5px 5px; "
                + "/* para Opera */ "
                + " border-radius: 5px 5px 5px 5px; float:right; padding: 7px; width: 210px;  "
                + " ");
  
        abc.appendChild(message);
        String stiloFecha = "color:gray; font-size:8px;float:right";
        //message.setStyle(stiloYo);
        fecha.setStyle(stiloFecha);
        Integer codigo = new Integer(((Textbox) getFellow("codigo")).getValue());
       
            fecha.setValue("Yo." + (new Date()).toLocaleString());
            message.setValue("\t" + ((Textbox) getFellow("msg")).getValue());
        try {
            Chat c = new Chat();
            Session ses = Sessions.getCurrent();
            Empleados user =  (Empleados)ses.getAttribute("user");
            c.setFecha(adm.Date());
            c.setUsuarioe(user.getCodigoemp());
            c.setCodigo(codigo);
            c.setMensaje(message.getValue());
            c.setVisto(false); 
            c.setUsuarior(codigo/user.getCodigoemp());
            adm.guardar(c); 
            c = null;
            user = null;
        } catch (Exception e) {
        }

        getFellow("msgBoard").appendChild(fecha);
        //getFellow("msgBoard").appendChild(message);
        getFellow("msgBoard").appendChild(abc);
        
        //con esto envio el mensaje al cliente
        chatter.sendMessage(((Textbox) getFellow("msg")).getValue());
        ((Textbox) getFellow("msg")).setRawValue("");

        //scroll down the scrollbar
		((Div)getFellow("dv")).smartUpdate("scrollTop", "10000");
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean bool) {
        isLogin = bool;
    }
}
