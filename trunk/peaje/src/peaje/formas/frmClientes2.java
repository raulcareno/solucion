package peaje.formas;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import peaje.Administrador;
import peaje.validaciones;
import hibernate.*;
//import org.eclipse.persistence.internal.history.HistoricalDatabaseTable;
/**
 *
 * @author geovanny
 */
public class frmClientes2 extends javax.swing.JDialog {

    /** Creates new form frmClientes2 */
    public boolean grabar = false;
    public boolean modificar = false;
    public List lista = null;
    public int posicion = 0;
    public int tamano = 0;
    private Container desktopContenedor;
    private Clientes usuarioObj;
    Administrador adm;
    private String claveActual;
    private validaciones val;
    private principal principal;
    public Tarjetas tarjeta;
    public String nuevaTarjeta;

    /** Creates new form frmProfesores */
    public frmClientes2(java.awt.Frame parent, boolean modal,Administrador adm1) {
          super(parent,modal);
          adm = adm1;
        llenarCombo();
        initComponents();
        this.setSize(615, 508);
        usuarioObj = new Clientes();
    
        val = new validaciones();
      Date date = new Date();
        SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner  = new JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(spinner , "HH:mm");
        spinner.setEditor(de);
        horaDesde.setModel(sm);
        horaDesde.setEditor(de);
        SpinnerDateModel sm2 = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner2  = new JSpinner(sm2);
        JSpinner.DateEditor de2 = new JSpinner.DateEditor(spinner2 , "HH:mm");

        horaHasta.setEditor(de2);
        horaHasta.setModel(sm2);
        
        tarjeta = new Tarjetas();
//             panelHoras.add(spinner);
    }

    public frmClientes2(java.awt.Frame parent, boolean modal,principal lo,Administrador adm1) {
          super(parent,modal);
        this.desktopContenedor = lo.contenedor;
        adm = adm1;
        llenarCombo();
        initComponents();
        this.setSize(615, 508);
        usuarioObj = new Clientes();
        
        val = new validaciones();
        principal = lo;
        Date date = new Date();
            SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
            JSpinner spinner  = new JSpinner(sm);
            JSpinner.DateEditor de = new JSpinner.DateEditor(spinner , "HH:mm");
            spinner .setEditor(de);
            horaDesde.setModel(sm);
            horaDesde.setEditor(de);

        SpinnerDateModel sm2 = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner2  = new JSpinner(sm2);
        JSpinner.DateEditor de2 = new JSpinner.DateEditor(spinner2 , "HH:mm");

        horaHasta.setEditor(de2);
        horaHasta.setModel(sm2);
            
            
            tarjeta = new Tarjetas();

    }
       public frmClientes2(java.awt.Frame parent, boolean modal,principal lo,String tarjetase) {
          super(parent,modal);
        this.desktopContenedor = lo.contenedor;
        llenarCombo();
        initComponents();
        this.setSize(615, 508);
        usuarioObj = new Clientes();
        adm = new Administrador();
        val = new validaciones();
        principal = lo;
        Date date = new Date();
            SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
            JSpinner spinner  = new JSpinner(sm);
            JSpinner.DateEditor de = new JSpinner.DateEditor(spinner , "HH:mm");
            spinner .setEditor(de);
            horaDesde.setModel(sm);
            horaDesde.setEditor(de);

        SpinnerDateModel sm2 = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner2  = new JSpinner(sm2);
        JSpinner.DateEditor de2 = new JSpinner.DateEditor(spinner2 , "HH:mm");

        horaHasta.setEditor(de2);
        horaHasta.setModel(sm2);

nuevaTarjeta = tarjetase;
            tarjeta = new Tarjetas();

    }

