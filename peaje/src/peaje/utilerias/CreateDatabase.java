package peaje.utilerias;

import java.io.*;
import java.sql.*;

public class CreateDatabase {

    public static void main(String[] args) {
        System.out.println("Database creation example!");
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "jcinform@2020");
            try {
                Statement st = con.createStatement();
                
                st.executeUpdate("CREATE DATABASE turnos");
                
                 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/turnos", "root", "jcinform@2020");
                 st = con.createStatement();
//                File f = new File("F://PROYECTOS//peaje//da//datos.sql");
//                String aExe = obtenContenido(f);
//                st.executeQuery(aExe);
//                st.execute(aExe);
                BufferedReader in = new BufferedReader(new FileReader("F://PROYECTOS//peaje//da//datos2.sql"));
                String str;
                StringBuilder sb = new StringBuilder();
                while ((str = in.readLine()) != null) {
                    sb.append(str + "\n ");
                }
                in.close();
                
                // here is our splitter ! We use ";" as a delimiter for each request  
            // then we are sure to have well formed statements  
            String[] inst = sb.toString().split(";");  
  
//            Connection c = Database.getConnection();  
//            Statement st = c.createStatement();  
  
            for(int i = 0; i<inst.length; i++)  
            {  
                // we ensure that there is no spaces before or after the request string  
                // in order to not execute empty statements  
                if(!inst[i].trim().equals(""))  
                {  
                    st.executeUpdate(inst[i]);  
                    System.out.println(">>"+inst[i]);  
                }  
            }  
//                st.executeUpdate(sb.toString());
                System.out.println("1 row(s) affacted");
            } catch (SQLException s) {
                System.out.println("SQL statement is not executed!"+s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean executeDBScripts(String aSQLScriptFilePath, Statement stmt) throws IOException, SQLException {
        boolean isScriptExecuted = false;
        try {
            BufferedReader in = new BufferedReader(new FileReader(aSQLScriptFilePath));
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = in.readLine()) != null) {
                sb.append(str + "\n ");
            }
            in.close();
            stmt.executeUpdate(sb.toString());
            isScriptExecuted = true;
        } catch (Exception e) {
            System.err.println("Failed to Execute" + aSQLScriptFilePath + ". The error is" + e.getMessage());
        }
        return isScriptExecuted;
    }

    public static String obtenContenido(java.io.File documento) {
        String sCadena = "";
        String retorno = "";

        if (!documento.exists()) {
            return null;
        }

        try {

            BufferedReader bf = new BufferedReader(new FileReader(documento));
            while ((sCadena = bf.readLine()) != null) {
                retorno += sCadena;
            }

        } catch (FileNotFoundException fnfe) {
            return null;
        } catch (IOException ioe) {
            return null;
        }

        return retorno;
    }
}