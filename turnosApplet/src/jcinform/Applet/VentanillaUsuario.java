/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.Applet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.CommDriver;
import javax.comm.CommPortIdentifier;
import javax.swing.*;
import jcinform.Administrador.Administrador;
import jcinform.Administrador.UsuarioActivo;
import jcinform.Administrador.claves;
import jcinform.Administrador.logger;
import jcinform.persistencia.*;
import jcinform.transmision.cliente.ControlCliente;

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

    public void iVentanillaUsuario() {
        try {
////            initComponents();
            panelCambiar.setVisible(false);
//         
            this.setName(empresaObj.getVentanilla());
//            
            totalAtencionesxVentanilla = empresaObj.getNoturnos();
//     
            this.setSize(678, 448);
//                calificaciones.setVisible(true);
//     
//                calificaciones.setLocation(10, 10);
//                empezarThreadCiclico();
// 
//
////            this.setLocationRelativeTo(null);
////            panelBotones.setLayout(new GridLayout(4, 1));
//            //PruebaPanelPersonalizado();
//

            ventanillasActuales = adm.query("Select o from VentanillasEmpleados as o "
                    + "where o.codigoemp.codigoemp = '" + usuario.getCodigoemp() + "' and o.estado = true "
                    + "and o.opcional = false order by o.codigovenemp");
            totalVentanillasAsignadas = ventanillasActuales.size();
            for (Iterator<VentanillasEmpleados> it = ventanillasActuales.iterator(); it.hasNext();) {
                VentanillasEmpleados ventanillasEmpleados = it.next();
                ventanillasActualesString += ventanillasEmpleados.getCodigoven().getCodigoven() + ",";

            }
            if (ventanillasActualesString.length() > 0) {
                ventanillasActualesString = ventanillasActualesString.substring(0, ventanillasActualesString.length() - 1);
            }
            //INICIALIZO VENTANILLAS OPCIONALES
            ventanillasActualesOpcionales = adm.query("Select o from VentanillasEmpleados as o "
                    + "where o.codigoemp.codigoemp = '" + usuario.getCodigoemp() + "' and o.estado = true "
                    + "and o.opcional = true order by o.codigovenemp");
            totalVentanillasAsignadasOpcionales = ventanillasActualesOpcionales.size();
            for (Iterator<VentanillasEmpleados> it = ventanillasActualesOpcionales.iterator(); it.hasNext();) {
                VentanillasEmpleados ventanillasEmpleados = it.next();
                ventanillasActualesStringOpcionales += ventanillasEmpleados.getCodigoven().getCodigoven() + ",";

            }
            if (ventanillasActualesStringOpcionales.length() > 0) {
                ventanillasActualesStringOpcionales = ventanillasActualesStringOpcionales.substring(0, ventanillasActualesStringOpcionales.length() - 1);
            }

            usuarioLogeado.setText("" + usuario.getApellidos() + " " + usuario.getNombres());
            PruebaPanelPersonalizado();
//            repaint();
//            iniciarPuertos();
        } catch (Exception ex) {
            Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
//      
        Image im = new ImageIcon(getClass().getResource("/jcinform/imagenes/icono.png")).getImage();
        try {
            Image image = im;
            PopupMenu men = new PopupMenu("JCINFORMAMENU");
            acerca = new MenuItem("Acerca de..");
            acerca.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
//                    acerca ac = new acerca();
//                    ac.setLocation(0, 0);
//                    ac.show();
                }
            });
            men.add(acerca);
            acerca = new MenuItem("Salir ");
            acerca.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            men.add(acerca);
            trayIcon = new TrayIcon(image, "JCINFORM \n Soluciones Informáticas \n Sistema de Turnos \n www.jcinform.com ", men);
            if (SystemTray.isSupported()) {
                SystemTray tray = SystemTray.getSystemTray();
                trayIcon.setImageAutoSize(true);
                trayIcon.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                    }
                });
                trayIcon.displayMessage("Bienvenidos al Sistema", " Sistema de Turnos \n www.jcinform.com ", TrayIcon.MessageType.INFO);
                try {
                    tray.add(trayIcon);
                } catch (AWTException e) {
                    System.err.println("No se puede añadir el icono al tray :...");
                }
            }
