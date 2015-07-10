package unit.com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.DeveloperBitDubai;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.CryptoWalletNicheWalletTypePluginRoot;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PluginLicensorInterfaceImplementationTest extends TestCase {

    DeveloperBitDubai developerBitDubai;

    @Before
    public void setUp() throws Exception {
        developerBitDubai = new DeveloperBitDubai();
    }

    @Test
    public void testGetAmountToPay_NotNull() throws Exception {
        int amountToPay = developerBitDubai.getAmountToPay();
        assertNotNull(amountToPay);
    }

    @Test
    public void testGetCryptoCurrency_NotNull() throws Exception {
        CryptoCurrency cryptoCurrency = developerBitDubai.getCryptoCurrency();
        assertNotNull(cryptoCurrency);
    }

    @Test
    public void testGetAddress_NotNull() throws Exception {
        String address = developerBitDubai.getAddress();
        assertNotNull(address);
    }

    @Test
    public void testGetTimePeriod_NotNull() throws Exception {
        TimeFrequency timePeriod = developerBitDubai.getTimePeriod();
        assertNotNull(timePeriod);
    }
}
