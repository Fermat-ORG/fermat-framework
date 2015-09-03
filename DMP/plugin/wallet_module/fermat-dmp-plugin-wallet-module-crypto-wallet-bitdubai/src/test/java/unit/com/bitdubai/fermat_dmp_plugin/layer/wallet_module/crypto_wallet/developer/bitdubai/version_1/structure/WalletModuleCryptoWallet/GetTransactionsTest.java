package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoCrypto;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class GetTransactionsTest extends TestCase {

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

    /**
     * DealsWithBitcoinWallet interface Mocked
     */
    @Mock
    BitcoinWalletManager bitcoinWalletManager;


    @Mock
    BitcoinWalletWallet bitcoinWalletWallet;

    String walletPublicKey;

    WalletModuleCryptoCrypto walletModuleCryptoWallet;

    @Before
    public void setUp() throws Exception {
        walletPublicKey = AsymmectricCryptography.derivePublicKey(AsymmectricCryptography.createPrivateKey());
        walletModuleCryptoWallet = new WalletModuleCryptoCrypto();
        walletModuleCryptoWallet.setActorAddressBookManager(actorAddressBookManager);
        walletModuleCryptoWallet.setErrorManager(errorManager);
        walletModuleCryptoWallet.setCryptoAddressBookManager(cryptoAddressBookManager);
        walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
        walletModuleCryptoWallet.setBitcoinWalletManager(bitcoinWalletManager);
        walletModuleCryptoWallet.initialize();
    }

    @Test
    public void testGetTransactions_Success() throws Exception {
        doReturn(bitcoinWalletWallet).when(bitcoinWalletManager).loadWallet(anyString());
        walletModuleCryptoWallet.getTransactions(1, 10, walletPublicKey);
    }

    @Test(expected=CantGetTransactionsException.class)
    public void ttestGetTransactions_CantLoadWalletException() throws Exception {
        doThrow(new CantLoadWalletException("MOCK", null, null, null))
                .when(bitcoinWalletManager).loadWallet(anyString());

        walletModuleCryptoWallet.getTransactions(1, 10, walletPublicKey);
    }

    @Test(expected=CantGetTransactionsException.class)
    public void testGetTransactions_CantGetTransactionsException() throws Exception {
        doReturn(bitcoinWalletWallet).when(bitcoinWalletManager).loadWallet(anyString());
        doThrow(new com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantGetTransactionsException("MOCK", null, null, null))
        .when(bitcoinWalletWallet).getTransactions(anyInt(), anyInt());

        walletModuleCryptoWallet.getTransactions(1, 10, walletPublicKey);
    }
}
