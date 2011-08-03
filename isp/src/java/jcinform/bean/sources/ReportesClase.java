/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean.sources;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import jcinform.bean.sources.clasestmp.Pendientes;
import jcinform.conexion.Administrador;
import jcinform.persistencia.Clientes;
import jcinform.persistencia.Contratos;
import jcinform.persistencia.Cxcobrar;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Empleadosfacturas;
import jcinform.persistencia.Sector;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author jcinform
 */
public class ReportesClase {

    public ReportesClase() {
        BigDecimal a = new BigDecimal(BigInteger.ONE);
        a.compareTo(a);
    }

    public JRDataSource facturasPendientes(Clientes cli) {
        Administrador adm = new Administrador();
        List<Clientes> clientes = new ArrayList<Clientes>();
        if (cli.getCodigo().equals(-1)) {
            clientes = adm.query("Select o from Clientes as o order by o.apellidos");
        } else {
            cli = (Clientes) adm.buscarClave(cli.getCodigo(), Clientes.class);
            clientes.add(cli);
        }
        ArrayList detalles = new ArrayList();
        for (Iterator<Clientes> itCli = clientes.iterator(); itCli.hasNext();) {
            Clientes clientes1 = itCli.next();
//            List facEncontradas = adm.queryNativo("SELECT fa.codigo, fa.fecha, p.nombre, fa.total,  (SUM(cx.debe) - SUM(cx.haber)) saldo FROM plan p, detalle de, cxcobrar cx, factura  fa "
//                    + " WHERE p.codigo = de.plan AND  de.factura = fa.codigo AND fa.clientes  =  " + clientes1.getCodigo() + "   AND cx.factura = fa.codigo GROUP BY fa.codigo  HAVING  (SUM(cx.debe) - SUM(cx.haber)) > 0");
            String quer = "SELECT fa.codigo, fa.fecha, fa.total,  (SUM(cx.debe) - SUM(cx.haber)) saldo, fa.contratos "
                    + "FROM cxcobrar cx, factura  fa "
                    + " WHERE fa.clientes  =  " + clientes1.getCodigo() + "  "
                    + "  AND cx.factura = fa.codigo GROUP BY fa.codigo  "
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

    public JRDataSource facturasPendientes(Sector sec) {
        Administrador adm = new Administrador();
        List<Clientes> clientes = new ArrayList<Clientes>();
//        if (cli.getCodigo().equals(-1)) {
        clientes = adm.query("Select o from Clientes as o order by o.apellidos");
//        } else {
//            cli = (Clientes) adm.buscarClave(cli.getCodigo(), Clientes.class);
//            clientes.add(cli);
//        }
        ArrayList detalles = new ArrayList();
        for (Iterator<Clientes> itCli = clientes.iterator(); itCli.hasNext();) {
            Clientes clientes1 = itCli.next();
            String quer = "SELECT fa.codigo, fa.fecha, fa.total,  (SUM(cx.debe) - SUM(cx.haber)) saldo, fa.contratos "
                    + "FROM cxcobrar cx, factura  fa "
                    + " WHERE fa.sector  = '" + sec.getCodigo() + "' and fa.clientes  =  " + clientes1.getCodigo() + "  "
                    + "  AND cx.factura = fa.codigo GROUP BY fa.codigo  "
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

    public JRDataSource facturasCobradas(Clientes cli, Date desde, Date hasta) {
        Administrador adm = new Administrador();
        List<Clientes> clientes = new ArrayList<Clientes>();
        if (cli.getCodigo().equals(-1)) {
            clientes = adm.query("Select o from Clientes as o order by o.apellidos");
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
                    + " WHERE p.codigo = de.plan AND  de.factura = fa.codigo AND fa.clientes  =  " + clientes1.getCodigo() + "  "
                    + " AND cx.factura = fa.codigo AND fa.fecha between '" + desdestr + "' and '" + hastastr + "' GROUP BY fa.codigo  ");
            if (facEncontradas.size() > 0) {
                Pendientes pendi = null;
                for (Iterator itna = facEncontradas.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();

                    List<Cxcobrar> abonos = adm.query("Select o from Cxcobrar as o where o.haber > 0 and o.factura.codigo = '" + vec.get(0) + "'");
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
                        pendi.setValorabonodeb(cIt.getDebito());
                        pendi.setValorabonotar(cIt.getTarjeta());
                        pendi.setNocheque(cIt.getNocheque());
                        pendi.setNocuenta(cIt.getNocuenta());
                        pendi.setNotarjeta(cIt.getNotarjeta());
                        detalles.add(pendi);
                        i++;
                    }




                }

            }

        }
        ReportePendientesDataSource ds = new ReportePendientesDataSource(detalles);
        return ds;
    }

    public JRDataSource reporteCaja(Date desde, Date hasta) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        String desdestr = convertiraString(desde);
        String hastastr = convertiraString(hasta);
        Pendientes pendi = null;
        List<Cxcobrar> abonos = adm.query("Select o from Cxcobrar as o "
                + "where o.haber > 0 and o.fecha between  '" + desdestr + "'  and '" + hastastr + "' ");
        int i = 1;
        for (Iterator<Cxcobrar> itAbono = abonos.iterator(); itAbono.hasNext();) {
            Cxcobrar cIt = itAbono.next();
            pendi = new Pendientes();
            pendi.setFactura("" + cIt.getFactura().getNumero());
            pendi.setFecha(cIt.getFecha());
            pendi.setPlan("");
            pendi.setTotal(new BigDecimal(0));
            pendi.setSaldo(new BigDecimal(0));
            pendi.setNoabono(cIt.getCodigo());
            pendi.setFechapago(cIt.getFecha());
            pendi.setValorabonoefe(cIt.getEfectivo());
            pendi.setValorabonoche(cIt.getCheque());
            pendi.setValorabonodeb(cIt.getDebito());
            pendi.setValorabonotar(cIt.getTarjeta());
            pendi.setNocheque(cIt.getNocheque());
            pendi.setNocuenta(cIt.getNocuenta());
            pendi.setNotarjeta(cIt.getNotarjeta());
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
                + "where date(o.fecha) = '" + desdestr + "'  and o.empleados = '" + emp.getCodigo() + "' ", Empleadosfacturas.class);
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
            pendi.setValorabonodeb(cIt.getDebito());
            pendi.setValorabonotar(cIt.getTarjeta());
            pendi.setNocheque(cIt.getNocheque());
            pendi.setNocuenta(cIt.getNocuenta());
            pendi.setNotarjeta(cIt.getNotarjeta());
            detalles.add(pendi);
            i++;
        }

        ReportePendientesDataSource ds = new ReportePendientesDataSource(detalles);
        return ds;
    }

    public static String convertiraString(Date fecha) {

        return (fecha.getYear() + 1900) + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();

    }

    public JRDataSource clientesxsector(Sector sec) {
        Administrador adm = new Administrador();
        ArrayList detalles = new ArrayList();
        List<Contratos> contra = adm.query("Select o from Contratos as o where o.radios.nodos.sector.codigo =  '" + sec.getCodigo() + "'"
                + " order by o.radios.nodos.sector.numero");
        for (Iterator<Contratos> it = contra.iterator(); it.hasNext();) {
            Contratos contratos = it.next();
            detalles.add(contratos);

        }

        ReporteContratoDataSource ds = new ReporteContratoDataSource(detalles);
        return ds;
    }
}
