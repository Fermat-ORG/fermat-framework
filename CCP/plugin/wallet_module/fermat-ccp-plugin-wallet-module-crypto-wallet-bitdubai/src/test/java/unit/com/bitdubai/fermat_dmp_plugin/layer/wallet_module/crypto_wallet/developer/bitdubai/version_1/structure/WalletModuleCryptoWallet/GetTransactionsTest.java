//package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;
//
//import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
//<<<<<<< HEAD:CCP/plugin/wallet_module/fermat-ccp-plugin-wallet-module-crypto-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/wallet_module/crypto_wallet/developer/bitdubai/version_1/structure/WalletModuleCryptoWallet/GetTransactionsTest.java
//import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantLoadWalletException;
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
//import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
//import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListTransactionsException;
//=======
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
//import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
//import com.bitdubai.fermat_ccp_api.layer.crypto_wallet.exceptions.CantListTransactionsException;
//>>>>>>> 193a4ce563d3916b505332563ad81fe262f074f2:CCP/plugin/wallet_module/fermat-ccp-plugin-wallet-module-crypto-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/wallet_module/crypto_wallet/developer/bitdubai/version_1/structure/WalletModuleCryptoWallet/GetTransactionsTest.java
//import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
//import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager;
//import ErrorManager;
//
//import junit.framework.TestCase;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import static org.mockito.Matchers.anyInt;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.doThrow;
//
//@RunWith(MockitoJUnitRunner.class)
//public class GetTransactionsTest extends TestCase {
//
//    /**
//     * DealsWithErrors interface Mocked
//     */
//    @Mock
//    ErrorManager errorManager;
//
//    /**
//     * DealsWithCryptoAddressBook interface Mocked
//     */
//    @Mock
//    CryptoAddressBookManager cryptoAddressBookManager;
//
//    /**
//     * DealsWithWalletContacts interface Mocked
//     */
//    @Mock
//    WalletContactsManager walletContactsManager;
//
//    /**
//     * DealsWithBitcoinWallet interface Mocked
//     */
//    @Mock
//    BitcoinWalletManager bitcoinWalletManager;
//
//
//    @Mock
//    BitcoinWalletWallet bitcoinWalletWallet;
//
//    String walletPublicKey;
//
//    CryptoWalletWalletModuleManager walletModuleCryptoWallet;
//
//    @Before
//    public void setUp() throws Exception {
//        walletPublicKey = AsymmetricCryptography.derivePublicKey(AsymmetricCryptography.createPrivateKey());
//        walletModuleCryptoWallet = new CryptoWalletWalletModuleManager();
//        walletModuleCryptoWallet.setErrorManager(errorManager);
//        walletModuleCryptoWallet.setCryptoAddressBookManager(cryptoAddressBookManager);
//        walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
//        walletModuleCryptoWallet.setBitcoinWalletManager(bitcoinWalletManager);
//        walletModuleCryptoWallet.initialize();
//    }
//
//    @Test
//    public void testGetTransactions_Success() throws Exception {
//        doReturn(bitcoinWalletWallet).when(bitcoinWalletManager).loadWallet(anyString());
//        walletModuleCryptoWallet.getTransactions(1, 10, walletPublicKey);
//    }
//
//    @Test(expected=CantListTransactionsException.class)
//    public void ttestGetTransactions_CantLoadWalletException() throws Exception {
//        doThrow(new CantLoadWalletException("MOCK", null, null, null))
//                .when(bitcoinWalletManager).loadWallet(anyString());
//
//        walletModuleCryptoWallet.getTransactions(1, 10, walletPublicKey);
//    }
//
//    @Test(expected=CantListTransactionsException.class)
//    public void testGetTransactions_CantGetTransactionsException() throws Exception {
//        doReturn(bitcoinWalletWallet).when(bitcoinWalletManager).loadWallet(anyString());
//        doThrow(new com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantListTransactionsException("MOCK", null, null, null))
//        .when(bitcoinWalletWallet).getTransactions(anyInt(), anyInt());
//
//        walletModuleCryptoWallet.getTransactions(1, 10, walletPublicKey);
//    }
//}
