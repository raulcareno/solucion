/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes.bean;

/**
 *
 * @author inform
 */
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModelListener;
import jcinform.persistencia.Matriculas;
import org.primefaces.component.datatable.DataTable;
 
public class MatriculasDataModel extends PrimeDataModel<Matriculas> {

    public MatriculasDataModel() {
    }
    public MatriculasDataModel(List<Matriculas> data) {
        super(data);
    }
    @Override
    public Matriculas getRowData(String rowKey) {
        List<Matriculas> matriculas = (List<Matriculas>) getWrappedData();
        for (Matriculas matri : matriculas) {
            if ((""+matri.getIdMatriculas()).equals(rowKey)) {
                return matri;
            }
        }
        return null;
    }

    
 
    @Override
    public Object getRowKey(Matriculas car) {
        return car.getIdMatriculas()+"";
    }
    
      public boolean isValueBlank(String value) {
                if(value == null)
                        return true;

                return value.trim().equals("");
        }

       void decodeMultipleSelection(DataTable table, String selection) {
                Class<?> clazz = table.getValueExpression("selection").getType(FacesContext.getCurrentInstance().getELContext());

                if(isValueBlank(selection)) {
                        Object data = Array.newInstance(clazz.getComponentType(), 0);
                        table.setSelection(data);   
                }
        else {
            String[] rowKeys = selection.split(",");
            List selectionList = new ArrayList();
            
            for(int i = 0; i < rowKeys.length; i++) {
                Object rowData = table.getRowData(rowKeys[i]);
                
                if(rowData != null)
                    selectionList.add(rowData);
            }

            Object selectinArray = Array.newInstance(clazz.getComponentType(), selectionList.size());
            table.setSelection(selectionList.toArray((Object[])selectinArray));
                }
        }
}
