package com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * Created by Natalia on 15/06/2015.
 */
public interface ActorAddressBook {

    public UUID getActorId();

    public Actors getActorType();

    public CryptoAddress getCryptoAddress();
}