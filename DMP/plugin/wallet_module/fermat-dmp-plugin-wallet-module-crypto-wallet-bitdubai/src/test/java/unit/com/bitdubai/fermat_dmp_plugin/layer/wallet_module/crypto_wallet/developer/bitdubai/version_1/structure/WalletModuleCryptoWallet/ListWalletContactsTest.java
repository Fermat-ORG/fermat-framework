package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.*;
import static com.googlecode.catchexception.CatchException.*;
import static org.fest.assertions.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ListWalletContactsTest extends TestCase {

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
     * DealsWithWalletAddressBook interface Mocked
     */
    @Mock
    WalletAddressBookManager walletAddressBookManager;

    /**
     * DealsWithWalletContacts interface Mocked
     */
    @Mock
    WalletContactsManager walletContactsManager;


    @Mock
    WalletContactsRegistry walletContactsRegistry;

    String walletPublicKey;

    WalletModuleCryptoWallet walletModuleCryptoWallet;

    @Before
    public void setUp() throws Exception {
        doReturn(walletContactsRegistry).when(walletContactsManager).getWalletContactsRegistry();
        walletPublicKey = AsymmectricCryptography.derivePublicKey(AsymmectricCryptography.createPrivateKey());
        walletModuleCryptoWallet = new WalletModuleCryptoWallet();
        walletModuleCryptoWallet.setActorAddressBookManager(actorAddressBookManager);
        walletModuleCryptoWallet.setErrorManager(errorManager);
        walletModuleCryptoWallet.setWalletAddressBookManager(walletAddressBookManager);
        walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
        walletModuleCryptoWallet.initialize();
    }

    @Test
    public void testListWalletContacts_NotNull() throws Exception {
        List<WalletContactRecord> walletContactRecordsList = walletModuleCryptoWallet.listWalletContacts(walletPublicKey);
        assertThat(walletContactRecordsList).isNotNull();
    }

    @Test
    public void testListWalletContacts_CantGetAllWalletContactsException() throws Exception {
        doThrow(new com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException())
        .when(walletContactsRegistry).listWalletContacts(anyString());

        catchException(walletModuleCryptoWallet).listWalletContacts(walletPublicKey);
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantGetAllWalletContactsException.class);
    }

    @Test
    public void testListWalletContacts_RegistryIsNotInitialized_CantGetAllWalletContactsException() throws Exception {
        walletModuleCryptoWallet = new WalletModuleCryptoWallet();
        catchException(walletModuleCryptoWallet).listWalletContacts(walletPublicKey);
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantGetAllWalletContactsException.class);
    }
}
