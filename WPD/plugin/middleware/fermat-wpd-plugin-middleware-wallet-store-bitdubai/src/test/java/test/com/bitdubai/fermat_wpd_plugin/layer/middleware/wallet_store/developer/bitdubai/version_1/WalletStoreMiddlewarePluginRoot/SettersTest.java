package test.com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.WalletStoreMiddlewarePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.WalletStoreMiddlewarePluginRoot;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Nerio on 20/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SettersTest extends TestCase {

    @Mock
    LogManager logManager;
    @Mock
    ErrorManager errorManager;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletStoreManager walletStoreNetworkManager;

    private UUID testPluginId;
    private WalletStoreMiddlewarePluginRoot walletStoreMiddlewarePluginRoot;

    static final String CLASS = "ClassToTest";
    static final LogLevel LOG_LEVEL = LogLevel.MODERATE_LOGGING;

    @Before
    public void setUp(){
        walletStoreMiddlewarePluginRoot = new WalletStoreMiddlewarePluginRoot();
        testPluginId = UUID.randomUUID();
    }

    @Test
    public void idTest() {
        walletStoreMiddlewarePluginRoot.setId(testPluginId);
    }

    @Test
    public void LogManagerTest() {
        walletStoreMiddlewarePluginRoot.setLogManager(logManager);
    }

    @Test
    public void ErrorManagerTest() {
        walletStoreMiddlewarePluginRoot.setErrorManager(errorManager);
    }

    @Test
    public void PluginDataBaseSystemTest() {
        walletStoreMiddlewarePluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
    }

    @Test
    public void LoggingLevelPerClassTest(){
        Map<String, LogLevel> data = new HashMap<String, LogLevel>();
        data.put(CLASS, LOG_LEVEL);
        walletStoreMiddlewarePluginRoot.setLoggingLevelPerClass(data);

        assertEquals(walletStoreMiddlewarePluginRoot.getLogLevelByClass(CLASS), LOG_LEVEL);

        data.clear();
        LogLevel newLogLevel = LogLevel.AGGRESSIVE_LOGGING;
        data.put(CLASS, newLogLevel);
        walletStoreMiddlewarePluginRoot.setLoggingLevelPerClass(data);

        assertEquals(walletStoreMiddlewarePluginRoot.getLogLevelByClass(CLASS), newLogLevel);
    }
}
