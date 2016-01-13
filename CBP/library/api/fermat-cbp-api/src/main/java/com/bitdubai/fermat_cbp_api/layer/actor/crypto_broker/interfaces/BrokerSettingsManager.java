package com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerSettingsException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerSettingsException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantUpdateBrokerSettingsException;

import java.util.Collection;

/**
 * Created by angel on 13/1/16.
 */
public interface BrokerSettingsManager {

    /**
     *
     * @param brokerSettings
     * @throws CantCreateNewBrokerSettingsException
     */
    void createBrokerSetting(BrokerSettings brokerSettings) throws CantCreateNewBrokerSettingsException;

    /**
     *
     * @param brokerSettings
     * @throws CantUpdateBrokerSettingsException
     */
    void updateBrokerSetting(BrokerSettings brokerSettings) throws CantUpdateBrokerSettingsException;

    /**
     *
     * @return
     * @throws CantGetListBrokerSettingsException
     */
    Collection<BrokerSettings> getAllBrokerSettings() throws CantGetListBrokerSettingsException;

    /**
     *
     * @return
     * @throws CantGetListBrokerSettingsException
     */
    Collection<BrokerSettings> getAllBrokerSettingsConnected() throws CantGetListBrokerSettingsException;

    /**
     *
     * @param identity
     * @return
     * @throws CantGetListBrokerSettingsException
     */
    Collection<BrokerSettings> getBrokerSettingsConnectedByIdentity(ActorIdentity identity) throws CantGetListBrokerSettingsException;

    /**
     *
     * @return
     * @throws CantGetListBrokerSettingsException
     */
    BrokerSettings getBrokerSettingsLocalActor() throws CantGetListBrokerSettingsException;

}