//            this.setIconImage(im);
        } catch (Exception e) {
            System.out.println("ERROR EN ICONOS" + e);
        }
        Thread cargar = new Thread() {

            public void run() {
                try {
                    while (true) {
                        Date fecha = adm.Date();
                        String fec = (fecha.getYear() + 1900) + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
                        String complementoAdicional = " in (" + ventanillasActualesString + ") ";
                        if (ventanillasActualesOpcionales.size() > 0) {
                            complementoAdicional = " in (" + ventanillasActualesString + "," + ventanillasActualesStringOpcionales + ") ";
                        }
                        List turnos = adm.query("SELECT count(o.codigotur) FROM Turnos  as o "
                                + "where o.fechasolicitado between '" + fec + " 00:00:01' and '" + fec + " 23:59:59' "
                                + "and o.ventanilla.codigoven " + complementoAdicional + "  "
                                + "and o.estado = false and (o.ocupado = false or o.ocupado is null ) order by o.numero ");
                        if (turnos.size() > 0) {
                            Long valor = (Long) turnos.get(0);
                            if (valor > 0) {
                                Toolkit.getDefaultToolkit().beep();
                                trayIcon.displayMessage("JC INFORM - Sistema de Turnos ",
                                        " ..: TIENE TURNOS POR ATENDER :.."
                                        + " \n Llame al siguiente Turno, "
                                        + " Clic AQUÍ para llamar al siguiente turno", TrayIcon.MessageType.INFO);
                            } else {
                                mensajes.setText("...");
                            }
                        } else {
                        }
                        turnos = null;
                        System.gc();
                        Thread.sleep(60000);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        cargar.start();
        iniciarSocket();
//        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//            this.addWindowListener(new java.awt.event.WindowAdapter(){
//            @Override
//           public void windowClosing(WindowEvent e){
//                
//                //GUARDO 
//                if (turnoSeleccionado != null) {
//                    try {
//                        turnoSeleccionado.setFechaatendido(adm.Date());
//                        if(!turnoSeleccionado.getEstado().equals(new Integer(2))){
//                            turnoSeleccionado.setEstado(1); //atendido
//                        }
//                        adm.actualizar(turnoSeleccionado);
//                    } catch (Exception ex) {
//                        Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                System.exit(0);
////            if (validarSiCalifico()) {
////                mensajes.setText("...");
////                System.exit(0);
////            } else {
////                mensajes.setText("Solicite la calificación de su Atención...!");
////                JOptionPane.showMessageDialog(getContentPane(),"Solicite la calificación de su Atención...!");
////                return;
////            }
//               
//           }
//        }
//        );
    }
//    

    void iniciarPuertos() {
        try {
//            String temp_string = "F:\\PROYECTOS\\turnos\\" + "lib" + separador + "javax.comm.properties";
//            String temp_string = "com.sun.comm.Win32Driver";
//            Method loadDriver_Method = CommPortIdentifier.class.getDeclaredMethod("loadDriver", new Class[]{String.class});
//            loadDriver_Method.setAccessible(true);
//            loadDriver_Method.invoke("loadDriver", new Object[]{temp_string});
            String driverName = "com.sun.comm.Win32Driver";
            try {
                CommDriver commdriver =
                        (CommDriver) Class.forName(driverName).newInstance();
                commdriver.initialize();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
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
//    public String user = "root";
//    public String clave = "j/eDF6Vyqmgzcb6udqpFMA==";
//    public String puerto = "3306";
//    public String ip = "192.168.10.101";
//    public String empleado = "15";
//    public String secalifica = "false";
//    public String ventanilla = "1";
    public String user = null;
    public String clave = null;
    public String puerto = null;
    public String ip = null;
    public String empleado = null;
    public String secalifica = null;
    public String ventanilla = null;
    Thread cargar = new Thread() {

        @Override
        public void run() {
            boolean sinconexion = true;
            int i = 0;
            System.out.println("llego al cargar");
            while (sinconexion) {
                try {
                    //String s = getParameter("empleado");
                    if (user != null) {
                        UsuarioActivo usr = new UsuarioActivo();
                        UsuarioActivo.setNombre(user);
                        UsuarioActivo.setContrasenia(clave);
                        UsuarioActivo.setPuerto(puerto);
                        UsuarioActivo.setIp(ip);
                        UsuarioActivo.setSeCalifica(false);
                        System.out.println("u: " + user + "c: " + clave + "p: " + puerto + "ip: " + ip + "emp: " + empleado);
                        try {
                            adm = new Administrador(usr);
                            System.out.println("intentando: " + i);
                            Thread.sleep(5000);
                            i++;
                            List<Empresa> empresas = adm.query("Select o from Empresa as o ");

                            sinconexion = false;
                            empresaObj = empresas.get(0);
                            String mac = devolverMac();
                            List<Mac> ma = adm.query("Select o from Mac as o where o.mac = '" + mac + "' ");
                            if (ma.size() > 0) {
                                empresaObj.setVentanilla(ma.get(0).getVentanilla());
                                empresaObj.setPuerto(ma.get(0).getCom());
                                UsuarioActivo.setSeCalifica(ma.get(0).getConcalificador());
                            } else {
                                String ventanilla = JOptionPane.showInputDialog(null, "Ingrese el No. de Ventanilla");
                                JComboBox cmb = llenarPuertos();
                                JOptionPane.showMessageDialog(null, cmb, "Seleccione el Puerto de su calificador", JOptionPane.QUESTION_MESSAGE);
//                                JOptionPane.showMessageDialog(null, new JLabel("User selected: " + cmb.getSelectedItem()));

                                Mac macNueva = new Mac(devolverMac());
                                macNueva.setConcalificador(true);
                                macNueva.setVentanilla(ventanilla);
                                macNueva.setCom(cmb.getSelectedItem().toString());
                                adm.guardar(macNueva);
                                empresaObj.setVentanilla("" + ventanilla);
                                empresaObj.setPuerto("COM1" + cmb.getSelectedItem().toString());
                                UsuarioActivo.setSeCalifica(false);
                            }

                            usuario = (Empleados) adm.buscarClave(new Integer(empleado), Empleados.class);
                            iniciarPuertos();
                            iVentanillaUsuario();
                            //repaint();
                            cargando.setVisible(false);

                        } catch (Exception ab) {
                            System.out.println("FALTA CONECTARSE: " + ab);
                        }

                    } else {
                        Thread.sleep(1000);
                        System.out.println("NO SE HA CARGADO AUN EL USER" + user);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(iniciarComm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            System.out.println("SALIO cargar");
            cargar.stop();
        }
    };

    private JComboBox llenarPuertos() {
        try {
//            String temp_string = "com.sun.comm.Win32Driver";
//            Method loadDriver_Method = CommPortIdentifier.class.getDeclaredMethod("loadDriver", new Class[]{String.class});
//            loadDriver_Method.setAccessible(true);
//            loadDriver_Method.invoke("loadDriver", new Object[]{temp_string});
            String driverName = "com.sun.comm.Win32Driver";
            try {
                CommDriver commdriver =
                        (CommDriver) Class.forName(driverName).newInstance();
                commdriver.initialize();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            CommPortIdentifier portId;
            Enumeration portList = CommPortIdentifier.getPortIdentifiers();
            LeerTarjetaUsuario reader;

//            String[] list = {"Banana", "Apple", "Pear", "Grape"};
            JComboBox jcb = new JComboBox();
            jcb.setEditable(true);


            ArrayList arr = new ArrayList();
            while (portList.hasMoreElements()) {
                portId = (CommPortIdentifier) portList.nextElement();
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    if (!arr.contains(portId.getName())) {
                        arr.add(portId.getName());
                    }
                }

            }
            for (Iterator it = arr.iterator(); it.hasNext();) {
                Object object = it.next();
                jcb.addItem(object);
            }
            return jcb;
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public void init() {
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {

                public void run() {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //WINDOWS
                        initComponents();

                        System.setSecurityManager(null);
                        cargar.start();

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
                    }

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
        cargando = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
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
        calificaciones = new javax.swing.JPanel();
        mensajes = new javax.swing.JLabel();
        usuarioLogeado = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        turnosPanel = new javax.swing.JPanel();
        turnoActual = new javax.swing.JLabel();
        botonSiguiente = new javax.swing.JButton();
        conectar = new javax.swing.JButton();
        enviado = new javax.swing.JButton();
        volverLlamar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Tiempo atención anterior: ");
        add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jDesktopPane1.setBackground(new java.awt.Color(190, 213, 255));

        cargando.setBackground(new java.awt.Color(230, 230, 230));

        jLabel2.setBackground(new java.awt.Color(230, 230, 230));
        jLabel2.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Cargando, espere uno segundos ...!");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jcinform/imagenes/procesando.gif"))); // NOI18N

        javax.swing.GroupLayout cargandoLayout = new javax.swing.GroupLayout(cargando);
        cargando.setLayout(cargandoLayout);
        cargandoLayout.setHorizontalGroup(
            cargandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cargandoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cargandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        cargandoLayout.setVerticalGroup(
            cargandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cargandoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(264, Short.MAX_VALUE))
        );

        cargando.setBounds(10, 10, 650, 400);
        jDesktopPane1.add(cargando, javax.swing.JLayeredPane.DEFAULT_LAYER);

        panelCambiar.setBackground(new java.awt.Color(241, 237, 237));
        panelCambiar.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
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
        jLabel40.setBounds(15, 60, 80, 14);

        jLabel41.setText("Repite Clave:");
        panelCambiar.add(jLabel41);
        jLabel41.setBounds(15, 80, 80, 14);

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
        jLabel42.setBounds(14, 40, 80, 14);

        panelCambiar.setBounds(10, 180, 300, 160);
        jDesktopPane1.add(panelCambiar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout calificacionesLayout = new javax.swing.GroupLayout(calificaciones);
        calificaciones.setLayout(calificacionesLayout);
        calificacionesLayout.setHorizontalGroup(
            calificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
        );
        calificacionesLayout.setVerticalGroup(
            calificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );

        calificaciones.setBounds(220, 10, 440, 310);
        jDesktopPane1.add(calificaciones, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mensajes.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        mensajes.setForeground(new java.awt.Color(255, 0, 0));
        mensajes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mensajes.setText("...");
        mensajes.setBounds(100, 320, 510, 40);
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
        jButton1.setBounds(630, 340, 30, 23);
        jDesktopPane1.add(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        turnosPanel.setLayout(null);

        turnoActual.setFont(new java.awt.Font("Tahoma", 1, 40)); // NOI18N
        turnoActual.setForeground(new java.awt.Color(255, 51, 0));
        turnoActual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        turnoActual.setText("TURNO");
        turnosPanel.add(turnoActual);
        turnoActual.setBounds(10, 200, 170, 70);

        botonSiguiente.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        botonSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jcinform/imagenes/ticket.png"))); // NOI18N
        botonSiguiente.setText("<html> <div style=\"text-align:center\" >SIGUIENTE TURNO (F1)</div> </html>");
        botonSiguiente.setActionCommand("<html> <div style=\"align:center\">SIGUIENTE TURNO </div></html>");
        botonSiguiente.setEnabled(false);
        botonSiguiente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        botonSiguiente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSiguiente.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSiguienteActionPerformed(evt);
            }
        });
        botonSiguiente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                botonSiguienteKeyPressed(evt);
            }
        });
        turnosPanel.add(botonSiguiente);
        botonSiguiente.setBounds(10, 10, 172, 100);

        conectar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jcinform/imagenes/conectar.png"))); // NOI18N
        conectar.setText("CONECTAR");
        conectar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        conectar.setOpaque(false);
        conectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conectarActionPerformed(evt);
            }
        });
        turnosPanel.add(conectar);
        conectar.setBounds(10, 270, 160, 30);

        enviado.setFont(new java.awt.Font("Tahoma", 1, 42)); // NOI18N
        enviado.setForeground(new java.awt.Color(255, 0, 0));
        enviado.setText("TURNO");
        enviado.setMargin(new java.awt.Insets(0, 0, 0, 0));
        enviado.setOpaque(false);
        turnosPanel.add(enviado);
        enviado.setBounds(160, 270, 10, 10);

        volverLlamar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        volverLlamar.setText("VOLVER A LLAMAR");
        volverLlamar.setEnabled(false);
        volverLlamar.setMargin(new java.awt.Insets(0, 0, 0, 0));
        volverLlamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverLlamarActionPerformed(evt);
            }
        });
        volverLlamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                volverLlamarKeyPressed(evt);
            }
        });
        turnosPanel.add(volverLlamar);
        volverLlamar.setBounds(10, 110, 172, 80);

        turnosPanel.setBounds(10, 10, 200, 310);
        jDesktopPane1.add(turnosPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

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

    private void botonSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSiguienteActionPerformed
        try {
            //lger.logger(VentanillaUsuario.class.getName(), "LLANDO TURNO");
            turnoActual.setText("");
            volverLlamar.setText("VOLVER A LLAMAR ");
            if (UsuarioActivo.getListaConexion() == false && empresaObj.getTouch()) {
                this.botonSiguiente.setEnabled(false);
                botonSiguiente.setText("SIN CONEXIÓN");
                volverLlamar.setText("Sin conexión");
                volverLlamar.setEnabled(false);
                enviado.setText("-");
                enviado.setEnabled(false);
                conectar.setEnabled(true);
                return;
//                cerraryAbrir();
            } else {
                UsuarioActivo.setListaConexion(true);
                conectar.setEnabled(false);
            }

            //VERIFICO SI ES QYE YA HA CALIFICADO
//            if (validarSiCalifico()) {
//                mensajes.setText("...");
//            } else {
//                mensajes.setText("Solicite la calificación de su Atención...!");
//                return;
//            }

            //VERIFICO QUE EXISTAN TURNOS PARA LAS VENTANILLAS ASIGNADAS.
            Date fecha = adm.Date();
            boolean siHayTurnos = true;
            String fec = (fecha.getYear() + 1900) + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
            List turnos = adm.query("SELECT count(o.codigotur) FROM Turnos  as o "
                    + "where o.fechasolicitado between '" + fec + " 00:00:01' and '" + fec + " 23:59:59' "
                    + "and o.ventanilla.codigoven  in (" + ventanillasActualesString + ") "
                    + "and o.estado = false and (o.ocupado = false or o.ocupado is null ) order by o.numero ");
            if (turnos.size() > 0) {
                Long valor = (Long) turnos.get(0);
                if (valor > 0) {
                    siguienteTurno();

                    mensajes.setText("...");
                } else {
                    if (UsuarioActivo.getSeCalifica()) {
                        botonSiguiente.setEnabled(true);
                        volverLlamar.setEnabled(false);
                    }
                    siHayTurnos = false;
                    System.out.println("NO HAY TURNOS NORMALES...!");
//                    System.out.println("NO EXISTEN AÚN TURNOS PARA LAS VENTANILLAS QUE ATIENDE: CODVEN: " + ventanillasActualesString);
//                    mensajes.setText("No hay turnos...!");
//                    enviado.setText("----");
//                    Toolkit.getDefaultToolkit().beep();
                }
            }
            boolean siHayTurnosOpcionales = true;
            if (siHayTurnos == false && ventanillasActualesOpcionales.size() > 0) {

                turnos = adm.query("SELECT count(o.codigotur) FROM Turnos  as o "
                        + "where o.fechasolicitado between '" + fec + " 00:00:01' and '" + fec + " 23:59:59' "
                        + "and o.ventanilla.codigoven  in (" + ventanillasActualesStringOpcionales + ") "
                        + "and o.estado = false and (o.ocupado = false or o.ocupado is null ) order by o.numero ");
                if (turnos.size() > 0) {
                    Long valor = (Long) turnos.get(0);
                    if (valor > 0) {
                        siguienteTurnoOpc();
                        mensajes.setText("...");
                    } else {
                        if (UsuarioActivo.getSeCalifica()) {
                            botonSiguiente.setEnabled(true);
                            volverLlamar.setEnabled(false);
                        }
                        siHayTurnosOpcionales = false;
                        System.out.println("NO HAY TURNOS OPCIONALES...!");

                    }
                }

            }
            if (siHayTurnos == false && ventanillasActualesOpcionales.size() <= 0) {
                System.out.println("NO EXISTEN AÚN TURNOS PARA LAS VENTANILLAS QUE ATIENDE: CODVEN: " + ventanillasActualesString);
                mensajes.setText("No hay turnos...!");
                enviado.setText("----");
                turnoActual.setText("----");
                Toolkit.getDefaultToolkit().beep();
                if (UsuarioActivo.getSeCalifica()) {
                    botonSiguiente.setEnabled(true);
                    volverLlamar.setEnabled(false);
                }
            }
            if (siHayTurnosOpcionales == false && ventanillasActualesOpcionales.size() > 0) {
                System.out.println("NO EXISTEN AÚN TURNOS PARA LAS VENTANILLAS QUE ATIENDE: CODVEN: " + ventanillasActualesString);
                mensajes.setText("No hay turnos...!");
                enviado.setText("----");
                turnoActual.setText("----");
                Toolkit.getDefaultToolkit().beep();
                if (UsuarioActivo.getSeCalifica()) {
                    botonSiguiente.setEnabled(true);
                    volverLlamar.setEnabled(false);
                }
            }

        } catch (Exception ex) {
            lger.logger(VentanillaUsuario.class.getName(), ex + "");
            Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonSiguienteActionPerformed

    private void botonSiguienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_botonSiguienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_F1) {
            botonSiguiente.doClick();
        }
    }//GEN-LAST:event_botonSiguienteKeyPressed

    public void iniciarSocket() {

        try {
            sinconexion = false;
            socket = new Socket("" + empresaObj.getIp(), 5557);
            ControlCliente control = new ControlCliente(socket, enviado, this.getName());
        } catch (UnknownHostException ex) {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("cierro el socket por no existir conexión");
            }


            System.out.println("NO EXISTE CONEXIÓN CON EL SERVIDOR" + ex);
            ex.printStackTrace();
            if (empresaObj.getTouch()) {
                sinconexion = true;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            if (empresaObj.getTouch()) {
                sinconexion = true;
            }
            System.out.println("NO EXISTE CONEXIÓN CON EL SERVIDOR: 2" + ex);
        }

    }

    private void conectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conectarActionPerformed
