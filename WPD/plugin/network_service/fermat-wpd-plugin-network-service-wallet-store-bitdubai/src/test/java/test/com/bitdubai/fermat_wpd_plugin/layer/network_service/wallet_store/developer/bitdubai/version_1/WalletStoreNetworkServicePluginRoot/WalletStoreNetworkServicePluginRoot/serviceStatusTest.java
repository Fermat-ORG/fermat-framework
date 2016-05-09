package test.com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot.WalletStoreNetworkServicePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreCatalogDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.CREATED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.PAUSED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STARTED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STOPPED;
import static org.mockito.Mockito.when;

/**
 * Created by Nerio on 22/08/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class serviceStatusTest extends TestCase {

    @Mock
    Database database;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    EventManager eventManager;
    @Mock
    ErrorManager errorManager;
    @Mock
    LogManager logManager;
    @Mock
    CommunicationLayerManager communicationLayerManager;

    private UUID testPluginId;
    private WalletStoreNetworkServicePluginRoot walletStoreNetworkServicePluginRoot;

    @Before
    public void setUp() {
        testPluginId = UUID.randomUUID();
        walletStoreNetworkServicePluginRoot = new WalletStoreNetworkServicePluginRoot();
        walletStoreNetworkServicePluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
        walletStoreNetworkServicePluginRoot.setId(testPluginId);
        walletStoreNetworkServicePluginRoot.setLogManager(logManager);
        walletStoreNetworkServicePluginRoot.setEventManager(eventManager);
        walletStoreNetworkServicePluginRoot.setErrorManager(errorManager);
        walletStoreNetworkServicePluginRoot.setCommunicationLayerManager(communicationLayerManager);
    }

    @Test
    public void createdTest() {
        Assert.assertEquals(CREATED, walletStoreNetworkServicePluginRoot.getStatus());
    }

    @Test
    public void startedTest() throws CantStartPluginException {
        try {
            when(pluginDatabaseSystem.openDatabase(testPluginId, WalletStoreCatalogDatabaseConstants.WALLET_STORE_DATABASE)).thenReturn(database);
            walletStoreNetworkServicePluginRoot.start();
        } catch (CantStartPluginException exception) {
            Assert.assertNotNull(exception);
        } catch (Exception exception) {
            Assert.assertNotNull(exception);
        }
        Assert.assertEquals(STARTED, walletStoreNetworkServicePluginRoot.getStatus());
    }

    @Test
    public void pausedTest() {
        try {
            startedTest();
        } catch (CantStartPluginException exception) {
            Assert.assertNotNull(exception);
        }
        walletStoreNetworkServicePluginRoot.pause();
        Assert.assertEquals(PAUSED, walletStoreNetworkServicePluginRoot.getStatus());
    }

    @Test
    public void resumeTest() {
        try {
            startedTest();
        } catch (CantStartPluginException exception) {
            Assert.assertNotNull(exception);
        }
        walletStoreNetworkServicePluginRoot.resume();
        Assert.assertEquals(STARTED, walletStoreNetworkServicePluginRoot.getStatus());
    }

    @Test
    public void stopTest() {
        try {
            startedTest();
        } catch (CantStartPluginException exception) {
            Assert.assertNotNull(exception);
        }
        walletStoreNetworkServicePluginRoot.stop();
        Assert.assertEquals(STOPPED, walletStoreNetworkServicePluginRoot.getStatus());
    }
}