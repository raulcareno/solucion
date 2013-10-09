/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes.bean;

import bean.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import jcinform.persistencia.Canton;
import jcinform.persistencia.Carreras;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Institucion;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Pais;
import jcinform.persistencia.Periodos;
import jcinform.persistencia.Provincia;
import jcinform.procesos.Administrador;
import reportes.ExportarReportes;
import reportes.ExportarReportesCon;
import reportes.FichaMatricula;

import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class ReportesBean implements Serializable {

    Administrador adm;
    Permisos permisos;
    Auditar aud = new Auditar();
    private Matriculas selectedMatricula;
    private Matriculas[] selectedMatriculas;
    private List<Matriculas> mediumMatriculasModel;
    Periodos per;
    Institucion inst;

    public ReportesBean() {
        //super();
        FacesContext context = FacesContext.getCurrentInstance();
        per = (Periodos) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("periodo");
        inst = (Institucion) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("institucion");
        if (adm == null) {
            adm = new Administrador();
        }
        if (permisos == null) {
            permisos = new Permisos();
        }
        if (!permisos.verificarPermisoReporte("Institucion", "ingresar_institucion", "ingresar", true, "PARAMETROS")) {
            try {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/universidad/noPuedeIngresar.jspx");
            } //selectedInstitucion = new Institucion();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(InstitucionBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(InstitucionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //selectedMatricula = new Matriculas();
        //selectedInstitucion = new Institucion();

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
    public Boolean todos;

    public void seleccionarTodos() {
        for (Iterator<Matriculas> it = mediumMatriculasModel.iterator(); it.hasNext();) {
            Matriculas matriculas = it.next();
            matriculas.setConfirmada(todos);
        }
    }

    public void buscarMatriculas() {

        try {
            try {
                mediumMatriculasModel = adm.query("Select o from Matriculas as o "
                        + "where o.idCarreras.idCarreras = '" + carreraSeleccionado.getIdCarreras() + "' "
                        + " and o.idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "' order by o.idEstudiantes.apellidoPaterno ");
                for (Iterator<Matriculas> it = mediumMatriculasModel.iterator(); it.hasNext();) {
                    Matriculas matriculas = it.next();
                    matriculas.setConfirmada(false);
                }
            } catch (Exception e) {
                System.out.println("NO SE HAN ENCONTRADO MATRICULADOS");
            }
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CantonBean.class.getName()).log(Level.SEVERE, null, e);
        }


    }

    public List<SelectItem> getSelectedItemCarreras() {
        try {
            List<Carreras> carrerasListado = new ArrayList<Carreras>();
            List<SelectItem> items = new ArrayList<SelectItem>();

            carrerasListado = adm.query("Select o from Carreras as o "
                    + " order by o.nombre ");
            if (carrerasListado.size() > 0) {
                Carreras objSel = new Carreras(0);
                items.add(new SelectItem(objSel, "Seleccione..."));
                for (Carreras obj : carrerasListado) {
                    items.add(new SelectItem(obj, obj.getNombre()));
                }
            } else {
                Carreras obj = new Carreras(0);
                items.add(new SelectItem(obj, "NO EXISTEN CARRERAS"));
            }

            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(MatriculasBean.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }

    public String verReporte(String tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            List<Estudiantes> estu = new ArrayList<Estudiantes>();
            Map map = new HashMap();
            if (tipoReporte.equals("")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione un Reporte...!", "Seleccione un Reporte...!"));
                return null;
            } else if (tipoReporte.equals("FM")) { //Ficha de Matricula" itemValue="FM" />
                if (mediumMatriculasModel.size() <= 0) {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione un Estudiante...!", "Seleccione un Estudiante...!"));
                    return null;
                }
                String codigoMatriculas = "";
                for (int i = 0; i < mediumMatriculasModel.size(); i++) {
                    if (mediumMatriculasModel.get(i).getConfirmada()) {
                        codigoMatriculas += mediumMatriculasModel.get(i).getIdMatriculas()+",";
                    }
                }
                if(codigoMatriculas.length()>0){
                    codigoMatriculas = codigoMatriculas.substring(0,codigoMatriculas.length()-1);
                }else{
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Seleccione al Menos un estudiantes...!"));
                    return null;
                }
                map.put("tituloReporte", "FICHA DE MATRICULA");
                map.put("nombre", inst.getNombre());
                carreraSeleccionado = new Carreras(0);
                tipoReporte = "";
                String query = "SELECT MAT.*,EST.*,CAR.NOMBRE CARRERA FROM matriculas mat, estudiantes est, carreras car  "
                        + "WHERE mat.id_estudiantes = est.id_estudiantes  AND mat.id_matriculas in ("+codigoMatriculas+") "
                        + "AND car.id_carreras = mat.id_carreras AND mat.id_periodos = '" + per.getIdPeriodos() + "' ";
                ExportarReportesCon ex = new ExportarReportesCon(query, "fichaMatricula", map);
                if (tipo.equals("PDF")) {
                    ex.PDF();
                } else if (tipo.equals("DOCX")) {
                    ex.DOCX();
                } else if (tipo.equals("XLS")) {
                    ex.XLSX();
                } else if (tipo.equals("PRINT")) {
                    ex.PRINT();
                }
            } else if (tipoReporte.equals("CM")) { //Ficha de Matricula" itemValue="FM" />
                if (mediumMatriculasModel.size() <= 0) {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione un Estudiante...!", "Seleccione un Estudiante...!"));
                    return null;
                }
                String codigoMatriculas = "";
                for (int i = 0; i < mediumMatriculasModel.size(); i++) {
                    if (mediumMatriculasModel.get(i).getConfirmada()) {
                        codigoMatriculas += mediumMatriculasModel.get(i).getIdMatriculas()+",";
                    }
                }
                if(codigoMatriculas.length()>0){
                    codigoMatriculas = codigoMatriculas.substring(0,codigoMatriculas.length()-1);
                }else{
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Seleccione al Menos un estudiantes...!"));
                    return null;
                }
                map.put("tituloReporte", "FICHA DE MATRICULA");
                map.put("nombre", inst.getNombre());
                carreraSeleccionado = new Carreras(0);
                tipoReporte = "";
                String query = "SELECT MAT.*,EST.*,CAR.NOMBRE CARRERA FROM matriculas mat, estudiantes est, carreras car  "
                        + "WHERE mat.id_estudiantes = est.id_estudiantes  AND mat.id_matriculas in ("+codigoMatriculas+") "
                        + "AND car.id_carreras = mat.id_carreras AND mat.id_periodos = '" + per.getIdPeriodos() + "' ";
                ExportarReportesCon ex = new ExportarReportesCon(query, "certificadoMatricula", map);
                if (tipo.equals("PDF")) {
                    ex.PDF();
                } else if (tipo.equals("DOCX")) {
                    ex.DOCX();
                } else if (tipo.equals("XLS")) {
                    ex.XLSX();
                } else if (tipo.equals("PRINT")) {
                    ex.PRINT();
                }
            }

        } catch (Exception ex1) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, ex1.getMessage(), ex1.getMessage()));
            java.util.logging.Logger.getLogger(FichaMatricula.class.getName()).log(Level.SEVERE, null, ex1);
        }

        return null;

    }
    protected String tipoReporte;

    /**
     * Get the value of tipoReporte
     *
     * @return the value of tipoReporte
     */
    public String getTipoReporte() {
        return tipoReporte;
    }

    /**
     * Set the value of tipoReporte
     *
     * @param tipoReporte new value of tipoReporte
     */
    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public Matriculas getSelectedMatricula() {
        return selectedMatricula;
    }

    public void setSelectedMatricula(Matriculas selectedMatricula) {
        this.selectedMatricula = selectedMatricula;
    }

    public Matriculas[] getSelectedMatriculas() {
        return selectedMatriculas;
    }

    public void setSelectedMatriculas(Matriculas[] selectedMatriculas) {
        this.selectedMatriculas = selectedMatriculas;
    }

    public List<Matriculas> getMediumMatriculasModel() {
        return mediumMatriculasModel;
    }

    public void setMediumMatriculasModel(List<Matriculas> mediumMatriculasModel) {
        this.mediumMatriculasModel = mediumMatriculasModel;
    }
    private Carreras carreraSeleccionado;

    /**
     * Get the value of carreraSeleccionado
     *
     * @return the value of carreraSeleccionado
     */
    public Carreras getCarreraSeleccionado() {
        return carreraSeleccionado;
    }

    /**
     * Set the value of carreraSeleccionado
     *
     * @param carreraSeleccionado new value of carreraSeleccionado
     */
    public void setCarreraSeleccionado(Carreras carreraSeleccionado) {
        this.carreraSeleccionado = carreraSeleccionado;
    }

    public Boolean getTodos() {
        return todos;
    }

    public void setTodos(Boolean todos) {
        this.todos = todos;
    }
}
