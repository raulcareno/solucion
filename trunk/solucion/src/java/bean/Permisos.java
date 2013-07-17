/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

//import java.awt.Image;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import java.util.Vector;
import javax.faces.context.FacesContext;
import jcinform.persistencia.*;
import jcinform.persistencia.ParametrosGlobales;
import jcinform.procesos.Administrador;

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

    public void llenar(Empleados user, Periodo per) {
        Administrador adm = new Administrador();
//        adm.queryNativo(null, Accesos.class)
        //user.getCodigoemp()
        //Window win = (Window) Executions.createComponents("/notasEstudiantes.zul", null, null);
        List listado = adm.query("Select o from Accesos as o where o.perfil.codigo = '" + user.getPerfil().getCodigo() + "'");
        Session a = Sessions.getCurrent();

        a.setAttribute("accesos", listado);
        a.setAttribute("user", user);
        a.setAttribute("periodo", per);
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
    public void crearParametros(){
        
    }

    public boolean verificarPermisoReporte(String idVariable, String descripcion, String accion, Boolean pantalla, String grupo) {

        Session a = Sessions.getCurrent();
        if (idVariable == null) {
            return true;
        }
        idVariable = idVariable.replace("_", " ");
        List<Accesos> accesosList = (List<Accesos>) a.getAttribute("accesos");
        Empleados empleadoAc = (Empleados) a.getAttribute("user");

        if (idVariable.equals("-1")) {
            return true;
        }
        if (idVariable.toUpperCase().contains("LINEA")) {
            return true;
        }
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


            if (elmodulo.equals(idVariable)) {
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

        if (empleadoAc.getPerfil().getDescripcion().contains("ADMINIS")) {
            Accesos ac = new Accesos();
            Administrador adm = new Administrador();
            ac.setCodigoacc(adm.getNuevaClave("Accesos", "codigoacc"));
            if (pantalla) {
                ac.setModulo(idVariable);
                ac.setGrupo("MATRICULAS");
            } else {
                ac.setModulo("REP | " + descripcion + " [" + idVariable + "]");

            }
            ac.setGrupo(grupo);

            ac.setGuardar(true);
            ac.setIngresar(true);
            ac.setActualizar(true);
            ac.setEliminar(Boolean.TRUE);
            ac.setPerfil(empleadoAc.getPerfil());
            adm.guardar(ac);

            accesosList.add(ac);
            a.removeAttribute("accesos");
            a.setAttribute("accesos", accesosList);
            
            //CAMBIO LOS ****************************
            ac = new Accesos();
            ac.setCodigoacc(adm.getNuevaClave("Accesos", "codigoacc"));
            if (pantalla) {
                ac.setModulo(idVariable);
                ac.setGrupo("MATRICULAS");
            } else {
                ac.setModulo("REP | " + descripcion + " [" + idVariable + "]");

            }
            ac.setGrupo(grupo);

            ac.setGuardar(true);
            ac.setIngresar(true);
            ac.setActualizar(true);
            ac.setEliminar(Boolean.TRUE);
            ac.setPerfil(null);
            adm.guardar(ac);
            //**********************
            
            

            return true;
        } else {
            Accesos ac = new Accesos();
            Administrador adm = new Administrador();
            ac.setCodigoacc(adm.getNuevaClave("Accesos", "codigoacc"));
            if (pantalla) {
                ac.setModulo(idVariable);
                ac.setGrupo("MATRICULAS");
            } else {
                ac.setModulo("REP | " + descripcion + " [" + idVariable + "]");
            }
            ac.setGrupo(grupo);
            ac.setGuardar(false);
            ac.setIngresar(false);
            ac.setActualizar(false);
            ac.setEliminar(false);
            ac.setPerfil(empleadoAc.getPerfil());
//            if(adm.query("Select o from Accesos as o where o.modulo = '"+ac.getModulo()+"' and perfil is null ").size()>0)
            adm.guardar(ac);
            accesosList.add(ac);
            a.removeAttribute("accesos");
            a.setAttribute("accesos", accesosList);
            
            return false;
        }

        //return false;

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
            audi.setUsuario(emp);
            audi.setTabla(tabla);
            audi.setTipo(tipo);
            adm.guardar(audi);
        } catch (Exception e) {
            System.out.println("ERROR EN AUDITAR" + e);
        }
    }

    public void auditarRepre(String tabla, String tipo, String campo) {
        try {
            Session a = Sessions.getCurrent();
            Administrador adm = new Administrador();
            Estudiantes emp = (Estudiantes) a.getAttribute("userEstudiante");

            Representante repre = (Representante) a.getAttribute("userRepresentante");

            Auditoria audi = new Auditoria();
            if (repre != null) {
                audi.setRepresentante(repre);
            } else {
                audi.setEstudiante(emp);
            }
            audi.setCodigo(adm.getNuevaClave("Auditoria", "codigo"));
            audi.setFecha(new Date());
            audi.setIp(a.getRemoteAddr());
            audi.setPc(campo);
            audi.setTabla(tabla);
            audi.setTipo(tipo);
            adm.guardar(audi);
        } catch (Exception e) {
            System.out.println("ERROR EN AUDITAR" + e);
        }
    }

    public Integer nuevaMatricula() {
        Administrador adm = new Administrador();
        Periodo periodo = (Periodo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("periodo");
        List NoActualMatricula = adm.query("Select o from ParametrosGlobales as o "
                + "where o.variable = 'MATRICULA' and o.periodo.codigoper = '" + periodo.getCodigoper() + "'");
        ParametrosGlobales parametros = new ParametrosGlobales();
        parametros = (ParametrosGlobales) NoActualMatricula.get(0);
        int noMatri = 0;
        Double decs = parametros.getNvalor();
        Long val = java.lang.Math.round(decs);
        noMatri = Integer.valueOf(val.toString());
        noMatri += 1;
        List<Matriculas> numeroYa = adm.query("Select o from Matriculas as o "
                + "where o.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "and o.numero = '" + noMatri + "'");
        if (numeroYa.size() > 0) {
            Integer nClave = adm.geUltimaMatricula("Select max(o.numero) from Matricula as o "
                    + "where o.curso.periodo.codigoper= '" + periodo.getCodigoper() + "'");
            parametros.setNvalor(new Double(nClave + 1));
            adm.actualizar(parametros);
            return nClave + 1;
        } else {
            parametros.setNvalor(new Double(noMatri));
            adm.actualizar(parametros);
            return noMatri;
        }
    }

    public Double redondear(Double numero, int decimales) {
        try {
            BigDecimal d = new BigDecimal(numero+"");
            d = d.setScale(decimales, RoundingMode.HALF_UP);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }
        String truncar = "public Double truncar(Double numero, int decimales) {         try {             java.math.BigDecimal d = new java.math.BigDecimal(numero);             d = d.setScale(decimales, java.math.BigDecimal.ROUND_DOWN);             return d.doubleValue();         } catch (Exception e) {             return 0.0;         }     }";
    public Double truncar(Double numero, int decimales) {
        try {
            BigDecimal d = new BigDecimal(numero);
            d = d.setScale(decimales, java.math.BigDecimal.ROUND_DOWN);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }


}
