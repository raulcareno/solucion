/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdConfiguracion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class execution {

    public static boolean executeQuery(String query) {

        Statement sentencias = null;
        conectaDB connector = new conectaDB();

        Connection con = connector.dameConexion("root", "seminario");

        if (connector.hasError()) {
            return false;
        }

        try {
            sentencias = con.createStatement();
            sentencias.executeUpdate(query);
            sentencias.close();
            con.close();
        } catch (Exception e) {
            return false;
        }

        return true;

    }

    public static String obtenContenido(String ubicacion) {
        String sCadena = "";
        String retorno = "";
        File documento = new File(ubicacion);
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

    public static boolean importaQuery(String documento) {
        String query = obtenContenido(documento);
        return executeQuery(query);
    }
}
