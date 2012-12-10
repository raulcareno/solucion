/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import jcinform.persistencia.Archivos;
import jcinform.persistencia.Canton;
import jcinform.persistencia.Carreras;
import jcinform.persistencia.CarrerasMaterias;
import jcinform.persistencia.CategoriasSociales;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Materias;
import jcinform.persistencia.MateriasMatricula;
import jcinform.persistencia.Aspirantes;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Pais;
import jcinform.persistencia.Parametros;
import jcinform.persistencia.Parientes;
import jcinform.persistencia.Periodos;
import jcinform.persistencia.Provincia;
import jcinform.persistencia.RangosGpa;
import jcinform.persistencia.RangosIngresos;
import jcinform.procesos.Administrador;
import jcinform.procesos.claves;
import miniaturas.ProcesadorImagenes;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
//@RequestScoped
public class AspirantesBean {

    /**
     * Creates a new instance of AspirantesBean
     */
    Aspirantes object;
    Administrador adm;
    protected List<Aspirantes> model;
    protected List<MateriasMatricula> materiasMatricula;
    protected List<Parientes> modelParientes;
    public String textoBuscar;
    Permisos permisos;
    public String foto1;
    public byte[] imagen;
    public byte[] cedula;
    public String cedulaNombre;
    public String cedulaFormato;
    public byte[] libreta;
    public String libretaNombre;
    public String libretaFormato;
    public byte[] titulo;
    public String tituloNombre;
    public String tituloFormato;
    protected String clave2;
    claves cl = new claves();
    List<SelectItem> provinciasEncontradas;
    List<SelectItem> cantonesEncontradas;
    List<SelectItem> provinciasEncontradas1;
    List<SelectItem> cantonesEncontradas1;
    protected Parientes pariente1;
    protected Parientes pariente2;
    protected Parientes pariente3;
    public Pais paisSeleccionado = new Pais();
    public Carreras carreraSeleccionado = new Carreras();
    public Provincia provinciaSeleccionado = new Provincia();
    public Canton cantonSeleccionado = new Canton();
    public Pais paisSeleccionado1 = new Pais();
    public Provincia provinciaSeleccionado1 = new Provincia();
    public Canton cantonSeleccionado1 = new Canton();
    public CategoriasSociales categoriaSeleccionado = new CategoriasSociales();
    Auditar aud = new Auditar();
    ProcesadorImagenes p = new ProcesadorImagenes();
    private DualListModel<Materias> materias;
    List<CarrerasMaterias> origen = new ArrayList<CarrerasMaterias>();
    List<CarrerasMaterias> destino = new ArrayList<CarrerasMaterias>();
    List<Estudiantes> estudiantesListado = new ArrayList<Estudiantes>();
    Periodos per;
    Archivos arLibreta;
    Archivos arTitulo;
    Archivos arCedula;
    List<RangosGpa> rangos;
    Boolean hoja;
    Boolean charla;
    Boolean charlavin;
    Boolean paginaweb;
    Boolean pp; //prensa
    Boolean pu; //publicidad Universidad
    Boolean rp;
    Boolean pr;
    Boolean goo;
    Boolean puev;
    Boolean red;
    Boolean guardado = true;

