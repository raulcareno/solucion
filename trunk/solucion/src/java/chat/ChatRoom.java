

package chat;

import java.util.Collection;
import java.util.LinkedList;

//import org.zkforge.chat.Chatter;
//import org.zkoss.zk.ui.WrongValueException;

/**
 * 
 * @author robbiecheng
 */
public class ChatRoom {
	private Collection<Chatter> _chatters;

	private static final String SIGNAL = "-";

	public ChatRoom() {
		_chatters = new LinkedList<Chatter>();
	}

	/**
	 * broadcast messages to all chatters except sender
	 * 
	 * @param sender
	 * @param message
	 */
	public void broadcast(String sender, String message) {
		//say(sender, sender + ":" + message);
        say(sender, "" + message);
	}

	private void say(String sender, String message) {
		synchronized (_chatters) {
			for (Chatter _chatter : _chatters)
				if (!_chatter.getSender().equals(sender)){
                    _chatter.addMessage(message);
                }
		}
	}

	/**
	 * subscribte to the chatroom
	 * 
	 * @param chatter
	 */

	public void subscribe(Chatter chatter) {
		//chatter.addMessage(SIGNAL + "Bienvenido " + chatter.getSender() + SIGNAL);
		synchronized (_chatters) {
			_chatters.add(chatter);
		}
		//say(chatter.getSender(), SIGNAL + chatter.getSender()
			//	+ " se unio a la Sala" + SIGNAL);
	}

	/**
	 * unsubsctibe to the chatroom
	 * 
	 * @param chatter
	 */
	public void unsubscribe(Chatter chatter) {
		_chatters.remove(chatter);
		chatter.addMessage(SIGNAL + "Chao " + chatter.getSender() + SIGNAL);
		synchronized (_chatters) {
			for (Chatter _chatter : _chatters)
				_chatter.addMessage(SIGNAL + chatter.getSender()
						+ " salio de la Sala!" + SIGNAL);
		}
	}
}
