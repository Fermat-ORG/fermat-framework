package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.requestMessage;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ConnectionRequestAction;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.RequestMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 29/04/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RequestMessage.class)
public class GetTests {


    UUID requestId;
    String senderPublicKey;
    PlatformComponentType senderActorType;
    String senderAlias;
    byte[] senderImage;
    String destinationPublicKey;
    PlatformComponentType destinationActorType;
    ConnectionRequestAction requestAction;

    @Test
    public void getRequestIdTest(){

        RequestMessage requestMessage = PowerMockito.mock(RequestMessage.class);
        when(requestMessage.getRequestId()).thenReturn(requestId);
    }
    @Test
    public void getSenderPublicKeyTest(){

        RequestMessage requestMessage = PowerMockito.mock(RequestMessage.class);
        when(requestMessage.getSenderPublicKey()).thenReturn(senderPublicKey);
    }
    @Test
    public void getSenderActorTypeTest(){
        RequestMessage requestMessage = PowerMockito.mock(RequestMessage.class);
        when(requestMessage.getSenderActorType()).thenReturn(senderActorType);
    }
    @Test
    public void getSenderAliasTest(){
        RequestMessage requestMessage = PowerMockito.mock(RequestMessage.class);
        when(requestMessage.getSenderAlias()).thenReturn(senderAlias);
    }
    @Test
    public void getSenderImageTest(){
        RequestMessage requestMessage = PowerMockito.mock(RequestMessage.class);
        when(requestMessage.getSenderImage()).thenReturn(senderImage);
    }
    @Test
    public void getDestinationPublicKeyTest(){
        RequestMessage requestMessage = PowerMockito.mock(RequestMessage.class);
        when(requestMessage.getDestinationPublicKey()).thenReturn(destinationPublicKey);
    }
    @Test
    public void getDestinationActorTypeTest(){
        RequestMessage requestMessage = PowerMockito.mock(RequestMessage.class);
        when(requestMessage.getDestinationActorType()).thenReturn(destinationActorType);
    }
    @Test
    public void getRequestActionTest(){
        RequestMessage requestMessage = PowerMockito.mock(RequestMessage.class);
        when(requestMessage.getRequestAction()).thenReturn(requestAction);
    }
}
