/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.source;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.Administrador.Administrador;
import jcinform.Administrador.UsuarioActivo;
import jcinform.Administrador.general;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Empresa;
import jcinform.persistencia.Turnos;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author inform
 */
public class reportesClase {
  String separador = File.separatorChar + "";
  Date desde=null;
  Date hasta=null;
  Date desdehora2=null;
  Date hastahora2=null;
  Administrador adm = null;
  String titulo = "";
  Empleados empleado;
    public reportesClase() {
         adm = new Administrador();
    }
    public ArrayList ejecutarReporte(String tipoReporte,String titulo,Empleados empleado,Date desde,Date hasta,Date desdehora,Date hastahora) {                                          
        String query = "";
        String dirreporte = "";
       
        this.desde = desde;
        this.hasta = hasta;
        this.desdehora2 = desdehora;
        this.hastahora2 = hastahora;
        this.empleado = empleado;
        String desde2 = (desde.getYear() + 1900) + "-" + (desde.getMonth() + 1) + "-" + (desde.getDate());
        String hasta2 = (hasta.getYear() + 1900) + "-" + (hasta.getMonth() + 1) + "-" + (hasta.getDate());
//        String desde02 = (desde.getYear() + 1900) + "-" + (desde.getMonth() + 1) + "-" + (desde);
//        String hasta02 = (hasta.getYear() + 1900) + "-" + (hasta.getMonth() + 1) + "-" + (hasta);
        desde2 = desde2 + " " + desdehora2.getHours() + ":" + desdehora2.getMinutes() + ":" + desdehora2.getSeconds();
        hasta2 = hasta2 + " " + hastahora2.getHours() + ":" + hastahora2.getMinutes() + ":" + hastahora2.getSeconds();
//        String titulo = "";
         
//            String query = "";
        String ubicacionDirectorio = UsuarioActivo.getUbicacionDirectorio();
         
 
        //Turnos en Espera de ser atendidos 
        if (tipoReporte.equals("102")) { //POR ATENDER
 
            query = "SELECT COUNT(*),ventanilla,nombre,ventanillas.escorporativa  FROM turnos,ventanillas "
                    + "WHERE fechaatendido IS NULL  AND turnos.ventanilla = ventanillas.codigoven "
                    + "AND fechasolicitado  between '" + desde2 + "' and '" + hasta2 + "' "
                    + "and turnos.estado = 0 "  
                    + "GROUP BY ventanilla order by ventanillas.escorporativa,nombre  ";
            System.out.println("" + query);
            dirreporte = "turnosxatender.jasper";
            titulo = "Por ser Atendidos ";
           return generarReporteTurnosFaltanAtender(dirreporte, query, titulo);
        }else  if (tipoReporte.equals("103")) { //ATENDIDOS
 
            query = "SELECT COUNT(*),ventanilla,nombre,ventanillas.escorporativa  FROM turnos,ventanillas "
                    + "WHERE fechaatendido IS NOT NULL  AND turnos.ventanilla = ventanillas.codigoven "
                    + "AND fechasolicitado  between '" + desde2 + "' and '" + hasta2 + "' "
                    + " and turnos.estado = 1 "  
                    + "GROUP BY ventanilla  order by ventanillas.escorporativa,nombre  ";
            System.out.println("" + query);
            dirreporte = "turnosxatender.jasper";
            titulo = "Atendidos ";
           return  generarReporteTurnosFaltanAtender(dirreporte, query, titulo);
  
        }else  if (tipoReporte.equals("104")) { //LOS TURNOS QUE MÁS SE 
            //DEMORARON EN SER ATENDIDOS
           //el cliente al que le tocó esperar mas
//            SELECT (TIMEDIFF(fechaatendido,fechasolicitado)),fechasolicitado,fechallamada,ventanilla   FROM turnos  
  //          ORDER BY 1 DESC  LIMIT 10
            query = "SELECT (TIMEDIFF(fechaatendido,fechasolicitado)),fechasolicitado,fechallamada,fechaatendido, "
                    + "ventanilla, nombre,ventanillas.escorporativa FROM turnos,ventanillas "
                    + "WHERE turnos.ventanilla = ventanillas.codigoven  "
                    + "and fechaatendido IS NOT NULL   "
                    + "AND fechasolicitado  between '" + desde2 + "' and '" + hasta2 + "'  "
                    + "and turnos.estado = 1 "  
                    + " ORDER BY 1 DESC,ventanilla  LIMIT 10 ";
            System.out.println("" + query);
            dirreporte = "demorados.jasper";
            titulo = "Atendidos ";
           return  generarReporteTurnosDemorados(dirreporte, query, titulo);
  
 
        }else  if (tipoReporte.equals("105")) { //LOS TURNOS QUE MÁS SE 
            //EMPLEADOS LENTOS EN ATENDER 
 
            query = "SELECT TIMEDIFF(fechaatendido,fechallamada),fechallamada,fechaatendido,"
                    + " turnos.codigoemp, CONCAT(apellidos,' ',nombres), ventanillas.nombre  "
                    + " ,ventanillas.escorporativa  FROM turnos, empleados, ventanillas "
                     + "WHERE turnos.codigoemp = empleados.codigoemp  "
                    + " and ventanillas.codigoven = turnos.ventanilla "
                    + " and fechaatendido IS NOT NULL   "
                    + " AND fechasolicitado  between '" + desde2 + "' and '" + hasta2 + "' "  
                    + " and turnos.estado = 1  "
                    + " ORDER BY 1 DESC LIMIT 10";
            System.out.println("" + query);
            dirreporte = "demoradosEmpleados.jasper";
            titulo = "Atendidos ";
           return  generarReporteTurnosLentos(dirreporte, query, titulo);
  
 
        }else  if (tipoReporte.equals("106")) { //LOS TURNOS QUE MÁS SE 
            //HORAS PICO
            query = "SELECT  COUNT(*) turnosSolicitados, HOUR(fechasolicitado) hora  FROM turnos  "
                    + "WHERE fechasolicitado  between '" + desde2 + "' and '" + hasta2 + "'  "
                    + "GROUP BY  HOUR(fechasolicitado) "
                    + " ORDER BY 1 DESC,2 LIMIT 5";
            System.out.println("" + query);
            dirreporte = "pico.jasper";
            titulo = "Atendidos ";
           return  generarReporteTurnosPicos(dirreporte, query, titulo);
  
 
        }else if (tipoReporte.equals("108")) { //perdidos 
 
            query = "SELECT ventanilla,nombre,ventanillas.escorporativa,fechasolicitado,fechallamada  FROM turnos,ventanillas "
                    + "WHERE turnos.ventanilla = ventanillas.codigoven "
                    + "AND fechasolicitado  between '" + desde2 + "' and '" + hasta2 + "' and turnos.estado = 2  "  
                    + "  order by ventanillas.escorporativa,nombre  ";
            System.out.println("" + query);
            dirreporte = "turnosxperdidos.jasper";
            titulo = "Por ser Atendidos ";
           return  generarReporteTurnosPerdidos(dirreporte, query, titulo);
        }else  if (tipoReporte.equals("107")) { //PICO VENTANILLA
            //HORAS PICO ventanilla

            query = "SELECT  COUNT(*),HOUR(fechasolicitado), ventanilla,nombre,ventanillas.escorporativa   FROM turnos, ventanillas   "
                    + "WHERE turnos.ventanilla = ventanillas.codigoven "
                    + "and fechasolicitado  between '" + desde2 + "' and '" + hasta2 + "'  "
                    + "GROUP BY  HOUR(fechasolicitado), ventanilla "
                    + "ORDER BY 1 DESC, ventanilla DESC  LIMIT 10";
                     
            System.out.println("" + query);
            dirreporte = "picoVentanilla.jasper";
            titulo = "Atendidos ";
          return   generarReporteTurnosVentanilla(dirreporte, query, titulo);
  
 
        }else if (tipoReporte.equals("201")) { //calificacioens x empleado
            String comple = "";
            if (!empleado.getCodigoemp().equals(-1)) {
                comple = " and e.codigoemp = '" + empleado.getCodigoemp() + "' ";
            }
            query = "SELECT e.codigoemp, e.apellidos, e.nombres, t.descripcion, COUNT(c.empleados) cantidad  "
                    + "FROM calificacion c, empleados e, tipos t "
                    + "WHERE c.empleados = e.codigoemp  "
                    + "AND t.codigo = c.tipos "
                    + "AND c.fecha between '" + desde2 + "' and '" + hasta2 + "' " + comple
                    + "GROUP BY e.codigoemp, c.tipos  ORDER BY c.empleados,c.tipos";
            System.out.println("" + query);
            dirreporte = "calificacionxempleado.jasper";
            titulo = "Reporte de Calificaciones x Usuario";
          return   generarReporte(dirreporte, query, titulo);
        }else  if (tipoReporte.equals("205")) { //COMPARATIVO
            String comple = "";
            if (!empleado.getCodigoemp().equals(-1)) {
                comple = " and e.codigoemp = '" + empleado.getCodigoemp() + "' ";
            }
            query = "SELECT e.codigoemp, e.apellidos, e.nombres, t.descripcion, COUNT(c.empleados) cantidad  "
                    + "FROM calificacion c, empleados e, tipos t "
                    + "WHERE c.empleados = e.codigoemp  "
                    + "AND t.codigo = c.tipos "
                    + "AND c.fecha between '" + desde2 + "' and '" + hasta2 + "' " + comple
                    + "GROUP BY e.codigoemp, c.tipos  ORDER BY c.empleados,c.tipos";
            System.out.println("" + query);
            dirreporte = "calificacionxempleadoComparativo.jasper";
            titulo = "Comparativo ";
           return  generarReporte(dirreporte, query, titulo);
        }else  if (tipoReporte.equals("101")) { //COMPARATIVO
            String comple = "";
            if (!empleado.getCodigoemp().equals(-1)) {
                comple = " and c.codigoemp.codigoemp = '" + empleado.getCodigoemp() + "' ";
            }
            query = "SELECT c FROM Turnos as c where c.fechaatendido between '" + desde2 + "' and '" + hasta2 + "' " + comple
                    + " order by c.codigoemp.codigoemp, c.fechaatendido ";
            System.out.println("" + query);
            dirreporte = "turnosxempleado.jasper";
            titulo = "Comparativo ";
            String query2 =     "SELECT empleados.codigoemp,  "
                            + " CONCAT(SEC_TO_TIME(SUM(TIME_TO_SEC((TIMEDIFF(fechaatendido,fechallamada)))) ),'') "
                                + "FROM turnos,ventanillas,empleados WHERE turnos.ventanilla = ventanillas.codigoven  "
                                + "  AND  empleados.codigoemp = turnos.codigoemp  AND fechaatendido IS NOT NULL   "
                                + "    AND fechasolicitado  BETWEEN  '" + desde2 + "' and '" + hasta2 + "'    "
                                + "  GROUP BY EMPLEADOS.CODIGOEMP ";
          return   generarReporteTurnos(dirreporte, query, titulo,query2);
        }else  if (tipoReporte.equals("100")) { //POR EMPLEADOS DETALLADO
            String comple = "";
            if (!empleado.getCodigoemp().equals(-1)) {
                comple = " and empleados.codigoemp = '" + empleado.getCodigoemp() + "' ";
            }
//            query = "SELECT c FROM Turnos as c where c.fechaatendido between '" + desde2 + "' and '" + hasta2 + "' " + comple
//                    + " order by c.codigoemp.codigoemp, c.fechaatendido ";
            query = "SELECT (TIMEDIFF(fechaatendido,fechallamada)),fechasolicitado,fechallamada,fechaatendido, "
                    + "ventanilla, nombre,ventanillas.escorporativa,  "
                    + "CONCAT(empleados.apellidos,' ',empleados.nombres), empleados.codigoemp  "
                    + "FROM turnos,ventanillas,empleados "
                    + "WHERE turnos.ventanilla = ventanillas.codigoven  "
                    + " and  empleados.codigoemp = turnos.codigoemp  "
                    + " and turnos.estado = 1 "
                    + " and fechaatendido IS NOT NULL   " + comple 
                    + " AND fechasolicitado  between '" + desde2 + "' and '" + hasta2 + "' "  
                    + " ORDER BY  nombres,empleados.codigoemp, (TIMEDIFF(fechaatendido,fechallamada)) desc";
            System.out.println("" + query);
            dirreporte = "turnosxempleadoDetallado.jasper";
            titulo = "Comparativo ";
                    String query2 =     "SELECT empleados.codigoemp,  "
                            + " CONCAT(SEC_TO_TIME(SUM(TIME_TO_SEC((TIMEDIFF(fechaatendido,fechallamada)))) ),'') "
                                + "FROM turnos,ventanillas,empleados WHERE turnos.ventanilla = ventanillas.codigoven  "
                                + "  AND  empleados.codigoemp = turnos.codigoemp  AND fechaatendido IS NOT NULL   "
                                + "    AND fechasolicitado  BETWEEN  '" + desde2 + "' and '" + hasta2 + "'    "
                                + "  GROUP BY EMPLEADOS.CODIGOEMP ";
            
          return   generarReporteDetallado(dirreporte, query, titulo,query2);
        } else if (tipoReporte.equals("204")) { //
            String comple = "";
            if (empleado.getCodigoemp().equals(-1)) {
                try {
                    //JOptionPane.showMessageDialog(this, "Seleccione un Empleado...!");
                    Messagebox.show("Seleccione un Empleado...!", "Administrador Educativo", Messagebox.OK, Messagebox.INFORMATION);
                    return null;
                } catch (InterruptedException ex) {
                    Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            comple = " and e.codigoemp = '" + empleado.getCodigoemp() + "' ";

            query = "SELECT   YEAR(c.fecha), MONTH(c.fecha) ,concat(t.codigo,'. ',t.descripcion), COUNT(c.empleados) cantidad  "
                    + ", MONTH(c.fecha) FROM calificacion c, empleados e, tipos t "
                    + "WHERE c.empleados = e.codigoemp  "
                    + "AND t.codigo = c.tipos "
                    + "AND c.fecha between '" + desde2 + "' and '" + hasta2 + "' " + comple
                    + " GROUP BY 1,2,  e.codigoemp, c.tipos  ORDER BY 1,2, c.tipos";
            System.out.println("" + query);
            dirreporte = "calificacionxempleadomeses.jasper";
            titulo = "Reporte de Calificaciones x Usuario";
          return   generarReporteMeses(dirreporte, query, titulo);
        } else if (tipoReporte.equals("202")) { //TICKEST POR COBRAR
            query = "SELECT t.descripcion, COUNT(c.empleados) cantidad  "
                    + "FROM calificacion c, empleados e, tipos t "
                    + "WHERE c.empleados = e.codigoemp  "
                    + "AND t.codigo = c.tipos "
                    + "AND c.fecha between '" + desde2 + "' and '" + hasta2 + "' "
                    + "GROUP BY  c.tipos  ORDER BY c.tipos";
            System.out.println("" + query);
            dirreporte = "calificacionxtotal.jasper";
            titulo = "Reporte de Calificaciones x Usuario";
          return  generarReporteTotal(dirreporte, query, titulo);
        } else if (tipoReporte.equals("203")) { //TICKEST POR COBRAR
            query = "SELECT MONTH(c.fecha) , YEAR(c.fecha),  t.descripcion, COUNT(c.empleados) cantidad  "
                    + "FROM calificacion c, empleados e, tipos t "
                    + "WHERE c.empleados = e.codigoemp  "
                    + "AND t.codigo = c.tipos "
                    + "AND c.fecha between '" + desde2 + "' and '" + hasta2 + "' "
                    + "GROUP BY 2,1, c.tipos  ORDER BY 2,1, c.tipos";
            System.out.println("" + query);
            dirreporte = "calificacionxtotalmes.jasper";
            titulo = "Reporte de Calificaciones x Usuario";
          return   generarReporteTotalMeses(dirreporte, query, titulo);
        }
        return null;

    }                                         
    public static String convertiraString(Date fecha) {

        return (fecha.getYear() + 1900) + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();

    }
       public String mes(int mes) {
        switch (mes) {
            case 1:
                return "Enero";
            case 2:
                return "Febrero";
            case 3:
                return "Marzo";
            case 4:
                return "Abril";
            case 5:
                return "Mayo";
            case 6:
                return "Junio";
            case 7:
                return "Julio";
            case 8:
                return "Agosto";
            case 9:
                return "Septiembre";
            case 10:
                return "Octubre";
            case 11:
                return "Noviembre";
            case 12:
                return "Diciembre";
        }
        return "";
    }
        //TURNOS
    
    public ArrayList generarReporteTurnos(String dirreporte, String query, String titulo,String query2) {
        try {
            
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            ArrayList detalle = new ArrayList();
            List<Turnos> fac = adm.query(query);
            List  listado = adm.queryNativo(query2);
            for (Iterator it = fac.iterator(); it.hasNext();) {
                Empleados cli = new Empleados();
                Turnos clienteIt = (Turnos) it.next();
                    cli.setApellidos(clienteIt.getCodigoemp().getApellidos());
                    cli.setNombres(clienteIt.getCodigoemp().getNombres());
                    cli.setCodigoemp(clienteIt.getCodigoemp().getCodigoemp());
                    cli.setDireccion("");
                    cli.setTipousuario(1);
                    cli.setUltimoingreso(clienteIt.getFechallamada()); 
                    cli.setTelefono(duracionDiaria(listado, cli.getCodigoemp()));
                    detalle.add(cli);
            }

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazonsocial());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefono());
            parametros.put("titulo", titulo);
            parametros.put("empleado",empleado+"");
            Date desdeF = desde;
            Date hastaF = desde;
            
            desdeF.setHours(desdehora2.getHours());
            desdeF.setMinutes(desdehora2.getMinutes());
            desdeF.setSeconds(desdehora2.getSeconds());
             
            
            hastaF.setHours(hastahora2.getHours());
            hastaF.setMinutes(hastahora2.getMinutes());
            hastaF.setSeconds(hastahora2.getSeconds());
             
            
            parametros.put("desde", desdeF);
            parametros.put("hasta", hastaF);
            parametros.put("titulo", titulo);
          ArrayList informacionRegresa = new ArrayList();
            informacionRegresa.add(parametros);
            informacionRegresa.add(ds);
            informacionRegresa.add(dirreporte); return informacionRegresa;
            
          
        } catch (Exception ex) {
            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
  
   public ArrayList generarReporteTurnosVentanilla(String dirreporte, String query, String titulo) {
        try {
            
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            ArrayList detalle = new ArrayList();
            List fac = adm.queryNativo(query);
            
                 for (Iterator it = fac.iterator(); it.hasNext();) {
                Object[] clienteIt = (Object[]) it.next();
                general cli = new general();
                cli.setCantidad((Long) clienteIt[0]);
                Date hora = new Date();
                hora.setMinutes(0);
                hora.setSeconds(0); 
                hora.setHours((Integer) clienteIt[1]);
                cli.setHoras(hora);
                cli.setVentanillacod((Integer) clienteIt[2]);
                cli.setVentanilla( clienteIt[3].toString());
                cli.setTipoVentanilla((clienteIt[4].toString().equals("0")) ?"EXPRESS":(clienteIt[3].toString().equals("1")) ?"CORPORATIVA":(clienteIt[3].toString().equals("2")) ?"PREFERENCIAL":"" );
                detalle.add(cli);
            }
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazonsocial());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefono());
            parametros.put("titulo", titulo);
            parametros.put("usuario", empleado);
            Date desdeF = desde;
            Date hastaF = desde;
            
            desdeF.setHours(desdehora2.getHours());
            desdeF.setMinutes(desdehora2.getMinutes());
            desdeF.setSeconds(desdehora2.getSeconds());
             
            
            hastaF.setHours(hastahora2.getHours());
            hastaF.setMinutes(hastahora2.getMinutes());
            hastaF.setSeconds(hastahora2.getSeconds());
             
            
            parametros.put("desde", desdeF);
            parametros.put("hasta", hastaF);
            parametros.put("titulo", titulo);
        ArrayList informacionRegresa = new ArrayList();
            informacionRegresa.add(parametros);
            informacionRegresa.add(ds);
            informacionRegresa.add(dirreporte); return informacionRegresa;
            
          
        } catch (Exception ex) {
            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
  public ArrayList generarReporteTurnosPicos(String dirreporte, String query, String titulo) {
        try {
            
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            ArrayList detalle = new ArrayList();
            List fac = adm.queryNativo(query);
            
     //(TIMEDIFF(fechaatendido,fechasolicitado)),fechasolicitado,fechallamada, ventanilla,nombre 
            for (Iterator it = fac.iterator(); it.hasNext();) {
                Object[] clienteIt = (Object[]) it.next();
                general cli = new general();
                cli.setCantidad((Long) clienteIt[0]);
                Date hora = new Date();
                hora.setMinutes(0);
                hora.setSeconds(0); 
                hora.setHours((Integer) clienteIt[1]);
                cli.setHoras(hora);
                detalle.add(cli);
            }
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazonsocial());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefono());
            parametros.put("titulo", titulo);
            parametros.put("usuario",empleado+"");
            Date desdeF = desde;
            Date hastaF = desde;
            
            desdeF.setHours(desdehora2.getHours());
            desdeF.setMinutes(desdehora2.getMinutes());
            desdeF.setSeconds(desdehora2.getSeconds());
             
            
            hastaF.setHours(hastahora2.getHours());
            hastaF.setMinutes(hastahora2.getMinutes());
            hastaF.setSeconds(hastahora2.getSeconds());
             
            
            parametros.put("desde", desdeF);
            parametros.put("hasta", hastaF);
            parametros.put("titulo", titulo);
            ArrayList informacionRegresa = new ArrayList();
            informacionRegresa.add(parametros);
            informacionRegresa.add(ds);
            informacionRegresa.add(dirreporte); return informacionRegresa;
            
          
        } catch (Exception ex) {
            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
  public String duracionDiaria(List listado,Integer empBuscar){
      
            for (Iterator it = listado.iterator(); it.hasNext();) {
                Object[] clienteIt = (Object[]) it.next();
                if(empBuscar.equals(new Integer(clienteIt[0].toString())))
                    return clienteIt[1].toString();
            }
            return null;
  }
    public ArrayList generarReporteDetallado(String dirreporte, String query, String titulo,String query2) {
        try {
            
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            ArrayList detalle = new ArrayList();

            List fac = adm.queryNativo(query);
            List listado = adm.queryNativo(query2);
             for (Iterator it = fac.iterator(); it.hasNext();) {
                Object[] clienteIt = (Object[]) it.next();
                general cli = new general();
                cli.setDiferencia((Date) clienteIt[0]);
                cli.setFechasolicitado((Date) clienteIt[1]);
                cli.setFechallamada((Date) clienteIt[2]);
                cli.setFechaatendido((Date)clienteIt[3]);
                cli.setCodigo(clienteIt[4].toString());
                cli.setVentanilla(clienteIt[5].toString());
                cli.setTipoVentanilla((clienteIt[6].toString().equals("0")) ?"EXPRESS":(clienteIt[6].toString().equals("1")) ?"CORPORATIVA":(clienteIt[6].toString().equals("2")) ?"PREFERENCIAL":"" );
                cli.setEmpleado(clienteIt[7].toString());
                cli.setNombre(duracionDiaria(listado, new Integer(clienteIt[8].toString())));
                detalle.add(cli);
            }
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazonsocial());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefono());
            parametros.put("titulo", titulo);
            parametros.put("usuario",empleado+"");
            Date desdeF = desde;
            Date hastaF = desde;
            
            desdeF.setHours(desdehora2.getHours());
            desdeF.setMinutes(desdehora2.getMinutes());
            desdeF.setSeconds(desdehora2.getSeconds());
             
            
            hastaF.setHours(hastahora2.getHours());
            hastaF.setMinutes(hastahora2.getMinutes());
            hastaF.setSeconds(hastahora2.getSeconds());
             
            
            parametros.put("desde", desdeF);
            parametros.put("hasta", hastaF);
            parametros.put("titulo", titulo);
            ArrayList informacionRegresa = new ArrayList();
            informacionRegresa.add(parametros);
            informacionRegresa.add(ds);
            informacionRegresa.add(dirreporte); return informacionRegresa;
            
          
        } catch (Exception ex) {
            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
  
    public ArrayList generarReporteTurnosLentos(String dirreporte, String query, String titulo) {
        try {
            
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            ArrayList detalle = new ArrayList();
            List fac = adm.queryNativo(query);
            
     //(TIMEDIFF(fechaatendido,fechasolicitado)),fechasolicitado,fechallamada, ventanilla,nombre 
            for (Iterator it = fac.iterator(); it.hasNext();) {
                Object[] clienteIt = (Object[]) it.next();
                general cli = new general();
                cli.setDiferencia((Date) clienteIt[0]);
                cli.setFechallamada((Date) clienteIt[1]);
                cli.setFechaatendido((Date)clienteIt[2]);
                cli.setCodigo(clienteIt[3].toString());
                cli.setEmpleado(clienteIt[4].toString());
                cli.setVentanilla(clienteIt[5].toString());
                cli.setTipoVentanilla((clienteIt[6].toString().equals("0")) ?"EXPRESS":(clienteIt[6].toString().equals("1")) ?"CORPORATIVA":(clienteIt[6].toString().equals("2")) ?"PREFERENCIAL":"" );
                detalle.add(cli);
            }
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazonsocial());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefono());
            parametros.put("titulo", titulo);
            parametros.put("usuario",empleado+"");
            Date desdeF = desde;
            Date hastaF = desde;
            
            desdeF.setHours(desdehora2.getHours());
            desdeF.setMinutes(desdehora2.getMinutes());
            desdeF.setSeconds(desdehora2.getSeconds());
             
            
            hastaF.setHours(hastahora2.getHours());
            hastaF.setMinutes(hastahora2.getMinutes());
            hastaF.setSeconds(hastahora2.getSeconds());
             
            
            parametros.put("desde", desdeF);
            parametros.put("hasta", hastaF);
            parametros.put("titulo", titulo);
            ArrayList informacionRegresa = new ArrayList();
            informacionRegresa.add(parametros);
            informacionRegresa.add(ds);
            informacionRegresa.add(dirreporte); return informacionRegresa;
            
          
        } catch (Exception ex) {
            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
  
    public ArrayList generarReporteTurnosDemorados(String dirreporte, String query, String titulo) {
        try {
            
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            ArrayList detalle = new ArrayList();
            List fac = adm.queryNativo(query);
     //(TIMEDIFF(fechaatendido,fechasolicitado)),fechasolicitado,fechallamada, ventanilla,nombre 
            for (Iterator it = fac.iterator(); it.hasNext();) {
                Object[] clienteIt = (Object[]) it.next();
                general cli = new general();
                cli.setDiferencia((Date) clienteIt[0]);
                cli.setFechasolicitado((Date) clienteIt[1]);
                cli.setFechallamada((Date)clienteIt[2]);
                cli.setFechaatendido((Date)clienteIt[3]);
                cli.setVentanillacod((Integer)clienteIt[4]);
                cli.setVentanilla(clienteIt[5].toString());
                cli.setTipoVentanilla((clienteIt[6].toString().equals("0")) ?"EXPRESS":(clienteIt[6].toString().equals("1")) ?"CORPORATIVA":(clienteIt[6].toString().equals("2")) ?"PREFERENCIAL":"" );
                detalle.add(cli);
            }
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazonsocial());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefono());
            parametros.put("titulo", titulo);
            parametros.put("usuario",empleado+"");
            Date desdeF = desde;
            Date hastaF = desde;
            
            desdeF.setHours(desdehora2.getHours());
            desdeF.setMinutes(desdehora2.getMinutes());
            desdeF.setSeconds(desdehora2.getSeconds());
            // 
            
            hastaF.setHours(hastahora2.getHours());
            hastaF.setMinutes(hastahora2.getMinutes());
            hastaF.setSeconds(hastahora2.getSeconds());
            // 
            
            parametros.put("desde", desdeF);
            parametros.put("hasta", hastaF);
            parametros.put("titulo", titulo);

             ArrayList informacionRegresa = new ArrayList();
            informacionRegresa.add(parametros);
            informacionRegresa.add(ds);
            informacionRegresa.add(dirreporte); return informacionRegresa;
            
          
        } catch (Exception ex) {
            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
  
     public ArrayList generarReporteTurnosFaltanAtender(String dirreporte, String query, String titulo) {
        try {
            
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            ArrayList detalle = new ArrayList();
            List fac = adm.queryNativo(query);
            for (Iterator it = fac.iterator(); it.hasNext();) {
                Object[] clienteIt = (Object[]) it.next();
                general cli = new general();
                cli.setCantidad((Long) clienteIt[0]);
                cli.setVentanillacod((Integer) clienteIt[1]);
                cli.setVentanilla(clienteIt[2].toString());
                cli.setTipoVentanilla((clienteIt[3].toString().equals("0")) ?"EXPRESS":(clienteIt[3].toString().equals("1")) ?"CORPORATIVA":(clienteIt[3].toString().equals("2")) ?"PREFERENCIAL":"" );
                detalle.add(cli);
            }
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazonsocial());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefono());
            parametros.put("titulo", titulo);
            parametros.put("usuario",empleado+"");
            Date desdeF = desde;
            Date hastaF = desde;
            
            desdeF.setHours(desdehora2.getHours());
            desdeF.setMinutes(desdehora2.getMinutes());
            desdeF.setSeconds(desdehora2.getSeconds());
             
            
            hastaF.setHours(hastahora2.getHours());
            hastaF.setMinutes(hastahora2.getMinutes());
            hastaF.setSeconds(hastahora2.getSeconds());
             
            
            parametros.put("desde", desdeF);
            parametros.put("hasta", hastaF);
            parametros.put("titulo", titulo);
        ArrayList informacionRegresa = new ArrayList();
            informacionRegresa.add(parametros);
            informacionRegresa.add(ds);
            informacionRegresa.add(dirreporte); return informacionRegresa;
            
          
        } catch (Exception ex) {
            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
     public ArrayList generarReporteTurnosPerdidos(String dirreporte, String query, String titulo) {
        try {
            
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            ArrayList detalle = new ArrayList();
            List fac = adm.queryNativo(query);
            for (Iterator it = fac.iterator(); it.hasNext();) {
                Object[] clienteIt = (Object[]) it.next();
                general cli = new general();
                //ventanilla,nombre,ventanillas.escorporativa,fechasolicitado,fechallamada 
                cli.setVentanillacod((Integer) clienteIt[0]);
                cli.setVentanilla(clienteIt[1].toString());
                cli.setTipoVentanilla((clienteIt[2].toString().equals("0")) ?"EXPRESS":(clienteIt[2].toString().equals("1")) ?"CORPORATIVA":(clienteIt[2].toString().equals("2")) ?"PREFERENCIAL":"" );
                cli.setFechasolicitado((Date)clienteIt[3]);
                cli.setFechallamada((Date)clienteIt[4]);
                detalle.add(cli);
            }
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazonsocial());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefono());
            parametros.put("titulo", titulo);
            parametros.put("usuario",empleado+"");
            Date desdeF = desde;
            Date hastaF = desde;
            
            desdeF.setHours(desdehora2.getHours());
            desdeF.setMinutes(desdehora2.getMinutes());
            desdeF.setSeconds(desdehora2.getSeconds());
             
            
            hastaF.setHours(hastahora2.getHours());
            hastaF.setMinutes(hastahora2.getMinutes());
            hastaF.setSeconds(hastahora2.getSeconds());
             
            
            parametros.put("desde", desdeF);
            parametros.put("hasta", hastaF);
            parametros.put("titulo", titulo);
           ArrayList informacionRegresa = new ArrayList();
            informacionRegresa.add(parametros);
            informacionRegresa.add(ds);
            informacionRegresa.add(dirreporte); return informacionRegresa;
            
          
        } catch (Exception ex) {
            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
  
    //CALIFICACIONES +++++++++++++++++++++++++++++++++++++++++++++++++++
    public ArrayList generarReporte(String dirreporte, String query, String titulo) {
        try {
            
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            ArrayList detalle = new ArrayList();
            List fac = adm.queryNativo(query);
            for (Iterator it = fac.iterator(); it.hasNext();) {
                Empleados cli = new Empleados();
                Object[] clienteIt = (Object[]) it.next();
                Integer codigoemp = (Integer) clienteIt[0];
                String apellidos = (String) clienteIt[1];
                String nombres = (String) clienteIt[2];
                String calificacion = (String) clienteIt[3];
                Long cantidad = (Long) clienteIt[4];
                cli.setApellidos(apellidos);
                cli.setNombres(nombres);
                cli.setCodigoemp(codigoemp);
                cli.setDireccion(calificacion);
                cli.setTipousuario(cantidad.intValue());
                detalle.add(cli);
            }

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazonsocial());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefono());
            parametros.put("titulo", titulo);
            parametros.put("empleado",empleado+"");
            Date desdeF = desde;
            Date hastaF = desde;
            
            desdeF.setHours(desdehora2.getHours());
            desdeF.setMinutes(desdehora2.getMinutes());
            desdeF.setSeconds(desdehora2.getSeconds());
             
            
            hastaF.setHours(hastahora2.getHours());
            hastaF.setMinutes(hastahora2.getMinutes());
            hastaF.setSeconds(hastahora2.getSeconds());
             
            
            parametros.put("desde", desdeF);
            parametros.put("hasta", hastaF);
            parametros.put("titulo", titulo);
            ArrayList informacionRegresa = new ArrayList();
            informacionRegresa.add(parametros);
            informacionRegresa.add(ds);
            informacionRegresa.add(dirreporte); return informacionRegresa;
            
          
        } catch (Exception ex) {
            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public ArrayList generarReporteMeses(String dirreporte, String query, String titulo) {
        try {
            
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            ArrayList detalle = new ArrayList();
            List fac = adm.queryNativo(query);
            for (Iterator it = fac.iterator(); it.hasNext();) {
                Empleados cli = new Empleados();
                Object[] clienteIt = (Object[]) it.next();
                Integer anio = (Integer) clienteIt[0];//anio apellido
                Integer mes = (Integer) clienteIt[1];//mes nombres
                String calificacion = (String) clienteIt[2];
                Long cantidad = (Long) clienteIt[3];
                cli.setApellidos(anio + "");
                cli.setNombres(mes + ". " + mes(mes));
                cli.setCodigoemp(mes);
                cli.setDireccion(calificacion);
                cli.setTipousuario(cantidad.intValue());
                detalle.add(cli);
            }

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazonsocial());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefono());
            parametros.put("titulo", titulo);
            parametros.put("empleado",empleado+"");
            Date desdeF = desde;
            Date hastaF = desde;
            
            desdeF.setHours(desdehora2.getHours());
            desdeF.setMinutes(desdehora2.getMinutes());
            desdeF.setSeconds(desdehora2.getSeconds());
             
            
            hastaF.setHours(hastahora2.getHours());
            hastaF.setMinutes(hastahora2.getMinutes());
            hastaF.setSeconds(hastahora2.getSeconds());
             
            
            parametros.put("desde", desdeF);
            parametros.put("hasta", hastaF);
            parametros.put("titulo", titulo);
            
                ArrayList informacionRegresa = new ArrayList();
            informacionRegresa.add(parametros);
            informacionRegresa.add(ds);
            informacionRegresa.add(dirreporte); return informacionRegresa;
            
          
        } catch (Exception ex) {
            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public ArrayList generarReporteTotal(String dirreporte, String query, String titulo) {
        try {
            
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            ArrayList detalle = new ArrayList();
            List fac = adm.queryNativo(query);
            for (Iterator it = fac.iterator(); it.hasNext();) {
                Empleados cli = new Empleados();
                Object[] clienteIt = (Object[]) it.next();
                String calificacion = (String) clienteIt[0];
                Long cantidad = (Long) clienteIt[1];
                cli.setDireccion(calificacion);
                cli.setTipousuario(cantidad.intValue());
                detalle.add(cli);
            }

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazonsocial());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefono());
            parametros.put("titulo", titulo);
            parametros.put("usuario",empleado+"");
            Date desdeF = desde;
            Date hastaF = desde;
            
            desdeF.setHours(desdehora2.getHours());
            desdeF.setMinutes(desdehora2.getMinutes());
            desdeF.setSeconds(desdehora2.getSeconds());
             
            
            hastaF.setHours(hastahora2.getHours());
            hastaF.setMinutes(hastahora2.getMinutes());
            hastaF.setSeconds(hastahora2.getSeconds());
             
            
            parametros.put("desde", desdeF);
            parametros.put("hasta", hastaF);
            parametros.put("titulo", titulo);
            
            ArrayList informacionRegresa = new ArrayList();
            informacionRegresa.add(parametros);
            informacionRegresa.add(ds);
            informacionRegresa.add(dirreporte);
            return informacionRegresa;
            
          
        } catch (Exception ex) {
            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public ArrayList generarReporteTotalMeses(String dirreporte, String query, String titulo) {
        try {
            
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            ArrayList detalle = new ArrayList();
            List fac = adm.queryNativo(query);
            for (Iterator it = fac.iterator(); it.hasNext();) {
                Empleados cli = new Empleados();
                Object[] clienteIt = (Object[]) it.next();
                Integer mes = (Integer) clienteIt[0];
                Integer anio = (Integer) clienteIt[1];
                String calificacion = (String) clienteIt[2];
                Long cantidad = (Long) clienteIt[3];
                cli.setCodigoemp(mes);
                cli.setApellidos("" + anio);
                cli.setNombres(mes(mes));
                cli.setDireccion(calificacion);
                cli.setTipousuario(cantidad.intValue());
                detalle.add(cli);
            }

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(detalle);
            Map parametros = new HashMap();
            parametros.put("empresa", emp.getRazonsocial());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefono());
            parametros.put("titulo", titulo);
            parametros.put("usuario",empleado+"");
            Date desdeF = desde;
            Date hastaF = desde;
            
            desdeF.setHours(desdehora2.getHours());
            desdeF.setMinutes(desdehora2.getMinutes());
            desdeF.setSeconds(desdehora2.getSeconds());
             
            
            hastaF.setHours(hastahora2.getHours());
            hastaF.setMinutes(hastahora2.getMinutes());
            hastaF.setSeconds(hastahora2.getSeconds());
             
            
            parametros.put("desde", desdeF);
            parametros.put("hasta", hastaF);
            parametros.put("titulo", titulo);
            ArrayList informacionRegresa = new ArrayList();
            informacionRegresa.add(parametros);
            informacionRegresa.add(ds);
            informacionRegresa.add(dirreporte); 
            return informacionRegresa;
           
        } catch (Exception ex) {
            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
return null;
    }

}
