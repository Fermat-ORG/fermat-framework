package CryptoVaultTests;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantCreateCryptoWalletException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.params.TestNet3Params;
import org.junit.Ignore;
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

    @Mock PluginFileSystem pluginFileSystem;
    @Mock ErrorManager errorManager;
    @Mock PluginDatabaseSystem pluginDatabaseSystem;

    @Ignore
    @Test
    public void createNewVault() throws CantCreateCryptoWalletException, AddressFormatException, InsufficientMoneyException {
        userId = UUID.randomUUID();
        BitcoinCryptoVault vault = new BitcoinCryptoVault(userId);
        vault.setErrorManager(errorManager);
        vault.setPluginFileSystem(pluginFileSystem);
        vault.setPluginId(UUID.randomUUID());
        vault.setPluginDatabaseSystem(pluginDatabaseSystem);

        vault.loadOrCreateVault();

        System.out.println(vault.getAddress().getAddress() + " len: " + vault.getAddress().getAddress().length());
        Wallet wallet = new Wallet(TestNet3Params.get());
        Address address = new Address(TestNet3Params.get(), vault.getAddress().getAddress());
        Transaction tx = new Transaction(TestNet3Params.get());
        System.out.println(tx.getHash().toString().length());
    }

    public void loadExistingVault(){
        userId = UUID.fromString("90e95ff4-c7de-4982-bdee-6fda3c96cbfb");

    }
}
