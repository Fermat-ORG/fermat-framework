package com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListPlatformsException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantUpdateActorExtraDataException;

import java.util.Collection;

/**
 * Created by angel on 13/1/16.
 */
public interface ActorExtraDataManager extends CryptoBrokerActorManager {

    /**
     *
     * @param actorExtraData
     * @throws CantCreateNewActorExtraDataException
     */
    void createBrokerExtraData(ActorExtraData actorExtraData) throws CantCreateNewActorExtraDataException;

    /**
     *
     * @param actorExtraData
     * @throws CantUpdateActorExtraDataException
     */
    void updateBrokerExtraData(ActorExtraData actorExtraData) throws CantUpdateActorExtraDataException;

    /**
     *
     * @return
     * @throws CantGetListActorExtraDataException
     */
    Collection<ActorExtraData> getAllActorExtraData() throws CantGetListActorExtraDataException;

    /**
     *
     * @return
     * @throws CantGetListActorExtraDataException
     */
    Collection<ActorExtraData> getAllActorExtraDataConnected() throws CantGetListActorExtraDataException;

    /**
     *
     * @param identity
     * @return
     * @throws CantGetListActorExtraDataException
     */
    ActorExtraData getActorExtraDataByIdentity(ActorIdentity identity) throws CantGetListActorExtraDataException;

    /**
     *
     * @return
     * @throws CantGetListActorExtraDataException
     */
    ActorExtraData getActorExtraDataLocalActor() throws CantGetListActorExtraDataException;

    /**
     *
     * @return all currencies handled with platforms that support them
     */
    Collection<Platforms> getPlatformsSupport(String brokerPublicKey, Currency currency) throws CantGetListPlatformsException;

}
