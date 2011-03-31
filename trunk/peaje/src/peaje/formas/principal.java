package peaje.formas;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
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
import hibernate.cargar.UsuarioActivo;
import hibernate.cargar.WorkingDirectory;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.math.BigDecimal;
import javax.swing.ListModel;

/**
 *
 * @author geovanny
 */
public class principal extends javax.swing.JFrame implements KeyListener, WindowListener {

    Administrador adm;
//    Image image = Toolkit.getDefaultToolkit().getImage("C:\\Documents and Settings\\geovanny\\Escritorio\\JavaApplication3\\src\\javaapplication3\\tray.gif");
    public Tarjetas tarjeta;
    private Clientes clienteObj;
    public boolean grabar = false;
    public boolean modificar = false;
    public boolean nuevaTarjeta = false;
    public Usuarios usuarioActual;
    public static Empresa empresaObj;
    public static String in;
    public static String out;
    hibernate.cargar.claves cl = new hibernate.cargar.claves();
    String separador = File.separatorChar + "";

    public void habilitarBotones(Boolean estado) {
        btnClientes.setEnabled(estado);
        btnCobrar.setEnabled(estado);
        btnEmpresa.setEnabled(estado);
        btnTicket.setEnabled(estado);
        btnTarifas.setEnabled(estado);
        btnReportes.setEnabled(estado);
        btnUsuarios.setEnabled(estado);
        btnAccesos.setEnabled(estado);
        btnReconfigurar.setEnabled(estado);
        btnAcerca.setEnabled(estado);
        btnAyuda.setEnabled(estado);
        btnSalir2.setEnabled(estado);
        btnCerrar.setEnabled(estado);

    }
//public principal(){
//
//    }

    /** Creates new form principal */
    public principal() {
//        super("principal");
//        this.addKeyListener(this);
//        this.setVisible(true);
//        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                //JOptionPane.showMessageDialog(puertoBase, "mensaje");
                auditar("","", "Salir del Sistema");
                System.exit(0);

            }
        });

        Image im = new ImageIcon(getClass().getResource("/images_botones/icono.png")).getImage();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //WINDOWS
            initComponents();
            contenedor.requestFocus();
            RelojModeloUtil modelo = new RelojModeloUtil();
            RelojVisual visual = new RelojVisual(modelo);
