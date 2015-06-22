package com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRecord;

import java.util.UUID;

/**
 * Created by Natalia on 16/06/2015
 */

/**
 * This class manages the relationship between actors and crypto addresses by storing them on a Database Table.
 */
public class ActorAddressBookCryptoModuleRecord implements ActorAddressBookRecord {

    /**
     * ActorAddressBookRecord Interface member variables.
     */
    private UUID actorId ;
    private Actors actorType;
    private CryptoAddress cryptoAddress;


    /**
     * Constructor.
     */
    public ActorAddressBookCryptoModuleRecord(UUID actorId, Actors actorType, CryptoAddress cryptoAddress){
        /**
         * Set actor settings.
         */
        this.actorId = actorId;
        this.actorType = actorType;
        this.cryptoAddress = cryptoAddress;
    }


    @Override
    public UUID getActorId(){
        return this.actorId;
    }

    @Override
    public Actors getActorType(){
        return this.actorType;
    }

    @Override
    public CryptoAddress getCryptoAddress(){
        return this.cryptoAddress;
    }
}