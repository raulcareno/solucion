package peaje.formas;

import hibernate.*;
import hibernate.cargar.WorkingDirectory;
import java.awt.Container;
import java.awt.print.PrinterJob;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import hibernate.cargar.Administrador;
import hibernate.cargar.validaciones;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import org.joda.time.DateTime;
import sources.FacturaDetalleSource;

import sources.FacturaSource;
//import org.eclipse.persistence.internal.history.HistoricalDatabaseTable;

/**
 *
 * @author geovanny
 */
public class frmFactura extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmEmpresa
     */
    public boolean grabar = false;
    public boolean modificar = false;
    public List lista = null;
    public int posicion = 0;
    public int tamano = 0;
    private Container desktopContenedor;
    private Empresa empresaObj;
    Administrador adm;
    private String claveActual;
    private validaciones val;
    private frmPrincipal principal;
    List<Tarifas> tarifario;
    String separador = File.separatorChar + "";
    Boolean guardando = false;
    WorkingDirectory w = new WorkingDirectory();
    String ubicacionDirectorio = w.get() + separador;

    /**
     * Creates new form frmProfesores
     */
    public frmFactura(java.awt.Frame parent, boolean modal, Administrador adm1) {
//        super(parent, modal);
        llenarCombo();
        initComponents();
        this.setSize(652, 587);
        empresaObj = new Empresa();
        adm = adm1;
        val = new validaciones();
        noTicket.requestFocusInWindow();
        dias.setVisible(false);
        dias1.setVisible(false);
        dias2.setVisible(false);
        panelencontrados.setVisible(false);
        panelencontrados1.setVisible(false);
        botonesVer.setVisible(false);
    }

    public frmFactura(java.awt.Frame parent, boolean modal, frmPrincipal lo, Administrador adm1) {
//        super(parent, modal);
        try {
            this.desktopContenedor = lo.contenedor;
            adm = adm1;
            llenarCombo();
            initComponents();
            this.setSize(652, 587);
            empresaObj = lo.empresaObj;
            val = new validaciones();
            principal = lo;

            tarifario = adm.query("Select o from Tarifas as o where o.hasta > 0 order by o.codigo ");
            dias.setVisible(false);
            dias1.setVisible(false);
            dias2.setVisible(false);
            panelencontrados.setVisible(false);
            panelencontrados1.setVisible(false);
            panelencontrados2.setVisible(false);
            panelencontrados3.setVisible(false);
            llenarProductos();
            noTicket.requestFocusInWindow();
            noTicket.requestFocusInWindow();
            noTicket.requestFocusInWindow();
            noTicket.requestFocusInWindow();
            noTicket.requestFocusInWindow();
            noTicket.requestFocusInWindow();
            noTicket.requestFocusInWindow();
            noTicket.requestFocusInWindow();
            int f = hastaF.getDate().getMonth() + 1;
            Date fefin = new Date();
            fefin.setMonth(f);
            hastaF.setDate(fefin);
            botonesVer.setVisible(false);

        } catch (Exception ex) {
            Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llenarCombo() {

        try {
            perfilesList = new ArrayList<Global>();
            perfilesList = adm.query("Select o from Global as o where o.grupo = 'PER'");
        } catch (Exception ex) {
            Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llenarProductos() {

        try {
            cmbProductos.removeAllItems();
            List produc = adm.query("Select o from Productos as o");
            for (Iterator it = produc.iterator(); it.hasNext();) {
                Productos object = (Productos) it.next();
                cmbProductos.addItem(object);

            }
        } catch (Exception ex) {
            Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// <editor-fold defaultstate="collapsed" desc="PROPIEDADES">
    public String getClaveActual() {
        return claveActual;
    }

    public void setClaveActual(String claveActual) {
        this.claveActual = claveActual;
    }

    public Empresa getUsuarioObj() {
        return empresaObj;
    }

    public void setUsuarioObj(Empresa empresaObj) {
        this.empresaObj = empresaObj;
    }
    private List<Global> perfilesList = new ArrayList<Global>();

    public List<Global> getPerfilesList() {
        return perfilesList;
    }

    public void setPerfilesList(List<Global> perfilesList) {
        this.perfilesList = perfilesList;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="FUNCIONES ACCIONES">
    public void habilitar(Boolean estado) {
    }

    public void limpiar() {
    }

    // </editor-fold >
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        formaBusqueda = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        codigoBuscar = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        busquedaTabla = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        panelencontrados1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        encontrados1 = new javax.swing.JList();
        ingreso = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        noTicket = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        salida = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        tiempo = new com.toedter.calendar.JDateChooser();
        placa = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        codigo = new javax.swing.JFormattedTextField();
        dias = new javax.swing.JLabel();
        dias1 = new javax.swing.JLabel();
        dias2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        panelencontrados = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        encontrados = new javax.swing.JList();
        telefono = new javax.swing.JFormattedTextField();
        identificacion = new javax.swing.JFormattedTextField();
        nombres = new javax.swing.JFormattedTextField();
        direccion = new javax.swing.JFormattedTextField();
        cliente = new javax.swing.JFormattedTextField();
        btnNuevoCliente = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        frmEliminar = new javax.swing.JInternalFrame();
        jLabel28 = new javax.swing.JLabel();
        frmGuardarAnulado = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        observacion = new javax.swing.JTextArea();
        frmAnular = new javax.swing.JInternalFrame();
        jLabel41 = new javax.swing.JLabel();
        frmGuardarAnulado1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        numeroIngresado = new javax.swing.JFormattedTextField();
        codigoAdministrador = new javax.swing.JPasswordField();
        cmbUsuarios = new javax.swing.JComboBox();
        jLabel44 = new javax.swing.JLabel();
        frmTarifa0 = new javax.swing.JInternalFrame();
        jLabel29 = new javax.swing.JLabel();
        guardarTarifa0 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        observacion1 = new javax.swing.JTextArea();
        botonesVer = new javax.swing.JPanel();
        btnAnularFactura = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnMulta = new javax.swing.JButton();
        btnAgregar2 = new javax.swing.JButton();
        btnAgregar3 = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        total = new javax.swing.JFormattedTextField();
        btnSalir = new javax.swing.JButton();
        descuento = new javax.swing.JFormattedTextField();
        jLabel33 = new javax.swing.JLabel();
        btnAplicarDscto = new javax.swing.JButton();
        chkEsNotaVenta = new javax.swing.JCheckBox();
        verBot = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        miBotonImagen = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        panelencontrados2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        encontrados2 = new javax.swing.JList();
        telefono1 = new javax.swing.JFormattedTextField();
        identificacion1 = new javax.swing.JFormattedTextField();
        nombres1 = new javax.swing.JFormattedTextField();
        direccion1 = new javax.swing.JFormattedTextField();
        cliente1 = new javax.swing.JFormattedTextField();
        btnNuevoCliente1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        productos = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        btnAgregar1 = new javax.swing.JButton();
        btnSalir1 = new javax.swing.JButton();
        txtTotal1 = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JFormattedTextField();
        txtIva = new javax.swing.JFormattedTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        observacionF = new javax.swing.JTextArea();
        hastaF = new com.toedter.calendar.JDateChooser();
        desdeF = new com.toedter.calendar.JDateChooser();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        cmbProductos = new javax.swing.JComboBox();
        btnAnadirProducto = new javax.swing.JButton();
        txtCantidad = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        panelencontrados3 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        encontrados3 = new javax.swing.JList();
        telefono2 = new javax.swing.JFormattedTextField();
        identificacion2 = new javax.swing.JFormattedTextField();
        nombres2 = new javax.swing.JFormattedTextField();
        direccion2 = new javax.swing.JFormattedTextField();
        cliente2 = new javax.swing.JFormattedTextField();
        btnNuevoCliente2 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        btnAgregar4 = new javax.swing.JButton();
        btnSalir2 = new javax.swing.JButton();
        txtTotal2 = new javax.swing.JFormattedTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        txtSubtotal1 = new javax.swing.JFormattedTextField();
        txtIva1 = new javax.swing.JFormattedTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        observacionF1 = new javax.swing.JTextArea();
        jScrollPane11 = new javax.swing.JScrollPane();
        ticketsPendientes = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        formaBusqueda.setLocationByPlatform(true);
        formaBusqueda.getContentPane().setLayout(null);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel9.setLayout(null);

        codigoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoBuscarKeyPressed(evt);
            }
        });
        jPanel9.add(codigoBuscar);
        codigoBuscar.setBounds(70, 10, 220, 20);

        jLabel18.setText("NOMBRES:");
        jPanel9.add(jLabel18);
        jLabel18.setBounds(10, 10, 70, 14);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enter.png"))); // NOI18N
        jLabel4.setText("Digite y luego Enter");
        jPanel9.add(jLabel4);
        jLabel4.setBounds(290, 10, 150, 22);

        formaBusqueda.getContentPane().add(jPanel9);
        jPanel9.setBounds(10, 10, 510, 40);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel10.setLayout(null);

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
        jScrollPane4.setViewportView(busquedaTabla);
        busquedaTabla.getColumnModel().getColumn(0).setPreferredWidth(0);
        busquedaTabla.getColumnModel().getColumn(0).setMaxWidth(0);

        jPanel10.add(jScrollPane4);
        jScrollPane4.setBounds(20, 20, 480, 150);

        formaBusqueda.getContentPane().add(jPanel10);
        jPanel10.setBounds(10, 60, 510, 180);

        setIconifiable(true);
        setTitle("Cobrar Ticket");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dinero.gif"))); // NOI18N
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel3.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/images_botones/fondoInicio.png")))); // NOI18N
        jPanel3.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Cobro de Tickets ..::..");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 270, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Registro el Numero de placa y presiona GUARDAR..::..");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 20, 290, 13);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 780, 40);

        jPanel5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel5KeyPressed(evt);
            }
        });
        jPanel5.setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        panelencontrados1.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.light"));
        panelencontrados1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelencontrados1.setLayout(null);

        encontrados1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Placas Ingresadas" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        encontrados1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        encontrados1.setAlignmentX(0.2F);
        encontrados1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                encontrados1MouseClicked(evt);
            }
        });
        encontrados1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                encontrados1KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(encontrados1);

        panelencontrados1.add(jScrollPane3);
        jScrollPane3.setBounds(10, 10, 180, 90);

        jPanel1.add(panelencontrados1);
        panelencontrados1.setBounds(70, 50, 200, 110);

        ingreso.setBackground(new java.awt.Color(255, 255, 255));
        ingreso.setDateFormatString("dd-MMM-yyyy HH:mm:ss");
        ingreso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(ingreso);
        ingreso.setBounds(70, 50, 210, 20);

        jLabel7.setForeground(new java.awt.Color(0, 0, 153));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("TICKET No: ");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 10, 60, 14);

        noTicket.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        noTicket.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                noTicketFocusGained(evt);
            }
        });
        noTicket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                noTicketKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                noTicketKeyReleased(evt);
            }
        });
        jPanel1.add(noTicket);
        noTicket.setBounds(70, 10, 90, 21);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 204));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Tiempo: ");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(20, 90, 50, 20);

        salida.setBackground(new java.awt.Color(255, 255, 255));
        salida.setDateFormatString("dd-MMM-yyyy HH:mm:ss");
        salida.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(salida);
        salida.setBounds(70, 70, 210, 20);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Ingreso: ");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(10, 50, 60, 20);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(204, 0, 0));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Salida: ");
        jPanel1.add(jLabel19);
        jLabel19.setBounds(20, 70, 50, 20);

        tiempo.setBackground(new java.awt.Color(255, 255, 255));
        tiempo.setDateFormatString("HH:mm");
        tiempo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(tiempo);
        tiempo.setBounds(70, 90, 110, 20);

        placa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                placaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                placaKeyReleased(evt);
            }
        });
        jPanel1.add(placa);
        placa.setBounds(70, 30, 80, 20);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Placa: ");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 30, 50, 14);

        codigo.setEditable(false);
        codigo.setBorder(null);
        codigo.setEnabled(false);
        codigo.setFont(new java.awt.Font("Tahoma", 0, 3)); // NOI18N
        jPanel1.add(codigo);
        codigo.setBounds(10, 30, 20, 20);

        dias.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        dias.setForeground(new java.awt.Color(0, 0, 204));
        dias.setText("DIA(s)");
        jPanel1.add(dias);
        dias.setBounds(130, 110, 60, 20);

        dias1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        dias1.setForeground(new java.awt.Color(204, 0, 0));
        dias1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dias1.setText("0");
        jPanel1.add(dias1);
        dias1.setBounds(80, 110, 40, 20);

        dias2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        dias2.setForeground(new java.awt.Color(0, 0, 204));
        dias2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dias2.setText("MAYOR A:");
        jPanel1.add(dias2);
        dias2.setBounds(10, 110, 60, 20);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enter.png"))); // NOI18N
        jLabel3.setText("Digite y luego Enter");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(160, 10, 130, 20);

        jLabel5.setText("En caso de pérdida Ticket");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(150, 30, 140, 20);

        jPanel5.add(jPanel1);
        jPanel1.setBounds(10, 10, 290, 160);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setLayout(null);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Teléfono: ");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(0, 70, 80, 20);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("CI/RUC: ");
        jPanel2.add(jLabel14);
        jLabel14.setBounds(0, 10, 80, 20);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Dirección: ");
        jPanel2.add(jLabel15);
        jLabel15.setBounds(0, 50, 80, 20);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Cliente: ");
        jPanel2.add(jLabel16);
        jLabel16.setBounds(0, 30, 80, 20);

        panelencontrados.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.light"));
        panelencontrados.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelencontrados.setLayout(null);

        encontrados.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Lista Clientes" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        encontrados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        encontrados.setAlignmentX(0.2F);
        encontrados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                encontradosMouseClicked(evt);
            }
        });
        encontrados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                encontradosKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(encontrados);

        panelencontrados.add(jScrollPane2);
        jScrollPane2.setBounds(10, 10, 230, 90);

        jPanel2.add(panelencontrados);
        panelencontrados.setBounds(80, 50, 250, 110);

        telefono.setEditable(false);
        telefono.setText("9999999999999");
        jPanel2.add(telefono);
        telefono.setBounds(80, 70, 140, 20);

        identificacion.setEditable(false);
        identificacion.setText("9999999999999");
        identificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identificacionActionPerformed(evt);
            }
        });
        identificacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                identificacionFocusLost(evt);
            }
        });
        identificacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                identificacionKeyPressed(evt);
            }
        });
        jPanel2.add(identificacion);
        identificacion.setBounds(80, 10, 110, 20);

        nombres.setEditable(false);
        nombres.setText("CONSUMIDOR FINAL");
        nombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombresKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nombresKeyReleased(evt);
            }
        });
        jPanel2.add(nombres);
        nombres.setBounds(80, 30, 250, 20);

        direccion.setEditable(false);
        direccion.setText("S/D");
        jPanel2.add(direccion);
        direccion.setBounds(80, 50, 190, 20);

        cliente.setEditable(false);
        cliente.setBorder(null);
        cliente.setText("1");
        cliente.setEnabled(false);
        cliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel2.add(cliente);
        cliente.setBounds(190, 10, 20, 20);

        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clientes.png"))); // NOI18N
        btnNuevoCliente.setText("CREAR O BUSCAR CLIENTE");
        btnNuevoCliente.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });
        jPanel2.add(btnNuevoCliente);
        btnNuevoCliente.setBounds(40, 100, 230, 30);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nuevo.gif"))); // NOI18N
        jButton3.setMargin(new java.awt.Insets(1, 1, 1, 1));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3);
        jButton3.setBounds(210, 10, 30, 23);

        jPanel5.add(jPanel2);
        jPanel2.setBounds(310, 10, 360, 160);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setLayout(null);

        frmEliminar.setTitle("Anular tickets");
        frmEliminar.getContentPane().setLayout(null);

        jLabel28.setText("Desea Anular el presente Ticket?, Porqué?");
        frmEliminar.getContentPane().add(jLabel28);
        jLabel28.setBounds(20, 0, 220, 30);

        frmGuardarAnulado.setText("SI");
        frmGuardarAnulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frmGuardarAnuladoActionPerformed(evt);
            }
        });
        frmEliminar.getContentPane().add(frmGuardarAnulado);
        frmGuardarAnulado.setBounds(50, 80, 60, 23);

        jButton2.setText("NO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        frmEliminar.getContentPane().add(jButton2);
        jButton2.setBounds(140, 80, 60, 23);

        observacion.setColumns(20);
        observacion.setRows(5);
        jScrollPane6.setViewportView(observacion);

        frmEliminar.getContentPane().add(jScrollPane6);
        jScrollPane6.setBounds(20, 30, 220, 40);

        jPanel4.add(frmEliminar);
        frmEliminar.setBounds(10, 0, 270, 140);

        frmAnular.setTitle("Anular tickets");
        frmAnular.setVisible(false);
        frmAnular.getContentPane().setLayout(null);

        jLabel41.setText("¿Desea Anular el presente FACTURA?");
        frmAnular.getContentPane().add(jLabel41);
        jLabel41.setBounds(20, 0, 220, 30);

        frmGuardarAnulado1.setText("SI");
        frmGuardarAnulado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frmGuardarAnulado1ActionPerformed(evt);
            }
        });
        frmAnular.getContentPane().add(frmGuardarAnulado1);
        frmGuardarAnulado1.setBounds(60, 110, 70, 30);

        jButton4.setText("NO");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        frmAnular.getContentPane().add(jButton4);
        jButton4.setBounds(140, 110, 60, 30);

        jLabel42.setText("Usuario: ");
        frmAnular.getContentPane().add(jLabel42);
        jLabel42.setBounds(10, 60, 50, 14);

        jLabel43.setText("# Factura: ");
        frmAnular.getContentPane().add(jLabel43);
        jLabel43.setBounds(10, 30, 90, 14);
        frmAnular.getContentPane().add(numeroIngresado);
        numeroIngresado.setBounds(70, 30, 120, 20);
        frmAnular.getContentPane().add(codigoAdministrador);
        codigoAdministrador.setBounds(60, 80, 210, 20);

        frmAnular.getContentPane().add(cmbUsuarios);
        cmbUsuarios.setBounds(60, 60, 210, 20);

        jLabel44.setText("Clave:");
        frmAnular.getContentPane().add(jLabel44);
        jLabel44.setBounds(10, 80, 50, 14);

        jPanel4.add(frmAnular);
        frmAnular.setBounds(10, 0, 300, 180);

        frmTarifa0.setTitle("Anular tickets");
        frmTarifa0.getContentPane().setLayout(null);

        jLabel29.setText("Va ha aplicar la Tarifa 0.0?, Escriba una razón");
        frmTarifa0.getContentPane().add(jLabel29);
        jLabel29.setBounds(20, 0, 220, 30);

        guardarTarifa0.setText("SI");
        guardarTarifa0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarTarifa0ActionPerformed(evt);
            }
        });
        frmTarifa0.getContentPane().add(guardarTarifa0);
        guardarTarifa0.setBounds(50, 80, 60, 23);

        jButton5.setText("NO");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        frmTarifa0.getContentPane().add(jButton5);
        jButton5.setBounds(140, 80, 60, 23);

        observacion1.setColumns(20);
        observacion1.setRows(5);
        jScrollPane7.setViewportView(observacion1);

        frmTarifa0.getContentPane().add(jScrollPane7);
        jScrollPane7.setBounds(20, 30, 220, 40);

        jPanel4.add(frmTarifa0);
        frmTarifa0.setBounds(10, 0, 270, 140);

        botonesVer.setBackground(new java.awt.Color(204, 204, 204));
        botonesVer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        botonesVer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                botonesVerFocusLost(evt);
            }
        });
        botonesVer.setLayout(new java.awt.GridLayout(5, 0));

        btnAnularFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_cancel.gif"))); // NOI18N
        btnAnularFactura.setText("Anular Fac.");
        btnAnularFactura.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAnularFactura.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAnularFactura.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnAnularFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularFacturaActionPerformed(evt);
            }
        });
        botonesVer.add(btnAnularFactura);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_cancel.gif"))); // NOI18N
        btnEliminar.setText("Anular Tick.");
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEliminar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btnEliminar.setOpaque(false);
        btnEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        botonesVer.add(btnEliminar);

        btnMulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/perdida.png"))); // NOI18N
        btnMulta.setMnemonic('G');
        btnMulta.setText("Multa");
        btnMulta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMulta.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnMulta.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnMulta.setOpaque(false);
        btnMulta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMultaActionPerformed(evt);
            }
        });
        btnMulta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnMultaKeyPressed(evt);
            }
        });
        botonesVer.add(btnMulta);

        btnAgregar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cero.png"))); // NOI18N
        btnAgregar2.setMnemonic('G');
        btnAgregar2.setText("Tarifa");
        btnAgregar2.setActionCommand("Tarifa 0.0");
        btnAgregar2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregar2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAgregar2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnAgregar2.setOpaque(false);
        btnAgregar2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgregar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregar2ActionPerformed(evt);
            }
        });
        btnAgregar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAgregar2KeyPressed(evt);
            }
        });
        botonesVer.add(btnAgregar2);

        btnAgregar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sello.png"))); // NOI18N
        btnAgregar3.setMnemonic('G');
        btnAgregar3.setText("Sellado");
        btnAgregar3.setActionCommand("Tarifa 0.0");
        btnAgregar3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregar3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAgregar3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnAgregar3.setOpaque(false);
        btnAgregar3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgregar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregar3ActionPerformed(evt);
            }
        });
        btnAgregar3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAgregar3KeyPressed(evt);
            }
        });
        botonesVer.add(btnAgregar3);

        jPanel4.add(botonesVer);
        botonesVer.setBounds(130, 0, 100, 130);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png"))); // NOI18N
        btnAgregar.setMnemonic('G');
        btnAgregar.setText("Guardar");
        btnAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnAgregar.setOpaque(false);
        btnAgregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        btnAgregar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAgregarKeyPressed(evt);
            }
        });
        jPanel4.add(btnAgregar);
        btnAgregar.setBounds(230, 130, 60, 50);

        total.setEditable(false);
        total.setBorder(null);
        total.setForeground(new java.awt.Color(51, 153, 0));
        total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        total.setText("0.0");
        total.setCaretColor(new java.awt.Color(0, 204, 0));
        total.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jPanel4.add(total);
        total.setBounds(170, 70, 170, 50);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salir.png"))); // NOI18N
        btnSalir.setMnemonic('S');
        btnSalir.setText("Salir");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnSalir.setOpaque(false);
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
        jPanel4.add(btnSalir);
        btnSalir.setBounds(290, 130, 60, 50);

        descuento.setEditable(false);
        descuento.setBorder(null);
        descuento.setForeground(new java.awt.Color(0, 0, 153));
        descuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        descuento.setText("0.0");
        descuento.setCaretColor(new java.awt.Color(0, 204, 0));
        descuento.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jPanel4.add(descuento);
        descuento.setBounds(210, 10, 130, 50);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 0));
        jLabel33.setLabelFor(total);
        jLabel33.setText("A PAGAR:");
        jPanel4.add(jLabel33);
        jLabel33.setBounds(50, 80, 120, 30);

        btnAplicarDscto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAplicarDscto.setForeground(new java.awt.Color(102, 102, 102));
        btnAplicarDscto.setText("Aplicar Dscto.:");
        btnAplicarDscto.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnAplicarDscto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarDsctoActionPerformed(evt);
            }
        });
        jPanel4.add(btnAplicarDscto);
        btnAplicarDscto.setBounds(10, 20, 130, 30);

        chkEsNotaVenta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        chkEsNotaVenta.setForeground(new java.awt.Color(255, 51, 51));
        chkEsNotaVenta.setText("ES NOTA DE VENTA");
        jPanel4.add(chkEsNotaVenta);
        chkEsNotaVenta.setBounds(10, 50, 140, 23);

        verBot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arriba.png"))); // NOI18N
        verBot.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/abajo.png"))); // NOI18N
        verBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verBotActionPerformed(evt);
            }
        });
        jPanel4.add(verBot);
        verBot.setBounds(170, 130, 60, 50);

        jPanel5.add(jPanel4);
        jPanel4.setBounds(310, 170, 360, 190);

        jLabel1.setText("NOTA: Para reimprimir el comprobante de pago, en caso de error en la impresora, digite nuevamente el No. de Ticket");
        jPanel5.add(jLabel1);
        jLabel1.setBounds(20, 370, 570, 14);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel12.setLayout(new java.awt.BorderLayout());

        miBotonImagen.setBackground(new java.awt.Color(204, 204, 255));
        miBotonImagen.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 204, 255)));
        jPanel12.add(miBotonImagen, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel12);
        jPanel12.setBounds(10, 170, 290, 190);

        jTabbedPane1.addTab("TICKETS", jPanel5);

        jPanel6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel6KeyPressed(evt);
            }
        });
        jPanel6.setLayout(null);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel7.setLayout(null);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Teléfono: ");
        jPanel7.add(jLabel11);
        jLabel11.setBounds(0, 70, 80, 20);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("CI/RUC: ");
        jPanel7.add(jLabel20);
        jLabel20.setBounds(0, 10, 80, 20);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Dirección: ");
        jPanel7.add(jLabel21);
        jLabel21.setBounds(0, 50, 80, 20);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Cliente: ");
        jPanel7.add(jLabel22);
        jLabel22.setBounds(0, 30, 80, 20);

        panelencontrados2.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.light"));
        panelencontrados2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelencontrados2.setLayout(null);

        encontrados2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Lista Clientes" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        encontrados2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        encontrados2.setAlignmentX(0.2F);
        encontrados2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                encontrados2MouseClicked(evt);
            }
        });
        encontrados2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                encontrados2KeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(encontrados2);

        panelencontrados2.add(jScrollPane5);
        jScrollPane5.setBounds(10, 10, 170, 90);

        jPanel7.add(panelencontrados2);
        panelencontrados2.setBounds(80, 50, 190, 110);

        telefono1.setEditable(false);
        telefono1.setText("9999999999999");
        telefono1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefono1KeyPressed(evt);
            }
        });
        jPanel7.add(telefono1);
        telefono1.setBounds(80, 70, 140, 20);

        identificacion1.setEditable(false);
        identificacion1.setText("9999999999999");
        identificacion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identificacion1ActionPerformed(evt);
            }
        });
        identificacion1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                identificacion1FocusLost(evt);
            }
        });
        identificacion1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                identificacion1KeyPressed(evt);
            }
        });
        jPanel7.add(identificacion1);
        identificacion1.setBounds(80, 10, 110, 20);

        nombres1.setEditable(false);
        nombres1.setText("CONSUMIDOR FINAL");
        nombres1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombres1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nombres1KeyReleased(evt);
            }
        });
        jPanel7.add(nombres1);
        nombres1.setBounds(80, 30, 190, 20);

        direccion1.setEditable(false);
        direccion1.setText("S/D");
        direccion1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccion1KeyPressed(evt);
            }
        });
        jPanel7.add(direccion1);
        direccion1.setBounds(80, 50, 190, 20);

        cliente1.setEditable(false);
        cliente1.setBorder(null);
        cliente1.setText("1");
        cliente1.setEnabled(false);
        cliente1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jPanel7.add(cliente1);
        cliente1.setBounds(190, 10, 20, 10);

        btnNuevoCliente1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clientes.png"))); // NOI18N
        btnNuevoCliente1.setText("CREAR O BUSCAR CLIENTE");
        btnNuevoCliente1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnNuevoCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCliente1ActionPerformed(evt);
            }
        });
        btnNuevoCliente1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNuevoCliente1KeyPressed(evt);
            }
        });
        jPanel7.add(btnNuevoCliente1);
        btnNuevoCliente1.setBounds(40, 100, 230, 30);

        jPanel6.add(jPanel7);
        jPanel7.setBounds(20, 10, 270, 160);

        productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "...", "Cantidad", "Descripcion", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
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
        productos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                productosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(productos);
        productos.getColumnModel().getColumn(0).setPreferredWidth(0);
        productos.getColumnModel().getColumn(0).setMaxWidth(5);
        productos.getColumnModel().getColumn(0).setHeaderValue("...");

        jPanel6.add(jScrollPane1);
        jScrollPane1.setBounds(300, 50, 350, 120);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel8.setLayout(null);

        btnAgregar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png"))); // NOI18N
        btnAgregar1.setMnemonic('G');
        btnAgregar1.setText("Guardar");
        btnAgregar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnAgregar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgregar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregar1ActionPerformed(evt);
            }
        });
        btnAgregar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAgregar1KeyPressed(evt);
            }
        });
        jPanel8.add(btnAgregar1);
        btnAgregar1.setBounds(450, 120, 60, 50);

        btnSalir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salir.png"))); // NOI18N
        btnSalir1.setMnemonic('S');
        btnSalir1.setText("Salir");
        btnSalir1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnSalir1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir1ActionPerformed(evt);
            }
        });
        btnSalir1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSalir1KeyPressed(evt);
            }
        });
        jPanel8.add(btnSalir1);
        btnSalir1.setBounds(510, 120, 60, 50);

        txtTotal1.setBorder(null);
        txtTotal1.setEditable(false);
        txtTotal1.setForeground(new java.awt.Color(51, 153, 0));
        txtTotal1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal1.setText("0.0");
        txtTotal1.setCaretColor(new java.awt.Color(0, 204, 0));
        txtTotal1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jPanel8.add(txtTotal1);
        txtTotal1.setBounds(140, 60, 140, 40);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setLabelFor(total);
        jLabel6.setText("A PAGAR:");
        jPanel8.add(jLabel6);
        jLabel6.setBounds(10, 70, 130, 30);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("IVA:");
        jPanel8.add(jLabel25);
        jLabel25.setBounds(60, 40, 90, 14);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("SUBTOTAL:");
        jPanel8.add(jLabel26);
        jLabel26.setBounds(70, 10, 80, 14);

        txtSubtotal.setEditable(false);
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubtotal.setText("0.0");
        jPanel8.add(txtSubtotal);
        txtSubtotal.setBounds(160, 10, 110, 20);

        txtIva.setEditable(false);
        txtIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIva.setText("0.0");
        jPanel8.add(txtIva);
        txtIva.setBounds(160, 30, 110, 20);

        observacionF.setColumns(20);
        observacionF.setRows(5);
        jScrollPane8.setViewportView(observacionF);

        jPanel8.add(jScrollPane8);
        jScrollPane8.setBounds(20, 120, 340, 60);

        hastaF.setBackground(new java.awt.Color(255, 255, 255));
        hastaF.setDate(new Date());
        hastaF.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel8.add(hastaF);
        hastaF.setBounds(420, 50, 150, 20);

        desdeF.setBackground(new java.awt.Color(255, 255, 255));
        desdeF.setDate(new Date());
        desdeF.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel8.add(desdeF);
        desdeF.setBounds(420, 30, 150, 20);

        jLabel30.setText("Hasta:");
        jPanel8.add(jLabel30);
        jLabel30.setBounds(380, 50, 40, 14);

        jLabel31.setText("Fechas de Validez de Tarjeta");
        jPanel8.add(jLabel31);
        jLabel31.setBounds(400, 10, 140, 14);

        jLabel32.setText("Desde: ");
        jPanel8.add(jLabel32);
        jLabel32.setBounds(380, 30, 37, 14);

        jPanel6.add(jPanel8);
        jPanel8.setBounds(20, 190, 630, 190);

        cmbProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbProductosKeyPressed(evt);
            }
        });
        jPanel6.add(cmbProductos);
        cmbProductos.setBounds(300, 30, 240, 20);

        btnAnadirProducto.setText("Añadir");
        btnAnadirProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirProductoActionPerformed(evt);
            }
        });
        btnAnadirProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAnadirProductoKeyPressed(evt);
            }
        });
        jPanel6.add(btnAnadirProducto);
        btnAnadirProducto.setBounds(590, 30, 63, 20);

        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setText("1");
        txtCantidad.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
        });
        jPanel6.add(txtCantidad);
        txtCantidad.setBounds(540, 30, 50, 20);

        jLabel23.setText("Productos disponibles");
        jPanel6.add(jLabel23);
        jLabel23.setBounds(300, 10, 190, 14);

        jLabel24.setText("Cantidad");
        jPanel6.add(jLabel24);
        jLabel24.setBounds(540, 10, 50, 14);

        jLabel27.setForeground(new java.awt.Color(255, 51, 0));
        jLabel27.setText("Para QUITAR un elemento seleccione y presione SUPRIMIR");
        jPanel6.add(jLabel27);
        jLabel27.setBounds(300, 170, 310, 14);

        jTabbedPane1.addTab("VENTA DE TARJETAS", jPanel6);

        jPanel11.setLayout(null);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel13.setLayout(null);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel34.setText("Teléfono: ");
        jPanel13.add(jLabel34);
        jLabel34.setBounds(0, 70, 80, 20);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel35.setText("CI/RUC: ");
        jPanel13.add(jLabel35);
        jLabel35.setBounds(0, 10, 80, 20);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel36.setText("Dirección: ");
        jPanel13.add(jLabel36);
        jLabel36.setBounds(0, 50, 80, 20);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel37.setText("Cliente: ");
        jPanel13.add(jLabel37);
        jLabel37.setBounds(0, 30, 80, 20);

        panelencontrados3.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.light"));
        panelencontrados3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelencontrados3.setLayout(null);

        encontrados3.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Lista Clientes" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        encontrados3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        encontrados3.setAlignmentX(0.2F);
        encontrados3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                encontrados3MouseClicked(evt);
            }
        });
        encontrados3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                encontrados3KeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(encontrados3);

        panelencontrados3.add(jScrollPane9);
        jScrollPane9.setBounds(10, 10, 170, 90);

        jPanel13.add(panelencontrados3);
        panelencontrados3.setBounds(80, 50, 190, 110);

        telefono2.setEditable(false);
        telefono2.setText("9999999999999");
        telefono2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefono2KeyPressed(evt);
            }
        });
        jPanel13.add(telefono2);
        telefono2.setBounds(80, 70, 140, 20);

        identificacion2.setEditable(false);
        identificacion2.setText("9999999999999");
        identificacion2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identificacion2ActionPerformed(evt);
            }
        });
        identificacion2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                identificacion2FocusLost(evt);
            }
        });
        identificacion2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                identificacion2KeyPressed(evt);
            }
        });
        jPanel13.add(identificacion2);
        identificacion2.setBounds(80, 10, 110, 20);

        nombres2.setEditable(false);
        nombres2.setText("CONSUMIDOR FINAL");
        nombres2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombres2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nombres2KeyReleased(evt);
            }
        });
        jPanel13.add(nombres2);
        nombres2.setBounds(80, 30, 190, 20);

        direccion2.setEditable(false);
        direccion2.setText("S/D");
        direccion2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccion2KeyPressed(evt);
            }
        });
        jPanel13.add(direccion2);
        direccion2.setBounds(80, 50, 190, 20);

        cliente2.setEditable(false);
        cliente2.setBorder(null);
        cliente2.setText("1");
        cliente2.setEnabled(false);
        cliente2.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jPanel13.add(cliente2);
        cliente2.setBounds(190, 10, 20, 10);

        btnNuevoCliente2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clientes.png"))); // NOI18N
        btnNuevoCliente2.setText("CREAR O BUSCAR CLIENTE");
        btnNuevoCliente2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnNuevoCliente2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCliente2ActionPerformed(evt);
            }
        });
        btnNuevoCliente2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNuevoCliente2KeyPressed(evt);
            }
        });
        jPanel13.add(btnNuevoCliente2);
        btnNuevoCliente2.setBounds(40, 100, 230, 30);

        jPanel11.add(jPanel13);
        jPanel13.setBounds(20, 10, 270, 160);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel14.setLayout(null);

        btnAgregar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png"))); // NOI18N
        btnAgregar4.setMnemonic('G');
        btnAgregar4.setText("Guardar");
        btnAgregar4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar4.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnAgregar4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgregar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregar4ActionPerformed(evt);
            }
        });
        btnAgregar4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAgregar4KeyPressed(evt);
            }
        });
        jPanel14.add(btnAgregar4);
        btnAgregar4.setBounds(500, 120, 60, 50);

        btnSalir2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salir.png"))); // NOI18N
        btnSalir2.setMnemonic('S');
        btnSalir2.setText("Salir");
        btnSalir2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnSalir2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir2ActionPerformed(evt);
            }
        });
        btnSalir2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSalir2KeyPressed(evt);
            }
        });
        jPanel14.add(btnSalir2);
        btnSalir2.setBounds(580, 120, 60, 50);

        txtTotal2.setEditable(false);
        txtTotal2.setBorder(null);
        txtTotal2.setForeground(new java.awt.Color(51, 153, 0));
        txtTotal2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal2.setText("0.0");
        txtTotal2.setCaretColor(new java.awt.Color(0, 204, 0));
        txtTotal2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jPanel14.add(txtTotal2);
        txtTotal2.setBounds(490, 70, 140, 40);

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 0, 0));
        jLabel38.setLabelFor(total);
        jLabel38.setText("A PAGAR:");
        jPanel14.add(jLabel38);
        jLabel38.setBounds(370, 80, 130, 30);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel39.setText("IVA:");
        jPanel14.add(jLabel39);
        jLabel39.setBounds(380, 40, 90, 14);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel40.setText("SUBTOTAL:");
        jPanel14.add(jLabel40);
        jLabel40.setBounds(390, 10, 80, 14);

        txtSubtotal1.setEditable(false);
        txtSubtotal1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubtotal1.setText("0.0");
        jPanel14.add(txtSubtotal1);
        txtSubtotal1.setBounds(510, 10, 110, 20);

        txtIva1.setEditable(false);
        txtIva1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIva1.setText("0.0");
        jPanel14.add(txtIva1);
        txtIva1.setBounds(510, 40, 110, 20);

        observacionF1.setColumns(20);
        observacionF1.setRows(5);
        jScrollPane10.setViewportView(observacionF1);

        jPanel14.add(jScrollPane10);
        jScrollPane10.setBounds(20, 70, 340, 60);

        jPanel11.add(jPanel14);
        jPanel14.setBounds(20, 180, 650, 190);

        ticketsPendientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "...", "Fecha Ingreso", "Fecha Salida", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.util.Date.class, java.util.Date.class, java.lang.Object.class
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
        ticketsPendientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ticketsPendientesKeyPressed(evt);
            }
        });
        jScrollPane11.setViewportView(ticketsPendientes);
        ticketsPendientes.getColumnModel().getColumn(0).setResizable(false);
        ticketsPendientes.getColumnModel().getColumn(0).setPreferredWidth(1);

        jPanel11.add(jScrollPane11);
        jScrollPane11.setBounds(310, 40, 350, 120);

        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("LISTA DE TICKETS SELLADOS");
        jPanel11.add(jLabel2);
        jLabel2.setBounds(310, 20, 150, 14);

        jTabbedPane1.addTab("COBRAR SELLADOS", jPanel11);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 42, 700, 420);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        botonesVer.setVisible(false);
        verBot.setSelected(false);
        if (guardando == false) {
            guardando = true;

            if (principal.permisos.getAgregar()) {
                try {
                    if (codigo.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Ingrese un Ticket y presione ENTER ...!", "", JOptionPane.ERROR_MESSAGE);
                        noTicket.requestFocusInWindow();
                        return;
                    }
                    if (cliente.getText().equals("0") && nombres.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Falta el ingresar o seleccionar el Cliente ...!", "", JOptionPane.ERROR_MESSAGE);
                        identificacion.requestFocusInWindow();
                        return;
                    }
                    Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
                    Factura facActual = (Factura) adm.buscarClave(new Integer(codigo.getText()), Factura.class);
                    Clientes cli = new Clientes();
                    if (cliente.getText().equals("0")) {
                        Clientes nuevoCl = new Clientes();
                        Integer codigoC = adm.getNuevaClave("Clientes", "codigo");
                        nuevoCl.setCodigo(codigoC);
                        nuevoCl.setDireccion(direccion.getText());
                        nuevoCl.setIdentificacion(identificacion.getText());
                        nuevoCl.setTelefono(telefono.getText());
                        nuevoCl.setNombres(nombres.getText());
                        cli = nuevoCl;
                        adm.guardar(nuevoCl);

                        //cliente.setText(""+nuevoCl.getCodigo());
                        identificacion.setText("9999999999999");
                        nombres.setText("CONSUMIDOR FINAL");
                        direccion.setText("S/D");
                        telefono.setText("9999999999999");
                        cliente.setText("1");
                        facActual.setClientes(nuevoCl);
                    } else {
                        facActual.setClientes(new Clientes(new Integer(cliente.getText())));
                        cli.setCodigo(new Integer(cliente.getText()));
                        cli.setDireccion(direccion.getText());
                        cli.setIdentificacion(identificacion.getText());
                        cli.setTelefono(telefono.getText());
                        cli.setNombres(nombres.getText());
                        adm.actualizar(cli);
                    }




                    Date fecSalida = new Date();
                    fecSalida.setHours(salida.getDate().getHours());
                    fecSalida.setMinutes(salida.getDate().getMinutes());
                    fecSalida.setSeconds(salida.getDate().getSeconds());
                    facActual.setFechafin(fecSalida);
                    Double descuentoV = Double.parseDouble(descuento.getText());
                    Double ivav = ((empresaObj.getIva() + 100) / 100);
                    Double totalv = Double.parseDouble(total.getText());
                    Double subtotalv = totalv / ivav;
                    Double ivav1 = subtotalv * (empresaObj.getIva() / 100);
                    facActual.setDescuento(new BigDecimal(descuentoV));
                    facActual.setTotal(new BigDecimal(totalv));
                    facActual.setSubtotal(new BigDecimal(subtotalv));
                    facActual.setIva(new BigDecimal(ivav1));

                    Date fecTiempo = new Date();
                    fecTiempo.setHours(tiempo.getDate().getHours());
                    fecTiempo.setMinutes(tiempo.getDate().getMinutes());
                    fecTiempo.setSeconds(tiempo.getDate().getSeconds());
                    facActual.setTiempo(fecTiempo);
                    facActual.setUsuarioc(principal.usuarioActual);
                    facActual.setSellado(false);
                    facActual.setYasalio(false);
                    facActual.setEsnota(chkEsNotaVenta.isSelected());
                    adm.actualizar(facActual);
//                    Integer numero = new Integer(emp.getDocumentofac());
//                    emp.setDocumentofac((numero + 1) + "");
                    int dia = 0;
                    try {
                        dia = new Integer(dias1.getText());
                    } catch (Exception e) {
                        dia = 0;
                    }

                    //adm.actualizar(emp);

                    Boolean pasar = true;
                    if (facActual.getTotal().doubleValue() > 0) {
                        Integer numero = new Integer(emp.getDocumentofac()) + 1;
                        while (pasar) {
                            List sihay = adm.query("Select o from Factura as o where o.numero = '" + numero + "'");
                            if (sihay.size() <= 0) {
                                pasar = false;
                                facActual.setNumero("" + numero);
                                emp.setDocumentofac((numero) + "");
                                adm.actualizar(emp);//GUARDO EMPRESA
                                adm.actualizar(facActual); // GUARDO FACTURA
                            } else {
                                numero++;
                            }

                        }
                    }

                    if (facActual.getTotal().doubleValue() > 0) {
                        imprimir(facActual.getCodigo(), emp, dia, false, cli);
                        Thread.sleep(5000);
                        //CORDILLERA
                        if (empresaObj.getImprime2facturas()) {
                            System.out.println("mando a imprimir otra vez");
                            imprimir(facActual.getCodigo(), emp, dia, false, cli);
                        }
                    }
                    if (empresaObj.getSeabrefac()) {
                        if (empresaObj.getRetardoSalida() != null) {
                            if (empresaObj.getRetardoSalida().length() > 0) {
                                Integer retardo = new Integer(empresaObj.getRetardoSalida());
                                Thread.sleep(retardo * 1000);
                            }
                        }

                        try {
                            LeerTarjeta ta = principal.buscarPuerto("principal");

                            ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                            //TEMPORAL
                            Thread.sleep(20);
                            ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                            Thread.sleep(20);
                            ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                            Thread.sleep(20);
                            ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                            Thread.sleep(20);
                            ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                            Thread.sleep(20);
                            ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                            Thread.sleep(20);
                            ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                            Thread.sleep(20);
                            ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                            Thread.sleep(20);
                            ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                            Thread.sleep(20);
                            ta.outputSream.write(empresaObj.getPuertafac().getBytes());


                            //TEMPORAL
                        } catch (Exception ex) {
                            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println("ABRIO PUERTA: " + empresaObj.getPuertafac());
                    } else {
                        //System.out.println("imprimo nuevo ticket");
                        //asdf    
                        //imprimirTicket(facActual.getCodigo(), emp);
                        System.out.println("NO ABRE BARRERA POR DESHABILITACION EN FRMEMPRESA ");
                    }

//                cargar.start();
                    principal.noDisponibles();
                    ingreso.setDate(null);
                    salida.setDate(null);
                    placa.setText(null);
                    tiempo.setDate(null);
                    noTicket.setText("");
                    codigo.setText("");
                    cliente.setText("1");
                    identificacion.setText("9999999999999");
                    nombres.setText("CONSUMIDOR FINAL");
                    telefono.setText("9999999999");
                    principal.auditar("Cobros", "No" + facActual.getNumero(), "GUARDAR");

                    descuento.setText("0.0");
                    total.setText("0.0");
                    codigo.setText("");
                    noTicket.setText("");
                    placa.setText("");
                    noTicket.requestFocusInWindow();
                    System.gc();
                    //cordillera
                    //principal.contenedor.requestFocus();
                    //this.setVisible(false);
                    //principal.contenedor.requestFocus();
                    //cordillera
                    noTicket.requestFocusInWindow();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error en guardar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
                //JOptionPane.showMessageDialog(this, "Registro Almacenado con éxito");
            } else {
                JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
            }
            guardando = false;
        }

    }//GEN-LAST:event_btnAgregarActionPerformed

    public void imprimir(int cod, Empresa emp, int dias, Boolean mensual, Clientes cli) {

//                    viewer.show();
        try {

            if (ubicacionDirectorio.contains("build")) {
                ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
            }
            JasperReport masterReport = null;
            if (mensual) {
                masterReport = (JasperReport) JRLoader.loadObject(ubicacionDirectorio + separador + "reportes" + separador + "factura2.jasper");
            } else if (chkEsNotaVenta.isSelected()) {
                masterReport = (JasperReport) JRLoader.loadObject(ubicacionDirectorio + separador + "reportes" + separador + "factura4.jasper");
            } else {
                masterReport = (JasperReport) JRLoader.loadObject(ubicacionDirectorio + separador + "reportes" + separador + "factura.jasper");
            }
            JRDataSource ds = null;
            ArrayList detalle = new ArrayList();
            Map parametros = new HashMap();
            String observacion = "";
            if (mensual) {
                List<Detalle> fac = adm.query("Select o from Detalle as o where o.factura.codigo = " + cod + " ");

                for (Iterator<Detalle> it = fac.iterator(); it.hasNext();) {
                    Detalle detalle1 = it.next();
                    Factura fac1 = (Factura) adm.querySimple("Select o from Factura as o where o.codigo = " + detalle1.getFactura().getCodigo() + " ");
                    observacion = fac1.getObservacion();
                    detalle1.setFactura(fac1);
                    System.out.println("SUBTOTAL: "+ detalle1.getSubtotal());
                    detalle.add(detalle1);
                }
                ds = new FacturaDetalleSource(detalle);
            } else {
                Factura fac = (Factura) adm.querySimple("Select o from Factura as o where o.codigo = " + cod + " ");
                observacion = fac.getObservacion();
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
            parametros.put("placa", placa.getText());
            parametros.put("usuario", principal.usuarioActual.getNombres());
            parametros.put("observacion", observacion);
            parametros.put("dias", (dias > 0 ? dias + " Dias" : ""));

            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);
            PrinterJob job = PrinterJob.getPrinterJob();
            /*
             * Create an array of PrintServices
             */
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            int selectedService = 0;
            /*
             * Scan found services to see if anyone suits our needs
             */
            String impresora = emp.getImpfactura();
            if (chkEsNotaVenta.isSelected()) {
                impresora = emp.getImpnota();
            }
            for (int i = 0; i < services.length; i++) {
                String nombre = services[i].getName();
                if (nombre.contains(impresora)) {
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
            /*
             * We set the selected service and pass it as a paramenter
             */
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

    public void imprimir2(int cod, Empresa emp, int dias, Boolean mensual, Clientes cli) {

//                    viewer.show();
        try {

            if (ubicacionDirectorio.contains("build")) {
                ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
            }
            JasperReport masterReport = null;
            masterReport = (JasperReport) JRLoader.loadObject(ubicacionDirectorio + separador + "reportes" + separador + "factura3.jasper");
            JRDataSource ds = null;
            ArrayList detalle = new ArrayList();
            Map parametros = new HashMap();
            String observacion = "";
            Factura fac = (Factura) adm.querySimple("Select o from Factura as o where o.codigo = " + cod + " ");
            observacion = fac.getObservacion();
            Clientes cli1 = (Clientes) fac.getClientes();
            cli1 = (Clientes) adm.querySimple("Select o from Clientes as o where o.codigo = " + cli1.getCodigo() + " ");
            System.out.println("" + cli1.getCodigo());
            fac.setClientes(cli1);
            detalle.add(fac);
            cli = cli1;
            ds = new FacturaSource(detalle);


            parametros.put("ruc", cli.getIdentificacion());
            parametros.put("cliente", cli.getNombres());
            parametros.put("direccion", cli.getDireccion());
            parametros.put("telefono", cli.getTelefono());
            parametros.put("placa", placa.getText());
            parametros.put("observacion", observacion);
            parametros.put("notickets", ticketsPendientes.getRowCount() + "");
            parametros.put("dias", (dias > 0 ? dias + " Dias" : ""));
            parametros.put("usuario", principal.usuarioActual.getNombres());

            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);
            PrinterJob job = PrinterJob.getPrinterJob();
            /*
             * Create an array of PrintServices
             */
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            int selectedService = 0;
            /*
             * Scan found services to see if anyone suits our needs
             */
            String impresora = emp.getImpfactura();
            if (chkEsNotaVenta.isSelected()) {
                impresora = emp.getImpnota();
            }
            for (int i = 0; i < services.length; i++) {
                String nombre = services[i].getName();
                if (nombre.contains(impresora)) {
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
            /*
             * We set the selected service and pass it as a paramenter
             */
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

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        principal.contenedor.requestFocus();
//        principal = null;
        //      empresaObj = null;
        this.setVisible(false);
        System.gc();
}//GEN-LAST:event_btnSalirActionPerformed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.setVisible(false);
        }
    }//GEN-LAST:event_formKeyReleased

    private void placaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_DOWN) {
            encontrados1.setSelectedIndex(0);
            encontrados1.requestFocusInWindow();
        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            panelencontrados1.setVisible(false);
        } else {
            principal.tecla(evt.getKeyCode());
        }
    }//GEN-LAST:event_placaKeyPressed

    private void identificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identificacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_identificacionActionPerformed

    private void codigoBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoBuscarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {

            Thread cargar = new Thread() {
                public void run() {
                    principal.procesando.setVisible(true);
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
                        Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    principal.procesando.setVisible(false);
                }
            };
            cargar.start();

        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            formaBusqueda.dispose();
        }
    }//GEN-LAST:event_codigoBuscarKeyPressed

    private void busquedaTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_busquedaTablaMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            try {
                int fila = busquedaTabla.getSelectedRow();
                Clientes client = (Clientes) adm.buscarClave((Integer) busquedaTabla.getValueAt(fila, 0), Clientes.class);
                cliente.setText(client.getCodigo() + "");
                nombres.setText(client.getNombres());
                identificacion.setText(client.getIdentificacion());
                direccion.setText(client.getDireccion());
                telefono.setText(client.getTelefono());
                formaBusqueda.dispose();
                client = null;
            } catch (Exception ex) {
                Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //        JOptionPane.showMessageDialog(this, usuarioObj);
}//GEN-LAST:event_busquedaTablaMouseClicked

    private void busquedaTablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaTablaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            try {
                int fila = busquedaTabla.getSelectedRow();
                Clientes client = (Clientes) adm.buscarClave((Integer) busquedaTabla.getValueAt(fila, 0), Clientes.class);
                cliente.setText(client.getCodigo() + "");
                nombres.setText(client.getNombres());
                identificacion.setText(client.getIdentificacion());
                direccion.setText(client.getDireccion());
                telefono.setText(client.getTelefono());
                formaBusqueda.dispose();
                client = null;
            } catch (Exception ex) {
                Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            formaBusqueda.dispose();
        }
}//GEN-LAST:event_busquedaTablaKeyPressed
    private Image getImage(byte[] bytes, boolean isThumbnail) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; // File or InputStream
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        if (isThumbnail) {
            param.setSourceSubsampling(4, 4, 0, 0);
        }
        return reader.read(0, param);

    }

