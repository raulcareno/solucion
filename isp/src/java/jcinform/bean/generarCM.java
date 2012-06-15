/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import jcinform.conexion.Administrador;
import jcinform.persistencia.*;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;
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

    public byte[] empezarGenerar(List datos, Bancos banco) {
        try {
            Administrador adm = new Administrador();
            Session ses = Sessions.getCurrent();
            Empleadossucursal sucursalEmp = (Empleadossucursal) ses.getAttribute("sector");

            byte[] data = null;
            File outFile = File.createTempFile("archivoBan", ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
            if (banco.getNombre().toLowerCase().contains("pichincha")) {

                for (Iterator it = datos.iterator(); it.hasNext();) {
                    Row object = (Row) it.next();
                    //Facturasenviadas fac = (Facturasenviadas) adm.buscarClave(new Integer(((Facturasenviadas)object.getValue()).getCodigo(), Facturasenviadas.class);
                    Facturasenviadas fac = (Facturasenviadas) object.getValue();
                    String factura = fac.getFactura().getNumero().replace("FAC", "-");
                    factura = factura.substring(0, 3) + "-" + factura.substring(3, 6) + "-00" + factura.substring(7, factura.length());
                    String valor = fac.getSaldo() + "";
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
                            + "\t" + fac.getFactura().getContratos().getContrato() // no de contrato
                            + "\tUSD" // moneda
                            + "\t" + valor // valor adeudado
                            + "\tCTA\t" + (fac.getFactura().getContratos().getTipocuenta().equals("AHO") ? "AHO" : "CTE") // tipo de cuenta aho, cTE
                            + "\t" + fac.getFactura().getContratos().getNocuenta() // cuenta del banco
                            + "\tMENSUALIDAD " + mes(fac.getFecha().getMonth()) + "" + (fac.getFecha().getYear() + 1900) // descripcion nuestra
                            + "\t" + fac.getFactura().getContratos().getClientes().getTipoidentificacion() // tipo de documento
                            + "\t" + fac.getFactura().getContratos().getClientes().getIdentificacion().replace("-", "") // cedula
                            + "\t" + ((fac.getFactura().getClientes().getApellidos() + " " + fac.getFactura().getClientes().getNombres()).length() > 41 ? (fac.getFactura().getClientes().getApellidos() + " " + fac.getFactura().getClientes().getNombres()).substring(0, 41) : (fac.getFactura().getClientes().getApellidos() + " " + fac.getFactura().getClientes().getNombres())) // nombres cliente
                            + "\t" + base //base del iva
                            + "\t"
                            + "\t"
                            + "\t"
                            + "\t"
                            + "\t"
                            + "\t"
                            + "\t" + factura // numero de factura
                            + "\t" + fac.getFactura().getAutorizacion()); // numero de autorización
                    writer.newLine(); // Esto es un salto de linea
                }

            } else if (banco.getNombre().toLowerCase().contains("guayaquil")) {


                for (Iterator it = datos.iterator(); it.hasNext();) {
                    Row object = (Row) it.next();
                    //Facturasenviadas fac = (Facturasenviadas) adm.buscarClave(new Integer(((Facturasenviadas)object.getValue()).getCodigo(), Facturasenviadas.class);
                    Facturasenviadas fac = (Facturasenviadas) object.getValue();
                    String valor = fac.getSaldo() + "";
                    String base = (fac.getSaldo().divide(new BigDecimal(1.12), 2, RoundingMode.HALF_UP)) + "";//BASE IVA TOCARÍA SACAR LA BASE IVA DE LO QUE SE ENVIARÍA
                    String cuenta = fac.getFactura().getContratos().getNocuenta();
                    try {


                        while (cuenta.length() < 10) {
                            cuenta = "0" + cuenta;
                        }
                    } catch (Exception e) {
                    }

                    //NDEAAAAMMDDCCC_SE.TXT
                    try {

                        valor = valor.replace(",", "").replace(".", "");
                        while (valor.length() < 15) {
                            valor = "0" + valor;
                        }
                    } catch (Exception e) {
                    }
                    try {

                        base = base.replace(",", "").replace(".", "");
                        while (base.length() < 15) {
                            base = "0" + base;
                        }
                    } catch (Exception e) {
                    }
                    String nombreCliente = fac.getFactura().getContratos().getClientes().getApellidos() +" "+fac.getFactura().getContratos().getClientes().getNombres();
                    try {
                        if(nombreCliente.length()>25){
                            nombreCliente = nombreCliente.substring(0,25);    
                        }else if(nombreCliente.length() == 25){
                            
                        }else{
                            while(nombreCliente.length()<25)
                                nombreCliente = nombreCliente+".";     
                        }
                    } catch (Exception e) {
                            nombreCliente = nombreCliente.substring(0,25);
                    }
                    if(fac.getFactura().getContratos().getTipocuenta().equals("") || fac.getFactura().getContratos().getTipocuenta().equals(" ")){
                        Messagebox.show("El cliente: "+ nombreCliente +" NO tiene tipo de cuenta AHO, COR ");
                        return null;
                    }
                            
                    writer.write("" + fac.getFactura().getContratos().getTipocuenta().substring(0, 1) //1 Tipo de cuenta	Alfanumérico	A/C	1	Tipo de cuenta del cliente.  A: Ahorros.  C: Corriente.
                            + "\t" + cuenta// Nro Cuenta Numérico 10	Número de cuenta del cliente a debitar
                            + "\t" + valor //3 Valor	Numérico 15 Valor a debitar.  13 enteros, 2 decimales.
                            + "\t" + "XX" //4 Constante	Alfanumérico	XX	2	Valor constante XX
                            + "\t" + "W" //5	Tipo nota Alfanumérico	W	1	Tipo de transacción.  W: Débito en dólares.
                            + "\t" + "01" //6	Agencia	Numérico 01/06	2 Tipo de agencia.  01: Matriz.  06: Sucursal. 01 si es por Banca Transaccional ( Internet)
                            + "\t" + banco.getEmpresa()
                            + "\t" + nombreCliente);
                                //                  Código empresa recaudadora; lo suministra el banco.(Motivo)
                                //Campo opcional.  Podrá contener datos que relacionen al cliente con la empresa, como: código del cliente, número de identificación, etc

                    writer.newLine(); // Esto es un salto de linea
                }

            } else if (banco.getNombre().toLowerCase().contains("bolivariano")) {
                int contador = 1;
                String contadorString = "";
                for (Iterator it = datos.iterator(); it.hasNext();) {
                    Row object = (Row) it.next();
                    //Facturasenviadas fac = (Facturasenviadas) adm.buscarClave(new Integer(((Facturasenviadas)object.getValue()).getCodigo(), Facturasenviadas.class);
                    Facturasenviadas fac = (Facturasenviadas) object.getValue();
                    String valor = fac.getSaldo() + "";
                    String base = (fac.getSaldo().divide(new BigDecimal(1.12), 2, RoundingMode.HALF_UP)) + "";//BASE IVA TOCARÍA SACAR LA BASE IVA DE LO QUE SE ENVIARÍA
                    try {

                        valor = valor.replace(",", "").replace(".", "");
                        while (valor.length() < 15) {
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
                    }//bolivariano
                    try {
                        contadorString = contador + "";
                        while (contadorString.length() < 2) {
                            contadorString = "0" + contador;
                        }
                    } catch (Exception e) {
                    }
                    //cedula
                    String cedula = "" + fac.getFactura().getContratos().getClientes().getIdentificacion().replace("-", "");
                    try {

                        while (cedula.length() < 14) {
                            cedula = "0" + cedula;
                        }
                    } catch (Exception e) {
                    }
                    //cuenta
                    String nocuenta = "" + fac.getFactura().getContratos().getNocuenta();
                    try {

                        while (nocuenta.length() < 20) {
                            nocuenta = "0" + nocuenta;
                        }
                    } catch (Exception e) {
                    }//bolivariano

                    String espacios35 = " ";
                    try {

                        while (espacios35.length() < 15) {
                            espacios35 = " " + espacios35;
                        }
                    } catch (Exception e) {
                    }//bolivariano
                    String espacios50 = " ";
                    try {

                        while (espacios50.length() < 50) {
                            espacios50 = " " + espacios50;
                        }
                    } catch (Exception e) {
                    }//bolivariano
                    String espacios31 = " ";
                    try {

                        while (espacios31.length() < 31) {
                            espacios31 = " " + espacios31;
                        }
                    } catch (Exception e) {
                    }//bolivariano

                    writer.write("BZDET" // cobro
                            + "\t" + contadorString //SECUENCIAL
                            + "\t" + fac.getFactura().getContratos().getContrato() //NUMERO CONTRATO 
                            + "\t" + fac.getFactura().getContratos().getClientes().getTipoidentificacion() // C, R, P
                            + "\t" + cedula// cedula, ruc, pasaporte
                            + "\t" + fac.getFactura().getClientes().getApellidos() + " " + fac.getFactura().getClientes().getNombres() // nombres cliente
                            + "\tCUE"// estatico
                            + "\t001"// estatico
                            + "\t34"// estatico
                            + "\t" + (fac.getFactura().getContratos().getTipocuenta().equals("AHO") ? "04" : "03")
                            + "\t" + nocuenta
                            + "\t1"// estatico moneda 1
                            + "\t" + valor // valor adeudado
                            + "\tMENSUALIDAD " + mes(fac.getFecha().getMonth()) + "" + (fac.getFecha().getYear() + 1900) // descripcion nuestra
                            + "\t" + fac.getFactura().getNumero().replace("FAC", "")
                            + "\t" + base //base del iva
                            + "\t" + espacios35// en blanco
                            + "\t" + "          "// en blanco 10 espacios
                            + "\t" + espacios50
                            + "\t" + espacios50
                            + "\t" + "                    "// en blanco 20 espacios
                            + "\tREC"
                            + "\t" + espacios31
                            + "\t" + banco.getEmpresa()
                            + "\t" + valor
                            + "\t001"); //tipo de comprobante 001 igual a factura

                    writer.newLine(); // Esto es un salto de linea
                }
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
        } catch (InterruptedException ex) {
            Logger.getLogger(generarCM.class.getName()).log(Level.SEVERE, null, ex);
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
