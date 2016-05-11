package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.artistActorNetworkServiceManager;

import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceManager;

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
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 03/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServiceManager.class)
public class ExposeIdentitiesTest {

    @Mock
    Collection<ArtistExposingData> artistExposingDataList;
    @Test
    public void exposeIdentitiesTest() throws CantExposeIdentitiesException {

        ArtistActorNetworkServiceManager artistActorNetworkServiceManager = PowerMockito.mock(ArtistActorNetworkServiceManager.class);

        doCallRealMethod().when(artistActorNetworkServiceManager).exposeIdentities(artistExposingDataList);

    }

}
