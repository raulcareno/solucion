/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

import java.util.Iterator;
import java.util.List;
import jcinform.conexion.Administrador;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Soporte;
import org.zkoss.lang.Threads;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.DesktopUnavailableException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.*;

/**
 *
 * @author Geovanny
 */
public class CometServerPush {

    static Administrador adm;

    public static void start(Panel mensajes, Listbox lista, Empleados emp)
            throws InterruptedException {
        final Desktop desktop = Executions.getCurrent().getDesktop();

        if (desktop.isServerPushEnabled()) {
            Messagebox.show("Already started");
        } else {
            adm = new Administrador();
            desktop.enableServerPush(true);

            new WorkingThread(mensajes, lista, emp).start();
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
        //(mensajes,cliente,telefono,comentario,emp)

        private WorkingThread(Panel mensajes, Listbox lista, Empleados empleados) {
            //_desktop = info.getDesktop();
            _desktop = mensajes.getDesktop();
            _lista = lista;
            _empleado = empleados;
            _mensajes = mensajes;
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

                        List<Soporte> soportes = adm.query("Select o from Soporte as o "
                                + " WHERE o.empleados.codigo = '" + _empleado.getCodigo() + "' and o.revisoescalar = false ");
                        for (Iterator<Soporte> it = soportes.iterator(); it.hasNext();) {
                            Soporte soporte = it.next();
                            Listitem li = new Listitem();
                            li.setValue(soporte);
                            li.appendChild(new Listcell(soporte.getClientes() + ""));
                            li.appendChild(new Listcell("REVISAR"));
                            _lista.appendChild(li);
                        }
                        if (soportes.size() <= 0) {
                            ((Panel) _mensajes).setVisible(false);
                        } else {
                            ((Panel) _mensajes).setVisible(true);
                        }

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