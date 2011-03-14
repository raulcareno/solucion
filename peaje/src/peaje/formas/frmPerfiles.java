/*
 * frmRubros.java
 *
 * Created on 1 de noviembre de 2007, 09:10 PM
 */

package peaje.formas;


import hibernate.Accesos;
import hibernate.Global;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.Container;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import peaje.Administrador;

/**
 *
 * @author  Francisco
 */
public class frmPerfiles  extends javax.swing.JDialog {
    public boolean grabar =false;
    public boolean modificar =false;
    private Container desktopContenedor;
    Administrador adm;
    /**
     * Creates new form frmRubros
     */
 public frmPerfiles(java.awt.Frame parent, boolean modal, Administrador adm1) {
        super(parent, modal);
        adm = adm1;
        
        initComponents();llenarCombo();
        this.setSize(440, 428);
    
    }

    public frmPerfiles(java.awt.Frame parent, boolean modal, principal lo, Administrador adm1) {
        super(parent, modal);
        this.desktopContenedor = lo.contenedor;
        adm = adm1;
        
        initComponents();
        llenarCombo();
        this.setSize(440, 428);



    }
//    public frmPerfiles() {
//        initComponents();
//
//        this.setSize(657, 529);
//        llenarCombo();
//    }
//
//
//     public frmPerfiles(Container desktop){
//            this.desktopContenedor = desktop;
//            this.initComponents();
//            this.setSize(657, 529);
//            llenarCombo();
//    }
    public void llenarCombo() {
        try {
//            perfilesList = new ArrayList<Global>();
            List listado = adm.query("Select o from Global as o where o.grupo = 'PER'");
//           cmbPerfil.removeAllItems();
//           Global global = new Global(-1);
//           global.setNombre("[Seleccione]");
//           cmbPerfil.addItem(global);
            for (Iterator<Global> it = listado.iterator(); it.hasNext();) {
                Global global = it.next();
                cmbPerfil.addItem(global);
            }
        }catch(Exception ex){
            Logger.getLogger(frmUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPerfilesRubros = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lblCodigo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        cmbPerfil = new javax.swing.JComboBox();

        setTitle("Accesos a Usuarios");
        setBackground(new java.awt.Color(236, 246, 255));
        getContentPane().setLayout(null);

        tablaPerfilesRubros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Pantalla", "Ingresar", "Agregar", "Modificar", "Eliminar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaPerfilesRubros.setSelectionBackground(new java.awt.Color(236, 246, 255));
        tablaPerfilesRubros.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tablaPerfilesRubros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPerfilesRubrosMouseClicked(evt);
            }
        });
        tablaPerfilesRubros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaPerfilesRubrosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaPerfilesRubros);
        tablaPerfilesRubros.getColumnModel().getColumn(0).setMinWidth(1);
        tablaPerfilesRubros.getColumnModel().getColumn(0).setPreferredWidth(1);
        tablaPerfilesRubros.getColumnModel().getColumn(0).setMaxWidth(1);
        tablaPerfilesRubros.getColumnModel().getColumn(2).setMaxWidth(60);
        tablaPerfilesRubros.getColumnModel().getColumn(3).setMaxWidth(60);
        tablaPerfilesRubros.getColumnModel().getColumn(4).setMaxWidth(60);
        tablaPerfilesRubros.getColumnModel().getColumn(5).setMaxWidth(60);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 80, 400, 240);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png"))); // NOI18N
        btnNuevo.setText("Guardar");
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo);
        btnNuevo.setBounds(240, 10, 60, 50);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salir.png"))); // NOI18N
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
        btnSalir.setBounds(310, 10, 60, 50);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(20, 340, 400, 70);
        getContentPane().add(lblCodigo);
        lblCodigo.setBounds(180, 20, 50, 0);

        jPanel3.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/images_botones/fondoInicio.png")))); // NOI18N
        jPanel3.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Catálogo de Accesos ..::..");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 200, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10));
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Permitir o Denegar accesos a pantallas ...");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 20, 230, 13);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 660, 40);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Privilegios:");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(20, 50, 70, 14);

        jButton1.setText("Listar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(349, 50, 70, 23);

        getContentPane().add(cmbPerfil);
        cmbPerfil.setBounds(100, 50, 240, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
// TODO add your handling code here:
      this.setVisible(false);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
// TODO add your handling code here:
        
try{
//                     baseComun bs = new baseComun();
                    Accesos mes = new Accesos();
                    int filas = tablaPerfilesRubros.getRowCount();
                    for (int i = 0; i < filas; i++){
                        mes.setCodigo((Integer) tablaPerfilesRubros.getValueAt(i,0));
                        mes.setPantalla((String) tablaPerfilesRubros.getValueAt(i,1));
                        mes.setIngresar((Boolean) tablaPerfilesRubros.getValueAt(i,2));
                        mes.setAgregar((Boolean) tablaPerfilesRubros.getValueAt(i,3));
                        mes.setModificar((Boolean) tablaPerfilesRubros.getValueAt(i,4));
                        mes.setEliminar((Boolean) tablaPerfilesRubros.getValueAt(i,5));
                        mes.setPerfil((Global)cmbPerfil.getSelectedItem());
                        adm.actualizar(mes);
                    }
                    JOptionPane.showMessageDialog(this, "Registros Actualizados");
}catch(Exception e){
    JOptionPane.showMessageDialog(this, "Error al Almacenar Registros \n Revise que los datos est�n ingresados correctamente");
}

        
    }//GEN-LAST:event_btnNuevoActionPerformed

private void tablaPerfilesRubrosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaPerfilesRubrosKeyPressed
    // TODO add your handling code here:
    if(evt.getKeyCode()==evt.VK_DELETE){
        int fil = tablaPerfilesRubros.getSelectedRow();
        //baseComun bs = new baseComun();
        //Perfil rub = (Perfil) tableRubros.getValueAt(fil,0);
        //bs.eliminarObjeto(RubrosAsignados.class,rub.getAsigCodigo());
        DefaultTableModel dtm = (DefaultTableModel)tablaPerfilesRubros.getModel();
        dtm.removeRow(fil);
        tablaPerfilesRubros.setModel(dtm);

    }
}//GEN-LAST:event_tablaPerfilesRubrosKeyPressed

