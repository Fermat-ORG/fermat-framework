package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantRequestBrokerExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateActorExtraDataException;

import java.util.Collection;

/**
 * TODO ADD A DESCRIPTION FOR THE INTERFACE AND FOR EACH OF ITS METHODS
 * <p/>
 * Created by angel on 13/1/16.
 */
public interface ActorExtraDataManager extends CryptoCustomerActorManager {

    /**
     * @param actorExtraData
     * @throws CantCreateNewActorExtraDataException
     */
    void createCustomerExtraData(ActorExtraData actorExtraData) throws CantCreateNewActorExtraDataException;

    /**
     * @param actorExtraData
     * @throws CantUpdateActorExtraDataException
     */
    void updateCustomerExtraData(ActorExtraData actorExtraData) throws CantUpdateActorExtraDataException;

    /**
     * @return
     * @throws CantGetListActorExtraDataException
     */
    Collection<ActorExtraData> getAllActorExtraData() throws CantGetListActorExtraDataException;

    /**
     * @return
     * @throws CantGetListActorExtraDataException
     */
    Collection<ActorExtraData> getAllActorExtraDataConnected() throws CantGetListActorExtraDataException;

    /**
     * @param customerPublicKey
     * @param brokerPublicKey
     * @return
     * @throws CantGetListActorExtraDataException
     */
    ActorExtraData getActorExtraDataByIdentity(String customerPublicKey, String brokerPublicKey) throws CantGetListActorExtraDataException;

    /**
     * @param publicKeyBroker
     * @return
     * @throws CantGetListActorExtraDataException
     */
    ActorIdentity getActorInformationByPublicKey(String publicKeyBroker) throws CantGetListActorExtraDataException;

    /**
     * Through the method <code>requestBrokerExtraData</code> we can ask a broker its extra data.
     * In this case we're requesting quotes information.
     *
     * @throws CantRequestBrokerExtraDataException if something goes wrong.
     */
    void requestBrokerExtraData() throws CantRequestBrokerExtraDataException;

    /**
     * @param paymentCurrency
     * @return list of platforms supporteds
     */
    Collection<Platforms> getPlatformsSupported(String customerPublicKey, String brokerPublicKey, String paymentCurrency) throws CantGetListActorExtraDataException;

}
