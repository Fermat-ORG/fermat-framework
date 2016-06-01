package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.tokenlyIdentityFanManagerImpl;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.TokenlyIdentityFanManagerImpl;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 06/05/16.
 */
public class SetPluginFileSystemTest {

    @Mock
    PluginFileSystem pluginFileSystem;

    @Test
    public void setPluginFileSystemTest(){
        TokenlyIdentityFanManagerImpl tokenlyIdentityFanManager = Mockito.mock(TokenlyIdentityFanManagerImpl.class);

        doCallRealMethod().when(tokenlyIdentityFanManager).setPluginFileSystem(pluginFileSystem);

    }

}
