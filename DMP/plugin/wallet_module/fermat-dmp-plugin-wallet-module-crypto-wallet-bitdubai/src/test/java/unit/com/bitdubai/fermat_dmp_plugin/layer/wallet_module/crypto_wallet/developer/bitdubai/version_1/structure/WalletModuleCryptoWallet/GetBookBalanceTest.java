package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class GetBookBalanceTest extends TestCase {

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

    CryptoWalletWalletModuleManager walletModuleCryptoWallet;

    @Before
    public void setUp() throws Exception {
        walletPublicKey = AsymmectricCryptography.derivePublicKey(AsymmectricCryptography.createPrivateKey());
        walletModuleCryptoWallet = new CryptoWalletWalletModuleManager();
        walletModuleCryptoWallet.setActorAddressBookManager(actorAddressBookManager);
        walletModuleCryptoWallet.setErrorManager(errorManager);
        walletModuleCryptoWallet.setCryptoAddressBookManager(cryptoAddressBookManager);
        walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
        walletModuleCryptoWallet.setBitcoinWalletManager(bitcoinWalletManager);
        walletModuleCryptoWallet.initialize();
    }

    //TODO: What does this test actually assert?
    @Ignore
    @Test
    public void testGetBalance_Success() throws Exception {
        doReturn(bitcoinWalletWallet).when(bitcoinWalletManager).loadWallet(anyString());
        walletModuleCryptoWallet.getBookBalance(walletPublicKey);
    }

    @Test(expected=CantGetBalanceException.class)
    public void testGetBalance_CantLoadWalletException() throws Exception {
        doThrow(new CantLoadWalletException("MOCK", null, null, null))
                .when(bitcoinWalletManager).loadWallet(anyString());

        walletModuleCryptoWallet.getBookBalance(walletPublicKey);
    }

    @Ignore
    @Test(expected=CantGetBalanceException.class)
    public void testGetBalance_CantCalculateBalanceException() throws Exception {
        doReturn(bitcoinWalletWallet).when(bitcoinWalletManager).loadWallet(anyString());
        doThrow(new CantCalculateBalanceException("MOCK", null, null, null))
        .when(bitcoinWalletWallet).getAvailableBalance();

        walletModuleCryptoWallet.getBookBalance(walletPublicKey);
    }
}
