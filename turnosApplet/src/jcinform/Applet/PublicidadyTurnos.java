/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.Applet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.text.StyleContext;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;
import jcinform.Administrador.Administrador;
import jcinform.persistencia.*;
import jcinform.transmision.HiloDeCliente;


/**
 *
 * @author Geovanny
 */
public class PublicidadyTurnos extends javax.swing.JFrame {

    public Administrador adm;
    Empleados emp;
    URL mediaURL = null;
//    videopanel mediaPanel;
    private StyleContext contexto;
    private HTMLEditorKit documento;
    private RTFEditorKit kitrtf;
    private ArrayList videosListado;
    private int actualNumero = 0;
    private int numeroVideos = 0;
    private String directorio;
    MenuItem acerca;
    TrayIcon trayIcon = null;

    /**
     * Creates new form PrincipalVentanilla
     */
    public JTextPane getTexto() {
        return panel;
    }

    public PublicidadyTurnos(Administrador ad, Empleados em) {
        try {
            try {
                initComponents();

                adm = ad;
                emp = em;

                this.setSize(620, 372);
                this.setLocationRelativeTo(null);
                iniciarServidor();
                kitrtf = new RTFEditorKit();

                panel.setEditorKit(kitrtf);

                contexto = new StyleContext();
                documento = new HTMLEditorKit();
                panel.setContentType("text/html");
                HTMLDocument doc = (HTMLDocument) documento.createDefaultDocument();
                panel.setDocument(doc);
                List<Empresa> empresa = adm.query("Select o from Empresa as o ");
                try {
                    directorio = empresa.get(0).getCamara2();
                } catch (Exception e) {
                }



                buscarTurnos();


            } catch (Exception ex) {
                Logger.getLogger(PublicidadyTurnos.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.setExtendedState(this.MAXIMIZED_BOTH);

            this.setVisible(true);

            String videosS = "";
            videosListado = new ArrayList();
            List<Videos> men = adm.query("Select o from Videos as o order by o.orden");
            for (Iterator<Videos> it = men.iterator(); it.hasNext();) {
                Videos vid = it.next();
                videosS = vid.getVideo();
                videosListado.add(videosS);
            }
            numeroVideos = men.size();
//            pantalla.removeAll();
            Dimension dp = new Dimension(pantalla.getWidth(), pantalla.getHeight());
//            video = new windowsMedia(directorio + "" + videosListado.get(actualNumero).toString(), dp);
//            pantalla.add(video, BorderLayout.CENTER);
//            video.empezar();
            actualNumero++;
//           Thread t = new Thread(video.loadPlayerFileRunnable);  
//            t.start(); 
//            t.interrupt();

            Thread appThread = new Thread() {

                public void run() {
                    try {
                        probar();
//                          probar();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Finished on " + Thread.currentThread());
                }
            };
            appThread.start();

            Thread mensajes = new Thread() {

                public void run() {
                    try {
                        mensajes();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Finished on " + Thread.currentThread());
                }
            };
            mensajes.start();



            Image im = new ImageIcon(getClass().getResource("/jcinform/imagenes/icono.png")).getImage();
            try {
                Image image = im;
                PopupMenu men2 = new PopupMenu("JCINFORMAMENU");
                acerca = new MenuItem("Acerca de..");
                acerca.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
//                        acerca ac = new acerca();
//                        ac.setLocation(0, 0);
//                        ac.show();
                    }
                });
                men2.add(acerca);
                acerca = new MenuItem("Salir ");
                acerca.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
                men2.add(acerca);
                trayIcon = new TrayIcon(image, "JCINFORM \n Soluciones Informáticas \n Sistema de Turnos \n www.jcinform.com ", men2);
                if (SystemTray.isSupported()) {
                    SystemTray tray = SystemTray.getSystemTray();
                    trayIcon.setImageAutoSize(true);
                    trayIcon.displayMessage("Bienvenidos al Sistema", " Sistema de Turnos \n www.jcinform.com ", TrayIcon.MessageType.INFO);
                    try {
                        tray.add(trayIcon);
                    } catch (AWTException e) {
                        System.err.println("No se puede añadir el icono al tray :...");
                    }
                }
                this.setIconImage(im);
                men = null;
            } catch (Exception e) {
                System.out.println("ERROR EN ICONOS" + e);
            }
        } catch (Exception ex) {
            Logger.getLogger(PublicidadyTurnos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    int duracionActual = 0;


    void probar() {
//         for (int i = 0; i < numeroVideos; i++) {
        Thread appThread = new Thread() {

            public void run() {
                try {
//                    SwingUtilities.invokeAndWait(doHelloWorld);
                    //Thread.sleep(5000);
//                    TestGarbageColector test = new TestGarbageColector();
//                    test.testGarbageColector();
                } catch (Exception e) {
                    e.printStackTrace();
                    actualNumero = 0;
                }
                System.out.println("Finished on " + Thread.currentThread());
            }
        };
        appThread.start();

    }
  List<Mensajes> mensajes = null;
    void mensajes() {
        try {
            //                            
            //if(mensajes ==null) 
          mensajes = adm.query("Select o from Mensajes as o "
                    + " order by o.orden ");
            int numeroMensajes = mensajes.size();
            if (numeroMensajes <= 0) {
                panel.setText("BIENVENIDOS");
            } else {
                for (Iterator<Mensajes> it1 = mensajes.iterator(); it1.hasNext();) {
                    try {
                        Mensajes mensajes1 = it1.next();
                        panel.setText(mensajes1.getDescripcion());
                        Thread.sleep(20000);

                    } catch (InterruptedException ex) {
                        Logger.getLogger(PublicidadyTurnos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                mensajes = null;
                mensajes();
            }
        } catch (Exception ex) {
            Logger.getLogger(PublicidadyTurnos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pantalla = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        panelBotones = new javax.swing.JPanel();
        pasado1 = new javax.swing.JFormattedTextField();
        pasado2 = new javax.swing.JFormattedTextField();
        pasado3 = new javax.swing.JFormattedTextField();
        pasado4 = new javax.swing.JFormattedTextField();
        pasado5 = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        pasado6 = new javax.swing.JFormattedTextField();
        pasado7 = new javax.swing.JFormattedTextField();
        pasado8 = new javax.swing.JFormattedTextField();
        pasado9 = new javax.swing.JFormattedTextField();
        pasado10 = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panel = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        hora = new javax.swing.JLabel();
        ventanilla2 = new javax.swing.JFormattedTextField();
        turno2 = new javax.swing.JFormattedTextField();
        turno1 = new javax.swing.JFormattedTextField();
        ventanilla1 = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        fondi = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Publicidad JCINFORM");
        setBackground(new java.awt.Color(153, 153, 153));
        setResizable(false);
        setUndecorated(true);

        pantalla.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pantallaLayout = new javax.swing.GroupLayout(pantalla);
        pantalla.setLayout(pantallaLayout);
        pantallaLayout.setHorizontalGroup(
            pantallaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pantallaLayout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pantallaLayout.setVerticalGroup(
            pantallaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pantallaLayout.createSequentialGroup()
                .addGap(272, 272, 272)
                .addComponent(jButton1)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        panelBotones.setBackground(new java.awt.Color(228, 228, 228));
        panelBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelBotones.setLayout(null);

        pasado1.setBackground(new java.awt.Color(240, 240, 240));
        pasado1.setEditable(false);
        pasado1.setForeground(new java.awt.Color(51, 51, 51));
        pasado1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pasado1.setText("-");
        pasado1.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        pasado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasado1ActionPerformed(evt);
            }
        });
        panelBotones.add(pasado1);
        pasado1.setBounds(30, 100, 250, 50);

        pasado2.setBackground(new java.awt.Color(240, 240, 240));
        pasado2.setEditable(false);
        pasado2.setForeground(new java.awt.Color(51, 51, 51));
        pasado2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pasado2.setText("-");
        pasado2.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        pasado2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasado2ActionPerformed(evt);
            }
        });
        panelBotones.add(pasado2);
        pasado2.setBounds(30, 150, 250, 50);

        pasado3.setBackground(new java.awt.Color(240, 240, 240));
        pasado3.setEditable(false);
        pasado3.setForeground(new java.awt.Color(51, 51, 51));
        pasado3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pasado3.setText("-");
        pasado3.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        pasado3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasado3ActionPerformed(evt);
            }
        });
        panelBotones.add(pasado3);
        pasado3.setBounds(30, 200, 250, 50);

        pasado4.setBackground(new java.awt.Color(240, 240, 240));
        pasado4.setEditable(false);
        pasado4.setForeground(new java.awt.Color(51, 51, 51));
        pasado4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pasado4.setText("-");
        pasado4.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        pasado4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasado4ActionPerformed(evt);
            }
        });
        panelBotones.add(pasado4);
        pasado4.setBounds(30, 250, 250, 50);

