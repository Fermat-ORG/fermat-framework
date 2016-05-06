package test.com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot.WalletStoreNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfoManager;

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
    PluginFileSystem pluginFileSystem;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    PlatformInfoManager platformInfoManager;

    private UUID testPluginId;
    private WalletStoreNetworkServicePluginRoot walletStoreNetworkServicePluginRoot;

    static final String CLASS = "ClassToTest";
    static final LogLevel LOG_LEVEL = LogLevel.MODERATE_LOGGING;

    @Before
    public void setUp() {
        walletStoreNetworkServicePluginRoot = new WalletStoreNetworkServicePluginRoot();
        testPluginId = UUID.randomUUID();
    }

    @Test
    public void idTest() {
        walletStoreNetworkServicePluginRoot.setId(testPluginId);
    }

    @Test
    public void LogManagerTest() {
        walletStoreNetworkServicePluginRoot.setLogManager(logManager);
    }

    @Test
    public void ErrorManagerTest() {
        walletStoreNetworkServicePluginRoot.setErrorManager(errorManager);
    }

    @Test
    public void PluginDataBaseSystemTest() {
        walletStoreNetworkServicePluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
    }

    @Test
    public void PlatformInfoManagerTest() {
        walletStoreNetworkServicePluginRoot.setPlatformInfoManager(platformInfoManager);
    }

    @Test
    public void PluginFileSystemTest() {
        walletStoreNetworkServicePluginRoot.setPluginFileSystem(pluginFileSystem);
    }

    @Test
    public void LoggingLevelPerClassTest() {
        Map<String, LogLevel> data = new HashMap<String, LogLevel>();
        data.put(CLASS, LOG_LEVEL);
        walletStoreNetworkServicePluginRoot.setLoggingLevelPerClass(data);

        assertEquals(walletStoreNetworkServicePluginRoot.getLogLevelByClass(CLASS), LOG_LEVEL);

        data.clear();
        LogLevel newLogLevel = LogLevel.AGGRESSIVE_LOGGING;
        data.put(CLASS, newLogLevel);
        walletStoreNetworkServicePluginRoot.setLoggingLevelPerClass(data);

        assertEquals(walletStoreNetworkServicePluginRoot.getLogLevelByClass(CLASS), newLogLevel);
    }
}
