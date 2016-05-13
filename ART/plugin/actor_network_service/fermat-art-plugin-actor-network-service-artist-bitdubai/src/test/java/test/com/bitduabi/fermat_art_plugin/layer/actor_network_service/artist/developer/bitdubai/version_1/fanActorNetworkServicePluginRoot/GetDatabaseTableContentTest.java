package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.fanActorNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.ArtistActorNetworkServicePluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 03/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableContentTest {

    @Mock
    List<DeveloperDatabaseTableRecord> developerDatabaseTableRecord;
    @Mock
    DeveloperObjectFactory developerObjectFactory;
    @Mock
    DeveloperDatabase developerDatabase;
    @Mock
    DeveloperDatabaseTable developerDatabaseTable ;

    @Test
    public void getDatabaseTableContentTest(){

        ArtistActorNetworkServicePluginRoot artistActorNetworkServicePluginRoot = Mockito.mock(ArtistActorNetworkServicePluginRoot.class);

        when(artistActorNetworkServicePluginRoot.getDatabaseTableContent(developerObjectFactory,
                                                                        developerDatabase,
                                                                        developerDatabaseTable)).thenReturn(developerDatabaseTableRecord);

    }

}
