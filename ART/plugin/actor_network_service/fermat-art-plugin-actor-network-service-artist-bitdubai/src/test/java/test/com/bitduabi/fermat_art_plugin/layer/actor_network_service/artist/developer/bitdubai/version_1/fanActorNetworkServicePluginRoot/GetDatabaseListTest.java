package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.fanActorNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.ArtistActorNetworkServicePluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

/**
 * Created by gianco on 03/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseListTest {


    @Mock
    List<DeveloperDatabase> developerDatabase;
    @Mock
    DeveloperObjectFactory developerObjectFactory;

    @Test
    public void getDatabaseListTest() {

        ArtistActorNetworkServicePluginRoot artistActorNetworkServicePluginRoot = Mockito.mock(ArtistActorNetworkServicePluginRoot.class);

        when(artistActorNetworkServicePluginRoot.getDatabaseList(developerObjectFactory)).thenReturn(developerDatabase);

    }

}
