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
import java.io.InputStream;
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
import jcinform.persistencia.Archivos;
import jcinform.persistencia.Canton;
import jcinform.persistencia.Carreras;
import jcinform.persistencia.CarrerasMaterias;
import jcinform.persistencia.CategoriasSociales;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Materias;
import jcinform.persistencia.MateriasMatricula;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Niveles;
import jcinform.persistencia.Notas;
import jcinform.persistencia.Pais;
import jcinform.persistencia.Parametros;
import jcinform.persistencia.Parientes;
import jcinform.persistencia.Perfiles;
import jcinform.persistencia.Periodos;
import jcinform.persistencia.Provincia;
import jcinform.persistencia.RangosGpa;
import jcinform.persistencia.RangosIngresos;
import jcinform.persistencia.SecuenciaDeMaterias;
import jcinform.persistencia.SecuenciaDeMateriasAdicionales;
import jcinform.persistencia.Titulos;
import jcinform.procesos.Administrador;
import jcinform.procesos.claves;
import miniaturas.ProcesadorImagenes;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import reportes.ExportarReportesCon;
import reportes.ExportarReportesSource;
import reportes.FichaMatricula;
import sources.ReporteActaMatricula;
import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
//@RequestScoped
public class MatriculasBean2 {

    /**
     * Creates a new instance of MatriculasBean
     */
    Matriculas object;
    Estudiantes estudiante;
    Administrador adm;
    protected List<Matriculas> model;
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
    List<CarrerasMaterias> origen;
    List<CarrerasMaterias> destino;
    List<Estudiantes> estudiantesListado = new ArrayList<Estudiantes>();
    Periodos per;
    Archivos arLibreta;
    Archivos arTitulo;
    Archivos arCedula;
    List<RangosGpa> rangos;

