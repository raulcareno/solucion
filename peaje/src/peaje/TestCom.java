/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peaje;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.*;
import java.util.*;
  class TestComm {
	public static void main(String [] args) throws NoSuchPortException {
        try {
            //String temp_string = "C:\\j2sdk1.4.2_13\\lib\\javax.comm.properties";
            String temp_string = "D:\\PROYECTOS\\peaje\\lib\\javax.comm.properties";
            Method loadDriver_Method = CommPortIdentifier.class.getDeclaredMethod("loadDriver", new Class[]{String.class});
            loadDriver_Method.setAccessible(true);
            loadDriver_Method.invoke("loadDriver", new Object[]{temp_string});
            CommPortIdentifier currentPort = null;
            Enumeration ports = CommPortIdentifier.getPortIdentifiers();
            System.out.println("Listing avaliable ports...");
            while (ports.hasMoreElements()) {
                currentPort = (CommPortIdentifier) ports.nextElement();
                System.out.println("Port Name: " + currentPort.getName());
                System.out.println("Port Type: " + currentPort.getPortType());
            }
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(TestComm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(TestComm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TestComm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(TestComm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(TestComm.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
