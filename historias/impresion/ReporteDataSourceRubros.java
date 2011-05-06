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
//import Almacen.*;
//import educativo.persistencia.RubrosAsignados;
//import java.util.Iterator;
//import java.util.List;
//import net.sf.jasperreports.engine.JRDataSource;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JRField;
//
//
///**
// *
// * @author Francisco
// */
//public class ReporteDataSourceRubros implements JRDataSource{
//
//   private Iterator itrAlumnos;
//   private Iterator itrNodos;
//   private Object valorAtual;
//   private boolean irParaProximoAlumno = true;
//
//    public ReporteDataSourceRubros(List lista) {
//        super();
//        this.itrNodos = lista.iterator();
//    }
//
//    public boolean next() throws JRException {
//        itrAlumnos = itrNodos;
//        valorAtual = itrAlumnos.hasNext() ? itrAlumnos.next() : null;
//        irParaProximoAlumno = (valorAtual != null);
//        return irParaProximoAlumno;
//    }
//
//    /*
//     * (non-Javadoc)
//     *
//     * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
//     */
//    public Object getFieldValue(JRField campo) throws JRException {
//        Object valor = null;
//        RubrosAsignados nodo = (RubrosAsignados) valorAtual;
//  Funciones fun = new Funciones();
//        if ("alumno".equals(campo.getName())) {
//            valor = nodo.getMatriCod().getAluCod().toString();
//        }else if ("rubro".equals(campo.getName())) {
//            valor = nodo.getRubCodigo().getRubNombre();
//        }
//        else if ("valor".equals(campo.getName())) {
//            valor = nodo.getAsigValor();
//        }
//        else if ("total".equals(campo.getName())) {
//            valor = Double.parseDouble(nodo.getMatriCod().getMttObs());
//        }
//        else if ("mes".equals(campo.getName())) {
//            valor = fun.meses(nodo.getAsigFecha().getMonth());
//        }
// /* Fijar que el arraylist de disciplinas es enbuelto en un JRBeanCollectionDataSource */
////   else if ("ListaDisciplinas".equals(campo.getName())) {
////   valor = new JRBeanCollectionDataSource(
////   			aluno.getDisciplinas());
////   }
//        return valor;
//    }
//}
//
