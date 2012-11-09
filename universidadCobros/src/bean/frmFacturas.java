/*
 * frmRubros.java
 *
 * Created on 1 de noviembre de 2007, 09:10 PM
 */
package bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.table.TableColumn;

import javax.swing.table.DefaultTableModel;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Facturas;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Parientes;
import jcinform.persistencia.Periodos;
import jcinform.persistencia.RubrosMatriculaPeriodo;
import jcinform.procesos.Administrador;
import util.general;

/**
 *
 * @author Francisco
 */
public class frmFacturas extends javax.swing.JInternalFrame {

    public boolean grabar = false;
    public boolean modificar = false;
    Administrador adm;
    public Periodos periodoActual;
    public Empleados empleadoActual;

    /**
     * Creates new form frmRubros
     */
    public frmFacturas() {
        initComponents();
 
    }

    public frmFacturas(Administrador adm1) {

        adm = adm1;
        this.initComponents();
  
        frmActualizar.setVisible(false);
        panelencontrados1.setVisible(false);
        codigoPariente.setVisible(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frmActualizar = new javax.swing.JPanel();
        ruc1 = new javax.swing.JFormattedTextField();
        nombre1 = new javax.swing.JFormattedTextField();
        direccion1 = new javax.swing.JFormattedTextField();
        telefono1 = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnGuardarCerra = new javax.swing.JButton();
        panelencontrados1 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        encontrados1 = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        tFactura = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        telefono = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        editarDatos = new javax.swing.JButton();
        buscarApellido = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        ruc = new javax.swing.JLabel();
        nombre = new javax.swing.JLabel();
        direccion = new javax.swing.JLabel();
        codigoPariente = new javax.swing.JFormattedTextField();
        total = new javax.swing.JLabel();
        total1 = new javax.swing.JLabel();
        total2 = new javax.swing.JLabel();
        total3 = new javax.swing.JLabel();
        total4 = new javax.swing.JLabel();
        total5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(236, 246, 255));
        setTitle("Rubros por matrícula");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rubros.gif"))); // NOI18N
        getContentPane().setLayout(null);

        frmActualizar.setLayout(null);

        ruc1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        ruc1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ruc1KeyPressed(evt);
            }
        });
        frmActualizar.add(ruc1);
        ruc1.setBounds(79, 10, 110, 20);

        nombre1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        nombre1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombre1KeyPressed(evt);
            }
        });
        frmActualizar.add(nombre1);
        nombre1.setBounds(79, 30, 220, 20);

        direccion1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        direccion1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccion1KeyPressed(evt);
            }
        });
        frmActualizar.add(direccion1);
        direccion1.setBounds(79, 50, 220, 20);

        telefono1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        telefono1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefono1KeyPressed(evt);
            }
        });
        frmActualizar.add(telefono1);
        telefono1.setBounds(79, 70, 170, 20);

        jLabel11.setText("RUC: ");
        frmActualizar.add(jLabel11);
        jLabel11.setBounds(10, 10, 80, 14);

        jLabel12.setText("NOMBRES: ");
        frmActualizar.add(jLabel12);
        jLabel12.setBounds(10, 30, 80, 14);

        jLabel13.setText("DIRECCIÓN:");
        frmActualizar.add(jLabel13);
        jLabel13.setBounds(10, 50, 80, 14);

        jLabel14.setText("TELÉFONO:");
        frmActualizar.add(jLabel14);
        jLabel14.setBounds(10, 70, 80, 14);

        btnGuardarCerra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png"))); // NOI18N
        btnGuardarCerra.setText("Guardar y Cerrar");
        btnGuardarCerra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCerraActionPerformed(evt);
            }
        });
        frmActualizar.add(btnGuardarCerra);
        btnGuardarCerra.setBounds(80, 90, 170, 23);

        getContentPane().add(frmActualizar);
        frmActualizar.setBounds(10, 70, 310, 120);

        panelencontrados1.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.light"));
        panelencontrados1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelencontrados1.setLayout(null);

        encontrados1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Estudiantes..." };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        encontrados1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        encontrados1.setAlignmentX(0.2F);
        encontrados1.setVisibleRowCount(10);
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
        jScrollPane8.setViewportView(encontrados1);

        panelencontrados1.add(jScrollPane8);
        jScrollPane8.setBounds(10, 10, 260, 170);

        getContentPane().add(panelencontrados1);
        panelencontrados1.setBounds(80, 70, 280, 190);

        tFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Nombre", "Cantidad", "V.Nuevo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
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
        tFactura.setSelectionBackground(new java.awt.Color(236, 246, 255));
        tFactura.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tFactura.setShowHorizontalLines(false);
        tFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tFacturaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tFactura);
        tFactura.getColumnModel().getColumn(0).setResizable(false);
        tFactura.getColumnModel().getColumn(0).setPreferredWidth(0);
        tFactura.getColumnModel().getColumn(1).setResizable(false);
        tFactura.getColumnModel().getColumn(1).setPreferredWidth(200);
        tFactura.getColumnModel().getColumn(2).setResizable(false);
        tFactura.getColumnModel().getColumn(2).setPreferredWidth(0);
        tFactura.getColumnModel().getColumn(3).setResizable(false);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 180, 580, 140);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/new.gif"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo);
        btnNuevo.setBounds(10, 10, 105, 30);

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/modificar.gif"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificar);
        btnModificar.setBounds(120, 10, 110, 30);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.gif"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir);
        btnSalir.setBounds(350, 10, 105, 30);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancel.gif"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar);
        btnEliminar.setBounds(240, 10, 105, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(130, 420, 480, 50);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Crear, Actualizar, Modificar Rubros x Matricula .......");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(20, 20, 290, 13);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Rubros que se cobrarán en Matricula ..::..");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(20, 0, 270, 15);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fondoInicio.jpg"))); // NOI18N
        getContentPane().add(jLabel7);
        jLabel7.setBounds(0, 0, 680, 40);

        jDateChooser1.setDate(new Date());
        getContentPane().add(jDateChooser1);
        jDateChooser1.setBounds(455, 70, 140, 30);

        jLabel1.setText("Fecha: ");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(406, 70, 50, 30);

        jLabel2.setText("Búsqueda: ");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 50, 70, 14);

        telefono.setText(".");
        getContentPane().add(telefono);
        telefono.setBounds(100, 140, 230, 14);

        jLabel4.setText("TELÉFONO:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 140, 80, 14);

        jLabel5.setText("NOMBRES: ");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(20, 100, 80, 14);

        jLabel6.setText("DIRECCIÓN:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(20, 120, 80, 14);

        editarDatos.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        editarDatos.setText("EDITAR");
        editarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarDatosActionPerformed(evt);
            }
        });
        getContentPane().add(editarDatos);
        editarDatos.setBounds(90, 160, 80, 23);

        buscarApellido.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        buscarApellido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                buscarApellidoFocusGained(evt);
            }
        });
        buscarApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buscarApellidoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarApellidoKeyReleased(evt);
            }
        });
        getContentPane().add(buscarApellido);
        buscarApellido.setBounds(80, 50, 280, 21);

        jLabel9.setText("RUC: ");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(20, 80, 80, 14);

        ruc.setText(".");
        getContentPane().add(ruc);
        ruc.setBounds(100, 80, 140, 14);

        nombre.setText(".");
        getContentPane().add(nombre);
        nombre.setBounds(100, 100, 230, 14);

        direccion.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        direccion.setText(". ");
        getContentPane().add(direccion);
        direccion.setBounds(100, 120, 320, 13);
        getContentPane().add(codigoPariente);
        codigoPariente.setBounds(20, 160, 60, 20);

        total.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        total.setForeground(new java.awt.Color(102, 102, 102));
        total.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        total.setText("00.00");
        getContentPane().add(total);
        total.setBounds(440, 320, 160, 30);

        total1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        total1.setForeground(new java.awt.Color(51, 51, 51));
        total1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        total1.setText("TOTAL:");
        getContentPane().add(total1);
        total1.setBounds(290, 380, 150, 30);

        total2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        total2.setForeground(new java.awt.Color(102, 102, 102));
        total2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        total2.setText("00.00");
        getContentPane().add(total2);
        total2.setBounds(440, 350, 160, 30);

        total3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        total3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        total3.setText("00.00");
        getContentPane().add(total3);
        total3.setBounds(440, 380, 160, 30);

        total4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        total4.setForeground(new java.awt.Color(51, 51, 51));
        total4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        total4.setText("Subtotal:");
        getContentPane().add(total4);
        total4.setBounds(290, 320, 150, 30);

        total5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        total5.setForeground(new java.awt.Color(51, 51, 51));
        total5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        total5.setText("IVA:");
        getContentPane().add(total5);
        total5.setBounds(290, 350, 150, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
// TODO add your handling code here:
        if (grabar == true) {
            grabar = false;
            modificar = false;

            this.btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
            this.btnNuevo.setLabel("Nuevo");
            this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/modificar.gif")));
            this.btnModificar.setLabel("Modificar");
//            this.txtNombre.setEditable(false);
        }
        this.setVisible(false);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
// TODO add your handling code here:

        if (grabar == false) {
//            this.txtNombre.setEditable(true);
//            this.unidadContable.setEditable(true);
//            this.codigoContable.setEditable(true);


//            this.cmbCarreras.setEnabled(true);


            this.btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
            this.btnNuevo.setLabel("Guardar");
            this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
            this.btnModificar.setLabel("Cancelar");

            grabar = true;
            modificar = false;
        } else if (grabar == true) {

            RubrosMatriculaPeriodo rub = new RubrosMatriculaPeriodo();

            rub.setIdPeriodos(periodoActual);

//            if (codigoRubro.getText().isEmpty()) {
//                rub.setIdRubrosMatriculaPeriodo(adm.getNuevaClave("RubrosMatriculaPeriodo", "idRubrosMatriculaPeriodo"));
//                adm.guardar(rub);
//            } else {
//                rub.setIdRubrosMatriculaPeriodo(new Integer(codigoRubro.getText()));
//                adm.actualizar(rub);
//            }
//

            this.btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/new.gif")));
            this.btnNuevo.setLabel("Nuevo");
            this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/modificar.gif")));
            this.btnModificar.setLabel("Modificar");
            grabar = false;
            modificar = false;
//            this.txtNombre.setEditable(false);
//            this.cmbCarreras.setEnabled(false);


        }

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
// TODO add your handling code here:
        if (grabar == false) {
//            if (codigoRubro.getText().isEmpty()) {
//                return;
//            }

            this.btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
            this.btnNuevo.setLabel("Guardar");
            this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
            this.btnModificar.setLabel("Cancelar");
            modificar = true;
            grabar = true;
        } else {
            grabar = false;
            modificar = false;

            this.btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar.png")));
            this.btnNuevo.setLabel("Nuevo");
            this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/modificar.gif")));
            this.btnModificar.setLabel("Modificar");

        }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void tFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tFacturaMouseClicked
// TODO add your handling code here:
//        int fila = tableRubros.getSelectedRow();

        //boolean val = new Boolean(tableRubros.getValueAt(fila, 3).toString());


//        this.chkEscredito.setSelected(Boolean.valueOf(val));
//        this.codigoContable.setText((String) this.tableRubros.getValueAt(fila, 4));
//        this.unidadContable.setText((String) this.tableRubros.getValueAt(fila, 5));

        //boolean val2 = new Boolean(.toString());
//        this.chkEstado.setSelected((Boolean)tableRubros.getValueAt(fila,3));
    }//GEN-LAST:event_tFacturaMouseClicked
    private void sumar(){
        int filas = tFactura.getRowCount();
        BigDecimal totalCalc = new BigDecimal(0);
        for (int i = 0; i < filas; i++) {
            totalCalc = totalCalc.add((BigDecimal)tFactura.getValueAt(i, 3));
        }
        this.total.setText(totalCalc+"");
        
    }
    private void cargarRubros(general gen) {
        Estudiantes es = (Estudiantes) adm.buscarClave(gen.getCodigoString(), Estudiantes.class);
        Parientes factura = new Parientes();
        if (es.getIdParientes().getTipoRepresentante().equals("F")) {
            factura = es.getIdParientes();
        } else if (es.getParIdParientes().getTipoRepresentante().equals("F")) {
            factura = es.getParIdParientes();
        } else if (es.getParIdParientes2().getTipoRepresentante().equals("F")) {
            factura = es.getParIdParientes2();
        }
        codigoPariente.setText(factura.getIdParientes() + "");
        ruc.setText(factura.getIdentificacion());
        nombre.setText(factura.getNombres());
        direccion.setText(factura.getDireccion());
        telefono.setText(factura.getTelefonoTrabajo());
        ruc1.setText(factura.getIdentificacion());
        nombre1.setText(factura.getNombres());
        direccion1.setText(factura.getDireccion());
        telefono1.setText(factura.getTelefonoTrabajo());

        List<Matriculas> matriculaList = adm.query("Select o from Matriculas as o where o.idEstudiantes.idEstudiantes = '" + es.getIdEstudiantes() + "'  ");
        if (matriculaList.size() > 0) {
            Matriculas actual = matriculaList.get(0);
            //VERIFICO SI ES QUE HA PAGADO UNO O VARIOS DE ESTOS RUBROS PARA PROCEDER A CAMBIARLE EL ESTADO A LA MATRICULA Y NO PAGUE 
//            List<Facturas> facturaLista = adm.query("Select o from Detalles");

            //tengo que verificar si el estado esta null o false y le cargo los rubros que se encuetnra en matricula
            //de acuerdo a la carrera y al perido actual buscando en rbrosMatriculasPeriodo
            if (actual.getPagada() == null) {
                actual.setPagada(false);
            }
            //no ha estado pagada
            if (actual.getPagada() == false) {
                //AQUÍ HE BUSCADO LOS RUBROS QUE ESTÁN ASIGNADOS PARA LA MATRICULA
                List<RubrosMatriculaPeriodo> rubros = adm.query("Select o from RubrosMatriculaPeriodo as o "
                        + " where o.idPeriodos.idPeriodos = '" + actual.getIdPeriodos().getIdPeriodos() + "' and "
                        + "o.idCarreras.idCarreras = '" + actual.getIdCarreras().getIdCarreras() + "' ");
               
                   DefaultTableModel dtm = (DefaultTableModel) this.tFactura.getModel();
                   dtm.getDataVector().removeAllElements();
                   for (Iterator<RubrosMatriculaPeriodo> it = rubros.iterator(); it.hasNext();) {
                    RubrosMatriculaPeriodo elem = it.next();
                    
                    Object[] obj = new Object[20];
                    obj[0] = elem.getIdRubros().getIdRubros();
                    obj[1] = elem.getIdRubros().getNombre();
                    obj[2] = 1;
                    obj[3] = elem.getValorNueva();
                    dtm.addRow(obj);


                }
                   tFactura.setModel(dtm);
                   sumar();
            }
        }


    }
    private void encontrados1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_encontrados1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.panelencontrados1.setVisible(false);
            general fac = (general) this.encontrados1.getSelectedValue();
            cargarRubros(fac);
            buscarApellido.setText("");
        }
    }//GEN-LAST:event_encontrados1MouseClicked

    private void encontrados1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_encontrados1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            this.panelencontrados1.setVisible(false);
            general fac = (general) this.encontrados1.getSelectedValue();
            cargarRubros(fac);
            buscarApellido.setText("");
            
        } else if (evt.getKeyCode() == evt.VK_UP && encontrados1.getSelectedIndex() == 0) {
            this.buscarApellido.requestFocusInWindow();
        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.panelencontrados1.setVisible(false);

        } else {
            //principal.tecla(evt.getKeyCode());
        }
    }//GEN-LAST:event_encontrados1KeyPressed

    private void buscarApellidoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_buscarApellidoFocusGained
        // TODO add your handling code here:
        buscarApellido.selectAll();
    }//GEN-LAST:event_buscarApellidoFocusGained

    private void buscarApellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarApellidoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_DOWN) {
            encontrados1.setSelectedIndex(0);
            encontrados1.requestFocusInWindow();
        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            panelencontrados1.setVisible(false);
        } else {
            //      principal.tecla(evt.getKeyCode());
        }
    }//GEN-LAST:event_buscarApellidoKeyPressed

    private void buscarApellidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarApellidoKeyReleased
        // TODO add your handling code here:
        if (!buscarApellido.getText().isEmpty()) {
            List lista = adm.query("Select o.idEstudiantes, o.apellidoPaterno, o.apellidoMaterno, o.nombre from Estudiantes as o "
                    + "where o.apellidoPaterno like '" + buscarApellido.getText() + "%' order by o.apellidoPaterno ", 0, 10);
            if (lista.size() > 0) {
                DefaultListModel dtm = new DefaultListModel();
                dtm.removeAllElements();
                encontrados1.setModel(dtm);
                int j = 0;
                for (Iterator it = lista.iterator(); it.hasNext();) {
                    Object[] elem = (Object[]) it.next();
                    Estudiantes est = new Estudiantes(elem[0] + "");
                    est.setApellidoPaterno(elem[1] + "");
                    est.setApellidoMaterno(elem[2] + "");
                    est.setNombre(elem[3] + "");
                    general gen = new general(est.getIdEstudiantes(), est.getApellidoPaterno() + " " + est.getApellidoMaterno() + " " + est.getNombre());
                    dtm.add(j, gen);
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
    }//GEN-LAST:event_buscarApellidoKeyReleased

    private void btnGuardarCerraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCerraActionPerformed
        // TODO add your handling code here:
        Parientes par = new Parientes(new Integer(codigoPariente.getText()));
        par.setIdentificacion(ruc1.getText());
        par.setNombres(nombre1.getText());
        par.setDireccion(direccion1.getText());
        par.setTelefonoTrabajo(telefono1.getText());
        par.setTipoRepresentante("F");
        adm.actualizar(par);
        adm.actualizar(par);
        ruc.setText(ruc1.getText());
        nombre.setText(nombre1.getText());
        direccion.setText(direccion1.getText());
        telefono.setText(telefono1.getText());


        frmActualizar.setVisible(false);
    }//GEN-LAST:event_btnGuardarCerraActionPerformed

    private void editarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarDatosActionPerformed
        // TODO add your handling code here:
       
        frmActualizar.setVisible(true);
        ruc1.requestFocusInWindow();
    }//GEN-LAST:event_editarDatosActionPerformed

    private void ruc1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ruc1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()== evt.VK_ENTER){
            nombre1.requestFocusInWindow();
        }
    }//GEN-LAST:event_ruc1KeyPressed

    private void nombre1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombre1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()== evt.VK_ENTER){
            direccion1.requestFocusInWindow();
        }
    }//GEN-LAST:event_nombre1KeyPressed

    private void direccion1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccion1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()== evt.VK_ENTER){
            telefono1.requestFocusInWindow();
        }
    }//GEN-LAST:event_direccion1KeyPressed

    private void telefono1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefono1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()== evt.VK_ENTER){
            btnGuardarCerra.requestFocusInWindow();
        }
    }//GEN-LAST:event_telefono1KeyPressed
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardarCerra;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JFormattedTextField buscarApellido;
    private javax.swing.JFormattedTextField codigoPariente;
    private javax.swing.JLabel direccion;
    private javax.swing.JFormattedTextField direccion1;
    private javax.swing.JButton editarDatos;
    private javax.swing.JList encontrados1;
    private javax.swing.JPanel frmActualizar;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel nombre;
    private javax.swing.JFormattedTextField nombre1;
    private javax.swing.JPanel panelencontrados1;
    private javax.swing.JLabel ruc;
    private javax.swing.JFormattedTextField ruc1;
    private javax.swing.JTable tFactura;
    private javax.swing.JLabel telefono;
    private javax.swing.JFormattedTextField telefono1;
    private javax.swing.JLabel total;
    private javax.swing.JLabel total1;
    private javax.swing.JLabel total2;
    private javax.swing.JLabel total3;
    private javax.swing.JLabel total4;
    private javax.swing.JLabel total5;
    // End of variables declaration//GEN-END:variables

 

    public Periodos getPeriodoActual() {
        return periodoActual;
    }

    public void setPeriodoActual(Periodos periodoActual) {
        this.periodoActual = periodoActual;
    }

    public Empleados getEmpleadoActual() {
        return empleadoActual;
    }

    public void setEmpleadoActual(Empleados empleadoActual) {
        this.empleadoActual = empleadoActual;
    }
}
