/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.Applet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author inform
 */
public class acerca2 extends javax.swing.JDialog {

    /**
     * Creates new form acerca2
     */
    public acerca2(java.applet.Applet parent, boolean modal) {
//        super(parent, modal);
        initComponents();
    }
    public acerca2(){}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        javax.swing.JLabel imageLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel appTitleLabel = new javax.swing.JLabel();
        javax.swing.JLabel versionLabel = new javax.swing.JLabel();
        javax.swing.JLabel appVersionLabel = new javax.swing.JLabel();
        javax.swing.JLabel appHomepageLabel = new javax.swing.JLabel();
        javax.swing.JLabel homepageLabel = new javax.swing.JLabel();
        javax.swing.JLabel appHomepageLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel appHomepageLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        javax.swing.JLabel homepageLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        todos = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Acerca de ..");
        getContentPane().setLayout(null);

        jPanel2.setLayout(null);

        jButton3.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(51, 51, 51));
        jButton3.setText("www.jcinform.com");
        jButton3.setBorder(null);
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton3.setFocusPainted(false);
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3);
        jButton3.setBounds(10, 0, 140, 14);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(50, 80, 140, 20);

        imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jcinform/imagenes/ico.png"))); // NOI18N
        getContentPane().add(imageLabel);
        imageLabel.setBounds(10, 10, 180, 90);

        jButton1.setText("Aceptar");
        jButton1.setSelected(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(60, 110, 100, 30);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(appTitleLabel.getFont().getStyle() | java.awt.Font.BOLD, appTitleLabel.getFont().getSize()+4));
        appTitleLabel.setForeground(new java.awt.Color(0, 51, 153));
        appTitleLabel.setText("Sistema de Turnos");
        jPanel1.add(appTitleLabel);
        appTitleLabel.setBounds(30, 0, 190, 19);

        versionLabel.setFont(versionLabel.getFont().deriveFont(versionLabel.getFont().getStyle() | java.awt.Font.BOLD));
        versionLabel.setText("Version:");
        jPanel1.add(versionLabel);
        versionLabel.setBounds(10, 20, 70, 14);

        appVersionLabel.setText("1.0");
        jPanel1.add(appVersionLabel);
        appVersionLabel.setBounds(80, 20, 70, 14);

        appHomepageLabel.setText("ventas@jcinform.com");
        jPanel1.add(appHomepageLabel);
        appHomepageLabel.setBounds(80, 60, 104, 14);

        homepageLabel.setFont(homepageLabel.getFont().deriveFont(homepageLabel.getFont().getStyle() | java.awt.Font.BOLD));
        homepageLabel.setText("Ventas:");
        jPanel1.add(homepageLabel);
        homepageLabel.setBounds(10, 60, 70, 14);

        appHomepageLabel1.setText("080-162 211");
        jPanel1.add(appHomepageLabel1);
        appHomepageLabel1.setBounds(80, 100, 143, 14);

        appHomepageLabel3.setText("jcinform@gmail.com");
        jPanel1.add(appHomepageLabel3);
        appHomepageLabel3.setBounds(80, 80, 143, 14);

        jButton2.setForeground(new java.awt.Color(0, 0, 255));
        jButton2.setText("http://www.jcinform.com");
        jButton2.setBorder(null);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.setFocusPainted(false);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(80, 40, 140, 14);

        homepageLabel1.setFont(homepageLabel1.getFont().deriveFont(homepageLabel1.getFont().getStyle() | java.awt.Font.BOLD));
        homepageLabel1.setText("Web:");
        jPanel1.add(homepageLabel1);
        homepageLabel1.setBounds(10, 40, 70, 14);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(210, 10, 230, 120);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.setEnabled(false);

        todos.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        todos.setEditable(false);
        todos.setText("Todos los derechos reservados a JCINFORM Soluciones Informáticas. Prohibida la reproducción total o parcial de éste software.");
        todos.setSelectedTextColor(new java.awt.Color(51, 51, 51));
        todos.setSelectionColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(todos);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 145, 430, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        abrirurl();
    }//GEN-LAST:event_jButton3ActionPerformed
                                       
void abrirurl(){
     try {
            // TODO add your handling code here:
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"http://www.jcinform.com");
        } catch (IOException ex) {
            Logger.getLogger(acerca2.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        abrirurl();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(acerca2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(acerca2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(acerca2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(acerca2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                acerca2 dialog = new acerca2(null, true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane todos;
    // End of variables declaration//GEN-END:variables
}
