/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.Applet;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Socket;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.CommPortIdentifier;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import jcinform.Administrador.Administrador;
import jcinform.Administrador.UsuarioActivo;
import jcinform.Administrador.claves;
import jcinform.Administrador.logger;
import jcinform.persistencia.*;

/**
 *
 * @author inform
 */
public class VentanillaUsuario extends java.applet.Applet implements Runnable {

    Administrador adm;
    Empleados usuario;
    Turnos turnoSeleccionado;
    List<VentanillasEmpleados> ventanillasActuales;
    String ventanillasActualesString = "";
    List<VentanillasEmpleados> ventanillasActualesOpcionales;
    String ventanillasActualesStringOpcionales = "";
    int actualOpcional;
    Accesos ac = new Accesos();
    String separador = File.separatorChar + "";
    public static ArrayList puertoListo;
    public static Empresa empresaObj;
    private Socket socket;
    public Boolean sinconexion = false;
    public int totalAtencionesxVentanilla = 1;
    claves cl = new claves();
    MenuItem acerca;
    TrayIcon trayIcon = null;
    String ubicacionDirectorio = UsuarioActivo.getUbicacionDirectorio();
    public int conteo = 0;
    public boolean abandonado = false;
    logger lger = new logger();
    public Thread t;
    public boolean active;
    public boolean expired;

//    VentanillaUsuario(Administrador ad, Empleados em, Empresa emp) {
//          try {
////            initComponents();
//            panelCambiar.setVisible(false);
//            adm = ad;
//            empresaObj = emp;
//            usuario = em;
//            this.setName(empresaObj.getVentanilla());
//            
//            totalAtencionesxVentanilla = empresaObj.getNoturnos();
//     
//                this.setSize(478, 419);
//                calificaciones.setVisible(true);
//     
//                calificaciones.setLocation(10, 10);
////                empezarThreadCiclico();
// 
//
////            this.setLocationRelativeTo(null);
////            panelBotones.setLayout(new GridLayout(4, 1));
//            //PruebaPanelPersonalizado();
//
//            ventanillasActuales = adm.query("Select o from VentanillasEmpleados as o "
//                    + "where o.codigoemp.codigoemp = '" + usuario.getCodigoemp() + "' and o.estado = true "
//                    + "and o.opcional = false order by o.codigovenemp");
//            totalVentanillasAsignadas = ventanillasActuales.size();
//            for (Iterator<VentanillasEmpleados> it = ventanillasActuales.iterator(); it.hasNext();) {
//                VentanillasEmpleados ventanillasEmpleados = it.next();
//                ventanillasActualesString += ventanillasEmpleados.getCodigoven().getCodigoven() + ",";
//
//            }
//            if (ventanillasActualesString.length() > 0) {
//                ventanillasActualesString = ventanillasActualesString.substring(0, ventanillasActualesString.length() - 1);
//            }
//            //INICIALIZO VENTANILLAS OPCIONALES
//            ventanillasActualesOpcionales = adm.query("Select o from VentanillasEmpleados as o "
//                    + "where o.codigoemp.codigoemp = '" + usuario.getCodigoemp() + "' and o.estado = true "
//                    + "and o.opcional = true order by o.codigovenemp");
//            totalVentanillasAsignadasOpcionales = ventanillasActualesOpcionales.size();
//            for (Iterator<VentanillasEmpleados> it = ventanillasActualesOpcionales.iterator(); it.hasNext();) {
//                VentanillasEmpleados ventanillasEmpleados = it.next();
//                ventanillasActualesStringOpcionales += ventanillasEmpleados.getCodigoven().getCodigoven() + ",";
//
//            }
//            if (ventanillasActualesStringOpcionales.length() > 0) {
//                ventanillasActualesStringOpcionales = ventanillasActualesStringOpcionales.substring(0, ventanillasActualesStringOpcionales.length() - 1);
//            }
//
//            usuarioLogeado.setText("" + usuario.getApellidos() + " " + usuario.getNombres());
//            PruebaPanelPersonalizado();
//            repaint();
//            iniciarPuertos();
//        } catch (Exception ex) {
//            Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
//        }
//      
//        Image im = new ImageIcon(getClass().getResource("/jcinform/imagenes/icono.png")).getImage();
//        try {
//            Image image = im;
//            PopupMenu men = new PopupMenu("JCINFORMAMENU");
//            acerca = new MenuItem("Acerca de..");
//            acerca.addActionListener(new ActionListener() {
//
//                public void actionPerformed(ActionEvent e) {
////                    acerca ac = new acerca();
////                    ac.setLocation(0, 0);
////                    ac.show();
//                }
//            });
//            men.add(acerca);
//            acerca = new MenuItem("Salir ");
//            acerca.addActionListener(new ActionListener() {
//
//                public void actionPerformed(ActionEvent e) {
//                    System.exit(0);
//                }
//            });
//            men.add(acerca);
//            trayIcon = new TrayIcon(image, "JCINFORM \n Soluciones Informáticas \n Sistema de Turnos \n www.jcinform.com ", men);
//            if (SystemTray.isSupported()) {
//                SystemTray tray = SystemTray.getSystemTray();
//                trayIcon.setImageAutoSize(true);
//                trayIcon.addActionListener(new ActionListener() {
//
//                    public void actionPerformed(ActionEvent e) {
//                       
//                    }
//                });
//                trayIcon.displayMessage("Bienvenidos al Sistema", " Sistema de Turnos \n www.jcinform.com ", TrayIcon.MessageType.INFO);
//                try {
//                    tray.add(trayIcon);
//                } catch (AWTException e) {
//                    System.err.println("No se puede añadir el icono al tray :...");
//                }
//            }
////            this.setIconImage(im);
//        } catch (Exception e) {
//            System.out.println("ERROR EN ICONOS" + e);
//        }
//        Thread cargar = new Thread() {
//
//            public void run() {
//                try {
//                    while (true) {
//                        Date fecha = adm.Date();
//                        String fec = (fecha.getYear() + 1900) + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
//                        String complementoAdicional = " in (" + ventanillasActualesString + ") ";
//                        if(ventanillasActualesOpcionales.size()>0){
//                             complementoAdicional = " in (" + ventanillasActualesString+","+ventanillasActualesStringOpcionales + ") ";
//                        }
//                        List turnos = adm.query("SELECT count(o.codigotur) FROM Turnos  as o "
//                                + "where o.fechasolicitado between '" + fec + " 00:00:01' and '" + fec + " 23:59:59' "
//                                + "and o.ventanilla.codigoven " + complementoAdicional  + "  "
//                                + "and o.estado = false and (o.ocupado = false or o.ocupado is null ) order by o.numero ");
//                        if (turnos.size() > 0) {
//                            Long valor = (Long) turnos.get(0);
//                            if (valor > 0) {
//                                Toolkit.getDefaultToolkit().beep();
//                                trayIcon.displayMessage("JC INFORM - Sistema de Turnos ",
//                                        " ..: TIENE TURNOS POR ATENDER :.."
//                                        + " \n Llame al siguiente Turno, "
//                                        + " Clic AQUÍ para llamar al siguiente turno", TrayIcon.MessageType.INFO);
//                            } else {
//                                mensajes.setText("...");
//                            }
//                        } else {
//                        }
//                        turnos = null;
//                        System.gc();
//                        Thread.sleep(60000);
//                    }
//                } catch (Exception ex) {
//                    Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//        };
//        cargar.start();
////        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
////            this.addWindowListener(new java.awt.event.WindowAdapter(){
////            @Override
////           public void windowClosing(WindowEvent e){
////                
////                //GUARDO 
////                if (turnoSeleccionado != null) {
////                    try {
////                        turnoSeleccionado.setFechaatendido(adm.Date());
////                        if(!turnoSeleccionado.getEstado().equals(new Integer(2))){
////                            turnoSeleccionado.setEstado(1); //atendido
////                        }
////                        adm.actualizar(turnoSeleccionado);
////                    } catch (Exception ex) {
////                        Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
////                    }
////                }
////                System.exit(0);
//////            if (validarSiCalifico()) {
//////                mensajes.setText("...");
//////                System.exit(0);
//////            } else {
//////                mensajes.setText("Solicite la calificación de su Atención...!");
//////                JOptionPane.showMessageDialog(getContentPane(),"Solicite la calificación de su Atención...!");
//////                return;
//////            }
////               
////           }
////        }
////        );
//    }
//    
    void iniciarPuertos() {
        try {
           String temp_string = "F:\\PROYECTOS\\turnos\\" + "lib" + separador + "javax.comm.properties";
//            String temp_string = "com.sun.comm.Win32Driver";
            Method loadDriver_Method = CommPortIdentifier.class.getDeclaredMethod("loadDriver", new Class[]{String.class});
            loadDriver_Method.setAccessible(true);
            loadDriver_Method.invoke("loadDriver", new Object[]{temp_string});
            CommPortIdentifier portId;
            Enumeration portList = CommPortIdentifier.getPortIdentifiers();
            LeerTarjetaUsuario reader;
//            String puertoYaAbiertos = "";
//            Boolean leds = true;
//            Boolean barras1 = true;
//            Boolean barras2 = true;
            puertoListo = new ArrayList();
            Boolean interfaz = true;
            while (portList.hasMoreElements()) {
                try {
                    portId = (CommPortIdentifier) portList.nextElement();
                    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                        if (portId.getName().equals(empresaObj.getPuerto())) {
                            try {
                                if (interfaz) {
                                    reader = new LeerTarjetaUsuario(portId, this);
                                    puertoListo.add(reader);
                                    System.out.println("PUERTO INTERFAZ PC BARRERA0 - ABIERTO: " + empresaObj.getPuerto());
                                    interfaz = false;
                                }
                            } catch (Exception e) {
                                System.out.println("NO SE ABRIÓ: INTERFAZ PC BARRERA0 - ABIERTO: " + empresaObj.getPuerto());
                            }
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(LeerTarjetaUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            portList = null;
            System.out.println("# PUERTOS PRINCIPALES INICIADOS: " + puertoListo.size());
            //        lger.logger("# PUERTOS PRINCIPALES INICIADOS: " + puertoListo.size(), "OK");
        } catch (Exception ex) {
            Logger.getLogger(LeerTarjetaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void PruebaPanelPersonalizado() {
        try {
            //calificaciones.repaint();an
            ArrayList colores = new ArrayList();
            colores.add(Color.green);
            colores.add(Color.yellow);
            colores.add(Color.orange);
            colores.add(Color.red);
            colores.add(Color.PINK);
            Date fecAc = adm.Date();
            String fechaI = (fecAc.getYear() + 1900) + "-" + (fecAc.getMonth() + 1) + "-" + fecAc.getDate() + " 00:00:01";
            String fechaF = (fecAc.getYear() + 1900) + "-" + (fecAc.getMonth() + 1) + "-" + fecAc.getDate() + " 23:59:59";
            List<Calificacion> todos = adm.query("Select o from Calificacion as o "
                    + "where o.empleados.codigoemp  = '" + usuario.getCodigoemp() + "' "
                    + " and o.fecha between '" + fechaI + "' and '" + fechaF + "' ");
            int total = todos.size();
            int i = 0;
            int x = 10;
            List<Tipos> tip = adm.query("Select o from Tipos as o order by o.codigo ");
            for (Iterator<Tipos> it = tip.iterator(); it.hasNext();) {
                Tipos tipos = it.next();
                List<Calificacion> califica = adm.query("Select o from Calificacion as o "
                        + "where o.empleados.codigoemp = '" + usuario.getCodigoemp() + "' "
                        + " and o.tipos.codigo = '" + tipos.getCodigo() + "' "
                        + " and o.fecha  between '" + fechaI + "' and '" + fechaF + "'  ");
                int tmp = califica.size();
                if (total == 0) {
                    total = 1;
                }
                Double porcentaje = ((tmp * 100) / new Double(total));
                BigDecimal b = new BigDecimal(porcentaje);
                b = b.setScale(2, RoundingMode.HALF_UP);
                Dibuja.rectangulo(calificaciones.getGraphics(), x, calificaciones.getY() + 15, 8, porcentaje.intValue() * 2, (Color) colores.get(i), "" + tipos.getDescripcion(), " % " + b);
                i++;
                x += 80;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    /**
     * Initializes the applet VentanillaUsuario
     */
    public void nuevo(String codigo) {
        try {


            String parametros = codigo;
            System.out.println("EL CODIGO: " + codigo);
            if (parametros != null) {
                try {
                    usuario = (Empleados) adm.buscarClave(new Integer(parametros), Empleados.class);
                } catch (Exception ex) {
                    Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    usuario = (Empleados) adm.buscarClave(new Integer("1"), Empleados.class);
                } catch (Exception ex) {
                    Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error: " + e);
        }
    }

    public void nuevo() {
        try {


            String parametros = getParameter("usuario");

            if (parametros != null) {
                try {
                    usuario = (Empleados) adm.buscarClave(new Integer(parametros), Empleados.class);
                } catch (Exception ex) {
                    Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    usuario = (Empleados) adm.buscarClave(new Integer("1"), Empleados.class);
                } catch (Exception ex) {
                    Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error: " + e);
        }
    }
   public String user="root";
   public String clave="j/eDF6Vyqmgzcb6udqpFMA==";
    public String puerto="3306";
    public String ip="192.168.10.101";
    public String empleado="15";
    Thread cargar = new Thread() {

        @Override
        public void run() {
            boolean sinconexion = true;
            int i = 0;
            System.out.println("llego al cargar");
            while (sinconexion) {
                try {
                    //String s = getParameter("empleado");
                    if(user!=null){
                   
                    UsuarioActivo usr = new UsuarioActivo();
                    UsuarioActivo.setNombre(user);
                    UsuarioActivo.setContrasenia(clave);
                    UsuarioActivo.setPuerto(puerto);
                    UsuarioActivo.setIp(ip);
                        System.out.println(""+user+"c:"+clave+"p:"+puerto+"ip"+ip);
                    try {
                        adm = new Administrador(usr);
                        System.out.println("intentando: "+i);
                        Thread.sleep(5000);
                        i++;
                        List<Empresa> empresas = adm.query("Select o from Empresa as o ");
                        sinconexion = false;
                        empresaObj = empresas.get(0);
                        empresaObj.setPuerto("COM5");
                        usuario = (Empleados) adm.buscarClave(new Integer(empleado), Empleados.class);
                        iniciarPuertos();
                        
                        repaint();
                        
                    } catch (Exception ab) {
                        System.out.println("FALTA CONECTARSE: " + ab);
                    }
                         
                    }else{
                        Thread.sleep(1000);
                        System.out.println("NO SE HA CARGADO AUN EL USER"+user);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(iniciarComm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
              System.out.println("SALIO cargar");
              cargar.stop();
        }
    };

    public void init() {
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {

                public void run() {
                    
                    initComponents();
                 
                System.setSecurityManager(null);
                cargar.start();
                
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        calificaciones = new javax.swing.JPanel();
        panelCambiar = new javax.swing.JPanel();
        guardarCambioClave = new javax.swing.JButton();
        usuarioA = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        claveActual = new javax.swing.JPasswordField();
        nuevaClave = new javax.swing.JPasswordField();
        repiteClave = new javax.swing.JPasswordField();
        jButton7 = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        mensajes = new javax.swing.JLabel();
        usuarioLogeado = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Tiempo atención anterior: ");
        add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jDesktopPane1.setBackground(new java.awt.Color(190, 213, 255));

        panelCambiar.setBackground(new java.awt.Color(241, 237, 237));
        panelCambiar.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        panelCambiar.setOpaque(false);
        panelCambiar.setLayout(null);

        guardarCambioClave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jcinform/imagenes/filesave.gif"))); // NOI18N
        guardarCambioClave.setText("Cambiar");
        guardarCambioClave.setMargin(new java.awt.Insets(1, 1, 1, 1));
        guardarCambioClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarCambioClaveActionPerformed(evt);
            }
        });
        panelCambiar.add(guardarCambioClave);
        guardarCambioClave.setBounds(30, 100, 90, 33);

        usuarioA.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        usuarioA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usuarioA.setText("...");
        panelCambiar.add(usuarioA);
        usuarioA.setBounds(10, 10, 250, 13);

        jLabel40.setText("Nueva Clave:");
        panelCambiar.add(jLabel40);
        jLabel40.setBounds(30, 60, 65, 14);

        jLabel41.setText("Repite Clave:");
        panelCambiar.add(jLabel41);
        jLabel41.setBounds(30, 80, 65, 14);

        claveActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                claveActualActionPerformed(evt);
            }
        });
        panelCambiar.add(claveActual);
        claveActual.setBounds(100, 40, 110, 20);
        panelCambiar.add(nuevaClave);
        nuevaClave.setBounds(100, 60, 110, 20);
        panelCambiar.add(repiteClave);
        repiteClave.setBounds(100, 80, 110, 20);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jcinform/imagenes/salir.png"))); // NOI18N
        jButton7.setText("Cerrar");
        jButton7.setMargin(new java.awt.Insets(1, 1, 1, 1));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        panelCambiar.add(jButton7);
        jButton7.setBounds(140, 100, 90, 35);

        jLabel42.setText("Clave Actual:");
        panelCambiar.add(jLabel42);
        jLabel42.setBounds(30, 40, 64, 14);

        javax.swing.GroupLayout calificacionesLayout = new javax.swing.GroupLayout(calificaciones);
        calificaciones.setLayout(calificacionesLayout);
        calificacionesLayout.setHorizontalGroup(
            calificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calificacionesLayout.createSequentialGroup()
                .addComponent(panelCambiar, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 150, Short.MAX_VALUE))
        );
        calificacionesLayout.setVerticalGroup(
            calificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, calificacionesLayout.createSequentialGroup()
                .addContainerGap(159, Short.MAX_VALUE)
                .addComponent(panelCambiar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        calificaciones.setBounds(10, 10, 440, 310);
        jDesktopPane1.add(calificaciones, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mensajes.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        mensajes.setForeground(new java.awt.Color(255, 0, 0));
        mensajes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mensajes.setText("...");
        mensajes.setBounds(100, 320, 430, 40);
        jDesktopPane1.add(mensajes, javax.swing.JLayeredPane.DEFAULT_LAYER);

        usuarioLogeado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        usuarioLogeado.setForeground(new java.awt.Color(51, 51, 51));
        usuarioLogeado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jcinform/imagenes/empleados.png"))); // NOI18N
        usuarioLogeado.setText("Cambiar Clave");
        usuarioLogeado.setBorderPainted(false);
        usuarioLogeado.setContentAreaFilled(false);
        usuarioLogeado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        usuarioLogeado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarioLogeadoActionPerformed(evt);
            }
        });
        usuarioLogeado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usuarioLogeadoKeyPressed(evt);
            }
        });
        usuarioLogeado.setBounds(20, 340, 200, 20);
        jDesktopPane1.add(usuarioLogeado, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 102, 204));
        jButton1.setText("!");
        jButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.setBounds(460, 340, 30, 23);
        jDesktopPane1.add(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        add(jDesktopPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void guardarCambioClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarCambioClaveActionPerformed
        // TODO add your handling code here:

        String clave = cl.desencriptar(usuario.getClave());
        String claveComprar = claveActual.getText().trim();
        if (clave.equals(claveComprar)) {
            if (nuevaClave.getText().equals(repiteClave.getText())) {
                try {
                    usuario.setClave(cl.encriptar(nuevaClave.getText().trim()));
                    adm.actualizar(usuario);
                    panelCambiar.setVisible(false);
                    claveActual.setText("");
                    repiteClave.setText("");
                    nuevaClave.setText("");

                } catch (Exception ex) {
                    Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);

                }
            } else {
                JOptionPane.showMessageDialog(this, "No coinciden las claves nueva y repeticion...!");


            }
        } else {
            JOptionPane.showMessageDialog(this, "La clave actual no coincide con su clave personal \n talvez Ud. no sea el propietario de ésta cuenta.");
            claveActual.setText("");
        }
    }//GEN-LAST:event_guardarCambioClaveActionPerformed

    private void claveActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_claveActualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_claveActualActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        panelCambiar.setVisible(false);
        claveActual.setText("");
        repiteClave.setText("");
        nuevaClave.setText("");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void usuarioLogeadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarioLogeadoActionPerformed
        // TODO add your handling code here:
        
        usuarioA.setText("" + usuario.getApellidos() + " " + usuario.getNombres());
        if (panelCambiar.isVisible()) {
            panelCambiar.setVisible(false);

        } else {
            panelCambiar.setVisible(true);
            claveActual.requestFocusInWindow();
            panelCambiar.repaint();
        }
    }//GEN-LAST:event_usuarioLogeadoActionPerformed

    private void usuarioLogeadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usuarioLogeadoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_usuarioLogeadoKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        acerca2 ac = new acerca2(null, true);
        ac.setSize(462, 230);
        ac.setLocationByPlatform(true);
        ac.show(true);
    }//GEN-LAST:event_jButton1ActionPerformed
    public int actual = 0;
    public int totalVentanillasAsignadas = 0;
    public int totalVentanillasAsignadasOpcionales = 0;
    public int yaAtendidosenVentanilla = 0;
    public static int vecesIntentando = 0;

    public void siguienteTurno() {

        try {
            PruebaPanelPersonalizado();
            Date fecha = adm.Date();
            if (turnoSeleccionado != null) {
                turnoSeleccionado.setFechaatendido(fecha);
                if (!turnoSeleccionado.getEstado().equals(new Integer(2))) {
                    turnoSeleccionado.setEstado(1); //atendido
                }
                adm.actualizar(turnoSeleccionado);
            }


            if (yaAtendidosenVentanilla == totalAtencionesxVentanilla) {
                actual++;
                yaAtendidosenVentanilla = 0;
                if (actual == (totalVentanillasAsignadas)) {
                    actual = 0;
                    yaAtendidosenVentanilla = 0;
                }
            }

            VentanillasEmpleados venA = null;
            try {
                venA = ventanillasActuales.get(actual);
            } catch (Exception ex) {
                actual = 0;
                venA = ventanillasActuales.get(actual);

            }
            String fec = (fecha.getYear() + 1900) + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
//            List turnos = null;
            List turnos = adm.query("SELECT o FROM Turnos  as o "
                    + "where o.fechasolicitado between '" + fec + " 00:00:01' and '" + fec + " 23:59:59' "
                    + "and o.ventanilla.codigoven  = '" + venA.getCodigoven().getCodigoven() + "' "
                    + "and o.estado = false and (o.ocupado = false or o.ocupado is null ) order by o.numero ", 0, 2);
            Boolean encontro = true;
            int vecesPasa = 0;
            int elQueleTocaba = actual;
            System.out.println("# DE VENTANILLAS" + ventanillasActuales.size());
            while (encontro && turnos.size() <= 0) {
                actual++;
                vecesPasa++;
                try {
                    venA = ventanillasActuales.get(actual);
                } catch (Exception e) {
                    encontro = true;
                    break;
                }

                turnos = adm.query("SELECT o FROM Turnos  as o "
                        + "where o.fechasolicitado between '" + fec + " 00:00:01' and '" + fec + " 23:59:59' "
                        + "and o.ventanilla.codigoven  = '" + venA.getCodigoven().getCodigoven() + "' "
                        + "and o.estado = false and (o.ocupado = false or o.ocupado is null ) order by o.numero ", 0, 2);
                if (turnos.size() > 0) {
                    encontro = true;
                    break;
                }
                if (vecesPasa == ventanillasActuales.size() && turnos.size() < 0) {
                    actual = 0;
                    vecesPasa = 0;
                    encontro = true;
                    break;
                }
            }
            if (turnos.size() <= 0) {
                actual = 0;
                vecesPasa = 0;
            }

            while (encontro && turnos.size() <= 0) {
                vecesPasa++;
                try {
                    venA = ventanillasActuales.get(actual);
                } catch (Exception e) {
                    encontro = true;
                    break;
                }

                turnos = adm.query("SELECT o FROM Turnos  as o "
                        + "where o.fechasolicitado between '" + fec + " 00:00:01' and '" + fec + " 23:59:59' "
                        + "and o.ventanilla.codigoven  = '" + venA.getCodigoven().getCodigoven() + "' "
                        + "and o.estado = false and (o.ocupado = false or o.ocupado is null ) order by o.numero ", 0, 2);
                if (turnos.size() > 0) {
                    encontro = true;
                    break;
                }
                if (vecesPasa == elQueleTocaba && turnos.size() < 0) {
                    encontro = true;
                    actual = 0;
                    break;
                }
                actual++;
            }


            //CON ESTO AVANZO LAS VENTANILLAS QUE ESTOY ATENDIENDO


            if (turnos.size() > 0) {
                yaAtendidosenVentanilla++;
                String actulTurno = ((Turnos) turnos.get(0)).getNumero() + "";
                while (actulTurno.length() < 3) {
                    actulTurno = "0" + actulTurno;
                }
                //HILO
                turnoSeleccionado = ((Turnos) turnos.get(0));
                turnoSeleccionado.setCodigoemp(usuario);
                turnoSeleccionado.setFechallamada(fecha);
                turnoSeleccionado.setLlamados(turnoSeleccionado.getLlamados());
                turnoSeleccionado.setOcupado(true);
                adm.actualizar(turnoSeleccionado);


            } else {
            }

        } catch (Exception ex) {
            actual = 0;
            lger.logger(VentanillaUsuario.class.getName(), ex + "");
            Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
//        if(turnoActual.getText()=="----"){
//            botonSiguiente();
//            //voy a validar que no exista turnos para las ventanillas que estoy atendiendo
//            
//            //tengo que validar de todas las ventanillas 
//             sdd
//            vecesIntentando+=1;

    }

    public void siguienteTurnoOpc() {

        try {
            PruebaPanelPersonalizado();
            Date fecha = adm.Date();
            if (turnoSeleccionado != null) {
                turnoSeleccionado.setFechaatendido(fecha);
                turnoSeleccionado.setEstado(1); //atendido
                adm.actualizar(turnoSeleccionado);
            }
            //VentanillasEmpleados venA = ventanillasActualesOpcionales.get(actual);
            String fec = (fecha.getYear() + 1900) + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
            List ventanillaconMasTurnos = adm.query("SELECT o.ventanilla.codigoven,count(o.ventanilla.codigoven) FROM Turnos  as o "
                    + "where o.fechasolicitado between '" + fec + " 00:00:01' and '" + fec + " 23:59:59' "
                    + "and o.ventanilla.codigoven  in (" + ventanillasActualesStringOpcionales + ") "
                    + "and o.estado = false and (o.ocupado = false or o.ocupado is null ) group by o.ventanilla.codigoven "
                    + " order by 2 desc ");

            int ventanillaMayor = 0;
            if (ventanillaconMasTurnos.size() > 0) {
                Object[] ventanillaIt = (Object[]) ventanillaconMasTurnos.get(0);
                ventanillaMayor = (Integer) ventanillaIt[0];
            }
            if (ventanillaMayor == 0) {
                return;
            }

            List turnos = adm.query("SELECT o FROM Turnos  as o "
                    + "where o.fechasolicitado between '" + fec + " 00:00:01' and '" + fec + " 23:59:59' "
                    + "and o.ventanilla.codigoven  = '" + ventanillaMayor + "' "
                    + "and o.estado = false and (o.ocupado = false or o.ocupado is null ) order by o.numero ", 0, 2);
            if (turnos.size() > 0) {
                Ventanillas ven = (Ventanillas) adm.buscarClave(new Integer(ventanillaMayor), Ventanillas.class);
                yaAtendidosenVentanilla++;
                String actulTurno = ((Turnos) turnos.get(0)).getNumero() + "";
                while (actulTurno.length() < 3) {
                    actulTurno = "0" + actulTurno;
                }

                //HILO
                turnoSeleccionado = ((Turnos) turnos.get(0));
                turnoSeleccionado.setCodigoemp(usuario);
                turnoSeleccionado.setFechallamada(fecha);
                turnoSeleccionado.setLlamados(turnoSeleccionado.getLlamados());
                turnoSeleccionado.setOcupado(true);
                adm.actualizar(turnoSeleccionado);

                //volverLlamar.setEnabled(true);
                if (UsuarioActivo.getSeCalifica()) {
                }
            } else {
                if (UsuarioActivo.getSeCalifica()) {
                }
            }

        } catch (Exception ex) {
            lger.logger(VentanillaUsuario.class.getName(), ex + "");
            Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel calificaciones;
    private javax.swing.JPasswordField claveActual;
    private javax.swing.JButton guardarCambioClave;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton7;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    public javax.swing.JLabel mensajes;
    private javax.swing.JPasswordField nuevaClave;
    private javax.swing.JPanel panelCambiar;
    private javax.swing.JPasswordField repiteClave;
    private javax.swing.JLabel usuarioA;
    private javax.swing.JButton usuarioLogeado;
    // End of variables declaration//GEN-END:variables


    public VentanillaUsuario() {

        active = false;
        expired = false;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
