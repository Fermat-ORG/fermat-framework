package test.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.pip_user.device_user.DeviceUserManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;

import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

/**
 * Created by rodrigo on 2015.07.05..
 */
public class StartTest {

    @Mock
    DeviceUserManager deviceUserManager;
    @Mock
    ErrorManager errorManager;
    @Mock
    PluginFileSystem pluginFileSystem;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    EventManager eventManager;

    @Test
    public void test(){
        BitcoinCryptoVaultPluginRoot root = new BitcoinCryptoVaultPluginRoot();
        root.setDeviceUserManager(deviceUserManager);
        root.setErrorManager(errorManager);
        root.setPluginFileSystem(pluginFileSystem);
        root.setPluginDatabaseSystem(pluginDatabaseSystem);
        root.setEventManager(eventManager);
        root.setId(UUID.randomUUID());
        try {
            root.start();
        } catch (CantStartPluginException e) {
            e.printStackTrace();
        }

    }
}
