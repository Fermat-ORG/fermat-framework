package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListPlatformsException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraDataManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CustomerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDao;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by angel on 5/1/16.
 */
public class ActorManager implements ActorExtraDataManager {

    private CryptoCustomerActorDao dao;
    private CryptoBrokerManager cryptoBrokerANSManager;

    public ActorManager(CryptoCustomerActorDao dao, CryptoBrokerManager cryptoBrokerANSManager){
        this.dao = dao;
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
    }

    /*==============================================================================================
    *
    *   Customer Identity Wallet Relationship
    *
    *==============================================================================================*/


        @Override
        public CustomerIdentityWalletRelationship createNewCustomerIdentityWalletRelationship(ActorIdentity identity, UUID wallet) throws CantCreateNewCustomerIdentityWalletRelationshipException {
            return this.dao.createNewCustomerIdentityWalletRelationship(identity, wallet);
        }

        @Override
        public Collection<CustomerIdentityWalletRelationship> getAllCustomerIdentityWalletRelationship() throws CantGetListCustomerIdentityWalletRelationshipException {
            return this.dao.getAllCustomerIdentityWalletRelationship();
        }

        @Override
        public CustomerIdentityWalletRelationship getCustomerIdentityWalletRelationshipByIdentity(ActorIdentity identity) throws CantGetListCustomerIdentityWalletRelationshipException {
            return this.dao.getCustomerIdentityWalletRelationshipByIdentity(identity);
        }

        @Override
        public CustomerIdentityWalletRelationship getCustomerIdentityWalletRelationshipByWallet(UUID wallet) throws CantGetListCustomerIdentityWalletRelationshipException {
            return this.dao.getCustomerIdentityWalletRelationshipByWallet(wallet);
        }

    /*==============================================================================================
    *
    *   Actor Extra Data
    *
    *==============================================================================================*/

        @Override
        public void createCustomerExtraData(ActorExtraData actorExtraData) throws CantCreateNewActorExtraDataException {
            this.dao.createCustomerExtraData(actorExtraData);
        }

        @Override
        public void updateCustomerExtraData(ActorExtraData actorExtraData) throws CantUpdateActorExtraDataException {

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
            // TODO: buscar la informacion local del Customer para pasarla al NS
            return null;
        }

        @Override
        public Collection<Platforms> getPlatformsSupport(String CustomerPublicKey, Currency currency) throws CantGetListPlatformsException {
            return this.dao.getPlatformsSupport(CustomerPublicKey, currency);
        }
}