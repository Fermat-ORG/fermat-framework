package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListPlatformsException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantSendActorNetworkServiceException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateActorExtraDataException;

import java.util.Collection;

/**
 * Created by angel on 13/1/16.
 */
public interface ActorExtraDataManager extends CryptoCustomerActorManager {

    /**
     *
     * @param actorExtraData
     * @throws CantCreateNewActorExtraDataException
     */
    void createCustomerExtraData(ActorExtraData actorExtraData) throws CantCreateNewActorExtraDataException;

    /**
     *
     * @param actorExtraData
     * @throws CantUpdateActorExtraDataException
     */
    void updateCustomerExtraData(ActorExtraData actorExtraData) throws CantUpdateActorExtraDataException;

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
     * @param customerPublicKey
     * @param brokerPublicKey
     * @return
     * @throws CantGetListActorExtraDataException
     */
    ActorExtraData getActorExtraDataByIdentity(String customerPublicKey, String brokerPublicKey) throws CantGetListActorExtraDataException;

    /**
     *
     * @param publicKeyBroker
     * @return
     * @throws CantGetListActorExtraDataException
     */
    ActorIdentity getActorInformationByPublicKey(String publicKeyBroker) throws CantGetListActorExtraDataException;

    /**
     *
     * @return all currencies handled with platforms that support them
     */
    Collection<Platforms> getPlatformsSupport(String CustomerPublicKey, Currency currency) throws CantGetListPlatformsException;

    /**
     *
     * @param actorExtraData este parametro debe ser una clase que implemente ActorExtraData pero solo la parte de la identidad Ej: new ActorExtraDataImpl(ActorIdentity);
     * @throws CantSendActorNetworkServiceException
     */
    void requestBrokerExtraData(ActorExtraData actorExtraData) throws CantSendActorNetworkServiceException;

}
