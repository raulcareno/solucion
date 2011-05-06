/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package procesos;

import historias.formas.frmPrincipal;
 

    public class inicio {
         static UsuarioActivo datosConecta;
         public static Boolean comprobar() {

        GeneraXMLPersonal pXml = new GeneraXMLPersonal();
        pXml.inicio();
        pXml.leerXML();
        datosConecta = pXml.user;
        try {
            String nombre = datosConecta.getNombre();
            System.out.println("NOMB:" + nombre);
            if (nombre.equals("null") || datosConecta.getContrasenia().equals("null")) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        if (datosConecta == null) {
            return false;
        } else {
            return true;
        }

    }
        public static void main(String args[]) {
           if(comprobar()){
                new frmPrincipal().show();
           }else{
               new frmConfiguracion().show();
           }


//           adios.start();
       }
   }

