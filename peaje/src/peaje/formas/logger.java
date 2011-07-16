package peaje.formas;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.*;

/**
 *
 * @author Geovanny Jadan
 */
public class logger {
    public logger(){}
  String logfile = "f:\\archivo_";
    Date fecha=new Date();

    public void logger(String clase, String novedades) throws IOException {
        Logger log = Logger.getLogger(clase);
        SimpleDateFormat formato = new SimpleDateFormat("dd.MM.yyyy");
        String fechaAc = formato.format(fecha);
        PatternLayout defaultLayout = new PatternLayout("%p %c,line %L,%d{dd.MM.yyyy/HH:mm:ss},%m%n");
        RollingFileAppender rollingFileAppender = new RollingFileAppender();
        rollingFileAppender.setFile(logfile+fechaAc+".log", true, false, 0);
        rollingFileAppender.setLayout(defaultLayout);
        log.removeAllAppenders();
        log.addAppender(rollingFileAppender);
        log.setAdditivity(false);
        log.info(novedades);


    }
 
}