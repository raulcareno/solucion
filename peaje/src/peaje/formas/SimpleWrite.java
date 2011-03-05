/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peaje.formas;

 
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.*;

/**
 * Class declaration
 *
 *
 * @author
 * @version 1.10, 08/04/00
 */
public class SimpleWrite {
    static Enumeration	      portList;
    static CommPortIdentifier portId;
    static String	      messageString = "Hello, world!";
    static SerialPort	      serialPort;
    static OutputStream       outputStream;
    static boolean	      outputBufferEmptyFlag = false;
    /**
     * Method declaration
     *
     *
     * @param args
     *
     * @see
     */
    public static void llamar(String puerto) {
	boolean portFound = false;
	String  defaultPort = puerto;
//	if (args.length > 0) {
//	    defaultPort = args[0];
//	}
	portList = CommPortIdentifier.getPortIdentifiers();

	while (portList.hasMoreElements()) {
	    portId = (CommPortIdentifier) portList.nextElement();

	    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

		if (portId.getName().equals(defaultPort)) {
                    try {
                        System.out.println("Found port " + defaultPort);
                        portFound = true;
                        try {
                            serialPort = (SerialPort) portId.open("SimpleWrite", 2000);
                            //serialPort = (SerialPort) puertoId.open("SimpleReadApp", 2000);
                        } catch (PortInUseException e) {
                            System.out.println("Port in use.");
                            continue;
                        }
                        serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
                        try {
                            outputStream = serialPort.getOutputStream();
                        } catch (IOException e) {
                            System.out.println("" + e);
                        }
                        try {
                            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                        } catch (UnsupportedCommOperationException e) {
                            System.out.println("" + e);
                        }
                        try {
                            serialPort.notifyOnOutputEmpty(true);
                        } catch (Exception e) {
                            System.out.println("Error setting event notification");
                            System.out.println(e.toString());
                            System.exit(-1);
                        }
                        System.out.println("Writing \"" + messageString + "\" to " + serialPort.getName());
                        try {
//                            String uno = "1";
//                            outputStream.write(uno.getBytes());
                            String dos = "2";
                            outputStream.write(dos.getBytes());
                            
                        } catch (IOException e) {
                        }
                        try {
                            Thread.sleep(100); // Be sure data is xferred before closing
                        } catch (Exception e) {
                        }
                        serialPort.close();
//                        System.exit(1);
                    } catch (UnsupportedCommOperationException ex) {
                        Logger.getLogger(SimpleWrite.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
	    }
	}

	if (!portFound) {
	    System.out.println("port " + defaultPort + " not found.");
	}
    }


}




