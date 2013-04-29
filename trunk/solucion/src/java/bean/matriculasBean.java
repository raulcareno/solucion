package bean;

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Vector;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;

public class matriculasBean {

    Administrador adm = new Administrador();
    Periodo per = new Periodo();

    public matriculasBean() {
//        try {
//            DBF db2 = new DBF("/home/geovanny/Escritorio/base1.dbf");
////            db = new DBF("/home/rousseau/base/interface.dbf"); ESTO DEJAR
//        } catch (xBaseJException ex) {
//            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(matriculasBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
//     public static void export_to_csv(Listbox listbox) {
    //exp.exportar(listbox);
    //return;

//        Filedownload.save(sb.toString().getBytes(), "application/vnd.ms-excel", "auditoria.csv");
//        }
    public void generarRubrosAnual(Cursos cursoActual, Matriculas mat) {
        if (mat.getEstado().equals("Matriculado")) {
            List<Asignadosproductos> rubros = adm.query("Select o from Asignadosproductos as o "
                    + "where o.curso.codigocur = '" + cursoActual.getCodigocur() + "'");
            for (Iterator<Asignadosproductos> irRubros = rubros.iterator(); irRubros.hasNext();) {
                Asignadosproductos aRub = irRubros.next();
                Asignados asig = new Asignados();
                Integer valo = adm.getNuevaClave("Asignados", "codigorub");
                asig.setCodigorub(valo);
                asig.setAnio(aRub.getFechai().getYear() + 1900);
                asig.setMatricula(mat);
                asig.setEstado(true);
                asig.setFechai(aRub.getFechai());
                asig.setGrabaiva(aRub.getProductos().getGrabaiva()); 
                asig.setMes(aRub.getMeses());
                asig.setProducto(aRub.getProductos());
                asig.setValor(aRub.getProductos().getPrecio().doubleValue());
                asig.setBeca((mat.getBeca() == null?0.0:(aRub.getProductos().getPrecio().doubleValue()*mat.getBeca()/100))); //porcentaje a aplicar en todos los rubros
                asig.setOtros((mat.getOtros() == null?0.0:mat.getOtros())); //descuento
                adm.guardar(asig);
            }
        }
    }

    public void generar(Periodo periodo) {

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {

            String nombre = periodo.getInstitucion().getFotos() + "/base/" + periodo.getDescripcion() + ".csv";
            //nombre = "F:/datos.csv";
            fichero = new FileWriter(nombre);

            pw = new PrintWriter(fichero);
            List listado = adm.queryNativo("SELECT mat.codigomat matricula,est.codigoest, CONCAT(est.apellido,' ',est.nombre) estudiante,rep.identificacionfactura, nombrefactura, "
                    + "rep.dirfactura, rep.telfactura,cur.codigocur, CONCAT(cur.descripcion,' ', espe.descripcion,' ',par.descripcion),per.descripcion "
                    + " FROM estudiantes est, matriculas mat, cursos cur, representante rep, periodo per, GLOBAL espe, GLOBAL par "
                    + " WHERE mat.estudiante = est.codigoest  AND rep.codigorep = est.representante "
                    + " AND per.codigoper = cur.periodo AND espe.codigo = cur.especialidad AND par.codigo = cur.paralelo and per.descripcion like '%" + periodo.getDescripcion() + "%' "
                    + "  AND mat.curso = cur.codigocur order by cur.descripcion");
            String s = ";\t";
            StringBuffer sb = new StringBuffer();

//          
            String h = "";
            //Field[] campos = Estudiantes.class.getDeclaredFields();
//            List campos = adm.queryNativo("desc estudiantes");
//            for (Iterator it = campos.iterator(); it.hasNext();) {
//                Vector datos = (Vector) it.next();
//                String field = datos.get(0).toString();
//                h += field + s;
////                  System.out.print(""+datos);
//            }
            h += "matricula" + s;
            h += "codigo_unico" + s;
            h += "estudiante" + s;
            h += "identificacion" + s;
            h += "representante" + s;
            h += "direccion" + s;
            h += "telefono" + s;
            h += "codigo_curso" + s;
            h += "curso" + s;
            h += "periodo" + s;
            System.out.println("" + h);
            sb.append(h + "\n");


            for (Iterator it = listado.iterator(); it.hasNext();) {
                Vector vec = (Vector) it.next();
                String i = "";
                for (int j = 0; j < vec.size(); j++) {
                    i += vec.get(j) + s;
                }
                sb.append(i + "\n");
            }

//            for (Object item : listbox.getItems()) {
//                String i = "";
//                for (Object cell : ((Listitem) item).getChildren()) {
//                    i += ((Listcell) cell).getLabel() + s;
//                }
//                sb.append(i + "\n");
//            }
            OutputStream out = new FileOutputStream(nombre);
            out.write(sb.toString().getBytes());
            out.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }


    }

    void todos() {
    }

    void actualizar(Matriculas matricula) {
    }

    void agregar(Matriculas matricula) {
    }

    public void concatenarPdf() {
    }
}
