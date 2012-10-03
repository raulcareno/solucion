/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean.sources;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import jcinform.bean.sources.clasestmp.InventarioNormal;
import jcinform.bean.sources.clasestmp.Pendientes;
import jcinform.conexion.Administrador;
import jcinform.persistencia.*;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Listitem;

/**
 *
 * @author jcinform
 */
public class ReportesClase {

    Sucursal sucursal = null;

    public ReportesClase() {
        Session ses = Sessions.getCurrent();
        Empleadossucursal sucursalEmp = (Empleadossucursal) ses.getAttribute("sector");
        sucursal = sucursalEmp.getSucursal();
    }

    public JRDataSource clientesxsector(Sector ini, Sector fin, String letraini, String letrafin, String estado) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        String complemento = " and substring(o.clientes.apellidos,1,1) >= '" + letraini + "' "
                + " and substring(o.clientes.apellidos,1,1) <= '" + letrafin + "' ";

        List<Contratos> contra = adm.query("Select o from Contratos as o "
                + "where o.sector.numero "
                + "between  '" + ini.getNumero() + "' and   '" + fin.getNumero() + "' " + complemento
                + " and o.sucursal.codigo = '" + sucursal.getCodigo() + "' "
                + "and o.estado = '" + estado + "' "
                + "order by o.clientes.apellidos");
        for (Iterator<Contratos> it = contra.iterator(); it.hasNext();) {
            Contratos contratos = it.next();
            detalles.add(contratos);
        }
        ReporteContratoDataSource ds = new ReporteContratoDataSource(detalles);
        return ds;
    }

    public JRDataSource clientesxnodo(Nodos nodo, String estado) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        String complemento = " and  o.radios.nodos.codigo = '" + nodo.getCodigo() + "' ";
        if (nodo.getCodigo().equals(new Integer(-1))) {
            complemento = "";
        }
        String estadoComp = " and o.estado = '" + estado + "' ";
        if (estado.equals("Todos")) {
            estadoComp = " and  o.estado in ('Activo','Cortado','Suspendido') ";
        }

        List<Contratos> contra = adm.query("Select o from Contratos as o "
                + "where o.sucursal.codigo = '" + sucursal.getCodigo() + "' "
                + " " + estadoComp + complemento
                + " order by o.radios.nombre, o.clientes.apellidos ");
        for (Iterator<Contratos> it = contra.iterator(); it.hasNext();) {
            Contratos contratos = it.next();
            detalles.add(contratos);
        }
        ReporteContratoDataSource ds = new ReporteContratoDataSource(detalles);
        return ds;
    }

    public JRDataSource clientesxestadoplan(String estado, List planes, String tipo) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        String comple = "";
        String complemento2 = "";
        if (!tipo.equals("TODOS")) {
            comple = " and o.plan.tipo = '" + tipo + "' ";
        }
        String estadoComp = " and o.estado = '" + estado + "' ";
        if (estado.equals("Todos")) {
            estadoComp = "";
        }
        Boolean todoslosPlanes = false;
        String codigosPlanes = "";
        if (planes != null) {
            for (Iterator it = planes.iterator(); it.hasNext();) {
                Listitem object = (Listitem) it.next();
                if (((Plan) object.getValue()).getCodigo().equals(-1)) {
                    todoslosPlanes = true;
                    break;
                }
                codigosPlanes += ((Plan) object.getValue()).getCodigo() + ",";
            }
            if (codigosPlanes.length() > 0) {
                codigosPlanes = codigosPlanes.substring(0, codigosPlanes.length() - 1);
            }

            if (todoslosPlanes == false) {
                complemento2 = " and o.plan.codigo in (" + codigosPlanes + ") ";
            }

        }

        List<Contratos> contra = adm.query("Select o from Contratos as o "
                + " where  o.sucursal.codigo = '" + sucursal.getCodigo() + "' " + comple + complemento2
                + estadoComp
                + "order by o.clientes.apellidos");
        for (Iterator<Contratos> it = contra.iterator(); it.hasNext();) {
            Contratos contratos = it.next();
            detalles.add(contratos);
        }
        ReporteContratoDataSource ds = new ReporteContratoDataSource(detalles);
        return ds;
    }

    public JRDataSource clientesxestado(String estado,Integer formapago) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        String estadoComp = " and o.estado = '" + estado + "' ";
        if (estado.equals("Todos")) {
            estadoComp = "";
        }
        
         String formaPago = " and o.formapago = '" + formapago + "' ";
        if (formapago.equals(0)) {
            formaPago = " and o.formapago in (0,1,2,3) ";
        }
        
        List<Contratos> contra = adm.query("Select o from Contratos as o "
                + " where o.sucursal.codigo = '" + sucursal.getCodigo() + "' "
                + estadoComp  + " " + formaPago
                + " order by o.clientes.apellidos");
        for (Iterator<Contratos> it = contra.iterator(); it.hasNext();) {
            Contratos contratos = it.next();
            detalles.add(contratos);
        }
        ReporteContratoDataSource ds = new ReporteContratoDataSource(detalles);
        return ds;
    }

    public JRDataSource equiposclientesxsectorContador(Sector ini, Sector fin, String letraini, String letrafin, String estado) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        String complemento = " and substring(o.contratos.clientes.apellidos,1,1) >= '" + letraini + "' "
                + " and substring(o.contratos.clientes.apellidos,1,1) <= '" + letrafin + "' ";

        List<Series> contra = adm.query("Select o from Series as o "
                + "where o.contratos.sector.numero "
                + "between  '" + ini.getNumero() + "' and   '" + fin.getNumero() + "' " + complemento
                + "and o.estado = 'P' "
                + "and o.contratos.sucursal.codigo = '" + sucursal.getCodigo() + "' "
                + "and o.contratos.estado = '" + estado + "' "
                + "order by o.contratos.clientes.apellidos");
        for (Iterator<Series> it = contra.iterator(); it.hasNext();) {
            Series ser = it.next();
            Contratos contratos = ser.getContratos();
            contratos.setSerie1(ser.getDetallecompra().getEquipos().getNombre()); // EQUIPO SERIE1
            //contratos.setSerie2(ser.getDetallecompra().getCabeceracompra());
            contratos.setSerie3(ser.getSerie()); // SERIE SERIE3
            detalles.add(contratos);
        }
        ReporteContratoDataSource ds = new ReporteContratoDataSource(detalles);
        return ds;
    }

    public JRDataSource equiposclientesxsector(Sector ini, Sector fin, String letraini, String letrafin, String estado) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        String complemento = " and substring(o.contratos.clientes.apellidos,1,1) >= '" + letraini + "' "
                + " and substring(o.contratos.clientes.apellidos,1,1) <= '" + letrafin + "' ";

        List<Series> contra = adm.query("Select o from Series as o "
                + "where o.contratos.sector.numero "
                + "between  '" + ini.getNumero() + "' and   '" + fin.getNumero() + "' " + complemento
                + "and o.estado = 'P' "
                + "and o.contratos.sucursal.codigo = '" + sucursal.getCodigo() + "' "
                + "and o.contratos.estado = '" + estado + "' "
                + "order by o.contratos.clientes.apellidos");
        for (Iterator<Series> it = contra.iterator(); it.hasNext();) {
            Series ser = it.next();
            Contratos contratos = ser.getContratos();
            contratos.setSerie1(ser.getDetallecompra().getEquipos().getNombre()); // EQUIPO SERIE1
            //contratos.setSerie2(ser.getDetallecompra().getCabeceracompra());
            contratos.setSerie3(ser.getSerie()); // SERIE SERIE3
            detalles.add(contratos);
        }
        ReporteContratoDataSource ds = new ReporteContratoDataSource(detalles);
        return ds;
    }

    public JRDataSource facturasPendientes(Clientes cli, Sector sec, Canton canton, Date desde, Date hasta, Boolean todasLasFechas, Integer formapago) {
        Administrador adm = new Administrador();
        List<Clientes> clientes = new ArrayList<Clientes>();
        String desdestr = convertiraString(desde) + "";
        String hastastr = convertiraString(hasta) + "";
        String compleme = " and fa.fecha between '" + desdestr + "' and  '" + hastastr + "' ";
        if (todasLasFechas) {
            compleme = "";
        }
        String formaPago = " and o.formapago = '" + formapago + "' ";
        if (formapago.equals(0)) {
            formaPago = " and o.formapago in (0,1,2,3) ";
        }
        if (cli.getCodigo().equals(-1)) {


            if (sec.getCodigo().equals(-1)) {

                if (canton.getCodigo().equals(-1)) {
                    clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                            + "where  o.sucursal.codigo = '" + sucursal.getCodigo() + "' "
                            + " " + formaPago
                            + "order by o.clientes.apellidos");

                } else {
                    clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                            + "where o.sector.canton.codigo = '" + canton.getCodigo() + "' and  o.sucursal.codigo = '" + sucursal.getCodigo() + "' "
                            + " " + formaPago
                            + " order by o.clientes.apellidos");

                }
            } else if (canton.getCodigo().equals(-1)) {

                clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                        + "where  o.sucursal.codigo = '" + sucursal.getCodigo() + "'  "
                        + " " + formaPago
                        + " order by o.clientes.apellidos");


            } else {
                clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                        + "where o.sector.codigo = '" + sec.getCodigo() + "' "
                        + " and o.sucursal.codigo = '" + sucursal.getCodigo() + "' "
                        + " " + formaPago
                        + " order by o.clientes.apellidos");

            }



        } else {
            cli = (Clientes) adm.buscarClave(cli.getCodigo(), Clientes.class);
            clientes.add(cli);
        }
        ArrayList detalles = new ArrayList();
        String quer = "";
        for (Iterator<Clientes> itCli = clientes.iterator(); itCli.hasNext();) {
            Clientes clientes1 = itCli.next();
            quer = "SELECT fa.codigo, fa.fecha, fa.total,  (SUM(cx.debe) - SUM(cx.haber)) saldo, fa.contratos "
                    + "FROM cxcobrar cx, factura  fa "
                    + " WHERE fa.clientes  =  " + clientes1.getCodigo() + "  "
                    + "  AND cx.factura = fa.codigo"
                    + compleme
                    + " GROUP BY fa.codigo  "
                    + " HAVING  (SUM(cx.debe) - SUM(cx.haber)) > 0 order by fa.contratos, fa.fecha ";

            List facEncontradas = adm.queryNativo(quer);

            if (facEncontradas.size() > 0) {
                Pendientes pendi = null;
                for (Iterator itna = facEncontradas.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();
                    pendi = new Pendientes();
                    pendi.setCliente(clientes1);
                    pendi.setFactura("" + vec.get(0));
                    Date d = (Date) vec.get(1);
                    pendi.setFecha(d);
                    Contratos c = (Contratos) adm.buscarClave(vec.get(4), Contratos.class);
                    pendi.setContratos(c);
                    pendi.setPlan(c.getPlan() + "");
                    pendi.setDireccion(c.getDireccion());
                    pendi.setContrato(c.getContrato()+"");
                    pendi.setTelefono(c.getTelefono()+" "+c.getTelefonof());
                    pendi.setTotal((BigDecimal) vec.get(2));
                    pendi.setSaldo((BigDecimal) vec.get(3));
                    detalles.add(pendi);
                }

            }

        }
        System.out.println("" + quer);
        ReportePendientesDataSource ds = new ReportePendientesDataSource(detalles);
        return ds;
    }

    public JRDataSource facturasPendientesest(Sector sec, Canton canton, String estado, Integer formapago) {
        Administrador adm = new Administrador();
        List<Clientes> clientes = new ArrayList<Clientes>();
        String estadoComp = " and o.estado = '" + estado + "' ";
        if (estado.equals("Todos")) {
            estadoComp = "";
        }
        String formaPago = " and o.formapago = '" + formapago + "' ";
        if (formapago.equals(0)) {
            formaPago = " and o.formapago in (0,1,2,3) ";
        }
        if (sec.getCodigo().equals(-1)) {
            if (canton.getCodigo().equals(-1)) {
                clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                        + "where  o.sucursal.codigo = '" + sucursal.getCodigo() + "' " + estadoComp + " "
                        + " " + formaPago
                        + " order by o.clientes.apellidos");
            } else {
                clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                        + "where o.sector.canton.codigo = '" + canton.getCodigo() + "'  "
                        + " and  o.sucursal.codigo = '" + sucursal.getCodigo() + "' " + estadoComp + "   "
                        + " " + formaPago
                        + " order by o.clientes.apellidos");

            }
        } else if (canton.getCodigo().equals(-1)) {

            clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                    + "where  o.sucursal.codigo = '" + sucursal.getCodigo() + "'  "
                    + "and o.estado = '" + estado + "'  "
                    + " " + formaPago
                    + " order by o.clientes.apellidos");


        } else {
            clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                    + "where o.sector.codigo = '" + sec.getCodigo() + "' "
                    + " and o.sucursal.codigo = '" + sucursal.getCodigo() + "' "
                    + " and o.estado = '" + estado + "'  "
                    + " " + formaPago
                    + " order by o.clientes.apellidos");

        }

        ArrayList detalles = new ArrayList();
        String quer = "";
        for (Iterator<Clientes> itCli = clientes.iterator(); itCli.hasNext();) {
            Clientes clientes1 = itCli.next();
            quer = "SELECT fa.codigo, fa.fecha, fa.total,  (SUM(cx.debe) - SUM(cx.haber)) saldo, fa.contratos "
                    + "FROM cxcobrar cx, factura  fa "
                    + " WHERE fa.clientes  =  " + clientes1.getCodigo() + "  "
                    + "  AND cx.factura = fa.codigo GROUP BY fa.codigo  "
                    + " HAVING  (SUM(cx.debe) - SUM(cx.haber)) > 0 order by fa.contratos, fa.fecha ";

            List facEncontradas = adm.queryNativo(quer);

            if (facEncontradas.size() > 0) {
                Pendientes pendi = null;
                for (Iterator itna = facEncontradas.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();
                    pendi = new Pendientes();
                    pendi.setCliente(clientes1);
                    pendi.setFactura("" + vec.get(0));
                    Date d = (Date) vec.get(1);
                    pendi.setFecha(d);
                    Contratos c = (Contratos) adm.buscarClave(vec.get(4), Contratos.class);
                    pendi.setPlan(c.getPlan() + "");
                    pendi.setDireccion(c.getDireccion());
                    pendi.setTotal((BigDecimal) vec.get(2));
                    pendi.setSaldo((BigDecimal) vec.get(3));
                    pendi.setTelefono(clientes1.getTelefono());
                    detalles.add(pendi);
                }

            }

        }
        System.out.println("" + quer);
        ReportePendientesDataSource ds = new ReportePendientesDataSource(detalles);
        return ds;
    }

    public JRDataSource facturasPendientesestven(Sector sec, Canton canton, String estado, List emp) {
        Administrador adm = new Administrador();
        List<Clientes> clientes = new ArrayList<Clientes>();
        String estadoComp = " and o.estado = '" + estado + "' ";
        if (estado.equals("Todos")) {
            estadoComp = "";
        }
        String compleEmpleado = "";
        String codigosEmpleados = "";


        for (Iterator it = emp.iterator(); it.hasNext();) {
            Listitem object = (Listitem) it.next();
            Empleados empleA = ((Empleados) object.getValue());
            codigosEmpleados += empleA.getCodigo() + ",";
            if (empleA.getCodigo().equals(new Integer(-1))) {
                codigosEmpleados = "";
                break;
            }

        }
        if (codigosEmpleados.length() > 0) {
            compleEmpleado = "  and o.empleados2.codigo in (" + codigosEmpleados.substring(0, codigosEmpleados.length() - 1) + ") ";
        }

        if (sec.getCodigo().equals(-1)) {
            if (canton.getCodigo().equals(-1)) {
                clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                        + "where  o.sucursal.codigo = '" + sucursal.getCodigo() + "' " + estadoComp + " " + compleEmpleado + "  order by o.clientes.apellidos");
            } else {
                clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                        + "where o.sector.canton.codigo = '" + canton.getCodigo() + "'  "
                        + "and  o.sucursal.codigo = '" + sucursal.getCodigo() + "' " + estadoComp + " " + compleEmpleado + "     order by o.clientes.apellidos");

            }
        } else if (canton.getCodigo().equals(-1)) {

            clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                    + "where  o.sucursal.codigo = '" + sucursal.getCodigo() + "'  and o.estado = '" + estado + "' " + compleEmpleado + "    order by o.clientes.apellidos");


        } else {
            clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                    + "where o.sector.codigo = '" + sec.getCodigo() + "' "
                    + " and o.sucursal.codigo = '" + sucursal.getCodigo() + "'  and o.estado = '" + estado + "' " + compleEmpleado + "    order by o.clientes.apellidos, o.empleados2.codigo ");

        }

        ArrayList detalles = new ArrayList();
        String quer = "";
        for (Iterator<Clientes> itCli = clientes.iterator(); itCli.hasNext();) {
            Clientes clientes1 = itCli.next();
            quer = "SELECT fa.codigo, fa.fecha, fa.total,  (SUM(cx.debe) - SUM(cx.haber)) saldo, fa.contratos "
                    + "FROM cxcobrar cx, factura  fa "
                    + " WHERE fa.clientes  =  " + clientes1.getCodigo() + "  "
                    + "  AND cx.factura = fa.codigo GROUP BY fa.codigo  "
                    + " HAVING  (SUM(cx.debe) - SUM(cx.haber)) > 0 order by fa.contratos, fa.fecha ";
            System.out.println("" + quer);
            List facEncontradas = adm.queryNativo(quer);

            if (facEncontradas.size() > 0) {
                Pendientes pendi = null;
                for (Iterator itna = facEncontradas.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();
                    pendi = new Pendientes();
                    pendi.setCliente(clientes1);
                    pendi.setFactura("" + vec.get(0));
                    Date d = (Date) vec.get(1);
                    pendi.setFecha(d);
                    Contratos c = (Contratos) adm.buscarClave(vec.get(4), Contratos.class);
                    pendi.setPlan(c.getPlan() + "");
                    pendi.setContrato(c.getContrato() + "");
                    pendi.setTelefono(c.getTelefono() + " " + c.getTelefonof());
                    pendi.setDireccion(c.getDireccion());
                    pendi.setTotal((BigDecimal) vec.get(2));
                    pendi.setSaldo((BigDecimal) vec.get(3));
                    pendi.setEmpleado(c.getEmpleados2() + "");
                    detalles.add(pendi);
                }

            }

        }
        System.out.println("" + quer);
        ReportePendientesDataSource ds = new ReportePendientesDataSource(detalles);
        return ds;
    }

    public JRDataSource facturasPendientes(Sector sec) {
        Administrador adm = new Administrador();
        List<Clientes> clientes = new ArrayList<Clientes>();
//        if (cli.getCodigo().equals(-1)) {
        //clientes = adm.query("Select o from Clientes as o order by o.apellidos");
        clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                + "where o.sector.codigo = '" + sec.getCodigo() + "' "
                + "  and o.sucursal.codigo = '" + sucursal.getCodigo() + "'  order by o.clientes.apellidos");
