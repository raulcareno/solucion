/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes.bean;

/**
 *
 * @author inform
 */
import java.util.List;
import javax.faces.model.ListDataModel;
import jcinform.persistencia.Matriculas;
import org.primefaces.model.SelectableDataModel;

public class MatriculasDataModel extends ListDataModel<Matriculas> implements SelectableDataModel<Matriculas> {

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
}
