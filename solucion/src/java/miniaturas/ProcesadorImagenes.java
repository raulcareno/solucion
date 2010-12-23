/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miniaturas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * @author Ismael Jadan
 */
public class ProcesadorImagenes {
    /* Constantes */
    /** Umbral a partir del cual aplicaremos filtros de convolucion . */
    protected static final double UMBRAL_APLICACION_FILTRO_CONVOLUCION = 0.5;
    /** Factor de convolucion que aplicamos para los algoritmos de
     * suavizado. */
    private static final Double FACTOR_CONVOLUCION_SUAVIZADO = 1.2;
    /* Atributos */
    /** Opciones de renderizado para las imagenes. */
    protected RenderingHints opcionesRenderizadoImagenes;
    /** Listado de formatos a los que debe aplicarse convolucion por
     * sus perdidas en caso de reducciones muy pronunciadas. */
    protected static List<String> listadoFormatosFiltroReduccionRuido;
    /* Metodos */
    /** Constructor de la clase. Carga opciones de renderizado
     * por defecto, tendiendo a la calidad. */
    public ProcesadorImagenes() {
        cargarOpcionesRenderizadoDefecto();
        cargarListadoFormatosReduccionRuido();
    }

    /** Constructor de la clase.
     * @param opcionesRenderizadoPropias Opciones propias de renderizado
     */
    public ProcesadorImagenes(Map<Key, Object> opcionesRenderizadoPropias) {
        opcionesRenderizadoImagenes = new RenderingHints(opcionesRenderizadoPropias);
        cargarListadoFormatosReduccionRuido();
    }

    /** Metodo que carga el listado de formatos que requieren de reducccion de
     * ruido en caso de reducciones sensibles.*/
    private void cargarListadoFormatosReduccionRuido() {

// Lo primero es crear el objeto
        listadoFormatosFiltroReduccionRuido = new ArrayList<String>();
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_JPEG_MAYUSC);
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_JPEG_MINUSC);
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_JPG_MAYUSC);
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_JPG_MINUSC);
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_BMP_MINUSC);
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_BMP_MAYUSC);
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_WBMP_MINUSC);
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_WBMP_MAYUSC);
    }

    /** Metodo que analiza si un formato dado como parametro requiere la aplicacion
     * de algoritmos de reduccion de ruido si es una reduccion sensible.
     * @param formato Formato sobre el que analizamos
     * @return Codigo booleano indicando si se da esta condicion
     */
    protected Boolean esFormatoRequiereReduccionRuido(final String formato) {

// Busco en mi listado
        Iterator<String> it = listadoFormatosFiltroReduccionRuido.iterator();

        while (it.hasNext()) {

// Recupero el formato del interior del iterador
            String formatoIt = it.next();

// Comparo ambos
            if (formatoIt.equals(formato)) {
// Formato esta en mi lista “negra” :)
                return true;
            }
        }

// Si llego hasta aqui es porque el elemento no estaba
        return false;
    }

    /** Metodo que genera una serie de valores por defecto para las opciones
     * de renderizado de las imagenes. */
    private void cargarOpcionesRenderizadoDefecto() {

        /* Cargo las opciones de renderizado por defecto para la clase: en general,
        en este caso se tiende a la calidad de las imagenes generadas */
        opcionesRenderizadoImagenes = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_DITHERING,
                RenderingHints.VALUE_DITHER_ENABLE);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    }

    /** Devuelve la lista de formatos disponibles a leer por ImageIO
     * @return un array de strings con los mismos.
     */
    public String[] dameListadoFormatosUsables() {
        return ImageIO.getReaderFormatNames();
    }

    /** Calcula el factor de escala minimo y en base a eso
     * escala la imagen segun dicho factor.
     * @param maximoAncho maximo tamaño para el ancho de la nueva imagen
     * @param maximoAlto maximo tamaño para el alto de la nueva imagen
     * @param formato Formato de la imagen. Determina los filtros a aplicar
     * @param imagen Imagen original que vamos a escalar
     * @return Devuelve la imagen escalada
     * @throws IOException Excepciones de entrada / salida
     */
    public BufferedImage escalarATamanyo(final File ficheroImagen, final Integer maximoAncho, final Integer maximoAlto,
            final String formato) throws IOException {

// Lo primero es obtener un BufferedImage
        BufferedImage imagenFichero = ImageIO.read(ficheroImagen);

// Aplico otro metodo de la clase
        return escalarATamanyo(imagenFichero, maximoAncho, maximoAlto, formato);
    }

    /** Calcula el factor de escala minimo y en base a eso
     * escala la imagen segun dicho factor.
     * @param maximoAncho maximo tamaño para el ancho de la nueva imagen
     * @param maximoAlto maximo tamaño para el alto de la nueva imagen
     * @param formato Formato de la imagen. Determina los filtros a aplicar
     * @param imagen Imagen original que vamos a escalar
     * @return Devuelve la imagen escalada
     */
    public BufferedImage escalarATamanyo(final BufferedImage imagen,
            final Integer maximoAncho, final Integer maximoAlto,
            final String formato) {

// Comprobacion de parametros
        if (imagen == null || maximoAlto <= 0 || maximoAncho <= 0) {
            return imagen;
        }

// Capturo ancho y alto de la imagen
        int anchoImagen = imagen.getHeight();
        int altoImagen = imagen.getWidth();

// Segunda comprobacion de parametros
        if (anchoImagen == 0 || altoImagen == 0) {
            return imagen;
        }

// Calculo la relacion entre anchos y altos de la imagen
        double escalaX = (double) maximoAncho / (double) anchoImagen;
        double escalaY = (double) maximoAlto / (double) altoImagen;

// Tomo como referencia el minimo de las escalas
        double fEscala = Math.min(escalaX, escalaY);

// Devuelvo el resultado de aplicar esa escala a la imagen
        return escalar(fEscala, imagen, formato);
    }

    /** Escala una imagen en porcentaje.
     * @param factorEscala ejemplo: factorEscala=0.6 (escala la imagen al 60%)
     * @param srcImg una imagen en formato BufferedImage
     * @param formatoOrigen Formato de la imagen. Determina los filtros a aplicar
     * @return un BufferedImage escalado
     */
    public BufferedImage escalar(final Double factorEscala,
            final BufferedImage srcImg, final String formatoOrigen) {

// Comprobacion de parametros
        if (srcImg == null || factorEscala == 0) {
            return null;
        }

// Preparo el tipo de los nuevos BufferedImage
        int tipoFormatoBufferedReader;
        if (formatoOrigen.equals(CodigosFormatosImagenes.CODIGO_FORMATO_GIF)) {
            tipoFormatoBufferedReader = srcImg.getType();
        } else {
            tipoFormatoBufferedReader = BufferedImage.TYPE_INT_RGB;
        }

// Caso de que realmente tengamos que escalar …
        BufferedImage filtroInicial = null;

// Compruebo escala nula
        if (factorEscala == 1) {

// En ese caso, devuelvo una copia de la imagen original
            BufferedImage copia = new BufferedImage(srcImg.getWidth(),
                    srcImg.getHeight(), tipoFormatoBufferedReader);
            copia.setData(srcImg.getData());
            return copia;
        } else {

// Se trata de una reduccion muy acuciada ?
            if (factorEscala < UMBRAL_APLICACION_FILTRO_CONVOLUCION
                    && esFormatoRequiereReduccionRuido(formatoOrigen)) {

                /* Para las imagenes cuyo factor de escala sea menor que el 0.5 …
                Preparo un objeto de tipo Kernel */
                Kernel kernel = crearKernelEscala(factorEscala);

// Lanzo una transformacion afin previa de suavizado
                ConvolveOp op = new ConvolveOp(
                        kernel, ConvolveOp.EDGE_NO_OP, null);

// Almaceno en filtroInicial la imagen suavizada
                BufferedImage copia = new BufferedImage(srcImg.getWidth(),
                        srcImg.getHeight(), tipoFormatoBufferedReader);
                copia.setData(srcImg.getData());

                filtroInicial = op.filter(copia, filtroInicial);
            } else {
// Factores de escala sin suavizado
                filtroInicial = srcImg;
            }
        }

// De aqui en adelante, debemos trabajar en base a filtroInicial

// La creo con las opciones de renderizado que tuviesemos
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(factorEscala, factorEscala),
                opcionesRenderizadoImagenes);
        BufferedImage resultadoFiltro = op.filter(filtroInicial, null);

        /* Balanceo entre elementos BufferedImage para eliminar canales
        de transparencia extras, si hay */
        BufferedImage biConversion = new BufferedImage(resultadoFiltro.getWidth(),
                resultadoFiltro.getHeight(), tipoFormatoBufferedReader);
        Graphics2D g = biConversion.createGraphics();

        g.setRenderingHints(opcionesRenderizadoImagenes);
        g.drawImage(resultadoFiltro, 0, 0, Color.WHITE, null);

