package bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.Sistemacalificacion;
import jcinform.persistencia.Sistemaevaluacion;
import jcinform.procesos.Administrador;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.zkoss.zhtml.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

/**
 *
 * @author GEOVANNY
 */
public class exportarExcel {

    public void exportarExcel() {
    }
//        response.setContentType("application/xls");
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Content-disposition", "attachment;filename=cuadroCalificaciones.xls");

    public void exportarAExcel(Grid datos,Sistemacalificacion sistema) {
        try {
      Administrador adm = new Administrador();
      List<Sistemaevaluacion> notas = adm.query("Select o from Sistemaevaluacion as o  "
                + "where o.sistemacalificacion.codigosis = '" + sistema.getCodigosis() + "' order by o.orden ");
        HSSFWorkbook libro = new HSSFWorkbook();
        HSSFSheet hoja = libro.createSheet("notas");
        //hoja.addMergedRegion(new CellRangeAddress(0,0,0,0));
        hoja.addMergedRegion(new Region(0,Short.parseShort("0"),0,Short.parseShort("0")));
        //hoja.addMergedRegion(new CellRangeAddress(0,0,2,6));
        String inicial = "";
        short  desde = 2;
        short hasta = 2;
        HSSFRow filaCabecera = hoja.createRow(0);
        for (Iterator<Sistemaevaluacion> it = notas.iterator(); it.hasNext();) {
            Sistemaevaluacion sistemaevaluacion = it.next();
            if(sistemaevaluacion.getEvaluacion()!=null){
            if(inicial.equals("")){
                inicial = sistemaevaluacion.getEvaluacion().getDescripcion();   
                hasta ++;
            }else if(inicial.equals(sistemaevaluacion.getEvaluacion().getDescripcion())){
                hasta ++;
            }else{
                 //hoja.addMergedRegion(new CellRangeAddress(0,0,desde,hasta));
                 hoja.addMergedRegion(new Region(0,Short.parseShort("0"),desde,hasta));
                 HSSFCell celda = filaCabecera.createCell((short) hasta);
                 HSSFRichTextString textoAnadir = new HSSFRichTextString("" +sistemaevaluacion.getEvaluacion().getDescripcion());
                 celda.setCellType(HSSFCell.CELL_TYPE_STRING);
                            //celda.setAsActiveCell();
                 celda.setCellValue(textoAnadir);
                 desde = hasta;
                 hasta++;
                 inicial = sistemaevaluacion.getEvaluacion().getDescripcion();
            }
            }
        }
        
        int i = 1;
        int j = 1;
        Boolean generadoCabeceeras = false;
        for (Object filas : datos.getRows().getChildren()) {
            Row fila2 = (Row) filas;
            j = 0;
            if (generadoCabeceeras == false) {
                for (Object columnas : datos.getHeads()) {
                    System.out.println("" + columnas);
                    HSSFRow fila = hoja.createRow(i);
                    if (columnas instanceof Auxhead) {
//                    j=0;
//                        List filaColumna = ((Auxhead) columnas).getChildren();
//                        for (Iterator it = filaColumna.iterator(); it.hasNext();) {
//                            Object object = it.next();
//                            String valor = "";
//                            HSSFCell celda = fila.createCell((short) j);
//                            HSSFRichTextString textoAnadir = new HSSFRichTextString("" + ((Auxheader) object).getLabel());
//                            celda.setCellType(HSSFCell.CELL_TYPE_STRING);
//                            //celda.setAsActiveCell();
//                            celda.setCellValue(textoAnadir);
//                            j++;
//                        }
                        
                    } else if (columnas instanceof Columns) {
                    j=0;
                        List filaColumna = ((Columns) columnas).getChildren();
                        for (Iterator it = filaColumna.iterator(); it.hasNext();) {
                            Object object = it.next();
                            String valor = "";
                            HSSFCell celda = fila.createCell((short) j);
                            HSSFRichTextString textoAnadir = new HSSFRichTextString("" + ((Column) object).getLabel());
                            celda.setCellType(HSSFCell.CELL_TYPE_STRING);
                            //celda.setAsActiveCell();
                            celda.setCellValue(textoAnadir);
                            j++;
                        }
                    }
                    
                    //i++;
                }

                generadoCabeceeras = true;
            }
            i++;
            j = 0;
            HSSFRow fila = hoja.createRow(i);
            //System.out.println(""+fila);
            List filaColumna = fila2.getChildren();
            for (Iterator it = filaColumna.iterator(); it.hasNext();) {

                Object object = it.next();
                String valor = "";
                if (object instanceof Label) {
                    Label val = ((Label) object);
                    valor = val.getValue();
                    HSSFCell celda = fila.createCell((short) j);
                    HSSFRichTextString textoAnadir = new HSSFRichTextString("" + val.getValue());
                    celda.setCellType(HSSFCell.CELL_TYPE_STRING);
                    //celda.setAsActiveCell();
                    celda.setCellValue(textoAnadir);
                } else if (object instanceof Decimalbox) {
                    Decimalbox val = ((Decimalbox) object);
                    valor = val.getValue() + "";
                    HSSFCell celda = fila.createCell((short) j);
                    celda.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    celda.setCellValue(val.getValue().doubleValue());
                }
                j++;
                System.out.println(valor);

            }


        }

        try {
            File f = new File("f://holamundo2.xls");
            FileOutputStream elFichero = new FileOutputStream(f);
            libro.write(elFichero);
            elFichero.close();
            FileInputStream input;
            byte[] data = null;
            try {
                input = new FileInputStream(f);
                data = new byte[input.available()];
                input.read(data);
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Filedownload.save(libro.getBytes(), "application/ms-excel", "formato.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void export_to_csv(Listbox listbox) {
        String s = ";\t";
        StringBuffer sb = new StringBuffer();
        for (Object head : listbox.getHeads()) {
            String h = "";
            for (Object header : ((Listhead) head).getChildren()) {
                h += ((Listheader) header).getLabel() + s;
            }
            sb.append(h + "\n");
        }
        for (Object item : listbox.getItems()) {
            String i = "";
            for (Object cell : ((Listitem) item).getChildren()) {
                i += ((Listcell) cell).getLabel() + s;
            }
            sb.append(i + "\n");
        }
        Filedownload.save(sb.toString().getBytes(), "text/plain", "auditoria.csv");
    }

    public void exportar(Listbox listbox) {
        FileOutputStream fileOut = null;
        try {
            //         HSSFWorkbook libro = new HSSFWorkbook();
            //         HSSFSheet hoja = libro.createSheet();
            //
            HSSFWorkbook milibro = new HSSFWorkbook();
            HSSFSheet mihoja = milibro.createSheet("auditoria_jcinform");
            HSSFPrintSetup ps = mihoja.getPrintSetup();
            mihoja.setAutobreaks(true);
            ps.setFitHeight((short) 1);
            ps.setFitWidth((short) 1);
            HSSFRow row = null;
            for (Object head : listbox.getHeads()) {
                int i = 0;
                for (Object header : ((Listhead) head).getChildren()) {
                    row = mihoja.createRow((short) i); //fila
                    mihoja.autoSizeColumn((short) 1);
                    row.createCell((short) 1).setCellValue("" + ((Listheader) header).getLabel()); //columna
                }
            }
            for (Object item : listbox.getItems()) {
                int i = 3;
                int j = 0;
                for (Object cell : ((Listitem) item).getChildren()) {
                    row = mihoja.createRow((short) i); //fila
                    mihoja.autoSizeColumn((short) 0);
                    row.createCell((short) 0).setCellValue("" + ((Listcell) cell).getLabel()); //columna
                    j++;
                }
            }
            System.out.println("NUMERO DE BYTES: " + milibro.getBytes().length);
//            fileOut = new FileOutputStream("workbook.xls");
//            milibro.write(fileOut);
//            fileOut.close();
            Filedownload.save(milibro.getBytes(), "application/xls", "auditoria.xls");
            //response.setContentType("application/vnd.ms-excel");
            //            ServletOutputStream outputStream = response.getOutputStream();
            //            milibro.write(outputStream);
            //            outputStream.flush();
            //            outputStream.close();
        } catch (Exception ex) {
            Logger.getLogger(exportarExcel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileOut.close();
            } catch (IOException ex) {
                Logger.getLogger(exportarExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }
}
