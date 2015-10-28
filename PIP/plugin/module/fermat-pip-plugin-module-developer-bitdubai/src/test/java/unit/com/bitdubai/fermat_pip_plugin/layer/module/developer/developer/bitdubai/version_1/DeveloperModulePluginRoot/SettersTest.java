package unit.com.bitdubai.fermat_pip_plugin.layer.module.developer.developer.bitdubai.version_1.DeveloperModulePluginRoot;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_plugin.layer.module.developer.developer.bitdubai.version_1.ModuleDeveloperPluginRoot;

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
    public void testSetErrorManager(){
        ModuleDeveloperPluginRoot root  = new ModuleDeveloperPluginRoot();
        ErrorManager errorManager = mock(ErrorManager.class);
        root.setErrorManager(errorManager);
    }

    @Test
    public void testSetUUID(){
        ModuleDeveloperPluginRoot root  = new ModuleDeveloperPluginRoot();
        root.setId(testPluginId);
    }
}
