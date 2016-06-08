package unit.com.bitdubai.fermat_pip_plugin.layer.module.developer.developer.bitdubai.version_1.DeveloperModulePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1.DeveloperSubAppModulePluginRoot;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;

/**
 * Created by Nerio on 18/07/15.
 */
public class SettersTest {

    UUID testPluginId;

    //TODO WE NEED TO IMPLEMENT AN ASSERTION STRATEGY HERE

    @Before
    public void setUp() throws Exception {
        testPluginId = UUID.randomUUID();

    }

    @Test
    public void testSetErrorManager() {
        DeveloperSubAppModulePluginRoot root = new DeveloperSubAppModulePluginRoot();
        ErrorManager errorManager = mock(ErrorManager.class);
        root.setErrorManager(errorManager);
    }

    @Test
    public void testSetUUID() {
        DeveloperSubAppModulePluginRoot root = new DeveloperSubAppModulePluginRoot();
        root.setId(testPluginId);
    }
}
