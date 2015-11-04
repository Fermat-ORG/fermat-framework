package test.com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.WalletStoreMiddlewarePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.WalletStoreMiddlewarePluginRoot;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants;

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
public class serviceStatusTest  extends TestCase {

    @Mock
    Database database;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    private UUID testPluginId;
    private WalletStoreMiddlewarePluginRoot walletStoreMiddlewarePluginRoot;

    @Before
    public void setUp() {
        walletStoreMiddlewarePluginRoot = new WalletStoreMiddlewarePluginRoot();
        walletStoreMiddlewarePluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
        testPluginId = UUID.randomUUID();
    }

    @Test
    public void createdTest() {
        Assert.assertEquals(CREATED, walletStoreMiddlewarePluginRoot.getStatus());
    }

    @Test
    public void startedTest() throws CantStartPluginException {
        try {
            when(pluginDatabaseSystem.openDatabase(testPluginId, WalletStoreMiddlewareDatabaseConstants.DATABASE_NAME)).thenReturn(database);
            walletStoreMiddlewarePluginRoot.start();
        } catch (CantStartPluginException exception) {
            Assert.assertNotNull(exception);
        } catch (Exception exception) {
            Assert.assertNotNull(exception);
        }
        Assert.assertEquals(STARTED, walletStoreMiddlewarePluginRoot.getStatus());
    }

    @Test
    public void pausedTest() {
        walletStoreMiddlewarePluginRoot.pause();
        Assert.assertEquals(PAUSED, walletStoreMiddlewarePluginRoot.getStatus());
    }

    @Test
    public void resumeTest() {
        walletStoreMiddlewarePluginRoot.resume();
        Assert.assertEquals(STARTED, walletStoreMiddlewarePluginRoot.getStatus());
    }

    @Test
    public void stopTest() {
        walletStoreMiddlewarePluginRoot.stop();
        Assert.assertEquals(STOPPED, walletStoreMiddlewarePluginRoot.getStatus());
    }
}