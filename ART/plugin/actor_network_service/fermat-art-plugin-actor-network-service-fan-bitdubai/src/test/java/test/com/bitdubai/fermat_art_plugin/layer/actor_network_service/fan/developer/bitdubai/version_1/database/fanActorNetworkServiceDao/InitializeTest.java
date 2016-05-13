package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.fanActorNetworkServiceDao;


import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.FanActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.doThrow;

/**
 * Created by gianco on 02/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FanActorNetworkServiceDao.class)
public class InitializeTest {


    @Test
    public void initializeTest() throws CantInitializeDatabaseException {


        FanActorNetworkServiceDao fanActorNetworkServiceDao = PowerMockito.mock(FanActorNetworkServiceDao.class);
        doThrow(new CantInitializeDatabaseException()).when(fanActorNetworkServiceDao).initialize();

    }

}
