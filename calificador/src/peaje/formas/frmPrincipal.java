package peaje.formas;

//import camaraIP.tomarImage;
import chatear.servidor.HiloDeCliente;
import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import hibernate.*;
import hibernate.cargar.Administrador;
import hibernate.cargar.GeneraXMLPersonal;
import hibernate.cargar.RelojModeloUtil;
import hibernate.cargar.RelojVisual;
import hibernate.cargar.UsuarioActivo;
import hibernate.cargar.WorkingDirectory;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import xml.XMLEmpresa;

/**
 *
 * @author geovanny
 */
public class frmPrincipal extends javax.swing.JFrame implements KeyListener, WindowListener {

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
    static UsuarioActivo datosConecta;
    static Boolean mostrar = true;
    public static ArrayList puertoListo;
    static WorkingDirectory w = new WorkingDirectory();
    String ubicacionDirectorio = w.get() + separador;
    public CamaraWeb ver = new CamaraWeb();
    public tomarImage verIp = new tomarImage();
    logger lger = new logger();

    public void habilitarBotones(Boolean estado) {
        btnAuditoria.setEnabled(estado);
        btnClientes.setEnabled(estado);
//        btnCobrar.setEnabled(estado);
        btnEmpresa.setEnabled(estado);
//        btnTicket.setEnabled(estado);
//        btnTarifas.setEnabled(estado);
        btnReportes.setEnabled(estado);
        btnUsuarios.setEnabled(estado);
        btnAccesos.setEnabled(estado);
        btnReconfigurar.setEnabled(estado);
        btnAcerca.setEnabled(estado);
        btnAyuda.setEnabled(estado);
        btnSalir2.setEnabled(estado);
        btnCerrar.setEnabled(estado);
    }
//  public BufferedImage image = null;
//     public void paint(Graphics g){
//         
//        
//        Graphics2D g2 = (Graphics2D)g; 
//        URL nUrl = null;
//              
//        try {
//          nUrl = new URL("http://192.168.10.3/cgi-bin/viewer/video.jpg");
//        } catch (MalformedURLException ex) {
//            ex.printStackTrace();
//        }
//        
//        //public BufferedImage image = null;
//        try {
//          image = ImageIO.read(nUrl);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//       }
//        
//        setTitle("Imagen_Camara");   
//        g2.drawImage(image,camaraVista.getX(),camaraVista.getY(),this);
//        repaint();
//        
//       
//     
//    }

    /** Creates new form frmPrincipal */
    public frmPrincipal() {
//        super("frmPrincipal");
//        this.addKeyListener(this);
//        this.setVisible(true);
//        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                //JOptionPane.showMessageDialog(puertoBase, "mensaje");
                auditar("", "", "Salir del Sistema");
                System.exit(0);

            }
        });
buscar();
        Image im = new ImageIcon(getClass().getResource("/images_botones/icono.png")).getImage();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //WINDOWS
            initComponents();
            contenedor.requestFocus();
            RelojModeloUtil modelo = new RelojModeloUtil();
            RelojVisual visual = new RelojVisual(modelo);