//            visual.setLocation(100, 100);
            barraHerramients.add(visual,4);
            //getContentPane().add(visual);
            this.setSize(600, 600);
            this.setExtendedState(this.MAXIMIZED_BOTH);
            procesando.setVisible(false);
            /* CARGO EL MESSENGER */
            Image image = im;
            PopupMenu men = new PopupMenu("JCINFORMAMENU");
            MenuItem acerca = new MenuItem("Acerca de..");
            acerca.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    //System.out.println("In here");
                    //JOptionPane.showMessageDialog(getContentPane(), "JCINFORM ");
                    acerca ac = new acerca();
                    //ac.setModal(true);
                    ac.setLocation(250, 250);
                    contenedor.add(ac);
                    ac.show();

                }
            });
            men.add(acerca);
            acerca = new MenuItem("Salir ");
            acerca.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    //System.out.println("In here");
                    //JOptionPane.showMessageDialog(getContentPane(), "JCINFORM ");
                    System.exit(0);

                }
            });
            men.add(acerca);

            final TrayIcon trayIcon = new TrayIcon(image, "JCINFORM \n Soluciones Informaticas \n Sistema de Parking \n www.jcinform.com ", men);
            if (SystemTray.isSupported()) {
                SystemTray tray = SystemTray.getSystemTray();
                trayIcon.setImageAutoSize(true);
                trayIcon.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        //System.out.println("In here");
                        trayIcon.displayMessage("JCINFORM - SOLUCIONES INFORMATICAS \n Sistema de Parking \n ", "www.jcinform.com", TrayIcon.MessageType.INFO);
//                        frmLogin.show(true);
                    }
                });
                trayIcon.displayMessage("Bienvenidos al Sistema", " Sistema de Parking \n www.jcinform.com ", TrayIcon.MessageType.INFO);
                try {
                    tray.add(trayIcon);
                } catch (AWTException e) {
                    System.err.println("TrayIcon could not be added.");
                }
            }
            this.setIconImage(im);

            habilitarBotones(false);
            if (comprobar()) {
                logear();
            } else {
                frmRegistrar.setIconImage(new ImageIcon(getClass().getResource("/images_botones/icono.png")).getImage());
                frmRegistrar.setModal(true);
                frmRegistrar.setSize(474, 280);
                frmRegistrar.setLocation(350, 300);
                frmRegistrar.setFocusable(true);
                frmRegistrar.setResizable(false);
                frmRegistrar.setTitle("Configuración del Sistema");
                frmRegistrar.setUndecorated(false);
                frmRegistrar.show();
                logear();

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

        usuariot.requestFocusInWindow();

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

        buscarClientes = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        codigoBuscar = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
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
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        cmbAbre = new javax.swing.JComboBox();
        cmbCierra = new javax.swing.JComboBox();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        barraHerramients = new javax.swing.JToolBar();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        totales = new javax.swing.JLabel();
        disponibles = new javax.swing.JLabel();
        ocupados = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        barrera1 = new javax.swing.JButton();
        barrera2 = new javax.swing.JButton();
        barrera3 = new javax.swing.JButton();
        barrera4 = new javax.swing.JButton();
        barrera5 = new javax.swing.JButton();
        barrera6 = new javax.swing.JButton();
        barrera7 = new javax.swing.JButton();
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
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        procesando = new javax.swing.JButton();
        formaTarjetas1 = new javax.swing.JInternalFrame();
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
        todos = new javax.swing.JCheckBox();
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
        frmClientes1 = new javax.swing.JInternalFrame();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        nombres = new javax.swing.JFormattedTextField();
        direccion = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        codigo = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        telefono = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tarjetas = new javax.swing.JTable();
        btnNuevaTarjeta = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tarifas = new javax.swing.JList();
        txtValor = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        cliente = new javax.swing.JFormattedTextField();
        tarjetatxt = new javax.swing.JFormattedTextField();
        spIngreso = new javax.swing.JSpinner();
        ingre = new javax.swing.JLabel();
        spSalida = new javax.swing.JSpinner();
        salid = new javax.swing.JLabel();
        spConsumo = new javax.swing.JSpinner();
        cons = new javax.swing.JLabel();
        placa = new javax.swing.JFormattedTextField();
        errores = new javax.swing.JLabel();
        usuarioLogeado = new javax.swing.JButton();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        contenedor1 = new org.jdesktop.swingx.JXTaskPane();
        jToolBar1 = new javax.swing.JToolBar();
        btnClientes = new javax.swing.JButton();
        btnTicket = new javax.swing.JButton();
        btnCobrar = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        contenedor2 = new org.jdesktop.swingx.JXTaskPane();
        jToolBar2 = new javax.swing.JToolBar();
        btnUsuarios = new javax.swing.JButton();
        btnEmpresa = new javax.swing.JButton();
        btnTarifas = new javax.swing.JButton();
        btnAccesos = new javax.swing.JButton();
        btnReconfigurar = new javax.swing.JButton();
        contenedor3 = new org.jdesktop.swingx.JXTaskPane();
        jToolBar3 = new javax.swing.JToolBar();
        btnAyuda = new javax.swing.JButton();
        btnAcerca = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnSalir2 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();

        buscarClientes.setLocationByPlatform(true);
        buscarClientes.getContentPane().setLayout(null);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel8.setLayout(null);

        codigoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoBuscarKeyPressed(evt);
            }
        });
        jPanel8.add(codigoBuscar);
        codigoBuscar.setBounds(70, 10, 220, 20);

        jLabel23.setText("NOMBRES:");
        jPanel8.add(jLabel23);
        jLabel23.setBounds(10, 10, 70, 14);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enter.png"))); // NOI18N
        jLabel3.setText("Presione Enter");
        jPanel8.add(jLabel3);
        jLabel3.setBounds(300, 10, 110, 20);

        buscarClientes.getContentPane().add(jPanel8);
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
        busquedaTabla.getColumnModel().getColumn(0).setPreferredWidth(0);
        busquedaTabla.getColumnModel().getColumn(0).setMaxWidth(0);

        jPanel9.add(jScrollPane3);
        jScrollPane3.setBounds(20, 20, 480, 150);

        buscarClientes.getContentPane().add(jPanel9);
        jPanel9.setBounds(10, 60, 510, 180);

        frmRegistrar.setTitle("Configuración del Sistema");
        frmRegistrar.setModalityType(java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
        frmRegistrar.getContentPane().setLayout(null);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Usuario: ");
        frmRegistrar.getContentPane().add(jLabel24);
        jLabel24.setBounds(20, 20, 110, 14);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Clave: ");
        frmRegistrar.getContentPane().add(jLabel25);
        jLabel25.setBounds(20, 40, 110, 14);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("IP: ");
        frmRegistrar.getContentPane().add(jLabel26);
        jLabel26.setBounds(20, 60, 110, 14);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Puerta de Saldia: ");
        frmRegistrar.getContentPane().add(jLabel27);
        jLabel27.setBounds(20, 120, 110, 14);

        usuarioBase.setText("root");
        frmRegistrar.getContentPane().add(usuarioBase);
        usuarioBase.setBounds(140, 20, 160, 20);

        claveBase.setText("jcinform@2020");
        frmRegistrar.getContentPane().add(claveBase);
        claveBase.setBounds(140, 40, 160, 20);

        ipBase.setText("localhost");
        ipBase.setToolTipText("Si su máquina es el servidor escriba: localhost, caso contrario escriba el nombre del servidor o la dirección IP");
        frmRegistrar.getContentPane().add(ipBase);
        ipBase.setBounds(140, 60, 160, 20);

        puertoBase.setText("3306");
        frmRegistrar.getContentPane().add(puertoBase);
        puertoBase.setBounds(140, 80, 70, 20);

        continuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png"))); // NOI18N
        continuar.setText("Continuar");
        continuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continuarActionPerformed(evt);
            }
        });
        frmRegistrar.getContentPane().add(continuar);
        continuar.setBounds(140, 150, 260, 50);

        jLabel29.setText("(localhost o IP del servidor)");
        frmRegistrar.getContentPane().add(jLabel29);
        jLabel29.setBounds(310, 60, 200, 14);

        jLabel30.setText("(Clave del motor de BD)");
        frmRegistrar.getContentPane().add(jLabel30);
        jLabel30.setBounds(310, 30, 200, 30);

        jLabel31.setText("(Usuario de de BD)");
        frmRegistrar.getContentPane().add(jLabel31);
        jLabel31.setBounds(310, 20, 200, 14);

        cmbAbre.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7" }));
        frmRegistrar.getContentPane().add(cmbAbre);
        cmbAbre.setBounds(140, 100, 40, 20);

        cmbCierra.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7" }));
        frmRegistrar.getContentPane().add(cmbCierra);
        cmbCierra.setBounds(140, 120, 40, 20);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setText("Puerto: ");
        frmRegistrar.getContentPane().add(jLabel33);
        jLabel33.setBounds(20, 80, 110, 14);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel34.setText("Puerta de Entrada: ");
        frmRegistrar.getContentPane().add(jLabel34);
        jLabel34.setBounds(20, 100, 110, 14);

        jLabel35.setText("(Puerto por el que se conecta)");
        frmRegistrar.getContentPane().add(jLabel35);
        jLabel35.setBounds(220, 80, 200, 20);

        jLabel36.setText("(Puerta que se CIERRA desde este PC");
        frmRegistrar.getContentPane().add(jLabel36);
        jLabel36.setBounds(190, 120, 200, 20);

        jLabel37.setText("(Puerta que se ABRE desde este PC");
        frmRegistrar.getContentPane().add(jLabel37);
        jLabel37.setBounds(190, 100, 200, 20);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de control de parqueaderos");
        setName("miForma"); // NOI18N

        barraHerramients.setBorder(null);
        barraHerramients.setRollover(true);

        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramients.add(jButton4);

        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramients.add(jButton5);
        barraHerramients.add(jSeparator3);

        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramients.add(jButton3);

        jButton1.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramients.add(jButton1);

        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramients.add(jButton2);
        barraHerramients.add(jSeparator2);

        totales.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        totales.setForeground(new java.awt.Color(255, 102, 0));
        totales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/naranja.png"))); // NOI18N
        totales.setText("TOTAL: ");
        totales.setEnabled(false);
        totales.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        totales.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramients.add(totales);

        disponibles.setFont(new java.awt.Font("Tahoma", 1, 11));
        disponibles.setForeground(new java.awt.Color(0, 153, 102));
        disponibles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        disponibles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/verde.png"))); // NOI18N
        disponibles.setText("DISPONIBLES:  ");
        disponibles.setEnabled(false);
        disponibles.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        disponibles.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramients.add(disponibles);

        ocupados.setFont(new java.awt.Font("Tahoma", 1, 11));
        ocupados.setForeground(new java.awt.Color(255, 0, 0));
        ocupados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ocupados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rojo.png"))); // NOI18N
        ocupados.setText("OCUPADOS:");
        ocupados.setEnabled(false);
        ocupados.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ocupados.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramients.add(ocupados);
        barraHerramients.add(jSeparator1);

        barrera1.setFont(new java.awt.Font("Tahoma", 1, 11));
        barrera1.setForeground(new java.awt.Color(204, 102, 0));
        barrera1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto.png"))); // NOI18N
        barrera1.setText("F1");
        barrera1.setEnabled(false);
        barrera1.setFocusable(false);
        barrera1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        barrera1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto2.png"))); // NOI18N
        barrera1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto1.png"))); // NOI18N
        barrera1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barrera1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barrera1ActionPerformed(evt);
            }
        });
        barraHerramients.add(barrera1);

        barrera2.setFont(new java.awt.Font("Tahoma", 1, 11));
        barrera2.setForeground(new java.awt.Color(204, 102, 0));
        barrera2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto.png"))); // NOI18N
        barrera2.setText("F2");
        barrera2.setEnabled(false);
        barrera2.setFocusable(false);
        barrera2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        barrera2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto2.png"))); // NOI18N
        barrera2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto1.png"))); // NOI18N
        barrera2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barrera2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barrera2ActionPerformed(evt);
            }
        });
        barraHerramients.add(barrera2);

        barrera3.setFont(new java.awt.Font("Tahoma", 1, 11));
        barrera3.setForeground(new java.awt.Color(204, 102, 0));
        barrera3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto.png"))); // NOI18N
        barrera3.setText("F3");
        barrera3.setEnabled(false);
        barrera3.setFocusable(false);
        barrera3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        barrera3.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto2.png"))); // NOI18N
        barrera3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto1.png"))); // NOI18N
        barrera3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barrera3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barrera3ActionPerformed(evt);
            }
        });
        barraHerramients.add(barrera3);

        barrera4.setFont(new java.awt.Font("Tahoma", 1, 11));
        barrera4.setForeground(new java.awt.Color(204, 102, 0));
        barrera4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto.png"))); // NOI18N
        barrera4.setText("F4");
        barrera4.setEnabled(false);
        barrera4.setFocusable(false);
        barrera4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        barrera4.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto2.png"))); // NOI18N
        barrera4.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto1.png"))); // NOI18N
        barrera4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barrera4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barrera4ActionPerformed(evt);
            }
        });
        barraHerramients.add(barrera4);

        barrera5.setFont(new java.awt.Font("Tahoma", 1, 11));
        barrera5.setForeground(new java.awt.Color(204, 102, 0));
        barrera5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto.png"))); // NOI18N
        barrera5.setText("F5");
        barrera5.setEnabled(false);
        barrera5.setFocusable(false);
        barrera5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        barrera5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto2.png"))); // NOI18N
        barrera5.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto1.png"))); // NOI18N
        barrera5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barrera5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barrera5ActionPerformed(evt);
            }
        });
        barraHerramients.add(barrera5);

        barrera6.setFont(new java.awt.Font("Tahoma", 1, 11));
        barrera6.setForeground(new java.awt.Color(204, 102, 0));
        barrera6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto.png"))); // NOI18N
        barrera6.setText("F6");
        barrera6.setEnabled(false);
        barrera6.setFocusable(false);
        barrera6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        barrera6.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto2.png"))); // NOI18N
        barrera6.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto1.png"))); // NOI18N
        barrera6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barrera6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barrera6ActionPerformed(evt);
            }
        });
        barraHerramients.add(barrera6);

        barrera7.setFont(new java.awt.Font("Tahoma", 1, 11));
        barrera7.setForeground(new java.awt.Color(204, 102, 0));
        barrera7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto.png"))); // NOI18N
        barrera7.setText("F7");
        barrera7.setEnabled(false);
        barrera7.setFocusable(false);
        barrera7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        barrera7.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto2.png"))); // NOI18N
        barrera7.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remoto1.png"))); // NOI18N
        barrera7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barrera7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barrera7ActionPerformed(evt);
            }
        });
        barraHerramients.add(barrera7);

        getContentPane().add(barraHerramients, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setDividerLocation(240);
        jSplitPane1.setDividerSize(0);
        jSplitPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSplitPane1KeyPressed(evt);
            }
        });

        contenedor.setBackground(new java.awt.Color(240, 240, 240));
        contenedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contenedorMouseClicked(evt);
            }
        });
        contenedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                contenedorKeyPressed(evt);
            }
        });

        frmIngresarSistema.setTitle("Seguridad");
        frmIngresarSistema.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unlock.gif"))); // NOI18N
        frmIngresarSistema.setOpaque(true);
        try {
            frmIngresarSistema.setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        frmIngresarSistema.getContentPane().setLayout(null);

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 153, 51), 1, true));
        jPanel2.setLayout(null);

        usuariot.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 153, 0)));
        usuariot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usuariot.setFont(new java.awt.Font("Tahoma", 0, 14));
        usuariot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuariotActionPerformed(evt);
            }
        });
        usuariot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usuariotKeyPressed(evt);
            }
        });
        jPanel2.add(usuariot);
        usuariot.setBounds(140, 20, 110, 19);

        clave.setFont(new java.awt.Font("Tahoma", 0, 14));
        clave.setHorizontalAlignment(javax.swing.JTextField.CENTER);
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
        clave.setBounds(140, 50, 110, 19);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Usuario:");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(50, 20, 70, 17);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Clave:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(60, 50, 60, 17);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.gif"))); // NOI18N
        jButton9.setText("Sallir");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton9);
        jButton9.setBounds(200, 90, 110, 40);

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
        btnIngresar.setBounds(70, 90, 120, 40);

        frmIngresarSistema.getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 40, 380, 150);

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
        procesando.setBounds(280, 0, 110, 40);

        frmIngresarSistema.getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 380, 40);

        frmIngresarSistema.setBounds(170, 160, 390, 220);
        contenedor.add(frmIngresarSistema, javax.swing.JLayeredPane.DEFAULT_LAYER);

        formaTarjetas1.setTitle("Registro y Modificación de Tarjetas");
        formaTarjetas1.getContentPane().setLayout(null);

        jLabel14.setText("No. Tarjeta: ");
        formaTarjetas1.getContentPane().add(jLabel14);
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

        formaTarjetas1.getContentPane().add(panelHoras);
        panelHoras.setBounds(30, 100, 160, 70);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Horas de ingreso"));
        jPanel7.setLayout(null);

        jLabel18.setText("Hasta: ");
        jPanel7.add(jLabel18);
        jLabel18.setBounds(10, 40, 40, 14);

        jLabel19.setText("Desde:");
        jPanel7.add(jLabel19);
        jLabel19.setBounds(10, 20, 50, 14);

        horaDesde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                horaDesdeKeyPressed(evt);
            }
        });
        jPanel7.add(horaDesde);
        horaDesde.setBounds(50, 20, 80, 20);

        horaHasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                horaHastaKeyPressed(evt);
            }
        });
        jPanel7.add(horaHasta);
        horaHasta.setBounds(50, 40, 80, 20);

        formaTarjetas1.getContentPane().add(jPanel7);
        jPanel7.setBounds(200, 100, 150, 70);

        diasHabiles.setBorder(javax.swing.BorderFactory.createTitledBorder("Días Habiles"));
        diasHabiles.setLayout(null);

        lunes.setText("Lunes");
        lunes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lunesActionPerformed(evt);
            }
        });
        lunes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lunesKeyPressed(evt);
            }
        });
        diasHabiles.add(lunes);
        lunes.setBounds(30, 70, 80, 23);

        martes.setText("Martes");
        martes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                martesKeyPressed(evt);
            }
        });
        diasHabiles.add(martes);
        martes.setBounds(120, 10, 80, 23);

        miercoles.setText("Miércoles");
        miercoles.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                miercolesKeyPressed(evt);
            }
        });
        diasHabiles.add(miercoles);
        miercoles.setBounds(120, 40, 80, 23);

        jueves.setText("Jueves");
        jueves.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                juevesActionPerformed(evt);
            }
        });
        jueves.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                juevesKeyPressed(evt);
            }
        });
        diasHabiles.add(jueves);
        jueves.setBounds(120, 70, 80, 23);

        viernes.setText("Viernes");
        viernes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                viernesKeyPressed(evt);
            }
        });
        diasHabiles.add(viernes);
        viernes.setBounds(230, 10, 80, 23);

        sabado.setText("Sábado");
        sabado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sabadoKeyPressed(evt);
            }
        });
        diasHabiles.add(sabado);
        sabado.setBounds(230, 40, 80, 23);

        domingo.setText("Domingo");
        domingo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                domingoKeyPressed(evt);
            }
        });
        diasHabiles.add(domingo);
        domingo.setBounds(230, 70, 80, 23);

        todos.setText("Todos");
        todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todosActionPerformed(evt);
            }
        });
        todos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                todosKeyPressed(evt);
            }
        });
        diasHabiles.add(todos);
        todos.setBounds(30, 40, 55, 23);

        formaTarjetas1.getContentPane().add(diasHabiles);
        diasHabiles.setBounds(30, 170, 320, 110);

        noTarjeta.setEditable(false);
        noTarjeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                noTarjetaKeyPressed(evt);
            }
        });
        formaTarjetas1.getContentPane().add(noTarjeta);
        noTarjeta.setBounds(90, 10, 110, 20);

        ingresos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ingresosKeyPressed(evt);
            }
        });
        formaTarjetas1.getContentPane().add(ingresos);
        ingresos.setBounds(290, 30, 40, 20);

        jLabel20.setText("No. Ingresos: ");
        formaTarjetas1.getContentPane().add(jLabel20);
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
        formaTarjetas1.getContentPane().add(btnGuardarTarjeta);
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
        formaTarjetas1.getContentPane().add(btnSalirTarjetas);
        btnSalirTarjetas.setBounds(290, 290, 60, 50);

        activa.setText("Tarjeta Activa:   ");
        activa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        activa.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        activa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                activaKeyPressed(evt);
            }
        });
        formaTarjetas1.getContentPane().add(activa);
        activa.setBounds(210, 10, 110, 23);

        placa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                placa1KeyPressed(evt);
            }
        });
        formaTarjetas1.getContentPane().add(placa1);
        placa1.setBounds(90, 30, 110, 20);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Placa:");
        formaTarjetas1.getContentPane().add(jLabel21);
        jLabel21.setBounds(20, 30, 60, 14);

        jLabel22.setText("Descripción:");
        formaTarjetas1.getContentPane().add(jLabel22);
        jLabel22.setBounds(20, 50, 70, 14);

        descripcionTarjeta.setColumns(20);
        descripcionTarjeta.setRows(5);
        descripcionTarjeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                descripcionTarjetaKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(descripcionTarjeta);

        formaTarjetas1.getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(90, 50, 240, 40);

        formaTarjetas1.setBounds(80, 0, 380, 380);
        contenedor.add(formaTarjetas1, javax.swing.JLayeredPane.MODAL_LAYER);

        frmClientes1.setTitle("Registro y Modifación de Clientes");
        frmClientes1.getContentPane().setLayout(null);

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

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Dirección:");
        jPanel4.add(jLabel5);
        jLabel5.setBounds(10, 50, 60, 14);

        telefono.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.telefono}"), telefono, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefonoKeyPressed(evt);
            }
        });
        jPanel4.add(telefono);
        telefono.setBounds(80, 70, 160, 20);

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
        tarjetas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tarjetasKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tarjetas);

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(10, 180, 380, 90);

        btnNuevaTarjeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/File3.gif"))); // NOI18N
        btnNuevaTarjeta.setText("Añadir Nueva Tarjeta");
        btnNuevaTarjeta.setEnabled(false);
        btnNuevaTarjeta.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btnNuevaTarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaTarjetaActionPerformed(evt);
            }
        });
        btnNuevaTarjeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNuevaTarjetaKeyPressed(evt);
            }
        });
        jPanel4.add(btnNuevaTarjeta);
        btnNuevaTarjeta.setBounds(70, 150, 170, 30);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Teléfono:");
        jPanel4.add(jLabel15);
        jLabel15.setBounds(10, 70, 60, 14);

        jLabel7.setForeground(new java.awt.Color(0, 0, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Valor Inc IVA:");
        jPanel4.add(jLabel7);
        jLabel7.setBounds(300, 100, 70, 14);

        jLabel9.setForeground(new java.awt.Color(0, 51, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Tarifa: ");
        jPanel4.add(jLabel9);
        jLabel9.setBounds(20, 90, 50, 14);

        tarifas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                tarifasValueChanged(evt);
            }
        });
        tarifas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tarifasKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tarifas);

        jPanel4.add(jScrollPane4);
        jScrollPane4.setBounds(80, 90, 220, 50);

        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValorKeyPressed(evt);
            }
        });
        jPanel4.add(txtValor);
        txtValor.setBounds(310, 120, 50, 20);

        frmClientes1.getContentPane().add(jPanel4);
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
        btnSalir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSalirKeyPressed(evt);
            }
        });
        jPanel5.add(btnSalir);
        btnSalir.setBounds(290, 10, 60, 50);

        frmClientes1.getContentPane().add(jPanel5);
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

        frmClientes1.getContentPane().add(jPanel6);
        jPanel6.setBounds(0, 0, 430, 40);

        frmClientes1.setBounds(40, 20, 440, 450);
        contenedor.add(frmClientes1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Último Ingreso", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 204))); // NOI18N
        jPanel10.setLayout(null);

        cliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cliente.setEditable(false);
        cliente.setForeground(new java.awt.Color(51, 51, 255));
        cliente.setText(".");
        cliente.setFont(new java.awt.Font("Tahoma", 0, 12));
        jPanel10.add(cliente);
        cliente.setBounds(20, 20, 180, 20);

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
        jPanel10.add(tarjetatxt);
        tarjetatxt.setBounds(20, 50, 190, 20);

        spIngreso.setFont(new java.awt.Font("Tahoma", 0, 14));
        spIngreso.setEnabled(false);
        spIngreso.setFocusable(false);
        jPanel10.add(spIngreso);
        spIngreso.setBounds(290, 20, 90, 20);

        ingre.setFont(new java.awt.Font("Tahoma", 1, 11));
        ingre.setForeground(new java.awt.Color(153, 153, 153));
        ingre.setText("INGRESO:");
        jPanel10.add(ingre);
        ingre.setBounds(230, 20, 60, 20);

        spSalida.setFont(new java.awt.Font("Tahoma", 0, 14));
        spSalida.setEnabled(false);
        spSalida.setFocusable(false);
        jPanel10.add(spSalida);
        spSalida.setBounds(290, 40, 90, 20);

        salid.setFont(new java.awt.Font("Tahoma", 1, 11));
        salid.setForeground(new java.awt.Color(153, 153, 153));
        salid.setText("SALIDA:");
        jPanel10.add(salid);
        salid.setBounds(230, 40, 60, 20);

        spConsumo.setFont(new java.awt.Font("Tahoma", 0, 14));
        spConsumo.setEnabled(false);
        spConsumo.setFocusable(false);
        jPanel10.add(spConsumo);
        spConsumo.setBounds(290, 60, 90, 20);

        cons.setFont(new java.awt.Font("Tahoma", 1, 11));
        cons.setForeground(new java.awt.Color(153, 153, 153));
        cons.setText("CONSUMO:");
        jPanel10.add(cons);
        cons.setBounds(230, 60, 60, 20);

        placa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        placa.setEditable(false);
        placa.setText(".");
        jPanel10.add(placa);
        placa.setBounds(20, 70, 180, 20);

        errores.setFont(new java.awt.Font("Tahoma", 1, 12));
        errores.setForeground(new java.awt.Color(255, 0, 0));
        errores.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel10.add(errores);
        errores.setBounds(20, 100, 360, 20);

        jPanel10.setBounds(0, 40, 430, 130);
        contenedor.add(jPanel10, javax.swing.JLayeredPane.DEFAULT_LAYER);

        usuarioLogeado.setFont(new java.awt.Font("Tahoma", 1, 11));
        usuarioLogeado.setForeground(new java.awt.Color(0, 102, 153));
        usuarioLogeado.setText("...");
        usuarioLogeado.setBorderPainted(false);
        usuarioLogeado.setContentAreaFilled(false);
        usuarioLogeado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        usuarioLogeado.setBounds(10, 10, 420, 23);
        contenedor.add(usuarioLogeado, javax.swing.JLayeredPane.DEFAULT_LAYER);

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

        contenedor1.setTitle("Control");

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(1);
        jToolBar1.setRollover(true);

        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/usr.png"))); // NOI18N
        btnClientes.setMnemonic('C');
        btnClientes.setText("Clientes (F8)");
        btnClientes.setToolTipText("Crear nuevos clientes y asignar tarjetas");
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
        btnTicket.setText("Ticket (F9)");
        btnTicket.setToolTipText("Registrar un ingreso de vehículo");
        btnTicket.setFocusable(false);
        btnTicket.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTicket.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTicketActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTicket);

        btnCobrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dinero.gif"))); // NOI18N
        btnCobrar.setMnemonic('B');
        btnCobrar.setText("Cobrar (F10)");
        btnCobrar.setToolTipText("Cobrar ticket");
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

        contenedor1.getContentPane().add(jToolBar1);

        contenedor2.setTitle("Administracion");

        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(1);
        jToolBar2.setRollover(true);

        btnUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/User3.gif"))); // NOI18N
        btnUsuarios.setMnemonic('O');
        btnUsuarios.setText("Operadores");
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

        btnEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/empresa.png"))); // NOI18N
        btnEmpresa.setMnemonic('E');
        btnEmpresa.setText("Datos Empresa");
        btnEmpresa.setFocusable(false);
        btnEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEmpresa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpresaActionPerformed(evt);
            }
        });
        jToolBar2.add(btnEmpresa);

        btnTarifas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dinero.gif"))); // NOI18N
        btnTarifas.setMnemonic('R');
        btnTarifas.setText("Tarifas");
        btnTarifas.setFocusable(false);
        btnTarifas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTarifas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTarifas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTarifasActionPerformed(evt);
            }
        });
        jToolBar2.add(btnTarifas);

        btnAccesos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lock.gif"))); // NOI18N
        btnAccesos.setMnemonic('S');
        btnAccesos.setText("Privilegios");
        btnAccesos.setFocusable(false);
        btnAccesos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAccesos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAccesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccesosActionPerformed(evt);
            }
        });
        jToolBar2.add(btnAccesos);

        btnReconfigurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/configure.gif"))); // NOI18N
        btnReconfigurar.setMnemonic('S');
        btnReconfigurar.setText("Reconfigurar Sistema");
        btnReconfigurar.setFocusable(false);
        btnReconfigurar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnReconfigurar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReconfigurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReconfigurarActionPerformed(evt);
            }
        });
        jToolBar2.add(btnReconfigurar);

        contenedor2.getContentPane().add(jToolBar2);

        contenedor3.setTitle("Ayuda");

        jToolBar3.setFloatable(false);
        jToolBar3.setOrientation(1);
        jToolBar3.setRollover(true);

        btnAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/help.gif"))); // NOI18N
        btnAyuda.setMnemonic('E');
        btnAyuda.setText("Manual");
        btnAyuda.setFocusable(false);
        btnAyuda.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAyuda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAyudaActionPerformed(evt);
            }
        });
        jToolBar3.add(btnAyuda);

        btnAcerca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/info.gif"))); // NOI18N
        btnAcerca.setMnemonic('O');
        btnAcerca.setText("Acerca de");
        btnAcerca.setFocusable(false);
        btnAcerca.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnAcerca.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAcerca.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAcerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcercaActionPerformed(evt);
            }
        });
        jToolBar3.add(btnAcerca);

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/undo.gif"))); // NOI18N
        btnCerrar.setMnemonic('S');
        btnCerrar.setText("Cerrar Sesión");
        btnCerrar.setFocusable(false);
        btnCerrar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCerrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jToolBar3.add(btnCerrar);

        btnSalir2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.gif"))); // NOI18N
        btnSalir2.setMnemonic('X');
        btnSalir2.setText("Salir");
        btnSalir2.setFocusable(false);
        btnSalir2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSalir2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir2ActionPerformed(evt);
            }
        });
        jToolBar3.add(btnSalir2);

        contenedor3.getContentPane().add(jToolBar3);

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images_botones/ico.png"))); // NOI18N
        jLabel32.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contenedor1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(contenedor2, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(contenedor3, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
                .addContainerGap())
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenedor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contenedor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contenedor3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel32)
                .addContainerGap(140, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jXTaskPaneContainer1);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void iniciarPuertos() {
        try {
            WorkingDirectory w = new WorkingDirectory();
            String ubicacionDirectorio = w.get() + separador;
            if (ubicacionDirectorio.contains("build")) {
                ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
            }
            String temp_string = ubicacionDirectorio + "lib" + separador + "javax.comm.properties";
            Method loadDriver_Method = CommPortIdentifier.class.getDeclaredMethod("loadDriver", new Class[]{String.class});
            loadDriver_Method.setAccessible(true);
            loadDriver_Method.invoke("loadDriver", new Object[]{temp_string});
            CommPortIdentifier portId;
            Enumeration portList = CommPortIdentifier.getPortIdentifiers();
            LeerTarjeta reader;
//            ArrayList read = new ArrayList();
            while (portList.hasMoreElements()) {
                portId = (CommPortIdentifier) portList.nextElement();
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
//                    if (portId.getName().equals(empresaObj.getPuerto())) {
//                        reader = new LeerTarjeta(portId, this);
////                        read.add(reader);
//                        System.out.println("ABIERTO: "+empresaObj.getPuerto());
//                    }
                    if (portId.getName().equals(empresaObj.getPuerto1()) && empresaObj.getActiva1()) {
                        reader = new LeerTarjeta(portId, this);
                        System.out.println("ABIERTO: " + empresaObj.getPuerto1());
//                        read.add(reader);
                    }
                    if (portId.getName().equals(empresaObj.getPuerto2()) && empresaObj.getActiva2()) {
                        reader = new LeerTarjeta(portId, this);
                        System.out.println("ABIERTO: " + empresaObj.getPuerto2());
//                        read.add(reader);
                    }
                    if (portId.getName().equals(empresaObj.getPuerto3()) && empresaObj.getActiva3()) {
                        reader = new LeerTarjeta(portId, this);
                        System.out.println("ABIERTO: " + empresaObj.getPuerto3());
//                        read.add(reader);
                    }
                    if (portId.getName().equals(empresaObj.getPuerto4()) && empresaObj.getActiva4()) {
                        reader = new LeerTarjeta(portId, this);
                        System.out.println("ABIERTO: " + empresaObj.getPuerto4());
//                        read.add(reader);
                    }
                    if (portId.getName().equals(empresaObj.getPuerto5()) && empresaObj.getActiva5()) {
                        reader = new LeerTarjeta(portId, this);
                        System.out.println("ABIERTO: " + empresaObj.getPuerto5());
//                        read.add(reader);
                    }
                    if (portId.getName().equals(empresaObj.getPuerto6()) && empresaObj.getActiva6()) {
                        reader = new LeerTarjeta(portId, this);
                        System.out.println("ABIERTO: " + empresaObj.getPuerto6());
//                        read.add(reader);
                    }
                    if (portId.getName().equals(empresaObj.getPuerto7()) && empresaObj.getActiva7()) {
                        reader = new LeerTarjeta(portId, this);
                        System.out.println("ABIERTO: " + empresaObj.getPuerto7());
//                        read.add(reader);
                    }

                    if (portId.getName().equals(empresaObj.getSalida1()) && empresaObj.getActiva1()) {
                        reader = new LeerTarjeta(portId, this);
                        System.out.println("ABIERTO: " + empresaObj.getSalida1());
//                        read.add(reader);
                    }
                    if (portId.getName().equals(empresaObj.getSalida2()) && empresaObj.getActiva2()) {
                        reader = new LeerTarjeta(portId, this);
                        System.out.println("ABIERTO: " + empresaObj.getSalida2());
//                        read.add(reader);
                    }
                    if (portId.getName().equals(empresaObj.getSalida3()) && empresaObj.getActiva3()) {
                        reader = new LeerTarjeta(portId, this);
                        System.out.println("ABIERTO: " + empresaObj.getSalida3());
//                        read.add(reader);
                    }
                    if (portId.getName().equals(empresaObj.getSalida4()) && empresaObj.getActiva4()) {
                        reader = new LeerTarjeta(portId, this);
                        System.out.println("ABIERTO: " + empresaObj.getSalida4());
//                        read.add(reader);
                    }
                    if (portId.getName().equals(empresaObj.getSalida5()) && empresaObj.getActiva5()) {
                        reader = new LeerTarjeta(portId, this);
                        System.out.println("ABIERTO: " + empresaObj.getSalida5());
//                        read.add(reader);
                    }
                    if (portId.getName().equals(empresaObj.getSalida6()) && empresaObj.getActiva6()) {
                        reader = new LeerTarjeta(portId, this);
                        System.out.println("ABIERTO: " + empresaObj.getSalida6());
//                        read.add(reader);
                    }
                    if (portId.getName().equals(empresaObj.getSalida7()) && empresaObj.getActiva7()) {
                        reader = new LeerTarjeta(portId, this);
                        System.out.println("ABIERTO: " + empresaObj.getSalida7());
//                        read.add(reader);
                    }
//                        else if (portId.getName().equals("COM10")) {
//                            reader = new LeerTarjeta(portId, this);
//                            read.add(reader);
//                        }
                }
            }
            portList = null;
        } catch (Exception ex) {
            Logger.getLogger(LeerTarjeta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logear() {
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
        beanEmpleado.setIn("" + cmbAbre.getSelectedItem().toString());
        beanEmpleado.setOut("" + cmbCierra.getSelectedItem().toString());


        //Generamos documento XML para los valores anteriores
        pXml.llenarEstructuraDocumentoXMLEmpleado(beanEmpleado);
        //obtenemos el documento XML en cadena de texto
        String textoXML = pXml.obtenerTextoXML();
        //grabamos en archivo el documento XML
        pXml.grabaDocumentoXML(textoXML);

    }

    public void llenarCombo() {
        try {
            List listado = adm.query("Select o from Productos as o ");
            Object[] listData = new Object[listado.size()];
            int i = 0;
            for (Iterator<Productos> it = listado.iterator(); it.hasNext();) {
                Productos global = it.next();
                listData[i] = global;
                i++;
            }
            tarifas.setListData(listData);
        } catch (Exception ex) {
            Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void noDisponibles() {
        totales.setText("Total: " + empresaObj.getParqueaderos());
        Object con = adm.querySimple("Select count(o) from Factura as o"
                + " where  o.fechafin is null  ");
        Long val2 = (Long) con;
        disponibles.setText("Disponibles: " + (empresaObj.getParqueaderos() - val2.intValue()));
        ocupados.setText("Ocupados: " + val2.intValue());

    }

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
            usuarioLogeado.setText("Usuario: "+usuarioActual.getNombres());
            List<Empresa> emp = adm.listar("Select o from Empresa as o ");

            this.empresaObj = emp.get(0);
            noDisponibles();
            disponibles.setEnabled(true);
            ocupados.setEnabled(true);
            totales.setEnabled(true);
            barrera1.setEnabled(false);
            barrera2.setEnabled(false);
            barrera3.setEnabled(false);
            barrera4.setEnabled(false);
            barrera5.setEnabled(false);
            barrera6.setEnabled(false);
            barrera7.setEnabled(false);

            if (empresaObj.getActiva1()) {
                barrera1.setEnabled(true);
            }
            if (empresaObj.getActiva2()) {
                barrera2.setEnabled(true);
            }
            if (empresaObj.getActiva3()) {
                barrera3.setEnabled(true);
            }
            if (empresaObj.getActiva4()) {
                barrera4.setEnabled(true);
            }
            if (empresaObj.getActiva5()) {
                barrera5.setEnabled(true);
            }
            if (empresaObj.getActiva6()) {
                barrera6.setEnabled(true);
            }
            if (empresaObj.getActiva7()) {
                barrera7.setEnabled(true);
            }
            in = UsuarioActivo.getIn();
            out = UsuarioActivo.getOut();
            habilitarBotones(true);
            List<Accesos> accesosL = adm.query("Select o from Accesos as o "
                    + "where o.perfil.codigo  = '" + usuarioActual.getPerfil().getCodigo() + "' ");
            for (Iterator<Accesos> it = accesosL.iterator(); it.hasNext();) {
                Accesos accesos = it.next();
                if (accesos.getPantalla().equals("Clientes") && !accesos.getIngresar()) {
                    btnClientes.setEnabled(false);
                }
                if (accesos.getPantalla().equals("Tickets") && !accesos.getIngresar()) {
                    btnTicket.setEnabled(false);
                }
                if (accesos.getPantalla().equals("Tarifas") && !accesos.getIngresar()) {
                    btnTarifas.setEnabled(false);
                }
                if (accesos.getPantalla().equals("Factura") && !accesos.getIngresar()) {
                    btnCobrar.setEnabled(false);
                }
                if (accesos.getPantalla().equals("Reportes") && !accesos.getIngresar()) {
                    btnReportes.setEnabled(false);
                }
                if (accesos.getPantalla().equals("Operadores") && !accesos.getIngresar()) {
                    btnUsuarios.setEnabled(false);
                }
                if (accesos.getPantalla().equals("Empresa") && !accesos.getIngresar()) {
                    btnEmpresa.setEnabled(false);
                }
                if (accesos.getPantalla().equals("Accesos") && !accesos.getIngresar()) {
                    btnAccesos.setEnabled(false);
                }
                if (accesos.getPantalla().equals("Reconfigurar") && !accesos.getIngresar()) {

                    btnReconfigurar.setEnabled(false);
                }


            }

            iniciarPuertos();
            contenedor.requestFocus();
            auditar("", "","Ingreso al Sistema");
        } else {
            clave.setEditable(true);
            usuariot.setEditable(true);
            usuariot.setText("");
            clave.setText("");
            JOptionPane.showMessageDialog(this, "Usuario o Clave incorrecta", "JCINFORM", JOptionPane.ERROR_MESSAGE);
            usuariot.requestFocusInWindow();


        }
    }

    public void buscarTarjeta(String puertoViene) {
        final principal pra = this;
        if (puertoViene.length() > 10) {
            puertoViene = puertoViene.substring(0, 10);
        }
        try {

            try {
                String noTarjeta001 = tarjetatxt.getText();
                if (noTarjeta001.length() > 10) {
                    noTarjeta001 = noTarjeta001.substring(0, 10);
                }
                Tarjetas tarje = (Tarjetas) adm.buscarClave(noTarjeta001, Tarjetas.class);
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
                    errores.setText("");
                } else {
                    errores.setText("OK");
                    String tipoIngreso = entradaosalida(puertoViene);
                    List<Registro> siHay = adm.query("Select o from Registro as o where o.tarjeta = '" + tarje.getTarjeta() + "' and o.fechafin is null ");
                    if (tipoIngreso.equals("e")) {

                        if (siHay.size() > 0) {
                            errores.setText("OTRO VEHÍCULO YA HA INGRESADO CON ESA TARJETA...!");
                            return;
                        } else {
                            Registro reg = new Registro();
                            reg.setFechaini(new Date());
                            reg.setTarjeta(tarje.getTarjeta());
                            adm.guardar(reg);
                            errores.setText("ENTRADA OK...!");
                            //errores.setText("OTRO VEHÍCULO YA HA INGRESADO CON ESA TARJETA...!");
                        }
                    } else {

                        if (siHay.size() > 0) {
                            Registro reg = siHay.get(0);
                            reg.setFechafin(new Date());
                            adm.actualizar(reg);
                            errores.setText("SALIDA OK...!");
                        } else {
                            Registro reg = new Registro();
                            reg.setFechaini(new Date()); //ENTRTA Y SALE, EN CASO DE NO REGISTRAR
                            reg.setFechafin(new Date());// LA ENTRADA SE CREA UN REGISTRO DE ENTRADA Y SALIDA
                            reg.setTarjeta(tarje.getTarjeta());
                            adm.guardar(reg);
                            errores.setText("OK...!");
                        }

                    }


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
                                //JOptionPane.showMessageDialog(getContentPane(), "Día NO hábil para ingresar ...! \n Cliente: " + tarje.getCliente().getNombres(), "JCINFORM ", JOptionPane.ERROR_MESSAGE);
                                errores.setText("NO PUEDE INGRESAR EN ÉSTE DÌA");
                                return;
                            }


                            Date fecIn = tarje.getHorainicio();
                            Date fecIn3 = tarje.getHorafin();
                            LocalTime horaIni = new LocalTime(new DateTime(fecIn));
                            LocalTime horaFin = new LocalTime(new DateTime(fecIn3));
                            LocalTime ahora = new LocalTime(new DateTime(new Date()));
                            if ((ahora.compareTo(horaIni) > 0 || ahora.compareTo(horaIni) == 0) && (ahora.compareTo(horaFin) < 0 || ahora.compareTo(horaFin) == 0)) {
                                System.out.println("EN EL RANGO DE HORA");
                                try {

                                    abrirPuerta(puertoViene);

                                } catch (Exception e) {
                                    System.out.println("PUERTO:" + puertoViene);
                                    System.out.println("ERROR AL ABRIR PUERTA: " + e);
                                }

                            } else {
                                //JOptionPane.showMessageDialog(getContentPane(), "No puede ingresar en este Horario...! \n Cliente: " + tarje.getCliente().getNombres(), "JCINFORM ", JOptionPane.ERROR_MESSAGE);
                                errores.setText("NO PUEDE INGRESAR EN ESTE HORARIO ");
                                return;
                            }

                        } else {
                            //JOptionPane.showMessageDialog(getContentPane(), "Su Fecha de tarjeta expiró...! \n Cliente: ", "JCINFORM ", JOptionPane.ERROR_MESSAGE);
                            errores.setText("TARJETA EXPIRADA");
                            return;
                        }


//                                System.out.println(""+desde);
//                                System.out.println(""+hasta);
//                                System.out.println(""+d.getDays());


//                        taskTarjeta.setCollapsed(false);
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
                        //JOptionPane.showMessageDialog(getContentPane(), "Tarjeta INHABILITADA ...! \n Cliente: " + tarje.getCliente().getNombres(), "JCINFORM ", JOptionPane.ERROR_MESSAGE);
                        errores.setText("TARJETA DESHABILITADA");
                    }

                    noDisponibles();
                }
            } catch (Exception ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            }

            procesando.setVisible(false);

