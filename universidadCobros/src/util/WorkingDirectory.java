/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;
import java.io.File;
import java.net.URL;

public class WorkingDirectory {

    private static File WORKING_DIRECTORY;

    public static File get() {
        if(WORKING_DIRECTORY == null) {
            try {
                URL url = WorkingDirectory.class.getResource("/images/cancel.gif");
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
        System.out.println(WorkingDirectory.get());
//        System.out.println(System.getProperty("user.dir"));
    }

}
