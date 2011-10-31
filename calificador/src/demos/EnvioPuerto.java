/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package demos;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

/**
 *
 * @author Geovanny Jadan
 */
public class EnvioPuerto {

static Enumeration listaPuertos;
static CommPortIdentifier idPuerto;
static String mensaje = "";
static SerialPort puertoSerie;
static OutputStream salida;
public boolean prendido = false;
static boolean entrada=false;
public EnvioPuerto(String me){

  mensaje=me;

}

public void cerrar(){ try{ if(prendido){

  salida.close();
  puertoSerie.close();
  prendido=false;

} }catch(Exception e1){
System.out.println("Problema al intentar cerrar el puerto. "+e1.getMessage()); } }

public void enviar(String mensaje){ entrada=false;
try{ listaPuertos = CommPortIdentifier.getPortIdentifiers();

while( listaPuertos.hasMoreElements() ) {
  idPuerto = (CommPortIdentifier)listaPuertos.nextElement();
  if( idPuerto.getPortType() == CommPortIdentifier.PORT_SERIAL ) {

 { if( idPuerto.getName().equals("COM4") ) {

      try {
          if(!idPuerto.isCurrentlyOwned()){

           puertoSerie = ( SerialPort )idPuerto.open("AplEscritura",2000);

          }
      } catch( Exception e ) {

           entrada=true;
      }
        if(!entrada){
                try {
        puertoSerie.setSerialPortParams( 9600,
          SerialPort.DATABITS_8,
          SerialPort.STOPBITS_1,
          SerialPort.PARITY_NONE );
      } catch( UnsupportedCommOperationException e ) {
       System.out.println(e.getMessage());
       entrada=true;
      }

      if(!entrada)    {
      try {

        salida = puertoSerie.getOutputStream();
        prendido=true;

      } catch( IOException e ) {

             entrada=true;
      }
      }

              if(!entrada)   {
      try {

            salida.write(mensaje.getBytes());

            cerrar();

      } catch( IOException e ) {
      }
             }
        }
    }
  }
}

}
}catch(Exception ad){

} }

public boolean prendido(){

  return prendido;

}

public static void main( String[] args ) {

    EnvioPuerto e1 = new EnvioPuerto("");
    e1.enviar("mensaje");

} }
