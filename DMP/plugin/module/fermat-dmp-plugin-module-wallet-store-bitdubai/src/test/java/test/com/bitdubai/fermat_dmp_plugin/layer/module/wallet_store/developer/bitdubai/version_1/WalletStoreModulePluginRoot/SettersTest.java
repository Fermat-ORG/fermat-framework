package test.com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.WalletStoreModulePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;

/**
 * Created by Nerio on 18/07/15.
 */
public class SettersTest {

    private UUID testPluginId;

    @Test
    public void ErrorManagerTest() {
        WalletContactsMiddlewarePluginRoot root = new WalletContactsMiddlewarePluginRoot();
        ErrorManager errorManager = mock(ErrorManager.class);
        root.setErrorManager(errorManager);
    }

    @Test
    public void LogManagerTest() {
        WalletContactsMiddlewarePluginRoot root = new WalletContactsMiddlewarePluginRoot();
        LogManager logManager = mock(LogManager.class);
        root.setLogManager(logManager);
    }

    @Test
    public void PluginDatabaseSystemTest() {
        WalletContactsMiddlewarePluginRoot root = new WalletContactsMiddlewarePluginRoot();
        PluginDatabaseSystem pluginDatabaseSystem = mock(PluginDatabaseSystem.class);
        root.setPluginDatabaseSystem(pluginDatabaseSystem);
    }

    @Test
    public void idTest() {
        WalletContactsMiddlewarePluginRoot root = new WalletContactsMiddlewarePluginRoot();
        testPluginId = UUID.randomUUID();
//    UUID uuid = mock(UUID.class);
        root.setId(testPluginId);
    }
}
