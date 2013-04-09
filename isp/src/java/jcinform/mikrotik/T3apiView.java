/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.mikrotik;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author inform
 */
public class T3apiView {
 
    /**
     * @param args the command line arguments
     */
    public static void llamar(){
            ApiConn ret = new ApiConn("186.5.68.82", 8728);
       if (!ret.isConnected()) {
           ret.start();
           try {
               ret.join();
               if (ret.isConnected()) {
                   String mbs = "apomega77";
                      char[]  pas  = mbs.toCharArray();
                   ret.login("mb",pas);
               }
           } catch (InterruptedException ex) {
               Logger.getLogger(T3apiView.class.getName()).log(Level.SEVERE, null, ex);
               return;
           }
       }
       //ret.sendCommand("/ip/address/print");
       ret.sendCommand("ip firewall address-list add list=clientes_conectados address=10.1.1.57 comment=geova");
       
//       DataReceiver dataRec = new DataReceiver(ret, this);
//       dataRec.start();
    }
    public static void main(String[] args) {
        // TODO code application logic here
      llamar();
    }
}
