/*
 * frmRubros.java
 *
 * Created on 1 de noviembre de 2007, 09:10 PM
 */
package bean;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import jcinform.persistencia.Empleados;
import jcinform.persistencia.Institucion;
import jcinform.persistencia.Periodos;
import jcinform.procesos.Administrador;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JRViewer;
import util.WorkingDirectory;

/**
 *
 * @author Francisco
 */
public class frmReportes extends javax.swing.JInternalFrame {

    public boolean grabar = false;
    public boolean modificar = false;
    public Periodos periodoActual;
    public Empleados empleadoActual;
    Administrador adm;
    String separador = File.separator;

    /**
     * Creates new form frmRubros
     */
    public frmReportes() {
        initComponents();
        ancho();

    }

    public frmReportes(Administrador adm1) {

        adm = adm1;
        this.initComponents();
        listar();

//        lblCodigo.setVisible(false);
//        codigoRubro.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cmbTipoReporte = new javax.swing.JComboBox();
        desde = new com.toedter.calendar.JDateChooser();
        hasta = new com.toedter.calendar.JDateChooser();
        btnBuscar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        panelReportes = new javax.swing.JPanel();

        setBackground(new java.awt.Color(236, 246, 255));
        setMaximizable(true);
        setTitle("Creación de Rubros");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rubros.gif"))); // NOI18N

        jPanel3.setOpaque(false);
        jPanel3.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Catálogo de Reportes ..::..");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 270, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Seleccione un reporte y presione ver ..::..");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 20, 250, 13);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        cmbTipoReporte.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        cmbTipoReporte.setMaximumRowCount(12);
        cmbTipoReporte.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[Seleccione]", "# de matriculados por carrera (101)", "# de matriculados sin tomar creditos (102)", "# de matriculados sin tomar creditos con nombres (103)", "# de matriculados tomados creditos impagos (104)", "# becas por carreras (105)", "# ayuda financiera (106)", " " }));
        cmbTipoReporte.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTipoReporteItemStateChanged(evt);
            }
        });
        cmbTipoReporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbTipoReporteKeyPressed(evt);
            }
        });
        jPanel1.add(cmbTipoReporte);
        cmbTipoReporte.setBounds(80, 10, 380, 22);

        desde.setDate(new Date());
        desde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                desdeKeyPressed(evt);
            }
        });
        jPanel1.add(desde);
        desde.setBounds(80, 40, 130, 26);

        hasta.setDate(new Date());
        hasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hastaKeyPressed(evt);
            }
        });
        jPanel1.add(hasta);
        hasta.setBounds(260, 40, 120, 26);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.gif"))); // NOI18N
        btnBuscar.setMnemonic('B');
        btnBuscar.setText("Ver");
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBuscar.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscar);
        btnBuscar.setBounds(460, 10, 60, 50);

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
        jPanel1.add(btnSalir);
        btnSalir.setBounds(520, 10, 60, 50);

        jLabel4.setForeground(new java.awt.Color(0, 0, 153));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Desde:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(10, 40, 60, 14);

        jLabel5.setForeground(new java.awt.Color(0, 0, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Hasta:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(190, 40, 60, 14);

        jLabel6.setForeground(new java.awt.Color(0, 0, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Reporte:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 10, 60, 14);

        panelReportes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelReportes.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelReportes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelReportes, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbTipoReporteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTipoReporteItemStateChanged
        // TODO add your handling code here:

        //106 CIERRE CAJA
        if (cmbTipoReporte.getSelectedItem().toString().contains("103") || cmbTipoReporte.getSelectedItem().toString().contains("104")
                || cmbTipoReporte.getSelectedItem().toString().contains("105") || cmbTipoReporte.getSelectedItem().toString().contains("102")
                || cmbTipoReporte.getSelectedItem().toString().contains("200") || cmbTipoReporte.getSelectedItem().toString().contains("201")
                || cmbTipoReporte.getSelectedItem().toString().contains("202") || cmbTipoReporte.getSelectedItem().toString().contains("203")
                || cmbTipoReporte.getSelectedItem().toString().contains("307")) {
            //               if(principal.getUsuario().getGlobal().getNombre().contains("Administrador")){
            //                     cmbUsuarios.setEnabled(true);
            //                }
        } else if (cmbTipoReporte.getSelectedItem().toString().contains("304") || cmbTipoReporte.getSelectedItem().toString().contains("204")) {
            try {
//                cmbClientes.setEnabled(true);
//                //cmbUsuarios.setEnabled(true);
//                cmbClientes.removeAllItems();
//                Clientes cliE = new Clientes(-1);
//                cliE.setNombres("[TODOS]");
//                cmbClientes.addItem(cliE);
//                List<Clientes> cli = adm.query("Select DISTINCT o.clientes from Tarjetas as o "
//                    + "where o.habilitada = true  order by o.clientes.nombres ");
//                for (Iterator<Clientes> it = cli.iterator(); it.hasNext();) {
//                    Clientes clientes = it.next();
//                    cmbClientes.addItem(clientes);
//                }
            } catch (Exception ex) {
                Logger.getLogger(frmReportes.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_cmbTipoReporteItemStateChanged

    private void cmbTipoReporteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbTipoReporteKeyPressed
        // TODO add your handling code here:
        int cod = evt.getKeyCode();
//        principal.tecla(cod);
    }//GEN-LAST:event_cmbTipoReporteKeyPressed

    private void desdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_desdeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_desdeKeyPressed

    private void hastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hastaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_hastaKeyPressed
    public void verReporteConexion(String dirreporte, String query, String titulo, Map parametros) {
        try {
            System.out.println("QUERY: " + query);
            Connection con = adm.conexion();
            System.out.println(query);
            ResultSet rs = con.createStatement().executeQuery(query);
            JRResultSetDataSource s = new JRResultSetDataSource(rs);
            //JasperReport masterReport = (JasperReport) JRLoader.loadObject(dirreporte);
            JasperPrint masterPrint = JasperFillManager.fillReport(dirreporte, parametros, s);
            JRViewer reporte = new JRViewer(masterPrint); //PARA VER EL REPORTE ANTES DE IMPRIMIR
            panelReportes.removeAll();
            reporte.repaint();
            reporte.setLocation(0, 0);
            reporte.setSize(723, 557);
            reporte.setVisible(true);
            panelReportes.add(reporte);
            panelReportes.repaint();
            this.repaint();
        } catch (Exception ex) {
            Logger.getLogger(frmReportes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String query = "";
        String dirreporte = "";
        Institucion emp = (Institucion) adm.querySimple("Select o from Institucion as o");
        String desde2 = (desde.getDate().getYear() + 1900) + "-" + (desde.getDate().getMonth() + 1) + "-" + (desde.getDate().getDate());
        String hasta2 = (hasta.getDate().getYear() + 1900) + "-" + (hasta.getDate().getMonth() + 1) + "-" + (hasta.getDate().getDate());
          Map parametros = new HashMap();
            parametros.put("empresa", emp.getNombre());
            parametros.put("direccion", emp.getDireccion());
     
             parametros.put("ubicacion", dirreporte);
            Date des = desde.getDate();
            Date has = desde.getDate();

            parametros.put("desde", des);
            parametros.put("hasta", has);
     String titulo = "";
        WorkingDirectory w = new WorkingDirectory();
        //            String query = "";
        String ubicacionDirectorio = w.get() + separador;
        if (ubicacionDirectorio.contains("build")) {
            ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
        }

        if (cmbTipoReporte.getSelectedItem().toString().contains("(101)")) {//# de MATRICULADOS POR CARRERA 
            query = "SELECT COUNT(*) AS TOTAL, carreras.`ID_CARRERAS` AS carreras_ID_CARRERAS, carreras.`NOMBRE` AS carreras_NOMBRE,"
                    + " escuela.`NOMBRE` AS escuela_NOMBRE,  modalidad.`NOMBRE` AS modalidad_NOMBRE,  jornada.`NOMBRE` AS jornada_NOMBRE "
                    + " FROM `carreras` carreras , `matriculas` matriculas, `escuela` escuela, `modalidad` modalidad, `jornada` jornada  "
                    + " WHERE  carreras.`ID_CARRERAS` = matriculas.`ID_CARRERAS`  "
                    + " AND carreras.`ID_ESCUELA` = escuela.`ID_ESCUELA` "
                    + " AND carreras.`ID_MODALIDAD` = modalidad.`ID_MODALIDAD` "
                    + " AND carreras.`ID_JORNADA` = jornada.`ID_JORNADA`  "
                    + " AND matriculas.id_periodos = '"+periodoActual.getIdPeriodos()+"' "
                    + " GROUP BY carreras.`ID_CARRERAS` "; 
            dirreporte = ubicacionDirectorio + "reportes" + separador + "NoMatriculados.jasper";
            titulo = "NÚMERO DE MATRICULADOS POR CARRERA";
                   parametros.put("titulo", titulo);
            verReporteConexion(dirreporte, query, titulo, parametros);
        }else if (cmbTipoReporte.getSelectedItem().toString().contains("(102)")) {//# de MATRICULADOS POR CARRERA 
            query = "SELECT COUNT(*) AS TOTAL, carreras.`ID_CARRERAS` AS carreras_ID_CARRERAS, carreras.`NOMBRE` AS carreras_NOMBRE,"
                    + " escuela.`NOMBRE` AS escuela_NOMBRE,  modalidad.`NOMBRE` AS modalidad_NOMBRE,  jornada.`NOMBRE` AS jornada_NOMBRE "
                    + " FROM `carreras` carreras , `matriculas` matriculas, `escuela` escuela, `modalidad` modalidad, `jornada` jornada  "
                    + " WHERE  carreras.`ID_CARRERAS` = matriculas.`ID_CARRERAS`  "
                    + " AND carreras.`ID_ESCUELA` = escuela.`ID_ESCUELA` "
                    + " AND carreras.`ID_MODALIDAD` = modalidad.`ID_MODALIDAD` "
                    + " AND carreras.`ID_JORNADA` = jornada.`ID_JORNADA`  "
                    + " AND matriculas.id_periodos = '"+periodoActual.getIdPeriodos()+"' "
                    + " AND matriculas.ID_MATRICULAS NOT IN (SELECT ID_MATRICULAS FROM MATERIAS_MATRICULA) "
                    + " GROUP BY carreras.`ID_CARRERAS` "; 
            dirreporte = ubicacionDirectorio + "reportes" + separador + "NoMatriculados.jasper";
            titulo = "NÚMERO DE MATRICULADOS QUE NO HAN TOMADO CRÉDITOS";
                   parametros.put("titulo", titulo);
            verReporteConexion(dirreporte, query, titulo, parametros);
        }else if (cmbTipoReporte.getSelectedItem().toString().contains("(103)")) {//# de MATRICULADOS POR CARRERA 
            query = "SELECT CONCAT(estudiantes.apellido_paterno,' ',estudiantes.apellido_materno, ' ',estudiantes.nombre) ESTUDIANTE, carreras.`ID_CARRERAS` AS carreras_ID_CARRERAS, carreras.`NOMBRE` AS carreras_NOMBRE,"
                    + " escuela.`NOMBRE` AS escuela_NOMBRE,  modalidad.`NOMBRE` AS modalidad_NOMBRE,  jornada.`NOMBRE` AS jornada_NOMBRE "
                    + " FROM `carreras` carreras , `matriculas` matriculas,`estudiantes` estudiantes, `escuela` escuela, `modalidad` modalidad, `jornada` jornada  "
                    + " WHERE  carreras.`ID_CARRERAS` = matriculas.`ID_CARRERAS`  "
                    + "    AND matriculas.id_estudiantes = estudiantes.id_estudiantes "
                    + " AND carreras.`ID_ESCUELA` = escuela.`ID_ESCUELA` "
                    + " AND carreras.`ID_MODALIDAD` = modalidad.`ID_MODALIDAD` "
                    + " AND carreras.`ID_JORNADA` = jornada.`ID_JORNADA`  "
                    + " AND matriculas.id_periodos = '"+periodoActual.getIdPeriodos()+"' "
                    + " AND matriculas.ID_MATRICULAS NOT IN (SELECT ID_MATRICULAS FROM MATERIAS_MATRICULA) "
                    + " ORDER BY carreras.`ID_CARRERAS` "; 
            dirreporte = ubicacionDirectorio + "reportes" + separador + "NoMatriculadosDetallado.jasper";
            titulo = "NÚMERO DE MATRICULADOS QUE NO HAN TOMADO CRÉDITOS DETALLADO";
                   parametros.put("titulo", titulo);
            verReporteConexion(dirreporte, query, titulo, parametros);
        }else if (cmbTipoReporte.getSelectedItem().toString().contains("(104)")) {//# de CREDITOS IMPAGOS 
            query = "SELECT matriculas.id_matriculas, CONCAT(estudiantes.apellido_paterno,' ',estudiantes.apellido_materno, ' ',estudiantes.nombre) ESTUDIANTE, "
                    + " carreras.`ID_CARRERAS` AS carreras_ID_CARRERAS, carreras.`NOMBRE` AS carreras_NOMBRE, "
                    + " escuela.`NOMBRE` AS escuela_NOMBRE,  modalidad.`NOMBRE` AS modalidad_NOMBRE,    "
                    + " jornada.`NOMBRE` AS jornada_NOMBRE FROM "
                    + " `carreras` carreras , `matriculas` matriculas,`estudiantes` estudiantes, `escuela` escuela, `modalidad` modalidad, `jornada` jornada "
                    + " WHERE  carreras.`ID_CARRERAS` = matriculas.`ID_CARRERAS` "
                    + " AND matriculas.id_estudiantes = estudiantes.id_estudiantes  "
                    + " AND carreras.`ID_ESCUELA` = escuela.`ID_ESCUELA` "
                    + " AND carreras.`ID_MODALIDAD` = modalidad.`ID_MODALIDAD` "
                    + " AND carreras.`ID_JORNADA` = jornada.`ID_JORNADA`  "
                    + " AND matriculas.id_periodos = '"+periodoActual.getIdPeriodos()+"' "
                    + " AND matriculas.ID_MATRICULAS  IN (SELECT ID_MATRICULAS FROM MATERIAS_MATRICULA)"
                    + " AND matriculas.id_matriculas NOT IN (SELECT id_matriculas FROM `facturas` facturas, `detalles` detalles WHERE facturas.id_facturas = detalles.id_facturas  "
                    + " AND detalles.id_rubros IN (SELECT id_rubros FROM rubros WHERE eselcredito = TRUE)) "
                    + " ORDER BY carreras.`ID_CARRERAS`"; 
            dirreporte = ubicacionDirectorio + "reportes" + separador + "NoMatriculadosDetallado.jasper";
            titulo = "ESTUDIANTES CON CRÉDITOS IMPAGOS";
                   parametros.put("titulo", titulo);
            verReporteConexion(dirreporte, query, titulo, parametros);
        }
        
        
        
//        principal.contenedor.requestFocus();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
//        principal.contenedor.requestFocus();
        this.setVisible(false);
//        principal = null;
//        empresaObj = null;
//        System.gc();
    }//GEN-LAST:event_btnSalirActionPerformed

    public void listar() {
//
//        List lista = adm.query("Select o from Rubros as o ");
//        DefaultTableModel dtm = (DefaultTableModel) tableRubros.getModel();
//        dtm.getDataVector().removeAllElements();
//        for (Iterator it = lista.iterator(); it.hasNext();) {
//            Rubros elem = (Rubros) it.next();
//            Object obj[] = new Object[7];
//            obj[0] = elem.getIdRubros();
//            obj[1] = elem.getNombre();
//            obj[2] = elem.getValor();
//            obj[3] = elem.getEselcredito();
//            obj[4] = elem.getCodigoContable();
//            obj[5] = elem.getUnidadContable();
//            dtm.addRow(obj);
//        }
//        tableRubros.setModel(dtm);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cmbTipoReporte;
    private com.toedter.calendar.JDateChooser desde;
    private com.toedter.calendar.JDateChooser hasta;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel panelReportes;
    // End of variables declaration//GEN-END:variables

    public void ancho() {
//        TableColumn column;
//        for (int i = 0; i < 3; i++) {
//            column = this.tableRubros.getColumnModel().getColumn(i);
//            if (i == 0) {
//                column.setPreferredWidth(50);
//            }
//            if (i == 1) {
//                column.setPreferredWidth(350);
//            }
//            if (i == 2) {
//                column.setPreferredWidth(100);
//            }
//
//
//
//        }
    }

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
