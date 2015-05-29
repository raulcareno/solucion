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
import org.zkoss.zk.ui.event.EventListener;
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
        desktop.enableServerPush(false);
        contactos = (ContactosChat) desktop.getWebApp().getAttribute("contactoschat");
        if (contactos == null) {
            contactos = new ContactosChat();
            desktop.getWebApp().setAttribute("contactoschat", contactos);
        }
        if (desktop.isServerPushEnabled()) {
            Messagebox.show("Already started..-");
            este.setSize("15%");
            este.setVisible(true);
        } else {
            adm = new Administrador();
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
                    if (_mensajes.getDesktop() == null
                            || !_desktop.isServerPushEnabled()) {
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
//                            try {
//                                List<Chat>  c = adm.queryNativo("Select o.* from Chat as o where o.usuarior = '"+object.getId()+"' "
//                                + " and o.visto = 0 group by o.usuarior ",Chat.class);
//                                if(c.size()>0){
//                                    li.appendChild(new Listcell(object.getNombre() + "("+c.size()+")"));       
//                                }else{
//                                    li.appendChild(new Listcell(object.getNombre() + ""));    
//                                }
//                            } catch (Exception e) {
                            li.appendChild(new Listcell(object.getNombre() + ""));
//                            }




                            _lista.appendChild(li);
                        }
//*******************************************************+++++++++++++++++++++++++++++++++++++++++++++++++********

                        List<Chat> c = adm.queryNativo("Select o.* from Chat as o where o.usuarior = '" + _empleado.getCodigoemp() + "' "
                                + " and o.visto = 0 group by o.usuarior ", Chat.class);

                        if (c.size() > 0) {

                            _mensajesSin.setLabel(".    " + c.size() + "");
//                            for (int is = 0; is < _mensajesSin.getChildren().size(); is++) {
//                                _mensajesSin.getChildren().remove(is);
//                            }
//                            Menupopup popup = new Menupopup();
//                            popup.setId("hola");
//                            //_mensajesSin.removeChild(p);
//                            for (Iterator<Chat> it = c.iterator(); it.hasNext();) {
//                                Chat chat = it.next();
//                                final Empleados empleadoEn = (Empleados) adm.querySimple("Select o from Empleados as o where o.codigoemp = '" + chat.getUsuarioe() + "'");
//                                Menuitem mi = new Menuitem(empleadoEn.toString() + " (" + adm.querySimple("Select count(o) from Chat as o where o.visto = false and o.usuarior = '" + chat.getUsuarior() + "'").toString() + ")");
//                                final Contactos nuev2o = new Contactos(empleadoEn.getCodigoemp() + "", empleadoEn.getApellidos() + " " + empleadoEn.getNombres(), true);
//                                mi.setValue(empleadoEn.getCodigoemp() + "");
//                                mi.addEventListener("onClick", new EventListener() {
//
//                                    public void onEvent(org.zkoss.zk.ui.event.Event event) throws Exception {
//                                        try {
//                                            final Window win = (Window) Executions.createComponents("/chat/chat.zul", null, null);
//                                            win.setClosable(true);
//                                            win.setAttribute("contacto", nuev2o);
//                                            win.setTitle("Conversación");
//                                            win.setPosition("bottom,right");
//                                            win.doOverlapped();
//                                            adm.ejecutaSqlNativo("update Chat set visto = true where usuarior = " + nuev2o.getId() + " and usuarioe = " + empleadoEn.getCodigoemp() + "  ");
//                                        } catch (Exception e) {
//                                            System.out.println("YA SE HA CREADO UNA SESION");
//                                        }
//                                    }
//                                });
//
//                                mi.setParent(popup);
//                            }
//                            try {
//                                popup.setParent(_mensajesSin);
//                            } catch (Exception ab) {
//                                System.out.println("" + ab);
//                            }
//                            _mensajesSin.setVisible(false);
                        } else {
//                            _mensajesSin.setVisible(false);
                        }

                        _empleado.getCodigoemp();
//                        if (cuantos > 1) {
//                            ((Panel) _mensajes).setVisible(false);
//                            _este.setSize("15%");
//                            _este.setVisible(false);
//                        } else {
                            ((Panel) _mensajes).setVisible(false);
                            _este.setSize("0%");
                            _este.setVisible(false);
//                        }

                        //**********************************************+++************************************************************

 














//                        List<Auditoria> soportes = adm.query("Select o from Auditoria as o "
//                                + " WHERE o.fecha between '2014-11-22 00:00:01' and '2014-11-24 23:59:01' order by o.fecha asc ");
//
//                        for (Iterator<Auditoria> it = soportes.iterator(); it.hasNext();) {
//                            Auditoria soporte = it.next();
//                            Listitem li = new Listitem();
//                            li.setValue(soporte);
//                            li.appendChild(new Listcell(soporte.getTabla() + ""));
//                            li.appendChild(new Listcell(soporte.getFecha().toLocaleString() + ""));
//                            _lista.appendChild(li);
//                        }
//                        if (cuantos > 1) {
//                            ((Panel) _mensajes).setVisible(false);
//                            _este.setSize("0%");
//                            _este.setVisible(false);
//                        } else {
//                            ((Panel) _mensajes).setVisible(true);
//                            _este.setSize("15%");
//                            _este.setVisible(true);
//                        }

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