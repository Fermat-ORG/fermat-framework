package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.networkServiceMessage;

import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.enums.MessageTypes;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.NetworkServiceMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 29/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetMessageTypeTest {


    MessageTypes messageType;


    @Test
    public void getMessageTypeTest(){

        NetworkServiceMessage networkServiceMessage = Mockito.mock(NetworkServiceMessage.class);
        when(networkServiceMessage.getMessageType()).thenReturn(messageType);

    }


}