    ;
    public MatriculasBean2() {
        //super();
        if (adm == null) {
            adm = new Administrador();
        }
        if (permisos == null) {
            permisos = new Permisos();
        }
        if (!permisos.verificarPermisoReporte("Matriculas", "ingresar_matriculas.jspx", "ingresar", true, "ADMINISTRACION")) {
            try {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/noPuedeIngresar.jspx");
            } //selectedMatriculas = new Matriculas();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(MatriculasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        p = new ProcesadorImagenes();
        inicializar();
        per = (Periodos) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("periodo");
        //selectedMatriculas = new Matriculas();

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
        if (arLibreta == null) {
            arLibreta = new Archivos();
        }
        if (arCedula == null) {
            arCedula = new Archivos();
        }
        if (arTitulo == null) {
            arTitulo = new Archivos();
        }
        object.setIdEstudiantes(estudiante);
        estudiante.setSexo("M");
        //estudiante.setTipoIdentificacion("C");
        foto1 = null;
        clave2 = "";
        rangos = adm.listar("RangosGpa");
    }

    public String anadir(CarrerasMaterias obj) {


        if (!validarSecuencia(obj)) {
            if (destino == null) {
//                destino = new ArrayList<CarrerasMaterias>();
            }
            destino.add(obj);
            origen.remove(obj);
            sumarCreditos();
        }

        return null;
    }

    public String quitar(CarrerasMaterias obj) {
        if (obj.convalidada) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se peude eliminar materia CONVALIDADA/HOMOLOGADA", "No se peude eliminar materia CONVALIDADA/HOMOLOGADA"));
            return null;
        }
        origen.add(obj);
        destino.remove(obj);
        sumarCreditos();
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
            Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Graba el registro asociado al objeto que
     */
    public String guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!object.getEstadoMat().equals("M")) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Estudiante NO matriculado...!", ""));
            return null;
        }
        if (estudiante.getIdEstudiantes().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la IDENTIFICACIÓN del Estudiante", ""));
            return null;
        }

        if (carreraSeleccionado.getIdCarreras().equals(new Integer(0))) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No ha seleccionado la CARRERA", "No ha seleccionado la CARRERA"));
            return null;
        }

        object.setIdEstudiantes(estudiante);
        object.setIdPeriodos(per);
        estudiante.setIdCategoriasSociales(categoriaSeleccionado);
        object.setIdCarreras(carreraSeleccionado);

        if (object.getIdMatriculas().equals(new Integer(0))) {
            if (!permisos.verificarPermisoReporte("Matriculas", "agregar_matriculas.jspx", "agregar", true, "ADMINISTRACION")) {
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
            if (!permisos.verificarPermisoReporte("Matriculas", "actualizar_matriculas.jspx", "agregar", true, "ADMINISTRACION")) {
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
                java.util.logging.Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

        }

        List<MateriasMatricula> materiasTomadas = adm.query("Select o from MateriasMatricula as o "
                + " where o.idMatriculas.idMatriculas = '" + object.getIdMatriculas() + "'"
                + " and (o.pagado  = true or o.convalidado = true) ");
        for (Iterator<MateriasMatricula> it = materiasTomadas.iterator(); it.hasNext();) {
            MateriasMatricula materiasMatricula1 = it.next();
            System.out.println("" + materiasMatricula1.getIdMaterias().getIdMaterias() + " " + materiasMatricula1.getIdMaterias().getNombre());

        }


        //GUARDO LAS MATERIAS DE LA MATRICULA
        adm.ejecutaSql("Delete from MateriasMatricula where idMatriculas.idMatriculas  = '" + object.getIdMatriculas() + "' and (pagado is null or pagado = false) and (convalidado = false or convalidado is null) ");
        for (Iterator<CarrerasMaterias> it = destino.iterator(); it.hasNext();) {
            CarrerasMaterias carrerasMaterias = it.next();
            MateriasMatricula matMat = new MateriasMatricula(adm.getNuevaClave("MateriasMatricula", "idMateriasMatricula"));
            matMat.setIdMaterias(carrerasMaterias.getIdMaterias());
            matMat.setIdMatriculas(object);
            //matMat.setTipo(carrerasMaterias.getIdEjes().getNombre());
            matMat.setNumeroMatricula(object.getNumero());
            int noEstaPagada = 0;
            for (Iterator<MateriasMatricula> matePagada = materiasTomadas.iterator(); matePagada.hasNext();) {
                MateriasMatricula materiasMatricula1 = matePagada.next();
                if (materiasMatricula1.getIdMaterias().getIdMaterias().equals(carrerasMaterias.getIdMaterias().getIdMaterias())) {
                    noEstaPagada++;
                }
            }
            if (noEstaPagada == 0) {
                adm.guardar(matMat);
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
            if (!permisos.verificarPermisoReporte("Matriculas", "eliminar_matriculas.jspx", "eliminar", true, "ADMINISTRACION")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
            }
            adm.eliminarObjeto(Matriculas.class, obj.getIdMatriculas());
            aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "eliminar", "", obj.getIdMatriculas() + "");
            inicializar();
            //cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, e);
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
            java.util.logging.Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, e);
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
            java.util.logging.Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, e);
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
            java.util.logging.Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, e);
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
        arLibreta = new Archivos();
        arCedula = new Archivos();
        arTitulo = new Archivos();
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
            java.util.logging.Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, e);
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
            java.util.logging.Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, e);
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
            java.util.logging.Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, e);
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

    public String generarUsuarioSiVacio(String apellido1, String apellido2, String nombre) {
        String usuario = apellido1.substring(0, 2) + "" + apellido2.substring(0, 2) + "" + nombre.substring(0, 3);
        return usuario.toUpperCase();
    }

    public String generarClaveSiVacio() {
        String caracterNumeros = "012345678901234567890123456789012345678901234567890123456789";
        int numero_caracteres = 4;
        int total = caracterNumeros.length();
        for (int a = 0; a < numero_caracteres; a++) {
            clave2 += caracterNumeros.charAt(((Double) (total * Math.random())).intValue());
        }
        String clave = clave2.toUpperCase();
        return clave;

    }

    public void buscarCedula() {

        String cedula2 = estudiante.getIdEstudiantes();
        estudiante = (Estudiantes) adm.buscarClave(cedula2, Estudiantes.class);
        if (estudiante.getIdParientes() != null) {
            pariente1 = estudiante.getIdParientes();
        } else {
            pariente1 = new Parientes();
        }
        if (estudiante.getParIdParientes() != null) {
            pariente2 = estudiante.getParIdParientes();
        } else {
            pariente2 = new Parientes();
        }

        if (estudiante.getParIdParientes2() != null) {
            pariente3 = estudiante.getParIdParientes2();
        } else {
            pariente3 = new Parientes();
        }

        arLibreta = new Archivos();
        arTitulo = new Archivos();
        arCedula = new Archivos();
        List<Archivos> archi = adm.query("Select o from Archivos as o "
                + "where o.idEstudiantes.idEstudiantes = '" + estudiante.getIdEstudiantes() + "'  ");
        for (Iterator<Archivos> it = archi.iterator(); it.hasNext();) {
            Archivos arcIt = it.next();
            if (arcIt.getTipoArchivo().equals("LIB")) {
                arLibreta = arcIt;
                libreta = arcIt.getArchivo();
                libretaNombre = arcIt.getNombre();
                libretaFormato = libretaFormato = arcIt.getNombre().substring(arcIt.getNombre().lastIndexOf(".") + 1);;
            } else if (arcIt.getTipoArchivo().equals("CED")) {
                arCedula = arcIt;
                cedula = arcIt.getArchivo();
                cedulaNombre = arcIt.getNombre();
                cedulaFormato = arcIt.getNombre().substring(arcIt.getNombre().lastIndexOf(".") + 1);;
            } else if (arcIt.getTipoArchivo().equals("TIT")) {
                arTitulo = arcIt;
                titulo = arcIt.getArchivo();
                tituloNombre = arcIt.getNombre();
                tituloFormato = arcIt.getNombre().substring(arcIt.getNombre().lastIndexOf(".") + 1);;
            }

        }
        paisSeleccionado = estudiante.getIdCanton().getIdProvincia().getIdPais();
        buscarProvincia();
        provinciaSeleccionado = estudiante.getIdCanton().getIdProvincia();
        buscarCanton();
        cantonSeleccionado = estudiante.getIdCanton();
        try {
            estudiante.setClave(cl.desencriptar(estudiante.getClave()));
            clave2 = estudiante.getClave();
        } catch (Exception e) {
            estudiante.setUsuario(generarUsuarioSiVacio(estudiante.getApellidoPaterno(), estudiante.getApellidoMaterno(), estudiante.getNombre()));
            estudiante.setClave(generarClaveSiVacio());
            clave2 = estudiante.getClave();
        }

        buscarMatricula(estudiante);
        foto1 = estudiante.getIdEstudiantes() + ".jpg";
        try {
            if (estudiante.getFoto() != null) {
                generarImagenTmp(foto1, estudiante.getFoto());
            } else {
                foto1 = "";
            }
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

    protected void buscarMateriasMatricula(Matriculas mat) {
        if (!object.getIdMatriculas().equals(new Integer(0))) {
            List<MateriasMatricula> mateMatriculas = adm.query(" Select o from MateriasMatricula as o "
                    + " WHERE o.idMatriculas.idMatriculas = '" + mat.getIdMatriculas() + "' "
                    + " and o.idMatriculas.idCarreras.idCarreras = '" + mat.getIdCarreras().getIdCarreras() + "' ");
            destino = new ArrayList<CarrerasMaterias>();
            for (Iterator<MateriasMatricula> it = mateMatriculas.iterator(); it.hasNext();) {
                MateriasMatricula materiasMatricula1 = it.next();
                List<CarrerasMaterias> carMat = adm.query("Select o from CarrerasMaterias as o "
                        + " where o.idMaterias.idMaterias = " + materiasMatricula1.getIdMaterias().getIdMaterias() + "  "
                        + " and o.idCarreras.idCarreras = " + mat.getIdCarreras().getIdCarreras() + " ");
                System.out.println(""+materiasMatricula1.getIdMaterias().getNombre()+" "+materiasMatricula1.getIdMaterias().getIdMaterias());
                try {
                    (carMat.get(0)).setConvalidada(materiasMatricula1.getConvalidado());
                } catch (Exception e) {
                    (carMat.get(0)).setConvalidada(false);
                }

                try {
                    (carMat.get(0)).setPagada(materiasMatricula1.getPagado());
                } catch (Exception e) {
                    (carMat.get(0)).setPagada(false);
                }

                (carMat.get(0)).setValor(materiasMatricula1.getValor());
                destino.add(carMat.get(0));
            }

//            destino = adm.queryNativo(" Select c.* from Carreras_Materias as  c "
//                    + " WHERE c.id_Materias in "
//                    + "(Select o.id_Materias from Materias_Matricula as o WHERE o.id_Matriculas = '" + object.getIdMatriculas() + "' ) "
//                    + " and c.id_Carreras = '" + object.getIdCarreras().getIdCarreras() + "' "
//                    + " ", CarrerasMaterias.class);



            origen = adm.query("Select m from CarrerasMaterias as m "
                    + " where m.idCarreras.idCarreras = '" + carreraSeleccionado.getIdCarreras() + "' "
                    + " and m.idMaterias.idMaterias not in (Select o.idMaterias.idMaterias from Notas as o where o.estado = 'A' "
                    + " and o.idMatriculas.idEstudiantes.idEstudiantes = '" + mat.getIdEstudiantes().getIdEstudiantes() + "' "
                    + " and o.idMatriculas.idCarreras.idCarreras = '" + mat.getIdCarreras().getIdCarreras() + "') "
                    + " order by m.idNiveles.secuencia");
            for (Iterator<CarrerasMaterias> it = destino.iterator(); it.hasNext();) {
                CarrerasMaterias destItem = it.next();
                origen.remove(destItem);
            }
            //BUSCAR MATERIAS APROBADAS Y QUITARLAS TAMBIÉNOON09N9KGFD444-,  J,M
//            List<Materias> lista = adm.query("");
//            for (Iterator<Materias> itMat = lista.iterator(); itMat.hasNext();) {
//                Materias materias1 = itMat.next();
//                System.out.println(""+materias1);
//                for (Iterator<CarrerasMaterias> it = origen.iterator(); it.hasNext();) {
//                    CarrerasMaterias oritem = it.next();
//                    if(oritem.getIdMaterias().getIdMaterias().equals(materias1.getIdMaterias())){
//                        try{
//                            origen.remove(oritem);
//                        }catch(Exception er){
//                            System.out.println(""+er);
//                        }
//                    }
//                     
//                }       
//                
//            }
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

//    public boolean validarSecuencia(CarrerasMaterias materiaAanadir) {
//
//        List<SecuenciaDeMaterias> secuencias = adm.query("Select o from SecuenciaDeMaterias as o "
//                + " where o.idCarrerasMaterias.idMaterias.idMaterias = '" + materiaAanadir.getIdMaterias().getIdMaterias() + "' "
//                + " and  o.idCarrerasMaterias.idCarreras.idCarreras =  '" + carreraSeleccionado.getIdCarreras() + "' ");
//          int existenRequeridas = 0;
//           String requeridas = "";
//                       FacesContext context = FacesContext.getCurrentInstance();
//        if (secuencias.size() > 0) {
//
//
//            SecuenciaDeMaterias sec = secuencias.get(0);
//            System.out.println("ENCONTRADA: " + sec.getIdCarrerasMaterias().getIdMaterias().getNombre());
//
//            //estas son las materias que tiene que aprobar para tomar la siguiente materia
//            List<SecuenciaDeMaterias> secuenciasPrevias = adm.query("Select o from SecuenciaDeMaterias as o "
//                    + " where o.idCarrerasMaterias.idCarreras.idCarreras =  '" + carreraSeleccionado.getIdCarreras() + "' "
//                    + " and o.fila = '" + sec.getFila() + "' "
//                    + " and o.orden < '" + sec.getOrden() + "' ");
//           
//          
//            for (Iterator<SecuenciaDeMaterias> it = secuenciasPrevias.iterator(); it.hasNext();) {
//                SecuenciaDeMaterias secuenciaDeMaterias = it.next();
//                List<Notas> notasEncontradas = adm.query("Select o from Notas as o "
//                        + " where o.idMateriasMatricula.idMaterias.idMaterias = '" + secuenciaDeMaterias.getIdCarrerasMaterias().getIdMaterias().getIdMaterias() + "' "
//                        + "and o.idMateriasMatricula.idMatriculas.idEstudiantes.idEstudiantes = '" + object.getIdEstudiantes().getIdEstudiantes() + "' ");
//                int existenReprobadas = 0;
//                if(notasEncontradas.size()>0){
//                    for (Iterator<Notas> it1 = notasEncontradas.iterator(); it1.hasNext();) {
//                        Notas notas = it1.next();
//                        if (notas.getEstadoNot()==false) {
//                            existenReprobadas++;
//                        }
//                    }
//
//                }else{
//                                requeridas += " || " + secuenciaDeMaterias.getIdCarrerasMaterias().getIdNiveles().getNombre() + ": " + secuenciaDeMaterias.getIdCarrerasMaterias().getIdMaterias().getNombre();                    
//                }
//                   if (existenReprobadas > 0) {
//                        requeridas += " || " + secuenciaDeMaterias.getIdCarrerasMaterias().getIdNiveles().getNombre() + ": " + secuenciaDeMaterias.getIdCarrerasMaterias().getIdMaterias().getNombre();
//                        existenRequeridas++;
//                    }
//                List<SecuenciaDeMateriasAdicionales> adicionales = adm.query("Select o from SecuenciaDeMateriasAdicionales as o "
//                        + " where o.idSecuenciaDeMaterias.idSecuenciaDeMaterias = '"+secuenciaDeMaterias.getIdSecuenciaDeMaterias()+"'  ");
//                for (Iterator<SecuenciaDeMateriasAdicionales> it1 = adicionales.iterator(); it1.hasNext();) {
//                    SecuenciaDeMateriasAdicionales secuenciaDeMateriasAdicionales = it1.next();
//                    notasEncontradas = adm.query("Select o from Notas as o "
//                            + " where o.idMateriasMatricula.idMaterias.idMaterias = '" + secuenciaDeMateriasAdicionales.getIdCarrerasMaterias().getIdMaterias().getIdMaterias() + "' "
//                            + "and o.idMateriasMatricula.idMatriculas.idEstudiantes.idEstudiantes = '" + object.getIdEstudiantes().getIdEstudiantes() + "' ");
//                     existenReprobadas = 0;
//                    for (Iterator<Notas> it3 = notasEncontradas.iterator(); it3.hasNext();) {
//                        Notas notas = it3.next();
//                        if (notas.getEstadoNot()==false) {
//                            existenReprobadas++;
//                        }
//                    }
//                    if (existenReprobadas> 0) {
//                        requeridas += " || " + secuenciaDeMaterias.getIdCarrerasMaterias().getIdNiveles().getNombre() + ": " + secuenciaDeMaterias.getIdCarrerasMaterias().getIdMaterias().getNombre();
//                        existenRequeridas++;
//
//                    }
//
//                }
//
//            }
//            //estas son las materias,ADICIONALES QUE TIENE QUE APROBAR que tiene que aprobar para tomar la siguiente materia
//             
//            
//
//
//        }
//        if(existenRequeridas >0){
//            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"NO PUEDE AGREGAR LA MATERIA, FALTA APROBAR OTRAS MATERIAS" ,requeridas));
//            return true;
//        }else{
//            // TOCA VALIDAR SI YA ASIGNÓ UNA MATERIA  DE LA MALLA INMEDIATA SUPERIOR EJEM: MATEMATICAS CON MATEMATICAS I 
//        //origen = adm.query("Select m from CarrerasMaterias as m  where m.idCarreras.idCarreras = '"+carreraSeleccionado.getIdCarreras()+"' order by m.idNiveles.secuencia");
//            for (Iterator<CarrerasMaterias> it = origen.iterator(); it.hasNext();) {
//                CarrerasMaterias destItem = it.next();
//                origen.remove(destItem);
//
//            }
//            return false;
//        }
//        
//        
//
//    }
    public boolean validarSecuencia(CarrerasMaterias materiaAanadir) {

        //ESTE METODO DEBE INICIALIZARSE CADA VEZ QUE CAMBIO DE ESPECIALDIAD
        try {
            CarrerasMaterias tmp = anadidasArray[0][0];
            if (tmp == null) {
                buscarMateriasdeCarrera();
            }
        } catch (Exception e) {
            System.out.println("erro en validar secuencia");
            if (destino == null) {
                buscarMateriasdeCarrera();
            }
        }




        FacesContext context = FacesContext.getCurrentInstance();
        int fila = -1;
        int columna = -1;
        String requeridas = "";
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 12; j++) {
                CarrerasMaterias carreraMateria = anadidasArray[i][j];
                try {
                    if (materiaAanadir.getIdMaterias().getIdMaterias().equals(carreraMateria.getIdMaterias().getIdMaterias())) {
                        fila = i;
                        columna = j;
                        break;
                    }
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            }

            if (fila > -1) {
                break;
            }
        }
        System.out.println("f: " + fila + " c: " + columna);
        int existenReprobadas = 0;
        //RECORRO PARA ENCONTRAR LAS MENORES A LA SELECCIONADA
        for (int j = 0; j < columna; j++) {
            CarrerasMaterias carreraMateria = anadidasArray[fila][j];
            if (carreraMateria.getIdMaterias().getIdMaterias() != null) {
                List<Notas> notasEncontradas = adm.query("Select o from Notas as o "
                        + " where o.idMaterias.idMaterias = '" + carreraMateria.getIdMaterias().getIdMaterias() + "' "
                        + "and o.idMatriculas.idEstudiantes.idEstudiantes = '" + object.getIdEstudiantes().getIdEstudiantes() + "' ");
                if (notasEncontradas.size() > 0) {
                    for (Iterator<Notas> it1 = notasEncontradas.iterator(); it1.hasNext();) {
                        Notas notas = it1.next();
                        if (notas.getEstadoNot() == false) {
                            requeridas += " || " + carreraMateria.getIdNiveles().getNombre() + ": " + carreraMateria.getIdMaterias().getNombre();
                        }
                    }

                } else {
                    requeridas += " || " + carreraMateria.getIdNiveles().getNombre() + ": " + carreraMateria.getIdMaterias().getNombre();
                }


            }
        }



        String idSeleccionado = "f" + fila + "c" + columna;
        adicionalesTmp = new ArrayList<SecuenciaDeMateriasAdicionales>();
        for (Iterator<SecuenciaDeMateriasAdicionales> it = adicionales.iterator(); it.hasNext();) {
            SecuenciaDeMateriasAdicionales secM = it.next();
            if (secM.getFilacolumna().equals(idSeleccionado)) {

                List<Notas> notasEncontradas = adm.query("Select o from Notas as o "
                        + " where o.idMaterias.idMaterias = '" + secM.getIdCarrerasMaterias().getIdMaterias().getIdMaterias() + "' "
                        + "and o.idMatriculas.idEstudiantes.idEstudiantes = '" + object.getIdEstudiantes().getIdEstudiantes() + "' ");
                if (notasEncontradas.size() > 0) {
                    for (Iterator<Notas> it1 = notasEncontradas.iterator(); it1.hasNext();) {
                        Notas notas = it1.next();
                        if (notas.getEstadoNot() == false) {
                            requeridas += " || " + secM.getIdCarrerasMaterias().getIdNiveles().getNombre() + ": " + secM.getIdCarrerasMaterias().getIdMaterias().getNombre();
                        }
                    }

                } else {
                    requeridas += " || " + secM.getIdCarrerasMaterias().getIdNiveles().getNombre() + ": " + secM.getIdCarrerasMaterias().getIdMaterias().getNombre();
                }



            }

        }
        if (requeridas.length() > 0) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO PUEDE AGREGAR LA MATERIA, FALTA APROBAR OTRAS MATERIAS", requeridas));
            return true;
        } else {
            return false;
        }


    }
    List<CarrerasMaterias> anadidas = new ArrayList<CarrerasMaterias>();
    CarrerasMaterias anadidasArray[][] = new CarrerasMaterias[30][12];
    List<CarrerasMaterias> listaMaterias = new ArrayList<CarrerasMaterias>();
    List<SecuenciaDeMateriasAdicionales> adicionales = new ArrayList<SecuenciaDeMateriasAdicionales>();
    List<SecuenciaDeMateriasAdicionales> adicionalesTmp = new ArrayList<SecuenciaDeMateriasAdicionales>();
    List<SelectItem> listaMateriasAdicionales = new ArrayList<SelectItem>();
    List<SelectItem> listaMateriasAdicionales2 = new ArrayList<SelectItem>();

    public void llenarArreglo() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 12; j++) {
                CarrerasMaterias car = new CarrerasMaterias();
                Materias mat = new Materias();
                mat.setNombre("");
                car.setIdMaterias(mat);
                car.setIdNiveles(new Niveles());
                car.setIdCarreras(new Carreras());
                car.setSecuenciaDeMateriasAdicionalesList(new ArrayList<SecuenciaDeMateriasAdicionales>());
                anadidasArray[i][j] = car;
            }
        }
    }

    public void buscarMateriasdeCarrera() {
        try {
            buscarMateriasMatricula(object);
            llenarArreglo();
            listaMaterias = adm.query("Select o from CarrerasMaterias as o "
                    + " where o.idCarreras.idCarreras = '" + carreraSeleccionado.getIdCarreras() + "'  "
                    + "and o.idCarrerasMaterias NOT IN (Select c.idCarrerasMaterias.idCarrerasMaterias "
                    + "from SecuenciaDeMaterias as c "
                    + " WHERE c.idCarrerasMaterias.idCarreras.idCarreras = '" + carreraSeleccionado.getIdCarreras() + "' ) "
                    + " order by o.idNiveles.secuencia ");
            List<CarrerasMaterias> adicionalesEncontradas = adm.query("Select o from CarrerasMaterias as o "
                    + " where o.idCarreras.idCarreras = '" + carreraSeleccionado.getIdCarreras() + "'  "
                    + "   order by o.idNiveles.secuencia ");

            listaMateriasAdicionales = new ArrayList<SelectItem>();
            for (Iterator<CarrerasMaterias> it = adicionalesEncontradas.iterator(); it.hasNext();) {
                CarrerasMaterias carrerasMaterias = it.next();
                listaMateriasAdicionales.add(new SelectItem(carrerasMaterias, carrerasMaterias.getIdNiveles().getNombre() + " - " + carrerasMaterias.getIdMaterias().getNombre()));

            }

            adicionales = adm.query("SELECT o FROM SecuenciaDeMateriasAdicionales AS o "
                    + " WHERE o.idCarrerasMaterias.idCarreras.idCarreras = '" + carreraSeleccionado.getIdCarreras() + "'  ");


            List<SecuenciaDeMaterias> materiasSecuenciales = adm.query("Select o from SecuenciaDeMaterias as o "
                    + "where o.idCarrerasMaterias.idCarreras.idCarreras = '" + carreraSeleccionado.getIdCarreras() + "' "
                    + "order by o.fila, o.orden ");
            if (materiasSecuenciales.size() > 0) {
                for (Iterator<SecuenciaDeMaterias> it = materiasSecuenciales.iterator(); it.hasNext();) {
                    SecuenciaDeMaterias cM = it.next();
                    cM.setSecuenciaDeMateriasAdicionalesList(new ArrayList<SecuenciaDeMateriasAdicionales>());
                    anadidasArray[cM.getFila()][cM.getOrden()] = cM.getIdCarrerasMaterias();
                }
                //LLENO LOS VACIOS
                for (int i = 0; i < 30; i++) {
                    for (int j = 0; j < 12; j++) {
                        try {
                            CarrerasMaterias tmp = anadidasArray[i][j];
                            if (tmp.getIdCarrerasMaterias().equals(null)) {
                                CarrerasMaterias car = new CarrerasMaterias();
                                Materias mat = new Materias();
                                mat.setNombre("");
                                car.setIdMaterias(mat);
                                car.setIdNiveles(new Niveles());
                                car.setIdCarreras(new Carreras());
                                car.setSecuenciaDeMateriasAdicionalesList(new ArrayList<SecuenciaDeMateriasAdicionales>());
                                anadidasArray[i][j] = car;
                            }


                        } catch (Exception e) {
                            CarrerasMaterias car = new CarrerasMaterias();
                            Materias mat = new Materias();
                            mat.setNombre("");
                            car.setIdMaterias(mat);
                            car.setIdNiveles(new Niveles());
                            car.setIdCarreras(new Carreras());
                            car.setSecuenciaDeMateriasAdicionalesList(new ArrayList<SecuenciaDeMateriasAdicionales>());
                            anadidasArray[i][j] = car;
                        }




                    }

                }

            } else {
                llenarArreglo();
            }

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String seleccionar(Matriculas obj) {
        object = obj;
        carreraSeleccionado = object.getIdCarreras();
        categoriaSeleccionado = object.getIdEstudiantes().getIdCategoriasSociales();
        buscarMateriasMatricula(object);
        if (object.getEstadoMat().equals("I") && (object.getPagadainscripcion() == null || object.getPagadainscripcion() == false)) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El estudiante Inscrito no ha cancelado valores por INSCRIPCION...!"));
        }
        estudiante = (Estudiantes) adm.buscarClave(obj.getIdEstudiantes().getIdEstudiantes(), Estudiantes.class);

        return null;
    }
    List<Matriculas> matriculasListado = new ArrayList<Matriculas>();

    protected void buscarMatricula(Estudiantes estudiante) {
        matriculasListado = adm.query("Select o from Matriculas as o "
                + " where o.idEstudiantes.idEstudiantes = '" + estudiante.getIdEstudiantes() + "' "
                + " and o.idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "'");
        if (matriculasListado.size() > 1) {
            return;
        } else {
            if (matriculasListado.size() > 0) {
                object = matriculasListado.get(0);
                carreraSeleccionado = object.getIdCarreras();
                categoriaSeleccionado = object.getIdEstudiantes().getIdCategoriasSociales();
                buscarMateriasMatricula(object);
                if (object.getEstadoMat().equals("I") && (object.getPagadainscripcion() == null || object.getPagadainscripcion() == false)) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El estudiante Inscrito no ha cancelado valores por INSCRIPCION...!"));
                }

            } else {

                FacesContext context = FacesContext.getCurrentInstance();
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El estudiante no se Encuentra Inscrito o Matriculado...!"));
                carreraSeleccionado = new Carreras(0);
                object = new Matriculas(0);
                //matriculasListado.add(object); 
                buscarMateriasMatricula(object);

            }
        }
    }

    public void handleSelect(SelectEvent event) {
        estudiante = (Estudiantes) adm.buscarClave(((Estudiantes) event.getObject()).getIdEstudiantes(), Estudiantes.class);
        buscarMatricula(estudiante);
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

    public void handleFileUploadLibreta(FileUploadEvent event) {
        try {

            libreta = event.getFile().getContents();
            libretaFormato = event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf(".") + 1);
            libretaNombre = event.getFile().getFileName();
            arLibreta.setNombre(libretaNombre);
        } catch (Exception ex) {
            Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleFileUploadTitulo(FileUploadEvent event) {
        try {

            titulo = event.getFile().getContents();
            tituloFormato = event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf(".") + 1);
            tituloNombre = event.getFile().getFileName();
            arTitulo.setNombre(tituloNombre);
        } catch (Exception ex) {
            Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleFileUploadCedula(FileUploadEvent event) {
        try {

            cedula = event.getFile().getContents();
            cedulaFormato = event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf(".") + 1);
            cedulaNombre = event.getFile().getFileName();
            arCedula.setNombre(cedulaNombre);
        } catch (Exception ex) {
            Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, ax);

            }



        } catch (Exception ex) {
            Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, e);

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
            java.util.logging.Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, e);

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
            java.util.logging.Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }

    public String copiarPadre() {
        pariente1.setNombres(pariente2.getApellidos() + " " + pariente2.getNombres());
        pariente1.setDireccion(pariente2.getDireccion());
        pariente1.setTelefonoTrabajo(pariente2.getTelefonoTrabajo());
        pariente1.setIdentificacion(pariente2.getIdentificacion());
        return "";
    }

    public String copiarMadre() {
        pariente1.setNombres(pariente3.getApellidos() + " " + pariente3.getNombres());
        pariente1.setDireccion(pariente3.getDireccion());
        pariente1.setTelefonoTrabajo(pariente3.getTelefonoTrabajo());
        pariente1.setIdentificacion(pariente3.getIdentificacion());
        return "";
    }

    public String copiarEstudiante() {
        pariente1.setNombres(estudiante.getApellidoPaterno() + " " + estudiante.getApellidoMaterno() + " " + estudiante.getNombre());
        pariente1.setDireccion(estudiante.getDireccion());
        pariente1.setIdentificacion(estudiante.getIdEstudiantes());
        pariente1.setTelefonoTrabajo(estudiante.getTelefono());
        return "";
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
            java.util.logging.Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, e);

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
                estudiante.setIdCanton(new Canton());
                estudiante.getIdCanton().setIdProvincia(new Provincia());
                estudiante.getIdCanton().getIdProvincia().setIdPais(new Pais());
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
                estudiante.setIdCanton(new Canton());
                estudiante.getIdCanton().setIdProvincia(new Provincia());
                estudiante.getIdCanton().getIdProvincia().setIdPais(new Pais());
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
                estudiante.setIdCanton(new Canton());
                estudiante.getIdCanton().setIdProvincia(new Provincia());
                estudiante.getIdCanton().getIdProvincia().setIdPais(new Pais());
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
//         Map<String,Object> options = new HashMap<String, Object>();   
//        options.put("modal", true);  
//        options.put("draggable", false);  
//        options.put("resizable", false);  
//        options.put("contentHeight", 320);  
//        RequestContext.getCurrentInstance().("reporteView", options, null); 
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
            map.put("usuario", ""+((Empleados) context.getExternalContext().getSessionMap().get("user")).getApellidoPaterno()+" "+((Empleados) context.getExternalContext().getSessionMap().get("user")).getNombre());
            map.put("fecha", adm.Date());

            BigDecimal valorConvOtra = new BigDecimal(0);
            BigDecimal valorConvReingreso = new BigDecimal(0);
            BigDecimal valorConvMisma = new BigDecimal(0);
            List<Parametros> parConvOtraList = adm.query("Select o from Parametros as o where o.variable = 'CONVOTRA' ");
            if (parConvOtraList.size() > 0) {
                valorConvOtra = parConvOtraList.get(0).getVNumerico();
            }

            parConvOtraList = adm.query("Select o from Parametros as o where o.variable = 'CONVREINGRESO' ");
            if (parConvOtraList.size() > 0) {
                valorConvReingreso = parConvOtraList.get(0).getVNumerico();
            }

            parConvOtraList = adm.query("Select o from Parametros as o where o.variable = 'CONVMISMA' ");
            if (parConvOtraList.size() > 0) {
                valorConvMisma = parConvOtraList.get(0).getVNumerico();
            }

            List<MateriasMatricula> matri = adm.query("Select o from MateriasMatricula as o "
                    + " where o.idMatriculas.idMatriculas = " + object.getIdMatriculas() + " ");
            for (Iterator<MateriasMatricula> it = matri.iterator(); it.hasNext();) {
                MateriasMatricula materiasMatricula1 = it.next();
                List<CarrerasMaterias> care = adm.query("Select o from CarrerasMaterias as o where "
                        + "o.idMaterias.idMaterias = '" + materiasMatricula1.getIdMaterias().getIdMaterias() + "' "
                        + " and o.idCarreras.idCarreras = '" + materiasMatricula1.getIdMatriculas().getIdCarreras().getIdCarreras() + "' ");
                materiasMatricula1.setNoCreditos(care.get(0).getNumeroCreditos());
                System.out.println("" + care.get(0).getIdMaterias().getCredito());
                //obj[4] = new BigDecimal(carM.getNumeroCreditos()).multiply(carM.getIdMaterias().getCredito());
                try {
                    if (materiasMatricula1.getConvalidado()) {
                        materiasMatricula1.setValorCredito(materiasMatricula1.getValor().equals("R") ? valorConvReingreso : materiasMatricula1.getValor().equals("M") ? valorConvMisma : materiasMatricula1.getValor().equals("O") ? valorConvOtra : new BigDecimal(0));
                        materiasMatricula1.setValorCreditoTotal(materiasMatricula1.getValor().equals("R") ? valorConvReingreso : materiasMatricula1.getValor().equals("M") ? valorConvMisma : materiasMatricula1.getValor().equals("O") ? valorConvOtra : new BigDecimal(0));
                    }else if (materiasMatricula1.getIdMaterias().getEspecial()) {
                        materiasMatricula1.setValorCredito(materiasMatricula1.getIdMaterias().getCredito());
                        materiasMatricula1.setValorCreditoTotal(materiasMatricula1.getIdMaterias().getCredito().multiply(new BigDecimal(care.get(0).getNumeroCreditos())));
                    }else{
                        materiasMatricula1.setValorCredito(materiasMatricula1.getIdMatriculas().getIdEstudiantes().getIdCategoriasSociales().getValorCredito());
                        materiasMatricula1.setValorCreditoTotal(materiasMatricula1.getIdMatriculas().getIdEstudiantes().getIdCategoriasSociales().getValorCredito().multiply(new BigDecimal(care.get(0).getNumeroCreditos())));
                    }
                } catch (Exception e) {
                    if (materiasMatricula1.getIdMaterias().getEspecial()) {
                        materiasMatricula1.setValorCredito(materiasMatricula1.getIdMaterias().getCredito());
                        materiasMatricula1.setValorCreditoTotal(materiasMatricula1.getIdMaterias().getCredito().multiply(new BigDecimal(care.get(0).getNumeroCreditos())));
                    }else{
                        materiasMatricula1.setValorCredito(materiasMatricula1.getIdMatriculas().getIdEstudiantes().getIdCategoriasSociales().getValorCredito());
                        materiasMatricula1.setValorCreditoTotal(materiasMatricula1.getIdMatriculas().getIdEstudiantes().getIdCategoriasSociales().getValorCredito().multiply(new BigDecimal(care.get(0).getNumeroCreditos())));
                    }
                    
                }

                /*
                 obj[3] = actualMatricula.getIdEstudiantes().getIdCategoriasSociales().getValorCredito();
                 obj[4] = actualMatricula.getIdEstudiantes().getIdCategoriasSociales().getValorCredito().multiply(new BigDecimal(creditos));
                 */
                materiasMatricula1.setNoHoras(care.get(0).getNumeroHoras());
            }
