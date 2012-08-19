package jcinform.transmision;

import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Date;


import java.util.HashSet;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import jcinform.Administrador.Administrador;
import jcinform.Applet.PublicidadyTurnos;
import jcinform.persistencia.Cola;
import jcinform.persistencia.Turnos;
 
/**
 * Hilo encargado de atender a un cliente.
 *
 * @author Chuidiang
 */
public class HiloDeCliente implements Runnable, ListDataListener {
public HiloDeCliente(){}
    /**
     * Lista en la que se guarda toda la charla
     */
    private DefaultListModel charla;
    /**
     * Socket al que est� conectado el cliente
     */
    private Socket socket;
    private PublicidadyTurnos princip;
    /**
     * Para lectura de datos en el socket
     */
    private DataInputStream dataInput;
    /**
     * Para escritura de datos en el socket
     */
    private DataOutputStream dataOutput;
private Administrador adm;
    /**
     * Crea una instancia de esta clase y se suscribe a cambios en la charla.
     *
     * @param charla La lista de textos que componen la charla del chat
     * @param socket Socket con el cliente.
     */
    public HiloDeCliente(DefaultListModel charla, Socket socket, PublicidadyTurnos principal) {
        this.charla = charla;
        this.socket = socket;
        try {
            dataInput = new DataInputStream(socket.getInputStream());
            dataOutput = new DataOutputStream(socket.getOutputStream());
            princip = principal;
            if(adm == null){
                adm = princip.adm;
                System.out.println("inicializo el adm: ");
            }
            charla.addListDataListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 
     public static Set<Turnos> listaCola = new HashSet<Turnos>();
 
     
     public void run() {
        try {
            while (true) {
                String texto = dataInput.readUTF();
                synchronized (charla) {
                    int guion = texto.indexOf("-");
                    int asterisco = texto.indexOf("*");
                    Integer codigoTur = new Integer(texto.substring(0, guion));
                    Turnos turnoCola =(Turnos) adm.buscarClave(codigoTur, Turnos.class);
                    
                    Date fec = adm.Date();
                    Cola col = new Cola(adm.getNuevaClave("Cola", "codigocol"));
                    col.setCodigotur(turnoCola);
                    col.setFecha(fec);
                    col.setHora(fec);
                    col.setEstado(Boolean.FALSE); 
                    adm.guardar(col);
                    turnoCola.setYapedido(false);
//                    listaCola.add(col.getCodigotur());
                    String turnoMostrar = texto.substring(guion+1,asterisco);
                    String ventanilla = texto.substring(asterisco+1,texto.length());
                    charla.addElement(turnoMostrar);
                     if(turnoMostrar.equals(princip.turno2.getText())){
                         princip.turno2.setOpaque(true);
                         princip.ventanilla2.setOpaque(true);
                        princip.turno2.setBackground(new java.awt.Color(255, 0, 0));
                         princip.ventanilla2.setBackground(new java.awt.Color(255, 0, 0));
                         Thread.sleep(500);
                         princip.turno2.setBackground(new java.awt.Color(0, 51, 204));
                         princip.ventanilla2.setBackground(new java.awt.Color(0, 51, 204));
                         princip.turno2.setOpaque(false);
                         princip.ventanilla2.setOpaque(false);
                     }else if(turnoMostrar.equals(princip.turno1.getText())){
                         princip.turno1.setOpaque(true);
                         princip.ventanilla1.setOpaque(true);
                         princip.turno1.setBackground(new java.awt.Color(255, 0, 0));
                         princip.ventanilla1.setBackground(new java.awt.Color(255, 0, 0));
                         Thread.sleep(500);
                         princip.turno1.setBackground(new java.awt.Color(0, 51, 204));
                         princip.ventanilla1.setBackground(new java.awt.Color(0, 51, 204));
                         princip.turno1.setOpaque(false);
                         princip.ventanilla1.setOpaque(false);
                     }else{
                         princip.turno1.setOpaque(true);
                         princip.ventanilla1.setOpaque(true);
                         princip.turno2.setOpaque(true);
                         princip.ventanilla2.setOpaque(true);
                         
                            princip.turno2.setText(princip.turno1.getText());
                            princip.ventanilla2.setText(princip.ventanilla1.getText());
                            princip.turno1.setText(turnoMostrar);
                            princip.ventanilla1.setText(ventanilla);
                            princip.turno1.setBackground(new java.awt.Color(255, 0, 0));
                            princip.ventanilla1.setBackground(new java.awt.Color(255, 0, 0));
                            Thread.sleep(500);
                            princip.turno1.setBackground(new java.awt.Color(0, 51, 204));
                            princip.ventanilla1.setBackground(new java.awt.Color(0, 51, 204));
                            princip.turno1.setOpaque(false);
                           princip.ventanilla1.setOpaque(false);
                           princip.turno2.setOpaque(false);
                         princip.ventanilla2.setOpaque(false);
                            
                     }
                    buscarEnTabla(turnoMostrar+" - "+ventanilla);
                   
//                    princip.turno1.setText(turnoMostrar); 
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
public void buscarEnTabla(String turno){
            int numero = 0;
            if(turno.contains("----")){
                    return;
            }
                if(princip.pasado1.getText().contains(""+turno.substring(0, 1))){
                    numero = 1;
                }else if(princip.pasado2.getText().contains(""+turno.substring(0, 1))){
                    numero = 2;
                }else if(princip.pasado3.getText().contains(""+turno.substring(0, 1))){
                    numero = 3;
                }else if(princip.pasado4.getText().contains(""+turno.substring(0, 1))){
                    numero = 4;
                }else if(princip.pasado5.getText().contains(""+turno.substring(0, 1))){
                    numero = 5;
                }else if(princip.pasado6.getText().contains(""+turno.substring(0, 1))){
                    numero = 6;
                }else if(princip.pasado7.getText().contains(""+turno.substring(0, 1))){
                    numero = 7;
                }else if(princip.pasado8.getText().contains(""+turno.substring(0, 1))){
                    numero = 8;
                }else if(princip.pasado9.getText().contains(""+turno.substring(0, 1))){
                    numero = 9;
                }else if(princip.pasado10.getText().contains(""+turno.substring(0, 1))){
                    numero = 10;
                }
                if(princip.pasado1.getText().equals("-") && numero == 0){
                    princip.pasado1.setText(turno);
                    return;
                }else if(princip.pasado2.getText().equals("-") && numero == 0){
                    princip.pasado2.setText(turno);
                    return;
                }else if(princip.pasado3.getText().equals("-") && numero == 0){
                    princip.pasado3.setText(turno);
                    return;
                }else if(princip.pasado4.getText().equals("-") && numero == 0){
                    princip.pasado4.setText(turno);
                    return;
                }else if(princip.pasado5.getText().equals("-") && numero == 0){
                    princip.pasado5.setText(turno);
                    return;
                }else if(princip.pasado6.getText().equals("-") && numero == 0){
                    princip.pasado6.setText(turno);
                    return;
                }else if(princip.pasado7.getText().equals("-") && numero == 0){
                    princip.pasado7.setText(turno);
                    return;
                } else if(princip.pasado8.getText().equals("-") && numero == 0){
                    princip.pasado8.setText(turno);
                    return;
                } else if(princip.pasado9.getText().equals("-") && numero == 0){
                    princip.pasado9.setText(turno);
                    return;
                } else if(princip.pasado10.getText().equals("-") && numero == 0){
                    princip.pasado10.setText(turno);
                    return;
                }             
                if(princip.pasado1.getText().contains(""+turno.substring(0, 1))){
                    princip.pasado1.setText(turno);
                }else if(princip.pasado2.getText().contains(""+turno.substring(0, 1))){
                    princip.pasado2.setText(turno);
                }else if(princip.pasado3.getText().contains(""+turno.substring(0, 1))){
                    princip.pasado3.setText(turno);
                }else if(princip.pasado4.getText().contains(""+turno.substring(0, 1))){
                    princip.pasado4.setText(turno);
                }else if(princip.pasado5.getText().contains(""+turno.substring(0, 1))){
                    princip.pasado5.setText(turno);
                }else if(princip.pasado6.getText().contains(""+turno.substring(0, 1))){
                    princip.pasado6.setText(turno);
                }else if(princip.pasado7.getText().contains(""+turno.substring(0, 1))){
                    princip.pasado7.setText(turno);
                }else if(princip.pasado8.getText().contains(""+turno.substring(0, 1))){
                    princip.pasado8.setText(turno);
                }else if(princip.pasado9.getText().contains(""+turno.substring(0, 1))){
                    princip.pasado9.setText(turno);
                }else if(princip.pasado10.getText().contains(""+turno.substring(0, 1))){
                    princip.pasado10.setText(turno);
                }
                
}
     
    public int buscarSiEstayEnDondeEsta(String turno, String ventanilla){
        
        if(turno.equals(princip.turno1.getText().trim()) &&turno.equals(princip.ventanilla1.getText().trim())){
            return 1;
        }else if(turno.equals(princip.turno2.getText().trim()) &&turno.equals(princip.ventanilla2.getText().trim())){
            return 2;
        }
        return 0;
    
    }

    /**
     * Env�a el �ltimo texto de la charla por el socket. Se avisa a este m�todo
     * cada vez que se mete algo en charla, incluido cuando lo mete este mismo
     * hilo. De esta manera, lo que un cliente escriba, se le reenviar� para que
     * se muestre en el textArea.
     */
    public void intervalAdded(ListDataEvent e) {
        String texto = (String) charla.getElementAt(e.getIndex0());
        try {
            dataOutput.writeUTF(texto);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }
    }

    /**
     * No hace nada
     */
    public void intervalRemoved(ListDataEvent e) {
    }

    /**
     * No hace nada
     */
    public void contentsChanged(ListDataEvent e) {
    }
}
