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

import hibernate.Auditoria;
import hibernate.Empresa;
import hibernate.Global;
import hibernate.Productos;
import hibernate.Tarifas;
import hibernate.Usuarios;
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
import java.util.Date;

/**
 *
 * @author Ismael Jadan
 */
public class frmAuditoria   extends javax.swing.JInternalFrame {
   public boolean grabar = false;
    public boolean modificar = false;
    public boolean grabar1 = false;
    public boolean modificar1 = false;
    public List lista = null;
    public int posicion = 0;
    public int tamano = 0;
    private Container desktopContenedor;
    private Empresa empresaObj;
    Administrador adm;
    private String claveActual;
    private validaciones val;
    private principal principal;
    /** Creates new form frmTarifas1 */
    public frmAuditoria(java.awt.Frame parent, boolean modal) {
//        super(parent, modal);
        initComponents();
    }
  public frmAuditoria(java.awt.Frame parent, boolean modal,Administrador adm1) {
//          super(parent,modal);

        initComponents();
        this.setSize(615, 508);
        empresaObj = new Empresa();
        adm = adm1;
        val = new validaciones();

    }

    public frmAuditoria(java.awt.Frame parent, boolean modal,principal lo,Administrador adm1) {
//          super(parent,modal);
        this.desktopContenedor = lo.contenedor;

        initComponents();

        this.setSize(615, 508);
        adm = adm1;
        val = new validaciones();
        principal = lo;
       llenarUsuarios();


    }

    public void llenarCombo(Usuarios usuario) {

        try {

            String fechInicio = (fechaI.getDate().getYear()+1900) +"-"+(fechaI.getDate().getMonth()+1)+"-"+(fechaI.getDate().getDate())+" 06:00:00";
            String fechFinal = (fechaF.getDate().getYear()+1900) +"-"+(fechaF.getDate().getMonth()+1)+"-"+(fechaF.getDate().getDate())+" 23:59:59";
            String select = "Select o from Auditoria as o "
                    + "where o.usuarios.codigo = '"+usuario.getCodigo()+"' and o.fecha between '"+fechInicio+"' and '"+fechFinal+"' order by o.fecha ";
            if(usuario.getCodigo().equals(-1)){
                 select = "Select o from Auditoria as o "
                    + "where o.fecha between '"+fechInicio+"' and '"+fechFinal+"' order by o.fecha ";
            }
            List<Auditoria> tar = adm.query(select);
            DefaultTableModel dtm = (DefaultTableModel) resultadoAuditoria.getModel();
            dtm.getDataVector().removeAllElements();
            for (Auditoria tarifas : tar) {
                Object[] obj = new Object[7];
                obj[0] = tarifas.getUsuarios().getNombres();
                obj[1] = tarifas.getTabla();
                obj[2] = tarifas.getCampo();
                obj[3] = tarifas.getAccion();
                obj[4] = tarifas.getFecha().toLocaleString();
                obj[5] = tarifas.getMaquina();
                
                dtm.addRow(obj);
            }
           
            resultadoAuditoria.setModel(dtm);
        } catch (Exception ex) {
            Logger.getLogger(frmAuditoria.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
void llenarUsuarios(){
      try {

            List<Usuarios> tar = adm.query("Select o from Usuarios as o ");
            cmbUsuarios.removeAllItems();
            Usuarios todos = new Usuarios(-1);
            todos.setNombres("[TODOS LOS USUARIOS]");
            cmbUsuarios.addItem(todos);
             for (Usuarios usuarios : tar) {
                cmbUsuarios.addItem(usuarios);
            }

        } catch (Exception ex) {
            Logger.getLogger(frmAuditoria.class.getName()).log(Level.SEVERE, null, ex);
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
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultadoAuditoria = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnEliminar1 = new javax.swing.JButton();
        btnSalir1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        fechaF = new com.toedter.calendar.JDateChooser();
        fechaI = new com.toedter.calendar.JDateChooser();
        cmbUsuarios = new javax.swing.JComboBox();

        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Movimiento Realizados");

        jPanel3.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/images_botones/fondoInicio.png")))); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Auditoria Interna del Sistema");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 270, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10));
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Seleccione fecha y Usuario  ..::..");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 20, 250, 13);

        resultadoAuditoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Usuario", "Tabla", "Campo", "Acción", "Fecha", "Maquina"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        resultadoAuditoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultadoAuditoriaMouseClicked(evt);
            }
        });
        resultadoAuditoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                resultadoAuditoriaKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(resultadoAuditoria);
        resultadoAuditoria.getColumnModel().getColumn(0).setResizable(false);
        resultadoAuditoria.getColumnModel().getColumn(0).setPreferredWidth(80);
        resultadoAuditoria.getColumnModel().getColumn(1).setResizable(false);
        resultadoAuditoria.getColumnModel().getColumn(1).setPreferredWidth(45);
        resultadoAuditoria.getColumnModel().getColumn(3).setResizable(false);
        resultadoAuditoria.getColumnModel().getColumn(3).setPreferredWidth(45);

        jLabel4.setText("Fecha Inicial: ");

        jLabel6.setText("Usuario: ");

        btnEliminar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.gif"))); // NOI18N
        btnEliminar1.setMnemonic('E');
        btnEliminar1.setText("Buscar");
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

        jLabel5.setText("Fecha Inicial: ");

        fechaF.setBackground(new java.awt.Color(255, 255, 255));
        fechaF.setDate(new Date());
        fechaF.setDateFormatString("dd-MMM-yyyy");
        fechaF.setFont(new java.awt.Font("Tahoma", 1, 14));

        fechaI.setBackground(new java.awt.Color(255, 255, 255));
        fechaI.setDate(new Date());
        fechaI.setDateFormatString("dd-MMM-yyyy");
        fechaI.setFont(new java.awt.Font("Tahoma", 1, 14));

        cmbUsuarios.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(fechaI, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(fechaF, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(110, 110, 110)
                .addComponent(btnEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSalir1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(cmbUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fechaI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fechaF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(btnEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(cmbUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalir1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalir1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
}//GEN-LAST:event_btnSalir1KeyPressed

    private void btnSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        principal = null;
        empresaObj = null;
        System.gc();
}//GEN-LAST:event_btnSalir1ActionPerformed

    private void resultadoAuditoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resultadoAuditoriaKeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
}//GEN-LAST:event_resultadoAuditoriaKeyPressed

    private void resultadoAuditoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultadoAuditoriaMouseClicked
        // TODO add your handling code here:
    
}//GEN-LAST:event_resultadoAuditoriaMouseClicked

    private void btnEliminar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminar1KeyPressed
        // TODO add your handling code here:
        principal.tecla(evt.getKeyCode());
}//GEN-LAST:event_btnEliminar1KeyPressed

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
        // TODO add your handling code here:
        if (principal.permisos.getIngresar()) {        // TODO add your handling code here:
            try {
                llenarCombo((Usuarios) cmbUsuarios.getSelectedItem());
                resultadoAuditoria.repaint();

            } catch (Exception ex) {
                Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }
}//GEN-LAST:event_btnEliminar1ActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JButton btnSalir1;
    private javax.swing.JComboBox cmbUsuarios;
    private com.toedter.calendar.JDateChooser fechaF;
    private com.toedter.calendar.JDateChooser fechaI;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable resultadoAuditoria;
    // End of variables declaration//GEN-END:variables

}
