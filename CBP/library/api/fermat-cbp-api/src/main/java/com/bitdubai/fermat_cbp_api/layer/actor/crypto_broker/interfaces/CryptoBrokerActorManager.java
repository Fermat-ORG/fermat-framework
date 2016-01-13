package com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerSettingsException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerSettingsException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantUpdateBrokerSettingsException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Angel 17-11-15
 */

public interface CryptoBrokerActorManager extends FermatManager {

    /**
     *
     * @param identity
     * @param wallet
     * @return
     * @throws CantCreateNewBrokerIdentityWalletRelationshipException
     */
    BrokerIdentityWalletRelationship createNewBrokerIdentityWalletRelationship(ActorIdentity identity, UUID wallet) throws CantCreateNewBrokerIdentityWalletRelationshipException;

    /**
     *
     * @return
     * @throws CantGetListBrokerIdentityWalletRelationshipException
     */
    Collection<BrokerIdentityWalletRelationship> getAllBrokerIdentityWalletRelationship() throws CantGetListBrokerIdentityWalletRelationshipException;

    /**
     *
     * @param identity
     * @return
     * @throws CantGetListBrokerIdentityWalletRelationshipException
     */
    BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByIdentity(ActorIdentity identity) throws CantGetListBrokerIdentityWalletRelationshipException;

    /**
     *
     * @param wallet
     * @return
     * @throws CantGetListBrokerIdentityWalletRelationshipException
     */
    BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByWallet(UUID wallet) throws CantGetListBrokerIdentityWalletRelationshipException;

    /*=========================================

        Methods of the Settngs

     =========================================*/

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
