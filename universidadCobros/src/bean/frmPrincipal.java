/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Component;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Institucion;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Periodos;
import jcinform.procesos.Administrador;
import jcinform.procesos.claves;
import util.general;

/**
 *
 * @author inform
 */
public class frmPrincipal extends javax.swing.JFrame {

    Administrador adm;
    Empleados usuarioActual;
    Periodos periodoActual;
    Institucion inst;

    /**
     * Creates new form frmPrincipal
     */
    public frmPrincipal() {
//        try {
        initComponents();
        this.setExtendedState(this.MAXIMIZED_BOTH);
        frmCambiarClave.setVisible(false);
     
//            frmLogin.setSelected(true);
//            frmLogin.requestFocusInWindow();
        usuario.requestFocusInWindow();
         
         general gen = new general(-1, " - CARGANDO -");
         cmbPeriodo.addItem(gen);
        Thread cargar = new Thread() {
            public void run() {
                   adm = new Administrador();
                List<Periodos> periodosLista = adm.query("Select o from Periodos as o where o.activo = true");
                cmbPeriodo.removeAllItems();
         general gen = new general(-1, " - SELECCIONE -");
         cmbPeriodo.addItem(gen);      
         java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("MMM/yyyy");

                for (Iterator<Periodos> it = periodosLista.iterator(); it.hasNext();) {
                    Periodos periodos = it.next();
                     gen = new general(periodos.getIdPeriodos(), sdf.format(periodos.getFechaInicio()) + " - " + sdf.format(periodos.getFechaFin()) );
                    cmbPeriodo.addItem(gen);

                }

                periodosLista = null;
            }
        };
        cargar.start();

//            Robot r = new Robot();
//            r.
//            r.mouseMove(frmLogin.getX()+10,frmLogin.getY()+10);
//            r.mousePress(InputEvent.BUTTON2_MASK);
//            r.mouseRelease(InputEvent.BUTTON2_MASK);
//        } catch (AWTException ex) {
//            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
//        }  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenedor = new javax.swing.JDesktopPane();
        frmLogin = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        mensaje = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        usuario = new javax.swing.JFormattedTextField();
        contrasena = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        ingresarSistema = new javax.swing.JButton();
        intentos = new javax.swing.JLabel();
        cmbPeriodo = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        frmCambiarClave = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnCambiarClave = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jScrollBar1 = new javax.swing.JScrollBar();
        claveActual = new javax.swing.JPasswordField();
        nuevaClave = new javax.swing.JPasswordField();
        confirmeClave = new javax.swing.JPasswordField();
        jMenuBar1 = new javax.swing.JMenuBar();
        Administracion = new javax.swing.JMenu();
        rubros = new javax.swing.JMenuItem();
        rubrosMatricula = new javax.swing.JMenuItem();
        categoriasSociales = new javax.swing.JMenuItem();
        Cobros = new javax.swing.JMenu();
        Facturar = new javax.swing.JMenuItem();
        usuario1 = new javax.swing.JMenu();
        usuarioActualLabel = new javax.swing.JMenu();
        cambiarClave = new javax.swing.JMenuItem();
        cerrarSesion = new javax.swing.JMenuItem();
        periodoActualLabel = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Módulo de Cobros");

        frmLogin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        frmLogin.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Bienvenido:");
        frmLogin.add(jLabel2);
        jLabel2.setBounds(10, 7, 220, 20);

        mensaje.setFont(new java.awt.Font("Book Antiqua", 1, 14)); // NOI18N
        mensaje.setForeground(new java.awt.Color(255, 255, 255));
        mensaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        frmLogin.add(mensaje);
        mensaje.setBounds(14, 40, 320, 20);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/login.png"))); // NOI18N
        frmLogin.add(jLabel1);
        jLabel1.setBounds(0, 0, 351, 60);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Periodo: ");
        frmLogin.add(jLabel4);
        jLabel4.setBounds(10, 120, 80, 25);

        usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarioActionPerformed(evt);
            }
        });
        usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usuarioKeyPressed(evt);
            }
        });
        frmLogin.add(usuario);
        usuario.setBounds(90, 67, 230, 25);

        contrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                contrasenaKeyPressed(evt);
            }
        });
        frmLogin.add(contrasena);
        contrasena.setBounds(90, 93, 230, 25);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Contraseña: ");
        frmLogin.add(jLabel3);
        jLabel3.setBounds(0, 90, 90, 25);

        ingresarSistema.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        ingresarSistema.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/encrypted.gif"))); // NOI18N
        ingresarSistema.setText("Ingresar");
        ingresarSistema.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ingresarSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ingresarSistemaActionPerformed(evt);
            }
        });
        frmLogin.add(ingresarSistema);
        ingresarSistema.setBounds(220, 120, 100, 25);

        intentos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        intentos.setText("...");
        frmLogin.add(intentos);
        intentos.setBounds(30, 150, 290, 14);

        cmbPeriodo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbPeriodoItemStateChanged(evt);
            }
        });
        cmbPeriodo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPeriodoKeyPressed(evt);
            }
        });
        frmLogin.add(cmbPeriodo);
        cmbPeriodo.setBounds(90, 120, 130, 24);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Usuario: ");
        frmLogin.add(jLabel8);
        jLabel8.setBounds(10, 70, 80, 20);

        frmLogin.setBounds(20, 20, 350, 170);
        contenedor.add(frmLogin, javax.swing.JLayeredPane.DEFAULT_LAYER);

        frmCambiarClave.setLayout(null);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Clave Actual:");
        frmCambiarClave.add(jLabel5);
        jLabel5.setBounds(14, 20, 130, 24);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Nueva Clave:");
        frmCambiarClave.add(jLabel6);
        jLabel6.setBounds(10, 50, 130, 24);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Confirme Nueva Clave: ");
        frmCambiarClave.add(jLabel7);
        jLabel7.setBounds(20, 80, 130, 24);

        btnCambiarClave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png"))); // NOI18N
        btnCambiarClave.setText("Cambiar");
        btnCambiarClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarClaveActionPerformed(evt);
            }
        });
        frmCambiarClave.add(btnCambiarClave);
        btnCambiarClave.setBounds(61, 120, 100, 25);

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.gif"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        frmCambiarClave.add(btnCerrar);
        btnCerrar.setBounds(180, 120, 100, 25);
        frmCambiarClave.add(jScrollBar1);
        jScrollBar1.setBounds(320, 0, 17, 48);
        frmCambiarClave.add(claveActual);
        claveActual.setBounds(150, 20, 130, 24);
        frmCambiarClave.add(nuevaClave);
        nuevaClave.setBounds(150, 50, 130, 24);

        confirmeClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmeClaveActionPerformed(evt);
            }
        });
        frmCambiarClave.add(confirmeClave);
        confirmeClave.setBounds(150, 80, 130, 24);

        frmCambiarClave.setBounds(150, 2, 320, 160);
        contenedor.add(frmCambiarClave, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(contenedor, java.awt.BorderLayout.CENTER);

        Administracion.setText("Administración");
        Administracion.setEnabled(false);

        rubros.setText("Rubros");
        rubros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rubrosActionPerformed(evt);
            }
        });
        Administracion.add(rubros);

        rubrosMatricula.setText("Rubros x Matricula");
        rubrosMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rubrosMatriculaActionPerformed(evt);
            }
        });
        Administracion.add(rubrosMatricula);

        categoriasSociales.setText("Categorías Sociales");
        categoriasSociales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoriasSocialesActionPerformed(evt);
            }
        });
        Administracion.add(categoriasSociales);

        jMenuBar1.add(Administracion);

        Cobros.setText("Cobros");
        Cobros.setEnabled(false);

        Facturar.setText("Facturar");
        Facturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FacturarActionPerformed(evt);
            }
        });
        Cobros.add(Facturar);

        jMenuBar1.add(Cobros);

        usuario1.setText(" | Usuario: ");
        usuario1.setEnabled(false);
        jMenuBar1.add(usuario1);

        usuarioActualLabel.setText("...");
        usuarioActualLabel.setEnabled(false);

        cambiarClave.setText("Cambiar Clave");
        cambiarClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarClaveActionPerformed(evt);
            }
        });
        usuarioActualLabel.add(cambiarClave);

        cerrarSesion.setText("Cerrar Sesión");
        cerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarSesionActionPerformed(evt);
            }
        });
        usuarioActualLabel.add(cerrarSesion);

        jMenuBar1.add(usuarioActualLabel);

        periodoActualLabel.setText("_");
        jMenuBar1.add(periodoActualLabel);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rubrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rubrosActionPerformed
        // TODO add your handling code here:
        try {
//            List<Accesos> accesosL = adm.query("Select o from Accesos as o "
//                    + "where o.pantalla = 'Factura' " + "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "' and o.ingresar = true  ");
//            if (accesosL.size() > 0) {
//                permisos = accesosL.get(0);                
//                accesosL = null;
//            } else {
//                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
            try {
                Component[] componentes = contenedor.getComponents();
                for (Component component : componentes) {
                    System.out.println("" + component.getName());
                    if ((component.getName() + "").equals("formaRubros")) {
                        System.out.println("LO ENCONTRE");
                        ((frmRubros) component).setEmpleadoActual(usuarioActual);
                        ((frmRubros) component).setPeriodoActual(periodoActual);
                        ((frmRubros) component).setVisible(true);
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR EN COMPONENTE" + e);
            }
            frmRubros usu = new frmRubros(adm);
            usu.setSize(546, 507);
            usu.setEmpleadoActual(usuarioActual);
            usu.setPeriodoActual(periodoActual);
            usu.setLocation(0, 0);
            usu.setName("formaRubros");
            contenedor.add(usu);

            usu.show();
//            usu.noTicket.requestFocusInWindow();


        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);

        }

//        frmRubros forma;
//        forma = new frmRubros(jDesktopPane1);
//        forma.setLocation(this.getWidth() / 3 - forma.getWidth() / 2 + 50, this.getHeight() / 2 - forma.getHeight() / 2 - 20);
//        this.jDesktopPane1.add(forma);
//        forma.setVisible(true);

    }//GEN-LAST:event_rubrosActionPerformed

    private void rubrosMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rubrosMatriculaActionPerformed
        // TODO add your handling code here:
        try {
//            List<Accesos> accesosL = adm.query("Select o from Accesos as o "
//                    + "where o.pantalla = 'Factura' " + "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "' and o.ingresar = true  ");
//            if (accesosL.size() > 0) {
//                permisos = accesosL.get(0);                
//                accesosL = null;
//            } else {
//                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
            try {
                Component[] componentes = contenedor.getComponents();
                for (Component component : componentes) {
                    System.out.println("" + component.getName());
                    if ((component.getName() + "").equals("formaRubrosMatriculas")) {
                        System.out.println("LO ENCONTRE");
                        ((frmRubrosMatriculas) component).setEmpleadoActual(usuarioActual);
                        ((frmRubrosMatriculas) component).setPeriodoActual(periodoActual);
                        ((frmRubrosMatriculas) component).setVisible(true);
                        return;
                    }
                }
                componentes = null;
            } catch (Exception e) {
                System.out.println("ERROR EN COMPONENTE" + e);
            }
            frmRubrosMatriculas usu = new frmRubrosMatriculas( adm);
            usu.setSize(546, 507);
 usu.setEmpleadoActual(usuarioActual);
            usu.setPeriodoActual(periodoActual);
            usu.setLocation(0, 0);
            usu.setName("formaRubrosMatriculas");
            contenedor.add(usu);

            usu.show();
//            usu.noTicket.requestFocusInWindow();


        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);

        }

    }//GEN-LAST:event_rubrosMatriculaActionPerformed

    private void usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usuarioActionPerformed

    private void ingresarSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ingresarSistemaActionPerformed
        // TODO add your handling code here:
        if (cmbPeriodo.getSelectedIndex() > 0) {
            verificarUsuario();
        } else {
            mensaje.setText("Seleccione un periodo...!");
        }

    }//GEN-LAST:event_ingresarSistemaActionPerformed

    private void usuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usuarioKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            contrasena.requestFocusInWindow();
        }
    }//GEN-LAST:event_usuarioKeyPressed

    private void contrasenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contrasenaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
           cmbPeriodo.requestFocusInWindow();
        }
    }//GEN-LAST:event_contrasenaKeyPressed

    private void cerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarSesionActionPerformed
        // TODO add your handling code here:
        usuarioActual = null;
        periodoActual = null;
        usuarioActualLabel.setText("...");
        usuarioActualLabel.setEnabled(false);
        Administracion.setEnabled(false);
        Cobros.setEnabled(false);
        usuario1.setEnabled(false);
        frmLogin.setVisible(true);
        usuario.setText("");
        contrasena.setText("");
        usuario.requestFocusInWindow();
        cmbPeriodo.setSelectedIndex(0);

    }//GEN-LAST:event_cerrarSesionActionPerformed

    private void cambiarClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarClaveActionPerformed
        // TODO add your handling code here:
        frmCambiarClave.setVisible(true);
        nuevaClave.setText("");
        claveActual.setText("");
        confirmeClave.setText("");
        claveActual.requestFocusInWindow();

    }//GEN-LAST:event_cambiarClaveActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        frmCambiarClave.setVisible(false);
        nuevaClave.setText("");
        claveActual.setText("");
        confirmeClave.setText("");
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnCambiarClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarClaveActionPerformed
        // TODO add your handling code here:
        if (claveActual.getText().trim().isEmpty()) {
            claveActual.requestFocusInWindow();
            return;
        }
        if (!nuevaClave.getText().equals(confirmeClave.getText())) {
            JOptionPane.showMessageDialog(this, "Las Clave Actual ingresada no es igual a la que se ha iniciado sesión ...!", "JC INFORM", JOptionPane.ERROR_MESSAGE);
            claveActual.setText("");
            confirmeClave.setText("");
            claveActual.requestFocusInWindow();
            return;
        }
        if (!nuevaClave.getText().equals(confirmeClave.getText())) {
            JOptionPane.showMessageDialog(this, "Las claves no coinciden...!", "JC INFORM", JOptionPane.ERROR_MESSAGE);
            nuevaClave.setText("");
            confirmeClave.setText("");
            nuevaClave.requestFocusInWindow();
            return;
        } else {
            usuarioActual.setClave(claves.encriptar(nuevaClave.getText()));
            adm.actualizar(usuarioActual);
            frmCambiarClave.setVisible(false);
            nuevaClave.setText("");
            claveActual.setText("");
            confirmeClave.setText("");
        }

    }//GEN-LAST:event_btnCambiarClaveActionPerformed

    private void confirmeClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmeClaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmeClaveActionPerformed

    private void cmbPeriodoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbPeriodoItemStateChanged
        // TODO add your handling code here:
        if (cmbPeriodo.getSelectedIndex() > 0) {
            periodoActual = (Periodos) adm.buscarClave(((general) cmbPeriodo.getSelectedItem()).getCodigo(), Periodos.class);
            periodoActualLabel.setText(((general) cmbPeriodo.getSelectedItem()).getDescripcion());
//            ingresarSistema.requestFocusInWindow();
//            usuario.requestFocusInWindow();
//            frmLogin.setVisible(false); 
//            cmbPeriodo.setVisible(false);
        }
    }//GEN-LAST:event_cmbPeriodoItemStateChanged

    private void FacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FacturarActionPerformed
        // TODO add your handling code here:
          try {
                Component[] componentes = contenedor.getComponents();
                for (Component component : componentes) {
                    System.out.println("" + component.getName());
                    if ((component.getName() + "").equals("formaFacturas")) {
                        System.out.println("LO ENCONTRE");
                        ((frmFacturas) component).setEmpleadoActual(usuarioActual);
                        ((frmFacturas) component).setPeriodoActual(periodoActual);
                        ((frmFacturas) component).setVisible(true);
                        ((frmFacturas) component).inst = inst;
                        ((frmFacturas) component).EstudianteSeleccionado = new general("0", "");
                        ((frmFacturas) component).actualMatricula = new Matriculas();
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR EN COMPONENTE" + e);
            }
            frmFacturas usu = new frmFacturas(adm);
            usu.setSize(642, 557);
            usu.setEmpleadoActual(usuarioActual);
            usu.setPeriodoActual(periodoActual);
            usu.setLocation(0, 0);
            usu.inst = inst;
            usu.actualMatricula = new Matriculas();
            usu.setName("formaFacturas");
            contenedor.add(usu);

            usu.show();
        
    }//GEN-LAST:event_FacturarActionPerformed

    private void cmbPeriodoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPeriodoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
         if (cmbPeriodo.getSelectedIndex() > 0) {
                verificarUsuario();
            } else {
                mensaje.setText("Seleccione un periodo...!");
            }
        }
    }//GEN-LAST:event_cmbPeriodoKeyPressed

    private void categoriasSocialesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoriasSocialesActionPerformed
        // TODO add your handling code here:
               // TODO add your handling code here:
        try {
//            List<Accesos> accesosL = adm.query("Select o from Accesos as o "
//                    + "where o.pantalla = 'Factura' " + "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "' and o.ingresar = true  ");
//            if (accesosL.size() > 0) {
//                permisos = accesosL.get(0);                
//                accesosL = null;
//            } else {
//                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
            try {
                Component[] componentes = contenedor.getComponents();
                for (Component component : componentes) {
                    System.out.println("" + component.getName());
                    if ((component.getName() + "").equals("formaCategoriasSociales")) {
                        System.out.println("LO ENCONTRE");
                        ((frmCategorias) component).setEmpleadoActual(usuarioActual);
                        ((frmCategorias) component).setPeriodoActual(periodoActual);
                        ((frmCategorias) component).setVisible(true);
                        return;
                    }
                }
                componentes = null;
            } catch (Exception e) {
                System.out.println("ERROR EN COMPONENTE" + e);
            }
            frmCategorias usu = new frmCategorias( adm);
            usu.setSize(546, 507);
 usu.setEmpleadoActual(usuarioActual);
            usu.setPeriodoActual(periodoActual);
            usu.setLocation(0, 0);
            usu.setName("formaCategoriasSociales");
            contenedor.add(usu);

            usu.show();
//            usu.noTicket.requestFocusInWindow();


        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);

        }

    }//GEN-LAST:event_categoriasSocialesActionPerformed
    private int intento = 0;

    public JLabel getIntentos() {
        return intentos;
    }

    public void setIntentos(JLabel intentos) {
        this.intentos = intentos;
    }

    private void verificarUsuario() {
        Empleados emp = adm.ingresoSistema(usuario.getText().trim(), contrasena.getText().trim());
        if (emp != null) {
            usuarioActualLabel.setText("" + emp.getApellidoPaterno() + " " + emp.getNombre());
            frmLogin.setVisible(false);
            mensaje.setText("");
            usuarioActualLabel.setEnabled(true);
            Administracion.setEnabled(true);
            Cobros.setEnabled(true);
            usuario1.setEnabled(true);
            usuarioActual = emp;
            List<Institucion> instituciones = adm.query("Select o from Institucion as o ");
            for (Iterator<Institucion> it = instituciones.iterator(); it.hasNext();) {
                 inst = it.next();
            }
            

        } else {

            usuario.setText("");
            contrasena.setText("");
            mensaje.setText("Usuario o Clave incorrecta...!");
            usuario.requestFocusInWindow();
            usuarioActualLabel.setEnabled(false);
            Administracion.setEnabled(false);
            Cobros.setEnabled(false);
            usuario1.setEnabled(false);
            intento++;
            if (intento == 4) {
                try {


                    Thread cargar = new Thread() {
                        public void run() {
                            try {
                                usuario.setEditable(false);
                                contrasena.setEditable(false);
                                ingresarSistema.setEnabled(false);
                                mensaje.setText("Lo sentimos usuario o clave incorrectas...!");
                                intentos.setText("Lo sentimos usuario o clave incorrectas...!");
                                Thread.sleep(3000);
                                System.exit(0);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    };
                    cargar.start();

                } catch (Exception ex) {
                    Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            intentos.setText("Fallos: " + intento + " de 4");
            if (intento == 3) {
                intentos.setText("Ultima Oportunidad: " + intento + " de 4");
            }
        }
    }

    public int getIntento() {
        return intento;
    }

    public void setIntento(int intento) {
        this.intento = intento;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPrincipal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Administracion;
    private javax.swing.JMenu Cobros;
    private javax.swing.JMenuItem Facturar;
    private javax.swing.JButton btnCambiarClave;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JMenuItem cambiarClave;
    private javax.swing.JMenuItem categoriasSociales;
    private javax.swing.JMenuItem cerrarSesion;
    private javax.swing.JPasswordField claveActual;
    private javax.swing.JComboBox cmbPeriodo;
    private javax.swing.JPasswordField confirmeClave;
    private javax.swing.JDesktopPane contenedor;
    private javax.swing.JPasswordField contrasena;
    private javax.swing.JPanel frmCambiarClave;
    private javax.swing.JPanel frmLogin;
    private javax.swing.JButton ingresarSistema;
    private javax.swing.JLabel intentos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JLabel mensaje;
    private javax.swing.JPasswordField nuevaClave;
    private javax.swing.JMenu periodoActualLabel;
    private javax.swing.JMenuItem rubros;
    private javax.swing.JMenuItem rubrosMatricula;
    private javax.swing.JFormattedTextField usuario;
    private javax.swing.JMenu usuario1;
    private javax.swing.JMenu usuarioActualLabel;
    // End of variables declaration//GEN-END:variables
}
