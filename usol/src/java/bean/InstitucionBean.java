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
import jcinform.persistencia.Institucion;
import jcinform.procesos.Administrador;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;

import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class InstitucionBean {

    /**
     * Creates a new instance of InstitucionBean
     */
    Institucion object;
    Administrador adm;
    protected List<Institucion> model;
    public String textoBuscar;
    Permisos permisos;

    public InstitucionBean() {
        //super();
        FacesContext context = FacesContext.getCurrentInstance();
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
        if (!permisos.verificarPermisoReporte("Institucion", "ingresar_institucion", "ingresar", true, "PARAMETROS")) {
            try {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("noPuedeIngresar.jspx");
            } //selectedInstitucion = new Institucion();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(InstitucionBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(InstitucionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //selectedInstitucion = new Institucion();

    }

    public String editarAction(Institucion obj) {
        inicializar();

        object = obj;
        System.out.println("" + object.getIdInstitucion());
        return null;
    }

    protected void inicializar() {
        object = new Institucion(0);
        cargarDataModel();
    }
    
 
    String foto1 = "";
    String foto2 = "";


   static int contarImagen = 0;

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        if (contarImagen == 0) {
            foto1 = event.getFile().getFileName();
            object.setLogo(event.getFile().getContents());
            contarImagen++;
            final byte[] datos = event.getFile().getContents();
            final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            this.foto1 = "logo.png";
            final String fileFoto = servletContext.getRealPath("") + File.separator + "fotos" + File.separator + foto1;
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

        } else {
            foto2 = event.getFile().getFileName();
            object.setImagen(event.getFile().getContents());
            contarImagen = 0;
            final byte[] datos = event.getFile().getContents();
            final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            this.foto2 = "foto.png";
            final String fileFoto = servletContext.getRealPath("") + File.separator + "fotos" + File.separator + foto2;
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



        }



    }
    private DefaultStreamedContent content, content2;

    public static int getContarImagen() {
        return contarImagen;
    }

    public static void setContarImagen(int contarImagen) {
        InstitucionBean.contarImagen = contarImagen;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
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
        if (object.getIdInstitucion() == 0) {
            if (!permisos.verificarPermisoReporte("Institucion", "agregar_institucion", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
            }
            try {
                if (adm.existe("Institucion", "nombre", object.getNombre()).size() <= 0) {
                    object.setIdInstitucion(adm.getNuevaClave("Institucion", "idInstitucion"));
                    adm.guardar(object);
                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre ya existe...!", "Nombre ya existe...!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
            if (!permisos.verificarPermisoReporte("Institucion", "actualizar_institucion", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
            }
            try {
                adm.actualizar(object);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(InstitucionBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

        }

        return null;
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(Institucion obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("Institucion", "eliminar_institucion", "eliminar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
            }
            adm.eliminarObjeto(Institucion.class, obj.getIdInstitucion());
            inicializar();
            cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(InstitucionBean.class.getName()).log(Level.SEVERE, null, e);
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
            List<Institucion> datos = adm.query("Select o from Institucion as o");
            List<SelectItem> items = new ArrayList<SelectItem>();
            for (Institucion obj : datos) {
                items.add(new SelectItem(obj, obj.getNombre()));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(InstitucionBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
            model = (adm.listar("Institucion"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(InstitucionBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public void limpiar() {
        object = new Institucion(0);
    }

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
        try {
            //setModel(adm.listar("Institucion"));
            model = (adm.query("Select o from Institucion as o where o.nombre like '%" + textoBuscar + "%'"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(InstitucionBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    /**
     * propiedades
     *
     * @return
     */
    public List<Institucion> getModel() {
        if (model == null) {
            cargarDataModel();
        }
        return model;
    }

    public void setModel(List<Institucion> model) {
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

    public Institucion getObject() {
        if (object == null) {
            object = new Institucion(0);
        }
        return object;
    }

    public void setObject(Institucion object) {
        this.object = object;
    }
}
