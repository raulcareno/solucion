/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Date;
import java.util.List;
import java.util.Vector;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Periodo;
import jcinform.procesos.Administrador;

/**
 *
 * @author geovanny
 */
public class matriculasBean {

    Administrador adm = new Administrador();

    public matriculasBean() {
    }

    public void generar(Boolean repreNuevo, Boolean estuNuevo, Matriculas matricula, Periodo periodo) {
        List maestroNuevo = adm.queryNativo("SELECT IF(MAX(invmae_codigo) IS NULL,0,MAX(invmae_codigo)) +1 FROM contable.invmaestro");
        Long maeCodigo = (Long) ((Vector) maestroNuevo.get(0)).get(0);
        Long codigoRepresentante = null;
        if (repreNuevo){
            adm.ejecutaSqlNativo("insert into contable.invmaestro (invmae_diascredito,invmae_telefono1,invmae_tipoid,invmae_nombre,invmae_numeroruc,invmae_direccion,glbemp_codigo,invmae_codigo,invmae_grupo,glbemp_codigo) values ( " + matricula.getCodigomat() + ",'" + matricula.getEstudiante().getRepresentante().getTelfactura() + "','" + matricula.getEstudiante().getRepresentante().getTipoidentificacion() + "','" + matricula.getEstudiante().getRepresentante().getNombrefactura() + "','" + matricula.getEstudiante().getRepresentante().getIdentificacionfactura() + "','" + matricula.getEstudiante().getRepresentante().getDirfactura() + "'," + periodo.getCodigoper() + "," + maeCodigo + ", 'REP'  )");
            codigoRepresentante = maeCodigo;
        } else {
            List datos = adm.queryNativo("select invmae_codigo from contable.invmaestro where invmae_diascredito = " + matricula.getCodigomat() + " and invmae_grupo = 'REP' ");
            System.out.println("ENCONTRADOS " + datos);
            if (datos.size() > 0) {
                codigoRepresentante = new Long(((Vector) datos.get(0)).get(0).toString());
                adm.ejecutaSqlNativo("update contable.invmaestro set invmae_telefono1='" + matricula.getEstudiante().getRepresentante().getTelfactura() + "',invmae_tipoid='" + matricula.getEstudiante().getRepresentante().getTipoidentificacion() + "',invmae_nombre='" + matricula.getEstudiante().getRepresentante().getNombrefactura() + "', invmae_numeroruc='" + matricula.getEstudiante().getRepresentante().getIdentificacionfactura() + "',   invmae_direccion='" + matricula.getEstudiante().getRepresentante().getDirfactura() + "' , invmae_representante = '" + matricula.getEstudiante().getApellido() + " " + matricula.getEstudiante().getNombre() + "', invmae_grupo = 'REP', glbemp_codigo = " + periodo.getCodigoper() + "  where invmae_codigo = " + codigoRepresentante + " ");
            } else {
                adm.ejecutaSqlNativo("insert into contable.invmaestro (invmae_diascredito,invmae_telefono1,invmae_tipoid,invmae_nombre,invmae_numeroruc,invmae_direccion,glbemp_codigo,invmae_codigo,invmae_grupo) values ( " + matricula.getCodigomat() + ",'" + matricula.getEstudiante().getRepresentante().getTelfactura() + "','" + matricula.getEstudiante().getRepresentante().getTipoidentificacion() + "','" + matricula.getEstudiante().getRepresentante().getNombrefactura() + "','" + matricula.getEstudiante().getRepresentante().getIdentificacionfactura() + "','" + matricula.getEstudiante().getRepresentante().getDirfactura() + "'," + periodo.getCodigoper() + ",'" + maeCodigo + "', 'REP' )");
                codigoRepresentante = maeCodigo;
            }
        }
        maestroNuevo = adm.queryNativo("SELECT IF(MAX(invmae_codigo) IS NULL,0,MAX(invmae_codigo)) +1 FROM contable.invmaestro");
        maeCodigo = (Long) ((Vector) maestroNuevo.get(0)).get(0);
        if (estuNuevo) {
            adm.ejecutaSqlNativo("insert into contable.invmaestro (invmae_diascredito,invmae_nombre,invmae_grupo,invmae_codigo,inv_invmae_codigo,glbemp_codigo) values ( " + matricula.getCodigomat() + ",'" + matricula.getEstudiante().getApellido() + " " + matricula.getEstudiante().getNombre() + "', 'EST', " + maeCodigo + ", " + codigoRepresentante + "," + periodo.getCodigoper() + " )");
        } else {
            List datos = adm.queryNativo("select invmae_codigo from contable.invmaestro where invmae_diascredito = " + matricula.getCodigomat() + "  and invmae_grupo = 'EST' ");
            if (datos.size() > 0) {
                adm.ejecutaSqlNativo("update contable.invmaestro set invmae_nombre = '" + matricula.getEstudiante().getApellido() + " " + matricula.getEstudiante().getNombre() + "', glbemp_codigo = " + periodo.getCodigoper() + "  where invmae_diascredito= " + matricula.getCodigomat() + " and invmae_grupo = 'EST' ");
            }else{
                adm.ejecutaSqlNativo("insert into contable.invmaestro (invmae_diascredito,invmae_nombre,invmae_grupo,invmae_codigo,inv_invmae_codigo,glbemp_codigo) values ( " + matricula.getCodigomat() + ",'" + matricula.getEstudiante().getApellido() + " " + matricula.getEstudiante().getNombre() + "','EST', " + maeCodigo + ", " + codigoRepresentante + "," + periodo.getCodigoper() + " )");

                
             //obtengo una nueva clave para la cabecera
                List  listCabecera = adm.queryNativo("SELECT IF(MAX(invcab_codigo) IS NULL,0,MAX(invcab_codigo)) +1 FROM contable.invcabecera ");
                Long cabeCodigo = (Long) ((Vector) listCabecera.get(0)).get(0);
                String fechaActual = ((new Date()).getYear()+1900) +"-"+((new Date()).getMonth()+1)+"-"+((new Date()).getDate());
                adm.ejecutaSqlNativo("INSERT INTO contable.invcabecera(invcab_codigo,invcab_fecharegistro,invcab_emision,invmae_codigo) " +
                        "VALUES ( '"+cabeCodigo+"', '"+fechaActual+"', '"+fechaActual+"','"+maeCodigo+"');");

             //ACA EMPIEZO A ITERAR LOS PRODUCTOS ASIGNADOS A  LA MATRICULA 
                List produ = adm.queryNativo("SELECT invpro_codigo, pro.invpro_precio1, invpro_descripcion  FROM contable.mesesproductos " +
                        "WHERE glbemp_codigo = '"+periodo.getEmpresa()+"' AND mes = 13");
                for (int i  = 0;  i  < produ.size();  i ++) {
                     Vector object = (Vector) produ.get(i);
                     //obtengo una nueva clave para la detalle en movimiento
                        List  listMovi = adm.queryNativo("SELECT IF(MAX(invmov_codigo) IS NULL,0,MAX(invmov_codigo)) +1 FROM contable.invmovimientos ");
                        Long moviCodigo = (Long) ((Vector) listMovi.get(0)).get(0);
                        adm.ejecutaSqlNativo("INSERT INTO contable.invmovimientos(invmov_codigo,invmov_descripcion,invmov_cantidad,invmov_totalmov,invpro_codigo,invcab_codigo) " +
                             "VALUES ( '"+moviCodigo+"','"+object.get(2)+"','1',"+object.get(1)+","+object.get(0)+",'"+cabeCodigo+"')");
                     
                }
                
            }
        }

    }
}
