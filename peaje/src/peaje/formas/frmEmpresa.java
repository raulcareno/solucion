package peaje.formas;

import hibernate.Empresa;
import hibernate.Global;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.CommPortIdentifier;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import hibernate.cargar.Administrador;
import hibernate.cargar.validaciones;
import xml.XMLEmpresa;
//import hibernate.cargar.validaciones;

//import org.eclipse.persistence.internal.history.HistoricalDatabaseTable;
/**
 *
 * @author geovanny
 */
public class frmEmpresa  extends javax.swing.JInternalFrame  {

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

    /** Creates new form frmProfesores */
//    public frmEmpresa(java.awt.Frame parent, boolean modal, Administrador adm1) {
////        super(parent, modal);
//        adm = adm1;
//        llenarCombo();
//        initComponents();
//        this.setSize(615, 508);
//        empresaObj = new Empresa();
//
//        val = new validaciones();
//
//            try {
//
//               Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
//                this.empresaObj = emp;
//                bindingGroup.unbind();
//                bindingGroup.bind();
//               
//            } catch (Exception ex) {
//                Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//    }

    public frmEmpresa(java.awt.Frame parent, boolean modal, frmPrincipal lo, Administrador adm1) {
//        super(parent, modal);
        this.desktopContenedor = lo.contenedor;
        adm = adm1;
        llenarCombo();
        initComponents();
        this.setSize(615, 508);
        empresaObj = lo.empresaObj;

        val = new validaciones();
        principal = lo;
        cmbTicket.removeAllItems();
        cmbFactura.removeAllItems();
        barreras.setText(empresaObj.getBarreras());
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (int i = 0; i < services.length; i++) {
            cmbTicket.addItem(services[i].getName());
            cmbFactura.addItem(services[i].getName());
            cmbImpresora.addItem(services[i].getName());
            cmbImpresora1.addItem(services[i].getName());
            //String nombre = services[i].getName().toUpperCase();
        }
        CommPortIdentifier portId;
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
                cmbEntrada1.removeAllItems();
                cmbEntrada2.removeAllItems();
                cmbEntrada3.removeAllItems();
                cmbEntrada4.removeAllItems();
                cmbSalida1.removeAllItems();
                cmbSalida2.removeAllItems();
                cmbSalida3.removeAllItems();
                cmbSalida4.removeAllItems();
                cmbPuertoPrincipal.removeAllItems();
                cmbPuertoLed.removeAllItems();
                cmbPortBarras.removeAllItems();
//                cmbPuertaSale.removeAllItems();
                cmbPortBarras2.removeAllItems();
//                cmbPuertaSale2.removeAllItems();

//                cmbSalida1.removeAllItems();
//                cmbSalida2.removeAllItems();
//                cmbSalida3.removeAllItems();
                ArrayList arr = new ArrayList();
         while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if(!arr.contains(portId.getName()))
                    arr.add(portId.getName());
            }

        }
                String blanco = "";
                cmbPuertoPrincipal.addItem(blanco);
                cmbPuertoLed.addItem(blanco);
                cmbPortBarras.addItem(blanco);
                cmbEntrada1.addItem(blanco);
                cmbEntrada2.addItem(blanco);
                cmbEntrada3.addItem(blanco);
                cmbEntrada4.addItem(blanco);
                cmbSalida1.addItem(blanco);
                cmbSalida2.addItem(blanco);
                cmbSalida3.addItem(blanco);
                cmbSalida4.addItem(blanco);
                cmbPortBarras2.addItem(blanco);
