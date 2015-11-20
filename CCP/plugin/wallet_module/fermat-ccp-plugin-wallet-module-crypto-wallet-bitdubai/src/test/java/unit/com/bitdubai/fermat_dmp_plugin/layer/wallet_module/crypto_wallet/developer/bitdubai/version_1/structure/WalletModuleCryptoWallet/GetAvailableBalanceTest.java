//package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;
//
//import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
//<<<<<<< HEAD:CCP/plugin/wallet_module/fermat-ccp-plugin-wallet-module-crypto-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/wallet_module/crypto_wallet/developer/bitdubai/version_1/structure/WalletModuleCryptoWallet/GetAvailableBalanceTest.java
//import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantCalculateBalanceException;
//import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantLoadWalletException;
//=======
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
//>>>>>>> 193a4ce563d3916b505332563ad81fe262f074f2:CCP/plugin/wallet_module/fermat-ccp-plugin-wallet-module-crypto-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/wallet_module/crypto_wallet/developer/bitdubai/version_1/structure/WalletModuleCryptoWallet/GetAvailableBalanceTest.java
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBalance;
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
//import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
//<<<<<<< HEAD:CCP/plugin/wallet_module/fermat-ccp-plugin-wallet-module-crypto-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/wallet_module/crypto_wallet/developer/bitdubai/version_1/structure/WalletModuleCryptoWallet/GetAvailableBalanceTest.java
//import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
//=======
//import com.bitdubai.fermat_ccp_api.layer.crypto_wallet.exceptions.CantGetBalanceException;
//>>>>>>> 193a4ce563d3916b505332563ad81fe262f074f2:CCP/plugin/wallet_module/fermat-ccp-plugin-wallet-module-crypto-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/wallet_module/crypto_wallet/developer/bitdubai/version_1/structure/WalletModuleCryptoWallet/GetAvailableBalanceTest.java
//import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
//import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager;
//import ErrorManager;
//import junit.framework.TestCase;
//
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import static org.hamcrest.CoreMatchers.instanceOf;
//import static org.junit.Assert.assertThat;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.doThrow;
//
//@RunWith(MockitoJUnitRunner.class)
//public class GetAvailableBalanceTest extends TestCase {
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
//    @Mock
//    BitcoinWalletBalance mockBitcoinWalletBalance;
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
//    public void testGetBalance_Success() throws Exception {
//        doReturn(bitcoinWalletWallet).when(bitcoinWalletManager).loadWallet(anyString());
//        doReturn(mockBitcoinWalletBalance).when(bitcoinWalletWallet).getAvailableBalance();
//        assertThat(walletModuleCryptoWallet.getAvailableBalance(walletPublicKey), instanceOf(long.class));
//    }
//
//    @Test(expected=CantGetBalanceException.class)
//    public void testGetBalance_CantLoadWalletException() throws Exception {
//        doThrow(new CantLoadWalletException(CantLoadWalletException.DEFAULT_MESSAGE, null, null, null))
//                .when(bitcoinWalletManager).loadWallet(anyString());
//
//        walletModuleCryptoWallet.getAvailableBalance(walletPublicKey);
//    }
//
//    @Ignore
//    @Test(expected=CantGetBalanceException.class)
//    public void testGetBalance_CantCalculateBalanceException() throws Exception {
//        doReturn(bitcoinWalletWallet).when(bitcoinWalletManager).loadWallet(anyString());
//        doThrow(new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, null, null, null))
//        .when(bitcoinWalletWallet).getAvailableBalance();
//
//        walletModuleCryptoWallet.getAvailableBalance(walletPublicKey);
//    }
//}
