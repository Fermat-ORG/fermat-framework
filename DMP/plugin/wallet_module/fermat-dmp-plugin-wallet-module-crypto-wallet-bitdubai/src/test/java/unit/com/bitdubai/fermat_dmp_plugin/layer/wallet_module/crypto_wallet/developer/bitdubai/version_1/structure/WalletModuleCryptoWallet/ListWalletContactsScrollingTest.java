package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoCrypto;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static com.googlecode.catchexception.CatchException.*;
import static org.fest.assertions.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ListWalletContactsScrollingTest extends TestCase {

    /**
     * DealsWithActorAddressBook interface Mocked
     */
    @Mock
    ActorAddressBookManager actorAddressBookManager;

    /**
     * DealsWithErrors interface Mocked
     */
    @Mock
    ErrorManager errorManager;

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


    @Mock
    WalletContactsRegistry walletContactsRegistry;

    String walletPublicKey;

    WalletModuleCryptoCrypto walletModuleCryptoWallet;

    @Before
    public void setUp() throws Exception {
        doReturn(walletContactsRegistry).when(walletContactsManager).getWalletContactsRegistry();
        walletPublicKey = AsymmectricCryptography.derivePublicKey(AsymmectricCryptography.createPrivateKey());
        walletModuleCryptoWallet = new WalletModuleCryptoCrypto();
        walletModuleCryptoWallet.setActorAddressBookManager(actorAddressBookManager);
        walletModuleCryptoWallet.setErrorManager(errorManager);
        walletModuleCryptoWallet.setCryptoAddressBookManager(cryptoAddressBookManager);
        walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
        walletModuleCryptoWallet.initialize();
    }

    @Test
    public void testListWalletContactsScrolling_NotNull() throws Exception {
        List<CryptoWalletWalletContact> walletContactRecordsList = walletModuleCryptoWallet.listWalletContactsScrolling(walletPublicKey, 1, 10);
        assertThat(walletContactRecordsList).isNotNull();
    }

    @Test
    public void testListWalletContacts_CantGetAllWalletContactsException() throws Exception {
        doThrow(new com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException())
        .when(walletContactsRegistry).listWalletContactsScrolling(anyString(), anyInt(), anyInt());

        catchException(walletModuleCryptoWallet).listWalletContactsScrolling(walletPublicKey, 1, 10);
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantGetAllWalletContactsException.class);
    }

    @Test
    public void ListWalletContacts_RegistryIsNotInitialized_CantGetAllWalletContactsException() throws Exception {
        walletModuleCryptoWallet = new WalletModuleCryptoCrypto();

        catchException(walletModuleCryptoWallet).listWalletContactsScrolling(walletPublicKey, 1, 10);
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantGetAllWalletContactsException.class);
    }
}