//        } else {
//            cli = (Clientes) adm.buscarClave(cli.getCodigo(), Clientes.class);
//            clientes.add(cli);
//        }
        ArrayList detalles = new ArrayList();
        for (Iterator<Clientes> itCli = clientes.iterator(); itCli.hasNext();) {
            Clientes clientes1 = itCli.next();
            String quer = "SELECT fa.codigo, fa.fecha, fa.total,  (SUM(cx.debe) - SUM(cx.haber)) saldo, fa.contratos "
                    + "FROM cxcobrar cx, factura  fa "
                    + " WHERE  fa.clientes  =  " + clientes1.getCodigo() + "  "
                    + " AND fa.sucursal = '" + sucursal.getCodigo() + "'  AND cx.factura = fa.codigo GROUP BY fa.codigo  "
                    + " HAVING  (SUM(cx.debe) - SUM(cx.haber)) > 0 order by fa.contratos, fa.fecha ";

            List facEncontradas = adm.queryNativo(quer);
            System.out.println("" + quer);
            if (facEncontradas.size() > 0) {
                Pendientes pendi = null;
                for (Iterator itna = facEncontradas.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();
                    pendi = new Pendientes();
                    pendi.setCliente(clientes1);
                    pendi.setFactura("" + vec.get(0));
                    Date d = (Date) vec.get(1);
                    pendi.setFecha(d);
                    Contratos c = (Contratos) adm.buscarClave(vec.get(4), Contratos.class);
                    pendi.setPlan(c.getPlan() + "");
                    pendi.setContrato(c.getContrato()+"");
                    pendi.setTelefono(c.getTelefono());
                    pendi.setDireccion(c.getDireccion());
                    pendi.setTotal((BigDecimal) vec.get(2));
                    pendi.setSaldo((BigDecimal) vec.get(3));
                    detalles.add(pendi);
                }

            }

        }
        ReportePendientesDataSource ds = new ReportePendientesDataSource(detalles);
        return ds;
    }

    public JRDataSource facturasCobradas(Clientes cli, Date desde, Date hasta, Sector sec) {
        Administrador adm = new Administrador();
        List<Clientes> clientes = new ArrayList<Clientes>();
        if (cli.getCodigo().equals(-1)) {
            //clientes = adm.query("Select o from Clientes as o order by o.apellidos");
            if (sec.getCodigo().equals(-1)) {
                clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                        + "   order by o.clientes.apellidos");
            } else {
                clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                        + "where o.sector.codigo = '" + sec.getCodigo() + "' "
                        + "   order by o.clientes.apellidos");
            }

        } else {
            cli = (Clientes) adm.buscarClave(cli.getCodigo(), Clientes.class);
            clientes.add(cli);
        }
        ArrayList detalles = new ArrayList();
        String desdestr = convertiraString(desde);
        String hastastr = convertiraString(hasta);
        for (Iterator<Clientes> itCli = clientes.iterator(); itCli.hasNext();) {
            Clientes clientes1 = itCli.next();

            List facEncontradas = adm.queryNativo("SELECT fa.codigo, fa.numero, fa.fecha, p.nombre, fa.total,  (SUM(cx.debe) - SUM(cx.haber)) saldo"
                    + " FROM plan p, detalle de, cxcobrar cx, factura  fa "
                    + " WHERE p.codigo = de.plan AND  de.factura = fa.codigo "
                    + "AND fa.clientes  =  " + clientes1.getCodigo() + "  "
                    + " AND fa.sucursal = '" + sucursal.getCodigo() + "' "
                    + " AND cx.factura = fa.codigo "
                    + "AND cx.fecha between '" + desdestr + "' and '" + hastastr + "' "
                    + "GROUP BY fa.codigo  ");
            if (facEncontradas.size() > 0) {
                Pendientes pendi = null;
                for (Iterator itna = facEncontradas.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();

                    List<Cxcobrar> abonos = adm.query("Select o from Cxcobrar as o where o.haber > 0 "
                            + " and o.factura.codigo = '" + vec.get(0) + "'");
                    int i = 1;
                    for (Iterator<Cxcobrar> itAbono = abonos.iterator(); itAbono.hasNext();) {
                        Cxcobrar cIt = itAbono.next();
                        pendi = new Pendientes();
                        pendi.setCliente(clientes1);
                        pendi.setFactura("" + vec.get(1));
                        Date d = (Date) vec.get(2);
                        pendi.setFecha(d);
                        pendi.setPlan((String) vec.get(3));
                        pendi.setTotal((BigDecimal) vec.get(4));
                        pendi.setSaldo((BigDecimal) vec.get(5));
                        pendi.setNoabono(cIt.getCodigo());
                        pendi.setFechapago(cIt.getFecha());
                        pendi.setValorabonoefe(cIt.getEfectivo());
                        pendi.setValorabonoche(cIt.getCheque());
                        pendi.setValorabonodep(cIt.getDeposito() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getDeposito());
                        pendi.setValorabonotar(cIt.getTarjeta() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getTarjeta());
                        pendi.setValorabonoban(cIt.getBancario() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getBancario());
                        pendi.setNocheque(cIt.getNocheque());
                        pendi.setNocuenta(cIt.getNocuenta());
                        pendi.setNotarjeta(cIt.getNotarjeta());
                        pendi.setValorabonoret(cIt.getRtotal() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getRtotal());
                        detalles.add(pendi);
                        i++;
                    }
                }

            }

        }
        ReportePendientesDataSource ds = new ReportePendientesDataSource(detalles);
        return ds;
    }

    public JRDataSource facturasCobradasContador(Clientes cli, Date desde, Date hasta, Sector sec) {
        Administrador adm = new Administrador();
        List<Clientes> clientes = new ArrayList<Clientes>();
        if (cli.getCodigo().equals(-1)) {
            //clientes = adm.query("Select o from Clientes as o order by o.apellidos");
            if (sec.getCodigo().equals(-1)) {
                clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                        + "   order by o.clientes.apellidos");
            } else {
                clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                        + "where o.sector.codigo = '" + sec.getCodigo() + "' "
                        + "   order by o.clientes.apellidos");
            }

        } else {
            cli = (Clientes) adm.buscarClave(cli.getCodigo(), Clientes.class);
            clientes.add(cli);
        }
        List<Pendientes> detalles = new ArrayList();
        String desdestr = convertiraString(desde);
        String hastastr = convertiraString(hasta);
        for (Iterator<Clientes> itCli = clientes.iterator(); itCli.hasNext();) {

            Clientes clientes1 = itCli.next();
            if (clientes1.getCodigo().equals(new Integer(309))) {
                System.out.println("" + clientes1);
            }
            String sql = "SELECT fa.codigo, fa.numero, fa.emision,  fa.total,  "
                    + "(SUM(cx.debe) - SUM(cx.haber)) saldo,SUM(cx.haber) abonos, fa.subtotal, fa.valoriva ,fa.contratos "
                    + " FROM detalle de, cxcobrar cx, factura  fa "
                    + " WHERE de.factura = fa.codigo "
                    + "AND fa.clientes  =  " + clientes1.getCodigo() + "  "
                    + " AND fa.sucursal = '" + sucursal.getCodigo() + "' "
                    + " AND cx.factura = fa.codigo "
                    + "AND fa.emision between '" + desdestr + "' and '" + hastastr + "' and fa.numero > 0 "
                    + "GROUP BY fa.numero    ";
            //+ "GROUP BY fa.numero  having SUM(cx.haber) >0  ";
//            System.out.println(""+sql);
            List facEncontradas = adm.queryNativo(sql);
            if (facEncontradas.size() > 0) {
                Pendientes pendi = null;
                for (Iterator itna = facEncontradas.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();
                    int i = 1;
                    List<Detalle> detalleLocal = adm.query("Select o from Detalle as o where o.factura.codigo = '" + vec.get(0) + "'");
                    int ii = 0;
                    pendi = new Pendientes();

                    pendi.setPlan("");
                    for (Iterator<Detalle> it = detalleLocal.iterator(); it.hasNext();) {
                        Detalle detalle = it.next();
                        String pth = " / ";
                        if (ii == 0) {
                            pth = "(" + mes(detalle.getFactura().getFecha().getMonth()) + ")";
                        }
                        if (detalle.getEquipos() != null) {
                            pendi.setPlan(pendi.getPlan() + pth + detalle.getEquipos().getNombre());
                        }
                        if (detalle.getPlan() != null) {
                            pendi.setPlan(pendi.getPlan() + pth + detalle.getPlan().getNombre());
                        }
                        ii++;
                    }
                    detalleLocal = null;

                    pendi.setCliente(clientes1);
                    pendi.setFactura("" + vec.get(1).toString().substring(9));
                    Date d = (Date) vec.get(2);
                    pendi.setFecha(d);
                    pendi.setEmision(d);
                    pendi.setTotal((BigDecimal) vec.get(3));

                    Contratos con = (Contratos) adm.buscarClave((Integer) vec.get(8), Contratos.class);
                    pendi.setContrato(con.getContrato() + "");
                    pendi.setDireccion(con.getDireccionf());
                    pendi.setSaldo((BigDecimal) vec.get(4));
                    pendi.setValorabonoefe((BigDecimal) vec.get(5));

                    pendi.setSubtotal((BigDecimal) vec.get(6));
                    pendi.setIva((BigDecimal) vec.get(7));

                    detalles.add(pendi);
                    i++;

                }

            }

        }
        Collections.sort(detalles);
        List<Facturaanulada> anuladas = adm.queryNativo("Select o.* from Facturaanulada as o "
                + "where o.fecha between '" + desdestr + " 00:01:01' and  '" + hastastr + " 23:59:59' "
                + " and o.numero LIKE '" + sucursal.getSerie1() + sucursal.getSerie2() + "FAC%'", Facturaanulada.class);
        for (Iterator<Facturaanulada> it = anuladas.iterator(); it.hasNext();) {
            Facturaanulada facturaanulada = it.next();
            Pendientes pendi = null;
            pendi = new Pendientes();
            pendi.setPlan("ANULADA");
            Clientes clientes1 = new Clientes();
            clientes1.setApellidos("-");
            clientes1.setNombres("");
            pendi.setCliente(clientes1);
            pendi.setFactura(facturaanulada.getNumero().substring(9));
            pendi.setFecha(facturaanulada.getFecha());
            pendi.setEmision(facturaanulada.getFecha());
            pendi.setTotal(new BigDecimal(0));
            pendi.setDireccion("");
            pendi.setSaldo(new BigDecimal(0));
            pendi.setSubtotal(new BigDecimal(0));
            pendi.setIva(new BigDecimal(0));
            pendi.setValorabonoefe(new BigDecimal(0));
            detalles.add(pendi);
        }

