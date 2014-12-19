package sources;
/**
 *
 * @author GeovannyFrancisco
 */
import java.io.*;
import java.sql.*;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.*;
public class ExcelFile {

    public ExcelFile() {
    }
public void EjecutarSql(String sql){
try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/academico", "root", "jcinform@2020");
             Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Excel Sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);


            ResultSetMetaData rsmd = rs.getMetaData();

            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                String name = rsmd.getColumnName(i + 1);
                rowhead.createCell((short) i).setCellValue("" + name);

            }

            int index = 1;
            while (rs.next()) {

                HSSFRow row = sheet.createRow((short) index);
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    row.createCell((short) i).setCellValue(rs.getString(i + 1));
                }

                index++;
            }
//            FileOutputStream fileOut = new FileOutputStream("f:\\"+(new java.util.Date()).getTime()+".xls");
//            wb.write(fileOut);
//            fileOut.close();
//            System.out.println("Data is saved in excel file.");
//            rs.close();
//            
            
              try {
                File outFile = File.createTempFile((new java.util.Date()).getTime()+"", ".xls");
                FileOutputStream elFichero = new FileOutputStream(outFile);
                wb.write(elFichero);
                elFichero.close();
                rs.close();
                Filedownload.save(outFile, "application/vnd.ms-excel");

            } catch (Exception e) {
                e.printStackTrace();
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
}

 
public Grid EjecutarSqlVista(String sql){
try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/academico", "root", "jcinform@2020");
             Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            org.zkoss.zul.Grid lista = new org.zkoss.zul.Grid();
            ResultSetMetaData rsmd = rs.getMetaData();

         Columns cols = new Columns();
         lista.appendChild(cols);
         
          
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                String name = rsmd.getColumnName(i + 1);
                Column matCol = new Column(name);
                    matCol.setAlign("right");
                    lista.getColumns().appendChild(matCol);
                //rowhead.createCell((short) i).setCellValue("" + name);

            }

            int index = 1;
            while (rs.next()) {
              Row row = new Row();
                      
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    row = new Row();
                    row.setValue("1");
                    //lista.getRows().appendChild(cols)
                    Label ce = new Label(rs.getString(i + 1)+"");
                    row.appendChild(ce);
                }
                lista.appendChild(row);

                index++;
                if(index==10){
                    break;
                }
            }
            rs.close();
            connection.close();
        return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
}
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/academico", "root", "jcinform@2020");
            PreparedStatement psmnt = null;
            Statement st = connection.createStatement();
            String sqlEjecutar = "SELECT CONCAT(c.descripcion,' ',esp.descripcion,' ',par.descripcion) as curso, e.apellido,e.nombre  FROM matriculas m , cursos c, GLOBAL par,GLOBAL esp, estudiantes e WHERE  "
            + "e.codigoest = m.estudiante AND c.codigocur = m.curso AND c.paralelo = par.codigo AND  "
            + "c.especialidad = esp.codigo ORDER BY c.secuencia,par.descripcion,esp.descripcion, e.apellido, e.nombre"; 
            //sqlEjecutar = "Select * from estudiantes";
            ResultSet rs = st.executeQuery(sqlEjecutar);

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Excel Sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);


            ResultSetMetaData rsmd = rs.getMetaData();

            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                String name = rsmd.getColumnName(i + 1);
                rowhead.createCell((short) i).setCellValue("" + name);

            }

            int index = 1;
            while (rs.next()) {

                HSSFRow row = sheet.createRow((short) index);
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    row.createCell((short) i).setCellValue(rs.getString(i + 1));
                }

//                                row.createCell((short) 1).setCellValue(rs.getString(2));
//                                row.createCell((short) 2).setCellValue(rs.getString(3));
//                                row.createCell((short) 3).setCellValue(rs.getString(4));
//                                row.createCell((short) 4).setCellValue(rs.getString(5));
                index++;
            }
            FileOutputStream fileOut = new FileOutputStream("f:\\"+(new java.util.Date()).getTime()+".xls");
            wb.write(fileOut);
            fileOut.close();
            System.out.println("Data is saved in excel file.");
            rs.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}