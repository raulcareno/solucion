/*
 * acerca.java
 *
 * Created on 30 de septiembre de 2008, 09:24 PM
 */

package peaje.formas;

import hibernate.Empresa;
import java.awt.Container;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author  geovanny
 */
public class acerca  extends javax.swing.JInternalFrame  {
    private Container desktopContenedor;

    /** Creates new form acerca */
    public acerca() {
        initComponents();
        this.setSize(458, 205);
    }
  public acerca(java.awt.Frame parent, boolean modal,Empresa emp) {
//          super(parent,modal);
        //this.desktopContenedor = lo.contenedor;
        initComponents();
//        derecho.setText(emp.getRazon());
        this.setSize(464, 220);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lavel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
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

        lavel2.setText("Una licencia de uso para:");

        jLabel4.setText("JCINFORM Soluciones Informáticas");

        setTitle("Acerca de ...");
        getContentPane().setLayout(null);

        jPanel2.setLayout(null);

        jButton3.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(51, 51, 51));
        jButton3.setText("www.siscontrol.com.ec");
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
        jButton3.setBounds(0, 0, 160, 14);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(50, 80, 150, 20);

        imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images_botones/ico.png"))); // NOI18N
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
        appTitleLabel.setText("Sistema de Parqueadero");
        jPanel1.add(appTitleLabel);
        appTitleLabel.setBounds(30, 0, 190, 19);

        versionLabel.setFont(versionLabel.getFont().deriveFont(versionLabel.getFont().getStyle() | java.awt.Font.BOLD));
        versionLabel.setText("Version:");
        jPanel1.add(versionLabel);
        versionLabel.setBounds(10, 20, 70, 14);

        appVersionLabel.setText("1.7 01012015");
        jPanel1.add(appVersionLabel);
        appVersionLabel.setBounds(80, 20, 140, 14);

        appHomepageLabel.setText("Ing. Geovanny Jadán Ortega");
        jPanel1.add(appHomepageLabel);
        appHomepageLabel.setBounds(80, 60, 141, 14);

        homepageLabel.setFont(homepageLabel.getFont().deriveFont(homepageLabel.getFont().getStyle() | java.awt.Font.BOLD));
        homepageLabel.setText("Ventas:");
        jPanel1.add(homepageLabel);
        homepageLabel.setBounds(10, 60, 70, 14);

        appHomepageLabel1.setText("0980-162 211/ 0996-038 706");
        jPanel1.add(appHomepageLabel1);
        appHomepageLabel1.setBounds(80, 100, 143, 14);

        appHomepageLabel3.setText("info@siscontrol.com.ec");
        jPanel1.add(appHomepageLabel3);
        appHomepageLabel3.setBounds(80, 80, 143, 14);

        jButton2.setForeground(new java.awt.Color(0, 0, 255));
        jButton2.setText("http://www.siscontrol.com.ec");
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
        jButton2.setBounds(70, 40, 150, 14);

        homepageLabel1.setFont(homepageLabel1.getFont().deriveFont(homepageLabel1.getFont().getStyle() | java.awt.Font.BOLD));
        homepageLabel1.setText("Web:");
        jPanel1.add(homepageLabel1);
        homepageLabel1.setBounds(10, 40, 70, 14);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(210, 10, 230, 120);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.setEnabled(false);

        todos.setEditable(false);
        todos.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        todos.setText("Todos los derechos reservados a SISCONTROL Soluciones Integradas de Seguridad y Control. Prohibida la reproducción total o parcial de éste software.");
        todos.setSelectedTextColor(new java.awt.Color(51, 51, 51));
        todos.setSelectionColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(todos);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 145, 430, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     abrirurl();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        abrirurl();
    }//GEN-LAST:event_jButton3ActionPerformed
void abrirurl(){
     try {
            // TODO add your handling code here:
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"http://www.siscontrol.com.ec");
        } catch (IOException ex) {
            Logger.getLogger(acerca.class.getName()).log(Level.SEVERE, null, ex);
        }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lavel2;
    private javax.swing.JTextPane todos;
    // End of variables declaration//GEN-END:variables

}
