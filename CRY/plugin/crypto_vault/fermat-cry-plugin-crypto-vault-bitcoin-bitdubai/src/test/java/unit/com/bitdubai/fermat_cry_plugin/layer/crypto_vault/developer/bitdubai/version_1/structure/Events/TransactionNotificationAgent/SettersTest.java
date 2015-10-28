package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.Events.TransactionNotificationAgent;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.events.TransactionNotificationAgent;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.Common.MockedPluginFileSystem;

/**
 * Created by rodrigo on 2015.07.15..
 */
@RunWith(MockitoJUnitRunner.class)
public class SettersTest {

    @Mock
    ErrorManager errorManager;

    @Mock
    LogManager logManager;

    @Mock
    EventManager eventManager;

    @Mock
    DeviceUserManager deviceUserManager;

    @Mock
    BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    MockedPluginFileSystem pluginFileSystem;


    @Test
    public void constructorTest(){
        pluginFileSystem = new MockedPluginFileSystem();
        TransactionNotificationAgent transactionNotificationAgent = new TransactionNotificationAgent(eventManager, pluginDatabaseSystem, errorManager, UUID.randomUUID(),  "replace_device_user_key");
    }

    @Test
    public void settersTest(){
        pluginFileSystem = new MockedPluginFileSystem();
        TransactionNotificationAgent transactionNotificationAgent = new TransactionNotificationAgent(eventManager, pluginDatabaseSystem, errorManager, UUID.randomUUID(),  "replace_device_user_key");
        transactionNotificationAgent.setErrorManager(errorManager);
        transactionNotificationAgent.setEventManager(eventManager);
        transactionNotificationAgent.setPluginId(UUID.randomUUID());
        transactionNotificationAgent.setLogManager(logManager);
        transactionNotificationAgent.setPluginDatabaseSystem(pluginDatabaseSystem);
    }
}
