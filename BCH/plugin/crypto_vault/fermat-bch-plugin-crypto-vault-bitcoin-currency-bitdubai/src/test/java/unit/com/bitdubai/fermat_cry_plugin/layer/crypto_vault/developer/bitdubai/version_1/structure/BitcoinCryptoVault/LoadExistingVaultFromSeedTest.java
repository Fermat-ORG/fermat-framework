package unit.com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantCreateCryptoWalletException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import org.bitcoinj.crypto.MnemonicCode;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by rodrigo on 2015.07.16..
 */
public class LoadExistingVaultFromSeedTest {

    @Test
    public void correctLoad() throws CantCreateCryptoWalletException, IOException {
        MnemonicCode code = new MnemonicCode();
        BitcoinCryptoVault vault = new BitcoinCryptoVault("replace_device_user_key");
        vault.loadExistingVaultFromSeed(code.toString() ,1);

        Assert.assertNotNull(vault);
    }
}
