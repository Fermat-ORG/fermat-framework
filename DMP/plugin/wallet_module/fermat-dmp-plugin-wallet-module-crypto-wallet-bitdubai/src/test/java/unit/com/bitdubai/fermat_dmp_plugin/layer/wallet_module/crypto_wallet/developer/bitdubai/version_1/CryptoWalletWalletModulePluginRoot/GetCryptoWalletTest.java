package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.CryptoWalletWalletModulePluginRoot;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.CryptoWalletCryptoModulePluginRoot;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetCryptoWalletTest extends TestCase {

    /**
     * DealsWithBitcoinWallet interface Mocked
     */
    @Mock
    BitcoinWalletManager bitcoinWalletManager;

    /**
     * DealsWithCryptoVault interface Mocked
     */
    @Mock
    CryptoVaultManager cryptoVaultManager;

    /**
     * DealsWithErrors interface Mocked
     */
    @Mock
    ErrorManager errorManager;

    /**
     * DealsWithExtraUsers interface Mocked
     */
    @Mock
    ExtraUserManager extraUserManager;

    /**
     * LogManagerForDevelopers interface Mocked
     */
    @Mock
    LogManager logManager;

    /**
     * DealsWithOutgoingExtraUser interface Mocked
     */
    @Mock
    OutgoingExtraUserManager outgoingExtraUserManager;

    /**
     * DealsWithCryptoAddressBook interface Mocked
     */
    @Mock
    CryptoAddressBookManager cryptoAddressBookManager;

    /**
     * DealsWithWalletContacts interface Mocked
     */
    @Mock
    WalletContactsManager walletContactsManager;


    CryptoWalletCryptoModulePluginRoot cryptoWalletWalletModulePluginRoot;

    @Before
    public void setUp() throws Exception {
        cryptoWalletWalletModulePluginRoot = new CryptoWalletCryptoModulePluginRoot();
        cryptoWalletWalletModulePluginRoot.setBitcoinWalletManager(bitcoinWalletManager);
        cryptoWalletWalletModulePluginRoot.setCryptoVaultManager(cryptoVaultManager);
        cryptoWalletWalletModulePluginRoot.setErrorManager(errorManager);
        cryptoWalletWalletModulePluginRoot.setExtraUserManager(extraUserManager);
        cryptoWalletWalletModulePluginRoot.setLogManager(logManager);
        cryptoWalletWalletModulePluginRoot.setOutgoingExtraUserManager(outgoingExtraUserManager);
        cryptoWalletWalletModulePluginRoot.setCryptoAddressBookManager(cryptoAddressBookManager);
        cryptoWalletWalletModulePluginRoot.setWalletContactsManager(walletContactsManager);
    }

    @Test
    public void testGetCryptoWallet_NotNull() throws Exception {
        CryptoWallet cryptoWallet = cryptoWalletWalletModulePluginRoot.getCryptoWallet();
        assertNotNull(cryptoWallet);
    }

    @Test(expected=CantGetCryptoWalletException.class)
    public void testGetCryptoWallet_CantGetWalletContactRegistryException() throws Exception {
        doThrow(new CantGetWalletContactRegistryException()).when(walletContactsManager).getWalletContactsRegistry();

        cryptoWalletWalletModulePluginRoot.getCryptoWallet();
    }
}
