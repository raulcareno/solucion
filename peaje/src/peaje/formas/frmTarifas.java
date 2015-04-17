/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmTarifas1.java
 *
 * Created on 17/03/2011, 06:02:21 PM
 */
package peaje.formas;

import hibernate.*;
import java.awt.Container;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import hibernate.cargar.Administrador;
import hibernate.cargar.validaciones;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JSpinner;
import javax.swing.ListModel;
import javax.swing.SpinnerDateModel;

/**
 *
 * @author Ismael Jadan
 */
public class frmTarifas extends javax.swing.JInternalFrame {

    public boolean grabar = false;
    public boolean modificar = false;
    public boolean grabar1 = false;
    public boolean modificar1 = false;
    public boolean grabar2 = false;
    public boolean modificar2 = false;
    public boolean grabar3 = false;
    public boolean modificar3 = false;
    public boolean grabar5 = false;
    public boolean modificar5 = false;
    public List lista = null;
    public int posicion = 0;
    public int tamano = 0;
    private Container desktopContenedor;
    private Empresa empresaObj;
    Administrador adm;
    private String claveActual;
    private validaciones val;
    private frmPrincipal principal;

    /**
     * Creates new form frmTarifas1
     */
    public frmTarifas(java.awt.Frame parent, boolean modal) {
//        super(parent, modal);
        initComponents();
    }

    public frmTarifas(java.awt.Frame parent, boolean modal, Administrador adm1) {
//          super(parent,modal);

        initComponents();
        this.setSize(615, 508);
        empresaObj = new Empresa();
        adm = adm1;
        val = new validaciones();
        cargarComboTipo();

    }

    public frmTarifas(java.awt.Frame parent, boolean modal, frmPrincipal lo, Administrador adm1) {
//          super(parent,modal);
        this.desktopContenedor = lo.contenedor;

        initComponents();

        this.setSize(615, 508);
        adm = adm1;
        val = new validaciones();
        principal = lo;
        //llenarCombo();
        cargarComboTipo();

    }

    public void llenarCombo() {

        try {
            boolean codigonull = false;
            List<Tarifas> tar = adm.query("Select o from Tarifas as o where o.tipotarifa.codigo = '" + ((Tipotarifa) cmbTipoTarifas.getSelectedItem()).getCodigo() + "' order by o.codigo ");
            if (tar.size() <= 0) {
                tar = adm.query("Select o from Tarifas as o where o.tipotarifa is null order by o.codigo ");
                codigonull = true;
            }

            DefaultTableModel dtm = (DefaultTableModel) tarifario.getModel();
            dtm.getDataVector().removeAllElements();
            for (Tarifas tarifas : tar) {
                Object[] obj = new Object[5];
                obj[0] = tarifas.getCodigo();
                if (codigonull) {
                    obj[0] = 0;
                }
                obj[1] = tarifas.getDesde();
                obj[2] = tarifas.getHasta();
                try {
                    obj[3] = tarifas.getValor().doubleValue();
                } catch (Exception e) {
                    obj[3] = 0.0;
                }
                dtm.addRow(obj);
            }

            tarifario.setModel(dtm);
        } catch (Exception ex) {
            Logger.getLogger(frmTarifas.class.getName()).log(Level.SEVERE, null, ex);
        }

        llenarProductos();
        llenarProductos1();
        llenarProductos2();

    }