// TODO add your handling code here:
        Thread cargar = new Thread() {

            public void run() {
                conectar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jcinform/imagenes/procesando.gif"))); // NOI18N
                conectar.setText("");
                conectar.setEnabled(false);
                iniciarSocket();

                if (sinconexion == false && empresaObj.getTouch()) {

                    botonSiguiente.setEnabled(true);
                    botonSiguiente.setText("<html> <div style='text-align:center' >SIGUIENTE TURNO (F1)</div> </html>");
                    volverLlamar.setText("VOLVER A LLAMAR");
                    volverLlamar.setEnabled(true);
                    enviado.setText("-");
                    turnoActual.setText("-");
                    enviado.setEnabled(true);
                    conectar.setIcon(null); // NOI18N
                    conectar.setEnabled(false);
                    UsuarioActivo.setListaConexion(true);
                    conectar.setText("CONECTAR");
                    mensajes.setText("...");
                    conectar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jcinform/imagenes/conectar.png"))); // NOI18N
                } else {
                    conectar.setIcon(null); // NOI18N
                    mensajes.setText("No existe conexión con el servidor...!");
                    conectar.setText("Intentar Nuevamente");
                    conectar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jcinform/imagenes/conectar.png"))); // NOI18N
                    conectar.setEnabled(true);
                    UsuarioActivo.setListaConexion(false);
                }

            }
        };
        cargar.start();
    }//GEN-LAST:event_conectarActionPerformed

    private void volverLlamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverLlamarActionPerformed


        try {
            Date fecha = adm.Date();
            turnoSeleccionado.setLlamados(turnoSeleccionado.getLlamados() + 1);
            if (turnoSeleccionado.getLlamados() >= 4) {
                turnoSeleccionado.setFechaatendido(fecha);
                turnoSeleccionado.setEstado(2); //usuario perdido
                if (UsuarioActivo.getSeCalifica()) {
                    volverLlamar.setEnabled(false);
                    botonSiguiente.setEnabled(true);
                }
                volverLlamar.setText("VOLVER A LLAMAR ");
                turnoSeleccionado.setLlamados(0);
                abandonado = true;
            }
            volverLlamar.setText("VOLVER A LLAMAR(" + turnoSeleccionado.getLlamados() + ")");
            adm.actualizar(turnoSeleccionado);
            if (turnoSeleccionado.getLlamados() < 4) {
                enviado.doClick();
            }





        } catch (Exception ex) {
            Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

//            }
//        };
//        cargar.start();

    }//GEN-LAST:event_volverLlamarActionPerformed

    private void volverLlamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_volverLlamarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_F1) {
            botonSiguiente.doClick();
        }
    }//GEN-LAST:event_volverLlamarKeyPressed
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
                turnoActual.setText(venA.getCodigoven().getCodificacion() + "" + actulTurno);
                //HILO
                turnoSeleccionado = ((Turnos) turnos.get(0));
                turnoSeleccionado.setCodigoemp(usuario);
                turnoSeleccionado.setFechallamada(fecha);
                turnoSeleccionado.setLlamados(turnoSeleccionado.getLlamados());
                turnoSeleccionado.setOcupado(true);
                adm.actualizar(turnoSeleccionado);
                enviado.setLabel(turnoActual.getText());
                enviado.setName("" + turnoSeleccionado.getCodigotur());
                turnoActual.setText(turnoActual.getText());
                enviado.doClick();
                if (UsuarioActivo.getSeCalifica()) {
                    volverLlamar.setEnabled(true);
                    botonSiguiente.setEnabled(false);
                }

            } else {
                enviado.setLabel(turnoActual.getText());
                enviado.setName("---");
                turnoActual.setText(turnoActual.getText());
                if (UsuarioActivo.getSeCalifica()) {
                    volverLlamar.setEnabled(false);
                    botonSiguiente.setEnabled(true);
                }
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

    public String devolverMac() {
        // TODO code application logic here
//        Session a = Sessions.getCurrent();
//             String abc = a.getRemoteAddr();
        InetAddress ip;

        try {
            ip = InetAddress.getLocalHost();
//            ip = InetAddress.getAllByName(abc)[0];
            System.out.println("Current IP address : " + ip);
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());
            return sb.toString();
        } catch (UnknownHostException e) {

            e.printStackTrace();

        } catch (SocketException e) {

            e.printStackTrace();

        }
        return null;
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
                turnoActual.setText(ven.getCodificacion() + "" + actulTurno);
                //HILO
                turnoSeleccionado = ((Turnos) turnos.get(0));
                turnoSeleccionado.setCodigoemp(usuario);
                turnoSeleccionado.setFechallamada(fecha);
                turnoSeleccionado.setLlamados(turnoSeleccionado.getLlamados());
                turnoSeleccionado.setOcupado(true);
                adm.actualizar(turnoSeleccionado);
                enviado.setLabel(turnoActual.getText());
                enviado.setName("" + turnoSeleccionado.getCodigotur());
                turnoActual.setText(turnoActual.getText());
                enviado.doClick();
                //volverLlamar.setEnabled(true);
                if (UsuarioActivo.getSeCalifica()) {
                    botonSiguiente.setEnabled(false);
                    volverLlamar.setEnabled(true);
                }
            } else {
                if (UsuarioActivo.getSeCalifica()) {
                    botonSiguiente.setEnabled(true);
                    volverLlamar.setEnabled(false);
                }
            }

        } catch (Exception ex) {
            lger.logger(VentanillaUsuario.class.getName(), ex + "");
            Logger.getLogger(VentanillaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton botonSiguiente;
    private javax.swing.JPanel calificaciones;
    private javax.swing.JPanel cargando;
    private javax.swing.JPasswordField claveActual;
    private javax.swing.JButton conectar;
    private javax.swing.JButton enviado;
    private javax.swing.JButton guardarCambioClave;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton7;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    public javax.swing.JLabel mensajes;
    private javax.swing.JPasswordField nuevaClave;
    private javax.swing.JPanel panelCambiar;
    private javax.swing.JPasswordField repiteClave;
    private javax.swing.JLabel turnoActual;
    private javax.swing.JPanel turnosPanel;
    private javax.swing.JLabel usuarioA;
    private javax.swing.JButton usuarioLogeado;
    public javax.swing.JButton volverLlamar;
    // End of variables declaration//GEN-END:variables

//    public VentanillaUsuario() {
//
//        active = false;
//        expired = false;
//    }
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
