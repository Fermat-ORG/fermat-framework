import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.cry_1_crypto_network.bitcoin.exceptions.CantCreateCryptoWalletException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

/**
 * Created by rodrigo on 11/06/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateNewVaultTest {
    UUID userId;

    @Mock
    PluginFileSystem pluginFileSystem;
    @Mock
    ErrorManager errorManager;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Test
    public void createNewVault() throws CantCreateCryptoWalletException {
        userId = UUID.randomUUID();
        BitcoinCryptoVault vault = new BitcoinCryptoVault(userId);
        vault.setErrorManager(errorManager);
        vault.setPluginFileSystem(pluginFileSystem);
        vault.setPluginId(UUID.randomUUID());
        vault.setPluginDatabaseSystem(pluginDatabaseSystem);

        vault.loadOrCreateVault();

    }

    public void loadExistingVault(){
        userId = UUID.fromString("90e95ff4-c7de-4982-bdee-6fda3c96cbfb");

    }
}
