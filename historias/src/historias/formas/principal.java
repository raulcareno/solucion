/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * principal.java
 *
 * Created on 10/03/2010, 05:24:14 PM
 */

package historias.formas;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.swing.UIManager;

/**
 *
 * @author geovanny
 */
public class principal extends javax.swing.JFrame {

    /** Creates new form principal */
    public principal() {
          try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        setSize(871, 470);
        setLocationRelativeTo(null);
        JPanelRound j = new JPanelRound();
        j.setSize(50,100);
        j.setLocation(50, 50);
        j.setAlignmentX(20f);
        t1.add(j);
    }

    public void seleccion(String tipo){
        if(tipo.equals("b1")){
            tabs.setSelectedIndex(0);
        }else if(tipo.equals("b2")){
            tabs.setSelectedIndex(1);
        }else if(tipo.equals("b3")){
            tabs.setSelectedIndex(2);
        }else if(tipo.equals("b4")){
            tabs.setSelectedIndex(3);
        }else if(tipo.equals("b5")){
            tabs.setSelectedIndex(4);
        }else if(tipo.equals("b6")){
            tabs.setSelectedIndex(5);
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        menuAdministracion = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        b4 = new javax.swing.JToggleButton();
        b1 = new javax.swing.JToggleButton();
        b2 = new javax.swing.JToggleButton();
        b3 = new javax.swing.JToggleButton();
        b5 = new javax.swing.JToggleButton();
        b6 = new javax.swing.JToggleButton();
        b8 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        b7 = new javax.swing.JToggleButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        t0 = new javax.swing.JPanel();
        plogin = new historias.formas.JPanelTransparente();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        bingresar = new javax.swing.JButton();
        bsalir = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        t1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        t2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        t3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        t4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();

        jMenuItem1.setText("jMenuItem1");
        menuAdministracion.add(jMenuItem1);

        jMenuItem2.setText("jMenuItem2");
        menuAdministracion.add(jMenuItem2);

        jMenuItem3.setText("jMenuItem3");
        menuAdministracion.add(jMenuItem3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(254, 252, 250));
        getContentPane().setLayout(null);

        jSplitPane1.setDividerLocation(150);
        jSplitPane1.setDividerSize(0);

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));
        jPanel1.setLayout(null);

        b4.setBackground(new java.awt.Color(175, 239, 163));
        buttonGroup1.add(b4);
        b4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/boton.png"))); // NOI18N
        b4.setText("Turnos");
        b4.setBorderPainted(false);
        b4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        b4.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonroll.png"))); // NOI18N
        b4.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        b4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        jPanel1.add(b4);
        b4.setBounds(0, 199, 160, 50);

