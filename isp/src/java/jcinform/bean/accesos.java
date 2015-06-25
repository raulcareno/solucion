package jcinform.bean;


import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.conexion.Administrador;
import jcinform.persistencia.*;


import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

public class accesos extends Rows {
//ArrayList listad = new ArrayList();

    public accesos() {
Textbox a;
//a.setC
    
    }

    public void addRow(Perfil p) {
Session ses = Sessions.getCurrent();
Empleadossucursal sucursalEmp = (Empleadossucursal) ses.getAttribute("sector");
         String complemento = "";
//        if (!tipo.equals("TODOS")) {
//            complemento = " AND grupo = '"+tipo+"'";
//        }
//        String complemento2 = "";
//        if (!tipo.equals("TODOS")) {
//            complemento2 = " and o.grupo = '"+tipo+"'";
//        }
        Administrador adm = new Administrador();
        String query = "SELECT * FROM accesos WHERE perfil =  '" + p.getCodigo() + "'   "+ complemento +"  "
                + " UNION SELECT * FROM accesos WHERE (perfil IS NULL  OR perfil = 0) "+ complemento +"  "
                + "AND modulo NOT IN "
                + " (SELECT modulo FROM accesos WHERE perfil =  '" + p.getCodigo() + "'    )";
        System.out.println(""+query);
        List accesosList = adm.queryNativo(query, Accesos.class);
        for (Iterator it = accesosList.iterator(); it.hasNext();) {
            Accesos object = (Accesos) it.next();
            if (object.getPerfil() == null) {
                object.setCodigoacc(null);
            }
        }
        if (accesosList.size() > 0) {
        } else {

            accesosList = adm.query("Select o from Accesos as o where o.perfil is null ");
            for (Iterator it = accesosList.iterator(); it.hasNext();) {
                Accesos object = (Accesos) it.next();
                object.setCodigoacc(null);
            }
        }
        Checkbox label = null;
        Label label3 = null;

        getChildren().clear();
        Row row = new Row();

        for (Iterator itna = accesosList.iterator(); itna.hasNext();) {
            Accesos vec = (Accesos) itna.next();
            row = new Row();

            label3 = new Label();
            label3.setValue("" + vec.getCodigoacc());
            label3.setParent(row);

            label3 = new Label();
            label3.setValue("" + vec.getModulo());
            label3.setParent(row);

            label = new Checkbox();
            label.setChecked(vec.getIngresar());
            label.setParent(row);

            label = new Checkbox();
            label.setChecked(vec.getGuardar());
            label.setParent(row);

            label = new Checkbox();
            label.setChecked(vec.getActualizar());
            label.setParent(row);

            label = new Checkbox();
            label.setChecked(vec.getEliminar());
            label.setParent(row);

                   
             label3 = new Label();
            //label3.setValue("" + vec.getGrupo());
            label3.setValue(""+vec.getGrupo());
            label3.setStyle("width:10px");
            label3.setParent(row);

            row.setParent(this);

        }




    }
public void seleccionar(String tipo,Boolean seleccionar){
    List lis = getChildren();
    for (Iterator it = lis.iterator(); it.hasNext();) {
        Row object = (Row) it.next();
         List labels = object.getChildren();
         if(tipo.equals("ingresar"))
          ((Checkbox) labels.get(2)).setChecked(seleccionar);
         if(tipo.equals("agregar"))
          ((Checkbox) labels.get(3)).setChecked(seleccionar);
         if(tipo.equals("modificar"))
          ((Checkbox) labels.get(4)).setChecked(seleccionar);
         if(tipo.equals("eliminar"))
          ((Checkbox) labels.get(5)).setChecked(seleccionar);

    }

}
    @SuppressWarnings("static-access")
    public String guardar(List col, Perfil g) {
Session ses = Sessions.getCurrent();
Empleadossucursal sucursalEmp = (Empleadossucursal) ses.getAttribute("sector");
        Administrador adm = new Administrador();
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                Accesos nota = new Accesos();
                List labels = object.getChildren();
                String valorCodigo = ((Label) labels.get(0)).getValue();
                Integer codigo = null;
                try {
                    codigo = new Integer(valorCodigo);
                } catch (Exception e) {
                    codigo = null;
                }
                nota.setCodigoacc(codigo);
                nota.setModulo(((Label) labels.get(1)).getValue());
                nota.setIngresar(((Checkbox) labels.get(2)).isChecked());
                nota.setGuardar(((Checkbox) labels.get(3)).isChecked());
                nota.setActualizar(((Checkbox) labels.get(4)).isChecked());
                nota.setEliminar(((Checkbox) labels.get(5)).isChecked());
                nota.setGrupo(((Label) labels.get(6)).getValue());
                nota.setSucursal(sucursalEmp.getSucursal());
//                nota.setGrupo(((Label) labels.get(6)).getValue());
//                System.out.println(nota.getModulo()+"---"+nota.getCodigoacc());
                if (nota.getCodigoacc() != null ) {
                    nota.setPerfil(g);
                    adm.actualizar(nota);
                    
                } else {
                    nota.setPerfil(g);
                    nota.setCodigoacc(adm.getNuevaClave("Accesos", "codigoacc"));
                    adm.guardar(nota);
                    //return "ok";
                }
//                    adm.guardar(nota);

            } catch (Exception ex) {
                Logger.getLogger(accesos.class.getName()).log(Level.SEVERE, null, ex);
                return "false";
            }
        }
//            recalculoNotas(materia, curso);
//            System.out.println("FINALIZO EN: " + new Date());
//            return "ok";
//        } catch (EvalError ex) {
//            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
        //return "Error en:  " + ex;
//        }
        getChildren().clear();
        return "ok";

    }
}
