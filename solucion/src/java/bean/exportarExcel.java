package bean;
 
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zhtml.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author GEOVANNY
 */
public class exportarExcel   {
    public void exportarExcel()   {}
//        response.setContentType("application/xls");
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Content-disposition", "attachment;filename=cuadroCalificaciones.xls");
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

     public void exportar(Listbox listbox){
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
        }  finally {
            try {
                fileOut.close();
            } catch (IOException ex) {
                Logger.getLogger(exportarExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }
}


