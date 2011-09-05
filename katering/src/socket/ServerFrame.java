/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ServerFrame.java
 *
 * Created on 15/04/2011, 09:53:56 AM
 */

package socket;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.SwingUtilities;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class ServerFrame extends javax.swing.JFrame {

    /** Creates new form ServerFrame */
    public ServerFrame() {
        // super("ServerFrame");
        initComponents();
        this.setVisible(true);
        ejecutarServidor();
        
    }
 private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private ServerSocket servidor;
    private Socket conexion;
    private int contador = 1;
    //funciones de sockets
  
    // configurar y ejecutar el servidor
    public void ejecutarServidor() {
        // configurar servidor para que reciba conexiones; procesar las conexiones
        try {

            // Paso 1: crear un objeto ServerSocket.
            servidor = new ServerSocket(12345, 100);

            while (true) {

                try {
                    esperarConexion(); // Paso 2: esperar una conexión.
                    obtenerFlujos();        // Paso 3: obtener flujos de entrada y salida.
                    procesarConexion(); // Paso 4: procesar la conexión.
                } // procesar excepción EOFException cuando el cliente cierre la conexión
                catch (EOFException excepcionEOF) {
                    System.err.println("El servidor terminó la conexión");
                } finally {
                    cerrarConexion();   // Paso 5: cerrar la conexión.
                    ++contador;
                }

            } // fin de instrucción while

        } // fin del bloque try
        // procesar problemas con E/S
        catch (IOException excepcionES) {
            excepcionES.printStackTrace();
        }

    } // fin del método ejecutarServidor

       // esperar que la conexión llegue, después mostrar información de la conexión
    private void esperarConexion() throws IOException {
        mostrarMensaje("Esperando una conexión\n");
        conexion = servidor.accept(); // permitir al servidor aceptar la conexión
        mostrarMensaje("Conexión " + contador + " recibida de: "
                + conexion.getInetAddress().getHostName());
    }
    // enviar mensaje al cliente
    private void enviarDatos(String mensaje) {
        // enviar objeto al cliente
        try {
            salida.writeObject("SERVIDOR>>> " + mensaje);
            salida.flush();
            mostrarMensaje("\nSERVIDOR>>> " + mensaje);
        } // procesar problemas que pueden ocurrir al enviar el objeto
        catch (IOException excepcionES) {
            areaPantalla.append("\nError al escribir objeto");
        }
    }
    // obtener flujos para enviar y recibir datos
    private void obtenerFlujos() throws IOException {
        // establecer flujo de salida para los objetos
        salida = new ObjectOutputStream(conexion.getOutputStream());
        salida.flush(); // vaciar búfer de salida para enviar información de encabezado

        // establecer flujo de entrada para los objetos
        entrada = new ObjectInputStream(conexion.getInputStream());

        mostrarMensaje("\nSe recibieron los flujos de E/S\n");
    }

    // procesar la conexión con el cliente
    private void procesarConexion() throws IOException {
        // enviar mensaje de conexión exitosa al cliente
        String mensaje = "Conexión exitosa";
        enviarDatos(mensaje);

        // habilitar campoIntroducir para que el usuario del servidor pueda enviar mensajes
        establecerCampoTextoEditable(true);

        do { // procesar los mensajes enviados por el cliente

            // leer el mensaje y mostrarlo en pantalla
            try {
                mensaje = (String) entrada.readObject();
                mostrarMensaje("\n" + mensaje);
            } // atrapar problemas que pueden ocurrir al tratar de leer del cliente
            catch (ClassNotFoundException excepcionClaseNoEncontrada) {
                mostrarMensaje("\nSe recibió un tipo de objeto desconocido");
            }

        } while (!mensaje.equals("CLIENTE>>> TERMINAR"));

    } // fin del método procesarConexion

    // cerrar flujos y socket
    private void cerrarConexion() {
        mostrarMensaje("\nFinalizando la conexión\n");
        establecerCampoTextoEditable(false); // deshabilitar campoIntroducir

        try {
            salida.close();
            entrada.close();
            conexion.close();
        } catch (IOException excepcionES) {
            excepcionES.printStackTrace();
        }
    }
   private void mostrarMensaje(final String mensajeAMostrar) {
        // mostrar mensaje del subproceso de ejecución despachador de eventos
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

      private void establecerCampoTextoEditable(final boolean editable) {
        // mostrar mensaje del subproceso de ejecución despachador de eventos
        SwingUtilities.invokeLater(
                new Runnable() {  // clase interna para asegurar que la GUI se actualice apropiadamente

                    public void run() // establece la capacidad de modificar a campoIntroducir
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
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat");

        areaPantalla.setColumns(20);
        areaPantalla.setRows(5);
        jScrollPane1.setViewportView(areaPantalla);

        campoIntroducir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoIntroducirKeyPressed(evt);
            }
        });

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoIntroducir, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jButton1)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(campoIntroducir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void campoIntroducirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoIntroducirKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()== evt.VK_ENTER){
            enviarDatos(campoIntroducir.getText());
        }
    }//GEN-LAST:event_campoIntroducirKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
                new ServerFrame().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaPantalla;
    private javax.swing.JTextField campoIntroducir;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
