package peaje.formas;

import java.awt.Container;
import java.awt.print.PrinterJob;
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
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JRViewer;
import hibernate.cargar.Administrador;
import hibernate.cargar.validaciones;
import hibernate.*;
import hibernate.cargar.WorkingDirectory;
import java.io.File;
import java.math.BigDecimal;
import java.util.Vector;
import sources.ClientesSource;
import sources.ConsolidadoSource;
import sources.FacturaSource;
import sources.General;
//import org.eclipse.persistence.internal.history.HistoricalDatabaseTable;

/**
 *
 * @author geovanny
 */
public class frmReportes extends javax.swing.JInternalFrame {

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
 String separador = File.separatorChar+"";
    /** Creates new form frmProfesores */
    public frmReportes(frmPrincipal lo, Administrador adm1) {
        this.principal = lo;
//          super(parent,modal);
        adm = adm1;
        initComponents();
        this.setSize(615, 508);
        empresaObj = new Empresa();
        val = new validaciones();
         Date desde1 = new Date();
            Date hasta1 = new Date();
            desde1.setHours(06);
            desde1.setMinutes(00);
            desde1.setSeconds(00);
            hasta1.setHours(23);
            hasta1.setMinutes(59);
            hasta1.setSeconds(59);
            desdehora2.setDate(desde1);
            hastahora2.setDate(hasta1);
          llenarCombo();
    }
//
//    public frmReportes(java.awt.Frame parent, boolean modal,frmPrincipal lo) {
//        super(parent,modal);
//        this.desktopContenedor = lo.contenedor;
//        llenarCombo();
//        initComponents();
//        this.setSize(615, 508);
//        empresaObj = new Empresa();
//        adm = new Administrador();
//        val = new validaciones();
//        frmPrincipal = lo;
//    }

