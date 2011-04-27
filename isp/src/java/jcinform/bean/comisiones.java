package jcinform.bean;


import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.conexion.Administrador;
import jcinform.persistencia.*;


import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

public class comisiones extends Rows {
//ArrayList listad = new ArrayList();

    public comisiones() {
      
    }

    public void addRow(Empleados p) {
         String complemento = "";
          Administrador adm = new Administrador();
        String query = "SELECT o FROM Comisiones as o WHERE o.empleados.codigo  =  '" + p.getCodigo() + "'  ";
        List comisionesList = adm.query(query);
        Doublebox  porcen = null;
        Label nombrePlan = null;

        getChildren().clear();
        Row row = new Row();
        if(comisionesList.size()<=0){
              query = "SELECT o FROM Plan as o  ";
              List planesList = adm.query(query);
              for (Iterator it = planesList.iterator(); it.hasNext();) {
                 Plan plan = (Plan)it.next();
                 Comisiones com = new Comisiones();
                 com.setPlan(plan);
                 com.setPorcentaje(0.0);
                 comisionesList.add(com);
            }
        }


        for (Iterator itna = comisionesList.iterator(); itna.hasNext();) {
            Comisiones vec = (Comisiones) itna.next();
            row = new Row();

            nombrePlan = new Label();
            nombrePlan.setStyle("font-size:1px;color:white");
            nombrePlan.setValue("" + vec.getCodigo());
            nombrePlan.setParent(row);

            nombrePlan = new Label();
            nombrePlan.setValue("" + vec.getPlan().getNombre());
            nombrePlan.setParent(row);

            nombrePlan = new Label();
            nombrePlan.setValue("" + vec.getPlan().getValor() +" "+vec.getPlan().getTipo());
            nombrePlan.setParent(row);

            porcen = new Doublebox();
            porcen.setStyle("text-align:right");
            porcen.setValue(vec.getPorcentaje());
            porcen.setParent(row);

            nombrePlan = new Label();
            nombrePlan.setStyle("font-size:1px;color:white");
            nombrePlan.setValue("" + vec.getPlan().getCodigo());
            nombrePlan.setParent(row);

            row.setParent(this);

        }




    }
public void seleccionar(Double valor){
    List lis = getChildren();
    for (Iterator it = lis.iterator(); it.hasNext();) {
        Row object = (Row) it.next();
         List labels = object.getChildren();
          ((Doublebox) labels.get(3)).setValue(valor);
    }

}
    @SuppressWarnings("static-access")
    public String guardar(List col, Empleados g) {

        Administrador adm = new Administrador();
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                Comisiones nota = new Comisiones();
                List labels = object.getChildren();
                String valorCodigo = ((Label) labels.get(0)).getValue();
                Integer codigo = null;
                try {
                    codigo = new Integer(valorCodigo);
                } catch (Exception e) {
                    codigo = null;
                }
                nota.setCodigo(codigo);
                nota.setPlan(new Plan(Integer.parseInt(((Label) labels.get(4)).getValue())));
                nota.setEmpleados(g);
                nota.setPorcentaje(((Doublebox) labels.get(3)).getValue() );
 
                if (nota.getCodigo() != null ) {
                    nota.setEmpleados(g);
                    adm.actualizar(nota);
                    
                } else {
                    nota.setEmpleados(g);
                    nota.setCodigo(adm.getNuevaClave("Comisiones", "codigo"));
                    adm.guardar(nota);
                    //return "ok";
                }
//                    adm.guardar(nota);

            } catch (Exception ex) {
                Logger.getLogger(comisiones.class.getName()).log(Level.SEVERE, null, ex);
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
