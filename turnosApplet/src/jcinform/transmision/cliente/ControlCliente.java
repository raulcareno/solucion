/**
 * Javier Abell�n, 25 Mayo 2006
 * Para Cliente de chat
 */
package jcinform.transmision.cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.JButton;
import jcinform.Administrador.UsuarioActivo;

public class ControlCliente implements ActionListener, Runnable
{
    /** Para lectura de datos del socket */
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    
    JButton control;
 String ventanilla;
    public ControlCliente(Socket socket, JButton panel,String ventanilla)
    {
      //  this.panel = panel;
        control = panel;
        this.ventanilla = ventanilla;
        try
        {
            dataInput = new DataInputStream(socket.getInputStream());
            dataOutput = new DataOutputStream(socket.getOutputStream());
        //    panel.addActionListener(this);
            //texto.addActionListener(this);
            control.addActionListener(this);
            Thread hilo = new Thread(this);
            hilo.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent evento)
    {
        try
        {
            dataOutput.writeUTF(control.getName()+"-"+control.getLabel()+"*"+ventanilla);
        } catch (Exception excepcion)
        {
            excepcion.printStackTrace();
        }
    }

    /**
     * M�todo run para antender al socket. Todo lo que llega por el socket se
     * escribe en el panel.
     */
    public void run()
    {
        try
        {
            while (true)
            {
                String texto = dataInput.readUTF();
                //control.setText(texto);
                System.out.println("DATO ENVIADO: "+texto);
                //panel.addTexto("\n");
            }
        } catch (Exception e)
        {
             UsuarioActivo.setListaConexion(false);
            e.printStackTrace();
        }
    }
}
