/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmConfiguracion.java
 *
 * Created on 03/04/2011, 11:26:12 PM
 */

package peaje.formas;

import hibernate.cargar.Administrador;
import hibernate.cargar.BeanUsuario;
import hibernate.cargar.GeneraXMLPersonal;
import hibernate.cargar.UsuarioActivo;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class frmConfiguracion extends javax.swing.JFrame {
 hibernate.cargar.claves cl = new hibernate.cargar.claves();
    String separador = File.separatorChar + "";
    static UsuarioActivo datosConecta;
    /** Creates new form frmConfiguracion */
    public frmConfiguracion() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        usuarioBase = new javax.swing.JFormattedTextField();
        claveBase = new javax.swing.JPasswordField();
        ipBase = new javax.swing.JFormattedTextField();
        puertoBase = new javax.swing.JFormattedTextField();
        continuar = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        cmbAbre = new javax.swing.JComboBox();
        cmbCierra = new javax.swing.JComboBox();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        btnRestaurar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Configuración Inicial del Sistema");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Reconfiguración en caso de una configuración previa"));
        jPanel1.setLayout(null);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Usuario: ");
        jPanel1.add(jLabel24);
        jLabel24.setBounds(0, 40, 110, 14);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Clave: ");
        jPanel1.add(jLabel25);
        jLabel25.setBounds(0, 60, 110, 14);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("IP: ");
        jPanel1.add(jLabel26);
        jLabel26.setBounds(0, 80, 110, 14);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Puerta de Saldia: ");
        jPanel1.add(jLabel27);
        jLabel27.setBounds(0, 140, 110, 14);

        usuarioBase.setText("root");
        jPanel1.add(usuarioBase);
        usuarioBase.setBounds(120, 40, 160, 20);

        claveBase.setText("jcinform@2020");
        jPanel1.add(claveBase);
        claveBase.setBounds(120, 60, 160, 20);

        ipBase.setText("localhost");
        ipBase.setToolTipText("Si su máquina es el servidor escriba: localhost, caso contrario escriba el nombre del servidor o la dirección IP");
        jPanel1.add(ipBase);
        ipBase.setBounds(120, 80, 160, 20);

        puertoBase.setText("3306");
        jPanel1.add(puertoBase);
        puertoBase.setBounds(120, 100, 70, 20);

        continuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/guardar.png"))); // NOI18N
        continuar.setText("Continuar");
        continuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continuarActionPerformed(evt);
            }
        });
        jPanel1.add(continuar);
        continuar.setBounds(70, 170, 180, 50);

        jLabel29.setText("(localhost o IP del servidor)");
        jPanel1.add(jLabel29);
        jLabel29.setBounds(290, 80, 200, 14);

        jLabel30.setText("(Clave del motor de BD)");
        jPanel1.add(jLabel30);
        jLabel30.setBounds(290, 50, 200, 30);

        jLabel31.setText("(Usuario de de BD)");
        jPanel1.add(jLabel31);
        jLabel31.setBounds(290, 40, 200, 14);

        cmbAbre.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7" }));
        jPanel1.add(cmbAbre);
        cmbAbre.setBounds(120, 120, 40, 20);

        cmbCierra.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7" }));
        jPanel1.add(cmbCierra);
        cmbCierra.setBounds(120, 140, 40, 20);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setText("Puerto: ");
        jPanel1.add(jLabel33);
        jLabel33.setBounds(0, 100, 110, 14);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel34.setText("Puerta de Entrada: ");
        jPanel1.add(jLabel34);
        jLabel34.setBounds(0, 120, 110, 14);

        jLabel35.setText("(Puerto por el que se conecta)");
        jPanel1.add(jLabel35);
        jLabel35.setBounds(200, 100, 200, 20);

        jLabel36.setText("(Puerta que se CIERRA desde este PC");
        jPanel1.add(jLabel36);
        jLabel36.setBounds(170, 140, 200, 20);

        jLabel37.setText("(Puerta que se ABRE desde este PC");
        jPanel1.add(jLabel37);
        jLabel37.setBounds(170, 120, 200, 20);

        btnRestaurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edittrash.gif"))); // NOI18N
        btnRestaurar.setText("Cargar Base de Datos");
        btnRestaurar.setEnabled(false);
        jPanel1.add(btnRestaurar);
        btnRestaurar.setBounds(320, 170, 180, 50);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("Si usted esta viendo esta pantalla, va a reconfigurar, o el archivo de configuración está dañado");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        public void escribir() {
 
        GeneraXMLPersonal pXml = new GeneraXMLPersonal();
        pXml.inicio();
        pXml.generaDocumentoXMLPersonal();
        BeanUsuario beanEmpleado = new BeanUsuario();
        //Establecemos los valores de atributos de Empleado
        beanEmpleado.setDNI("80114918");
        beanEmpleado.setUsuario("" + usuarioBase.getText().trim());
        beanEmpleado.setClave("" + cl.encriptar(claveBase.getText().trim()));
        beanEmpleado.setIp("" + ipBase.getText().trim());
        beanEmpleado.setPuerto("" + puertoBase.getText().trim());
        beanEmpleado.setIn("" + cmbAbre.getSelectedItem().toString());
        beanEmpleado.setOut("" + cmbCierra.getSelectedItem().toString());


        //Generamos documento XML para los valores anteriores
        pXml.llenarEstructuraDocumentoXMLEmpleado(beanEmpleado);
        //obtenemos el documento XML en cadena de texto
        String textoXML = pXml.obtenerTextoXML();
        //grabamos en archivo el documento XML
        pXml.grabaDocumentoXML(textoXML);

    }
    private void continuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continuarActionPerformed
        // TODO add your handling code here:
        escribir();
        GeneraXMLPersonal pXml = new GeneraXMLPersonal();
        pXml.leerXML();
        UsuarioActivo usuario = pXml.user;
        try {
            Administrador adm = new Administrador(usuario);
            adm.query("Select o from Accesos as o where o.codigo = -1 ");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Conexión Erronea corrija y vuelva a intentarlo...", "", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.disable();
        this.dispose();
        new frmPrincipal().show();
        
//        btnAgregar.setEnabled(true);
//        //          btnBuscar.setEnabled(true);
//        btnEliminar.setEnabled(true);
//        btnModificar.setEnabled(true);
//        btnSalir.setEnabled(true);
    }//GEN-LAST:event_continuarActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmConfiguracion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRestaurar;
    private javax.swing.JPasswordField claveBase;
    private javax.swing.JComboBox cmbAbre;
    private javax.swing.JComboBox cmbCierra;
    private javax.swing.JButton continuar;
    private javax.swing.JFormattedTextField ipBase;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JFormattedTextField puertoBase;
    private javax.swing.JFormattedTextField usuarioBase;
    // End of variables declaration//GEN-END:variables

}
