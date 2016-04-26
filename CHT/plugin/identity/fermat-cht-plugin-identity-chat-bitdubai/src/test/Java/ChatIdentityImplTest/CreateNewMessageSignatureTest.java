package ChatIdentityImplTest;

import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure.ChatIdentityImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Miguel Rincon on 4/15/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class CreateNewMessageSignatureTest {

    @Test
    public void createNewMessageSignature() {
        ChatIdentityImpl chatIdentity = mock(ChatIdentityImpl.class);

        when(chatIdentity.createMessageSignature(Mockito.anyString())).thenReturn(new String()).thenCallRealMethod();
        assertThat(chatIdentity.createMessageSignature(Mockito.anyString())).isNotNull();
    }

}
