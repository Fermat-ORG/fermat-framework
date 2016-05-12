package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.requestMessage;

import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.RequestMessage;
import com.google.gson.JsonObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 29/04/16.
 */
@RunWith(MockitoJUnitRunner.class)

public class ToJsonTest {

    @Test
    public void toJsonTest(){

        RequestMessage requestMessage = Mockito.mock(RequestMessage.class);
        JsonObject jsonObject = new JsonObject();
        when(requestMessage.toJson()).thenReturn(String.valueOf(jsonObject));

    }

}
