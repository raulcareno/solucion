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
import hibernate.cargar.UsuarioActivo;
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
import sources.FacturaDetalleSource;

import sources.FacturaSource;
//import org.eclipse.persistence.internal.history.HistoricalDatabaseTable;

/**
 *
 * @author geovanny
 */
public class frmFactura extends javax.swing.JInternalFrame {

    /** Creates new form frmEmpresa */
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

    /** Creates new form frmProfesores */
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
            llenarProductos();
            noTicket.requestFocusInWindow();
            noTicket.requestFocusInWindow();
            noTicket.requestFocusInWindow();
            noTicket.requestFocusInWindow();
            noTicket.requestFocusInWindow();
            noTicket.requestFocusInWindow();
            noTicket.requestFocusInWindow();
            noTicket.requestFocusInWindow();
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
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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
        jPanel4 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        total = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        btnMulta = new javax.swing.JButton();
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
        cmbProductos = new javax.swing.JComboBox();
        btnAnadirProducto = new javax.swing.JButton();
        txtCantidad = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();

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

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Facturación de Tickets ..::..");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 270, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10));
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Registro el Numero de placa y presiona GUARDAR..::..");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 20, 290, 13);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 640, 40);

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
        ingreso.setFont(new java.awt.Font("Tahoma", 1, 14));
        jPanel1.add(ingreso);
        ingreso.setBounds(70, 50, 210, 20);

