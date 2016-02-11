package com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Angel 17-11-15
 */

public interface CryptoBrokerActorManager extends FermatManager {

    /**
     *
     * @param identity
     * @param walletPublicKey
     * @return
     * @throws CantCreateNewBrokerIdentityWalletRelationshipException
     */
    BrokerIdentityWalletRelationship createNewBrokerIdentityWalletRelationship(ActorIdentity identity, String walletPublicKey) throws CantCreateNewBrokerIdentityWalletRelationshipException;

    /**
     *
     * @return
     * @throws CantGetListBrokerIdentityWalletRelationshipException
     */
    Collection<BrokerIdentityWalletRelationship> getAllBrokerIdentityWalletRelationship() throws CantGetListBrokerIdentityWalletRelationshipException;

    /**
     *
     * @param publicKey
     * @return
     * @throws CantGetListBrokerIdentityWalletRelationshipException
     */
    BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByIdentity(String publicKey) throws CantGetListBrokerIdentityWalletRelationshipException;

    /**
     *
     * @param walletPublicKey
     * @return
     * @throws CantGetListBrokerIdentityWalletRelationshipException
     */
    BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByWallet(String walletPublicKey) throws CantGetListBrokerIdentityWalletRelationshipException;

}