private void tablaPerfilesRubrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPerfilesRubrosMouseClicked
    // TODO add your handling code here:
    //        int fila = tableRubros.getSelectedRow();
    //        this.lblCodigo.setText(Integer.parseInt(tableRubros.getValueAt(fila,0).toString())+"");
    //        this.txtNombre.setText((String) this.tableRubros.getValueAt(fila,1));
    //        boolean val = new Boolean(tableRubros.getValueAt(fila,2).toString());
    //
    //        this.chkFacturado.setSelected(Boolean.valueOf(val));
}//GEN-LAST:event_tablaPerfilesRubrosMouseClicked

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    // TODO add your handling code here:
    listar();
//    tablaPerfilesRubros.repaint();2384-198
}//GEN-LAST:event_jButton1ActionPerformed
    
   public void listar(){
    Administrador adm = new Administrador();
       List lista = adm.query("Select o from Accesos as o where perfil.codigo = '"+((Global)cmbPerfil.getSelectedItem()).getCodigo()+"' order by o.pantalla ");
       DefaultTableModel dtm = (DefaultTableModel)tablaPerfilesRubros.getModel();
       dtm.getDataVector().removeAllElements();
       for (Iterator it = lista.iterator(); it.hasNext();) {
           Accesos elem = (Accesos) it.next();
           Object obj[] = new Object[6];
           obj[0]= elem.getCodigo();
           obj[1] = elem.getPantalla();
           obj[2] = elem.getIngresar();
           obj[3] = elem.getAgregar();
           obj[4] = elem.getModificar();
           obj[5] = elem.getEliminar();
           dtm.addRow(obj);
       }
       tablaPerfilesRubros.setModel(dtm);
   } 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cmbPerfil;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JTable tablaPerfilesRubros;
    // End of variables declaration//GEN-END:variables
     private List<Global> perfilesList = new ArrayList<Global>();

    public List<Global> getPerfilesList() {
        return perfilesList;
    }

    public void setPerfilesList(List<Global> perfilesList) {
        this.perfilesList = perfilesList;
    }

   
     
  
}
