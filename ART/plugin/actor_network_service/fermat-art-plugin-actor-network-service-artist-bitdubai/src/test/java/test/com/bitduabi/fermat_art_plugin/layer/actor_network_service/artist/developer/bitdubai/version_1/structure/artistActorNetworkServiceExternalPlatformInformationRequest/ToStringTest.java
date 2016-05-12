package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.artistActorNetworkServiceExternalPlatformInformationRequest;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceExternalPlatformInformationRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 03/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ToStringTest {

    String requestId = null;
    String requesterPublicKey = null;
    String requesterActorType = null;
    String cryptoBrokerPublicKey = null;
    String updateTime = null;
    String type = null;
    String state = null;
    String informationList = null;


    @Test
    public void toStringTest(){

        ArtistActorNetworkServiceExternalPlatformInformationRequest artistActorNetworkServiceExternalPlatformInformationRequest = Mockito.mock(ArtistActorNetworkServiceExternalPlatformInformationRequest.class);

        when(artistActorNetworkServiceExternalPlatformInformationRequest.toString()).thenReturn(requestId,
                                                                                                requesterPublicKey,
                                                                                                requesterActorType,
                                                                                                cryptoBrokerPublicKey,
                                                                                                updateTime,
                                                                                                type,
                                                                                                state,
                                                                                                informationList);
    }

}