    public AspirantesBean() {
        //super();
        if (adm == null) {
            adm = new Administrador();
        }
        if (permisos == null) {
            permisos = new Permisos();
        }
        guardado = true;
        Empleados user = (Empleados) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if(user !=null){
        if (!permisos.verificarPermisoReporte("Aspirantes", "ingresar_listaAspirantes.jspx", "ingresar", true, "ADMINISTRACION")) {
            try {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/noPuedeIngresar.jspx");
            } //selectedAspirantes = new Aspirantes();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(AspirantesBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(AspirantesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
        p = new ProcesadorImagenes();
        inicializar();
        per = (Periodos) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("periodo");
        //selectedAspirantes = new Aspirantes();

    }

    public String editarAction(Aspirantes obj) {
        inicializar();
        object = new Aspirantes("");
        object = obj;
        carreraSeleccionado = object.getIdCarreras();
        hoja = false;
        charla = false;
        charlavin = false;
        paginaweb = false;
        pp = false;
        pu = false;
        rp = false;
        pr = false;
        goo = false;
        puev = false;
        red = false;
        if (object.getMedio().contains("hoja")) {
            hoja = true;
        }
        if (object.getMedio().contains("charla")) {
            charla = true;
        }
        if (object.getMedio().contains("charlavin")) {
            charlavin = true;
        }
        if (object.getMedio().contains("paginaweb")) {
            paginaweb = true;
        }
        if (object.getMedio().contains("pp")) {
            pp = true;
        }
        if (object.getMedio().contains("pu")) {
            pu = true;
        }
        if (object.getMedio().contains("rp")) {
            rp = true;
        }
        if (object.getMedio().contains("pr")) {
            pr = true;
        }
        if (object.getMedio().contains("goo")) {
            goo = true;
        }

        if (object.getMedio().contains("puev")) {
            puev = true;
        }
        if (object.getMedio().contains("red")) {
            red = true;
        }



        return null;
    }

    protected void inicializar() {
        object = new Aspirantes("");
        
    }

    public StreamedContent getImageAsStream() {
        try {
            //         try {
            //             ByteArrayInputStream stream = new ByteArrayInputStream(imagen);
            //            StreamedContent imageAsStream = new DefaultStreamedContent(stream);
            //            return imageAsStream;
            //         } catch (Exception e) {
            //             System.out.println(""+e);         }
            BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bufferedImg.createGraphics();
            g2.drawString("This is a text", 0, 10);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImg, "png", os);
            return new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");

        } catch (IOException ex) {
            Logger.getLogger(AspirantesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Graba el registro asociado al objeto que
     */
    public String guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (object.getNombres().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese los NOMBRES:", ""));
            return null;
        }
        /**
         * GUARDAR PARIENTES ANTES DE GUARDAR A ESTUDIANTES
         */
        object.setIdCarreras(carreraSeleccionado);
        object.setFecha(adm.Date());
        if (!object.getIdAspirantes().equals("")) {
//            if (!permisos.verificarPermisoReporte("Aspirantes", "agregar_listaAspirantes.jspx", "agregar", true, "ADMINISTRACION")) {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
//                return null;
//            }
            try {
//                  Boolean hoja;
//                    Boolean charla;
//                    Boolean charlavin;
//                    Boolean paginaweb;
//                    Boolean pp; //prensa
//                    Boolean pu; //publicidad Universidad
//                    Boolean rp;
//                    Boolean pr;
//                    Boolean goo;
//                    Boolean puev;
//                    Boolean red;
                object.setIdCarreras(carreraSeleccionado);
                if (adm.existe("Aspirantes", "idAspirantes", object.getIdAspirantes()).size() <= 0) {
                    object.setLlamadas(0); 
                    object.setMedio((hoja.booleanValue() ? "hoja;" : "")
                            + "" + (charla.booleanValue() ? "charla;" : "")
                            + "" + (charlavin.booleanValue() ? "charlavin;" : "")
                            + "" + (paginaweb.booleanValue() ? "paginaweb;" : "")
                            + "" + (pp.booleanValue() ? "pp;" : "")
                            + "" + (pu.booleanValue() ? "pu;" : "")
                            + "" + (rp.booleanValue() ? "rp;" : "")
                            + "" + (pr.booleanValue() ? "pr;" : "")
                            + "" + (goo.booleanValue() ? "goo;" : "")
                            + "" + (puev.booleanValue() ? "puev;" : "")
                            + "" + (red.booleanValue() ? "red;" : ""));
                    adm.guardar(object);
                    guardado = false;
                    //  aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", object.getIdAspirantes() + "");
//                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
                } else {
                    object.setLlamadas(0); 
                    object.setMedio((hoja.booleanValue() ? "hoja;" : "")
                            + "" + (charla.booleanValue() ? "charla;" : "")
                            + "" + (charlavin.booleanValue() ? "charlavin;" : "")
                            + "" + (paginaweb.booleanValue() ? "paginaweb;" : "")
                            + "" + (pp.booleanValue() ? "pp;" : "")
                            + "" + (pu.booleanValue() ? "pu;" : "")
                            + "" + (rp.booleanValue() ? "rp;" : "")
                            + "" + (pr.booleanValue() ? "pr;" : "")
                            + "" + (goo.booleanValue() ? "goo;" : "")
                            + "" + (puev.booleanValue() ? "puev;" : "")
                            + "" + (red.booleanValue() ? "red;" : ""));
                    adm.actualizar(object);
                    guardado = false;
                    //aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdAspirantes() + "");
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
//            if (!permisos.verificarPermisoReporte("Aspirantes", "actualizar_listaAspirantes.jspx", "agregar", true, "ADMINISTRACION")) {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
//                return null;
//            }
            try {
                adm.actualizar(object);
                aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdAspirantes() + "");
                //FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
//                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(AspirantesBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

        }

        return null;
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(Aspirantes obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("Aspirantes", "eliminar_listaAspirantes.jspx", "eliminar", true, "ADMINISTRACION")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
            }
            adm.eliminarObjeto(Aspirantes.class, obj.getIdAspirantes());
            aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "eliminar", "", obj.getIdAspirantes() + "");
            inicializar();
            //cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(AspirantesBean.class.getName()).log(Level.SEVERE, null, e);
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
            List<Aspirantes> datos = adm.query("Select o from Aspirantes as o");
            List<SelectItem> items = new ArrayList<SelectItem>();

            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AspirantesBean.class.getName()).log(Level.SEVERE, null, e);
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
            if (datos.size() > 0) {

                for (CategoriasSociales obj : datos) {
                    items.add(new SelectItem(obj, obj.getNombre()));
                }
            } else {
                CategoriasSociales sel = new CategoriasSociales(0);
                items.add(new SelectItem(sel, "NO SE HAN CREADO CATEGORIAS..."));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AspirantesBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
            model = (adm.query("Select o from Aspirantes as o order by o.fecha"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AspirantesBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public void limpiar() {
        //object = new Aspirantes(0);
        object = new Aspirantes("0");

    }

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
        FacesContext context = FacesContext.getCurrentInstance();
   
        if(object.getLlamadas()!=null){
            object.setLlamadas(object.getLlamadas()+1);
        }else{
            object.setLlamadas(1);
        }
        object.setObservacion(object.getObservacion()+"["+(new Date()).toLocaleString().substring(0,10)+"]" );
        if (!object.getIdAspirantes().equals("")) {
            if (!permisos.verificarPermisoReporte("Aspirantes", "agregar_listaAspirantes.jspx", "agregar", true, "ADMINISTRACION")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
                return;
            }
            try {

                object.setIdCarreras(carreraSeleccionado);
                if (adm.existe("Aspirantes", "idAspirantes", object.getIdAspirantes()).size() <= 0) {
                } else {
                    object.setMedio((hoja.booleanValue() ? "hoja;" : "")
                            + "" + (charla.booleanValue() ? "charla;" : "")
                            + "" + (charlavin.booleanValue() ? "charlavin;" : "")
                            + "" + (paginaweb.booleanValue() ? "paginaweb;" : "")
                            + "" + (pp.booleanValue() ? "pp;" : "")
                            + "" + (pu.booleanValue() ? "pu;" : "")
                            + "" + (rp.booleanValue() ? "rp;" : "")
                            + "" + (pr.booleanValue() ? "pr;" : "")
                            + "" + (goo.booleanValue() ? "goo;" : "")
                            + "" + (puev.booleanValue() ? "puev;" : "")
                            + "" + (red.booleanValue() ? "red;" : ""));
                    adm.actualizar(object);
                    guardado = false;
                    aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdAspirantes() + "");
//                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
            if (!permisos.verificarPermisoReporte("Aspirantes", "actualizar_listaAspirantes.jspx", "agregar", true, "ADMINISTRACION")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
                return;
            }
            try {
                adm.actualizar(object);
                aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdAspirantes() + "");
  //              FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
//                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(AspirantesBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

        }
inicializar();
        //return null;
    }

    public List<Estudiantes> buscarApellido(String apellido) {
        try {
            apellido = apellido.trim();
            estudiantesListado = adm.query("Select o from Estudiantes as o "
                    + " where o.apellidoPaterno like '%" + apellido + "%' "
                    + " order by o.apellidoPaterno ", 0, 10);
            return estudiantesListado;


        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AspirantesBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
        return null;
    }

    public List<Estudiantes> buscarCedula(String cedula) {
        try {
            cedula = cedula.trim();
            return estudiantesListado;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AspirantesBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
        return null;
    }

    public void buscarCedulaPadre() {
        List<Parientes> parientesEncontrados = adm.query("Select o from Parientes as o "
                + "where o.identificacion = '" + pariente2.getIdentificacion() + "' "
                + " and o.tipoRepresentante = 'P'  ");
        for (Iterator<Parientes> it = parientesEncontrados.iterator(); it.hasNext();) {
            Parientes parientes = it.next();
            pariente2 = parientes;
        }

    }

    public void buscarCedulaMadre() {
        List<Parientes> parientesEncontrados = adm.query("Select o from Parientes as o "
                + "where o.identificacion = '" + pariente3.getIdentificacion() + "' "
                + " and o.tipoRepresentante = 'M'  ");
        for (Iterator<Parientes> it = parientesEncontrados.iterator(); it.hasNext();) {
            Parientes parientes = it.next();
            pariente3 = parientes;
        }

    }

    public void buscarCedula() {

        String cedula2 = object.getIdAspirantes();
        object = (Aspirantes) adm.buscarClave(cedula2, Aspirantes.class);

    }

    protected Integer nuevaMatricula() {
        Administrador adm = new Administrador();

        List NoActualMatricula = adm.query("Select o from Parametros as o "
                + "where o.variable = 'MATRICULA' ");
        Parametros parametros = new Parametros();
        if (NoActualMatricula.size() <= 0) {
            System.out.println("FALTA PARAMETRO matricula EN PARAMETROS ");
//            alert("Falta copiar los parámetros a este Año Lectivo");
        }
        parametros = (Parametros) NoActualMatricula.get(0);
        int noMatri = 0;
        Double decs = parametros.getVNumerico().doubleValue();
        Long val = java.lang.Math.round(decs);
        noMatri = Integer.valueOf(val.toString());
        noMatri += 1;
        List numeroYa = adm.query("Select o from Aspirantes as o  where  o.numero = '" + noMatri + "'");
        if (numeroYa.size() > 0) {
            Integer nClave = adm.geUltimaMatricula("Select max(o.numero) from Aspirantes as o "
                    + " ");
            parametros.setVNumerico(new BigDecimal(nClave + 1));
            adm.actualizar(parametros);
            return nClave + 1;
        } else {
            parametros.setVNumerico(new BigDecimal(noMatri));
            adm.actualizar(parametros);
            return noMatri;
        }
    }

    protected void buscarMateriasMatricula(Aspirantes mat) {
        if (!object.getIdAspirantes().equals(new Integer(0))) {
            destino = adm.queryNativo(" Select c.* from Carreras_Materias as  c "
                    + " WHERE c.id_Materias in "
                    + "(Select o.id_Materias from Materias_Matricula as o WHERE o.id_Aspirantes = '" + object.getIdAspirantes() + "' ) "
                    + " and c.id_Carreras = '" + object.getIdCarreras().getIdCarreras() + "' "
                    + " ", CarrerasMaterias.class);

            origen = adm.query("Select m from CarrerasMaterias as m  where m.idCarreras.idCarreras = '" + carreraSeleccionado.getIdCarreras() + "' order by m.idNiveles.secuencia");
            for (Iterator<CarrerasMaterias> it = destino.iterator(); it.hasNext();) {
                CarrerasMaterias destItem = it.next();
                origen.remove(destItem);

            }
            sumarCreditos();
        } else {
            destino = new ArrayList<CarrerasMaterias>();
            origen = adm.query("Select m from CarrerasMaterias as m  "
                    + " where m.idCarreras.idCarreras = '" + carreraSeleccionado.getIdCarreras() + "' order by m.idNiveles.secuencia");
            for (Iterator<CarrerasMaterias> it = destino.iterator(); it.hasNext();) {
                CarrerasMaterias destItem = it.next();
                origen.remove(destItem);

            }
            sumarCreditos();

        }
    }
    public int noCreditos = 0;

    public void sumarCreditos() {
        noCreditos = 0;
        for (Iterator<CarrerasMaterias> it = destino.iterator(); it.hasNext();) {
            CarrerasMaterias carrerasMaterias = it.next();
            noCreditos += carrerasMaterias.getNumeroCreditos();
        }

    }

    protected void buscarMatricula(Estudiantes estudiante) {
        List<Aspirantes> matriculasListado = adm.query("Select o from Aspirantes as o "
                + " where o.idEstudiantes.idEstudiantes = '" + estudiante.getIdEstudiantes() + "' "
                + " and o.idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "'");
//        if (matriculasListado.size() > 0) {
//            object = matriculasListado.get(0);
//            carreraSeleccionado = object.getIdCarreras();
//            categoriaSeleccionado = object.getIdCategoriasSociales();
//            buscarMateriasMatricula(object);
//
//        }else{
//            carreraSeleccionado = new Carreras(0);
//            object = new Aspirantes(0); 
//            buscarMateriasMatricula(object);
//        
//        }
    }

    public void generarImagenTmp(String nombre, byte[] datos) {

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String fileFoto = servletContext.getRealPath("") + File.separator + "fotosAspirantes" + File.separator + nombre;
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

    /**
     * Obtiene el listado de carreras para llenar combos
     */
    public List<SelectItem> getSelectedItemCarreras() {
        try {
            List<Carreras> divisionPoliticas = new ArrayList<Carreras>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            if (object != null) {
                divisionPoliticas = adm.query("Select o from Carreras as o "
                        + " WHERE o.estado = 'A' order by o.nombre ");
                if (divisionPoliticas.size() > 0) {
                    Carreras objSel = new Carreras(0);
                    items.add(new SelectItem(objSel, "Seleccione..."));
                    for (Carreras obj : divisionPoliticas) {
                        items.add(new SelectItem(obj, obj.getNombre()));
                    }
                } else {
                    Carreras obj = new Carreras(0);
                    items.add(new SelectItem(obj, "NO EXISTEN CARRERAS"));
                }
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AspirantesBean.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }

    public List<SelectItem> getSelectedItemIngresos() {
        try {
            List<RangosIngresos> divisionPoliticas = new ArrayList<RangosIngresos>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            if (object != null) {
                divisionPoliticas = adm.query("Select o from RangosIngresos as o "
                        + "  order by o.rangoInicial ");
                if (divisionPoliticas.size() > 0) {
                    //RangosIngresos objSel = new RangosIngresos(0);
                    items.add(new SelectItem("-", "Seleccione..."));
                    for (RangosIngresos obj : divisionPoliticas) {
                        items.add(new SelectItem(obj.getRangoInicial() + "-" + obj.getRangoFinal(), obj.getRangoInicial() + "-" + obj.getRangoFinal()));
                    }
                } else {
                    //RangosIngresos obj = new RangosIngresos(0);
                    items.add(new SelectItem("-", "NO EXISTEN CARRERAS"));
                }
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AspirantesBean.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
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
    public List<Aspirantes> getModel() {
        if (model == null) {
            cargarDataModel();
        }
        return model;
    }

    public void setModel(List<Aspirantes> model) {
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

    public Aspirantes getObject() {
        if (object == null) {
            object = new Aspirantes("0");
        }
        return object;
    }

    public void setObject(Aspirantes object) {
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

    public List<CarrerasMaterias> getOrigen() {
        return origen;
    }

    public void setOrigen(List<CarrerasMaterias> origen) {
        this.origen = origen;
    }

    public List<CarrerasMaterias> getDestino() {
        return destino;
    }

    public void setDestino(List<CarrerasMaterias> destino) {
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

    public List<Parientes> getModelParientes() {
        return modelParientes;
    }

    public void setModelParientes(List<Parientes> modelParientes) {
        this.modelParientes = modelParientes;
    }

    public Parientes getPariente1() {
        return pariente1;
    }

    public void setPariente1(Parientes pariente1) {
        this.pariente1 = pariente1;
    }

    public Parientes getPariente2() {
        return pariente2;
    }

    public void setPariente2(Parientes pariente2) {
        this.pariente2 = pariente2;
    }

    public Parientes getPariente3() {
        return pariente3;
    }

    public void setPariente3(Parientes pariente3) {
        this.pariente3 = pariente3;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Carreras getCarreraSeleccionado() {
        return carreraSeleccionado;
    }

    public void setCarreraSeleccionado(Carreras carreraSeleccionado) {
        this.carreraSeleccionado = carreraSeleccionado;
    }

    public byte[] getCedula() {
        return cedula;
    }

    public void setCedula(byte[] cedula) {
        this.cedula = cedula;
    }

    public byte[] getLibreta() {
        return libreta;
    }

    public void setLibreta(byte[] libreta) {
        this.libreta = libreta;
    }

    public byte[] getTitulo() {
        return titulo;
    }

    public void setTitulo(byte[] titulo) {
        this.titulo = titulo;
    }

    public String getCedulaNombre() {
        return cedulaNombre;
    }

    public void setCedulaNombre(String cedulaNombre) {
        this.cedulaNombre = cedulaNombre;
    }

    public String getCedulaFormato() {
        return cedulaFormato;
    }

    public void setCedulaFormato(String cedulaFormato) {
        this.cedulaFormato = cedulaFormato;
    }

    public String getLibretaNombre() {
        return libretaNombre;
    }

    public void setLibretaNombre(String libretaNombre) {
        this.libretaNombre = libretaNombre;
    }

    public String getLibretaFormato() {
        return libretaFormato;
    }

    public void setLibretaFormato(String libretaFormato) {
        this.libretaFormato = libretaFormato;
    }

    public String getTituloNombre() {
        return tituloNombre;
    }

    public void setTituloNombre(String tituloNombre) {
        this.tituloNombre = tituloNombre;
    }

    public String getTituloFormato() {
        return tituloFormato;
    }

    public void setTituloFormato(String tituloFormato) {
        this.tituloFormato = tituloFormato;
    }

    public Archivos getArLibreta() {
        return arLibreta;
    }

    public void setArLibreta(Archivos arLibreta) {
        this.arLibreta = arLibreta;
    }

    public Archivos getArTitulo() {
        return arTitulo;
    }

    public void setArTitulo(Archivos arTitulo) {
        this.arTitulo = arTitulo;
    }

    public Archivos getArCedula() {
        return arCedula;
    }

    public void setArCedula(Archivos arCedula) {
        this.arCedula = arCedula;
    }

    public List<MateriasMatricula> getMateriasMatricula() {
        return materiasMatricula;
    }

    public void setMateriasMatricula(List<MateriasMatricula> materiasMatricula) {
        this.materiasMatricula = materiasMatricula;
    }

    public int getNoCreditos() {
        return noCreditos;
    }

    public void setNoCreditos(int noCreditos) {
        this.noCreditos = noCreditos;
    }

    public Boolean getHoja() {
        return hoja;
    }

    public void setHoja(Boolean hoja) {
        this.hoja = hoja;
    }

    public Boolean getCharla() {
        return charla;
    }

    public void setCharla(Boolean charla) {
        this.charla = charla;
    }

    public Boolean getCharlavin() {
        return charlavin;
    }

    public void setCharlavin(Boolean charlavin) {
        this.charlavin = charlavin;
    }

    public Boolean getPp() {
        return pp;
    }

    public void setPp(Boolean pp) {
        this.pp = pp;
    }

    public Boolean getPu() {
        return pu;
    }

    public void setPu(Boolean pu) {
        this.pu = pu;
    }

    public Boolean getRp() {
        return rp;
    }

    public void setRp(Boolean rp) {
        this.rp = rp;
    }

    public Boolean getGoo() {
        return goo;
    }

    public void setGoo(Boolean goo) {
        this.goo = goo;
    }

    public Boolean getRed() {
        return red;
    }

    public void setRed(Boolean red) {
        this.red = red;
    }

    public Boolean getPaginaweb() {
        return paginaweb;
    }

    public void setPaginaweb(Boolean paginaweb) {
        this.paginaweb = paginaweb;
    }

    public Boolean getPr() {
        return pr;
    }

    public void setPr(Boolean pr) {
        this.pr = pr;
    }

    public Boolean getPuev() {
        return puev;
    }

    public void setPuev(Boolean puev) {
        this.puev = puev;
    }

    public Boolean getGuardado() {
        return guardado;
    }

    public void setGuardado(Boolean guardado) {
        this.guardado = guardado;
    }
}
