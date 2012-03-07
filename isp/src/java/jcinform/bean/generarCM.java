/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;

/**
 *
 * @author inform
 */
public class generarCM {

    public generarCM() {
    }
  public static void main(String[] args) {
         
 empezarGenerar();
    }
    public static  void empezarGenerar() {
        try {
            File outFile = new File("F:\\archivoSalida.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
            for (int i = 0; i < 10; i++) {
                writer.write("CO\tNoCONTRATO"+i+"\tUSD\tvalorcancelar01234567890"
                    + "\tCTA\tAHOCTE"
                    + "\tNO.CUENTA"
                    + "\tREFERENCIADELPAGO"
                    + "\tTipo(C)edula(R)ucPasaporte"
                    + "\tIDENTIFICACIONnO."
                    + "\tNOMBRE DEL BENEFICIARIO O DEUDOR"
                    + "\tVALOR BASE IMPONIBLE MENOR A VALOR"
                    + "\tCO"
                    + "\tCB"
                    + "\tCB"
                    + "\tCB"
                    + "\tCB"
                    + "\tCB"
                    + "\tNOFACTURAORECIBO"
                    + "\tNo.AutorizacionFactura");
                    writer.newLine(); // Esto es un salto de linea
            }
         
            writer.close();
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }
}
 
