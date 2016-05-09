package ChatIdentityImplTest;

import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure.ChatIdentityImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Miguel Rincon on 4/15/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class SetPluginIdTest {

    @Test
    public void setPluginId() {
        ChatIdentityImpl chatIdentity = mock(ChatIdentityImpl.class);

        doCallRealMethod().when(chatIdentity).setPluginId(Mockito.any(UUID.class));
    }

}
