package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.CryptoWalletWalletModulePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.OutgoingExtraUserManager;

import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.CryptoWalletCryptoModulePluginRoot;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PluginInterfaceImplementationTest extends TestCase {


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
        cryptoWalletWalletModulePluginRoot.setOutgoingExtraUserManager(outgoingExtraUserManager);
        cryptoWalletWalletModulePluginRoot.setCryptoAddressBookManager(cryptoAddressBookManager);
        cryptoWalletWalletModulePluginRoot.setWalletContactsManager(walletContactsManager);
    }

    @Test
    public void testStart() throws Exception {
        cryptoWalletWalletModulePluginRoot.start();
    }

    @Test
    public void testStop() throws Exception {
        cryptoWalletWalletModulePluginRoot.stop();
    }

    @Test
    public void testResume() throws Exception {
        cryptoWalletWalletModulePluginRoot.resume();
    }

    @Test
    public void testPause() throws Exception {
        cryptoWalletWalletModulePluginRoot.pause();
    }

    @Test
    public void testGetStatus_NotNull() throws Exception {
        ServiceStatus serviceStatus = cryptoWalletWalletModulePluginRoot.getStatus();
        assertNotNull(serviceStatus);
    }
}
