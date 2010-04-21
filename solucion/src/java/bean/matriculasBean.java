/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
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
//        if (repreNuevo) {
//            adm.ejecutaSqlNativo("insert into contable.invmaestro (invmae_diascredito,invmae_telefono1,invmae_tipoid,invmae_nombre,invmae_numeroruc,invmae_direccion,glbemp_codigo,invmae_codigo,invmae_grupo,glbemp_codigo,invmae_activo) values ( " + matricula.getEstudiante().getRepresentante().getCodigorep() + ",'" + matricula.getEstudiante().getRepresentante().getTelfactura() + "','" + matricula.getEstudiante().getRepresentante().getTipoidentificacion() + "','" + matricula.getEstudiante().getRepresentante().getNombrefactura() + "','" + matricula.getEstudiante().getRepresentante().getIdentificacionfactura() + "','" + matricula.getEstudiante().getRepresentante().getDirfactura() + "'," + periodo.getCodigoper() + "," + maeCodigo + ", 'REP', 1 )");
//
//        } else {
//            List datos = adm.queryNativo("select invmae_codigo from contable.invmaestro where invmae_diascredito = " + matricula.getEstudiante().getRepresentante().getCodigorep() + " and invmae_grupo = 'REP' ");
//            System.out.println("ENCONTRADOS " + datos);
//            if (datos.size() > 0) {
//                codigoRepresentante = new Long(((Vector) datos.get(0)).get(0).toString());
//                adm.ejecutaSqlNativo("update contable.invmaestro set invmae_telefono1='" + matricula.getEstudiante().getRepresentante().getTelfactura() + "',invmae_tipoid='" + matricula.getEstudiante().getRepresentante().getTipoidentificacion() + "',invmae_nombre='" + matricula.getEstudiante().getRepresentante().getNombrefactura() + "', invmae_numeroruc='" + matricula.getEstudiante().getRepresentante().getIdentificacionfactura() + "',   invmae_direccion='" + matricula.getEstudiante().getRepresentante().getDirfactura() + "' , invmae_representante = '" + matricula.getEstudiante().getApellido() + " " + matricula.getEstudiante().getNombre() + "', invmae_grupo = 'REP', glbemp_codigo = " + periodo.getCodigoper() + ", invmae_activo = 1  where invmae_codigo = " + codigoRepresentante + " ");
//            } else {
//                adm.ejecutaSqlNativo("insert into contable.invmaestro (invmae_diascredito,invmae_telefono1,invmae_tipoid,invmae_nombre,invmae_numeroruc,invmae_direccion,glbemp_codigo,invmae_codigo,invmae_grupo,invmae_activo) values ( " + matricula.getEstudiante().getRepresentante().getCodigorep() + ",'" + matricula.getEstudiante().getRepresentante().getTelfactura() + "','" + matricula.getEstudiante().getRepresentante().getTipoidentificacion() + "','" + matricula.getEstudiante().getRepresentante().getNombrefactura() + "','" + matricula.getEstudiante().getRepresentante().getIdentificacionfactura() + "','" + matricula.getEstudiante().getRepresentante().getDirfactura() + "'," + periodo.getCodigoper() + ",'" + maeCodigo + "', 'REP',1 )");
//                codigoRepresentante = maeCodigo;
//            }
//        }
//        maestroNuevo = adm.queryNativo("SELECT IF(MAX(invmae_codigo) IS NULL,0,MAX(invmae_codigo)) +1 FROM contable.invmaestro");
//        maeCodigo = (Long) ((Vector) maestroNuevo.get(0)).get(0);
//        if (estuNuevo) {
//            adm.ejecutaSqlNativo("insert into contable.invmaestro (invmae_diascredito,invmae_nombre,invmae_grupo,invmae_codigo,inv_invmae_codigo,glbemp_codigo,invmae_activo) values ( " + matricula.getEstudiante().getCodigoest() + ",'" + matricula.getEstudiante().getApellido() + " " + matricula.getEstudiante().getNombre() + "', 'EST', " + maeCodigo + ", " + codigoRepresentante + "," + periodo.getCodigoper() + ", 1 )");
//        } else {
//            List datos = adm.queryNativo("select invmae_codigo from contable.invmaestro where invmae_diascredito = " + matricula.getEstudiante().getCodigoest() + "  and invmae_grupo = 'EST' ");
//            if (datos.size() > 0) {
//                adm.ejecutaSqlNativo("update contable.invmaestro set invmae_nombre = '" + matricula.getEstudiante().getApellido() + " " + matricula.getEstudiante().getNombre() + "', glbemp_codigo = " + periodo.getCodigoper() + ", invmae_activo = 1  where invmae_diascredito= " + matricula.getEstudiante().getCodigoest() + " and invmae_grupo = 'EST' ");
//
//            } else {
//                adm.ejecutaSqlNativo("insert into contable.invmaestro (invmae_diascredito,invmae_nombre,invmae_grupo,invmae_codigo,inv_invmae_codigo,glbemp_codigo,invmae_activo) values ( " + matricula.getEstudiante().getCodigoest() + ",'" + matricula.getEstudiante().getApellido() + " " + matricula.getEstudiante().getNombre() + "','EST', " + maeCodigo + ", " + codigoRepresentante + "," + periodo.getCodigoper() + ",1 )");
//
//
//            }
//        }
    }

    public void leerPdf() {
        try {
            System.out.println("Iniciamos proceso");
            InputStream inputStream = new FileInputStream(new String("/home/geovanny/Escritorio/interfase.dbf"));
            DBFReader reader = new DBFReader(inputStream);
            // get the field count if you want for some reasons like the following
//            int numberOfFields = reader.getFieldCount();
//            for (int i = 0; i < numberOfFields; i++) {
//                DBFField field = reader.getField(i);
//                System.out.print(i+"::"+field.getName()+"   ");
//            }
            System.out.println("*********************************************");
            Object[] rowObjects;
            while ((rowObjects = reader.nextRecord()) != null) {
                System.out.println("*********************************************");
                System.out.print(((String)rowObjects[0]).trim());
//                System.out.print(((String)rowObjects[3]).trim()+" "+((String)rowObjects[4]).trim()+"");
                
                
            }
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }

    }
