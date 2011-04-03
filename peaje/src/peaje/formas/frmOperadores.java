package peaje.formas;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;
import hibernate.cargar.Administrador;
import hibernate.cargar.validaciones;
import hibernate.*;

;
//import org.eclipse.persistence.internal.history.HistoricalDatabaseTable;

/**
 *
 * @author geovanny
 */
public class frmOperadores  extends javax.swing.JInternalFrame {

    /** Creates new form frmUsuarios */
    public boolean grabar = false;
    public boolean modificar = false;
    public List lista = null;
    public int posicion = 0;
    public int tamano = 0;
    private Container desktopContenedor;
    private Usuarios usuarioObj;
    Administrador adm;
    private String claveActual;
    private validaciones val;
    private principal principal;
    claves cl = new claves();

    /** Creates new form frmProfesores */
    public frmOperadores(java.awt.Frame parent, boolean modal, Administrador adm1) {
//        super(parent, modal);
        adm = adm1;
        llenarCombo();
        initComponents();
        this.setSize(615, 508);
        usuarioObj = new Usuarios();

        val = new validaciones();
    }

    public frmOperadores(java.awt.Frame parent, boolean modal, principal lo, Administrador adm1) {
//        super(parent, modal);
        this.desktopContenedor = lo.contenedor;
        adm = adm1;

        initComponents();
                llenarCombo();
        this.setSize(615, 508);
        usuarioObj = new Usuarios();

        val = new validaciones();
        principal = lo;
    }

