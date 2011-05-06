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
//import Almacen.Funciones;
//import educativo.persistencia.Detallefactura;
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
//public class ReporteDataSourceRubrosPagados implements JRDataSource{
//
//   private Iterator itrAlumnos;
//   private Iterator itrNodos;
//   private Object valorAtual;
//   private boolean irParaProximoAlumno = true;
//
//    public ReporteDataSourceRubrosPagados(List lista) {
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
//        Detallefactura nodo = (Detallefactura) valorAtual;
//        Funciones fun = new Funciones();
//        if ("estudiante".equals(campo.getName())) {
//            valor = nodo.getCabecerafactura().getMatriCod().getAluCod().toString();
//        }else if ("rubro".equals(campo.getName())) {
//            valor = nodo.getRubCodigo().getRubNombre();
//        }else if ("no".equals(campo.getName())) {
//            valor = this.getI();
//            this.setI(getI()+1);
//        }else if ("mes".equals(campo.getName())) {
//            valor = fun.meses(nodo.getDetMes()-1);
//
//        }else if ("factura".equals(campo.getName())) {
//            valor = nodo.getCabecerafactura().getFacNumero();
//
//        }else if ("valor".equals(campo.getName())) {
//            valor = nodo.getDetPrecio();
//
//        }
//        return valor;
//    }
//
//    /**
//     * Holds value of property i.
//     */
//    private int i=1;
//
//    /**
//     * Getter for property i.
//     * @return Value of property i.
//     */
//    public int getI() {
//        return this.i;
//    }
//
//    /**
//     * Setter for property i.
//     * @param i New value of property i.
//     */
//    public void setI(int i) {
//        this.i = i;
//    }
//}
//