    void llenarProductos() {
        try {

            List<Productos> tar = adm.query("Select o from Productos as o ");
            DefaultTableModel dtm = (DefaultTableModel) tbTarifas.getModel();
            dtm.getDataVector().removeAllElements();
            for (Productos tarifas : tar) {
                Object[] obj = new Object[5];
                obj[0] = tarifas.getCodigo();
                obj[1] = tarifas.getDescripcion();
                obj[2] = tarifas.getValor();
                dtm.addRow(obj);
            }

            tbTarifas.setModel(dtm);
        } catch (Exception ex) {
            Logger.getLogger(frmTarifas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void llenarProductos1() {
        try {

            List<Tarifasdiarias> tar = adm.query("Select o from Tarifasdiarias as o ");
            DefaultTableModel dtm = (DefaultTableModel) tbTarifas1.getModel();
            dtm.getDataVector().removeAllElements();
            for (Tarifasdiarias tarifas : tar) {
                Object[] obj = new Object[5];
                obj[0] = tarifas.getCodigo();
                obj[1] = tarifas.getNombre();
                obj[2] = tarifas.getValor();
                dtm.addRow(obj);
            }

            tbTarifas1.setModel(dtm);
        } catch (Exception ex) {
            Logger.getLogger(frmTarifas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void llenarProductos2() {
        try {

            List<Descuento> tar = adm.query("Select o from Descuento as o ");
            DefaultTableModel dtm = (DefaultTableModel) tbTarifas2.getModel();
            dtm.getDataVector().removeAllElements();
            for (Descuento tarifas : tar) {
                Object[] obj = new Object[5];
                obj[0] = tarifas.getCodigo();
                obj[1] = tarifas.getNombre();
                obj[2] = tarifas.getValor();
                obj[3] = tarifas.getTipo().equals("1") ? "%" : "USD";
                dtm.addRow(obj);
            }
            tbTarifas2.setModel(dtm);
        } catch (Exception ex) {
            Logger.getLogger(frmTarifas.class.getName()).log(Level.SEVERE, null, ex);
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

    public void habilitar1(Boolean estado) {

    }

    public void habilitar3(Boolean estado) {

    }

    public void habilitar2(Boolean estado) {
////        txtCodigo2.setEditable(estado);
////        txtNombre2.setEditable(estado);
////        txtValor2.setEditable(estado);

    }

    public void limpiar() {
        String estado = "";
        txtCodigo.setText("");
        txtNombre.setText("");
        txtValor.setText("");
    }

    public void limpiar1() {
        String estado = "";
        txtCodigo1.setText("");
        txtNombre1.setText("");
        txtValor1.setText("");
    }

    public void limpiar3() {
        String estado = "";
        txtCodigo2.setText("");
        txtNombre2.setText("");
        txtValor2.setText("");
        esPorcentaje.setSelected(false);
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

        formaUsuarios = new javax.swing.JDialog();
        jPanel12 = new javax.swing.JPanel();
        codigoBuscar = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        busquedaTabla = new javax.swing.JTable();
        frmTipoTarifas = new javax.swing.JInternalFrame();
        jLabel17 = new javax.swing.JLabel();
        btnModificar5 = new javax.swing.JButton();
        btnSalir5 = new javax.swing.JButton();
        btnAgregar5 = new javax.swing.JButton();
        nombreTipo = new javax.swing.JFormattedTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
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
        txtCodigoTipo = new javax.swing.JFormattedTextField();
        btnEliminar5 = new javax.swing.JButton();
        btnBuscar5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        horas = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        minutos = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tarifario = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        cmbTipoTarifas = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbTarifas = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtValor = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        btnAgregar1 = new javax.swing.JButton();
        btnModificar1 = new javax.swing.JButton();
        btnEliminar1 = new javax.swing.JButton();
        btnSalir1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        txtNombre1 = new javax.swing.JTextField();
        txtCodigo1 = new javax.swing.JTextField();
        txtValor1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbTarifas1 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        btnAgregar2 = new javax.swing.JButton();
        btnModificar2 = new javax.swing.JButton();
        btnEliminar2 = new javax.swing.JButton();
        btnSalir2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbTarifas2 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtCodigo2 = new javax.swing.JTextField();
        txtNombre2 = new javax.swing.JTextField();
        txtValor2 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        btnAgregar3 = new javax.swing.JButton();
        btnModificar3 = new javax.swing.JButton();
        btnEliminar3 = new javax.swing.JButton();
        btnSalir3 = new javax.swing.JButton();
        esPorcentaje = new javax.swing.JCheckBox();

        formaUsuarios.setLocationByPlatform(true);
        formaUsuarios.getContentPane().setLayout(null);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel12.setLayout(null);

        codigoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoBuscarKeyPressed(evt);
            }
        });
        jPanel12.add(codigoBuscar);
        codigoBuscar.setBounds(100, 10, 220, 20);

        jLabel18.setText("Nombre Tarifa: ");
        jPanel12.add(jLabel18);
        jLabel18.setBounds(10, 10, 90, 14);

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enter.png"))); // NOI18N
        jLabel19.setText("Presione Enter");
        jPanel12.add(jLabel19);
        jLabel19.setBounds(340, 10, 170, 22);

        formaUsuarios.getContentPane().add(jPanel12);
        jPanel12.setBounds(10, 10, 510, 40);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel14.setLayout(null);

        busquedaTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Codigo", "Tarifa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
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
        jScrollPane5.setViewportView(busquedaTabla);
        if (busquedaTabla.getColumnModel().getColumnCount() > 0) {
            busquedaTabla.getColumnModel().getColumn(0).setMinWidth(0);
            busquedaTabla.getColumnModel().getColumn(0).setPreferredWidth(0);
            busquedaTabla.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jPanel14.add(jScrollPane5);
        jScrollPane5.setBounds(20, 20, 350, 150);

        formaUsuarios.getContentPane().add(jPanel14);
        jPanel14.setBounds(10, 60, 510, 180);

        setTitle("Tarifario");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dinero.gif"))); // NOI18N
        getContentPane().setLayout(null);

        frmTipoTarifas.setTitle("Tipos de Tarifas");
        frmTipoTarifas.setVisible(false);
        frmTipoTarifas.getContentPane().setLayout(null);

        jLabel17.setText("Nombre:");
        frmTipoTarifas.getContentPane().add(jLabel17);
        jLabel17.setBounds(10, 20, 60, 14);

        btnModificar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png"))); // NOI18N
        btnModificar5.setMnemonic('M');
        btnModificar5.setText("Modificar");
        btnModificar5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificar5.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btnModificar5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificar5ActionPerformed(evt);
            }
        });
        btnModificar5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnModificar5KeyPressed(evt);
            }
        });
        frmTipoTarifas.getContentPane().add(btnModificar5);
        btnModificar5.setBounds(190, 190, 60, 50);

        btnSalir5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salir.png"))); // NOI18N
        btnSalir5.setMnemonic('S');
        btnSalir5.setText("Salir");
        btnSalir5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnSalir5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir5ActionPerformed(evt);
            }
        });
        btnSalir5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSalir5KeyPressed(evt);
            }
        });
        frmTipoTarifas.getContentPane().add(btnSalir5);
        btnSalir5.setBounds(310, 190, 60, 50);

        btnAgregar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png"))); // NOI18N
        btnAgregar5.setMnemonic('N');
        btnAgregar5.setText("Nuevo");
        btnAgregar5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnAgregar5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgregar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregar5ActionPerformed(evt);
            }
        });
        btnAgregar5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAgregar5KeyPressed(evt);
            }
        });
        frmTipoTarifas.getContentPane().add(btnAgregar5);
        btnAgregar5.setBounds(130, 190, 60, 50);

        nombreTipo.setEditable(false);
        frmTipoTarifas.getContentPane().add(nombreTipo);
        nombreTipo.setBounds(70, 20, 290, 20);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Horas de ingreso", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 102, 204)));
        jPanel13.setLayout(null);

        jLabel20.setText("Hasta: ");
        jPanel13.add(jLabel20);
        jLabel20.setBounds(190, 20, 40, 14);

        jLabel21.setText("Desde:");
        jPanel13.add(jLabel21);
        jLabel21.setBounds(10, 20, 50, 14);

        horaDesde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                horaDesdeKeyPressed(evt);
            }
        });
        jPanel13.add(horaDesde);
        horaDesde.setBounds(50, 20, 80, 20);

        horaHasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                horaHastaKeyPressed(evt);
            }
        });
        jPanel13.add(horaHasta);
        horaHasta.setBounds(230, 20, 80, 20);

        frmTipoTarifas.getContentPane().add(jPanel13);
        jPanel13.setBounds(10, 50, 360, 50);

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
        lunes.setBounds(30, 50, 80, 23);

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
        miercoles.setBounds(120, 30, 80, 23);

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
        jueves.setBounds(120, 50, 80, 23);

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
        sabado.setBounds(230, 30, 80, 23);

        domingo.setText("Domingo");
        domingo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                domingoKeyPressed(evt);
            }
        });
        diasHabiles.add(domingo);
        domingo.setBounds(230, 50, 80, 23);

        todos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        todos.setForeground(new java.awt.Color(204, 51, 0));
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
        todos.setBounds(30, 20, 70, 23);

        txtCodigoTipo.setEditable(false);
        txtCodigoTipo.setText("0");
        diasHabiles.add(txtCodigoTipo);
        txtCodigoTipo.setBounds(310, 20, 30, 20);

        frmTipoTarifas.getContentPane().add(diasHabiles);
        diasHabiles.setBounds(10, 100, 360, 80);

        btnEliminar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eliminar.png"))); // NOI18N
        btnEliminar5.setMnemonic('E');
        btnEliminar5.setText("Eliminar");
        btnEliminar5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnEliminar5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar5ActionPerformed(evt);
            }
        });
        btnEliminar5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminar5KeyPressed(evt);
            }
        });
        frmTipoTarifas.getContentPane().add(btnEliminar5);
        btnEliminar5.setBounds(250, 190, 60, 50);

        btnBuscar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.gif"))); // NOI18N
        btnBuscar5.setMnemonic('B');
        btnBuscar5.setText("Buscar");
        btnBuscar5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBuscar5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnBuscar5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBuscar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscar5ActionPerformed(evt);
            }
        });
        btnBuscar5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBuscar5KeyPressed(evt);
            }
        });
        frmTipoTarifas.getContentPane().add(btnBuscar5);
        btnBuscar5.setBounds(70, 190, 60, 50);

        getContentPane().add(frmTipoTarifas);
        frmTipoTarifas.setBounds(10, 125, 390, 290);

        jPanel3.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/images_botones/fondoInicio.png")))); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Catálogo de Tarifas ..::..");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 270, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Tarifario en minutos de cobros ..::..");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 20, 250, 13);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 410, 40);

        jPanel5.setLayout(null);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setLayout(null);

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
        btnModificar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnModificarKeyPressed(evt);
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
        btnEliminar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminarKeyPressed(evt);
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
        btnSalir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSalirKeyPressed(evt);
            }
        });
        jPanel4.add(btnSalir);
        btnSalir.setBounds(290, 10, 60, 50);

        jPanel5.add(jPanel4);
        jPanel4.setBounds(10, 390, 370, 70);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Convertidor"));
        jPanel2.setLayout(null);

        horas.setText("0");
        horas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                horasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                horasKeyReleased(evt);
            }
        });
        jPanel2.add(horas);
        horas.setBounds(50, 20, 30, 20);

        jLabel1.setText("HORA EQUIVALE A ->");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(90, 20, 120, 14);

        minutos.setEditable(false);
        minutos.setText("0");
        minutos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                minutosKeyPressed(evt);
            }
        });
        jPanel2.add(minutos);
        minutos.setBounds(210, 20, 60, 20);

        jLabel3.setText(" MINUTOS");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(270, 20, 80, 14);

        jPanel5.add(jPanel2);
        jPanel2.setBounds(10, 330, 370, 50);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images_botones/fondoInicio.png"))); // NOI18N
        jLabel2.setText("TIEMPO EN MINUTOS");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 50, 220, 20);

        tarifario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No.", "Desde", "Hasta", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tarifario.setCellSelectionEnabled(true);
        tarifario.setEnabled(false);
        tarifario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tarifarioKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tarifario);
        tarifario.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tarifario.getColumnModel().getColumnCount() > 0) {
            tarifario.getColumnModel().getColumn(0).setMinWidth(0);
            tarifario.getColumnModel().getColumn(0).setPreferredWidth(10);
            tarifario.getColumnModel().getColumn(0).setMaxWidth(25);
        }

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 70, 330, 240);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images_botones/fondoInicio.png"))); // NOI18N
        jLabel5.setText("VALOR $(USD)");
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel5);
        jLabel5.setBounds(230, 50, 110, 20);

        jButton1.setText("Crear");
        jButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(313, 20, 50, 19);

        cmbTipoTarifas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTipoTarifasItemStateChanged(evt);
            }
        });
        jPanel1.add(cmbTipoTarifas);
        cmbTipoTarifas.setBounds(90, 20, 220, 20);

        jLabel16.setText("Tipo Tarifa: ");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(20, 20, 70, 20);

        jPanel5.add(jPanel1);
        jPanel1.setBounds(10, 10, 370, 310);

        jTabbedPane1.addTab("Tarifas x Horas", jPanel5);

        jPanel6.setLayout(null);

        tbTarifas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Cod", "Nombre", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbTarifas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTarifasMouseClicked(evt);
            }
        });
        tbTarifas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTarifasKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbTarifas);
        if (tbTarifas.getColumnModel().getColumnCount() > 0) {
            tbTarifas.getColumnModel().getColumn(0).setPreferredWidth(5);
            tbTarifas.getColumnModel().getColumn(0).setMaxWidth(10);
        }

        jPanel6.add(jScrollPane2);
        jScrollPane2.setBounds(20, 90, 330, 170);

        jLabel4.setText("Codigo:");
        jPanel6.add(jLabel4);
        jLabel4.setBounds(30, 20, 50, 14);

        jLabel6.setText("Nombre:");
        jPanel6.add(jLabel6);
        jLabel6.setBounds(30, 40, 50, 14);

        jLabel7.setText("Valor:");
        jPanel6.add(jLabel7);
        jLabel7.setBounds(30, 60, 40, 14);

        txtCodigo.setEditable(false);
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        jPanel6.add(txtCodigo);
        txtCodigo.setBounds(80, 20, 100, 20);

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
        });
        jPanel6.add(txtNombre);
        txtNombre.setBounds(80, 40, 230, 20);

        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValorKeyPressed(evt);
            }
        });
        jPanel6.add(txtValor);
        txtValor.setBounds(80, 60, 60, 20);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel7.setLayout(null);

        btnAgregar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png"))); // NOI18N
        btnAgregar1.setMnemonic('N');
        btnAgregar1.setText("Nuevo");
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
        jPanel7.add(btnAgregar1);
        btnAgregar1.setBounds(80, 10, 60, 50);

        btnModificar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png"))); // NOI18N
        btnModificar1.setMnemonic('M');
        btnModificar1.setText("Modificar");
        btnModificar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificar1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btnModificar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificar1ActionPerformed(evt);
            }
        });
        btnModificar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnModificar1KeyPressed(evt);
            }
        });
        jPanel7.add(btnModificar1);
        btnModificar1.setBounds(140, 10, 60, 50);

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
        jPanel7.add(btnEliminar1);
        btnEliminar1.setBounds(200, 10, 60, 50);

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
        jPanel7.add(btnSalir1);
        btnSalir1.setBounds(260, 10, 60, 50);

        jPanel6.add(jPanel7);
        jPanel7.setBounds(20, 280, 330, 70);

        jTabbedPane1.addTab("Tarifas Mensuales", jPanel6);

        jPanel8.setLayout(null);

        txtNombre1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre1KeyPressed(evt);
            }
        });
        jPanel8.add(txtNombre1);
        txtNombre1.setBounds(80, 40, 230, 20);

        txtCodigo1.setEditable(false);
        txtCodigo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigo1ActionPerformed(evt);
            }
        });
        jPanel8.add(txtCodigo1);
        txtCodigo1.setBounds(80, 20, 100, 20);

        txtValor1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValor1KeyPressed(evt);
            }
        });
        jPanel8.add(txtValor1);
        txtValor1.setBounds(80, 60, 60, 20);

        tbTarifas1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Cod", "Nombre", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbTarifas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTarifas1MouseClicked(evt);
            }
        });
        tbTarifas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTarifas1KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tbTarifas1);
        if (tbTarifas1.getColumnModel().getColumnCount() > 0) {
            tbTarifas1.getColumnModel().getColumn(0).setPreferredWidth(5);
            tbTarifas1.getColumnModel().getColumn(0).setMaxWidth(10);
        }

        jPanel8.add(jScrollPane3);
        jScrollPane3.setBounds(20, 90, 330, 170);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel9.setLayout(null);

        btnAgregar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png"))); // NOI18N
        btnAgregar2.setMnemonic('N');
        btnAgregar2.setText("Nuevo");
        btnAgregar2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar2.setMargin(new java.awt.Insets(2, 2, 2, 2));
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
        jPanel9.add(btnAgregar2);
        btnAgregar2.setBounds(80, 10, 60, 50);

        btnModificar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png"))); // NOI18N
        btnModificar2.setMnemonic('M');
        btnModificar2.setText("Modificar");
        btnModificar2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificar2.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btnModificar2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificar2ActionPerformed(evt);
            }
        });
        btnModificar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnModificar2KeyPressed(evt);
            }
        });
        jPanel9.add(btnModificar2);
        btnModificar2.setBounds(140, 10, 60, 50);

        btnEliminar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eliminar.png"))); // NOI18N
        btnEliminar2.setMnemonic('E');
        btnEliminar2.setText("Eliminar");
        btnEliminar2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnEliminar2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar2ActionPerformed(evt);
            }
        });
        btnEliminar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminar2KeyPressed(evt);
            }
        });
        jPanel9.add(btnEliminar2);
        btnEliminar2.setBounds(200, 10, 60, 50);

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
        jPanel9.add(btnSalir2);
        btnSalir2.setBounds(260, 10, 60, 50);

        jPanel8.add(jPanel9);
        jPanel9.setBounds(20, 280, 330, 70);

        jLabel9.setText("Codigo:");
        jPanel8.add(jLabel9);
        jLabel9.setBounds(30, 20, 50, 14);

        jLabel11.setText("Nombre:");
        jPanel8.add(jLabel11);
        jLabel11.setBounds(30, 40, 50, 14);

        jLabel12.setText("Valor:");
        jPanel8.add(jLabel12);
        jLabel12.setBounds(30, 60, 40, 14);

        jTabbedPane1.addTab("Tarifa x Día", jPanel8);

        jPanel10.setLayout(null);

        tbTarifas2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Cod", "Nombre", "Valor", "Tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbTarifas2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTarifas2MouseClicked(evt);
            }
        });
        tbTarifas2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTarifas2KeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tbTarifas2);
        if (tbTarifas2.getColumnModel().getColumnCount() > 0) {
            tbTarifas2.getColumnModel().getColumn(0).setPreferredWidth(5);
            tbTarifas2.getColumnModel().getColumn(0).setMaxWidth(10);
        }

        jPanel10.add(jScrollPane4);
        jScrollPane4.setBounds(20, 90, 330, 170);

        jLabel13.setText("Codigo:");
        jPanel10.add(jLabel13);
        jLabel13.setBounds(30, 20, 50, 14);

        jLabel14.setText("Nombre:");
        jPanel10.add(jLabel14);
        jLabel14.setBounds(30, 40, 50, 14);

        jLabel15.setText("Valor:");
        jPanel10.add(jLabel15);
        jLabel15.setBounds(30, 60, 40, 14);

        txtCodigo2.setEditable(false);
        txtCodigo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigo2ActionPerformed(evt);
            }
        });
        jPanel10.add(txtCodigo2);
        txtCodigo2.setBounds(80, 20, 100, 20);

        txtNombre2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre2KeyPressed(evt);
            }
        });
        jPanel10.add(txtNombre2);
        txtNombre2.setBounds(80, 40, 230, 20);

        txtValor2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValor2KeyPressed(evt);
            }
        });
        jPanel10.add(txtValor2);
        txtValor2.setBounds(80, 60, 60, 20);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel11.setLayout(null);

        btnAgregar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png"))); // NOI18N
        btnAgregar3.setMnemonic('N');
        btnAgregar3.setText("Nuevo");
        btnAgregar3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar3.setMargin(new java.awt.Insets(2, 2, 2, 2));
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
        jPanel11.add(btnAgregar3);
        btnAgregar3.setBounds(80, 10, 60, 50);

        btnModificar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png"))); // NOI18N
        btnModificar3.setMnemonic('M');
        btnModificar3.setText("Modificar");
        btnModificar3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificar3.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btnModificar3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificar3ActionPerformed(evt);
            }
        });
        btnModificar3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnModificar3KeyPressed(evt);
            }
        });
        jPanel11.add(btnModificar3);
        btnModificar3.setBounds(140, 10, 60, 50);

        btnEliminar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eliminar.png"))); // NOI18N
        btnEliminar3.setMnemonic('E');
        btnEliminar3.setText("Eliminar");
        btnEliminar3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar3.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnEliminar3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar3ActionPerformed(evt);
            }
        });
        btnEliminar3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminar3KeyPressed(evt);
            }
        });
        jPanel11.add(btnEliminar3);
        btnEliminar3.setBounds(200, 10, 60, 50);

        btnSalir3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salir.png"))); // NOI18N
        btnSalir3.setMnemonic('S');
        btnSalir3.setText("Salir");
        btnSalir3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir3.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnSalir3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir3ActionPerformed(evt);
            }
        });
        btnSalir3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSalir3KeyPressed(evt);
            }
        });
        jPanel11.add(btnSalir3);
        btnSalir3.setBounds(260, 10, 60, 50);

        jPanel10.add(jPanel11);
        jPanel11.setBounds(20, 280, 330, 70);

        esPorcentaje.setText("Es porcentaje %");
        jPanel10.add(esPorcentaje);
        esPorcentaje.setBounds(140, 60, 140, 23);

        jTabbedPane1.addTab("Descuentos", jPanel10);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 50, 400, 500);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tarifarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tarifarioKeyPressed
        // TODO add your handling code here:
        if (!evt.isActionKey()) {
            switch (evt.getKeyCode()) {
                case java.awt.event.KeyEvent.VK_ENTER:
                    evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
                    break;
            }
        }
        int fila = tarifario.getSelectedRow();
        int columna = tarifario.getSelectedColumn();
        if (this.tarifario.getSelectedColumn() >= 0) {
            Object obje = tarifario.getValueAt(fila, columna);
            //tarifario.getCellEditor(fila, columna).isCellEditable(evt);
            if (obje != null) {
                if (tarifario.isCellEditable(fila, columna) && evt.getKeyCode() != evt.VK_UP && evt.getKeyCode() != evt.VK_DOWN && evt.getKeyCode() != evt.VK_LEFT && evt.getKeyCode() != evt.VK_RIGHT && evt.getKeyCode() != evt.VK_ENTER && evt.getKeyCode() != evt.VK_TAB) {
                    tarifario.setValueAt(null, tarifario.getSelectedRow(), columna);
                }
            }
        }
        principal.tecla(evt.getKeyCode());
}//GEN-LAST:event_tarifarioKeyPressed

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

                limpiar();
                btnAgregar.setMnemonic('G');
                btnModificar.setMnemonic('C');
                //            btnBuscar.setEnabled(false);

            } else if (grabar == true) {
                try {
                    //                    try {
                    int filas = contarFilas();
//                    adm.ejecutaSql("Delete from Tarifas ");
                    System.out.println("FILAS: ENCONTRADAS: " + filas);
                    for (int i = 0; i < filas; i++) {
                        Tarifas tar = new Tarifas(null);
                        tar.setCodigo((Integer) tarifario.getValueAt(i, 0));
                        int des = ((Integer) tarifario.getValueAt(i, 1));
                        int has = ((Integer) tarifario.getValueAt(i, 2));

                        BigDecimal val = null;
                        try {
                            val = new BigDecimal((Double) tarifario.getValueAt(i, 3));
                        } catch (Exception e) {
                            val = new BigDecimal(0);
                            System.out.println("ERROR 002: " + e);
                        }

                        tar.setDesde(des);
                        tar.setHasta(has);
                        tar.setValor(val);
                        tar.setTipotarifa((Tipotarifa) cmbTipoTarifas.getSelectedItem());
                        try {
                            if (tar.getCodigo().equals(0)) {
                                tar.setCodigo(adm.getNuevaClave("Tarifas", "codigo"));
                                adm.guardar(tar);
                            } else {
                                adm.actualizar(tar);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("" + e);
                        }
                    }
                    //                    } catch (Exception ex) {
                    //                        JOptionPane.showMessageDialog(this, "Error en actualizar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                    //                        Logger.getLogger(frmTarifas.class.getName()).log(Level.SEVERE, null, ex);
                    //                        return;
                    //                    }
                    cmbTipoTarifas.setEnabled(true);
                } catch (Exception ex) {
                    //Logger.getLogger(frmTarifas.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("" + ex);
                }
                llenarCombo();
                this.btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
                this.btnAgregar.setLabel("Nuevo");
                this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png")));
                this.btnModificar.setLabel("Modificar");
                btnAgregar.setMnemonic('N');
                btnModificar.setMnemonic('M');
                grabar = false;
                modificar = false;
                habilitar(false);
                //                    btnBuscar.setEnabled(true);
                btnAgregar.setEnabled(false);

            }
        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    public int contarFilas() {
        int filas = tarifario.getRowCount();
        int m = 0;

        for (int i = 0; i < filas; i++) {
            try {
                System.out.println(i + " :" + tarifario.getValueAt(i, 0));
                Object obj = (tarifario.getValueAt(i, 0));
                if (obj != null) {
                    m = m + 1;
                }
                System.out.println("" + m);
            } catch (Exception e) {
                System.out.println("ERROR EN CONTAR FILAS: " + e);
                return m;
            }

        }
        return m;

    }
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:

        if (grabar == false && cmbTipoTarifas.getSelectedIndex() > 0) {
            if (principal.permisos.getModificar()) {

                this.btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
                this.btnAgregar.setLabel("Guardar");
                btnAgregar.setEnabled(true);
                this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
                this.btnModificar.setLabel("Cancelar");
                btnAgregar.setMnemonic('G');
                btnModificar.setMnemonic('C');
                modificar = true;
                grabar = true;
                cmbTipoTarifas.setEnabled(false);
                tarifario.setEnabled(true);
                //            btnBuscar.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
            }
        } else {
            cmbTipoTarifas.setEnabled(true);
            grabar = false;
            modificar = false;
            this.btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
            this.btnAgregar.setLabel("Nuevo");
            this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png")));
            this.btnModificar.setLabel("Modificar");
            btnAgregar.setMnemonic('N');
            btnModificar.setMnemonic('M');
            btnAgregar.setEnabled(false);
            tarifario.setEnabled(false);
            //            btnBuscar.setEnabled(true);

        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (principal.permisos.getEliminar()) {        // TODO add your handling code here:
            try {
                adm.eliminarObjeto(Empresa.class, empresaObj.getRuc());
                this.limpiar();
            } catch (Exception ex) {
                Logger.getLogger(frmTarifas.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }
}//GEN-LAST:event_btnEliminarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        this.dispose();
        principal = null;
        empresaObj = null;
        System.gc();
}//GEN-LAST:event_btnSalirActionPerformed

    private void horasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_horasKeyReleased
        // TODO add your handling code here:
        Integer hor = Integer.parseInt(horas.getText());
        Integer min = 60 * hor;
        minutos.setText(min + "");
}//GEN-LAST:event_horasKeyReleased

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void btnAgregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar1ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (principal.permisos.getAgregar()) {
            if (grabar1 == false) {

                this.btnAgregar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
                this.btnAgregar1.setLabel("Guardar");
                this.btnModificar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
                this.btnModificar1.setLabel("Cancelar");
                grabar1 = true;
                modificar1 = false;
                habilitar(true);
                limpiar();

                txtNombre.requestFocusInWindow();
                btnAgregar1.setMnemonic('G');
                btnModificar1.setMnemonic('C');

            } else if (grabar1 == true) {
                if (txtNombre.getText().trim().isEmpty() || txtValor.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Registre los campos requeridos ...!");
                } else {
                    Productos tipotarifaObj = new Productos();

                    tipotarifaObj.setDescripcion(txtNombre.getText());
                    tipotarifaObj.setValor(Double.parseDouble(txtValor.getText()));
                    if (modificar1) {
                        try {
                            tipotarifaObj.setCodigo(Integer.parseInt(txtCodigo.getText()));
                            adm.actualizar(tipotarifaObj);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en actualizar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                    } else {
                        try {
                            tipotarifaObj.setCodigo(null);
                            adm.guardar(tipotarifaObj);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en guardar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                    }
                    this.btnAgregar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
                    this.btnAgregar1.setLabel("Nuevo");
                    this.btnModificar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png")));
                    this.btnModificar1.setLabel("Modificar");
                    btnAgregar1.setMnemonic('N');
                    btnModificar1.setMnemonic('M');
                    grabar1 = false;
                    modificar1 = false;
                    habilitar(false);
                    llenarProductos();

                }

            }
        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }

    }//GEN-LAST:event_btnAgregar1ActionPerformed

    private void btnModificar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificar1ActionPerformed
        // TODO add your handling code here:
        if (grabar1 == false) {
            if (principal.permisos.getModificar()) {

                this.btnAgregar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
                this.btnAgregar1.setLabel("Guardar");
                btnAgregar1.setEnabled(true);
                this.btnModificar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
                this.btnModificar1.setLabel("Cancelar");
                btnAgregar1.setMnemonic('G');
                btnModificar1.setMnemonic('C');
                modificar1 = true;
                grabar1 = true;

                habilitar(true);
                //            btnBuscar.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
            }
        } else {

            grabar1 = false;
            modificar1 = false;
            this.btnAgregar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
            this.btnAgregar1.setLabel("Nuevo");
            this.btnModificar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png")));
            this.btnModificar1.setLabel("Modificar");
            btnAgregar1.setMnemonic('N');
            btnModificar1.setMnemonic('M');
            btnAgregar1.setEnabled(false);

            habilitar(false);
            //            btnBuscar.setEnabled(true);

        }
    }//GEN-LAST:event_btnModificar1ActionPerformed

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
        // TODO add your handling code here:
        if (principal.permisos.getEliminar()) {        // TODO add your handling code here:
            try {
                adm.eliminarObjeto(Productos.class, new Integer(txtCodigo.getText()));
                this.limpiar();
                llenarProductos();

            } catch (Exception ex) {
                Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }
    }//GEN-LAST:event_btnEliminar1ActionPerformed

    private void btnSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        this.dispose();
        principal = null;
        empresaObj = null;
        System.gc();
    }//GEN-LAST:event_btnSalir1ActionPerformed

    private void tbTarifasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTarifasMouseClicked
        // TODO add your handling code here:
        int fila = tbTarifas.getSelectedRow();
        txtCodigo.setText((Integer) tbTarifas.getValueAt(fila, 0) + "");
        txtNombre.setText((String) tbTarifas.getValueAt(fila, 1));
        txtValor.setText((Double) tbTarifas.getValueAt(fila, 2) + "");
    }//GEN-LAST:event_tbTarifasMouseClicked

    private void horasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_horasKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_horasKeyPressed

    private void minutosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_minutosKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_minutosKeyPressed

    private void btnAgregarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregarKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnAgregarKeyPressed

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

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_txtValorKeyPressed

    private void tbTarifasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTarifasKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_tbTarifasKeyPressed

    private void btnAgregar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregar1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnAgregar1KeyPressed

    private void btnModificar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnModificar1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnModificar1KeyPressed

    private void btnEliminar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminar1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnEliminar1KeyPressed

    private void btnSalir1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalir1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnSalir1KeyPressed

    private void txtNombre1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre1KeyPressed

    private void txtCodigo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigo1ActionPerformed

    private void txtValor1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValor1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValor1KeyPressed

    private void tbTarifas1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTarifas1MouseClicked
        // TODO add your handling code here:
        int fila = tbTarifas1.getSelectedRow();
        txtCodigo1.setText((Integer) tbTarifas1.getValueAt(fila, 0) + "");
        txtNombre1.setText((String) tbTarifas1.getValueAt(fila, 1));
        txtValor1.setText((BigDecimal) tbTarifas1.getValueAt(fila, 2) + "");
    }//GEN-LAST:event_tbTarifas1MouseClicked

    private void tbTarifas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTarifas1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbTarifas1KeyPressed

    private void btnAgregar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar2ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (principal.permisos.getAgregar()) {
            if (grabar2 == false) {

                this.btnAgregar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
                this.btnAgregar2.setLabel("Guardar");
                this.btnModificar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
                this.btnModificar2.setLabel("Cancelar");
                grabar2 = true;
                modificar2 = false;
                habilitar1(true);
                limpiar1();

                txtNombre1.requestFocusInWindow();
                btnAgregar2.setMnemonic('G');
                btnModificar2.setMnemonic('C');

            } else if (grabar2 == true) {
                if (txtNombre1.getText().trim().isEmpty() || txtValor1.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Registre los campos requeridos ...!");
                } else {
                    Tarifasdiarias tipotarifaObj = new Tarifasdiarias();

                    tipotarifaObj.setNombre(txtNombre1.getText());
                    tipotarifaObj.setValor(new BigDecimal(txtValor1.getText()));
                    if (modificar2) {
                        try {
                            tipotarifaObj.setCodigo(Integer.parseInt(txtCodigo1.getText()));
                            adm.actualizar(tipotarifaObj);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en actualizar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                    } else {
                        try {
                            tipotarifaObj.setCodigo(null);
                            adm.guardar(tipotarifaObj);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en guardar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                    }
                    this.btnAgregar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
                    this.btnAgregar2.setLabel("Nuevo");
                    this.btnModificar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png")));
                    this.btnModificar2.setLabel("Modificar");
                    btnAgregar2.setMnemonic('N');
                    btnModificar2.setMnemonic('M');
                    grabar2 = false;
                    modificar2 = false;
                    habilitar1(false);
                    llenarProductos1();

                }

            }
        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }
    }//GEN-LAST:event_btnAgregar2ActionPerformed

    private void btnAgregar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregar2KeyPressed

    private void btnModificar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificar2ActionPerformed
        // TODO add your handling code here:
        if (grabar2 == false) {
            if (principal.permisos.getModificar()) {

                this.btnAgregar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
                this.btnAgregar2.setLabel("Guardar");
                btnAgregar2.setEnabled(true);
                this.btnModificar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
                this.btnModificar2.setLabel("Cancelar");
                btnAgregar2.setMnemonic('G');
                btnModificar2.setMnemonic('C');
                modificar2 = true;
                grabar2 = true;

                habilitar1(true);
                //            btnBuscar.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
            }
        } else {

            grabar2 = false;
            modificar2 = false;
            this.btnAgregar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
            this.btnAgregar2.setLabel("Nuevo");
            this.btnModificar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png")));
            this.btnModificar2.setLabel("Modificar");
            btnAgregar2.setMnemonic('N');
            btnModificar2.setMnemonic('M');
            btnAgregar2.setEnabled(false);

            habilitar1(false);
            //            btnBuscar.setEnabled(true);

        }
    }//GEN-LAST:event_btnModificar2ActionPerformed

    private void btnModificar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnModificar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificar2KeyPressed

    private void btnEliminar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar2ActionPerformed
        // TODO add your handling code here:
        if (principal.permisos.getEliminar()) {        // TODO add your handling code here:
            try {
                adm.eliminarObjeto(Tarifasdiarias.class, new Integer(txtCodigo1.getText()));
                this.limpiar1();
                llenarProductos1();

            } catch (Exception ex) {
                Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }
    }//GEN-LAST:event_btnEliminar2ActionPerformed

    private void btnEliminar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar2KeyPressed

    private void btnSalir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        this.dispose();
        principal = null;
        empresaObj = null;
        System.gc();
    }//GEN-LAST:event_btnSalir2ActionPerformed

    private void btnSalir2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalir2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalir2KeyPressed

