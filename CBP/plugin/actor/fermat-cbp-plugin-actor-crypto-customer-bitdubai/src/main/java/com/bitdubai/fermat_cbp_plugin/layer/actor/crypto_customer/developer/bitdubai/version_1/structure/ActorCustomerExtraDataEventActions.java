package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetExtraDataActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CustomerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.QuotesExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingQuotesRequestsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestQuotesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerQuote;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantGetBrokersConnectedException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantNewConnectionEventException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantNewsEventException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 4/02/16.
 */

public class ActorCustomerExtraDataEventActions {

    private CryptoBrokerManager cryptoBrokerANSManager;
    private CryptoCustomerActorDao cryptoCustomerActorDao;
    private CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager;

    public ActorCustomerExtraDataEventActions(CryptoBrokerManager cryptoBrokerANSManager, CryptoCustomerActorDao cryptoCustomerActorDao, CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager){
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.cryptoCustomerActorDao = cryptoCustomerActorDao;
        this.cryptoBrokerActorConnectionManager = cryptoBrokerActorConnectionManager;
    }

    public void handleNewsEvent() throws CantNewsEventException {
        try {
            this.setExtraData();
        } catch (CantGetExtraDataActorException e) {
            throw new CantNewsEventException(e.getMessage(), e, "", "");
        }
    }

    public void handleNewConnectionEvent() throws CantNewConnectionEventException {
        try {
            Collection<CustomerIdentityWalletRelationship> relationships = cryptoCustomerActorDao.getAllCustomerIdentityWalletRelationship();
            for(CustomerIdentityWalletRelationship relationship : relationships){
                List<CryptoBrokerActorConnection> actores = getBrokersConnects(relationship);
                for(CryptoBrokerActorConnection broker : actores){
                    if( !this.cryptoCustomerActorDao.existBrokerExtraData(relationship.getCryptoCustomer(), broker.getPublicKey()) ) {
                        try {
                            ActorIdentity brokerIdentity = new ActorExtraDataIdentity(broker.getAlias(), broker.getPublicKey(), broker.getImage());
                            this.cryptoCustomerActorDao.createCustomerExtraData(new ActorExtraDataInformation(relationship.getCryptoCustomer(), brokerIdentity, null, null));
                            this.cryptoBrokerANSManager.requestQuotes(relationship.getCryptoCustomer(), Actors.CBP_CRYPTO_CUSTOMER, broker.getPublicKey());
                        } catch (CantCreateNewActorExtraDataException e) {
                            throw new CantNewConnectionEventException(e.getMessage(), e, "", "");
                        } catch (CantRequestQuotesException e) {
                            throw new CantNewConnectionEventException(e.getMessage(), e, "", "");
                        }
                    }
                }
            }
        } catch (CantGetListCustomerIdentityWalletRelationshipException e) {
            throw new CantNewConnectionEventException(e.getMessage(), e, "", "");
        } catch (CantGetBrokersConnectedException e) {
            throw new CantNewConnectionEventException(e.getMessage(), e, "", "");
        }
    }

    private List<CryptoBrokerActorConnection> getBrokersConnects(CustomerIdentityWalletRelationship relationship) throws CantGetBrokersConnectedException {
        CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(
            relationship.getCryptoCustomer(),
            Actors.CBP_CRYPTO_CUSTOMER
        );
        final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);
        search.addConnectionState(ConnectionState.CONNECTED);
        try {
            return search.getResult();
        } catch (CantListActorConnectionsException e) {
            throw new CantGetBrokersConnectedException(e.getMessage(), e, "", "");
        }
    }

    public void setExtraData() throws CantGetExtraDataActorException {
        List<CryptoBrokerExtraData<CryptoBrokerQuote>> dataNS;
        try {
            dataNS = cryptoBrokerANSManager.listPendingQuotesRequests(RequestType.SENT);
            if(dataNS != null) {
                for (CryptoBrokerExtraData<CryptoBrokerQuote> extraDate : dataNS) {
                    Collection<QuotesExtraData> quotes = new ArrayList<>();
                    ActorIdentity identity = new ActorExtraDataIdentity("", extraDate.getCryptoBrokerPublicKey(), null);
                    for (CryptoBrokerQuote quo : extraDate.listInformation()) {
                        QuotesExtraData quote = new QuotesExtraDataInformation(UUID.randomUUID(), quo.getMerchandise(), quo.getPaymentCurrency(), quo.getPrice());
                        quotes.add(quote);
                    }
                    ActorExtraData actorExtraData = new ActorExtraDataInformation(extraDate.getRequesterPublicKey(), identity, quotes, null);
                    if( this.cryptoCustomerActorDao.existBrokerExtraDataQuotes(identity.getPublicKey(), extraDate.getRequesterPublicKey()) ){
                        this.cryptoCustomerActorDao.updateQuotes(actorExtraData);
                    }else{
                        this.cryptoCustomerActorDao.createCustomerExtraData(actorExtraData);
                    }
                }
            }
        } catch (CantUpdateActorExtraDataException e) {
            throw new CantGetExtraDataActorException(e.getMessage(), e, "", "");
        } catch (CantCreateNewActorExtraDataException e) {
            throw new CantGetExtraDataActorException(e.getMessage(), e, "", "");
        } catch (CantListPendingQuotesRequestsException e) {
            throw new CantGetExtraDataActorException(e.getMessage(), e, "", "");
        }
    }
}
