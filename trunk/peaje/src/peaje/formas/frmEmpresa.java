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
import peaje.Administrador;
import peaje.validaciones;

//import org.eclipse.persistence.internal.history.HistoricalDatabaseTable;
/**
 *
 * @author geovanny
 */
public class frmEmpresa extends javax.swing.JDialog {

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
    private principal principal;

    /** Creates new form frmProfesores */
    public frmEmpresa(java.awt.Frame parent, boolean modal, Administrador adm1) {
        super(parent, modal);
        adm = adm1;
        llenarCombo();
        initComponents();
        this.setSize(615, 508);
        empresaObj = new Empresa();

        val = new validaciones();

            try {

               Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
                this.empresaObj = emp;
                bindingGroup.unbind();
                bindingGroup.bind();
               
            } catch (Exception ex) {
                Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    public frmEmpresa(java.awt.Frame parent, boolean modal, principal lo, Administrador adm1) {
        super(parent, modal);
        this.desktopContenedor = lo.contenedor;
        adm = adm1;
        llenarCombo();
        initComponents();
        this.setSize(615, 508);
        empresaObj = new Empresa();

        val = new validaciones();
        principal = lo;
        cmbTicket.removeAllItems();
        cmbFactura.removeAllItems();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (int i = 0; i < services.length; i++) {
            cmbTicket.addItem(services[i].getName());
            cmbFactura.addItem(services[i].getName());
            cmbImpresora.addItem(services[i].getName());
            //String nombre = services[i].getName().toUpperCase();
        }
        CommPortIdentifier portId;
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
                cmbEntrada1.removeAllItems();
                cmbEntrada2.removeAllItems();
                cmbEntrada3.removeAllItems();
                cmbEntrada4.removeAllItems();
                cmbPuertoPrincipal.removeAllItems();
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
        for (Iterator it = arr.iterator(); it.hasNext();) {
            Object object = it.next();
                cmbEntrada1.addItem(object);
                cmbEntrada2.addItem(object);
                cmbEntrada3.addItem(object);
                cmbEntrada4.addItem(object);
                cmbPuertoPrincipal.addItem(object);
//                cmbSalida1.addItem(object);
//                cmbSalida2.addItem(object);
//                cmbSalida3.addItem(object);

        }
           try {


                Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
                this.empresaObj = emp;
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
        parqueaderos.setEditable(estado);
        iva.setEditable(estado);
        cmbFactura.setEnabled(estado);
        cmbTicket.setEnabled(estado);
        cmbEntrada1.setEnabled(estado);
        cmbEntrada2.setEnabled(estado);
        cmbEntrada3.setEnabled(estado);
        cmbEntrada4.setEnabled(estado);
        cmbImpresora.setEnabled(estado);
        cmbPuertoPrincipal.setEnabled(estado);
        cmbSalida1.setEnabled(estado);
        cmbSalida2.setEnabled(estado);
        cmbSalida3.setEnabled(estado);
        cmbSalida4.setEnabled(estado);
        chkActivo1.setEnabled(estado);
        chkActivo2.setEnabled(estado);
        chkActivo3.setEnabled(estado);
        chkActivo4.setEnabled(estado);



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
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nombres = new javax.swing.JFormattedTextField();
        direccion = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        codigo = new javax.swing.JFormattedTextField();
        razonsocial = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        telefono = new javax.swing.JFormattedTextField();
        parqueaderos = new javax.swing.JFormattedTextField();
        cmbFactura = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmbTicket = new javax.swing.JComboBox();
        cmbEntrada1 = new javax.swing.JComboBox();
        cmbSalida1 = new javax.swing.JComboBox();
        cmbSalida2 = new javax.swing.JComboBox();
        cmbEntrada2 = new javax.swing.JComboBox();
        cmbSalida3 = new javax.swing.JComboBox();
        cmbEntrada3 = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cmbEntrada4 = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        cmbSalida4 = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cmbImpresora = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        cmbPuertoPrincipal = new javax.swing.JComboBox();
        chkActivo1 = new javax.swing.JCheckBox();
        chkActivo2 = new javax.swing.JCheckBox();
        chkActivo3 = new javax.swing.JCheckBox();
        chkActivo4 = new javax.swing.JCheckBox();
        iva = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

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

        setTitle("Datos de la Empresa");
        addKeyListener(new java.awt.event.KeyAdapter() {
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
        jPanel3.setBounds(0, 0, 600, 40);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        jLabel1.setForeground(new java.awt.Color(0, 0, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("CI/RUC:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 10, 100, 14);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Dirección:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 70, 100, 14);

        nombres.setEditable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.nombre}"), nombres, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        nombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombresKeyPressed(evt);
            }
        });
        jPanel1.add(nombres);
        nombres.setBounds(130, 30, 220, 20);

        direccion.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.direccion}"), direccion, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionKeyPressed(evt);
            }
        });
        jPanel1.add(direccion);
        direccion.setBounds(130, 70, 220, 20);

        jLabel12.setForeground(new java.awt.Color(0, 0, 153));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Nombres:");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 30, 100, 14);

        codigo.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.ruc}"), codigo, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoKeyPressed(evt);
            }
        });
        jPanel1.add(codigo);
        codigo.setBounds(130, 10, 100, 20);

        razonsocial.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.razon}"), razonsocial, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        razonsocial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                razonsocialKeyPressed(evt);
            }
        });
        jPanel1.add(razonsocial);
        razonsocial.setBounds(130, 50, 109, 20);

        jLabel5.setForeground(new java.awt.Color(0, 0, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Razón Social:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 50, 110, 14);

        jLabel6.setForeground(new java.awt.Color(0, 0, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Teléfonos:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 90, 100, 14);

        jLabel7.setForeground(new java.awt.Color(0, 0, 153));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("IVA %");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(160, 110, 110, 20);

        telefono.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.telefonos}"), telefono, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefonoKeyPressed(evt);
            }
        });
        jPanel1.add(telefono);
        telefono.setBounds(130, 90, 110, 20);

        parqueaderos.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.parqueaderos}"), parqueaderos, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        parqueaderos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                parqueaderosKeyPressed(evt);
            }
        });
        jPanel1.add(parqueaderos);
        parqueaderos.setBounds(130, 110, 40, 20);

        cmbFactura.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.impfactura}"), cmbFactura, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jPanel1.add(cmbFactura);
        cmbFactura.setBounds(130, 150, 240, 20);

        jLabel9.setForeground(new java.awt.Color(0, 0, 153));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("No. Parqueaderos:");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(-10, 110, 130, 14);

        jLabel13.setForeground(new java.awt.Color(0, 0, 153));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Imp. Ticket:");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(10, 130, 110, 14);

        cmbTicket.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.impticket}"), cmbTicket, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jPanel1.add(cmbTicket);
        cmbTicket.setBounds(130, 130, 240, 20);

        cmbEntrada1.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerto1}"), cmbEntrada1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jPanel1.add(cmbEntrada1);
        cmbEntrada1.setBounds(120, 240, 60, 20);

        cmbSalida1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        cmbSalida1.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerta1}"), cmbSalida1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jPanel1.add(cmbSalida1);
        cmbSalida1.setBounds(190, 240, 37, 20);

        cmbSalida2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        cmbSalida2.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerta2}"), cmbSalida2, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jPanel1.add(cmbSalida2);
        cmbSalida2.setBounds(190, 260, 37, 20);

        cmbEntrada2.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerto2}"), cmbEntrada2, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jPanel1.add(cmbEntrada2);
        cmbEntrada2.setBounds(120, 260, 60, 20);

        cmbSalida3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        cmbSalida3.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerta3}"), cmbSalida3, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jPanel1.add(cmbSalida3);
        cmbSalida3.setBounds(190, 280, 37, 20);

        cmbEntrada3.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerto3}"), cmbEntrada3, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jPanel1.add(cmbEntrada3);
        cmbEntrada3.setBounds(120, 280, 60, 20);

        jLabel14.setText("1:");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(100, 240, 20, 14);

        jLabel15.setText("4:");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(100, 300, 20, 14);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("PUERTO LECTORA");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(80, 220, 110, 20);

        cmbEntrada4.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerto4}"), cmbEntrada4, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jPanel1.add(cmbEntrada4);
        cmbEntrada4.setBounds(120, 300, 60, 20);

        jLabel19.setText("2:");
        jPanel1.add(jLabel19);
        jLabel19.setBounds(100, 260, 20, 14);

        cmbSalida4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));
        cmbSalida4.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerta4}"), cmbSalida4, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jPanel1.add(cmbSalida4);
        cmbSalida4.setBounds(190, 300, 37, 20);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("ABRE PUERTA");
        jPanel1.add(jLabel21);
        jLabel21.setBounds(190, 220, 90, 20);

        jLabel3.setText("3:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(100, 280, 20, 14);

        jLabel18.setForeground(new java.awt.Color(0, 0, 153));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Imp. Factura:");
        jPanel1.add(jLabel18);
        jLabel18.setBounds(10, 150, 110, 14);

        cmbImpresora.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.impresora}"), cmbImpresora, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jPanel1.add(cmbImpresora);
        cmbImpresora.setBounds(130, 170, 240, 20);

        jLabel20.setForeground(new java.awt.Color(0, 0, 153));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Impresora Botón:");
        jPanel1.add(jLabel20);
        jLabel20.setBounds(0, 170, 120, 14);

        cmbPuertoPrincipal.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.puerto}"), cmbPuertoPrincipal, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jPanel1.add(cmbPuertoPrincipal);
        cmbPuertoPrincipal.setBounds(130, 190, 60, 20);

        chkActivo1.setText("Activo");
        chkActivo1.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.activa1}"), chkActivo1, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        chkActivo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkActivo1ActionPerformed(evt);
            }
        });
        jPanel1.add(chkActivo1);
        chkActivo1.setBounds(230, 240, 81, 23);

        chkActivo2.setText("Activo");
        chkActivo2.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.activa2}"), chkActivo2, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        chkActivo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkActivo2ActionPerformed(evt);
            }
        });
        jPanel1.add(chkActivo2);
        chkActivo2.setBounds(230, 260, 81, 23);

        chkActivo3.setText("Activo");
        chkActivo3.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.activa3}"), chkActivo3, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        chkActivo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkActivo3ActionPerformed(evt);
            }
        });
        jPanel1.add(chkActivo3);
        chkActivo3.setBounds(230, 280, 81, 23);

        chkActivo4.setText("Activo");
        chkActivo4.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.activa4}"), chkActivo4, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        chkActivo4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkActivo4ActionPerformed(evt);
            }
        });
        jPanel1.add(chkActivo4);
        chkActivo4.setBounds(230, 300, 81, 23);

        iva.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.iva}"), iva, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jPanel1.add(iva);
        iva.setBounds(280, 110, 59, 20);

        jLabel22.setForeground(new java.awt.Color(0, 0, 153));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Puerto Tarjeta Princ.:");
        jPanel1.add(jLabel22);
        jLabel22.setBounds(10, 190, 110, 14);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(20, 40, 380, 330);

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
        jPanel4.add(btnBuscar);
        btnBuscar.setBounds(50, 10, 60, 50);

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
        jPanel4.add(btnAgregar);
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
        jPanel4.add(btnModificar);
        btnModificar.setBounds(170, 10, 60, 50);

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
        jPanel4.add(btnEliminar);
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
        jPanel4.add(btnSalir);
        btnSalir.setBounds(290, 10, 60, 50);

        getContentPane().add(jPanel4);
        jPanel4.setBounds(30, 380, 370, 70);

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
                    empresaObj.setPuerto1((String)cmbEntrada1.getSelectedItem());
                    empresaObj.setPuerto2((String)cmbEntrada2.getSelectedItem());
                    empresaObj.setPuerto3((String)cmbEntrada3.getSelectedItem());
                    empresaObj.setPuerto4((String)cmbEntrada4.getSelectedItem());
                    
                    empresaObj.setPuerta1((String)cmbSalida1.getSelectedItem());
                    empresaObj.setPuerta2((String)cmbSalida2.getSelectedItem());
                    empresaObj.setPuerta3((String)cmbSalida3.getSelectedItem());
                    empresaObj.setPuerta4((String)cmbSalida4.getSelectedItem());

                    empresaObj.setPuerto((String)cmbPuertoPrincipal.getSelectedItem());
                    empresaObj.setImpresora((String)cmbImpresora.getSelectedItem());
                    
                    
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

                    cmbEntrada1.setEnabled(chkActivo1.isSelected()); cmbSalida1.setEnabled(chkActivo1.isSelected());


                    cmbEntrada2.setEnabled(chkActivo2.isSelected()); cmbSalida2.setEnabled(chkActivo2.isSelected());


                    cmbEntrada3.setEnabled(chkActivo3.isSelected()); cmbSalida3.setEnabled(chkActivo3.isSelected());



                    cmbEntrada4.setEnabled(chkActivo4.isSelected()); cmbSalida4.setEnabled(chkActivo4.isSelected());
                 

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
        }
    }//GEN-LAST:event_codigoKeyPressed

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

    private void razonsocialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_razonsocialKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            razonsocial.nextFocus();
        }
    }//GEN-LAST:event_razonsocialKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.setVisible(false);
        }
    }//GEN-LAST:event_formKeyReleased

    private void telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonoKeyPressed

    private void parqueaderosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_parqueaderosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_parqueaderosKeyPressed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void chkActivo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkActivo1ActionPerformed
        // TODO add your handling code here:

        cmbEntrada1.setEnabled(chkActivo1.isSelected());
        cmbSalida1.setEnabled(chkActivo1.isSelected());
    }//GEN-LAST:event_chkActivo1ActionPerformed

    private void chkActivo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkActivo2ActionPerformed
        // TODO add your handling code here:
        cmbEntrada2.setEnabled(chkActivo2.isSelected());
        cmbSalida2.setEnabled(chkActivo2.isSelected());
    }//GEN-LAST:event_chkActivo2ActionPerformed

    private void chkActivo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkActivo3ActionPerformed
        // TODO add your handling code here:
        cmbEntrada3.setEnabled(chkActivo3.isSelected());
        cmbSalida3.setEnabled(chkActivo3.isSelected());
    }//GEN-LAST:event_chkActivo3ActionPerformed

    private void chkActivo4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkActivo4ActionPerformed
        // TODO add your handling code here:
        cmbEntrada4.setEnabled(chkActivo4.isSelected());
        cmbSalida4.setEnabled(chkActivo4.isSelected());
    }//GEN-LAST:event_chkActivo4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JComboBox cmbEntrada1;
    private javax.swing.JComboBox cmbEntrada2;
    private javax.swing.JComboBox cmbEntrada3;
    private javax.swing.JComboBox cmbEntrada4;
    private javax.swing.JComboBox cmbFactura;
    private javax.swing.JComboBox cmbImpresora;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JFormattedTextField nombres;
    private javax.swing.JFormattedTextField parqueaderos;
    private javax.swing.JFormattedTextField razonsocial;
    private javax.swing.JFormattedTextField telefono;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
