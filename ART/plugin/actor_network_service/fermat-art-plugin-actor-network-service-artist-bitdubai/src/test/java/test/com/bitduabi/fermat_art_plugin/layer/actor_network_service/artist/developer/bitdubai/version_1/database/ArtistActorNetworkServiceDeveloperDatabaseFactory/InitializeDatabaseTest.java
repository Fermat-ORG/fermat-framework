package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDeveloperDatabaseFactory;

import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 04/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServiceDeveloperDatabaseFactory.class)
public class InitializeDatabaseTest {

    final String tableId = null;

    @Test
    public void initializeDatabaseTest() throws CantInitializeDatabaseException {

        ArtistActorNetworkServiceDeveloperDatabaseFactory artistActorNetworkServiceDeveloperDatabaseFactory = PowerMockito.mock(ArtistActorNetworkServiceDeveloperDatabaseFactory.class);

        doCallRealMethod().when(artistActorNetworkServiceDeveloperDatabaseFactory).initializeDatabase(tableId);

    }

}
