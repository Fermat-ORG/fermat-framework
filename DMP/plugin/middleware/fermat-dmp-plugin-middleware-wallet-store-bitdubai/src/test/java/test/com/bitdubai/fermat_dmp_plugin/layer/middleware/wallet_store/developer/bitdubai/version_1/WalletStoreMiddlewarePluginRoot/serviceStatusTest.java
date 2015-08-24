package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.WalletStoreMiddlewarePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.WalletStoreMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants;

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
    private WalletStoreMiddlewarePluginRoot walletStoreModulePluginRoot;

    @Before
    public void setUp() {
        walletStoreModulePluginRoot = new WalletStoreMiddlewarePluginRoot();
        walletStoreModulePluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
        testPluginId = UUID.randomUUID();
    }

    @Test
    public void createdTest() {
        Assert.assertEquals(CREATED, walletStoreModulePluginRoot.getStatus());
    }

    @Test
    public void startedTest() throws CantStartPluginException {
        try {
            when(pluginDatabaseSystem.openDatabase(testPluginId, WalletStoreMiddlewareDatabaseConstants.DATABASE_NAME)).thenReturn(database);
            walletStoreModulePluginRoot.start();
        } catch (CantStartPluginException exception) {
            Assert.assertNotNull(exception);
        } catch (Exception exception) {
            Assert.assertNotNull(exception);
        }
        Assert.assertEquals(STARTED, walletStoreModulePluginRoot.getStatus());
    }

    @Test
    public void pausedTest() {
        walletStoreModulePluginRoot.pause();
        Assert.assertEquals(PAUSED, walletStoreModulePluginRoot.getStatus());
    }

    @Test
    public void resumeTest() {
        walletStoreModulePluginRoot.resume();
        Assert.assertEquals(STARTED, walletStoreModulePluginRoot.getStatus());
    }

    @Test
    public void stopTest() {
        walletStoreModulePluginRoot.stop();
        Assert.assertEquals(STOPPED, walletStoreModulePluginRoot.getStatus());
    }
}