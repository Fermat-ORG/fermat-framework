package ChatIdentityManagerImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantGetChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure.ChatIdentityManagerImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Miguel Rincon on 4/15/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetIdentityChatUserTest {

    @Test
    public void getIdentityChatUserTest() throws CantGetChatIdentityException {
        ChatIdentityManagerImpl chatIdentityManager = mock(ChatIdentityManagerImpl.class);

        when(chatIdentityManager.getIdentityChatUser()).thenReturn(new ChatIdentity() {
            @Override
            public void setNewProfileImage(byte[] newProfileImage) {

            }

            @Override
            public String createMessageSignature(String message) {
                return null;
            }

            @Override
            public boolean getIsPaymetForChat() {
                return false;
            }

            @Override
            public String getPublicKey() {
                return null;
            }

            @Override
            public Actors getActorType() {
                return null;
            }

            @Override
            public String getAlias() {
                return null;
            }

            @Override
            public byte[] getImage() {
                return new byte[0];
            }
        }).thenCallRealMethod();
        assertThat(chatIdentityManager.getIdentityChatUser()).isNotNull();
    }

}
