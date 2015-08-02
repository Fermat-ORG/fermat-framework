package unit.com.bitdubai.fermat_pip_plugin.layer.module.developer.developer.bitdubai.version_1.DeveloperModulePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_pip_plugin.layer.module.developer.developer.bitdubai.version_1.ModuleDeveloperPluginRoot;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nerio on 18/07/15.
 */
public class setLoggingLevelPerClassTest {

    static final String CLASS = "ClassToTest";
    static final LogLevel LOG_LEVEL = LogLevel.MODERATE_LOGGING;

    @Test
    public void testMethod(){
        ModuleDeveloperPluginRoot root = new ModuleDeveloperPluginRoot();
        Map<String, LogLevel> data = new HashMap<String, LogLevel>();
        data.put(CLASS, LOG_LEVEL);
        root.setLoggingLevelPerClass(data);

        assertEquals(ModuleDeveloperPluginRoot.getLogLevelByClass(CLASS), LOG_LEVEL);

        data.clear();
        LogLevel logLevel = LogLevel.AGGRESSIVE_LOGGING;
        data.put(CLASS, logLevel);
        root.setLoggingLevelPerClass(data);

        assertEquals(ModuleDeveloperPluginRoot.getLogLevelByClass(CLASS), logLevel);
    }
}
