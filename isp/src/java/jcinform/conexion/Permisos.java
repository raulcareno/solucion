/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.conexion;

//import java.awt.Image;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import java.util.Vector;
//import javax.faces.context.FacesContext;
import jcinform.persistencia.*;
//import jcinform.persistencia.ParametrosGlobales;

import org.zkoss.image.Image;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
//import org.zkoss.image.Image;
//import org.zkoss.image.AImage;

/**
 *
 * @author geovanny
 */
public class Permisos {

    claves cla = new claves();

    public Permisos() {
    }
    public static ArrayList usuarios;

    public org.zkoss.image.Image devolverImagen(String imageName, byte[] imageData) throws IOException {
//    Listbox a = new Listbox();
//    a.setModel(arg0)
//    a.clearSelection()
        org.zkoss.image.AImage alImage = new org.zkoss.image.AImage(imageName, imageData);
        org.zkoss.zul.Image zkImage = new org.zkoss.zul.Image();
        zkImage.setContent(alImage);
        Image mim = zkImage.getContent();
        Image nueva = mim;

        return nueva;
        //org.zkoss.image.Image miImage2 = zkImage.getContent();
        //return miImage2;

    }

    public Boolean verificarSistema() {
        //VERIFICAR PERMISOS DE USO DEL SISTEMA
        Administrador adm = new Administrador();
        List dat = adm.queryNativo("Select o.dl from mysql.plugin as o where o.name = 'status' ");
        try {


            if (dat != null) {

                Vector vec = (Vector) dat.get(0);
                String valorPrevio = vec.toString().replace("[", "").replace("]", "") + "";
                String valorPrevio3 = valorPrevio.substring(4);
                String valor = decriptar(valorPrevio3);
                if (valor.contains("jcinform@activo")) {

                    return true;
                } else {
//                    return;

                    return false;
                }

            } else {

                System.out.println("PERMISOS DENEGADO AL SISTEMA NO ESTA AUTORIZADO A USAR");
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        //VERIFICAR
    }

    public void llenar(Empleados user, Empleadossucursal per) {
        Administrador adm = new Administrador();
//        adm.queryNativo(null, Accesos.class)
        //user.getCodigoemp()
        //Window win = (Window) Executions.createComponents("/notasEstudiantes.zul", null, null);
        Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o ");
        List listado = adm.query("Select o from Accesos as o where o.perfil.codigo = '" + user.getPerfil().getCodigo() + "'");
        Session a = Sessions.getCurrent();

        a.setAttribute("accesos", listado);
        a.setAttribute("user", user);
        a.setAttribute("sector", per);
        a.setAttribute("empresa", emp);
//        a.setAttribute("periodo",per);
        if (usuarios == null) {
            usuarios = new ArrayList();
        } else {
            boolean estado = false;
            for (Iterator it = usuarios.iterator(); it.hasNext();) {
                Empleados object = (Empleados) it.next();
                if (object.equals(user)) {
                    estado = true;
                    break;
                }
            }
            if (!estado) {
                usuarios.add(user);
            }
        }
//        usuarios.add(user);


        auditar("-", "-", "Ingreso Sistema");
        //getServletConfig().getServletContext().getRealPath("WEB-INF/asistencia.jasper")
//       Executions.getCurrent().getContextPath();
//       Executions.getCurrent() ;
    }

    public boolean verificarPermiso(String modulo, String accion) {
        Session a = Sessions.getCurrent();
        modulo = modulo.replace("_", " ");



        List<Accesos> accesosList = (List<Accesos>) a.getAttribute("accesos");

        try {
            if (accesosList.size() <= 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        for (Iterator<Accesos> it = accesosList.iterator(); it.hasNext();) {
            Accesos accesos = it.next();
            if (accesos.getModulo().equals(modulo)) {
                if (accion.equals("Ingresar")) {
                    return accesos.getIngresar();
                } else if (accion.equals("Agregar")) {
                    return accesos.getGuardar();
                } else if (accion.equals("Modificar")) {
                    return accesos.getActualizar();
                } else if (accion.equals("Eliminar")) {
                    return accesos.getEliminar();
                }
            }
        }
        return false;

    }

    public boolean verificarPermisoReporte(String modulo, String accion) {
        Session a = Sessions.getCurrent();
        modulo = modulo.replace("_", " ");
        List<Accesos> accesosList = (List<Accesos>) a.getAttribute("accesos");
        for (Iterator<Accesos> it = accesosList.iterator(); it.hasNext();) {
            Accesos accesos = it.next();
            int inicio = accesos.getModulo().indexOf("[");
            int finales = accesos.getModulo().indexOf("]");
            String elmodulo = "";
            try {
                elmodulo = accesos.getModulo().substring(inicio + 1, finales);

            } catch (Exception e) {
                //System.out.println("error leve"+e);
                elmodulo = accesos.getModulo();
            }


            if (elmodulo.equals(modulo)) {
//                 System.out.println("MODULO: "+elmodulo);
                if (accion.equals("Ingresar")) {
                    return accesos.getIngresar();
                } else if (accion.equals("Agregar")) {
                    return accesos.getGuardar();
                } else if (accion.equals("Modificar")) {
                    return accesos.getActualizar();
                } else if (accion.equals("Eliminar")) {
                    return accesos.getEliminar();
                }
            }
        }
        return false;

    }

    public String encriptar(String clave) {
        try {
            if (clave.equals(null) || clave.equals("")) {
                return "";
            }
            return cla.encriptar(clave);
        } catch (Exception e) {


            return "";
        }
    }

    public String decriptar(String clave) {
        try {
            if (clave.equals(null) || clave.equals("")) {
                return "";
            }
            return cla.desencriptar(clave);
        } catch (Exception e) {
            return "";
        }

    }

    public void auditar(String tabla, String tipo, String campo) {
        try {
            Session a = Sessions.getCurrent();
            Administrador adm = new Administrador();
            Empleados emp = (Empleados) a.getAttribute("user");
            Auditoria audi = new Auditoria();

            audi.setCodigo(adm.getNuevaClave("Auditoria", "codigo"));
            audi.setFecha(new Date());
            audi.setIp(a.getRemoteAddr());
            //.getClientAddr()
            audi.setPc(campo);
            audi.setEmpleados(emp);
            audi.setTabla(tabla);
            audi.setTipo(tipo);
            adm.guardar(audi);
        } catch (Exception e) {
            System.out.println("ERROR EN AUDITAR" + e);
        }
    }

    public Double redondear(Double numero, int decimales) {
        try {
            BigDecimal d = new BigDecimal(numero);
            d = d.setScale(decimales, RoundingMode.HALF_UP);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }
}