//        for (Iterator<Pendientes> it = detalles.iterator(); it.hasNext();) {
//            Pendientes pendientes = it.next();
//            System.out.println(""+pendientes.getFactura()+ ";"+pendientes.getCliente());
//        }
        Collections.sort(detalles);

//        System.out.println("__________________________________________--");
//       for (Iterator<Pendientes> it = detalles.iterator(); it.hasNext();) {
//            Pendientes pendientes = it.next();
//            System.out.println(""+pendientes.getFactura()+ ";"+pendientes.getCliente());
//        }
        ReportePendientesDataSource ds = new ReportePendientesDataSource(detalles);
        return ds;
    }

    public JRDataSource facturasComision(Empleados emp, Date desde, Date hasta, String estado, List planes) {
        Administrador adm = new Administrador();
        List<Contratos> contratosList = new ArrayList<Contratos>();
        //clientes = adm.query("Select o from Clientes as o order by o.apellidos");
        String estadoComp = " and o.estado = '" + estado + "' ";

        if (estado.equals("Todos")) {
            estadoComp = "";

        }
        String codigosPlanes = "";
        Boolean todoslosPlanes = false;
        String complemento2 = "";
        if (planes != null) {
            for (Iterator it = planes.iterator(); it.hasNext();) {
                Listitem object = (Listitem) it.next();
                if (((Plan) object.getValue()).getCodigo().equals(-1)) {
                    todoslosPlanes = true;
                    break;
                }
                codigosPlanes += ((Plan) object.getValue()).getCodigo() + ",";
            }
            if (codigosPlanes.length() > 0) {
                codigosPlanes = codigosPlanes.substring(0, codigosPlanes.length() - 1);
            }

            if (todoslosPlanes == false) {
                complemento2 = " and o.plan.codigo in (" + codigosPlanes + ") ";
            }

        }
        String desdestr = convertiraString(desde);
        String hastastr = convertiraString(hasta);
        if (emp.getCodigo().equals(-1)) {
            contratosList = adm.query("Select o from Contratos as o WHERE  o.fechainstalacion between '" + desdestr + "' "
                    + " and '" + hastastr + "' " + "  " + estadoComp + complemento2
                    + " order by o.clientes.apellidos");
        } else {
            contratosList = adm.query("Select o from Contratos as o "
                    + " WHERE o.empleados2.codigo = '" + emp.getCodigo() + "'   "
                    + estadoComp + "  AND o.fechainstalacion between '" + desdestr + "' "
                    + " and '" + hastastr + "'  " + complemento2
                    + " order by o.clientes.apellidos");
        }


        List<Pendientes> detalles = new ArrayList();

        for (Iterator<Contratos> itCli = contratosList.iterator(); itCli.hasNext();) {
            Contratos contra = itCli.next();

            Pendientes pendi = new Pendientes();

            pendi.setContratos(contra);
            pendi.setEmpleado(contra.getEmpleados2().toString());
            pendi.setContrato(contra.getContrato() + "");
            pendi.setCliente(contra.getClientes());
            pendi.setFactura("0000000000000000");
            pendi.setFecha(contra.getFechainstalacion());
            pendi.setTotal(new BigDecimal(contra.getPlan().getValor()));
            pendi.setSaldo(null);
            pendi.setPlan(contra.getPlan() + "");
//                    pendi.setValorabonotot(contra.getPlan().getValor());
            pendi.setValorabonoefe(null);
            detalles.add(pendi);


        }

        Collections.sort(detalles);
        ReportePendientesDataSource ds = new ReportePendientesDataSource(detalles);
        return ds;
    }

    public JRDataSource supertel(Date desde, Date hasta, String estado, List planes) {
        Administrador adm = new Administrador();
        List<Contratos> contratosList = new ArrayList<Contratos>();
        //clientes = adm.query("Select o from Clientes as o order by o.apellidos");
        String estadoComp = " and o.estado = '" + estado + "' ";

        if (estado.equals("Todos")) {
            estadoComp = "";

        }
        String codigosPlanes = "";
        Boolean todoslosPlanes = false;
        String complemento2 = "";
        if (planes != null) {
            for (Iterator it = planes.iterator(); it.hasNext();) {
                Listitem object = (Listitem) it.next();
                if (((Plan) object.getValue()).getCodigo().equals(-1)) {
                    todoslosPlanes = true;
                    break;
                }
                codigosPlanes += ((Plan) object.getValue()).getCodigo() + ",";
            }
            if (codigosPlanes.length() > 0) {
                codigosPlanes = codigosPlanes.substring(0, codigosPlanes.length() - 1);
            }

            if (todoslosPlanes == false) {
                complemento2 = " and o.plan.codigo in (" + codigosPlanes + ") ";
            }

        }
        String desdestr = convertiraString(desde);
        String hastastr = convertiraString(hasta);

        contratosList = adm.query("Select o from Contratos as o WHERE  o.fechainstalacion between '" + desdestr + "' "
                + " and '" + hastastr + "' " + "  " + estadoComp + complemento2
                + " order by o.clientes.apellidos");


        List<Contratos> detalles = new ArrayList();

        for (Iterator<Contratos> itCli = contratosList.iterator(); itCli.hasNext();) {
            Contratos contra = itCli.next();
            detalles.add(contra);


        }
//        Collections.sort(detalles);
        ReporteContratoDataSource ds = new ReporteContratoDataSource(detalles);
        return ds;
    }

    public String mes(int mes) {
        switch (mes) {
            case 0:
                return "Ene";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Abr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Ago";
            case 8:
                return "Sep";
            case 9:
                return "Oct";
            case 10:
                return "Nov";
            case 11:
                return "Dic";
        }
        return "";

    }

    public JRDataSource reporteCaja(Date desde, Date hasta) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        String desdestr = convertiraString(desde);
        String hastastr = convertiraString(hasta);
        Pendientes pendi = null;
        List<Cxcobrar> abonos = adm.query("Select o from Cxcobrar as o "
                + "where o.haber > 0 "
                + "and o.factura.sucursal.codigo = '" + sucursal.getCodigo() + "' "
                + "and o.fecha between  '" + desdestr + "'  and '" + hastastr + "' order by o.factura.numero");
        int i = 1;
        for (Iterator<Cxcobrar> itAbono = abonos.iterator(); itAbono.hasNext();) {
            Cxcobrar cIt = itAbono.next();
            pendi = new Pendientes();
            pendi.setFactura("" + cIt.getFactura().getNumero());
            pendi.setFecha(cIt.getFecha());
            pendi.setPlan("");
            pendi.setTotal(new BigDecimal(0));
            try {
                pendi.setCliente(cIt.getFactura().getClientes());
            } catch (Exception e) {
            }
            pendi.setSaldo(new BigDecimal(0));
            pendi.setNoabono(cIt.getCodigo());
            pendi.setFechapago(cIt.getFecha());
            pendi.setValorabonoefe(cIt.getEfectivo());
            pendi.setValorabonoche(cIt.getCheque());
            pendi.setValorabonodep(cIt.getDeposito() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getDeposito());
            pendi.setValorabonotar(cIt.getTarjeta() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getTarjeta());
            pendi.setNocheque(cIt.getNocheque());
            pendi.setNocuenta(cIt.getNocuenta());
            pendi.setValorabonotra(cIt.getTransferencia() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getTransferencia());
            pendi.setValorabonorfue(cIt.getRfuente());
            pendi.setValorabonoriva(cIt.getRiva());
            pendi.setValorabonotot(cIt.getRtotal());
            pendi.setValorabonoban(cIt.getBancario() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getBancario());
            pendi.setNumeroretencion(cIt.getNumeroretencion());
            pendi.setNumerotransferencia(cIt.getNotransferencia());
            pendi.setNotarjeta(cIt.getNotarjeta());
            pendi.setValorabonoret(cIt.getRtotal() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getRtotal());
            detalles.add(pendi);
            i++;
        }

        ReportePendientesDataSource ds = new ReportePendientesDataSource(detalles);
        return ds;
    }

    public JRDataSource reporteCaja2(Date desde, Date hasta) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        String desdestr = convertiraString(desde);
        String hastastr = convertiraString(hasta);
        Pendientes pendi = null;
        List<Cxcobrar> abonos = adm.query("Select o from Cxcobrar as o "
                + "where o.haber > 0 "
                + "and o.factura.sucursal.codigo = '" + sucursal.getCodigo() + "' "
                + "and o.fecha between  '" + desdestr + "'  and '" + hastastr + "' order by o.factura.numero");
        int i = 1;
        for (Iterator<Cxcobrar> itAbono = abonos.iterator(); itAbono.hasNext();) {
            Cxcobrar cIt = itAbono.next();
            pendi = new Pendientes();
            pendi.setEnequipos(BigDecimal.ZERO);
            pendi.setEninstalaciones(BigDecimal.ZERO);
            pendi.setEnplanes(BigDecimal.ZERO);
            pendi.setFactura("" + cIt.getFactura().getNumero());
            System.out.println("SI EXISTEN DETALLES" + cIt.getFactura().getDetalleCollection());
            if (cIt.getFactura().getDetalleCollection() != null) {
                for (Detalle deta : cIt.getFactura().getDetalleCollection()) {
                    if (deta.getEquipos() != null) {
                        if (deta.getEquipos().getBien()) {
                            pendi.setEnequipos(deta.getTotal().multiply(new BigDecimal(1.12)).add(pendi.getEnequipos()));
                        } else {
                            pendi.setEninstalaciones(deta.getTotal().multiply(new BigDecimal(1.12)).add(pendi.getEninstalaciones()));
                        }
                    } else if (deta.getPlan() != null) {
                        pendi.setEnplanes(deta.getTotal().multiply(new BigDecimal(1.12)).add(pendi.getEnplanes()));
                    }
                }
            }
            pendi.setFecha(cIt.getFecha());
            pendi.setPlan("");
            pendi.setTotal(new BigDecimal(0));
            try {
                pendi.setCliente(cIt.getFactura().getClientes());
            } catch (Exception e) {
            }
            pendi.setSaldo(new BigDecimal(0));
            pendi.setNoabono(cIt.getCodigo());
            pendi.setFechapago(cIt.getFecha());
            pendi.setValorabonoefe(cIt.getEfectivo());
            pendi.setValorabonoche(cIt.getCheque());
            pendi.setValorabonodep(cIt.getDeposito() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getDeposito());
            pendi.setValorabonotar(cIt.getTarjeta() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getTarjeta());
            pendi.setNocheque(cIt.getNocheque());
            pendi.setNocuenta(cIt.getNocuenta());
            pendi.setValorabonotra(cIt.getTransferencia() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getTransferencia());
            pendi.setValorabonorfue(cIt.getRfuente());
            pendi.setValorabonoriva(cIt.getRiva());
            pendi.setValorabonotot(cIt.getRtotal());
            pendi.setValorabonoban(cIt.getBancario() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getBancario());
            pendi.setNumeroretencion(cIt.getNumeroretencion());
            pendi.setNumerotransferencia(cIt.getNotransferencia());
            pendi.setNotarjeta(cIt.getNotarjeta());
            pendi.setValorabonoret(cIt.getRtotal() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getRtotal());
            detalles.add(pendi);
            i++;
        }

        ReportePendientesDataSource ds = new ReportePendientesDataSource(detalles);
        return ds;
    }

    public JRDataSource reporteCaja(Date desde, Date hasta, List emp) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        String desdestr = convertiraString(desde);
        String hastastr = convertiraString(hasta);
        Pendientes pendi = null;
        String compleEmpleado = "";
        String codigosEmpleados = "";

        for (Iterator it = emp.iterator(); it.hasNext();) {
            Listitem object = (Listitem) it.next();
            Empleados empleA = ((Empleados) object.getValue());
            codigosEmpleados += empleA.getCodigo() + ",";
            if (empleA.getCodigo().equals(new Integer(-1))) {
                codigosEmpleados = "";
                break;
            }

        }
        if (codigosEmpleados.length() > 0) {
            compleEmpleado = "  and o.empleados.codigo in (" + codigosEmpleados.substring(0, codigosEmpleados.length() - 1) + ") ";
        }
        String query = "Select o from Cxcobrar as o "
                + "where o.haber > 0 "
                + "and o.factura.sucursal.codigo = '" + sucursal.getCodigo() + "' "
                + "and o.fecha between  '" + desdestr + "'  and '" + hastastr + "' "
                + compleEmpleado
                + " order by   o.empleados.apellidos,o.factura.numero, o.fecha ";


        List<Cxcobrar> abonos = adm.query(query);
        int i = 1;
        for (Iterator<Cxcobrar> itAbono = abonos.iterator(); itAbono.hasNext();) {
            Cxcobrar cIt = itAbono.next();
            pendi = new Pendientes();
            pendi.setFactura("" + cIt.getFactura().getNumero());
            pendi.setFecha(cIt.getFecha());
            pendi.setPlan("");
            try {
                pendi.setCliente(cIt.getFactura().getClientes());
            } catch (Exception e) {
            }

            pendi.setTotal(new BigDecimal(0));
            pendi.setSaldo(new BigDecimal(0));
            pendi.setNoabono(cIt.getCodigo());
            pendi.setFechapago(cIt.getFecha());
            pendi.setValorabonoefe(cIt.getEfectivo());
            pendi.setValorabonoche(cIt.getCheque());
            pendi.setValorabonodep(cIt.getDeposito() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getDeposito());
            pendi.setValorabonotar(cIt.getTarjeta() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getTarjeta());
            pendi.setNocheque(cIt.getNocheque());
            pendi.setNocuenta(cIt.getNocuenta());
            pendi.setValorabonotra(cIt.getTransferencia() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getTransferencia());
            pendi.setValorabonorfue(cIt.getRfuente());
            pendi.setValorabonoriva(cIt.getRiva());
            pendi.setValorabonotot(cIt.getRtotal());
            pendi.setValorabonoban(cIt.getBancario() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getBancario());
            pendi.setNumeroretencion(cIt.getNumeroretencion());
            pendi.setNumerotransferencia(cIt.getNotransferencia());
            pendi.setValorabonoret(cIt.getRtotal() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getRtotal());
            pendi.setNotarjeta(cIt.getNotarjeta());
            try {
                pendi.setEmpleado(cIt.getEmpleados().getApellidos() + " " + cIt.getEmpleados().getNombres());
            } catch (Exception e) {
//                pendi.setEmpleado("TODOS");    
            }

            detalles.add(pendi);
            i++;
        }

        ReportePendientesDataSource ds = new ReportePendientesDataSource(detalles);
        return ds;
    }

    public JRDataSource reporteCobrosRecaudador(Date desde, Empleados emp) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        String desdestr = convertiraString(desde);
        Pendientes pendi = null;
        List<Empleadosfacturas> abonos = adm.queryNativo("Select o.* from Empleadosfacturas as o "
                + "where date(o.fecha) = '" + desdestr + "' "
                + " and o.empleados = '" + emp.getCodigo() + "' ", Empleadosfacturas.class);
        int i = 1;
        for (Iterator<Empleadosfacturas> itAbono = abonos.iterator(); itAbono.hasNext();) {
            Empleadosfacturas cIt = itAbono.next();
            pendi = new Pendientes();
            pendi.setFactura("" + cIt.getFactura().getNumero());
            pendi.setFecha(cIt.getFecha());
            pendi.setCliente(cIt.getFactura().getClientes());
            pendi.setPlan("");
            pendi.setTotal(cIt.getTotal());
            pendi.setSaldo(new BigDecimal(0));
            pendi.setNoabono(cIt.getCodigo());
            pendi.setFechapago(cIt.getFecha());
            pendi.setValorabonodes(cIt.getDescuento());
            pendi.setValorabonoefe(cIt.getEfectivo());
            pendi.setValorabonoche(cIt.getCheque());
            pendi.setValorabonodep(cIt.getDeposito() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getDeposito());
            pendi.setValorabonoban(cIt.getBancario() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getBancario());
            pendi.setValorabonotar(cIt.getTarjeta() == null ? new BigDecimal(BigInteger.ZERO) : cIt.getTarjeta());
            pendi.setNocheque(cIt.getNocheque());
            pendi.setNocuenta(cIt.getNocuenta());
            pendi.setNotarjeta(cIt.getNotarjeta());
//            pendi.setValorabonoret(cIt.getRtotal());
            detalles.add(pendi);
            i++;
        }

        ReportePendientesDataSource ds = new ReportePendientesDataSource(detalles);
        return ds;
    }

    public static String convertiraString(Date fecha) {

        return (fecha.getYear() + 1900) + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();

    }

    public JRDataSource clientesxsector(Sector sec, String estado) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        String estadoComp = " and o.estado = '" + estado + "' ";
        if (estado.equals("Todos")) {
            estadoComp = "";
        }
        String que = "";

        if (sec.getCodigo().equals(-1)) {
            que = "Select o from Contratos as o "
                    + "where o.sucursal.codigo = '" + sucursal.getCodigo() + "'  "
                    + estadoComp
                    + "order by o.clientes.apellidos";
        } else {
            que = "Select o from Contratos as o "
                    + "where o.sector.codigo =  '" + sec.getCodigo() + "'"
                    + "  and o.sucursal.codigo = '" + sucursal.getCodigo() + "'  "
                    + estadoComp
                    + "order by o.clientes.apellidos";
        }
        List<Contratos> contra = adm.query(que);;

        for (Iterator<Contratos> it = contra.iterator(); it.hasNext();) {
            Contratos contratos = it.next();
            detalles.add(contratos);
        }
        ReporteContratoDataSource ds = new ReporteContratoDataSource(detalles);
        return ds;
    }

    //INVENTARIOS
    public JRDataSource inventarioNormal(Date desde, Date hasta) {
        Administrador adm = new Administrador();
        String desdestr = convertiraString(desde) + "";
        String hastastr = convertiraString(hasta) + "";
        ArrayList detalles = new ArrayList();
        String quer = "SELECT concat(e.nombre,' ', e.modelo,' ', m.nombre), SUM(IF(d.cantidad>0,d.cantidad,0)) entrada, "
                + " SUM(IF(d.cantidad<0 AND c.documento = 'VEN' ,d.cantidad,0)) salida, SUM(IF(d.cantidad<0 AND c.documento = 'AJU' ,d.cantidad,0)) AJUSTE, SUM(IF(d.cantidad<0 AND c.documento = 'PRE' ,d.cantidad,0)) PRESTAMO, (SUM(IF(d.cantidad>0,d.cantidad,0))+SUM(IF(d.cantidad<0,d.cantidad,0))) total "
                + "FROM cabeceracompra c, detallecompra d, equipos e, marcas m WHERE e.codigo = d.equipos AND m.codigo = e.marcas "
                + "AND c.codigo = d.compra  AND e.bien = TRUE AND c.documento IN ('COM','VEN','AJU','PRE')  "
                + " AND c.sucursal = '" + sucursal.getCodigo() + "' "
                + "AND c.fecha between  '" + desdestr + "' and '" + hastastr + "' GROUP BY d.equipos "
                + " order by 1";
        List contra = adm.queryNativo(quer);
        System.out.println("" + quer);
        for (Iterator it = contra.iterator(); it.hasNext();) {
            Vector vec = (Vector) it.next();
            InventarioNormal inv = new InventarioNormal();
            inv.setProducto(vec.get(0) + "");
            inv.setEntrada(new Integer(vec.get(1) + ""));
            inv.setSalida(new Integer(vec.get(2) + ""));
            inv.setAjuste(new Integer(vec.get(3) + ""));
            inv.setPrestamo(new Integer(vec.get(4) + ""));
            inv.setTotal(new Integer(vec.get(5) + ""));
            detalles.add(inv);

        }

        ReporteInventarioNormalDataSource ds = new ReporteInventarioNormalDataSource(detalles);
        return ds;
    }

    public JRDataSource inventarioTransito() {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        List contra = adm.queryNativo("SELECT concat(e.nombre,' ', e.modelo,' ', m.nombre),   "
                + "SUM(IF(d.cantidad<0,d.cantidad,0)) salida "
                + "FROM cabeceracompra c, detallecompra d, equipos e, marcas m "
                + "WHERE e.codigo = d.equipos AND m.codigo = e.marcas "
                + "AND c.codigo = d.compra AND c.sucursal = '" + sucursal.getCodigo() + "' AND c.documento IN ('PRE') "
                + "GROUP BY d.equipos "
                + " order by 1");

        for (Iterator it = contra.iterator(); it.hasNext();) {
            Vector vec = (Vector) it.next();
            InventarioNormal inv = new InventarioNormal();
            inv.setProducto(vec.get(0) + "");
            inv.setTotal(new Integer(vec.get(1) + ""));
            detalles.add(inv);

        }

        ReporteInventarioNormalDataSource ds = new ReporteInventarioNormalDataSource(detalles);
        return ds;
    }

    public JRDataSource inventarioEmpleados(List emp, Date desde, Date hasta) {
        Administrador adm = new Administrador();
        String desdestr = convertiraString(desde) + "";
        String hastastr = convertiraString(hasta) + "";
        ArrayList detalles = new ArrayList();
        String compleEmpleado = "";
        String codigosEmpleados = "";


        for (Iterator it = emp.iterator(); it.hasNext();) {
            Listitem object = (Listitem) it.next();
            Empleados empleA = ((Empleados) object.getValue());
            codigosEmpleados += empleA.getCodigo() + ",";
            if (empleA.getCodigo().equals(new Integer(-1))) {
                codigosEmpleados = "";
                break;
            }

        }
        if (codigosEmpleados.length() > 0) {
            compleEmpleado = "  and o.empleados.codigo in (" + codigosEmpleados.substring(0, codigosEmpleados.length() - 1) + ") ";
        }

        List<Seriesempleados> equivaEncontrados = adm.query("Select o from Seriesempleados as o "
                + " where o.estado = true   " + compleEmpleado
                //+ " and o.estado = true and o.fecha between '"+desdestr+"' and '"+hastastr+"'  "
                + " order by o.empleados.apellidos, o.fecha ");
        for (Iterator<Seriesempleados> it = equivaEncontrados.iterator(); it.hasNext();) {
            Seriesempleados seriesempleados = it.next();
            InventarioNormal inv = new InventarioNormal();
            inv.setProducto(seriesempleados.getSeries().getDetallecompra().getEquipos() + "");
            inv.setSerie(seriesempleados.getSeries().getSerie());
            inv.setFecha(seriesempleados.getFecha());
            inv.setEmpleado(seriesempleados.getEmpleados() + "");
            detalles.add(inv);
        }
        ReporteInventarioNormalDataSource ds = new ReporteInventarioNormalDataSource(detalles);
        return ds;
    }

    public JRDataSource inventarioAjuste() {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        List contra = adm.queryNativo("SELECT concat(e.nombre,' ', e.modelo,' ', m.nombre),   "
                + "SUM(IF(d.cantidad<0,d.cantidad,0)) salida "
                + "FROM cabeceracompra c, detallecompra d, equipos e, marcas m WHERE e.codigo = d.equipos AND m.codigo = e.marcas "
                + "AND c.codigo = d.compra AND c.sucursal = '" + sucursal.getCodigo() + "'  AND c.documento IN ('AJU') "
                + "GROUP BY d.equipos "
                + " order by 1");

        for (Iterator it = contra.iterator(); it.hasNext();) {
            Vector vec = (Vector) it.next();
            InventarioNormal inv = new InventarioNormal();
            inv.setProducto(vec.get(0) + "");
            inv.setTotal(new Integer(vec.get(1) + ""));
            detalles.add(inv);

        }

        ReporteInventarioNormalDataSource ds = new ReporteInventarioNormalDataSource(detalles);
        return ds;
    }

    public JRDataSource inventarioGeneral(Date desde, Date hasta) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        String desdestr = convertiraString(desde) + "";
        String hastastr = convertiraString(hasta) + "";
        List<Cabeceracompra> compras = adm.query("Select o from Cabeceracompra as o "
                + "where o.documento = 'COM' and o.fecha between '" + desdestr + "' and '" + hastastr + "' "
                + " and o.sucursal.codigo = '" + sucursal.getCodigo() + "' ");
        for (Iterator<Cabeceracompra> it = compras.iterator(); it.hasNext();) {
            Cabeceracompra cabeceracompra = it.next();
            List<Detallecompra> detallesC = adm.query("Select o from Detallecompra as o "
                    + " where o.cabeceracompra.codigo = '" + cabeceracompra.getCodigo() + "'");
            for (Iterator<Detallecompra> it1 = detallesC.iterator(); it1.hasNext();) {
                Detallecompra detallecompra = it1.next();
                List<Series> seriesPrestadas = adm.query("Select s from Series as s where s.estado in('P','V','A')  "
                        + "and s.serie in (Select o.serie from Series as o "
                        + "where o.detallecompra.codigo = '" + detallecompra.getCodigo() + "' ) order by s.estado  ");
                for (Iterator<Series> it2 = seriesPrestadas.iterator(); it2.hasNext();) {
                    Series series = it2.next();
                    InventarioNormal inv = new InventarioNormal();
                    inv.setProducto(series.getDetallecompra().getEquipos() + "");
                    inv.setCantidadpro(detallecompra.getCantidad());
                    inv.setEntrada(cabeceracompra.getCantidad());
                    inv.setCompra(cabeceracompra.getCodigo() + "");
                    inv.setFactura(cabeceracompra.getFactura() + "");
                    inv.setSerie(series.getSerie());
                    inv.setProveedor(cabeceracompra.getProveedores().getRazonsocial() + "");
                    inv.setFecha(cabeceracompra.getFecha());
                    if (series.getEstado().equals("A")) { //POR AJUSTE
                        inv.setDocumento(series.getDetallecompra().getCabeceracompra().getSeries());
                        inv.setTipo("AJUSTE");
                        inv.setCantidad(1);
                    } else if (series.getEstado().equals("P")) { //POR PRESTAMO TRANSITO
                        inv.setDocumento(series.getDetallecompra().getContratos().getClientes() + "");
                        inv.setTipo("PRESTAMO");
                        inv.setCantidad(1);
                    } else if (series.getEstado().equals("V")) { //POR VENTA
                        inv.setDocumento(series.getDetallecompra().getContratos().getClientes() + "");
                        inv.setTipo("VENTA");
                        inv.setCantidad(1);
                    }

                    detalles.add(inv);
                }

                List<Series> seriesCompradas = adm.query("Select s from Series as s "
                        + "where s.estado in ('C') "
                        + "and s.detallecompra.codigo  = '" + detallecompra.getCodigo() + "'  and s.serie "
                        + " not in (Select o.serie from Series as o "
                        + "where o.estado in ('P','V','A') and o.sucursal.codigo = '" + sucursal.getCodigo() + "'  )  ");
                for (Iterator<Series> it2 = seriesCompradas.iterator(); it2.hasNext();) {
                    Series series = it2.next();
                    InventarioNormal inv = new InventarioNormal();
                    inv.setCantidadpro(detallecompra.getCantidad());
                    inv.setProducto(series.getDetallecompra().getEquipos() + "");
                    inv.setEntrada(cabeceracompra.getCantidad());
                    inv.setCompra(cabeceracompra.getCodigo() + "");
                    inv.setFactura(cabeceracompra.getFactura() + "");
                    inv.setSerie(series.getSerie());
                    inv.setProveedor(cabeceracompra.getProveedores().getRazonsocial() + "");
                    inv.setDocumento("Bodega");
                    inv.setTipo("STOCK");
                    inv.setCantidad(1);
                    inv.setFecha(cabeceracompra.getFecha());
                    detalles.add(inv);
                }

            }
        }
        ReporteInventarioNormalDataSource ds = new ReporteInventarioNormalDataSource(detalles);
        return ds;
    }

    public JRDataSource inventarioGeneral2(Proveedores proveedore, String numero) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        List<Cabeceracompra> compras = adm.query("Select o from Cabeceracompra as o "
                + " where o.documento = 'COM' and o.factura = '" + numero + "' "
                + " and o.proveedores.codigo = '" + proveedore.getCodigo() + "' "
                + " and o.sucursal.codigo = '" + sucursal.getCodigo() + "' "
                + " ");
        for (Iterator<Cabeceracompra> it = compras.iterator(); it.hasNext();) {
            Cabeceracompra cabeceracompra = it.next();
            List<Detallecompra> detallesC = adm.query("Select o from Detallecompra as o "
                    + " where o.cabeceracompra.codigo = '" + cabeceracompra.getCodigo() + "'");
            for (Iterator<Detallecompra> it1 = detallesC.iterator(); it1.hasNext();) {
                Detallecompra detallecompra = it1.next();
                List<Series> seriesPrestadas = adm.query("Select s from Series as s where s.estado in('P','V','A')  "
                        + "and s.serie in (Select o.serie from Series as o "
                        + "where o.detallecompra.codigo = '" + detallecompra.getCodigo() + "' ) order by s.estado  ");
                for (Iterator<Series> it2 = seriesPrestadas.iterator(); it2.hasNext();) {
                    Series series = it2.next();
                    InventarioNormal inv = new InventarioNormal();
                    inv.setProducto(series.getDetallecompra().getEquipos() + "");
                    inv.setCantidadpro(detallecompra.getCantidad());
                    inv.setEntrada(cabeceracompra.getCantidad());
                    inv.setCompra(cabeceracompra.getCodigo() + "");
                    inv.setFactura(cabeceracompra.getFactura() + "");
                    inv.setSerie(series.getSerie());
                    inv.setProveedor(cabeceracompra.getProveedores().getRazonsocial() + "");
                    inv.setFecha(cabeceracompra.getFecha());
                    if (series.getEstado().equals("A")) { //POR AJUSTE
                        inv.setDocumento(series.getDetallecompra().getCabeceracompra().getSeries());
                        inv.setTipo("AJUSTE");
                        inv.setCantidad(1);
                    } else if (series.getEstado().equals("P")) { //POR PRESTAMO TRANSITO
                        inv.setDocumento(series.getDetallecompra().getContratos().getClientes() + "");
                        inv.setTipo("PRESTAMO");
                        inv.setCantidad(1);
                    } else if (series.getEstado().equals("V")) { //POR VENTA
                        inv.setDocumento(series.getDetallecompra().getContratos().getClientes() + "");
                        inv.setTipo("VENTA");
                        inv.setCantidad(1);
                    }

                    detalles.add(inv);
                }

                List<Series> seriesCompradas = adm.query("Select s from Series as s "
                        + "where s.estado in ('C') "
                        + "and s.detallecompra.codigo  = '" + detallecompra.getCodigo() + "'  and s.serie "
                        + " not in (Select o.serie from Series as o "
                        + "where o.estado in ('P','V','A') and o.sucursal.codigo = '" + sucursal.getCodigo() + "'  )  ");
                for (Iterator<Series> it2 = seriesCompradas.iterator(); it2.hasNext();) {
                    Series series = it2.next();
                    InventarioNormal inv = new InventarioNormal();
                    inv.setCantidadpro(detallecompra.getCantidad());
                    inv.setProducto(series.getDetallecompra().getEquipos() + "");
                    inv.setEntrada(cabeceracompra.getCantidad());
                    inv.setCompra(cabeceracompra.getCodigo() + "");
                    inv.setFactura(cabeceracompra.getFactura() + "");
                    inv.setSerie(series.getSerie());
                    inv.setProveedor(cabeceracompra.getProveedores().getRazonsocial() + "");
                    inv.setDocumento("Bodega");
                    inv.setTipo("STOCK");
                    inv.setCantidad(1);
                    inv.setFecha(cabeceracompra.getFecha());
                    detalles.add(inv);
                }

            }
        }
        ReporteInventarioNormalDataSource ds = new ReporteInventarioNormalDataSource(detalles);
        return ds;
    }

    public JRDataSource inventarioGeneral3(Proveedores proveedore, Date desde, Date hasta, Boolean todasLasFechas) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        String desdestr = convertiraString(desde) + "";
        String hastastr = convertiraString(hasta) + "";
        String compleme = " and o.fecha between '" + desdestr + "' and  '" + hastastr + "' ";
        if (todasLasFechas) {
            compleme = "";
        }
        List<Cabeceracompra> compras = adm.query("Select o from Cabeceracompra as o "
                + " where o.documento = 'COM' "
                + " and o.proveedores.codigo = '" + proveedore.getCodigo() + "' "
                + " and o.sucursal.codigo = '" + sucursal.getCodigo() + "' "
                + compleme
                + "");
        for (Iterator<Cabeceracompra> it = compras.iterator(); it.hasNext();) {
            Cabeceracompra cabeceracompra = it.next();
            List<Detallecompra> detallesC = adm.query("Select o from Detallecompra as o "
                    + " where o.cabeceracompra.codigo = '" + cabeceracompra.getCodigo() + "'");
            for (Iterator<Detallecompra> it1 = detallesC.iterator(); it1.hasNext();) {
                Detallecompra detallecompra = it1.next();
                List<Series> seriesPrestadas = adm.query("Select s from Series as s where s.estado in('P','V','A')  "
                        + "and s.serie in (Select o.serie from Series as o "
                        + "where o.detallecompra.codigo = '" + detallecompra.getCodigo() + "' ) order by s.estado  ");
                for (Iterator<Series> it2 = seriesPrestadas.iterator(); it2.hasNext();) {
                    Series series = it2.next();
                    InventarioNormal inv = new InventarioNormal();
                    inv.setProducto(series.getDetallecompra().getEquipos() + "");
                    inv.setCantidadpro(detallecompra.getCantidad());
                    inv.setEntrada(cabeceracompra.getCantidad());
                    inv.setCompra(cabeceracompra.getCodigo() + "");
                    inv.setFactura(cabeceracompra.getFactura() + "");
                    inv.setSerie(series.getSerie());
                    inv.setProveedor(cabeceracompra.getProveedores().getRazonsocial() + "");
                    inv.setFecha(cabeceracompra.getFecha());
                    if (series.getEstado().equals("A")) { //POR AJUSTE
                        inv.setDocumento(series.getDetallecompra().getCabeceracompra().getSeries());
                        inv.setTipo("AJUSTE");
                        inv.setContrato("");
                        inv.setDireccion("");
                        inv.setCantidad(1);
                    } else if (series.getEstado().equals("P")) { //POR PRESTAMO TRANSITO
                        inv.setDocumento(series.getDetallecompra().getContratos().getClientes() + "");
                        inv.setTipo("PRESTAMO");
                        try {
                            inv.setContrato("" + series.getDetallecompra().getContratos().getContrato());
                            inv.setDireccion("" + series.getDetallecompra().getContratos().getDireccionf());
                        } catch (Exception e) {
                        }
                        inv.setCantidad(1);
                    } else if (series.getEstado().equals("V")) { //POR VENTA
                        inv.setDocumento(series.getDetallecompra().getContratos().getClientes() + "");
                        inv.setTipo("VENTA");
                        try {
                            inv.setContrato("" + series.getDetallecompra().getContratos().getContrato());
                            inv.setDireccion("" + series.getDetallecompra().getContratos().getDireccionf());
                        } catch (Exception e) {
                        }

                        inv.setCantidad(1);
                    }

                    detalles.add(inv);
                }

                List<Series> seriesCompradas = adm.query("Select s from Series as s "
                        + "where s.estado in ('C') "
                        + "and s.detallecompra.codigo  = '" + detallecompra.getCodigo() + "'  and s.serie "
                        + " not in (Select o.serie from Series as o "
                        + "where o.estado in ('P','V','A') and o.sucursal.codigo = '" + sucursal.getCodigo() + "'  )  ");
                for (Iterator<Series> it2 = seriesCompradas.iterator(); it2.hasNext();) {
                    Series series = it2.next();
                    InventarioNormal inv = new InventarioNormal();
                    inv.setCantidadpro(detallecompra.getCantidad());
                    inv.setProducto(series.getDetallecompra().getEquipos() + "");
                    inv.setEntrada(cabeceracompra.getCantidad());
                    inv.setCompra(cabeceracompra.getCodigo() + "");
                    inv.setFactura(cabeceracompra.getFactura() + "");
                    inv.setSerie(series.getSerie());
                    inv.setContrato("");//CARGO VACIO POR QUE ESTÁ EN STOCK 
                    inv.setDireccion("");
                    inv.setProveedor(cabeceracompra.getProveedores().getRazonsocial() + "");
                    inv.setDocumento("Bodega");
                    inv.setTipo("STOCK");
                    inv.setCantidad(1);
                    inv.setFecha(cabeceracompra.getFecha());
                    detalles.add(inv);
                }

            }
        }
        ReporteInventarioNormalDataSource ds = new ReporteInventarioNormalDataSource(detalles);
        return ds;
    }

    public JRDataSource facturasComisiones1(List emp, Date desde, Date hasta) {
        Administrador adm = new Administrador();
        String desdestr = convertiraString(desde) + " 00:00:01";
        String hastastr = convertiraString(hasta) + " 23:59:59";
        List<Contratos> contratos = new ArrayList<Contratos>();

        String compleEmpleado = "";
        String codigosEmpleados = "";
        for (Iterator it = emp.iterator(); it.hasNext();) {
            Listitem object = (Listitem) it.next();
            Empleados empleA = ((Empleados) object.getValue());
            codigosEmpleados += empleA.getCodigo() + ",";
            if (empleA.getCodigo().equals(new Integer(-1))) {
                codigosEmpleados = "";
                break;
            }

        }
        if (codigosEmpleados.length() > 0) {
            compleEmpleado = "  and o.empleados in (" + codigosEmpleados.substring(0, codigosEmpleados.length() - 1) + ") ";
        }

        String q = "Select o.*  from Contratos as o "
                + " where o.fechainstalacion between '" + desdestr + "' and '" + hastastr + "'  " + compleEmpleado
                + " order by o.empleados , o.fecha  ";
        System.out.println("" + q);
        contratos = adm.queryNativo(q, Contratos.class);

        ArrayList detalles = new ArrayList();
        String quer = "";
        List<Equipos> equ = adm.queryNativo("SELECT * FROM Equipos WHERE CAST(tipo AS SIGNED)  BETWEEN 1 AND  10 ", Equipos.class);
        String codigoEq = "";
        for (Iterator<Equipos> it = equ.iterator(); it.hasNext();) {
            Equipos equipos = it.next();
            codigoEq += equipos.getCodigo() + ",";
        }
        if (codigoEq.length() > 1) {
            codigoEq = codigoEq.substring(0, codigoEq.length() - 1);
        }
        for (Iterator<Contratos> itCli = contratos.iterator(); itCli.hasNext();) {
            Contratos contra = itCli.next();
//            String query = "Select o from Detalle as o "
//                        + " where o.equipos.codigo in ("+codigoEq+")  "
//                        + " and o.factura.contratos.codigo = '" + contra.getCodigo() + "' "
//                        + " and o.factura.fecha between  '" + desdestr + "'  and '" + hastastr + "' "
//                        + " and o.factura.contratos.codigo = '"+contra.getCodigo()+"'  ";
//                   query = "Select o from Cxcobrar as o "
//                        + "where o.haber > 0 "
//                        + "and o.factura.contratos.codigo = '" + contra.getCodigo() + "' "
//                        + "and o.fecha between  '" + desdestr + "'  and '" + hastastr + "' "
//                        + " order by  o.empleados.apellidos,o.factura.numero ";
            detalles.add(contra);
        }
        System.out.println("" + quer);
        ReporteContratoDataSource ds = new ReporteContratoDataSource(detalles);
        return ds;
    }

    public JRDataSource historiaTecnicaCliente(Clientes cli, Date desde, Date hasta) {
        String desdestr = convertiraString(desde);
        String hastastr = convertiraString(hasta);
        Administrador adm = new Administrador();
        List<Clientes> clientes = new ArrayList<Clientes>();
        if (cli.getCodigo().equals(-1)) {

            clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                    + "where  o.sucursal.codigo = '" + sucursal.getCodigo() + "' order by o.clientes.apellidos");
        } else {
            cli = (Clientes) adm.buscarClave(cli.getCodigo(), Clientes.class);
            clientes.add(cli);
        }
        List detalles = new ArrayList();
        String quer = "";
        for (Iterator<Clientes> itCli = clientes.iterator(); itCli.hasNext();) {
            Clientes clientes1 = itCli.next();
            quer = "SELECT  o FROM Soporte as o WHERE o.clientes.codigo  =  " + clientes1.getCodigo() + "  "
                    + "and  o.fecha between '" + desdestr + "' and '" + hastastr + "' "
                    + "order by o.fecha ";

            List facEncontradas = adm.query(quer);
            Pendientes pendi = null;
            for (Iterator it = facEncontradas.iterator(); it.hasNext();) {
                Soporte object = (Soporte) it.next();
                detalles.add(object);

            }


        }
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detalles);
        return beanCollectionDataSource;
    }

    public JRDataSource historiaTecnicaUsuario(List emp, Date desde, Date hasta) {

        Administrador adm = new Administrador();
        String desdestr = convertiraString(desde);
        String hastastr = convertiraString(hasta);
        List detalles = new ArrayList();
        String compleEmpleado = "";

        String codigosEmpleados = "";


        for (Iterator it = emp.iterator(); it.hasNext();) {
            Listitem object = (Listitem) it.next();
            Empleados empleA = ((Empleados) object.getValue());
            codigosEmpleados += empleA.getCodigo() + ",";
            if (empleA.getCodigo().equals(new Integer(-1))) {
                codigosEmpleados = "";
                break;
            }

        }
        if (codigosEmpleados.length() > 0) {
            compleEmpleado = " and o.empleados.codigo in (" + codigosEmpleados.substring(0, codigosEmpleados.length() - 1) + ")    ";
        }
        String quer = "SELECT  o FROM Soporte as o WHERE  "
                + " o.fecha between '" + desdestr + "' and '" + hastastr + "' " + compleEmpleado
                + "order by o.fecha ";
        List facEncontradas = adm.query(quer);
        for (Iterator it = facEncontradas.iterator(); it.hasNext();) {
            Soporte object = (Soporte) it.next();
            detalles.add(object);

        }
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detalles);
        return beanCollectionDataSource;
    }

    public JRDataSource historiaTecnicaSector(Sector sec, Canton canton, Date desde, Date hasta) {
        Administrador adm = new Administrador();
        List<Clientes> clientes = new ArrayList<Clientes>();
//            clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
//                    + "where  o.sucursal.codigo = '" + sucursal.getCodigo() + "' "
//                    + "and o.sector.codigo = '"+sec.getCodigo()+"' order by o.clientes.apellidos");
//            
        if (sec.getCodigo().equals(-1)) {

            if (canton.getCodigo().equals(-1)) {
                clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                        + "where  o.sucursal.codigo = '" + sucursal.getCodigo() + "' order by o.clientes.apellidos");

            } else {
                clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                        + "where o.sector.canton.codigo = '" + canton.getCodigo() + "' and  o.sucursal.codigo = '" + sucursal.getCodigo() + "' order by o.clientes.apellidos");

            }
        } else if (canton.getCodigo().equals(-1)) {

            clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                    + "where  o.sucursal.codigo = '" + sucursal.getCodigo() + "' order by o.clientes.apellidos");


        } else {
            clientes = adm.query("Select DISTINCT o.clientes from Contratos as o "
                    + "where o.sector.codigo = '" + sec.getCodigo() + "' "
                    + " and o.sucursal.codigo = '" + sucursal.getCodigo() + "' order by o.clientes.apellidos");

        }

        List detalles = new ArrayList();
        String quer = "";
        String desdestr = convertiraString(desde);
        String hastastr = convertiraString(hasta);
        for (Iterator<Clientes> itCli = clientes.iterator(); itCli.hasNext();) {
            Clientes clientes1 = itCli.next();
            quer = "SELECT  o FROM Soporte as o WHERE o.clientes.codigo  =  " + clientes1.getCodigo() + " "
                    + "and  o.fecha between '" + desdestr + "' and '" + hastastr + "' "
                    + "order by o.fecha ";
            List facEncontradas = adm.query(quer);
            for (Iterator it = facEncontradas.iterator(); it.hasNext();) {
                Soporte object = (Soporte) it.next();
                detalles.add(object);

            }


        }
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detalles);
        return beanCollectionDataSource;
    }

    public JRDataSource hojaRutaEmpleado(List emp, Date desde, Date hasta) {

        Administrador adm = new Administrador();
        String desdestr = convertiraString(desde) + " 00:00:01";
        String hastastr = convertiraString(hasta) + " 23:59:59";
        List detalles = new ArrayList();
        String compleEmpleado = "";
//        if(emple.getCodigo().equals(-1)){
//            comple ="";
//        }
//            String compleEmpleado = "";
        String codigosEmpleados = "";


        for (Iterator it = emp.iterator(); it.hasNext();) {
            Listitem object = (Listitem) it.next();
            Empleados empleA = ((Empleados) object.getValue());
            codigosEmpleados += empleA.getCodigo() + ",";
            if (empleA.getCodigo().equals(new Integer(-1))) {
                codigosEmpleados = "";
                break;
            }

        }
        if (codigosEmpleados.length() > 0) {
            compleEmpleado = " o.tecnico.codigo in (" + codigosEmpleados.substring(0, codigosEmpleados.length() - 1) + ") and  ";
        }

        String quer = "SELECT  o FROM Soporte as o WHERE " + compleEmpleado
                + " o.fecha between '" + desdestr + "' and '" + hastastr + "' and o.estado in (3,4) "
                + "order by o.fecha ";
        List facEncontradas = adm.query(quer);
        for (Iterator it = facEncontradas.iterator(); it.hasNext();) {
            Soporte object = (Soporte) it.next();
            detalles.add(object);

        }
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detalles);
        return beanCollectionDataSource;
    }

    public JRDataSource soporteEstado(String actividad, String estado, Date desde, Date hasta) {

        Administrador adm = new Administrador();
        String desdestr = convertiraString(desde) + " 00:00:01";
        String hastastr = convertiraString(hasta) + " 23:59:59";
        List detalles = new ArrayList();
        String comple1 = " and o.actividad =  '" + actividad + "' ";
        if (actividad.equals("-1")) {
            comple1 = "";
        }
        String comple2 = " and o.estado =  " + estado + "  ";
        if (estado.equals("-1")) {
            comple2 = "";
        }
        String quer = "SELECT  o FROM Soporte as o WHERE "
                + " o.fecha between '" + desdestr + "' and '" + hastastr + "' " + comple1 + " " + comple2
                + "order by o.fecha ";
        List facEncontradas = adm.query(quer);
        for (Iterator it = facEncontradas.iterator(); it.hasNext();) {
            Soporte object = (Soporte) it.next();
            detalles.add(object);

        }
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detalles);
        return beanCollectionDataSource;
    }
}
