/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import jxl.*;
import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;
import org.zkoss.zul.Label;

/**
 *
 * @author GEOVANNY
 */
public class ReadExcelMigrarEmpleados {

    private void leerArchivoExcel(String archivoDestino) {
        try {
            Administrador adm = new Administrador();
            Workbook archivoExcel = Workbook.getWorkbook(new File(archivoDestino));
            System.out.println("Número de Hojas\t" + archivoExcel.getNumberOfSheets());
            secuencial sec = new secuencial();

            int orden = 1;
            int estudianteCodigo = 1;
                Sheet hoja = archivoExcel.getSheet(0);
                System.out.println("NOMBRE DE LA HOJA" + hoja.getName());
                int numFilas = hoja.getRows();
                System.out.println("------------------------------" + archivoExcel.getSheet(0).getName());
                //PRIMERO CREO EL DISTRIBUTIVO
 
                for (int fila = 3; fila < numFilas; fila++) { // Recorre cada 

                    String identificacion = hoja.getCell(2, fila).getContents();
                    if (!identificacion.trim().equals("")) {

                        System.out.println("" + identificacion + " ");
                        String nombres = hoja.getCell(3, fila).getContents();
                        String direccion = hoja.getCell(4, fila).getContents();
                        String telefono = hoja.getCell(5, fila).getContents();
                        Empleados est = new Empleados();
                        est.setCodigoemp(adm.getNuevaClave("Empleados", "codigoemp"));
                        est.setApellidos(nombres);
                        est.setIdentificacion(identificacion); 
                        est.setNombres(nombres);
                        est.setDireccion(direccion);
                        est.setTelefono1(telefono); 
                        adm.guardar(est);
 

                    }

                }

 

        } catch (Exception ioe) {
            ioe.printStackTrace();
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ReadExcelMigrarEmpleados excel = new ReadExcelMigrarEmpleados();
        excel.leerArchivoExcel("E:\\docentes1.xls");
    }
}
