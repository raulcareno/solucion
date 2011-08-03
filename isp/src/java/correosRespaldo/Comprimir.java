package correosRespaldo;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.*;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zul.Fileupload;
/**
 *
 * @author jcinform
 */
public class Comprimir {

    /**
     * @param args the command line arguments
     */
      static final int BUFFER  = 2048;
  public void comprimir(String archivo){
          try{

            BufferedInputStream origin = null;
            FileOutputStream dest = new  FileOutputStream(archivo.replace(".sql", ".zip"));
            ZipOutputStream out = new ZipOutputStream( new BufferedOutputStream(dest));
            //out.setMethod(ZipOutputStream.DEFLATED);
            byte data[] = new byte[BUFFER];
            //obtenemos la lista de los archivos del directorio actual
            File f = new File(archivo);
            String files[] = f.list();
            FileInputStream fi = new FileInputStream(f);
                        origin = new
                                BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(f.getName());
                out.putNextEntry(entry);
                int count;
                while((count = origin.read(data, 0,
                                BUFFER))!= -1){
                        out.write(data, 0, count);
                }
                origin.close();

            out.close();
            }catch(Exception e){
                e.printStackTrace();
            }
      
  }

}
