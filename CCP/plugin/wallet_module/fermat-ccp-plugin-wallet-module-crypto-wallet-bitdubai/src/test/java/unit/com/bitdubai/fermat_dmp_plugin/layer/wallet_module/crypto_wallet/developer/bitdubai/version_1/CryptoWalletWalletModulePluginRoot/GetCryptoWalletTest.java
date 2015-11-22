//package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.CryptoWalletWalletModulePluginRoot;
//
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
//<<<<<<< HEAD:CCP/plugin/wallet_module/fermat-ccp-plugin-wallet-module-crypto-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/wallet_module/crypto_wallet/developer/bitdubai/version_1/CryptoWalletWalletModulePluginRoot/GetCryptoWalletTest.java
//import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
//import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
//import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_extra_user.OutgoingExtraUserManager;
//=======
//import com.bitdubai.fermat_ccp_api.layer.crypto_wallet.exceptions.CantGetCryptoWalletException;
//import com.bitdubai.fermat_ccp_api.layer.crypto_wallet.interfaces.CryptoWallet;
//import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
//>>>>>>> 193a4ce563d3916b505332563ad81fe262f074f2:CCP/plugin/wallet_module/fermat-ccp-plugin-wallet-module-crypto-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/wallet_module/crypto_wallet/developer/bitdubai/version_1/CryptoWalletWalletModulePluginRoot/GetCryptoWalletTest.java
//import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
//import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException;
//import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsManager;
//import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.CryptoWalletCryptoModulePluginRoot;
//import ErrorManager;
//import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
//import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
//import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
//
//import junit.framework.TestCase;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class GetCryptoWalletTest extends TestCase {
//
//    /**
//     * DealsWithBitcoinWallet interface Mocked
//     */
//    @Mock
//    BitcoinWalletManager bitcoinWalletManager;
//
//    /**
//     * DealsWithCryptoVault interface Mocked
//     */
//    @Mock
//    CryptoVaultManager cryptoVaultManager;
//
//    /**
//     * DealsWithErrors interface Mocked
//     */
//    @Mock
//    ErrorManager errorManager;
//
//    /**
//     * DealsWithExtraUsers interface Mocked
//     */
//    @Mock
//    ExtraUserManager extraUserManager;
//
//    /**
//     * LogManagerForDevelopers interface Mocked
//     */
//    @Mock
//    LogManager logManager;
//
//    /**
//     * DealsWithOutgoingExtraUser interface Mocked
//     */
//    @Mock
//    OutgoingExtraUserManager outgoingExtraUserManager;
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
//    CryptoWalletCryptoModulePluginRoot cryptoWalletWalletModulePluginRoot;
//
//    @Before
//    public void setUp() throws Exception {
//        cryptoWalletWalletModulePluginRoot = new CryptoWalletCryptoModulePluginRoot();
//        cryptoWalletWalletModulePluginRoot.setBitcoinWalletManager(bitcoinWalletManager);
//        cryptoWalletWalletModulePluginRoot.setCryptoVaultManager(cryptoVaultManager);
//        cryptoWalletWalletModulePluginRoot.setErrorManager(errorManager);
//        cryptoWalletWalletModulePluginRoot.setExtraUserManager(extraUserManager);
//        cryptoWalletWalletModulePluginRoot.setLogManager(logManager);
//        cryptoWalletWalletModulePluginRoot.setOutgoingExtraUserManager(outgoingExtraUserManager);
//        cryptoWalletWalletModulePluginRoot.setCryptoAddressBookManager(cryptoAddressBookManager);
//        cryptoWalletWalletModulePluginRoot.setWalletContactsManager(walletContactsManager);
//    }
//
//    @Test
//    public void testGetCryptoWallet_NotNull() throws Exception {
//        CryptoWallet cryptoWallet = cryptoWalletWalletModulePluginRoot.getCryptoWallet();
//        assertNotNull(cryptoWallet);
//    }
//
//    @Test(expected=CantGetCryptoWalletException.class)
//    public void testGetCryptoWallet_CantGetWalletContactRegistryException() throws Exception {
//        doThrow(new CantGetWalletContactRegistryException()).when(walletContactsManager).getWalletContactsRegistry();
//
//        cryptoWalletWalletModulePluginRoot.getCryptoWallet();
//    }
//}
