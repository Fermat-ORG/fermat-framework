package unit.com.bitdubai.fermat_pip_plugin.layer.module.developer.developer.bitdubai.version_1.DeveloperModulePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_plugin.layer.module.developer.developer.bitdubai.version_1.DeveloperModulePluginRoot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Nerio on 18/07/15.
 */
public class SettersTest {

    UUID testPluginId;

    @Before
    public void setUp() throws Exception {
        testPluginId = UUID.randomUUID();

    }
    @Test
    public void testSetErrorManager(){
        DeveloperModulePluginRoot root  = new DeveloperModulePluginRoot();
        ErrorManager errorManager = mock(ErrorManager.class);
        root.setErrorManager(errorManager);
    }

    @Test
    public void testSetLogManager(){
        DeveloperModulePluginRoot root  = new DeveloperModulePluginRoot();
        LogManager logManager= mock(LogManager.class);
        root.setLogManager(logManager);
    }

    @Test
    public void testSetUUID(){
        DeveloperModulePluginRoot root  = new DeveloperModulePluginRoot();
        root.setId(testPluginId);
    }
}
