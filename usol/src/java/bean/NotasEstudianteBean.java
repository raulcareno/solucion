package bean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import jcinform.persistencia.Accesos;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Institucion;
import jcinform.persistencia.Materias;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Periodos;
import jcinform.persistencia.RangosGpa;
import jcinform.persistencia.SistemaNotas;
import jcinform.procesos.Administrador;
import org.joda.time.DateMidnight;
import utilerias.NotasIngresar;
import utilerias.RecuperarBean;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class NotasEstudianteBean {

    public String usuario;
    public String clave;
    Administrador adm;
FacesContext context = FacesContext.getCurrentInstance();
    /**
     * Creates a new instance of LoginBean
     */
    public NotasEstudianteBean() {
        super();
        usuario = "";
        clave = "";
        adm = new Administrador();
         context = FacesContext.getCurrentInstance();
//        context.addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_INFO, "", ""));
 
         Matriculas mat = (Matriculas) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("matricula");
         if(mat !=null)
            buscarNotasEstudiante();

    }
    public Periodos periodoSeleccionado;

    public List<SelectItem> getSelectedItemPeriodos() {
        try {
            List<Periodos> divisionPoliticas = new ArrayList<Periodos>();
            List<SelectItem> items = new ArrayList<SelectItem>();

            divisionPoliticas = adm.query("Select o from Periodos as o order by o.fechaInicio ");
            if (divisionPoliticas.size() > 0) {
//                        Periodos objSel = new Periodos(0);
//                        items.add(new SelectItem(objSel, "SELECCIONE UN PERIODO"));
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM/yyyy");

                for (Periodos obj : divisionPoliticas) {
                    String fechaI = sdf.format(obj.getFechaInicio());
                    String fechaF = sdf.format(obj.getFechaFin());
                    items.add(new SelectItem(obj, fechaI + "-" + fechaF));
                }
                divisionPoliticas = null;
            } else {
                Periodos obj = new Periodos(0);
                items.add(new SelectItem(obj, "NO PUEDE INGRESAR NO EXISTEN PERIODOS"));
            }

            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(NotasEstudianteBean.class.getName()).log(Level.SEVERE, null, e);

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
    ArrayList listaNotas;
     List cabeceras = null;
  public String buscarNotasEstudiante() {
         Matriculas mat = (Matriculas) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("matricula");
         try {
          cabeceras = adm.query("Select o from SistemaNotas as o "
                  + " where o.idPeriodos.idPeriodos = '" + mat.getIdPeriodos().getIdPeriodos() + "' ");
      } catch (Exception e) {
      }
          
        boolean interno = true;
        listaNotas = new ArrayList();
        List<RangosGpa> rangos = adm.query("Select o from RangosGpa as o ");
        List<SistemaNotas> sistemas = adm.query("Select o from SistemaNotas as o "
                + "where o.idPeriodos.idPeriodos = '" + mat.getIdPeriodos().getIdPeriodos() + "' "
                + "order by o.idSistemaNotas ");
        SistemaNotas sisEstado = new SistemaNotas(-1);
        sisEstado.setAbreviatura("ESTADO");
        sisEstado.setEsgpa(false);
        sisEstado.setEsexamen(false);
        sisEstado.setEsnota(false);
        sisEstado.setEsasistencia(false);
        sisEstado.setSeimprime(false); 
        sisEstado.setNota("");
        sistemas.add(sisEstado); 
        String query = "";
        for (SistemaNotas notass : sistemas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        int tamanio = sistemas.size();
        String q = "SELECT matricula.estado_mat, matricula.id_matriculas, mm.id_materias, " + query + "  notas.estado   FROM  Materias_matricula mm  LEFT JOIN Matriculas matricula  "
                + "ON matricula.id_matriculas = mm.id_matriculas    "
                + " LEFT JOIN  Estudiantes estudiantes  ON matricula.id_estudiantes = estudiantes.id_estudiantes   "
                + " LEFT JOIN Notas notas ON mm.id_materias = notas.id_materias and  notas.id_matriculas = mm.id_matriculas      "
                + "  WHERE  matricula.estado_mat IN ('M','P','R') AND matricula.id_matriculas = '" + mat.getIdMatriculas() + "'     "
                + "   ORDER BY estudiantes.apellido_paterno, estudiantes.apellido_materno, estudiantes.nombre";
        System.out.println("" + q);
        List nativo = adm.queryNativo(q);
        Date fechaActual = adm.Date();
        DateMidnight actual = new DateMidnight(fechaActual);
        int xy = 0;
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Object[] vec = (Object[]) itna.next();
            int a = 0;
            int x = 0;
            ArrayList notaAnadir = new ArrayList();
            Boolean estadoNota = false;
            Double notaVerifica = 0.0;
            for (a = 0; a < vec.length; a++) {
                SistemaNotas tnota = sistemas.get(x);
                NotasIngresar n = new NotasIngresar();
                if (a == vec.length-1) {
//                    String object = (String) vec[a];
                    n.setNombre(aprobado(rangos, notaVerifica)); 
                    n.setTexto(true);
                    n.setNota(null);
                    if (n.getNombre().contains("A")) {
                        n.setNombre("APROBADO");
                        n.setColorEstado("blue");
                    }else{
                        n.setNombre("REPROBADO");
                        n.setColorEstado("red");
                    }
                    System.out.println("NOTAveri: "+notaVerifica);
                    n.setAncho(12);
                }else if (a == 2) {
                    Integer idMat = (Integer) vec[a];
                    Materias mater = (Materias) adm.buscarClave(idMat,Materias.class);
                    n.setNombre(mater.getNombre());
                    n.setTexto(true);
                    n.setNota(null);
                    n.setColorEstado("black");
                    if (estadoNota) {
                        n.setColorEstado("red");
                    }
                    n.setAncho(45);
                } else if (a == 1) {
                    Integer object = (Integer) vec[a];
                    n.setNombre(object + "");
                    n.setTexto(true);
                    n.setColorEstado("black");
                    n.setNota(null);
                    n.setAncho(2);
                } else if (a == 0) {
                    String object = (String) vec[a];

                    n.setTexto(true);
                    n.setNota(null);
                    if (object.equals("M") || object.equals("P")) {
                        n.setEstado(false);//DESHABILITO LA NOTA
                    } else {
                        estadoNota = true;
                        n.setEstado(true);//DESHABILITO LA NOTA
                        n.setNombre("");
                    }
                    n.setAncho(5);
                } else {

                        n.setEstado(true);//habilito la notaIngresada
                        n.setColor("white");
                    
                    if (estadoNota) {
                        n.setEstado(estadoNota);
                        n.setColor("#E9E7E2");

                    }

                    Double object = (Double) vec[a];
                    if (object == null) {
                        object = new Double(0.0);
                    }
                    n.setDesde(0.0);
                    n.setHasta(100d);
                    n.setNota(redondear(object.doubleValue(), 2));
                    n.setNombre("");
                    n.setAncho(10);
                    n.setTexto(false);
                    if(tnota.getEsnota()){
                        notaVerifica = n.getNota();
                    }
                    if (tnota.getEsgpa()) {
                        n.setNombre(gpa(rangos,n.getNota())+"");
                        n.setTexto(true);
                        n.setAncho(10); 
                        n.setColorEstado("black");
                        n.setNota(null);
                    }
                    if (tnota.getEsexamen()) {
                        n.setNombre(equivalencia(rangos,n.getNota())+"");
                        n.setTexto(true);
                        n.setNota(null);
                        n.setColorEstado("black");
                        n.setAncho(10);
                    }
                    
                    x++;
                }
                if (a > 0) {
                    n.setId(xy);
                    xy++;
                    notaAnadir.add(n);
                }
            }
            listaNotas.add(notaAnadir);
        }

        return null;
    }
   public Double redondear(Double numero, int decimales) {
        try {
            BigDecimal d = new BigDecimal(numero);
            d = d.setScale(decimales, RoundingMode.HALF_UP);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }
    /**
     * REGRESAR GPA
     */
    public Double gpa(List<RangosGpa> ra, Double valor) {
        for (Iterator<RangosGpa> it = ra.iterator(); it.hasNext();) {
            RangosGpa rangosGpa = it.next();
            if (valor.doubleValue() >= rangosGpa.getDesde() && valor.doubleValue() <= rangosGpa.getHasta()) {
                return rangosGpa.getGpa().doubleValue();
            }
        }
        return 0.0;
    }
    /**
     * REGRESAR EQUIVALENCIA
     */
    public String equivalencia(List<RangosGpa> ra, Double valor) {
        for (Iterator<RangosGpa> it = ra.iterator(); it.hasNext();) {
            RangosGpa rangosGpa = it.next();
            if (valor.doubleValue() >= rangosGpa.getDesde() && valor.doubleValue() <= rangosGpa.getHasta()) {
                return rangosGpa.getEquivalencia();
            }
        }
        return "";
    }

    public String aprobado(List<RangosGpa> ra, Double valor) {
        for (Iterator<RangosGpa> it = ra.iterator(); it.hasNext();) {
            RangosGpa rangosGpa = it.next();
            if (valor.doubleValue() >= rangosGpa.getDesde() && valor.doubleValue() <= rangosGpa.getHasta()) {
                return rangosGpa.getResultado();
            }
        }
        return "";
    }

    public String loginAction() {
        
        try {
            Estudiantes user = (Estudiantes) adm.ingresoSistemaE(usuario, clave);

            if (user == null) {
                //FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage("El nombre de Usuario o Contraseña están incorrectas...!"));
                context.addMessage(findComponent(context.getViewRoot(), "loginEstudiante").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre de Usuario o Contraseña están incorrectas...!", "El nombre de Usuario o Contraseña están incorrectas...!"));
                return null;
            } else {
                
                  List<Matriculas> matriculasListado = adm.query("Select o from Matriculas as o "
                + " where o.idEstudiantes.idEstudiantes = '" + user.getIdEstudiantes() + "' "
                + " and o.idPeriodos.activo = true ");
                  Matriculas object = new Matriculas();
                if (matriculasListado.size() > 0) {
                    object = matriculasListado.get(0);
                 
                }

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("matricula");
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("accesos");
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("matricula", object);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("periodo", periodoSeleccionado);
                
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("menu");
                FacesContext.getCurrentInstance().getExternalContext().redirect("indexEstudiante.jspx");


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


//            String url = "index";
//            System.out.println(url);
            //context.getExternalContext().dispatch(url);
            //String encodeURL = context.getExternalContext().encodeResourceURL(url);
            //context.getExternalContext().redirect(encodeURL);
        } catch (Exception e) {
            //log.error("loginAction() ERROR " + e);
        } finally {
            context.responseComplete();
        }
        return "indexEstudiante";
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

    public ArrayList getListaNotas() {
        return listaNotas;
    }

    public void setListaNotas(ArrayList listaNotas) {
        this.listaNotas = listaNotas;
    }

    public List getCabeceras() {
        return cabeceras;
    }

    public void setCabeceras(List cabeceras) {
        this.cabeceras = cabeceras;
    }
    
}
