/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.io.File;
import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;

import org.primefaces.event.CaptureEvent;

@ManagedBean
@ViewScoped
public class PhotoCamBean {

    private String nombre;
    private int edad;
    private String foto;

    public void oncapture(CaptureEvent captureEvent) {

        // obtenemos los datos de la foto como array de bytes
        final byte[] datos = captureEvent.getData();

        final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        // le asignamos el nombre que sea a la imagen (en este caso siempre el mismo)
        this.foto = "foto.png";
        // ruta destino de la imagen /photocam/foto.png
        final String fileFoto = servletContext.getRealPath("") + File.separator + "fotos" + File.separator + foto;

        FileImageOutputStream outputStream = null;
        try {
            outputStream = new FileImageOutputStream(new File(fileFoto));
            // guardamos la imagen
            outputStream.write(datos, 0, datos.length);
        } catch (IOException e) {
            throw new FacesException("Error guardando la foto.", e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
            }
        }
    }

    public void guardarDatos() {
        // hacemos lo que sea con los datos...
    }

    public String getFoto() {
        return foto;
    }

    public boolean isVerFoto() {
        return foto != null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}