package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDisconnectException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingConnectionNewsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerConnectionNew;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerData;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantCreateCryptoBrokerActorException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantGetCryptoBrokerActorException;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Angel 17-11-15
 */

public interface CryptoBrokerActorManager {

    /* Identity management a broker */

        CryptoBrokerActor createNewCryptoBroker(ActorIdentity identity) throws CantCreateCryptoBrokerActorException;
        CryptoBrokerActor getCryptoBroker(ActorIdentity identity) throws CantGetCryptoBrokerActorException;

    /* Management of customers of broker */

        Collection<ActorIdentity> createBrokerConnection(ActorIdentity cryptoCustomer);
        Collection<ActorIdentity> getAllConnectedBrokers();
        ActorIdentity getConnectedCustomerByCustomer(ActorIdentity cryptoCustomer);

    /* Management of Associations of the Wallets of broker */

        void createAssociationCryptoBrokerIdentityWallet(CryptoBrokerActorWalletIdentity association);
        Collection<CryptoBrokerActorWalletIdentity> getAllAssociationCryptoBrokerIdentityWallet();
        CryptoBrokerActorWalletIdentity getAssociationCryptoBrokerIdentityWalletByIdentity(ActorIdentity cryptoBroker);
        CryptoBrokerActorWalletIdentity getAssociationCryptoBrokerIdentityWalletByWallet(UUID wallet);

    /* Communication with the network service */

        void requestConnection(final CryptoBrokerData cryptoBrokerData) throws CantRequestConnectionException;
        void disconnect(final String publicKey) throws CantDisconnectException;
        void denyConnection(final UUID requestId) throws CantDenyConnectionRequestException, ConnectionRequestNotFoundException;
        void cancelConnection(final UUID requestId) throws CantCancelConnectionRequestException, ConnectionRequestNotFoundException;
        void acceptConnection(final UUID requestId) throws CantAcceptConnectionRequestException, ConnectionRequestNotFoundException;
        List<CryptoBrokerConnectionNew> getPendingConnectionNews() throws CantListPendingConnectionNewsException;

        void requestReceivedProcessed(ActorIdentity cryptoCustomer);
        void requestSentProcessed(ActorIdentity cryptoCustomer);

}
