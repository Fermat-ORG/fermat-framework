package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantCreateCryptoWalletException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import org.bitcoinj.core.Wallet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 27/08/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class LoadOrCreateVaultTest {
    @Mock
    Wallet mockVault;

    @Mock
    CryptoAddress mockCryptoAddress;

    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    PluginTextFile mockPluginTextFile;

    @Mock
    private LogManager mockLogManager;

    private BitcoinCryptoVault bitcoinCryptoVault;

    private UUID pluginId = UUID.randomUUID();

    private String userPublicKey = "replace_user_public_key";

    @Before
    public void setUp() throws Exception{

        bitcoinCryptoVault = new BitcoinCryptoVault(userPublicKey);
        bitcoinCryptoVault.setPluginFileSystem(pluginFileSystem);
        bitcoinCryptoVault.setUserPublicKey(userPublicKey);
        bitcoinCryptoVault.setPluginId(pluginId);
        bitcoinCryptoVault.setLogManager(mockLogManager);
        when(pluginFileSystem.createTextFile(pluginId, userPublicKey, userPublicKey.toString() + ".vault", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT)).thenReturn(mockPluginTextFile);


    }
    @Test
    public void loadOrCreateVaultTest_LoadOk_ThrowsCantCreateCryptoWalletException() throws CantCreateCryptoWalletException {

        catchException(bitcoinCryptoVault).loadOrCreateVault();
        assertThat(caughtException())
                .isNull();

    }

    @Test
    public void loadOrCreateVaultTest_LoadError_ThrowsCantCreateCryptoWalletException() throws Exception {

        when(pluginFileSystem.createTextFile(pluginId, userPublicKey, userPublicKey.toString() + ".vault", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT)).thenReturn(null);

        catchException(bitcoinCryptoVault).loadOrCreateVault();
        assertThat(caughtException())
                .isNotNull().isInstanceOf(CantCreateCryptoWalletException.class);

    }
}
