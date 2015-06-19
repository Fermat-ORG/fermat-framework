package CryptoVaultTests;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.DealsWithBitcoinCryptoNetwork;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantCreateCryptoWalletException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

/**
 * Created by rodrigo on 2015.06.17..
 */
@RunWith(MockitoJUnitRunner.class)
public class ConnectCryptoVaultToBitcoinNetworkTest implements DealsWithBitcoinCryptoNetwork{

    @Mock PluginFileSystem pluginFileSystem;
    @Mock ErrorManager errorManager;
    @Mock PluginDatabaseSystem pluginDatabaseSystem;
    @Mock BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;
    UUID userId;


    @Test
    public void connectToBitcoinTes() throws CantCreateCryptoWalletException {
        userId = UUID.randomUUID();
        BitcoinCryptoVault vault = new BitcoinCryptoVault(userId);
        vault.setErrorManager(errorManager);
        vault.setPluginFileSystem(pluginFileSystem);
        vault.setPluginId(UUID.randomUUID());
        vault.setPluginDatabaseSystem(pluginDatabaseSystem);

        vault.loadOrCreateVault();
        bitcoinCryptoNetworkManager.setVault(vault);
        bitcoinCryptoNetworkManager.connectToBitcoinNetwork();
    }

    @Override
    public void setBitcoinCryptoNetworkManager(BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager) {
        this.bitcoinCryptoNetworkManager = bitcoinCryptoNetworkManager;

    }
}
