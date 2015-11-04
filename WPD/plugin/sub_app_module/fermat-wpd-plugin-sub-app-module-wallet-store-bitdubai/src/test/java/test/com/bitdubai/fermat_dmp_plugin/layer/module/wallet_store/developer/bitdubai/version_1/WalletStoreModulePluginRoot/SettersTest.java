package test.com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.WalletStoreModulePluginRoot;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.WalletStoreModulePluginRoot;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nerio on 20/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SettersTest {

    @Mock
    LogManager logManager;
    @Mock
    ErrorManager errorManager;
    @Mock
    EventManager eventManager;
    @Mock
    WalletStoreManager walletStoreManager;
    @Mock
    com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletStoreManager walletStoreNetworkManager;

    private UUID testPluginId;
    private WalletStoreModulePluginRoot walletStoreModulePluginRoot;

    static final String CLASS = "ClassToTest";
    static final LogLevel LOG_LEVEL = LogLevel.MODERATE_LOGGING;

    @Before
    public void setUp(){
        walletStoreModulePluginRoot = new WalletStoreModulePluginRoot();
        testPluginId = UUID.randomUUID();
    }

    @Test
    public void idTest() {
        walletStoreModulePluginRoot.setId(testPluginId);
    }

    @Test
    public void LogManagerTest() {
        walletStoreModulePluginRoot.setLogManager(logManager);
    }

    @Test
    public void ErrorManagerTest() {
        walletStoreModulePluginRoot.setErrorManager(errorManager);
    }

    @Test
    public void EventManagerTest() {
        walletStoreModulePluginRoot.setEventManager(eventManager);
    }

    @Test
    public void WalletStoreManagerTest() {
        walletStoreModulePluginRoot.setWalletStoreManager(walletStoreManager);
    }

    @Test
    public void WalletStoreNetworkManagerTest() {
        walletStoreModulePluginRoot.setWalletStoreManager(walletStoreNetworkManager);
    }

    @Test
    public void LoggingLevelPerClassTest(){
        Map<String, LogLevel> data = new HashMap<String, LogLevel>();
        data.put(CLASS, LOG_LEVEL);
        walletStoreModulePluginRoot.setLoggingLevelPerClass(data);

        assertEquals(walletStoreModulePluginRoot.getLogLevelByClass(CLASS), LOG_LEVEL);

        data.clear();
        LogLevel newLogLevel = LogLevel.AGGRESSIVE_LOGGING;
        data.put(CLASS, newLogLevel);
        walletStoreModulePluginRoot.setLoggingLevelPerClass(data);

        assertEquals(walletStoreModulePluginRoot.getLogLevelByClass(CLASS), newLogLevel);
    }


}
