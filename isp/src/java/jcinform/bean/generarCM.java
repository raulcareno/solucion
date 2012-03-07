/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import jcinform.conexion.Administrador;
import jcinform.persistencia.Factura;
import org.zkoss.zul.Row;

/**
 *
 * @author inform
 */
public class generarCM {

    public generarCM() {
    }

    public static void main(String[] args) {
//         
        //empezarGenerar();
    }

    public   byte[] empezarGenerar(List datos) {
        try {
            Administrador adm = new Administrador();

            byte[] data = null;
            File outFile = File.createTempFile("archivoSalida", ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
            for (Iterator it = datos.iterator(); it.hasNext();) {
                Row object = (Row) it.next();
                System.out.println(" " + object.getValue());
                System.out.println(" " + object.getChildren().get(0));
                Factura fac = (Factura) adm.buscarClave(new Integer(object.getValue().toString()), Factura.class);
                String valor = fac.getTotal() + "";
                String base = fac.getBaseiva() + "";
                try {

                    valor = valor.replace(",", "").replace(".", "");
                    while (valor.length() < 11) {
                        valor = "0" + valor;
                    }
                } catch (Exception e) {
                }
                try {

                    base = base.replace(",", "").replace(".", "");
                    while (base.length() < 11) {
                        base = "0" + base;
                    }
                } catch (Exception e) {
                }
                writer.write("CO\t" + fac.getContratos().getCodigo() + "\tUSD\t" + valor
                        + "\tCTA\t" + fac.getContratos().getTipocuenta()
                        + "\t" + fac.getContratos().getNocuenta()
                        + "\tMENSUALIDAD " + mes(fac.getFecha().getMonth()) +""+(fac.getFecha().getYear()+1900)
                        + "\t" + fac.getContratos().getClientes().getTipoidentificacion()
                        + "\t" + fac.getContratos().getClientes().getIdentificacion().replace("-", "")
                        + "\t" + fac.getClientes().getApellidos() + " " + fac.getClientes().getNombres()
                        + "\t" + base
                        + "\t"
                        + "\t"
                        + "\t"
                        + "\t"
                        + "\t"
                        + "\t"
                        + "\t" + fac.getNumero()
                        + "\tNo.AutorizacionFactura");
                writer.newLine(); // Esto es un salto de linea

            }
            for (int i = 0; i < 10; i++) {
//                writer.write("CO\tNoCONTRATO"+i+"\tUSD\tvalorcancelar01234567890"
//                    + "\tCTA\tAHOCTE"
//                    + "\tNO.CUENTA"
//                    + "\tREFERENCIADELPAGO"
//                    + "\tTipo(C)edula(R)ucPasaporte"
//                    + "\tIDENTIFICACIONnO."
//                    + "\tNOMBRE DEL BENEFICIARIO O DEUDOR"
//                    + "\tVALOR BASE IMPONIBLE MENOR A VALOR"
//                    + "\tCO"
//                    + "\tCB"
//                    + "\tCB"
//                    + "\tCB"
//                    + "\tCB"
//                    + "\tCB"
//                    + "\tNOFACTURAORECIBO"
//                    + "\tNo.AutorizacionFactura");
//                    writer.newLine(); // Esto es un salto de linea
            }

            FileInputStream input;
            writer.close();
            try {
                input = new FileInputStream(outFile);
                data = new byte[input.available()];
                input.read(data);
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //outFile.deleteOnExit();
            //Filedownload.save(data, "file", "archivo.txt");

            return data;
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        return null;
    }

    public String mes(int val) {
        switch (val) {
            case 0:
                return "Enero";
            case 1:
                return "Febrero";
            case 2:
                return "Marzo";
            case 3:
                return "Abril";
            case 4:
                return "Mayo";
            case 5:
                return "Junio";
            case 6:
                return "Julio";
            case 7:
                return "Agosto";
            case 8:
                return "Septiembre";
            case 9:
                return "Octubre";
            case 10:
                return "Noviembre";
            case 11:
                return "Diciembre";
        }
        return "";

    }
}
