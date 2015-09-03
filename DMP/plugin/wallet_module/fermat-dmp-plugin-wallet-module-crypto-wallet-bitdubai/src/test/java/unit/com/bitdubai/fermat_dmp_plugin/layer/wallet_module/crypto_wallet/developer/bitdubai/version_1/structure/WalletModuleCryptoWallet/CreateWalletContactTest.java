package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_actor.Actor;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRegistry;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.WalletAddressBookRegistry;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static com.googlecode.catchexception.CatchException.*;
import static org.fest.assertions.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateWalletContactTest extends TestCase {

    /**
     * DealsWithActorAddressBook interface Mocked
     */
    @Mock
    ActorAddressBookManager actorAddressBookManager;

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
    ActorAddressBookRegistry actorAddressBookRegistry;

    @Mock
    WalletAddressBookRegistry walletAddressBookRegistry;

    @Mock
    WalletContactsRegistry walletContactsRegistry;

    @Mock
    Actor user;

    @Mock
    CryptoAddress cryptoAddress;

    @Mock
    WalletContactRecord walletContactRecord;

    String actressName;
    Actors actorType;
    CryptoAddress deliveredCryptoAddress;
    ReferenceWallet referenceWallet;
    String walletPublicKey;

    CryptoWalletWalletModuleManager walletModuleCryptoWallet;

    @Before
    public void setUp() throws Exception {
        actressName = "Penelope Cruz";
        actorType = Actors.EXTRA_USER;
        deliveredCryptoAddress = new CryptoAddress("asdasd ", CryptoCurrency.BITCOIN);
        referenceWallet = ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET;
        walletPublicKey = AsymmectricCryptography.derivePublicKey(AsymmectricCryptography.createPrivateKey());
        walletModuleCryptoWallet = new CryptoWalletWalletModuleManager();
        walletModuleCryptoWallet.setActorAddressBookManager(actorAddressBookManager);
        walletModuleCryptoWallet.setErrorManager(errorManager);
        walletModuleCryptoWallet.setExtraUserManager(extraUserManager);
        walletModuleCryptoWallet.setCryptoAddressBookManager(cryptoAddressBookManager);
        walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
        walletModuleCryptoWallet.setCryptoVaultManager(cryptoVaultManager);
        doReturn(actorAddressBookRegistry).when(actorAddressBookManager).getActorAddressBookRegistry();
        doReturn(walletAddressBookRegistry).when(cryptoAddressBookManager).getWalletAddressBookRegistry();
        doReturn(walletContactsRegistry).when(walletContactsManager).getWalletContactsRegistry();
        doReturn(user).when(extraUserManager).createActor(anyString());
        doReturn(cryptoAddress).when(cryptoVaultManager).getAddress();
        doReturn(walletContactRecord).when(walletContactsRegistry).createWalletContact(any(UUID.class), anyString(), any(Actors.class), any(CryptoAddress.class), anyString());
        walletModuleCryptoWallet.initialize();
    }

    @Test
    public void testCreateWalletContact_NotNull() throws Exception {
        CryptoWalletWalletContact walletContactRecord = walletModuleCryptoWallet.createWalletContact(deliveredCryptoAddress, actressName, actorType, referenceWallet, walletPublicKey);
        assertThat(walletContactRecord).isNotNull();
    }

    // CONTACTS ALREADY EXISTS TEST
    @Ignore
    @Test
    public void testCreateWalletContact_ContactAlreadyExists() throws Exception {
        doReturn(walletContactRecord).when(walletContactsRegistry).getWalletContactByNameAndWalletPublicKey(anyString(), anyString());

        walletModuleCryptoWallet.createWalletContact(deliveredCryptoAddress, actressName, actorType, referenceWallet, walletPublicKey);
    }

    // TYPE OF ACTOR NOT RECOGNIZED BY THE PLUGIN
    @Test(expected=CantCreateWalletContactException.class)
    public void testCreateWalletContact_ActorTypeNotRecognized() throws Exception {
        actorType = Actors.INTRA_USER;

        walletModuleCryptoWallet.createWalletContact(deliveredCryptoAddress, actressName, actorType, referenceWallet, walletPublicKey);
    }

    /**
     * TODO: CANT CREATE USER TEST / EXTRA USER EXCEPTION DOESN'T EXIST
     */


    // CANT REGISTER ACTOR ADDRESS BOOK TEST
    @Test(expected=CantCreateWalletContactException.class)
    public void testCreateWalletContact_CantRegisterActorAddressBookException() throws Exception {
        doThrow(new com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantCreateWalletContactException("MOCK", null, null, null))
                .when(walletContactsRegistry).createWalletContact(any(UUID.class), anyString(), any(Actors.class), any(CryptoAddress.class), anyString());

        walletModuleCryptoWallet.createWalletContact(deliveredCryptoAddress, actressName, actorType, referenceWallet, walletPublicKey);
    }

    // CANT GET REQUESTED CONTACT TO KNOW IF ALREADY EXISTS TEST
    @Test(expected=CantCreateWalletContactException.class)
    public void testCreateWalletContact_CantGetWalletContactException() throws Exception {
        doThrow(new com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactException("MOCK", null, null, null))
            .when(walletContactsRegistry).getWalletContactByNameAndWalletPublicKey(anyString(), anyString());

        walletModuleCryptoWallet.createWalletContact(deliveredCryptoAddress, actressName, actorType, referenceWallet, walletPublicKey);
    }

    @Test
    public void testCreateWalletContact_RegistryIsNotInitialized_CantGetWalletContactException() throws Exception {
        walletModuleCryptoWallet = new CryptoWalletWalletModuleManager();
        catchException(walletModuleCryptoWallet).createWalletContact(deliveredCryptoAddress, actressName, actorType, referenceWallet, walletPublicKey);
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateWalletContactException.class);
    }
}
