package unit.com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.structure.LoggerManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by rodrigo on 2015.06.25..
 */
public class GenerateLoggingTest {

    LoggerManager root;

    @Before
    public void setUp() {
        root = new LoggerManager();

    }

    @Test
    public void ExecuteLog() {
        root.log(LogLevel.NOT_LOGGING, "Simple Login", "Intermediate Login", "Agressive logging");
        root.log(LogLevel.MINIMAL_LOGGING, "Simple Login", "Intermediate Login", "Agressive logging");
        root.log(LogLevel.MODERATE_LOGGING, "Simple Login", "Intermediate Login", "Agressive logging");
        root.log(LogLevel.AGGRESSIVE_LOGGING, "Simple Login", "Intermediate Login", "Agressive logging");
        root.getOutputMessage();
    }

    @Test
    public void ExecuteLog_MesaggeNull() {
        root.log(LogLevel.AGGRESSIVE_LOGGING, null, null, null);
    }
}
