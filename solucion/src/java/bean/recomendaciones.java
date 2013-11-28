package bean;

import bsh.EvalError;
import bsh.Interpreter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.*;

public class recomendaciones extends Rows {

    public recomendaciones() {
    }

    public Boolean regresaVariableParametrosLogico(String variable, List<ParametrosGlobales> textos) {
        for (Iterator<ParametrosGlobales> it = textos.iterator(); it.hasNext();) {
            ParametrosGlobales textos1 = it.next();
            if (textos1.getVariable().equals(variable)) {
                return textos1.getBvalor();
            }
        }
        return false;
    }

    public void addPerfil(Cursos curso, Sistemacalificacion sistema) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' AND o.variable = 'RECOMENDACION' ");

        boolean recomendacion = false;
        if (parametrosGlobales.size() > 0) {
            recomendacion = parametrosGlobales.get(0).getBvalor();
        } else {
            ParametrosGlobales p = new ParametrosGlobales(adm.getNuevaClave("ParametrosGlobales", "codigo"));
            p.setDescripcion("SELECCIONAN AL INGRESAR RECOMENDACIONES");
            p.setVariable("RECOMENDACION");
            p.setPeriodo(periodo);
            p.setBvalor(false);
            p.setTipo("L");
            adm.guardar(p);
            //recomendacion
        }

        Listbox combo = new Listbox();
        getChildren().clear();
        Label label3 = null;

        Textbox texto = new Textbox();
        Listitem item = new Listitem("USUALMENTE");


