package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDao;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestExternalPlatformInformationException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceExternalPlatformInformationRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 04/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServiceDao.class)
public class CreateExternalPlatformInformationRequestTest {

    final UUID requestId = null;
    final String requesterPublicKey = null;
    final PlatformComponentType requesterActorType = null;
    final String artistPublicKey = null;
    final ProtocolState state = null;
    final RequestType type = null;
    final ArtistActorNetworkServiceExternalPlatformInformationRequest informationRequest = null;

    @Test
    public void createExternalPlatformInformationRequestTest() throws CantRequestExternalPlatformInformationException {

        ArtistActorNetworkServiceDao artistActorNetworkServiceDao = PowerMockito.mock(ArtistActorNetworkServiceDao.class);

        when(artistActorNetworkServiceDao.createExternalPlatformInformationRequest(requestId,
                requesterPublicKey,
                requesterActorType,
                artistPublicKey,
                state,
                type)).thenReturn(informationRequest);

    }
}
