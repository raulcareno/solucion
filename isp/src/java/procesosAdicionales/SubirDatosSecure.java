/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package procesosAdicionales;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jcinform.conexion.Administrador;
import jcinform.persistencia.Contratos;
import jcinform.persistencia.Plan;
import jxl.*;
import org.zkoss.zul.Combobox;
 
public class SubirDatosSecure {

    public SubirDatosSecure() {
    }
    public boolean listadoBusca(List<Contratos> contratos, Contratos compara){
        int i = 0;
        for (Iterator<Contratos> it = contratos.iterator(); it.hasNext();) {
            Contratos contratos1 = it.next();
            if(contratos1.getContrato().equals(compara.getContrato())){
                i++;
            }
        }
        if(i>0){
            return true;
        }else{
            return false;
        }
        
    
    }

    public ArrayList leerArchivoExcel( org.zkoss.util.media.AMedia media, String estado) {
        try {
             System.out.println(media.getFormat());
	                System.out.println(media.getContentType());
	                System.out.println(media.getName());
            Administrador adm = new Administrador();
            List contratosActivos = null;
            if(estado.equals("Habilitado")){
                    contratosActivos = adm.query("Select o from Contratos as o where o.estado = 'Activo' ");
            }else{
                    contratosActivos = adm.query("Select o from Contratos as o where o.estado in ('Cortado','Suspendido') ");
                
            }
            
                        //InputStream input = new ByteArrayInputStream(getByteData());
            Workbook archivoExcel = Workbook.getWorkbook(media.getStreamData());
            System.out.println("Número de Hojas\t" + archivoExcel.getNumberOfSheets());
//            secuencial sec = new secuencial();

 
            Sheet hoja = archivoExcel.getSheet(0);
            System.out.println("NOMBRE DE LA HOJA" + hoja.getName());
            int numFilas = hoja.getRows();
            System.out.println("------------------------------" + archivoExcel.getSheet(0).getName());
            //PRIMERO CREO EL DISTRIBUTIVO
            ArrayList errores = new ArrayList();
            for (int fila = 2; fila < numFilas; fila++) { // Recorre cada 
                System.out.println("4. "+hoja.getCell(4, fila).getContents()); //numero de contrato
                System.out.println("10. "+hoja.getCell(10, fila).getContents()); //tipo de plan
                System.out.println("17. "+hoja.getCell(17, fila).getContents()); //estado del contrato
                String contratoCodigo = "";
                Contratos con = new Contratos();
                if((hoja.getCell(4, fila).getContents()+"").equals("")){
                    contratoCodigo = "0";
                }else{
                    contratoCodigo = (""+hoja.getCell(4, fila).getContents());
                }
                con.setContrato(new Integer(contratoCodigo));
                
                Plan p = new Plan();
                p.setNombre(hoja.getCell(10, fila).getContents()); 
                con.setPlan(p); 
                con.setEstado("Activo"); 
                if(!listadoBusca(contratosActivos, con)){
                        errores.add("fila:\t"+fila+"\tContrato:\t"+con.getContrato()+"\tCLIENTE:\t"+ hoja.getCell(2, fila).getContents());
                }
            }


            //System.out.println(""+errores+""+errores.size());
            return errores;
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return new ArrayList();

    }

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        // TODO code application logic here
//        SubirDatosSecure excel = new SubirDatosSecure();
////        excel.leerArchivoExcel("E:\\docentes1.xls");
//    }
}
