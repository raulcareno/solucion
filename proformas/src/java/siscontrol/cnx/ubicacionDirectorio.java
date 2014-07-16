/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siscontrol.cnx;
import java.io.File;
import java.net.URL;

public class ubicacionDirectorio {

    private static File WORKING_DIRECTORY;

    public static File get() {
        if(WORKING_DIRECTORY == null) {
            try {
                URL url = ubicacionDirectorio.class.getResource("/images/bloquear.gif");
//                System.out.println(url);
                if(url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile()
                    .getParentFile()
                    .getParentFile();
                    WORKING_DIRECTORY = f;
                } else if(url.getProtocol().equals("jar")) {
                    String expected = "!/util/cuadronotas.jasper";
                    String s = url.toString();
                    s = s.substring(4);
                    s = s.substring(0, s.length() - expected.length());
                    File f = new File(new URL(s).toURI());
                    f = f.getParentFile();
                    WORKING_DIRECTORY = f;
                }
            } catch(Exception e) {
                WORKING_DIRECTORY = new File(".");
            }
        }
        return WORKING_DIRECTORY;
    }

    public static void main(String[] args) {
        System.out.println(ubicacionDirectorio.get());
//        System.out.println(System.getProperty("user.dir"));
    }

}