// Devuelvo el resultado de aplicar el filtro sobre la imagen
        return biConversion;
    }

    /** Metodo que guarda una imagen en disco
     * @param imagen Imagen a almacenar en disco
     * @param rutaFichero Ruta de la imagen donde vamos a salvar la imagen
     * @param formato Formato de la imagen al almacenarla en disco
     * @return Booleano indicando si se consiguio salvar con exito la imagen
     * @throws IOException Excepciones de entrada / salida generales
     */
    public Boolean salvarImagen(final BufferedImage imagen,
            final String rutaFichero, final String formato)
            throws IOException {
        // Comprobacion de parametros
        if (imagen != null && rutaFichero != null && formato != null) {
            ImageIO.write(imagen, formato, new File(rutaFichero));
            return true;
        } else {
            // Fallo en tema de parametros
            return false;
        }
    }
    /** Metodo que crea un objeto de tipo Kernel para aplicar en
     * posteriores transformaciones.
     * @param factorEscala Factor de escala que tiene la imagen
     * @return Objeto Kernel construido
     */
    private Kernel crearKernelEscala(final Double factorEscala) {
// Calculos matematicos de proporciones de suavizado
        int tamanyo = 1 + (int) (FACTOR_CONVOLUCION_SUAVIZADO / factorEscala);
        float[] datos = new float[tamanyo * tamanyo];
        float factor = 1 / (float) datos.length;
        for (int i = 0; i < datos.length; i++) {
            datos[i] = factor;
        }
// Devuelvo un objeto Kernel entendible por el API
        return new Kernel(tamanyo, tamanyo, datos);
    }
}