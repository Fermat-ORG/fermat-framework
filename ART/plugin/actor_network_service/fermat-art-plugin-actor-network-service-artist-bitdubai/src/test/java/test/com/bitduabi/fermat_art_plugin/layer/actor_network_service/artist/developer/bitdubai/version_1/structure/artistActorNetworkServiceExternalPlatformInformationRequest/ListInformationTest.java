package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.artistActorNetworkServiceExternalPlatformInformationRequest;

import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceExternalPlatformInformationRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 03/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ListInformationTest {


    @Mock
    List<ArtistExternalPlatformInformation> artistExternalPlatformInformation;

    @Test
    public void listInformationTest() {
        ArtistActorNetworkServiceExternalPlatformInformationRequest artistActorNetworkServiceExternalPlatformInformationRequest = PowerMockito.mock(ArtistActorNetworkServiceExternalPlatformInformationRequest.class);

        when(artistActorNetworkServiceExternalPlatformInformationRequest.listInformation()).thenReturn(artistExternalPlatformInformation);

    }
}
