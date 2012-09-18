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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import jcinform.persistencia.Carreras;
import jcinform.persistencia.CategoriasSociales;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Materias;
import jcinform.persistencia.MateriasMatricula;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Pais;
import jcinform.persistencia.Parametros;
import jcinform.persistencia.Parientes;
import jcinform.persistencia.Perfiles;
import jcinform.persistencia.Periodos;
import jcinform.persistencia.Provincia;
import jcinform.persistencia.RangosIngresos;
import jcinform.persistencia.Titulos;
import jcinform.procesos.Administrador;
import jcinform.procesos.claves;
import miniaturas.ProcesadorImagenes;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import reportes.ExportarReportes;
import reportes.FichaMatricula;
import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
//@RequestScoped
public class MatriculasEstudianteBean {

    /**
     * Creates a new instance of MatriculasBean
     */
    Boolean habilitadoMatriculas = false;
    Matriculas object;
    Estudiantes estudiante;
    Administrador adm;
    protected List<Matriculas> model;
    protected List<MateriasMatricula> modelMaterias;
    protected List<Parientes> modelParientes;
    public String textoBuscar;
    Permisos permisos;
    public String foto1;
    public byte[] imagen;
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
    List<Materias> origen = new ArrayList<Materias>();
    List<Materias> destino = new ArrayList<Materias>();
    List<Estudiantes> estudiantesListado = new ArrayList<Estudiantes>();
    Periodos per;

    ;
    public MatriculasEstudianteBean() {
        //super();
        if (adm == null) {
            adm = new Administrador();
        }
        if (permisos == null) {
            permisos = new Permisos();
        }
        p = new ProcesadorImagenes();
        Date fechaActual = adm.Date();
        String fechaI = (fechaActual.getYear()+1900)+"-"+(fechaActual.getMonth()+1)+"-"+fechaActual.getDate()+" "+fechaActual.getHours()+":"+fechaActual.getMinutes()+":"+fechaActual.getSeconds();
        System.out.println("FECHA ACTUAL:"+fechaI);
        List<Periodos> periodosList = adm.query("Select o from Periodos as o "
                            + " where o.activo = true and '"+fechaI+"' between o.fechaIniMat and o.fechaFinMat  ");
        if (periodosList.size() > 0) {
            per = periodosList.get(0);
            habilitadoMatriculas = true;
            estudiante = (Estudiantes) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
            buscarCedula();
        } else {
            habilitadoMatriculas = false;
        }
    }

    public void buscarMateriasNoAsignadas() {
        origen = adm.queryNativo(" Select o.* from Materias as o "
                + "where o.id_materias not in (Select m.id_Materias from Matriculas_Materias as m where m.id_Matriculas = '" + object.getIdMatriculas() + "') "
                + " order by o.nombre ", Materias.class);
        destino = adm.query("Select m.idMaterias from MateriasMatricula as m where m.idMatriculas.idMatriculas = '" + object.getIdMatriculas() + "' "
                + " order by m.idMaterias.nombre ");
    }

    public String editarAction(Matriculas obj) {
        inicializar();
        object = obj;
        obj.getIdEstudiantes().setClave(cl.desencriptar(obj.getIdEstudiantes().getClave()));
        clave2 = obj.getIdEstudiantes().getClave();
        foto1 = object.getIdMatriculas() + ".jpg";
        try {
            generarImagenTmp(foto1, estudiante.getFoto());
        } catch (Exception e) {
            System.out.println("AUN NO SE HA CARGADO LA IMAGEN...");
        }
        paisSeleccionado = estudiante.getIdCanton().getIdProvincia().getIdPais();
        buscarProvincia();
        provinciaSeleccionado = estudiante.getIdCanton().getIdProvincia();
        buscarCanton();
        cantonSeleccionado = estudiante.getIdCanton();
//         
//        paisSeleccionado3 = estudiante.getIdPais();
//        paisSeleccionado4 = estudiante.getPaiIdPais();
        //generarImagen("logo.png", object.getFoto());
        buscarMateriasNoAsignadas();
        //foto1 = "logo.png";
        System.out.println("" + object.getIdMatriculas());
        return null;
    }

