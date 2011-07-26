/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

/**
 *
 * @author jcinform
 */
import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.MessageboxDlg;

/**
 * @author marcos.sousa
 * 
 */
public class InputMessageBox extends Messagebox {

    private static final long serialVersionUID = 1L;
    // path of the messagebox zul-template
    private static String oldTemplate = Messagebox.getTemplate();
    private static String _templ = "/inputMessageBox.zul";
    //private static String _templ = "/zul/inputMessageBox.zul";
    private static Textbox txtQuestion;

    public static final Object showQuestion(String message, String title)
            throws InterruptedException {
        setTemplate(_templ);
        Object object = null;
        if (showInput(message, title, Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, 0, null) == Messagebox.OK) {
            object = txtQuestion.getValue(); // TODO: Remember NULL means that CANCEL, otherwise means an Object is being passed. I used StringUtils from commons lang to trim to empty
        }
        setTemplate(oldTemplate);
        return object;
    }

    /** Shows a message box and returns what button is pressed.
     *
     * @param title the title. If null, {@link WebApp#getAppName} is used.
     * @param buttons a combination of {@link #OK}, {@link #CANCEL},
     * {@link #YES}, {@link #NO}, {@link #ABORT}, {@link #RETRY},
     * and {@link #IGNORE}. If zero, {@link #OK} is assumed
     * @param icon one of predefined images: {@link #QUESTION},
     * {@link #EXCLAMATION}, {@link #ERROR}, {@link #NONE}, or any style class
     * name(s) to show an image.
     * @param focus one of button to have to focus. If 0, the first button
     * will gain the focus. One of {@link #OK}, {@link #CANCEL},
     * {@link #YES}, {@link #NO}, {@link #ABORT}, {@link #RETRY},
     * and {@link #IGNORE}.
     * @param listener the event listener which is invoked when a button
     * is clicked. Ignored if null.
     * It is useful if the event processing thread is disabled
     * ({@link org.zkoss.zk.ui.util.Configuration#enableEventThread}).
     * If the event processing thread is disable, this method always
     * return {@link #OK}. To know which button is pressed, you have to pass an
     * event listener. Then, when the user clicks a button, the event
     * listener is invoked. You can identify which button is clicked
     * by examining the event name ({@link org.zkoss.zk.ui.event.Event#getName}) as shown
     * in the following table. Alternatively, you can examine the value
     * of {@link org.zkoss.zk.ui.event.Event#getData}, which must be an
     * integer represetinng the button, such as {@link #OK}, {@link #YES}
     * and so on.
     * <table border="1">
     *<tr><td>Button</td><td>Event Name</td></tr>
     *<tr><td>OK</td><td>onOK</td></tr>
     *<tr><td>Cancel</td><td>onCancel</td></tr>
     *<tr><td>Yes</td><td>onYes</td></tr>
     *<tr><td>No</td><td>onNo</td></tr>
     *<tr><td>Retry</td><td>onRetry</td></tr>
     *<tr><td>Abort</td><td>onAbort</td></tr>
     *<tr><td>Ignore</td><td>onIgnore</td></tr>
     *</table>
     * @return the button being pressed (one of {@link #OK}, {@link #CANCEL},
     * {@link #YES}, {@link #NO}, {@link #ABORT}, {@link #RETRY},
     * and {@link #IGNORE}).
     * Note: if the event processing thread is disable, it always
     * returns {@link #OK}.
     * @since 3.0.4
     */
    public static final int showInput(String message, String title, int buttons, String icon, int focus, EventListener listener) throws InterruptedException {
        final Map params = new HashMap();
        params.put("message", message);
        params.put("title", title != null ? title
                : Executions.getCurrent().getDesktop().getWebApp().getAppName());
        params.put("icon", icon);
        params.put("buttons", new Integer(
                (buttons & (OK | CANCEL | YES | NO | ABORT | RETRY | IGNORE)) != 0 ? buttons : OK));
        if ((buttons & OK) != 0) {
            params.put("OK", new Integer(OK));
        }
        if ((buttons & CANCEL) != 0) {
            params.put("CANCEL", new Integer(CANCEL));
        }
        if ((buttons & YES) != 0) {
            params.put("YES", new Integer(YES));
        }
        if ((buttons & NO) != 0) {
            params.put("NO", new Integer(NO));
        }
        if ((buttons & RETRY) != 0) {
            params.put("RETRY", new Integer(RETRY));
        }
        if ((buttons & ABORT) != 0) {
            params.put("ABORT", new Integer(ABORT));
        }
        if ((buttons & IGNORE) != 0) {
            params.put("IGNORE", new Integer(IGNORE));
        }

        final MessageboxDlg dlg = (MessageboxDlg) Executions.createComponents(_templ, null, params);
        dlg.setButtons(buttons);
        dlg.setEventListener(listener);
        txtQuestion = (Textbox) dlg.getFellowIfAny("txtQuestion");
        if (focus > 0) {
            dlg.setFocus(focus);
        }

        if (dlg.getDesktop().getWebApp().getConfiguration().isEventThreadEnabled()) {
            try {
                dlg.doModal();
            } catch (Throwable ex) {
                if (ex instanceof InterruptedException) {
                    throw (InterruptedException) ex;
                }
                try {
                    dlg.detach();
                } catch (Throwable ex2) {
                    System.err.println("Failed to detach when recovering from an error " + ex2.toString());
                }
                throw UiException.Aide.wrap(ex);
            }
            return dlg.getResult();
        } else {
            dlg.doHighlighted();
            return OK;
        }
    }
}