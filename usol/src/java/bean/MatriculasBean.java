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
import jcinform.persistencia.CategoriasSociales;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.MateriasMatricula;
import jcinform.persistencia.Materias;
import jcinform.persistencia.Pais;
import jcinform.persistencia.Perfiles;
import jcinform.persistencia.Provincia;
import jcinform.persistencia.Titulos;
import jcinform.procesos.Administrador;
import jcinform.procesos.claves;
import miniaturas.ProcesadorImagenes;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;
//import org.primefaces.event.TransferEvent;
import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
//@RequestScoped
public class MatriculasBean{

    /**
     * Creates a new instance of MatriculasBean
     */
    Matriculas object;
    Administrador adm;
    protected List<Matriculas> model;
    protected List<MateriasMatricula> modelMaterias;
    public String textoBuscar;
    Permisos permisos;
    public String foto1;
    protected String clave2;
    claves cl = new claves();
    List<SelectItem> provinciasEncontradas;
    List<SelectItem> cantonesEncontradas;
    
    List<SelectItem> provinciasEncontradas1;
    List<SelectItem> cantonesEncontradas1;
    
    public Pais paisSeleccionado = new Pais();
    public Provincia provinciaSeleccionado = new Provincia();
    public Canton cantonSeleccionado = new Canton();
    
    public Pais paisSeleccionado1 = new Pais();
    public Provincia provinciaSeleccionado1 = new Provincia();
    public Canton cantonSeleccionado1 = new Canton();
    
    public CategoriasSociales categoriaSeleccionado = new CategoriasSociales();
     
    
    Auditar aud = new Auditar();
    ProcesadorImagenes p = new ProcesadorImagenes();
    
