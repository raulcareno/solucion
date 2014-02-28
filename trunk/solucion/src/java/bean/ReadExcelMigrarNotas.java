/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import jxl.*;
import java.io.*;
import java.util.Date;
import java.util.List;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;
import org.zkoss.zul.Label;

/**
 *
 * @author GEOVANNY
 */
public class ReadExcelMigrarNotas {

    private void leerArchivoExcel(String archivoDestino) {
        try {
            Administrador adm = new Administrador();
            Workbook archivoExcel = Workbook.getWorkbook(new File(archivoDestino));
            System.out.println("Número de Hojas\t" + archivoExcel.getNumberOfSheets());
            secuencial sec = new secuencial();
            int numeroColumnas1A=1;
            int numeroColumnas1b=2;
            
            int columnadelCurso = 5;
            int filaMaterias = 3;
            int sheetNo = 0;
            
                Sheet hoja = archivoExcel.getSheet(sheetNo);
                int numColumnas = hoja.getColumns();
                int numFilas = hoja.getRows();
                String notaLeida;
                System.out.println("Nombre de la Hoja\t"+ archivoExcel.getSheet(sheetNo).getName());
                //PRIMERO CREO EL DISTRIBUTIVO
                int orden = 1;
                for (int fila = 4; fila < numFilas; fila++) { // Recorre cada 
                        
                   String codigomatricula = hoja.getCell(1, fila).getContents();
                   System.out.print("MATRICULA :"+codigomatricula + " ");
                    for (int columna = 7; columna < numColumnas; columna++) { // RecorrE
                        String codigocurso = hoja.getCell(columnadelCurso, fila).getContents();
                        //System.out.print("CURSO: "+codigocurso + " ");
                        
                        String codigoMATERIA = hoja.getCell(columna, filaMaterias).getContents();
                        //System.out.print("MATERIA: "+codigoMATERIA + " ");
                        
                        notaLeida = hoja.getCell(columna, fila).getContents();
                        
                        //valor de la nota
                        //System.out.print("xx:"+notaLeida + " ");
                        
                        
                        List<Global>  materias =  adm.query("Select o from Global as o where o.descripcion like '%"+codigoMATERIA+"%' and o.grupo = 'MAT'");
                        Global materiaActual = null;
                        if(materias.size()>0){
                             materiaActual=materias.get(0);
                        }else{
                            materiaActual =new Global(adm.getNuevaClave("Global", "codigo"));
                            materiaActual.setDescripcion(codigoMATERIA);
                            materiaActual.setGrupo("MAT");
                            adm.guardar(materiaActual);
                        }
                        List<MateriaProfesor>  MateriaProfesores =  adm.query("Select o from MateriaProfesor as o "
                                + " where o.curso.codigocur = '"+codigocurso+"' and o.materia.codigo = '"+materiaActual.getCodigo()+"' ");
                        MateriaProfesor materiaProfeActual = null;
                        if(MateriaProfesores.size()>0){
                             materiaProfeActual = MateriaProfesores.get(0);
                        }else{
                            materiaProfeActual =new MateriaProfesor(adm.getNuevaClave("MateriaProfesor", "codigomap"));
                            materiaProfeActual.setCurso(new Cursos(new Integer(codigocurso)));
                            materiaProfeActual.setPeriodo(new Periodo(7)); 
                            materiaProfeActual.setMateria(materiaActual);
                            materiaProfeActual.setEmpleado(new Empleados(1)); 
                            materiaProfeActual.setOrden(orden);
                            materiaProfeActual.setCuantitativa(true); 
                            adm.guardar(materiaProfeActual);
                            orden++;
                        }
                        
                        List<Notas> notas = adm.query("Select o from Notas as o where o.matricula.codigomat = '"+codigomatricula+"' "
                                + " and o.materia.codigo = '"+materiaProfeActual.getMateria().getCodigo()+"' ");
                        Notas notaa= new Notas();
                        Double notaDouble = new Double("0");
                        boolean escuantitativa = true;
                        if(notaLeida.equals("MS")){
                                notaDouble = 3d;
                                escuantitativa = false;
                        }else if(notaLeida.equals("S")){
                                notaDouble = 2d;
                                escuantitativa = false;
                        }else if(notaLeida.equals("PS")){
                                notaDouble = 1d;
                                escuantitativa = false;
                        }else if(notaLeida.equals("")){
                                notaDouble = 0d;
                        }else if(notaLeida.contains("#")){
                                notaDouble = 0d;
                                escuantitativa = false;
                        }else if(notaLeida.equals("A")){
                                notaDouble = 10d;
                                escuantitativa = false;
                        }else if(notaLeida.equals("B")){
                                notaDouble = 9d;
                                escuantitativa = false;
                        }else if(notaLeida.equals("C")){
                                notaDouble = 8d;
                                escuantitativa = false;
                        }else if(notaLeida.equals("D")){
                                notaDouble = 7d;
                                escuantitativa = false;
                        }else if(notaLeida.equals("E")){
                                notaDouble = 6d;
                                escuantitativa = false;
                        }else{
                            try {
                                notaDouble = new Double(notaLeida.replace(",","."));
                                escuantitativa = true;
                            } catch (Exception e) {
                                e.printStackTrace();
                                //System.out.println(""+e);
                                 escuantitativa = false;
                                //notaDouble = new Double(notaLeida);
                            }
                                
                        }
                        if(notas.size()>0){
                            notaa = notas.get(0);
                             
                            if(notaa.getNota2()<=0){
                                notaa.setNota2(notaDouble);
                                
                            }else if(notaa.getNota3()<=0){
                                notaa.setNota3(notaDouble);
                            }else if(notaa.getNota4()<=0){
                                notaa.setNota4(notaDouble);
                            }else if(notaa.getNota5()<=0){
                                notaa.setNota5(notaDouble);
                            }else if(notaa.getNota6()<=0){
                                notaa.setNota6(notaDouble);
                            }
                            
                            adm.actualizar(notaa);
                        }else{
                            notaa = new Notas();
                            notaa.setCodigonot(sec.generarClave());
                            notaa.setMatricula(new Matriculas(new Integer(codigomatricula)));
                            notaa.setMateria(materiaProfeActual.getMateria());
                            notaa.setFecha(new Date());
                            notaa.setOrden(materiaProfeActual.getOrden());
                            notaa.setCuantitativa(materiaProfeActual.getCuantitativa());
                            notaa.setPromedia(true);
                            notaa.setSeimprime(true);
                            notaa.setDisciplina(false);
                            notaa.setNota1(notaDouble);
                            notaa.setNota2(0d);
                            notaa.setNota3(0d);
                            notaa.setNota4(0d);
                            notaa.setNota5(0d);
                            notaa.setNota6(0d);
                            adm.guardar(notaa); 
                            //inter.set("nota", nota);
                        }
                        
                    }
                    System.out.println("\n");
                }
//            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ReadExcelMigrarNotas excel = new ReadExcelMigrarNotas();
        excel.leerArchivoExcel("E:\\SISTEMA\\primerobachi.xls");
    }
}
