/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;
import gnu.io.*;
import java.io.*;
import javax.swing.JOptionPane;
/**
 *
 * @author geovanny
 */


public class ReadComPort {

    public static void main(String[] s) {
        readcomport();
    }

    public static String readcomport() {
        String value = null;

        try {
            // CommPortIdentifier portIdentifier = CommPortIdentifier
            // .getPortIdentifier("COM1");

            // String comportidentifier = "COM1"; //*win
            String comportidentifier = "/dev/ttyACM1";
            

            CommPortIdentifier portIdentifier = null;
            portIdentifier = CommPortIdentifier.getPortIdentifier(comportidentifier);

            if (portIdentifier.isCurrentlyOwned()) {
                JOptionPane.showMessageDialog(null, "port in use");
            } else {

                SerialPort serialPort = (SerialPort) portIdentifier.open("ReadComPort", 500);
                JOptionPane.showMessageDialog(null, serialPort.getBaudRate());

                serialPort.setSerialPortParams(serialPort.getBaudRate(), SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
                // serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
                serialPort.setDTR(true);
                serialPort.setRTS(true);

                InputStream mInputFromPort = serialPort.getInputStream();

                Thread.sleep(500);
                byte mBytesIn[] = new byte[32];
                mInputFromPort.read(mBytesIn);

                value = new String(mBytesIn);

                mInputFromPort.close();
                serialPort.close();
                System.out.println(""+value);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Exception : " + ex.getMessage());

        }

        return value;

    }
}