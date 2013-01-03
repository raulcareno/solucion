/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.*;
import javax.swing.table.DefaultTableModel;
import jcinform.persistencia.Matriculas;
import jcinform.procesos.Administrador;

/**
 *
 * @author inform
 */
public class LeerInscrito extends Thread {
 Administrador adm = null;
 frmPrincipal princip = null;
    public LeerInscrito(frmPrincipal princip) {
        this.princip = princip;
    }

    @Override
    public void run() {
        try {
            adm = new Administrador();
            while (true) {
                
                DefaultTableModel dtm = (DefaultTableModel) princip.tablaInscritos.getModel();
                dtm.getDataVector().removeAllElements();
                List<Matriculas> matriculas = adm.query("Select o from Matriculas as o"
                        + " where o.estadoMat = 'I' and( o.pagadainscripcion is null or o.pagadainscripcion = false) ");
                if(matriculas.size()>0){
                 for (Iterator<Matriculas> it = matriculas.iterator(); it.hasNext();) {
                    Matriculas matriculas1 = it.next();
                    Object obj[] = new Object[2];
                    obj[0] = matriculas1.getIdEstudiantes();
                    obj[1] = matriculas1.getIdEstudiantes().getApellidoPaterno()+" "+ matriculas1.getIdEstudiantes().getApellidoMaterno()+" "+ matriculas1.getIdEstudiantes().getNombre();
                    dtm.addRow(obj);
                }
                 princip.tablaInscritos.setModel(dtm); 
                   
                    princip.panelInscritos.setVisible(true);
                }else{
                    princip.panelInscritos.setVisible(false);
                }
                System.out.println("RENEW: "+new Date());
                Thread.sleep(15000);
                
            }
        } catch (InterruptedException e) {
        }
    }
}
