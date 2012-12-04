package bean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;
import utilerias.RecuperarBean;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@SessionScoped
public class LoginBean {

    public String usuario;
    public String clave;
    Administrador adm;
    FacesContext context = FacesContext.getCurrentInstance();

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        super();
        usuario = "";
        clave = "";
        adm = new Administrador();
        context = FacesContext.getCurrentInstance();
//        context.addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_INFO, "", ""));

        try {
            List<Institucion> user = adm.query("Select o from Institucion as o ");
            ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("institucion");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("accesos");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("institucion", user.get(0));

            String fileFoto = servletContext.getRealPath("") + File.separator + "logo.png";
            String fileFoto2 = servletContext.getRealPath("") + File.separator + "imagen.png";
            if (!new File(fileFoto).exists() || !new File(fileFoto2).exists()) {
                System.out.println("ENTRO A SELECCIONAR LA IMAGEN...");
                generarImagen("logo.png", user.get(0).getLogo());
                generarImagen("imagen.png", user.get(0).getImagen());
            }
        } catch (Exception e) {
            System.out.println("NO SE PUDO CARGAR LA IMAGEN DE INICIO");
        }

        try {
            //FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().remove("accesos");
            context.getExternalContext().getSessionMap().remove("user");
            context.getExternalContext().getSessionMap().remove("periodo");
        } catch (Exception e) {
        }


    }
    public Periodos periodoSeleccionado;

    public List<SelectItem> getSelectedItemPeriodos() {
        try {
            List<Periodos> divisionPoliticas = new ArrayList<Periodos>();
            List<SelectItem> items = new ArrayList<SelectItem>();

            divisionPoliticas = adm.query("Select o from Periodos as o order by o.orden, o.fechaInicio ");
            if (divisionPoliticas.size() > 0) {
//                        Periodos objSel = new Periodos(0);
//                        items.add(new SelectItem(objSel, "SELECCIONE UN PERIODO"));
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM/yyyy");

                for (Periodos obj : divisionPoliticas) {
                    String fechaI = sdf.format(obj.getFechaInicio());
                    String fechaF = sdf.format(obj.getFechaFin());
                    items.add(new SelectItem(obj, " | " + fechaI + "-" + fechaF + " | " + (obj.getActivo() ? "Activo" : "Inactivo")));
                }
                divisionPoliticas = null;
            } else {
                Periodos obj = new Periodos(0);
                items.add(new SelectItem(obj, "NO PUEDE INGRESAR NO EXISTEN PERIODOS"));
            }

            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(EmpleadosBean.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }

    public String cambiarPeriodo() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("periodo");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("periodo", periodoSeleccionado);
        return null;
    }
    public String cedula;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String recuperarClave() {
        RecuperarBean rec = new RecuperarBean();
        return rec.recuperarClave(cedula);
    }

    public boolean verificarPermisoReporte(String idVariable, String accionPantalla, String accion, Boolean pantalla, String modulo) {


        if (idVariable == null) {
            return true;
        }
        idVariable = idVariable.replace("_", " ");
        List<Accesos> accesosList = adm.query("Select o from Accesos as o  "
                + " where o.idPerfiles.idPerfiles is null ");
        Empleados empleadoAc = (Empleados) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if (empleadoAc == null) {
            return false;
        }
        if (idVariable.equals("-1")) {
            return true;
        }
        if (idVariable.toUpperCase().contains("LINEA")) {
            return true;
        }
        if (accesosList == null) {
            accesosList = new ArrayList<Accesos>();

        }
        for (Iterator<Accesos> it = accesosList.iterator(); it.hasNext();) {
            Accesos accesos = it.next();
//            int inicio = accesos.getModulo().indexOf("[");
//            int finales = accesos.getModulo().indexOf("]");
            String elmodulo = accesos.getVariable();
            try {
//                elmodulo = accesos.getModulo().substring(inicio + 1, finales);
            } catch (Exception e) {
                //System.out.println("error leve"+e);
                elmodulo = accesos.getModulo();
            }


            if (elmodulo.equals(idVariable)) {
//                 System.out.println("MODULO: "+elmodulo);
                if (accion.equals("ingresar")) {
                    return accesos.getIngresar();
                } else if (accion.equals("agregar")) {
                    return accesos.getAgregar();
                } else if (accion.equals("modificar")) {
                    return accesos.getModificar();
                } else if (accion.equals("eliminar")) {
                    return accesos.getEliminar();
                }
            }
        }

        if (empleadoAc.getIdPerfiles().getNombre().contains("ADMINIS")) {
            Accesos ac = new Accesos();
            Administrador adm = new Administrador();
            ac.setIdAccesos(adm.getNuevaClave("Accesos", "idAccesos"));
            if (pantalla) {

                ac.setModulo("" + modulo);
            } else {
                ac.setModulo("REPORTES");

            }
            ac.setVariable(idVariable);;

            ac.setAgregar(true);
            ac.setNombre(accionPantalla.replace("ingresar_", "").replace("actualizar_", "").replace("eliminar_", "").replace("agregar_", ""));
            ac.setIngresar(true);
            ac.setModificar(true);
            ac.setEliminar(Boolean.TRUE);
            ac.setIdPerfiles(empleadoAc.getIdPerfiles());
            if (adm.existe("Accesos", "variable", idVariable, "nombre", accionPantalla.replace("ingresar_", "").replace("actualizar_", "").replace("eliminar_", "").replace("agregar_", ""), " ").size() <= 0) {
                adm.guardar(ac);
            }

            accesosList.add(ac);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("accesos");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("accesos", accesosList);

            //CAMBIO LOS ****************************
            ac = new Accesos();
            ac.setIdAccesos(adm.getNuevaClave("Accesos", "idAccesos"));
            if (pantalla) {
                ac.setModulo("" + modulo);
            } else {
                ac.setModulo("REPORTES");

            }
            ac.setVariable(idVariable);
            ac.setNombre(accionPantalla.replace("ingresar_", "").replace("actualizar_", "").replace("eliminar_", "").replace("agregar_", ""));
            ac.setAgregar(true);
            ac.setIngresar(true);
            ac.setModificar(true);
            ac.setEliminar(Boolean.TRUE);
            ac.setIdPerfiles(null);
            if (adm.existe("Accesos", "variable", idVariable, "nombre", accionPantalla.replace("ingresar_", "").replace("actualizar_", "").replace("eliminar_", "").replace("agregar_", ""), " and o.idPerfiles is null").size() <= 0) {
                adm.guardar(ac);
            }
            //**********************



            return true;
        } else {
            Accesos ac = new Accesos();
            Administrador adm = new Administrador();
            ac.setIdAccesos(adm.getNuevaClave("Accesos", "idAccesos"));
            if (pantalla) {
                ac.setModulo(idVariable);
                ac.setModulo("" + modulo);
            } else {
                ac.setModulo("REPORTES");
            }
            ac.setVariable(idVariable);
            ac.setNombre(accionPantalla.replace("ingresar_", "").replace("actualizar_", "").replace("eliminar_", "").replace("agregar_", ""));
            ac.setAgregar(false);
            ac.setIngresar(false);
            ac.setModificar(false);
            ac.setEliminar(false);
            ac.setIdPerfiles(empleadoAc.getIdPerfiles());
//            if(adm.query("Select o from Accesos as o where o.modulo = '"+ac.getModulo()+"' and perfil is null ").size()>0)
            if (adm.existe("Accesos", "variable", idVariable, "nombre", accionPantalla.replace("ingresar_", "").replace("actualizar_", "").replace("eliminar_", "").replace("agregar_", ""), " ").size() <= 0) {
                adm.guardar(ac);
            }
            accesosList.add(ac);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("accesos");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("accesos", accesosList);

            return false;
        }

        //return false;

    }

    void generar() {
        verificarPermisoReporte("Institucion", "ingresar_institucion.jspx", "ingresar", true, "ADMINISTRACION");
        verificarPermisoReporte("Accesos", "accesos.jspx", "ingresar", true, "ADMINISTRACION");
        verificarPermisoReporte("Auditoria", "ingresar_auditoria.jspx", "ingresar", true, "ADMINISTRACION");
        verificarPermisoReporte("Matriculas", "ingresar_matriculas.jspx", "ingresar", true, "ADMINISTRACION");
        verificarPermisoReporte("Empleados", "ingresar_empleados.jspx", "ingresar", true, "ADMINISTRACION");

        
        verificarPermisoReporte("Aulas", "ingresar_aulas.jspx", "ingresar", true, "HORARIOS");
        verificarPermisoReporte("Horarios", "ingresar_horarios.jspx", "ingresar", true, "HORARIOS");
        verificarPermisoReporte("Horas", "ingresar_horas.jspx", "ingresar", true, "HORARIOS");
        
        
        verificarPermisoReporte("RangosGpa", "ingresar_rangosGpa.jspx", "ingresar", true, "NOTAS");
        verificarPermisoReporte("Carreras", "ingresar_carreras.jspx", "ingresar", true, "NOTAS");
        verificarPermisoReporte("Malla", "ingresar_carrerasMaterias.jspx", "ingresar", true, "NOTAS");
        verificarPermisoReporte("SecuenciaMaterias", "ingresar_carrerasMateriasSecuencia.jspx", "ingresar", true, "NOTAS");
        verificarPermisoReporte("SistemaNotas", "ingresar_sistemaNotas.jspx", "ingresar", true, "NOTAS");
        verificarPermisoReporte("Registro de Notas", "ingresar_notas.jspx", "ingresar", true, "NOTAS");
        
        
        verificarPermisoReporte("Rubros", "ingresar_rubros.jspx", "ingresar", true, "PAGOS");
        verificarPermisoReporte("CategoriasSociales", "ingresar_categoriasSociales.jspx", "ingresar", true, "PAGOS");
        verificarPermisoReporte("Bancos", "ingresar_bancos.jspx", "ingresar", true, "PAGOS");
        
        verificarPermisoReporte("Jornada", "ingresar_jornada.jspx", "ingresar", true, "PARAMETROS");
        verificarPermisoReporte("Canton", "ingresar_canton.jspx", "ingresar", true, "PARAMETROS");
        verificarPermisoReporte("Ejes", "ingresar_ejes.jspx", "ingresar", true, "PARAMETROS");

        verificarPermisoReporte("Escuela", "ingresar_escuela.jspx", "ingresar", true, "PARAMETROS");
        verificarPermisoReporte("Facultad", "ingresar_facultad.jspx", "ingresar", true, "PARAMETROS");
        verificarPermisoReporte("Materias", "ingresar_materias.jspx", "ingresar", true, "PARAMETROS");

        verificarPermisoReporte("Modalidad", "ingresar_modalidad.jspx", "ingresar", true, "PARAMETROS");
        verificarPermisoReporte("Niveles", "ingresar_niveles.jspx", "ingresar", true, "PARAMETROS");
        verificarPermisoReporte("Pais", "ingresar_pais.jspx", "ingresar", true, "PARAMETROS");
        verificarPermisoReporte("Perfiles", "ingresar_perfiles.jspx", "ingresar", true, "PARAMETROS");
        verificarPermisoReporte("Periodos", "ingresar_periodos.jspx", "ingresar", true, "PARAMETROS");
        verificarPermisoReporte("Provincia", "ingresar_provincia.jspx", "ingresar", true, "PARAMETROS");

        verificarPermisoReporte("RangosIngresos", "ingresar_rangosIngresos.jspx", "ingresar", true, "PARAMETROS");
        verificarPermisoReporte("TipoMateria", "ingresar_tipomateria.jspx", "ingresar", true, "PARAMETROS");
        verificarPermisoReporte("Titulos", "ingresar_titulos.jspx", "ingresar", true, "PARAMETROS");

    }

    public String loginAction() {

        try {
            Empleados user = (Empleados) adm.ingresoSistema(usuario, clave);

            if (user == null) {
                //FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage("El nombre de Usuario o Contraseña están incorrectas...!"));
                context.addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre de Usuario o Contraseña están incorrectas...!", "El nombre de Usuario o Contraseña están incorrectas...!"));
                return null;
            } else {
                generar();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("accesos");
                List<Accesos> accesos = adm.query("Select o from Accesos as o  "
                        + " where o.idPerfiles.idPerfiles = '" + user.getIdPerfiles().getIdPerfiles() + "' order by o.variable ");
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("accesos", accesos);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("periodo", periodoSeleccionado);

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("menu");
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.jspx");


                Auditar aud = new Auditar();
                aud.auditar(adm, "", "IngresarSistema", "", "");
            }



//            inicializar();
        } catch (Exception e) {
            //FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "datosform").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.toString(), bundle.getString("msg.iniciarSesion.error")));
            //log.error("loginAction() ERROR " + e);
            return null;
        }
        usuario = "";
        clave = "";
        try {


            String url = "index";
//            System.out.println(url);
            //context.getExternalContext().dispatch(url);
            //String encodeURL = context.getExternalContext().encodeResourceURL(url);
            //context.getExternalContext().redirect(encodeURL);
        } catch (Exception e) {
            //log.error("loginAction() ERROR " + e);
        } finally {
            context.responseComplete();
        }
        return "index";
    }

    public void generarImagen(String nombre, byte[] datos) {

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String fileFoto = servletContext.getRealPath("") + File.separator + "fotos" + File.separator + nombre;
        FileImageOutputStream outputStream = null;
        try {
            if (!new File(fileFoto).exists()) {
                outputStream = new FileImageOutputStream(new File(fileFoto));
                outputStream.write(datos, 0, datos.length);
            }
        } catch (IOException e) {
            throw new FacesException("Error cargando fotografía.", e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
            }
        }
        datos = null;
    }

    protected UIComponent findComponent(UIComponent c, String id) {
        if (id.equals(c.getId())) {
            return c;
        }
        Iterator<UIComponent> kids = c.getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent found = findComponent(kids.next(), id);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public Periodos getPeriodoSeleccionado() {
        return periodoSeleccionado;
    }

    public void setPeriodoSeleccionado(Periodos periodoSeleccionado) {
        this.periodoSeleccionado = periodoSeleccionado;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
