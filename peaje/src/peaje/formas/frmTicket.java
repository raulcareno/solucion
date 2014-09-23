package peaje.formas;

import java.awt.Container;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import hibernate.cargar.Administrador;
import hibernate.cargar.validaciones;
import hibernate.*;
import hibernate.cargar.UsuarioActivo;
import hibernate.cargar.WorkingDirectory;
import java.io.File;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;
import sources.FacturaSource;

//import org.eclipse.persistence.internal.history.HistoricalDatabaseTable;
/**
 *
 * @author geovanny
 */
public class frmTicket extends javax.swing.JInternalFrame {

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
    private String in;
    private String out;
    String separador = File.separatorChar + "";
    Boolean guardando = false;
    WorkingDirectory w = new WorkingDirectory();
    String ubicacionDirectorio = w.get() + separador;
    logger lger = new logger();
    /** Creates new form frmProfesores */
    public frmTicket(java.awt.Frame parent, boolean modal, Administrador adm1) {
//        super(parent, modal);
        this.adm = adm1;
        llenarCombo();
        initComponents();
        this.setSize(615, 508);
//        empresaObj = lo.empresaObj;

        val = new validaciones();
        placa.requestFocusInWindow();
        mascaras();
    }

    public frmTicket(java.awt.Frame parent, boolean modal, frmPrincipal lo, Administrador adm1) {
//        super(parent, modal);
        this.desktopContenedor = lo.contenedor;
        this.adm = adm1;
        llenarCombo();
        initComponents();
        this.setSize(615, 508);
        empresaObj = lo.empresaObj;

        val = new validaciones();
        principal = lo;
        //mascaras() ;
        placa.requestFocusInWindow();
//        placa.requestFocusInWindow();
//        placa.requestFocusInWindow();
//        placa.requestFocusInWindow();
//        placa.requestFocusInWindow();
//        placa.requestFocusInWindow();
//        placa.requestFocusInWindow();
//        placa.requestFocusInWindow();
//        placa.requestFocusInWindow();
//        placa.requestFocusInWindow();
//        placa.requestFocusInWindow();
//        placa.requestFocusInWindow();

    }

