//package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;
//
//import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
//import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
//import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsRegistry;
//<<<<<<< HEAD:CCP/plugin/wallet_module/fermat-ccp-plugin-wallet-module-crypto-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/wallet_module/crypto_wallet/developer/bitdubai/version_1/structure/WalletModuleCryptoWallet/GetWalletContactByContainsLikeAndWalletIdTest.java
//import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetWalletContactException;
//import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
//import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager;
//=======
//import com.bitdubai.fermat_ccp_api.layer.crypto_wallet.exceptions.CantGetWalletContactException;
//import com.bitdubai.fermat_ccp_api.layer.crypto_wallet.interfaces.CryptoWalletWalletContact;
//import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager;
//>>>>>>> 193a4ce563d3916b505332563ad81fe262f074f2:CCP/plugin/wallet_module/fermat-ccp-plugin-wallet-module-crypto-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/wallet_module/crypto_wallet/developer/bitdubai/version_1/structure/WalletModuleCryptoWallet/GetWalletContactByContainsLikeAndWalletIdTest.java
//import ErrorManager;
//import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
//
//import junit.framework.TestCase;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.List;
//
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.doThrow;
//
//@RunWith(MockitoJUnitRunner.class)
//public class GetWalletContactByContainsLikeAndWalletIdTest extends TestCase {
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
//
//    @Mock
//    WalletContactsRegistry walletContactsRegistry;
//
//    String walletPublicKey;
//    String actorName;
//
//    CryptoWalletWalletModuleManager walletModuleCryptoWallet;
//
//    @Before
//    public void setUp() throws Exception {
//        doReturn(walletContactsRegistry).when(walletContactsManager).getWalletContactsRegistry();
//        walletPublicKey = AsymmetricCryptography.derivePublicKey(AsymmetricCryptography.createPrivateKey());
//        actorName = "Leonardo Dicaprio";
//        walletModuleCryptoWallet = new CryptoWalletWalletModuleManager();
//        walletModuleCryptoWallet.setErrorManager(errorManager);
//        walletModuleCryptoWallet.setCryptoAddressBookManager(cryptoAddressBookManager);
//        walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
//        walletModuleCryptoWallet.initialize();
//    }
//
//    @Test
//    public void testGetWalletContactByNameContainsAndWalletId_NotNull() throws Exception {
//        List<CryptoWalletWalletContact> walletContactRecordList = walletModuleCryptoWallet.getWalletContactByNameContainsAndWalletPublicKey(actorName, walletPublicKey);
//
//        assertNotNull(walletContactRecordList);
//    }
//
//    @Test(expected=CantGetWalletContactException.class)
//    public void testGetWalletContactByNameContainsAndWalletId_CantGetWalletContactException() throws Exception {
//        doThrow(new com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactException())
//        .when(walletContactsRegistry).getWalletContactByNameContainsAndWalletPublicKey(anyString(), anyString());
//
//        walletModuleCryptoWallet.getWalletContactByNameContainsAndWalletPublicKey(actorName, walletPublicKey);
//    }
//}
