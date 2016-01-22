package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListPlatformsException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantUpdateActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.*;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorDao;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by angel on 5/1/16.
 */
public class ActorManager implements ActorExtraDataManager {

    private CryptoBrokerActorDao dao;
    private CryptoBrokerManager cryptoBrokerANSManager;
    private CryptoBrokerWalletManager cryptoBrokerWalletManager;

    public ActorManager(CryptoBrokerActorDao dao, CryptoBrokerManager cryptoBrokerANSManager, CryptoBrokerWalletManager cryptoBrokerWalletManager){
        this.dao = dao;
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
    }

    /*==============================================================================================
    *
    *   Broker Identity Wallet Relationship
    *
    *==============================================================================================*/


    @Override
        public BrokerIdentityWalletRelationship createNewBrokerIdentityWalletRelationship(ActorIdentity identity, UUID wallet) throws CantCreateNewBrokerIdentityWalletRelationshipException {
            return this.dao.createNewBrokerIdentityWalletRelationship(identity, wallet);
        }

        @Override
        public Collection<BrokerIdentityWalletRelationship> getAllBrokerIdentityWalletRelationship() throws CantGetListBrokerIdentityWalletRelationshipException {
            return this.dao.getAllBrokerIdentityWalletRelationship();
        }

        @Override
        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByIdentity(ActorIdentity identity) throws CantGetListBrokerIdentityWalletRelationshipException {
            return this.dao.getBrokerIdentityWalletRelationshipByIdentity(identity);
        }

        @Override
        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByWallet(UUID wallet) throws CantGetListBrokerIdentityWalletRelationshipException {
            return this.dao.getBrokerIdentityWalletRelationshipByWallet(wallet);
        }

    /*==============================================================================================
    *
    *   Actor Extra Data
    *
    *==============================================================================================*/

        @Override
        public void createBrokerExtraData(ActorExtraData actorExtraData) throws CantCreateNewActorExtraDataException {
            this.dao.createBrokerExtraData(actorExtraData);
        }

        @Override
        public void updateBrokerExtraData(ActorExtraData actorExtraData) throws CantUpdateActorExtraDataException {

        }

        @Override
        public Collection<ActorExtraData> getAllActorExtraData() throws CantGetListActorExtraDataException {
            return this.dao.getAllActorExtraData();
        }

        @Override
        public Collection<ActorExtraData> getAllActorExtraDataConnected() throws CantGetListActorExtraDataException {

            // TODO: buscar la lista de actores conectados y luego usar el metodo
            // TODO: getActorExtraDataByPublicKey(String _publicKey) para obtener la informacion de cada uno
            // TODO: y poder retornarla

            return null;
        }

        @Override
        public ActorExtraData getActorExtraDataByIdentity(ActorIdentity identity) throws CantGetListActorExtraDataException {
            return this.dao.getActorExtraDataByPublicKey(identity.getPublicKey());
        }

        @Override
        public ActorExtraData getActorExtraDataLocalActor() throws CantGetListActorExtraDataException {
            // TODO: buscar la informacion local del broker para pasarla al NS
            return null;
        }

        @Override
        public Collection<Platforms> getPlatformsSupport(String brokerPublicKey, Currency currency) throws CantGetListPlatformsException {
            return this.dao.getPlatformsSupport(brokerPublicKey, currency);
        }
}