/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import jcinform.conexion.Administrador;
import jcinform.persistencia.*;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
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

    public   byte[] empezarGenerar(List datos,Bancos banco) {
        try {
            Administrador adm = new Administrador();
            Session ses = Sessions.getCurrent();
            Empleadossucursal sucursalEmp = (Empleadossucursal) ses.getAttribute("sector");
            byte[] data = null;
            File outFile = File.createTempFile("archivoSalida", ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
            for (Iterator it = datos.iterator(); it.hasNext();) {
                Row object = (Row) it.next();
                //Facturasenviadas fac = (Facturasenviadas) adm.buscarClave(new Integer(((Facturasenviadas)object.getValue()).getCodigo(), Facturasenviadas.class);
                Facturasenviadas fac = (Facturasenviadas) object.getValue();
                String valor = fac.getSaldo()+ "";
                String base = (fac.getSaldo().divide(new BigDecimal(1.12), 2, RoundingMode.HALF_UP)) + "";//BASE IVA TOCARÍA SACAR LA BASE IVA DE LO QUE SE ENVIARÍA
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
                writer.write("CO" // cobro
                        + "\t" + fac.getFactura().getContratos().getCodigo()  // no de contrato
                        + "\tUSD" // moneda
                        + "\t" + valor // valor adeudado
                        + "\tCTA\t" + fac.getFactura().getContratos().getTipocuenta() // tipo de cuenta aho, cor
                        + "\t" + fac.getFactura().getContratos().getNocuenta() // cuenta del banco
                        + "\tMENSUALIDAD " + mes(fac.getFecha().getMonth()) +""+(fac.getFecha().getYear()+1900) // descripcion nuestra
                        + "\t" + fac.getFactura().getContratos().getClientes().getTipoidentificacion() // tipo de documento
                        + "\t" + fac.getFactura().getContratos().getClientes().getIdentificacion().replace("-", "") // cedula
                        + "\t" + fac.getFactura().getClientes().getApellidos() + " " + fac.getFactura().getClientes().getNombres() // nombres cliente
                        + "\t" + base //base del iva
                        + "\t"
                        + "\t"
                        + "\t"
                        + "\t"
                        + "\t"
                        + "\t"
                        + "\t" + fac.getNumero()
                        + "\t"+sucursalEmp.getSucursal().getEmpresa().getNoautorizacion());
                writer.newLine(); // Esto es un salto de linea
            }
//                writer.write("CO\tNoCONTRATO"+i+"\tUSD\tvalorcancelar01234567890"
//                    + "\tCTA\tAHOCTE" //                    + "\tNO.CUENTA"
//                    + "\tREFERENCIADELPAGO" //                    + "\tTipo(C)edula(R)ucPasaporte"
//                    + "\tIDENTIFICACIONnO." //                    + "\tNOMBRE DEL BENEFICIARIO O DEUDOR"
//                    + "\tVALOR BASE IMPONIBLE MENOR A VALOR" //                    + "\tCO"
 //                    + "\tNOFACTURAORECIBO" //                    + "\tNo.AutorizacionFactura");
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
            Archivosbanco archivo = new Archivosbanco();
            archivo.setArchivo(data);
            archivo.setFecha(adm.Date());
            archivo.setNumero(Integer.SIZE);
            archivo.setBancos(banco); 
            archivo.setEmpleados(sucursalEmp.getEmpleados());
            adm.guardar(archivo);

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
