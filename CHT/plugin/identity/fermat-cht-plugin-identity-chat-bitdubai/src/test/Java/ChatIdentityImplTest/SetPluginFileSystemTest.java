package ChatIdentityImplTest;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure.ChatIdentityImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Miguel Rincon on 4/15/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class SetPluginFileSystemTest {

    @Test
    public void setPluginFileSystemTest() {
        ChatIdentityImpl chatIdentity = mock(ChatIdentityImpl.class);

        doCallRealMethod().when(chatIdentity).setPluginFileSystem(Mockito.any(PluginFileSystem.class));
    }

}
