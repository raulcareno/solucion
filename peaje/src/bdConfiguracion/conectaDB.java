/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bdConfiguracion;

import java.sql.*; 

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class conectaDB {

    private final String dbDrvr = "com.mysql.jdbc.Driver";
    private final String dbName = "DBName";
    private final String dbHost = "jdbc:mysql://localhost:3306/" + dbName;
    private final String dbPort = "3306";
    private String mensajeError = "";

    public Connection dameConexion(String username, String password){
        Connection con = null;

        try{
            Class.forName(dbDrvr).newInstance();
        }catch(ClassNotFoundException cnfe){
            return null;
        }catch(InstantiationException ie){
            return null;
        }catch(IllegalAccessException iae){
            return null;
        }

        try{
            con = DriverManager.getConnection(this.dbHost, username, password);
        }catch(SQLException sqle){
            return null;
        }

        return con;
    }





    public boolean hasError(){
        if(this.mensajeError.length() > 0)
            return true;
        return false;
    }

    public String getError(){
        return this.mensajeError;
    } 
}
