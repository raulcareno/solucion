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
import peaje.Administrador;
import peaje.validaciones;
import hibernate.*;
import hibernate.cargar.WorkingDirectory;
import sources.ClientesSource;
import sources.FacturaSource;
//import org.eclipse.persistence.internal.history.HistoricalDatabaseTable;

/**
 *
 * @author geovanny
 */
public class frmManual extends javax.swing.JInternalFrame {

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
    public frmManual(Container contendor,Administrador adm1) {
        this.desktopContenedor = contendor;
//          super(parent,modal);
        adm = adm1;
        llenarCombo();
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
        
       
    }
//
//    public frmReportes(java.awt.Frame parent, boolean modal,principal lo) {
//        super(parent,modal);
//        this.desktopContenedor = lo.contenedor;
//        llenarCombo();
//        initComponents();
//        this.setSize(615, 508);
//        empresaObj = new Empresa();
//        adm = new Administrador();
//        val = new validaciones();
//        principal = lo;
//    }

    public void llenarCombo() {
       
        try {
            perfilesList = new ArrayList<Global>();
            perfilesList = adm.query("Select o from Global as o where o.grupo = 'PER'");
        } catch (Exception ex) {
            Logger.getLogger(frmManual.class.getName()).log(Level.SEVERE, null, ex);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();

        setTitle("Reportes");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images_botones/ico.gif"))); // NOI18N
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
        jLabel8.setText("Manual de Ayuda ..::..");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 270, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10));
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Seleccione un reporte y presione ver ..::..");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 20, 250, 13);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Manual");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Crear Clientes");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Tickets y Cobrar");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Reportes");
        treeNode1.add(treeNode2);
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(jTree1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 487, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 488, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.setVisible(false);
        }
    }//GEN-LAST:event_formKeyReleased

    public void tickets(String dirreporte, String query, String titulo) {
        try {
            System.out.println("QUERY: "+query);
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(dirreporte);
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");

            List<Factura> fac = adm.query(query);
            ArrayList detalle = new ArrayList();
            for (Iterator<Factura> it = fac.iterator(); it.hasNext();) {
                Factura factura = it.next();
                detalle.add(factura);
            }
            FacturaSource ds = new FacturaSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazon());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefonos());
            parametros.put("titulo", titulo);
            parametros.put("parqueaderos", emp.getParqueaderos());
        


            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);
            JRViewer reporte = new JRViewer(masterPrint); //PARA VER EL REPORTE ANTES DE IMPRIMIR
         
            reporte.repaint();
            reporte.setLocation(0, 0);
            reporte.setSize(723, 557);
            reporte.setVisible(true);
          
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
         

            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);
            JRViewer reporte = new JRViewer(masterPrint); //PARA VER EL REPORTE ANTES DE IMPRIMIR
      
            reporte.repaint();
            reporte.setLocation(0, 0);
            reporte.setSize(723, 557);
            reporte.setVisible(true);
         
            this.repaint();
        } catch (Exception ex) {
            Logger.getLogger(frmTicket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
