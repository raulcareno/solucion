/* Chatter.java

 {{IS_NOTE
 Purpose:
 
 Description:
 
 History:
 Aug 17, 2007 12:58:55 PM , Created by robbiecheng
 }}IS_NOTE

 Copyright (C) 2007 Potix Corporation. All Rights Reserved.

 {{IS_RIGHT
 This program is distributed under GPL Version 2.0 in the hope that
 it will be useful, but WITHOUT ANY WARRANTY.
 }}IS_RIGHT
 */

package chat;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.zkoss.lang.Threads;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.DesktopUnavailableException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

/**
 * 
 * @author robbiecheng
 */
public class Chatter extends Thread {
	private static final Log log = Log.lookup(Chatter.class);

	private boolean _ceased;

	private ChatRoom _chatroom;

	private final Desktop _desktop;

	private Component _msgBoard;

	private String _name;

	private List<String> _msgs;

	public Chatter(ChatRoom chatroom, String name, Component msgBoard) {
		_chatroom = chatroom;
		_name = name;
		_msgBoard = msgBoard;
		_desktop = msgBoard.getDesktop();
		_msgs = new LinkedList<String>();
	}
	
	/**
	 * send new messages to UI if necessay
	 */
	public void run() {
		if (!_desktop.isServerPushEnabled()) 
			_desktop.enableServerPush(true);
		log.info("active chatter : " + getName());
		_chatroom.subscribe(this);
		try {
			while (!_ceased) {
				try {
					if (_msgs.isEmpty()) {
						Threads.sleep(500);// Update each 0.5 seconds
					} else {
						Executions.activate(_desktop);
						try {
							process();
						} finally {
							Executions.deactivate(_desktop);
						}
					}
				} catch (DesktopUnavailableException ex) {
					throw ex;
				} catch (Throwable ex) {
					log.error(ex);
					throw UiException.Aide.wrap(ex);
				}
			}
		} finally {
			log.info(getName() + " logout the chatroom!");
			_chatroom.unsubscribe(this);			
			if (_desktop.isServerPushEnabled())
				Executions.getCurrent().getDesktop().enableServerPush(false);
		}
		log.info("The chatter thread ceased: " + getName() );
	}

	/**
	 * return sender's name
	 * 
	 * @return
	 */
	public String getSender() {
		return _name;
	}

	/**
	 * add message to this chatter
	 * 
	 * @param message
	 */
	public void addMessage(String message) {
		_msgs.add(message);
	}

	/**
	 * send message to others
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		_chatroom.broadcast(getSender(), message);
	}

	private void renderMessages() {
		while (!_msgs.isEmpty()) {
			String msg;
			synchronized (_msgs) {
				msg = _msgs.remove(0);
			}
            
         Label message = new Label(msg);
//        Label fecha = new Label();
        Div abc = new Div();
        abc.setStyle("border: 1px solid #999999; background-color:#B0ECEC; height:100%; "
                + "-moz-border-radius: 15px 15px 15px 15px; "
                + "/*para Safari y Chrome*/ "
                + "-webkit-border-radius: 5px 5px 5px 5px; "
                + "/* para Opera */ "
                + " border-radius: 5px 5px 5px 5px;  padding: 7px;  ");
  
        abc.appendChild(message);
        
//            Label lbl = new Label(msg);
            //lbl.setStyle("color:red;");
//            String stiloYo = " background-color: #dbedfe;background-image: url(./images/fondoChat1.png);";
//            lbl.setStyle(stiloYo);
            
            Label fecha = new Label(((new Date()).toLocaleString()));
            String stiloFecha = "color:gray; font-size:8px;";
            
            fecha.setStyle(stiloFecha);
            
           _msgBoard.appendChild(fecha);
		   _msgBoard.appendChild(abc);
            try {
              ((Div) _desktop.getWebApp().getAttribute("dv")).smartUpdate("scrollTop", "10000");  
            } catch (Exception e) {
                
            }
              
              
           //((Div)getFellow("dv")).smartUpdate("scrollTop", "10000");
		}
	}

	private void process() throws Exception {
		renderMessages();
	}
	/**
	 * stop this thread
	 * 
	 */
	public void setDone() {
		_ceased = true;
	}

}
