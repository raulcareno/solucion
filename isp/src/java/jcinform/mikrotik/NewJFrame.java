/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.mikrotik;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author inform
 */
public class NewJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        datos = new javax.swing.JFormattedTextField();
        datos1 = new javax.swing.JFormattedTextField();

        jFormattedTextField1.setText("jFormattedTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        datos.setText("/ppp/secret/add");

        datos1.setText("=name=1234 password=123 profile=HOME-VIP");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(datos)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(23, 23, 23))))
            .addComponent(datos1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addComponent(datos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(datos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jButton1)
                .addGap(135, 135, 135))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        llamar(datos.getText(),datos1.getText());
        
    }//GEN-LAST:event_jButton1ActionPerformed
  public void salida(String a){
      System.out.println(""+a);
  }
  
   private Socket sock = null;
   private DataOutputStream out = null;
   private DataInputStream in = null;
   private String ipAddress;
   private int ipPort;
   private boolean connected = false;
   private String message = "Not connected";
   private ReadCommand readCommand = null;
   private WriteCommand writeCommand = null;
   private Thread listener = null;
   LinkedBlockingQueue queue = new LinkedBlockingQueue(40);
    public void llamar(String datos,String datos2){
        try {
//            InetAddress ia = InetAddress.getByName("186.5.68.17");
//            Socket sock = new Socket("186.5.68.17", 8728);
////              if (ia.isReachable(1000)) {
//               sock = new Socket("186.5.68.17", 8728);
//               in = new DataInputStream(sock.getInputStream());
//               out = new DataOutputStream(sock.getOutputStream());
//               connected = true;
//               readCommand = new ReadCommand(in, queue);
//               writeCommand = new WriteCommand(out);
//               writeCommand.setCommand(s).runCommand();
////               this.listen();
//                  System.out.println("Connected");
////           } else {
////               System.out.println("Desc");
////           }
               ApiConn ret = new ApiConn("186.5.68.17", 8728);
          if (!ret.isConnected()) {
              ret.start();
              
              System.out.println(""+ret.getMessage());
              try {
                  ret.join();
                  if (ret.isConnected()) {
                      String mbs = "mbomega";
                      char[]  pas  = mbs.toCharArray();
                      ret.login("mb", pas);
                  }else{
                      System.out.println("sin conexion");
                  }
              } catch (InterruptedException ex) {
                  Logger.getLogger(T3apiView.class.getName()).log(Level.SEVERE, null, ex);
                  return;
              }
          }
          //ret.sendCommand("/ip/address/print");
          Thread.sleep(1000);
            System.out.println("*********************************************");
          //ret.sendCommand("/user/active/listen");
          //ret.sendCommand("ip route print");
          //ret.sendCommand(datos);
          //ret.sendCommand(datos2);
          ret.sendCommand("/ppp/secret/add");
          ret.sendCommand("=name=1234");
          ret.sendCommand("=password=123");
          ret.sendCommand("=profile=HOME-VIP");
             //ret.sendCommand("ppp secret add name=1234 password=123 profile=HOME-VIP");
          
          
          DataReceiver dataRec = new DataReceiver(ret, this);
          dataRec.start();
        } catch (Exception ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField datos;
    private javax.swing.JFormattedTextField datos1;
    private javax.swing.JButton jButton1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    // End of variables declaration//GEN-END:variables
}
