/**
 * Javier Abell�n, 25 Mayo 2006
 * 
 * Para Cliente de chat.
 */
package chatear.cliente;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Panel para mostrar la conversaci�n y pedir al usuario el texto que quiere
 * enviar.
 * @author Chuidiang
 */
public class PanelCliente
{
    /** Scroll */
    private JScrollPane scroll;

    /** Area para mostrar la conversaci�n */
    private JTextArea textArea;

    /** Para pedir el texto al usuario */
    private JTextField textField;

    /** Bot�n para enviar el texto */
    private JButton boton;

    /**
     * Crea el panel con todos sus componentes. Un Area de texto para ver la
     * conversaci�n, un textField para escribir el texto que queremos enviar y
     * un bot�n de enviar.
     * 
     * @param contenedor
     *            Contenedor en el que a�adir todos los componentes
     */
    public PanelCliente(Container contenedor)
    {
        contenedor.setLayout(new BorderLayout());
        textArea = new JTextArea();
        scroll = new JScrollPane(textArea);

        JPanel panel = new JPanel(new FlowLayout());
        textField = new JTextField(50);
        boton = new JButton("Enviar");
        panel.add(textField);
        panel.add(boton);

        contenedor.add(scroll, BorderLayout.CENTER);
        contenedor.add(panel, BorderLayout.SOUTH);
    }

    /** A�ade el actionListener que se le pasa tanto a pulsar <intro> en el
     * textField como al bot�n.
     * @param accion ActionListener a a�adir.
     */
    public void addActionListener(ActionListener accion)
    {
        textField.addActionListener(accion);
        boton.addActionListener(accion);

    }

    /**
     * A�ade el texto que se le pasa al textArea.
     * @param texto Texto a a�adir
     */
    public void addTexto(String texto)
    {
        textArea.append(texto);
    }

    /**
     * Devuelve el texto que hay en el textfield y lo borra.
     * @return El texto del textfield.
     */
    public String getTexto()
    {
        String texto = textField.getText();
        textField.setText("");
        return texto;
    }
}
