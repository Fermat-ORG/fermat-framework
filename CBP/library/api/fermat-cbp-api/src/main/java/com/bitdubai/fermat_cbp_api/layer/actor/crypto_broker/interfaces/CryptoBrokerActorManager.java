package com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantClearBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetRelationBetweenBrokerIdentityAndBrokerWalletException;

import java.util.Collection;


/**
 * Created by Angel 17-11-15
 */

public interface CryptoBrokerActorManager extends FermatManager {

    /**
     * @param identity
     * @param walletPublicKey
     * @return
     * @throws CantCreateNewBrokerIdentityWalletRelationshipException
     */
    BrokerIdentityWalletRelationship createNewBrokerIdentityWalletRelationship(ActorIdentity identity, String walletPublicKey) throws CantCreateNewBrokerIdentityWalletRelationshipException;

    /**
     * @param walletPublicKey
     * @return
     * @throws CantCreateNewBrokerIdentityWalletRelationshipException
     */
    void clearBrokerIdentityWalletRelationship(String walletPublicKey) throws CantClearBrokerIdentityWalletRelationshipException;


    /**
     * @return
     * @throws CantGetRelationBetweenBrokerIdentityAndBrokerWalletException
     */
    Collection<BrokerIdentityWalletRelationship> getAllBrokerIdentityWalletRelationship() throws CantGetRelationBetweenBrokerIdentityAndBrokerWalletException;

    /**
     * @param publicKey
     * @return
     * @throws CantGetRelationBetweenBrokerIdentityAndBrokerWalletException
     */
    BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByIdentity(String publicKey) throws CantGetRelationBetweenBrokerIdentityAndBrokerWalletException;

    /**
     * @param walletPublicKey
     * @return
     * @throws CantGetRelationBetweenBrokerIdentityAndBrokerWalletException
     */
    BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByWallet(String walletPublicKey) throws CantGetRelationBetweenBrokerIdentityAndBrokerWalletException;

}
