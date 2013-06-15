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

public class servicios extends Rows {
//ArrayList listad = new ArrayList();

    public servicios() {
      
    }

    public void addRow(Matriculas p) {
        if(p.getCodigomat().equals(new Integer(0))){
            getChildren().clear();
            return;
        }
         String complemento = "";
          Administrador adm = new Administrador();
        String query = "SELECT o FROM ServiciosComplementarios as o WHERE o.matricula.codigomat  =  '" + p.getCodigomat() + "'  ";
        List empleadosectorList = adm.query(query);
        Checkbox  porcen = null;
        Label nombreProductos = null;
        getChildren().clear();
        Row row = new Row();
        if(empleadosectorList.size()<=0){
              query = "SELECT o FROM Productos as o  ";
              List planesList = adm.query(query);
              for (Iterator it = planesList.iterator(); it.hasNext();) {
                 Productos sect = (Productos)it.next();
                 ServiciosComplementarios com = new ServiciosComplementarios();
                 com.setMatricula(p);
                 com.setProducto(sect);
                 com.setSi(false);
                 empleadosectorList.add(com);
            }
        }else{
            query = "SELECT o FROM Productos as o  "
                    + "where o.codigo not in (SELECT m.producto.codigo FROM ServiciosComplementarios as m "
                    + "WHERE m.matricula.codigomat  =  '" + p.getCodigomat() + "'  ) ";
              List planesList = adm.query(query);
              for (Iterator it = planesList.iterator(); it.hasNext();) {
                 Productos sect = (Productos)it.next();
                 ServiciosComplementarios com = new ServiciosComplementarios();
                 com.setMatricula(p);
                 com.setProducto(sect);
                  com.setSi(false);
                 empleadosectorList.add(com);
            }
        }
        for (Iterator itna = empleadosectorList.iterator(); itna.hasNext();) {
            ServiciosComplementarios vec = (ServiciosComplementarios) itna.next();
            row = new Row();

            nombreProductos = new Label(); //0
            nombreProductos.setStyle("font-size:1px;color:white");
            nombreProductos.setValue("" + vec.getCodigo());
            nombreProductos.setParent(row);

            nombreProductos = new Label();//1
            nombreProductos.setValue(vec.getProducto().getDescripcion()+"  ");
            nombreProductos.setParent(row);

            nombreProductos = new Label();//2
            nombreProductos.setValue("  " + vec.getProducto().getPrecio());
            nombreProductos.setParent(row);


            porcen = new Checkbox();//3
            porcen.setStyle("text-align:right");
            porcen.setChecked(vec.getSi());

            
            porcen.setParent(row);

            nombreProductos = new Label();//4
            nombreProductos.setStyle("font-size:1px;color:white");
            nombreProductos.setValue("" + vec.getProducto().getCodigo());
            nombreProductos.setParent(row);

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
    public String guardar(List col, Matriculas g) {

        Administrador adm = new Administrador();
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                ServiciosComplementarios nota = new ServiciosComplementarios();
                List labels = object.getChildren();
                String valorCodigo = ((Label) labels.get(0)).getValue();
                Integer codigo = null;
                try {
                    codigo = new Integer(valorCodigo);
                } catch (Exception e) {
                    codigo = null;
                }
                nota.setCodigo(codigo);
                nota.setSi( ((Checkbox) labels.get(3)).isChecked());
                nota.setProducto(new Productos(Integer.parseInt(((Label) labels.get(4)).getValue())));
                nota.setMatricula(g);
                if (nota.getCodigo() != null ) {
                    nota.setMatricula(g);
                    adm.actualizar(nota);
                    
                } else {
                    nota.setMatricula(g);
                    nota.setCodigo(adm.getNuevaClave("ServiciosComplementarios", "codigo"));
                    adm.guardar(nota);
                    //return "ok";
                }
//                    adm.guardar(nota);

            } catch (Exception ex) {
                Logger.getLogger(servicios.class.getName()).log(Level.SEVERE, null, ex);
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
