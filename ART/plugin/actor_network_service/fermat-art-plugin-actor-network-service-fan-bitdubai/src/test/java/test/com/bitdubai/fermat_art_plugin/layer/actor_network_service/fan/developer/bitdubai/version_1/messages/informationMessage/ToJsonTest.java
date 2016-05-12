package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.informationMessage;

import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.InformationMessage;
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

        InformationMessage informationMessage = Mockito.mock(InformationMessage.class);
        JsonObject jsonObject = new JsonObject();
        when(informationMessage.toJson()).thenReturn(String.valueOf(jsonObject));

    }

}
