package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.ActorAddressBookNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRecord;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRegistry;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.DealsWithActorAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookException;
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
        cryptoAddress.setAddress(cryptoTransaction.getAddressTo().getAddress());
        cryptoAddress.setCryptoCurrency(cryptoTransaction.getCryptoCurrency());

        ActorAddressBookRegistry actorsRegistry = null;

        try {
            actorsRegistry = this.actorAddressBook.getActorAddressBookRegistry();
        } catch (CantGetActorAddressBookRegistryException e) {
            // This exception will be managed by the relay agent
            throw new CantSelectSpecialistException("Can't get actor address book registry",e,"","");
        }

        ActorAddressBookRecord actor = null;

        if (actorsRegistry != null) {
            try {
                actor = actorsRegistry.getActorAddressBookByCryptoAddress(cryptoAddress);
                switch (actor.getDeliveredToActorType()) {
                    case DEVICE_USER:
                        return Specialist.DEVICE_USER_SPECIALIST;
                    case INTRA_USER:
                        return Specialist.INTRA_USER_SPECIALIST;
                    case EXTRA_USER:
                        return Specialist.EXTRA_USER_SPECIALIST;
                    default:
                        // Here we have a serious problem
                        throw new CantSelectSpecialistException("NO SPECIALIST FOUND",null,"Actor: " + actor.getDeliveredToActorType() + " with code " + actor.getDeliveredToActorType().getCode(),"Actor not considered in switch statement");
                }

            } catch (CantGetActorAddressBookException|ActorAddressBookNotFoundException cantGetActorAddressBookException) {
                // todo ver como manejar ActorAddressBookNotFoundException
                // This exception will be managed by the relay agent
                throw new CantSelectSpecialistException("Can't get actor address from registry", cantGetActorAddressBookException,"CryptoAddress: "+ cryptoAddress.getAddress(),"Address not stored");
            }
        }

        throw new CantSelectSpecialistException("NO SPECIALIST FOUND",null,"Registry is null","");
    }
}