    public void llenarCombo() {
        adm = new Administrador();
        try {
            perfilesList = new ArrayList<Global>();
            perfilesList = adm.query("Select o from Global as o where o.grupo = 'PER'");
        } catch (Exception ex) {
            Logger.getLogger(frmClientes2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// <editor-fold defaultstate="collapsed" desc="PROPIEDADES">
  

    public String getClaveActual() {
        return claveActual;
    }

    public void setClaveActual(String claveActual) {
        this.claveActual = claveActual;
    }

    public Clientes getUsuarioObj() {
        return usuarioObj;
    }

    public void setUsuarioObj(Clientes usuarioObj) {
        this.usuarioObj = usuarioObj;
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
        telefono.setEditable(estado);
        tarjetas.setEnabled(estado);
        cmbTipo.setEnabled(estado);
        btnNuevaTarjeta.setEnabled(estado);
    }

    public void limpiar() {
        usuarioObj = new Clientes();
        bindingGroup.unbind();
        bindingGroup.bind();
//        String estado = "";
//        codigo.setText(estado);
//        nombres.setText(estado);
//        direccion.setText(estado);
//        telefono.setText(estado);
//        DefaultTableModel dtm = (DefaultTableModel)tarjetas.getModel();
//        dtm.getDataVector().removeAllElements();
//        tarjetas.setModel(dtm);
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

        formaUsuarios = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        codigoBuscar = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        busquedaTabla = new javax.swing.JTable();
        peajePUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("peajePU").createEntityManager();
        tarjetasQuery = java.beans.Beans.isDesignTime() ? null : peajePUEntityManager.createQuery("SELECT t FROM Tarjetas t");
        tarjetasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : tarjetasQuery.getResultList();
        formaTarjetas = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        panelHoras = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        fechaDesde = new com.toedter.calendar.JDateChooser();
        fechaHasta = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
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
        jLabel14 = new javax.swing.JLabel();
        btnGuardarTarjeta = new javax.swing.JButton();
        btnSalirTarjetas = new javax.swing.JButton();
        activa = new javax.swing.JCheckBox();
        placa = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nombres = new javax.swing.JFormattedTextField();
        direccion = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        codigo = new javax.swing.JFormattedTextField();
        cmbTipo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        telefono = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tarjetas = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        btnNuevaTarjeta = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

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

        jLabel11.setText("NOMBRES:");
        jPanel8.add(jLabel11);
        jLabel11.setBounds(10, 10, 70, 14);

        formaUsuarios.getContentPane().add(jPanel8);
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

        formaUsuarios.getContentPane().add(jPanel7);
        jPanel7.setBounds(10, 60, 510, 180);

        formaTarjetas.getContentPane().setLayout(null);

        jLabel5.setText("No. Tarjeta: ");
        formaTarjetas.getContentPane().add(jLabel5);
        jLabel5.setBounds(30, 10, 70, 14);

        panelHoras.setBorder(javax.swing.BorderFactory.createTitledBorder("Fechas de Validez"));
        panelHoras.setLayout(null);

        jLabel7.setText("Hasta: ");
        panelHoras.add(jLabel7);
        jLabel7.setBounds(10, 40, 40, 14);

        jLabel6.setText("Desde:");
        panelHoras.add(jLabel6);
        jLabel6.setBounds(10, 20, 50, 14);

        fechaDesde.setDateFormatString("dd/MMM/yyyy");
        panelHoras.add(fechaDesde);
        fechaDesde.setBounds(60, 20, 95, 20);

        fechaHasta.setDateFormatString("dd/MMM/yyyy");
        panelHoras.add(fechaHasta);
        fechaHasta.setBounds(60, 40, 95, 20);

        formaTarjetas.getContentPane().add(panelHoras);
        panelHoras.setBounds(30, 100, 160, 70);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Horas de ingreso"));
        jPanel5.setLayout(null);

        jLabel9.setText("Hasta: ");
        jPanel5.add(jLabel9);
        jLabel9.setBounds(10, 40, 40, 14);

        jLabel13.setText("Desde:");
        jPanel5.add(jLabel13);
        jLabel13.setBounds(10, 20, 50, 14);
        jPanel5.add(horaDesde);
        horaDesde.setBounds(50, 20, 80, 20);
        jPanel5.add(horaHasta);
        horaHasta.setBounds(50, 40, 80, 20);

        formaTarjetas.getContentPane().add(jPanel5);
        jPanel5.setBounds(200, 100, 150, 70);

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
        formaTarjetas.getContentPane().add(noTarjeta);
        noTarjeta.setBounds(90, 10, 110, 20);
        formaTarjetas.getContentPane().add(ingresos);
        ingresos.setBounds(290, 30, 40, 20);

        jLabel14.setText("No. Ingresos: ");
        formaTarjetas.getContentPane().add(jLabel14);
        jLabel14.setBounds(214, 30, 70, 14);

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
        formaTarjetas.getContentPane().add(placa);
        placa.setBounds(90, 30, 110, 20);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Placa:");
        formaTarjetas.getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 30, 60, 14);

        jLabel16.setText("Descripción:");
        formaTarjetas.getContentPane().add(jLabel16);
        jLabel16.setBounds(20, 50, 70, 14);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        formaTarjetas.getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(90, 50, 240, 40);

        setTitle("Registro de Clientes");
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
        jLabel8.setText("Catálogo de Clientes ..::..");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 270, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10));
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Busqueda, Creación,Modificación,  Clientes ..::..");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 20, 250, 13);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 600, 40);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        jLabel1.setForeground(new java.awt.Color(0, 0, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("CI/RUC");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 10, 60, 14);

        nombres.setEditable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.nombres}"), nombres, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        nombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombresKeyPressed(evt);
            }
        });
        jPanel1.add(nombres);
        nombres.setBounds(80, 30, 310, 20);

        direccion.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.direccion}"), direccion, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionKeyPressed(evt);
            }
        });
        jPanel1.add(direccion);
        direccion.setBounds(80, 50, 310, 20);

        jLabel12.setForeground(new java.awt.Color(0, 0, 153));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Nombres:");
        jPanel1.add(jLabel12);
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
        jPanel1.add(codigo);
        codigo.setBounds(80, 10, 100, 20);

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CEDULA", "RUC", "PASAPORTE" }));
        cmbTipo.setEnabled(false);
        jPanel1.add(cmbTipo);
        cmbTipo.setBounds(180, 10, 120, 20);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Dirección:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 50, 60, 14);

        telefono.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.telefono}"), telefono, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jPanel1.add(telefono);
        telefono.setBounds(80, 70, 220, 20);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${usuarioObj.tarjetasCollection}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, tarjetas);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${habilitada}"));
        columnBinding.setColumnName("Habilitada");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tarjeta}"));
        columnBinding.setColumnName("Tarjeta");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${desde}"));
        columnBinding.setColumnName("Desde");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${hasta}"));
        columnBinding.setColumnName("Hasta");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        tarjetas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tarjetasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tarjetas);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 120, 380, 150);

        jLabel4.setText("Tarjetas");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 100, 70, 14);

        btnNuevaTarjeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/File3.gif"))); // NOI18N
        btnNuevaTarjeta.setText("Nueva Tarjeta");
        btnNuevaTarjeta.setEnabled(false);
        btnNuevaTarjeta.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btnNuevaTarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaTarjetaActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevaTarjeta);
        btnNuevaTarjeta.setBounds(80, 90, 100, 30);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Teléfono:");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(10, 70, 60, 14);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 50, 400, 280);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setLayout(null);

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
        jPanel4.add(btnBuscar);
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
        jPanel4.setBounds(40, 340, 370, 70);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents
 

    

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed

        // TODO add your handling code here:
        if(principal.permisos.getAgregar()){
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
                        Logger.getLogger(frmClientes2.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(frmClientes2.class.getName()).log(Level.SEVERE, null, ex);
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

            }

        }
        }else{
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }


    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:

        if (grabar == false) {
            if(principal.permisos.getModificar()){
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

            }else{
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
        if(principal.permisos.getEliminar()){        // TODO add your handling code here:
            try {
                adm.eliminarObjeto(Clientes.class, usuarioObj.getCodigo());
                this.limpiar();
            } catch (Exception ex) {
                Logger.getLogger(frmClientes2.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        }else{
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }
}//GEN-LAST:event_btnEliminarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        principal = null;
        usuarioObj = null;
        System.gc();
}//GEN-LAST:event_btnSalirActionPerformed

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

    private void codigoBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoBuscarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {

            Thread cargar = new Thread() {

                public void run() {
                    principal.procesando.setVisible(true);


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
                        Logger.getLogger(frmClientes2.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    principal.procesando.setVisible(false);
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
                this.usuarioObj = (Clientes) adm.buscarClave((Integer)busquedaTabla.getValueAt(fila, 0), Clientes.class);
                bindingGroup.unbind();
                bindingGroup.bind();

                formaUsuarios.dispose();
            } catch (Exception ex) {
                Logger.getLogger(frmClientes2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        JOptionPane.showMessageDialog(this, usuarioObj);
    }//GEN-LAST:event_busquedaTablaMouseClicked

    @SuppressWarnings("static-access")
    private void busquedaTablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaTablaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            try {
                int fila = busquedaTabla.getSelectedRow();
                this.usuarioObj = (Clientes) adm.buscarClave((Integer)busquedaTabla.getValueAt(fila, 0), Clientes.class);
  
                bindingGroup.unbind();
                bindingGroup.bind();
               formaUsuarios.dispose();
            } catch (Exception ex) {
                Logger.getLogger(frmClientes2.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
           formaUsuarios.dispose();
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

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.setVisible(false);
        }
    }//GEN-LAST:event_formKeyReleased

    private void btnNuevaTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaTarjetaActionPerformed
        // TODO add your handling code here:
        if(usuarioObj.getCodigo().equals(null)){
            JOptionPane.showMessageDialog(this,"Guarde primero el cliente ");
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

    }//GEN-LAST:event_btnNuevaTarjetaActionPerformed

    private void juevesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_juevesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_juevesActionPerformed

    private void btnGuardarTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarTarjetaActionPerformed
        // TODO add your handling code here:
        try {
            tarjeta.setTarjeta(noTarjeta.getText());
                tarjeta.setCliente(usuarioObj);
                tarjeta.setDesde(fechaDesde.getDate());
                tarjeta.setHasta(fechaHasta.getDate());
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
                 
                adm.actualizar(tarjeta);
                formaTarjetas.dispose();
                usuarioObj = (Clientes) adm.buscarClave(new Integer(usuarioObj.getCodigo()),Clientes.class);
                bindingGroup.unbind();
                bindingGroup.bind();
                               formaUsuarios.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al Guardar Tarjeta vuelva a intentarlo...!");
        }
        
        
    }//GEN-LAST:event_btnGuardarTarjetaActionPerformed

    private void btnSalirTarjetasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirTarjetasActionPerformed
        // TODO add your handling code here:
        formaTarjetas.dispose();
    }//GEN-LAST:event_btnSalirTarjetasActionPerformed


    void llenarTarjeta(){
                noTarjeta.setText(tarjeta.getTarjeta());
                fechaDesde.setDate(tarjeta.getDesde());
                fechaHasta.setDate(tarjeta.getHasta());
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
                JSpinner spinner  = new JSpinner(sm);
                JSpinner.DateEditor de = new JSpinner.DateEditor(spinner , "HH:mm");
                  horaDesde.setModel(sm);
                horaDesde.setEditor(de);

                SpinnerDateModel sm2 = new SpinnerDateModel(tarjeta.getHorafin(), null, null, Calendar.HOUR_OF_DAY);
                JSpinner spinner2  = new JSpinner(sm2);
                JSpinner.DateEditor de2 = new JSpinner.DateEditor(spinner2 , "HH:mm");

                horaHasta.setEditor(de2);
                horaHasta.setModel(sm2);
                if(!nuevaTarjeta.equals("")){
                    noTarjeta.setText(nuevaTarjeta);
                }
        

    }
    private void tarjetasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tarjetasMouseClicked
      // TODO add your handling code here:
        if(evt.getClickCount()==2){
            try {
                tarjeta = (Tarjetas) adm.buscarClave((String) tarjetas.getValueAt(tarjetas.getSelectedRow(), 1), Tarjetas.class);
                llenarTarjeta();
                formaTarjetas.setModal(true);
                formaTarjetas.setSize(400, 388);
                formaTarjetas.setLocation(250, 70);
                formaTarjetas.show();
            } catch (Exception ex) {
                Logger.getLogger(frmClientes2.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_tarjetasMouseClicked

    private void codigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoFocusLost
        // TODO add your handling code here:
        try {
            usuarioObj = (Clientes) adm.querySimple("Select o from Clientes as o " +
                    "where o.identificacion = '"+ codigo.getText().trim() +"' ");
            if(usuarioObj != null){
                bindingGroup.unbind();
                bindingGroup.bind();
                 modificar = true;
                    grabar = true;
            }
        } catch (Exception e) {
            System.out.println("BUSCAR CEDULA UNICA "+e);
        }
    }//GEN-LAST:event_codigoFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox activa;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardarTarjeta;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevaTarjeta;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalirTarjetas;
    private javax.swing.JTable busquedaTabla;
    private javax.swing.JComboBox cmbTipo;
    private javax.swing.JFormattedTextField codigo;
    private javax.swing.JFormattedTextField codigoBuscar;
    private javax.swing.JPanel diasHabiles;
    private javax.swing.JFormattedTextField direccion;
    private javax.swing.JCheckBox domingo;
    private com.toedter.calendar.JDateChooser fechaDesde;
    private com.toedter.calendar.JDateChooser fechaHasta;
    private javax.swing.JDialog formaTarjetas;
    private javax.swing.JDialog formaUsuarios;
    private javax.swing.JSpinner horaDesde;
    private javax.swing.JSpinner horaHasta;
    private javax.swing.JSpinner ingresos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JCheckBox jueves;
    private javax.swing.JCheckBox lunes;
    private javax.swing.JCheckBox martes;
    private javax.swing.JCheckBox miercoles;
    public javax.swing.JFormattedTextField noTarjeta;
    private javax.swing.JFormattedTextField nombres;
    private javax.swing.JPanel panelHoras;
    private javax.persistence.EntityManager peajePUEntityManager;
    private javax.swing.JFormattedTextField placa;
    private javax.swing.JCheckBox sabado;
    private javax.swing.JTable tarjetas;
    private java.util.List<Tarjetas> tarjetasList;
    private javax.persistence.Query tarjetasQuery;
    private javax.swing.JFormattedTextField telefono;
    private javax.swing.JCheckBox viernes;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    public Tarjetas getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjetas tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getNuevaTarjeta() {
        return nuevaTarjeta;
    }

    public void setNuevaTarjeta(String nuevaTarjeta) {
        this.nuevaTarjeta = nuevaTarjeta;
    }


}
