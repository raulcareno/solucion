package chat;

//import org.zkforge.chat.ChatRoom;
//import org.zkforge.chat.Chatter;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ChatWindow extends Window {

	private String sender;

	private ChatRoom chatroom;

	private Chatter chatter;

	private Desktop desktop;

	private boolean isLogin;

	/**
	 * setup initilization
	 * 
	 */
	public void init() {
		desktop = Executions.getCurrent().getDesktop();

		chatroom = (ChatRoom) desktop.getWebApp().getAttribute("chatroom");
		if (chatroom == null) {
			chatroom = new ChatRoom();
			desktop.getWebApp().setAttribute("chatroom", chatroom);
		}
        onLogin();
	}

	public void onCreate() {
		init();
	}

	public void onOK() {
		if (isLogin())
			onSendMsg();
		else
			onLogin();
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
		message.setValue(sender + ":" + ((Textbox) getFellow("msg")).getValue());
		getFellow("msgBoard").appendChild(message);
		chatter.sendMessage(((Textbox) getFellow("msg")).getValue());
		((Textbox) getFellow("msg")).setRawValue("");
		
		//scroll down the scrollbar
//		getFellow("dv").smartUpdate("scrollTop", "10000");
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin( boolean bool) {
		isLogin = bool;
	}
}
