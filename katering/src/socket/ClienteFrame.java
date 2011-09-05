/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ClienteFrame.java
 *
 * Created on 15/04/2011, 09:53:51 AM
 */

package socket;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.SwingUtilities;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class ClienteFrame extends javax.swing.JFrame {
 private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private String mensaje = "";
    private String servidorChat;
    private Socket cliente;
    /** Creates new form ClienteFrame */
    public ClienteFrame() {

        initComponents();
this.setVisible(true);
        servidorChat = "127.0.0.1";
        servidorChat = "192.168.10.2";
//         Cliente aplicacion;
//        if (args.length == 0) {
//            aplicacion = new Cliente("127.0.0.1");
//        } else {
//            aplicacion = new Cliente(args[ 0]);
//        }
        ejecutarCliente();
    }
 


      // conectarse al servidor y procesar mensajes del servidor
    private void ejecutarCliente() {
        // conectarse al servidor, obtener flujos, procesar la conexión
        try {
            conectarAServidor(); // Paso 1: crear un socket para realizar la conexión
            obtenerFlujos();      // Paso 2: obtener los flujos de entrada y salida
            procesarConexion(); // Paso 3: procesar la conexión
        } // el servidor cerró la conexión
        catch (EOFException excepcionEOF) {
            System.err.println("El cliente termino la conexión");
        } // procesar los problemas que pueden ocurrir al comunicarse con el servidor
        catch (IOException excepcionES) {
            excepcionES.printStackTrace();
        } finally {
            cerrarConexion(); // Paso 4: cerrar la conexión
        }

    } // fin del método ejecutarCliente

    // conectarse al servidor
    private void conectarAServidor() throws IOException {
        mostrarMensaje("Intentando realizar conexión\n");
        // crear Socket para realizar la conexión con el servidor
        cliente = new Socket(InetAddress.getByName(servidorChat), 12345);
        // mostrar la información de la conexión
        mostrarMensaje("Conectado a: "+ cliente.getInetAddress().getHostName());
    }

    // obtener flujos para enviar y recibir datos
    private void obtenerFlujos() throws IOException {
        // establecer flujo de salida para los objetos
        salida = new ObjectOutputStream(cliente.getOutputStream());
        salida.flush(); // vacíar búfer de salida para enviar información de encabezado

        // establecer flujo de entrada para los objetos
        entrada = new ObjectInputStream(cliente.getInputStream());

        mostrarMensaje("\nSe recibieron los flujos de E/S\n");
    }

    // procesar la conexión con el servidor
    private void procesarConexion() throws IOException {
        // habilitar campoIntroducir para que el usuario del cliente pueda enviar mensajes
        establecerCampoTextoEditable(true);

        do { // procesar mensajes enviados del servidor

            // leer mensaje y mostrarlo en pantalla
            try {
                mensaje = (String) entrada.readObject();
                mostrarMensaje("\n" + mensaje);
            } // atrapar los problemas que pueden ocurrir al leer del servidor
            catch (ClassNotFoundException excepcionClaseNoEncontrada) {
                mostrarMensaje("\nSe recibió un objeto de tipo desconocido");
            }

        } while (!mensaje.equals("SERVIDOR>>> TERMINAR"));

    } // fin del método procesarConexion

    // cerrar flujos y socket
    private void cerrarConexion() {
        mostrarMensaje("\nCerrando conexión");
        establecerCampoTextoEditable(false); // deshabilitar campoIntroducir

        try {
            salida.close();
            entrada.close();
            cliente.close();
        } catch (IOException excepcionES) {
            excepcionES.printStackTrace();
        }
    }

    // enviar mensaje al servidor
    private void enviarDatos(String mensaje) {
        // enviar objeto al servidor
        try {
            salida.writeObject("CLIENTE>>> " + mensaje);
            salida.flush();
            mostrarMensaje("\nCLIENTE>>> " + mensaje);
        } // procesar los problemas que pueden ocurrir al enviar el objeto
        catch (IOException excepcionES) {
            areaPantalla.append("\nError al escribir el objeto");
        }
    }

    // método utilitario que es llamado desde otros subprocesos para manipular a
    // areaPantalla en el subproceso despachador de eventos
    private void mostrarMensaje(final String mensajeAMostrar) {
        // mostrar mensaje del subproceso de ejecución de la GUI
        SwingUtilities.invokeLater(
                new Runnable() {  // clase interna para asegurar que la GUI se actualice apropiadamente

                    public void run() // actualiza areaPantalla
                    {
                        areaPantalla.append(mensajeAMostrar);
                        areaPantalla.setCaretPosition(
                                areaPantalla.getText().length());
                    }
                } // fin de la clase interna
                ); // fin de la llamada a SwingUtilities.invokeLater
    }

    // método utilitario que es llamado desde otros subprocesos para manipular a
    // campoIntroducir en el subproceso despachador de eventos
    private void establecerCampoTextoEditable(final boolean editable) {
        // mostrar mensaje del subproceso de ejecución de la GUI
        SwingUtilities.invokeLater(
                new Runnable() {  // clase interna para asegurar que la GUI se actualice apropiadamente

                    public void run() // establece la capacidad de modificar campoIntroducir
                    {
                        campoIntroducir.setEditable(editable);
                    }
                } // fin de la clase interna
                ); // fin de la llamada a SwingUtilities.invokeLater
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        areaPantalla = new javax.swing.JTextArea();
        campoIntroducir = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CLIENTE");

        areaPantalla.setColumns(20);
        areaPantalla.setRows(5);
        jScrollPane1.setViewportView(areaPantalla);

        campoIntroducir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoIntroducirKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoIntroducir, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(campoIntroducir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void campoIntroducirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoIntroducirKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()== evt.VK_ENTER){
            enviarDatos(campoIntroducir.getText());
        }

    }//GEN-LAST:event_campoIntroducirKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
                new ClienteFrame().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaPantalla;
    private javax.swing.JTextField campoIntroducir;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