//            visual.setLocation(100, 100);
//            barraHerramients.add(visual, 4);
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
                    ac.setLocation(0, 0);
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
            // panelIngreso.setVisible(false);
            panelCambiar.setVisible(false);
            habilitarBotones(false);
            if (comprobar()) {
                mostrar = true;
                logear();
            } else {


//              this.hide();
//              this.disable();
//              this.dispose();
//               new frmConfiguracion().show();
                mostrar = false;
                //logear();

            }


        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
            lger.logger(frmPrincipal.class.getName(), ex + "");
        } catch (InstantiationException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        } catch (IllegalAccessException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        }

        usuariot.requestFocusInWindow();

    }
    public ArrayList<Tipos> tipos = new ArrayList<Tipos>();
   
    void buscar(){
                    try {
                        List<Tipos> usuarios = adm.query("Select o from Tipos as o ");
                        Object[] obj = new Object[4];
                        DefaultTableModel dtm = (DefaultTableModel) busquedaTabla.getModel();
                        dtm.getDataVector().removeAllElements();
                        for (Iterator<Tipos> it = usuarios.iterator(); it.hasNext();) {
                            Tipos glbusuario = it.next();
                                tipos.add(glbusuario);
                        }
 
                    } catch (Exception ex) {
                        Logger.getLogger(frmTiposCalificadores.class.getName()).log(Level.SEVERE, null, ex);
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

        buscarClientes = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        codigoBuscar = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        busquedaTabla = new javax.swing.JTable();
        jSplitPane1 = new javax.swing.JSplitPane();
        contenedor = new javax.swing.JDesktopPane();
        frmClientes1 = new javax.swing.JInternalFrame();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        nombres = new javax.swing.JFormattedTextField();
        direccion = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        codigo = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        telefono = new javax.swing.JFormattedTextField();
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
        frmRespaldarBase = new javax.swing.JInternalFrame();
        btnRespaldoWindows = new javax.swing.JButton();
        btnRespaldoLinux = new javax.swing.JButton();
        ubicacionArchivo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        nombreArchivo = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        panelCambiar = new javax.swing.JPanel();
        guardarCambioClave = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        claveActual = new javax.swing.JPasswordField();
        nuevaClave = new javax.swing.JPasswordField();
        repiteClave = new javax.swing.JPasswordField();
        jButton7 = new javax.swing.JButton();
        panelIngreso = new javax.swing.JPanel();
        tarjetatxt = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        placa = new javax.swing.JFormattedTextField();
        camaraVista = new javax.swing.JLabel();
        camaraVista1 = new javax.swing.JLabel();
        webTeka = new javax.swing.JLabel();
        logoTeka = new javax.swing.JLabel();
        miBotonImagen = new javax.swing.JLabel();
        ultimoIngreso = new javax.swing.JLabel();
        usuarioLogeado = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        botoninst = new javax.swing.JButton();
        tempjButton13 = new javax.swing.JButton();
        tempnotarjetaTemp = new javax.swing.JTextField();
        temppuertoText = new javax.swing.JComboBox();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        contenedor1 = new org.jdesktop.swingx.JXTaskPane();
        jToolBar1 = new javax.swing.JToolBar();
        btnUsuarios = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnTipos = new javax.swing.JButton();
        contenedor2 = new org.jdesktop.swingx.JXTaskPane();
        jToolBar2 = new javax.swing.JToolBar();
        btnEmpresa = new javax.swing.JButton();
        btnAccesos = new javax.swing.JButton();
        btnReconfigurar = new javax.swing.JButton();
        btnAuditoria = new javax.swing.JButton();
        btnAuditoria1 = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Control");
        setName("miForma"); // NOI18N

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(220);
        jSplitPane1.setDividerSize(0);
        jSplitPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSplitPane1KeyPressed(evt);
            }
        });

        contenedor.setBackground(new java.awt.Color(255, 255, 255));
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

        frmClientes1.setTitle("Registro y Modifación de Clientes");
        frmClientes1.setAutoscrolls(true);
        frmClientes1.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clientes.png"))); // NOI18N
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

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Teléfono:");
        jPanel4.add(jLabel15);
        jLabel15.setBounds(10, 70, 60, 14);

        frmClientes1.getContentPane().add(jPanel4);
        jPanel4.setBounds(10, 50, 400, 130);

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
        btnBuscar.setBounds(80, 10, 60, 50);

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
        btnAgregar.setBounds(140, 10, 60, 50);

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
        btnModificar.setBounds(200, 10, 60, 50);

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
        btnEliminar.setBounds(260, 10, 60, 50);

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
        btnSalir.setBounds(320, 10, 60, 50);

        frmClientes1.getContentPane().add(jPanel5);
        jPanel5.setBounds(20, 200, 390, 70);

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

        frmClientes1.setBounds(5, 5, 440, 320);
        contenedor.add(frmClientes1, javax.swing.JLayeredPane.DEFAULT_LAYER);

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

        frmIngresarSistema.setBounds(110, 100, 380, 220);
        contenedor.add(frmIngresarSistema, javax.swing.JLayeredPane.DEFAULT_LAYER);

        frmRespaldarBase.setTitle("Respaldo de Información");
        frmRespaldarBase.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edittrash.gif"))); // NOI18N
        frmRespaldarBase.getContentPane().setLayout(null);

        btnRespaldoWindows.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/windows.png"))); // NOI18N
        btnRespaldoWindows.setText("SI ESWINDOWS");
        btnRespaldoWindows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRespaldoWindowsActionPerformed(evt);
            }
        });
        frmRespaldarBase.getContentPane().add(btnRespaldoWindows);
        btnRespaldoWindows.setBounds(50, 120, 135, 40);

        btnRespaldoLinux.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/linux.png"))); // NOI18N
        btnRespaldoLinux.setText("SI ES LINUX");
        btnRespaldoLinux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRespaldoLinuxActionPerformed(evt);
            }
        });
        frmRespaldarBase.getContentPane().add(btnRespaldoLinux);
        btnRespaldoLinux.setBounds(190, 120, 153, 40);

        ubicacionArchivo.setText("C:\\RESPALDOS\\");
            frmRespaldarBase.getContentPane().add(ubicacionArchivo);
            ubicacionArchivo.setBounds(140, 50, 205, 20);

            jLabel6.setText("Nombre del Archivo:");
            frmRespaldarBase.getContentPane().add(jLabel6);
            jLabel6.setBounds(40, 90, 97, 14);

            jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12));
            jLabel24.setText(".sql");
            frmRespaldarBase.getContentPane().add(jLabel24);
            jLabel24.setBounds(347, 80, 30, 30);

            jButton8.setText("Examinar");
            jButton8.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton8ActionPerformed(evt);
                }
            });
            frmRespaldarBase.getContentPane().add(jButton8);
            jButton8.setBounds(350, 50, 77, 23);

            nombreArchivo.setText("RESPALDO"+ (new Date()).toLocaleString().replace("/","").replace(" ","").replace(":",""));
            frmRespaldarBase.getContentPane().add(nombreArchivo);
            nombreArchivo.setBounds(140, 80, 205, 20);

            jLabel25.setText("Ubicación:");
            frmRespaldarBase.getContentPane().add(jLabel25);
            jLabel25.setBounds(40, 60, 103, 14);

            jLabel26.setForeground(new java.awt.Color(51, 0, 204));
            jLabel26.setText("Recuerde respaldar su información periodicamente, para una mayor seguridad de su sistema");
            frmRespaldarBase.getContentPane().add(jLabel26);
            jLabel26.setBounds(40, 10, 480, 14);

            jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salir.png"))); // NOI18N
            jButton6.setText("Salir");
            jButton6.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton6ActionPerformed(evt);
                }
            });
            frmRespaldarBase.getContentPane().add(jButton6);
            jButton6.setBounds(350, 120, 140, 40);

            frmRespaldarBase.setBounds(5, 5, 520, 210);
            contenedor.add(frmRespaldarBase, javax.swing.JLayeredPane.DEFAULT_LAYER);

            panelCambiar.setBackground(new java.awt.Color(227, 240, 254));
            panelCambiar.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
            panelCambiar.setLayout(null);

            guardarCambioClave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/filesave.gif"))); // NOI18N
            guardarCambioClave.setText("Cambiar");
            guardarCambioClave.setMargin(new java.awt.Insets(1, 1, 1, 1));
            guardarCambioClave.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    guardarCambioClaveActionPerformed(evt);
                }
            });
            panelCambiar.add(guardarCambioClave);
            guardarCambioClave.setBounds(30, 100, 90, 33);

            jLabel39.setText("Clave Actual:");
            panelCambiar.add(jLabel39);
            jLabel39.setBounds(30, 10, 64, 14);

            jLabel40.setText("Nueva Clave:");
            panelCambiar.add(jLabel40);
            jLabel40.setBounds(30, 40, 65, 14);

            jLabel41.setText("Repite Clave:");
            panelCambiar.add(jLabel41);
            jLabel41.setBounds(30, 70, 65, 14);

            claveActual.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    claveActualActionPerformed(evt);
                }
            });
            panelCambiar.add(claveActual);
            claveActual.setBounds(110, 10, 110, 20);
            panelCambiar.add(nuevaClave);
            nuevaClave.setBounds(111, 41, 110, 20);
            panelCambiar.add(repiteClave);
            repiteClave.setBounds(111, 67, 110, 20);

            jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.gif"))); // NOI18N
            jButton7.setText("Cerrar");
            jButton7.setMargin(new java.awt.Insets(1, 1, 1, 1));
            jButton7.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton7ActionPerformed(evt);
                }
            });
            panelCambiar.add(jButton7);
            jButton7.setBounds(140, 100, 90, 35);

            panelCambiar.setBounds(10, 30, 270, 150);
            contenedor.add(panelCambiar, javax.swing.JLayeredPane.DEFAULT_LAYER);

            panelIngreso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            panelIngreso.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    panelIngresoKeyPressed(evt);
                }
            });
            panelIngreso.setLayout(null);

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
            panelIngreso.add(tarjetatxt);
            tarjetatxt.setBounds(620, 370, 140, 20);

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 20, Short.MAX_VALUE)
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 70, Short.MAX_VALUE)
            );

            panelIngreso.add(jPanel1);
            jPanel1.setBounds(360, 10, 20, 70);

            placa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
            placa.setEditable(false);
            placa.setText(".");
            panelIngreso.add(placa);
            placa.setBounds(620, 350, 140, 20);

            camaraVista.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            camaraVista.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 204), 1, true));
            camaraVista.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    camaraVistaKeyPressed(evt);
                }
            });
            panelIngreso.add(camaraVista);
            camaraVista.setBounds(10, 270, 260, 190);

            camaraVista1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            camaraVista1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 204), 1, true));
            camaraVista1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    camaraVista1KeyPressed(evt);
                }
            });
            panelIngreso.add(camaraVista1);
            camaraVista1.setBounds(10, 270, 260, 190);

            webTeka.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            webTeka.setText("www.tekatronic.com.ec");
            panelIngreso.add(webTeka);
            webTeka.setBounds(20, 520, 140, 14);

            logoTeka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tekatronic.png"))); // NOI18N
            panelIngreso.add(logoTeka);
            logoTeka.setBounds(10, 460, 160, 70);

            miBotonImagen.setBackground(new java.awt.Color(204, 204, 255));
            miBotonImagen.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 204, 255)));
            panelIngreso.add(miBotonImagen);
            miBotonImagen.setBounds(280, 270, 240, 190);

            ultimoIngreso.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
            ultimoIngreso.setText("Ultima Calificación");
            panelIngreso.add(ultimoIngreso);
            ultimoIngreso.setBounds(280, 250, 230, 13);

            panelIngreso.setBounds(0, 30, 770, 590);
            contenedor.add(panelIngreso, javax.swing.JLayeredPane.DEFAULT_LAYER);

            usuarioLogeado.setFont(new java.awt.Font("Tahoma", 1, 11));
            usuarioLogeado.setForeground(new java.awt.Color(0, 153, 204));
            usuarioLogeado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editPerfil.png"))); // NOI18N
            usuarioLogeado.setText("...");
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
            usuarioLogeado.setBounds(10, 3, 270, 30);
            contenedor.add(usuarioLogeado, javax.swing.JLayeredPane.DEFAULT_LAYER);

            jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12));
            jLabel37.setForeground(new java.awt.Color(102, 102, 102));
            jLabel37.setText("PARTNERS");
            jLabel37.setBounds(500, 450, 160, 20);
            contenedor.add(jLabel37, javax.swing.JLayeredPane.DEFAULT_LAYER);

            botoninst.setText("Ver Instantanea");
            botoninst.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    botoninstActionPerformed(evt);
                }
            });
            botoninst.setBounds(290, 0, 120, 23);
            contenedor.add(botoninst, javax.swing.JLayeredPane.DEFAULT_LAYER);

            tempjButton13.setText("Tarjeta");
            tempjButton13.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    tempjButton13ActionPerformed(evt);
                }
            });
            tempjButton13.setBounds(410, 0, 80, 23);
            contenedor.add(tempjButton13, javax.swing.JLayeredPane.DEFAULT_LAYER);
            tempnotarjetaTemp.setBounds(510, 0, 110, 22);
            contenedor.add(tempnotarjetaTemp, javax.swing.JLayeredPane.DEFAULT_LAYER);

            temppuertoText.setEditable(true);
            temppuertoText.setBounds(630, 0, 50, 20);
            contenedor.add(temppuertoText, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
            contenedor1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    contenedor1KeyPressed(evt);
                }
            });

            jToolBar1.setFloatable(false);
            jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
            jToolBar1.setRollover(true);

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
            jToolBar1.add(btnUsuarios);

            btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clientes.png"))); // NOI18N
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

            btnTipos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clientes.png"))); // NOI18N
            btnTipos.setMnemonic('C');
            btnTipos.setText("Tipos");
            btnTipos.setToolTipText("Crear tipos de calificadores");
            btnTipos.setFocusable(false);
            btnTipos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            btnTipos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnTipos.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnTiposActionPerformed(evt);
                }
            });
            jToolBar1.add(btnTipos);

            contenedor1.getContentPane().add(jToolBar1);

            contenedor2.setTitle("Administracion");
            contenedor2.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    contenedor2KeyPressed(evt);
                }
            });

            jToolBar2.setFloatable(false);
            jToolBar2.setOrientation(javax.swing.SwingConstants.VERTICAL);
            jToolBar2.setRollover(true);

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

            btnAuditoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/today.gif"))); // NOI18N
            btnAuditoria.setMnemonic('S');
            btnAuditoria.setText("Auditoria");
            btnAuditoria.setFocusable(false);
            btnAuditoria.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            btnAuditoria.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnAuditoria.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAuditoriaActionPerformed(evt);
                }
            });
            jToolBar2.add(btnAuditoria);

            btnAuditoria1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edittrash.gif"))); // NOI18N
            btnAuditoria1.setText("Respaldar Base ");
            btnAuditoria1.setFocusable(false);
            btnAuditoria1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            btnAuditoria1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnAuditoria1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAuditoria1ActionPerformed(evt);
                }
            });
            jToolBar2.add(btnAuditoria1);

            contenedor2.getContentPane().add(jToolBar2);

            contenedor3.setTitle("Ayuda");
            contenedor3.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    contenedor3KeyPressed(evt);
                }
            });

            jToolBar3.setFloatable(false);
            jToolBar3.setOrientation(javax.swing.SwingConstants.VERTICAL);
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
            jLabel32.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jLabel32KeyPressed(evt);
                }
            });

            javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
            jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
            jXTaskPaneContainer1Layout.setHorizontalGroup(
                jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel32)
                        .addComponent(contenedor2, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                        .addComponent(contenedor3, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                        .addComponent(contenedor1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                    .addContainerGap())
            );
            jXTaskPaneContainer1Layout.setVerticalGroup(
                jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                    .addComponent(contenedor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(22, 22, 22)
                    .addComponent(contenedor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(contenedor3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel32)
                    .addContainerGap(199, Short.MAX_VALUE))
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
            String puertoYaAbiertos = "";
            puertoListo = new ArrayList();
            Boolean interfaz = true;
            Boolean leds = true;
            Boolean barras1 = true;
            Boolean barras2 = true;
            temppuertoText.removeAllItems();
            while (portList.hasMoreElements()) {
                try {
                    portId = (CommPortIdentifier) portList.nextElement();
                    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

                        //(0) //PUERTO DE LA TARJETA INTERFAZ PC - BARRERA
//                         puertoYaAbiertos = puertoYaAbiertos+"; "+portId.getName();
//                        System.out.println("" + portId.getName() + " " + empresaObj.getPuerto());
                        if (portId.getName().equals(empresaObj.getPuerto())) {
                            try {
                                if (interfaz) {
                                    reader = new LeerTarjeta(portId, this);
                                    puertoListo.add(reader);
                                    System.out.println("PUERTO INTERFAZ PC BARRERA0 - ABIERTO: " + empresaObj.getPuerto());
                                    interfaz = false;
                                    lger.logger("PUERTO INTERFAZ PC BARRERA0 - ABIERTO: " + empresaObj.getPuerto(), "OK");

                                }
                            } catch (Exception e) {
                                System.out.println("NO SE ABRIÓ: INTERFAZ PC BARRERA0 - ABIERTO: " + empresaObj.getPuerto());
                                lger.logger("NO SE ABRIÓ: INTERFAZ PC BARRERA0 - ABIERTO: " + empresaObj.getPuerto(), "ERROR");
                            }

                        }
                        //(1)    //PUERTO DE LETRERO LEDS
                        if (portId.getName().equals(empresaObj.getLed())) {
                            try {
                                if (leds) {
                                    reader = new LeerTarjeta(portId, this);
                                    puertoListo.add(reader);
                                    leds = false;
                                    System.out.println("PUERTO LETRERO-LEDS ABIERTO: " + empresaObj.getLed());
                                    lger.logger("PUERTO LETRERO-LEDS ABIERTO: " + empresaObj.getLed(), "OK");

                                }
                            } catch (Exception e) {
                                System.out.println("NO SE ABRIÓ: PUERTO LETRERO-LEDS ABIERTO: " + empresaObj.getLed());
                                lger.logger("NO SE ABRIÓ: PUERTO LETRERO-LEDS ABIERTO: " + empresaObj.getLed(), "ERROR");
                            }
                        }
                        //(2)  //PUERTO DE CODIGO DE BARRAS
                        if (portId.getName().equals(empresaObj.getBarras())) {
                            try {
                                if (barras1) {
                                    reader = new LeerTarjeta(portId, this);
                                    puertoListo.add(reader);
                                    barras1 = false;
                                    System.out.println("PUERTO LECTORA COD.BARRAS ABIERTO: " + empresaObj.getBarras());
                                    lger.logger("PUERTO LECTORA COD.BARRAS ABIERTO: " + empresaObj.getBarras(), "OK");

                                }
                            } catch (Exception e) {
                                System.out.println("NO SE ABRIÓ: PUERTO LECTORA COD.BARRAS ABIERTO: " + empresaObj.getBarras());
                                lger.logger("NO SE ABRIÓ: PUERTO LECTORA COD.BARRAS ABIERTO: " + empresaObj.getBarras(), "ERROR");
                            }
                        }
                        //(3)  //PUERTO DE CODIGO DE BARRAS 2
                        if (portId.getName().equals(empresaObj.getBarras2())) {
                            try {
                                if (barras2) {

                                    reader = new LeerTarjeta(portId, this);
                                    puertoListo.add(reader);
                                    System.out.println("PUERTO LECTORA COD.BARRAS 2 ABIERTO: " + empresaObj.getBarras2());
                                    barras2 = false;
                                    lger.logger("PUERTO LECTORA COD.BARRAS 2 ABIERTO: " + empresaObj.getBarras2(), "OK");

                                }
                            } catch (Exception e) {
                                System.out.println("NO SE ABRIÓ: PUERTO LECTORA COD.BARRAS 2 ABIERTO: " + empresaObj.getBarras2());
                                lger.logger("NO SE ABRIÓ: PUERTO LECTORA COD.BARRAS 2 ABIERTO: " + empresaObj.getBarras2(), "ERROR");
                            }

                        }
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
                            temppuertoText.addItem(empresaObj.getSalida1());
//                        read.add(reader);
                        }
                        if (portId.getName().equals(empresaObj.getSalida2()) && empresaObj.getActiva2()) {
                            reader = new LeerTarjeta(portId, this);
                            System.out.println("ABIERTO: " + empresaObj.getSalida2());
                            temppuertoText.addItem(empresaObj.getSalida2());
//                        read.add(reader);
                        }
                        if (portId.getName().equals(empresaObj.getSalida3()) && empresaObj.getActiva3()) {
                            reader = new LeerTarjeta(portId, this);
                            System.out.println("ABIERTO: " + empresaObj.getSalida3());
                            temppuertoText.addItem(empresaObj.getSalida3());
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
                } catch (Exception ex) {
                    Logger.getLogger(LeerTarjeta.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            portList = null;
            System.out.println("# PUERTOS PRINCIPALES INICIADOS: " + puertoListo.size());
            lger.logger("# PUERTOS PRINCIPALES INICIADOS: " + puertoListo.size(), "OK");
        } catch (Exception ex) {
            Logger.getLogger(LeerTarjeta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logear() {
//        try {
        //        try {

        frmIngresarSistema.setVisible(true);
//            frmIngresarSistema.;
        //            Robot r = new Robot();
        //            r.mouseMove(180,170);
        //            r.
        //frmLogin.setIconImage(new ImageIcon(getClass().getResource("/images_botones/ico.gif")).getImage());
        adm = new Administrador(datosConecta);
        //                frmLogin.setModal(true);
        //                frmLogin.setSize(400, 230);
        //                frmLogin.setLocation(350, 300);
        //                frmLogin.setFocusable(true);
        //                frmLogin.setResizable(false);
        //                frmLogin.setTitle("Inicio de Sesión");
        //                frmLogin.setUndecorated(true);
        //                frmLogin.shogeova geow();
        //        } catch (AWTException ex) {
        //            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);             lger.logger(frmPrincipal.class.getName(), ex+"");
        //        }
//        } catch (PropertyVetoException ex) {
//            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);             lger.logger(frmPrincipal.class.getName(), ex+"");
//        }
    }

    public Boolean comprobar() {

        GeneraXMLPersonal pXml = new GeneraXMLPersonal();
        pXml.inicio();
        pXml.leerXML();
        datosConecta = pXml.user;
        try {
            String nombre = datosConecta.getNombre();
            //System.out.println("NOMB:" + nombre);
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

    public void llenarCombo() {
//        try {
//            List listado = adm.query("Select o from Productos as o ");
//            Object[] listData = new Object[listado.size()];
//            int i = 0;
//            for (Iterator<Productos> it = listado.iterator(); it.hasNext();) {
//                Productos global = it.next();
//                listData[i] = global;
//                i++;
//            }
//            tarifas.setListData(listData);
//        } catch (Exception ex) {
//            Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void noDisponibles() {
       

    }

    public void cargarEmpresa(EmpresaPuertosStatic emp) {

        empresaObj.setPuerto(emp.getPuerto());
        empresaObj.setBarras(emp.getBarras());
        empresaObj.setBarras2(emp.getBarras2());
        empresaObj.setLed(emp.getLed());
        empresaObj.setSale(emp.getSale());
        empresaObj.setSale2(emp.getSale2());
        empresaObj.setBloquear(emp.getBloquear());
        empresaObj.setBloquearsalida(emp.getBloquearsalida());

        empresaObj.setActiva1(emp.getActiva1());
        empresaObj.setActiva2(emp.getActiva2());
        empresaObj.setActiva3(emp.getActiva3());
        empresaObj.setActiva4(emp.getActiva4());
        empresaObj.setActiva5(emp.getActiva5());
        empresaObj.setActiva6(emp.getActiva6());
        empresaObj.setActiva7(emp.getActiva7());

        empresaObj.setSalida1(emp.getSalida1());
        empresaObj.setSalida2(emp.getSalida2());
        empresaObj.setSalida3(emp.getSalida3());
        empresaObj.setSalida4(emp.getSalida4());
        empresaObj.setSalida5(emp.getSalida5());
        empresaObj.setSalida6(emp.getSalida6());
        empresaObj.setSalida7(emp.getSalida7());

        empresaObj.setPuerta1(emp.getPuerta1());
        empresaObj.setPuerta2(emp.getPuerta2());
        empresaObj.setPuerta3(emp.getPuerta3());
        empresaObj.setPuerta4(emp.getPuerta4());
        empresaObj.setPuerta5(emp.getPuerta5());
        empresaObj.setPuerta6(emp.getPuerta6());
        empresaObj.setPuerta7(emp.getPuerta7());

        empresaObj.setPuerto1(emp.getPuerto1());
        empresaObj.setPuerto2(emp.getPuerto2());
        empresaObj.setPuerto3(emp.getPuerto3());
        empresaObj.setPuerto4(emp.getPuerto4());
        empresaObj.setPuerto5(emp.getPuerto5());
        empresaObj.setPuerto6(emp.getPuerto6());
        empresaObj.setPuerto7(emp.getPuerto7());
        empresaObj.setWebcam(emp.getWebcam());
        empresaObj.setIpcam(emp.getIpcam());
        empresaObj.setUrl(emp.getUrl());
        empresaObj.setEntra1(emp.getEntra1());
        empresaObj.setEntra2(emp.getEntra2());
        empresaObj.setSeabretic(emp.getSeabretic());
        empresaObj.setSeabrefac(emp.getSeabrefac());
        empresaObj.setMulta(emp.getMulta());
        empresaObj.setPuertafac(emp.getPuertafac());
        empresaObj.setPuertatic(emp.getPuertatic());


    }

    void iniciarCamar() {
        try {


//            this.camaraVista.setLayout(null);
            this.camaraVista.add(ver.VerCamara(2, 2, new Double(camaraVista.getSize().getWidth()).intValue() - 4, new Double(camaraVista.getSize().getHeight()).intValue() - 4));
        } catch (Exception e) {
            System.out.println("NO SE PUEDE INICIAR CAMARA.....!");
        }
    }

    public void verificarUsuario() {
        Usuarios usu = null;
        try {
            Date fechaAc = new Date();
//            String hora = fechaAc.getHours() + ":" + fechaAc.getMinutes() + ":" + fechaAc.getSeconds();
            usu = adm.ingresoSistema(usuariot.getText(), clave.getText());

            if (usu != null) {
                try {
                    Date feI = usu.getHoraini();
                    Date feF = usu.getHorafin();
                    feI.setYear(fechaAc.getYear());
                    feI.setMonth(fechaAc.getMonth());
                    feI.setDate(fechaAc.getDate());
                    feF.setYear(fechaAc.getYear());
                    feF.setMonth(fechaAc.getMonth());
                    feF.setDate(fechaAc.getDate());
                    Boolean estado = true;
                    Boolean estado2 = true;
                    if (feI.getTime() <= fechaAc.getTime()) {
                        estado = true;
                    } else {
                        estado = false;
                    }
                    if (feF.getTime() >= fechaAc.getTime()) {
                        estado2 = true;
                    } else {
                        estado2 = false;
                    }

                    if (estado && estado2) {
                    } else {

                        if (usu.getUsuario().equals("geova") && claves.desencriptar(usu.getClave()).equals("root")) {
                            usuarioActual = usu;
                        } else {
                            clave.setEditable(true);
                            usuariot.setEditable(true);
                            usuariot.setText("");
                            clave.setText("");

                            JOptionPane.showMessageDialog(this, "No puede ingresar en éste horario", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                            usuariot.requestFocusInWindow();
                            return;

                        }

                    }
                } catch (Exception e) {
                    clave.setEditable(true);
                    usuariot.setEditable(true);
                    usuariot.setText("");
                    clave.setText("");
                    JOptionPane.showMessageDialog(this, "No se ha cargado su horario de trabajo\n solicite a un administrador que realice éste proceso", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                    usuariot.requestFocusInWindow();
                    System.out.println("NO SE HAN CARGADO LAS FECHAS " + e);
                    if (usu.getUsuario().equals("geova") && claves.desencriptar(usu.getClave()).equals("root")) {
                        //  PASA A LOS SIGUIENTES PROCESOS Y NO TOMA EN CUENTA LAS FECHAS YA QUE SOY ADMINISTRADOR
                        File fichero = new File(ubicacionDirectorio + "config.xml");
                        if (fichero.exists()) {
                            fichero.delete();
                            System.out.println("ELIMINADO: " + fichero.getAbsolutePath());
                            XMLEmpresa pXml = new XMLEmpresa();
                            pXml.inicio();
                            pXml.leerXML();
                        } else {
                            JOptionPane.showMessageDialog(this, "CONFIGURE LOS PUERTOS DE LA EMPRESA Y VUELVA A REINICIAR LA APLICACIÓN");
                        }

                    } else {
                        return;
                    }


                }
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

        if (usu != null) {
            try {
                usuario = usu;
                clave.setEditable(true);
                usuariot.setEditable(true);
                usuariot.setText("");
                clave.setText("");
                frmIngresarSistema.setVisible(false);
                usuarioActual = usu;
                usuarioLogeado.setText("" + usuarioActual.getNombres());
                List<Empresa> emp = adm.listar("Select o from Empresa as o ");
                this.empresaObj = emp.get(0);
                if (ubicacionDirectorio.contains("build")) {
                    ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
                }

                File fichero = new File(ubicacionDirectorio + "config.xml");
                if (fichero.exists()) {
                    //System.out.println("ELIMINADO: " + fichero.getAbsolutePath());
                    XMLEmpresa pXml = new XMLEmpresa();
                    pXml.inicio();
                    EmpresaPuertosStatic amp = pXml.leerXML();
                    cargarEmpresa(amp);
                } else {
                    JOptionPane.showMessageDialog(this, "Configurar PUERTOS en modulo EMPRESA y reinicie la aplicación");
                }

                noDisponibles();
//                disponibles.setEnabled(true);
//                ocupados.setEnabled(true);
//                totales.setEnabled(true);
         
             
                if (empresaObj.getWebcam()) {
                    iniciarCamar();
                }
                in = UsuarioActivo.getIn();
                out = UsuarioActivo.getOut();
                habilitarBotones(true);
                List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.global.codigo  = '" + usuarioActual.getGlobal().getCodigo() + "' ");
                for (Iterator<Accesos> it = accesosL.iterator(); it.hasNext();) {
                    Accesos accesos = it.next();
                    if (accesos.getPantalla().equals("Clientes") && !accesos.getIngresar()) {
                        btnClientes.setEnabled(false);
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
                    if (accesos.getPantalla().equals("Auditoria") && !accesos.getIngresar()) {
                        btnAuditoria.setEnabled(false);
                    }
                    
                }

                iniciarPuertos();
                contenedor.requestFocus();
                auditar("", "", "Ingreso al Sistema");




            } catch (Exception ex) {
                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                lger.logger(frmPrincipal.class.getName(), ex + "");
            }
        } else {
            clave.setEditable(true);
            usuariot.setEditable(true);
            usuariot.setText("");
            clave.setText("");
            JOptionPane.showMessageDialog(this, "Usuario o Clave incorrecta", "JCINFORM", JOptionPane.ERROR_MESSAGE);
            usuariot.requestFocusInWindow();


        }
    }

    public Boolean funcionValida(Tarjetas tarje) {
        verPanel();
        //VALIDO LA TARJETA QUE ESTE´HABILITADA Y ESTE EN LAS FECHAS ESTABELCIDAS
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
//                    errores.setText("<html>NO PUEDE INGRESAR EN ÉSTE DÌA</html>");
                    return false;
                }

                Date fecIn = tarje.getHorainicio();
                Date fecIn3 = tarje.getHorafin();
                LocalTime horaIni = new LocalTime(new DateTime(fecIn));
                LocalTime horaFin = new LocalTime(new DateTime(fecIn3));
                LocalTime ahora = new LocalTime(new DateTime(new Date()));
                if ((ahora.compareTo(horaIni) > 0 || ahora.compareTo(horaIni) == 0) && (ahora.compareTo(horaFin) < 0 || ahora.compareTo(horaFin) == 0)) {
                    System.out.println("EN EL RANGO DE HORA");
                    try {
                        return true;
                    } catch (Exception e) {
                        System.out.println("ERROR AL ABRIR PUERTA: " + e);
                    }

                } else {
                    //JOptionPane.showMessageDialog(getContentPane(), "No puede ingresar en este Horario...! \n Cliente: " + tarje.getCliente().getNombres(), "JCINFORM ", JOptionPane.ERROR_MESSAGE);
//                    errores.setText("<html>NO PUEDE INGRESAR EN ESTE HORARIO </html>");
                    return false;
                }
            } else {
                //JOptionPane.showMessageDialog(getContentPane(), "Su Fecha de tarjeta expiró...! \n Cliente: ", "JCINFORM ", JOptionPane.ERROR_MESSAGE);
//                errores.setText("<html>TARJETA EXPIRADA</html>");
                return false;
            }
//            cliente.setText(tarje.getClientes().getNombres());
        } else {
            //JOptionPane.showMessageDialog(getContentPane(), "Tarjeta INHABILITADA ...! \n Cliente: " + tarje.getCliente().getNombres(), "JCINFORM ", JOptionPane.ERROR_MESSAGE);
//            errores.setText("<html>TARJETA DESHABILITADA</html>");
            return false;
        }
//        errores.setText("<html>OK</html>");
        return true;
    }

    void cargarFoto(Integer codigoFactura) {
        try {
            miBotonImagen.setIcon(null);
            if (ubicacionDirectorio.contains("build")) {
                ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
            }
            String ubiv = ubicacionDirectorio + separador + "fotos" + separador + codigoFactura + ".jpg";
            Image image = Toolkit.getDefaultToolkit().getImage(ubiv);
            //Icon icon = new ImageIcon(image);
            ImageIcon tmpIconAux = new ImageIcon(image);
            ImageIcon tmpIcon = new ImageIcon(tmpIconAux.getImage().getScaledInstance(240, -1, Image.SCALE_DEFAULT));
            miBotonImagen.setIcon(tmpIcon);
        } catch (Exception e) {
            System.out.println("NO SE CARGO LA FOTO...");
        }

    }

    
 

    public void buscarTarjeta(String puertoViene) {
//        final frmPrincipal pra = this;
        miBotonImagen.setIcon(null);
        if (puertoViene.length() > 10) {
            puertoViene = puertoViene.substring(0, 10);
        }
        String tipoIngreso = entradaosalida(puertoViene);
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
               
            } catch (Exception ex) {
                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                lger.logger(frmPrincipal.class.getName(), ex + "");
            }

            procesando.setVisible(false);

//            taskTarjeta.setCollapsed(true);
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        }
    }

    public void buscarTarjetaValidarSalida(String puertoViene, String noticket) {//VALIDO LA SALIDA CON CODIGO DE  BARRAS
//        final frmPrincipal pra = this;
        if (puertoViene.length() > 10) {
            puertoViene = puertoViene.substring(0, 10);
        }
        if (noticket.length() > 10) {
            noticket = noticket.substring(0, 10);
        }
        try {
            try {
                verPanel();
//                errores.setText("");
                //EN CASO DE QUE TODO ESTE CORRECTO PROCEDO A GUARDAR
                 noDisponibles();
            } catch (Exception ex) {
                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                lger.logger(frmPrincipal.class.getName(), ex + "");
            }
            procesando.setVisible(false);
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        }
    }

    public void abrirPush(String puerta) {
        try {
            //        final frmPrincipal pra = this;
            if (puerta == null) {
                puerta = "1";
            }
            LeerTarjeta ta = (LeerTarjeta) puertoListo.get(0);
            ta.outputSream.write(puerta.getBytes());
            //TEMPORAL
            Thread.sleep(20);
            ta.outputSream.write(puerta.getBytes());
            Thread.sleep(20);
            ta.outputSream.write(puerta.getBytes());
            Thread.sleep(20);
            ta.outputSream.write(puerta.getBytes());
            Thread.sleep(20);
            ta.outputSream.write(puerta.getBytes());
            Thread.sleep(20);
            ta.outputSream.write(puerta.getBytes());
            //TEMPORAL
            noDisponibles();
        } catch (InterruptedException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void imprimir(int cod, Empresa emp, int dias, Boolean mensual, Clientes cli) {
 
    }

    
    public long diferenciaFechas(Date fechai, Date fechaf) {
        fechaf = new Date();
        java.util.GregorianCalendar date1 = new java.util.GregorianCalendar(fechai.getYear(), fechai.getMonth(), fechai.getDate(), fechai.getHours(), fechai.getMinutes(), fechai.getSeconds());
        java.util.GregorianCalendar date2 = new java.util.GregorianCalendar(fechaf.getYear(), fechaf.getMonth(), fechaf.getDate(), fechaf.getHours(), fechaf.getMinutes(), fechaf.getSeconds());
        long difms = date2.getTimeInMillis() - date1.getTimeInMillis();
        long difd = difms / (1000 * 60);
        return difd;
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
        } else if (puertoqueViene.equals(empresaObj.getBarras())) {
            lapuertaaAbrir = empresaObj.getSale();
        } else if (puertoqueViene.equals(empresaObj.getBarras2())) {
            lapuertaaAbrir = empresaObj.getSale2();
        }
        System.out.println("INI: " + (new Date()));
//        AbrirPuerta.abrir(empresaObj.getPuerto(), lapuertaaAbrir);

        try {
            LeerTarjeta ta = (LeerTarjeta) puertoListo.get(0);
            ta.outputSream.write(lapuertaaAbrir.getBytes());
            //TEMPORAL
            Thread.sleep(20);
            ta.outputSream.write(lapuertaaAbrir.getBytes());
            Thread.sleep(20);
            ta.outputSream.write(lapuertaaAbrir.getBytes());
            Thread.sleep(20);
            ta.outputSream.write(lapuertaaAbrir.getBytes());
            Thread.sleep(20);
            ta.outputSream.write(lapuertaaAbrir.getBytes());
            Thread.sleep(20);
            ta.outputSream.write(lapuertaaAbrir.getBytes());
            
            
            System.out.println("ABRIR PUERTA TARJETA: "+lapuertaaAbrir);
            //TEMPORAL

//             try {
//                            Thread.sleep(1000);  // Me aseguro que es transmitido correctamente antes de cerrar
//                        } catch (Exception e) {
//                        } //ESPERO UN POCO
            //AbrirPuerta.abrir(empresaObj.getPuerto(), "1");
//            barrera1.setEnabled(true);
        } catch (InterruptedException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        }
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
                    + "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "' and o.ingresar = true ");
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

//            frmClientes.show();
            frmClientes1.setVisible(true);
            contenedor.requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        }

//        contenedor.requestFocus();
        //<property name="toplink.cache.type.default" value="NONE"/>

    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        try {
            // TODO add your handling code here:
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Operadores' " + "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "'  and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }

            frmOperadores usu = new frmOperadores(this, true, this, adm);
            usu.setSize(441, 445);
            usu.setLocation(0, 0);
            contenedor.add(usu);
            usu.show();
            contenedor.requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        }
//        contenedor.requestFocus();
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpresaActionPerformed
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Empresa' " + "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "' and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frmEmpresa usu = new frmEmpresa(this, true, this, adm);
            usu.setSize(462, 496);
            usu.setLocation(0, 0);
            contenedor.add(usu);

            usu.show();
            contenedor.requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        }
//        contenedor.requestFocus();
    }//GEN-LAST:event_btnEmpresaActionPerformed

    private void jXTaskPaneContainer1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jXTaskPaneContainer1KeyPressed
        // TODO add your handling code here:
//        if(evt.getKeyCode()== evt.VK_F2){
//            JOptionPane.showMessageDialog(this, "PRESIONO F2");
//        }
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_jXTaskPaneContainer1KeyPressed

    private void jSplitPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSplitPane1KeyPressed
        // TODO add your handling code here:
//        if(evt.getKeyCode()== evt.VK_F2){
//            JOptionPane.showMessageDialog(this, "PRESIONO F2");
//        }
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_jSplitPane1KeyPressed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Reportes' " + "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "' and o.ingresar = true  ");
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
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        }
//        contenedor.requestFocus();
    }//GEN-LAST:event_btnReportesActionPerformed

    private void mnAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAcercaActionPerformed
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Reportes' " + "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "' ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
            acerca usu = new acerca(this, true, empresaObj);
            usu.setSize(458, 239);
            usu.setLocation(0, 0);
            usu.show();
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        }

    }//GEN-LAST:event_mnAcercaActionPerformed

    private void nombresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombresKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            nombres.nextFocus();
        } else {

            tecla(evt.getKeyCode());
        }

}//GEN-LAST:event_nombresKeyPressed

    private void direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            direccion.nextFocus();
        } else {
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
        } else {
            tecla(evt.getKeyCode());
        }
}//GEN-LAST:event_codigoKeyPressed


    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        buscarClientes.setModal(true);
        buscarClientes.setSize(533, 300);
        buscarClientes.setLocation(0, 0);
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

    }

    public void limpiar() {
        clienteObj = new Clientes();
        bindingGroup.unbind();
        bindingGroup.bind();


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
                if (codigo.getText().isEmpty() || nombres.getText().trim().isEmpty() ) {
                    JOptionPane.showMessageDialog(this, "Registre los campos requeridos ...!");
                } else {
                    if (clienteObj == null) {
                        clienteObj = new Clientes();
                    }
                  

                    clienteObj.setDireccion(direccion.getText());
                    clienteObj.setNombres(nombres.getText());
                    clienteObj.setTelefono(telefono.getText());
                    clienteObj.setIdentificacion(codigo.getText());
                    if (modificar) {
                        try {
                            adm.actualizar(clienteObj);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en actualizar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            lger.logger(frmPrincipal.class.getName(), ex + "");
                            return;
                        }
                    } else {
                        try {

                            adm.guardar(clienteObj);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en guardar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            lger.logger(frmPrincipal.class.getName(), ex + "");
                            return;
                        }
                    }
    
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
                    auditar("Clientes", "" + clienteObj.getNombres(), "GUARDAR/ACTUALIZAR");

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
                    auditar("Clientes", "" + clienteObj.getNombres(), "ELIMINAR");
                    adm.eliminarObjeto(Clientes.class, clienteObj.getCodigo());
                    this.limpiar();
                } catch (Exception ex) {
                    Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    lger.logger(frmPrincipal.class.getName(), ex + "");
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }
}//GEN-LAST:event_btnEliminarActionPerformed

    private void codigoBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoBuscarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {

            Thread cargar = new Thread() {

                public void run() {
                    procesando.setVisible(true);


                    try {
                        List<Clientes> usuarios = adm.query("Select o from Clientes as o where o.nombres like '%" + codigoBuscar.getText().trim() + "%' ");
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
                        Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        lger.logger(frmPrincipal.class.getName(), ex + "");
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
                int fila = busquedaTabla.getSelectedRow();
                this.clienteObj = (Clientes) adm.buscarClave((Integer) busquedaTabla.getValueAt(fila, 0), Clientes.class);
   
                buscarClientes.dispose();
                bindingGroup.unbind();
                bindingGroup.bind();



            } catch (Exception ex) {
                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                lger.logger(frmPrincipal.class.getName(), ex + "");
            }
        }
        //        JOptionPane.showMessageDialog(this, usuarioObj);
}//GEN-LAST:event_busquedaTablaMouseClicked


    private void busquedaTablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaTablaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            try {
  
                int fila = busquedaTabla.getSelectedRow();
                this.clienteObj = (Clientes) adm.buscarClave((Integer) busquedaTabla.getValueAt(fila, 0), Clientes.class);
                buscarClientes.dispose();
                bindingGroup.unbind();
                bindingGroup.bind();
            } catch (Exception ex) {
                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                lger.logger(frmPrincipal.class.getName(), ex + "");
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

    private void btnAccesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccesosActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o "
                    + "where o.pantalla = 'Accesos' " + "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "'  and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frmPrivilegios usu = new frmPrivilegios(this, true, this, adm);
            usu.setSize(457, 449);

            usu.setLocation(0, 0);
            contenedor.add(usu);
            usu.show();
//              contenedor.add(usu);

        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        }
        contenedor.requestFocus();
    }//GEN-LAST:event_btnAccesosActionPerformed

    private void btnAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcercaActionPerformed
        // TODO add your handling code here:
        acerca ac = new acerca(this, true, empresaObj);
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
                    + "where o.pantalla = 'Reconfigurar' " + "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "'  and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);

                int seleccion = JOptionPane.showOptionDialog(this, "SE BORRARÁ LA CONFIGURACIÓN ACTUAL DEL SISTEMA \n ¿SEGURO QUE DESEA CONTINUAR?",
                        "JCINFORM", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, // null para icono por defecto.
                        new Object[]{"SI", "NO", "Cancelar"}, "NO");// null para YES, NO y CANCEL
                if (0 == seleccion) {
                    seleccion = JOptionPane.showOptionDialog(this, "¿ESTÁ UD. SEGURO QUE DESEA CONTINUAR SE BORRARÁ LA CONFIGURACIÓN? \n NO PODRÁ USAR EL SISTEMA, HASTA QUE VUELVA A CONFIGURARLO "
                            + "\\n ESTO TENDRÁ UN COSTO (USD) POR REINSTALACION, PARA SALIR PRESIONE ( NO ) ",
                            "JCINFORM", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, // null para icono por defecto.
                            new Object[]{"SI", "NO", "Cancelar"}, "NO");// null para YES, NO y CANCEL
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
                        this.dispose();

                        new frmConfiguracion().show();
                    }
                }

            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        }
        contenedor.requestFocus();

    }//GEN-LAST:event_btnReconfigurarActionPerformed

    private void tarjetatxtVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tarjetatxtVetoableChange
        // TODO add your handling code here:
        //            try {
        //            // TODO add your handling code here:
        //            Tarjetas tarje = (Tarjetas) adm.buscarClave(tarjeta.getText(), Tarjetas.class);
        //            cliente.setText(tarje.getCliente().getNombres());
        //
        //        } catch (Exception ex) {
        //            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);             lger.logger(frmPrincipal.class.getName(), ex+"");
        //        }
}//GEN-LAST:event_tarjetatxtVetoableChange

    private void tarjetatxtPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tarjetatxtPropertyChange
        //        try {
        //            // TODO add your handling code here:
        //            Tarjetas tarje = (Tarjetas) adm.buscarClave(tarjeta.getText(), Tarjetas.class);
        //            cliente.setText(tarje.getCliente().getNombres());
        //
        //        } catch (Exception ex) {
        //            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);             lger.logger(frmPrincipal.class.getName(), ex+"");
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
          

        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
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
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
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
//        sdfasd;
        KeyEvent evt = new KeyEvent(this, WIDTH, WIDTH, WIDTH, WIDTH);
        if (teclaPresionada == evt.VK_F8) {//ABRIR CLIENTES
            btnClientes.doClick();
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

    private void panelIngresoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_panelIngresoKeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_panelIngresoKeyPressed

    private void contenedor1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contenedor1KeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_contenedor1KeyPressed

    private void contenedor2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contenedor2KeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_contenedor2KeyPressed

    private void contenedor3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contenedor3KeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_contenedor3KeyPressed

    private void jLabel32KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel32KeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_jLabel32KeyPressed

    private void usuarioLogeadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usuarioLogeadoKeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_usuarioLogeadoKeyPressed

    private void btnAuditoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAuditoriaActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Auditoria' " + "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "'  and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }

            frmAuditoria usu = new frmAuditoria(this, true, this, adm);
            usu.setSize(747, 539);
            usu.setLocation(0, 0);
            contenedor.add(usu);
            usu.show();
            contenedor.requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        }
    }//GEN-LAST:event_btnAuditoriaActionPerformed

    private void btnAuditoria1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAuditoria1ActionPerformed
        // TODO add your handling code here:
        frmRespaldarBase.setVisible(true);
    }//GEN-LAST:event_btnAuditoria1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        JFileChooser f = new JFileChooser();
        //f.setDialogType(JFileChooser.OPEN_DIALOG);
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showOpenDialog(this);
        ubicacionArchivo.setText(f.getSelectedFile().getAbsolutePath());
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btnRespaldoWindowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRespaldoWindowsActionPerformed
        // TODO add your handling code here:
        if (ubicacionArchivo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una ubicación haciendo click en EXAMINAR");
            return;
        } else if (nombreArchivo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escriba un nombre para su respaldo");
            return;
        }
        respaldar();
    }//GEN-LAST:event_btnRespaldoWindowsActionPerformed

    private void btnRespaldoLinuxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRespaldoLinuxActionPerformed
        // TODO add your handling code here:
        if (ubicacionArchivo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una ubicación haciendo click en EXAMINAR");
            return;
        } else if (nombreArchivo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escriba un nombre para su respaldo");
            return;
        }
        respaldarLinux();
    }//GEN-LAST:event_btnRespaldoLinuxActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        frmRespaldarBase.setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void camaraVistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_camaraVistaKeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_camaraVistaKeyPressed

    private void usuarioLogeadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarioLogeadoActionPerformed
        // TODO add your handling code here:
        if (panelCambiar.isVisible()) {
            panelCambiar.setVisible(false);
        } else {
            panelCambiar.setVisible(true);
            claveActual.requestFocusInWindow();
        }

    }//GEN-LAST:event_usuarioLogeadoActionPerformed

    private void claveActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_claveActualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_claveActualActionPerformed

    private void guardarCambioClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarCambioClaveActionPerformed
        // TODO add your handling code here:

        String clave = cl.desencriptar(usuarioActual.getClave());
        String claveComprar = claveActual.getText().trim();
        if (clave.equals(claveComprar)) {
            if (nuevaClave.getText().equals(repiteClave.getText())) {
                try {
                    usuarioActual.setClave(cl.encriptar(nuevaClave.getText().trim()));
                    adm.actualizar(usuarioActual);
                    panelCambiar.setVisible(false);
                    claveActual.setText("");
                    repiteClave.setText("");
                    nuevaClave.setText("");

                } catch (Exception ex) {
                    Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    lger.logger(frmPrincipal.class.getName(), ex + "");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No coinciden las claves nueva y repeticion...!");


            }
        } else {
            JOptionPane.showMessageDialog(this, "La clave actual no coincide con su clave personal \n talvez Ud. no sea el propietario de ésta cuenta.");
            claveActual.setText("");
        }

    }//GEN-LAST:event_guardarCambioClaveActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        panelCambiar.setVisible(false);
        claveActual.setText("");
        repiteClave.setText("");
        nuevaClave.setText("");

    }//GEN-LAST:event_jButton7ActionPerformed

    private void camaraVista1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_camaraVista1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_camaraVista1KeyPressed

    private void botoninstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoninstActionPerformed
        // TODO add your handling code here:

        Thread cargar = new Thread() {

            public void run() {
                verIp.mostrarFotop(camaraVista1, empresaObj.getUrl());

            }
        };
        cargar.start();
    }//GEN-LAST:event_botoninstActionPerformed

    public void llenarCliente(Clientes nCliente) {


//        identificacion.setText(nCliente.getIdentificacion());
        //nombres1.setText(nCliente.getNombres());

//        direccion.setText(nCliente.getDireccion());
//        telefono.setText(nCliente.getTelefono());
    }