        jLabel7.setForeground(new java.awt.Color(0, 0, 153));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("TICKET No: ");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 10, 60, 14);

        noTicket.setFont(new java.awt.Font("Tahoma", 1, 12));
        noTicket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                noTicketKeyPressed(evt);
            }
        });
        jPanel1.add(noTicket);
        noTicket.setBounds(70, 10, 90, 21);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel13.setForeground(new java.awt.Color(0, 0, 204));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Tiempo: ");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(20, 90, 50, 20);

        salida.setBackground(new java.awt.Color(255, 255, 255));
        salida.setDateFormatString("dd-MMM-yyyy HH:mm:ss");
        salida.setFont(new java.awt.Font("Tahoma", 1, 14));
        jPanel1.add(salida);
        salida.setBounds(70, 70, 210, 20);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel17.setForeground(new java.awt.Color(0, 102, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Ingreso: ");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(10, 50, 60, 20);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel19.setForeground(new java.awt.Color(204, 0, 0));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Salida: ");
        jPanel1.add(jLabel19);
        jLabel19.setBounds(20, 70, 50, 20);

        tiempo.setBackground(new java.awt.Color(255, 255, 255));
        tiempo.setDateFormatString("HH:mm");
        tiempo.setFont(new java.awt.Font("Tahoma", 1, 14));
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

        codigo.setBorder(null);
        codigo.setEditable(false);
        codigo.setEnabled(false);
        codigo.setFont(new java.awt.Font("Tahoma", 0, 3));
        jPanel1.add(codigo);
        codigo.setBounds(10, 30, 20, 20);

        dias.setFont(new java.awt.Font("Tahoma", 1, 11));
        dias.setForeground(new java.awt.Color(0, 0, 204));
        dias.setText("DIA(s)");
        jPanel1.add(dias);
        dias.setBounds(130, 110, 60, 20);

        dias1.setFont(new java.awt.Font("Tahoma", 1, 13));
        dias1.setForeground(new java.awt.Color(204, 0, 0));
        dias1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dias1.setText("0");
        jPanel1.add(dias1);
        dias1.setBounds(80, 110, 40, 20);

        dias2.setFont(new java.awt.Font("Tahoma", 1, 11));
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
        jScrollPane2.setBounds(10, 10, 170, 90);

        jPanel2.add(panelencontrados);
        panelencontrados.setBounds(80, 50, 190, 110);

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
        nombres.setBounds(80, 30, 190, 20);

        direccion.setEditable(false);
        direccion.setText("S/D");
        jPanel2.add(direccion);
        direccion.setBounds(80, 50, 190, 20);

        cliente.setBorder(null);
        cliente.setEditable(false);
        cliente.setText("1");
        cliente.setEnabled(false);
        cliente.setFont(new java.awt.Font("Tahoma", 0, 8));
        jPanel2.add(cliente);
        cliente.setBounds(190, 10, 20, 10);

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

        jPanel5.add(jPanel2);
        jPanel2.setBounds(310, 10, 290, 160);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setLayout(null);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png"))); // NOI18N
        btnAgregar.setMnemonic('G');
        btnAgregar.setText("Guardar");
        btnAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar.setMargin(new java.awt.Insets(2, 2, 2, 2));
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
        btnAgregar.setBounds(160, 130, 60, 50);

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
        jPanel4.add(btnSalir);
        btnSalir.setBounds(220, 130, 60, 50);

        total.setBorder(null);
        total.setEditable(false);
        total.setForeground(new java.awt.Color(51, 153, 0));
        total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        total.setText("0.0");
        total.setCaretColor(new java.awt.Color(0, 204, 0));
        total.setFont(new java.awt.Font("Tahoma", 1, 36));
        jPanel4.add(total);
        total.setBounds(130, 60, 150, 50);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setLabelFor(total);
        jLabel2.setText("A PAGAR:");
        jPanel4.add(jLabel2);
        jLabel2.setBounds(10, 70, 120, 30);

        btnMulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/perdida.png"))); // NOI18N
        btnMulta.setMnemonic('G');
        btnMulta.setText("Multa por Pérdida");
        btnMulta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMulta.setMargin(new java.awt.Insets(2, 2, 2, 2));
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
        jPanel4.add(btnMulta);
        btnMulta.setBounds(60, 130, 100, 50);

        jPanel5.add(jPanel4);
        jPanel4.setBounds(310, 170, 300, 190);

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

        cliente1.setBorder(null);
        cliente1.setEditable(false);
        cliente1.setText("1");
        cliente1.setEnabled(false);
        cliente1.setFont(new java.awt.Font("Tahoma", 0, 8));
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

        jPanel6.add(jScrollPane1);
        jScrollPane1.setBounds(300, 50, 320, 120);

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
        btnAgregar1.setBounds(440, 10, 60, 50);

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
        btnSalir1.setBounds(500, 10, 60, 50);

        txtTotal1.setBorder(null);
        txtTotal1.setEditable(false);
        txtTotal1.setForeground(new java.awt.Color(51, 153, 0));
        txtTotal1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal1.setText("0.0");
        txtTotal1.setCaretColor(new java.awt.Color(0, 204, 0));
        txtTotal1.setFont(new java.awt.Font("Tahoma", 1, 36));
        jPanel8.add(txtTotal1);
        txtTotal1.setBounds(140, 60, 140, 40);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setLabelFor(total);
        jLabel6.setText("A PAGAR:");
        jPanel8.add(jLabel6);
        jLabel6.setBounds(20, 70, 130, 30);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("IVA:");
        jPanel8.add(jLabel25);
        jLabel25.setBounds(67, 30, 90, 14);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("SUBTOTAL:");
        jPanel8.add(jLabel26);
        jLabel26.setBounds(80, 10, 80, 14);

        txtSubtotal.setEditable(false);
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubtotal.setText("0.0");
        jPanel8.add(txtSubtotal);
        txtSubtotal.setBounds(172, 10, 110, 20);

        txtIva.setEditable(false);
        txtIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIva.setText("0.0");
        jPanel8.add(txtIva);
        txtIva.setBounds(172, 30, 110, 20);

        jPanel6.add(jPanel8);
        jPanel8.setBounds(20, 190, 600, 100);

        cmbProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbProductosKeyPressed(evt);
            }
        });
        jPanel6.add(cmbProductos);
        cmbProductos.setBounds(300, 30, 200, 20);

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
        btnAnadirProducto.setBounds(550, 30, 63, 20);

        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setText("1");
        txtCantidad.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
        });
        jPanel6.add(txtCantidad);
        txtCantidad.setBounds(500, 30, 50, 20);

        jLabel23.setText("Productos disponibles");
        jPanel6.add(jLabel23);
        jLabel23.setBounds(300, 10, 190, 14);

        jLabel24.setText("Cantidad");
        jPanel6.add(jLabel24);
        jLabel24.setBounds(500, 10, 50, 14);

        jLabel27.setForeground(new java.awt.Color(255, 51, 0));
        jLabel27.setText("Para QUITAR un elemento seleccione y presione SUPRIMIR");
        jPanel6.add(jLabel27);
        jLabel27.setBounds(300, 170, 310, 14);

        jTabbedPane1.addTab("VENTA DE TARJETAS", jPanel6);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 42, 620, 420);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        if (guardando == false) {
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

                    }


                    Date fecSalida = new Date();
                    fecSalida.setHours(salida.getDate().getHours());
                    fecSalida.setMinutes(salida.getDate().getMinutes());
                    fecSalida.setSeconds(salida.getDate().getSeconds());
                    facActual.setFechafin(fecSalida);

                    facActual.setNumero(emp.getDocumentofac());
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


                    adm.actualizar(facActual);
                    Integer numero = new Integer(emp.getDocumentofac());
                    emp.setDocumentofac((numero + 1) + "");
                    int dia = 0;
                    try {
                        dia = new Integer(dias1.getText());
                    } catch (Exception e) {
                        dia = 0;
                    }
                    imprimir(facActual.getCodigo(), emp, dia, false, cli);
                    adm.actualizar(emp);
                 if(empresaObj.getSeabrefac()){

                    try {
                        LeerTarjeta ta = (LeerTarjeta) principal.puertoListo.get(0);
                        ta.outputSream.write(empresaObj.getPuertafac().getBytes());
                    } catch (Exception ex) {
                        Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("ABRIO PUERTA: " + empresaObj.getPuertafac());
            }else{
                    System.out.println("NO ABRE BARRERA POR DESHABILITACION DEN FRMEMPRESA " );
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
                    principal = null;
                    empresaObj = null;
                    System.gc();


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
            parametros.put("placa", placa.getText());
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

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        principal.contenedor.requestFocus();
        this.setVisible(false);
        principal = null;
        empresaObj = null;
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

//                        int valor = JOptionPane.showConfirmDialog(this, ,"JCINFORM",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[] { "opcion 1", "opcion 2", "opcion 3" },"opcion 1" );
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
        }
        ingreso.setDate(fac.getFechaini());
        salida.setDate(new Date());



        Date act = new Date();
        dias.setVisible(true);
        dias1.setVisible(true);
        dias2.setVisible(true);

        Long minutos0 = diferenciaFechas(fac.getFechaini(), new Date());
        Integer minutos = minutos0.intValue();

        Integer horas = minutos / 60;
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
        BigDecimal aCobrar = new BigDecimal(0);
        aCobrar = aCobrar.add(buscar(minutos));
        
        Float min = minutos / 60f;
        int indice = min.toString().indexOf(".");
        Float valorf = new Float("0" + min.toString().substring(indice));
        int valorMinutos = java.lang.Math.round((valorf * 60));
        act.setHours(horas);
        act.setMinutes(valorMinutos);
        tiempo.setDate(act);
        placa.setText(fac.getPlaca());
        if(valorMinutos <= empresaObj.getGracia().intValue()){
            BigDecimal descuento = buscar(valorMinutos);
            aCobrar = aCobrar.subtract(descuento);
        }
//        for (int a = 0; a < horas; a++) {
//            aCobrar = aCobrar.add(buscar(60));
//        }
        try {
            int noDias = 0;
            noDias = (horas / 24);
            dias1.setText(noDias + "");
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
     BigDecimal valorRegresa= new BigDecimal(0);
     int ultimo = 0;
     BigDecimal valorUltimo= new BigDecimal(0);
        for (Iterator<Tarifas> it = tarifario.iterator(); it.hasNext();) {
            Tarifas tarifas = it.next();
            int desde = tarifas.getDesde();
            int hasta = tarifas.getHasta();
            if (minutos >= desde && minutos <= hasta) {
                valorRegresa = tarifas.getValor();
                return valorRegresa;
            }
            ultimo = hasta;
            valorUltimo =tarifas.getValor();
         
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
        System.out.println("" + evt.getKeyChar());
        if (evt.getKeyCode() == evt.VK_ENTER) {
            try {
                ingreso.setDate(null);
                salida.setDate(null);
                placa.setText(null);
                tiempo.setDate(null);

                Factura fac = (Factura) adm.querySimple("Select o from Factura as o where o.ticket = '" + new Integer(noTicket.getText()) + "' ");
                if (fac != null) {
                    llenarFactura(fac);
                    try {
                        cargarFoto(fac.getCodigo());
                    } catch (Exception es) {
                        System.out.println("NO SE CARGO FOTO");
                    }

                    btnAgregar.requestFocusInWindow();
                } else {
                    ingreso.setDate(null);
                    salida.setDate(null);
                    placa.setText(null);
                    tiempo.setDate(null);
                }
            } catch (Exception e) {
                ingreso.setDate(null);
                salida.setDate(null);
                placa.setText(null);
                tiempo.setDate(null);
            }
        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            principal.contenedor.requestFocus();
            principal = null;
            empresaObj = null;
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
            llenarFactura(fac);
            noTicket.setText(fac.getTicket());
        }
    }//GEN-LAST:event_encontrados1MouseClicked

    private void encontrados1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_encontrados1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            this.panelencontrados1.setVisible(false);
            Factura fac = (Factura) this.encontrados1.getSelectedValue();
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
            facActual.setNumero(emp.getDocumentofac());
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
                det.setIva(det.getSubtotal().multiply(new BigDecimal(empresaObj.getIva() / 100)));
                det.setTotal(det.getSubtotal().add(det.getIva()));
                det.setFactura(facActual);
                adm.guardar(det);
            }
            imprimir(facActual.getCodigo(), emp, dia, true, cli);
            //JOptionPane.showMessageDialog(this, "Registro Almacenado con éxito...!");
            DefaultTableModel dtm = (DefaultTableModel) productos.getModel();
            dtm.getDataVector().removeAllElements();
            productos.setModel(dtm);
            principal.auditar("Cobros", "No" + facActual.getNumero(), "GUARDAR");
            principal.contenedor.requestFocus();

            this.setVisible(false);
            principal = null;
            empresaObj = null;
            System.gc();
        } catch (Exception ex) {
            Logger.getLogger(frmFactura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnAgregar1ActionPerformed

    private void btnSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir1ActionPerformed
        // TODO add your handling code here:
        principal.contenedor.requestFocus();
        this.setVisible(false);
        principal = null;
        empresaObj = null;
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
            obj[3] = new BigDecimal(asigRub.getValor() * Integer.parseInt(txtCantidad.getText()));
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
        if (guardando == false) {
            guardando = true;

            if (principal.permisos.getAgregar()) {
                try {


                    Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
                    Factura facActual = new Factura();
                    Clientes nuevoCl = (Clientes) adm.buscarClave(new Integer(1), Clientes.class);
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

                    adm.guardar(facActual);
                    Integer numero = new Integer(emp.getDocumentofac());
                    emp.setDocumentofac((numero + 1) + "");
                    int dia = 0;
                    try {
                        dia = new Integer(dias1.getText());
                    } catch (Exception e) {
                        dia = 0;
                    }
                    imprimir(facActual.getCodigo(), emp, dia, false, nuevoCl);
                    adm.actualizar(emp);
                    if (empresaObj.getSeabretic()) {
                        Thread cargar = new Thread() {

                            public void run() {
                                AbrirPuerta.abrir(empresaObj.getPuerto(), frmPrincipal.out);
                                System.out.println("SALIO PUERTA: " + frmPrincipal.out);

                            }
                        };

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
                    principal = null;
                    empresaObj = null;
                    System.gc();


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
    }//GEN-LAST:event_btnMultaActionPerformed

    private void btnMultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnMultaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMultaKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregar1;
    private javax.swing.JButton btnAnadirProducto;
    private javax.swing.JButton btnMulta;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoCliente1;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalir1;
    private javax.swing.JTable busquedaTabla;
    private javax.swing.JFormattedTextField cliente;
    private javax.swing.JFormattedTextField cliente1;
    private javax.swing.JComboBox cmbProductos;
    private javax.swing.JFormattedTextField codigo;
    private javax.swing.JFormattedTextField codigoBuscar;
    private javax.swing.JLabel dias;
    private javax.swing.JLabel dias1;
    private javax.swing.JLabel dias2;
    private javax.swing.JFormattedTextField direccion;
    private javax.swing.JFormattedTextField direccion1;
    private javax.swing.JList encontrados;
    private javax.swing.JList encontrados1;
    private javax.swing.JList encontrados2;
    private javax.swing.JDialog formaBusqueda;
    private javax.swing.JFormattedTextField identificacion;
    private javax.swing.JFormattedTextField identificacion1;
    private com.toedter.calendar.JDateChooser ingreso;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
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
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel miBotonImagen;
    public javax.swing.JFormattedTextField noTicket;
    private javax.swing.JFormattedTextField nombres;
    private javax.swing.JFormattedTextField nombres1;
    private javax.swing.JPanel panelencontrados;
    private javax.swing.JPanel panelencontrados1;
    private javax.swing.JPanel panelencontrados2;
    private javax.swing.JFormattedTextField placa;
    private javax.swing.JTable productos;
    private com.toedter.calendar.JDateChooser salida;
    private javax.swing.JFormattedTextField telefono;
    private javax.swing.JFormattedTextField telefono1;
    private com.toedter.calendar.JDateChooser tiempo;
    private javax.swing.JFormattedTextField total;
    private javax.swing.JFormattedTextField txtCantidad;
    private javax.swing.JFormattedTextField txtIva;
    private javax.swing.JFormattedTextField txtSubtotal;
    private javax.swing.JFormattedTextField txtTotal1;
    // End of variables declaration//GEN-END:variables
}