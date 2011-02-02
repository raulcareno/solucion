package hibernate;

public class XMLFileConfig {
    private static long idEmpresa;    

    private static String hostConexion;
    private static String bdConexion;
    private static int puertoConexion;



    public XMLFileConfig() {}

    public static String getBdConexion() {
        return bdConexion;
    }

    public static void setBdConexion(String bdConexion) {
        XMLFileConfig.bdConexion = bdConexion;
    }

    public static String getHostConexion() {
        return hostConexion;
    }

    public static void setHostConexion(String hostConexion) {
        XMLFileConfig.hostConexion = hostConexion;
    }

   
}
