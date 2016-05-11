package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.fanActorNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
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

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 03/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableList {

    @Mock
    List<DeveloperDatabaseTable> developerDatabaseTable;
    @Mock
    DeveloperObjectFactory developerObjectFactory;
    @Mock
    DeveloperDatabase developerDatabase;

    @Test
    public void getDatabaseTableList(){

        ArtistActorNetworkServicePluginRoot artistActorNetworkServicePluginRoot = Mockito.mock(ArtistActorNetworkServicePluginRoot.class);

        when(artistActorNetworkServicePluginRoot.getDatabaseTableList(developerObjectFactory,
                                                                        developerDatabase)).thenReturn(developerDatabaseTable);

    }
}
