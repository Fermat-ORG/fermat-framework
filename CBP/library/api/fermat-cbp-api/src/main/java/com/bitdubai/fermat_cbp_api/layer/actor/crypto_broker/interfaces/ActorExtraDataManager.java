package com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantUpdateActorExtraDataException;

import java.util.Collection;

/**
 * Created by angel on 13/1/16.
 */
public interface ActorExtraDataManager {

    /**
     *
     * @param ActorExtraData
     * @throws CantCreateNewActorExtraDataException
     */
    void createBrokerSetting(ActorExtraData ActorExtraData) throws CantCreateNewActorExtraDataException;

    /**
     *
     * @param ActorExtraData
     * @throws CantUpdateActorExtraDataException
     */
    void updateBrokerSetting(ActorExtraData ActorExtraData) throws CantUpdateActorExtraDataException;

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
    Collection<ActorExtraData> getActorExtraDataConnectedByIdentity(ActorIdentity identity) throws CantGetListActorExtraDataException;

    /**
     *
     * @return
     * @throws CantGetListActorExtraDataException
     */
    ActorExtraData getActorExtraDataLocalActor() throws CantGetListActorExtraDataException;

}
