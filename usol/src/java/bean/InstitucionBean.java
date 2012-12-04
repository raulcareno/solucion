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
import jcinform.persistencia.Canton;
import jcinform.persistencia.Institucion;
import jcinform.persistencia.Pais;
import jcinform.persistencia.Provincia;
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
    boolean esLogo;
    boolean verSubir;
Auditar  aud = new Auditar();
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
        if (!permisos.verificarPermisoReporte("Institucion", "ingresar_institucion.jspx", "ingresar", true, "ADMINSTRACION")) {
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
        paisSeleccionado = object.getIdCanton().getIdProvincia().getIdPais();
        buscarProvincia();
        provinciaSeleccionado = object.getIdCanton().getIdProvincia();
        buscarCanton();
        cantonSeleccionado = object.getIdCanton();
        generarImagen("logo.png", object.getLogo());
        generarImagen("imagen.png", object.getImagen());
        System.out.println("" + object.getIdInstitucion());
        foto1 = "logo.png";
        foto2 = "imagen.png";
        return null;
    }

    protected void inicializar() {
        object = new Institucion(0);
        object.setIdCanton(new Canton());
        object.getIdCanton().setIdProvincia(new Provincia());
        object.getIdCanton().getIdProvincia().setIdPais(new Pais());
        cargarDataModel();
    }
    String foto1 = "";
    String foto2 = "";
    static int contarImagen = 0;

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
        if (esLogo) {
            foto1 = "logo.png";
            object.setLogo(event.getFile().getContents());
            generarImagen(foto1, datos);
            datos = null;
            verSubir = false;
        } else {
            foto2 = "imagen.png";
            object.setImagen(event.getFile().getContents());
            generarImagen(foto2, datos);
            datos = null;
            verSubir = false;
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
        object.setIdCanton(cantonSeleccionado);
        if (object.getNombre().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el NOMBRE", ""));
            return null;
        }
        if (object.getIdInstitucion() == 0) {
            if (!permisos.verificarPermisoReporte("Institucion", "agregar_institucion.jspx", "agregar", true, "ADMINSTRACION")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                if (adm.existe("Institucion", "nombre", object.getNombre()).size() <= 0) {
                    object.setIdInstitucion(adm.getNuevaClave("Institucion", "idInstitucion"));
                    adm.guardar(object);
                    aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", object.getIdInstitucion()+"");
                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre ya existe...!", "Nombre ya existe...!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
            if (!permisos.verificarPermisoReporte("Institucion", "actualizar_institucion.jspx", "agregar", true, "ADMINSTRACION")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                adm.actualizar(object);
                aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdInstitucion()+"");
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
            if (!permisos.verificarPermisoReporte("Institucion", "eliminar_institucion.jspx", "eliminar", true, "ADMINSTRACION")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            adm.eliminarObjeto(Institucion.class, obj.getIdInstitucion());
            aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "eliminar", "", obj.getIdInstitucion()+"");
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
    List<SelectItem> provinciasEncontradas;
    List<SelectItem> cantonesEncontradas;
    public Pais paisSeleccionado = new Pais();
    public Provincia provinciaSeleccionado = new Provincia();
    public Canton cantonSeleccionado = new Canton();

    public List<SelectItem> getProvinciasEncontradas() {
        return provinciasEncontradas;
    }

    public void setProvinciasEncontradas(List<SelectItem> provinciasEncontradas) {
        this.provinciasEncontradas = provinciasEncontradas;
    }

    public Pais getPaisSeleccionado() {
        return paisSeleccionado;
    }

    public void setPaisSeleccionado(Pais paisSeleccionado) {
        this.paisSeleccionado = paisSeleccionado;
    }

    public List<SelectItem> getCantonesEncontradas() {
        return cantonesEncontradas;
    }

    public void setCantonesEncontradas(List<SelectItem> cantonesEncontradas) {
        this.cantonesEncontradas = cantonesEncontradas;
    }

    public Provincia getProvinciaSeleccionado() {
        return provinciaSeleccionado;
    }

    public void setProvinciaSeleccionado(Provincia provinciaSeleccionado) {
        this.provinciaSeleccionado = provinciaSeleccionado;
    }

    public Canton getCantonSeleccionado() {
        return cantonSeleccionado;
    }

    public void setCantonSeleccionado(Canton cantonSeleccionado) {
        this.cantonSeleccionado = cantonSeleccionado;
    }

    /**
     * Obtiene el el listado de provincias
     *
     * @return
     */
    public void buscarCanton() {
        try {
            List<Canton> divisionPoliticas = new ArrayList<Canton>();
            cantonesEncontradas = new ArrayList<SelectItem>();

            if (object == null) {
                object = new Institucion();
                object.setIdCanton(new Canton());
                object.getIdCanton().setIdProvincia(new Provincia());
                object.getIdCanton().getIdProvincia().setIdPais(new Pais());
            }
            if (object != null) {
//                if (!object.getIdCanton().equals("")) {
                try {
                    divisionPoliticas = adm.query("Select o from Canton as o where o.idProvincia.idProvincia = '" + provinciaSeleccionado.getIdProvincia() + "' order by o.nombre ");
                    if (divisionPoliticas.size() > 0) {
                        Canton objSel = new Canton(0);
                        cantonesEncontradas.add(new SelectItem(objSel, "Seleccione..."));
                        for (Canton obj : divisionPoliticas) {
                            cantonesEncontradas.add(new SelectItem(obj, obj.getNombre()));
                        }
                    } else {
                        Canton obj = new Canton(0);
                        cantonesEncontradas.add(new SelectItem(obj, "No ha seleccionado la provincia"));
                    }
                } catch (Exception e) {
                    Canton obj = new Canton(0);
                    cantonesEncontradas.add(new SelectItem(obj, "No ha seleccionado la provincia"));
                }

//                }
            }
//            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CantonBean.class.getName()).log(Level.SEVERE, null, e);
        }
//        return null;
    }

    /**
     * Obtiene el el listado de provincias
     *
     * @return
     */
    public void buscarProvincia() {
        try {
            List<Provincia> divisionPoliticas = new ArrayList<Provincia>();
            provinciasEncontradas = new ArrayList<SelectItem>();

            if (object == null) {
                object = new Institucion();
                object.setIdCanton(new Canton());
                object.getIdCanton().setIdProvincia(new Provincia());
                object.getIdCanton().getIdProvincia().setIdPais(new Pais());
            }
            if (object != null) {
//                if (!object.getIdCanton().equals("")) {
                try {
                    divisionPoliticas = adm.query("Select o from Provincia as o where o.idPais.idPais= '" + paisSeleccionado.getIdPais() + "' order by o.nombre ");
                    if (divisionPoliticas.size() > 0) {
                        Provincia objSel = new Provincia(0);
                        provinciasEncontradas.add(new SelectItem(objSel, "Seleccione..."));
                        for (Provincia obj : divisionPoliticas) {
                            provinciasEncontradas.add(new SelectItem(obj, obj.getNombre()));
                        }
                    } else {
                        Provincia obj = new Provincia(0);
                        provinciasEncontradas.add(new SelectItem(obj, "No ha seleccionado el País"));
                    }
                } catch (Exception e) {
                    Provincia obj = new Provincia(0);
                    provinciasEncontradas.add(new SelectItem(obj, "No ha seleccionado el País"));
                }

//                }
            }
//            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CantonBean.class.getName()).log(Level.SEVERE, null, e);
        }
//        return null;
    }

    /**
     * Obtiene el el listado de paises
     *
     * @return
     */
    public List<SelectItem> getSelectedItemPais() {
        try {
            List<Pais> divisionPoliticas = new ArrayList<Pais>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            if (object == null) {
                //object = new Institucion();
                object.setIdCanton(new Canton());
                object.getIdCanton().setIdProvincia(new Provincia());
                object.getIdCanton().getIdProvincia().setIdPais(new Pais());
            }
            if (object != null) {

                divisionPoliticas = adm.query("Select o from Pais as o order by o.nombre ");
                if (divisionPoliticas.size() > 0) {
                    Pais objSel = new Pais(0);
                    items.add(new SelectItem(objSel, "Seleccione..."));
                    for (Pais obj : divisionPoliticas) {
                        items.add(new SelectItem(obj, obj.getNombre()));
                    }
                } else {
                    Pais obj = new Pais(0);
                    items.add(new SelectItem(obj, "NO EXISTEN PAISES"));
                }

            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CantonBean.class.getName()).log(Level.SEVERE, null, e);
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

    public boolean getVerSubir() {
        return verSubir;
    }

    public void setVerSubir(boolean verSubir) {
        this.verSubir = verSubir;
    }

    public boolean getEsLogo() {
        return esLogo;
    }

    public void setEsLogo(boolean esLogo) {
        verSubir = true;
        this.esLogo = esLogo;
    }
}
