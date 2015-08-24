package unit.com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.structure.NicheWalletTypeCryptoWallet;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.TransactionManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.CantGetTransactionManagerException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.CantSendFundsException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.structure.NicheWalletTypeCryptoWallet;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class SendTest extends TestCase {

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
     * DealsWithWalletAddressBook interface Mocked
     */
    @Mock
    WalletAddressBookManager walletAddressBookManager;

    /**
     * DealsWithWalletContacts interface Mocked
     */
    @Mock
    WalletContactsManager walletContactsManager;

    /**
     * DealsWithOutgoingExtraUser interface Mocked
     */
    @Mock
    OutgoingExtraUserManager outgoingExtraUserManager;


    @Mock
    TransactionManager transactionManager;

    long cryptoAmount;
    CryptoAddress destinationAddress;
    String walletPublicKey;
    String notes;
    UUID deliveredByActorId;
    Actors deliveredByActorType;
    UUID deliveredToActorId;
    Actors deliveredToActorType;

    NicheWalletTypeCryptoWallet nicheWalletTypeCryptoWallet;

    @Before
    public void setUp() throws Exception {
        cryptoAmount = 1;
        destinationAddress = new CryptoAddress("asdasd", CryptoCurrency.BITCOIN);
        walletPublicKey = AsymmectricCryptography.derivePublicKey(AsymmectricCryptography.createPrivateKey());
        notes = "NOTE";
        deliveredByActorId = UUID.randomUUID();
        deliveredByActorType = Actors.EXTRA_USER;
        deliveredToActorId = UUID.randomUUID();
        deliveredToActorType = Actors.INTRA_USER;

        nicheWalletTypeCryptoWallet = new NicheWalletTypeCryptoWallet();
        nicheWalletTypeCryptoWallet.setActorAddressBookManager(actorAddressBookManager);
        nicheWalletTypeCryptoWallet.setErrorManager(errorManager);
        nicheWalletTypeCryptoWallet.setWalletAddressBookManager(walletAddressBookManager);
        nicheWalletTypeCryptoWallet.setWalletContactsManager(walletContactsManager);
        nicheWalletTypeCryptoWallet.setOutgoingExtraUserManager(outgoingExtraUserManager);
        nicheWalletTypeCryptoWallet.initialize();
    }


    @Test
    public void testSend_Success() throws Exception {
        doReturn(transactionManager).when(outgoingExtraUserManager).getTransactionManager();
        nicheWalletTypeCryptoWallet.send(cryptoAmount, destinationAddress, notes, walletPublicKey, deliveredByActorId, deliveredByActorType, deliveredToActorId, deliveredToActorType);
    }

    @Ignore
    @Test(expected=CantSendCryptoException.class)
    public void testSend_InsufficientFundsException() throws Exception {
        doReturn(transactionManager).when(outgoingExtraUserManager).getTransactionManager();
        doThrow(new InsufficientFundsException("gasdil", null, null, null))
        .when(transactionManager).send(anyString(), any(CryptoAddress.class), anyLong(), anyString(), any(UUID.class), any(Actors.class), any(UUID.class), any(Actors.class));

        nicheWalletTypeCryptoWallet.send(cryptoAmount, destinationAddress, notes, walletPublicKey, deliveredByActorId, deliveredByActorType, deliveredToActorId, deliveredToActorType);
    }

    @Test(expected=CantSendCryptoException.class)
    public void testSend_CantGetTransactionManagerException() throws Exception {
        doThrow(new CantGetTransactionManagerException("MOCK", null, null, null))
            .when(outgoingExtraUserManager).getTransactionManager();

        nicheWalletTypeCryptoWallet.send(cryptoAmount, destinationAddress, notes, walletPublicKey, deliveredByActorId, deliveredByActorType, deliveredToActorId, deliveredToActorType);
    }

    @Test(expected=CantSendCryptoException.class)
    public void testSend_CantSendFundsException() throws Exception {
        doReturn(transactionManager).when(outgoingExtraUserManager).getTransactionManager();
        doThrow(new CantSendFundsException("MOCK", null, null, null))
                .when(transactionManager).send(anyString(), any(CryptoAddress.class), anyLong(), anyString(), any(UUID.class), any(Actors.class), any(UUID.class), any(Actors.class));

        nicheWalletTypeCryptoWallet.send(cryptoAmount, destinationAddress, notes, walletPublicKey, deliveredByActorId, deliveredByActorType, deliveredToActorId, deliveredToActorType);
    }
}