//    void llenarFactura(Factura fac) {
//        dias.setVisible(false);
//        dias1.setVisible(false);
//        dias2.setVisible(false);
//        if (fac.getFechafin() != null) {
//
////                        int valor = JOptionPane.showConfirmDialog(this, ,"JCINFORM",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[] { "opcion 1", "opcion 2", "opcion 3" },"opcion 1" );
//            int seleccion = JOptionPane.showOptionDialog(this, "TICKET YA FACTURADO \n ¿Desea volver a imprimir la factura?",
//                    "JCINFORM",
//                    JOptionPane.YES_NO_CANCEL_OPTION,
//                    JOptionPane.QUESTION_MESSAGE,
//                    null, // null para icono por defecto.
//                    new Object[]{"SI", "NO", "Cancelar"}, // null para YES, NO y CANCEL
//                    "NO");
//            System.out.println("" + seleccion);
//
//            if (0 == seleccion) {
//                try {
//                    Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
//                    imprimir(fac.getCodigo(), emp, 0, false, new Clientes());
//                } catch (Exception ex) {
//                    Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//                    noTicket.setText("");
//            noTicket.requestFocusInWindow();
//            return;
//        }
//        ingreso.setDate(fac.getFechaini());
//        salida.setDate(new Date());
//
//
//
//        Date act = new Date();
//        dias.setVisible(true);
//        dias1.setVisible(true);
//        dias2.setVisible(true);
//
//        Long minutos0 = diferenciaFechas(fac.getFechaini(), new Date());
//        Integer minutos = minutos0.intValue();
//
//        Integer horas = minutos / 60;
//        if (minutos.intValue() < 0) {
//            minutos = minutos * -1;
//        }
//        if (horas.intValue() < 0) {
//            horas = horas * -1;
////            horas += 24;
//            dias.setVisible(true);
//            dias1.setVisible(true);
//            dias2.setVisible(true);
//        }
//        if (minutos.intValue() < 0) {
//            horas = 0;
//            minutos = 0;
//            dias.setVisible(true);
//            dias1.setVisible(true);
//            dias2.setVisible(true);
//        }
//        Float min = minutos / 60f;
//        int indice = min.toString().indexOf(".");
//        Float valorf = new Float("0" + min.toString().substring(indice));
//        int valorMinutos = java.lang.Math.round((valorf * 60));
//        act.setHours(horas);
//        act.setMinutes(valorMinutos);
//        tiempo.setDate(act);
//        placa.setText(fac.getPlaca());
//        BigDecimal aCobrar = new BigDecimal(0);
//        for (int a = 0; a < horas; a++) {
//            aCobrar = aCobrar.add(buscar(60));
//        }
//        try {
//            int noDias = 0;
//            noDias = (horas / 24);
//            dias1.setText(noDias + "");
//        } catch (Exception e) {
//            dias1.setText("0");
//        }
//        if (horas.intValue() > 0) {
//            if (valorMinutos > 0) {
//                if (valorMinutos > empresaObj.getGracia().intValue()) {
//                    aCobrar = aCobrar.add(buscar(valorMinutos));
//                }
//            } else {
//            }
//        } else {
//            if (valorMinutos > 0) {
//                aCobrar = aCobrar.add(buscar(valorMinutos));
//            } else {
//                aCobrar = aCobrar.add(buscar(1));
//            }
//
//        }
//        total.setText(aCobrar.setScale(2, RoundingMode.UP) + "");
//        codigo.setText(fac.getCodigo() + "");
//    }
    void llenarFactura(Factura fac) {
        dias.setVisible(false);
        dias1.setVisible(false);
        dias2.setVisible(false);
        if (fac.getFechafin() != null) {

            if (fac.getAnulado()) {
                codigo.setText("");
                //JOptionPane.showMessageDialog(this, "EL TICKET INGRESADO YA HA SIDO ANULADO...");
                noTicket.requestFocusInWindow();
                JOptionPane.showMessageDialog(this, "El No. de Ticket ha sido ANULADO ...!", "", JOptionPane.ERROR_MESSAGE);
                noTicket.setText("");
                noTicket.requestFocusInWindow();
                return;
            }
            Long minutosEntreSalidayActual = diferenciaFechas(fac.getFechafin(), new Date());
            int minutosPasados = minutosEntreSalidayActual.intValue();
            if (minutosPasados > empresaObj.getSalida().intValue() && fac.getYasalio() == false) {
                int seleccion = JOptionPane.showOptionDialog(this, "Ticket NO HA SALIDO y se ha SOBREPASADO con: " + minutosPasados + " min. "
                        + "\n ¿Desea volver a facturar e imprimir un nuevo Ticket?",
                        "JCINFORM",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, // null para icono por defecto.
                        new Object[]{"SI", "NO", "Salir"}, // null para YES, NO y CANCEL
                        "SI");
                System.out.println("" + seleccion);

                if (0 == seleccion) {
                    try {
                        //CREO UNA NUEVA FACTURA CON UN DETERMINADO NÚMERO DE TICKET 
                        Factura facNueva = new Factura();
//                        facNueva.setFechaini(fac.getFechafin());
                        Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");

                        facNueva.setPlaca("");
                        facNueva.setFechaini(fac.getFechafin());
                        facNueva.setFecha(fac.getFechafin());
                        facNueva.setAnulado(false);
                        facNueva.setUsuario(principal.getUsuario());
                        facNueva.setNocontar(false);
                        facNueva.setAnulado(false);
                        facNueva.setYasalio(false);
                        facNueva.setEsnota(chkEsNotaVenta.isSelected());
                        facNueva.setTarifa0(false);
                        facNueva.setSellado(false);
                        Boolean pasar = true;
                        Integer numero = new Integer(emp.getDocumentoticket()) + 1;
                        while (pasar) {
                            List sihay = adm.query("Select o from Factura as o where o.ticket = '" + numero + "'");
                            if (sihay.size() <= 0) {
                                pasar = false;

                                facNueva.setTicket("" + numero);
                                emp.setDocumentoticket((numero) + "");
                                adm.actualizar(emp);//GUARDO EMPRESA
                                adm.guardar(facNueva); // GUARDO FACTURA
                                noTicket.setText(numero + "");
                                codigo.setText(facNueva.getCodigo() + "");
                            } else {
                                numero++;
                            }

                        }
                        DateTime dt = new DateTime(fac.getFechafin());

                        DateTime tiempoDespuesGracia = dt.minusMinutes(emp.getGracia());
                        if ((minutosEntreSalidayActual + emp.getGracia()) < 60) {
                            facNueva.setFechaini(tiempoDespuesGracia.toDate());
                            facNueva.setFecha(tiempoDespuesGracia.toDate());
                        }
                        llenarFactura(facNueva);

                        DateTime dt0 = new DateTime(facNueva.getFechaini());
                        DateTime tiempoDespuesGracia0 = dt0.plusMinutes(emp.getGracia());
                        if ((minutosEntreSalidayActual + emp.getGracia()) < 60) {
                            facNueva.setFechaini(tiempoDespuesGracia0.toDate());
                            ingreso.setDate(facNueva.getFechaini());
                            DateTime dt2 = new DateTime(tiempo.getDate());
                            DateTime tiempoDespuesGracia2 = dt2.minusMinutes(emp.getGracia());
                            tiempo.setDate(tiempoDespuesGracia2.toDate());
                        }
                        guardando = false;
                        System.out.println("imprimo nuevo ticket por sobrepasar tiempo");
                        imprimirTicket(facNueva.getCodigo(), emp);
                    } catch (Exception ab) {
                    }
                }
                return;
            }

//                        int valor = JOptionPane.showConfirmDialog(this, ,"JCINFORM",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[] { "opcion 1", "opcion 2", "opcion 3" },"opcion 1" );
            if(empresaObj.getReimprimir()){
            int seleccion = JOptionPane.showOptionDialog(this, "TICKET YA FACTURADO \n ¿Desea volver a imprimir la factura?",
                    "JCINFORM",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, // null para icono por defecto.
                    new Object[]{"SI", "NO", "Cancelar"}, // null para YES, NO y CANCEL
                    "NO");
            System.out.println("" + seleccion);

            if (0 == seleccion) {
                try {
                    Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
                    imprimir(fac.getCodigo(), emp, 0, false, new Clientes());
                } catch (Exception ex) {
                    Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            noTicket.setText("");
            noTicket.requestFocusInWindow();
            return;
            }else{
                JOptionPane.showMessageDialog(this, "EL TICKET YA HA SIDO FACTURADO");
                noTicket.setText("");
                noTicket.requestFocusInWindow();
                return;
            }
            
        }
        ingreso.setDate(fac.getFechaini());
        salida.setDate(new Date());
        btnAgregar.requestFocusInWindow();


        Date act = new Date();
        dias.setVisible(true);
        dias1.setVisible(true);
        dias2.setVisible(true);

        Long minutos0 = diferenciaFechas(fac.getFechaini(), new Date());
        Integer minutos = minutos0.intValue();

        Integer horas = minutos / 60;
        BigDecimal aCobrar = new BigDecimal(0);

        //EMPIEZO A VERIRICAR
        int noDias0 = 0;
        if (empresaObj.getValorMaximo() > 0) {
            //INCREMENTO EL VALOR POR DÍA Y SOLO SACO LO DE UN DÍA
            while (horas > 24) { //VERIRICO SI SOBREPASA
                horas = horas - 24;
                minutos = minutos - 1440;
                aCobrar = aCobrar.add(new BigDecimal(empresaObj.getValorMaximo()));
                noDias0++;
            }
        }

        if (minutos.intValue() < 0) {
            minutos = minutos * -1;
        }
        if (horas.intValue() < 0) {
            horas = horas * -1;
//            horas += 24;
            dias.setVisible(true);
            dias1.setVisible(true);
            dias2.setVisible(true);
        }
        if (minutos.intValue() < 0) {
            horas = 0;
            minutos = 0;
            dias.setVisible(true);
            dias1.setVisible(true);
            dias2.setVisible(true);
        }

        aCobrar = aCobrar.add(buscar(minutos));

        Float min = minutos / 60f;
        int indice = min.toString().indexOf(".");
        Float valorf = new Float("0" + min.toString().substring(indice));
        int valorMinutos = java.lang.Math.round((valorf * 60));
        if (minutos.equals(60)) {
            valorMinutos = 60;
        }
        act.setHours(horas);
        act.setMinutes(valorMinutos);
        if (horas == 1 && valorMinutos == 60) {
            act.setMinutes(0);
        }
        tiempo.setDate(act);
        placa.setText(fac.getPlaca());
        //VERIFICO EL TIEMPO DE GRACIA SI ES QUE ESTÁ EN EL TIEMPO DE GRACIA
        if (horas == 0) {
            if (valorMinutos <= empresaObj.getGracia().intValue() && empresaObj.getGracia().intValue() > 0) {
                BigDecimal descuento = buscar(valorMinutos);
                aCobrar = aCobrar.subtract(descuento);
            }
        }
//        for (int a = 0; a < horas; a++) {
//            aCobrar = aCobrar.add(buscar(60));
//        }
        try {
            int noDias = 0;
            noDias = (horas / 24);
            dias1.setText((noDias + noDias0) + "");
        } catch (Exception e) {
            dias1.setText("0");
        }

//        if (horas.intValue() > 0) {
//            if (valorMinutos > 0) {
//                if (valorMinutos > empresaObj.getGracia().intValue()) {
//                    aCobrar = aCobrar.add(buscar(valorMinutos));
//                }
//            } else {
//            }
//        } else {
//            if (valorMinutos > 0) {
//                aCobrar = aCobrar.add(buscar(valorMinutos));
//            } else {
//                aCobrar = aCobrar.add(buscar(1));
//            }
//
//        }
        total.setText(aCobrar.setScale(2, RoundingMode.UP) + "");
        codigo.setText(fac.getCodigo() + "");
    }

    public BigDecimal buscar(Integer minutos) {
        BigDecimal valorRegresa = new BigDecimal(0);
        int ultimo = 0;
        BigDecimal valorUltimo = new BigDecimal(0);
        for (Iterator<Tarifas> it = tarifario.iterator(); it.hasNext();) {
            Tarifas tarifas = it.next();
            int desde = tarifas.getDesde();
            int hasta = tarifas.getHasta();
            if (minutos >= desde && minutos <= hasta) {
                valorRegresa = tarifas.getValor();
                return valorRegresa;
            }
            ultimo = hasta;
            valorUltimo = tarifas.getValor();

        }
        valorRegresa = valorRegresa.add(valorUltimo);
        minutos = minutos - ultimo;
        valorRegresa = valorRegresa.add(buscar(minutos));
        return valorRegresa;


    }

    public long diferenciaFechas(Date fechai, Date fechaf) {
        fechaf = new Date();
        java.util.GregorianCalendar date1 = new java.util.GregorianCalendar(fechai.getYear(), fechai.getMonth(), fechai.getDate(), fechai.getHours(), fechai.getMinutes(), fechai.getSeconds());
        java.util.GregorianCalendar date2 = new java.util.GregorianCalendar(fechaf.getYear(), fechaf.getMonth(), fechaf.getDate(), fechaf.getHours(), fechaf.getMinutes(), fechaf.getSeconds());
        long difms = date2.getTimeInMillis() - date1.getTimeInMillis();
        long difd = difms / (1000 * 60);
        return difd;
    }

    public Double redondear(Double numero, int decimales) {
        try {
            BigDecimal d = new BigDecimal(numero);
            d = d.setScale(decimales, RoundingMode.HALF_UP);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
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

    @SuppressWarnings("static-access")
    private void noTicketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_noTicketKeyPressed
        // TODO add your handling code here:


        if (evt.getKeyCode() == evt.VK_ENTER) {
            try {
                descuento.setText("0.0");

                ingreso.setDate(null);
                salida.setDate(null);
                placa.setText(null);
                tiempo.setDate(null);

                Factura fac = (Factura) adm.querySimple("Select o from Factura as o where o.ticket = '" + new Integer(noTicket.getText().trim()) + "' ");
                if (fac != null) {
                    btnAplicarDscto.setEnabled(true);
                    llenarFactura(fac);
                    try {
                        cargarFoto(fac.getCodigo());
                    } catch (Exception es) {
                        System.out.println("NO SE CARGO FOTO");
                    }


                } else {
                    ingreso.setDate(null);
                    salida.setDate(null);
                    placa.setText(null);
                    tiempo.setDate(null);
                }
            } catch (Exception e) {
                Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, e);
                ingreso.setDate(null);
                salida.setDate(null);
                placa.setText(null);
                tiempo.setDate(null);

            }
        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            principal.contenedor.requestFocus();
            //principal = null;
            //empresaObj = null;
            System.gc();
            this.setVisible(false);
        } else if (evt.getKeyCode() == evt.VK_F1
                || evt.getKeyCode() == evt.VK_F2
                || evt.getKeyCode() == evt.VK_F3
                || evt.getKeyCode() == evt.VK_F4
                || evt.getKeyCode() == evt.VK_F5
                || evt.getKeyCode() == evt.VK_F6
                || evt.getKeyCode() == evt.VK_F7
                || evt.getKeyCode() == evt.VK_F8
                || evt.getKeyCode() == evt.VK_F9
                || evt.getKeyCode() == evt.VK_F10
                || evt.getKeyCode() == evt.VK_F11
                || evt.getKeyCode() == evt.VK_F12) {
            principal.tecla(evt.getKeyCode());
        }
    }//GEN-LAST:event_noTicketKeyPressed

    public void llenarCliente(Clientes nCliente) {
        cliente.setText("" + nCliente.getCodigo());
        identificacion.setText(nCliente.getIdentificacion());
        nombres.setText(nCliente.getNombres());
        direccion.setText(nCliente.getDireccion());
        telefono.setText(nCliente.getTelefono());
    }

    public void llenarCliente2(Clientes nCliente) {
        cliente1.setText("" + nCliente.getCodigo());
        identificacion1.setText(nCliente.getIdentificacion());
        nombres1.setText(nCliente.getNombres());
        direccion1.setText(nCliente.getDireccion());
        telefono1.setText(nCliente.getTelefono());
    }

    public void llenarCliente3(Clientes nCliente) {
        cliente2.setText("" + nCliente.getCodigo());
        identificacion2.setText(nCliente.getIdentificacion());
        nombres2.setText(nCliente.getNombres());
        direccion2.setText(nCliente.getDireccion());
        telefono2.setText(nCliente.getTelefono());
    }

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        // TODO add your handling code here:

        identificacion.setEditable(true);
        nombres.setEditable(true);
        direccion.setEditable(true);
        telefono.setEditable(true);
        llenarCliente(new Clientes(0));
//            cliente.setEditable(true);
        identificacion.requestFocusInWindow();


    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void identificacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_identificacionFocusLost
        // TODO add your handling code here:
        try {
            Clientes cliObj = (Clientes) adm.querySimple("Select o from Clientes as o "
                    + "where o.identificacion like '" + identificacion.getText().trim() + "%' ");
            if (cliObj != null) {
                cliente.setText(cliObj.getCodigo() + "");
                identificacion.setText(cliObj.getIdentificacion());
                nombres.setText(cliObj.getNombres());
                direccion.setText(cliObj.getDireccion());
                telefono.setText(cliObj.getTelefono());
            }

        } catch (Exception e) {
            System.out.println("BUSCAR CEDULA UNICA " + e);
        }
    }//GEN-LAST:event_identificacionFocusLost

    private void encontradosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_encontradosKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            this.panelencontrados.setVisible(false);
            Clientes est = (Clientes) this.encontrados.getSelectedValue();
            llenarCliente(est);


        }
        if (evt.getKeyCode() == evt.VK_UP && encontrados.getSelectedIndex() == 0) {
            this.nombres.requestFocusInWindow();
        }
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.panelencontrados.setVisible(false);

        }
        principal.tecla(evt.getKeyCode());
}//GEN-LAST:event_encontradosKeyPressed

    private void nombresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombresKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_DOWN) {
            encontrados.setSelectedIndex(0);
            encontrados.requestFocusInWindow();
        }
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            panelencontrados.setVisible(false);
        }
    }//GEN-LAST:event_nombresKeyPressed

    private void nombresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombresKeyReleased
        // TODO add your handling code here:
        if (!nombres.getText().isEmpty()) {

            List<Clientes> encon = adm.query("Select o from Clientes as o where o.nombres like  '%" + nombres.getText().trim() + "%' order by o.nombres ", 0, 10);
            if (encon.size() > 0) {
                DefaultListModel dtm = new DefaultListModel();
                dtm.removeAllElements();
                encontrados.setModel(dtm);
                int j = 0;
                for (Clientes est : encon) {
                    dtm.add(j, est);
                    j++;
                }
                encontrados.setModel(dtm);
                this.panelencontrados.setVisible(true);
            } else {
                DefaultListModel dtm = new DefaultListModel();
                dtm.removeAllElements();
                encontrados.setModel(dtm);
                this.panelencontrados.setVisible(false);
            }

        } else {
            DefaultListModel dtm = new DefaultListModel();
            dtm.removeAllElements();
            encontrados.setModel(dtm);
            this.panelencontrados.setVisible(false);
        }
    }//GEN-LAST:event_nombresKeyReleased

    private void encontradosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_encontradosMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.panelencontrados.setVisible(false);
            Clientes est = (Clientes) this.encontrados.getSelectedValue();
            llenarCliente(est);
        }
    }//GEN-LAST:event_encontradosMouseClicked

    private void encontrados1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_encontrados1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.panelencontrados1.setVisible(false);
            Factura fac = (Factura) this.encontrados1.getSelectedValue();
            descuento.setText("0.0");
            btnAplicarDscto.setEnabled(true);
            llenarFactura(fac);
            noTicket.setText(fac.getTicket());
        }
    }//GEN-LAST:event_encontrados1MouseClicked

    private void encontrados1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_encontrados1KeyPressed
        // TODO add your handling code here:

        if (evt.getKeyCode() == evt.VK_ENTER) {
            this.panelencontrados1.setVisible(false);
            Factura fac = (Factura) this.encontrados1.getSelectedValue();

            descuento.setText("0.0");
            btnAplicarDscto.setEnabled(true);
            llenarFactura(fac);
            try {
                cargarFoto(fac.getCodigo());
            } catch (Exception e) {
                System.out.println("NO SE CARGO FOTO....!");
            }

            noTicket.setText(fac.getTicket());
            btnAgregar.requestFocusInWindow();
        } else if (evt.getKeyCode() == evt.VK_UP && encontrados1.getSelectedIndex() == 0) {
            this.placa.requestFocusInWindow();
        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.panelencontrados1.setVisible(false);

        } else {
            principal.tecla(evt.getKeyCode());
        }
    }//GEN-LAST:event_encontrados1KeyPressed

    private void placaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placaKeyReleased
        // TODO add your handling code here:
        if (!placa.getText().isEmpty()) {
            List<Factura> encon = adm.query("Select o from Factura as o   where o.fechafin is null  and o.placa like  '%" + placa.getText().trim() + "%' order by o.fecha ", 0, 10);
            if (encon.size() > 0) {
                DefaultListModel dtm = new DefaultListModel();
                dtm.removeAllElements();
                encontrados1.setModel(dtm);
                int j = 0;
                for (Factura est : encon) {
                    dtm.add(j, est);
                    j++;
                }
                encontrados1.setModel(dtm);
                this.panelencontrados1.setVisible(true);
            } else {
                DefaultListModel dtm = new DefaultListModel();
                dtm.removeAllElements();
                encontrados1.setModel(dtm);
                this.panelencontrados1.setVisible(false);
            }

        } else {
            DefaultListModel dtm = new DefaultListModel();
            dtm.removeAllElements();
            encontrados1.setModel(dtm);
            this.panelencontrados1.setVisible(false);
        }
    }//GEN-LAST:event_placaKeyReleased

    private void encontrados2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_encontrados2MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.panelencontrados2.setVisible(false);
            Clientes est = (Clientes) this.encontrados2.getSelectedValue();
            llenarCliente2(est);
            cargarGrid(est.getProductos(), est.getValor());

        }
    }//GEN-LAST:event_encontrados2MouseClicked

    private void encontrados2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_encontrados2KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            this.panelencontrados2.setVisible(false);
            Clientes est = (Clientes) this.encontrados2.getSelectedValue();
            llenarCliente2(est);
            cargarGrid(est.getProductos(), est.getValor());


        }
        if (evt.getKeyCode() == evt.VK_UP && encontrados2.getSelectedIndex() == 0) {
            this.nombres1.requestFocusInWindow();
        }
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.panelencontrados2.setVisible(false);

        }
    }//GEN-LAST:event_encontrados2KeyPressed

    private void identificacion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identificacion1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_identificacion1ActionPerformed
    public void cargarGrid(Productos pro, BigDecimal valor) {
        Productos asigRub = pro;
        DefaultTableModel dtm = (DefaultTableModel) this.productos.getModel();
        Object[] obj = new Object[10];
        obj[0] = asigRub.getCodigo();
        obj[1] = new Integer(1);
        obj[2] = asigRub.getDescripcion();
        obj[3] = valor.setScale(2, RoundingMode.UP);
        dtm.addRow(obj);
        productos.setModel(dtm);
        sumar();

    }

    public void cargarGrid1(Productos pro, BigDecimal valor) {
//        DefaultTableModel dtm = (DefaultTableModel) this.ticketsPendientes.getModel();
//        Object[] obj = new Object[10];
//        obj[0] = asigRub.getCodigo();
//        obj[1] = new Integer(1);
//        obj[2] = asigRub.getDescripcion();
//        obj[3] = valor.setScale(2, RoundingMode.UP);
//        dtm.addRow(obj);
//        ticketsPendientes.setModel(dtm);
//        sumar();
    }
    private void identificacion1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_identificacion1FocusLost
        // TODO add your handling code here:
        try {
            Clientes cliObj = (Clientes) adm.querySimple("Select o from Clientes as o "
                    + "where o.identificacion like '" + identificacion1.getText().trim() + "%' ");
            if (cliObj != null) {
                cliente1.setText(cliObj.getCodigo() + "");
                identificacion1.setText(cliObj.getIdentificacion());
                nombres1.setText(cliObj.getNombres());
                direccion1.setText(cliObj.getDireccion());
                telefono1.setText(cliObj.getTelefono());
                cargarGrid(cliObj.getProductos(), cliObj.getValor());
            }

        } catch (Exception e) {
            System.out.println("BUSCAR CEDULA UNICA " + e);
        }
    }//GEN-LAST:event_identificacion1FocusLost

    private void nombres1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombres1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_DOWN) {
            encontrados2.setSelectedIndex(0);
            encontrados2.requestFocusInWindow();
        }
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            panelencontrados2.setVisible(false);
        }
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_nombres1KeyPressed

    private void nombres1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombres1KeyReleased
        // TODO add your handling code here:
        if (!nombres1.getText().isEmpty()) {

            List<Clientes> encon = adm.query("Select o from Clientes as o where o.nombres like  '%" + nombres1.getText().trim() + "%' order by o.nombres ", 0, 10);
            if (encon.size() > 0) {
                DefaultListModel dtm = new DefaultListModel();
                dtm.removeAllElements();
                encontrados2.setModel(dtm);
                int j = 0;
                for (Clientes est : encon) {
                    dtm.add(j, est);
                    j++;
                }
                encontrados2.setModel(dtm);
                this.panelencontrados2.setVisible(true);
            } else {
                DefaultListModel dtm = new DefaultListModel();
                dtm.removeAllElements();
                encontrados2.setModel(dtm);
                this.panelencontrados2.setVisible(false);
            }

        } else {
            DefaultListModel dtm = new DefaultListModel();
            dtm.removeAllElements();
            encontrados2.setModel(dtm);
            this.panelencontrados2.setVisible(false);
        }
    }//GEN-LAST:event_nombres1KeyReleased

    private void btnNuevoCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCliente1ActionPerformed
        // TODO add your handling code here:
        identificacion1.setEditable(true);
        nombres1.setEditable(true);
        direccion1.setEditable(true);
        telefono1.setEditable(true);
        llenarCliente2(new Clientes(0));
