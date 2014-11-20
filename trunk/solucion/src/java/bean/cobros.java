package bean;

import bsh.Interpreter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.zkoss.zk.ui.Path;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.*;
import sources.ReporteFacturaDataSource;

public class cobros extends Rows {
//ArrayList listad = new ArrayList();

    private Double notaDisciplina = 0.0;
    private static Boolean malFormulas = false;
    String redon = "public Double redondear(Double numero, int decimales) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero+\"\");" + "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);" + "        return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
    String truncar = "public Double truncar(Double numero, int decimales) {         try {             java.math.BigDecimal d = new java.math.BigDecimal(numero+\"\");             d = d.setScale(decimales, java.math.BigDecimal.ROUND_DOWN);             return d.doubleValue();         } catch (Exception e) {             return 0.0;         }     }";
    String equival = "public Double equivalencia(Double numero) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero+\"\");" + "       return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
    String prom1 = metodos.prom1;
    String prom2 = metodos.prom2;

    public cobros() {
        //         Grid g;
//         Label l;
//         Row row;
//         row.getZIndex()
    }

    void limpiarMemoria() {
        System.gc();
        System.gc();
        System.gc();
        System.gc();
    }

    public void addRow(Date fecha, String separador, Estudiantes alu) {
        Session ses = Sessions.getCurrent();
        Empleados empleado = (Empleados) ses.getAttribute("user");
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();
        getChildren().clear();
        Interpreter inter = new Interpreter();
        Row row = new Row();
//        Date fechaActual = adm.Date();

        List<Matriculas> matriculas = adm.query("Select o from Matriculas as o "
                + "where o.estudiante.codigoest = '" + alu.getCodigoest() + "' "
                + " and o.estado not in ('Anulado','anulado','Eliminado')  order by o.curso.periodo.codigoper ");
        if (matriculas.size() <= 0) {
            try {
                Messagebox.show("NO registra deudas pendientes ...! ");
                //          this.formaBuscar.setVisible(false);
                return;
            } catch (InterruptedException ex) {
                Logger.getLogger(cobros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Matriculas mat = new Matriculas();
        for (Matriculas matriculas1 : matriculas) {
            mat = matriculas1;
            //matriculaActual = mat;
            //observacion.setText(matriculaActual.getEstudiante().getObservacion());
        }

        String fechaS = (fecha.getYear() + 1900) + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
        String quer = "Select o from Asignados AS o "
                + "WHERE o.estado = true  "
                + "AND o.matricula.estudiante.codigoest = '" + alu.getCodigoest() + "' "
                + "AND o.fechai <= '" + fechaS + "' ORDER BY o.fechai";
        System.out.println(quer);
//Matriculas m;
//m.getMttCod();
        List rubros = adm.query(quer);
//                                  "mat,txtFecha.getDate());
        ValidarCampos val = new ValidarCampos();

        if (rubros.size() > 0) {
            for (Iterator it = rubros.iterator(); it.hasNext();) {
                Asignados elem = (Asignados) it.next();
                row = new Row();
                row.setValue(elem);
                Textbox gen = new Textbox();
                gen.setCols(1);
                gen.setReadonly(true);
                gen.setText(elem.getCodigorub() + "");
                row.appendChild(gen);

                gen = new Textbox();
                gen.setCols(50);
                gen.setReadonly(true);
                gen.setText(elem.getProducto() + "");
                row.appendChild(gen);

                gen = new Textbox();
                gen.setCols(7);
                gen.setReadonly(true);
                gen.setText(mes(elem.getFechai().getMonth() + 1) + "");
                row.appendChild(gen);

                gen = new Textbox();
                gen.setReadonly(true);
                gen.setText(elem.getFechai().getYear() + 1900 + "");
                row.appendChild(gen);

                Checkbox gen1 = new Checkbox();
                gen1.setChecked(elem.getProducto().getGrabaiva());
                gen1.setDisabled(true);
                row.appendChild(gen1);

                Doublebox gen2 = new Doublebox();
                gen2.setCols(5);
                gen2.setReadonly(true);
                gen2.setValue((elem.getValor()));
                row.appendChild(gen2);

                gen2 = new Doublebox();
                gen2.setCols(5);
                gen2.setReadonly(true);
                gen2.setText((elem.getBeca() == null ? 0d : elem.getBeca()) + "");
                row.appendChild(gen2);

                gen2 = new Doublebox();
                gen2.setCols(5);
                gen2.setReadonly(true);
                gen2.setText((elem.getOtros() == null ? 0d : elem.getOtros()) + "");
                row.appendChild(gen2);
                try {
                    gen2 = new Doublebox();
                    gen2.setCols(5);
                    gen2.setReadonly(true);
                    gen2.setValue(elem.getValor() - (elem.getBeca() == null ? 0d : elem.getBeca()) - (elem.getOtros() == null ? 0d : elem.getOtros()));
                    row.appendChild(gen2);
                } catch (Exception e) {
                    gen2 = new Doublebox();
                    gen2.setCols(5);
                    gen2.setReadonly(true);
                    gen2.setValue((elem.getValor()));
                    row.appendChild(gen2);
                }


                final Button aButton = new Button("");//"Cobrar"
                aButton.setImage("/images/eliminar.gif");
                aButton.setId("id" + elem.getCodigorub());
                //aButton.setTooltip(cobr);
                aButton.setAttribute("valor", elem.getValor());
                //aButton.setTooltip(cobr);

                aButton.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
//                               if(verificar3("Ingresar")){
//                                        registro.setSelected(true);
//                                        final Window win = (Window) Executions.createComponents("facturarCliente.zul", null, null);
//                                        win.setMaximizable(true);
//                                        win.setClosable(true);
                        eliminar(aButton.getId()+"");
//                                        win.setAttribute("codigoFactura", aButton.getId());
//                                        win.setAttribute("valoraPagar", aButton.getAttribute("valor"));
//                                        win.setTitle("Facturar Clientes");
//                                        win.doModal();
//                                 }else{
//                                            Messagebox.show("No tiene permisos para realizar esta acción...!", "Administrador Educativo", Messagebox.OK, Messagebox.ERROR);
//                                    }  
                    }
                });

                row.appendChild(aButton);


                //añado toda la fila
                row.setParent(this);



            }
        } else {
            try {
                Messagebox.show("NO registra deudas pendientes ...! ");
            } catch (InterruptedException ex) {
                Logger.getLogger(cobros.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
//        tFactura.setModel(dtm);
//        sumar();
//        sumarFacturas();
//        sumarRecibos();
        //     row = new Row();






    }

    public void cargarRubros(Estudiantes alu) {
        //this.tMatriculados.setVisible(false);
//        this.formaBuscar.setVisible(false);
    }

    public Double totalfinal(){
         Rows filas2 = this;
        Double total = new Double(0);
        List listadoProductos = filas2.getChildren();
        for (int i = 0; i <= listadoProductos.size() - 1; i++) {
            Row object = (Row) listadoProductos.get(i);
            Asignados nuevo = ((Asignados) object.getValue());
                total +=(nuevo.getValor());
        }
        return total;
    }
    public Double total1(){
         Rows filas2 = this;
        Double total = new Double(0);
        List listadoProductos = filas2.getChildren();
        for (int i = 0; i <= listadoProductos.size() - 1; i++) {
            Row object = (Row) listadoProductos.get(i);
            Asignados nuevo = ((Asignados) object.getValue());
            if(!nuevo.getProducto().getGrabaiva()){
                total +=(nuevo.getValor());
            }
             
        }
        return total;
    }
    public Double total(){
         Rows filas2 = this;
        Double total = new Double(0);
        List listadoProductos = filas2.getChildren();
        for (int i = 0; i <= listadoProductos.size() - 1; i++) {
            Row object = (Row) listadoProductos.get(i);
            Asignados nuevo = ((Asignados) object.getValue());
            if(nuevo.getProducto().getGrabaiva()){
                total +=(nuevo.getValor());
            }
             
        }
        return total;
    }
    
       public Double iva(){
         Rows filas2 = this;
        Double total = new Double(0);
        List listadoProductos = filas2.getChildren();
        for (int i = 0; i <= listadoProductos.size() - 1; i++) {
            Row object = (Row) listadoProductos.get(i);
            Asignados nuevo = ((Asignados) object.getValue());
            if(nuevo.getProducto().getGrabaiva()){
                total +=(nuevo.getValor());
            }
             
        }
        //return ((total.divide(new Double(0), RoundingMode.UP)));
        return new Double(0);
        
    }
       
    public Double subtotal(){
         Rows filas2 = this;
        Double total = new Double(0);
        List listadoProductos = filas2.getChildren();
        for (int i = 0; i <= listadoProductos.size() - 1; i++) {
            Row object = (Row) listadoProductos.get(i);
            Asignados nuevo = ((Asignados) object.getValue());
            if(nuevo.getProducto().getGrabaiva()){
                total +=(nuevo.getValor());
            }
             
        }
        return total;
    }
    public void eliminar(String id) {
         

        Rows filas2 = this;

        List listadoProductos = filas2.getChildren();
        for (int i = 0; i <= listadoProductos.size() - 1; i++) {
            Row object = (Row) listadoProductos.get(i);
            Asignados nuevo = ((Asignados) object.getValue());
            String codigoA = "id" + nuevo.getCodigorub();
            if (codigoA.equals(id)) {
                this.removeChild(object);
                break;
            }
        }
        try{
            Tab t2 = (Tab)Path.getComponent("//cobrospage/cobrosventana/facturas2");
            Tab t = (Tab)Path.getComponent("//cobrospage/cobrosventana/facturas");
            if(t2.isSelected()){
                t.setSelected(true); 
            }else if(t.isSelected()){
                t2.setSelected(true); 
            }
            
        }catch(Exception e){
            
            e.printStackTrace();
        }
      
        
    }
 

    public Double redondear(Double numero, int decimales) {
        try {

            BigDecimal d = new BigDecimal(numero + "");
            d = d.setScale(decimales, RoundingMode.HALF_UP);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }

    public Double truncar(Double numero, int decimales) {
        try {
            BigDecimal d = new BigDecimal(numero + "");
            d = d.setScale(decimales, java.math.BigDecimal.ROUND_DOWN);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }

    public Boolean regresaVariableParametrosLogico(String variable, List<ParametrosGlobales> textos) {
        for (Iterator<ParametrosGlobales> it = textos.iterator(); it.hasNext();) {
            ParametrosGlobales textos1 = it.next();
            if (textos1.getVariable().equals(variable)) {
                return textos1.getBvalor();
            }
        }
        return false;
    }

    public String mes(int mes) {
        switch (mes) {
            case 1:
                return "ENERO";
            case 2:
                return "FEBRERO";
            case 3:
                return "MARZO";
            case 4:
                return "ABRIL";
            case 5:
                return "MAYO";
            case 6:
                return "JUNIO";
            case 7:
                return "JULIO";
            case 8:
                return "AGOSTO";
            case 9:
                return "SEPTIEMBRE";
            case 10:
                return "OCTUBRE";
            case 11:
                return "NOVIEMBRE";
            case 12:
                return "DICIEMBRE";

        }
        return null;
    }

    public int mesint(String mes) {

        if (mes.equals("ENERO")) {
            return 1;
        }
        if (mes.equals("FEBRERO")) {
            return 2;
        }
        if (mes.equals("MARZO")) {
            return 3;
        }
        if (mes.equals("ABRIL")) {
            return 4;
        }
        if (mes.equals("MAYO")) {
            return 5;
        }
        if (mes.equals("JUNIO")) {
            return 6;
        }
        if (mes.equals("JULIO")) {
            return 7;
        }
        if (mes.equals("AGOSTO")) {
            return 8;
        }
        if (mes.equals("SEPTIEMBRE")) {
            return 9;
        }
        if (mes.equals("OCTUBRE")) {
            return 10;
        }
        if (mes.equals("NOVIEMBRE")) {
            return 11;
        }
        if (mes.equals("DICIEMBRE")) {
            return 12;
        }
        return 0;
    }
     

}
