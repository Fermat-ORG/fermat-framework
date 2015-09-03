package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_actor.Actor;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoCrypto;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantRegisterActorAddressBookException;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class RequestAddressTest extends TestCase {

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
    Actor user;


    @Mock
    CryptoAddress cryptoAddress;

    UUID actorId;
    Actors actorType;
    ReferenceWallet referenceWallet;
    String walletPublicKey;

    WalletModuleCryptoCrypto walletModuleCryptoWallet;

    @Before
    public void setUp() throws Exception {
        actorId = UUID.randomUUID();
        walletPublicKey = AsymmectricCryptography.derivePublicKey(AsymmectricCryptography.createPrivateKey());
        actorType = Actors.EXTRA_USER;
        referenceWallet = ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET;
        walletModuleCryptoWallet = new WalletModuleCryptoCrypto();
        walletModuleCryptoWallet.setActorAddressBookManager(actorAddressBookManager);
        walletModuleCryptoWallet.setErrorManager(errorManager);
        walletModuleCryptoWallet.setExtraUserManager(extraUserManager);
        walletModuleCryptoWallet.setCryptoAddressBookManager(cryptoAddressBookManager);
        walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
        walletModuleCryptoWallet.setCryptoVaultManager(cryptoVaultManager);
        doReturn(actorAddressBookRegistry).when(actorAddressBookManager).getActorAddressBookRegistry();
        doReturn(walletAddressBookRegistry).when(cryptoAddressBookManager).getWalletAddressBookRegistry();
        doReturn(user).when(extraUserManager).createActor(anyString());
        doReturn(cryptoAddress).when(cryptoVaultManager).getAddress();
        walletModuleCryptoWallet.initialize();
    }

    @Test
    public void testRequestAddress_NotNull() throws Exception {
        CryptoAddress cryptoAddress = walletModuleCryptoWallet.requestAddress(actorId, actorType, actorId, actorType, referenceWallet, walletPublicKey);
        assertNotNull(cryptoAddress);
    }

    // TYPE OF REQUESTED NOT RECOGNIZED BY THE PLUGIN
    @Test(expected=CantRequestCryptoAddressException.class)
    public void testRequestAddress_PlatformWalletTypeNotRecognized() throws Exception {
        referenceWallet = ReferenceWallet.BASIC_WALLET_DISCOUNT_WALLET;

        walletModuleCryptoWallet.requestAddress(actorId, actorType, actorId, actorType, referenceWallet, walletPublicKey);
    }

    /**
     * TODO: CANT GET ADDRESS TEST / CRYPTO VAULT EXCEPTION DOESN'T EXIST
     */

    // TYPE OF ACTOR NOT RECOGNIZED BY THE PLUGIN
    @Ignore
    @Test(expected=CantRequestCryptoAddressException.class)
    public void testRequestAddress_ActorTypeNotRecognized() throws Exception {
        actorType = Actors.INTRA_USER;

        walletModuleCryptoWallet.requestAddress(actorId, actorType, actorId, actorType, referenceWallet, walletPublicKey);
    }

    /**
     * TODO: CANT CREATE USER TEST / EXTRA USER EXCEPTION DOESN'T EXIST
     */


    // CANT REGISTER ACTOR ADDRESS BOOK TEST
    @Test(expected=CantRequestCryptoAddressException.class)
    public void testRequestAddress_CantRegisterActorAddressBookException() throws Exception {
        doThrow(new CantRegisterActorAddressBookException("gasdil", null, null, null))
                .when(actorAddressBookRegistry).registerActorAddressBook(any(UUID.class), any(Actors.class),any(UUID.class), any(Actors.class), any(CryptoAddress.class));

        walletModuleCryptoWallet.requestAddress(actorId, actorType, actorId, actorType, referenceWallet, walletPublicKey);
    }

    // CANT REGISTER REQUESTED ADDRESS BOOK TEST
    @Test(expected=CantRequestCryptoAddressException.class)
    public void testRequestAddress_CantRequestOrRegisterCryptoAddressException() throws Exception {
        doThrow(new CantRegisterCryptoAddressBookRecordException("gasdil", null, null, null))
                .when(walletAddressBookRegistry).registerWalletCryptoAddressBook(any(CryptoAddress.class), any(ReferenceWallet.class), anyString());

        walletModuleCryptoWallet.requestAddress(actorId, actorType, actorId, actorType, referenceWallet, walletPublicKey);
    }
}
