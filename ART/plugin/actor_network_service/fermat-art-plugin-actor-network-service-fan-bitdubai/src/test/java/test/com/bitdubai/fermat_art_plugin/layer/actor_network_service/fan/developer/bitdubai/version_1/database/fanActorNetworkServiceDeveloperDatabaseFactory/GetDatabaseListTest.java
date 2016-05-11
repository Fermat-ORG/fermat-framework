package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.fanActorNetworkServiceDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.FanActorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;

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
 * Created by gianco on 02/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FanActorNetworkServiceDeveloperDatabaseFactory.class)
public class GetDatabaseListTest {

    @Mock
    List<DeveloperDatabase> DeveloperDatabase;

    @Mock
    DeveloperObjectFactory developerObjectFactory;

    @Test
    public void getDatabaseListTest() {

        FanActorNetworkServiceDeveloperDatabaseFactory fanActorNetworkServiceDeveloperDatabaseFactory = PowerMockito.mock(FanActorNetworkServiceDeveloperDatabaseFactory.class);

        when(fanActorNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory)).thenReturn(DeveloperDatabase);
    }
}