//                cmbPuertaSale.removeAllItems();
//                cmbPuertaSale2.removeAllItems();


        for (Iterator it = arr.iterator(); it.hasNext();) {
            Object object = it.next();
                cmbEntrada1.addItem(object);
                cmbEntrada2.addItem(object);
                cmbEntrada3.addItem(object);
                cmbEntrada4.addItem(object);
                cmbSalida1.addItem(object);
                cmbSalida2.addItem(object);
                cmbSalida3.addItem(object);
                cmbSalida4.addItem(object);

                cmbPuertoPrincipal.addItem(object);
                cmbPuertoLed.addItem(object);
                cmbPortBarras.addItem(object);
                cmbPortBarras2.addItem(object);
           
//                cmbSalida1.addItem(object);
//                cmbSalida2.addItem(object);
//                cmbSalida3.addItem(object);

        }
           try {


//                Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
//                this.empresaObj = emp;
               
                bindingGroup.unbind();
                bindingGroup.bind();

            } catch (Exception ex) {
                Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
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
        retardoEntrada.setEditable(estado);
        retardoSalida.setEditable(estado);
        ipBarras1.setEditable(estado);
        ipBarras2.setEditable(estado);
        puertoBarras1.setEditable(estado);
        puertoBarras2.setEditable(estado);
        codigo.setEditable(estado);
        nombres.setEditable(estado);
        direccion.setEditable(estado);
        razonsocial.setEditable(estado);
        telefono.setEditable(estado);
        graciasalida.setEditable(estado);
        graciaentrada.setEditable(estado);
        url.setEditable(estado);
        chkIpcam.setEnabled(estado);
        chkWebcam.setEnabled(estado);
        parqueaderos.setEditable(estado);
        iva.setEditable(estado);
        cmbFactura.setEnabled(estado);
        cmbTicket.setEnabled(estado);
        cmbEntrada1.setEnabled(estado);
        cmbEntrada2.setEnabled(estado);
        cmbEntrada3.setEnabled(estado);
        cmbEntrada4.setEnabled(estado);
        cmbSalida1.setEnabled(estado);
        cmbSalida2.setEnabled(estado);
        cmbSalida3.setEnabled(estado);
        cmbSalida4.setEnabled(estado);
        cmbImpresora.setEnabled(estado);
        cmbImpresora1.setEnabled(estado);
        cmbPuertoPrincipal.setEnabled(estado);
        cmbPuertoLed.setEnabled(estado);
        cmbPortBarras.setEnabled(estado);
        cmbPortBarras2.setEnabled(estado);
        cmbPuertaSale.setEnabled(estado);
        cmbPuertaSale2.setEnabled(estado);
        cmbPuerta1.setEnabled(estado);
        cmbPuerta2.setEnabled(estado);
        cmbPuerta3.setEnabled(estado);
        cmbPuerta4.setEnabled(estado);
        chkActivo1.setEnabled(estado);
        chkActivo2.setEnabled(estado);
        chkActivo3.setEnabled(estado);
        chkActivo4.setEnabled(estado);
        multa.setEditable(estado);
        cmbPuertaEntra1.setEnabled(estado);
        cmbPuertaEntra2.setEnabled(estado);
        cmbPuertaTicket.setEnabled(estado);
        cmbPuertaFac.setEnabled(estado);
    }

    public void limpiar() {
        String estado = "";
        codigo.setText(estado);
        nombres.setText(estado);
        direccion.setText(estado);
        razonsocial.setText(estado);
        telefono.setText(estado);
        parqueaderos.setText(estado);

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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        formaEmpresa = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        codigoBuscar = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        busquedaTabla = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        cmbPuertoBarras1 = new javax.swing.JComboBox();
        jLabel35 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nombres = new javax.swing.JFormattedTextField();
        direccion = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        codigo = new javax.swing.JFormattedTextField();
        razonsocial = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        telefono = new javax.swing.JFormattedTextField();
        parqueaderos = new javax.swing.JFormattedTextField();
        cmbFactura = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmbTicket = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        cmbImpresora = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        iva = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        graciaentrada = new javax.swing.JFormattedTextField();
        graciasalida = new javax.swing.JFormattedTextField();
        jLabel28 = new javax.swing.JLabel();
        chkWebcam = new javax.swing.JCheckBox();
        seabrefactura = new javax.swing.JCheckBox();
        seabreticket = new javax.swing.JCheckBox();
        multa = new javax.swing.JFormattedTextField();
        jLabel29 = new javax.swing.JLabel();
        cmbImpresora1 = new javax.swing.JComboBox();
        jLabel37 = new javax.swing.JLabel();
        chkIpcam = new javax.swing.JCheckBox();
        jLabel38 = new javax.swing.JLabel();
        url = new javax.swing.JFormattedTextField();
        cmbPuertaFac = new javax.swing.JComboBox();
        cmbPuertaTicket = new javax.swing.JComboBox();
        jLabel50 = new javax.swing.JLabel();
        chkBloquear = new javax.swing.JCheckBox();
        chkBloquearSalida = new javax.swing.JCheckBox();
        barreras = new javax.swing.JFormattedTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        retardoEntrada = new javax.swing.JFormattedTextField();
        retardoSalida = new javax.swing.JFormattedTextField();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        cmbEntrada1 = new javax.swing.JComboBox();
        cmbPuerta1 = new javax.swing.JComboBox();
        cmbPuerta2 = new javax.swing.JComboBox();
        cmbEntrada2 = new javax.swing.JComboBox();
        cmbPuerta3 = new javax.swing.JComboBox();
        cmbEntrada3 = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cmbEntrada4 = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        cmbPuerta4 = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        chkActivo1 = new javax.swing.JCheckBox();
        chkActivo2 = new javax.swing.JCheckBox();
        chkActivo3 = new javax.swing.JCheckBox();
        chkActivo4 = new javax.swing.JCheckBox();
        cmbSalida1 = new javax.swing.JComboBox();
        cmbSalida3 = new javax.swing.JComboBox();
        cmbSalida2 = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cmbSalida4 = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        cmbPuertoPrincipal = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        cmbPuertoLed = new javax.swing.JComboBox();
        cmbPortBarras = new javax.swing.JComboBox();
        jLabel30 = new javax.swing.JLabel();
        cmbPuertaSale = new javax.swing.JComboBox();
        cmbPuertaSale2 = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        cmbPortBarras2 = new javax.swing.JComboBox();
        jLabel36 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        cmbPuertaEntra1 = new javax.swing.JComboBox();
        cmbPuertaEntra2 = new javax.swing.JComboBox();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        ipBarras1 = new javax.swing.JFormattedTextField();
        puertoBarras1 = new javax.swing.JFormattedTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        ipBarras2 = new javax.swing.JFormattedTextField();
        puertoBarras2 = new javax.swing.JFormattedTextField();
        jLabel52 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();

        formaEmpresa.setLocationByPlatform(true);
        formaEmpresa.getContentPane().setLayout(null);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel8.setLayout(null);

        codigoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoBuscarKeyPressed(evt);
            }
        });
        jPanel8.add(codigoBuscar);
        codigoBuscar.setBounds(100, 10, 220, 20);

        jLabel11.setText("NOMBRES:");
        jPanel8.add(jLabel11);
        jLabel11.setBounds(10, 10, 70, 14);

        formaEmpresa.getContentPane().add(jPanel8);
        jPanel8.setBounds(10, 10, 510, 40);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel7.setLayout(null);

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
        busquedaTabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        busquedaTabla.getColumnModel().getColumn(1).setPreferredWidth(350);

        jPanel7.add(jScrollPane3);
        jScrollPane3.setBounds(20, 20, 480, 150);

        formaEmpresa.getContentPane().add(jPanel7);
        jPanel7.setBounds(10, 60, 510, 180);

        jLabel4.setText("Entrada 1:");

        jCheckBox2.setText("Activo");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jLabel17.setText("Lectora 1:");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setText("Puerta");

        cmbPuertoBarras1.setEditable(true);
        cmbPuertoBarras1.setEnabled(false);
        cmbPuertoBarras1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuertoBarras1KeyPressed(evt);
            }
        });

        jLabel35.setForeground(new java.awt.Color(0, 0, 153));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel35.setText("Sale por:");

        setTitle("Datos de la Empresa");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/empresa.png"))); // NOI18N
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
        jPanel3.setOpaque(false);
        jPanel3.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Catálogo de Empresas ..::..");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 270, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Configuración de la Empresa ..::..");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 20, 250, 13);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 440, 40);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setLayout(null);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.gif"))); // NOI18N
        btnBuscar.setMnemonic('B');
        btnBuscar.setText("Buscar");
        btnBuscar.setEnabled(false);
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBuscar.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        btnBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBuscarKeyPressed(evt);
            }
        });
        jPanel4.add(btnBuscar);
        btnBuscar.setBounds(100, 10, 60, 50);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png"))); // NOI18N
        btnAgregar.setMnemonic('N');
        btnAgregar.setText("Nuevo");
        btnAgregar.setEnabled(false);
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
        btnAgregar.setBounds(160, 10, 60, 50);

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
        btnModificar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnModificarKeyPressed(evt);
            }
        });
        jPanel4.add(btnModificar);
        btnModificar.setBounds(220, 10, 60, 50);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eliminar.png"))); // NOI18N
        btnEliminar.setMnemonic('E');
        btnEliminar.setText("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        btnEliminar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminarKeyPressed(evt);
            }
        });
        jPanel4.add(btnEliminar);
        btnEliminar.setBounds(280, 10, 60, 50);

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
        btnSalir.setBounds(340, 10, 60, 50);

        getContentPane().add(jPanel4);
        jPanel4.setBounds(10, 390, 430, 70);

        jPanel2.setLayout(null);

        jLabel1.setForeground(new java.awt.Color(0, 0, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("CI/RUC:");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(10, 0, 100, 18);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Dirección:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(10, 50, 100, 18);

        nombres.setEditable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.nombre}"), nombres, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        nombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombresKeyPressed(evt);
            }
        });
        jPanel2.add(nombres);
        nombres.setBounds(120, 18, 240, 18);

        direccion.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.direccion}"), direccion, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionKeyPressed(evt);
            }
        });
        jPanel2.add(direccion);
        direccion.setBounds(120, 52, 240, 18);

        jLabel12.setForeground(new java.awt.Color(0, 0, 153));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Nombres:");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(10, 18, 100, 18);

        codigo.setEditable(false);
        codigo.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.ruc}"), codigo, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoKeyPressed(evt);
            }
        });
        jPanel2.add(codigo);
        codigo.setBounds(120, 0, 130, 18);

        razonsocial.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.razon}"), razonsocial, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        razonsocial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                razonsocialKeyPressed(evt);
            }
        });
        jPanel2.add(razonsocial);
        razonsocial.setBounds(120, 36, 240, 18);

        jLabel5.setForeground(new java.awt.Color(0, 0, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Razón Social:");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(0, 36, 110, 18);

        jLabel6.setForeground(new java.awt.Color(0, 0, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Teléfonos:");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(10, 70, 100, 18);

        telefono.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.telefonos}"), telefono, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefonoKeyPressed(evt);
            }
        });
        jPanel2.add(telefono);
        telefono.setBounds(120, 70, 100, 18);

        parqueaderos.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.parqueaderos}"), parqueaderos, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        parqueaderos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                parqueaderosKeyPressed(evt);
            }
        });
        jPanel2.add(parqueaderos);
        parqueaderos.setBounds(120, 87, 40, 18);

        cmbFactura.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.impfactura}"), cmbFactura, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFacturaKeyPressed(evt);
            }
        });
        jPanel2.add(cmbFactura);
        cmbFactura.setBounds(120, 146, 240, 18);

        jLabel9.setForeground(new java.awt.Color(0, 0, 153));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("No. Parqueaderos:");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(-10, 87, 120, 18);

        jLabel13.setForeground(new java.awt.Color(0, 0, 153));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Imp. Ticket:");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(0, 128, 110, 18);

        cmbTicket.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.impticket}"), cmbTicket, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbTicket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbTicketKeyPressed(evt);
            }
        });
        jPanel2.add(cmbTicket);
        cmbTicket.setBounds(120, 128, 240, 18);

        jLabel18.setForeground(new java.awt.Color(0, 0, 153));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Imp. Factura:");
        jPanel2.add(jLabel18);
        jLabel18.setBounds(0, 146, 110, 18);

        cmbImpresora.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.impresora}"), cmbImpresora, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbImpresora.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbImpresoraKeyPressed(evt);
            }
        });
        jPanel2.add(cmbImpresora);
        cmbImpresora.setBounds(120, 164, 240, 18);

        jLabel20.setForeground(new java.awt.Color(0, 0, 153));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Impresora Botón(1):");
        jPanel2.add(jLabel20);
        jLabel20.setBounds(0, 164, 110, 18);

        iva.setEditable(false);
        iva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.iva}"), iva, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        iva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ivaKeyPressed(evt);
            }
        });
        jPanel2.add(iva);
        iva.setBounds(320, 87, 40, 18);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Gracia por Hora: ");
        jPanel2.add(jLabel16);
        jLabel16.setBounds(0, 200, 110, 18);

        graciaentrada.setEditable(false);
        graciaentrada.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        graciaentrada.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.gracia}"), graciaentrada, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        graciaentrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graciaentradaActionPerformed(evt);
            }
        });
        jPanel2.add(graciaentrada);
        graciaentrada.setBounds(120, 200, 60, 18);

        graciasalida.setEditable(false);
        graciasalida.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        graciasalida.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.salida}"), graciasalida, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        graciasalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graciasalidaActionPerformed(evt);
            }
        });
        jPanel2.add(graciasalida);
        graciasalida.setBounds(300, 200, 60, 18);

        jLabel28.setText("Tiempo Gracia Salida: ");
        jPanel2.add(jLabel28);
        jLabel28.setBounds(190, 200, 110, 18);

        chkWebcam.setText("Trabaja con WebCam");
        chkWebcam.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.webcam}"), chkWebcam, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        chkWebcam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkWebcamActionPerformed(evt);
            }
        });
        jPanel2.add(chkWebcam);
        chkWebcam.setBounds(220, 260, 140, 18);

        seabrefactura.setText("Abrir barrera al cobrar");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.seabrefac}"), seabrefactura, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        seabrefactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seabrefacturaActionPerformed(evt);
            }
        });
        jPanel2.add(seabrefactura);
        seabrefactura.setBounds(220, 220, 140, 18);

        seabreticket.setText("Abrir barrera al imprimir Ticket");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.seabretic}"), seabreticket, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        jPanel2.add(seabreticket);
        seabreticket.setBounds(10, 220, 170, 18);

        multa.setEditable(false);
        multa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        multa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        multa.setText("5");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.multa}"), multa, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jPanel2.add(multa);
        multa.setBounds(320, 70, 40, 18);

        jLabel29.setText("Barre.que Fotografía: ");
        jPanel2.add(jLabel29);
        jLabel29.setBounds(10, 300, 110, 20);

        cmbImpresora1.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.impresora2}"), cmbImpresora1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbImpresora1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbImpresora1KeyPressed(evt);
            }
        });
        jPanel2.add(cmbImpresora1);
        cmbImpresora1.setBounds(120, 182, 240, 18);

        jLabel37.setForeground(new java.awt.Color(0, 0, 153));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel37.setText("Impresora Botón(2):");
        jPanel2.add(jLabel37);
        jLabel37.setBounds(0, 183, 110, 18);

        chkIpcam.setText("Trabaja con Cámara IP        ó");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.ipcam}"), chkIpcam, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        chkIpcam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIpcamActionPerformed(evt);
            }
        });
        jPanel2.add(chkIpcam);
        chkIpcam.setBounds(10, 260, 170, 18);

        jLabel38.setText("Multa por pérdida: ");
        jPanel2.add(jLabel38);
        jLabel38.setBounds(230, 70, 100, 18);

        url.setEditable(false);
        url.setText("http://");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.url}"), url, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jPanel2.add(url);
        url.setBounds(120, 280, 240, 18);

        cmbPuertaFac.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        cmbPuertaFac.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puertafac}"), cmbPuertaFac, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPuertaFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuertaFacKeyPressed(evt);
            }
        });
        jPanel2.add(cmbPuertaFac);
        cmbPuertaFac.setBounds(360, 220, 37, 20);

        cmbPuertaTicket.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        cmbPuertaTicket.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puertatic}"), cmbPuertaTicket, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPuertaTicket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuertaTicketKeyPressed(evt);
            }
        });
        jPanel2.add(cmbPuertaTicket);
        cmbPuertaTicket.setBounds(180, 220, 37, 20);

        jLabel50.setForeground(new java.awt.Color(0, 0, 153));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel50.setText("IVA %");
        jPanel2.add(jLabel50);
        jLabel50.setBounds(280, 87, 40, 18);

        chkBloquear.setText("Bloquear Pulsador al llenarse");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.bloquear}"), chkBloquear, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        chkBloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBloquearActionPerformed(evt);
            }
        });
        jPanel2.add(chkBloquear);
        chkBloquear.setBounds(20, 105, 180, 18);

        chkBloquearSalida.setText("Bloquear Salida si no ha Ingresado");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.bloquearsalida}"), chkBloquearSalida, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        jPanel2.add(chkBloquearSalida);
        chkBloquearSalida.setBounds(200, 105, 210, 18);

        barreras.setToolTipText("");
        jPanel2.add(barreras);
        barreras.setBounds(120, 300, 90, 20);

        jLabel51.setText("URL IP CAM:");
        jPanel2.add(jLabel51);
        jLabel51.setBounds(20, 280, 80, 20);

        jLabel7.setText("Ej.: 1;2;3;4");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(220, 300, 70, 14);

        jLabel53.setText("Tiempo retardo al cobrar");
        jPanel2.add(jLabel53);
        jLabel53.setBounds(230, 240, 130, 14);

        jLabel55.setText("Tiempo retardo al abrir barrera");
        jPanel2.add(jLabel55);
        jLabel55.setBounds(20, 240, 160, 14);

        retardoEntrada.setEditable(false);
        retardoEntrada.setText("1");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.retardoEntrada}"), retardoEntrada, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jPanel2.add(retardoEntrada);
        retardoEntrada.setBounds(180, 240, 20, 18);

        retardoSalida.setEditable(false);
        retardoSalida.setText("1");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.retardoSalida}"), retardoSalida, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jPanel2.add(retardoSalida);
        retardoSalida.setBounds(360, 240, 20, 18);

        jLabel56.setText("s");
        jPanel2.add(jLabel56);
        jLabel56.setBounds(200, 243, 5, 14);

        jLabel57.setText("s");
        jPanel2.add(jLabel57);
        jLabel57.setBounds(380, 243, 10, 14);

        jTabbedPane1.addTab("DATOS DE LA EMPRESA", new javax.swing.ImageIcon(getClass().getResource("/images/empresa.png")), jPanel2); // NOI18N

        jPanel5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel5KeyPressed(evt);
            }
        });
        jPanel5.setLayout(null);

        cmbEntrada1.setEditable(true);
        cmbEntrada1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cmbEntrada1.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerto1}"), cmbEntrada1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbEntrada1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbEntrada1KeyPressed(evt);
            }
        });
        jPanel5.add(cmbEntrada1);
        cmbEntrada1.setBounds(120, 230, 60, 19);

        cmbPuerta1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        cmbPuerta1.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerta1}"), cmbPuerta1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPuerta1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuerta1KeyPressed(evt);
            }
        });
        jPanel5.add(cmbPuerta1);
        cmbPuerta1.setBounds(180, 230, 37, 20);

        cmbPuerta2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        cmbPuerta2.setSelectedIndex(1);
        cmbPuerta2.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerta2}"), cmbPuerta2, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPuerta2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuerta2KeyPressed(evt);
            }
        });
        jPanel5.add(cmbPuerta2);
        cmbPuerta2.setBounds(180, 250, 37, 20);

        cmbEntrada2.setEditable(true);
        cmbEntrada2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cmbEntrada2.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerto3}"), cmbEntrada2, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbEntrada2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbEntrada2KeyPressed(evt);
            }
        });
        jPanel5.add(cmbEntrada2);
        cmbEntrada2.setBounds(120, 250, 60, 19);

        cmbPuerta3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        cmbPuerta3.setSelectedIndex(2);
        cmbPuerta3.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerta3}"), cmbPuerta3, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPuerta3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuerta3KeyPressed(evt);
            }
        });
        jPanel5.add(cmbPuerta3);
        cmbPuerta3.setBounds(180, 270, 37, 20);

        cmbEntrada3.setEditable(true);
        cmbEntrada3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cmbEntrada3.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerto2}"), cmbEntrada3, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbEntrada3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbEntrada3KeyPressed(evt);
            }
        });
        jPanel5.add(cmbEntrada3);
        cmbEntrada3.setBounds(120, 270, 60, 19);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Entrada 1: ");
        jPanel5.add(jLabel14);
        jLabel14.setBounds(60, 230, 60, 20);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Entrada 4: ");
        jPanel5.add(jLabel15);
        jLabel15.setBounds(60, 290, 60, 20);

        cmbEntrada4.setEditable(true);
        cmbEntrada4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cmbEntrada4.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerto4}"), cmbEntrada4, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbEntrada4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbEntrada4KeyPressed(evt);
            }
        });
        jPanel5.add(cmbEntrada4);
        cmbEntrada4.setBounds(120, 290, 60, 19);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Entrada 3: ");
        jPanel5.add(jLabel19);
        jLabel19.setBounds(60, 270, 60, 20);

        cmbPuerta4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        cmbPuerta4.setSelectedIndex(3);
        cmbPuerta4.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerta4}"), cmbPuerta4, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPuerta4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuerta4KeyPressed(evt);
            }
        });
        jPanel5.add(cmbPuerta4);
        cmbPuerta4.setBounds(180, 290, 37, 20);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("Puerta");
        jPanel5.add(jLabel21);
        jLabel21.setBounds(180, 210, 50, 18);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Entrada 2: ");
        jPanel5.add(jLabel3);
        jLabel3.setBounds(60, 250, 60, 20);

        chkActivo1.setText("Activo");
        chkActivo1.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.activa1}"), chkActivo1, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        chkActivo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkActivo1ActionPerformed(evt);
            }
        });
        chkActivo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chkActivo1KeyPressed(evt);
            }
        });
        jPanel5.add(chkActivo1);
        chkActivo1.setBounds(340, 230, 60, 23);

        chkActivo2.setText("Activo");
        chkActivo2.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.activa2}"), chkActivo2, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        chkActivo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkActivo2ActionPerformed(evt);
            }
        });
        chkActivo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chkActivo2KeyPressed(evt);
            }
        });
        jPanel5.add(chkActivo2);
        chkActivo2.setBounds(340, 250, 60, 23);

        chkActivo3.setText("Activo");
        chkActivo3.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.activa3}"), chkActivo3, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        chkActivo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkActivo3ActionPerformed(evt);
            }
        });
        chkActivo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chkActivo3KeyPressed(evt);
            }
        });
        jPanel5.add(chkActivo3);
        chkActivo3.setBounds(340, 270, 60, 23);

        chkActivo4.setText("Activo");
        chkActivo4.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.activa4}"), chkActivo4, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        chkActivo4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkActivo4ActionPerformed(evt);
            }
        });
        chkActivo4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chkActivo4KeyPressed(evt);
            }
        });
        jPanel5.add(chkActivo4);
        chkActivo4.setBounds(340, 290, 60, 23);

        cmbSalida1.setEditable(true);
        cmbSalida1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cmbSalida1.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.salida1}"), cmbSalida1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbSalida1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSalida1KeyPressed(evt);
            }
        });
        jPanel5.add(cmbSalida1);
        cmbSalida1.setBounds(280, 230, 60, 19);

        cmbSalida3.setEditable(true);
        cmbSalida3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cmbSalida3.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.salida3}"), cmbSalida3, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbSalida3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSalida3KeyPressed(evt);
            }
        });
        jPanel5.add(cmbSalida3);
        cmbSalida3.setBounds(280, 270, 60, 19);

        cmbSalida2.setEditable(true);
        cmbSalida2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cmbSalida2.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.salida2}"), cmbSalida2, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbSalida2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSalida2KeyPressed(evt);
            }
        });
        jPanel5.add(cmbSalida2);
        cmbSalida2.setBounds(280, 250, 60, 19);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Salida 1: ");
        jPanel5.add(jLabel23);
        jLabel23.setBounds(220, 230, 60, 20);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Salida 4: ");
        jPanel5.add(jLabel24);
        jLabel24.setBounds(220, 290, 60, 20);

        cmbSalida4.setEditable(true);
        cmbSalida4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cmbSalida4.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.salida4}"), cmbSalida4, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbSalida4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSalida4KeyPressed(evt);
            }
        });
        jPanel5.add(cmbSalida4);
        cmbSalida4.setBounds(280, 290, 60, 19);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Salida 3: ");
        jPanel5.add(jLabel25);
        jLabel25.setBounds(220, 270, 60, 20);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Salida 2: ");
        jPanel5.add(jLabel26);
        jLabel26.setBounds(220, 250, 60, 20);

        cmbPuertoPrincipal.setEditable(true);
        cmbPuertoPrincipal.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerto}"), cmbPuertoPrincipal, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPuertoPrincipal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuertoPrincipalKeyPressed(evt);
            }
        });
        jPanel5.add(cmbPuertoPrincipal);
        cmbPuertoPrincipal.setBounds(330, 10, 60, 20);

        jLabel22.setForeground(new java.awt.Color(0, 0, 153));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Puerto Tarjeta Interfaz  PC-BARRERA:");
        jPanel5.add(jLabel22);
        jLabel22.setBounds(80, 10, 240, 14);

        jLabel27.setForeground(new java.awt.Color(0, 0, 153));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Puerto Pantalla LEDS (parqueaderos disponibles):");
        jPanel5.add(jLabel27);
        jLabel27.setBounds(80, 30, 240, 14);

        cmbPuertoLed.setEditable(true);
        cmbPuertoLed.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.led}"), cmbPuertoLed, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPuertoLed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuertoLedKeyPressed(evt);
            }
        });
        jPanel5.add(cmbPuertoLed);
        cmbPuertoLed.setBounds(330, 30, 60, 20);

        cmbPortBarras.setEditable(true);
        cmbPortBarras.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.barras}"), cmbPortBarras, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPortBarras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPortBarrasKeyPressed(evt);
            }
        });
        jPanel5.add(cmbPortBarras);
        cmbPortBarras.setBounds(260, 130, 50, 20);

        jLabel30.setForeground(new java.awt.Color(0, 0, 153));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("1.Botón pulsador abre (ing. de vehículo):");
        jPanel5.add(jLabel30);
        jLabel30.setBounds(40, 70, 230, 14);

        cmbPuertaSale.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        cmbPuertaSale.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.sale}"), cmbPuertaSale, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPuertaSale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuertaSaleKeyPressed(evt);
            }
        });
        jPanel5.add(cmbPuertaSale);
        cmbPuertaSale.setBounds(360, 130, 37, 20);

        cmbPuertaSale2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        cmbPuertaSale2.setSelectedIndex(1);
        cmbPuertaSale2.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.sale2}"), cmbPuertaSale2, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPuertaSale2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuertaSale2KeyPressed(evt);
            }
        });
        jPanel5.add(cmbPuertaSale2);
        cmbPuertaSale2.setBounds(360, 170, 37, 20);

        jLabel31.setForeground(new java.awt.Color(0, 0, 153));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel31.setText("Sale por:");
        jPanel5.add(jLabel31);
        jLabel31.setBounds(310, 130, 50, 14);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel33.setText("Puerta");
        jPanel5.add(jLabel33);
        jLabel33.setBounds(360, 110, 40, 16);

        jLabel34.setForeground(new java.awt.Color(0, 0, 153));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel34.setText("2.Botón pulsador abre (ing. de vehículo):");
        jPanel5.add(jLabel34);
        jLabel34.setBounds(40, 90, 230, 14);

        cmbPortBarras2.setEditable(true);
        cmbPortBarras2.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.barras2}"), cmbPortBarras2, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPortBarras2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPortBarras2KeyPressed(evt);
            }
        });
        jPanel5.add(cmbPortBarras2);
        cmbPortBarras2.setBounds(260, 170, 50, 20);

        jLabel36.setForeground(new java.awt.Color(0, 0, 153));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel36.setText("Sale por:");
        jPanel5.add(jLabel36);
        jLabel36.setBounds(310, 170, 50, 20);

        jLabel39.setForeground(new java.awt.Color(0, 0, 153));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel39.setText("IP/Puerto:");
        jPanel5.add(jLabel39);
        jLabel39.setBounds(70, 150, 50, 14);

        jLabel40.setForeground(new java.awt.Color(0, 0, 153));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel40.setText("2.Puerto Codigo Barras (sal.de vehículo):");
        jPanel5.add(jLabel40);
        jLabel40.setBounds(50, 170, 210, 14);

        cmbPuertaEntra1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        cmbPuertaEntra1.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.entra1}"), cmbPuertaEntra1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPuertaEntra1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuertaEntra1KeyPressed(evt);
            }
        });
        jPanel5.add(cmbPuertaEntra1);
        cmbPuertaEntra1.setBounds(280, 70, 37, 20);

        cmbPuertaEntra2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        cmbPuertaEntra2.setSelectedIndex(1);
        cmbPuertaEntra2.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.entra2}"), cmbPuertaEntra2, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPuertaEntra2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuertaEntra2KeyPressed(evt);
            }
        });
        jPanel5.add(cmbPuertaEntra2);
        cmbPuertaEntra2.setBounds(280, 90, 37, 20);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel41.setText("Puerta");
        jPanel5.add(jLabel41);
        jLabel41.setBounds(280, 55, 50, 20);

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/push.png"))); // NOI18N
        jPanel5.add(jLabel42);
        jLabel42.setBounds(20, 70, 50, 44);

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/barcode.png"))); // NOI18N
        jPanel5.add(jLabel43);
        jLabel43.setBounds(18, 150, 37, 30);

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lectora.png"))); // NOI18N
        jPanel5.add(jLabel44);
        jLabel44.setBounds(20, 240, 38, 60);

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/interfaz.png"))); // NOI18N
        jPanel5.add(jLabel45);
        jLabel45.setBounds(20, 10, 40, 40);

        jLabel46.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(jLabel46);
        jLabel46.setBounds(10, 212, 400, 108);

        jLabel47.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(jLabel47);
        jLabel47.setBounds(10, 5, 400, 50);

        ipBarras1.setText(" ");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.ipBarras1}"), ipBarras1, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jPanel5.add(ipBarras1);
        ipBarras1.setBounds(120, 150, 140, 20);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puertoBarras1}"), puertoBarras1, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jPanel5.add(puertoBarras1);
        puertoBarras1.setBounds(260, 150, 50, 20);

        jLabel48.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(jLabel48);
        jLabel48.setBounds(10, 57, 400, 60);

        jLabel54.setForeground(new java.awt.Color(0, 0, 153));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel54.setText("1.Puerto Codigo Barras (sal.de vehículo):");
        jPanel5.add(jLabel54);
        jLabel54.setBounds(50, 130, 210, 14);

        ipBarras2.setText(" ");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.ipBarras2}"), ipBarras2, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jPanel5.add(ipBarras2);
        ipBarras2.setBounds(120, 188, 140, 20);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puertoBarras2}"), puertoBarras2, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jPanel5.add(puertoBarras2);
        puertoBarras2.setBounds(260, 190, 50, 20);

        jLabel52.setForeground(new java.awt.Color(0, 0, 153));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel52.setText("IP/Puerto:");
        jPanel5.add(jLabel52);
        jLabel52.setBounds(70, 190, 50, 14);

        jLabel49.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(jLabel49);
        jLabel49.setBounds(10, 120, 400, 90);

        jTabbedPane1.addTab("PUERTAS Y LECTORAS", new javax.swing.ImageIcon(getClass().getResource("/images/admin.gif")), jPanel5); // NOI18N

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 40, 430, 360);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed

        // TODO add your handling code here:
        if (principal.permisos.getAgregar()) {
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
                if (codigo.getText().isEmpty() || nombres.getText().trim().isEmpty() || razonsocial.getText().trim().isEmpty() || razonsocial.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Registre los campos requeridos ...!");
                } else {
                    empresaObj.setImpticket((String) cmbTicket.getSelectedItem());
                    empresaObj.setImpfactura((String) cmbFactura.getSelectedItem());
                    empresaObj.setImpresora((String)cmbImpresora.getSelectedItem());
                    empresaObj.setImpresora2((String)cmbImpresora1.getSelectedItem());

                    empresaObj.setPuerto1((String)cmbEntrada1.getSelectedItem());
                    empresaObj.setPuerto2((String)cmbEntrada2.getSelectedItem());
                    empresaObj.setPuerto3((String)cmbEntrada3.getSelectedItem());
                    empresaObj.setPuerto4((String)cmbEntrada4.getSelectedItem());

                    empresaObj.setSalida1((String)cmbSalida1.getSelectedItem());
                    empresaObj.setSalida2((String)cmbSalida2.getSelectedItem());
                    empresaObj.setSalida3((String)cmbSalida3.getSelectedItem());
                    empresaObj.setSalida4((String)cmbSalida4.getSelectedItem());

                    empresaObj.setPuerta1((String)cmbPuerta1.getSelectedItem());
                    empresaObj.setPuerta2((String)cmbPuerta2.getSelectedItem());
                    empresaObj.setPuerta3((String)cmbPuerta3.getSelectedItem());
                    empresaObj.setPuerta4((String)cmbPuerta4.getSelectedItem());

                    empresaObj.setPuerto((String)cmbPuertoPrincipal.getSelectedItem());
                    empresaObj.setLed((String)cmbPuertoLed.getSelectedItem());
                    empresaObj.setBarras((String)cmbPortBarras.getSelectedItem());
                    empresaObj.setBarras2((String)cmbPortBarras2.getSelectedItem());
                    empresaObj.setSale((String)cmbPuertaSale.getSelectedItem());
                    empresaObj.setSale2((String)cmbPuertaSale2.getSelectedItem());
                    empresaObj.setEntra1((String)cmbPuertaEntra1.getSelectedItem());
                    empresaObj.setEntra2((String)cmbPuertaEntra2.getSelectedItem());
                    empresaObj.setWebcam(chkWebcam.isSelected());
                    empresaObj.setUrl(url.getText());
                    empresaObj.setBarreras(barreras.getText());
                    empresaObj.setIpcam(chkIpcam.isSelected());
                    empresaObj.setBloquear(chkBloquear.isSelected());
                    empresaObj.setBloquearsalida(chkBloquearSalida.isSelected());
                    empresaObj.setSeabretic(seabreticket.isSelected());
                    empresaObj.setSeabrefac(seabrefactura.isSelected());
                    empresaObj.setPuertatic((String)cmbPuertaTicket.getSelectedItem());
                    empresaObj.setPuertafac((String)cmbPuertaFac.getSelectedItem());
                    empresaObj.setIpBarras1(ipBarras1.getText());
                    empresaObj.setIpBarras2(ipBarras2.getText());
                    empresaObj.setPuertoBarras1(puertoBarras1.getText());
                    empresaObj.setPuertoBarras2(puertoBarras2.getText());
                    empresaObj.setRetardoEntrada(retardoEntrada.getText());
                    empresaObj.setRetardoSalida(retardoSalida.getText());
                    try {
                        empresaObj.setMulta(new Double(multa.getText()));
                    } catch (Exception e) {
                        empresaObj.setMulta(0.0);
                        multa.setText("0");
                    }
                    

                    XMLEmpresa xm = new XMLEmpresa();
                    xm.inicio();
                    xm.escribir(empresaObj);
                   
                    
                    
                    if (modificar) {
                        try {
                            adm.actualizar(empresaObj);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en actualizar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                    } else {
                        try {
                            adm.guardar(empresaObj);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en guardar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                    }
                    principal.empresaObj = empresaObj;
                    this.btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
                    this.btnAgregar.setLabel("Nuevo");
                    this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png")));
                    this.btnModificar.setLabel("Modificar");
                    btnAgregar.setMnemonic('N');
                    btnModificar.setMnemonic('M');
                    grabar = false;
                    modificar = false;
                    habilitar(false);
                    btnBuscar.setEnabled(false);
                    btnAgregar.setEnabled(false);
                }

            }
        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }


    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:

        if (grabar == false) {
            if (principal.permisos.getModificar()) {
                if (codigo.getText().trim().isEmpty()) {
                    return;
                }
                this.codigo.requestFocusInWindow();
                this.btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
                this.btnAgregar.setLabel("Guardar");
                this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
                this.btnModificar.setLabel("Cancelar");
                btnAgregar.setMnemonic('G');
                btnAgregar.setEnabled(true);
                btnModificar.setMnemonic('C');
                modificar = true;
                grabar = true;
                habilitar(true);
                btnBuscar.setEnabled(false);

                    cmbEntrada1.setEnabled(chkActivo1.isSelected()); cmbPuerta1.setEnabled(chkActivo1.isSelected());cmbSalida1.setEnabled(chkActivo1.isSelected());
                    cmbEntrada2.setEnabled(chkActivo2.isSelected()); cmbPuerta2.setEnabled(chkActivo2.isSelected());cmbSalida2.setEnabled(chkActivo2.isSelected());
                    cmbEntrada3.setEnabled(chkActivo3.isSelected()); cmbPuerta3.setEnabled(chkActivo3.isSelected());cmbSalida3.setEnabled(chkActivo3.isSelected());
                    cmbEntrada4.setEnabled(chkActivo4.isSelected()); cmbPuerta4.setEnabled(chkActivo4.isSelected());cmbSalida4.setEnabled(chkActivo4.isSelected());
                 

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
            btnBuscar.setEnabled(false);
            btnAgregar.setEnabled(false);

        }


    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (principal.permisos.getEliminar()) {        // TODO add your handling code here:
            try {
                adm.eliminarObjeto(Empresa.class, empresaObj.getRuc());
                this.limpiar();
            } catch (Exception ex) {
                Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }
}//GEN-LAST:event_btnEliminarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        principal.contenedor.requestFocus();
        this.setVisible(false);
        principal = null;
        empresaObj = null;
        System.gc();

}//GEN-LAST:event_btnSalirActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        formaEmpresa.setModal(true);
        formaEmpresa.setSize(533, 300);
        formaEmpresa.setLocation(250, 70);
        formaEmpresa.show();

        this.codigoBuscar.requestFocusInWindow();
        DefaultTableModel dtm = (DefaultTableModel) busquedaTabla.getModel();
        dtm.getDataVector().removeAllElements();
        busquedaTabla.setModel(dtm);
        codigoBuscar.setText("");
//                    nombresBuscar.setText("");




}//GEN-LAST:event_btnBuscarActionPerformed

    private void codigoBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoBuscarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {

            Thread cargar = new Thread() {

                public void run() {
                    principal.procesando.setVisible(true);
                    try {
                        List<Empresa> usuarios = adm.query("Select o from Empresa as o where o.nombre like '" + codigoBuscar.getText().trim() + "%' ");
                        Object[] obj = new Object[4];
                        DefaultTableModel dtm = (DefaultTableModel) busquedaTabla.getModel();
                        dtm.getDataVector().removeAllElements();
                        for (Iterator<Empresa> it = usuarios.iterator(); it.hasNext();) {
                            Empresa glbusuario = it.next();
                            obj[1] = glbusuario.getRazon();
                            obj[0] = glbusuario.getRuc();
                            dtm.addRow(obj);
                        }
                        busquedaTabla.setModel(dtm);
                        if (busquedaTabla.getRowCount() > 0) {
                            busquedaTabla.requestFocusInWindow();
                        } else {
                            codigoBuscar.requestFocusInWindow();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    principal.procesando.setVisible(false);
                }
            };
            cargar.start();

        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            formaEmpresa.dispose();
        }

}//GEN-LAST:event_codigoBuscarKeyPressed

    private void busquedaTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_busquedaTablaMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            try {
                int fila = busquedaTabla.getSelectedRow();
                this.empresaObj = (Empresa) adm.buscarClave((String) busquedaTabla.getValueAt(fila, 0), Empresa.class);
                bindingGroup.unbind();
                bindingGroup.bind();

                formaEmpresa.dispose();
            } catch (Exception ex) {
                Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        JOptionPane.showMessageDialog(this, empresaObj);
    }//GEN-LAST:event_busquedaTablaMouseClicked

    private void busquedaTablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaTablaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            try {
                int fila = busquedaTabla.getSelectedRow();
                this.empresaObj = (Empresa) adm.buscarClave((String) busquedaTabla.getValueAt(fila, 0), Empresa.class);
                bindingGroup.unbind();
                bindingGroup.bind();
                formaEmpresa.dispose();

            } catch (Exception ex) {
                Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            formaEmpresa.dispose();
        }


    }//GEN-LAST:event_busquedaTablaKeyPressed

    private void codigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            codigo.nextFocus();
        }else{
            principal.tecla(evt.getKeyCode());
        }
    }//GEN-LAST:event_codigoKeyPressed

    private void nombresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombresKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            nombres.nextFocus();
        }else{
            principal.tecla(evt.getKeyCode());
        }
    }//GEN-LAST:event_nombresKeyPressed

    private void direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            direccion.nextFocus();
        }else{
            principal.tecla(evt.getKeyCode());
        }
    }//GEN-LAST:event_direccionKeyPressed

    private void razonsocialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_razonsocialKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            razonsocial.nextFocus();
        }else{
            principal.tecla(evt.getKeyCode());
        }
    }//GEN-LAST:event_razonsocialKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.setVisible(false);
        }
    }//GEN-LAST:event_formKeyReleased

    private void telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonoKeyPressed
        // TODO add your handling code here:else{
            principal.tecla(evt.getKeyCode());
        
    }//GEN-LAST:event_telefonoKeyPressed

    private void parqueaderosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_parqueaderosKeyPressed
        // TODO add your handling code here:

            principal.tecla(evt.getKeyCode());
        
    }//GEN-LAST:event_parqueaderosKeyPressed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void chkActivo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkActivo1ActionPerformed
        // TODO add your handling code here:

        cmbEntrada1.setEnabled(chkActivo1.isSelected());
        cmbPuerta1.setEnabled(chkActivo1.isSelected());
        cmbSalida1.setEnabled(chkActivo1.isSelected());
    }//GEN-LAST:event_chkActivo1ActionPerformed

    private void chkActivo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkActivo2ActionPerformed
        // TODO add your handling code here:
        cmbEntrada2.setEnabled(chkActivo2.isSelected());
        cmbPuerta2.setEnabled(chkActivo2.isSelected());
        cmbSalida2.setEnabled(chkActivo2.isSelected());
    }//GEN-LAST:event_chkActivo2ActionPerformed

    private void chkActivo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkActivo3ActionPerformed
        // TODO add your handling code here:
        cmbEntrada3.setEnabled(chkActivo3.isSelected());
        cmbPuerta3.setEnabled(chkActivo3.isSelected());
        cmbSalida3.setEnabled(chkActivo3.isSelected());
    }//GEN-LAST:event_chkActivo3ActionPerformed

    private void chkActivo4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkActivo4ActionPerformed
        // TODO add your handling code here:
        cmbEntrada4.setEnabled(chkActivo4.isSelected());
        cmbPuerta4.setEnabled(chkActivo4.isSelected());
        cmbSalida4.setEnabled(chkActivo4.isSelected());
    }//GEN-LAST:event_chkActivo4ActionPerformed

    private void ivaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ivaKeyPressed
        // TODO add your handling code here:

            principal.tecla(evt.getKeyCode());
     
    }//GEN-LAST:event_ivaKeyPressed

    private void cmbTicketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbTicketKeyPressed
        // TODO add your handling code here:

            principal.tecla(evt.getKeyCode());

    }//GEN-LAST:event_cmbTicketKeyPressed

    private void cmbFacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbFacturaKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbFacturaKeyPressed

    private void cmbImpresoraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbImpresoraKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbImpresoraKeyPressed

    private void cmbPuertoPrincipalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuertoPrincipalKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbPuertoPrincipalKeyPressed

    private void btnAgregarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregarKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnAgregarKeyPressed

    private void btnBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBuscarKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnBuscarKeyPressed

    private void btnModificarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnModificarKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnModificarKeyPressed

    private void btnEliminarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminarKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnEliminarKeyPressed

    private void btnSalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalirKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnSalirKeyPressed

    private void cmbEntrada1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbEntrada1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbEntrada1KeyPressed

    private void cmbPuerta1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuerta1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbPuerta1KeyPressed

    private void cmbSalida1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSalida1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbSalida1KeyPressed

    private void chkActivo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chkActivo1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_chkActivo1KeyPressed

    private void cmbEntrada2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbEntrada2KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbEntrada2KeyPressed

    private void cmbPuerta2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuerta2KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbPuerta2KeyPressed

    private void cmbSalida2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSalida2KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbSalida2KeyPressed

    private void chkActivo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chkActivo2KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_chkActivo2KeyPressed

    private void cmbEntrada3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbEntrada3KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbEntrada3KeyPressed

    private void cmbPuerta3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuerta3KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbPuerta3KeyPressed

    private void cmbSalida3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSalida3KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbSalida3KeyPressed

    private void chkActivo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chkActivo3KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_chkActivo3KeyPressed

    private void cmbEntrada4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbEntrada4KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbEntrada4KeyPressed

    private void cmbPuerta4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuerta4KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbPuerta4KeyPressed

    private void cmbSalida4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSalida4KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbSalida4KeyPressed

    private void chkActivo4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chkActivo4KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_chkActivo4KeyPressed

    private void jPanel5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel5KeyPressed
        // TODO add your handling code here:
         principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_jPanel5KeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
         principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_formKeyPressed

    private void graciaentradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graciaentradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_graciaentradaActionPerformed

    private void cmbPuertoLedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuertoLedKeyPressed
        // TODO add your handling code here:
           principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbPuertoLedKeyPressed

    private void graciasalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graciasalidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_graciasalidaActionPerformed

    private void cmbPortBarrasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPortBarrasKeyPressed
        // TODO add your handling code here:
          principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbPortBarrasKeyPressed

    private void cmbPuertaSaleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuertaSaleKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbPuertaSaleKeyPressed

    private void cmbPuertaSale2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuertaSale2KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbPuertaSale2KeyPressed

    private void cmbPuertoBarras1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuertoBarras1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbPuertoBarras1KeyPressed

    private void cmbPortBarras2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPortBarras2KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbPortBarras2KeyPressed

    private void seabrefacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seabrefacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seabrefacturaActionPerformed

    private void cmbImpresora1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbImpresora1KeyPressed
        // TODO add your handling code here:
         principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbImpresora1KeyPressed

    private void chkIpcamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIpcamActionPerformed
        // TODO add your handling code here:
        if(chkIpcam.isSelected())
            chkWebcam.setSelected(false);
        
    }//GEN-LAST:event_chkIpcamActionPerformed

    private void chkWebcamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkWebcamActionPerformed
        // TODO add your handling code here:
         if(chkWebcam.isSelected())
            chkIpcam.setSelected(false);
    }//GEN-LAST:event_chkWebcamActionPerformed

    private void cmbPuertaEntra1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuertaEntra1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPuertaEntra1KeyPressed

    private void cmbPuertaEntra2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuertaEntra2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPuertaEntra2KeyPressed

    private void cmbPuertaFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuertaFacKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPuertaFacKeyPressed

    private void cmbPuertaTicketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuertaTicketKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPuertaTicketKeyPressed

    private void chkBloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBloquearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkBloquearActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField barreras;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JTable busquedaTabla;
    private javax.swing.JCheckBox chkActivo1;
    private javax.swing.JCheckBox chkActivo2;
    private javax.swing.JCheckBox chkActivo3;
    private javax.swing.JCheckBox chkActivo4;
    private javax.swing.JCheckBox chkBloquear;
    private javax.swing.JCheckBox chkBloquearSalida;
    private javax.swing.JCheckBox chkIpcam;
    private javax.swing.JCheckBox chkWebcam;
    private javax.swing.JComboBox cmbEntrada1;
    private javax.swing.JComboBox cmbEntrada2;
    private javax.swing.JComboBox cmbEntrada3;
    private javax.swing.JComboBox cmbEntrada4;
    private javax.swing.JComboBox cmbFactura;
    private javax.swing.JComboBox cmbImpresora;
    private javax.swing.JComboBox cmbImpresora1;
    private javax.swing.JComboBox cmbPortBarras;
    private javax.swing.JComboBox cmbPortBarras2;
    private javax.swing.JComboBox cmbPuerta1;
    private javax.swing.JComboBox cmbPuerta2;
    private javax.swing.JComboBox cmbPuerta3;
    private javax.swing.JComboBox cmbPuerta4;
    private javax.swing.JComboBox cmbPuertaEntra1;
    private javax.swing.JComboBox cmbPuertaEntra2;
    private javax.swing.JComboBox cmbPuertaFac;
    private javax.swing.JComboBox cmbPuertaSale;
    private javax.swing.JComboBox cmbPuertaSale2;
    private javax.swing.JComboBox cmbPuertaTicket;
    private javax.swing.JComboBox cmbPuertoBarras1;
    private javax.swing.JComboBox cmbPuertoLed;
    private javax.swing.JComboBox cmbPuertoPrincipal;
    private javax.swing.JComboBox cmbSalida1;
    private javax.swing.JComboBox cmbSalida2;
    private javax.swing.JComboBox cmbSalida3;
    private javax.swing.JComboBox cmbSalida4;
    private javax.swing.JComboBox cmbTicket;
    private javax.swing.JFormattedTextField codigo;
    private javax.swing.JFormattedTextField codigoBuscar;
    private javax.swing.JFormattedTextField direccion;
    private javax.swing.JDialog formaEmpresa;
    private javax.swing.JFormattedTextField graciaentrada;
    private javax.swing.JFormattedTextField graciasalida;
    private javax.swing.JFormattedTextField ipBarras1;
    private javax.swing.JFormattedTextField ipBarras2;
    private javax.swing.JTextField iva;
    private javax.swing.JCheckBox jCheckBox2;
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
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JFormattedTextField multa;
    private javax.swing.JFormattedTextField nombres;
    private javax.swing.JFormattedTextField parqueaderos;
    private javax.swing.JFormattedTextField puertoBarras1;
    private javax.swing.JFormattedTextField puertoBarras2;
    private javax.swing.JFormattedTextField razonsocial;
    private javax.swing.JFormattedTextField retardoEntrada;
    private javax.swing.JFormattedTextField retardoSalida;
    private javax.swing.JCheckBox seabrefactura;
    private javax.swing.JCheckBox seabreticket;
    private javax.swing.JFormattedTextField telefono;
    private javax.swing.JFormattedTextField url;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
