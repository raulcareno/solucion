/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import jcinform.persistencia.Canton;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Pais;
import jcinform.persistencia.Perfiles;
import jcinform.persistencia.Provincia;
import jcinform.persistencia.Titulos;
import jcinform.procesos.Administrador;
import jcinform.procesos.claves;
import miniaturas.ProcesadorImagenes;
import org.primefaces.event.FileUploadEvent;

import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
//@RequestScoped
public class EmpleadosBean {

    /**
     * Creates a new instance of EmpleadosBean
     */
    Empleados object;
    Administrador adm;
    protected List<Empleados> model;
    public String textoBuscar;
    Permisos permisos;
    protected Titulos titulosSeleccionado = new Titulos();
    protected Perfiles perfilesSeleccioando = new Perfiles();
    public String foto1;
    protected String clave2;
    claves cl = new claves();
    List<SelectItem> provinciasEncontradas;
    List<SelectItem> cantonesEncontradas;
    public Pais paisSeleccionado = new Pais();
    public Provincia provinciaSeleccionado = new Provincia();
    public Canton cantonSeleccionado = new Canton();
    public Pais paisSeleccionado3 = new Pais();
    public Pais paisSeleccionado4 = new Pais();
    Auditar aud = new Auditar();
    ProcesadorImagenes p = new ProcesadorImagenes();
    public EmpleadosBean() {
        //super();
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
        obj.setClave(cl.desencriptar(obj.getClave()));
        clave2 = obj.getClave();
        foto1 = object.getIdEmpleados() + ".jpg";
        try {
            generarImagenTmp(foto1, object.getFoto());
        } catch (Exception e) {
            System.out.println("AUN NO SE HA CARGADO LA IMAGEN...");
        }
        paisSeleccionado = object.getIdCanton().getIdProvincia().getIdPais();
        buscarProvincia();
        provinciaSeleccionado = object.getIdCanton().getIdProvincia();
        buscarCanton();
        cantonSeleccionado = object.getIdCanton();
        perfilesSeleccioando = object.getIdPerfiles();
        titulosSeleccionado = object.getIdTitulos();
        
        paisSeleccionado3 = object.getIdPais();
        paisSeleccionado4 = object.getPaiIdPais();
        //generarImagen("logo.png", object.getFoto());

        //foto1 = "logo.png";
        System.out.println("" + object.getIdEmpleados());
        return null;
    }
    
