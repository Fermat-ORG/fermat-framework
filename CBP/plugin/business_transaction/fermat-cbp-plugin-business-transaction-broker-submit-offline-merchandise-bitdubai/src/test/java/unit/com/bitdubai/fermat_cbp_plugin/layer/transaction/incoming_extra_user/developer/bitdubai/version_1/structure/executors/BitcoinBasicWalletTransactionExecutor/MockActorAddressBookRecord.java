package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.executors.BitcoinBasicWalletTransactionExecutor;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRecord;

import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.07.14..
 */
public class MockActorAddressBookRecord implements ActorAddressBookRecord {
    @Override
    public UUID getDeliveredByActorId() {
        return UUID.randomUUID();
    }

    @Override
    public Actors getDeliveredByActorType() {
        return Actors.EXTRA_USER;
    }

    @Override
    public UUID getDeliveredToActorId() {
        return UUID.randomUUID();
    }

    @Override
    public Actors getDeliveredToActorType() {
        return Actors.INTRA_USER;
    }

    @Override
    public CryptoAddress getCryptoAddress() {
        return new CryptoAddress("156jBLBvtaDccomhbiWDoySKuzx1AbWUG8", CryptoCurrency.BITCOIN);
    }
}
