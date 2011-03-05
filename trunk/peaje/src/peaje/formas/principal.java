package peaje.formas;

import java.awt.AWTException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import peaje.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import hibernate.*;
import hibernate.cargar.BeanUsuario;
import hibernate.cargar.GeneraXMLPersonal;
import hibernate.cargar.WorkingDirectory;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Timer;

/**
 *
 * @author geovanny
 */
public class principal extends javax.swing.JFrame {

    Administrador adm;
//    Image image = Toolkit.getDefaultToolkit().getImage("C:\\Documents and Settings\\geovanny\\Escritorio\\JavaApplication3\\src\\javaapplication3\\tray.gif");
    public Tarjetas tarjeta;
    private Clientes usuarioObj;
    public boolean grabar = false;
    public boolean modificar = false;
    public boolean nuevaTarjeta = false;
    public Usuarios usuarioActual;
    public Empresa empresaObj;
    hibernate.cargar.claves cl = new hibernate.cargar.claves();

    /** Creates new form principal */
    public principal() {



        this.addWindowListener(new WindowAdapter() {
              public void windowClosing(WindowEvent we) {
                //JOptionPane.showMessageDialog(puertoBase, "mensaje");
                  System.exit(0);
                
              }
        });
      
        Image im = new ImageIcon(getClass().getResource("/images_botones/ico.gif")).getImage();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //WINDOWS
            initComponents();
            RelojModeloUtil modelo = new RelojModeloUtil();
            RelojVisual visual = new RelojVisual(modelo);
            visual.setLocation(100, 100);
            miPanel.add(visual);
            //getContentPane().add(visual);
            this.setSize(600, 600);
            this.setExtendedState(this.MAXIMIZED_BOTH);
            procesando.setVisible(false);
            /* CARGO EL MESSENGER */
            Image image = im;
            PopupMenu men = new PopupMenu("JCINFORMAMENU");
            MenuItem acerca = new MenuItem("Acerca de..");
            acerca.addActionListener(new ActionListener()     {

                public void actionPerformed(ActionEvent e) {
                    //System.out.println("In here");
                    //JOptionPane.showMessageDialog(getContentPane(), "JCINFORM ");
                    acerca ac = new acerca();
                    ac.setModal(true);
                    ac.setLocation(250, 250);
                    ac.show();

                }
            });
            men.add(acerca);
            acerca = new MenuItem("Salir ");
            acerca.addActionListener(new ActionListener()     {

                public void actionPerformed(ActionEvent e) {
                    //System.out.println("In here");
                    //JOptionPane.showMessageDialog(getContentPane(), "JCINFORM ");
                    System.exit(0);

                }
            });
            men.add(acerca);

            final TrayIcon trayIcon = new TrayIcon(image, "JCINFORM \n Soluciones Informaticas \n 080 162 -211 / 5103 843 ", men);
            if (SystemTray.isSupported()) {
                SystemTray tray = SystemTray.getSystemTray();
                trayIcon.setImageAutoSize(true);
                trayIcon.addActionListener(new ActionListener()     {

                    public void actionPerformed(ActionEvent e) {
                        //System.out.println("In here");
                        trayIcon.displayMessage("JCINFORM - SOLUCIONES INFORMATICAS \n Sistema de Parking \n ", "Telf. 5103 843", TrayIcon.MessageType.INFO);
//                        frmLogin.show(true);
                    }
                });
                trayIcon.displayMessage("Bienvenidos al Sistema", "Telf. 5103 843", TrayIcon.MessageType.INFO);
                try {
                    tray.add(trayIcon);
                } catch (AWTException e) {
                    System.err.println("TrayIcon could not be added.");
                }
            }
            this.setIconImage(im);


            if (comprobar()) {
                logear();
            } else {
                frmRegistrar.setIconImage(new ImageIcon(getClass().getResource("/images_botones/ico.gif")).getImage());
                frmRegistrar.setModal(true);
                frmRegistrar.setSize(474, 280);
                frmRegistrar.setLocation(350, 300);
                frmRegistrar.setFocusable(true);
                frmRegistrar.setResizable(false);
                frmRegistrar.setTitle("Inicio de Sesión");
                frmRegistrar.setUndecorated(false);
                frmRegistrar.show();
 logear();
            }
            try {
                WorkingDirectory w = new WorkingDirectory();
                String ubicacionDirectorio = w.get()+"\\";
                if(ubicacionDirectorio.contains("build"))
                    ubicacionDirectorio = ubicacionDirectorio.replace("\\build", "");
                String temp_string = ubicacionDirectorio+"lib\\javax.comm.properties";
                Method loadDriver_Method = CommPortIdentifier.class.getDeclaredMethod("loadDriver", new Class[]{String.class});
                loadDriver_Method.setAccessible(true);
                loadDriver_Method.invoke("loadDriver", new Object[]{temp_string});
                CommPortIdentifier portId;
                Enumeration portList = CommPortIdentifier.getPortIdentifiers();
                SimpleRead reader;
                ArrayList read = new ArrayList();
                while (portList.hasMoreElements()) {
                    portId = (CommPortIdentifier) portList.nextElement();
                    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                        if (portId.getName().equals("COM30")) {
                            reader = new SimpleRead(portId, this);
                            read.add(reader);
//                            break;
                        } else if (portId.getName().equals("COM10")) {
                            reader = new SimpleRead(portId, this);
                            read.add(reader);
//                            break;

                        }
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleRead.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        frmClientes = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        nombres = new javax.swing.JFormattedTextField();
        direccion = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        codigo = new javax.swing.JFormattedTextField();
        cmbTipo = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        telefono = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tarjetas = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        btnNuevaTarjeta = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        formaTarjetas = new javax.swing.JDialog();
        jLabel14 = new javax.swing.JLabel();
        panelHoras = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        fechaDesde = new com.toedter.calendar.JDateChooser();
        fechaHasta = new com.toedter.calendar.JDateChooser();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        horaDesde = new javax.swing.JSpinner();
        horaHasta = new javax.swing.JSpinner();
        diasHabiles = new javax.swing.JPanel();
        lunes = new javax.swing.JCheckBox();
        martes = new javax.swing.JCheckBox();
        miercoles = new javax.swing.JCheckBox();
        jueves = new javax.swing.JCheckBox();
        viernes = new javax.swing.JCheckBox();
        sabado = new javax.swing.JCheckBox();
        domingo = new javax.swing.JCheckBox();
        noTarjeta = new javax.swing.JFormattedTextField();
        ingresos = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        btnGuardarTarjeta = new javax.swing.JButton();
        btnSalirTarjetas = new javax.swing.JButton();
        activa = new javax.swing.JCheckBox();
        placa1 = new javax.swing.JFormattedTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        descripcionTarjeta = new javax.swing.JTextArea();
        formaUsuarios = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        codigoBuscar = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        busquedaTabla = new javax.swing.JTable();
        frmRegistrar = new javax.swing.JDialog();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        usuarioBase = new javax.swing.JFormattedTextField();
        claveBase = new javax.swing.JPasswordField();
        ipBase = new javax.swing.JFormattedTextField();
        puertoBase = new javax.swing.JFormattedTextField();
        continuar = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        contenedor = new javax.swing.JDesktopPane();
        frmIngresarSistema = new javax.swing.JInternalFrame();
        jPanel2 = new javax.swing.JPanel();
        usuariot = new javax.swing.JFormattedTextField();
        clave = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        btnIngresar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        puerto = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        procesando = new javax.swing.JButton();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        miPanel = new javax.swing.JPanel();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jXTaskPane1 = new org.jdesktop.swingx.JXTaskPane();
        jToolBar1 = new javax.swing.JToolBar();
        btnClientes = new javax.swing.JButton();
        btnTicket = new javax.swing.JButton();
        btnCobrar = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jToolBar2 = new javax.swing.JToolBar();
        btnUsuarios = new javax.swing.JButton();
        btnEmpresa = new javax.swing.JButton();
        tarifas = new javax.swing.JButton();
        taskTarjeta = new org.jdesktop.swingx.JXTaskPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tarjetatxt = new javax.swing.JFormattedTextField();
        placa = new javax.swing.JFormattedTextField();
        cliente = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        spConsumo = new javax.swing.JSpinner();
        spIngreso = new javax.swing.JSpinner();
        spSalida = new javax.swing.JSpinner();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnCerrar = new javax.swing.JMenuItem();
        mnCambioclave = new javax.swing.JMenuItem();
        mnSalir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        mnAcerca = new javax.swing.JMenuItem();

        frmClientes.setTitle("Clientes");
        frmClientes.getContentPane().setLayout(null);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setLayout(null);

        jLabel4.setForeground(new java.awt.Color(0, 0, 153));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("CI/RUC");
        jPanel4.add(jLabel4);
        jLabel4.setBounds(10, 10, 60, 14);

        nombres.setEditable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.nombres}"), nombres, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        nombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombresKeyPressed(evt);
            }
        });
        jPanel4.add(nombres);
        nombres.setBounds(80, 30, 310, 20);

        direccion.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.direccion}"), direccion, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionKeyPressed(evt);
            }
        });
        jPanel4.add(direccion);
        direccion.setBounds(80, 50, 310, 20);

        jLabel12.setForeground(new java.awt.Color(0, 0, 153));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Nombres:");
        jPanel4.add(jLabel12);
        jLabel12.setBounds(10, 30, 60, 14);

        codigo.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.identificacion}"), codigo, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                codigoFocusLost(evt);
            }
        });
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoKeyPressed(evt);
            }
        });
        jPanel4.add(codigo);
        codigo.setBounds(80, 10, 100, 20);

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CEDULA", "RUC", "PASAPORTE" }));
        cmbTipo.setEnabled(false);
        jPanel4.add(cmbTipo);
        cmbTipo.setBounds(180, 10, 120, 20);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Dirección:");
        jPanel4.add(jLabel5);
        jLabel5.setBounds(10, 50, 60, 14);

        telefono.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.telefono}"), telefono, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jPanel4.add(telefono);
        telefono.setBounds(80, 70, 220, 20);

        tarjetas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Habilitada", "Tarjeta", "Desde", "Hasta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tarjetas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tarjetasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tarjetas);

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(10, 120, 380, 150);

        jLabel6.setText("Tarjetas");
        jPanel4.add(jLabel6);
        jLabel6.setBounds(20, 100, 70, 14);

        btnNuevaTarjeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/File3.gif"))); // NOI18N
        btnNuevaTarjeta.setText("Nueva Tarjeta");
        btnNuevaTarjeta.setEnabled(false);
        btnNuevaTarjeta.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btnNuevaTarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaTarjetaActionPerformed(evt);
            }
        });
        jPanel4.add(btnNuevaTarjeta);
        btnNuevaTarjeta.setBounds(80, 90, 100, 30);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Teléfono:");
        jPanel4.add(jLabel15);
        jLabel15.setBounds(10, 70, 60, 14);

        frmClientes.getContentPane().add(jPanel4);
        jPanel4.setBounds(10, 50, 400, 280);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel5.setLayout(null);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.gif"))); // NOI18N
        btnBuscar.setMnemonic('B');
        btnBuscar.setText("Buscar");
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBuscar.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel5.add(btnBuscar);
        btnBuscar.setBounds(50, 10, 60, 50);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png"))); // NOI18N
        btnAgregar.setMnemonic('N');
        btnAgregar.setText("Nuevo");
        btnAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnAgregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel5.add(btnAgregar);
        btnAgregar.setBounds(110, 10, 60, 50);

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png"))); // NOI18N
        btnModificar.setMnemonic('M');
        btnModificar.setText("Modificar");
        btnModificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btnModificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel5.add(btnModificar);
        btnModificar.setBounds(170, 10, 60, 50);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eliminar.png"))); // NOI18N
        btnEliminar.setMnemonic('E');
        btnEliminar.setText("Eliminar");
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel5.add(btnEliminar);
        btnEliminar.setBounds(230, 10, 60, 50);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salir.png"))); // NOI18N
        btnSalir.setMnemonic('S');
        btnSalir.setText("Salir");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel5.add(btnSalir);
        btnSalir.setBounds(290, 10, 60, 50);

        frmClientes.getContentPane().add(jPanel5);
        jPanel5.setBounds(40, 340, 370, 70);

        jPanel6.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/images_botones/fondoInicio.png")))); // NOI18N
        jPanel6.setOpaque(false);
        jPanel6.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel11.setForeground(new java.awt.Color(0, 51, 51));
        jLabel11.setText("Catálogo de Clientes ..::..");
        jPanel6.add(jLabel11);
        jLabel11.setBounds(10, 0, 270, 15);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 10));
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Busqueda, Creación,Modificación,  Clientes ..::..");
        jPanel6.add(jLabel13);
        jLabel13.setBounds(10, 20, 250, 13);

        frmClientes.getContentPane().add(jPanel6);
        jPanel6.setBounds(0, 0, 600, 40);

        formaTarjetas.getContentPane().setLayout(null);

        jLabel14.setText("No. Tarjeta: ");
        formaTarjetas.getContentPane().add(jLabel14);
        jLabel14.setBounds(30, 10, 70, 14);

        panelHoras.setBorder(javax.swing.BorderFactory.createTitledBorder("Fechas de Validez"));
        panelHoras.setLayout(null);

        jLabel16.setText("Hasta: ");
        panelHoras.add(jLabel16);
        jLabel16.setBounds(10, 40, 40, 14);

        jLabel17.setText("Desde:");
        panelHoras.add(jLabel17);
        jLabel17.setBounds(10, 20, 50, 14);

        fechaDesde.setDateFormatString("dd/MMM/yyyy");
        panelHoras.add(fechaDesde);
        fechaDesde.setBounds(60, 20, 95, 20);

        fechaHasta.setDateFormatString("dd/MMM/yyyy");
        panelHoras.add(fechaHasta);
        fechaHasta.setBounds(60, 40, 95, 20);

        formaTarjetas.getContentPane().add(panelHoras);
        panelHoras.setBounds(30, 100, 160, 70);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Horas de ingreso"));
        jPanel7.setLayout(null);

        jLabel18.setText("Hasta: ");
        jPanel7.add(jLabel18);
        jLabel18.setBounds(10, 40, 40, 14);

        jLabel19.setText("Desde:");
        jPanel7.add(jLabel19);
        jLabel19.setBounds(10, 20, 50, 14);
        jPanel7.add(horaDesde);
        horaDesde.setBounds(50, 20, 80, 20);
        jPanel7.add(horaHasta);
        horaHasta.setBounds(50, 40, 80, 20);

        formaTarjetas.getContentPane().add(jPanel7);
        jPanel7.setBounds(200, 100, 150, 70);

        diasHabiles.setBorder(javax.swing.BorderFactory.createTitledBorder("Días Habiles"));
        diasHabiles.setLayout(null);

        lunes.setText("Lunes");
        diasHabiles.add(lunes);
        lunes.setBounds(30, 20, 80, 23);

        martes.setText("Martes");
        diasHabiles.add(martes);
        martes.setBounds(30, 50, 80, 23);

        miercoles.setText("Miércoles");
        diasHabiles.add(miercoles);
        miercoles.setBounds(30, 80, 80, 23);

        jueves.setText("Jueves");
        jueves.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                juevesActionPerformed(evt);
            }
        });
        diasHabiles.add(jueves);
        jueves.setBounds(120, 20, 80, 23);

        viernes.setText("Viernes");
        diasHabiles.add(viernes);
        viernes.setBounds(120, 50, 80, 23);

        sabado.setText("Sábado");
        diasHabiles.add(sabado);
        sabado.setBounds(120, 80, 80, 23);

        domingo.setText("Domingo");
        diasHabiles.add(domingo);
        domingo.setBounds(210, 20, 80, 23);

        formaTarjetas.getContentPane().add(diasHabiles);
        diasHabiles.setBounds(30, 170, 320, 110);

        noTarjeta.setEditable(false);
        formaTarjetas.getContentPane().add(noTarjeta);
        noTarjeta.setBounds(90, 10, 110, 20);
        formaTarjetas.getContentPane().add(ingresos);
        ingresos.setBounds(290, 30, 40, 20);

        jLabel20.setText("No. Ingresos: ");
        formaTarjetas.getContentPane().add(jLabel20);
        jLabel20.setBounds(214, 30, 70, 14);

        btnGuardarTarjeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png"))); // NOI18N
        btnGuardarTarjeta.setMnemonic('N');
        btnGuardarTarjeta.setText("Guardar");
        btnGuardarTarjeta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarTarjeta.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnGuardarTarjeta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarTarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarTarjetaActionPerformed(evt);
            }
        });
        formaTarjetas.getContentPane().add(btnGuardarTarjeta);
        btnGuardarTarjeta.setBounds(230, 290, 60, 50);

        btnSalirTarjetas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salir.png"))); // NOI18N
        btnSalirTarjetas.setMnemonic('N');
        btnSalirTarjetas.setText("Salir");
        btnSalirTarjetas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirTarjetas.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnSalirTarjetas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirTarjetas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirTarjetasActionPerformed(evt);
            }
        });
        formaTarjetas.getContentPane().add(btnSalirTarjetas);
        btnSalirTarjetas.setBounds(290, 290, 60, 50);

        activa.setText("Tarjeta Activa:   ");
        activa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        activa.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        formaTarjetas.getContentPane().add(activa);
        activa.setBounds(210, 10, 110, 23);
        formaTarjetas.getContentPane().add(placa1);
        placa1.setBounds(90, 30, 110, 20);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Placa:");
        formaTarjetas.getContentPane().add(jLabel21);
        jLabel21.setBounds(20, 30, 60, 14);

        jLabel22.setText("Descripción:");
        formaTarjetas.getContentPane().add(jLabel22);
        jLabel22.setBounds(20, 50, 70, 14);

        descripcionTarjeta.setColumns(20);
        descripcionTarjeta.setRows(5);
        jScrollPane2.setViewportView(descripcionTarjeta);

        formaTarjetas.getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(90, 50, 240, 40);

        formaUsuarios.setLocationByPlatform(true);
        formaUsuarios.getContentPane().setLayout(null);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel8.setLayout(null);

        codigoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoBuscarKeyPressed(evt);
            }
        });
        jPanel8.add(codigoBuscar);
        codigoBuscar.setBounds(100, 10, 220, 20);

        jLabel23.setText("NOMBRES:");
        jPanel8.add(jLabel23);
        jLabel23.setBounds(10, 10, 70, 14);

        formaUsuarios.getContentPane().add(jPanel8);
        jPanel8.setBounds(10, 10, 510, 40);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel9.setLayout(null);

        busquedaTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Identificación", "Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        busquedaTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                busquedaTablaMouseClicked(evt);
            }
        });
        busquedaTabla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                busquedaTablaKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(busquedaTabla);

        jPanel9.add(jScrollPane3);
        jScrollPane3.setBounds(20, 20, 480, 150);

        formaUsuarios.getContentPane().add(jPanel9);
        jPanel9.setBounds(10, 60, 510, 180);

        frmRegistrar.setModal(true);
        frmRegistrar.getContentPane().setLayout(null);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Usuario:");
        frmRegistrar.getContentPane().add(jLabel24);
        jLabel24.setBounds(12, 32, 40, 14);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Clave:");
        frmRegistrar.getContentPane().add(jLabel25);
        jLabel25.setBounds(12, 60, 55, 14);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("IP:");
        frmRegistrar.getContentPane().add(jLabel26);
        jLabel26.setBounds(12, 89, 55, 14);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("PUERTO:");
        frmRegistrar.getContentPane().add(jLabel27);
        jLabel27.setBounds(12, 118, 44, 14);

        usuarioBase.setText("root");
        frmRegistrar.getContentPane().add(usuarioBase);
        usuarioBase.setBounds(79, 27, 160, 20);

        claveBase.setText("jcinform@2020");
        frmRegistrar.getContentPane().add(claveBase);
        claveBase.setBounds(79, 60, 160, 20);

        ipBase.setText("localhost");
        ipBase.setToolTipText("Si su máquina es el servidor escriba: localhost, caso contrario escriba el nombre del servidor o la dirección IP");
        frmRegistrar.getContentPane().add(ipBase);
        ipBase.setBounds(79, 94, 160, 20);

        puertoBase.setText("3306");
        frmRegistrar.getContentPane().add(puertoBase);
        puertoBase.setBounds(79, 128, 70, 20);

        continuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png"))); // NOI18N
        continuar.setText("Continuar");
        continuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continuarActionPerformed(evt);
            }
        });
        frmRegistrar.getContentPane().add(continuar);
        continuar.setBounds(12, 173, 350, 23);

        jLabel28.setText("(Puerto por el que se conecta)");
        frmRegistrar.getContentPane().add(jLabel28);
        jLabel28.setBounds(250, 130, 200, 14);

        jLabel29.setText("(localhost o IP del servidor)");
        frmRegistrar.getContentPane().add(jLabel29);
        jLabel29.setBounds(250, 100, 200, 14);

        jLabel30.setText("(Clave del motor de BD)");
        frmRegistrar.getContentPane().add(jLabel30);
        jLabel30.setBounds(250, 60, 200, 30);

        jLabel31.setText("(Usuario de de BD)");
        frmRegistrar.getContentPane().add(jLabel31);
        jLabel31.setBounds(250, 30, 200, 14);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de control de parqueaderos");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jSplitPane1.setDividerLocation(220);
        jSplitPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSplitPane1KeyPressed(evt);
            }
        });

        contenedor.setBackground(new java.awt.Color(240, 240, 240));

        frmIngresarSistema.setTitle("Seguridad");
        frmIngresarSistema.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unlock.gif"))); // NOI18N
        try {
            frmIngresarSistema.setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        frmIngresarSistema.setVisible(true);
        frmIngresarSistema.getContentPane().setLayout(null);

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 153, 51), 1, true));
        jPanel2.setLayout(null);

        usuariot.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 153, 0)));
        usuariot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usuariotKeyPressed(evt);
            }
        });
        jPanel2.add(usuariot);
        usuariot.setBounds(120, 20, 110, 16);

        clave.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 153, 0)));
        clave.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                claveFocusLost(evt);
            }
        });
        clave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                claveKeyPressed(evt);
            }
        });
        jPanel2.add(clave);
        clave.setBounds(120, 40, 110, 16);

        jLabel1.setText("Usuario:");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(60, 20, 50, 14);

        jLabel2.setText("Clave:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(70, 40, 50, 14);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.gif"))); // NOI18N
        jButton9.setText("Sallir");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton9);
        jButton9.setBounds(200, 80, 100, 30);

        btnIngresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lock.gif"))); // NOI18N
        btnIngresar.setText("Ingresar");
        btnIngresar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        btnIngresar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnIngresarKeyPressed(evt);
            }
        });
        jPanel2.add(btnIngresar);
        btnIngresar.setBounds(70, 80, 110, 30);

        jButton1.setText("ejectuar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(0, 60, 73, 23);

        puerto.setText("COM3");
        puerto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                puertoActionPerformed(evt);
            }
        });
        jPanel2.add(puerto);
        puerto.setBounds(0, 30, 35, 20);

        frmIngresarSistema.getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 40, 390, 140);

        jPanel3.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/images_botones/fondoInicio.png")))); // NOI18N
        jPanel3.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Acceso al Sistema");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 270, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10));
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Ingrese el usuario proporcionado por el Administrador");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 20, 300, 13);

        procesando.setBackground(new java.awt.Color(204, 204, 255));
        procesando.setFont(new java.awt.Font("Tahoma", 0, 10));
        procesando.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/procesando.gif"))); // NOI18N
        procesando.setBorderPainted(false);
        procesando.setContentAreaFilled(false);
        procesando.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        procesando.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/procesando.gif"))); // NOI18N
        procesando.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel3.add(procesando);
        procesando.setBounds(280, 0, 140, 40);

        frmIngresarSistema.getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 396, 40);

        frmIngresarSistema.setBounds(120, 170, 410, 220);
        contenedor.add(frmIngresarSistema, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jSplitPane1.setRightComponent(contenedor);

        jXTaskPaneContainer1.setAlignmentX(0.1F);
        jXTaskPaneContainer1.setAlignmentY(0.1F);
        jXTaskPaneContainer1.setMinimumSize(new java.awt.Dimension(154, 519));
        jXTaskPaneContainer1.setPaintBorderInsets(false);
        jXTaskPaneContainer1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jXTaskPaneContainer1KeyPressed(evt);
            }
        });

        miPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        miPanel.setPreferredSize(new java.awt.Dimension(100, 60));
        jXTaskPaneContainer1.add(miPanel);

        jCalendar1.setFocusable(false);
        jCalendar1.setMinimumSize(new java.awt.Dimension(158, 140));
        jCalendar1.setPreferredSize(new java.awt.Dimension(134, 95));
        jCalendar1.setWeekOfYearVisible(false);
        jCalendar1.setWeekdayForeground(new java.awt.Color(0, 0, 0));
        jXTaskPaneContainer1.add(jCalendar1);

        jXTaskPane1.setTitle("Control");

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(1);
        jToolBar1.setRollover(true);

        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/usr.png"))); // NOI18N
        btnClientes.setMnemonic('C');
        btnClientes.setText("Clientes");
        btnClientes.setFocusable(false);
        btnClientes.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnClientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        jToolBar1.add(btnClientes);

        btnTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ticket.gif"))); // NOI18N
        btnTicket.setMnemonic('T');
        btnTicket.setText("Ticket");
        btnTicket.setFocusable(false);
        btnTicket.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTicket.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTicketActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTicket);

        btnCobrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Previous.gif"))); // NOI18N
        btnCobrar.setText("Cobrar");
        btnCobrar.setFocusable(false);
        btnCobrar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCobrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobrarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCobrar);

        btnReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/resultados.png"))); // NOI18N
        btnReportes.setText("Reportes");
        btnReportes.setFocusable(false);
        btnReportes.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnReportes.setPreferredSize(new java.awt.Dimension(101, 21));
        btnReportes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });
        jToolBar1.add(btnReportes);

        jXTaskPane1.getContentPane().add(jToolBar1);

        jXTaskPaneContainer1.add(jXTaskPane1);

        jXTaskPane2.setTitle("Administracion");

        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(1);
        jToolBar2.setRollover(true);

        btnUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/User3.gif"))); // NOI18N
        btnUsuarios.setMnemonic('U');
        btnUsuarios.setText("Usuarios");
        btnUsuarios.setFocusable(false);
        btnUsuarios.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnUsuarios.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUsuarios.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        jToolBar2.add(btnUsuarios);

        btnEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/configure.gif"))); // NOI18N
        btnEmpresa.setMnemonic('E');
        btnEmpresa.setText("Empresa");
        btnEmpresa.setFocusable(false);
        btnEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEmpresa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpresaActionPerformed(evt);
            }
        });
        jToolBar2.add(btnEmpresa);

        tarifas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dinero.gif"))); // NOI18N
        tarifas.setMnemonic('R');
        tarifas.setText("Tarifas");
        tarifas.setFocusable(false);
        tarifas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        tarifas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tarifas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tarifasActionPerformed(evt);
            }
        });
        jToolBar2.add(tarifas);

        jXTaskPane2.getContentPane().add(jToolBar2);

        jXTaskPaneContainer1.add(jXTaskPane2);

        taskTarjeta.setCollapsed(true);
        taskTarjeta.setTitle("Registro Tarjeta");

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel1.setPreferredSize(new java.awt.Dimension(0, 170));
        jPanel1.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("CONSUMO:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 120, 60, 14);

        tarjetatxt.setBorder(null);
        tarjetatxt.setEditable(false);
        tarjetatxt.setForeground(new java.awt.Color(255, 0, 0));
        tarjetatxt.setText(".");
        tarjetatxt.setValue(new String(""));
        tarjetatxt.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                tarjetatxtCaretUpdate(evt);
            }
        });
        tarjetatxt.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tarjetatxtPropertyChange(evt);
            }
        });
        tarjetatxt.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tarjetatxtVetoableChange(evt);
            }
        });
        jPanel1.add(tarjetatxt);
        tarjetatxt.setBounds(10, 30, 110, 20);

        placa.setEditable(false);
        placa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel1.add(placa);
        placa.setBounds(110, 30, 70, 20);

        cliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cliente.setEditable(false);
        cliente.setForeground(new java.awt.Color(51, 51, 255));
        cliente.setText(".");
        cliente.setFont(new java.awt.Font("Tahoma", 0, 12));
        jPanel1.add(cliente);
        cliente.setBounds(0, 0, 180, 30);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("INGRESO:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 60, 60, 14);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel9.setText("SALIDA:");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(10, 90, 60, 14);

        spConsumo.setFont(new java.awt.Font("Tahoma", 1, 14));
        spConsumo.setForeground(new java.awt.Color(0, 153, 102));
        spConsumo.setFocusable(false);
        jPanel1.add(spConsumo);
        spConsumo.setBounds(70, 110, 90, 30);

        spIngreso.setFont(new java.awt.Font("Tahoma", 1, 14));
        spIngreso.setForeground(new java.awt.Color(0, 102, 255));
        spIngreso.setFocusable(false);
        jPanel1.add(spIngreso);
        spIngreso.setBounds(70, 50, 90, 30);

        spSalida.setFont(new java.awt.Font("Tahoma", 1, 14));
        spSalida.setForeground(new java.awt.Color(255, 0, 0));
        spSalida.setFocusable(false);
        jPanel1.add(spSalida);
        spSalida.setBounds(70, 80, 90, 30);

        taskTarjeta.getContentPane().add(jPanel1);

        jXTaskPaneContainer1.add(taskTarjeta);

        jSplitPane1.setLeftComponent(jXTaskPaneContainer1);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("Opciones");

        mnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/undo.gif"))); // NOI18N
        mnCerrar.setText("Cerrar Sesión");
        mnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCerrarActionPerformed(evt);
            }
        });
        jMenu1.add(mnCerrar);

        mnCambioclave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/User3.gif"))); // NOI18N
        mnCambioclave.setText("Cambio Clave");
        jMenu1.add(mnCambioclave);

        mnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.gif"))); // NOI18N
        mnSalir.setText("Salir");
        mnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSalirActionPerformed(evt);
            }
        });
        jMenu1.add(mnSalir);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        jMenuItem3.setText("Ayuda de Contenidos");
        jMenu2.add(jMenuItem3);

        mnAcerca.setText("Acerca de..");
        mnAcerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAcercaActionPerformed(evt);
            }
        });
        jMenu2.add(mnAcerca);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents
public void logear(){
    frmIngresarSistema.setVisible(true);
                //frmLogin.setIconImage(new ImageIcon(getClass().getResource("/images_botones/ico.gif")).getImage());
                adm = new Administrador();
//                frmLogin.setModal(true);
//                frmLogin.setSize(400, 230);
//                frmLogin.setLocation(350, 300);
//                frmLogin.setFocusable(true);
//                frmLogin.setResizable(false);
//                frmLogin.setTitle("Inicio de Sesión");
//                frmLogin.setUndecorated(true);
//                frmLogin.show();
}
    public Boolean comprobar() {

        GeneraXMLPersonal pXml = new GeneraXMLPersonal();
        pXml.inicio();
        String textoXML = pXml.leerXML();
        if (textoXML == null) {
            return false;
        } else {
            return true;
        }

    }

    public void escribir() {

        GeneraXMLPersonal pXml = new GeneraXMLPersonal();
        pXml.generaDocumentoXMLPersonal();
        BeanUsuario beanEmpleado = new BeanUsuario();
        //Establecemos los valores de atributos de Empleado
        beanEmpleado.setDNI("80114918");
        beanEmpleado.setUsuario("" + usuarioBase.getText().trim());
        beanEmpleado.setClave("" + cl.encriptar(claveBase.getText().trim()));
        beanEmpleado.setIp("" + ipBase.getText().trim());
        beanEmpleado.setPuerto("" + puertoBase.getText().trim());

        //Generamos documento XML para los valores anteriores
        pXml.llenarEstructuraDocumentoXMLEmpleado(beanEmpleado);
        //obtenemos el documento XML en cadena de texto
        String textoXML = pXml.obtenerTextoXML();
        //grabamos en archivo el documento XML
        pXml.grabaDocumentoXML(textoXML);

    }

    private void usuariotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usuariotKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            usuariot.nextFocus();
        }
}//GEN-LAST:event_usuariotKeyPressed

    private void claveFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_claveFocusLost
        // TODO add your handling code here:
}//GEN-LAST:event_claveFocusLost

    private void claveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_claveKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            Thread cargar = new Thread()     {

                public void run() {
                    btnIngresar.doClick();
                }
            };
            cargar.start();



        }
}//GEN-LAST:event_claveKeyPressed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        try {
            // TODO add your handling code here:
            this.finalize();
            this.hide();

            System.exit(0);

        } catch (Throwable ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_jButton9ActionPerformed
    public void verificarUsuario() {
        Usuarios usu = adm.ingresoSistema(usuariot.getText(), clave.getText());
        if (usu != null) {
            usuario = usu;
            clave.setEditable(true);
            usuariot.setEditable(true);
            usuariot.setText("");
            clave.setText("");
            frmIngresarSistema.setVisible(false);
            usuarioActual = usu;
             List<Empresa> emp = adm.listar("Select o from Empresa as o ");
                this.empresaObj = emp.get(0);
           } else {
            clave.setEditable(true);
            usuariot.setEditable(true);
            usuariot.setText("");
            clave.setText("");
            JOptionPane.showMessageDialog(this, "Usuario o Clave incorrecta", "JCINFORM", JOptionPane.ERROR_MESSAGE);
            usuariot.requestFocusInWindow();


        }
    }

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        // TODO add your handling code here:

        Thread cargar = new Thread()     {

            public void run() {
                procesando.setVisible(true);
                btnIngresar.setEnabled(false);
                clave.setEditable(false);
                usuariot.setEditable(false);
                verificarUsuario();
                procesando.setVisible(false);
                btnIngresar.setEnabled(true);

            }
        };
        cargar.start();


//                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);

}//GEN-LAST:event_btnIngresarActionPerformed

    private void btnIngresarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnIngresarKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_btnIngresarKeyPressed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        try {
            // TODO add your handling code here:
            frmClientes.setIconImage(new ImageIcon(getClass().getResource("/images_botones/ico.gif")).getImage());
            //adm = new Administrador();
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Clientes' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar ");
                return;
            }

            frmClientes.setSize(441, 455);
            frmClientes.setLocation(240, 100);
            frmClientes.show();
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        //<property name="toplink.cache.type.default" value="NONE"/>

    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        try {
            // TODO add your handling code here:
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Usuarios' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar ");
                return;
            }

            frmUsuarios usu = new frmUsuarios(this, true, this, adm);
            usu.setSize(441, 445);
            usu.setLocation(240, 100);
            usu.show();
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUsuariosActionPerformed
    public void buscarTarjeta() {
        final principal pra = this;
//        Thread cargar = new Thread() {
//
//            @SuppressWarnings("static-access")
//            public void run() {
        try {

            try {
                Tarjetas tarje = (Tarjetas) adm.buscarClave(tarjetatxt.getText(), Tarjetas.class);
                try {
                    String tar = tarje.getTarjeta();
                } catch (Exception e) {
                    tarje = null;
                }
                if (tarje == null) {
//                      int val = JOptionPane.showConfirmDialog(getContentPane(), "Tarjeta " + "  NO registrada, \n Desea Registrar la tarjeta? ");
//                    if(val ==0){
//
//                            try {
//                                // TODO add your handling code here:
//                                List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Clientes' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' ");
//                                if (accesosL.size() > 0) {
//                                    permisos = accesosL.get(0);
//                                } else {
//                                    JOptionPane.showMessageDialog(pra, "No tiene permisos para ingresar ");
//                                    return;
//                                }
//                                frmClientes.setSize(441, 455);
//                                frmClientes.setLocation(240, 100);
//                            if (nuevaTarjeta == true) {
                    noTarjeta.setText(tarjetatxt.getText());
//                            }
//                                frmClientes.show();
//                            } catch (Exception ex) {
//                                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//
//                        }
                } else {
                    Date fechaActual = new Date();
                    Boolean habilitada = tarje.getHabilitada();
                    int diaActual = fechaActual.getDay(); //1=Domingo, 2=Lunes 3=Martes,4=Miercoles,5=Jueves,6=Viernes
                    DateTime desde = new DateTime(tarje.getDesde());
                    DateTime hasta = new DateTime(tarje.getHasta());
                    DateTime fechaAct = new DateTime(fechaActual);
                    Boolean continua = false;
                    if (habilitada) {
                        if ((fechaAct.compareTo(desde) > 0 || fechaAct.compareTo(desde) == 0) && (fechaAct.compareTo(hasta) < 0 || fechaAct.compareTo(hasta) == 0)) {
                            System.out.println("EN EL RANGO DE FECHAS");
                        } else {
                            JOptionPane.showMessageDialog(getContentPane(), "Su Fecha de tarjeta expiró...! \n Cliente: " + tarje.getCliente().getNombres(), "JCINFORM ", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Date fecIn = tarje.getHorainicio();
                        Date fecIn3 = tarje.getHorafin();
                        LocalTime horaIni = new LocalTime(new DateTime(fecIn));
                        LocalTime horaFin = new LocalTime(new DateTime(fecIn3));
                        LocalTime ahora = new LocalTime(new DateTime(new Date()));
                        if ((ahora.compareTo(horaIni) > 0 || ahora.compareTo(horaIni) == 0) && (ahora.compareTo(horaFin) < 0 || ahora.compareTo(horaFin) == 0)) {
                            System.out.println("EN EL RANGO DE HORA");
                        } else {
                            JOptionPane.showMessageDialog(getContentPane(), "No puede ingresar en este Horario...! \n Cliente: " + tarje.getCliente().getNombres(), "JCINFORM ", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

//                                System.out.println(""+desde);
//                                System.out.println(""+hasta);
//                                System.out.println(""+d.getDays());

                        if (diaActual == 0) {
                            if (tarje.getDomingo()) {
                                continua = true;
                            }
                        } else if (diaActual == 1) {
                            if (tarje.getLunes()) {
                                continua = true;
                            }
                        } else if (diaActual == 2) {
                            if (tarje.getMartes()) {
                                continua = true;
                            }
                        } else if (diaActual == 3) {
                            if (tarje.getMiercoles()) {
                                continua = true;
                            }
                        } else if (diaActual == 4) {
                            if (tarje.getJueves()) {
                                continua = true;
                            }
                        } else if (diaActual == 5) {
                            if (tarje.getViernes()) {
                                continua = true;
                            }
                        } else if (diaActual == 6) {
                            if (tarje.getSabado()) {
                                continua = true;
                            }
                        }
                        if (continua == false) {
                            JOptionPane.showMessageDialog(getContentPane(), "Día NO hábil para ingresar ...! \n Cliente: " + tarje.getCliente().getNombres(), "JCINFORM ", JOptionPane.ERROR_MESSAGE);
                        }


                        taskTarjeta.setCollapsed(false);
                        procesando.setVisible(true);
                        cliente.setText(tarje.getCliente().getNombres());
                        List<Factura> facturas = adm.query("Select o from Factura as o "
                                + "where o.tarjeta.tarjeta = '" + tarje.getTarjeta() + "' "
                                + "and o.fechafin is null  ");
                        Factura fac = new Factura();



                        if (facturas.size() > 0) {

                            //CALCULO DE TIEMPO QUE DURO

                            LocalTime horaInicio = new LocalTime(new DateTime(fac.getFechaini()));
                            LocalTime horaFini = new LocalTime(new DateTime(fac.getFechafin()));
                            Integer minutos = (Minutes.minutesBetween(horaInicio, horaFini).getMinutes());
                            Integer horas = minutos / 60;
                            Date act = new Date();
                            act.setHours(horas);
                            Float min = minutos / 60f;
                            int indice = min.toString().indexOf(".");
                            Float valorf = new Float("0" + min.toString().substring(indice));
                            int valor = java.lang.Math.round((valorf * 60));
                            act.setMinutes(valor);
                            //FIN DE CALCULO DEL TIEMPO


                            fac = facturas.get(0);
                            fac.setFechafin(new Date());
                            fac.setTiempo(act);
                            adm.actualizar(fac);

                            SpinnerDateModel sm = new SpinnerDateModel(fac.getFechaini(), null, null, Calendar.HOUR_OF_DAY);
                            JSpinner spinner = new JSpinner(sm);
                            JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "HH:mm:ss");
                            spIngreso.setModel(sm);
                            spIngreso.setEditor(de);

                            SpinnerDateModel sm2 = new SpinnerDateModel(fac.getFechafin(), null, null, Calendar.HOUR_OF_DAY);
                            JSpinner spinner2 = new JSpinner(sm2);
                            JSpinner.DateEditor de2 = new JSpinner.DateEditor(spinner2, "HH:mm:ss");
                            spSalida.setModel(sm2);
                            spSalida.setEditor(de2);

                            SpinnerDateModel sm3 = new SpinnerDateModel(fac.getTiempo(), null, null, Calendar.HOUR_OF_DAY);
                            JSpinner spinner3 = new JSpinner(sm3);
                            JSpinner.DateEditor de3 = new JSpinner.DateEditor(spinner3, "HH:mm:ss");
                            spConsumo.setModel(sm3);
                            spConsumo.setEditor(de3);

                        } else {
                            fac.setPlaca(placa.getText());
                            fac.setFechaini(new Date());
                            fac.setFecha(new Date());
                            fac.setTarjeta(tarje);
                            fac.setTicket(null);
                            adm.guardar(fac);

                            SpinnerDateModel sm = new SpinnerDateModel(fac.getFechaini(), null, null, Calendar.HOUR_OF_DAY);
                            JSpinner spinner = new JSpinner(sm);
                            JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "HH:mm:ss");
                            spIngreso.setModel(sm);
                            spIngreso.setEditor(de);
                            Date f = new Date();
                            f.setHours(0);
                            f.setMinutes(0);
                            f.setSeconds(0);

                            SpinnerDateModel sm2 = new SpinnerDateModel(f, null, null, Calendar.HOUR_OF_DAY);
                            JSpinner spinner2 = new JSpinner(sm2);
                            JSpinner.DateEditor de2 = new JSpinner.DateEditor(spinner2, "HH:mm:ss");
                            spSalida.setModel(sm2);
                            spSalida.setEditor(de2);

                            SpinnerDateModel sm3 = new SpinnerDateModel(f, null, null, Calendar.HOUR_OF_DAY);
                            JSpinner spinner3 = new JSpinner(sm3);
                            JSpinner.DateEditor de3 = new JSpinner.DateEditor(spinner3, "HH:mm:ss");
                            spConsumo.setModel(sm3);
                            spConsumo.setEditor(de3);

                        }



                    } else {
                        JOptionPane.showMessageDialog(getContentPane(), "Tarjeta INHABILITADA ...! \n Cliente: " + tarje.getCliente().getNombres(), "JCINFORM ", JOptionPane.ERROR_MESSAGE);
                    }


                }
            } catch (Exception ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            }

            procesando.setVisible(false);
//                    this.sleep(2000);
            taskTarjeta.setCollapsed(true);
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
//            }
//        };
//        cargar.start();



//        JOptionPane.showMessageDialog(this, tarjetatxt.getText());

    }
    private void tarjetatxtPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tarjetatxtPropertyChange
        //        try {
//            // TODO add your handling code here:
//            Tarjetas tarje = (Tarjetas) adm.buscarClave(tarjeta.getText(), Tarjetas.class);
//            cliente.setText(tarje.getCliente().getNombres());
//
//        } catch (Exception ex) {
//            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_tarjetatxtPropertyChange

    private void tarjetatxtVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tarjetatxtVetoableChange
        // TODO add your handling code here:
//            try {
//            // TODO add your handling code here:
//            Tarjetas tarje = (Tarjetas) adm.buscarClave(tarjeta.getText(), Tarjetas.class);
//            cliente.setText(tarje.getCliente().getNombres());
//
//        } catch (Exception ex) {
//            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_tarjetatxtVetoableChange

    private void tarjetatxtCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tarjetatxtCaretUpdate
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            Tarjetas tarje = (Tarjetas) adm.buscarClave(tarjetatxt.getText(), Tarjetas.class);
            try {
                String cli = tarje.getTarjeta();
            } catch (Exception e) {
                tarje = null;
            }
            if (tarje != null) {
                cliente.setText(tarje.getCliente().getNombres());
            } else {
                cliente.setText("");
                placa.setText("");

            }

        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tarjetatxtCaretUpdate

    private void btnEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpresaActionPerformed
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Empresa' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar ");
                return;
            }
            frmEmpresa usu = new frmEmpresa(this, true, this, adm);
            usu.setSize(441, 445);
            usu.setLocation(240, 100);
            usu.show();
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnEmpresaActionPerformed

    private void tarifasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tarifasActionPerformed
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Tarifas' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar ");
                return;
            }
            frmTarifas usu = new frmTarifas(this, true, this, adm);
            usu.setSize(441, 445);
            usu.setLocation(240, 100);
            usu.show();
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tarifasActionPerformed

    private void btnTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTicketActionPerformed
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Tickets' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar ");
                return;
            }
            frmTicket usu = new frmTicket(this, true, this, adm);
            usu.setSize(334, 238);
            usu.setLocation(240, 100);
            usu.show();
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnTicketActionPerformed

    private void jXTaskPaneContainer1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jXTaskPaneContainer1KeyPressed
        // TODO add your handling code here:
//        if(evt.getKeyCode()== evt.VK_F2){
//            JOptionPane.showMessageDialog(this, "PRESIONO F2");
//        }
    }//GEN-LAST:event_jXTaskPaneContainer1KeyPressed

    private void jSplitPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSplitPane1KeyPressed
        // TODO add your handling code here:
//        if(evt.getKeyCode()== evt.VK_F2){
//            JOptionPane.showMessageDialog(this, "PRESIONO F2");
//        }
    }//GEN-LAST:event_jSplitPane1KeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
//                if(evt.getKeyCode()== evt.VK_F2){
//            JOptionPane.showMessageDialog(this, "PRESIONO F2");
//        }
    }//GEN-LAST:event_formKeyPressed

    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarActionPerformed
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Factura' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar ");
                return;
            }
            frmFactura usu = new frmFactura(this, true, this, adm);
            usu.setSize(606, 354);
            usu.setLocation(240, 120);
            usu.show();
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnCobrarActionPerformed

    private void mnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);

    }//GEN-LAST:event_mnSalirActionPerformed

    private void mnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCerrarActionPerformed
        // TODO add your handling code here:
        usuario = new Usuarios();
        permisos = new Accesos();
        frmIngresarSistema.setVisible(true);
//        frmLogin.setModal(true);
//        frmLogin.setSize(400, 230);
//        frmLogin.setLocation(350, 300);
//        frmLogin.setResizable(false);
//        frmLogin.setTitle("Inicio de Sesión");
//        frmLogin.setUndecorated(true);
//        frmLogin.show();

    }//GEN-LAST:event_mnCerrarActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Reportes' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar ");
                return;
            }
            frmReportes usu = new frmReportes(this, adm);
            usu.setSize(723, 557);
            usu.setLocation(0, 0);
            usu.setMaximizable(true);
            contenedor.add(usu);
            usu.show();
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnReportesActionPerformed

    private void mnAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAcercaActionPerformed
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Reportes' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar ");
                return;
            }
            acerca usu = new acerca(this, true);
            usu.setSize(458, 239);
            usu.setLocation(260, 220);
            usu.show();
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_mnAcercaActionPerformed

    private void nombresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombresKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            nombres.nextFocus();
        }
}//GEN-LAST:event_nombresKeyPressed

    private void direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            direccion.nextFocus();
        }
}//GEN-LAST:event_direccionKeyPressed

    private void codigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoFocusLost
        // TODO add your handling code here:
        try {
            usuarioObj = (Clientes) adm.querySimple("Select o from Clientes as o "
                    + "where o.identificacion = '" + codigo.getText().trim() + "' ");
            if (usuarioObj != null) {
                bindingGroup.unbind();
                bindingGroup.bind();
                modificar = true;
                grabar = true;
            }
        } catch (Exception e) {
            System.out.println("BUSCAR CEDULA UNICA " + e);
        }
}//GEN-LAST:event_codigoFocusLost

    private void codigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            codigo.nextFocus();
        }
}//GEN-LAST:event_codigoKeyPressed

    void llenarTarjeta() {
        noTarjeta.setText(tarjeta.getTarjeta());
        fechaDesde.setDate(tarjeta.getDesde());
        fechaHasta.setDate(tarjeta.getHasta());
        descripcionTarjeta.setText(tarjeta.getDescripcion());
        placa1.setText(tarjeta.getPlaca());
//                horaDesde.setValue(tarjeta.getHorainicio());
//                horaHasta.setValue(tarjeta.getHorafin());
        lunes.setSelected(tarjeta.getLunes());
        martes.setSelected(tarjeta.getMartes());
        miercoles.setSelected(tarjeta.getMiercoles());
        jueves.setSelected(tarjeta.getJueves());
        viernes.setSelected(tarjeta.getViernes());
        sabado.setSelected(tarjeta.getSabado());
        domingo.setSelected(tarjeta.getDomingo());
        activa.setSelected(tarjeta.getHabilitada());
        //AQUI CARGO LAS HORAS

        SpinnerDateModel sm = new SpinnerDateModel(tarjeta.getHorainicio(), null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner = new JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "HH:mm");
        horaDesde.setModel(sm);
        horaDesde.setEditor(de);

        SpinnerDateModel sm2 = new SpinnerDateModel(tarjeta.getHorafin(), null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner2 = new JSpinner(sm2);
        JSpinner.DateEditor de2 = new JSpinner.DateEditor(spinner2, "HH:mm");

        horaHasta.setEditor(de2);
        horaHasta.setModel(sm2);



    }
    private void tarjetasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tarjetasMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            try {
                tarjeta = (Tarjetas) adm.buscarClave((String) tarjetas.getValueAt(tarjetas.getSelectedRow(), 1), Tarjetas.class);
                llenarTarjeta();
                formaTarjetas.setModal(true);
                formaTarjetas.setSize(400, 388);
                formaTarjetas.setLocation(250, 70);
                formaTarjetas.show();
            } catch (Exception ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
}//GEN-LAST:event_tarjetasMouseClicked

    private void btnNuevaTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaTarjetaActionPerformed
        // TODO add your handling code here:
        if (usuarioObj.getCodigo().equals(null)) {
            JOptionPane.showMessageDialog(this, "Guarde primero el cliente ");
            return;
        }
        tarjeta = new Tarjetas();
        tarjeta.setHabilitada(true);
        tarjeta.setLunes(false);
        tarjeta.setMartes(false);
        tarjeta.setMiercoles(false);
        tarjeta.setJueves(false);
        tarjeta.setViernes(false);
        tarjeta.setSabado(false);
        tarjeta.setDomingo(false);
        tarjeta.setHorainicio(new Date());
        tarjeta.setHorafin(new Date());
        tarjeta.setDesde(new Date());
        tarjeta.setHasta(new Date());
        llenarTarjeta();
        formaTarjetas.setModal(true);
        formaTarjetas.setSize(400, 388);
        formaTarjetas.show();
        nuevaTarjeta = true;

    }//GEN-LAST:event_btnNuevaTarjetaActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        formaUsuarios.setModal(true);
        formaUsuarios.setSize(533, 300);
        formaUsuarios.setLocation(250, 70);
        formaUsuarios.show();
        this.codigoBuscar.requestFocusInWindow();
        DefaultTableModel dtm = (DefaultTableModel) busquedaTabla.getModel();
        dtm.getDataVector().removeAllElements();
        busquedaTabla.setModel(dtm);
        codigoBuscar.setText("");
    }//GEN-LAST:event_btnBuscarActionPerformed
    public void habilitar(Boolean estado) {
        codigo.setEditable(estado);
        nombres.setEditable(estado);
        direccion.setEditable(estado);
        telefono.setEditable(estado);
        tarjetas.setEnabled(estado);
        cmbTipo.setEnabled(estado);
        btnNuevaTarjeta.setEnabled(estado);
    }

    public void limpiar() {
        usuarioObj = new Clientes();
        bindingGroup.unbind();
        bindingGroup.bind();
        DefaultTableModel dtm = (DefaultTableModel) tarjetas.getModel();
        dtm.getDataVector().removeAllElements();
        tarjetas.setModel(dtm);

    }
    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed

        // TODO add your handling code here:
        if (permisos.getAgregar()) {
            if (grabar == false) {
                this.btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
                this.btnAgregar.setLabel("Guardar");
                this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
                this.btnModificar.setLabel("Cancelar");
                grabar = true;
                modificar = false;
                habilitar(true);
                limpiar();
                codigo.requestFocusInWindow();
                btnAgregar.setMnemonic('G');
                btnModificar.setMnemonic('C');
                btnBuscar.setEnabled(false);

            } else if (grabar == true) {
                if (codigo.getText().isEmpty() || nombres.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Registre los campos requeridos ...!");
                } else {
                    usuarioObj.setDireccion(direccion.getText());
                    usuarioObj.setNombres(nombres.getText());
                    usuarioObj.setTelefono(telefono.getText());
                    usuarioObj.setIdentificacion(codigo.getText());
                    if (modificar) {
                        try {
                            adm.actualizar(usuarioObj);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en actualizar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                    } else {
                        try {

                            adm.guardar(usuarioObj);
                            formaTarjetas.setModal(true);
                            formaTarjetas.setSize(400, 388);
                            formaTarjetas.show();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en guardar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                    }
                    nuevaTarjeta = false;
                    this.btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
                    this.btnAgregar.setLabel("Nuevo");
                    this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png")));
                    this.btnModificar.setLabel("Modificar");
                    btnAgregar.setMnemonic('N');
                    btnModificar.setMnemonic('M');
                    grabar = false;
                    modificar = false;
                    habilitar(false);
                    btnBuscar.setEnabled(true);

                }

            }
        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }

    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:

        if (grabar == false) {
            if (permisos.getModificar()) {
                if (codigo.getText().trim().isEmpty()) {
                    return;
                }
                this.codigo.requestFocusInWindow();
                this.btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
                this.btnAgregar.setLabel("Guardar");
                this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
                this.btnModificar.setLabel("Cancelar");
                btnAgregar.setMnemonic('G');
                btnModificar.setMnemonic('C');
                modificar = true;
                grabar = true;
                habilitar(true);
                btnBuscar.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
            }
        } else {

            grabar = false;
            modificar = false;
            this.btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
            this.btnAgregar.setLabel("Nuevo");
            this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png")));
            this.btnModificar.setLabel("Modificar");
            btnAgregar.setMnemonic('N');
            btnModificar.setMnemonic('M');
            habilitar(false);
            btnBuscar.setEnabled(true);

        }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (permisos.getEliminar()) {        // TODO add your handling code here:
            try {
                adm.eliminarObjeto(Clientes.class, usuarioObj.getCodigo());
                this.limpiar();
            } catch (Exception ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }
}//GEN-LAST:event_btnEliminarActionPerformed

    private void juevesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_juevesActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_juevesActionPerformed

    private void btnGuardarTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarTarjetaActionPerformed
        // TODO add your handling code here:
        try {
            tarjeta.setTarjeta(noTarjeta.getText());
            tarjeta.setCliente(usuarioObj);
            Date fechaDes = fechaDesde.getDate();
            fechaDes.setHours(0);
            fechaDes.setMinutes(01);
            fechaDes.setSeconds(01);
            tarjeta.setDesde(fechaDes);
            Date fechaHas = fechaHasta.getDate();
            fechaHas.setHours(23);
            fechaHas.setMinutes(59);
            fechaHas.setSeconds(59);
            tarjeta.setDescripcion(descripcionTarjeta.getText());
            tarjeta.setHasta(fechaHas);
            tarjeta.setDias((Integer) ingresos.getValue());
            tarjeta.setIngresos((Integer) ingresos.getValue());
            tarjeta.setDomingo(domingo.isSelected());
            tarjeta.setLunes(lunes.isSelected());
            tarjeta.setMartes(martes.isSelected());
            tarjeta.setMiercoles(miercoles.isSelected());
            tarjeta.setJueves(jueves.isSelected());
            tarjeta.setViernes(viernes.isSelected());
            tarjeta.setSabado(sabado.isSelected());
            tarjeta.setHorainicio((Date) horaDesde.getValue());
            tarjeta.setHorafin((Date) horaHasta.getValue());
            tarjeta.setHabilitada(activa.isSelected());
            tarjeta.setPlaca(placa1.getText());
            adm.actualizar(tarjeta);
            formaTarjetas.dispose();
            llenarTabla(usuarioObj.getCodigo());
            formaUsuarios.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al Guardar Tarjeta vuelva a intentarlo...!");
        }

    }//GEN-LAST:event_btnGuardarTarjetaActionPerformed

    private void btnSalirTarjetasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirTarjetasActionPerformed
        // TODO add your handling code here:
        formaTarjetas.dispose();
}//GEN-LAST:event_btnSalirTarjetasActionPerformed

    private void codigoBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoBuscarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {

            Thread cargar = new Thread()     {

                public void run() {
                    procesando.setVisible(true);


                    try {
                        List<Clientes> usuarios = adm.query("Select o from Clientes as o where o.nombres like '" + codigoBuscar.getText().trim() + "%' ");
                        Object[] obj = new Object[4];
                        DefaultTableModel dtm = (DefaultTableModel) busquedaTabla.getModel();
                        dtm.getDataVector().removeAllElements();
                        for (Iterator<Clientes> it = usuarios.iterator(); it.hasNext();) {
                            Clientes glbusuario = it.next();
                            obj[1] = glbusuario.getNombres();
                            obj[0] = glbusuario.getCodigo();
                            dtm.addRow(obj);
                        }
                        busquedaTabla.setModel(dtm);
                        if (busquedaTabla.getRowCount() > 0) {
                            busquedaTabla.requestFocusInWindow();
                        } else {
                            codigoBuscar.requestFocusInWindow();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    procesando.setVisible(false);
                }
            };
            cargar.start();

        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            formaUsuarios.dispose();
        }
    }//GEN-LAST:event_codigoBuscarKeyPressed

    private void busquedaTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_busquedaTablaMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            try {
                int fila = busquedaTabla.getSelectedRow();
                this.usuarioObj = (Clientes) adm.buscarClave((Integer) busquedaTabla.getValueAt(fila, 0), Clientes.class);
  llenarTabla(usuarioObj.getCodigo());

                formaUsuarios.dispose();
                 bindingGroup.unbind();
                bindingGroup.bind();
            } catch (Exception ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //        JOptionPane.showMessageDialog(this, usuarioObj);
}//GEN-LAST:event_busquedaTablaMouseClicked
public void llenarTabla(Integer clie){
    List<Tarjetas> tarjetasRs =   adm.query("Select o from Tarjetas as o where o.cliente.codigo = '"+clie+"'");

            DefaultTableModel dtm =  (DefaultTableModel) tarjetas.getModel();
            dtm.getDataVector().removeAllElements();

            for (Iterator<Tarjetas> it = tarjetasRs.iterator(); it.hasNext();) {
                    Tarjetas tarjetasIt = it.next();
                   Object[] obj = new Object[4];
                   obj[0] = tarjetasIt.getHabilitada();
                    obj[1] = tarjetasIt.getTarjeta();
                    obj[2] = tarjetasIt.getDesde();
                    obj[3] = tarjetasIt.getHasta();
                    dtm.addRow(obj);
               }
              tarjetas.setModel(dtm);
}

    private void busquedaTablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaTablaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            try {
                int fila = busquedaTabla.getSelectedRow();
                this.usuarioObj = (Clientes) adm.buscarClave((Integer) busquedaTabla.getValueAt(fila, 0), Clientes.class);
        llenarTabla(usuarioObj.getCodigo());
                formaUsuarios.dispose();
                 bindingGroup.unbind();
                bindingGroup.bind();
            } catch (Exception ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            formaUsuarios.dispose();
        }
}//GEN-LAST:event_busquedaTablaKeyPressed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        frmClientes.dispose();
}//GEN-LAST:event_btnSalirActionPerformed

    private void continuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continuarActionPerformed
        // TODO add your handling code here:
        escribir();
        GeneraXMLPersonal pXml = new GeneraXMLPersonal();
        pXml.leerXML();
        try {
            adm = new Administrador();
            adm.query("Select o from Accesos as o where o.codigo = -1 ");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Conexión Erronea corrija y vuelva a intentarlo...", "", JOptionPane.ERROR_MESSAGE);
            return;
        }
        frmRegistrar.dispose();
        btnAgregar.setEnabled(true);
        //          btnBuscar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnModificar.setEnabled(true);
        btnSalir.setEnabled(true);

    }//GEN-LAST:event_continuarActionPerformed

    private void puertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_puertoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_puertoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//        demos.SimpleWrite.llamar(puerto.getText());
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable()     {

            public void run() {
                new principal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox activa;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEmpresa;
    private javax.swing.JButton btnGuardarTarjeta;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevaTarjeta;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalirTarjetas;
    private javax.swing.JButton btnTicket;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JTable busquedaTabla;
    private javax.swing.JPasswordField clave;
    private javax.swing.JPasswordField claveBase;
    private javax.swing.JFormattedTextField cliente;
    private javax.swing.JComboBox cmbTipo;
    private javax.swing.JFormattedTextField codigo;
    private javax.swing.JFormattedTextField codigoBuscar;
    public javax.swing.JDesktopPane contenedor;
    private javax.swing.JButton continuar;
    private javax.swing.JTextArea descripcionTarjeta;
    private javax.swing.JPanel diasHabiles;
    private javax.swing.JFormattedTextField direccion;
    private javax.swing.JCheckBox domingo;
    private com.toedter.calendar.JDateChooser fechaDesde;
    private com.toedter.calendar.JDateChooser fechaHasta;
    private javax.swing.JDialog formaTarjetas;
    private javax.swing.JDialog formaUsuarios;
    private javax.swing.JDialog frmClientes;
    private javax.swing.JInternalFrame frmIngresarSistema;
    private javax.swing.JDialog frmRegistrar;
    private javax.swing.JSpinner horaDesde;
    private javax.swing.JSpinner horaHasta;
    private javax.swing.JSpinner ingresos;
    private javax.swing.JFormattedTextField ipBase;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton9;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JCheckBox jueves;
    private javax.swing.JCheckBox lunes;
    private javax.swing.JCheckBox martes;
    private javax.swing.JPanel miPanel;
    private javax.swing.JCheckBox miercoles;
    private javax.swing.JMenuItem mnAcerca;
    private javax.swing.JMenuItem mnCambioclave;
    private javax.swing.JMenuItem mnCerrar;
    private javax.swing.JMenuItem mnSalir;
    public javax.swing.JFormattedTextField noTarjeta;
    private javax.swing.JFormattedTextField nombres;
    private javax.swing.JPanel panelHoras;
    private javax.swing.JFormattedTextField placa;
    private javax.swing.JFormattedTextField placa1;
    public javax.swing.JButton procesando;
    private javax.swing.JTextField puerto;
    private javax.swing.JFormattedTextField puertoBase;
    private javax.swing.JCheckBox sabado;
    private javax.swing.JSpinner spConsumo;
    private javax.swing.JSpinner spIngreso;
    private javax.swing.JSpinner spSalida;
    private javax.swing.JButton tarifas;
    private javax.swing.JTable tarjetas;
    public javax.swing.JFormattedTextField tarjetatxt;
    private org.jdesktop.swingx.JXTaskPane taskTarjeta;
    private javax.swing.JFormattedTextField telefono;
    private javax.swing.JFormattedTextField usuarioBase;
    private javax.swing.JFormattedTextField usuariot;
    private javax.swing.JCheckBox viernes;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    public Accesos permisos;

    public Accesos getPermisos() {
        return permisos;
    }

    public void setPermisos(Accesos permisos) {
        this.permisos = permisos;
    }
    public Usuarios usuario;

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Clientes getUsuarioObj() {
        return usuarioObj;
    }

    public void setUsuarioObj(Clientes usuarioObj) {
        this.usuarioObj = usuarioObj;
    }

    public boolean getNuevaTarjeta() {
        return nuevaTarjeta;
    }

    public void setNuevaTarjeta(boolean nuevaTarjeta) {
        this.nuevaTarjeta = nuevaTarjeta;
    }
}