private void tempjButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tempjButton13ActionPerformed
// TODO add your handling code here:
    tarjetatxt.setText(tempnotarjetaTemp.getText());
    buscarTarjeta(temppuertoText.getSelectedItem().toString());
}//GEN-LAST:event_tempjButton13ActionPerformed

    private void btnTiposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTiposActionPerformed
        // TODO add your handling code here:
           try {
            // TODO add your handling code here:
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " +
                    "where o.pantalla = 'Tipos' " + 
                    "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "'  and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }

            frmTiposCalificadores usu = new frmTiposCalificadores(this, true, this, adm);
            usu.setSize(441, 445);
            usu.setLocation(0, 0);
            contenedor.add(usu);
            usu.show();
            contenedor.requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
        }
    }//GEN-LAST:event_btnTiposActionPerformed
    public void verPanel() {
        panelIngreso.setVisible(true);
//        Thread cargar = new Thread() {
//
//            public void run() {
//                try {
//
////                    sleep(40000);
////                    panelIngreso.setVisible(false);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);             lger.logger(frmPrincipal.class.getName(), ex+"");
//                }
//
//            }
//        };
//        cargar.start();

    }

    public void respaldar() {

        try {
//            System.out.println(""+System.getProperty("os.name"));
//            System.out.println(""+System.getProperty("os.arch"));
//            System.out.println(""+System.getProperty("os.version"));

            String sFichero = "C:\\WINDOWS\\system32\\mysqldump.exe";
            File fichero = new File(sFichero);
            if (!fichero.exists()) {
                JOptionPane.showMessageDialog(this, "No existe el archivo mysqldump.exe \n En el directorio  c:\\windows\\system32 \n copielo en el directorio indicado y vuelva a ejecutar");
                return;
            }
            String ip = UsuarioActivo.getIp();
            String direc = ubicacionArchivo.getText();

            String usuario = UsuarioActivo.getNombre();
            String clave = cl.desencriptar(UsuarioActivo.getContrasenia());

//            Date fechaActual = new Date();
//            String fec = "fecha" + fechaActual.getDate() + "-" + (fechaActual.getMonth() + 1) + "-" + (fechaActual.getYear() + 1900) + "_" + fechaActual.getHours() + "-" + fechaActual.getMinutes() + "-" + fechaActual.getSeconds() + "";
            File duir = new File(direc);
            duir.mkdir();
            Runtime.getRuntime().exec("cmd /c mysqldump -h " + ip + " --op -u " + usuario + " -p" + clave + " peaje > " + direc + "\\" + nombreArchivo.getText() + ".sql");
//            respaldo.value = direc + "\\academico" + fec + ".sql ";
            JOptionPane.showMessageDialog(this, "Respaldo realizado con Éxito ...! ");
        } catch (Exception e) {
            System.out.println("" + e);
            JOptionPane.showMessageDialog(this, "No se pudo realizar el Respaldo");
        }

    }
