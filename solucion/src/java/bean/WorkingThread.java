/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;

/**
 *
 * @author inform
 */
public class WorkingThread extends Thread {

    private static int _cnt;
    private Desktop _desktop;
    private Label _label;
    private final Object _mutex = new Integer(0);

    /** Called by thread.zul to create a label asynchronously.
     *To create a label, it start a thread, and wait for its completion.
     */
    public static final Label asyncCreate(Desktop desktop)
            throws InterruptedException {
        final WorkingThread worker = new WorkingThread(desktop);
        synchronized (worker._mutex) { //to avoid racing
            worker.start();
            Executions.wait(worker._mutex);
            return worker._label;
        }
    }

    public WorkingThread(Desktop desktop) {
        _desktop = desktop;
    }

    public void run() {
        _label = new Label("Execute " + ++_cnt);
        synchronized (_mutex) { //to avoid racing
            Executions.notify(_desktop, _mutex);
        }
    }
}
