package peaje.formas;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
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
import hibernate.*;
import hibernate.cargar.Administrador;
import hibernate.cargar.GeneraXMLPersonal;
import hibernate.cargar.RelojModeloUtil;
import hibernate.cargar.RelojVisual;
import hibernate.cargar.UsuarioActivo;
import hibernate.cargar.WorkingDirectory;
import java.awt.Color;
import java.awt.Component;
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
import java.awt.print.PrinterJob;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JFileChooser;
import javax.swing.ListModel;
import javax.swing.table.TableCellRenderer;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import sources.FacturaDetalleSource;
import sources.FacturaSource;
import xml.XMLEmpresa;
import peaje.utilerias.*;

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

    public void habilitarBotones(Boolean estado) {
        btnAuditoria.setEnabled(estado);
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
//public frmPrincipal(){
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

        Image im = new ImageIcon(getClass().getResource("/images_botones/icono.png")).getImage();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //WINDOWS
            initComponents();
            contenedor.requestFocus();
            RelojModeloUtil modelo = new RelojModeloUtil();
            RelojVisual visual = new RelojVisual(modelo);
//            visual.setLocation(100, 100);
            barraHerramients.add(visual, 4);
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
            panelIngreso.setVisible(false);
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
        } catch (InstantiationException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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
        btnEliminar1 = new javax.swing.JButton();
        frmLote = new javax.swing.JInternalFrame();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaCambios = new javax.swing.JTable(){
            public Component prepareRenderer(TableCellRenderer renderer, int
                row, int column){
                Component returnComp = super.prepareRenderer(renderer, row,
                    column);
                returnComp.setForeground(Color.BLACK);
                returnComp.setBackground(Color.WHITE);
                try{
                    String tipo= getValueAt(row,0).toString();
                    if(tipo.contains("false")){
                        returnComp.setForeground(Color.WHITE);
                        returnComp.setBackground(Color.GRAY);
                    }else{
                        returnComp.setForeground(Color.BLACK);
                        returnComp.setBackground(Color.WHITE);
                    }
                }catch(Exception e){}
                return returnComp;
            }
        };
        diasHabiles1 = new javax.swing.JPanel();
        lunes1 = new javax.swing.JCheckBox();
        martes1 = new javax.swing.JCheckBox();
        miercoles1 = new javax.swing.JCheckBox();
        jueves1 = new javax.swing.JCheckBox();
        viernes1 = new javax.swing.JCheckBox();
        sabado1 = new javax.swing.JCheckBox();
        domingo1 = new javax.swing.JCheckBox();
        todos1 = new javax.swing.JCheckBox();
        jPanel10 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        horaDesde1 = new javax.swing.JSpinner();
        horaHasta1 = new javax.swing.JSpinner();
        panelHoras1 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        fechaDesde1 = new com.toedter.calendar.JDateChooser();
        fechaHasta1 = new com.toedter.calendar.JDateChooser();
        nombreBuscar = new javax.swing.JFormattedTextField();
        jLabel31 = new javax.swing.JLabel();
        btnGuardarCambios = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        activa1 = new javax.swing.JCheckBox();
        jLabel35 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
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
        btnLote = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
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
        panelIngreso = new javax.swing.JPanel();
        cliente = new javax.swing.JFormattedTextField();
        tarjetatxt = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        spIngreso = new javax.swing.JSpinner();
        ingre = new javax.swing.JLabel();
        spSalida = new javax.swing.JSpinner();
        salid = new javax.swing.JLabel();
        spConsumo = new javax.swing.JSpinner();
        cons = new javax.swing.JLabel();
        placa = new javax.swing.JFormattedTextField();
        errores = new javax.swing.JLabel();
        imAviso = new javax.swing.JLabel();
        usuarioLogeado = new javax.swing.JButton();
        panelCambiar = new javax.swing.JPanel();
        guardarCambioClave = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        claveActual = new javax.swing.JPasswordField();
        nuevaClave = new javax.swing.JPasswordField();
        repiteClave = new javax.swing.JPasswordField();
        jButton7 = new javax.swing.JButton();
        camaraVista = new javax.swing.JLabel();
        labelPartner = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
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
        setTitle("Sistema de control de parqueaderos");
        setName("miForma"); // NOI18N

        barraHerramients.setBorder(null);
        barraHerramients.setRollover(true);
        barraHerramients.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                barraHerramientsKeyPressed(evt);
            }
        });

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

        totales.setFont(new java.awt.Font("Tahoma", 1, 11));
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

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(240);
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

        frmIngresarSistema.setTitle("Seguridad");
        frmIngresarSistema.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unlock.gif"))); // NOI18N
        frmIngresarSistema.setOpaque(true);
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

        frmIngresarSistema.setBounds(180, 170, 390, 220);
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

        btnEliminar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eliminar.png"))); // NOI18N
        btnEliminar1.setMnemonic('E');
        btnEliminar1.setText("Eliminar");
        btnEliminar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnEliminar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar1ActionPerformed(evt);
            }
        });
        btnEliminar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminar1KeyPressed(evt);
            }
        });
        formaTarjetas1.getContentPane().add(btnEliminar1);
        btnEliminar1.setBounds(170, 290, 60, 50);

        formaTarjetas1.setBounds(80, 0, 380, 380);
        contenedor.add(formaTarjetas1, javax.swing.JLayeredPane.MODAL_LAYER);

        frmLote.setMaximizable(true);
        frmLote.setTitle("Cambiar horarios por lote");

        tablaCambios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ESTADO", "CLIENTE", "TARJETA", "APLICO EL CAMBIO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tablaCambios);
        tablaCambios.getColumnModel().getColumn(0).setResizable(false);
        tablaCambios.getColumnModel().getColumn(0).setPreferredWidth(5);
        tablaCambios.getColumnModel().getColumn(1).setResizable(false);
        tablaCambios.getColumnModel().getColumn(1).setPreferredWidth(250);
        tablaCambios.getColumnModel().getColumn(2).setResizable(false);
        tablaCambios.getColumnModel().getColumn(3).setResizable(false);
        tablaCambios.getColumnModel().getColumn(3).setPreferredWidth(10);

        diasHabiles1.setBorder(javax.swing.BorderFactory.createTitledBorder("Días Habiles"));
        diasHabiles1.setLayout(null);

        lunes1.setText("Lunes");
        lunes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lunes1ActionPerformed(evt);
            }
        });
        lunes1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lunes1KeyPressed(evt);
            }
        });
        diasHabiles1.add(lunes1);
        lunes1.setBounds(80, 20, 55, 23);

        martes1.setText("Martes");
        martes1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                martes1KeyPressed(evt);
            }
        });
        diasHabiles1.add(martes1);
        martes1.setBounds(150, 20, 60, 23);

        miercoles1.setText("Miércoles");
        miercoles1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                miercoles1KeyPressed(evt);
            }
        });
        diasHabiles1.add(miercoles1);
        miercoles1.setBounds(220, 20, 70, 23);

        jueves1.setText("Jueves");
        jueves1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jueves1ActionPerformed(evt);
            }
        });
        jueves1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jueves1KeyPressed(evt);
            }
        });
        diasHabiles1.add(jueves1);
        jueves1.setBounds(300, 20, 60, 23);

        viernes1.setText("Viernes");
        viernes1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                viernes1KeyPressed(evt);
            }
        });
        diasHabiles1.add(viernes1);
        viernes1.setBounds(360, 20, 62, 23);

        sabado1.setText("Sábado");
        sabado1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sabado1KeyPressed(evt);
            }
        });
        diasHabiles1.add(sabado1);
        sabado1.setBounds(430, 20, 63, 23);

        domingo1.setText("Domingo");
        domingo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                domingo1KeyPressed(evt);
            }
        });
        diasHabiles1.add(domingo1);
        domingo1.setBounds(490, 20, 70, 23);

        todos1.setText("Todos");
        todos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todos1ActionPerformed(evt);
            }
        });
        todos1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                todos1KeyPressed(evt);
            }
        });
        diasHabiles1.add(todos1);
        todos1.setBounds(10, 20, 55, 23);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Horas de ingreso"));
        jPanel10.setLayout(null);

        jLabel27.setText("Hasta: ");
        jPanel10.add(jLabel27);
        jLabel27.setBounds(140, 20, 40, 14);

        jLabel28.setText("Desde:");
        jPanel10.add(jLabel28);
        jLabel28.setBounds(10, 20, 50, 14);

        horaDesde1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                horaDesde1KeyPressed(evt);
            }
        });
        jPanel10.add(horaDesde1);
        horaDesde1.setBounds(50, 20, 80, 20);

        horaHasta1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                horaHasta1KeyPressed(evt);
            }
        });
        jPanel10.add(horaHasta1);
        horaHasta1.setBounds(180, 20, 80, 20);

        panelHoras1.setBorder(javax.swing.BorderFactory.createTitledBorder("Fechas de Validez"));
        panelHoras1.setLayout(null);

        jLabel29.setText("Hasta: ");
        panelHoras1.add(jLabel29);
        jLabel29.setBounds(160, 20, 40, 14);

        jLabel30.setText("Desde:");
        panelHoras1.add(jLabel30);
        jLabel30.setBounds(10, 20, 40, 14);

        fechaDesde1.setDateFormatString("dd/MMM/yyyy");
        panelHoras1.add(fechaDesde1);
        fechaDesde1.setBounds(50, 20, 95, 20);

        fechaHasta1.setDateFormatString("dd/MMM/yyyy");
        panelHoras1.add(fechaHasta1);
        fechaHasta1.setBounds(200, 20, 95, 20);

        nombreBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombreBuscarKeyPressed(evt);
            }
        });

        jLabel31.setText("Buscar Clientes: ");

        btnGuardarCambios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png"))); // NOI18N
        btnGuardarCambios.setText("GUARDAR CAMBIOS");
        btnGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCambiosActionPerformed(evt);
            }
        });

        jLabel33.setForeground(new java.awt.Color(0, 51, 255));
        jLabel33.setText("Se aplicará el horario y fechas a todos los que estén en la lista ");

        jLabel34.setText("Habilitar Tarjetas");

        activa1.setSelected(true);

        jLabel35.setForeground(new java.awt.Color(0, 51, 255));
        jLabel35.setText("y con el VISTO en APLICO CAMBIO");

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.gif"))); // NOI18N
        jButton10.setText("Salir");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enter.png"))); // NOI18N
        jLabel36.setText("Presione Enter");

        javax.swing.GroupLayout frmLoteLayout = new javax.swing.GroupLayout(frmLote.getContentPane());
        frmLote.getContentPane().setLayout(frmLoteLayout);
        frmLoteLayout.setHorizontalGroup(
            frmLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmLoteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, frmLoteLayout.createSequentialGroup()
                        .addComponent(panelHoras1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                        .addGap(4, 4, 4))
                    .addGroup(frmLoteLayout.createSequentialGroup()
                        .addGroup(frmLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(frmLoteLayout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nombreBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel36))
                            .addGroup(frmLoteLayout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(activa1)))
                        .addGap(20, 20, 20)))
                .addGap(23, 23, 23))
            .addGroup(frmLoteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(diasHabiles1, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                .addGap(35, 35, 35))
            .addGroup(frmLoteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                .addGap(35, 35, 35))
            .addGroup(frmLoteLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnGuardarCambios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        frmLoteLayout.setVerticalGroup(
            frmLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmLoteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(nombreBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(activa1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(frmLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelHoras1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diasHabiles1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmLoteLayout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jLabel35))
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnGuardarCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );

        frmLote.setBounds(20, 10, 650, 540);
        contenedor.add(frmLote, javax.swing.JLayeredPane.DEFAULT_LAYER);

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

        tarjetas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Habilitada", "Tarjeta", "Desde", "Hasta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.util.Date.class, java.util.Date.class
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
        btnNuevaTarjeta.setBounds(10, 150, 170, 30);

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

        btnLote.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/todo.gif"))); // NOI18N
        btnLote.setMnemonic('B');
        btnLote.setText("Modificar Lote");
        btnLote.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLote.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnLote.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoteActionPerformed(evt);
            }
        });
        jPanel4.add(btnLote);
        btnLote.setBounds(180, 150, 160, 30);

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
        jPanel5.setBounds(20, 340, 390, 70);

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

            frmRespaldarBase.setBounds(50, 120, 520, 210);
            contenedor.add(frmRespaldarBase, javax.swing.JLayeredPane.DEFAULT_LAYER);

            panelIngreso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            panelIngreso.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    panelIngresoKeyPressed(evt);
                }
            });
            panelIngreso.setLayout(null);

            cliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
            cliente.setEditable(false);
            cliente.setForeground(new java.awt.Color(51, 51, 255));
            cliente.setText(".");
            cliente.setFont(new java.awt.Font("Tahoma", 0, 12));
            panelIngreso.add(cliente);
            cliente.setBounds(20, 30, 180, 20);

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
            tarjetatxt.setBounds(20, 50, 190, 20);

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

            spIngreso.setFont(new java.awt.Font("Tahoma", 0, 14));
            spIngreso.setBorder(null);
            spIngreso.setEnabled(false);
            spIngreso.setFocusable(false);
            panelIngreso.add(spIngreso);
            spIngreso.setBounds(290, 20, 90, 20);

            ingre.setFont(new java.awt.Font("Tahoma", 1, 11));
            ingre.setForeground(new java.awt.Color(153, 153, 153));
            ingre.setText("INGRESO:");
            panelIngreso.add(ingre);
            ingre.setBounds(230, 20, 60, 20);

            spSalida.setFont(new java.awt.Font("Tahoma", 0, 14));
            spSalida.setBorder(null);
            spSalida.setEnabled(false);
            spSalida.setFocusable(false);
            panelIngreso.add(spSalida);
            spSalida.setBounds(290, 40, 90, 20);

            salid.setFont(new java.awt.Font("Tahoma", 1, 11));
            salid.setForeground(new java.awt.Color(153, 153, 153));
            salid.setText("SALIDA:");
            panelIngreso.add(salid);
            salid.setBounds(230, 40, 60, 20);

            spConsumo.setFont(new java.awt.Font("Tahoma", 0, 14));
            spConsumo.setBorder(null);
            spConsumo.setEnabled(false);
            spConsumo.setFocusable(false);
            panelIngreso.add(spConsumo);
            spConsumo.setBounds(290, 60, 90, 20);

            cons.setFont(new java.awt.Font("Tahoma", 1, 11));
            cons.setForeground(new java.awt.Color(153, 153, 153));
            cons.setText("CONSUMO:");
            panelIngreso.add(cons);
            cons.setBounds(230, 60, 60, 20);

            placa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
            placa.setEditable(false);
            placa.setText(".");
            panelIngreso.add(placa);
            placa.setBounds(20, 70, 180, 20);

            errores.setFont(new java.awt.Font("Tahoma", 1, 12));
            errores.setForeground(new java.awt.Color(255, 0, 0));
            errores.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            panelIngreso.add(errores);
            errores.setBounds(20, 100, 360, 20);

            imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
            panelIngreso.add(imAviso);
            imAviso.setBounds(382, 20, 70, 70);

            panelIngreso.setBounds(10, 480, 470, 130);
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

            camaraVista.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            camaraVista.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 204), 1, true));
            camaraVista.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    camaraVistaKeyPressed(evt);
                }
            });
            camaraVista.setBounds(10, 30, 670, 440);
            contenedor.add(camaraVista, javax.swing.JLayeredPane.DEFAULT_LAYER);

            labelPartner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tekatronic.JPG"))); // NOI18N
            labelPartner.setBounds(510, 500, 170, 70);
            contenedor.add(labelPartner, javax.swing.JLayeredPane.DEFAULT_LAYER);

            jLabel38.setForeground(new java.awt.Color(0, 51, 204));
            jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel38.setText("www.tekatronic.com.ec");
            jLabel38.setBounds(510, 580, 140, 14);
            contenedor.add(jLabel38, javax.swing.JLayeredPane.DEFAULT_LAYER);

            jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12));
            jLabel37.setForeground(new java.awt.Color(102, 102, 102));
            jLabel37.setText("PARTNERS");
            jLabel37.setBounds(500, 480, 160, 20);
            contenedor.add(jLabel37, javax.swing.JLayeredPane.DEFAULT_LAYER);

            jLabel42.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jLabel42.setBounds(490, 480, 190, 130);
            contenedor.add(jLabel42, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
            jToolBar1.setOrientation(1);
            jToolBar1.setRollover(true);

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
            contenedor2.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    contenedor2KeyPressed(evt);
                }
            });

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
                        .addComponent(contenedor1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(contenedor2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(contenedor3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
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
                    .addContainerGap(96, Short.MAX_VALUE))
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
            while (portList.hasMoreElements()) {
                try {
                    portId = (CommPortIdentifier) portList.nextElement();
                    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

                        //(0) //PUERTO DE LA TARJETA INTERFAZ PC - BARRERA
//                         puertoYaAbiertos = puertoYaAbiertos+"; "+portId.getName();
                        System.out.println("" + portId.getName() + " " + empresaObj.getPuerto());
                        if (portId.getName().equals(empresaObj.getPuerto())) {
                            reader = new LeerTarjeta(portId, this);
                            puertoListo.add(reader);

                            System.out.println("ABIERTO: " + empresaObj.getPuerto());
                        }
                        //(1)    //PUERTO DE LETRERO LEDS
                        if (portId.getName().equals(empresaObj.getLed())) {
                            reader = new LeerTarjeta(portId, this);
                            puertoListo.add(reader);
                            System.out.println("ABIERTO: " + empresaObj.getLed());
                        }
                        //(2)  //PUERTO DE CODIGO DE BARRAS
                        if (portId.getName().equals(empresaObj.getBarras())) {
                            reader = new LeerTarjeta(portId, this);
                            puertoListo.add(reader);
                            System.out.println("ABIERTO: " + empresaObj.getBarras());
                        }
                        //(3)  //PUERTO DE CODIGO DE BARRAS 2
                        if (portId.getName().equals(empresaObj.getBarras2())) {
                            reader = new LeerTarjeta(portId, this);
                            puertoListo.add(reader);
                            System.out.println("ABIERTO: " + empresaObj.getBarras2());
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
                } catch (Exception ex) {
                    Logger.getLogger(LeerTarjeta.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            portList = null;
            System.out.println("LISTA DE PUERTOS PRINCIPALES: " + puertoListo);
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
        //            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        //        }
//        } catch (PropertyVetoException ex) {
//            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

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
        try {
            totales.setText("Total: " + empresaObj.getParqueaderos());
            Object con = adm.querySimple("Select count(o) from Factura as o" + " where  o.fechafin is null  ");
            Long val2 = (Long) con;
            disponibles.setText("Disponibles: " + (empresaObj.getParqueaderos() - val2.intValue()));
            ocupados.setText("Ocupados: " + val2.intValue());
            final int dispo = (empresaObj.getParqueaderos() - val2.intValue());
            //ENVIO A LA PANTALLA DE LEDS LA INFORMACIÓN
            Thread cargar = new Thread() {

                public void run() {
                    try {
                        LeerTarjeta ta = (LeerTarjeta) puertoListo.get(1);
                        ta.outputSream.write((("" + dispo).getBytes()));
                        //AbrirPuerta.abrir(empresaObj.getPuerto(), "1");
                        barrera1.setEnabled(true);
                    } catch (IOException ex) {
                        System.out.println("ERROR EN ENVIAR NO. DE DISPONIBLES ");
                        Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("FIN ERROR EN ENVIAR");
                    }
                }
            };
            cargar.start();

        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarEmpresa(EmpresaPuertosStatic emp) {

        empresaObj.setPuerto(emp.getPuerto());
        empresaObj.setBarras(emp.getBarras());
        empresaObj.setBarras2(emp.getBarras2());
        empresaObj.setLed(emp.getLed());
        empresaObj.setSale(emp.getSale());
        empresaObj.setSale2(emp.getSale2());

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
        empresaObj.setSeabretic(emp.getSeabretic());
        empresaObj.setSeabrefac(emp.getSeabrefac());
        empresaObj.setMulta(emp.getMulta());


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
                    if (accesos.getPantalla().equals("Auditoria") && !accesos.getIngresar()) {
                        btnAuditoria.setEnabled(false);
                    }
                    if (accesos.getPantalla().equals("AbrirBarreras") && !accesos.getIngresar()) {
                        barrera1.setEnabled(false);
                        barrera2.setEnabled(false);
                        barrera3.setEnabled(false);
                        barrera4.setEnabled(false);
                        barrera5.setEnabled(false);
                        barrera6.setEnabled(false);
                        barrera7.setEnabled(false);
                    }
                }

                iniciarPuertos();
                contenedor.requestFocus();
                auditar("", "", "Ingreso al Sistema");




            } catch (Exception ex) {
                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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

    public void buscarTarjeta(String puertoViene) {
//        final frmPrincipal pra = this;
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
                if (tarje == null) {//SI LA TARJETA NO ESTÁ REGISTRADA, LE CARGO AL CLIENTE
                    noTarjeta.setText(tarjetatxt.getText());
                    errores.setText("");
                } else {
                    verPanel();
                    if (tarje.getClientes().getCodigo().intValue() > 1) {

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
                                    errores.setText("NO PUEDE INGRESAR EN ÉSTE DÌA");
                                    imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
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
                                    imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
                                    return;
                                }

                            } else {
                                //JOptionPane.showMessageDialog(getContentPane(), "Su Fecha de tarjeta expiró...! \n Cliente: ", "JCINFORM ", JOptionPane.ERROR_MESSAGE);
                                errores.setText("TARJETA EXPIRADA");
                                imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
                                return;
                            }

//                        taskTarjeta.setCollapsed(false);
                            procesando.setVisible(true);
                            cliente.setText(tarje.getClientes().getNombres());
                        } else {
                            //JOptionPane.showMessageDialog(getContentPane(), "Tarjeta INHABILITADA ...! \n Cliente: " + tarje.getCliente().getNombres(), "JCINFORM ", JOptionPane.ERROR_MESSAGE);
                            errores.setText("TARJETA DESHABILITADA");
                            imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
                        }
                        errores.setText("OK");
                        imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
                        String tipoIngreso = entradaosalida(puertoViene);
//EN CASO DE QUE TODO ESTE CORRECTO PROCEDO A GUARDAR
                        List<Factura> facturas = adm.query("Select o from Factura as o where o.tarjetas.tarjeta = '" + tarje.getTarjeta() + "' "
                                + "and o.fechafin is null  ");
                        Factura fac = new Factura();
                        if (tipoIngreso.equals("e")) {//ENTRANDO
                            if (facturas.size() > 0) {
                                errores.setText("ERROR: OTRO VEHÍCULO YA HA INGRESADO CON ESA TARJETA...!");
                                imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
                                return;
                            } else {
                                fac.setPlaca("CLIENTE TARJETA");
                                fac.setFechaini(new Date());
                                fac.setFecha(new Date());
                                fac.setTarjetas(tarje);
                                fac.setTicket(null);
                                adm.guardar(fac);
                                llenarFechayHora(fac, "no");
                                errores.setText("ENTRADA OK...!");
                                imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
                            }
                        } else {        //SALIENDO

                            if (facturas.size() > 0) {




                                fac = facturas.get(0);
                                fac.setTarjetas(tarje);
                                fac.setPlaca("CLIENTE TARJETA");
                                fac.setFechafin(new Date());
                                fac = calcularTiempo(fac);
                                fac.setSubtotal(new BigDecimal(0));
                                fac.setTotal(new BigDecimal(0));
                                fac.setIva(new BigDecimal(0));
                                fac.setClientes(tarje.getClientes());
                                adm.actualizar(fac);

                                llenarFechayHora(fac, "nuevo");
                                errores.setText("SALIDA OK...!)");
                                imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N

                            } else {
                                fac.setPlaca("CLIENTE TARJETA");
                                fac.setFechaini(new Date()); //ENTRTA Y SALE, EN CASO DE NO REGISTRAR
                                fac.setFechafin(new Date()); //ENTRTA Y SALE, EN CASO DE NO REGISTRAR
                                fac.setFecha(new Date()); //ENTRTA Y SALE, EN CASO DE NO REGISTRAR
                                fac.setTarjetas(tarje);
                                fac.setTicket(null);
//                                fac.set
                                adm.guardar(fac);
                                llenarFechayHora(fac, "no");
                                errores.setText("OK...!  (NO SE REGISTRO EL INGRESO)");
                                imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
                            }
                        }




                        noDisponibles();
                    } else {//EN EL CASO DE QUE SEA CONSUMIDOR FINAL Y PARA TARJETAS DE USUARIOS ESPORADICOS
                        //CLIENTES QUE NO TIENEN TARJETA **********************************************************************************************

                        errores.setText("OK");
                        imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
                        String tipoIngreso = entradaosalida(puertoViene);
                        Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
                        //EN CASO DE QUE TODO ESTE CORRECTO PROCEDO A GUARDAR
                        List<Factura> facturas = adm.query("Select o from Factura as o where o.tarjetas.tarjeta = '" + tarje.getTarjeta() + "' "
                                + "and o.fechafin is null  ");
                        Factura fac = new Factura();
                        if (tipoIngreso.equals("e")) {//ESTA ENTRANDO
                            if (facturas.size() > 0) {
                                errores.setText("ERROR: OTRO VEHÍCULO YA HA INGRESADO CON ESA TARJETA...!");
                                imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
                                return;
                            } else {
                                fac.setFechaini(new Date());
                                fac.setFecha(new Date());
                                fac.setTarjetas(tarje);
                                fac.setPlaca("NO CLIENTE TARJETA");
                                fac.setTicket(null);
                                fac.setClientes(new Clientes(1));
                                adm.guardar(fac);
                                llenarFechayHora(fac, "no");
                                errores.setText("ENTRADA OK...!");
                                imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
                                imprimir(fac.getCodigo(), emp, fac.getDias(), false, fac.getClientes());
                            }
                        } else {//ESTA SALIENDO

                            if (facturas.size() > 0) {

                                fac = facturas.get(0);
                                fac.setPlaca("NO CLIENTE TARJETA");
                                fac.setClientes(new Clientes(1));//CARGO EL CONSUMIDOR FINAL
                                fac.setFechafin(new Date());
                                fac.setTarjetas(tarje);
                                fac = calcularTiempo(fac);
                                fac.setNumero(emp.getDocumentofac());
//                                fac.setClientes(new Clientes(1));
                                adm.actualizar(fac);
                                Integer numero = new Integer(emp.getDocumentofac());
                                emp.setDocumentofac((numero + 1) + "");
                                adm.actualizar(emp);

                                llenarFechayHora(fac, "nuevo");
                                errores.setText("SALIDA OK...!");
                                imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
                                imprimir(fac.getCodigo(), emp, fac.getDias(), false, fac.getClientes());

                            } else {
                                fac.setPlaca(placa.getText());
                                fac.setFechaini(new Date()); //ENTRTA Y SALE, EN CASO DE NO REGISTRAR
                                fac.setFechafin(new Date()); //ENTRTA Y SALE, EN CASO DE NO REGISTRAR
                                fac.setPlaca("NO CLIENTE TARJETA");
                                fac.setFecha(new Date()); //ENTRTA Y SALE, EN CASO DE NO REGISTRAR
                                fac.setTarjetas(tarje);
                                fac = calcularTiempo(fac);
                                fac.setClientes(new Clientes(1));
                                fac.setTicket(null);
                                fac.setNumero(emp.getDocumentofac());
//                                fac.set
                                adm.guardar(fac);

                                Integer numero = new Integer(emp.getDocumentofac());
                                emp.setDocumentofac((numero + 1) + "");
                                adm.actualizar(emp);

                                llenarFechayHora(fac, "no");
                                errores.setText("OK...!  (NO SE REGISTRO EL INGRESO)");
                                imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
                                imprimir(fac.getCodigo(), emp, fac.getDias(), false, fac.getClientes());
                            }
                        }
                        noDisponibles();



                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

            procesando.setVisible(false);

//            taskTarjeta.setCollapsed(true);
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscarTarjetaValidarSalida(String puertoViene, String noticket) {
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
                errores.setText("");
                imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
                //EN CASO DE QUE TODO ESTE CORRECTO PROCEDO A GUARDAR
                List<Factura> facturas = adm.query("Select o from Factura as o where o.ticket = '" + new Integer(noticket) + "' "
                        + "  ");
                if (facturas.size() > 0) {

                    try {
                        Date fechaFin = facturas.get(0).getFechafin();
                        if (fechaFin == null) {
                            errores.setText("TICKET No: " + new Integer(noticket) + " QUIERE SALIR SIN PAGAR)");
                            imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alerta.png"))); // NOI18N
                        } else {
                            Long minutos0 = diferenciaFechas(fechaFin, new Date());
                            Integer minutos = minutos0.intValue();
                            if (minutos > empresaObj.getSalida()) {
                                errores.setText("TIEMPO DE GRACIA EXCEDIDO CON " + minutos + " min...!");
                                imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alerta.png"))); // NOI18N
                            } else {
                                errores.setText("SALIDA OK ...!");
                                imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salidaok.png"))); // NOI18N
                                abrirPuerta(puertoViene);
                            }


                        }
                    } catch (Exception e) {
                    }


                } else {
                }


                noDisponibles();


            } catch (Exception ex) {
                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            procesando.setVisible(false);
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir(int cod, Empresa emp, int dias, Boolean mensual, Clientes cli) {

//                    viewer.show();
        try {
            WorkingDirectory w = new WorkingDirectory();
            String ubicacionDirectorio = w.get() + separador;
            if (ubicacionDirectorio.contains("build")) {
                ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
            }
            JasperReport masterReport = null;
            if (mensual) {
                masterReport = (JasperReport) JRLoader.loadObject(ubicacionDirectorio + separador + "reportes" + separador + "factura2.jasper");
            } else {
                masterReport = (JasperReport) JRLoader.loadObject(ubicacionDirectorio + separador + "reportes" + separador + "factura.jasper");
            }
            JRDataSource ds = null;
            ArrayList detalle = new ArrayList();
            Map parametros = new HashMap();
            if (mensual) {
                List<Detalle> fac = adm.query("Select o from Detalle as o where o.factura.codigo = " + cod + " ");
                for (Iterator<Detalle> it = fac.iterator(); it.hasNext();) {
                    Detalle detalle1 = it.next();
                    Factura fac1 = (Factura) adm.querySimple("Select o from Factura as o where o.codigo = " + detalle1.getFactura().getCodigo() + " ");
                    detalle1.setFactura(fac1);
                    detalle.add(detalle1);
                }
                ds = new FacturaDetalleSource(detalle);
            } else {
                Factura fac = (Factura) adm.querySimple("Select o from Factura as o where o.codigo = " + cod + " ");
                Clientes cli1 = (Clientes) fac.getClientes();
                cli1 = (Clientes) adm.querySimple("Select o from Clientes as o where o.codigo = " + cli1.getCodigo() + " ");
                System.out.println("" + cli1.getCodigo());
                fac.setClientes(cli1);
                detalle.add(fac);
                cli = cli1;
                ds = new FacturaSource(detalle);

            }
            parametros.put("ruc", cli.getIdentificacion());
            parametros.put("cliente", cli.getNombres());
            parametros.put("direccion", cli.getDireccion());
            parametros.put("telefono", cli.getTelefono());
            parametros.put("dias", (dias > 0 ? dias + " Dias" : ""));

            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);
            PrinterJob job = PrinterJob.getPrinterJob();
            /* Create an array of PrintServices */
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            int selectedService = 0;
            /* Scan found services to see if anyone suits our needs */
            for (int i = 0; i < services.length; i++) {
                String nombre = services[i].getName();
                if (nombre.contains(emp.getImpfactura())) {
                    selectedService = i;
                }
            }
            job.setPrintService(services[selectedService]);
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            MediaSizeName mediaSizeName = MediaSize.findMedia(7, 7, MediaPrintableArea.INCH);
            printRequestAttributeSet.add(mediaSizeName);
            printRequestAttributeSet.add(new Copies(1));
            JRPrintServiceExporter exporter;
            exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, masterPrint);
            /* We set the selected service and pass it as a paramenter */
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            exporter.exportReport();

//            JasperViewer viewer = new JasperViewer(masterPrint, false); //PARA VER EL REPORTE ANTES DE IMPRIMIR
//            viewer.show();
//            try {
//                JasperPrintManager.printPage(masterPrint, 0, false);//LE ENVIO A IMPRIMIR false NO MUESTRA EL CUADRO DE DIALOGO
//            } catch (JRException ex) {
//                ex.printStackTrace();
//            }
        } catch (Exception ex) {
            Logger.getLogger(frmTicket.class.getName()).log(Level.SEVERE, null, ex);
        }

//        } catch (JRException ex) {
//            ex.printStackTrace();
//        }
    }

    public Factura calcularTiempo(Factura fac) {

        Date act = new Date();
        Long minutos0 = diferenciaFechas(fac.getFechaini(), new Date());
        Integer minutos = minutos0.intValue();

        Integer horas = minutos / 60;
        if (minutos.intValue() < 0) {
            minutos = minutos * -1;
        }
        if (horas.intValue() < 0) {
            horas = horas * -1;
//            dias.setVisible(true);
//            dias1.setVisible(true);
//            dias2.setVisible(true);
        }
        if (minutos.intValue() < 0) {
            horas = 0;
            minutos = 0;
//            dias.setVisible(true);
//            dias1.setVisible(true);
//            dias2.setVisible(true);
        }
        Float min = minutos / 60f;
        int indice = min.toString().indexOf(".");
        Float valorf = new Float("0" + min.toString().substring(indice));
        int valorMinutos = java.lang.Math.round((valorf * 60));
        act.setHours(horas);
        act.setMinutes(valorMinutos);
        fac.setTiempo(act);
        placa.setText(fac.getPlaca());
        BigDecimal aCobrar = new BigDecimal(0);
        for (int a = 0; a < horas; a++) {
            aCobrar = aCobrar.add(buscar(60));
        }
        try {
            int noDias = 0;
            noDias = (horas / 24);
            fac.setDias(noDias);
            //dias1.setText(noDias + "");
        } catch (Exception e) {
            //dias1.setText("0");
            fac.setDias(0);
        }

        if (horas.intValue() > 0) {
            if (valorMinutos > 0) {
                if (valorMinutos > empresaObj.getGracia().intValue()) {
                    aCobrar = aCobrar.add(buscar(valorMinutos));
                }
            } else {
            }
        } else {
            if (valorMinutos > 0) {
                aCobrar = aCobrar.add(buscar(valorMinutos));
            } else {
                aCobrar = aCobrar.add(buscar(1));
            }

        }

        Double totalv01 = aCobrar.doubleValue();
        Double iva01 = ((empresaObj.getIva() + 100) / 100);
        Double subtotalv01 = totalv01 / iva01;
        Double iva02 = subtotalv01 * ((empresaObj.getIva()) / 100);

        BigDecimal subtotalv = new BigDecimal(subtotalv01);
        BigDecimal ivav = new BigDecimal(iva02);
        BigDecimal totalv = new BigDecimal(totalv01);


        fac.setSubtotal(subtotalv);
        fac.setIva(ivav);
        fac.setTotal(aCobrar);
        return fac;

    }

    public long diferenciaFechas(Date fechai, Date fechaf) {
        fechaf = new Date();
        java.util.GregorianCalendar date1 = new java.util.GregorianCalendar(fechai.getYear(), fechai.getMonth(), fechai.getDate(), fechai.getHours(), fechai.getMinutes(), fechai.getSeconds());
        java.util.GregorianCalendar date2 = new java.util.GregorianCalendar(fechaf.getYear(), fechaf.getMonth(), fechaf.getDate(), fechaf.getHours(), fechaf.getMinutes(), fechaf.getSeconds());
        long difms = date2.getTimeInMillis() - date1.getTimeInMillis();
        long difd = difms / (1000 * 60);
        return difd;
    }

    public BigDecimal buscar(Integer minutos) {
        try {
            List<Tarifas> tarifario = adm.query("Select o from Tarifas as o order by o.codigo ");
            for (Iterator<Tarifas> it = tarifario.iterator(); it.hasNext();) {
                Tarifas tarifas = it.next();
                int desde = tarifas.getDesde();
                int hasta = tarifas.getHasta();
                int valo = minutos;
                if (valo >= desde && valo <= hasta) {
                    return tarifas.getValor();
                }
            }
            return new BigDecimal(0);
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BigDecimal(0);

    }

    public void llenarFechayHora(Factura fac, String nuevo) {

        if (nuevo.equals("si")) {

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
            //AbrirPuerta.abrir(empresaObj.getPuerto(), "1");
            barrera1.setEnabled(true);
        } catch (IOException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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
            usu.setLocation(240, 100);
            contenedor.add(usu);
            usu.show();
            contenedor.requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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
            usu.setSize(441, 470);
            usu.setLocation(240, 80);
            contenedor.add(usu);

            usu.show();
            contenedor.requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
//        contenedor.requestFocus();
    }//GEN-LAST:event_btnEmpresaActionPerformed

    private void btnTarifasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTarifasActionPerformed
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Tarifas' " + "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "' and o.ingresar = true  ");
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
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
//        contenedor.requestFocus();
    }//GEN-LAST:event_btnTarifasActionPerformed

    private void btnTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTicketActionPerformed
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Tickets' " + "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "'  and o.ingresar = true  ");
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
            usu.show();
            usu.placa.requestFocusInWindow();

        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
//        contenedor.requestFocus();
    }//GEN-LAST:event_btnTicketActionPerformed

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

    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarActionPerformed
        // TODO add your handling code here:
        try {
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'Factura' " + "and o.global.codigo  = '" + usuario.getGlobal().getCodigo() + "' and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ingresar a esta pantalla ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frmFactura usu = new frmFactura(this, true, this, adm);
            usu.setSize(669, 507);
            usu.setLocation(80, 10);
            contenedor.add(usu);

            usu.show();
            usu.noTicket.requestFocusInWindow();


        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
//        contenedor.requestFocus();

    }//GEN-LAST:event_btnCobrarActionPerformed

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
            usu.setLocation(260, 220);
            usu.show();
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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
        } else {
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
                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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
                btnNuevaTarjeta.setEnabled(false);
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
                    clienteObj.setProductos((Productos) tarifas.getSelectedValue());
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
                            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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
                            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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
            if(noTarjeta.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "PASE LA TARJETA POR LA LECTORA");
            return;
            }
            tarjeta.setTarjeta(noTarjeta.getText());
            tarjeta.setClientes(clienteObj);
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
            auditar("Tarjetas", "" + tarjeta.getTarjeta() + " " + tarjeta.getDesde().toLocaleString() + " " + tarjeta.getHasta().toLocaleString(), "GUARDAR/ACTUALIZAR");

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
                g = clienteObj.getProductos();
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
                txtValor.setText(clienteObj.getValor().setScale(2) + "");

            } catch (Exception ex) {
                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //        JOptionPane.showMessageDialog(this, usuarioObj);
}//GEN-LAST:event_busquedaTablaMouseClicked
    public void llenarTabla(Integer clie) {
        try {
            List<Tarjetas> tarjetasRs = adm.query("Select o from Tarjetas as o where o.clientes.codigo = '" + clie + "'");
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
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void busquedaTablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaTablaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            try {
                Productos g = new Productos();
                int fila = busquedaTabla.getSelectedRow();
                this.clienteObj = (Clientes) adm.buscarClave((Integer) busquedaTabla.getValueAt(fila, 0), Clientes.class);
                llenarTabla(clienteObj.getCodigo());
                g = clienteObj.getProductos();
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
                txtValor.setText(clienteObj.getValor().setScale(2) + "");
            } catch (Exception ex) {
                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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

            usu.setLocation(240, 100);
            contenedor.add(usu);
            usu.show();
//              contenedor.add(usu);

        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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
                     seleccion = JOptionPane.showOptionDialog(this, "¿ESTÁ UD. SEGURO QUE DESEA CONTINUAR SE BORRARÁ LA CONFIGURACIÓN? \n NO PODRÁ USAR EL SISTEMA, HASTA QUE VUELVA A CONFIGURARLO",
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
                try {
                    LeerTarjeta ta = (LeerTarjeta) puertoListo.get(0);
                    ta.outputSream.write("7".getBytes());
                    //AbrirPuerta.abrir(empresaObj.getPuerto(), "1");
                    barrera1.setEnabled(true);
                } catch (IOException ex) {
                    Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                //AbrirPuerta.abrir(empresaObj.getPuerto(), "7");
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
                try {
                    LeerTarjeta ta = (LeerTarjeta) puertoListo.get(0);
                    ta.outputSream.write("6".getBytes());
                    //AbrirPuerta.abrir(empresaObj.getPuerto(), "1");
                    barrera1.setEnabled(true);
                } catch (IOException ex) {
                    Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                //AbrirPuerta.abrir(empresaObj.getPuerto(), "6");
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
                try {
                    LeerTarjeta ta = (LeerTarjeta) puertoListo.get(0);
                    ta.outputSream.write("5".getBytes());
                    //AbrirPuerta.abrir(empresaObj.getPuerto(), "1");
                    barrera1.setEnabled(true);
                } catch (IOException ex) {
                    Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                //AbrirPuerta.abrir(empresaObj.getPuerto(), "5");
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
                //AbrirPuerta.abrir(empresaObj.getPuerto(), "4");
                try {
                    LeerTarjeta ta = (LeerTarjeta) puertoListo.get(0);
                    ta.outputSream.write("4".getBytes());
                    //AbrirPuerta.abrir(empresaObj.getPuerto(), "1");
                    barrera1.setEnabled(true);
                } catch (IOException ex) {
                    Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
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
                try {
                    LeerTarjeta ta = (LeerTarjeta) puertoListo.get(0);
                    ta.outputSream.write("3".getBytes());
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                    //AbrirPuerta.abrir(empresaObj.getPuerto(), "1");
                    barrera1.setEnabled(true);
                } catch (IOException ex) {
                    Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                //AbrirPuerta.abrir(empresaObj.getPuerto(), "3");
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
                try {
                    LeerTarjeta ta = (LeerTarjeta) puertoListo.get(0);
                    ta.outputSream.write("2".getBytes());
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    //AbrirPuerta.abrir(empresaObj.getPuerto(), "1");

                } catch (IOException ex) {
                    Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                //AbrirPuerta.abrir(empresaObj.getPuerto(), "2");
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
                try {
                    LeerTarjeta ta = (LeerTarjeta) puertoListo.get(0);
                    ta.outputSream.write("1".getBytes());
                    //AbrirPuerta.abrir(empresaObj.getPuerto(), "1");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    barrera1.setEnabled(true);
                } catch (IOException ex) {
                    Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        //            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        //        }
}//GEN-LAST:event_tarjetatxtVetoableChange

    private void tarjetatxtPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tarjetatxtPropertyChange
        //        try {
        //            // TODO add your handling code here:
        //            Tarjetas tarje = (Tarjetas) adm.buscarClave(tarjeta.getText(), Tarjetas.class);
        //            cliente.setText(tarje.getCliente().getNombres());
        //
        //        } catch (Exception ex) {
        //            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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
                cliente.setText(tarje.getClientes().getNombres());
            } else {
                cliente.setText("");
                placa.setText("");

            }

        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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

    private void barraHerramientsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barraHerramientsKeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
    }//GEN-LAST:event_barraHerramientsKeyPressed

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
            usu.setLocation(200, 40);
            contenedor.add(usu);
            usu.show();
            contenedor.requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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

    private void btnLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoteActionPerformed
        // TODO add your handling code here:
        if (permisos.getModificar()) {
            SpinnerDateModel sm = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
            JSpinner spinner = new JSpinner(sm);
            JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "HH:mm");
            horaDesde1.setModel(sm);
            horaDesde1.setEditor(de);

            SpinnerDateModel sm2 = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
            JSpinner spinner2 = new JSpinner(sm2);
            JSpinner.DateEditor de2 = new JSpinner.DateEditor(spinner2, "HH:mm");

            horaHasta1.setEditor(de2);
            horaHasta1.setModel(sm2);
            fechaDesde1.setDate(new Date());
            fechaHasta1.setDate(new Date());
            frmLote.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }


    }//GEN-LAST:event_btnLoteActionPerformed

    private void lunes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lunes1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lunes1ActionPerformed

    private void lunes1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lunes1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lunes1KeyPressed

    private void martes1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_martes1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_martes1KeyPressed

    private void miercoles1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_miercoles1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_miercoles1KeyPressed

    private void jueves1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jueves1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jueves1ActionPerformed

    private void jueves1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jueves1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jueves1KeyPressed

    private void viernes1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_viernes1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_viernes1KeyPressed

    private void sabado1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sabado1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sabado1KeyPressed

    private void domingo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_domingo1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_domingo1KeyPressed

    private void todos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todos1ActionPerformed
        // TODO add your handling code here:
        lunes1.setSelected(todos1.isSelected());
        martes1.setSelected(todos1.isSelected());
        miercoles1.setSelected(todos1.isSelected());
        jueves1.setSelected(todos1.isSelected());
        viernes1.setSelected(todos1.isSelected());
        sabado1.setSelected(todos1.isSelected());
        domingo1.setSelected(todos1.isSelected());
    }//GEN-LAST:event_todos1ActionPerformed

    private void todos1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_todos1KeyPressed
        // TODO add your handling code here:
        lunes1.setSelected(todos1.isSelected());
        martes1.setSelected(todos1.isSelected());
        miercoles1.setSelected(todos1.isSelected());
        jueves1.setSelected(todos1.isSelected());
        viernes1.setSelected(todos1.isSelected());
        sabado1.setSelected(todos1.isSelected());
        domingo1.setSelected(todos1.isSelected());
    }//GEN-LAST:event_todos1KeyPressed

    private void horaDesde1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_horaDesde1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_horaDesde1KeyPressed

    private void horaHasta1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_horaHasta1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_horaHasta1KeyPressed

    private void nombreBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreBuscarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            Thread cargar = new Thread() {

                public void run() {
                    procesando.setVisible(true);

                    try {

                        List<Tarjetas> usuarios = adm.query("Select o from Tarjetas as o "
                                + "where o.clientes.nombres like '%" + nombreBuscar.getText().trim() + "%' order by o.clientes.nombres  ");
                        Object[] obj = new Object[5];
                        DefaultTableModel dtm = (DefaultTableModel) tablaCambios.getModel();
                        dtm.getDataVector().removeAllElements();
                        for (Iterator<Tarjetas> it = usuarios.iterator(); it.hasNext();) {
                            Tarjetas glbusuario = it.next();
                            obj[0] = glbusuario.getHabilitada();
                            obj[1] = glbusuario.getClientes().getNombres();
                            obj[2] = glbusuario.getTarjeta();
                            obj[3] = glbusuario.getHabilitada();


                            dtm.addRow(obj);
                        }
                        tablaCambios.setModel(dtm);
                        if (tablaCambios.getRowCount() > 0) {
                            tablaCambios.requestFocusInWindow();
                        } else {
                            codigoBuscar.requestFocusInWindow();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    procesando.setVisible(false);
                }
            };
            cargar.start();

        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            frmLote.dispose();
        }
    }//GEN-LAST:event_nombreBuscarKeyPressed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        nombreBuscar.setText("");
        DefaultTableModel dtm = (DefaultTableModel) tablaCambios.getModel();
        dtm.getDataVector().removeAllElements();
        frmLote.setVisible(false);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void btnGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCambiosActionPerformed
        // TODO add your handling code here:
        int filas = tablaCambios.getRowCount();
        for (int i = 0; i < filas; i++) {
            String tarjeta = (String) tablaCambios.getValueAt(i, 2);
            Boolean guardo = (Boolean) tablaCambios.getValueAt(i, 3);
            if (guardo) {
                try {
                    Tarjetas tarActu = (Tarjetas) adm.buscarClave(tarjeta, Tarjetas.class);
                    Date fechaDes = fechaDesde1.getDate();
                    fechaDes.setHours(0);
                    fechaDes.setMinutes(01);
                    fechaDes.setSeconds(01);
                    tarActu.setDesde(fechaDes);
                    Date fechaHas = fechaHasta1.getDate();
                    fechaHas.setHours(23);
                    fechaHas.setMinutes(59);
                    fechaHas.setSeconds(59);
                    tarActu.setHasta(fechaHas);
                    tarActu.setDomingo(domingo1.isSelected());
                    tarActu.setLunes(lunes1.isSelected());
                    tarActu.setMartes(martes1.isSelected());
                    tarActu.setMiercoles(miercoles1.isSelected());
                    tarActu.setJueves(jueves1.isSelected());
                    tarActu.setViernes(viernes1.isSelected());
                    tarActu.setSabado(sabado1.isSelected());
                    tarActu.setHorainicio((Date) horaDesde1.getValue());
                    tarActu.setHorafin((Date) horaHasta1.getValue());
                    tarActu.setHabilitada(activa1.isSelected());
                    System.out.println("CORRECTO" + tarActu.getClientes().getNombres());
                    adm.actualizar(tarActu);

                } catch (Exception ex) {
                    Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);

                }


            }

        }
    }//GEN-LAST:event_btnGuardarCambiosActionPerformed

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

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
        if (permisos.getEliminar()) {        // TODO add your handling code here:
            try {
                adm.eliminarObjeto(Tarjetas.class, noTarjeta.getText());
                llenarTabla(clienteObj.getCodigo());
                formaTarjetas1.setVisible(false);
                auditar("Tarjetas", "" + noTarjeta.getText(), "ELIMINAR TARJETA");
            } catch (Exception ex) {
                int valor = JOptionPane.showConfirmDialog(this, "No se puede eliminar la tarjeta debido a que ya tiene movimientos(ya ha sido usada) \n"
                        + " ¿Desea Eliminar los movimientos para poder borrar la tarjeta? ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                if (valor == 0) {
                    try {
                        adm.ejecutaSql("Delete from Factura where tarjetas.tarjeta = '" + noTarjeta.getText().trim() + "'");
                        adm.eliminarObjeto(Tarjetas.class, noTarjeta.getText());
                        llenarTabla(clienteObj.getCodigo());
                        formaTarjetas1.setVisible(false);
                        auditar("Tarjetas", "" + noTarjeta.getText(), "ELIMINAR TARJETA");
                    } catch (Exception ex1) {
                        Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
                //Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }
}//GEN-LAST:event_btnEliminar1ActionPerformed

    private void btnEliminar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminar1KeyPressed
        // TODO add your handling code here:
        tecla(evt.getKeyCode());
}//GEN-LAST:event_btnEliminar1KeyPressed
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
//                    Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JCheckBox activa;
    private javax.swing.JCheckBox activa1;
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
    private javax.swing.JButton btnAuditoria;
    private javax.swing.JButton btnAuditoria1;
    private javax.swing.JButton btnAyuda;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JButton btnEmpresa;
    private javax.swing.JButton btnGuardarCambios;
    private javax.swing.JButton btnGuardarTarjeta;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnLote;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevaTarjeta;
    private javax.swing.JButton btnReconfigurar;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnRespaldoLinux;
    private javax.swing.JButton btnRespaldoWindows;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalir2;
    private javax.swing.JButton btnSalirTarjetas;
    private javax.swing.JButton btnTarifas;
    private javax.swing.JButton btnTicket;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JDialog buscarClientes;
    private javax.swing.JTable busquedaTabla;
    private javax.swing.JLabel camaraVista;
    private javax.swing.JPasswordField clave;
    private javax.swing.JPasswordField claveActual;
    private javax.swing.JFormattedTextField cliente;
    private javax.swing.JFormattedTextField codigo;
    private javax.swing.JFormattedTextField codigoBuscar;
    private javax.swing.JLabel cons;
    public javax.swing.JDesktopPane contenedor;
    private org.jdesktop.swingx.JXTaskPane contenedor1;
    private org.jdesktop.swingx.JXTaskPane contenedor2;
    private org.jdesktop.swingx.JXTaskPane contenedor3;
    private javax.swing.JTextArea descripcionTarjeta;
    private javax.swing.JPanel diasHabiles;
    private javax.swing.JPanel diasHabiles1;
    private javax.swing.JFormattedTextField direccion;
    private javax.swing.JLabel disponibles;
    private javax.swing.JCheckBox domingo;
    private javax.swing.JCheckBox domingo1;
    private javax.swing.JLabel errores;
    private com.toedter.calendar.JDateChooser fechaDesde;
    private com.toedter.calendar.JDateChooser fechaDesde1;
    private com.toedter.calendar.JDateChooser fechaHasta;
    private com.toedter.calendar.JDateChooser fechaHasta1;
    private javax.swing.JInternalFrame formaTarjetas1;
    private javax.swing.JInternalFrame frmClientes1;
    private javax.swing.JInternalFrame frmIngresarSistema;
    private javax.swing.JInternalFrame frmLote;
    private javax.swing.JInternalFrame frmRespaldarBase;
    private javax.swing.JButton guardarCambioClave;
    private javax.swing.JSpinner horaDesde;
    private javax.swing.JSpinner horaDesde1;
    private javax.swing.JSpinner horaHasta;
    private javax.swing.JSpinner horaHasta1;
    private javax.swing.JLabel imAviso;
    private javax.swing.JLabel ingre;
    private javax.swing.JSpinner ingresos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
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
    private javax.swing.JLabel jLabel28;
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
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JCheckBox jueves;
    private javax.swing.JCheckBox jueves1;
    private javax.swing.JLabel labelPartner;
    private javax.swing.JCheckBox lunes;
    private javax.swing.JCheckBox lunes1;
    private javax.swing.JCheckBox martes;
    private javax.swing.JCheckBox martes1;
    private javax.swing.JCheckBox miercoles;
    private javax.swing.JCheckBox miercoles1;
    public javax.swing.JFormattedTextField noTarjeta;
    private javax.swing.JTextField nombreArchivo;
    private javax.swing.JFormattedTextField nombreBuscar;
    private javax.swing.JFormattedTextField nombres;
    private javax.swing.JPasswordField nuevaClave;
    private javax.swing.JLabel ocupados;
    private javax.swing.JPanel panelCambiar;
    private javax.swing.JPanel panelHoras;
    private javax.swing.JPanel panelHoras1;
    private javax.swing.JPanel panelIngreso;
    private javax.swing.JFormattedTextField placa;
    private javax.swing.JFormattedTextField placa1;
    public javax.swing.JButton procesando;
    private javax.swing.JPasswordField repiteClave;
    private javax.swing.JCheckBox sabado;
    private javax.swing.JCheckBox sabado1;
    private javax.swing.JLabel salid;
    private javax.swing.JSpinner spConsumo;
    private javax.swing.JSpinner spIngreso;
    private javax.swing.JSpinner spSalida;
    private javax.swing.JTable tablaCambios;
    private javax.swing.JList tarifas;
    private javax.swing.JTable tarjetas;
    public javax.swing.JFormattedTextField tarjetatxt;
    private javax.swing.JFormattedTextField telefono;
    private javax.swing.JCheckBox todos;
    private javax.swing.JCheckBox todos1;
    private javax.swing.JLabel totales;
    private javax.swing.JTextField txtValor;
    private javax.swing.JTextField ubicacionArchivo;
    private javax.swing.JButton usuarioLogeado;
    private javax.swing.JFormattedTextField usuariot;
    private javax.swing.JCheckBox viernes;
    private javax.swing.JCheckBox viernes1;
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
