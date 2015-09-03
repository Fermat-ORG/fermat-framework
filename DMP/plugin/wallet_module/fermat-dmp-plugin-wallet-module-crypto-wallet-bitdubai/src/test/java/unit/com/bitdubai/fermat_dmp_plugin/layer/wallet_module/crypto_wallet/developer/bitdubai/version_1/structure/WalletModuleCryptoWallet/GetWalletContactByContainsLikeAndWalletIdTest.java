package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetWalletContactException;
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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class GetWalletContactByContainsLikeAndWalletIdTest extends TestCase {

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
    String actorName;

    WalletModuleCryptoCrypto walletModuleCryptoWallet;

    @Before
    public void setUp() throws Exception {
        doReturn(walletContactsRegistry).when(walletContactsManager).getWalletContactsRegistry();
        walletPublicKey = AsymmectricCryptography.derivePublicKey(AsymmectricCryptography.createPrivateKey());
        actorName = "Leonardo Dicaprio";
        walletModuleCryptoWallet = new WalletModuleCryptoCrypto();
        walletModuleCryptoWallet.setActorAddressBookManager(actorAddressBookManager);
        walletModuleCryptoWallet.setErrorManager(errorManager);
        walletModuleCryptoWallet.setCryptoAddressBookManager(cryptoAddressBookManager);
        walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
        walletModuleCryptoWallet.initialize();
    }

    @Test
    public void testGetWalletContactByNameContainsAndWalletId_NotNull() throws Exception {
        List<CryptoWalletWalletContact> walletContactRecordList = walletModuleCryptoWallet.getWalletContactByNameContainsAndWalletPublicKey(actorName, walletPublicKey);

        assertNotNull(walletContactRecordList);
    }

    @Test(expected=CantGetWalletContactException.class)
    public void testGetWalletContactByNameContainsAndWalletId_CantGetWalletContactException() throws Exception {
        doThrow(new com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactException())
        .when(walletContactsRegistry).getWalletContactByNameContainsAndWalletPublicKey(anyString(), anyString());

        walletModuleCryptoWallet.getWalletContactByNameContainsAndWalletPublicKey(actorName, walletPublicKey);
    }
}