//            taskTarjeta.setCollapsed(true);
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String entradaosalida(String puertoqueViene) {
        String lapuertaaAbrir = "";
        if (puertoqueViene.equals(empresaObj.getPuerto1())) {
            lapuertaaAbrir = "e";
        } else if (puertoqueViene.equals(empresaObj.getPuerto2())) {
            lapuertaaAbrir = "e";
        } else if (puertoqueViene.equals(empresaObj.getPuerto3())) {
            lapuertaaAbrir = "e";
        } else if (puertoqueViene.equals(empresaObj.getPuerto4())) {
            lapuertaaAbrir = "e";
        } else if (puertoqueViene.equals(empresaObj.getPuerto5())) {
            lapuertaaAbrir = "e";
        } else if (puertoqueViene.equals(empresaObj.getPuerto6())) {
            lapuertaaAbrir = "e";
        } else if (puertoqueViene.equals(empresaObj.getPuerto7())) {
            lapuertaaAbrir = "e";
        } else if (puertoqueViene.equals(empresaObj.getSalida1())) {
            lapuertaaAbrir = "s";
        } else if (puertoqueViene.equals(empresaObj.getSalida2())) {
            lapuertaaAbrir = "s";
        } else if (puertoqueViene.equals(empresaObj.getSalida3())) {
            lapuertaaAbrir = "s";
        } else if (puertoqueViene.equals(empresaObj.getSalida4())) {
            lapuertaaAbrir = "s";
        } else if (puertoqueViene.equals(empresaObj.getSalida5())) {
            lapuertaaAbrir = "s";
        } else if (puertoqueViene.equals(empresaObj.getSalida6())) {
            lapuertaaAbrir = "s";
        } else if (puertoqueViene.equals(empresaObj.getSalida7())) {
            lapuertaaAbrir = "s";
        }
        return lapuertaaAbrir;

//        LeerTarjeta le = new LeerTarjeta();
//                le.abrir(empresaObj.getPuerto(), lapuertaaAbrir);
    }

    void abrirPuerta(String puertoqueViene) {
        String lapuertaaAbrir = "";
        if (puertoqueViene.equals(empresaObj.getPuerto1())) {
            lapuertaaAbrir = empresaObj.getPuerta1();
        } else if (puertoqueViene.equals(empresaObj.getPuerto2())) {
            lapuertaaAbrir = empresaObj.getPuerta2();
        } else if (puertoqueViene.equals(empresaObj.getPuerto3())) {
            lapuertaaAbrir = empresaObj.getPuerta3();
        } else if (puertoqueViene.equals(empresaObj.getPuerto4())) {
            lapuertaaAbrir = empresaObj.getPuerta4();
        } else if (puertoqueViene.equals(empresaObj.getPuerto5())) {
            lapuertaaAbrir = empresaObj.getPuerta5();
        } else if (puertoqueViene.equals(empresaObj.getPuerto6())) {
            lapuertaaAbrir = empresaObj.getPuerta6();
        } else if (puertoqueViene.equals(empresaObj.getPuerto7())) {
            lapuertaaAbrir = empresaObj.getPuerta7();
        } else if (puertoqueViene.equals(empresaObj.getSalida1())) {
            lapuertaaAbrir = empresaObj.getPuerta1();
        } else if (puertoqueViene.equals(empresaObj.getSalida2())) {
            lapuertaaAbrir = empresaObj.getPuerta2();
        } else if (puertoqueViene.equals(empresaObj.getSalida3())) {
            lapuertaaAbrir = empresaObj.getPuerta3();
        } else if (puertoqueViene.equals(empresaObj.getSalida4())) {
            lapuertaaAbrir = empresaObj.getPuerta4();
        } else if (puertoqueViene.equals(empresaObj.getSalida5())) {
            lapuertaaAbrir = empresaObj.getPuerta5();
        } else if (puertoqueViene.equals(empresaObj.getSalida6())) {
            lapuertaaAbrir = empresaObj.getPuerta6();
        } else if (puertoqueViene.equals(empresaObj.getSalida7())) {
            lapuertaaAbrir = empresaObj.getPuerta7();
        }
        System.out.println("INI: " + (new Date()));
        AbrirPuerta.abrir(empresaObj.getPuerto(), lapuertaaAbrir);
        System.out.println("FIN: " + (new Date()));

//        LeerTarjeta le = new LeerTarjeta();
//                le.abrir(empresaObj.getPuerto(), lapuertaaAbrir);
    }
    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
