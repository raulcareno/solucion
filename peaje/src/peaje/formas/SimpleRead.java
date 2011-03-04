/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peaje.formas;

import java.io.*;
import java.util.*;
import javax.comm.*;
import peaje.formas.principal;

public class SimpleRead implements Runnable, SerialPortEventListener {

    static CommPortIdentifier puertoId;
//  portList;
    InputStream inputStream;
    SerialPort serialPort;
    Thread readThread;
    public String tarjeta;
    principal princip;

    @SuppressWarnings("static-access")
    public SimpleRead(CommPortIdentifier portIde, principal pantalla) {

        try {
            this.tarjeta = "";
            princip = pantalla;
            this.puertoId = portIde;
            serialPort = (SerialPort) puertoId.open("SimpleReadApp", 2000);
        } catch (PortInUseException e) {
        }
        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
        }
        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
        }
        serialPort.notifyOnDataAvailable(true);
        try {
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {
        }
        readThread = new Thread(this);
        readThread.start();
    }

    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
        }
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }
    public int inicio = 0;

    public void serialEvent(SerialPortEvent event) {
//    if(tarjeta.length()>10){
//        tarjeta = "";
//    }
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                byte[] readBuffer = new byte[10];
                try {
                    while (inputStream.available() > 0) {
                        int numBytes = inputStream.read(readBuffer);

                        tarjeta += new String(readBuffer).trim();
                    }
                } catch (IOException e) {
                }
                break;
        }
        //System.out.println(""+tarjeta);
        if (tarjeta.length() >= 11) {

            System.out.println("" + tarjeta);
            princip.tarjetatxt.setText("");
            princip.tarjetatxt.setText(tarjeta);
            princip.buscarTarjeta();
            tarjeta = "";
            return;
        }


    }
}
