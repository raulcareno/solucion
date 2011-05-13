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

public class sectores extends Rows {
//ArrayList listad = new ArrayList();

    public sectores() {
      
    }

    public void addRow(Empleados p) {
         String complemento = "";
          Administrador adm = new Administrador();
        String query = "SELECT o FROM Empleadossucursal as o WHERE o.empleados.codigo  =  '" + p.getCodigo() + "'  ";
        List empleadosectorList = adm.query(query);
        Checkbox  porcen = null;
        Label nombreSucursal = null;

        getChildren().clear();
        Row row = new Row();
        if(empleadosectorList.size()<=0){
              query = "SELECT o FROM Sucursal as o  ";
              List planesList = adm.query(query);
              for (Iterator it = planesList.iterator(); it.hasNext();) {
                 Sucursal sect = (Sucursal)it.next();
                 Empleadossucursal com = new Empleadossucursal();
                 com.setEmpleados(p);
                 com.setSucursal(sect);
                 com.setEstado(false);

                 empleadosectorList.add(com);
            }
        }else{
            query = "SELECT o FROM Sucursal as o  "
                    + "where o.codigo not in (SELECT m.sucursal.codigo FROM Empleadossucursal as m "
                    + "WHERE m.empleados.codigo  =  '" + p.getCodigo() + "'  ) ";
              List planesList = adm.query(query);
              for (Iterator it = planesList.iterator(); it.hasNext();) {
                 Sucursal sect = (Sucursal)it.next();
                 Empleadossucursal com = new Empleadossucursal();
                 com.setEmpleados(p);
                 com.setSucursal(sect);
                com.setEstado(false);
                 empleadosectorList.add(com);
            }

        }


        for (Iterator itna = empleadosectorList.iterator(); itna.hasNext();) {
            Empleadossucursal vec = (Empleadossucursal) itna.next();
            row = new Row();

            nombreSucursal = new Label(); //0
            nombreSucursal.setStyle("font-size:1px;color:white");
            nombreSucursal.setValue("" + vec.getCodigo());
            nombreSucursal.setParent(row);

            nombreSucursal = new Label();//1
            nombreSucursal.setValue(vec.getSucursal().getDescripcion()+"  ");
            nombreSucursal.setParent(row);

            nombreSucursal = new Label();//2
            nombreSucursal.setValue("  " + vec.getSucursal().getDescripcion());
            nombreSucursal.setParent(row);


            porcen = new Checkbox();//3
            porcen.setStyle("text-align:right");
            porcen.setChecked(vec.getEstado());

            
            porcen.setParent(row);

            nombreSucursal = new Label();//4
            nombreSucursal.setStyle("font-size:1px;color:white");
            nombreSucursal.setValue("" + vec.getSucursal().getCodigo());
            nombreSucursal.setParent(row);

            row.setParent(this);

        }




    }
public void seleccionar(Boolean estado){
    List lis = getChildren();
    for (Iterator it = lis.iterator(); it.hasNext();) {
        Row object = (Row) it.next();
         List labels = object.getChildren();
          ((Checkbox) labels.get(3)).setChecked(estado);
    }

}
    @SuppressWarnings("static-access")
    public String guardar(List col, Empleados g) {

        Administrador adm = new Administrador();
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                Empleadossucursal nota = new Empleadossucursal();
                List labels = object.getChildren();
                String valorCodigo = ((Label) labels.get(0)).getValue();
                Integer codigo = null;
                try {
                    codigo = new Integer(valorCodigo);
                } catch (Exception e) {
                    codigo = null;
                }
                nota.setCodigo(codigo);
                nota.setEstado( ((Checkbox) labels.get(3)).isChecked());
                nota.setSucursal(new Sucursal(Integer.parseInt(((Label) labels.get(4)).getValue())));
                nota.setEmpleados(g);
                if (nota.getCodigo() != null ) {
                    nota.setEmpleados(g);
                    adm.actualizar(nota);
                    
                } else {
                    nota.setEmpleados(g);
                    nota.setCodigo(adm.getNuevaClave("Empleadossucursal", "codigo"));
                    adm.guardar(nota);
                    //return "ok";
                }
//                    adm.guardar(nota);

            } catch (Exception ex) {
                Logger.getLogger(sectores.class.getName()).log(Level.SEVERE, null, ex);
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