//        contenedor.requestFocus();
        try {
            // TODO add your handling code here:
//            frmClientes1.setFrameIcon(new ImageIcon(getClass().getResource("/images_botones/ico.gif")).getImage());
            //adm = new Administrador();
            List<Accesos> accesosL = adm.query("Select o from Accesos as o "
                    + "where o.pantalla = 'Clientes' "
                    + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' and o.ingresar = true ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
            llenarCombo();
//            frmClientes.setSize(441, 455);
//            frmClientes.setLocation(240, 100);
//            frmClientes.setModal(true);
//            btnSalir.requestFocusInWindow();
            contenedor.requestFocus();
//            frmClientes.show();
            frmClientes1.setVisible(true);

            btnSalir.requestFocusInWindow();
            btnSalir.requestFocusInWindow();
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }

//        contenedor.requestFocus();
        //<property name="toplink.cache.type.default" value="NONE"/>

    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        try {
            // TODO add your handling code here:
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Operadores' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "'  and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }

            frmOperadores usu = new frmOperadores(this, true, this, adm);
            usu.setSize(441, 445);
            usu.setLocation(240, 100);
            contenedor.add(usu);
            usu.show();
            contenedor.requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
//        contenedor.requestFocus();
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpresaActionPerformed
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Empresa' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frmEmpresa usu = new frmEmpresa(this, true, this, adm);
            usu.setSize(441, 470);
            usu.setLocation(240, 80);
            contenedor.add(usu);
            
            usu.show();contenedor.requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
//        contenedor.requestFocus();
    }//GEN-LAST:event_btnEmpresaActionPerformed

    private void btnTarifasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTarifasActionPerformed
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Tarifas' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frmTarifas usu = new frmTarifas(this, true, this, adm);
            usu.setSize(409, 460);
            usu.setLocation(240, 100);
              contenedor.add(usu);
            usu.show();
            contenedor.requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
