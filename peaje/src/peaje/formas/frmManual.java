package peaje.formas;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JRViewer;
import peaje.Administrador;
import peaje.validaciones;
import hibernate.*;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
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
    public frmManual(Container contendor, Administrador adm1) {
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
        jLabel32 = new javax.swing.JLabel();
        btnClientes = new javax.swing.JButton();
        btnTicket = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        btnEmpresa = new javax.swing.JButton();
        btnTarifas = new javax.swing.JButton();
        btnAccesos = new javax.swing.JButton();
        btnReconfigurar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        mipanel = new javax.swing.JPanel();

        setTitle("CONTENIDOS DE AYUDA");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images_botones/ico.gif"))); // NOI18N
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel3.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/images_botones/fondoInicio.png")))); // NOI18N
        jPanel3.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("VIDEO TUTORIALES DE AYUDA ..::..");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 270, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Recuerde tener encendidos sus parlantes ..::..");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 20, 600, 13);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 601, 40);

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images_botones/ico.png"))); // NOI18N
        jLabel32.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(jLabel32);
        jLabel32.setBounds(340, 60, 225, 80);

        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/usr.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.setToolTipText("Crear nuevos clientes y asignar tarjetas");
        btnClientes.setFocusable(false);
        btnClientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        getContentPane().add(btnClientes);
        btnClientes.setBounds(10, 58, 152, 43);

        btnTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ticket.gif"))); // NOI18N
        btnTicket.setText("Ticket y Cobros");
        btnTicket.setToolTipText("Registrar un ingreso de vehículo");
        btnTicket.setFocusable(false);
        btnTicket.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTicket.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTicketActionPerformed(evt);
            }
        });
        getContentPane().add(btnTicket);
        btnTicket.setBounds(10, 112, 152, 43);

        btnReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/resultados.png"))); // NOI18N
        btnReportes.setText("Reportes");
        btnReportes.setFocusable(false);
        btnReportes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReportes.setPreferredSize(new java.awt.Dimension(101, 21));
        btnReportes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });
        getContentPane().add(btnReportes);
        btnReportes.setBounds(10, 161, 152, 43);

        btnUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/User3.gif"))); // NOI18N
        btnUsuarios.setMnemonic('O');
        btnUsuarios.setText("Operadores");
        btnUsuarios.setFocusable(false);
        btnUsuarios.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUsuarios.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuarios);
        btnUsuarios.setBounds(10, 210, 152, 43);

        btnEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/empresa.png"))); // NOI18N
        btnEmpresa.setMnemonic('E');
        btnEmpresa.setText("Datos Empresa");
        btnEmpresa.setFocusable(false);
        btnEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEmpresa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpresaActionPerformed(evt);
            }
        });
        getContentPane().add(btnEmpresa);
        btnEmpresa.setBounds(168, 58, 135, 43);

        btnTarifas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dinero.gif"))); // NOI18N
        btnTarifas.setMnemonic('R');
        btnTarifas.setText("Tarifas");
        btnTarifas.setFocusable(false);
        btnTarifas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTarifas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTarifas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTarifasActionPerformed(evt);
            }
        });
        getContentPane().add(btnTarifas);
        btnTarifas.setBounds(168, 112, 135, 43);

        btnAccesos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lock.gif"))); // NOI18N
        btnAccesos.setMnemonic('S');
        btnAccesos.setText("Accesos");
        btnAccesos.setFocusable(false);
        btnAccesos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAccesos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAccesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccesosActionPerformed(evt);
            }
        });
        getContentPane().add(btnAccesos);
        btnAccesos.setBounds(168, 161, 135, 43);

        btnReconfigurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/configure.gif"))); // NOI18N
        btnReconfigurar.setMnemonic('S');
        btnReconfigurar.setText("Reconfigurar Sistema");
        btnReconfigurar.setFocusable(false);
        btnReconfigurar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReconfigurar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReconfigurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReconfigurarActionPerformed(evt);
            }
        });
        getContentPane().add(btnReconfigurar);
        btnReconfigurar.setBounds(168, 210, 135, 43);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.gif"))); // NOI18N
        jButton1.setText("SALIR");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(390, 210, 120, 40);

        javax.swing.GroupLayout mipanelLayout = new javax.swing.GroupLayout(mipanel);
        mipanel.setLayout(mipanelLayout);
        mipanelLayout.setHorizontalGroup(
            mipanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        mipanelLayout.setVerticalGroup(
            mipanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        getContentPane().add(mipanel);
        mipanel.setBounds(530, 190, 70, 70);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.setVisible(false);
        }
    }//GEN-LAST:event_formKeyReleased

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed

        Thread cargar = new Thread() {
            public void run() {
                NativeInterface.open();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        JFrame frame = new JFrame("MANUAL DE CLIENTES");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.getContentPane().add(new SimpleFlashExample("/manualpeaje/CLIENTES.swf"), BorderLayout.CENTER);
                        frame.setSize(800, 600);
                        frame.setLocationByPlatform(true);
                        frame.setVisible(true);

                        mipanel.add(frame);

                    }
                });
                NativeInterface.runEventPump();

            }
        };
        cargar.start();

        //<property name="toplink.cache.type.default" value="NONE"/>
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnTarifasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTarifasActionPerformed
        // TODO add your handling code here:
            Thread cargar = new Thread() {
            public void run() {
                NativeInterface.open();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        JFrame frame = new JFrame("MANUAL DE TICKETS Y FACTURACIÓN");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.getContentPane().add(new SimpleFlashExample("/manualpeaje/TICKET_COBRAR.swf"), BorderLayout.CENTER);
                        frame.setSize(800, 600);
                        frame.setLocationByPlatform(true);
                        frame.setVisible(true);

//                        mipanel.add(frame);

                    }
                });
                NativeInterface.runEventPump();

            }
        };
        cargar.start();
}//GEN-LAST:event_btnTarifasActionPerformed

    private void btnAccesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccesosActionPerformed
        // TODO add your handling code here:
         Thread cargar = new Thread() {
            public void run() {
                NativeInterface.open();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        JFrame frame = new JFrame("MANUAL DE TICKETS Y FACTURACIÓN");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.getContentPane().add(new SimpleFlashExample("/manualpeaje/TICKET_COBRAR.swf"), BorderLayout.CENTER);
                        frame.setSize(800, 600);
                        frame.setLocationByPlatform(true);
                        frame.setVisible(true);

//                        mipanel.add(frame);

                    }
                });
                NativeInterface.runEventPump();

            }
        };
        cargar.start();
}//GEN-LAST:event_btnAccesosActionPerformed

    private void btnReconfigurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReconfigurarActionPerformed
          Thread cargar = new Thread() {
            public void run() {
                NativeInterface.open();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        JFrame frame = new JFrame("MANUAL DE TICKETS Y FACTURACIÓN");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.getContentPane().add(new SimpleFlashExample("/manualpeaje/TICKET_COBRAR.swf"), BorderLayout.CENTER);
                        frame.setSize(800, 600);
                        frame.setLocationByPlatform(true);
                        frame.setVisible(true);

//                        mipanel.add(frame);

                    }
                });
                NativeInterface.runEventPump();

            }
        };
        cargar.start();
    }//GEN-LAST:event_btnReconfigurarActionPerformed

    private void btnTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTicketActionPerformed
        // TODO add your handling code here:
           Thread cargar = new Thread() {
            public void run() {
                NativeInterface.open();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        JFrame frame = new JFrame("MANUAL DE TICKETS Y FACTURACIÓN");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.getContentPane().add(new SimpleFlashExample("/manualpeaje/TICKET_COBRAR.swf"), BorderLayout.CENTER);
                        frame.setSize(800, 600);
                        frame.setLocationByPlatform(true);
                        frame.setVisible(true);

//                        mipanel.add(frame);

                    }
                });
                NativeInterface.runEventPump();

            }
        };
        cargar.start();
    }//GEN-LAST:event_btnTicketActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
           this.setVisible(false);
        principal = null;
        empresaObj = null;
        System.gc();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        // TODO add your handling code here:
            Thread cargar = new Thread() {
            public void run() {
                NativeInterface.open();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        JFrame frame = new JFrame("MANUAL DE TICKETS Y FACTURACIÓN");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.getContentPane().add(new SimpleFlashExample("/manualpeaje/TICKET_COBRAR.swf"), BorderLayout.CENTER);
                        frame.setSize(800, 600);
                        frame.setLocationByPlatform(true);
                        frame.setVisible(true);

//                        mipanel.add(frame);

                    }
                });
                NativeInterface.runEventPump();

            }
        };
        cargar.start();
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        // TODO add your handling code here:
            Thread cargar = new Thread() {
            public void run() {
                NativeInterface.open();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        JFrame frame = new JFrame("MANUAL DE TICKETS Y FACTURACIÓN");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.getContentPane().add(new SimpleFlashExample("/manualpeaje/TICKET_COBRAR.swf"), BorderLayout.CENTER);
                        frame.setSize(800, 600);
                        frame.setLocationByPlatform(true);
                        frame.setVisible(true);

//                        mipanel.add(frame);

                    }
                });
                NativeInterface.runEventPump();

            }
        };
        cargar.start();
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpresaActionPerformed
        // TODO add your handling code here:
            Thread cargar = new Thread() {
            public void run() {
                NativeInterface.open();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        JFrame frame = new JFrame("MANUAL DE TICKETS Y FACTURACIÓN");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.getContentPane().add(new SimpleFlashExample("/manualpeaje/TICKET_COBRAR.swf"), BorderLayout.CENTER);
                        frame.setSize(800, 600);
                        frame.setLocationByPlatform(true);
                        frame.setVisible(true);

//                        mipanel.add(frame);

                    }
                });
                NativeInterface.runEventPump();

            }
        };
        cargar.start();
    }//GEN-LAST:event_btnEmpresaActionPerformed

    public void tickets(String dirreporte, String query, String titulo) {
        try {
            System.out.println("QUERY: " + query);
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
    private javax.swing.JButton btnAccesos;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnEmpresa;
    private javax.swing.JButton btnReconfigurar;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnTarifas;
    private javax.swing.JButton btnTicket;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel mipanel;
    // End of variables declaration//GEN-END:variables
}