    public void llenarCombo() {
try {
            List listado = adm.query("Select o from Global as o where o.grupo = 'PER'");
             Object [] listData = new Object[listado.size()];
            int i=0;
            for (Iterator<Global> it = listado.iterator(); it.hasNext();) {
                Global global = it.next();
                listData[i]= global;
                i++;
            }
            perfil.setListData(listData);
        }catch(Exception ex){
            Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// <editor-fold defaultstate="collapsed" desc="PROPIEDADES">
    public String getClaveActual() {
        return claveActual;
    }

    public void setClaveActual(String claveActual) {
        this.claveActual = claveActual;
    }

    public Usuarios getUsuarioObj() {
        return usuarioObj;
    }

    public void setUsuarioObj(Usuarios usuarioObj) {
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
        cedula.setEditable(estado);
        nombres.setEditable(estado);
        direccion.setEditable(estado);
        usuario.setEditable(estado);
        clave.setEditable(estado);
        confirmar.setEditable(estado);

    }

    public void limpiar() {
        String estado = "";
        codigo.setText("");
        cedula.setText(estado);
        nombres.setText(estado);
        direccion.setText(estado);
        usuario.setText(estado);
        clave.setText(estado);
        confirmar.setText(estado);

    }
  public void llenar(Usuarios usuario001) {
      codigo.setText(usuario001.getCodigo()+"");
        cedula.setText(usuario001.getCedula());
        nombres.setText(usuario001.getNombres());
        direccion.setText(usuario001.getDireccion());
        usuario.setText(usuario001.getUsuario());
        clave.setText(usuario001.getClave());
        confirmar.setText(usuario001.getClave());

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

        formaUsuarios = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        codigoBuscar = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        busquedaTabla = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nombres = new javax.swing.JFormattedTextField();
        direccion = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        cedula = new javax.swing.JFormattedTextField();
        usuario = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        clave = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        confirmar = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        perfil = new javax.swing.JList();
        codigo = new javax.swing.JLabel();
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

        setTitle("Operadores y Usuarios del Sistema");
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
        jLabel8.setText("Catálogo de Operadores ..::..");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 0, 270, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10));
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Busqueda, Creación,Modificación,  Operadores u Usuarios ..::..");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 20, 350, 13);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 420, 40);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        jLabel1.setForeground(new java.awt.Color(0, 0, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("CI/RUC:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 10, 90, 14);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Dirección:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 50, 90, 14);

        nombres.setEditable(false);
        nombres.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nombresFocusLost(evt);
            }
        });
        nombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombresKeyPressed(evt);
            }
        });
        jPanel1.add(nombres);
        nombres.setBounds(110, 30, 220, 20);

        direccion.setEditable(false);
        direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionKeyPressed(evt);
            }
        });
        jPanel1.add(direccion);
        direccion.setBounds(110, 50, 220, 20);

        jLabel12.setForeground(new java.awt.Color(0, 0, 153));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Nombres:");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(10, 30, 90, 14);

        cedula.setEditable(false);
        cedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cedulaKeyPressed(evt);
            }
        });
        jPanel1.add(cedula);
        cedula.setBounds(110, 10, 100, 20);

        usuario.setEditable(false);
        usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usuarioKeyPressed(evt);
            }
        });
        jPanel1.add(usuario);
        usuario.setBounds(110, 70, 110, 20);

        jLabel5.setForeground(new java.awt.Color(0, 0, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Usuario:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 70, 90, 14);

        clave.setEditable(false);
        clave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                claveKeyPressed(evt);
            }
        });
        jPanel1.add(clave);
        clave.setBounds(110, 90, 110, 20);

        jLabel6.setForeground(new java.awt.Color(0, 0, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Clave:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 90, 90, 14);

        confirmar.setEditable(false);
        confirmar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                confirmarFocusLost(evt);
            }
        });
        confirmar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                confirmarKeyPressed(evt);
            }
        });
        jPanel1.add(confirmar);
        confirmar.setBounds(110, 110, 110, 20);

        jLabel7.setForeground(new java.awt.Color(0, 0, 153));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Confirmar:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(0, 110, 100, 14);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Este Usuario es:");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jPanel1.add(jLabel13);
        jLabel13.setBounds(0, 130, 100, 40);

        perfil.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                perfilValueChanged(evt);
            }
        });
        perfil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                perfilKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(perfil);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(110, 130, 220, 50);

        codigo.setFont(new java.awt.Font("Tahoma", 0, 3));
        codigo.setText("0");
        jPanel1.add(codigo);
        codigo.setBounds(80, 160, 10, 14);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(20, 50, 370, 190);

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
        btnBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBuscarKeyPressed(evt);
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

        getContentPane().add(jPanel4);
        jPanel4.setBounds(20, 240, 370, 70);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed

        // TODO add your handling code here:
        if (principal.permisos.getAgregar()) {
            if (grabar == false) {
                usuarioObj = new Usuarios(0);
                this.btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png")));
                this.btnAgregar.setLabel("Guardar");
                this.btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png")));
                this.btnModificar.setLabel("Cancelar");
                grabar = true;
                modificar = false;
                habilitar(true);
                limpiar();
                llenar(usuarioObj);
                cedula.requestFocusInWindow();
                btnAgregar.setMnemonic('G');
                btnModificar.setMnemonic('C');
                btnBuscar.setEnabled(false);
               cedula.requestFocusInWindow();

            } else if (grabar == true) {
                if (cedula.getText().isEmpty() || nombres.getText().trim().isEmpty() || usuario.getText().trim().isEmpty() || clave.getText().isEmpty() || confirmar.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Registre los campos requeridos ...!");
                } else {
                     usuarioObj.setGlobal((Global)perfil.getSelectedValue());
                     usuarioObj.setClave(cl.encriptar(clave.getText()));
                     usuarioObj.setCedula(cedula.getText());
                     usuarioObj.setNombres(nombres.getText());
                     usuarioObj.setDireccion(direccion.getText());
                     usuarioObj.setUsuario(usuario.getText());
                     usuarioObj.setCodigo(Integer.parseInt(codigo.getText()));
                     if(usuarioObj.getCodigo().equals(0)){
                         usuarioObj.setCodigo(null);
                     }
                    if (modificar) {
                        try {
                            adm.actualizar(usuarioObj);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en actualizar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                    } else {
                        try {
                            adm.guardar(usuarioObj);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error en guardar Registro ...! \n" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
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
        } else {
            JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA REALIZAR ESTA ACCIÓN");
        }


    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:

        if (grabar == false) {
            if (principal.permisos.getModificar()) {
                if (cedula.getText().trim().isEmpty()) {
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

            } else {
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
        if (principal.permisos.getEliminar()) {        // TODO add your handling code here:
            try {
                adm.eliminarObjeto(Usuarios.class, usuarioObj.getCodigo());
                this.limpiar();
            } catch (Exception ex) {
                Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
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
//                    nombresBuscar.setText("");




}//GEN-LAST:event_btnBuscarActionPerformed

    private void codigoBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoBuscarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {

            Thread cargar = new Thread()  {

                public void run() {
                    principal.procesando.setVisible(true);


                    try {
                        List<Usuarios> usuarios = adm.query("Select o from Usuarios as o where o.nombres like '" + codigoBuscar.getText().trim() + "%' ");
                        Object[] obj = new Object[4];
                        DefaultTableModel dtm = (DefaultTableModel) busquedaTabla.getModel();
                        dtm.getDataVector().removeAllElements();
                        for (Iterator<Usuarios> it = usuarios.iterator(); it.hasNext();) {
                            Usuarios glbusuario = it.next();
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
                this.usuarioObj = (Usuarios) adm.buscarClave((Integer) busquedaTabla.getValueAt(fila, 0), Usuarios.class);
                usuarioObj.setClave(cl.desencriptar(usuarioObj.getClave()));
                 Global g =  usuarioObj.getGlobal();
                llenar(usuarioObj);
                perfil.setSelectedValue(g+"",true);

                claveActual = usuarioObj.getClave();

                formaUsuarios.dispose();
            } catch (Exception ex) {
                Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        JOptionPane.showMessageDialog(this, usuarioObj);
    }//GEN-LAST:event_busquedaTablaMouseClicked

    private void busquedaTablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaTablaKeyPressed


        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            try {
                Global g = new Global();
                int fila = busquedaTabla.getSelectedRow();
                this.usuarioObj = (Usuarios) adm.buscarClave((Integer) busquedaTabla.getValueAt(fila, 0), Usuarios.class);
                usuarioObj.setClave(cl.desencriptar(usuarioObj.getClave()));
                g =  usuarioObj.getGlobal();
              llenar(usuarioObj);
                ListModel datos = perfil.getModel();
                   for (int i = 0; i < datos.getSize(); i++) {
                    Global object = (Global) datos.getElementAt(i);
                       if(g.getCodigo().equals(object.getCodigo())){
                            perfil.setSelectedIndex(i);
                        break;
                       }

                }
                
//                perfil.setSelectedValue(g,true);
                formaUsuarios.dispose();
                claveActual = usuarioObj.getClave();

            } catch (Exception ex) {
                Logger.getLogger(frmOperadores.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            formaUsuarios.dispose();
        }


    }//GEN-LAST:event_busquedaTablaKeyPressed

    private void cedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cedulaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            cedula.nextFocus();
        }
    }//GEN-LAST:event_cedulaKeyPressed

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

    private void usuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usuarioKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            usuario.nextFocus();
        }
    }//GEN-LAST:event_usuarioKeyPressed

    private void claveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_claveKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            clave.nextFocus();
        }
    }//GEN-LAST:event_claveKeyPressed

    private void confirmarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confirmarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            confirmar.nextFocus();
        }
    }//GEN-LAST:event_confirmarKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.setVisible(false);
        }
    }//GEN-LAST:event_formKeyReleased

    private void nombresFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nombresFocusLost
        // TODO add your handling code here:
        if(usuario.getText().trim().isEmpty()){
            int vacio = nombres.getText().indexOf(" ");
            usuario.setText(nombres.getText().replace(" ","").substring(0, vacio+1).toLowerCase());
        }
    }//GEN-LAST:event_nombresFocusLost

    private void confirmarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_confirmarFocusLost
        // TODO add your handling code here:
        if(!clave.getText().equals(confirmar.getText())){
            JOptionPane.showMessageDialog(this, "CLAVES NO COINCIDEN");
            clave.setText("");
            confirmar.setText("");
            clave.requestFocusInWindow();
        }
    }//GEN-LAST:event_confirmarFocusLost

    private void perfilValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_perfilValueChanged
        // TODO add your handling code here:
//        listar();
//        tablaPerfilesRubros.repaint();
}//GEN-LAST:event_perfilValueChanged

    private void btnBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBuscarKeyPressed
        // TODO add your handling code here:
         principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_btnBuscarKeyPressed

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

    private void perfilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_perfilKeyPressed
        // TODO add your handling code here:
         principal.tecla(evt.getKeyCode());
    }//GEN-LAST:event_perfilKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JTable busquedaTabla;
    private javax.swing.JFormattedTextField cedula;
    private javax.swing.JPasswordField clave;
    private javax.swing.JLabel codigo;
    private javax.swing.JFormattedTextField codigoBuscar;
    private javax.swing.JPasswordField confirmar;
    private javax.swing.JFormattedTextField direccion;
    private javax.swing.JDialog formaUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JFormattedTextField nombres;
    private javax.swing.JList perfil;
    private javax.swing.JFormattedTextField usuario;
    // End of variables declaration//GEN-END:variables
}
