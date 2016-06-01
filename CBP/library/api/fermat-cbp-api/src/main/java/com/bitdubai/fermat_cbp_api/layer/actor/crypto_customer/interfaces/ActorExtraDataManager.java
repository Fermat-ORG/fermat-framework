package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListPlatformsException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantRequestBrokerExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateActorExtraDataException;

import java.util.Collection;

/**
 *
 * TODO ADD A DESCRIPTION FOR THE INTERFACE AND FOR EACH OF ITS METHODS
 *
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
     * Through the method <code>requestBrokerExtraData</code> we can ask a broker its extra data.
     * In this case we're requesting quotes information.
     *
     * @param actorExtraData data needed to ask for the quotes.
     *
     * @throws CantRequestBrokerExtraDataException if something goes wrong.
     */
    @Deprecated //TODO WE CAN'T USE THIS METHOD FROM OUTSIDE THE PLUG-IN, IF NEEDED DELETE FROM THE INTERFACE.
    void requestBrokerExtraData(ActorExtraData actorExtraData) throws CantRequestBrokerExtraDataException;

    /**
     *
     * @param paymentCurrency
     * @return list of platforms supporteds
     */
    Collection<Platforms> getPlatformsSupported(String customerPublicKey, String brokerPublicKey, String paymentCurrency) throws CantGetListActorExtraDataException;

}
