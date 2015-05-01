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
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import sources.FacturaDetalleSource;

import sources.FacturaSource;
//import org.eclipse.persistence.internal.history.HistoricalDatabaseTable;

/**
 *
 * @author geovanny
 */
public class frmFacturaTarifas extends javax.swing.JInternalFrame {

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
    public frmFacturaTarifas(java.awt.Frame parent, boolean modal, Administrador adm1) {
//        super(parent, modal);
        llenarCombo();
        initComponents();
        this.setSize(652, 587);
        empresaObj = new Empresa();
        adm = adm1;
        val = new validaciones();

        panelencontrados.setVisible(false);
//        panelTarifas.setVisible(false);
    }

    public frmFacturaTarifas(java.awt.Frame parent, boolean modal, frmPrincipal lo, Administrador adm1) {
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

            panelencontrados.setVisible(false);
//            panelTarifas.setVisible(false);
            //panelencontrados2.setVisible(false);
            //llenarProductos();
            panelTarifas.setLayout(new GridLayout(4, 1));
            List<Tarifasdiarias> lVenta = adm.query("Select o from Tarifasdiarias as o ");
            for (Iterator<Tarifasdiarias> it = lVenta.iterator(); it.hasNext();) {
                Tarifasdiarias ventanillas = it.next();
                nuevoBotonNumerico(ventanillas);
            }

        } catch (Exception ex) {
            Logger.getLogger(frmFacturaTarifas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void nuevoBotonNumerico(final Tarifasdiarias tarifa) {
        JButton btn = new JButton();
        btn.setSize(200, 20);
//        btn.setName(title);
        btn.setText(tarifa.getNombre()+" \n"+tarifa.getHoras()+" hor.");
        btn.addMouseListener(new MouseAdapter() {
           
            @Override
            public void mouseReleased(MouseEvent evt) {
                final JButton btn = (JButton) evt.getSource();
                Thread cargar = new Thread() {

                    public void run() {
                        btnSalir.setEnabled(false);
                        //btnAgregar.setVisible(false);
                        btn.setEnabled(false);
                        panelTarifas.setVisible(false);
                        tarifaNombre.setText("<html> " + tarifa.getNombre() + " </html>");
                        total.setText("" + tarifa.getValor());
                        guardar(tarifa);


                    }
                };
                cargar.start();

            }
        });

        panelTarifas.add(btn);
        panelTarifas.repaint();
    }

    public void llenarCombo() {

        try {
            perfilesList = new ArrayList<Global>();
            perfilesList = adm.query("Select o from Global as o where o.grupo = 'PER'");
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
        jPanel1 = new javax.swing.JPanel();
        panelTarifas = new javax.swing.JPanel();
        codigo = new javax.swing.JFormattedTextField();
        tarifaNombre = new javax.swing.JLabel();
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
        jPanel12 = new javax.swing.JPanel();
        miBotonImagen = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        frmEliminar = new javax.swing.JInternalFrame();
        jLabel28 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        observacion = new javax.swing.JTextArea();
        btnSalir = new javax.swing.JButton();
        total = new javax.swing.JFormattedTextField();
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

        setTitle("Facturar ");
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
        jLabel8.setText("Facturación según el tipo de vehículo  ..::..");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 270, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Registro el Numero de placa y presiona GUARDAR..::..");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 20, 290, 13);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 640, 40);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        panelTarifas.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.light"));
        panelTarifas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelTarifas.setLayout(null);
        jPanel1.add(panelTarifas);
        panelTarifas.setBounds(10, 10, 270, 150);

        codigo.setBorder(null);
        codigo.setEditable(false);
        codigo.setEnabled(false);
        codigo.setFont(new java.awt.Font("Tahoma", 0, 3)); // NOI18N
        jPanel1.add(codigo);
        codigo.setBounds(10, 30, 20, 20);

        tarifaNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tarifaNombre.setForeground(new java.awt.Color(0, 51, 153));
        tarifaNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tarifaNombre.setText(".");
        jPanel1.add(tarifaNombre);
        tarifaNombre.setBounds(30, 50, 240, 50);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(20, 50, 290, 160);

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

        getContentPane().add(jPanel2);
        jPanel2.setBounds(330, 50, 290, 160);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel12.setLayout(new java.awt.BorderLayout());

        miBotonImagen.setBackground(new java.awt.Color(204, 204, 255));
        miBotonImagen.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 204, 255)));
        jPanel12.add(miBotonImagen, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel12);
        jPanel12.setBounds(20, 230, 290, 190);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setLayout(null);

        frmEliminar.setTitle("Anular tickets");
        frmEliminar.getContentPane().setLayout(null);

        jLabel28.setText("Desea Anular el presente Ticket?, Porqué?");
        frmEliminar.getContentPane().add(jLabel28);
        jLabel28.setBounds(20, 0, 220, 30);

        jButton1.setText("SI");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        frmEliminar.getContentPane().add(jButton1);
        jButton1.setBounds(50, 80, 60, 23);

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

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salir.png"))); // NOI18N
        btnSalir.setMnemonic('S');
        btnSalir.setText("Salir");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setMargin(new java.awt.Insets(2, 2, 2, 2));
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
        btnSalir.setBounds(230, 130, 60, 50);

        total.setBorder(null);
        total.setEditable(false);
        total.setForeground(new java.awt.Color(51, 153, 0));
        total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        total.setText("0.0");
        total.setCaretColor(new java.awt.Color(0, 204, 0));
        total.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jPanel4.add(total);
        total.setBounds(20, 70, 240, 50);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setLabelFor(total);
        jLabel2.setText("A PAGAR:");
        jPanel4.add(jLabel2);
        jLabel2.setBounds(10, 30, 120, 30);

        getContentPane().add(jPanel4);
        jPanel4.setBounds(320, 230, 300, 190);

        getAccessibleContext().setAccessibleName("Facturar");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void guardar(Tarifasdiarias valor) {

        if (guardando == false) {
            guardando = true;

            if (principal.permisos.getAgregar()) {
                try {

                    if (cliente.getText().equals("0") && nombres.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Falta el ingresar o seleccionar el Cliente ...!", "", JOptionPane.ERROR_MESSAGE);
                        identificacion.requestFocusInWindow();
                        return;
                    }
                    total.setText(valor.getValor() + "");
                    Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
                    Factura facActual = new Factura();
                    facActual.setTicket("0");
                    facActual.setUsuarioc(principal.usuarioActual);
                    facActual.setUsuario(principal.usuarioActual);
                    facActual.setNocontar(false);
                    facActual.setDias(1);
                    facActual.setDescuento(BigDecimal.ZERO);
                    if (valor.getNombre().length() >= 20) {
                        facActual.setPlaca("" + valor.getNombre().substring(0, 19));
                    } else {
                        facActual.setPlaca("" + valor.getNombre());
                    }

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
                        System.out.println("CLIENTE: "+cliente.getText());
                        facActual.setClientes(new Clientes(new Integer(cliente.getText())));
                        cli.setCodigo(facActual.getClientes().getCodigo());
                        cli.setDireccion(direccion.getText());
                        cli.setIdentificacion(identificacion.getText());
                        cli.setTelefono(telefono.getText());
                        cli.setNombres(nombres.getText());
                        adm.actualizar(cli);
                    }


                    Date fecSalida = new Date();

                    
                    //aqui tengo que verificar y sumar la hora final con el numero de horas del tarifario
                    //tengo que verificar en empresa si es que se imprime
                    boolean imprimeTicket = true;
                    if(imprimeTicket) {
                            Calendar calendar = Calendar.getInstance();
                             calendar.setTime(fecSalida); // Configuramos la fecha que se recibe
                             calendar.add(Calendar.HOUR, valor.getHoras());  // numero de horas a añadir, o restar en caso de horas<0
                             
                             fecSalida = calendar.getTime();
                    }
                    facActual.setFechafin(fecSalida);
                    facActual.setFecha(fecSalida);
                    facActual.setFechaini(fecSalida);

                    Double ivav = ((empresaObj.getIva() + 100) / 100);
                    Double totalv = valor.getValor().doubleValue();
                    Double subtotalv = totalv / ivav;
                    Double ivav1 = subtotalv * (empresaObj.getIva() / 100);
                    facActual.setTotal(new BigDecimal(totalv));
                    facActual.setSubtotal(new BigDecimal(subtotalv));
                    facActual.setIva(new BigDecimal(ivav1));
                    Date fecTiempo = new Date();
                    facActual.setTiempo(fecTiempo);
                    facActual.setUsuarioc(principal.usuarioActual);
                    adm.guardar(facActual);
                    try {

                        if (empresaObj.getWebcam()) {
                            if (ubicacionDirectorio.contains("build")) {
                                ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
                            }
                            fotografiar("" + facActual.getCodigo(), ubicacionDirectorio + "\\fotos");
                        }
                        if (empresaObj.getIpcam()) {
                            if (ubicacionDirectorio.contains("build")) {
                                ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
                            }

                            fotografiarIp("" + facActual.getCodigo() + ".jpg", ubicacionDirectorio + "\\fotos");
                        }
                        cargarFoto(facActual.getCodigo());
                        principal.cargarFoto(facActual.getCodigo());
                        principal.errores.setText("<html> valor: " + facActual.getTotal() + " </html>");
                    } catch (Exception e) {
                    }

                    Boolean pasar = true;
                    if (facActual.getTotal().doubleValue() > 0) {//SI ES QUE EL TOTAL ES MAYOR A CERO
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
                        try {
                            
                        imprimir(facActual.getCodigo(), emp, 1, false, cli);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String nombreCaja = empresaObj.nombreCaja;
                        
                        //si es que imprime ticket entonces tengo que aumentar la hora de salida.
                        boolean imprimirTicket = true;
                        if(imprimirTicket){
                        
                             Integer numeroTck = new Integer(emp.getDocumentoticket()) + 1;
                                while (pasar) {
                                    List sihay = adm.query("Select o from Factura as o where o.ticket = '" + nombreCaja + numeroTck + "'");
                                    if (sihay.size() <= 0) {
                                        try {
                                            pasar = false;
                                            facActual.setTicket("" + nombreCaja + numeroTck);
                                            emp.setDocumentoticket((numeroTck) + "");
                                            adm.guardar(facActual); // GUARDO FACTURA
                                            adm.actualizar(emp);
                                            codigo.setText(facActual.getCodigo() + "");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            numeroTck++;
                                            pasar = true;
                                        }

                                    } else {
                                        numeroTck++;
                                    }
                                }
                                imprimirTicket(facActual.getCodigo(), emp);
                                
                        }
                        
                        
                    }

                    if (empresaObj.getSeabrefac()) {

                        try {
                            if (empresaObj.getRetardoSalida() != null) {
                                if (empresaObj.getRetardoSalida().length() > 0) {
                                    Integer retardo = new Integer(empresaObj.getRetardoSalida());
                                    System.out.println("---" + new Date());
                                    System.out.println("Retardo de: ");
                                    Thread.sleep(retardo * 1000);
                                    System.out.println("---" + new Date());

                                }
                            }

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

    }

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
            /*
             * Create an array of PrintServices
             */
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            int selectedService = 0;
            /*
             * Scan found services to see if anyone suits our needs
             */
            for (int i = 0; i < services.length; i++) {
                String nombre = services[i].getName();
                if (nombre.contains(emp.getImpticket())) {
                    selectedService = i;
                }
            }
            job.setPrintService(services[selectedService]);

            PrintService printer = services[selectedService];
            AttributeSet att = printer.getAttributes();
            for (Attribute a : att.toArray()) {
                String attributeName;
                String attributeValue;
                attributeName = a.getName();
                attributeValue = att.get(a.getClass()).toString();
                System.out.println(attributeName + " : " + attributeValue);
                if(attributeName.contains("queued-job-count")){
                    if(Integer.parseInt(attributeValue)>0){
                        System.out.println("EXISTE COLA DE IMPRESIÓN EN: "+emp.getImpticket());
                        principal.errores.setText("<html> EXISTE COLA DE IMPRESIÓN, revise que la conexión con su impresora "+emp.getImpticket()+" se encuentra bien o que contenga PAPEL</html>");
                         System.out.println("EXISTE COLA DE IMPRESIÓN EN: "+emp.getImpticket());
                        principal.imAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alto.png"))); // NOI18N
                        //mensajeErrorImpresora.setText("<html> EXISTE COLA DE IMPRESIÓN, revise que la conexión con su impresora "+emp.getImpticket()+" se encuentra bien o que contenga PAPEL</html>");
                        //mensajeErrorImpresora.setVisible(true); 
                    }else{
                        //mensajeErrorImpresora.setVisible(false);
                    }
                }
                  attributeName = null;
                  attributeValue = null;
                
             
            }
            printer = null;
            att = null;

            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            MediaSizeName mediaSizeName = MediaSize.findMedia(3.08F, 3.70F, MediaPrintableArea.INCH);
            //MediaSizeName mediaSizeName = MediaSize.findMedia(3F,3F, MediaPrintableArea.INCH);
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

//            Boolean[] datos = exporter.getPrintStatus();
//            System.out.println("" + datos);

//            JasperViewer viewer = new JasperViewer(masterPrint, false); //PARA VER EL REPORTE ANTES DE IMPRIMIR
//            viewer.show();
//            try {
//                JasperPrintManager.printPage(masterPrint, 0, false);//LE ENVIO A IMPRIMIR false NO MUESTRA EL CUADRO DE DIALOGO
//            } catch (JRException ex) {
//                ex.printStackTrace();
//            }
        } catch (Exception ex) {
            Logger.getLogger(frmTicket.class.getName()).log(Level.SEVERE, null, ex);
            //lger.logger(frmTicket.class.getName(), ex + "");
        }

//        } catch (JRException ex) {
//            ex.printStackTrace();
//        }
    }

    
    public void fotografiar(String nombre, String direccion) {
        int resultado = principal.ver.Fotografiar(direccion, false, nombre);
        if (resultado == 0) {
            JOptionPane.showMessageDialog(null, "Error en la Fotografia");
        }
    }

    public void fotografiarIp(String nombre, String direccion) {
        principal.verIp.tomarFotoIp(direccion + separador + nombre, principal);
//        if (resultado == 0) {
//            JOptionPane.showMessageDialog(null, "Error en la Fotografia");
//        }
    }

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
            for (int i = 0; i < services.length; i++) {
                String nombre = services[i].getName();
                if (nombre.contains(emp.getImpfactura())) {
                    selectedService = i;
                    break;
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
                        Logger.getLogger(frmFacturaTarifas.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(frmFacturaTarifas.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(frmFacturaTarifas.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(frmFacturaTarifas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return;
        }




        Date act = new Date();

        Long minutos0 = diferenciaFechas(fac.getFechaini(), new Date());
        Integer minutos = minutos0.intValue();

        Integer horas = minutos / 60;
        if (minutos.intValue() < 0) {
            minutos = minutos * -1;
        }
        if (horas.intValue() < 0) {
            horas = horas * -1;
//            horas += 24;

        }
        if (minutos.intValue() < 0) {
            horas = 0;
            minutos = 0;

        }
        BigDecimal aCobrar = new BigDecimal(0);
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

        //VERIFICO EL TIEMPO DE GRACIA SI ES QUE ESTÁ EN EL TIEMPO DE GRACIA
        if (valorMinutos <= empresaObj.getGracia().intValue() && empresaObj.getGracia().intValue() > 0) {
            BigDecimal descuento = buscar(valorMinutos);
            aCobrar = aCobrar.subtract(descuento);
        }
//        for (int a = 0; a < horas; a++) {
//            aCobrar = aCobrar.add(buscar(60));
//        }
        try {
            int noDias = 0;
            noDias = (horas / 24);

        } catch (Exception e) {
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

    public void llenarCliente(Clientes nCliente) {
        cliente.setText("" + nCliente.getCodigo());
        identificacion.setText(nCliente.getIdentificacion());
        nombres.setText(nCliente.getNombres());
        direccion.setText(nCliente.getDireccion());
        telefono.setText(nCliente.getTelefono());
    }

    public void llenarCliente2(Clientes nCliente) {
        
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
 
    private void btnSalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalirKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnSalirKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_formKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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
                facActual.setTotal(BigDecimal.ZERO);
                total.setText("0.0");
                codigo.setText("");


                adm.actualizar(facActual);
                frmEliminar.setVisible(false);
                principal.noDisponibles();
            } catch (Exception ex) {
                Logger.getLogger(frmFacturaTarifas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        frmEliminar.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        cliente.setText("0");
        identificacion.setText("");
        nombres.setText("");
        direccion.setText("");
        telefono.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnSalir;
    private javax.swing.JTable busquedaTabla;
    private javax.swing.JFormattedTextField cliente;
    private javax.swing.JFormattedTextField codigo;
    private javax.swing.JFormattedTextField codigoBuscar;
    private javax.swing.JFormattedTextField direccion;
    private javax.swing.JList encontrados;
    private javax.swing.JDialog formaBusqueda;
    private javax.swing.JInternalFrame frmEliminar;
    private javax.swing.JFormattedTextField identificacion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel miBotonImagen;
    private javax.swing.JFormattedTextField nombres;
    private javax.swing.JTextArea observacion;
    private javax.swing.JPanel panelTarifas;
    private javax.swing.JPanel panelencontrados;
    private javax.swing.JLabel tarifaNombre;
    private javax.swing.JFormattedTextField telefono;
    private javax.swing.JFormattedTextField total;
    // End of variables declaration//GEN-END:variables
}
