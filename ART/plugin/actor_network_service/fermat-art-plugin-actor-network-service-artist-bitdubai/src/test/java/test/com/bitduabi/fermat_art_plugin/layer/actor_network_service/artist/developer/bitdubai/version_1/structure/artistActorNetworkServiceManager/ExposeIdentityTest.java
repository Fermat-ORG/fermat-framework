package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.artistActorNetworkServiceManager;

import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 03/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServiceManager.class)
public class ExposeIdentityTest {

    final ArtistExposingData artist = null;

    @Test
    public void exposeIdentityTest() throws CantExposeIdentityException {

        ArtistActorNetworkServiceManager artistActorNetworkServiceManager = PowerMockito.mock(ArtistActorNetworkServiceManager.class);

        doCallRealMethod().when(artistActorNetworkServiceManager).exposeIdentity(artist);

    }
}