//            cliente.setEditable(true);
        identificacion1.requestFocusInWindow();
    }//GEN-LAST:event_btnNuevoCliente1ActionPerformed

    private void btnAgregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar1ActionPerformed
        try {
            // TODO add your handling code here:
            if (cliente1.getText().equals("0") && nombres1.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Falta el ingresar o seleccionar el Cliente ...!", "", JOptionPane.ERROR_MESSAGE);
                identificacion.requestFocusInWindow();
                return;
            }
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            Factura facActual = new Factura();
            Clientes cli = new Clientes();
            if (cliente1.getText().equals("0")) {
                Clientes nuevoCl = new Clientes();
                Integer codigoC = adm.getNuevaClave("Clientes", "codigo");
                nuevoCl.setCodigo(codigoC);
                nuevoCl.setDireccion(direccion1.getText());
                nuevoCl.setIdentificacion(identificacion1.getText());
                nuevoCl.setTelefono(telefono1.getText());
                nuevoCl.setNombres(nombres1.getText());
                cli = nuevoCl;
                adm.guardar(nuevoCl);
                identificacion1.setText("9999999999999");
                nombres1.setText("CONSUMIDOR FINAL");
                direccion1.setText("S/D");
                telefono1.setText("9999999999999");
                cliente1.setText("1");
                facActual.setClientes(nuevoCl);
            } else {
                facActual.setClientes(new Clientes(new Integer(cliente1.getText())));
                cli.setDireccion(direccion1.getText());
                cli.setIdentificacion(identificacion1.getText());
                cli.setTelefono(telefono1.getText());
                cli.setNombres(nombres1.getText());
            }
            facActual.setSubtotal(new BigDecimal(txtSubtotal.getText()));
            facActual.setIva(new BigDecimal(txtIva.getText()));
            facActual.setTotal(new BigDecimal(txtTotal1.getText()));
            facActual.setFecha(new Date());
            facActual.setFechafin(new Date());
            facActual.setUsuario(principal.usuarioActual);
            facActual.setSellado(false);
            facActual.setUsuarioc(principal.usuarioActual);
            facActual.setNumero(emp.getDocumentofac());
            facActual.setObservacion(" Desde:" + desdeF.getDate().toLocaleString().substring(0, 11) + " Hasta:" + hastaF.getDate().toLocaleString().substring(0, 11) + " " + observacionF.getText());
            adm.guardar(facActual);
            Integer numero = new Integer(emp.getDocumentofac());
            emp.setDocumentofac((numero + 1) + "");
            int dia = 0;
            try {
                dia = new Integer(dias1.getText());
            } catch (Exception e) {
                dia = 0;
            }
            adm.actualizar(emp);
            Detalle det = new Detalle();
            int filas = productos.getRowCount();
            for (int i = 0; i < filas; i++) {
                det = new Detalle();
                det.setProductos(new Productos((Integer) productos.getValueAt(i, 0)));
                det.setCantidad((Integer) productos.getValueAt(i, 1));
                det.setDetalle((String) productos.getValueAt(i, 2));
                det.setSubtotal((BigDecimal) productos.getValueAt(i, 3));
                System.out.println("A CARGAR: "+(BigDecimal) productos.getValueAt(i, 3));
                det.setIva(det.getSubtotal().multiply(new BigDecimal(empresaObj.getIva() / 100)));
                det.setTotal(det.getSubtotal().add(det.getIva()));
                det.setFactura(facActual);
                adm.guardar(det);
            }
            imprimir(facActual.getCodigo(), emp, dia, true, cli);
            /**
             * cordillera
             */
            if (empresaObj.getImprime2facturas()) {
                Thread.sleep(5000);
                System.out.println("mando a imprimir otra vez");
                imprimir(facActual.getCodigo(), emp, dia, true, cli);
            }


            //JOptionPane.showMessageDialog(this, "Registro Almacenado con éxito...!");
            DefaultTableModel dtm = (DefaultTableModel) productos.getModel();
            dtm.getDataVector().removeAllElements();
            productos.setModel(dtm);
            principal.auditar("Cobros", "No" + facActual.getNumero(), "GUARDAR");
            principal.contenedor.requestFocus();

            this.setVisible(false);
            //principal = null;
            //empresaObj = null;
            System.gc();
        } catch (Exception ex) {
            Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnAgregar1ActionPerformed

    private void btnSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir1ActionPerformed
        // TODO add your handling code here:
        principal.contenedor.requestFocus();
        this.setVisible(false);
        //principal = null;
        //empresaObj = null;
        System.gc();
    }//GEN-LAST:event_btnSalir1ActionPerformed
    public void sumar() {
        int filasNo = this.productos.getRowCount();
        BigDecimal suma = new BigDecimal(0.0);
        for (int i = 0; i < filasNo; i++) {
            BigDecimal valor = new BigDecimal(this.productos.getValueAt(i, 3).toString());
            suma = suma.add(valor);
        }
        Double totalv01 = suma.doubleValue();
        Double iva01 = ((empresaObj.getIva() + 100) / 100);
        Double subtotalv01 = totalv01 / iva01;
        Double iva02 = subtotalv01 * ((empresaObj.getIva()) / 100);

        BigDecimal subtotalv = new BigDecimal(subtotalv01);
        BigDecimal ivav = new BigDecimal(iva02);
        BigDecimal totalv = new BigDecimal(totalv01);
//        BigDecimal subtotalv = suma;
//        BigDecimal ivav = subtotalv.multiply(new BigDecimal(empresaObj.getIva() / 100));
//        BigDecimal totalv = subtotalv.add(ivav);
        txtSubtotal.setText(subtotalv.setScale(2, RoundingMode.HALF_UP) + "");
        txtIva.setText(ivav.setScale(2, RoundingMode.HALF_UP) + "");
        txtTotal1.setText(totalv.setScale(2, RoundingMode.HALF_UP) + "");
    }
    private void btnAnadirProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirProductoActionPerformed
        // TODO add your handling code here:
        if (!txtCantidad.getText().isEmpty()) {
            Productos asigRub = (Productos) cmbProductos.getSelectedItem();
            DefaultTableModel dtm = (DefaultTableModel) this.productos.getModel();
            Object[] obj = new Object[10];
            obj[0] = asigRub.getCodigo();
            obj[1] = Integer.parseInt(txtCantidad.getText());
            obj[2] = asigRub.getDescripcion();
            obj[3] = new BigDecimal(asigRub.getValor() * Integer.parseInt(txtCantidad.getText())).setScale(2, RoundingMode.HALF_UP) ; 
            dtm.addRow(obj);
            productos.setModel(dtm);
            sumar();
        } else {
            JOptionPane.showMessageDialog(this, "INGRESE UNA CANTIDAD");
            txtCantidad.requestFocusInWindow();
        }


    }//GEN-LAST:event_btnAnadirProductoActionPerformed

    private void productosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_productosKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_DELETE) {
            int fil = productos.getSelectedRow();
            DefaultTableModel dtm = (DefaultTableModel) productos.getModel();
            dtm.removeRow(fil);
            productos.setModel(dtm);
            this.sumar();

        }
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_productosKeyPressed

    private void btnAgregarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregarKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnAgregarKeyPressed

    private void btnSalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalirKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnSalirKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_formKeyPressed

    private void jPanel5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel5KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_jPanel5KeyPressed

    private void jPanel6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel6KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_jPanel6KeyPressed

    private void identificacion1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_identificacion1KeyPressed
        principal.tecla(evt.getKeyCode());
        // TODO add your handling code here:
    }//GEN-LAST:event_identificacion1KeyPressed

    private void telefono1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefono1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_telefono1KeyPressed

    private void direccion1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccion1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_direccion1KeyPressed

    private void cmbProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbProductosKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbProductosKeyPressed

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_txtCantidadKeyPressed

    private void btnAnadirProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAnadirProductoKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnAnadirProductoKeyPressed

    private void btnNuevoCliente1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoCliente1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnNuevoCliente1KeyPressed

    private void btnAgregar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregar1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnAgregar1KeyPressed

    private void btnSalir1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalir1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnSalir1KeyPressed

    private void btnMultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultaActionPerformed
        // TODO add your handling code here:
        botonesVer.setVisible(false);
        verBot.setSelected(false);
        if (guardando == false) {
            guardando = true;

            if (principal.permisos.getAgregar()) {
                int seleccion = JOptionPane.showOptionDialog(this, " ¿Seguro que Desea Aplicar MULTA?",
                        "JCINFORM",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, // null para icono por defecto.
                        new Object[]{"SI", "NO", "Cancelar"}, // null para YES, NO y CANCEL
                        "NO");
                System.out.println("" + seleccion);

                if (0 == seleccion) {


                    try {


                        Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
                        Factura facActual = new Factura();

                        /**
                         * ASIGNO EL CLIENTE AL QUE SE LE APLICA LA MULTA
                         */
                        Clientes nuevoCl = new Clientes();
                        if (cliente.getText().equals("0")) {

                            Integer codigoC = adm.getNuevaClave("Clientes", "codigo");
                            nuevoCl.setCodigo(codigoC);
                            nuevoCl.setDireccion(direccion.getText());
                            nuevoCl.setIdentificacion(identificacion.getText());
                            nuevoCl.setTelefono(telefono.getText());
                            nuevoCl.setNombres(nombres.getText());
                            adm.guardar(nuevoCl);

                            //cliente.setText(""+nuevoCl.getCodigo());
                            identificacion.setText("9999999999999");
                            nombres.setText("CONSUMIDOR FINAL");
                            direccion.setText("S/D");
                            telefono.setText("9999999999999");
                            cliente.setText("1");
                            facActual.setClientes(nuevoCl);
                        } else {
                            facActual.setClientes(new Clientes(new Integer(cliente.getText())));
                            nuevoCl.setCodigo(new Integer(cliente.getText()));
                            nuevoCl.setDireccion(direccion.getText());
                            nuevoCl.setIdentificacion(identificacion.getText());
                            nuevoCl.setTelefono(telefono.getText());
                            nuevoCl.setNombres(nombres.getText());
                            adm.actualizar(nuevoCl);
                        }



                        facActual.setClientes(nuevoCl);
                        Date fecSalida = new Date();
                        facActual.setFechaini(fecSalida);
                        facActual.setFecha(fecSalida);
                        facActual.setFechafin(fecSalida);
                        facActual.setNumero(emp.getDocumentofac());
                        Double ivav = ((empresaObj.getIva() + 100) / 100);
                        Double totalv = empresaObj.getMulta();
                        Double subtotalv = totalv / ivav;
                        Double ivav1 = subtotalv * (empresaObj.getIva() / 100);
                        facActual.setTotal(new BigDecimal(totalv));
                        facActual.setSubtotal(new BigDecimal(subtotalv));
                        facActual.setIva(new BigDecimal(ivav1));
                        facActual.setPlaca("PAGO MULTA");
                        facActual.setTiempo(new Date());
                        facActual.setTicket("000000000");
                        Boolean pasar0 = true;
                        Integer numero0 = new Integer(emp.getDocumentoticket()) + 1;
                        while (pasar0) {
                            List sihay = adm.query("Select o from Factura as o where o.ticket = '" + numero0 + "'");
                            if (sihay.size() <= 0) {
                                pasar0 = false;
                                facActual.setTicket("" + numero0);
                                emp.setDocumentoticket((numero0) + "");
                                adm.actualizar(emp);//GUARDO EMPRESA
                            } else {
                                numero0++;
                            }

                        }

                        facActual.setUsuario(principal.usuarioActual);
                        facActual.setUsuarioc(principal.usuarioActual);
                        facActual.setSellado(false);
                        facActual.setYasalio(false);
                        facActual.setEsnota(chkEsNotaVenta.isSelected());
                        facActual.setAnulado(false);

                        //adm.guardar(facActual);


//                    Integer numero = new Integer(emp.getDocumentofac());
//                    emp.setDocumentofac((numero + 1) + "");
                        Boolean pasar = true;
                        Integer numero = new Integer(emp.getDocumentofac()) + 1;
                        while (pasar) {
                            List sihay = adm.query("Select o from Factura as o where o.numero = '" + numero + "'");
                            if (sihay.size() <= 0) {
                                pasar = false;
                                facActual.setNumero("" + numero);
                                emp.setDocumentofac((numero) + "");
                                adm.actualizar(emp);//GUARDO EMPRESA
                                adm.guardar(facActual); // GUARDO FACTURA
                            } else {
                                numero++;
                            }

                        }

                        int dia = 0;
                        try {
                            dia = new Integer(dias1.getText());
                        } catch (Exception e) {
                            dia = 0;
                        }


                        imprimir(facActual.getCodigo(), emp, dia, false, nuevoCl);
                        if (empresaObj.getSeabrefac()) {
                            if (empresaObj.getRetardoSalida() != null) {
                                if (empresaObj.getRetardoSalida().length() > 0) {
                                    Integer retardo = new Integer(empresaObj.getRetardoSalida());
                                    Thread.sleep(retardo * 1000);
                                }
                            }

                            try {
                                LeerTarjeta ta = principal.buscarPuerto("principal");
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                //TEMPORAL
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                //TEMPORAL
                            } catch (Exception ex) {
                                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("ABRIO PUERTA: " + empresaObj.getPuertafac());
                        } else {
                            System.out.println("imprimo nuevo ticket");
                            imprimirTicket(facActual.getCodigo(), emp);
                            System.out.println("NO ABRE BARRERA POR DESHABILITACION EN FRMEMPRESA ");
                        }

                        principal.noDisponibles();
                        ingreso.setDate(null);
                        salida.setDate(null);
                        placa.setText(null);
                        tiempo.setDate(null);
                        noTicket.setText("");
                        codigo.setText("");
                        principal.auditar("Cobros", "No" + facActual.getNumero(), "GUARDAR");
                        principal.contenedor.requestFocus();
                        this.setVisible(false);
                        //principal = null;
                        //empresaObj = null;
                        System.gc();


                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error en guardar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                    //JOptionPane.showMessageDialog(this, "Registro Almacenado con éxito");

                }
            } else {
                JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
            }
            guardando = false;
        }
    }//GEN-LAST:event_btnMultaActionPerformed

    public void imprimirTicket(int cod, Empresa emp) {

//                    viewer.show();
        try {

            if (ubicacionDirectorio.contains("build")) {
                ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
            }

            JasperReport masterReport = (JasperReport) JRLoader.loadObject(ubicacionDirectorio + "reportes" + separador + "ticket.jasper");

            Factura fac = (Factura) adm.querySimple("Select o from Factura as o where o.codigo = " + cod + " ");
            ArrayList detalle = new ArrayList();
            detalle.add(fac);
            FacturaSource ds = new FacturaSource(detalle);
            Map parametros = new HashMap();

            parametros.put("empresa", emp.getRazon());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefonos());
            parametros.put("multa", emp.getMulta());
            parametros.put("usuario", principal.usuarioActual.getNombres());
            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);
            PrinterJob job = PrinterJob.getPrinterJob();
            /* Create an array of PrintServices */
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            int selectedService = 0;
            /* Scan found services to see if anyone suits our needs */
            for (int i = 0; i < services.length; i++) {
                String nombre = services[i].getName();
                if (nombre.contains(emp.getImpmulta())) {
                    selectedService = i;
                }
            }
            job.setPrintService(services[selectedService]);
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            MediaSizeName mediaSizeName = MediaSize.findMedia(3.08F, 3.70F, MediaPrintableArea.INCH);
            //MediaSizeName mediaSizeName = MediaSize.findMedia(3F,3F, MediaPrintableArea.INCH);
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
//            lger.logger(frmTicket.class.getName(), ex+"");
        }