    protected void inicializar() {
        object = new Matriculas(0);
        if (estudiante == null) {
            estudiante = new Estudiantes("");
        }
        if (pariente1 == null) {
            pariente1 = new Parientes();
        }
        if (pariente2 == null) {
            pariente2 = new Parientes();
        }
        if (pariente3 == null) {
            pariente3 = new Parientes();
        }


        object.setIdEstudiantes(estudiante);
        estudiante.setSexo("M");
        //estudiante.setTipoIdentificacion("C");
        foto1 = null;
        clave2 = "";
//        origen = adm.query(" Select o from Materias as o order by o.nombre ");
//        destino = new ArrayList<Materias>();
        //materias = new DualListModel<Materias>(origen, destino); 
        //   cargarDataModel();
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
            Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Graba el registro asociado al objeto que
     */
    public String guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (estudiante.getIdEstudiantes().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la IDENTIFICACIÓN del Estudiante", ""));
            return null;
        }

        if (estudiante.getApellidoPaterno().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el APELLLIDO PATERNO del Estudiante", ""));
            return null;
        }
        if (estudiante.getNombre().isEmpty()) {
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
        if (carreraSeleccionado.getIdCarreras().equals(new Integer(0))) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No ha seleccionado la CARRERA", "No ha seleccionado la CARRERA"));
            return null;
        }
        if (categoriaSeleccionado.getIdCategoriasSociales().equals(new Integer(0))) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No ha seleccionado el estado de la matricula", "No ha seleccionado la categoría A,B,C "));
            return null;
        }
        if (object.getEstadoMat().equals("")) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No ha seleccionado el estado de la matricula", "No ha seleccionado el estado de la matricula"));
            return null;
        }


        estudiante.setClave(cl.encriptar(estudiante.getClave()));
        estudiante.setIdCanton(cantonSeleccionado);
        estudiante.setFoto(imagen);
        //ESTUDIANTES EMPIEZO A GUARDAR O ACTUALIZAR
        if (adm.existe("Estudiantes", "idEstudiantes", estudiante.getIdEstudiantes()).size() <= 0) {
            adm.guardar(estudiante);
        } else {
            adm.actualizar(estudiante);
        }
        object.setIdEstudiantes(estudiante);
        pariente1.setIdEstudiantes(object.getIdEstudiantes());
        pariente1.setTipoRepresentante("F");
        pariente2.setTipoRepresentante("P");
        pariente3.setTipoRepresentante("M");
        //1.- PARIENTES EMPIEZO A GUARDAR
        if (pariente1.getIdParientes().equals(new Integer(0))) {
            List<Parientes> pariList = adm.existe("Parientes", "identificacion", pariente1.getIdentificacion());
            if (pariList.size() > 0) {
                pariente1.setIdParientes(pariList.get(0).getIdParientes());
                adm.actualizar(pariente1);
            } else {
                pariente1.setIdParientes(adm.getNuevaClave("Parientes", "idParientes"));
                adm.guardar(pariente1);
            }
        } else {
            adm.actualizar(pariente1);
        }

        //2.- PARIENTES EMPIEZO A GUARDAR
        if (pariente2.getIdParientes().equals(new Integer(0))) {
            List<Parientes> pariList = adm.existe("Parientes", "identificacion", pariente2.getIdentificacion());
            if (pariList.size() > 0) {
                pariente2.setIdParientes(pariList.get(0).getIdParientes());
                adm.actualizar(pariente2);
            } else {
                pariente2.setIdParientes(adm.getNuevaClave("Parientes", "idParientes"));
                adm.guardar(pariente2);
            }
        } else {
            adm.actualizar(pariente2);
        }

        //3.- PARIENTES EMPIEZO A GUARDAR
        if (pariente3.getIdParientes().equals(new Integer(0))) {
            List<Parientes> pariList = adm.existe("Parientes", "identificacion", pariente3.getIdentificacion());
            if (pariList.size() > 0) {
                pariente3.setIdParientes(pariList.get(0).getIdParientes());
                adm.actualizar(pariente3);
            } else {
                pariente3.setIdParientes(adm.getNuevaClave("Parientes", "idParientes"));
                adm.guardar(pariente3);
            }
        } else {
            adm.actualizar(pariente3);
        }

        object.setIdPeriodos(per);
        object.setIdCategoriasSociales(categoriaSeleccionado);
        object.setIdCarreras(carreraSeleccionado);

        if (object.getIdMatriculas().equals(new Integer(0))) {
            if (!permisos.verificarPermisoReporte("Matriculas", "agregar_matriculas", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
                return null;
            }
            try {
                if (adm.existe("Matriculas", "idEstudiantes", object.getIdEstudiantes(), "idPeriodos", object.getIdPeriodos(), "").size() <= 0) {
                    object.setIdMatriculas(adm.getNuevaClave("Matriculas", "idMatriculas"));
                    object.setNumero(nuevaMatricula());
                    adm.guardar(object);
                    aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", object.getIdMatriculas() + "");
//                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre y Cedula ya MATRICULADOS, búsquelos primero si desea editarlos...!", "Nombre y Cedula ya MATRICULADOS, busquelos primero si desea editarlos...!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
            if (!permisos.verificarPermisoReporte("Matriculas", "actualizar_matriculas", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
                return null;
            }
            try {
                adm.actualizar(object);
                aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdMatriculas() + "");
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
//                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

        }
        estudiante.setClave(cl.desencriptar(estudiante.getClave()));
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
            //cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, e);
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
            java.util.logging.Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, e);
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
            java.util.logging.Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, e);
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
            java.util.logging.Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public void limpiar() {
        //object = new Matriculas(0);
        object = new Matriculas(0);
        estudiante = new Estudiantes("");
        pariente1 = new Parientes();
        pariente2 = new Parientes();
        pariente3 = new Parientes();
        object.setIdEstudiantes(estudiante);
        estudiante.setSexo("M");
        //estudiante.setTipoIdentificacion("C");
        foto1 = null;
        clave2 = "";
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
            java.util.logging.Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public List<Estudiantes> buscarApellido(String apellido) {
        try {
            apellido = apellido.trim();
            estudiantesListado = adm.query("Select o from Estudiantes as o "
                    + " where o.apellidoPaterno like '%" + apellido + "%' "
                    + " order by o.apellidoPaterno ", 0, 10);
            return estudiantesListado;


        } catch (Exception e) {
            java.util.logging.Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
        return null;
    }

    public List<Estudiantes> buscarCedula(String cedula) {
        try {
            cedula = cedula.trim();
            if (cedula.isEmpty()) {
                cedula = estudiante.getIdEstudiantes();
            }
            estudiantesListado = adm.query("Select o from Estudiantes as o "
                    + " where o.idEstudiantes like '%" + cedula + "%' "
                    + " order by o.idEstudiantes ", 0, 10);
            return estudiantesListado;


        } catch (Exception e) {
            java.util.logging.Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
        return null;
    }

    public void generarClaveyUsuario() {
        try {
            if (estudiante.getUsuario() == null) {
                estudiante.setUsuario(estudiante.getNombre().substring(0, 1).toLowerCase() + "" + estudiante.getApellidoPaterno().toLowerCase());
                estudiante.setClave(estudiante.getUsuario().toLowerCase());
                clave2 = estudiante.getClave();

            }

        } catch (Exception e) {
        }

    }

    public void buscarCedula() {

        String cedula = estudiante.getIdEstudiantes();
        estudiante = (Estudiantes) adm.buscarClave(cedula, Estudiantes.class);

        List<Parientes> par = adm.query("Select o from Parientes as o where o.idEstudiantes.idEstudiantes = '" + estudiante.getIdEstudiantes() + "'  ");
        for (Iterator<Parientes> it = par.iterator(); it.hasNext();) {
            Parientes parientes = it.next();
            if (parientes.getTipoRepresentante().equals("F")) {
                pariente1 = parientes;
            } else if (parientes.getTipoRepresentante().equals("P")) {
                pariente2 = parientes;
            } else if (parientes.getTipoRepresentante().equals("M")) {
                pariente3 = parientes;
            }

        }
        paisSeleccionado = estudiante.getIdCanton().getIdProvincia().getIdPais();
        buscarProvincia();
        provinciaSeleccionado = estudiante.getIdCanton().getIdProvincia();
        buscarCanton();
        cantonSeleccionado = estudiante.getIdCanton();
        estudiante.setClave(cl.desencriptar(estudiante.getClave()));
        clave2 = estudiante.getClave();
        buscarMatricula(estudiante);
        foto1 = estudiante.getIdEstudiantes() + ".jpg";
        try {
            generarImagenTmp(foto1, estudiante.getFoto());
        } catch (Exception e) {
            System.out.println("AUN NO SE HA CARGADO LA IMAGEN..." + e);
        }

        estudiantesListado = null;
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
        List numeroYa = adm.query("Select o from Matriculas as o  where  o.numero = '" + noMatri + "'");
        if (numeroYa.size() > 0) {
            Integer nClave = adm.geUltimaMatricula("Select max(o.numero) from Matriculas as o "
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

    protected void buscarMatricula(Estudiantes estudiante) {
        List<Matriculas> matriculasListado = adm.query("Select o from Matriculas as o "
                + " where o.idEstudiantes.idEstudiantes = '" + estudiante.getIdEstudiantes() + "' "
                + " and o.idPeriodos.activo = true ");
        if (matriculasListado.size() > 0) {
            object = matriculasListado.get(0);
            carreraSeleccionado = object.getIdCarreras();
            categoriaSeleccionado = object.getIdCategoriasSociales();
        }
    }

    public void handleSelect(SelectEvent event) {
        estudiante = (Estudiantes) adm.buscarClave(((Estudiantes) event.getObject()).getIdEstudiantes(), Estudiantes.class);

        imagen = estudiante.getFoto();
        List<Parientes> par = adm.query("Select o from Parientes as o where o.idEstudiantes.idEstudiantes = '" + estudiante.getIdEstudiantes() + "'  ");
        for (Iterator<Parientes> it = par.iterator(); it.hasNext();) {
            Parientes parientes = it.next();
            if (parientes.getTipoRepresentante().equals("F")) {
                pariente1 = parientes;
            } else if (parientes.getTipoRepresentante().equals("P")) {
                pariente2 = parientes;
            } else if (parientes.getTipoRepresentante().equals("M")) {
                pariente3 = parientes;
            }

        }
        paisSeleccionado = estudiante.getIdCanton().getIdProvincia().getIdPais();
        buscarProvincia();
        provinciaSeleccionado = estudiante.getIdCanton().getIdProvincia();
        buscarCanton();
        cantonSeleccionado = estudiante.getIdCanton();
        estudiante.setClave(cl.desencriptar(estudiante.getClave()));
        clave2 = estudiante.getClave();
        buscarMatricula(estudiante);
        foto1 = estudiante.getIdEstudiantes() + ".jpg";
        try {
            generarImagenTmp(foto1, estudiante.getFoto());
        } catch (Exception e) {
            System.out.println("AUN NO SE HA CARGADO LA IMAGEN..." + e);
        }

        estudiantesListado = null;

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
            estudiante.setFoto(event.getFile().getContents());
            String nombreTemporal = event.getFile().getFileName().substring(0, event.getFile().getFileName().lastIndexOf("."));
            String formato = event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf(".") + 1);
            nombreTemporal = nombreTemporal + "" + (new Date()).getTime();
            java.io.File f = java.io.File.createTempFile(nombreTemporal, "." + formato);
            FileOutputStream archivoNuevo = new FileOutputStream(f);
            archivoNuevo.write(event.getFile().getContents());

            FileInputStream fin = null;
            fin = new FileInputStream(f);
            byte[] buffer = new byte[(int) f.length()];
            fin.read(buffer);
            fin.close();
            foto1 = event.getFile().getFileName().substring(0, event.getFile().getFileName().lastIndexOf(".")) + "." + formato;
            estudiante.setFoto(buffer);
            imagen = buffer;
            System.out.println("" + foto1);
            try {
                BufferedImage bf = p.escalarATamanyo(f, 230, 170, formato);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bf, "jpg", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                estudiante.setFoto(imageInByte);
                imagen = buffer;
                baos.close();
                f = null;
                bf = null;
                p = null;

                buffer = null;
                imageInByte = null;
                fin = null;
                archivoNuevo.close();
                archivoNuevo = null;
                foto1 = event.getFile().getFileName().substring(0, event.getFile().getFileName().lastIndexOf(".")) + "." + formato;
            } catch (Exception ax) {
                Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, ax);

            }



        } catch (Exception ex) {
            Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, e);

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
            java.util.logging.Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
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
            java.util.logging.Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, e);

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
            java.util.logging.Logger.getLogger(MatriculasEstudianteBean.class.getName()).log(Level.SEVERE, null, e);

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
//                estudiante.setIdCanton(new Canton());
//                estudiante.getIdCanton().setIdProvincia(new Provincia());
//                estudiante.getIdCanton().getIdProvincia().setIdPais(new Pais());
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
//                estudiante.setIdCanton(new Canton());
//                estudiante.getIdCanton().setIdProvincia(new Provincia());
//                estudiante.getIdCanton().getIdProvincia().setIdPais(new Pais());
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
//                estudiante.setIdCanton(new Canton());
//                estudiante.getIdCanton().setIdProvincia(new Provincia());
//                estudiante.getIdCanton().getIdProvincia().setIdPais(new Pais());
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

    public String fichaMatricula(String tipo) throws JRException {
        FacesContext context = FacesContext.getCurrentInstance();
        try {

            if (object.getIdMatriculas().equals(new Integer(0))) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Primero guarde la matricula para proceder a imprimir", "Primero guarde la matricula para proceder a imprimir"));
                return null;
            }
            if (object.getEstadoMat().equals("")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No ha seleccionado el estado de la matricula", "No ha seleccionado el estado de la matricula"));
                return null;
            }
            Map map = new HashMap();
            map.put("tituloReporte", "FICHA DE MATRICULA");
            map.put("titulo1", "USUARIO");
            map.put("titulo2", "# DE INFORMES");
            List<Estudiantes> estu = new ArrayList<Estudiantes>();
            estu.add(estudiante);
            ExportarReportes ex = new ExportarReportes(estu, "fichaMatricula", map);
            if (tipo.equals("PDF")) {
                ex.PDF();
            } else if (tipo.equals("DOCX")) {
                ex.DOCX();
            } else if (tipo.equals("XLS")) {
                ex.XLSX();
            } else if (tipo.equals("PRINT")) {
                ex.PRINT();
            }
        } catch (Exception ex1) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, ex1.getMessage(), ex1.getMessage()));
            java.util.logging.Logger.getLogger(FichaMatricula.class.getName()).log(Level.SEVERE, null, ex1);
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
            if (object == null) {
                //object = new Institucion();
                estudiante.setIdCanton(new Canton());
                estudiante.getIdCanton().setIdProvincia(new Provincia());
                estudiante.getIdCanton().getIdProvincia().setIdPais(new Pais());
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
            //cargarDataModel();
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

    public Estudiantes getEstudiante() {
        if (estudiante == null) {
            estudiante = new Estudiantes("");
        }
        return estudiante;
    }

    public void setEstudiante(Estudiantes estudiante) {
        this.estudiante = estudiante;
    }

    public List<Estudiantes> getEstudiantesListado() {
        return estudiantesListado;
    }

    public void setEstudiantesListado(List<Estudiantes> estudiantesListado) {
        this.estudiantesListado = estudiantesListado;
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

    public Boolean getHabilitadoMatriculas() {
        return habilitadoMatriculas;
    }

    public void setHabilitadoMatriculas(Boolean habilitadoMatriculas) {
        this.habilitadoMatriculas = habilitadoMatriculas;
    }
}
