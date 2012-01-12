/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Equivalencias;
import jcinform.procesos.Administrador;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author GEOVANNY
 */
public class ReportePromocionDataSource implements JRDataSource {

    private Iterator itrAlumnos;
    private Iterator itrNodos;
    private Object valorAtual;
    private boolean irParaProximoAlumno = true;
    
 
            Administrador adm=null;
            List<Equivalencias> equ = null;
         
    public ReportePromocionDataSource(List lista) {
        super();
        adm = new Administrador();
        this.itrNodos = lista.iterator();
        
    }

    public boolean next() throws JRException {
        itrAlumnos = itrNodos;
        valorAtual = itrAlumnos.hasNext() ? itrAlumnos.next() : null;
        irParaProximoAlumno = (valorAtual != null);
        return irParaProximoAlumno;
    }
   public Equivalencias devolverNombre(List<Equivalencias> equiva, Double codigo) {

        for (Iterator<Equivalencias> it = equiva.iterator(); it.hasNext();) {
            Equivalencias equivalencias = it.next();
            if (equivalencias.getValorminimo() <= codigo && codigo <= equivalencias.getValormaximo()) {
                return equivalencias;
            }
        }
        return equiva.get(0);


    }
    /*
     * (non-Javadoc)
     *
     * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
     */
    public Object getFieldValue(JRField campo) throws JRException {
        Object valor = null;
        NotasClaseTemp nodo = (NotasClaseTemp) valorAtual;
        NumerosaLetras num = new NumerosaLetras();
        String fieldName = campo.getName();
         if(equ == null){
            equ = adm.query("Select o from Equivalencias as o"
                    + " where o.periodo.codigoper  = '" + nodo.getMatricula().getCurso().getPeriodo().getCodigoper() + "' "
                    + "and o.grupo = 'AP' ");
         }
        try {

            if ("textoParametro".equals(fieldName)) {
                valor = nodo.getCabeceraTexto();
            } else if ("textPie".equals(fieldName)) {
                if (nodo.estado) {
                    valor = nodo.getPieTextoAprobado();
                } else {
                    valor = nodo.getPieTextoReprobado();
                }
            } else if ("asignatura".equals(fieldName)) {
                valor = nodo.getMateria();
            } else if ("letras2".equals(fieldName)) {


                if (nodo.getMateriaProfesor().getCuantitativa() == true) {

                    double numero = redondear((Double) nodo.getNota(), 2).doubleValue();
                    String cadena = Double.toString(numero);
                    String parte_decimal_cadena = cadena.substring(cadena.lastIndexOf("."), cadena.length());
                    String parte_entera_cadena = cadena.substring(0, cadena.lastIndexOf("."));
                    Double parte_entera_numero = Double.valueOf(parte_entera_cadena);
                    parte_decimal_cadena = parte_decimal_cadena.replace(".", ",");
                    if (parte_decimal_cadena.length() < 3) {
                        parte_decimal_cadena = parte_decimal_cadena + "0";
                    }
                    
                    if((nodo.getMateria()+"").contains("APROVECHAMIENTO")){
                         valor = num.numeros(parte_entera_numero) + " " + parte_decimal_cadena+" "+devolverNombre(equ,(Double)nodo.getNota()).getNombre();
                    }else{
                        valor = num.numeros(parte_entera_numero) + " " + parte_decimal_cadena;
                    }
                } else {
                    valor = null;
                }

            } else if ("letras".equals(fieldName)) {
                if (nodo.getMateriaProfesor().getCuantitativa() == true) {
                    valor = num.numeros((Double) nodo.getNota()) + "";
                } else {
                    valor = null;
                }

            } else if ("matricula".equals(fieldName)) {
                valor = nodo.getMatricula().getCodigomat();
            } else if ("estudiante".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getApellido() + " " + nodo.getMatricula().getEstudiante().getNombre();
            } else if ("genero".equals(fieldName)) {
                valor = nodo.getMatricula().getEstudiante().getGenero();
            } else if ("observacion".equals(fieldName)) {
                valor = nodo.getEstadoMateria();
            } else if ("equivalencia".equals(fieldName)) {
                valor = nodo.getCabeceraTexto();
            } else if ("promedio".equals(fieldName)) {
                if (nodo.getMateriaProfesor().getCuantitativa() == true) {
                    valor = java.lang.Math.round((Double) nodo.getNota());
                } else {
                    valor = nodo.getNotaCuali();
                }


            } else if ("promedio2".equals(fieldName)) {
                if (nodo.getMateriaProfesor().getCuantitativa() == true) {
                    valor = redondear((Double) nodo.getNota(), 2).doubleValue();
                } else {
                    valor = nodo.getNotaCuali();
                }


            } else if ("aprovechamiento".equals(fieldName)) {
                valor = nodo.getAprovechamiento();
            } else if ("disciplina".equals(fieldName)) {
                valor = redondear(nodo.getDisciplina(),2).doubleValue();
            } else if ("letrasDisciplina".equals(fieldName)) {
                valor = num.numeros(nodo.getDisciplina()).toUpperCase();
            } else if ("letrasDisciplina2".equals(fieldName)) {

                double numero = redondear((Double) nodo.getDisciplina(), 2).doubleValue();
                String cadena = Double.toString(numero);
                String parte_decimal_cadena = cadena.substring(cadena.lastIndexOf("."), cadena.length());
                String parte_entera_cadena = cadena.substring(0, cadena.lastIndexOf("."));
                Double parte_entera_numero = Double.valueOf(parte_entera_cadena);
                parte_decimal_cadena = parte_decimal_cadena.replace(".", ",");
                if(parte_decimal_cadena.length() < 3){
                    parte_decimal_cadena = parte_decimal_cadena + "0";
                }
                valor = num.numeros(parte_entera_numero) + " " + parte_decimal_cadena+" "+devolverNombre(equ, (Double) nodo.getDisciplina()).getNombre();
            } else if ("curso".equals(fieldName)) {
                valor = nodo.getMatricula().getCurso().getDescripcion();
            } else if ("paralelo".equals(fieldName)) {
                valor = nodo.getMatricula().getCurso().getParalelo().getDescripcion();
            } else if ("especialidad".equals(fieldName)) {
                valor = nodo.getMatricula().getCurso().getEspecialidad().getDescripcion();
            } else if ("letrasAprovechamiento".equals(fieldName)) {
                String formado = num.numerosDecimales(nodo.getAprovechamiento()).toUpperCase();
                try {
                    String valor1 = nodo.getAprovechamiento().toString();
                    int indice = valor1.indexOf(".");
                    String cortado = valor1.substring(indice + 1);
                    String cientos = " " + num.GetHundreds(cortado).toUpperCase();
                    if (!cientos.trim().equals("")) {
                        valor = formado + " PUNTOS CON " + cientos + " CENTESIMAS";
                    } else {
                        valor = formado + " PUNTOS ";
                    }
                } catch (Exception e) {
                    valor = formado;
                    System.out.println("erroren" + e);
                }
            } else if ("sello".equals(fieldName)) {
                try {
                    byte[] bImage = nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getEscudo();
                    if (bImage != null) {
                        InputStream is = new ByteArrayInputStream(bImage);
                        valor = is;
                    } else {
                    }
                } catch (Exception ex) {
                    System.out.println("Error en foto:" + ex);
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR EN ITERAR REPORTE" + e);
        }

        return valor;
    }

    public Double redondear(Double numero, int decimales) {
        try {

            BigDecimal d = new BigDecimal(numero);
            d = d.setScale(decimales, RoundingMode.HALF_UP);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }
}
