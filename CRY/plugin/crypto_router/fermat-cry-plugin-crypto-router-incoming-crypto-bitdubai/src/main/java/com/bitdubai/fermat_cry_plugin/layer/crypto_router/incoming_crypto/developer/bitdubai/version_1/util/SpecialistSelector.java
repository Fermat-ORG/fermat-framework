package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._1_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer._1_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.pip_user.User;
import com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.ActorAddressBook;
import com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.DealsWithActorAddressBook;
import com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.exceptions.CantGetActorCryptoAddress;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantSelectSpecialistException;

/**
 * Created by eze on 12/06/15.
 */

/*
 * The purpose of this class is to indicate the correct
 * destination for a given transaction
 */
public class SpecialistSelector implements DealsWithActorAddressBook {

    private ActorAddressBook actorAddressBook;

    public Specialist getSpecialist(CryptoTransaction cryptoTransaction) throws CantSelectSpecialistException {

        CryptoAddress cryptoAddress = new CryptoAddress();
        cryptoAddress.setAddress(cryptoTransaction.getAddressFrom());
        cryptoAddress.setCryptoCurrency(cryptoTransaction.getCryptoCurrency());

        User user = null;
        try {
            user = this.actorAddressBook.getActorByCryptoAddress(cryptoAddress);
        } catch (CantGetActorCryptoAddress cantGetActorCryptoAddress) {
            cantGetActorCryptoAddress.printStackTrace();
        }

        if (user != null) {
            switch (user.getType()) {
                case DEVICE_USER:
                    return Specialist.DEVICE_USER;
                case INTRA_USER:
                    return Specialist.INTRA_USER;
                case EXTRA_USER:
                    return Specialist.EXTRA_USER;
            }
        }
        // Here we have a serious problem
        throw new CantSelectSpecialistException();

    }

    @Override
    public void setUserAddressBookManager(ActorAddressBook actorAddressBook) {
        this.actorAddressBook = actorAddressBook;
    }

}
