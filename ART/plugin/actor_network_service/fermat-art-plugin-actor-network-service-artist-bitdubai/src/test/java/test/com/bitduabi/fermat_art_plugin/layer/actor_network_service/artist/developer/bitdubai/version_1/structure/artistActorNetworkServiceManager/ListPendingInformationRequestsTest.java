package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.artistActorNetworkServiceManager;

import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingInformationRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtArtistExtraData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 04/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServiceManager.class)
public class ListPendingInformationRequestsTest {

    @Mock
    List<ArtArtistExtraData<ArtistExternalPlatformInformation>> artistExternalData;
    @Test
    public void listPendingInformationRequestsTest() throws CantListPendingInformationRequestsException {

        ArtistActorNetworkServiceManager artistActorNetworkServiceManager = PowerMockito.mock(ArtistActorNetworkServiceManager.class);

        RequestType requestType = null;

        when(artistActorNetworkServiceManager.listPendingInformationRequests(requestType)).thenReturn(artistExternalData);

    }

}
