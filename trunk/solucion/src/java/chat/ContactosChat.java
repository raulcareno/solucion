

package chat;

import java.util.Collection;
import java.util.LinkedList;

//import org.zkforge.chat.Chatter;
//import org.zkoss.zk.ui.WrongValueException;

/**
 * 
 * @author robbiecheng
 */
public class ContactosChat {
	private Collection<Contactos> _chatters;

	private static final String SIGNAL = "-";

	public ContactosChat() {
		_chatters = new LinkedList<Contactos>();
	}

    public Collection<Contactos> getChatters() {
        return _chatters;
    }

    public void setChatters(Collection<Contactos> _chatters) {
        this._chatters = _chatters;
    }

 
  

	public void subscribe(Contactos chatter) {
		//chatter.addMessage(SIGNAL + "Bienvenido " + chatter.getNombre() + SIGNAL);
		synchronized (_chatters) {
			_chatters.add(chatter);
		}
//		say(chatter.getSender(), SIGNAL + chatter.getSender()
//				+ " se unio a la Sala" + SIGNAL);
	}

	/**
	 * unsubsctibe to the chatroom
	 * 
	 * @param chatter
	 */
	public void unsubscribe(Contactos chatter) {
		_chatters.remove(chatter);
		//chatter.addMessage(SIGNAL + "Chao " + chatter.getSender() + SIGNAL);
		synchronized (_chatters) {
			//for (Contactos _chatter : _chatters)
//				_chatter.addMessage(SIGNAL + chatter.getNombre()
//						+ " salio de la Sala!" + SIGNAL);
		}
	}
}