    public void llenarCombo() {
        try {
            cmbUsuarios.removeAllItems();
            Usuarios user = new Usuarios(-1);
            user.setNombres("[TODOS]");
            cmbUsuarios.addItem(user);
             List<Usuarios> us = adm.query("Select o from Usuarios as o ");
             for (Iterator<Usuarios> it = us.iterator(); it.hasNext();) {
                    Usuarios usuarios = it.next();
                    cmbUsuarios.addItem(usuarios);
                    if(usuarios.getCodigo().equals(principal.usuarioActual.getCodigo())){
                        if(usuarios.getGlobal().getCodigo()>100)
                            cmbUsuarios.setSelectedItem(usuarios);
                    }
            }
//            perfilesList = new ArrayList<Global>();
//            perfilesList = adm.query("Select o from Global as o where o.grupo = 'PER'");
        } catch (Exception ex) {
            Logger.getLogger(frmReportes.class.getName()).log(Level.SEVERE, null, ex);
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
    // </editor-fold >
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbTipoReporte = new javax.swing.JComboBox();
        desde = new com.toedter.calendar.JDateChooser();
        hasta = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        hastahora2 = new com.toedter.calendar.JDateChooser();
        desdehora2 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbClientes = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        cmbUsuarios = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        panelReportes = new javax.swing.JPanel();

        setTitle("Reportes");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/resultados.png"))); // NOI18N
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jPanel3.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/images_botones/fondoInicio.png")))); // NOI18N
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

        jLabel1.setForeground(new java.awt.Color(0, 0, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("HORA:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(190, 60, 60, 14);

        cmbTipoReporte.setMaximumRowCount(12);
        cmbTipoReporte.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tickets por cobrar  (101)", "Tickets cobrados (102)", "Tickets Anulados (103)", "Tickets Tarifa 0 (104)", "Fotos de Vehiculos (105)", "CIERRE DE CAJA (106)", "Facturas Tickest y Tarjetas (200)", "Facturas de Tickets (201)", "Facturas de Tarjetas (202)", "Consolidado por Mes (300)", "Consolidado x fechas (301)", "Clientes mas frecuentes (302)", "Listado clientes (303)", "No. de Ingresos x Cliente (304)", "Puestos ocupados (305) ", "Tarjetas Ocupadas(Dentro del Parqu.) (304) " }));
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
        cmbTipoReporte.setBounds(80, 10, 300, 20);

        desde.setDate(new Date());
        desde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                desdeKeyPressed(evt);
            }
        });
        jPanel1.add(desde);
        desde.setBounds(80, 40, 130, 20);

        hasta.setDate(new Date());
        hasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hastaKeyPressed(evt);
            }
        });
        jPanel1.add(hasta);
        hasta.setBounds(260, 40, 120, 20);

        jLabel2.setForeground(new java.awt.Color(0, 0, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Usuarios:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(380, 10, 60, 14);

        jLabel3.setForeground(new java.awt.Color(0, 0, 153));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("HORA:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 60, 60, 14);

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
        btnBuscar.setBounds(410, 50, 60, 50);

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
        btnSalir.setBounds(470, 50, 60, 50);

        hastahora2.setDateFormatString("HH:mm:ss");
        hastahora2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hastahora2KeyPressed(evt);
            }
        });
        jPanel1.add(hastahora2);
        hastahora2.setBounds(260, 60, 100, 20);

        desdehora2.setDateFormatString("HH:mm:ss");
        desdehora2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                desdehora2KeyPressed(evt);
            }
        });
        jPanel1.add(desdehora2);
        desdehora2.setBounds(80, 60, 100, 20);

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

        jPanel1.add(cmbClientes);
        cmbClientes.setBounds(80, 80, 300, 20);

        jLabel6.setForeground(new java.awt.Color(0, 0, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Reporte:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 10, 60, 14);

        cmbUsuarios.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[TODOS]" }));
        jPanel1.add(cmbUsuarios);
        cmbUsuarios.setBounds(446, 10, 230, 20);

        jLabel7.setForeground(new java.awt.Color(0, 0, 153));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Clientes:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 80, 60, 14);

        panelReportes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelReportes.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelReportes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelReportes, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.setVisible(false);
        }
    }//GEN-LAST:event_formKeyReleased

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
                   principal.contenedor.requestFocus();
        this.setVisible(false);
        principal = null;
        empresaObj = null;
        System.gc();
}//GEN-LAST:event_btnSalirActionPerformed
 public void noingresos(String dirreporte, String query, String titulo) {
        try {
            System.out.println("QUERY: "+query);
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(dirreporte);
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");

            List<Factura> fac = adm.queryNativo(query,Factura.class);
            ArrayList detalle = new ArrayList();
            for (Iterator<Factura> it = fac.iterator(); it.hasNext();) {
                Factura factura = it.next();
//                if (cmbTipoReporte.getSelectedIndex() > 0) {
//                    factura.setFecha(factura.getFechafin());
//                }
                detalle.add(factura);
            }
            FacturaSource ds = new FacturaSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazon());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefonos());
            parametros.put("titulo", titulo);
            parametros.put("parqueaderos", emp.getParqueaderos());
            parametros.put("desde",desde.getDate());
            parametros.put("hasta",hasta.getDate());

            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);
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
            Logger.getLogger(frmTicket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void consolidadoxfecha(String dirreporte, String query, String titulo) {
        try {
            System.out.println("QUERY: "+query);
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(dirreporte);
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");

            List fac = adm.queryNativo(query);
            ArrayList detalle = new ArrayList();
            for (Iterator it = fac.iterator(); it.hasNext();) {
                Vector faci = (Vector)it.next();
                General gen = new General();
                gen.setDato1(faci.get(0)+"");  
                gen.setNumero1(new Integer(faci.get(2).toString()));  
                gen.setValor1(new BigDecimal(faci.get(1).toString()));  
                gen.setValor2(new BigDecimal(faci.get(3).toString()));  
                detalle.add(gen);
            }
            ConsolidadoSource  ds = new ConsolidadoSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazon());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefonos());
            parametros.put("titulo", titulo);
            parametros.put("parqueaderos", emp.getParqueaderos());
            parametros.put("desde",desde.getDate());
            parametros.put("hasta",hasta.getDate());

            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);
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
            Logger.getLogger(frmTicket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void tickets(String dirreporte, String query, String titulo) {
        try {
            
            System.out.println("QUERY: "+query);
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(dirreporte);
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");

            List<Factura> fac = adm.query(query);
            ArrayList detalle = new ArrayList();
            for (Iterator<Factura> it = fac.iterator(); it.hasNext();) {
                Factura factura = it.next();
                if (cmbTipoReporte.getSelectedIndex() > 0) {
                    factura.setFecha(factura.getFechafin());
                }
                try{
                    factura.setClientes(factura.getTarjetas().getClientes());
                }catch(Exception e){
                    
                }
                
                
                detalle.add(factura);
            }
            FacturaSource ds = new FacturaSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazon());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefonos());
            parametros.put("titulo", titulo);
            parametros.put("parqueaderos", emp.getParqueaderos());
            WorkingDirectory w = new WorkingDirectory();
              String ubicacionDirectorio = w.get()+separador;
                if(ubicacionDirectorio.contains("build"))
                    ubicacionDirectorio = ubicacionDirectorio.replace(separador+"build", "");
         
            dirreporte = ubicacionDirectorio+separador+"fotos"+ separador;
            parametros.put("ubicacion", dirreporte);
            parametros.put("usuario", cmbUsuarios.getSelectedItem().toString());
            
            Date des = desde.getDate();
            Date has = desde.getDate();
            des.setHours(desdehora2.getDate().getHours());
            des.setMinutes(desdehora2.getDate().getMinutes());
            des.setSeconds(desdehora2.getDate().getSeconds());
            has.setHours(hastahora2.getDate().getHours());
            has.setMinutes(hastahora2.getDate().getMinutes());
            has.setSeconds(hastahora2.getDate().getSeconds());
            parametros.put("desde",des);
            parametros.put("hasta",has);
            
            if (cmbTipoReporte.getSelectedIndex() == 2) {
                Object con = adm.querySimple("Select count(o) from Factura as o" +
                        " where  o.fechafin is null  ");
                Long val2 = (Long) con;
                System.out.println("" + con);
                parametros.put("ocupados", val2.intValue());
            }


            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);
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
            Logger.getLogger(frmTicket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void consolidado(String dirreporte, String query, String titulo) {
        try {
            System.out.println("QUERY: "+query);
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(dirreporte);
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");

            List fac = adm.queryNativo(query);
            ArrayList detalle = new ArrayList();
            for (Iterator it = fac.iterator(); it.hasNext();) {
                Factura fac01 = new Factura();
                 Vector factura =   (Vector) it.next();
                 Date fecha = (Date) factura.get(0);
                 BigDecimal valor = (BigDecimal) factura.get(1);
                 fac01.setFecha(fecha);
                 fac01.setTotal(valor);
                detalle.add(fac01);
            }
            FacturaSource ds = new FacturaSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazon());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefonos());
            parametros.put("titulo", titulo);
            parametros.put("parqueaderos", emp.getParqueaderos());
//            if (jComboBox1.getSelectedIndex() == 2) {
//                Object con = adm.querySimple("Select count(o) from Factura as o" +
//                        " where  o.fechafin is null  ");
//                Long val2 = (Long) con;
//                System.out.println("" + con);
//                parametros.put("ocupados", val2.intValue());
//            }


            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);
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
            Logger.getLogger(frmTicket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void clientes(String dirreporte, String query, String titulo) {
        try {
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(dirreporte);
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");

            List<Clientes> fac = adm.query(query);
            ArrayList detalle = new ArrayList();
            for (Iterator<Clientes> it = fac.iterator(); it.hasNext();) {
                Clientes factura = it.next();
                detalle.add(factura);
            }
            ClientesSource ds = new ClientesSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazon());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefonos());
            parametros.put("titulo", titulo);
            parametros.put("parqueaderos", emp.getParqueaderos());
            parametros.put("desde", desde.getDate());
            parametros.put("hasta", hasta.getDate());
            if (cmbTipoReporte.getSelectedIndex() == 2) {
                Object con = adm.querySimple("Select count(o) from Factura as o" +
                        " where  o.fechafin is null  ");
                Long val2 = (Long) con;
                System.out.println("" + con);
                parametros.put("ocupados", val2.intValue());
            }


            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);
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
            Logger.getLogger(frmTicket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
  public void clientes2(String dirreporte, String query, String titulo) {
        try {
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(dirreporte);
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
    ArrayList detalle = new ArrayList();
            List fac = adm.queryNativo(query);
             for (Iterator it = fac.iterator(); it.hasNext();) {
                Clientes cli = new Clientes();
                 Vector clienteIt =   (Vector) it.next();
                 Integer fecha = (Integer) clienteIt.get(0);
                 Long valor = (Long) clienteIt.get(1);
                 cli = (Clientes)adm.buscarClave(fecha, Clientes.class);
                 cli.setTelefono(""+valor);//CARGO EL NUMERO DE INGRESOS
                 cli.setUltimoacceso( (Date) clienteIt.get(2));
                detalle.add(cli);
            }
        
//            for (Iterator<Clientes> it = fac.iterator(); it.hasNext();) {
//                Clientes factura = it.next();
//                detalle.add(factura);
//            }
            ClientesSource ds = new ClientesSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazon());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefonos());
            parametros.put("titulo", titulo);
            parametros.put("parqueaderos", emp.getParqueaderos());
            parametros.put("usuario", cmbUsuarios.getSelectedItem().toString());



            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);
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
            Logger.getLogger(frmTicket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void pruebas() {
        String desde2 = (desde.getDate().getYear() + 1900) + "-" + (desde.getDate().getMonth() + 1) + "-" + (desde.getDate().getDate());
        String hasta2 = (hasta.getDate().getYear() + 1900) + "-" + (hasta.getDate().getMonth() + 1) + "-" + (hasta.getDate().getDate());

        String dirreporte = "", query = "", titulo = "";
        query = "Select o from Clientes as o" +
                "  ";
        dirreporte = "D:"+separador+"PROYECTOS"+separador+"peaje"+separador+"src"+separador+"reportes"+separador+"clientes.jasper";
        titulo = "Tickest por Cobrar";

        try {
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(dirreporte);
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");

            List<Clientes> fac = adm.query(query);
            ArrayList detalle = new ArrayList();
            for (Iterator<Clientes> it = fac.iterator(); it.hasNext();) {
                Clientes factura = it.next();
                detalle.add(factura);
            }
            ClientesSource ds = new ClientesSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazon());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefonos());
            parametros.put("titulo", titulo);
            parametros.put("parqueaderos", emp.getParqueaderos());
            if (cmbTipoReporte.getSelectedIndex() == 2) {
                Object con = adm.querySimple("Select count(o) from Factura as o" +
                        " where  o.fechafin is null  ");
                Long val2 = (Long) con;
                System.out.println("" + con);
                parametros.put("ocupados", val2.intValue());
            }


            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);