//            estu.add(estudiante);
//            String query = "SELECT est.id_estudiantes cedula, est.apellido_paterno, est.apellido_materno, "
//                    + " est.nombre, mate.nombre materia, carma.numero_creditos, mat.numero, car.nombre carrera, per.fecha_inicio, per.fecha_fin, niv.nombre nivel     "
//                     + " FROM materias_matricula mama, matriculas mat, estudiantes est, materias mate, carreras_materias carma, carreras car, periodos per, niveles niv "
//                     + " WHERE mama.id_matriculas = "+object.getIdMatriculas()+" AND mama.id_matriculas = mat.id_matriculas  AND mate.id_materias = mama.id_materias  "
//                     + " AND est.id_estudiantes = mat.id_estudiantes  AND carma.id_materias = mate.id_materias AND carma.id_carreras = mat.id_carreras "
//                     + " AND car.id_carreras = mat.id_carreras AND mat.id_periodos = per.id_periodos "
//                    + " AND niv.id_niveles = carma.id_niveles  order by niv.secuencia ";

            ReporteActaMatricula ds = new ReporteActaMatricula(matri);
            ExportarReportesSource ex = new ExportarReportesSource(ds, "creditosMatricula", map);
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
    private StreamedContent fileLibreta;

    public StreamedContent getFileLibreta() {
        try {
            InputStream input = new ByteArrayInputStream(libreta);
            fileLibreta = new DefaultStreamedContent(input, "application/pdf", "libretaMilitar.pdf");
            return fileLibreta;
        } catch (Exception ex) {
            Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private StreamedContent fileTitulo;

    public StreamedContent getFileTitulo() {
        try {
            InputStream input = new ByteArrayInputStream(titulo);
            fileTitulo = new DefaultStreamedContent(input, "application/pdf", "tituloBachiller.pdf");
            return fileTitulo;
        } catch (Exception ex) {
            Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private StreamedContent fileCedula;

    public StreamedContent getFileCedula() {
        try {
            InputStream input = new ByteArrayInputStream(cedula);
            fileCedula = new DefaultStreamedContent(input, "application/pdf", "cedula.pdf");
            return fileCedula;
        } catch (Exception ex) {
            Logger.getLogger(MatriculasBean2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

    public List<Matriculas> getMatriculasListado() {
        return matriculasListado;
    }

    public void setMatriculasListado(List<Matriculas> matriculasListado) {
        this.matriculasListado = matriculasListado;
    }
}
