package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.informationMessage;

import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.InformationMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 29/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetRequestIdTest {

//    @Mock
    UUID requestId;

    @Test
    public void getRequestIdTest(){

        InformationMessage informationMessage = Mockito.mock(InformationMessage.class);
        when(informationMessage.getRequestId()).thenReturn(requestId);

    }
}
