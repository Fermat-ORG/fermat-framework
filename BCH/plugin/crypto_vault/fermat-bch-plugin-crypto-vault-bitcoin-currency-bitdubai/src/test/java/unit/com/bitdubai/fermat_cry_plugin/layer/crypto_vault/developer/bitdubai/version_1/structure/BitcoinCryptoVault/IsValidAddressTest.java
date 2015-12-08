package unit.com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantCreateCryptoWalletException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by rodrigo on 2015.07.10..
 */
public class IsValidAddressTest {

    @Test
    public void test() throws CantCreateCryptoWalletException {
        BitcoinCryptoVault vault = new BitcoinCryptoVault("replace_device_user_key");
        CryptoAddress badAddress = new CryptoAddress("sdfkjsdfkjsdflksdf", CryptoCurrency.BITCOIN);
        Assert.assertFalse(vault.isValidAddress(badAddress));

        CryptoAddress goodAddress = new CryptoAddress("mwTdg897T6WEFRnFVm87APwpUeQb6jMgi6", CryptoCurrency.BITCOIN);
        Assert.assertTrue(vault.isValidAddress(goodAddress));
    }
}
