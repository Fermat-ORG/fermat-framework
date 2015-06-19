package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRecord;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRegistry;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.DealsWithActorAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBook;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantSelectSpecialistException;

/**
 * Created by eze on 12/06/15.
 */

/*
 * The purpose of this class is to indicate the correct
 * destination for a given transaction
 */
public class SpecialistSelector implements DealsWithActorAddressBook {

    /*
     * DealsWithActorAddressBook Interface member variables
     */
    private ActorAddressBookManager actorAddressBook;

    /*
     * DealsWithActorAddressBook Interface method implementation
     */
    @Override
    public void setActorAddressBookManager(ActorAddressBookManager actorAddressBook) {
        this.actorAddressBook = actorAddressBook;
    }

    public Specialist getSpecialist(CryptoTransaction cryptoTransaction) throws CantSelectSpecialistException {

        CryptoAddress cryptoAddress = new CryptoAddress();
        cryptoAddress.setAddress(cryptoTransaction.getAddressFrom().getAddress());
        cryptoAddress.setCryptoCurrency(cryptoTransaction.getCryptoCurrency());

        ActorAddressBookRegistry actorsRegistry = null;

        try {
            actorsRegistry = this.actorAddressBook.getActorAddressBookRegistry();
        } catch (CantGetActorAddressBookRegistryException e) {
            //TODO: Manage Exception
            e.printStackTrace();
        }

        if (actorsRegistry != null) {
            try {
                ActorAddressBookRecord actor = actorsRegistry.getActorAddressBookByCryptoAddress(cryptoAddress);
                switch (actor.getActorType()) {
                    case DEVICE_USER:
                        return Specialist.DEVICE_USER_SPECIALIST;
                    case INTRA_USER:
                        return Specialist.INTRA_USER_SPECIALIST;
                    case EXTRA_USER:
                        return Specialist.EXTRA_USER_SPECIALIST;
                }
            } catch (CantGetActorAddressBook cantGetActorAddressBook) {
                //TODO: Manage Exception
                cantGetActorAddressBook.printStackTrace();
            }
        }


        // Here we have a serious problem
        throw new CantSelectSpecialistException();

    }
}