//PARA BUSCAR LAS IMPRESORAS INSTALADAS
//            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
//            PrinterJob comprobante = PrinterJob.getPrinterJob();
//            DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
//            PrintService impresoras[] = PrintServiceLookup.lookupPrintServices(null, printRequestAttributeSet);

//JasperPrint print = JasperFillManager.fillReport( this.class.getResource("/classpath/yourReport.jasper").getPath(), new HashMap(), new yourReportDataSource());
            PrinterJob job = PrinterJob.getPrinterJob();
            /* Create an array of PrintServices */
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            int selectedService = 0;
            /* Scan found services to see if anyone suits our needs */
            for (int i = 0; i < services.length; i++) {
                String nombre = services[i].getName().toUpperCase();
                if (nombre.contains("Office".toUpperCase())) {
                    /*If the service is named as what we are querying we select it */
                    selectedService = i;
                }
            }
            job.setPrintService(services[selectedService]);
            PrintRequestAttributeSet  printRequestAttributeSet = new HashPrintRequestAttributeSet();
            MediaSizeName mediaSizeName = MediaSize.findMedia(4, 4, MediaPrintableArea.INCH);
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



//            JRViewer reporte = new JRViewer(masterPrint); //PARA VER EL REPORTE ANTES DE IMPRIMIR
//            panelReportes.removeAll();
//            reporte.repaint();
//            reporte.setLocation(0, 0);
//            reporte.setSize(723, 557);
//            reporte.setVisible(true);
//            panelReportes.add(reporte);
//            panelReportes.repaint();
//            this.repaint();
        } catch (Exception ex) {
            Logger.getLogger(frmTicket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String query = "";
        String dirreporte = "";
       

        String desde2 = (desde.getDate().getYear() + 1900) + "-" + (desde.getDate().getMonth() + 1) + "-" + (desde.getDate().getDate());
        String hasta2 = (hasta.getDate().getYear() + 1900) + "-" + (hasta.getDate().getMonth() + 1) + "-" + (hasta.getDate().getDate());
//        String desde02 = (desde.getDate().getYear() + 1900) + "-" + (desde.getDate().getMonth() + 1) + "-" + (desde.getDate().getDate());
//        String hasta02 = (hasta.getDate().getYear() + 1900) + "-" + (hasta.getDate().getMonth() + 1) + "-" + (hasta.getDate().getDate());
            desde2 = desde2+" "+desdehora2.getDate().getHours()+":"+desdehora2.getDate().getMinutes()+":"+desdehora2.getDate().getSeconds();
            hasta2 = hasta2+" "+hastahora2.getDate().getHours()+":"+hastahora2.getDate().getMinutes()+":"+hastahora2.getDate().getSeconds();
        String titulo = "";
          WorkingDirectory w = new WorkingDirectory();
//            String query = "";
          String ubicacionDirectorio = w.get()+separador;
                if(ubicacionDirectorio.contains("build"))
                    ubicacionDirectorio = ubicacionDirectorio.replace(separador+"build", "");
        

        if (cmbTipoReporte.getSelectedItem().toString().contains("(101)")) {//TICKEST x cobrar
            query = "Select o from Factura as o" +
                    " where o.fecha between '" + desde2 + "' and '" + hasta2 + "'  and (o.ticket is not null or o.placa like '%NO CLIENTE%') "
                    + "and o.fechafin is null ";
            dirreporte = ubicacionDirectorio+"reportes"+separador+"ticketsporcobrar.jasper";
            titulo = "Tickest por Cobrar";
            tickets(dirreporte, query, titulo);
        } else if (cmbTipoReporte.getSelectedItem().toString().contains("(102)")) { //TICKEST COBRADOS
            query = "Select o from Factura as o" +
                    " where o.fechafin between '" + desde2 + "' and '" + hasta2 + "' "
                    + "and   o.fechafin is not null  and (o.ticket is not null or o.placa like '%NO CLIENTE%') "
                    + "  AND (o.anulado IS NULL  OR o.anulado = FALSE)";
            if(cmbUsuarios.getSelectedIndex()>0){
                    query = "Select o from Factura as o" +
                            " where o.fechafin between '" + desde2 + "' and '" + hasta2 + "' "
                            + " and o.usuarioc.codigo  = '"+((Usuarios)cmbUsuarios.getSelectedItem()).getCodigo()+"'  "
                            + " and   o.fechafin is not null  and (o.ticket is not null or o.placa like '%NO CLIENTE%') "
                            + "  AND (o.anulado IS NULL  OR o.anulado = FALSE)";
            }
            dirreporte = ubicacionDirectorio+"reportes"+separador+"ticketscobrados.jasper";
            titulo = "Tickest Cobrados";
            tickets(dirreporte, query, titulo);

        } else if (cmbTipoReporte.getSelectedItem().toString().contains("(103)")) { //TICKEST ANULADOS
            query = "Select o from Factura as o" +
                    " where o.fechafin between '" + desde2 + "' and '" + hasta2 + "' "
                    + "and o.anulado = TRUE ";
            if(cmbUsuarios.getSelectedIndex()>0){
                    query = "Select o from Factura as o" +
                            " where o.fechafin between '" + desde2 + "' and '" + hasta2 + "' "
                            + " and o.usuarioc.codigo  = '"+((Usuarios)cmbUsuarios.getSelectedItem()).getCodigo()+"'  "
                            + "  AND  o.anulado = true ";
            }
            dirreporte = ubicacionDirectorio+"reportes"+separador+"ticketsanulados.jasper";
            titulo = "Tickest Anulados";
            tickets(dirreporte, query, titulo);

        }else if (cmbTipoReporte.getSelectedItem().toString().contains("(104)")) { //TICKEST TARIFA 0
            query = "Select o from Factura as o" +
                    " where o.fechafin between '" + desde2 + "' and '" + hasta2 + "' "
                    + "and o.tarifa0 = TRUE ";
            if(cmbUsuarios.getSelectedIndex()>0){
                    query = "Select o from Factura as o" +
                            " where o.fechafin between '" + desde2 + "' and '" + hasta2 + "' "
                            + " and o.usuarioc.codigo  = '"+((Usuarios)cmbUsuarios.getSelectedItem()).getCodigo()+"'  "
                            + "  AND  o.tarifa0 = true ";
            }
            dirreporte = ubicacionDirectorio+"reportes"+separador+"ticketsanulados.jasper";
            titulo = "Tickest con Tarifa 0.0";
            tickets(dirreporte, query, titulo);
 
        }else if (cmbTipoReporte.getSelectedItem().toString().contains("(105)")) { //FOTOS DE VEHÍCULOS
            query = "Select o from Factura as o" +
                    " where o.fechaini between '" + desde2 + "' and '" + hasta2 + "' "
                    + " ";
            if(cmbUsuarios.getSelectedIndex()>0){
                    query = "Select o from Factura as o" +
                            " where o.fechaini between '" + desde2 + "' and '" + hasta2 + "' "
                            + " and o.usuario.codigo  = '"+((Usuarios)cmbUsuarios.getSelectedItem()).getCodigo()+"'  "
                            + "    ";
            }
            dirreporte = ubicacionDirectorio+"reportes"+separador+"ticketsfotos.jasper";
            titulo = "Tickest Fotos";
            tickets(dirreporte, query, titulo);

        }else if (cmbTipoReporte.getSelectedItem().toString().contains("(106)")) { //CIERRE DE CAJA
        //} else if (cmbTipoReporte.getSelectedIndex() == 12) {//FACTURADO
            query = "Select o from Factura as o" +
                    " where o.fechafin between '" + desde2 + "' and '" + hasta2 + "' "
                    + "and o.fechafin is not null and o.numero is not null  "
                    + "AND (o.anulado IS NULL  OR o.anulado = FALSE)  ";
              if(cmbUsuarios.getSelectedIndex()>0){
                  query = "Select o from Factura as o" +
                    " where o.fechafin between '" + desde2 + "' and '" + hasta2 + "' "
                    + " and o.fechafin is not null and o.numero is not null  "
                    + " and o.usuarioc.codigo  = '"+((Usuarios)cmbUsuarios.getSelectedItem()).getCodigo()+"'  "
                    + " AND (o.anulado IS NULL  OR o.anulado = FALSE)  ";
               }
            
            
            dirreporte = ubicacionDirectorio+"reportes"+separador+"cierrecaja.jasper";
            titulo = "Facturas ";
            tickets(dirreporte, query, titulo);

        //} else if (cmbTipoReporte.getSelectedIndex() == 2) {//PUESTO OCUPADOS
        }else if (cmbTipoReporte.getSelectedItem().toString().contains("(305)")) { //PUESTOS OCUPADOS
            query = "Select o from Factura as o" +
                    " where o.placa = 'xxxxxx..'";
            dirreporte = ubicacionDirectorio+"reportes"+separador+"ocupados.jasper";
            titulo = "Cupos Disponibles";
            tickets(dirreporte, query, titulo);

        }else if (cmbTipoReporte.getSelectedItem().toString().contains("(200)")) { 
        //} else if (cmbTipoReporte.getSelectedIndex() == 3) {//Facturas Tickest y Tarjetas (200) 3
            query = "Select o from Factura as o" +
                    " where o.fechafin between '" + desde2 + "' and '" + hasta2 + "' "
                    + "and o.fechafin is not null and o.numero is not null  "
                    + "AND (o.anulado IS NULL  OR o.anulado = FALSE)  ";
              if(cmbUsuarios.getSelectedIndex()>0){
                  query = "Select o from Factura as o" +
                    " where o.fechafin between '" + desde2 + "' and '" + hasta2 + "' "
                    + " and o.fechafin is not null and o.numero is not null  "
                    + " and o.usuarioc.codigo  = '"+((Usuarios)cmbUsuarios.getSelectedItem()).getCodigo()+"'  "
                    + " AND (o.anulado IS NULL  OR o.anulado = FALSE)  ";
               }
            
            dirreporte = ubicacionDirectorio+"reportes"+separador+"facturasdiarias.jasper";
            titulo = "Facturas ";
            tickets(dirreporte, query, titulo);
        }else if (cmbTipoReporte.getSelectedItem().toString().contains("(201)")) { 
        //}  else if (cmbTipoReporte.getSelectedIndex() == 4) {//FACTURADO SOLO TICKETS         Facturas de Tickets (201) 4
            query = "Select o from Factura as o" +
                    " where o.fechafin between '" + desde2 + "' and '" + hasta2 + "' "
                    + "and o.fechafin is not null "
                    + "and (o.ticket is not null or o.placa like '%NO CLIENTE%') "
                   + " AND (o.anulado IS NULL  OR o.anulado = FALSE)  ";
            System.out.println("SOLO TICKETS: "+query);
            if(cmbUsuarios.getSelectedIndex()>0){
                 query = "Select o from Factura as o" +
                    " where o.fechafin between '" + desde2 + "' and '" + hasta2 + "' "
                    + " and o.fechafin is not null and (o.ticket is not null or o.placa like '%NO CLIENTE%') "
                    + " and o.usuarioc.codigo  = '"+((Usuarios)cmbUsuarios.getSelectedItem()).getCodigo()+"'  "
                    + " AND (o.anulado IS NULL  OR o.anulado = FALSE)  ";
               }
            
            dirreporte = ubicacionDirectorio+"reportes"+separador+"facturasdiariastickets.jasper";
            titulo = "Facturas ";
            tickets(dirreporte, query, titulo);

        }else if (cmbTipoReporte.getSelectedItem().toString().contains("(202)")) {     //Facturas de Tarjetas (202) 5
        //}  else if (cmbTipoReporte.getSelectedIndex() == 5) {//FACTURADO SOLO TARJETAS MENSUALES
            query = "Select o from Factura as o" +
                    " where o.fechafin between '" + desde2 + "' and '" + hasta2 + "' "
                    + "and o.fechafin is not null  and o.ticket is null and o.tarjetas is null "
                    + " AND (o.anulado IS NULL  OR o.anulado = FALSE)  ";
            System.out.println("SOLO TARJETAS: "+query);
            dirreporte = ubicacionDirectorio+"reportes"+separador+"facturasdiariastarjetas.jasper";
            titulo = "Facturas ";
            tickets(dirreporte, query, titulo);
        }else if (cmbTipoReporte.getSelectedItem().toString().contains("(300)")) {     //Consolidado por Mes (300) 6
        //}else if (cmbTipoReporte.getSelectedIndex() == 6) {//CONSOLIDADO POR MES CLIENTES
            query = "Select date(o.fechafin),sum(o.total) from Factura as o" +
                    " where o.fechafin between '" + desde2 + "' and '" + hasta2 + "'  "
                    + " AND (o.anulado IS NULL  OR o.anulado = FALSE)  "
                    + " and o.fechafin is not null  and (o.nocontar = false or o.nocontar is null ) group by month(o.fechafin)  ";
            System.out.println(""+query);
            dirreporte = ubicacionDirectorio+"reportes"+separador+"consolidado.jasper";
            titulo = " ";
            consolidado(dirreporte, query, titulo);

        }else if (cmbTipoReporte.getSelectedItem().toString().contains("(302)")) {     //Clientes mas frecuentes (302) 7
        //} else if (cmbTipoReporte.getSelectedIndex() == 7) {//CLIENTES MAS FRECUENTS CON TARJETAS
            
            String desde3 = (desde.getDate().getYear() + 1900) + "-" + (desde.getDate().getMonth() + 1) + "-" + (desde.getDate().getDate());
            String hasta3 = (hasta.getDate().getYear() + 1900) + "-" + (hasta.getDate().getMonth() + 1) + "-" + (hasta.getDate().getDate());

            query = "SELECT CLIENTE,COUNT(*), MAX(fechaini)  FROM FACTURA WHERE CLIENTE > 1 AND "
                    + "FECHA  between '" + desde3 + "' and '" + hasta3 + "' GROUP BY CLIENTE  ORDER BY COUNT(*) desc ";
            dirreporte = ubicacionDirectorio+"reportes"+separador+"clientes2.jasper";
            titulo = " ";
            clientes2(dirreporte, query, titulo);

       }else if (cmbTipoReporte.getSelectedItem().toString().contains("(303)")) {     //Listado clientes (303) 8
        //} else if (cmbTipoReporte.getSelectedIndex() == 8) {//LISTADO DE CLIENTES
            query = "Select o from Clientes as o where o.codigo > 1 order by o.nombres";
            dirreporte = ubicacionDirectorio+"reportes"+separador+"clientes.jasper";
            titulo = " ";
            clientes(dirreporte, query, titulo);

            }else if (cmbTipoReporte.getSelectedItem().toString().contains("(304)")) {     //No. de Ingresos x Cliente (304) 9
        //}else if (cmbTipoReporte.getSelectedIndex() == 9) {//no de ingresos por cliente
            String complemento = "";
            
            if(cmbUsuarios.getSelectedIndex()>0){
                complemento = " and  f.usuarioc = '"+((Usuarios)cmbUsuarios.getSelectedItem()).getCodigo()+"' ";    
            }
            query = "SELECT * FROM Factura f, Tarjetas t, Clientes c WHERE  t.cliente = c.codigo "
                    + " AND t.tarjeta = f.tarjeta AND f.fecha BETWEEN '"+convertiraString(desde.getDate())+"' "
                    + "AND  '"+convertiraString(hasta.getDate())+"'  "
                    +complemento
                    + "ORDER BY c.nombres ";
            if(cmbClientes.getSelectedIndex()>0){
                query = "SELECT * FROM Factura f, Tarjetas t, Clientes c WHERE  t.cliente = c.codigo "
                        + " AND t.tarjeta = f.tarjeta AND " 
                        + "f.fecha BETWEEN '"+convertiraString(desde.getDate())+"' "
                        + "AND  '"+convertiraString(hasta.getDate())+"' "
                        + "and c.codigo = '"+((Clientes)cmbClientes.getSelectedItem()).getCodigo() +"'"
                        + complemento
                        + "  ORDER BY c.nombres ";    
            }
            
            dirreporte = ubicacionDirectorio+"reportes"+separador+"noingresos.jasper";
            titulo = " ";
            noingresos(dirreporte, query, titulo);

        }else if (cmbTipoReporte.getSelectedItem().toString().contains("(306)")) {     //Tarjetas Ocupadas(Dentro del Parqu.) (306) 13
        //}  else if (cmbTipoReporte.getSelectedIndex() == 13) {//TARJETAS DENTRO
            query = "Select o from Factura as o" +
                    " where o.fechaini between '" + desde2 + "' and '" + hasta2 + "' "
                    + "AND o.fechafin is null AND o.ticket  IS NULL order by o.fechaini ";
            dirreporte = ubicacionDirectorio+"reportes"+separador+"clientesActuales.jasper";
            titulo = "Clientes dentro del parqueadero";
            tickets(dirreporte, query, titulo);
        }else if (cmbTipoReporte.getSelectedItem().toString().contains("(301)")) {      //Consolidado x fechas (301) 14
        //} else if (cmbTipoReporte.getSelectedIndex() == 14) {//CONSOLIDADO
                query = "SELECT placa, total, COUNT(*), SUM(total) FROM factura  "
                        + "WHERE fechafin BETWEEN '"+desde2+"' "
                        + "AND  '"+hasta2+"' GROUP BY placa "; 
                System.out.println(""+query);
                dirreporte = ubicacionDirectorio+"reportes"+separador+"consolidadoxfecha.jasper";
                titulo = "Consolidado";
                consolidadoxfecha(dirreporte, query, titulo);
                
        }
           principal.contenedor.requestFocus();

    }//GEN-LAST:event_btnBuscarActionPerformed
        public static String convertiraString(Date fecha) {

        return (fecha.getYear() + 1900) + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();

    }
    private void cmbTipoReporteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTipoReporteItemStateChanged
        // TODO add your handling code here:
    cmbUsuarios.setEnabled(false);  
    cmbClientes.setEnabled(false);
        if(cmbTipoReporte.getSelectedIndex() == 0  || cmbTipoReporte.getSelectedIndex() == 1
                || cmbTipoReporte.getSelectedIndex() == 3 || cmbTipoReporte.getSelectedIndex() == 4
                || cmbTipoReporte.getSelectedIndex() == 5 || cmbTipoReporte.getSelectedIndex() == 12){
                cmbUsuarios.setEnabled(true);    
    }else if(cmbTipoReporte.getSelectedIndex() == 9){
            try {
                cmbClientes.setEnabled(true);
                cmbUsuarios.setEnabled(true);   
                cmbClientes.removeAllItems();
                Clientes cliE = new Clientes(-1);
                cliE.setNombres("[TODOS]");
                cmbClientes.addItem(cliE);
                List<Clientes> cli = adm.query("Select DISTINCT o.clientes from Tarjetas as o "
                        + "where o.habilitada = true  order by o.clientes.nombres ");
                 for (Iterator<Clientes> it = cli.iterator(); it.hasNext();) {
                     Clientes clientes = it.next();
                     cmbClientes.addItem(clientes);
                 }
            } catch (Exception ex) {
                Logger.getLogger(frmReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        } 
    }//GEN-LAST:event_cmbTipoReporteItemStateChanged

    private void cmbTipoReporteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbTipoReporteKeyPressed
        // TODO add your handling code here:
        int cod = evt.getKeyCode();
        principal.tecla(cod);
    }//GEN-LAST:event_cmbTipoReporteKeyPressed

    private void desdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_desdeKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_desdeKeyPressed

    private void hastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hastaKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_hastaKeyPressed

    private void desdehora2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_desdehora2KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_desdehora2KeyPressed

    private void hastahora2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hastahora2KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_hastahora2KeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cmbClientes;
    private javax.swing.JComboBox cmbTipoReporte;
    private javax.swing.JComboBox cmbUsuarios;
    private com.toedter.calendar.JDateChooser desde;
    private com.toedter.calendar.JDateChooser desdehora2;
    private com.toedter.calendar.JDateChooser hasta;
    private com.toedter.calendar.JDateChooser hastahora2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel panelReportes;
    // End of variables declaration//GEN-END:variables
}