//

    public void respaldarLinux() {
        try {
            String ip = UsuarioActivo.getIp();
            String direc = ubicacionArchivo.getText();
            String usuario = UsuarioActivo.getNombre();
            String clave = cl.desencriptar(UsuarioActivo.getContrasenia());
            File duir = new File(direc);
            duir.mkdir();
            String ubicacion = direc + "/" + nombreArchivo.getText() + ".sql";
            String cnd = "mysqldump -h" + ip + " -u" + usuario + " -p" + clave + " -B peaje " + " -r" + ubicacion;
            StringTokenizer st = new StringTokenizer(cnd);
            String[] coma = new String[st.countTokens() + 1];
            int i = 0;
            while (st.hasMoreTokens()) {
                coma[i] = st.nextToken();
                System.out.println(coma[i] + " n:" + i);
                i++;
            }
            coma[7] = "-r" + ubicacion;
            Runtime.getRuntime().exec(coma);
//            respaldo.value = ubicacion;
//            alert("Respaldo realizado con Éxito en: " + ubicacion);
        } catch (Exception e) {
            System.out.println("" + e);
//            alert("No se pudo realizar el Respaldo");
        }

    }

    static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                frmPrincipal pr = new frmPrincipal();
                if (mostrar == true) {
                    pr.setVisible(true);
                } else {
                    pr.dispose();
                    pr.setVisible(false);
                    new frmConfiguracion().setVisible(true);
                }

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botoninst;
    private javax.swing.JButton btnAccesos;
    private javax.swing.JButton btnAcerca;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAuditoria;
    private javax.swing.JButton btnAuditoria1;
    private javax.swing.JButton btnAyuda;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEmpresa;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnReconfigurar;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnRespaldoLinux;
    private javax.swing.JButton btnRespaldoWindows;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalir2;
    private javax.swing.JButton btnTipos;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JDialog buscarClientes;
    private javax.swing.JTable busquedaTabla;
    private javax.swing.JLabel camaraVista;
    public javax.swing.JLabel camaraVista1;
    private javax.swing.JPasswordField clave;
    private javax.swing.JPasswordField claveActual;
    private javax.swing.JFormattedTextField codigo;
    private javax.swing.JFormattedTextField codigoBuscar;
    public javax.swing.JDesktopPane contenedor;
    private org.jdesktop.swingx.JXTaskPane contenedor1;
    private org.jdesktop.swingx.JXTaskPane contenedor2;
    private org.jdesktop.swingx.JXTaskPane contenedor3;
    private javax.swing.JFormattedTextField direccion;
    private javax.swing.JInternalFrame frmClientes1;
    private javax.swing.JInternalFrame frmIngresarSistema;
    private javax.swing.JInternalFrame frmRespaldarBase;
    private javax.swing.JButton guardarCambioClave;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JLabel logoTeka;
    private javax.swing.JLabel miBotonImagen;
    private javax.swing.JTextField nombreArchivo;
    private javax.swing.JFormattedTextField nombres;
    private javax.swing.JPasswordField nuevaClave;
    private javax.swing.JPanel panelCambiar;
    private javax.swing.JPanel panelIngreso;
    private javax.swing.JFormattedTextField placa;
    public javax.swing.JButton procesando;
    private javax.swing.JPasswordField repiteClave;
    public javax.swing.JFormattedTextField tarjetatxt;
    private javax.swing.JFormattedTextField telefono;
    private javax.swing.JButton tempjButton13;
    private javax.swing.JTextField tempnotarjetaTemp;
    private javax.swing.JComboBox temppuertoText;
    private javax.swing.JTextField ubicacionArchivo;
    private javax.swing.JLabel ultimoIngreso;
    private javax.swing.JButton usuarioLogeado;
    private javax.swing.JFormattedTextField usuariot;
    private javax.swing.JLabel webTeka;
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

    public void auditar(String tabla, String campo, String accion) {
        try {
            Auditoria aud = new Auditoria();
            aud.setAccion(accion);
            aud.setCampo(campo);
            aud.setTabla(tabla);
            aud.setFecha(new Date());
            java.net.InetAddress i = java.net.InetAddress.getLocalHost();
            aud.setMaquina(System.getProperty("user.name") + " IP: " + i.getHostAddress());
            aud.setUsuarios(usuarioActual);
            adm.guardar(aud);
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmPrincipal.class.getName(), ex + "");
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

    public static String convertiraString(Date fecha) {

        return (fecha.getYear() + 1900) + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();

    }
//       
//    void funcionSiSale(Tarjetas tarje, String puertoViene) {
//
//        verPanel();
//        if (tarje.getClientes().getCodigo().intValue() > 1) {
//            try {
//                //VALIDO LA TARJETA QUE ESTE´HABILITADA Y ESTE EN LAS FECHAS ESTABELCIDAS
//                Date fechaActual = new Date();
//                Boolean habilitada = tarje.getHabilitada();
//                int diaActual = fechaActual.getDay(); //1=Domingo, 2=Lunes 3=Martes,4=Miercoles,5=Jueves,6=Viernes
//                DateTime desde = new DateTime(tarje.getDesde());
//                DateTime hasta = new DateTime(tarje.getHasta());
//                DateTime fechaAct = new DateTime(fechaActual);
//                Boolean continua = false;
//                List<Factura> facturas = adm.query(fechaActual, tarje.getTarjeta());
//                if (habilitada) {
//                    if (tipoIngreso.equals("e")) {//ENTRANDO
//                        if (facturas.size() >= tarje.getIngresos().intValue()) {
//                            errores.setText("<html>ERROR: TARJETA YA HA SIDO USADA...!</html>");
//                            imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
//                            return;
//                        }
//                    }
//                    if ((fechaAct.compareTo(desde) > 0 || fechaAct.compareTo(desde) == 0) && (fechaAct.compareTo(hasta) < 0 || fechaAct.compareTo(hasta) == 0)) {
//                        System.out.println("EN EL RANGO DE FECHAS");
//
//                        if (diaActual == 0) {
//                            if (tarje.getDomingo()) {
//                                continua = true;
//                            }
//                        } else if (diaActual == 1) {
//                            if (tarje.getLunes()) {
//                                continua = true;
//                            }
//                        } else if (diaActual == 2) {
//                            if (tarje.getMartes()) {
//                                continua = true;
//                            }
//                        } else if (diaActual == 3) {
//                            if (tarje.getMiercoles()) {
//                                continua = true;
//                            }
//                        } else if (diaActual == 4) {
//                            if (tarje.getJueves()) {
//                                continua = true;
//                            }
//                        } else if (diaActual == 5) {
//                            if (tarje.getViernes()) {
//                                continua = true;
//                            }
//                        } else if (diaActual == 6) {
//                            if (tarje.getSabado()) {
//                                continua = true;
//                            }
//                        }
//                        if (continua == false) {
//                            //JOptionPane.showMessageDialog(getContentPane(), "Día NO hábil para ingresar ...! \n Cliente: " + tarje.getCliente().getNombres(), "JCINFORM ", JOptionPane.ERROR_MESSAGE);
//                            errores.setText("<html>NO PUEDE INGRESAR EN ÉSTE DÌA</html>");
//                            imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
//                            return;
//                        }
//
//
//                        Date fecIn = tarje.getHorainicio();
//                        Date fecIn3 = tarje.getHorafin();
//                        LocalTime horaIni = new LocalTime(new DateTime(fecIn));
//                        LocalTime horaFin = new LocalTime(new DateTime(fecIn3));
//                        LocalTime ahora = new LocalTime(new DateTime(new Date()));
//                        if ((ahora.compareTo(horaIni) > 0 || ahora.compareTo(horaIni) == 0) && (ahora.compareTo(horaFin) < 0 || ahora.compareTo(horaFin) == 0)) {
//                            System.out.println("EN EL RANGO DE HORA");
//                            try {
//
//                                abrirPuerta(puertoViene);
//
//                            } catch (Exception e) {
//                                System.out.println("PUERTO:" + puertoViene);
//                                System.out.println("ERROR AL ABRIR PUERTA: " + e);
//                            }
//
//                        } else {
//                            //JOptionPane.showMessageDialog(getContentPane(), "No puede ingresar en este Horario...! \n Cliente: " + tarje.getCliente().getNombres(), "JCINFORM ", JOptionPane.ERROR_MESSAGE);
//                            errores.setText("<html>NO PUEDE INGRESAR EN ESTE HORARIO </html>");
//                            imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
//                            return;
//                        }
//
//                    } else {
//                        //JOptionPane.showMessageDialog(getContentPane(), "Su Fecha de tarjeta expiró...! \n Cliente: ", "JCINFORM ", JOptionPane.ERROR_MESSAGE);
//                        errores.setText("<html>TARJETA EXPIRADA</html>");
//                        imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
//                        return;
//                    }
//
//    //                        taskTarjeta.setCollapsed(false);
//                    procesando.setVisible(true);
//                    cliente.setText(tarje.getClientes().getNombres());
//                } else {
//                    //JOptionPane.showMessageDialog(getContentPane(), "Tarjeta INHABILITADA ...! \n Cliente: " + tarje.getCliente().getNombres(), "JCINFORM ", JOptionPane.ERROR_MESSAGE);
//                    errores.setText("<html>TARJETA DESHABILITADA</html>");
//                    imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
//                }
//                errores.setText("<html>OK</html>");
//                imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
//
//
//
//                Factura fac = new Factura();
//                if (tipoIngreso.equals("e")) {//ENTRANDO
//                    //if (facturas.size() > 0) {
//                    if (facturas.size() >= tarje.getIngresos().intValue()) {
//                        errores.setText("<html>ERROR: TARJETA YA HA SIDO USADA...!</html>");
//                        imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
//                        return;
//                    } else {
//                        fac.setPlaca("CLIENTE TARJETA");
//                        fac.setFechaini(new Date());
//                        fac.setFecha(new Date());
//                        fac.setTarjetas(tarje);
//                        fac.setTicket(null);
//                        adm.guardar(fac);
//                        llenarFechayHora(fac, "no");
//                        errores.setText("<html>ENTRADA OK...!</html>");
//                        imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
//                    }
//                } else {        //SALIENDO
//
//                    if (facturas.size() > 0) {
//                        fac = facturas.get(0);
//                        fac.setTarjetas(tarje);
//                        fac.setPlaca("CLIENTE TARJETA");
//                        fac.setFechafin(new Date());
//                        fac = calcularTiempo(fac);
//                        fac.setSubtotal(new BigDecimal(0));
//                        fac.setTotal(new BigDecimal(0));
//                        fac.setIva(new BigDecimal(0));
//                        fac.setClientes(tarje.getClientes());
//                        adm.actualizar(fac);
//
//                        llenarFechayHora(fac, "nuevo");
//                        errores.setText("<html>SALIDA OK...!</html>)");
//                        imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
//
//                    } else {
//                        String mensaje = "";
//                        List<Factura> facturasCompro = adm.query(fechaActual, tarje.getTarjeta());
//                        if (facturasCompro.size() >= tarje.getIngresos().intValue()) {
//                            System.out.println("SALE SIN MARCAR ENTRADA CON TARJETA YA USADA POR OTRA PERSONA");
//                            mensaje = "<html>ERROR: NO REGISTRÓ LA ENTRADA, Y LA TARJETA YA HA SIDO USADA HOY, ...!</html>";
//                            errores.setText(mensaje);
//                            imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
//                            // return;
//                        }
//                        fac.setPlaca("CLIENTE TARJETA");
//                        fac.setFechaini(new Date()); //ENTRTA Y SALE, EN CASO DE NO REGISTRAR
//                        fac.setFechafin(new Date()); //ENTRTA Y SALE, EN CASO DE NO REGISTRAR
//                        fac.setFecha(new Date()); //ENTRTA Y SALE, EN CASO DE NO REGISTRAR
//                        fac.setTarjetas(tarje);
//
//    //                                fac.set       fac.setTicket(null);
//                        adm.guardar(fac);
//                        llenarFechayHora(fac, "no");
//                        if (mensaje.equals("")) {
//                            errores.setText("<html>OK...!  (No paso antes por el ingreso)</html>");
//                        }
//                        if (mensaje.equals("")) {
//                            imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
//                        }
//                    }
//                }
//
//
//
//
//                noDisponibles();
//            } catch (Exception ex) {
//                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else {//EN EL CASO DE QUE SEA CONSUMIDOR FINAL Y PARA TARJETAS DE USUARIOS ESPORADICOS
//            //CLIENTES QUE TIENEN TARJETA **********************************************************************************************
//
//            errores.setText("<html>OK</html>");
//            imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
////                        String tipoIngreso = entradaosalida(puertoViene);
//            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
//            //EN CASO DE QUE TODO ESTE CORRECTO PROCEDO A GUARDAR
//            List<Factura> facturas = adm.query("Select o from Factura as o where o.tarjetas.tarjeta = '" + tarje.getTarjeta() + "' "
//                    + "and o.fechafin is null  ");
//            Factura fac = new Factura();
//            if (tipoIngreso.equals("e")) {//ESTA ENTRANDO
//                if (facturas.size() > 0) {
//                    errores.setText("<html>ERROR: OTRO VEHÍCULO YA HA INGRESADO CON ESA TARJETA...!</html>");
//                    imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
//                    return;
//                } else {
//                    fac.setFechaini(new Date());
//                    fac.setFecha(new Date());
//                    fac.setTarjetas(tarje);
//                    fac.setPlaca("NO CLIENTE TARJETA");
//                    fac.setTicket(null);
//                    fac.setClientes(new Clientes(1));
//                    adm.guardar(fac);
//                    llenarFechayHora(fac, "no");
//                    errores.setText("<html>ENTRADA OK...!</html>");
//                    imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
//                    imprimir(fac.getCodigo(), emp, fac.getDias(), false, fac.getClientes());
//                }
//            } else {//ESTA SALIENDO
//
//                if (facturas.size() > 0) {
//
//                    fac = facturas.get(0);
//                    fac.setPlaca("NO CLIENTE TARJETA");
//                    fac.setClientes(new Clientes(1));//CARGO EL CONSUMIDOR FINAL
//                    fac.setFechafin(new Date());
//                    fac.setTarjetas(tarje);
//                    fac = calcularTiempo(fac);
//                    fac.setNumero(emp.getDocumentofac());
////                                fac.setClientes(new Clientes(1));
//                    adm.actualizar(fac);
//                    Integer numero = new Integer(emp.getDocumentofac());
//                    emp.setDocumentofac((numero + 1) + "");
//                    adm.actualizar(emp);
//
//                    llenarFechayHora(fac, "nuevo");
//                    errores.setText("<html>SALIDA OK...!</html>");
//                    imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
//                    imprimir(fac.getCodigo(), emp, fac.getDias(), false, fac.getClientes());
//
//                } else {
//                    fac.setPlaca(placa.getText());
//                    fac.setFechaini(new Date()); //ENTRTA Y SALE, EN CASO DE NO REGISTRAR
//                    fac.setFechafin(new Date()); //ENTRTA Y SALE, EN CASO DE NO REGISTRAR
//                    fac.setPlaca("NO CLIENTE TARJETA");
//                    fac.setFecha(new Date()); //ENTRTA Y SALE, EN CASO DE NO REGISTRAR
//                    fac.setTarjetas(tarje);
//                    fac = calcularTiempo(fac);
//                    fac.setClientes(new Clientes(1));
//                    fac.setTicket(null);
//                    fac.setNumero(emp.getDocumentofac());
////                                fac.set
//                    adm.guardar(fac);
//
//                    Integer numero = new Integer(emp.getDocumentofac());
//                    emp.setDocumentofac((numero + 1) + "");
//                    adm.actualizar(emp);
//
//                    llenarFechayHora(fac, "no");
//                    errores.setText("<html>OK...!  (NO SE REGISTRO EL INGRESO)");
//                    imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
//                    imprimir(fac.getCodigo(), emp, fac.getDias(), false, fac.getClientes());
//                }
//            }
//            noDisponibles();
//
//
//
//        }
//
//    }
       private DefaultListModel charla = new DefaultListModel();
    //COMUNICACIÓN 
        public void ServidorChat()    {
        try
        {
            ServerSocket socketServidor = new ServerSocket(5557);
            while (true)
            {
                Socket cliente = socketServidor.accept();
                Runnable nuevoCliente = new HiloDeCliente(charla, cliente,this);
                Thread hilo = new Thread(nuevoCliente);
                hilo.start();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
}
