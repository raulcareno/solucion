//package sources;
//
//import java.io.*;
//
//import java.util.Iterator;
//import java.util.List;
//import javax.servlet.*;
//import javax.servlet.http.*;
//import jcinform.persistencia.Cursos;
//import jcinform.persistencia.Equivalencias;
//import jcinform.persistencia.MateriaProfesor;
//import jcinform.persistencia.Periodo;
//import jcinform.persistencia.Sistemacalificacion;
//import jcinform.procesos.Administrador;
//import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
// 
//
///**
// *
// * @author GEOVANNY
// */
//public class cuadroFinalExcel extends HttpServlet {
//
//    public int contador1 = 0,  contador2 = 0,  contador3 = 0,  contador4 = 0,  contador5 = 0;
//    public int contador6 = 0,  contador7 = 0,  contador8 = 0,  contador9 = 0,  contador10 = 0,  k = 0;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
// contador1 = 0;  contador2 = 0;  contador3 = 0;  contador4 = 0;  contador5 = 0;
// contador6 = 0;  contador7 = 0;  contador8 = 0;  contador9 = 0;  contador10 = 0;  k = 0;
//        Administrador adm = new Administrador();
//        varios var = new varios();
//
//        Periodo periodo = (Periodo) request.getSession().getAttribute("periodoActual");
//        String nombre = (String) request.getSession().getAttribute("nombre");
//        Cursos curso = (Cursos) request.getSession().getAttribute("curso");
//        curso = (Cursos) adm.buscarClave(curso.getCodigocur(),Cursos.class);
//        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o " +
//                "where o.perCodigo.perCodigo = '" + periodo.getCodigoper() + "' and o.eqGrupo = 'CA'");
//        int eqTamano = equivalencias.size();
//        Sistemacalificacion sistema = (Sistemacalificacion) request.getSession().getAttribute("sistema");
//        sistema = (Sistemacalificacion) adm.buscarClave(sistema.getCodigosis(),Sistemacalificacion.class);
//        response.setContentType("application/xls");
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Content-disposition", "attachment;filename=cuadroCalificaciones.xls");
//
//
//        HSSFWorkbook wb = new HSSFWorkbook();
//        HSSFSheet sheet = wb.createSheet("geovanny");
//        HSSFPrintSetup ps = sheet.getPrintSetup();
//        sheet.setAutobreaks(true);
//        ps.setFitHeight((short) 1);
//        ps.setFitWidth((short) 1);
//
//
//        HSSFRow row = sheet.createRow((short) 0);//fila
//
//        sheet.autoSizeColumn((short) 1);
//        row.createCell((short) 1).setCellValue("" + periodo.getInstitucion().getDenominacion());//columna
//
//        row = sheet.createRow((short) 1);//fila
//
//        sheet.autoSizeColumn((short) 1);
//        row.createCell((short) 1).setCellValue("" + periodo.getInstitucion().getNombre());//columna
//
//        row = sheet.createRow((short) 2);//fila
//
//        sheet.autoSizeColumn((short) 1);
//        row.createCell((short) 1).setCellValue("" + nombre);//columna
//
//        row = sheet.createRow((short) 3);//fila
//
//        sheet.autoSizeColumn((short) 1);
//        row.createCell((short) 1).setCellValue("" + periodo.getDescripcion());//columna
//
//        row = sheet.createRow((short) 4);//fila
//
//        sheet.autoSizeColumn((short) 1);
//        row.createCell((short) 1).setCellValue("NIVEL: " + curso);//columna
//
//        row = sheet.createRow((short) 5);//fila
//
//        sheet.autoSizeColumn((short) 1);
//        row.createCell((short) 1).setCellValue("" + sistema.getTipCodigo().getTipNombre());//columna
//        List<MateriaProfesor> materiaProfesor = adm.listarQueryMap("Select o from MateriaProfesor as o " +
//                        "where o.curCodigo.curCodigo= '" + curso.getCodigocur() + "' " +
//                        "and o.mapControl = true order by o.mapOrden");
//        List<MateriaProfesor> materiaProfesorOpcionales = adm.listarQueryMap("Select o from MateriaProfesor as o " +
//                    "where o.curCodigo.curCodigo= '" + curso.getCodigocur() + "' " +
//                    "and o.mapControl = false order by o.mapOrden");
//
//            List<AcaMatricula> matriculados = adm.buscarAcaMatriculaporCurso(curso.getCodigocur());
//
//            row = sheet.createRow((short) 7);//fila
//
//            sheet.autoSizeColumn((short) 0);
//            row.createCell((short) 0).setCellValue("Orden");//columna
//
//            row = sheet.createRow((short) 7);//fila
//
//            sheet.autoSizeColumn((short) 1);
//            row.createCell((short) 1).setCellValue("Apellidos y Nombres");//columna
//
//            int materiasCabecera = 2;
//                        for (Iterator<MateriaProfesor> ImapTitulos = materiaProfesor.iterator(); ImapTitulos.hasNext();) {
//                            MateriaProfesor acaMateriaProfesor = ImapTitulos.next();
//                            row = sheet.createRow((short) 7);
//                            sheet.autoSizeColumn((short) materiasCabecera);
//                            row.createCell((short) materiasCabecera).setCellValue("" + acaMateriaProfesor.getAsiCodigo().getAsiNombre());
//
//                            materiasCabecera++;
//                        }
//                            row = sheet.createRow((short) 7);
//                            sheet.autoSizeColumn((short) materiasCabecera);
//                            row.createCell((short) materiasCabecera).setCellValue("PROMEDIO");
//                            materiasCabecera++;
//
//                    for (Iterator<MateriaProfesor> ImapTitulos = materiaProfesorOpcionales.iterator(); ImapTitulos.hasNext();) {
//                            MateriaProfesor acaMateriaProfesor = ImapTitulos.next();
//                            row = sheet.createRow((short) 7);
//                            sheet.autoSizeColumn((short) materiasCabecera);
//                            row.createCell((short) materiasCabecera).setCellValue("" + acaMateriaProfesor.getAsiCodigo().getAsiNombre());
//
//                            materiasCabecera++;
//                        }
//            row = sheet.createRow((short) 7);
//            sheet.autoSizeColumn((short) materiasCabecera);
//            row.createCell((short) materiasCabecera).setCellValue("DISCIPLINA");
//            row = sheet.createRow((short) 7);
//            materiasCabecera++;
//            sheet.autoSizeColumn((short) materiasCabecera);
//            row.createCell((short) materiasCabecera).setCellValue("OBSERVACION");
//            //HSSFCellStyle.ALIGN_CENTER)
//            int i = 8;
//            int j = 1;
//            Double sumaPromedio =0.0;
//            for (Iterator<AcaMatricula> it = matriculados.iterator(); it.hasNext();) {
//                AcaMatricula acaMatricula = it.next();
//                row = sheet.createRow((short) i);//fila
//
//                sheet.autoSizeColumn((short) 0);
//                row.createCell((short) 0).setCellValue(j);//columna
//
//                j++;
//
//                row = sheet.createRow((short) i);//fila
//
//                sheet.autoSizeColumn((short) 1);
//                row.createCell((short) 1).setCellValue("" + acaMatricula.getEstCodigo().getEstApellido() + " " + acaMatricula.getEstCodigo().getEstNombre());//columna
//                //PARA IMPRIMIR EN LAS NOTAS
//
//                int materias = 2;
//                int contadorPromedio = 0;
//
//                for (Iterator<MateriaProfesor> Imap = materiaProfesor.iterator(); Imap.hasNext();) {
//                    MateriaProfesor acaMateriaProfesor = Imap.next();
//                    List<AcaNotas> notas = adm.listarQueryNotas("Select o from AcaNotas as o " +
//                            "where o.sisCodigo.sisCodigo ='" + sistema.getCodigosis() + "' " +
//                            "and o.asiCodigo.asiCodigo = '" + acaMateriaProfesor.getAsiCodigo().getAsiCodigo() + "' " +
//                            "and o.matCodigo.matCodigo = '" + acaMatricula.getMatCodigo() + "'");
//                    Double nota = 0.0;
//                    if (acaMateriaProfesor.getMapCuantitativa()) {
//                        if (notas.size() > 0) {
//                            AcaNotas notSeleccionada = notas.get(0);
//                            nota = notSeleccionada.getNotValor();
//                        }
//                        row = sheet.createRow((short) i);//fila
//                        sheet.autoSizeColumn((short) materias);
//                        row.createCell((short) materias).setCellValue(nota);//columna
//                        sumaPromedio +=nota;
//                        contadorPromedio++;
//                    } else {
//                        if (notas.size() > 0) {
//                            AcaNotas notSeleccionada = notas.get(0);
//                            nota = notSeleccionada.getNotValor();
//                        }
//                        row = sheet.createRow((short) i);//fila
//
//                        sheet.autoSizeColumn((short) materias);
//                        row.createCell((short) materias).setCellValue(comparacion(nota, eqTamano, equivalencias));//columna
//                         sumaPromedio +=nota;
//                         contadorPromedio++;
//                    }
//
//                    materias++;
//                } //FIN DEL FOR DE NOTAS
//                //PARA IMPRIMIR EL PROMEDIO
//               row = sheet.createRow((short) i);//fila
//                sheet.autoSizeColumn((short) materias);
//                //row.createCell((short) materias).setCellValue(var.redondear(sumaPromedio/contadorPromedio,2));//columna
//                row.createCell((short) materias).setCellValue(var.redondear(sumaPromedio/contadorPromedio,2));//columna
//                materias++;
//                //IMPRIMIR MATERIAS OPCIONALES
//                for (Iterator<MateriaProfesor> Imap = materiaProfesorOpcionales.iterator(); Imap.hasNext();) {
//                    MateriaProfesor acaMateriaProfesor = Imap.next();
//                    List<AcaNotas> notas = adm.listarQueryNotas("Select o from AcaNotas as o " +
//                            "where o.sisCodigo.sisCodigo ='" + sistema.getCodigosis() + "' " +
//                            "and o.asiCodigo.asiCodigo = '" + acaMateriaProfesor.getAsiCodigo().getAsiCodigo() + "' " +
//                            "and o.matCodigo.matCodigo = '" + acaMatricula.getMatCodigo() + "'");
//                    Double nota = 0.0;
//                    if (acaMateriaProfesor.getMapCuantitativa()) {
//                        if (notas.size() > 0) {
//                            AcaNotas notSeleccionada = notas.get(0);
//                            nota = notSeleccionada.getNotValor();
//                        }
//                        row = sheet.createRow((short) i);//fila
//
//                        sheet.autoSizeColumn((short) materias);
//                        row.createCell((short) materias).setCellValue(nota);//columna
//
//                    } else {
//                        if (notas.size() > 0) {
//                            AcaNotas notSeleccionada = notas.get(0);
//                            nota = notSeleccionada.getNotValor();
//                            notSeleccionada.getAsiCodigo().getAsiTipo().equals("A");
//                        }
//                        row = sheet.createRow((short) i);//fila
//
//                        sheet.autoSizeColumn((short) materias);
//                        row.createCell((short) materias).setCellValue(comparacion(nota, eqTamano, equivalencias));//columna
//
//                    }
//
//                    materias++;
//                } //FIN DEL FOR DE NOTAS
//
//
////PARA IMPRIMIR DISCIPLINA
//                List<AcaNotas> notas = adm.listarQueryNotas("Select o from AcaNotas as o " +
//                        "where o.sisCodigo.sisCodigo ='" + sistema.getCodigosis() + "' " +
//                        "and o.asiCodigo.asiCodigo = 0 " +
//                        "and o.matCodigo.matCodigo = '" + acaMatricula.getMatCodigo() + "'");
//                Double nota = 0.0;
//                if (notas.size() > 0) {
//                    AcaNotas notSeleccionada = notas.get(0);
//                    nota = notSeleccionada.getNotValor();
//                }
//                row = sheet.createRow((short) i);//fila
//
//                sheet.autoSizeColumn((short) materias);
//                row.createCell((short) materias).setCellValue(nota);//columna
//
//                materias++;
//                if (acaMatricula.getMatEstado().equals("M")) {
//                    row = sheet.createRow((short) i);//fila
//
//                    sheet.autoSizeColumn((short) materias);
//                    row.createCell((short) materias).setCellValue("");//columna
//
//                } else if (acaMatricula.getMatEstado().equals("R")) {
//                    String sexo = (acaMatricula.getEstCodigo().getEstGenero().equals("M") ? "O" : "A");
//                    row = sheet.createRow((short) i);//fila
//
//                    sheet.autoSizeColumn((short) materias);
//                    row.createCell((short) materias).setCellValue("RETIRAD" + sexo + " " +
//                            "" + acaMatricula.getMatfechaRet().toLocaleString().substring(0, 10));//columna
//
//                }
//                i++;
//
//
//            sumaPromedio =0.0;
//
//            }//FIN DEL FOR MATRICULAS
//
//
//        try {
//            response.setContentType("application/vnd.ms-excel");
//            ServletOutputStream outputStream = response.getOutputStream();
//            wb.write(outputStream);
//            outputStream.flush();
//            outputStream.close();
//        } catch (Exception ex) {
//        }
//
//    }
//
//// <editor-fold defaultstate="collapsed" desc="CALCULO DE MB, B, S, I ">
//    public String comparacion(Double nota, int eqTamano, List<Equivalencias> equivalencias) {
//        Equivalencias acaEquivalencias1 = null, acaEquivalencias2 = null, acaEquivalencias3 = null, acaEquivalencias4 = null, acaEquivalencias5 = null;
//        Equivalencias acaEquivalencias6 = null, acaEquivalencias7 = null, acaEquivalencias8 = null, acaEquivalencias9 = null, acaEquivalencias10 = null;
//        if (eqTamano >= 1) {
//            acaEquivalencias1 = equivalencias.get(0);
//        }
//        if (eqTamano >= 2) {
//            acaEquivalencias2 = equivalencias.get(1);
//        }
//        if (eqTamano >= 3) {
//            acaEquivalencias3 = equivalencias.get(2);
//        }
//        if (eqTamano >= 4) {
//            acaEquivalencias4 = equivalencias.get(3);
//        }
//        if (eqTamano >= 5) {
//            acaEquivalencias5 = equivalencias.get(4);
//        }
//        if (eqTamano >= 6) {
//            acaEquivalencias6 = equivalencias.get(5);
//        }
//        if (eqTamano >= 7) {
//            acaEquivalencias7 = equivalencias.get(6);
//        }
//        if (eqTamano >= 8) {
//            acaEquivalencias8 = equivalencias.get(7);
//        }
//        if (eqTamano >= 9) {
//            acaEquivalencias9 = equivalencias.get(8);
//        }
//        if (eqTamano >= 10) {
//            acaEquivalencias10 = equivalencias.get(9);
//        }
//        if (eqTamano >= 1) {
//            if (nota >= acaEquivalencias1.getEqVminimo() && nota <= acaEquivalencias1.getEqVmaximo()) {
//                return "" + acaEquivalencias1.getEqAbreviatura();
//            }
//        }
//        if (eqTamano >= 2) {
//            if (nota >= acaEquivalencias2.getEqVminimo() && nota <= acaEquivalencias2.getEqVmaximo()) {
//                return "" + acaEquivalencias2.getEqAbreviatura();
//            }
//        }
//        if (eqTamano >= 3) {
//            if (nota >= acaEquivalencias3.getEqVminimo() && nota <= acaEquivalencias3.getEqVmaximo()) {
//                return "" + acaEquivalencias3.getEqAbreviatura();
//            }
//        }
//        if (eqTamano >= 4) {
//            if (nota >= acaEquivalencias4.getEqVminimo() && nota <= acaEquivalencias4.getEqVmaximo()) {
//                return "" + acaEquivalencias4.getEqAbreviatura();
//            }
//        }
//        if (eqTamano >= 5) {
//            if (nota >= acaEquivalencias5.getEqVminimo() && nota <= acaEquivalencias5.getEqVmaximo()) {
//                return "" + acaEquivalencias5.getEqAbreviatura();
//            }
//        }
//        if (eqTamano >= 6) {
//            if (nota >= acaEquivalencias6.getEqVminimo() || nota <= acaEquivalencias6.getEqVmaximo());
//            return "" + acaEquivalencias6.getEqAbreviatura();
//        }
//        if (eqTamano >= 7) {
//            if (nota >= acaEquivalencias7.getEqVminimo() || nota <= acaEquivalencias7.getEqVmaximo());
//            return "" + acaEquivalencias7.getEqAbreviatura();
//        }
//        if (eqTamano >= 8) {
//            if (nota >= acaEquivalencias8.getEqVminimo() || nota <= acaEquivalencias8.getEqVmaximo());
//            return "" + acaEquivalencias8.getEqAbreviatura();
//        }
//        if (eqTamano >= 9) {
//            if (nota >= acaEquivalencias9.getEqVminimo() || nota <= acaEquivalencias9.getEqVmaximo());
//            return "" + acaEquivalencias9.getEqAbreviatura();
//        }
//        if (eqTamano >= 10) {
//            if (nota >= acaEquivalencias10.getEqVminimo() || nota <= acaEquivalencias10.getEqVmaximo());
//            return "" + acaEquivalencias10.getEqAbreviatura();
//        }
//        return null;
//    }
//// </editor-fold >
//}
//
