package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.artistActorNetworkServiceSearch;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListArtistsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceSearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 04/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServiceSearch.class)
public class GetResultTest {

    @Mock
    List<ArtistExposingData> artistExposingData;

    @Test
    public void getResultTest() throws CantListArtistsException {

        ArtistActorNetworkServiceSearch artistActorNetworkServiceSearch = PowerMockito.mock(ArtistActorNetworkServiceSearch.class);

        when(artistActorNetworkServiceSearch.getResult()).thenReturn(artistExposingData);

    }

    @Test
    public void resultTest() throws CantListArtistsException {

        ArtistActorNetworkServiceSearch artistActorNetworkServiceSearch = PowerMockito.mock(ArtistActorNetworkServiceSearch.class);

        PlatformComponentType actorTypeToLookFor = null;

        when(artistActorNetworkServiceSearch.getResult(actorTypeToLookFor)).thenReturn(artistExposingData);


    }

}
