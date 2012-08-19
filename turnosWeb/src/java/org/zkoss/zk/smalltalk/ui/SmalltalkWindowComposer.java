package org.zkoss.zk.smalltalk.ui;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Html;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class SmalltalkWindowComposer extends GenericAutowireComposer {

	private static final long serialVersionUID = 1L;
	
	public void onCreateApplet(Event evt) {
		String appletDef= "<applet id=\"appletVisor\" z.autohide=\"true\" name=\"appletVisor\" width=\"100%\" height=\"100%\" code=\"org.zkoss.zk.smalltalk.applet.TestApplet\" MAYSCRIPT></applet>";

		Constraint constraint= new Constraint() {
			public void validate(Component comp, Object value) throws WrongValueException {
				if(value instanceof Integer) {
					Integer integer= (Integer)value;
					
					if(integer.intValue()> 255 || integer.intValue()< 0) {
						((Intbox)comp).setValue(new Integer(0));
						throw new WrongValueException("Wrong Value. [0-255] expected.");
					}
				} else {
					throw new WrongValueException("Invalid Type");
				}
			}
		};
		
		Window window= (Window)evt.getTarget();

		this.htmlComponent.setContent(appletDef);
		
		this.redBox.setConstraint(constraint);
		this.greenBox.setConstraint(constraint);
		this.blueBox.setConstraint(constraint);
		
		window.addEventListener("onNotifyServer", this);
	}

	public void onEvent(Event evt) throws Exception {
		super.onEvent(evt);
		
		if(evt.getName().equals("onNotifyServer")) {
			Messagebox.show("Event Received!!");
		}
	}
	
	public void onChangeColor(Event evt) {
		Clients.evalJavaScript("document.getElementById('appletVisor').changeColor(" + redBox.getValue().toString() + "," + greenBox.getValue().toString() + "," + blueBox.getValue().toString() + ")");
	}
	
	private Intbox redBox= null;
	private Intbox greenBox= null;
	private Intbox blueBox= null;
	private Html htmlComponent= null;
}
