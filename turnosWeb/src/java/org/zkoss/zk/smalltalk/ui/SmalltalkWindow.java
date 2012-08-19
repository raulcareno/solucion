package org.zkoss.zk.smalltalk.ui;

import org.zkoss.zk.au.AuRequest;
import org.zkoss.zk.au.Command;
import org.zkoss.zk.au.ComponentCommand;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Window;

public class SmalltalkWindow extends Window {

	public Command getCommand(String cmdId) {
	
		if(cmdId.equals("onNotifyServer")) {
    		return new CustomCommand(cmdId);
	    }
    	
    	return super.getCommand(cmdId);
   }
	
    private class CustomCommand extends ComponentCommand {
    	
    	public CustomCommand(String command) {
    		super(command, Command.SKIP_IF_EVER_ERROR|Command.CTRL_GROUP);
    	}
    			
    	protected void process(AuRequest request) {
    		Events.postEvent(new Event(request.getCommand().getId(),request.getComponent(),request.getData()));
    	}
    }
}
