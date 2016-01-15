package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantUpdateActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.*;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorDao;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by angel on 5/1/16.
 */
public class ActorManager implements ActorExtraDataManager {

    private CryptoBrokerActorDao dao;

    public ActorManager(CryptoBrokerActorDao dao){
        this.dao = dao;
    }

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

    @Override
    public void createBrokerSetting(ActorExtraData ActorExtraData) throws CantCreateNewActorExtraDataException {

    }

    @Override
    public void updateBrokerSetting(ActorExtraData ActorExtraData) throws CantUpdateActorExtraDataException {

    }

    @Override
    public Collection<ActorExtraData> getAllActorExtraData() throws CantGetListActorExtraDataException {
        return null;
    }

    @Override
    public Collection<ActorExtraData> getAllActorExtraDataConnected() throws CantGetListActorExtraDataException {
        return null;
    }

    @Override
    public Collection<ActorExtraData> getActorExtraDataConnectedByIdentity(ActorIdentity identity) throws CantGetListActorExtraDataException {
        return null;
    }

    @Override
    public ActorExtraData getActorExtraDataLocalActor() throws CantGetListActorExtraDataException {
        return null;
    }
}