     private DualListModel<Materias> materias;  
     List<Materias> origen = new ArrayList<Materias>();  
        List<Materias> destino = new ArrayList<Materias>();  
     
     
    public MatriculasBean() {
        //super();
        if (adm == null) {
            adm = new Administrador();
        }
        if (permisos == null) {
            permisos = new Permisos();
        }
        if (!permisos.verificarPermisoReporte("Matriculas", "ingresar_matriculas", "ingresar", true, "PARAMETROS")) {
            try {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/noPuedeIngresar.jspx");
            } //selectedMatriculas = new Matriculas();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(MatriculasBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(MatriculasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        inicializar();
        //selectedMatriculas = new Matriculas();

    }
    public void buscarMateriasNoAsignadas(){
        origen =  adm.queryNativo(" Select o.* from Materias as o "
                + "where o.id_materias not in (Select m.id_Materias from Matriculas_Materias as m where m.id_Matriculas = '"+object.getIdMatriculas()+"') "
                + " order by o.nombre ",Materias.class);
        destino =  adm.query("Select m.idMaterias from MateriasMatricula as m where m.idMatriculas.idMatriculas = '"+object.getIdMatriculas()+"' "
                + " order by m.idMaterias.nombre ");
    }

    public String editarAction(Matriculas obj) {
        inicializar();
        object = obj;
        obj.getEstIdEstudiantes().setClave(cl.desencriptar(obj.getEstIdEstudiantes().getClave()));
        clave2 = obj.getEstIdEstudiantes().getClave();
        foto1 = object.getIdMatriculas() + ".jpg";
        try {
            generarImagenTmp(foto1, object.getEstIdEstudiantes().getFoto());
        } catch (Exception e) {
            System.out.println("AUN NO SE HA CARGADO LA IMAGEN...");
        }
        paisSeleccionado = object.getIdEstudiantes().getIdCanton().getIdProvincia().getIdPais();
        buscarProvincia();
        provinciaSeleccionado = object.getIdEstudiantes().getIdCanton().getIdProvincia();
        buscarCanton();
        cantonSeleccionado = object.getIdEstudiantes().getIdCanton();
//         
//        paisSeleccionado3 = object.getIdEstudiantes().getIdPais();
//        paisSeleccionado4 = object.getIdEstudiantes().getPaiIdPais();
        //generarImagen("logo.png", object.getFoto());
 buscarMateriasNoAsignadas();
        //foto1 = "logo.png";
        System.out.println("" + object.getIdMatriculas());
        return null;
    }
    
    protected void inicializar() {
        object = new Matriculas(0);
        object.setIdEstudiantes(new Estudiantes());
        object.getIdEstudiantes().setSexo("M");
        //object.getIdEstudiantes().setTipoIdentificacion("C");
        foto1 = null;
        clave2 = "";
        origen =  adm.query(" Select o from Materias as o order by o.nombre ");
        destino = new ArrayList<Materias>();
        //materias = new DualListModel<Materias>(origen, destino); 
        cargarDataModel();
    }

     public String anadir(Materias obj) {  
        destino.add(obj);  
        origen.remove(obj);  
        return null;
    }
     public String quitar(Materias obj) {  
        origen.add(obj);  
        destino.remove(obj);  
        return null;
    }
    
    /**
     * Graba el registro asociado al objeto que
     */
    public String guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (object.getIdEstudiantes().getNombre().isEmpty()) {
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
         
        object.getIdEstudiantes().setClave(cl.encriptar(object.getIdEstudiantes().getClave()));
        object.getIdEstudiantes().setIdCanton(cantonSeleccionado);
         
        if (object.getIdMatriculas().equals(new Integer(0))) {
            if (!permisos.verificarPermisoReporte("Matriculas", "agregar_matriculas", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
            }
            try {
                if (adm.existe("Matriculas", "nombre", object.getIdEstudiantes().getNombre()).size() <= 0) {
                    //object.setIdMatriculas(adm.getNuevaClave("Matriculas", "idMatriculas"));
                    adm.guardar(object);
                    
                   adm.ejecutaSql("Delete from MateriasMatricula where idMatriculas.idMatriculas = '"+object.getIdMatriculas()+"' ");
                    for (Iterator<Materias> it = destino.iterator(); it.hasNext();) {
                        Materias matGuardar = it.next();
                        MateriasMatricula empMat = new MateriasMatricula(adm.getNuevaClave("MateriasMatricula", "idMateriasMatricula"));
                        empMat.setIdMaterias(matGuardar);
                        empMat.setIdMatriculas(object); 
                        adm.guardar(empMat);
                        
                    }
                    
                    
                    aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", object.getIdMatriculas() + "");
                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre ya existe...!", "Nombre ya existe...!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
            if (!permisos.verificarPermisoReporte("Matriculas", "actualizar_matriculas", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
            }
            try {
                adm.actualizar(object);
                 adm.ejecutaSql("Delete from MateriasMatricula where idMatriculas.idMatriculas = '"+object.getIdMatriculas()+"' ");
                    for (Iterator<Materias> it = destino.iterator(); it.hasNext();) {
                        Materias matGuardar = it.next();
                        MateriasMatricula empMat = new MateriasMatricula(adm.getNuevaClave("MateriasMatricula", "idMateriasMatricula"));
                        empMat.setIdMaterias(matGuardar);
                        empMat.setIdMatriculas(object); 
                        adm.guardar(empMat);
                        
                    }
                aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdMatriculas() + "");
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(MatriculasBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
            
        }
        
        return null;
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(Matriculas obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("Matriculas", "eliminar_matriculas", "eliminar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
            }
            adm.eliminarObjeto(Matriculas.class, obj.getIdMatriculas());
            aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "eliminar", "", obj.getIdMatriculas() + "");
            inicializar();
            cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(MatriculasBean.class.getName()).log(Level.SEVERE, null, e);
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
            List<Matriculas> datos = adm.query("Select o from Matriculas as o");
            List<SelectItem> items = new ArrayList<SelectItem>();
            for (Matriculas obj : datos) {
                items.add(new SelectItem(obj, obj.getIdEstudiantes().getNombre()));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(MatriculasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }

       /**
     *
     * Obtiene el registro seleccionado
     */
    public List<SelectItem> getSelectedCategoriasSociales() {
        try {
            List<CategoriasSociales> datos = adm.query("Select o from CategoriasSociales as o order by o.nombre ");
            List<SelectItem> items = new ArrayList<SelectItem>();
            if(datos.size()>0){
                 
                for (CategoriasSociales obj : datos) {
                    items.add(new SelectItem(obj, obj.getNombre()));
                }
            }else {
                CategoriasSociales sel = new CategoriasSociales(0);
                items.add(new SelectItem(sel,"NO SE HAN CREADO CATEGORIAS..."));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(MatriculasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }

    
 
    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
            model = (adm.listar("Matriculas"));
            setModel(model);
            
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(MatriculasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }
    
    public void limpiar() {
        //object = new Matriculas(0);
        inicializar();
    }

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
        try {
            //setModel(adm.listar("Matriculas"));
            model = (adm.query("Select o from Matriculas as o where o.nombre like '%" + textoBuscar + "%' order by o.nombre "));
            setModel(model);
            
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(MatriculasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }
    
  
    
    public void generarImagenTmp(String nombre, byte[] datos) {
        
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        
        String fileFoto = servletContext.getRealPath("") + File.separator + "fotosMatriculas" + File.separator + nombre;
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
            object.getIdEstudiantes().setFoto(event.getFile().getContents());
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
            object.getIdEstudiantes().setFoto(imageInByte);
            buffer = null;
            imageInByte = null;
            fin = null;
            archivoNuevo = null;
            
        } catch (Exception ex) {
            Logger.getLogger(MatriculasBean.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(MatriculasBean.class.getName()).log(Level.SEVERE, null, e);
            
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
            java.util.logging.Logger.getLogger(MatriculasBean.class.getName()).log(Level.SEVERE, null, e);
            
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
                object = new Matriculas(0);
                object.getIdEstudiantes().setIdCanton(new Canton());
                object.getIdEstudiantes().getIdCanton().setIdProvincia(new Provincia());
                object.getIdEstudiantes().getIdCanton().getIdProvincia().setIdPais(new Pais());
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
                object = new Matriculas(0);
                object.getIdEstudiantes().setIdCanton(new Canton());
                object.getIdEstudiantes().getIdCanton().setIdProvincia(new Provincia());
                object.getIdEstudiantes().getIdCanton().getIdProvincia().setIdPais(new Pais());
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

      public void buscarProvincia1() {
        try {
            List<Provincia> divisionPoliticas = new ArrayList<Provincia>();
            provinciasEncontradas1 = new ArrayList<SelectItem>();
            
            if (object == null) {
                object = new Matriculas(0);
                object.getIdEstudiantes().setIdCanton(new Canton());
                object.getIdEstudiantes().getIdCanton().setIdProvincia(new Provincia());
                object.getIdEstudiantes().getIdCanton().getIdProvincia().setIdPais(new Pais());
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
                object.getIdEstudiantes().setIdCanton(new Canton());
                object.getIdEstudiantes().getIdCanton().setIdProvincia(new Provincia());
                object.getIdEstudiantes().getIdCanton().getIdProvincia().setIdPais(new Pais());
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
    public List<Matriculas> getModel() {
        if (model == null) {
            cargarDataModel();
        }
        return model;
    }
    
    public void setModel(List<Matriculas> model) {
        this.model = model;
    }

// public void onTransfer(TransferEvent event) {  
//        StringBuilder builder = new StringBuilder();  
////        for(Object item : event.getItems()) {  
////            builder.append(((Player) item).getName()).append("<br />");  
////        }  
//          
//        FacesMessage msg = new FacesMessage();  
//        msg.setSeverity(FacesMessage.SEVERITY_INFO);  
//        msg.setSummary("Items Transferred");  
//        msg.setDetail(builder.toString());  
//          
//        FacesContext.getCurrentInstance().addMessage(null, msg);  
//    }
    
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
    
    public Matriculas getObject() {
        if (object == null) {
            object = new Matriculas(0);
        }
        return object;
    }
    
    public void setObject(Matriculas object) {
        this.object = object;
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

    public DualListModel<Materias> getMaterias() {
        return materias;
    }

    public void setMaterias(DualListModel<Materias> materias) {
        this.materias = materias;
    }

    public List<Materias> getOrigen() {
        return origen;
    }

    public void setOrigen(List<Materias> origen) {
        this.origen = origen;
    }

    public List<Materias> getDestino() {
        return destino;
    }

    public void setDestino(List<Materias> destino) {
        this.destino = destino;
    }

    public Pais getPaisSeleccionado1() {
        return paisSeleccionado1;
    }

    public void setPaisSeleccionado1(Pais paisSeleccionado1) {
        this.paisSeleccionado1 = paisSeleccionado1;
    }

    public Provincia getProvinciaSeleccionado1() {
        return provinciaSeleccionado1;
    }

    public void setProvinciaSeleccionado1(Provincia provinciaSeleccionado1) {
        this.provinciaSeleccionado1 = provinciaSeleccionado1;
    }

    public Canton getCantonSeleccionado1() {
        return cantonSeleccionado1;
    }

    public void setCantonSeleccionado1(Canton cantonSeleccionado1) {
        this.cantonSeleccionado1 = cantonSeleccionado1;
    }

    public CategoriasSociales getCategoriaSeleccionado() {
        return categoriaSeleccionado;
    }

    public void setCategoriaSeleccionado(CategoriasSociales categoriaSeleccionado) {
        this.categoriaSeleccionado = categoriaSeleccionado;
    }
    
    
}