        b1.setBackground(new java.awt.Color(175, 239, 163));
        buttonGroup1.add(b1);
        b1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/boton.png"))); // NOI18N
        b1.setText("Pacientes");
        b1.setBorderPainted(false);
        b1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        b1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        b1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonroll.png"))); // NOI18N
        b1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });
        jPanel1.add(b1);
        b1.setBounds(0, 49, 160, 50);

        b2.setBackground(new java.awt.Color(175, 239, 163));
        buttonGroup1.add(b2);
        b2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/boton.png"))); // NOI18N
        b2.setText("Turnos");
        b2.setBorderPainted(false);
        b2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        b2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonroll.png"))); // NOI18N
        b2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        b2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });
        jPanel1.add(b2);
        b2.setBounds(0, 99, 160, 50);

        b3.setBackground(new java.awt.Color(175, 239, 163));
        buttonGroup1.add(b3);
        b3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/boton.png"))); // NOI18N
        b3.setText("Historia");
        b3.setBorderPainted(false);
        b3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        b3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonroll.png"))); // NOI18N
        b3.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        b3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });
        jPanel1.add(b3);
        b3.setBounds(0, 149, 160, 50);

        b5.setBackground(new java.awt.Color(175, 239, 163));
        buttonGroup1.add(b5);
        b5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/boton.png"))); // NOI18N
        b5.setText("Turnos");
        b5.setBorderPainted(false);
        b5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        b5.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonroll.png"))); // NOI18N
        b5.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        b5.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        jPanel1.add(b5);
        b5.setBounds(0, 249, 160, 50);

        b6.setBackground(new java.awt.Color(175, 239, 163));
        buttonGroup1.add(b6);
        b6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/boton.png"))); // NOI18N
        b6.setText("Turnos");
        b6.setBorderPainted(false);
        b6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        b6.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonroll.png"))); // NOI18N
        b6.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        b6.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        jPanel1.add(b6);
        b6.setBounds(0, 299, 160, 50);

        b8.setBackground(new java.awt.Color(175, 239, 163));
        buttonGroup1.add(b8);
        b8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/boton.png"))); // NOI18N
        b8.setText("Administración");
        b8.setBorderPainted(false);
        b8.setComponentPopupMenu(menuAdministracion);
        b8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        b8.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonroll.png"))); // NOI18N
        b8.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        b8.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        b8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b8ActionPerformed(evt);
            }
        });
        jPanel1.add(b8);
        b8.setBounds(0, 399, 160, 50);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botontop.png"))); // NOI18N
        jLabel1.setText("MENU");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 420, 150, 40);

        b7.setBackground(new java.awt.Color(175, 239, 163));
        buttonGroup1.add(b7);
        b7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/boton.png"))); // NOI18N
        b7.setText("Turnos");
        b7.setBorderPainted(false);
        b7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        b7.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonroll.png"))); // NOI18N
        b7.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        b7.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botonseleccionado.png"))); // NOI18N
        jPanel1.add(b7);
        b7.setBounds(0, 349, 160, 50);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/botontop.png"))); // NOI18N
        jLabel8.setText("MENU");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel8);
        jLabel8.setBounds(0, 10, 150, 40);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setLayout(null);

        tabs.setBackground(new java.awt.Color(253, 254, 250));
        tabs.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        t0.setLayout(null);

        plogin.setColorContorno(javax.swing.UIManager.getDefaults().getColor("Button.highlight"));
        plogin.setColorPrimario(java.awt.Color.gray);
        plogin.setColorSecundario(java.awt.Color.white);
        plogin.setTran(1.0F);
        plogin.setLayout(null);

        jLabel11.setForeground(java.awt.Color.white);
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Usuario:");
        plogin.add(jLabel11);
        jLabel11.setBounds(40, 80, 80, 20);

        jLabel12.setForeground(java.awt.Color.white);
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Contraseña:");
        plogin.add(jLabel12);
        jLabel12.setBounds(30, 120, 90, 14);
        plogin.add(jFormattedTextField3);
        jFormattedTextField3.setBounds(130, 80, 150, 20);
        plogin.add(jFormattedTextField4);
        jFormattedTextField4.setBounds(130, 110, 150, 20);

        bingresar.setText("Ingresar");
        bingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bingresarActionPerformed(evt);
            }
        });
        plogin.add(bingresar);
        bingresar.setBounds(130, 150, 80, 23);

        bsalir.setText("Salir");
        bsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsalirActionPerformed(evt);
            }
        });
        plogin.add(bsalir);
        bsalir.setBounds(210, 150, 70, 23);

        jLabel10.setBackground(java.awt.Color.white);
        jLabel10.setForeground(java.awt.Color.white);
        jLabel10.setText("         Ingrese su clave y contraseña");
        plogin.add(jLabel10);
        jLabel10.setBounds(-10, 0, 420, 40);

        t0.add(plogin);
        plogin.setBounds(100, 70, 390, 220);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/fondo verde_claro.png"))); // NOI18N
        t0.add(jLabel9);
        jLabel9.setBounds(50, 0, 727, 447);

        tabs.addTab(".", t0);

        t1.setBackground(new java.awt.Color(182, 228, 145));
        t1.setLayout(null);

        jButton1.setText("jButton1");
        t1.add(jButton1);
        jButton1.setBounds(310, 390, 73, 23);

        jLabel3.setText("Apellidos:");
        t1.add(jLabel3);
        jLabel3.setBounds(60, 90, 80, 14);

        jLabel4.setText("Nombres:");
        t1.add(jLabel4);
        jLabel4.setBounds(60, 60, 80, 14);
        t1.add(jFormattedTextField1);
        jFormattedTextField1.setBounds(140, 60, 230, 20);
        t1.add(jFormattedTextField2);
        jFormattedTextField2.setBounds(140, 90, 230, 20);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/fondo verde_claro.png"))); // NOI18N
        t1.add(jLabel2);
        jLabel2.setBounds(0, 0, 727, 447);

        tabs.addTab(".", t1);

        t2.setLayout(null);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/fondo verde_claro.png"))); // NOI18N
        t2.add(jLabel5);
        jLabel5.setBounds(0, 0, 727, 447);

        tabs.addTab(".", t2);

        t3.setLayout(null);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/fondo verde_claro.png"))); // NOI18N
        t3.add(jLabel6);
        jLabel6.setBounds(0, 0, 727, 447);

        tabs.addTab(".", t3);

        t4.setLayout(null);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/fondo verde_claro.png"))); // NOI18N
        t4.add(jLabel7);
        jLabel7.setBounds(0, 0, 727, 447);

        tabs.addTab(".", t4);

        jPanel2.add(tabs);
        tabs.setBounds(-30, 5, 760, 510);
        tabs.getAccessibleContext().setAccessibleName("Historias");

        jSplitPane1.setRightComponent(jPanel2);

        getContentPane().add(jSplitPane1);
        jSplitPane1.setBounds(0, -10, 873, 540);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
        // TODO add your handling code here:
        seleccion("b1");
    }//GEN-LAST:event_b1ActionPerformed

    private void b2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b2ActionPerformed
        // TODO add your handling code here:
        seleccion("b2");
    }//GEN-LAST:event_b2ActionPerformed

    private void b3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3ActionPerformed
        // TODO add your handling code here:
        seleccion("b3");
    }//GEN-LAST:event_b3ActionPerformed

    private void b8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b8ActionPerformed
        // TODO add your handling code here:
        menuAdministracion.show(this, b8.getX(),new BigDecimal(b8.getLocation().getY()).intValue());
    }//GEN-LAST:event_b8ActionPerformed

    private void bsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_bsalirActionPerformed

    private void bingresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bingresarActionPerformed
        // TODO add your handling code here:
        plogin.setVisible(false);
    }//GEN-LAST:event_bingresarActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton b1;
    private javax.swing.JToggleButton b2;
    private javax.swing.JToggleButton b3;
    private javax.swing.JToggleButton b4;
    private javax.swing.JToggleButton b5;
    private javax.swing.JToggleButton b6;
    private javax.swing.JToggleButton b7;
    private javax.swing.JToggleButton b8;
    private javax.swing.JButton bingresar;
    private javax.swing.JButton bsalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPopupMenu menuAdministracion;
    private historias.formas.JPanelTransparente plogin;
    private javax.swing.JPanel t0;
    private javax.swing.JPanel t1;
    private javax.swing.JPanel t2;
    private javax.swing.JPanel t3;
    private javax.swing.JPanel t4;
    private javax.swing.JTabbedPane tabs;
    // End of variables declaration//GEN-END:variables

}