        pasado5.setBackground(new java.awt.Color(240, 240, 240));
        pasado5.setEditable(false);
        pasado5.setForeground(new java.awt.Color(51, 51, 51));
        pasado5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pasado5.setText("-");
        pasado5.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        pasado5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasado5ActionPerformed(evt);
            }
        });
        panelBotones.add(pasado5);
        pasado5.setBounds(30, 300, 250, 50);

        jLabel4.setBackground(new java.awt.Color(0, 51, 204));
        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 204));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("HISTORIAL DE TURNOS");
        panelBotones.add(jLabel4);
        jLabel4.setBounds(20, 20, 250, 50);

        pasado6.setBackground(new java.awt.Color(240, 240, 240));
        pasado6.setEditable(false);
        pasado6.setForeground(new java.awt.Color(51, 51, 51));
        pasado6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pasado6.setText("-");
        pasado6.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        pasado6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasado6ActionPerformed(evt);
            }
        });
        panelBotones.add(pasado6);
        pasado6.setBounds(290, 100, 250, 50);

        pasado7.setBackground(new java.awt.Color(240, 240, 240));
        pasado7.setEditable(false);
        pasado7.setForeground(new java.awt.Color(51, 51, 51));
        pasado7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pasado7.setText("-");
        pasado7.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        pasado7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasado7ActionPerformed(evt);
            }
        });
        panelBotones.add(pasado7);
        pasado7.setBounds(290, 150, 250, 50);

        pasado8.setBackground(new java.awt.Color(240, 240, 240));
        pasado8.setEditable(false);
        pasado8.setForeground(new java.awt.Color(51, 51, 51));
        pasado8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pasado8.setText("-");
        pasado8.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        pasado8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasado8ActionPerformed(evt);
            }
        });
        panelBotones.add(pasado8);
        pasado8.setBounds(290, 200, 250, 50);

        pasado9.setBackground(new java.awt.Color(240, 240, 240));
        pasado9.setEditable(false);
        pasado9.setForeground(new java.awt.Color(51, 51, 51));
        pasado9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pasado9.setText("-");
        pasado9.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        pasado9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasado9ActionPerformed(evt);
            }
        });
        panelBotones.add(pasado9);
        pasado9.setBounds(290, 250, 250, 50);

        pasado10.setBackground(new java.awt.Color(240, 240, 240));
        pasado10.setEditable(false);
        pasado10.setForeground(new java.awt.Color(51, 51, 51));
        pasado10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pasado10.setText("-");
        pasado10.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        pasado10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasado10ActionPerformed(evt);
            }
        });
        panelBotones.add(pasado10);
        pasado10.setBounds(290, 300, 250, 50);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jcinform/imagenes/empresaMini.gif"))); // NOI18N
        panelBotones.add(jLabel3);
        jLabel3.setBounds(260, 0, 290, 100);

        panel.setBackground(new java.awt.Color(228, 228, 228));
        panel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        panel.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(panel);

        jPanel2.setLayout(null);

        hora.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        hora.setForeground(new java.awt.Color(255, 255, 255));
        hora.setText("SISTEMAS DE TURNOS:     www.jcinform.com         www.tekatronic.com.ec");
        jPanel2.add(hora);
        hora.setBounds(40, 330, 461, 17);

        ventanilla2.setBackground(new java.awt.Color(0, 51, 204));
        ventanilla2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        ventanilla2.setForeground(new java.awt.Color(255, 255, 255));
        ventanilla2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ventanilla2.setText("00");
        ventanilla2.setFont(new java.awt.Font("Verdana", 1, 100)); // NOI18N
        ventanilla2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ventanilla2.setOpaque(false);
        jPanel2.add(ventanilla2);
        ventanilla2.setBounds(350, 200, 190, 120);

        turno2.setBackground(new java.awt.Color(0, 51, 204));
        turno2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        turno2.setForeground(new java.awt.Color(255, 255, 255));
        turno2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        turno2.setText("-");
        turno2.setFont(new java.awt.Font("Verdana", 1, 100)); // NOI18N
        turno2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        turno2.setOpaque(false);
        jPanel2.add(turno2);
        turno2.setBounds(10, 200, 335, 120);

        turno1.setBackground(new java.awt.Color(0, 51, 204));
        turno1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        turno1.setForeground(new java.awt.Color(255, 255, 255));
        turno1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        turno1.setText("-");
        turno1.setToolTipText("");
        turno1.setFont(new java.awt.Font("Verdana", 1, 100)); // NOI18N
        turno1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        turno1.setOpaque(false);
        turno1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turno1ActionPerformed(evt);
            }
        });
        jPanel2.add(turno1);
        turno1.setBounds(10, 70, 335, 120);

        ventanilla1.setBackground(new java.awt.Color(0, 51, 204));
        ventanilla1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        ventanilla1.setForeground(new java.awt.Color(255, 255, 255));
        ventanilla1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ventanilla1.setText("00");
        ventanilla1.setFont(new java.awt.Font("Verdana", 1, 100)); // NOI18N
        ventanilla1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ventanilla1.setOpaque(false);
        jPanel2.add(ventanilla1);
        ventanilla1.setBounds(350, 70, 190, 120);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MÓDULO");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(360, 20, 163, 40);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TURNO");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(100, 20, 154, 40);

        fondi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jcinform/imagenes/fondoPrincipal.png"))); // NOI18N
        fondi.setText(".");
        jPanel2.add(fondi);
        fondi.setBounds(0, 0, 640, 420);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pantalla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBotones, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(1837, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pantalla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE))
                .addContainerGap(542, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//BUSCO SI EXISTE DATOS EN EL ARRAY
    public void buscarTurnos() {
        final HiloDeCliente hc = new HiloDeCliente();

        Thread buscarTurno = new Thread() {

            public void run() {
                try {
                    while (true) {
                        //COMPARAR SI EXISTE MAS DE DOS
                        if (HiloDeCliente.listaCola.size() > 2) {
                            for (Iterator<Turnos> it = HiloDeCliente.listaCola.iterator(); it.hasNext();) {
                                Turnos c = it.next();
                                if (c.getYapedido() == false) {
                                    String code = c.getVentanilla().getCodificacion() + "";
                                    String nume = c.getNumero() + "";
                                    turno2.setText(turno1.getText());
                                    ventanilla2.setText(ventanilla1.getText());
                                    turno1.setForeground(new java.awt.Color(255, 0, 0));
                                    ventanilla1.setForeground(new java.awt.Color(255, 0, 0));
                                    Thread.sleep(500);
                                    turno1.setForeground(new java.awt.Color(255, 255, 255));
                                    ventanilla1.setForeground(new java.awt.Color(255, 255, 255));
                                    turno1.setText(code + nume);
                                    ventanilla1.setText("2");

                                    Thread.sleep(5000);
                                }
                                c.setYapedido(true);
                                HiloDeCliente.listaCola.add(c);
                                Thread.sleep(500);

                            }
                        }





                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        buscarTurno.start();
    }
//    hiloTransportador ht;
    static int repeticiones = 1;
    //CARGO LOS VIDEOS
//    windowsMedia video = null;

//    public void cargarVideos() {
//        try {
//            System.out.println("NO..................................");
//            System.out.println(".................................. " + repeticiones);
//            repeticiones++;
//            int i = 1;
//            List<Videos> men = adm.query("Select o from Videos as o order by o.orden");
//            for (Iterator<Videos> it = men.iterator(); it.hasNext();) {
//                Videos vid = it.next();
//                System.gc();
//                //File f = new File(vid.getVideo());
//                URL url = new URL("file:/" + vid.getVideo());
//                mediaURL = url;
//                Dimension dp = new Dimension(pantalla.getWidth(), pantalla.getHeight());
//                video = new windowsMedia(vid.getVideo(), dp);
//                pantalla.add(video, BorderLayout.CENTER);
//                video.empezar();
//                List<Mensajes> mensajes = adm.query("Select o from Mensajes as o "
//                        + "where o.video.codigo = '" + vid.getCodigo() + "'  order by o.orden ");
//                int numeroMensajes = mensajes.size();
//                Long tiempoVideo = new Long(video.duracion()) / 1000000;
//                Long dividendo = new Long(0);
//                if (numeroMensajes <= 0) {
//                    panel.setText("BIENVENIDOS");
//                    Thread.sleep(tiempoVideo);
//                } else {
//                    dividendo = tiempoVideo / numeroMensajes;
//                    for (Iterator<Mensajes> it1 = mensajes.iterator(); it1.hasNext();) {
//                        Mensajes mensajes1 = it1.next();
//                        panel.setText(mensajes1.getDescripcion());
//                        Thread.sleep(dividendo);
//                        System.out.println("DURACIÓN DE CADA MENSAJE: " + dividendo + " seg.");
//                    }
//                }
//                break;
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(PublicidadyTurnos.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    public void cargarVideos2() {
//        try {
//            System.out.println("NO..................................");
//            System.out.println(".................................. " + repeticiones);
//            repeticiones++;
//            int i = 1;
//            List<Videos> men = adm.query("Select o from Videos as o order by o.orden");
//            for (Iterator<Videos> it = men.iterator(); it.hasNext();) {
//                Videos vid = it.next();
//                System.gc();
//                //File f = new File(vid.getVideo());
//                URL url = new URL("file:/" + vid.getVideo());
//                mediaURL = url;
//                Dimension dp = new Dimension(pantalla.getWidth(), pantalla.getHeight());
//                video = new windowsMedia(vid.getVideo(), dp);
//                pantalla.add(video, BorderLayout.CENTER);
//                video.empezar();
//                List<Mensajes> mensajes = adm.query("Select o from Mensajes as o "
//                        + "where o.video.codigo = '" + vid.getCodigo() + "'  order by o.orden ");
//                int numeroMensajes = mensajes.size();
//                Long tiempoVideo = new Long(video.duracion()) / 1000000;
//                Long dividendo = new Long(0);
//                if (numeroMensajes <= 0) {
//                    panel.setText("BIENVENIDOS");
//                    Thread.sleep(tiempoVideo);
//                } else {
//                    dividendo = tiempoVideo / numeroMensajes;
//                    for (Iterator<Mensajes> it1 = mensajes.iterator(); it1.hasNext();) {
//                        Mensajes mensajes1 = it1.next();
//                        panel.setText(mensajes1.getDescripcion());
//                        Thread.sleep(dividendo);
//                        System.out.println("DURACIÓN DE CADA MENSAJE: " + dividendo + " seg.");
//                    }
//                }
//                TestGarbageColector test = new TestGarbageColector();
//                test.testGarbageColector();
//
//                for (int m = 0; m < 10; m++) {
//                    System.out.print("\n" + (m + 1));
//                    Thread.sleep(1000);
//                }
//
//                i++;
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        try {//espero un minuto antes de avanzar
//            //logoTemporal.setVisible(true);
//
//            for (int m = 0; m < 60; m++) {
//                System.out.print("\n" + (m + 1));
//                Thread.sleep(1000);
//            }
//            //logoTemporal.setVisible(false);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(PublicidadyTurnos.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        cargarVideos();
//    }
//    public void cargarVideos() {
//        try {
//            int i = 1;
//            List<Videos> men = adm.query("Select o from Videos as o order by o.orden");
//            for (Iterator<Videos> it = men.iterator(); it.hasNext();) {
//                Videos vid = it.next();
//                System.gc();
//                //File f = new File(vid.getVideo());
//                URL url = new URL("file:/"+vid.getVideo());
//                mediaURL = url;
//                System.out.println("REPRODUCIENDO... "+vid.getVideo());
//                Dimension dp = new Dimension(pantalla.getWidth(), pantalla.getHeight());
//                videopanel mediaPanel = new videopanel(mediaURL, dp);
//                pantalla.add(mediaPanel);
//                pantalla.setVisible(true);
//                pantalla.repaint();
////                RelojModeloUtil modelo = new RelojModeloUtil();
////                RelojVisual visual = new RelojVisual(modelo);
////                visual.setLocation(100, 100);
////                barraHerramientas.add(visual, 0);
//                List<Mensajes> mensajes = adm.query("Select o from Mensajes as o "
//                        + "where o.video.codigo = '" + vid.getCodigo() + "'  order by o.orden ");
//                int numeroMensajes = mensajes.size();
//                Long tiempoVideo = mediaPanel.getTiempo() / 1000000;
//                Long dividendo = new Long(0);
//                if (numeroMensajes <= 0) {
//                    panel.setText("BIENVENIDOS");
//                    Thread.sleep(tiempoVideo);
//                } else {
//                    dividendo = tiempoVideo / numeroMensajes;
//                    for (Iterator<Mensajes> it1 = mensajes.iterator(); it1.hasNext();) {
//                        Mensajes mensajes1 = it1.next();
//                        panel.setText(mensajes1.getDescripcion());
//                        Thread.sleep(dividendo);
//                        System.out.println("DURACIÓN DE CADA MENSAJE: " + dividendo+" seg.");
//                    }
//                }
//                mediaPanel.detener();
//                mediaPanel = null;
//                //f = null;
//                mediaPanel = null;
//                pantalla.removeAll();
//                TestGarbageColector test = new TestGarbageColector();
//                test.testGarbageColector();
//                Thread.sleep(5000);
//                i++;
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        cargarVideos();
//    }
    private void turno1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turno1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_turno1ActionPerformed

    private void pasado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasado1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pasado1ActionPerformed

    private void pasado2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasado2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pasado2ActionPerformed

    private void pasado3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasado3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pasado3ActionPerformed

    private void pasado4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasado4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pasado4ActionPerformed

    private void pasado5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasado5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pasado5ActionPerformed

    private void pasado6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasado6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pasado6ActionPerformed

    private void pasado7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasado7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pasado7ActionPerformed

    private void pasado8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasado8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pasado8ActionPerformed

    private void pasado9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasado9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pasado9ActionPerformed

    private void pasado10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasado10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pasado10ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        Runnable otro = new Runnable() {

            public void run() {
//                cargarVideos();
            }
        };
        otro.run();

    }//GEN-LAST:event_jButton1ActionPerformed
    void iniciarServidor() {
        Thread cargar = new Thread() {

            public void run() {
                try {
                    System.out.println("EMPEZO.. SERVIDOR");
                    ServidorChat();
                    System.out.println("EMPEZO.. SERVIDOR//");
                } catch (Exception ex) {
                    Logger.getLogger(PublicidadyTurnos.class.getName()).log(Level.SEVERE, null, ex);
                    //lger.logger(frmPrincipal.class.getName(), ex + "");
                }

            }
        };
        cargar.start();
    }
    private DefaultListModel charla = new DefaultListModel();

    public void ServidorChat() {
        try {
            ServerSocket socketServidor = new ServerSocket(5557);
            while (true) {
                Socket cliente = socketServidor.accept();
                Runnable nuevoCliente = new HiloDeCliente(charla, cliente, this);
                Thread hilo = new Thread(nuevoCliente);
                hilo.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PublicidadyTurnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PublicidadyTurnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PublicidadyTurnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PublicidadyTurnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        //NativeInterface.open();
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
//                new PrincipalVentanilla().setVisible(true);
                Thread cargar = new Thread() {

                    public void run() {
                        System.out.println("SI LLEGA A ARRANCAR POR AQUÍ");
                    }
                };
                cargar.start();
            }
        });

        //NativeInterface.runEventPump();
    }

    private Image getImage(byte[] bytes, boolean isThumbnail) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; // File or InputStream
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        if (isThumbnail) {
            param.setSourceSubsampling(4, 4, 0, 0);
        }
        return reader.read(0, param);

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fondi;
    private javax.swing.JLabel hora;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane panel;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel pantalla;
    public javax.swing.JFormattedTextField pasado1;
    public javax.swing.JFormattedTextField pasado10;
    public javax.swing.JFormattedTextField pasado2;
    public javax.swing.JFormattedTextField pasado3;
    public javax.swing.JFormattedTextField pasado4;
    public javax.swing.JFormattedTextField pasado5;
    public javax.swing.JFormattedTextField pasado6;
    public javax.swing.JFormattedTextField pasado7;
    public javax.swing.JFormattedTextField pasado8;
    public javax.swing.JFormattedTextField pasado9;
    public javax.swing.JFormattedTextField turno1;
    public javax.swing.JFormattedTextField turno2;
    public javax.swing.JFormattedTextField ventanilla1;
    public javax.swing.JFormattedTextField ventanilla2;
    // End of variables declaration//GEN-END:variables
}
