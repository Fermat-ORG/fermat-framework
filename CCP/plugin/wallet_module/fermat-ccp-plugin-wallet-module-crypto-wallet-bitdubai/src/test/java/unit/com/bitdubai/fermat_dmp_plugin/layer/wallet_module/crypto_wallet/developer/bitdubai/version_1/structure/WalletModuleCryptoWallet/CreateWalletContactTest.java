//package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;
//
//import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
//import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
//import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
//import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
//import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
//<<<<<<< HEAD:CCP/plugin/wallet_module/fermat-ccp-plugin-wallet-module-crypto-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/wallet_module/crypto_wallet/developer/bitdubai/version_1/structure/WalletModuleCryptoWallet/CreateWalletContactTest.java
//import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
//import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
//import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
//import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
//=======
//import com.bitdubai.fermat_api.layer.dmp_actor.Actor;
//import com.bitdubai.fermat_ccp_api.layer.crypto_wallet.exceptions.CantCreateWalletContactException;
//import com.bitdubai.fermat_ccp_api.layer.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
//import com.bitdubai.fermat_ccp_api.layer.crypto_wallet.interfaces.CryptoWalletWalletContact;
//>>>>>>> 193a4ce563d3916b505332563ad81fe262f074f2:CCP/plugin/wallet_module/fermat-ccp-plugin-wallet-module-crypto-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/wallet_module/crypto_wallet/developer/bitdubai/version_1/structure/WalletModuleCryptoWallet/CreateWalletContactTest.java
//import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException;
//import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactRecord;
//import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsManager;
//import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsRegistry;
//import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
//import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager;
//import ErrorManager;
//import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
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
//import java.util.UUID;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.*;
//import static com.googlecode.catchexception.CatchException.*;
//import static org.fest.assertions.api.Assertions.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class CreateWalletContactTest extends TestCase {
//
//    /**
//     * DealsWithExtraUserManager interface Mocked
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
//     * DealsWithExtraUserManager interface Mocked
//     */
//    @Mock
//    ExtraUserManager extraUserManager;
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
//    @Mock
//    WalletContactsRegistry walletContactsRegistry;
//
//    @Mock
//    Actor user;
//
//    @Mock
//    CryptoAddress cryptoAddress;
//
//    @Mock
//    WalletContactRecord walletContactRecord;
//
//    String actressName;
//    Actors actorType;
//    CryptoAddress deliveredCryptoAddress;
//    ReferenceWallet referenceWallet;
//    String walletPublicKey;
//
//    CryptoWalletWalletModuleManager walletModuleCryptoWallet;
//
//    @Before
//    public void setUp() throws Exception {
//        actressName = "Penelope Cruz";
//        actorType = Actors.EXTRA_USER;
//        deliveredCryptoAddress = new CryptoAddress("asdasd ", CryptoCurrency.BITCOIN);
//        referenceWallet = ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET;
//        walletPublicKey = AsymmetricCryptography.derivePublicKey(AsymmetricCryptography.createPrivateKey());
//        walletModuleCryptoWallet = new CryptoWalletWalletModuleManager();
//        walletModuleCryptoWallet.setErrorManager(errorManager);
//        walletModuleCryptoWallet.setExtraUserManager(extraUserManager);
//        walletModuleCryptoWallet.setCryptoAddressBookManager(cryptoAddressBookManager);
//        walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
//        walletModuleCryptoWallet.setCryptoVaultManager(cryptoVaultManager);
//        doReturn(walletContactsRegistry).when(walletContactsManager).getWalletContactsRegistry();
//        doReturn(user).when(extraUserManager).createActor(anyString());
//        doReturn(cryptoAddress).when(cryptoVaultManager).getAddress();
//        doReturn(walletContactRecord).when(walletContactsRegistry)
//                .createWalletContact(
//                        anyString(),
//                        anyString(),
//                        anyString(),
//                        anyString(),
//                        any(Actors.class),
//                        anyList(),
//                        anyString()
//                );
//        walletModuleCryptoWallet.initialize();
//    }
//
//    @Test
//    public void testCreateWalletContact_NotNull() throws Exception {
//        CryptoWalletWalletContact walletContactRecord = walletModuleCryptoWallet.createWalletContact(
//                deliveredCryptoAddress,
//                actressName,
//                actressName,
//                actressName,
//                actorType,
//                walletPublicKey);
//        assertThat(walletContactRecord).isNotNull();
//    }
//
//    // CONTACTS ALREADY EXISTS TEST
//    @Test(expected=ContactNameAlreadyExistsException.class)
//    public void testCreateWalletContact_ContactNameAlreadyExistsException() throws Exception {
//        doReturn(walletContactRecord).when(walletContactsRegistry).getWalletContactByAliasAndWalletPublicKey(anyString(), anyString());
//        doReturn(UUID.randomUUID()).when(walletContactRecord).getContactId();
//
//        walletModuleCryptoWallet.createWalletContact(
//                deliveredCryptoAddress,
//                actressName,
//                actressName,
//                actressName,
//                actorType,
//                walletPublicKey);
//    }
//
//    // TYPE OF ACTOR NOT RECOGNIZED BY THE PLUGIN
//    @Test(expected=CantCreateWalletContactException.class)
//    public void testCreateWalletContact_ActorTypeNotRecognized() throws Exception {
//        actorType = Actors.INTRA_USER;
//
//        walletModuleCryptoWallet.createWalletContact(
//                deliveredCryptoAddress,
//                actressName,
//                actressName,
//                actressName,
//                actorType,
//                walletPublicKey);
//    }
//
//    /**
//     * TODO: CANT CREATE USER TEST / EXTRA USER EXCEPTION DOESN'T EXIST
//     */
//
//
//    // CANT REGISTER ACTOR ADDRESS BOOK TEST
//    @Test(expected=CantCreateWalletContactException.class)
//    public void testCreateWalletContact_CantRegisterActorAddressBookException() throws Exception {
//        doThrow(new CantCreateWalletContactException("MOCK", null, null, null))
//                .when(walletContactsRegistry).createWalletContact(anyString(),anyString(),anyString(),anyString(), any(Actors.class),anyList(),anyString());
//
//        walletModuleCryptoWallet.createWalletContact(
//                deliveredCryptoAddress,
//                actressName,
//                actressName,
//                actressName,
//                actorType,
//                walletPublicKey);
//    }
//
//    // CANT GET REQUESTED CONTACT TO KNOW IF ALREADY EXISTS TEST
//    @Test(expected=CantCreateWalletContactException.class)
//    public void testCreateWalletContact_CantGetWalletContactException() throws Exception {
//        doThrow(new CantGetWalletContactException("MOCK", null, null, null))
//            .when(walletContactsRegistry).getWalletContactByAliasAndWalletPublicKey(anyString(), anyString());
//
//        walletModuleCryptoWallet.createWalletContact(
//                deliveredCryptoAddress,
//                actressName,
//                actressName,
//                actressName,
//                actorType,
//                walletPublicKey);
//    }
//
//    @Test
//    public void testCreateWalletContact_RegistryIsNotInitialized_CantGetWalletContactException() throws Exception {
//        walletModuleCryptoWallet = new CryptoWalletWalletModuleManager();
//        catchException(walletModuleCryptoWallet).createWalletContact(
//                deliveredCryptoAddress,
//                actressName,
//                actressName,
//                actressName,
//                actorType,
//                walletPublicKey);
//        assertThat(caughtException())
//                .isNotNull()
//                .isInstanceOf(CantCreateWalletContactException.class);
//    }
//}
