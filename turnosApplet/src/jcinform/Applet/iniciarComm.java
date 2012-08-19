/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.Applet;

import java.applet.Applet;
import java.awt.*;
import java.io.File;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import jcinform.Administrador.Administrador;
import jcinform.Administrador.UsuarioActivo;
import jcinform.Administrador.claves;
import jcinform.Administrador.logger;
import jcinform.persistencia.*;

public class iniciarComm extends Applet implements Runnable {

    Administrador adm;
    Empleados usuario;
    Turnos turnoSeleccionado;
    java.util.List<VentanillasEmpleados> ventanillasActuales;
    String ventanillasActualesString = "";
    java.util.List<VentanillasEmpleados> ventanillasActualesOpcionales;
    String ventanillasActualesStringOpcionales = "";
    int actualOpcional;
    Accesos ac = new Accesos();
    String separador = File.separatorChar + "";
    public static ArrayList puertoListo;
    public static Empresa empresaObj;
    private Socket socket;
    public Boolean sinconexion = false;
    public int totalAtencionesxVentanilla = 1;
    claves cl = new claves();
    MenuItem acerca;
    TrayIcon trayIcon = null;
    String ubicacionDirectorio = jcinform.Administrador.UsuarioActivo.getUbicacionDirectorio();
    public int conteo = 0;
    public boolean abandonado = false;
    logger lger = new logger();
    public String message;
    public Font messageF;
    public int messageX;
    public int messageY;
    public int messageW;
    public URL url_;
    int speed;
    public Thread t;
    public boolean active;
    public Color txtCo;
    public Color bgCo;
    public boolean expired;
    public Dimension lastS;
    Image im;
    Graphics gr;
    Thread cargar = new Thread() {

        @Override
        public void run() {
            boolean sinconexion = true;
            while(sinconexion){
            try {
                String s = getParameter("empleado");
                UsuarioActivo usr = new UsuarioActivo();
                UsuarioActivo.setNombre("root");
                UsuarioActivo.setContrasenia("j/eDF6Vyqmgzcb6udqpFMA==");
                UsuarioActivo.setPuerto("3306");
                UsuarioActivo.setIp(message);
                try {
                    adm = new Administrador(usr);
                    adm.query("Select o from Empresa as o ");
                    sinconexion = false;
                } catch (Exception ab) {
                    System.out.println("FALTA CONECTARSE: "+ab);
                }

            } catch (Exception ex) {
                Logger.getLogger(iniciarComm.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }
    };

    public void init() {
        TextField label = new TextField("123");
        try {

            label.setText("CONECTADO");
        } catch (Exception e) {
            label.setText("SIN CONEXIÓN");
        }
        label.setBounds(20, 20, 500, 30);
        JButton bo = new JButton("VER MENSAJE");
        bo.setBounds(20, 80, 200, 30);
        bo.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showConfirmDialog(null, message);
            }
        });
       // add(label);
        add(bo);
        cargar.start();
    }

    public String[][] getParameterInfo() {
        String as[][] = {
            {
                "msg", "String", "Message to display (New!)"
            }, {
                "usuario", "String", "Usuario "
            }, {
                "clave", "String", "Clave"
            }, {
                "puerto", "String", "Puerto"
            }, {
                "ip", "String", "Dirección IP"
            }
        };
        return as;
    }

    public String getAppletInfo() {
        return "ticker.java, V b1.1, 11/28/95 by Thomas Wendt, http://www.uni-kassel.de/fb16/ipm/mt/staff/thwendte.html";
    }

    public Color readColor(String s, Color color) {
        if (s == null) {
            return color;
        }
        StringTokenizer stringtokenizer = new StringTokenizer(s, ",");
        try {
            int i = Integer.valueOf(stringtokenizer.nextToken()).intValue();
            int j = Integer.valueOf(stringtokenizer.nextToken()).intValue();
            int k = Integer.valueOf(stringtokenizer.nextToken()).intValue();
            return new Color(i, j, k);
        } catch (Exception _ex) {
            return color;
        }
    }

    public void createParams() {
        int i = size().width;
        int j = size().height;
        lastS.width = i;
        lastS.height = j;
        if (gr != null) {
            gr.finalize();
        }
        if (im != null) {
            im = null;
        }
        byte byte0 = 14;
        Font font = new Font("TimesRoman", 1, byte0);
        setFont(font);
        FontMetrics fontmetrics = getFontMetrics(font);
        int k = fontmetrics.getHeight();
        k = (byte0 * (j - 10)) / k;
        messageF = new Font("TimesRoman", 1, k);
        FontMetrics fontmetrics1 = getFontMetrics(messageF);
        k = fontmetrics1.getHeight();
        messageX = i;
        messageY = (j - k >> 1) + fontmetrics1.getAscent();
        messageW = fontmetrics1.stringWidth(message);
        im = createImage(i, j);
        gr = im.getGraphics();
    }

    public void paint(Graphics g) {
        update(g);
    }

    public synchronized void update(Graphics g) {
    }

    public synchronized void nextPos() {
        messageX -= speed;
        if (messageX + messageW < 0) {
            messageX = lastS.width;
        }
        repaint();
    }

    public void run() {
        if (expired) {
            return;
        }
        Thread.currentThread().setPriority(1);
        while (active) {
            nextPos();
            try {
                Thread.sleep(100L);
            } catch (InterruptedException _ex) {
            }
        }
    }

    public void start() {
        if (!active) {
            t = new Thread(this);
            active = true;
            t.start();
        }
    }

    public void stop() {
        active = false;
        t = null;
    }

    public boolean mouseUp(Event event, int i, int j) {
        if (url_ != null) {
            getAppletContext().showDocument(url_);
        }
        return true;
    }

    public iniciarComm() {

        active = false;
        expired = false;
        lastS = new Dimension(1, 1);
    }
}
