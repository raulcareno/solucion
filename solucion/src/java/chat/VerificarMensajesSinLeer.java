/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Chat;
import jcinform.persistencia.Empleados;
import jcinform.procesos.Administrador;
import org.zkoss.lang.Threads;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.DesktopUnavailableException;
import org.zkoss.zk.ui.Executions;

import org.zkoss.zkex.zul.East;
import org.zkoss.zul.*;

/**
 *
 * @author Geovanny
 */
public class VerificarMensajesSinLeer {

    static Administrador adm;
    static ContactosChat contactos;

    public static void start(Panel mensajes, Listbox lista, Empleados emp, East este, Menu mensajesSin)
            throws InterruptedException {
        final Desktop desktop = Executions.getCurrent().getDesktop();
        //desktop.enableServerPush(false);
        contactos = (ContactosChat) desktop.getWebApp().getAttribute("contactoschat");
        if (contactos == null) {
            contactos = new ContactosChat();
            desktop.getWebApp().setAttribute("contactoschat", contactos);
        }
        if(adm == null){
            adm = new Administrador();
        }
        if (desktop.isServerPushEnabled()) {
//            Messagebox.show("Already started..-");
//            este.setSize("15%");
//            este.setVisible(true);
        } else {
            
            desktop.enableServerPush(true);

            new WorkingThread(mensajes, lista, emp, este, mensajesSin).start();
        }
    }

    public static void stop() throws InterruptedException {
        final Desktop desktop = Executions.getCurrent().getDesktop();
        if (desktop.isServerPushEnabled()) {
            desktop.enableServerPush(false);
        } else {
            Messagebox.show("Already stopped");
        }
    }

    private static class WorkingThread extends Thread {

        private final Desktop _desktop;
        private final Panel _mensajes;
        private final Listbox _lista;
        private final Empleados _empleado;
        private final East _este;
        private final Menu _mensajesSin;

        //(mensajes,cliente,telefono,comentario,emp)
        private WorkingThread(Panel mensajes, Listbox lista, Empleados empleados, East este, Menu mensajesSin) {
            //_desktop = info.getDesktop();
            _desktop = mensajes.getDesktop();
            _lista = lista;
            _empleado = empleados;
            _mensajes = mensajes;
            _este = este;
            _mensajesSin = mensajesSin;
        }

        public void run() {
            try {
                int i = 0;
                while (true) {
                    if (_mensajes.getDesktop() == null || !_desktop.isServerPushEnabled()) {
                        _desktop.enableServerPush(false);
                        return;
                    }
                    Executions.activate(_desktop);
                    try {
                        int a = 0;

                        for (Iterator it = _lista.getItems().iterator(); it.hasNext();) {
                            _lista.getItems().remove(a);
                        }
                        int cuantos = 0;
                        Contactos nuevo = new Contactos(_empleado.getCodigoemp() + "", _empleado.getApellidos() + " " + _empleado.getNombres(), true);
                        synchronized ((ContactosChat) _desktop.getWebApp().getAttribute("contactoschat")) {
                            boolean existe = false;

                            for (Iterator it = ((ContactosChat) _desktop.getWebApp().getAttribute("contactoschat")).getChatters().iterator(); it.hasNext();) {
                                Contactos object = (Contactos) it.next();
                                cuantos++;
                                if (!object.getIsLogin()) {
                                    ((ContactosChat) _desktop.getWebApp().getAttribute("contactoschat")).unsubscribe(nuevo);
                                }
                                if (object.id.equals(nuevo.getId())) {
                                    existe = true;
                                    cuantos--;
                                }

                            }
                            if (existe == false) {
                                ((ContactosChat) _desktop.getWebApp().getAttribute("contactoschat")).subscribe(nuevo);
                            }

                        }

                        for (Iterator it = ((ContactosChat) _desktop.getWebApp().getAttribute("contactoschat")).getChatters().iterator(); it.hasNext();) {
                            Contactos object = (Contactos) it.next();
                            Listitem li = new Listitem();
                            li.setValue(object);
                            li.appendChild(new Listcell(object.getId() + ""));
                            li.appendChild(new Listcell(object.getNombre() + ""));
                            _lista.appendChild(li);
                        }
//*******************************************************+++++++++++++++++++++++++++++++++++++++++++++++++********

                        List<Chat> c = adm.queryNativo("Select o.* from Chat as o where o.usuarior = '" + _empleado.getCodigoemp() + "' "
                                + " and o.visto = 0 group by o.usuarior ", Chat.class);

                        if (c.size() > 0) {
                            _mensajesSin.setLabel(".    " + c.size() + "");
                        } else {
                        }

                        _empleado.getCodigoemp();
 
                            ((Panel) _mensajes).setVisible(false);
                            _este.setSize("0%");
                            _este.setVisible(false);
 

                    } finally {
                        Executions.deactivate(_desktop);
                    }
                    Threads.sleep(10000);
                    i++;
                }
            } catch (DesktopUnavailableException ex) {
                
                System.out.println("The server push thread interrupted");
            } catch (InterruptedException ex) {
                System.out.println("The server push thread interrupted");
            }
        }
    }
}