/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Perfiles;
import jcinform.persistencia.Titulos;
import jcinform.procesos.Administrador;
import org.primefaces.event.FileUploadEvent;

import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class EmpleadosBean {

    /**
     * Creates a new instance of EmpleadosBean
     */
    Empleados object;
    Administrador adm;
    protected List<Empleados> model;
    public String textoBuscar;
    Permisos permisos;
Auditar  aud = new Auditar();
    public EmpleadosBean() {
        //super();
//        FacesContext context = FacesContext.getCurrentInstance();
//        String s = context.getExternalContext().getRequestParameterMap().get("skp");
//        if (s != null) {
//            System.out.println(s);
//        }
        if (adm == null) {
            adm = new Administrador();
        }
        if (permisos == null) {
            permisos = new Permisos();
        }
        if (!permisos.verificarPermisoReporte("Empleados", "ingresar_empleados", "ingresar", true, "PARAMETROS")) {
            try {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/noPuedeIngresar.jspx");
            } //selectedEmpleados = new Empleados();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(EmpleadosBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(EmpleadosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        inicializar();
        //selectedEmpleados = new Empleados();

    }

    public String editarAction(Empleados obj) {
        inicializar();
        object = obj;
        System.out.println("" + object.getIdEmpleados());
        return null;
    }

    protected void inicializar() {
        object = new Empleados("0");
         
        cargarDataModel();
    }

    /**
     * Graba el registro asociado al objeto que
     */
    public String guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (object.getNombre().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el NOMBRE", ""));
            return null;
        }
        if (object.getIdEmpleados().equals("0")) {
            if (!permisos.verificarPermisoReporte("Empleados", "agregar_empleados", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
            }
            try {
                if (adm.existe("Empleados", "nombre", object.getNombre()).size() <= 0) {
                    //object.setIdEmpleados(adm.getNuevaClave("Empleados", "idEmpleados"));
                    adm.guardar(object);
                    aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", object.getIdEmpleados()+"");
                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nombre ya existe...!","Nombre ya existe...!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
            if (!permisos.verificarPermisoReporte("Empleados", "actualizar_empleados", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
            }
            try {
                adm.actualizar(object);
                aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdEmpleados()+"");
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(EmpleadosBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

        }

        return null;
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(Empleados obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("Empleados", "eliminar_empleados", "eliminar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
            }
            adm.eliminarObjeto(Empleados.class, obj.getIdEmpleados());
            aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "eliminar", "", obj.getIdEmpleados()+"");
            inicializar();
            cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(EmpleadosBean.class.getName()).log(Level.SEVERE, null, e);
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return null;
    }

    /**
     *
     * Obtiene el registro seleccionado
     */
    public List<SelectItem> getSelectedItem() {
        try {
            List<Empleados> datos = adm.query("Select o from Empleados as o");
            List<SelectItem> items = new ArrayList<SelectItem>();
            for (Empleados obj : datos) {
                items.add(new SelectItem(obj, obj.getNombre()));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(EmpleadosBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
            model = (adm.listar("Empleados"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(EmpleadosBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public void limpiar() {
        object = new Empleados("0");
    }

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
        try {
            //setModel(adm.listar("Empleados"));
            model = (adm.query("Select o from Empleados as o where o.nombre like '%" + textoBuscar + "%' order by o.nombre "));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(EmpleadosBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }
    
      public void generarImagen(String nombre, byte[] datos) {

        final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
         
        final String fileFoto = servletContext.getRealPath("") + File.separator + "fotos" + File.separator + nombre;
        FileImageOutputStream outputStream = null;
        try {
            outputStream = new FileImageOutputStream(new File(fileFoto));
            outputStream.write(datos, 0, datos.length);
        } catch (IOException e) {
            throw new FacesException("Error guardando la foto.", e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
            }
        }
        datos = null;
    }
public void handleFileUpload(FileUploadEvent event) {
        //FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        byte[] datos = event.getFile().getContents();
            object.setFoto(event.getFile().getFileName());
            generarImagen(""+object.getIdEmpleados(), datos);
            datos = null;
             
      


    }

    public List<SelectItem> getSelectedItemTitulos() {
        try {
            List<Titulos> divisionPoliticas = new ArrayList<Titulos>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            if (object != null) {
                    divisionPoliticas = adm.query("Select o from Titulos as o order by o.nombre ");
                    if (divisionPoliticas.size() > 0) {
                        Titulos objSel = new Titulos(0);
                        items.add(new SelectItem(objSel, "Seleccione..."));
                        for (Titulos obj : divisionPoliticas) {
                            items.add(new SelectItem(obj, obj.getNombre()));
                        }
                    } else {
                        Titulos obj = new Titulos(0);
                        items.add(new SelectItem(obj, "NO EXISTEN PAISES"));
                    }
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(EmpleadosBean.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }
    public List<SelectItem> getSelectedItemPerfiles() {
        try {
            List<Perfiles> divisionPoliticas = new ArrayList<Perfiles>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            if (object != null) {
                    divisionPoliticas = adm.query("Select o from Perfiles as o order by o.nombre ");
                    if (divisionPoliticas.size() > 0) {
                        Perfiles objSel = new Perfiles(0);
                        items.add(new SelectItem(objSel, "Seleccione..."));
                        for (Perfiles obj : divisionPoliticas) {
                            items.add(new SelectItem(obj, obj.getNombre()));
                        }
                    } else {
                        Perfiles obj = new Perfiles(0);
                        items.add(new SelectItem(obj, "NO EXISTEN PAISES"));
                    }
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(EmpleadosBean.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }

    /**
     * propiedades
     *
     * @return
     */
    public List<Empleados> getModel() {
        if (model == null) {
            cargarDataModel();
        }
        return model;
    }

    public void setModel(List<Empleados> model) {
        this.model = model;
    }

    /**
     * busca un componente dentro del form.jspx para enviar mensajes
     *
     * @param c
     * @param id
     * @return
     */
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

    public String getTextoBuscar() {
        return textoBuscar;
    }

    public void setTextoBuscar(String textoBuscar) {
        this.textoBuscar = textoBuscar;
    }
    private static final long serialVersionUID = 1L;

    public Empleados getObject() {
        if (object == null) {
            object = new Empleados("0");
        }
        return object;
    }

    public void setObject(Empleados object) {
        this.object = object;
    }
}
