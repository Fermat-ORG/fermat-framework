package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_actor.Actor;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;

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
public class RequestAddressToKnownUserTest extends TestCase {

    /**
     * TODO: CANT GET ADDRESS TEST / CRYPTO VAULT EXCEPTION DOESN'T EXIST
     */

    /**
     * DealsWithExtraUserManager interface Mocked
     */
    @Mock
    CryptoVaultManager cryptoVaultManager;

    /**
     * DealsWithErrors interface Mocked
     */
    @Mock
    ErrorManager errorManager;

    /**
     * DealsWithExtraUserManager interface Mocked
     */
    @Mock
    ExtraUserManager extraUserManager;

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
    Actor user;


    @Mock
    CryptoAddress cryptoAddress;

    String actorPublicKey;
    Actors actorType;
    ReferenceWallet referenceWallet;
    String walletPublicKey;
    String vaultIdentifier;

    CryptoWalletWalletModuleManager walletModuleCryptoWallet;

    @Before
    public void setUp() throws Exception {

        actorPublicKey = new ECCKeyPair().getPublicKey();
        walletPublicKey = AsymmetricCryptography.derivePublicKey(AsymmetricCryptography.createPrivateKey());
        actorType = Actors.EXTRA_USER;
        referenceWallet = ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET;
        vaultIdentifier = "";

        walletModuleCryptoWallet = new CryptoWalletWalletModuleManager();
        walletModuleCryptoWallet.setErrorManager(errorManager);
        walletModuleCryptoWallet.setExtraUserManager(extraUserManager);
        walletModuleCryptoWallet.setCryptoAddressBookManager(cryptoAddressBookManager);
        walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
        walletModuleCryptoWallet.setCryptoVaultManager(cryptoVaultManager);
        doReturn(user).when(extraUserManager).createActor(anyString());
        doReturn(cryptoAddress).when(cryptoVaultManager).getAddress();
        walletModuleCryptoWallet.initialize();
    }

    @Test
    public void testRequestAddress_NotNull() throws Exception {
        CryptoAddress cryptoAddress = walletModuleCryptoWallet.requestAddressToKnownUser(actorPublicKey, actorType, actorPublicKey, actorType, Platforms.CRYPTO_CURRENCY_PLATFORM, CryptoCurrencyVault.BITCOIN_VAULT, walletPublicKey, referenceWallet);
        assertNotNull(cryptoAddress);
    }

    // TYPE OF REQUESTED NOT RECOGNIZED BY THE PLUGIN
    @Test(expected=CantRequestCryptoAddressException.class)
    public void testRequestAddress_PlatformWalletTypeNotRecognized() throws Exception {
        referenceWallet = ReferenceWallet.BASIC_WALLET_DISCOUNT_WALLET;

        walletModuleCryptoWallet.requestAddressToKnownUser(actorPublicKey, actorType, actorPublicKey, actorType, Platforms.CRYPTO_CURRENCY_PLATFORM, CryptoCurrencyVault.BITCOIN_VAULT, walletPublicKey, referenceWallet);
    }

    // TYPE OF ACTOR NOT RECOGNIZED BY THE PLUGIN
    @Ignore
    @Test(expected=CantRequestCryptoAddressException.class)
    public void testRequestAddress_ActorTypeNotRecognized() throws Exception {
        actorType = Actors.INTRA_USER;

        walletModuleCryptoWallet.requestAddressToKnownUser(actorPublicKey, actorType, actorPublicKey, actorType, Platforms.CRYPTO_CURRENCY_PLATFORM, CryptoCurrencyVault.BITCOIN_VAULT, walletPublicKey, referenceWallet);
    }

    // CANT REGISTER REQUESTED CRYPTO ADDRESS BOOK TEST
    @Test(expected=CantRequestCryptoAddressException.class)
    public void testRequestAddress_CantRequestOrRegisterCryptoAddressException() throws Exception {
        doThrow(new CantRegisterCryptoAddressBookRecordException("gasdil", null, null, null))
                .when(cryptoAddressBookManager).registerCryptoAddress(
                any(CryptoAddress.class),
                anyString(),
                any(Actors.class),
                anyString(),
                any(Actors.class),
                any(Platforms.class),
                any(VaultType.class),
                anyString(),
                anyString(),
                any(ReferenceWallet.class)
        );

        walletModuleCryptoWallet.requestAddressToKnownUser(
                actorPublicKey,
                actorType,
                actorPublicKey,
                actorType,
                Platforms.CRYPTO_CURRENCY_PLATFORM,
                VaultType.CRYPTO_CURRENCY_VAULT,
                vaultIdentifier,
                walletPublicKey,
                referenceWallet
        );
    }
}
