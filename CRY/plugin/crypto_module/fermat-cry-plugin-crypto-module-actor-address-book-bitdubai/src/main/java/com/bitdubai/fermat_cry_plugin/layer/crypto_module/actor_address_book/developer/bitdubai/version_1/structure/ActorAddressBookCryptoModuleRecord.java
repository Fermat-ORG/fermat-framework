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
    private UUID deliveredByActorId ;
    private Actors deliveredByActorType;
    private UUID deliveredToActorId ;
    private Actors deliveredToActorType;
    private CryptoAddress cryptoAddress;


    /**
     * Constructor.
     */
    public ActorAddressBookCryptoModuleRecord(UUID deliveredByActorId, Actors deliveredByActorType, UUID deliveredToActorId, Actors deliveredToActorType, CryptoAddress cryptoAddress){
        /**
         * Set actor settings.
         */
        this.deliveredByActorId = deliveredByActorId;
        this.deliveredByActorType = deliveredByActorType;
        this.deliveredToActorId = deliveredToActorId;
        this.deliveredToActorType = deliveredToActorType;
        this.cryptoAddress = cryptoAddress;
    }


    @Override
    public UUID getDeliveredByActorId() {
        return deliveredByActorId;
    }

    @Override
    public Actors getDeliveredByActorType() {
        return deliveredByActorType;
    }

    @Override
    public UUID getDeliveredToActorId() {
        return deliveredToActorId;
    }

    @Override
    public Actors getDeliveredToActorType() {
        return deliveredToActorType;
    }

    @Override
    public CryptoAddress getCryptoAddress(){
        return this.cryptoAddress;
    }
}