private void tbTarifas2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTarifas2MouseClicked
// TODO add your handling code here:
    int fila = tbTarifas2.getSelectedRow();
    txtCodigo2.setText((Integer) tbTarifas2.getValueAt(fila, 0) + "");
    txtNombre2.setText((String) tbTarifas2.getValueAt(fila, 1));
    txtValor2.setText((BigDecimal) tbTarifas2.getValueAt(fila, 2) + "");
    esPorcentaje.setSelected(((String) tbTarifas2.getValueAt(fila, 2)).equals("%") ? true : false);
}//GEN-LAST:event_tbTarifas2MouseClicked

private void tbTarifas2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTarifas2KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_tbTarifas2KeyPressed

private void txtCodigo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigo2ActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtCodigo2ActionPerformed

private void txtNombre2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre2KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_txtNombre2KeyPressed

private void txtValor2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValor2KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_txtValor2KeyPressed

private void btnAgregar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar3ActionPerformed
// TODO add your handling code here:
    // TODO add your handling code here:
    // TODO add your handling code here:
    if (principal.permisos.getAgregar()) {
        if (grabar3 == false) {

            this.btnAgregar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
            this.btnAgregar3.setLabel("Guardar");
            this.btnModificar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
            this.btnModificar3.setLabel("Cancelar");
            grabar3 = true;
            modificar3 = false;
            limpiar3();

            txtNombre2.requestFocusInWindow();
            btnAgregar3.setMnemonic('G');
            btnModificar3.setMnemonic('C');

        } else if (grabar3 == true) {
            if (txtNombre2.getText().trim().isEmpty() || txtValor2.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Registre los campos requeridos ...!");
            } else {
                Descuento tipotarifaObj = new Descuento();

                tipotarifaObj.setNombre(txtNombre2.getText());
                tipotarifaObj.setValor(new BigDecimal(txtValor2.getText()));
                tipotarifaObj.setTipo((esPorcentaje.isSelected() ? "1" : "0"));
                if (modificar3) {
                    try {
                        tipotarifaObj.setCodigo(Integer.parseInt(txtCodigo2.getText()));
                        adm.actualizar(tipotarifaObj);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error en actualizar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                } else {
                    try {
                        tipotarifaObj.setCodigo(null);
                        adm.guardar(tipotarifaObj);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error en guardar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                this.btnAgregar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
                this.btnAgregar3.setLabel("Nuevo");
                this.btnModificar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png")));
                this.btnModificar3.setLabel("Modificar");
                btnAgregar3.setMnemonic('N');
                btnModificar3.setMnemonic('M');
                grabar3 = false;
                modificar3 = false;
                habilitar3(false);
                llenarProductos2();

            }

        }
    } else {
        JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
    }
}//GEN-LAST:event_btnAgregar3ActionPerformed

private void btnAgregar3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregar3KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_btnAgregar3KeyPressed

private void btnModificar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificar3ActionPerformed
// TODO add your handling code here:

    if (grabar3 == false) {
        if (principal.permisos.getModificar()) {

            this.btnAgregar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
            this.btnAgregar3.setLabel("Guardar");
            btnAgregar3.setEnabled(true);
            this.btnModificar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
            this.btnModificar3.setLabel("Cancelar");
            btnAgregar3.setMnemonic('G');
            btnModificar3.setMnemonic('C');
            modificar3 = true;
            grabar3 = true;

            habilitar3(true);
            //            btnBuscar.setEnabled(false);

        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }
    } else {

        grabar3 = false;
        modificar3 = false;
        this.btnAgregar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
        this.btnAgregar3.setLabel("Nuevo");
        this.btnModificar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png")));
        this.btnModificar3.setLabel("Modificar");
        btnAgregar3.setMnemonic('N');
        btnModificar3.setMnemonic('M');
        //btnAgregar3.setEnabled(false);

        habilitar3(false);
        //            btnBuscar.setEnabled(true);

    }
}//GEN-LAST:event_btnModificar3ActionPerformed

private void btnModificar3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnModificar3KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_btnModificar3KeyPressed

private void btnEliminar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar3ActionPerformed
// TODO add your handling code here:
    // TODO add your handling code here:
    if (principal.permisos.getEliminar()) {        // TODO add your handling code here:
        try {
            adm.eliminarObjeto(Descuento.class, new Integer(txtCodigo2.getText()));
            this.limpiar3();
            llenarProductos2();

        } catch (Exception ex) {
            Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
        }

    } else {
        JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
    }
}//GEN-LAST:event_btnEliminar3ActionPerformed

