package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.artistActorNetworkServiceExternalPlatformInformationRequest;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceExternalPlatformInformationRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 03/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServiceExternalPlatformInformationRequest.class)
public class GetTests {

    UUID requestId = null;
    String requesterPublicKey = null;
    PlatformComponentType requesterActorType = null;
    String cryptoBrokerPublicKey = null;
    long updateTime = 0;
    RequestType type = null;
    ProtocolState state = null;

    @Test
    public void getRequestIdTest(){
        ArtistActorNetworkServiceExternalPlatformInformationRequest artistActorNetworkServiceExternalPlatformInformationRequest = PowerMockito.mock(ArtistActorNetworkServiceExternalPlatformInformationRequest.class);
        when(artistActorNetworkServiceExternalPlatformInformationRequest.getRequestId()).thenReturn(requestId);
    }

    @Test
    public void getRequesterPublicKeyTest(){
        ArtistActorNetworkServiceExternalPlatformInformationRequest artistActorNetworkServiceExternalPlatformInformationRequest = PowerMockito.mock(ArtistActorNetworkServiceExternalPlatformInformationRequest.class);
        when(artistActorNetworkServiceExternalPlatformInformationRequest.getRequesterPublicKey()).thenReturn(requesterPublicKey);
    }

    @Test
    public void getRequesterActorTypeTest(){
        ArtistActorNetworkServiceExternalPlatformInformationRequest artistActorNetworkServiceExternalPlatformInformationRequest = PowerMockito.mock(ArtistActorNetworkServiceExternalPlatformInformationRequest.class);
        when(artistActorNetworkServiceExternalPlatformInformationRequest.getRequesterActorType()).thenReturn(requesterActorType);
    }

    @Test
    public void getArtistPublicKeyTest(){
        ArtistActorNetworkServiceExternalPlatformInformationRequest artistActorNetworkServiceExternalPlatformInformationRequest = PowerMockito.mock(ArtistActorNetworkServiceExternalPlatformInformationRequest.class);
        when(artistActorNetworkServiceExternalPlatformInformationRequest.getArtistPublicKey()).thenReturn(cryptoBrokerPublicKey);
    }

    @Test
    public void getUpdateTimeTest(){
        ArtistActorNetworkServiceExternalPlatformInformationRequest artistActorNetworkServiceExternalPlatformInformationRequest = PowerMockito.mock(ArtistActorNetworkServiceExternalPlatformInformationRequest.class);
        when(artistActorNetworkServiceExternalPlatformInformationRequest.getUpdateTime()).thenReturn(updateTime);
    }

    @Test
    public void getTypeTest(){
        ArtistActorNetworkServiceExternalPlatformInformationRequest artistActorNetworkServiceExternalPlatformInformationRequest = PowerMockito.mock(ArtistActorNetworkServiceExternalPlatformInformationRequest.class);
        when(artistActorNetworkServiceExternalPlatformInformationRequest.getType()).thenReturn(type);
    }

    @Test
    public void getStateTest(){
        ArtistActorNetworkServiceExternalPlatformInformationRequest artistActorNetworkServiceExternalPlatformInformationRequest = PowerMockito.mock(ArtistActorNetworkServiceExternalPlatformInformationRequest.class);
        when(artistActorNetworkServiceExternalPlatformInformationRequest.getState()).thenReturn(state);
    }
}
