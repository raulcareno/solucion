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
     
     
        CommPortIdentifier portId;
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
            
                cmbPuertoPrincipal.removeAllItems();
                cmbPuertoLed.removeAllItems();
            
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
              
//                cmbPuertaSale.removeAllItems();
//                cmbPuertaSale2.removeAllItems();


        for (Iterator it = arr.iterator(); it.hasNext();) {
            Object object = it.next();
               

                cmbPuertoPrincipal.addItem(object);
                cmbPuertoLed.addItem(object);
           
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
        codigo.setEditable(estado);
        nombres.setEditable(estado);
        direccion.setEditable(estado);
        razonsocial.setEditable(estado);
        telefono.setEditable(estado);
       
        url.setEditable(estado);
        chkIpcam.setEnabled(estado);
        chkWebcam.setEnabled(estado);
        
        cmbPuertoPrincipal.setEnabled(estado);
        cmbPuertoLed.setEnabled(estado);
        
    }

    public void limpiar() {
        String estado = "";
        codigo.setText(estado);
        nombres.setText(estado);
        direccion.setText(estado);
        razonsocial.setText(estado);
        telefono.setText(estado);
 

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
        chkWebcam = new javax.swing.JCheckBox();
        jLabel29 = new javax.swing.JLabel();
        chkIpcam = new javax.swing.JCheckBox();
        url = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        cmbPuertoPrincipal = new javax.swing.JComboBox();
        cmbPuertoLed = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();

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

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11));
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

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Catálogo de Empresas ..::..");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 270, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10));
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
        jPanel4.setBounds(10, 330, 430, 70);

        jPanel2.setLayout(null);

        jLabel1.setForeground(new java.awt.Color(0, 0, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("CI/RUC:");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(10, 10, 100, 14);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Dirección:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(10, 70, 100, 14);

        nombres.setEditable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.nombre}"), nombres, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        nombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombresKeyPressed(evt);
            }
        });
        jPanel2.add(nombres);
        nombres.setBounds(120, 30, 220, 20);

        direccion.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.direccion}"), direccion, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionKeyPressed(evt);
            }
        });
        jPanel2.add(direccion);
        direccion.setBounds(120, 70, 220, 20);

        jLabel12.setForeground(new java.awt.Color(0, 0, 153));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Nombres:");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(10, 30, 100, 14);

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
        codigo.setBounds(120, 10, 100, 20);

        razonsocial.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.razon}"), razonsocial, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        razonsocial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                razonsocialKeyPressed(evt);
            }
        });
        jPanel2.add(razonsocial);
        razonsocial.setBounds(120, 50, 220, 20);

        jLabel5.setForeground(new java.awt.Color(0, 0, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Razón Social:");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(0, 50, 110, 14);

        jLabel6.setForeground(new java.awt.Color(0, 0, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Teléfonos:");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(10, 90, 100, 14);

        telefono.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.telefonos}"), telefono, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefonoKeyPressed(evt);
            }
        });
        jPanel2.add(telefono);
        telefono.setBounds(120, 90, 100, 20);

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
        chkWebcam.setBounds(230, 120, 140, 18);

        jLabel29.setText("URL IP CAM:");
        jPanel2.add(jLabel29);
        jLabel29.setBounds(30, 150, 80, 20);

        chkIpcam.setText("Trabaja con Cámara IP        ó");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.ipcam}"), chkIpcam, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        chkIpcam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIpcamActionPerformed(evt);
            }
        });
        jPanel2.add(chkIpcam);
        chkIpcam.setBounds(50, 120, 170, 18);

        url.setEditable(false);
        url.setText("http://");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.url}"), url, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jPanel2.add(url);
        url.setBounds(120, 150, 240, 18);

        jLabel22.setForeground(new java.awt.Color(0, 0, 153));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Puerto Tarjeta Interfaz  PC-BARRERA:");
        jPanel2.add(jLabel22);
        jLabel22.setBounds(90, 180, 240, 14);

        cmbPuertoPrincipal.setEditable(true);
        cmbPuertoPrincipal.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerto}"), cmbPuertoPrincipal, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPuertoPrincipal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuertoPrincipalKeyPressed(evt);
            }
        });
        jPanel2.add(cmbPuertoPrincipal);
        cmbPuertoPrincipal.setBounds(340, 180, 60, 20);

        cmbPuertoLed.setEditable(true);
        cmbPuertoLed.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.led}"), cmbPuertoLed, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbPuertoLed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPuertoLedKeyPressed(evt);
            }
        });
        jPanel2.add(cmbPuertoLed);
        cmbPuertoLed.setBounds(340, 200, 60, 20);

        jLabel27.setForeground(new java.awt.Color(0, 0, 153));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Puerto Pantalla LEDS (parqueaderos disponibles):");
        jPanel2.add(jLabel27);
        jLabel27.setBounds(90, 200, 240, 14);

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/interfaz.png"))); // NOI18N
        jPanel2.add(jLabel45);
        jLabel45.setBounds(40, 170, 40, 40);

        jTabbedPane1.addTab("DATOS DE LA EMPRESA", new javax.swing.ImageIcon(getClass().getResource("/images/empresa.png")), jPanel2); // NOI18N

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 40, 430, 280);

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
                  

                    

                    

                    empresaObj.setPuerto((String)cmbPuertoPrincipal.getSelectedItem());
                    empresaObj.setLed((String)cmbPuertoLed.getSelectedItem());
                   
                    empresaObj.setWebcam(chkWebcam.isSelected());
                    empresaObj.setUrl(url.getText());
                    empresaObj.setIpcam(chkIpcam.isSelected());
                   
                   
                    

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

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

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

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
         principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_formKeyPressed

    private void cmbPuertoLedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuertoLedKeyPressed
        // TODO add your handling code here:
           principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbPuertoLedKeyPressed

    private void cmbPuertoBarras1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPuertoBarras1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_cmbPuertoBarras1KeyPressed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JTable busquedaTabla;
    private javax.swing.JCheckBox chkIpcam;
    private javax.swing.JCheckBox chkWebcam;
    private javax.swing.JComboBox cmbPuertoBarras1;
    private javax.swing.JComboBox cmbPuertoLed;
    private javax.swing.JComboBox cmbPuertoPrincipal;
    private javax.swing.JFormattedTextField codigo;
    private javax.swing.JFormattedTextField codigoBuscar;
    private javax.swing.JFormattedTextField direccion;
    private javax.swing.JDialog formaEmpresa;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JFormattedTextField nombres;
    private javax.swing.JFormattedTextField razonsocial;
    private javax.swing.JFormattedTextField telefono;
    private javax.swing.JFormattedTextField url;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
