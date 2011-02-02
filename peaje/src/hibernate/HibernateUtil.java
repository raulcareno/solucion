package hibernate;

import org.hibernate.*;
import org.hibernate.cfg.*;
import hibernate.cargar.UsuarioActivo;
import hibernate.cargar.claves;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private static void createSessionFactory() {
        claves cl = new claves();
//        String url = "jdbc:mysql://" + XMLFileConfig.getHostConexion() + ":"
//                + XMLFileConfig.getPuertoConexion() + "/" + XMLFileConfig.getBdConexion();
        String url = "jdbc:mysql://" + UsuarioActivo.getIp() + ":" + UsuarioActivo.getPuerto() + "/peaje";
        String usu = UsuarioActivo.getNombre();
        String pass = cl.desencriptar(UsuarioActivo.getContrasenia());
        // Create the SessionFactory from hibernate.cfg.xml
        sessionFactory = new Configuration()
                .configure("/hibernate/hibernate.cfg.xml")
                .setProperty("hibernate.connection.url", url)
                .setProperty("hibernate.connection.username", usu)
                .setProperty("hibernate.connection.password", pass).buildSessionFactory();
                //.setProperty("hibernate.hbm2ddl.auto", "update").buildSessionFactory();
         
    }

    public static Boolean conectar() {
        try{
            createSessionFactory();
            return true;
        }catch(Exception a){
            
            a.printStackTrace();
            return false;
            
        }
    }

    public static void desconectar() {
        if (!sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