    public void mascaras() {
        try {
            MaskFormatter mf = new MaskFormatter("AAA-####");
            mf.setPlaceholderCharacter('_');
            mf.install(placa);

        } catch (ParseException e) {
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
    }

    public void limpiar() {
     
        System.out.println("antes: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024);
        System.gc();
        System.gc();
        System.gc();
        System.runFinalization();
        System.gc();
        System.out.println("despues: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024);
     
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

        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        noTicket = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        placa = new javax.swing.JFormattedTextField();
        codigo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setTitle("Usuarios");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ticket.gif"))); // NOI18N
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
        jLabel8.setText("Registro de Tickets ..::..");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 270, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Registro el Numero de placa y presiona ENTER..::..");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 20, 300, 13);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 320, 40);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        fecha.setDate(new Date());
        fecha.setDateFormatString("dd/MM/yyyy HH:mm:ss");
        fecha.setEnabled(false);
        fecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaKeyPressed(evt);
            }
        });
        jPanel1.add(fecha);
        fecha.setBounds(90, 30, 150, 20);

        jLabel7.setForeground(new java.awt.Color(0, 0, 153));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Ticket No.");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(20, 10, 60, 14);

        jLabel9.setForeground(new java.awt.Color(0, 0, 153));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Fecha y Hora: ");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(0, 30, 80, 20);

        jLabel12.setForeground(new java.awt.Color(0, 0, 153));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Placa: ");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(10, 50, 70, 14);

        noTicket.setEditable(false);
        noTicket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                noTicketKeyPressed(evt);
            }
        });
        jPanel1.add(noTicket);
        noTicket.setBounds(90, 10, 130, 20);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enter.png"))); // NOI18N
        jLabel3.setText("Digite y luego Enter");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(160, 50, 140, 20);

        placa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                placaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                placaKeyReleased(evt);
            }
        });
        jPanel1.add(placa);
        placa.setBounds(90, 50, 70, 20);

        codigo.setFont(new java.awt.Font("Tahoma", 0, 5)); // NOI18N
        jPanel1.add(codigo);
        codigo.setBounds(240, 10, 0, 0);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 50, 300, 80);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setLayout(null);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png"))); // NOI18N
        btnAgregar.setMnemonic('G');
        btnAgregar.setText("Guardar");
        btnAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar.setMargin(new java.awt.Insets(1, 1, 1, 1));
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
        btnAgregar.setBounds(170, 10, 60, 50);

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
        btnSalir.setBounds(230, 10, 60, 50);

        getContentPane().add(jPanel4);
        jPanel4.setBounds(10, 130, 300, 70);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void guardarTic() {
        if (guardando == false) {
            guardando = true;

//                Thread.sleep(500);
            if (principal.permisos.getAgregar()) {
                try {
                    if (placa.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Ingrese el No. de Placa ...!", "", JOptionPane.ERROR_MESSAGE);
                        placa.requestFocusInWindow();
                        btnSalir.setEnabled(true);
                        btnAgregar.setEnabled(true);
                        guardando = false;
                        return;
                    }
                    btnAgregar.setVisible(false);
                    Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
                    Factura fac = new Factura();
                    fac.setPlaca(placa.getText().replace("_", "").toUpperCase());
                    fac.setFechaini(new Date());
                    fac.setFecha(new Date());
                    fac.setAnulado(false);
                    fac.setUsuario(principal.getUsuario());
                    fac.setNocontar(false);
                    fac.setAnulado(false);
                    fac.setYasalio(false);
                    fac.setTarifa0(false);
                    fac.setSellado(false); 
                      Boolean pasar = true;
                      String nombreCaja = empresaObj.nombreCaja;
                        Integer numero = new Integer(emp.getDocumentoticket())+1;
                        while(pasar){
                            List sihay = adm.query("Select o from Factura as o where o.ticket = '"+nombreCaja+numero+"'"); 
                            if(sihay.size()<=0){
                                pasar = false;
                                fac.setTicket("" +nombreCaja+ numero);
                                emp.setDocumentoticket((numero) + "");
                                adm.guardar(fac); // GUARDO FACTURA
                                noTicket.setText(nombreCaja+numero+"");
                                codigo.setText(fac.getCodigo() + "");
                            }else{
                                numero++;
                            }
                        }
                    imprimir(fac.getCodigo(), emp);
                    emp = null;
                if(empresaObj.getSeabretic()){
                        if (empresaObj.getRetardoEntrada() != null) {
                                if (empresaObj.getRetardoEntrada().length() > 0) {
                                    Integer retardo = new Integer(empresaObj.getRetardoEntrada());
                                    Thread.sleep(retardo * 1000);
                                }
                        }
                    try {
                        LeerTarjeta ta = principal.buscarPuerto("principal");
                        ta.outputSream.write(empresaObj.getPuertatic().getBytes());
                        //TEMPORAL
                        Thread.sleep(20);                            
                        ta.outputSream.write(empresaObj.getPuertatic().getBytes());
                        Thread.sleep(20);                            
                        ta.outputSream.write(empresaObj.getPuertatic().getBytes());
                        Thread.sleep(20);                            
                        ta.outputSream.write(empresaObj.getPuertatic().getBytes());
                        Thread.sleep(20);                            
                        ta.outputSream.write(empresaObj.getPuertatic().getBytes());
                        Thread.sleep(20);                            
                        ta.outputSream.write(empresaObj.getPuertatic().getBytes());
                        //TEMPORAL
                        ta = null;
                    } catch (Exception ex) {
                        Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("ABRIO PUERTA: " + empresaObj.getPuertatic());
                }else{
                    System.out.println("NO ABRE BARRERA POR DESHABILITACION DEN FRMEMPRESA " );
                }
                    principal.noDisponibles();
                    //                btnAgregar.setEnabled(true);
                    principal.auditar("Ticket", "No." + fac.getTicket(), "GUARDAR");
                    principal.contenedor.requestFocus();
                    guardando = false;
                    try {
                        
                    
                    if(empresaObj.getWebcam()){
                         if (ubicacionDirectorio.contains("build")) {
                            ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
                        }
                        
                        fotografiar(""+fac.getCodigo(), ubicacionDirectorio+"\\fotos");
                    }
                    if(empresaObj.getIpcam()){
                         if (ubicacionDirectorio.contains("build")) {
                            ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
                        }
                        
                        fotografiarIp(""+fac.getCodigo()+".jpg", ubicacionDirectorio+"\\fotos");
                    }
                    } catch (Exception e) {
                        System.out.println("fotografíar: "+e);
                        lger.logger(frmTicket.class.getName(), e.getMessage());   
                    }
                    principal.cargarFoto(fac.getCodigo());
                    System.gc();
                    limpiar();
                    this.setVisible(false);
                     guardando = false;
                } catch (Exception ex) {
                    lger.logger(frmTicket.class.getName(), ex.getMessage());
                    
                    JOptionPane.showMessageDialog(this, "Error en guardar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(frmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
                     
                    btnAgregar.setEnabled(true);
                    btnSalir.setEnabled(true);
                    btnAgregar.setVisible(true);
                   guardando = false;
                   limpiar();
                    return;
                }
            } else {
                 guardando = false;
                JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
            }
            guardando = false;

        }
    }
public void fotografiar(String nombre,String direccion){
      int resultado = principal.ver.Fotografiar(direccion, false, nombre);
        if (resultado == 0) {
            JOptionPane.showMessageDialog(null, "Error en la Fotografia");
        }
}

public void fotografiarIp(String nombre,String direccion){
   principal.verIp.tomarFotoIp(direccion+separador+nombre,principal);
//        if (resultado == 0) {
//            JOptionPane.showMessageDialog(null, "Error en la Fotografia");
//        }
}

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        Thread cargar = new Thread() {

            public void run() {
                btnAgregar.setEnabled(false);
                btnSalir.setEnabled(false);
                //btnAgregar.setVisible(false);
                guardarTic();
                btnAgregar.setEnabled(true);
                btnSalir.setEnabled(true);
                btnAgregar.setVisible(true);

            }
        };
        cargar.start();

    }//GEN-LAST:event_btnAgregarActionPerformed

    public void imprimir(int cod, Empresa emp) {

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
            /* Create an array of PrintServices */
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            int selectedService = 0;
            /* Scan found services to see if anyone suits our needs */
            for (int i = 0; i < services.length; i++) {
                String nombre = services[i].getName();
                if (nombre.contains(emp.getImpticket())) {
                    selectedService = i;
                }
            }
            job.setPrintService(services[selectedService]);
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            MediaSizeName mediaSizeName = MediaSize.findMedia(3.08F, 3.70F, MediaPrintableArea.INCH);
            //MediaSizeName mediaSizeName = MediaSize.findMedia(3F,3F, MediaPrintableArea.INCH);
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



//            JasperViewer viewer = new JasperViewer(masterPrint, false); //PARA VER EL REPORTE ANTES DE IMPRIMIR
//            viewer.show();
//            try {
//                JasperPrintManager.printPage(masterPrint, 0, false);//LE ENVIO A IMPRIMIR false NO MUESTRA EL CUADRO DE DIALOGO
//            } catch (JRException ex) {
//                ex.printStackTrace();
//            }
        } catch (Exception ex) {
            Logger.getLogger(frmTicket.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(frmTicket.class.getName(), ex+"");
        }

//        } catch (JRException ex) {
//            ex.printStackTrace();
//        }
    }

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        principal.contenedor.requestFocus();
        this.setVisible(false);
        System.gc();
}//GEN-LAST:event_btnSalirActionPerformed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.setVisible(false);
            principal = null;
            empresaObj = null;
            System.gc();
        }
    }//GEN-LAST:event_formKeyReleased

    private void placaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            btnAgregar.requestFocusInWindow();
        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {

            principal.contenedor.requestFocus();
            principal = null;
            empresaObj = null;
            System.gc();
            this.setVisible(false);
        } else {
            principal.tecla(evt.getKeyCode());
        }

    }//GEN-LAST:event_placaKeyPressed

    private void placaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placaKeyReleased
        // TODO add your handling code here:
        placa.setText(placa.getText().toUpperCase());
    }//GEN-LAST:event_placaKeyReleased

    private void btnAgregarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregarKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());

    }//GEN-LAST:event_btnAgregarKeyPressed

    private void btnSalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalirKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnSalirKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_formKeyPressed

    private void noTicketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_noTicketKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_noTicketKeyPressed

    private void fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_fechaKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel codigo;
    public com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JFormattedTextField noTicket;
    public javax.swing.JFormattedTextField placa;
    // End of variables declaration//GEN-END:variables
}