//        } catch (JRException ex) {
//            ex.printStackTrace();
//        }
    }

    private void btnMultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnMultaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMultaKeyPressed

    private void frmGuardarAnuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_frmGuardarAnuladoActionPerformed
        // TODO add your handling code here:
        if (observacion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un motivo por el cuál está anulando el ticket...!");
            observacion.requestFocusInWindow();
        } else {
            try {
                Factura facActual = (Factura) adm.buscarClave(new Integer(codigo.getText()), Factura.class);
                facActual.setObservacion(observacion.getText());
                facActual.setAnulado(true);
                facActual.setFechafin(new Date());
                facActual.setTiempo(new Date());
                facActual.setDias(0);
                facActual.setUsuarioc(principal.usuarioActual);
                facActual.setSubtotal(BigDecimal.ZERO);
                facActual.setIva(BigDecimal.ZERO);
                facActual.setSellado(false);
                facActual.setTotal(BigDecimal.ZERO);
                total.setText("0.0");
                codigo.setText("");
                noTicket.setText("");
                placa.setText("");
                noTicket.requestFocusInWindow();

                adm.actualizar(facActual);
                frmEliminar.setVisible(false);
                principal.noDisponibles();
            } catch (Exception ex) {
                Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_frmGuardarAnuladoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        frmEliminar.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {
            // TODO add your handling code here:
            botonesVer.setVisible(false);
            verBot.setSelected(false);
            Accesos permisos = null;
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'AnularTickets' "
                    + "and o.global.codigo  = '" + principal.usuarioActual.getGlobal().getCodigo() + "' and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ANULAR TICKETS...! ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (permisos.getAgregar()) {
                if (codigo.getText().isEmpty()) {
                    codigo.setText("");
                    JOptionPane.showMessageDialog(this, "Ingrese un Ticket ...!", "", JOptionPane.ERROR_MESSAGE);
                    noTicket.requestFocusInWindow();
                    return;
                } else {
                    observacion.setText("");
                    frmEliminar.setVisible(true);
                }

            } else {
                System.out.println("NO TIENE PERMISOS");
                JOptionPane.showMessageDialog(this, "No tiene permisos para ANULAR TICKETS...! ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
        }



    }//GEN-LAST:event_btnEliminarActionPerformed

    private void noTicketKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_noTicketKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_noTicketKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        cliente.setText("0");
        identificacion.setText("");
        nombres.setText("");
        direccion.setText("");
        telefono.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnAgregar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar2ActionPerformed
        // TODO add your handling code here:

        botonesVer.setVisible(false);
        verBot.setSelected(false);
        try {
            // TODO add your handling code here:
            Accesos permisos = null;
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'AnularTickets' "
                    + "and o.global.codigo  = '" + principal.usuarioActual.getGlobal().getCodigo() + "' and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ANULAR TICKETS...! ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (permisos.getAgregar()) {
                if (codigo.getText().isEmpty()) {
                    codigo.setText("");
                    JOptionPane.showMessageDialog(this, "Ingrese un Ticket ...!", "", JOptionPane.ERROR_MESSAGE);
                    noTicket.requestFocusInWindow();
                    return;
                } else {
                    observacion1.setText("");
                    frmTarifa0.setVisible(true);
                }

            } else {
                System.out.println("NO TIENE PERMISOS");
                JOptionPane.showMessageDialog(this, "No tiene permisos para ANULAR TICKETS...! ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAgregar2ActionPerformed

    private void btnAgregar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregar2KeyPressed

    private void guardarTarifa0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarTarifa0ActionPerformed
        // TODO add your handling code here:
        if (observacion1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un motivo por el cuál está anulando el ticket...!");
            observacion1.requestFocusInWindow();
        } else {
            try {
                guardando = true;

                if (principal.permisos.getAgregar()) {
                    try {
                        if (codigo.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Ingrese un Ticket ...!", "", JOptionPane.ERROR_MESSAGE);
                            noTicket.requestFocusInWindow();
                            return;
                        }
                        if (cliente.getText().equals("0") && nombres.getText().trim().isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Falta el ingresar o seleccionar el Cliente ...!", "", JOptionPane.ERROR_MESSAGE);
                            identificacion.requestFocusInWindow();
                            return;
                        }
                        Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
                        Factura facActual = (Factura) adm.buscarClave(new Integer(codigo.getText()), Factura.class);
                        Clientes cli = new Clientes();
                        if (cliente.getText().equals("0")) {
                            Clientes nuevoCl = new Clientes();
                            Integer codigoC = adm.getNuevaClave("Clientes", "codigo");
                            nuevoCl.setCodigo(codigoC);
                            nuevoCl.setDireccion(direccion.getText());
                            nuevoCl.setIdentificacion(identificacion.getText());
                            nuevoCl.setTelefono(telefono.getText());
                            nuevoCl.setNombres(nombres.getText());
                            cli = nuevoCl;
                            adm.guardar(nuevoCl);
                            identificacion.setText("9999999999999");
                            nombres.setText("CONSUMIDOR FINAL");
                            direccion.setText("S/D");
                            telefono.setText("9999999999999");
                            cliente.setText("1");
                            facActual.setClientes(nuevoCl);
                        } else {
                            facActual.setClientes(new Clientes(new Integer(cliente.getText())));
                            cli.setDireccion(direccion.getText());
                            cli.setIdentificacion(identificacion.getText());
                            cli.setTelefono(telefono.getText());
                            cli.setNombres(nombres.getText());
                            adm.actualizar(cli);
                        }




                        Date fecSalida = new Date();
                        fecSalida.setHours(salida.getDate().getHours());
                        fecSalida.setMinutes(salida.getDate().getMinutes());
                        fecSalida.setSeconds(salida.getDate().getSeconds());
                        facActual.setFechafin(fecSalida);
                        facActual.setTarifa0(true);

                        Double ivav = ((empresaObj.getIva() + 100) / 100);
                        Double totalv = Double.parseDouble(total.getText());
                        Double subtotalv = totalv / ivav;
                        Double ivav1 = subtotalv * (empresaObj.getIva() / 100);
                        facActual.setTotal(new BigDecimal(totalv));
                        facActual.setSubtotal(new BigDecimal(subtotalv));
                        facActual.setIva(new BigDecimal(ivav1));

                        Date fecTiempo = new Date();
                        fecTiempo.setHours(tiempo.getDate().getHours());
                        fecTiempo.setMinutes(tiempo.getDate().getMinutes());
                        fecTiempo.setSeconds(tiempo.getDate().getSeconds());
                        facActual.setTiempo(fecTiempo);
                        facActual.setUsuarioc(principal.usuarioActual);
                        facActual.setTarifa0(true);
                        facActual.setObservacion(observacion1.getText());
                        facActual.setSellado(false);
                        adm.actualizar(facActual);
//                    Integer numero = new Integer(emp.getDocumentofac());
//                    emp.setDocumentofac((numero + 1) + "");
                        int dia = 0;
                        try {
                            dia = new Integer(dias1.getText());
                        } catch (Exception e) {
                            dia = 0;
                        }
                        //imprimir(facActual.getCodigo(), emp, dia, false, cli);
                        if (empresaObj.getSeabrefac()) {
                            if (empresaObj.getRetardoSalida() != null) {
                                if (empresaObj.getRetardoSalida().length() > 0) {
                                    Integer retardo = new Integer(empresaObj.getRetardoSalida());
                                    Thread.sleep(retardo * 1000);
                                }
                            }

                            try {
                                LeerTarjeta ta = principal.buscarPuerto("principal");

                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                //TEMPORAL
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                                Thread.sleep(20);
                                ta.outputSream.write(empresaObj.getPuertafac().getBytes());


                                //TEMPORAL
                            } catch (Exception ex) {
                                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("ABRIO PUERTA: " + empresaObj.getPuertafac());
                        } else {
                            System.out.println("NO ABRE BARRERA POR DESHABILITACION DEN FRMEMPRESA ");
                        }

//                cargar.start();
                        principal.noDisponibles();
                        ingreso.setDate(null);
                        salida.setDate(null);
                        placa.setText(null);
                        tiempo.setDate(null);
                        noTicket.setText("");
                        codigo.setText("");
                        principal.auditar("Cobros", "No" + facActual.getNumero(), "GUARDAR");
                        principal.contenedor.requestFocus();
                        this.setVisible(false);
                        //principal = null;
                        // empresaObj = null;
                        System.gc();


                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error en guardar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                    //JOptionPane.showMessageDialog(this, "Registro Almacenado con éxito");

                    guardando = false;
                }
                frmTarifa0.setVisible(false);
                principal.noDisponibles();
            } catch (Exception ex) {
                Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_guardarTarifa0ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        frmTarifa0.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void identificacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_identificacionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            nombres.requestFocusInWindow();
        }
    }//GEN-LAST:event_identificacionKeyPressed

private void btnAplicarDsctoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarDsctoActionPerformed
    try {
        // TODO add your handling code here:
        if (identificacion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del Cliente ...!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (identificacion.getText().contains("9999999")) {
            JOptionPane.showMessageDialog(this, "Cambie primero el Cliente ...!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }
        btnAplicarDscto.setEnabled(false);
        List<Descuento> des = adm.query("Select o from Descuento as o");
        for (Iterator<Descuento> it = des.iterator(); it.hasNext();) {
            Descuento descuento1 = it.next();
            if (descuento1.getTipo().equals("1")) {
                BigDecimal tot = new BigDecimal(total.getText());
                descuento.setText(redondear(descuento1.getValor().doubleValue(), 2) + "");
                BigDecimal porcentaje = tot.multiply(descuento1.getValor()).divide(new BigDecimal(100));
                descuento.setText(redondear(porcentaje.doubleValue(), 2) + "");
                BigDecimal nuevoTotal = tot.subtract(porcentaje);
                total.setText(redondear(nuevoTotal.doubleValue(), 2) + "");
            } else {
                descuento.setText(redondear(descuento1.getValor().doubleValue(), 2) + "");
                BigDecimal tot = new BigDecimal(total.getText());
                BigDecimal nuevoTotal = tot.subtract(descuento1.getValor());
                total.setText(redondear(nuevoTotal.doubleValue(), 2) + "");

            }
        }
    } catch (Exception ex) {
        btnAplicarDscto.setEnabled(true);
        Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
    }

}//GEN-LAST:event_btnAplicarDsctoActionPerformed

    private void btnAgregar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar3ActionPerformed
        // TODO add your handling code here:
        botonesVer.setVisible(false);
        verBot.setSelected(false);
        try {
            if (codigo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un Ticket y presione ENTER ...!", "", JOptionPane.ERROR_MESSAGE);
                noTicket.requestFocusInWindow();
                return;
            }
            if (identificacion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el nombre del Cliente ...!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (identificacion.getText().contains("9999999")) {
                JOptionPane.showMessageDialog(this, "Cambie primero el Cliente ...!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            Factura facActual = (Factura) adm.buscarClave(new Integer(codigo.getText()), Factura.class);
            Clientes cli = new Clientes();
            boolean nuevoCLiente = false;
            if (cliente.getText().equals("0")) {
                Clientes nuevoCl = new Clientes();
                Integer codigoC = adm.getNuevaClave("Clientes", "codigo");
                nuevoCl.setCodigo(codigoC);
                nuevoCl.setDireccion(direccion.getText());
                nuevoCl.setIdentificacion(identificacion.getText());
                nuevoCl.setTelefono(telefono.getText());
                nuevoCl.setNombres(nombres.getText());
                cli = nuevoCl;
                adm.guardar(nuevoCl);
                nuevoCLiente = true;
                identificacion.setText("9999999999999");
                nombres.setText("CONSUMIDOR FINAL");
                direccion.setText("S/D");
                telefono.setText("9999999999999");
                cliente.setText("1");
                facActual.setClientes(nuevoCl);

            } else {
                facActual.setClientes(new Clientes(new Integer(cliente.getText())));
                cli.setCodigo(new Integer(cliente.getText()));
                cli.setDireccion(direccion.getText());
                cli.setIdentificacion(identificacion.getText());
                cli.setTelefono(telefono.getText());
                cli.setNombres(nombres.getText());
                adm.actualizar(cli);
            }
            Boolean alNuevoClienteCobrarle = false;
            if (nuevoCLiente) {
                int seleccion = JOptionPane.showOptionDialog(this, "SE HA CREADO UN NUEVO CLIENTE, \n ¿Desea aplicar la TARIFA 0 en los TICKETS SELLADOS?",
                        "JCINFORM",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, // null para icono por defecto.
                        new Object[]{"SI", "NO", "Cancelar"}, // null para YES, NO y CANCEL
                        "NO");
                System.out.println("" + seleccion);

                if (0 == seleccion) {
                    alNuevoClienteCobrarle = true;
                }

            }
            Date fecSalida = new Date();
            fecSalida.setHours(salida.getDate().getHours());
            fecSalida.setMinutes(salida.getDate().getMinutes());
            fecSalida.setSeconds(salida.getDate().getSeconds());
            facActual.setFechafin(fecSalida);
            Double descuentoV = Double.parseDouble(descuento.getText());
            Double ivav = ((empresaObj.getIva() + 100) / 100);
            Double totalv = Double.parseDouble(total.getText());
            Double subtotalv = totalv / ivav;
            Double ivav1 = subtotalv * (empresaObj.getIva() / 100);
            facActual.setDescuento(new BigDecimal(descuentoV));
            facActual.setTotal(new BigDecimal(totalv));
            facActual.setSubtotal(new BigDecimal(subtotalv));
            facActual.setIva(new BigDecimal(ivav1));
            facActual.setSellado(true);
            Date fecTiempo = new Date();
            fecTiempo.setHours(tiempo.getDate().getHours());
            fecTiempo.setMinutes(tiempo.getDate().getMinutes());
            fecTiempo.setSeconds(tiempo.getDate().getSeconds());
            facActual.setTiempo(fecTiempo);
            facActual.setUsuarioc(principal.usuarioActual);
            facActual.setNumero(null);
            if (!alNuevoClienteCobrarle) {
                facActual.setDescuento(new BigDecimal(descuentoV));
                facActual.setTotal(new BigDecimal(totalv));
                facActual.setSubtotal(new BigDecimal(subtotalv));
                facActual.setIva(new BigDecimal(ivav1));
                facActual.setSellado(true);
            }

            adm.actualizar(facActual);
            /**
             * GENERAR CUENTA POR COBRAR PARA LOS VALORES PENDIENTES.
             */
            Cxcobrar cx = new Cxcobrar(adm.getNuevaClave("Cxcobrar", "codigo"));
            cx.setFactura(facActual);
            cx.setClientes(cli);
            cx.setPagada(false);
            cx.setUsuario(principal.usuarioActual);
            adm.guardar(cx);



            if (empresaObj.getSeabrefac()) {
                if (empresaObj.getRetardoSalida() != null) {
                    if (empresaObj.getRetardoSalida().length() > 0) {
                        Integer retardo = new Integer(empresaObj.getRetardoSalida());
                        Thread.sleep(retardo * 1000);
                    }
                }

                try {
                    LeerTarjeta ta = principal.buscarPuerto("principal");

                    ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                    //TEMPORAL
                    Thread.sleep(20);
                    ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                    Thread.sleep(20);
                    ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                    Thread.sleep(20);
                    ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                    Thread.sleep(20);
                    ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                    Thread.sleep(20);
                    ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                    Thread.sleep(20);
                    ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                    Thread.sleep(20);
                    ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                    Thread.sleep(20);
                    ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                    Thread.sleep(20);
                    ta.outputSream.write(empresaObj.getPuertafac().getBytes());


                    //TEMPORAL
                } catch (Exception ex) {
                    Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("ABRIO PUERTA: " + empresaObj.getPuertafac());
            } else {
                System.out.println("NO ABRE BARRERA POR DESHABILITACION DEN FRMEMPRESA ");
            }

//                cargar.start();
            principal.noDisponibles();
            ingreso.setDate(null);
            salida.setDate(null);
            placa.setText(null);
            tiempo.setDate(null);
            noTicket.setText("");
            codigo.setText("");
            principal.auditar("Cobros", "No" + facActual.getNumero(), "GUARDAR");
            principal.contenedor.requestFocus();
            this.setVisible(false);
            //principal = null;
            //empresaObj = null;
            System.gc();
        } catch (Exception ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnAgregar3ActionPerformed

    private void btnAgregar3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregar3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregar3KeyPressed

    private void encontrados3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_encontrados3MouseClicked
        // TODO add your handling code here:

        if (evt.getClickCount() == 2) {
            this.panelencontrados3.setVisible(false);
            Clientes est = (Clientes) this.encontrados3.getSelectedValue();
            llenarCliente3(est);
            llenarTickest(est);
            //cargarGrid(est.getProductos(), est.getValor());

        }
    }//GEN-LAST:event_encontrados3MouseClicked

    private void encontrados3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_encontrados3KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            this.panelencontrados3.setVisible(false);
            Clientes est = (Clientes) this.encontrados3.getSelectedValue();
            llenarCliente3(est);
            llenarTickest(est);
        }
        if (evt.getKeyCode() == evt.VK_UP && encontrados2.getSelectedIndex() == 0) {
            this.nombres2.requestFocusInWindow();
        }
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.panelencontrados3.setVisible(false);

        }

    }//GEN-LAST:event_encontrados3KeyPressed
    public void llenarTickest(Clientes cli) {
        try {
            List<Cxcobrar> pendientes = adm.query("Select o from Cxcobrar as o  "
                    + "where o.clientes.codigo = '" + cli.getCodigo() + "'  "
                    + " and o.pagada = false ");
            DefaultTableModel dtm = (DefaultTableModel) this.ticketsPendientes.getModel();
            dtm.getDataVector().removeAllElements();
            BigDecimal suma = new BigDecimal("0");
            for (Iterator<Cxcobrar> it = pendientes.iterator(); it.hasNext();) {
                Cxcobrar cxcobrar = it.next();
                Object[] obj = new Object[10];
                obj[0] = cxcobrar;
                obj[1] = cxcobrar.getFactura().getFechaini();
                obj[2] = cxcobrar.getFactura().getFechafin();
                obj[3] = cxcobrar.getFactura().getTotal();
                dtm.addRow(obj);
                suma = suma.add(cxcobrar.getFactura().getTotal());
            }
            ticketsPendientes.setModel(dtm);
            //txtTotal2.setText(redondear(suma.doubleValue(), 2)+"");

            Double totalv01 = suma.doubleValue();
            Double iva01 = ((empresaObj.getIva() + 100) / 100);
            Double subtotalv01 = totalv01 / iva01;
            Double iva02 = subtotalv01 * ((empresaObj.getIva()) / 100);

            BigDecimal subtotalv = new BigDecimal(subtotalv01);
            BigDecimal ivav = new BigDecimal(iva02);
            BigDecimal totalv = new BigDecimal(totalv01);
            txtSubtotal1.setText(subtotalv.setScale(2, RoundingMode.HALF_UP) + "");
            txtIva1.setText(ivav.setScale(2, RoundingMode.HALF_UP) + "");
            txtTotal2.setText(totalv.setScale(2, RoundingMode.HALF_UP) + "");
        } catch (Exception ex) {
            Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void telefono2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefono2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefono2KeyPressed

    private void identificacion2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identificacion2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_identificacion2ActionPerformed

    private void identificacion2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_identificacion2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_identificacion2FocusLost

    private void identificacion2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_identificacion2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_identificacion2KeyPressed

    private void nombres2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombres2KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_DOWN) {
            encontrados3.setSelectedIndex(0);
            encontrados3.requestFocusInWindow();
        }
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            panelencontrados3.setVisible(false);
        }
        principal.tecla(evt.getKeyCode());


    }//GEN-LAST:event_nombres2KeyPressed

    private void nombres2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombres2KeyReleased
        // TODO add your handling code here:
        if (!nombres2.getText().isEmpty()) {

            List<Clientes> encon = adm.query("Select o from Clientes as o where o.nombres like  '%" + nombres2.getText().trim() + "%' order by o.nombres ", 0, 10);
            if (encon.size() > 0) {
                DefaultListModel dtm = new DefaultListModel();
                dtm.removeAllElements();
                encontrados3.setModel(dtm);
                int j = 0;
                for (Clientes est : encon) {
                    dtm.add(j, est);
                    j++;
                }
                encontrados3.setModel(dtm);
                this.panelencontrados3.setVisible(true);
            } else {
                DefaultListModel dtm = new DefaultListModel();
                dtm.removeAllElements();
                encontrados3.setModel(dtm);
                this.panelencontrados3.setVisible(false);
            }

        } else {
            DefaultListModel dtm = new DefaultListModel();
            dtm.removeAllElements();
            encontrados3.setModel(dtm);
            this.panelencontrados3.setVisible(false);
        }
    }//GEN-LAST:event_nombres2KeyReleased

    private void direccion2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccion2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccion2KeyPressed

    private void btnNuevoCliente2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCliente2ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        identificacion2.setEditable(true);
        nombres2.setEditable(true);
        direccion2.setEditable(true);
        telefono2.setEditable(true);
        llenarCliente3(new Clientes(0));
//            cliente.setEditable(true);
        identificacion2.requestFocusInWindow();
    }//GEN-LAST:event_btnNuevoCliente2ActionPerformed

    private void btnNuevoCliente2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoCliente2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoCliente2KeyPressed

    private void btnAgregar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar4ActionPerformed
        try {
            if (cliente2.getText().equals("0") && nombres2.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Falta el ingresar o seleccionar el Cliente ...!", "", JOptionPane.ERROR_MESSAGE);
                identificacion2.requestFocusInWindow();
                return;
            }
            if (txtTotal2.getText().contains("0.0") || txtTotal2.getText().contains("0.00")) {
                JOptionPane.showMessageDialog(this, "Valor en 0, no se puede facturar...!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // TODO add your handling code here:
            int filas = ticketsPendientes.getRowCount();
            for (int i = 0; i < filas; i++) {
                try {
                    Cxcobrar cx = (Cxcobrar) ticketsPendientes.getValueAt(i, 0);
                    cx.setPagada(true);
                    adm.actualizar(cx);
                } catch (Exception ex) {
                    Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            Factura facActual = new Factura(adm.getNuevaClave("Factura", "codigo"));
            Clientes cli = new Clientes();
            if (cliente2.getText().equals("0")) {
                Clientes nuevoCl = new Clientes();
                Integer codigoC = adm.getNuevaClave("Clientes", "codigo");
                nuevoCl.setCodigo(codigoC);
                nuevoCl.setDireccion(direccion2.getText());
                nuevoCl.setIdentificacion(identificacion2.getText());
                nuevoCl.setTelefono(telefono2.getText());
                nuevoCl.setNombres(nombres2.getText());
                cli = nuevoCl;
                adm.guardar(nuevoCl);
                identificacion2.setText("9999999999999");
                nombres2.setText("CONSUMIDOR FINAL");
                direccion2.setText("S/D");
                telefono2.setText("9999999999999");
                cliente2.setText("1");
                facActual.setClientes(nuevoCl);
            } else {
                facActual.setClientes(new Clientes(new Integer(cliente2.getText())));
                cli.setCodigo(new Integer(cliente2.getText()));
                cli.setDireccion(direccion2.getText());
                cli.setIdentificacion(identificacion2.getText());
                cli.setTelefono(telefono2.getText());
                cli.setNombres(nombres2.getText());
                adm.actualizar(cli);
            }


            facActual.setClientes(cli);
            facActual.setDescuento(new BigDecimal("0"));
            facActual.setTotal(new BigDecimal(txtTotal2.getText()));
            facActual.setSubtotal(new BigDecimal(txtSubtotal1.getText()));
            facActual.setIva(new BigDecimal(txtIva1.getText()));

            Date fecTiempo = new Date();
            facActual.setTiempo(fecTiempo);
            facActual.setFecha(fecTiempo);
            facActual.setFechaini(fecTiempo);
            facActual.setFechafin(fecTiempo);
            facActual.setSellado(false);
            facActual.setEsnota(chkEsNotaVenta.isSelected());
            facActual.setUsuarioc(principal.usuarioActual);
            adm.actualizar(facActual);
            //                    Integer numero = new Integer(emp.getDocumentofac());
            //                    emp.setDocumentofac((numero + 1) + "");
            int dia = 0;
            try {
                dia = new Integer(dias1.getText());
            } catch (Exception e) {
                dia = 0;
            }

            //adm.actualizar(emp);

            Boolean pasar = true;
            Integer numero = new Integer(emp.getDocumentofac()) + 1;
            while (pasar) {
                List sihay = adm.query("Select o from Factura as o where o.numero = '" + numero + "'");
                if (sihay.size() <= 0) {
                    pasar = false;
                    facActual.setNumero("" + numero);
                    emp.setDocumentofac((numero) + "");
                    adm.actualizar(emp);//GUARDO EMPRESA
                    adm.actualizar(facActual); // GUARDO FACTURA
                } else {
                    numero++;
                }

            }
            imprimir2(facActual.getCodigo(), emp, dia, false, cli);
            llenarTickest(cli);
        } catch (Exception ex) {
            Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnAgregar4ActionPerformed

    private void btnAgregar4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregar4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregar4KeyPressed

    private void btnSalir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir2ActionPerformed
        // TODO add your handling code here:
        principal.contenedor.requestFocus();
        this.setVisible(false);
        //principal = null;
        //empresaObj = null;
        System.gc();
    }//GEN-LAST:event_btnSalir2ActionPerformed

    private void btnSalir2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalir2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalir2KeyPressed

    private void ticketsPendientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ticketsPendientesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ticketsPendientesKeyPressed

    private void verBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verBotActionPerformed
        // TODO add your handling code here:e
        if (verBot.isSelected()) {
            botonesVer.setVisible(true);
            botonesVer.requestFocusInWindow();
        } else {
            botonesVer.setVisible(false);
        }
    }//GEN-LAST:event_verBotActionPerformed

    private void botonesVerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonesVerFocusLost
        // TODO add your handling code here:
//         botonesVer.setVisible(false);
    }//GEN-LAST:event_botonesVerFocusLost

    private void noTicketFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_noTicketFocusGained
        // TODO add your handling code here:
        botonesVer.setVisible(false);
        verBot.setSelected(false);
    }//GEN-LAST:event_noTicketFocusGained

    private void frmGuardarAnulado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_frmGuardarAnulado1ActionPerformed
        // TODO add your handling code here:
        if (codigoAdministrador.getText().isEmpty() || numeroIngresado.getText().isEmpty()) {
            numeroIngresado.requestFocusInWindow();
            JOptionPane.showMessageDialog(this, "Ingrese el # de Factura\no\n "
                    + "Ingrese la Clave de Administrador ...!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuarios usuAnula = adm.ingresoSistema(cmbUsuarios.getSelectedItem().toString(), codigoAdministrador.getText());
        if (usuAnula != null) {
            try {
                Date fechaActual = new Date();
                String desde = (fechaActual.getYear() + 1900) + "-" + (fechaActual.getMonth() + 1) + "-" + (fechaActual.getDate()) + " 00:01:01";
                String hasta = (fechaActual.getYear() + 1900) + "-" + (fechaActual.getMonth() + 1) + "-" + (fechaActual.getDate()) + " 23:59:59";
                Factura fac = (Factura) adm.querySimple("Select o from Factura as o "
                        + "  where o.numero = " + numeroIngresado.getText() + " "
                        + " and o.anuladofac = false and o.anulado = false "
                        + " and o.fechafin between '" + desde + "' and  '" + hasta + "' ");
                if (fac == null) {
                    JOptionPane.showMessageDialog(this, "El # de Factura ya ha sido anulado...!", "", JOptionPane.ERROR_MESSAGE);
                    codigoAdministrador.setText("");
                    numeroIngresado.setText("");
                    //frmAnular.setVisible(false);
                    return;

                }
                Factura facNueva = new Factura();
                facNueva.setFechaini(fac.getFechaini());
                Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
                facNueva.setPlaca("");
                facNueva.setFechaini(fac.getFechaini());
                facNueva.setFecha(fac.getFechaini());
                facNueva.setAnulado(false);
                facNueva.setFechafin(fac.getFechafin());
                facNueva.setSubtotal(fac.getSubtotal());
                facNueva.setIva(fac.getIva());
                facNueva.setDescuento(fac.getDescuento());
                facNueva.setTotal(fac.getTotal());
                facNueva.setUsuario(principal.getUsuario());
                facNueva.setClientes(null);
                facNueva.setDias(fac.getDias());
                facNueva.setTiempo(fac.getTiempo());
                facNueva.setObservacion("Anulo tick: " + fac.getTicket() + "fac: " + fac.getNumero());
                facNueva.setNocontar(false);
                facNueva.setAnuladofac(false);
                facNueva.setNumero("");
                facNueva.setYasalio(false);
                facNueva.setEsnota(chkEsNotaVenta.isSelected());
                facNueva.setTarifa0(false);
                facNueva.setSellado(false);
                Boolean pasar = true;
                Integer numero = new Integer(emp.getDocumentoticket()) + 1;
                while (pasar) {
                    List sihay = adm.query("Select o from Factura as o where o.ticket = '" + numero + "'");
                    if (sihay.size() <= 0) {
                        pasar = false;
                        facNueva.setTicket("" + numero);
                        emp.setDocumentoticket((numero) + "");
                        adm.actualizar(emp);//GUARDO EMPRESA
                        adm.guardar(facNueva); // GUARDO FACTURA
                        noTicket.setText(numero + "");
                        codigo.setText(facNueva.getCodigo() + "");
                    } else {
                        numero++;
                    }

                }
                Integer numeroFactura = new Integer(emp.getDocumentofac()) + 1;
                while (pasar) {
                    List sihay = adm.query("Select o from Factura as o where o.numero = '" + numeroFactura + "'");
                    if (sihay.size() <= 0) {
                        pasar = false;
                        facNueva.setNumero("" + numeroFactura);
                        emp.setDocumentofac((numeroFactura) + "");
                        adm.actualizar(emp);//GUARDO EMPRESA
                        adm.actualizar(facNueva); // GUARDO FACTURA
                    } else {
                        numeroFactura++;
                    }

                }
                fac.setAnulado(true);
                fac.setAnuladofac(true);
                fac.setUsuarioa(usuAnula); //FALTA CARGAR EL USUARIO QUE CARGÓ SU CLAVE
                adm.actualizar(fac);//GUARDO EMPRESA
                codigo.setText(facNueva.getCodigo() + "");
                //llenarFactura(facNueva); 

                /**
                 * CARGO LOS DATOS
                 */
                ingreso.setDate(fac.getFechaini());
                salida.setDate(new Date());
                btnAgregar.requestFocusInWindow();


                Date act = new Date();
                dias.setVisible(true);
                dias1.setVisible(true);
                dias2.setVisible(true);

                Long minutos0 = diferenciaFechas(fac.getFechaini(), new Date());
                Integer minutos = minutos0.intValue();

                Integer horas = minutos / 60;
                BigDecimal aCobrar = fac.getTotal();

                //EMPIEZO A VERIRICAR
                int noDias0 = 0;
                if (empresaObj.getValorMaximo() > 0) {
                    //INCREMENTO EL VALOR POR DÍA Y SOLO SACO LO DE UN DÍA
                    while (horas > 24) { //VERIRICO SI SOBREPASA
                        horas = horas - 24;
                        minutos = minutos - 1440;

                        noDias0++;
                    }
                }

                if (minutos.intValue() < 0) {
                    minutos = minutos * -1;
                }
                if (horas.intValue() < 0) {
                    horas = horas * -1;
//            horas += 24;
                    dias.setVisible(true);
                    dias1.setVisible(true);
                    dias2.setVisible(true);
                }
                if (minutos.intValue() < 0) {
                    horas = 0;
                    minutos = 0;
                    dias.setVisible(true);
                    dias1.setVisible(true);
                    dias2.setVisible(true);
                }



                Float min = minutos / 60f;
                int indice = min.toString().indexOf(".");
                Float valorf = new Float("0" + min.toString().substring(indice));
                int valorMinutos = java.lang.Math.round((valorf * 60));
                if (minutos.equals(60)) {
                    valorMinutos = 60;
                }
                act.setHours(horas);
                act.setMinutes(valorMinutos);
                if (horas == 1 && valorMinutos == 60) {
                    act.setMinutes(0);
                }
                tiempo.setDate(act);
                placa.setText(fac.getPlaca());
                //VERIFICO EL TIEMPO DE GRACIA SI ES QUE ESTÁ EN EL TIEMPO DE GRACIA
                try {
                    int noDias = 0;
                    noDias = (horas / 24);
                    dias1.setText((noDias + noDias0) + "");
                } catch (Exception e) {
                    dias1.setText("0");
                }
                total.setText(aCobrar.setScale(2, RoundingMode.UP) + "");
                //codigo.setText(fac.getCodigo() + "");
                //


                guardando = false;
            } catch (Exception ab) {
                System.out.println("" + ab);
                JOptionPane.showMessageDialog(this, "El # de Factura ya ha sido ANULADO"
                        + "\no\n"
                        + "NO EXISTE "
                        + "\no\n"
                        + "NO CORRESPONDE A LA FECHA ACTUAL ...!", "", JOptionPane.ERROR_MESSAGE);
                codigoAdministrador.setText("");
                numeroIngresado.setText("");
                //frmAnular.setVisible(false);
                numeroIngresado.setText("");
                numeroIngresado.requestFocusInWindow();
                return;
            }
            frmAnular.setVisible(false);
            principal.auditar("Anula Factura", "No" + numeroIngresado.getText(), "" + cmbUsuarios.getSelectedItem());
            usuAnula = null;
        } else {
            usuAnula = null;
            JOptionPane.showMessageDialog(this, "La Contraseña ingresada está incorrecta...!", "", JOptionPane.ERROR_MESSAGE);
            principal.auditar("Anula Factura", "No" + numeroIngresado.getText(), "" + cmbUsuarios.getSelectedItem());
            frmAnular.setVisible(false);
        }

    }//GEN-LAST:event_frmGuardarAnulado1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        frmAnular.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnAnularFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularFacturaActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            botonesVer.setVisible(false);
            verBot.setSelected(false);
            Accesos permisos = null;
            List<Accesos> accesosL = adm.query("Select o from Accesos as o " + "where o.pantalla = 'AnularTickets' "
                    + "and o.global.codigo  = '" + principal.usuarioActual.getGlobal().getCodigo() + "' and o.ingresar = true  ");
            if (accesosL.size() > 0) {
                permisos = accesosL.get(0);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene permisos para ANULAR FACTURAS...! ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (permisos.getAgregar()) {
                frmAnular.setVisible(true);
                numeroIngresado.setText("");
                codigoAdministrador.setText("");
                numeroIngresado.requestFocusInWindow();
                try {

                    List<Usuarios> uss = adm.query("Select o from Usuarios as o where o.global.nombre like '%admin%'");
                    for (Iterator<Usuarios> it = uss.iterator(); it.hasNext();) {
                        Usuarios usuarios = it.next();
                        cmbUsuarios.addItem(usuarios.getUsuario());
                    }
                } catch (Exception e) {
                    System.out.println("error en cargar usuarios: " + e);
                }
            } else {
                System.out.println("NO TIENE PERMISOS");
                JOptionPane.showMessageDialog(this, "No tiene permisos para ANULAR TICKETS...! ", "JCINFORM", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
        }



    }//GEN-LAST:event_btnAnularFacturaActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel botonesVer;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregar1;
    private javax.swing.JButton btnAgregar2;
    private javax.swing.JButton btnAgregar3;
    private javax.swing.JButton btnAgregar4;
    private javax.swing.JButton btnAnadirProducto;
    private javax.swing.JButton btnAnularFactura;
    private javax.swing.JButton btnAplicarDscto;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnMulta;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoCliente1;
    private javax.swing.JButton btnNuevoCliente2;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalir1;
    private javax.swing.JButton btnSalir2;
    private javax.swing.JTable busquedaTabla;
    public javax.swing.JCheckBox chkEsNotaVenta;
    public javax.swing.JFormattedTextField cliente;
    private javax.swing.JFormattedTextField cliente1;
    private javax.swing.JFormattedTextField cliente2;
    private javax.swing.JComboBox cmbProductos;
    private javax.swing.JComboBox cmbUsuarios;
    public javax.swing.JFormattedTextField codigo;
    private javax.swing.JPasswordField codigoAdministrador;
    private javax.swing.JFormattedTextField codigoBuscar;
    public javax.swing.JFormattedTextField descuento;
    private com.toedter.calendar.JDateChooser desdeF;
    public javax.swing.JLabel dias;
    public javax.swing.JLabel dias1;
    private javax.swing.JLabel dias2;
    public javax.swing.JFormattedTextField direccion;
    private javax.swing.JFormattedTextField direccion1;
    private javax.swing.JFormattedTextField direccion2;
    private javax.swing.JList encontrados;
    private javax.swing.JList encontrados1;
    private javax.swing.JList encontrados2;
    private javax.swing.JList encontrados3;
    private javax.swing.JDialog formaBusqueda;
    private javax.swing.JInternalFrame frmAnular;
    private javax.swing.JInternalFrame frmEliminar;
    private javax.swing.JButton frmGuardarAnulado;
    private javax.swing.JButton frmGuardarAnulado1;
    private javax.swing.JInternalFrame frmTarifa0;
    private javax.swing.JButton guardarTarifa0;
    private com.toedter.calendar.JDateChooser hastaF;
    public javax.swing.JFormattedTextField identificacion;
    private javax.swing.JFormattedTextField identificacion1;
    private javax.swing.JFormattedTextField identificacion2;
    public com.toedter.calendar.JDateChooser ingreso;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel miBotonImagen;
    public javax.swing.JFormattedTextField noTicket;
    public javax.swing.JFormattedTextField nombres;
    private javax.swing.JFormattedTextField nombres1;
    private javax.swing.JFormattedTextField nombres2;
    private javax.swing.JFormattedTextField numeroIngresado;
    private javax.swing.JTextArea observacion;
    private javax.swing.JTextArea observacion1;
    private javax.swing.JTextArea observacionF;
    private javax.swing.JTextArea observacionF1;
    private javax.swing.JPanel panelencontrados;
    private javax.swing.JPanel panelencontrados1;
    private javax.swing.JPanel panelencontrados2;
    private javax.swing.JPanel panelencontrados3;
    public javax.swing.JFormattedTextField placa;
    private javax.swing.JTable productos;
    public com.toedter.calendar.JDateChooser salida;
    public javax.swing.JFormattedTextField telefono;
    private javax.swing.JFormattedTextField telefono1;
    private javax.swing.JFormattedTextField telefono2;
    private javax.swing.JTable ticketsPendientes;
    public com.toedter.calendar.JDateChooser tiempo;
    public javax.swing.JFormattedTextField total;
    private javax.swing.JFormattedTextField txtCantidad;
    private javax.swing.JFormattedTextField txtIva;
    private javax.swing.JFormattedTextField txtIva1;
    private javax.swing.JFormattedTextField txtSubtotal;
    private javax.swing.JFormattedTextField txtSubtotal1;
    private javax.swing.JFormattedTextField txtTotal1;
    private javax.swing.JFormattedTextField txtTotal2;
    private javax.swing.JToggleButton verBot;
    // End of variables declaration//GEN-END:variables
}