//        contenedor.requestFocus();
    }//GEN-LAST:event_btnTarifasActionPerformed

    private void btnTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTicketActionPerformed
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Tickets' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "'  and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
//            frmTicket usu = new frmTicket(this, true, this, adm);
//            usu.setSize(334, 238);
//            usu.setLocation(240, 100);
//            usu.show();

            frmTicket usu = new frmTicket(this, true, this, adm);
            usu.setSize(334, 238);
            usu.setLocation(240, 100);
//            usu.setMaximizable(true);
            contenedor.add(usu);
            usu.placa.requestFocusInWindow();
            usu.show();usu.placa.requestFocusInWindow();

        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
//        contenedor.requestFocus();
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

    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarActionPerformed
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Factura' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frmFactura usu = new frmFactura(this, true, this, adm);
            usu.setSize(669, 411);
            usu.setLocation(240, 120);
            contenedor.add(usu);
     
            usu.show();
       usu.noTicket.requestFocusInWindow();


        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
//        contenedor.requestFocus();

    }//GEN-LAST:event_btnCobrarActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Reportes' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
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
//        contenedor.requestFocus();
    }//GEN-LAST:event_btnReportesActionPerformed

    private void mnAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAcercaActionPerformed
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Reportes' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "' ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
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
        }else{
        
            tecla(evt.getKeyCode());
        }

}//GEN-LAST:event_nombresKeyPressed

    private void direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            direccion.nextFocus();
        }else{
            tecla(evt.getKeyCode());
        }
}//GEN-LAST:event_direccionKeyPressed

    private void codigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoFocusLost
        // TODO add your handling code here:
        try {
            clienteObj = (Clientes) adm.querySimple("Select o from Clientes as o "
                    + "where o.identificacion = '" + codigo.getText().trim() + "' ");
            if (clienteObj != null) {
                bindingGroup.unbind();
                bindingGroup.bind();
                llenarTabla(clienteObj.getCodigo());
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
        }else{
            tecla(evt.getKeyCode());
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
//                formaTarjetas1.setModal(true);
//                formaTarjetas1.setSize(400, 388);
                formaTarjetas1.setLocation(250, 70);
                formaTarjetas1.show();
            } catch (Exception ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
}//GEN-LAST:event_tarjetasMouseClicked

    private void btnNuevaTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaTarjetaActionPerformed
        // TODO add your handling code here:
        if (clienteObj == null) {
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
//        formaTarjetas.setModal(true);
//        formaTarjetas.setSize(400, 388);
//        formaTarjetas.show();
        formaTarjetas1.setVisible(true);
        nuevaTarjeta = true;

    }//GEN-LAST:event_btnNuevaTarjetaActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        buscarClientes.setModal(true);
        buscarClientes.setSize(533, 300);
        buscarClientes.setLocation(250, 70);
        buscarClientes.show();

        DefaultTableModel dtm = (DefaultTableModel) busquedaTabla.getModel();
        dtm.getDataVector().removeAllElements();
        busquedaTabla.setModel(dtm);
        codigoBuscar.setText("");
        this.codigoBuscar.requestFocusInWindow();
    }//GEN-LAST:event_btnBuscarActionPerformed
    public void habilitar(Boolean estado) {
        codigo.setEditable(estado);
        nombres.setEditable(estado);
        direccion.setEditable(estado);
        telefono.setEditable(estado);
        tarjetas.setEnabled(estado);

        btnNuevaTarjeta.setEnabled(estado);
    }

    public void limpiar() {
        clienteObj = new Clientes();
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
                clienteObj = new Clientes();
                bindingGroup.unbind();
                bindingGroup.bind();
                codigo.requestFocusInWindow();
                btnAgregar.setMnemonic('G');
                btnModificar.setMnemonic('C');
                btnBuscar.setEnabled(false);

            } else if (grabar == true) {
                if (codigo.getText().isEmpty() || nombres.getText().trim().isEmpty() || txtValor.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Registre los campos requeridos ...!");
                } else {
                    if (clienteObj == null) {
                        clienteObj = new Clientes();
                    }
                    clienteObj.setProducto((Productos) tarifas.getSelectedValue());
                    Double valorv = Double.valueOf(txtValor.getText());
                    clienteObj.setValor(new BigDecimal(valorv));
                    clienteObj.setDireccion(direccion.getText());
                    clienteObj.setNombres(nombres.getText());
                    clienteObj.setTelefono(telefono.getText());
                    clienteObj.setIdentificacion(codigo.getText());
                    if (modificar) {
                        try {
                            adm.actualizar(clienteObj);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en actualizar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                    } else {
                        try {

                            adm.guardar(clienteObj);
//                            formaTarjetas.setModal(true);
//                            formaTarjetas.setSize(400, 388);
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
                            formaTarjetas1.setVisible(true);
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
                    auditar("Clientes", ""+clienteObj.getNombres(), "GUARDAR/ACTUALIZAR");

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
            int conf = JOptionPane.showConfirmDialog(this, "Seguro que desea eliminar el registro? ", "JCINFORM", JOptionPane.OK_CANCEL_OPTION);
            if (conf == JOptionPane.OK_OPTION) {
                try {
                     auditar("Clientes", ""+clienteObj.getNombres(), "ELIMINAR");
                    adm.eliminarObjeto(Clientes.class, clienteObj.getCodigo());
                    this.limpiar();
                } catch (Exception ex) {
                    Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            tarjeta.setCliente(clienteObj);
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
            formaTarjetas1.setVisible(false);
            llenarTabla(clienteObj.getCodigo());
            buscarClientes.dispose();
            auditar("Tarjetas", ""+tarjeta.getTarjeta()+" "+tarjeta.getDesde()+" "+tarjeta.getHasta(), "GUARDAR/ACTUALIZAR");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al Guardar Tarjeta vuelva a intentarlo...!");
        }

    }//GEN-LAST:event_btnGuardarTarjetaActionPerformed

    private void btnSalirTarjetasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirTarjetasActionPerformed
        // TODO add your handling code here:
        formaTarjetas1.setVisible(false);
}//GEN-LAST:event_btnSalirTarjetasActionPerformed

    private void codigoBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoBuscarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {

            Thread cargar = new Thread() {

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
            buscarClientes.dispose();
        }
    }//GEN-LAST:event_codigoBuscarKeyPressed

    private void busquedaTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_busquedaTablaMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            try {
                Productos g = new Productos();

                int fila = busquedaTabla.getSelectedRow();
                this.clienteObj = (Clientes) adm.buscarClave((Integer) busquedaTabla.getValueAt(fila, 0), Clientes.class);
                llenarTabla(clienteObj.getCodigo());
                g = clienteObj.getProducto();
                buscarClientes.dispose();
                bindingGroup.unbind();
                bindingGroup.bind();

                ListModel datos = tarifas.getModel();
                for (int i = 0; i < datos.getSize(); i++) {
                    Productos object = (Productos) datos.getElementAt(i);
                    if (g.getCodigo().equals(object.getCodigo())) {
                        tarifas.setSelectedIndex(i);
                        break;
                    }
                }
                txtValor.setText(clienteObj.getValor() + "");

            } catch (Exception ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //        JOptionPane.showMessageDialog(this, usuarioObj);
}//GEN-LAST:event_busquedaTablaMouseClicked
    public void llenarTabla(Integer clie) {
        List<Tarjetas> tarjetasRs = adm.query("Select o from Tarjetas as o where o.cliente.codigo = '" + clie + "'");

        DefaultTableModel dtm = (DefaultTableModel) tarjetas.getModel();
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
                Productos g = new Productos();
                int fila = busquedaTabla.getSelectedRow();
                this.clienteObj = (Clientes) adm.buscarClave((Integer) busquedaTabla.getValueAt(fila, 0), Clientes.class);
                llenarTabla(clienteObj.getCodigo());
                g = clienteObj.getProducto();
                buscarClientes.dispose();
                bindingGroup.unbind();
                bindingGroup.bind();
                ListModel datos = tarifas.getModel();
                for (int i = 0; i < datos.getSize(); i++) {
                    Productos object = (Productos) datos.getElementAt(i);
                    if (g.getCodigo().equals(object.getCodigo())) {
                        tarifas.setSelectedIndex(i);
                        break;
                    }

                }
                txtValor.setText(clienteObj.getValor() + "");
            } catch (Exception ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            buscarClientes.dispose();
        }
}//GEN-LAST:event_busquedaTablaKeyPressed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
          
        grabar = false;
        btnAgregar.doClick();
        btnModificar.doClick();
        frmClientes1.setVisible(false);
         contenedor.requestFocus();

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

    private void btnAccesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccesosActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o "
                    + "where o.pantalla = 'Accesos' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "'  and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frmPrivilegios usu = new frmPrivilegios(this, true, this, adm);
            usu.setSize(457, 449);
          
            usu.setLocation(240, 100);
            contenedor.add(usu);
            usu.show();
//              contenedor.add(usu);
            
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        contenedor.requestFocus();
    }//GEN-LAST:event_btnAccesosActionPerformed

    private void btnAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcercaActionPerformed
        // TODO add your handling code here:
        acerca ac = new acerca(this, true);
        ac.setLocation(240, 100);
        contenedor.add(ac);
        ac.show();
    }//GEN-LAST:event_btnAcercaActionPerformed

    private void btnAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAyudaActionPerformed
        // TODO add your handling code here:
        frmManual usu = new frmManual(this, adm);
        usu.setSize(617, 322);
        usu.setLocation(0, 0);
//            usu.setMaximizable(true);
        contenedor.add(usu);
        usu.show();

    }//GEN-LAST:event_btnAyudaActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        usuario = new Usuarios();
        permisos = new Accesos();
        frmIngresarSistema.setVisible(true);
        habilitarBotones(false);
        usuariot.requestFocusInWindow();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnSalir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir2ActionPerformed
        // TODO add your handling code here:
        auditar("", "", "Salio del Sistema");
        System.exit(0);
    }//GEN-LAST:event_btnSalir2ActionPerformed

    private void btnSalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalirKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            grabar = false;
            btnAgregar.doClick();
            btnModificar.doClick();
            frmClientes1.setVisible(false);
        }

    }//GEN-LAST:event_btnSalirKeyPressed

    private void btnReconfigurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReconfigurarActionPerformed
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o "
                    + "where o.pantalla = 'Reconfigurar' " + "and o.perfil.codigo  = '" + usuario.getPerfil().getCodigo() + "'  and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);

                int seleccion = JOptionPane.showOptionDialog(this, "SE BORRARÁ LA CONFIGURACIÓN ACTUAL DEL SISTEMA \n ¿SEGURO QUE DESEA CONTINUAR?",
                        "JCINFORM", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, // null para icono por defecto.
                        new Object[]{"SI", "NO", "Cancelar"}, "NO");// null para YES, NO y CANCEL
                System.out.println("" + seleccion);
                if (0 == seleccion) {
                    WorkingDirectory w = new WorkingDirectory();
                    String ubicacionDirectorio = w.get() + separador;
                    if (ubicacionDirectorio.contains("build")) {
                        ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
                    }

                    File fichero = new File(ubicacionDirectorio + "KDJFASD5F4AS5D2.xml");
                    if (fichero.exists()) {
                        fichero.delete();
                        System.out.println("ELIMINADO: " + fichero.getAbsolutePath());
                    }


                    frmRegistrar.setIconImage(new ImageIcon(getClass().getResource("/images_botones/icono.png")).getImage());
                    frmRegistrar.setModal(true);
                    frmRegistrar.setSize(474, 280);
                    frmRegistrar.setLocation(350, 300);
                    frmRegistrar.setFocusable(true);
                    frmRegistrar.setResizable(false);
                    frmRegistrar.setTitle("Configuración del Sistema");
                    frmRegistrar.setUndecorated(false);
                    frmRegistrar.show();
                    logear();
                }

            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        contenedor.requestFocus();

    }//GEN-LAST:event_btnReconfigurarActionPerformed

    private void tarifasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_tarifasValueChanged
        // TODO add your handling code here:
        //        listar();
        //        tablaPerfilesRubros.repaint();
        Productos pr = (Productos) tarifas.getSelectedValue();
        txtValor.setText(pr.getValor() + "");
        pr = null;
}//GEN-LAST:event_tarifasValueChanged

    private void barrera7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barrera7ActionPerformed
        // TODO add your handling code here:
        barrera7.setEnabled(false);
        Thread cargar = new Thread() {

            public void run() {
                AbrirPuerta.abrir(empresaObj.getPuerto(), "7");
                barrera7.setEnabled(true);
            }
        };
        cargar.start();
}//GEN-LAST:event_barrera7ActionPerformed

    private void barrera6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barrera6ActionPerformed
        // TODO add your handling code here:
        barrera6.setEnabled(false);
        Thread cargar = new Thread() {

            public void run() {
                AbrirPuerta.abrir(empresaObj.getPuerto(), "6");
                barrera6.setEnabled(true);
            }
        };
        cargar.start();
}//GEN-LAST:event_barrera6ActionPerformed

    private void barrera5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barrera5ActionPerformed
        // TODO add your handling code here:
        barrera5.setEnabled(false);
        Thread cargar = new Thread() {

            public void run() {
                AbrirPuerta.abrir(empresaObj.getPuerto(), "5");
                barrera5.setEnabled(true);
            }
        };
        cargar.start();
}//GEN-LAST:event_barrera5ActionPerformed

    private void barrera4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barrera4ActionPerformed
        // TODO add your handling code here:
        barrera4.setEnabled(false);
        Thread cargar = new Thread() {

            public void run() {
                AbrirPuerta.abrir(empresaObj.getPuerto(), "4");
                barrera4.setEnabled(true);
            }
        };
        cargar.start();
}//GEN-LAST:event_barrera4ActionPerformed

    private void barrera3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barrera3ActionPerformed
        // TODO add your handling code here:
        barrera3.setEnabled(false);
        Thread cargar = new Thread() {

            public void run() {
                AbrirPuerta.abrir(empresaObj.getPuerto(), "3");
                barrera3.setEnabled(true);
            }
        };
        cargar.start();
}//GEN-LAST:event_barrera3ActionPerformed

    private void barrera2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barrera2ActionPerformed
        // TODO add your handling code here:
        barrera2.setEnabled(false);
        Thread cargar = new Thread() {

            public void run() {
                AbrirPuerta.abrir(empresaObj.getPuerto(), "2");
                barrera2.setEnabled(true);
            }
        };
        cargar.start();
}//GEN-LAST:event_barrera2ActionPerformed

    private void barrera1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barrera1ActionPerformed
        // TODO add your handling code here:
        barrera1.setEnabled(false);
        Thread cargar = new Thread() {

            public void run() {
                AbrirPuerta.abrir(empresaObj.getPuerto(), "1");
                barrera1.setEnabled(true);
            }
        };
        cargar.start();



    }//GEN-LAST:event_barrera1ActionPerformed

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

    private void tarjetatxtCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tarjetatxtCaretUpdate
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            String noTarjeta001 = tarjetatxt.getText();
            if (noTarjeta001.length() > 10) {
                noTarjeta001 = noTarjeta001.substring(0, 10);
            }
            Tarjetas tarje = (Tarjetas) adm.buscarClave(noTarjeta001, Tarjetas.class);
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

    private void btnIngresarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnIngresarKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_btnIngresarKeyPressed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        // TODO add your handling code here:

        Thread cargar = new Thread() {

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

    private void claveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_claveKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            Thread cargar = new Thread() {

                public void run() {
                    btnIngresar.doClick();
                }
            };
            cargar.start();



        }
}//GEN-LAST:event_claveKeyPressed

    private void claveFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_claveFocusLost
        // TODO add your handling code here:
}//GEN-LAST:event_claveFocusLost

    private void usuariotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usuariotKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            usuariot.nextFocus();
        }
}//GEN-LAST:event_usuariotKeyPressed

    private void usuariotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuariotActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_usuariotActionPerformed
    public void tecla(int teclaPresionada) {
        int WIDTH = 1;
        KeyEvent evt = new KeyEvent(this, WIDTH, WIDTH, WIDTH, WIDTH);
        if (teclaPresionada == evt.VK_F1) {
            barrera1.doClick();
        } else if (teclaPresionada == evt.VK_F2) {
            barrera2.doClick();
        } else if (teclaPresionada == evt.VK_F3) {
            barrera3.doClick();
        } else if (teclaPresionada == evt.VK_F4) {
            barrera4.doClick();
        } else if (teclaPresionada == evt.VK_F5) {
            barrera5.doClick();
        } else if (teclaPresionada == evt.VK_F6) {
            barrera6.doClick();
        } else if (teclaPresionada == evt.VK_F7) {
            barrera7.doClick();
        } else if (teclaPresionada == evt.VK_F8) {//ABRIR CLIENTES
            btnClientes.doClick();
        } else if (teclaPresionada == evt.VK_F9) {//ABRIR TICKETS
            btnTicket.doClick();
        } else if (teclaPresionada == evt.VK_F10) {
            btnCobrar.doClick(); //ABRIR COBROS

        }
    }
    private void contenedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contenedorKeyPressed
        // TODO add your handling code here:
        int teclaPresionada = evt.getKeyCode();
        tecla(teclaPresionada);



    }//GEN-LAST:event_contenedorKeyPressed

    private void contenedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contenedorMouseClicked
        // TODO add your handling code here:
        contenedor.requestFocus();
    }//GEN-LAST:event_contenedorMouseClicked

    private void telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonoKeyPressed
        // TODO add your handling code here:

            tecla(evt.getKeyCode());
       
    }//GEN-LAST:event_telefonoKeyPressed

    private void tarifasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tarifasKeyPressed
        // TODO add your handling code here:

            tecla(evt.getKeyCode());
        
    }//GEN-LAST:event_tarifasKeyPressed

    private void txtValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyPressed
        // TODO add your handling code here:

            tecla(evt.getKeyCode());
       
    }//GEN-LAST:event_txtValorKeyPressed

    private void btnNuevaTarjetaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevaTarjetaKeyPressed
  tecla(evt.getKeyCode());
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevaTarjetaKeyPressed

    private void tarjetasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tarjetasKeyPressed
        // TODO add your handling code here:
          tecla(evt.getKeyCode());
    }//GEN-LAST:event_tarjetasKeyPressed

    private void noTarjetaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_noTarjetaKeyPressed
        // TODO add your handling code here:
  
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_noTarjetaKeyPressed

    private void placa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placa1KeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_placa1KeyPressed

    private void activaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_activaKeyPressed
        // TODO add your handling code here:

        tecla(evt.getKeyCode());
    }//GEN-LAST:event_activaKeyPressed

    private void ingresosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ingresosKeyPressed
        // TODO add your handling code here:

        tecla(evt.getKeyCode());
    }//GEN-LAST:event_ingresosKeyPressed

    private void descripcionTarjetaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_descripcionTarjetaKeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_descripcionTarjetaKeyPressed

    private void horaDesdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_horaDesdeKeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_horaDesdeKeyPressed

    private void horaHastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_horaHastaKeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_horaHastaKeyPressed

    private void lunesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lunesKeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_lunesKeyPressed

    private void martesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_martesKeyPressed

        tecla(evt.getKeyCode());
    }//GEN-LAST:event_martesKeyPressed

    private void miercolesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_miercolesKeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_miercolesKeyPressed

    private void juevesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_juevesKeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_juevesKeyPressed

    private void viernesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_viernesKeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_viernesKeyPressed

    private void sabadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sabadoKeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_sabadoKeyPressed

    private void domingoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_domingoKeyPressed


        tecla(evt.getKeyCode());
    }//GEN-LAST:event_domingoKeyPressed

    private void todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todosActionPerformed
        // TODO add your handling code here:
         lunes.setSelected(todos.isSelected());
        martes.setSelected(todos.isSelected());
        miercoles.setSelected(todos.isSelected());
        jueves.setSelected(todos.isSelected());
        viernes.setSelected(todos.isSelected());
        sabado.setSelected(todos.isSelected());
        domingo.setSelected(todos.isSelected());
    }//GEN-LAST:event_todosActionPerformed

    private void lunesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lunesActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_lunesActionPerformed

    private void todosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_todosKeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_todosKeyPressed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        new principal();
