package com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * Created by Natalia on 15/06/2015.a
 */
public interface ActorAddressBookRecord {

    UUID getDeliveredByActorId();

    Actors getDeliveredByActorType();

    UUID getDeliveredToActorId();

    Actors getDeliveredToActorType();

    CryptoAddress getCryptoAddress();
}