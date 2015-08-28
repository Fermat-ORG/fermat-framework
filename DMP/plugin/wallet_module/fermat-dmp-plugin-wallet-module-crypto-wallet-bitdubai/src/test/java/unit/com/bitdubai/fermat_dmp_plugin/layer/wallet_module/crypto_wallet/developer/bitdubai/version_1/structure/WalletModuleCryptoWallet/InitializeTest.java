package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantInitializeCryptoWalletManagerException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doThrow;
import static com.googlecode.catchexception.CatchException.*;
import static org.fest.assertions.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class InitializeTest extends TestCase {

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


    WalletModuleCryptoWallet walletModuleCryptoWallet;

    @Before
    public void setUp() throws Exception {
        walletModuleCryptoWallet = new WalletModuleCryptoWallet();
        walletModuleCryptoWallet.setActorAddressBookManager(actorAddressBookManager);
        walletModuleCryptoWallet.setBitcoinWalletManager(bitcoinWalletManager);
        walletModuleCryptoWallet.setCryptoVaultManager(cryptoVaultManager);
        walletModuleCryptoWallet.setErrorManager(errorManager);
        walletModuleCryptoWallet.setExtraUserManager(extraUserManager);
        walletModuleCryptoWallet.setOutgoingExtraUserManager(outgoingExtraUserManager);
        walletModuleCryptoWallet.setWalletAddressBookManager(walletAddressBookManager);
        walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
    }

    @Test
    public void testGetCryptoWallet_Success() throws Exception {
        catchException(walletModuleCryptoWallet).initialize();
        assertThat(caughtException()).isNull();
    }

    @Test
    public void testInitialize_CantGetActorAddressBookRegistryException() throws Exception {
        doThrow(new CantGetActorAddressBookRegistryException()).when(actorAddressBookManager).getActorAddressBookRegistry();

        catchException(walletModuleCryptoWallet).initialize();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantInitializeCryptoWalletManagerException.class);
    }

    @Test
    public void testInitialize_CantGetWalletContactRegistryException() throws Exception {
        doThrow(new CantGetWalletContactRegistryException()).when(walletContactsManager).getWalletContactsRegistry();

        catchException(walletModuleCryptoWallet).initialize();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantInitializeCryptoWalletManagerException.class);
    }

    @Test
    public void testInitialize_CantGetWalletAddressBookRegistryException() throws Exception {
        doThrow(new CantGetWalletAddressBookRegistryException()).when(walletAddressBookManager).getWalletAddressBookRegistry();

        catchException(walletModuleCryptoWallet).initialize();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantInitializeCryptoWalletManagerException.class);
    }

    @Test
    public void testInitialize_NullPointerException_ThrowCantInitializeCryptoWalletManagerException() throws Exception {
        walletModuleCryptoWallet.setActorAddressBookManager(null);

        catchException(walletModuleCryptoWallet).initialize();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantInitializeCryptoWalletManagerException.class);
    }

}
