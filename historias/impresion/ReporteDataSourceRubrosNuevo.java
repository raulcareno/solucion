package impresion;

///*
// * ReporteDataSource.java
// *
// * Created on 11 de noviembre de 2007, 10:22 PM
// *
// * To change this template, choose Tools | Template Manager
// * and open the template in the editor.
// */
//
//package impresion;
//
//import educativo.persistencia.RubrosAsignados;
//import java.util.Iterator;
//import java.util.List;
//import net.sf.jasperreports.engine.JRDataSource;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JRField;
//import utilerias.ValidarCampos;
//
///**
// *
// * @author Francisco
// */
//public class ReporteDataSourceRubrosNuevo implements JRDataSource{
//
//   private Iterator itrAlumnos;
//   private Iterator itrNodos;
//   private Object valorAtual;
//   private boolean irParaProximoAlumno = true;
//
//    public ReporteDataSourceRubrosNuevo(List lista) {
//        super();
//        this.itrNodos = lista.iterator();
//    }
//
//    public boolean next() throws JRException {
//        itrAlumnos = itrNodos;
//        valorAtual = itrAlumnos.hasNext() ? itrAlumnos.next() : "-";
//        irParaProximoAlumno = (valorAtual != "-");
//        return irParaProximoAlumno;
//    }
//
//    /*
//     * (non-Javadoc)
//     *
//     * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
//     */
//    public Object getFieldValue(JRField campo) throws JRException {
//        Object valor = "-";
//        ValidarCampos val = new ValidarCampos();
//        RubrosAsignados nodo = (RubrosAsignados) valorAtual;
//
//        if ("alumno".equals(campo.getName())) {
//            valor = nodo.getMatriCod().getAluCod().toString();
//        }else if ("rubro".equals(campo.getName())) {
//            valor = nodo.getRubCodigo().getRubNombre();
//        } else if ("mes".equals(campo.getName())) {
//            valor = val.mes(nodo.getAsigFecha().getMonth()+1);
//        }else if ("anio".equals(campo.getName())) {
//            valor = (nodo.getAsigFecha().getYear()+1900)+"";
//        }
//
//        else if ("valor".equals(campo.getName())) {
//            valor = nodo.getAsigValor();
//        }
//
//        else if ("total".equals(campo.getName())) {
//            valor = Double.parseDouble(nodo.getMatriCod().getMttObs());
//        }
//// /* Fijar que el arraylist de disciplinas es enbuelto en un JRBeanCollectionDataSource */
//////   else if ("ListaDisciplinas".equals(campo.getName())) {
//////   valor = new JRBeanCollectionDataSource(
//////   			aluno.getDisciplinas());
//////   }
//        return valor;
//    }
//}
//