private void btnEliminar3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminar3KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_btnEliminar3KeyPressed

private void btnSalir3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir3ActionPerformed
// TODO add your handling code here:
    this.setVisible(false);
    this.dispose();
    principal = null;
    empresaObj = null;
    System.gc();
}//GEN-LAST:event_btnSalir3ActionPerformed

private void btnSalir3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalir3KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_btnSalir3KeyPressed

    private void lunesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lunesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lunesActionPerformed

    private void lunesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lunesKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_lunesKeyPressed

    private void martesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_martesKeyPressed


    }//GEN-LAST:event_martesKeyPressed

    private void miercolesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_miercolesKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_miercolesKeyPressed

    private void juevesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_juevesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_juevesActionPerformed

    private void juevesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_juevesKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_juevesKeyPressed

    private void viernesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_viernesKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_viernesKeyPressed

    private void sabadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sabadoKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_sabadoKeyPressed

    private void domingoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_domingoKeyPressed


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

    private void todosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_todosKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_todosKeyPressed

    public void llenar(Tipotarifa usuario001) {
        txtCodigoTipo.setText(usuario001.getCodigo() + "");
        nombreTipo.setText(usuario001.getNombre());
        lunes.setSelected(usuario001.getLunes());
        martes.setSelected(usuario001.getMartes());
        miercoles.setSelected(usuario001.getMiercoles());
        jueves.setSelected(usuario001.getJueves());
        viernes.setSelected(usuario001.getViernes());
        sabado.setSelected(usuario001.getSabado());
        domingo.setSelected(usuario001.getDomingo());
        SpinnerDateModel sm = new SpinnerDateModel(usuario001.getDesde(), null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner = new JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "HH:mm");
        horaDesde.setModel(sm);
        horaDesde.setEditor(de);

        SpinnerDateModel sm2 = new SpinnerDateModel(usuario001.getHasta(), null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner2 = new JSpinner(sm2);
        JSpinner.DateEditor de2 = new JSpinner.DateEditor(spinner2, "HH:mm");

        horaHasta.setEditor(de2);
        horaHasta.setModel(sm2);
    }

    public void limpiarTipo() {

        txtCodigoTipo.setText("");
        nombreTipo.setText("");

        SpinnerDateModel sm = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner = new JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "HH:mm");

        horaDesde.setModel(sm);
        horaDesde.setEditor(de);

        SpinnerDateModel sm2 = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner2 = new JSpinner(sm2);
        JSpinner.DateEditor de2 = new JSpinner.DateEditor(spinner2, "HH:mm");

        horaHasta.setEditor(de2);
        horaHasta.setModel(sm2);
        lunes.setEnabled(true);
        martes.setEnabled(true);
        miercoles.setEnabled(true);
        jueves.setEnabled(true);
        viernes.setEnabled(true);
        sabado.setEnabled(true);
        domingo.setEnabled(true);

        lunes.setSelected(false);
        martes.setSelected(false);
        miercoles.setSelected(false);
        jueves.setSelected(false);
        viernes.setSelected(false);
        sabado.setSelected(false);
        domingo.setSelected(false);
    }

    public void habilitarTipo(boolean estado) {
        lunes.setEnabled(estado);
        martes.setEnabled(estado);
        miercoles.setEnabled(estado);
        jueves.setEnabled(estado);
        viernes.setEnabled(estado);
        sabado.setEnabled(estado);
        domingo.setEnabled(estado);
        horaDesde.setEnabled(estado);
        horaHasta.setEnabled(estado);
        nombreTipo.setEditable(estado);
    }
    private void btnModificar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificar5ActionPerformed
        // TODO add your handling code here:

        if (grabar5 == false) {
            if (principal.permisos.getModificar()) {
                if (txtCodigoTipo.getText().trim().isEmpty()) {
                    return;
                }
                this.nombreTipo.requestFocusInWindow();
                this.btnAgregar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
                this.btnAgregar5.setLabel("Guardar");
                this.btnModificar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
                this.btnModificar5.setLabel("Cancelar");
                btnAgregar5.setMnemonic('G');
                btnModificar5.setMnemonic('C');
                modificar5 = true;
                grabar5 = true;
                habilitarTipo(true);
                btnBuscar5.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
            }
        } else {

            grabar5 = false;
            modificar5 = false;
            this.btnAgregar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
            this.btnAgregar5.setLabel("Nuevo");
            this.btnModificar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png")));
            this.btnModificar5.setLabel("Modificar");
            btnAgregar5.setMnemonic('N');
            btnModificar5.setMnemonic('M');
            habilitarTipo(false);
            btnBuscar5.setEnabled(true);

        }

    }//GEN-LAST:event_btnModificar5ActionPerformed

    private void btnModificar5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnModificar5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificar5KeyPressed

    public void cargarComboTipo() {

        try {
            cmbTipoTarifas.removeAllItems();
            Tipotarifa user = new Tipotarifa(-1);
            user.setNombre("[SELECCIONE UNA TARIFA]");
            cmbTipoTarifas.addItem(user);
            List<Tipotarifa> us = adm.query("Select o from Tipotarifa as o ");
            for (Iterator<Tipotarifa> it = us.iterator(); it.hasNext();) {
                Tipotarifa usuarios = it.next();
                cmbTipoTarifas.addItem(usuarios);
            }
        } catch (Exception ex) {
            Logger.getLogger(frmReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void btnSalir5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir5ActionPerformed
        // TODO add your handling code here:
        cargarComboTipo();
        frmTipoTarifas.setVisible(false);
    }//GEN-LAST:event_btnSalir5ActionPerformed

    private void btnSalir5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalir5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalir5KeyPressed
    private Tipotarifa tipotarifaObj;

    public Tipotarifa getTipotarifaObj() {
        return tipotarifaObj;
    }

    public void setTipotarifaObj(Tipotarifa tipotarifaObj) {
        this.tipotarifaObj = tipotarifaObj;
    }

    private void btnAgregar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar5ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (principal.permisos.getAgregar()) {
            if (grabar5 == false) {
                tipotarifaObj = new Tipotarifa(0);
                this.btnAgregar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
                this.btnAgregar5.setLabel("Guardar");
                this.btnModificar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
                this.btnModificar5.setLabel("Cancelar");
                grabar5 = true;
                modificar5 = false;
                habilitarTipo(true);
                limpiarTipo();
                llenar(tipotarifaObj);
                nombreTipo.requestFocusInWindow();
                btnAgregar5.setMnemonic('G');
                btnModificar5.setMnemonic('C');
                btnBuscar5.setEnabled(false);

            } else if (grabar5 == true) {
                if (nombreTipo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Registre los campos requeridos ...!");
                } else {
                    tipotarifaObj.setNombre(nombreTipo.getText());

                    tipotarifaObj.setCodigo(Integer.parseInt(txtCodigoTipo.getText()));
                    tipotarifaObj.setDesde((Date) horaDesde.getValue());
                    tipotarifaObj.setHasta((Date) horaHasta.getValue());
                    tipotarifaObj.setDomingo(domingo.isSelected());
                    tipotarifaObj.setLunes(lunes.isSelected());
                    tipotarifaObj.setMartes(martes.isSelected());
                    tipotarifaObj.setMiercoles(miercoles.isSelected());
                    tipotarifaObj.setJueves(jueves.isSelected());
                    tipotarifaObj.setViernes(viernes.isSelected());
                    tipotarifaObj.setSabado(sabado.isSelected());

                    if (tipotarifaObj.getCodigo().equals(0)) {
                        tipotarifaObj.setCodigo(null);
                    }
                    if (modificar5) {
                        try {
                            adm.actualizar(tipotarifaObj);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en actualizar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                    } else {
                        try {
                            adm.guardar(tipotarifaObj);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en guardar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                    }
                    this.btnAgregar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
                    this.btnAgregar5.setLabel("Nuevo");
                    this.btnModificar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png")));
                    this.btnModificar5.setLabel("Modificar");
                    btnAgregar5.setMnemonic('N');
                    btnModificar5.setMnemonic('M');
                    grabar5 = false;
                    modificar5 = false;
                    habilitarTipo(false);
                    btnBuscar5.setEnabled(true);

                }

            }
        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }

    }//GEN-LAST:event_btnAgregar5ActionPerformed

    private void btnAgregar5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregar5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregar5KeyPressed

    private void horaDesdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_horaDesdeKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_horaDesdeKeyPressed

    private void horaHastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_horaHastaKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_horaHastaKeyPressed

    private void btnEliminar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar5ActionPerformed
        // TODO add your handling code here:

        if (principal.permisos.getEliminar()) {        // TODO add your handling code here:
            try {
                adm.eliminarObjeto(Tipotarifa.class, tipotarifaObj.getCodigo());
                this.limpiar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No se puede eliminar el registro este Tipo de Tarifa  "
                        + "\n  contiene datos en  procesos "
                        + "que haya realizado: \n" + ex.getCause().getMessage(), "JCINFORM", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }
    }//GEN-LAST:event_btnEliminar5ActionPerformed

    private void btnEliminar5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminar5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar5KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        SpinnerDateModel sm = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner = new JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "HH:mm");

        horaDesde.setModel(sm);
        horaDesde.setEditor(de);

        SpinnerDateModel sm2 = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner2 = new JSpinner(sm2);
        JSpinner.DateEditor de2 = new JSpinner.DateEditor(spinner2, "HH:mm");

        horaHasta.setEditor(de2);
        horaHasta.setModel(sm2);
        frmTipoTarifas.setVisible(true);


    }//GEN-LAST:event_jButton1ActionPerformed

    private void codigoBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoBuscarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {

            Thread cargar = new Thread() {

                public void run() {
                    principal.procesando.setVisible(true);

                    try {
                        List<Tipotarifa> usuarios = adm.query("Select o from Tipotarifa as o where o.nombre like '" + codigoBuscar.getText().trim() + "%' ");
                        Object[] obj = new Object[4];
                        DefaultTableModel dtm = (DefaultTableModel) busquedaTabla.getModel();
                        dtm.getDataVector().removeAllElements();
                        for (Iterator<Tipotarifa> it = usuarios.iterator(); it.hasNext();) {
                            Tipotarifa glbusuario = it.next();
                            obj[1] = glbusuario.getNombre();
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
                        Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
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
                this.tipotarifaObj = (Tipotarifa) adm.buscarClave((Integer) busquedaTabla.getValueAt(fila, 0), Tipotarifa.class);

                llenar(tipotarifaObj);

                formaUsuarios.dispose();
            } catch (Exception ex) {
                Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //        JOptionPane.showMessageDialog(this, tipotarifaObj);
    }//GEN-LAST:event_busquedaTablaMouseClicked

    private void busquedaTablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaTablaKeyPressed

        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            try {

                int fila = busquedaTabla.getSelectedRow();
                this.tipotarifaObj = (Tipotarifa) adm.buscarClave((Integer) busquedaTabla.getValueAt(fila, 0), Tipotarifa.class);

                llenar(tipotarifaObj);

                //                perfil.setSelectedValue(g,true);
                formaUsuarios.dispose();

            } catch (Exception ex) {
                Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            formaUsuarios.dispose();
        }

    }//GEN-LAST:event_busquedaTablaKeyPressed

    private void btnBuscar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar5ActionPerformed
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
        //                    nombresBuscar.setText("");

    }//GEN-LAST:event_btnBuscar5ActionPerformed

    private void btnBuscar5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBuscar5KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnBuscar5KeyPressed

    private void cmbTipoTarifasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTipoTarifasItemStateChanged
        // TODO add your handling code here:
        try {
            List<Tipotarifa> abc = adm.query("Select o from Tipotarifa as o");
            if (abc.size() <= 0) {
                JOptionPane.showMessageDialog(this, "CREE PRIMERO UN TARIFARIO PARA PODER CREAR LA TARIFA");
                frmTipoTarifas.setVisible(true);
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(frmTarifas.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cmbTipoTarifas.getSelectedIndex() > 0) {
            System.out.println("" + evt.getStateChange());
            System.out.println("" + evt.getItem());

            llenarCombo();
            tarifario.repaint();

        } else {
            DefaultTableModel dtm = (DefaultTableModel) tarifario.getModel();
            dtm.getDataVector().removeAllElements();
            tarifario.setModel(dtm);
            tarifario.repaint();
        }
        tarifario.repaint();
    }//GEN-LAST:event_cmbTipoTarifasItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregar1;
    private javax.swing.JButton btnAgregar2;
    private javax.swing.JButton btnAgregar3;
    private javax.swing.JButton btnAgregar5;
    private javax.swing.JButton btnBuscar5;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JButton btnEliminar2;
    private javax.swing.JButton btnEliminar3;
    private javax.swing.JButton btnEliminar5;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnModificar1;
    private javax.swing.JButton btnModificar2;
    private javax.swing.JButton btnModificar3;
    private javax.swing.JButton btnModificar5;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalir1;
    private javax.swing.JButton btnSalir2;
    private javax.swing.JButton btnSalir3;
    private javax.swing.JButton btnSalir5;
    private javax.swing.JTable busquedaTabla;
    private javax.swing.JComboBox cmbTipoTarifas;
    private javax.swing.JFormattedTextField codigoBuscar;
    private javax.swing.JPanel diasHabiles;
    private javax.swing.JCheckBox domingo;
    private javax.swing.JCheckBox esPorcentaje;
    private javax.swing.JDialog formaUsuarios;
    private javax.swing.JInternalFrame frmTipoTarifas;
    private javax.swing.JSpinner horaDesde;
    private javax.swing.JSpinner horaHasta;
    private javax.swing.JTextField horas;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JCheckBox jueves;
    private javax.swing.JCheckBox lunes;
    private javax.swing.JCheckBox martes;
    private javax.swing.JCheckBox miercoles;
    private javax.swing.JTextField minutos;
    private javax.swing.JFormattedTextField nombreTipo;
    private javax.swing.JCheckBox sabado;
    private javax.swing.JTable tarifario;
    private javax.swing.JTable tbTarifas;
    private javax.swing.JTable tbTarifas1;
    private javax.swing.JTable tbTarifas2;
    private javax.swing.JCheckBox todos;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigo1;
    private javax.swing.JTextField txtCodigo2;
    private javax.swing.JFormattedTextField txtCodigoTipo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtNombre2;
    private javax.swing.JTextField txtValor;
    private javax.swing.JTextField txtValor1;
    private javax.swing.JTextField txtValor2;
    private javax.swing.JCheckBox viernes;
    // End of variables declaration//GEN-END:variables

}
