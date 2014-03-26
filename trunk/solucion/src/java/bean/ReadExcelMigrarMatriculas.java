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
public class ReadExcelMigrarMatriculas {

    private void leerArchivoExcel(String archivoDestino) {
        try {
            Administrador adm = new Administrador();
            Workbook archivoExcel = Workbook.getWorkbook(new File(archivoDestino));
            System.out.println("Número de Hojas\t" + archivoExcel.getNumberOfSheets());
            secuencial sec = new secuencial();

            int orden = 1;
            int estudianteCodigo = 1;
            for (int i = 0; i < archivoExcel.getNumberOfSheets(); i++) {
                Sheet hoja = archivoExcel.getSheet(i);
                System.out.println("NOMBRE DE LA HOJA" + hoja.getName());
                int numFilas = hoja.getRows();
                System.out.println("------------------------------" + archivoExcel.getSheet(i).getName());
                //PRIMERO CREO EL DISTRIBUTIVO

                Cursos cursoActual = new Cursos();
                List<Cursos> cursosEn = adm.query("Select o from Cursos as o where o.descripcion = '" + hoja.getName() + "'");
                if (cursosEn.size() > 0) {
                    cursoActual = cursosEn.get(0);
                } else {
                    cursoActual.setCodigocur(adm.getNuevaClave("Cursos", "codigocur"));
                    cursoActual.setSecuencia(orden);
                    cursoActual.setDescripcion(hoja.getName());
                    adm.guardar(cursoActual);
                    orden++;
                }
                for (int fila = 9; fila < numFilas; fila++) { // Recorre cada 

                    String estutidante = hoja.getCell(1, fila).getContents();
                    if (!estutidante.trim().equals("")) {

                        System.out.println("" + estutidante + " ");
                        String genero = hoja.getCell(3, fila).getContents();
                        System.out.print("GENE:" + genero + " ");
                        genero ="Masculino";
                        Estudiantes est = new Estudiantes();
                        est.setCodigoest(adm.getNuevaClave("Estudiantes", "codigoest"));
                        est.setApellido(estutidante);
                        est.setNombre(estutidante);
                        est.setGenero((genero.contains("1") ? "Masculino" : "Femenino"));
                        adm.guardar(est);

                        Matriculas mat = new Matriculas(adm.getNuevaClave("Matriculas", "codigomat"));
                        mat.setEstudiante(est);
                        mat.setCurso(cursoActual);
                        mat.setEstado("Matriculado");
                        adm.guardar(mat);

                    }

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
        ReadExcelMigrarMatriculas excel = new ReadExcelMigrarMatriculas();
        excel.leerArchivoExcel("E:\\listado2.xls");
    }
}
