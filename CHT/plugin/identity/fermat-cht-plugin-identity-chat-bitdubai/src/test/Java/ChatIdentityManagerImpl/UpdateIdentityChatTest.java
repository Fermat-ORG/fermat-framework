package ChatIdentityManagerImpl;

import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantUpdateChatIdentityException;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure.ChatIdentityManagerImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Miguel Rincon on 4/15/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class UpdateIdentityChatTest {

    @Test
    public void updateIdentityChat() throws CantUpdateChatIdentityException {
        ChatIdentityManagerImpl chatIdentityManager = mock(ChatIdentityManagerImpl.class);

        doCallRealMethod().when(chatIdentityManager).updateIdentityChat(Mockito.anyString(), Mockito.anyString(), eq(new byte[10]));
    }

}
