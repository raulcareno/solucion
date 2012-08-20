/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package turnosapplet;

/**
 *
 * @author inform
 */
public class TurnosApplet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //loadDriver_Method.invoke("loadDriver", new Object[]{temp_string});
         
        String sysdir = System.getenv("WINDIR") + "\\system32";
        System.out.println("------"+sysdir);
         sysdir = System.getenv("SYSTEMROOT") + "\\system32";
        System.out.println("------"+sysdir);
    }
}