//
//    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new principal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox activa;
    private javax.swing.JToolBar barraHerramients;
    private javax.swing.JButton barrera1;
    private javax.swing.JButton barrera2;
    private javax.swing.JButton barrera3;
    private javax.swing.JButton barrera4;
    private javax.swing.JButton barrera5;
    private javax.swing.JButton barrera6;
    private javax.swing.JButton barrera7;
    private javax.swing.JButton btnAccesos;
    private javax.swing.JButton btnAcerca;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAyuda;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEmpresa;
    private javax.swing.JButton btnGuardarTarjeta;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevaTarjeta;
    private javax.swing.JButton btnReconfigurar;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalir2;
    private javax.swing.JButton btnSalirTarjetas;
    private javax.swing.JButton btnTarifas;
    private javax.swing.JButton btnTicket;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JDialog buscarClientes;
    private javax.swing.JTable busquedaTabla;
    private javax.swing.JPasswordField clave;
    private javax.swing.JPasswordField claveBase;
    private javax.swing.JFormattedTextField cliente;
    private javax.swing.JComboBox cmbAbre;
    private javax.swing.JComboBox cmbCierra;
    private javax.swing.JFormattedTextField codigo;
    private javax.swing.JFormattedTextField codigoBuscar;
    private javax.swing.JLabel cons;
    public javax.swing.JDesktopPane contenedor;
    private org.jdesktop.swingx.JXTaskPane contenedor1;
    private org.jdesktop.swingx.JXTaskPane contenedor2;
    private org.jdesktop.swingx.JXTaskPane contenedor3;
    private javax.swing.JButton continuar;
    private javax.swing.JTextArea descripcionTarjeta;
    private javax.swing.JPanel diasHabiles;
    private javax.swing.JFormattedTextField direccion;
    private javax.swing.JLabel disponibles;
    private javax.swing.JCheckBox domingo;
    private javax.swing.JLabel errores;
    private com.toedter.calendar.JDateChooser fechaDesde;
    private com.toedter.calendar.JDateChooser fechaHasta;
    private javax.swing.JInternalFrame formaTarjetas1;
    private javax.swing.JInternalFrame frmClientes1;
    private javax.swing.JInternalFrame frmIngresarSistema;
    private javax.swing.JDialog frmRegistrar;
    private javax.swing.JSpinner horaDesde;
    private javax.swing.JSpinner horaHasta;
    private javax.swing.JLabel ingre;
    private javax.swing.JSpinner ingresos;
    private javax.swing.JFormattedTextField ipBase;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JCheckBox jueves;
    private javax.swing.JCheckBox lunes;
    private javax.swing.JCheckBox martes;
    private javax.swing.JCheckBox miercoles;
    public javax.swing.JFormattedTextField noTarjeta;
    private javax.swing.JFormattedTextField nombres;
    private javax.swing.JLabel ocupados;
    private javax.swing.JPanel panelHoras;
    private javax.swing.JFormattedTextField placa;
    private javax.swing.JFormattedTextField placa1;
    public javax.swing.JButton procesando;
    private javax.swing.JFormattedTextField puertoBase;
    private javax.swing.JCheckBox sabado;
    private javax.swing.JLabel salid;
    private javax.swing.JSpinner spConsumo;
    private javax.swing.JSpinner spIngreso;
    private javax.swing.JSpinner spSalida;
    private javax.swing.JList tarifas;
    private javax.swing.JTable tarjetas;
    public javax.swing.JFormattedTextField tarjetatxt;
    private javax.swing.JFormattedTextField telefono;
    private javax.swing.JCheckBox todos;
    private javax.swing.JLabel totales;
    private javax.swing.JTextField txtValor;
    private javax.swing.JFormattedTextField usuarioBase;
    private javax.swing.JButton usuarioLogeado;
    private javax.swing.JFormattedTextField usuariot;
    private javax.swing.JCheckBox viernes;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    public static Accesos permisos;

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
        return clienteObj;
    }

    public void setUsuarioObj(Clientes usuarioObj) {
        this.clienteObj = usuarioObj;
    }

    public boolean getNuevaTarjeta() {
        return nuevaTarjeta;
    }

    public void setNuevaTarjeta(boolean nuevaTarjeta) {
        this.nuevaTarjeta = nuevaTarjeta;
    }

    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void keyPressed(KeyEvent e) {
        int teclaPresionada = e.getKeyCode();
        System.out.println("Tecla Presionada: code: " + teclaPresionada + " char:" + e.getKeyChar());
    }

    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void auditar(String tabla,String campo,String accion){
        try {
            Auditoria aud = new Auditoria();
            aud.setAccion(accion);
            aud.setCampo(campo);
            aud.setTabla(tabla);
            aud.setFecha(new Date());
            java.net.InetAddress i = java.net.InetAddress.getLocalHost();
            System.out.println(i); // name and IP address
            System.out.println(i.getHostName()); // name
            System.out.println(i.getHostAddress()); // IP address only
            aud.setMaquina(System.getProperty("user.name")+" IP: "+i.getHostAddress());
            aud.setUsuario(usuarioActual);
            adm.guardar(aud);
        } catch (UnknownHostException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowClosing(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowClosed(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
