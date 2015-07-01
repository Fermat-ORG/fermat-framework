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
        final LoggerManager root = new LoggerManager();
        root.log(LogLevel.AGGRESSIVE_LOGGING, "Prueba de No Logging", "Prueba de No Logging", "Prueba de No Logging");
    }
    }
