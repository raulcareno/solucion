package bean;


import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

public class accesos extends Rows {
//ArrayList listad = new ArrayList();

    public accesos() {
       
    }

    public void addRow(Global p) {

        Administrador adm = new Administrador();
        List accesosList = adm.queryNativo("SELECT * FROM accesos WHERE perfil =  '" + p.getCodigo() + "'  "
                + " UNION SELECT * FROM accesos WHERE (perfil IS NULL  OR perfil = 0)AND modulo NOT IN "
                + " (SELECT modulo FROM accesos WHERE perfil =  '" + p.getCodigo() + "'  ) ", Accesos.class);
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
            label3.setValue("" + vec.getGrupo());
            label3.setParent(row);

            row.setParent(this);

        }




    }

    @SuppressWarnings("static-access")
    public String guardar(List col, Global g) {

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
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
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
