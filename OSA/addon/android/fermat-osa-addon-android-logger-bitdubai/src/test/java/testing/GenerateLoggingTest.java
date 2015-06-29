package testing;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.structure.LoggerManager;

import org.junit.Test;

/**
 * Created by rodrigo on 2015.06.25..
 */
public class GenerateLoggingTest {
    @Test
    public void ExecuteLog()  {
        final LoggerManager root = new LoggerManager(LogLevel.NOT_LOGGING);
        root.Log("Prueba de No Logging");


        root.setLogLevel(LogLevel.MINIMAL_LOGGING);
        root.Log("Prueba de Minimal Logging");


        root.setLogLevel(LogLevel.MODERATE_LOGGING);
        root.Log("Prueba de Moderate Logging");


        root.setLogLevel(LogLevel.AGGRESSIVE_LOGGING);
        root.Log("Prueba de Aggresive Logging");


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                root.setLogLevel(LogLevel.AGGRESSIVE_LOGGING);
                root.Log("Prueba de Aggresive Logging");
            }
        });
        thread.setName("Nuevo thread");
        thread.start();


    }
    }
