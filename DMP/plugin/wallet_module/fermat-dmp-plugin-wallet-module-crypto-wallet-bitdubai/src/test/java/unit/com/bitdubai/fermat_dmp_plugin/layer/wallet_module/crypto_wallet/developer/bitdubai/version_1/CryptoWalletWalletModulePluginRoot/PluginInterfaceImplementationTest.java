package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.CryptoWalletWalletModulePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.CryptoWalletWalletModulePluginRoot;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PluginInterfaceImplementationTest extends TestCase {

    /**
     * DealsWithActorAddressBook interface Mocked
     */
    @Mock
    ActorAddressBookManager actorAddressBookManager;

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
     * DealsWithWalletAddressBook interface Mocked
     */
    @Mock
    WalletAddressBookManager walletAddressBookManager;

    /**
     * DealsWithWalletContacts interface Mocked
     */
    @Mock
    WalletContactsManager walletContactsManager;


    CryptoWalletWalletModulePluginRoot cryptoWalletWalletModulePluginRoot;

    @Before
    public void setUp() throws Exception {
        cryptoWalletWalletModulePluginRoot = new CryptoWalletWalletModulePluginRoot();
        cryptoWalletWalletModulePluginRoot.setActorAddressBookManager(actorAddressBookManager);
        cryptoWalletWalletModulePluginRoot.setBitcoinWalletManager(bitcoinWalletManager);
        cryptoWalletWalletModulePluginRoot.setCryptoVaultManager(cryptoVaultManager);
        cryptoWalletWalletModulePluginRoot.setErrorManager(errorManager);
        cryptoWalletWalletModulePluginRoot.setExtraUserManager(extraUserManager);
        cryptoWalletWalletModulePluginRoot.setOutgoingExtraUserManager(outgoingExtraUserManager);
        cryptoWalletWalletModulePluginRoot.setWalletAddressBookManager(walletAddressBookManager);
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