        List<Matriculas> matriculas = adm.query("Select o from Matriculas as o "
                + "where o.curso.codigocur = '" + curso.getCodigocur() + "' "
                + "and   (o.estado = 'Matriculado' or o.estado  = 'Recibir Pase'  or o.estado  = 'Emitir Pase'  or o.estado  = 'Retirado' ) order by o.estudiante.apellido, o.estudiante.nombre ");
        Row row = new Row();
        try {
            for (Iterator<Matriculas> it = matriculas.iterator(); it.hasNext();) {
                Matriculas matriculas1 = it.next();
                row = new Row();
                String q = "SELECT  o from Recomendaciones as o where o.matricula.codigomat = '" + matriculas1.getCodigomat() + "' "
                        + " AND o.sistema.codigosis = '" + sistema.getCodigosis() + "' ";
                List<Recomendaciones> recom = adm.query(q);

                texto = new Textbox();
                texto.setCols(70);
                texto.setRows(2);

                //combo.setWidth("300px");
                if (recom.size() <= 0) {
                    label3 = new Label("" + matriculas1.getCodigomat());
                    row.appendChild(label3);
                    label3 = new Label("" + matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                    row.appendChild(label3);
                    row.appendChild(texto);
                } else {
                    label3 = new Label("" + matriculas1.getCodigomat());
                    row.appendChild(label3);
                    label3 = new Label("" + matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                    row.appendChild(label3);
                    texto.setValue(recom.get(0).getRecomendacion() + "");
                    row.appendChild(texto);
                }


                row.setParent(this);

            }


        } catch (Exception ex) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void addPerfil(Cursos curso, Sistemacalificacion sistema, MateriaProfesor mateP) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();

        getChildren().clear();
        Label label3 = null;
        Listitem item = new Listitem("");
        Textbox texto = new Textbox();
        Textbox plan = new Textbox();
        //Listitem combo = new Listitem("USUALMENTE");
        Listbox combo = new Listbox();
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' AND o.variable = 'RECOMENDACION' ");
        List<Item> equivalencias = adm.query("Select o from Item as o ");
        try {
            Item i = new Item(0);
            i.setRecomendacion("[SELECCIONE]");
            i.setTipo(".");
            adm.guardar(i);
        } catch (Exception e) {
            System.out.println("en crear item 0" + e);
        }
        boolean recomendacion = false;
        if (parametrosGlobales.size() > 0) {
            recomendacion = parametrosGlobales.get(0).getBvalor();
        } else {


            ParametrosGlobales p = new ParametrosGlobales(adm.getNuevaClave("ParametrosGlobales", "codigopar"));
            p.setDescripcion("SELECCIONAN AL INGRESAR RECOMENDACIONES");
            p.setVariable("RECOMENDACION");
            p.setPeriodo(periodo);
            p.setBvalor(false);
            p.setTipo("L");
            adm.guardar(p);
            //recomendacion
        }

        List<Matriculas> matriculas = adm.query("Select o from Matriculas as o "
                + "where o.curso.codigocur = '" + curso.getCodigocur() + "'"
                + "and    (o.estado = 'Matriculado' or o.estado  = 'Recibir Pase'  or o.estado  = 'Emitir Pase'  or o.estado  = 'Retirado' )  order by o.estudiante.apellido, o.estudiante.nombre ");
        Row row = new Row();
        try {
            for (Iterator<Matriculas> it = matriculas.iterator(); it.hasNext();) {
                Matriculas matriculas1 = it.next();
                row = new Row();
                String q = "SELECT  o from Recomendaciones as o where o.matricula.codigomat = '" + matriculas1.getCodigomat() + "' "
                        + " AND o.sistema.codigosis = '" + sistema.getCodigosis() + "' "
                        + " and o.materia.codigo = '" + mateP.getMateria().getCodigo() + "' ";
                List<Recomendaciones> recom = adm.query(q);

                if (recomendacion == false) {
                    texto = new Textbox();
                    plan = new Textbox();
                    texto.setCols(55);
                    texto.setRows(2);
                    plan.setCols(55);
                    plan.setRows(2);
                    //combo.setWidth("300px");
                    if (recom.size() <= 0) {
                        label3 = new Label("" + matriculas1.getCodigomat());
                        row.appendChild(label3);
                        label3 = new Label("" + matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                        row.appendChild(label3);
                        row.appendChild(texto);
                        row.appendChild(plan);
                    } else {
                        label3 = new Label("" + matriculas1.getCodigomat());
                        row.appendChild(label3);
                        label3 = new Label("" + matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                        row.appendChild(label3);
                        texto.setValue(recom.get(0).getRecomendacion() + "");
                        row.appendChild(texto);
                        plan.setValue(recom.get(0).getPlan() + "");
                        row.appendChild(plan);
                    }
                } else {
                    if (recom.size() <= 0) {
                        label3 = new Label("" + matriculas1.getCodigomat());
                        row.appendChild(label3);
                        label3 = new Label("" + matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                        row.appendChild(label3);
                        combo = new Listbox();
                        combo.setMold("select");
                        combo.setWidth("550px");
                        combo.setRows(1);
                        item = new Listitem("[SELECCIONE]");
                        item.setValue(new Item(0));
                        combo.appendChild(item);
                        combo.setSelectedItem(item);
                        combo.setStyle("font-size:11px;width:230px");
                        for (Iterator<Item> it2 = equivalencias.iterator(); it2.hasNext();) {
                            Item eq = it2.next();
                            item = new Listitem("" + eq.getRecomendacion());
                            item.setValue(eq);
                            if (eq.getTipo().equals("R")) {
                                combo.appendChild(item);
                            }

                        }

                        row.appendChild(combo);

                        combo = new Listbox();
                        combo.setMold("select");
                        combo.setWidth("550px");
                        combo.setRows(1);
                        item = new Listitem("[SELECCIONE]");
                        item.setValue(new Item(0));
                        combo.appendChild(item);
                        combo.setSelectedItem(item);
                        combo.setStyle("font-size:11px;width:230px");
                        for (Iterator<Item> it2 = equivalencias.iterator(); it2.hasNext();) {
                            Item eq = it2.next();
                            item = new Listitem("" + eq.getRecomendacion());
                            item.setValue(eq);
                            if (eq.getTipo().equals("P")) {
                                combo.appendChild(item);
                            }

                        }

                        row.appendChild(combo);
//                        row.appendChild(combo);



                    } else {
                        label3 = new Label("" + matriculas1.getCodigomat());
                        row.appendChild(label3);
                        label3 = new Label("" + matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                        row.appendChild(label3);

                        combo = new Listbox();
                        combo.setMold("select");
                        combo.setWidth("550px");
                        combo.setRows(1);
                        combo.setStyle("font-size:11px;width:230px");
                        for (Iterator<Item> it2 = equivalencias.iterator(); it2.hasNext();) {
                            Item eq = it2.next();
                            item = new Listitem("" + eq.getRecomendacion());
                            item.setValue(eq);
                            combo.appendChild(item);

                        }

                        item = new Listitem((recom.get(0).getItemRecomendacion().getRecomendacion()) + "");
                        item.setValue(recom.get(0).getItemRecomendacion());
                        combo.appendChild(item);
                        combo.setSelectedItem(item);
                        row.appendChild(combo);




                        combo = new Listbox();
                        combo.setMold("select");
                        combo.setWidth("550px");
                        combo.setRows(1);
                        combo.setStyle("font-size:11px;width:230px");
                        for (Iterator<Item> it2 = equivalencias.iterator(); it2.hasNext();) {
                            Item eq = it2.next();
                            item = new Listitem("" + eq.getRecomendacion());
                            item.setValue(eq);
                            combo.appendChild(item);

                        }
                        item = new Listitem((recom.get(0).getItemPlan().getRecomendacion()) + "");
                        item.setValue(recom.get(0).getItemPlan());
                        combo.appendChild(item);
                        combo.setSelectedItem(item);
                        row.appendChild(combo);

                    }


                }

                row.setParent(this);

            }


        } catch (Exception ex) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void guardarPerfil(List col, Cursos curso, Sistemacalificacion sistema, MateriaProfesor mateP) {
        System.out.println("INICIO EN: " + new Date());
        Interpreter inter = new Interpreter();
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");

        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' AND o.variable = 'RECOMENDACION' ");
//        List<Item> equivalencias = adm.query("Select o from Item as o ");

        boolean recomendacion = false;
        if (parametrosGlobales.size() > 0) {
            recomendacion = parametrosGlobales.get(0).getBvalor();
        }
        String del = "Delete from Recomendaciones "
                + "where matricula.curso.codigocur = '" + curso.getCodigocur() + "' "
                + "and sistema.codigosis = '" + sistema.getCodigosis() + "' and materia.codigo = '" + mateP.getMateria().getCodigo() + "' ";
        adm.ejecutaSql(del);
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                Recomendaciones nota = new Recomendaciones();

                List labels = object.getChildren();
                nota.setMatricula(new Matriculas(new Integer(((Label) labels.get(0)).getValue())));
                nota.setSistema(sistema);
                nota.setMateria(mateP.getMateria());
//                inter.set("nota", nota);
                if (recomendacion == false) {
                    for (int j = 2; j < labels.size(); j++) {
                        Textbox object1 = (Textbox) labels.get(j);
                        String vaNota = object1.getValue();
                        if (j == 2) {
                            nota.setRecomendacion(vaNota);
                        } else {
                            nota.setPlan(vaNota);
                        }
                    }
                } else {
                    for (int j = 2; j < labels.size(); j++) {
                        Listbox object1 = (Listbox) labels.get(j);
                        Item vaNota = (Item) object1.getSelectedItem().getValue();
                        if (j == 2) {
                            nota.setItemRecomendacion(vaNota);
                        } else {
                            nota.setItemPlan(vaNota);
                        }
                    }
                }

                adm.guardar(nota);
            } catch (Exception ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("FINALIZO EN: " + new Date());


    }
}