    protected void inicializar() {
        object = new Empleados(0);
        object.setSexo("M");
        object.setTipoIdentificacion("C");
        foto1 = null;
        clave2 = "";
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
        if (paisSeleccionado.getIdPais().equals(new Integer(0))) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione el Lugar de Nacimiento, Pais", ""));
            return null;
        }
        if (provinciaSeleccionado.getIdProvincia().equals(new Integer(0))) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione el Lugar de Nacimiento, Provincia", ""));
            return null;
        }
        if (cantonSeleccionado.getIdCanton().equals(new Integer(0))) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione el Lugar de Nacimiento, Canton", ""));
            return null;
        }
        if (perfilesSeleccioando.getIdPerfiles().equals(new Integer(0))) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione el Perfil", ""));
            return null;
        }
        if (titulosSeleccionado.getIdTitulos().equals(new Integer(0))) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione el Titulo", ""));
            return null;
        }
        
        object.setClave(cl.encriptar(object.getClave()));
        object.setIdCanton(cantonSeleccionado);
        object.setIdPerfiles(perfilesSeleccioando);
        object.setIdTitulos(titulosSeleccionado);
        if(paisSeleccionado3.getIdPais().equals(new Integer(0))){
            object.setPaiIdPais(null);
        }else{
            object.setIdPais(paisSeleccionado3);
        }
        if(paisSeleccionado4.getIdPais().equals(new Integer(0))){
            object.setPaiIdPais(null);
        }else{
            object.setPaiIdPais(paisSeleccionado4);
        }
        
        if (object.getIdEmpleados().equals(new Integer(0))) {
            if (!permisos.verificarPermisoReporte("Empleados", "agregar_empleados", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
            }
            try {
                if (adm.existe("Empleados", "nombre", object.getNombre()).size() <= 0) {
                    //object.setIdEmpleados(adm.getNuevaClave("Empleados", "idEmpleados"));
                    adm.guardar(object);
                    aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", object.getIdEmpleados() + "");
                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre ya existe...!", "Nombre ya existe...!"));
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
                aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdEmpleados() + "");
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
            aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "eliminar", "", obj.getIdEmpleados() + "");
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
        //object = new Empleados(0);
        inicializar();
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
    
  
    
    public void generarImagenTmp(String nombre, byte[] datos) {
        
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        
        String fileFoto = servletContext.getRealPath("") + File.separator + "fotosEmpleados" + File.separator + nombre;
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
        try {
            //FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
            //FacesContext.getCurrentInstance().addMessage(null, msg);
            //        byte[] datos = event.getFile().getContents();
            object.setFoto(event.getFile().getContents());
            String nombreTemporal = event.getFile().getFileName().substring(0, event.getFile().getFileName().lastIndexOf("."));
            String formato = event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf(".") + 1);
            java.io.File f = java.io.File.createTempFile(nombreTemporal, "."+formato);
            
            
//            byte[] archivo = tuByte[];
            FileOutputStream archivoNuevo = new FileOutputStream(f);
            archivoNuevo.write(event.getFile().getContents());
            
            
            //File.createTempFile("miArchivo", ".gif");
//            ImageIO.write(imageBuffer, "jpg", f);
            FileInputStream fin = null;
            fin = new FileInputStream(f);
            byte[] buffer = new byte[(int) f.length()];
            fin.read(buffer);
            fin.close();
            
      
            BufferedImage bf = p.escalarATamanyo(f, 230, 170, formato);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bf, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            f = null;
            bf = null;
            p = null;
            object.setFoto(imageInByte);
            buffer = null;
            imageInByte = null;
            fin = null;
            archivoNuevo = null;
            
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                    items.add(new SelectItem(obj, "NO EXISTEN TITULOS "));
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
                    items.add(new SelectItem(obj, "NO EXISTEN PERFILES"));
                }
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(EmpleadosBean.class.getName()).log(Level.SEVERE, null, e);
            
        }
        return null;
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
                object = new Empleados(0);
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
                object = new Empleados(0);
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
            object = new Empleados(0);
        }
        return object;
    }
    
    public void setObject(Empleados object) {
        this.object = object;
    }
    
    public Titulos getTitulosSeleccionado() {
        return titulosSeleccionado;
    }
    
    public void setTitulosSeleccionado(Titulos titulosSeleccionado) {
        this.titulosSeleccionado = titulosSeleccionado;
    }
    
    public Perfiles getPerfilesSeleccioando() {
        return perfilesSeleccioando;
    }
    
    public void setPerfilesSeleccioando(Perfiles perfilesSeleccioando) {
        this.perfilesSeleccioando = perfilesSeleccioando;
    }
    
    public String getFoto1() {
        return foto1;
    }
    
    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }
    
    public String getClave2() {
        return clave2;
    }
    
    public void setClave2(String clave2) {
        this.clave2 = clave2;
    }
    
    public List<SelectItem> getProvinciasEncontradas() {
        return provinciasEncontradas;
    }
    
    public void setProvinciasEncontradas(List<SelectItem> provinciasEncontradas) {
        this.provinciasEncontradas = provinciasEncontradas;
    }
    
    public List<SelectItem> getCantonesEncontradas() {
        return cantonesEncontradas;
    }
    
    public void setCantonesEncontradas(List<SelectItem> cantonesEncontradas) {
        this.cantonesEncontradas = cantonesEncontradas;
    }
    
    public Pais getPaisSeleccionado() {
        return paisSeleccionado;
    }
    
    public void setPaisSeleccionado(Pais paisSeleccionado) {
        this.paisSeleccionado = paisSeleccionado;
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

    public Pais getPaisSeleccionado3() {
        return paisSeleccionado3;
    }

    public void setPaisSeleccionado3(Pais paisSeleccionado3) {
        this.paisSeleccionado3 = paisSeleccionado3;
    }

    public Pais getPaisSeleccionado4() {
        return paisSeleccionado4;
    }

    public void setPaisSeleccionado4(Pais paisSeleccionado4) {
        this.paisSeleccionado4 = paisSeleccionado4;
    }
    
}
