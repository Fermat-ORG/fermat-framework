package unit.com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.CryptoWalletNicheWalletTypePluginRoot;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.CryptoWalletNicheWalletTypePluginRoot;

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
     * DealsWithWalletAddressBook interface Mocked
     */
    @Mock
    WalletAddressBookManager walletAddressBookManager;

    /**
     * DealsWithWalletContacts interface Mocked
     */
    @Mock
    WalletContactsManager walletContactsManager;


    CryptoWalletNicheWalletTypePluginRoot cryptoWalletNicheWalletTypePluginRoot;

    @Before
    public void setUp() throws Exception {
        cryptoWalletNicheWalletTypePluginRoot = new CryptoWalletNicheWalletTypePluginRoot();
        cryptoWalletNicheWalletTypePluginRoot.setActorAddressBookManager(actorAddressBookManager);
        cryptoWalletNicheWalletTypePluginRoot.setBitcoinWalletManager(bitcoinWalletManager);
        cryptoWalletNicheWalletTypePluginRoot.setCryptoVaultManager(cryptoVaultManager);
        cryptoWalletNicheWalletTypePluginRoot.setErrorManager(errorManager);
        cryptoWalletNicheWalletTypePluginRoot.setExtraUserManager(extraUserManager);
        cryptoWalletNicheWalletTypePluginRoot.setLogManager(logManager);
        cryptoWalletNicheWalletTypePluginRoot.setOutgoingExtraUserManager(outgoingExtraUserManager);
        cryptoWalletNicheWalletTypePluginRoot.setWalletAddressBookManager(walletAddressBookManager);
        cryptoWalletNicheWalletTypePluginRoot.setWalletContactsManager(walletContactsManager);
    }

    @Test
    public void testGetCryptoWallet_NotNull() throws Exception {
        CryptoWallet cryptoWallet = cryptoWalletNicheWalletTypePluginRoot.getCryptoWallet();
        assertNotNull(cryptoWallet);
    }

    @Test(expected=CantGetCryptoWalletException.class)
    public void testGetCryptoWallet_CantGetActorAddressBookRegistryException() throws Exception {
        doThrow(new CantGetActorAddressBookRegistryException()).when(actorAddressBookManager).getActorAddressBookRegistry();

        cryptoWalletNicheWalletTypePluginRoot.getCryptoWallet();
    }

    @Test(expected=CantGetCryptoWalletException.class)
    public void testGetCryptoWallet_CantGetWalletContactRegistryException() throws Exception {
        doThrow(new CantGetWalletContactRegistryException()).when(walletContactsManager).getWalletContactsRegistry();

        cryptoWalletNicheWalletTypePluginRoot.getCryptoWallet();
    }

    @Test(expected=CantGetCryptoWalletException.class)
    public void testGetCryptoWallet_CantGetWalletAddressBookRegistryException() throws Exception {
        doThrow(new CantGetWalletAddressBookRegistryException()).when(walletAddressBookManager).getWalletAddressBookRegistry();

        cryptoWalletNicheWalletTypePluginRoot.getCryptoWallet();
    }
}
