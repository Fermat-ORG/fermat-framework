package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.SepecialistSelector;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.ActorAddressBookNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRecord;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRegistry;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantSelectSpecialistException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.SpecialistSelector;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;


@RunWith(MockitoJUnitRunner.class)
public class GetSpecialistTest extends TestCase {

    @Mock
    ActorAddressBookManager actorAddressBookManager;

    @Mock
    ActorAddressBookRegistry actorAddressBookRegistry;

    @Mock
    ActorAddressBookRecord actorAddressBookRecord;

    @Mock
    CryptoAddress cryptoAddress;

    SpecialistSelector specialistSelector;

    CryptoTransaction cryptoTransaction;


    @Before
    public void setUp() throws Exception {
        specialistSelector = new SpecialistSelector();
        specialistSelector.setActorAddressBookManager(actorAddressBookManager);

        cryptoTransaction = new CryptoTransaction("ernseto", cryptoAddress, cryptoAddress, CryptoCurrency.BITCOIN, 10, CryptoStatus.ON_BLOCKCHAIN);
        doReturn(actorAddressBookRegistry).when(actorAddressBookManager).getActorAddressBookRegistry();
        doReturn(actorAddressBookRecord).when(actorAddressBookRegistry).getActorAddressBookByCryptoAddress(any(CryptoAddress.class));
        doReturn(Actors.DEVICE_USER).when(actorAddressBookRecord).getDeliveredToActorType();
    }


    // test if you get a specialist
    @Test
    public void testGetSpecialist_NotNull() throws Exception {
        Specialist specialist = specialistSelector.getSpecialist(cryptoTransaction);
        assertNotNull(specialist);
    }

    // test if i cannot get an actor address book registry
    @Test(expected = CantSelectSpecialistException.class)
    public void testGetSpecialist_CantSelectSpecialistException() throws Exception {
        doThrow(new CantGetActorAddressBookRegistryException()).when(actorAddressBookManager).getActorAddressBookRegistry();

        specialistSelector.getSpecialist(cryptoTransaction);
    }

    // test if i cannot get an actor address book
    @Test(expected = CantSelectSpecialistException.class)
    public void testGetSpecialist_CantGetActorAddressBookException() throws Exception {
        doThrow(new CantGetActorAddressBookException()).when(actorAddressBookRegistry).getActorAddressBookByCryptoAddress(any(CryptoAddress.class));

        specialistSelector.getSpecialist(cryptoTransaction);
    }

    // test if i cannot found an actor address book
    @Test(expected = CantSelectSpecialistException.class)
    public void testGetSpecialist_ActorAddressBookNotFoundException() throws Exception {
        doThrow(new ActorAddressBookNotFoundException()).when(actorAddressBookRegistry).getActorAddressBookByCryptoAddress(any(CryptoAddress.class));

        specialistSelector.getSpecialist(cryptoTransaction);
    }

    // test if i cannot found a specialist
    @Test(expected = CantSelectSpecialistException.class)
    public void testGetSpecialist_CantFoundSpecialist() throws Exception {
        doReturn(Actors.SHOP).when(actorAddressBookRecord).getDeliveredToActorType();

        specialistSelector.getSpecialist(cryptoTransaction);
    }
}
