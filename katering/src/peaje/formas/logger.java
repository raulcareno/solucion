package peaje.formas;

import hibernate.cargar.WorkingDirectory;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.*;

/**
 *
 * @author Geovanny Jadan
 */
public class logger {

    static WorkingDirectory w = new WorkingDirectory();
    String separador = File.separatorChar + "";
    String ubicacionDirectorio = w.get() + separador;
    String logfile = ubicacionDirectorio + "logs_";
    Date fecha = new Date();

    public logger() {
        if (ubicacionDirectorio.contains("build")) {
            ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
        }
        logfile = ubicacionDirectorio + "logs_";
    }

    public void logger(String clase, String novedades) {
        try {
            Logger log = Logger.getLogger(clase);
            SimpleDateFormat formato = new SimpleDateFormat("dd.MM.yyyy");
            String fechaAc = formato.format(fecha);
            PatternLayout defaultLayout = new PatternLayout("%p %c,line %L,%d{dd.MM.yyyy/HH:mm:ss},%m%n");
            RollingFileAppender rollingFileAppender = new RollingFileAppender();
            rollingFileAppender.setFile(logfile + fechaAc + ".log", true, false, 0);
            rollingFileAppender.setLayout(defaultLayout);
            log.removeAllAppenders();
            log.addAppender(rollingFileAppender);
            log.setAdditivity(false);
            log.info(novedades);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(logger.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}