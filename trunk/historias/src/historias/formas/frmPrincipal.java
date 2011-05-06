/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmPrincipal.java
 *
 * Created on 06/05/2011, 11:31:47 AM
 */
package historias.formas;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import persistencia.Accesos;
import persistencia.Empleados;
import procesos.Administrador;
import procesos.GeneraXMLPersonal;
import procesos.UsuarioActivo;
import procesos.claves;
import procesos.frmConfiguracion;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class frmPrincipal extends javax.swing.JFrame {

    static UsuarioActivo datosConecta;
    Administrador adm = null;
    claves claves = new claves();
    String separador = File.separatorChar + "";
    static WorkingDirectory w = new WorkingDirectory();
    String ubicacionDirectorio = w.get() + separador;
    Empleados usuario = null;

    /** Creates new form frmPrincipal */
    public frmPrincipal() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //WINDOWS
            initComponents();
            procesando.setVisible(false);
            adm = new Administrador();


            this.setExtendedState(this.MAXIMIZED_BOTH);
            
            txtUsuario.requestFocusInWindow();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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

        contenedor = new javax.swing.JDesktopPane();
        plogin = new historias.formas.JPanelTransparente();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JFormattedTextField();
        txtContrasena = new javax.swing.JFormattedTextField();
        btnIngresar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        procesando = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        contenedor.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));

        plogin.setColorContorno(javax.swing.UIManager.getDefaults().getColor("Button.highlight"));
        plogin.setColorPrimario(java.awt.Color.gray);
        plogin.setColorSecundario(java.awt.Color.white);
        plogin.setTran(1.0F);
        plogin.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel11.setForeground(java.awt.Color.white);
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Usuario:");
        plogin.add(jLabel11);
        jLabel11.setBounds(40, 80, 80, 20);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel12.setForeground(java.awt.Color.white);
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Contraseña:");
        plogin.add(jLabel12);
        jLabel12.setBounds(30, 110, 90, 15);

        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
        });
        plogin.add(txtUsuario);
        txtUsuario.setBounds(130, 80, 150, 20);

        txtContrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContrasenaKeyPressed(evt);
            }
        });
        plogin.add(txtContrasena);
        txtContrasena.setBounds(130, 110, 150, 20);

        btnIngresar.setText("Ingresar");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        plogin.add(btnIngresar);
        btnIngresar.setBounds(130, 150, 80, 23);

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        plogin.add(btnSalir);
        btnSalir.setBounds(210, 150, 70, 23);

        jLabel10.setBackground(java.awt.Color.white);
        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 0, 18));
        jLabel10.setForeground(java.awt.Color.white);
        jLabel10.setText("iniciar sesión");
        plogin.add(jLabel10);
        jLabel10.setBounds(30, 20, 130, 40);

        plogin.setBounds(310, 220, 380, 210);
        contenedor.add(plogin, javax.swing.JLayeredPane.DEFAULT_LAYER);

        procesando.setBackground(new java.awt.Color(204, 204, 255));
        procesando.setFont(new java.awt.Font("Tahoma", 0, 10));
        procesando.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/procesando.gif"))); // NOI18N
        procesando.setText("Procesando...");
        procesando.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        procesando.setContentAreaFilled(false);
        procesando.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        procesando.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        procesando.setBounds(10, 10, 90, 60);
        contenedor.add(procesando, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(contenedor, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);
        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jMenu1.setText("Administración");

        jMenuItem1.setText("Empleados");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Tratamientos");
        jMenu1.add(jMenuItem2);

        jMenuItem4.setText("Estados Dientes");
        jMenu1.add(jMenuItem4);

        jMenuItem7.setText("Privilegios");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Pacientes");

        jMenuItem3.setText("Control Pacientes");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Cobros");

        jMenuItem5.setText("Presupuestos");
        jMenu3.add(jMenuItem5);

        jMenuItem6.setText("Cobros");
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public Boolean comprobar() {

        GeneraXMLPersonal pXml = new GeneraXMLPersonal();
        pXml.inicio();
        pXml.leerXML();
        datosConecta = pXml.user;
        try {
            String nombre = datosConecta.getNombre();
            System.out.println("NOMB:" + nombre);
            if (nombre.equals("null") || datosConecta.getContrasenia().equals("null")) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        if (datosConecta == null) {
            return false;
        } else {
            return true;
        }

    }
    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        // TODO add your handling code here:

        Thread cargar = new Thread() {

            public void run() {
                procesando.setVisible(true);
                btnIngresar.setEnabled(false);
                txtContrasena.setEditable(false);
                txtUsuario.setEditable(false);
                verificarUsuario();
                procesando.setVisible(false);
                btnIngresar.setEnabled(true);

            }
        };
        cargar.start();
//        plogin.setVisible(false);
}//GEN-LAST:event_btnIngresarActionPerformed
    public void verificarUsuario() {
        Empleados usu = null;
        try {
            usu = adm.ingresoSistema(txtUsuario.getText(), txtContrasena.getText());

            if (usu != null) {
                usuario = usu;
                plogin.setVisible(false);



            } else {
                JOptionPane.showMessageDialog(this, "Usuario o Clave incorrectas", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                txtContrasena.setEditable(true);
                txtUsuario.setEditable(true);
                txtUsuario.setText("");
                txtContrasena.setText("");
                txtUsuario.requestFocusInWindow();
                //System.out.println("NO SE HAN CARGADO LAS FECHAS " + e);
            }


        } catch (Exception e) {
            //JOptionPane.showMessageDialog(this, "ERROR EN CONFIGURACION DEL SISTEMA"+e);

            if (ubicacionDirectorio.contains("build")) {
                ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
            }

            File fichero = new File(ubicacionDirectorio + "KDJFASD5F4AS5D2.xml");
            if (fichero.exists()) {
                fichero.delete();
                System.out.println("ELIMINADO: " + fichero.getAbsolutePath());
            }
            this.dispose();
            new frmConfiguracion().show();




        }


    }

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
}//GEN-LAST:event_btnSalirActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:

        List<Accesos> accesosL = adm.query("Select o from Accesos as o "
                + "where o.modulo = 'Pacientes' "
                + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' and o.ingresar = true ");
        if (accesosL.size() > 0) {
            permisos = accesosL.get(0);
        } else {
            JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
            return;
        }

        frmPaciente usu = new frmPaciente(this, adm);
        usu.setSize(634, 541);
        usu.setLocation(240, 100);
        contenedor.add(usu);
        usu.txtBusqueda.requestFocusInWindow();
        usu.show();
        usu.txtBusqueda.requestFocusInWindow();
           
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:

        frmPrivilegios usu = new frmPrivilegios(this, adm);
        usu.setSize(457, 449);
        usu.setLocation(240, 100);
        contenedor.add(usu);
        usu.show();
        contenedor.requestFocus();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void txtContrasenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContrasenaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == evt.VK_ENTER)
            btnIngresar.doClick();
    }//GEN-LAST:event_txtContrasenaKeyPressed

    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == evt.VK_ENTER)
            txtContrasena.requestFocusInWindow();
    }//GEN-LAST:event_txtUsuarioKeyPressed
    public static Accesos permisos;

    public Accesos getPermisos() {
        return permisos;
    }

    public void setPermisos(Accesos permisos) {
        this.permisos = permisos;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new frmPrincipal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JDesktopPane contenedor;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JToolBar jToolBar1;
    private historias.formas.JPanelTransparente plogin;
    public javax.swing.JButton procesando;
    private javax.swing.JFormattedTextField txtContrasena;
    private javax.swing.JFormattedTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
