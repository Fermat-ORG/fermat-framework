package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.informationMessage;

import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ConnectionRequestAction;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.InformationMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 29/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConnectionRequestActionTest {


    ConnectionRequestAction requestId;
    @Test
    public void connectionRequestActionTest(){

        InformationMessage informationMessage = Mockito.mock(InformationMessage.class);
        when(informationMessage.getAction()).thenReturn(requestId);

    }

}
