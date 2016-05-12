package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCustomerIdentityWalletRelationshipException;
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
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantCheckIfExistsException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantGetBrokersConnectedException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantHandleNewConnectionEventException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantNewsEventException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantSetActorExtraDataException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.helpers.AdapterPlatformsSupported;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.ActorCustomerExtraDataEventActions</code>
 * It contains all the necessary logic to manage events related to receiving quotes broker
 * <p/>
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 4/02/16.
 *
 * @author angel
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class ActorCustomerExtraDataEventActions {

    private final CryptoBrokerManager                cryptoBrokerANSManager            ;
    private final CryptoCustomerActorDao             cryptoCustomerActorDao            ;
    private final CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager;

    public ActorCustomerExtraDataEventActions(final CryptoBrokerManager                cryptoBrokerANSManager            ,
                                              final CryptoCustomerActorDao             cryptoCustomerActorDao            ,
                                              final CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager) {

        this.cryptoBrokerANSManager             = cryptoBrokerANSManager            ;
        this.cryptoCustomerActorDao             = cryptoCustomerActorDao            ;
        this.cryptoBrokerActorConnectionManager = cryptoBrokerActorConnectionManager;
    }

    public void handleNewsEvent() throws CantNewsEventException {

        try {

            this.setExtraData();

        } catch (CantSetActorExtraDataException e) {

            throw new CantNewsEventException(e.getMessage(), e, "", "");
        }
    }

    public void handleNewConnectionEvent() throws CantHandleNewConnectionEventException {

        try {

            Collection<CustomerIdentityWalletRelationship> relationships = cryptoCustomerActorDao.getAllCustomerIdentityWalletRelationship();

            for(CustomerIdentityWalletRelationship relationship : relationships){

                List<CryptoBrokerActorConnection> connections = getBrokersConnected(relationship);

                for(CryptoBrokerActorConnection broker : connections){

                    if( !this.cryptoCustomerActorDao.existBrokerExtraData(relationship.getCryptoCustomer(), broker.getPublicKey()) ) {

                        if( !this.cryptoCustomerActorDao.existBrokerExtraData(broker.getPublicKey(), relationship.getCryptoCustomer()) ){

                            ActorIdentity brokerIdentity = new ActorExtraDataIdentity(broker.getAlias(), broker.getPublicKey(), broker.getImage());

                            this.cryptoCustomerActorDao.createCustomerExtraData(new ActorExtraDataInformation(relationship.getCryptoCustomer(), brokerIdentity, null, null));
                        }

                        this.cryptoBrokerANSManager.requestQuotes(relationship.getCryptoCustomer(), Actors.CBP_CRYPTO_CUSTOMER, broker.getPublicKey());
                    }
                }
            }

        } catch (CantGetCustomerIdentityWalletRelationshipException e) {

            throw new CantHandleNewConnectionEventException(e, "", "Error trying to get the list of customer-wallet relationships.");
        } catch (CantGetBrokersConnectedException e) {

            throw new CantHandleNewConnectionEventException(e, "", "Error trying to get the list of brokers connected to that customer.");
        } catch (CantCheckIfExistsException e) {

            throw new CantHandleNewConnectionEventException(e, "", "Error in DAO trying to check if the extra data for the broker exists.");
        }  catch (CantCreateNewActorExtraDataException e) {

            throw new CantHandleNewConnectionEventException(e, "", "Error trying to create a new actor extra data for requesting the quotes.");
        } catch (CantRequestQuotesException e) {

            throw new CantHandleNewConnectionEventException(e, "", "Cant request quotes through the network service.");
        } catch (Exception e) {

            throw new CantHandleNewConnectionEventException(e, "", "Unhandled error.");
        }
    }

    private List<CryptoBrokerActorConnection> getBrokersConnected(CustomerIdentityWalletRelationship relationship) throws CantGetBrokersConnectedException {

        try {

            CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(
                    relationship.getCryptoCustomer(),
                    Actors.CBP_CRYPTO_CUSTOMER
            );

            final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);
            search.addConnectionState(ConnectionState.CONNECTED);

            return search.getResult();

        } catch (CantListActorConnectionsException e) {

            throw new CantGetBrokersConnectedException(e, "", "Error trying to list the broker connections of the customer.");
        }
    }

    public void setExtraData() throws CantSetActorExtraDataException {

        try {

            List<CryptoBrokerExtraData<CryptoBrokerQuote>> dataNS = cryptoBrokerANSManager.listPendingQuotesRequests(RequestType.SENT);

            for (CryptoBrokerExtraData<CryptoBrokerQuote> extraDate : dataNS) {

                Collection<QuotesExtraData> quotes = new ArrayList<>();
                ActorIdentity identity = new ActorExtraDataIdentity("", extraDate.getCryptoBrokerPublicKey(), null);

                for (CryptoBrokerQuote quo : extraDate.listInformation()) {
                    QuotesExtraData quote = new QuotesExtraDataInformation(UUID.randomUUID(), quo.getMerchandise(), quo.getPaymentCurrency(), quo.getPrice(), AdapterPlatformsSupported.getPlatformsSupported(quo.getSupportedPlatforms()));
                    quotes.add(quote);
                }

                ActorExtraData actorExtraData = new ActorExtraDataInformation(extraDate.getRequesterPublicKey(), identity, quotes, null);

                if( this.cryptoCustomerActorDao.existBrokerExtraDataQuotes( extraDate.getRequesterPublicKey(), identity.getPublicKey() ) ){

                    this.cryptoCustomerActorDao.updateQuotes(actorExtraData);
                }else{

                    this.cryptoCustomerActorDao.createActorQuotes(actorExtraData);
                }

                cryptoBrokerANSManager.confirmQuotesRequest(extraDate.getRequestId());
            }

        } catch (CantUpdateActorExtraDataException e) {

            throw new CantSetActorExtraDataException(e, "", "Error in DAO trying to update the actor extra data.");
        } catch (CantCreateNewActorExtraDataException e) {

            throw new CantSetActorExtraDataException(e, "", "Error in DAO trying to create new actor extra data.");
        } catch (CantListPendingQuotesRequestsException e) {

            throw new CantSetActorExtraDataException(e, "", "Error in ANS trying to list the pending quotes requests.");
        } catch (CantCheckIfExistsException e) {

            throw new CantSetActorExtraDataException(e, "", "Error in DAO trying to check if the extra data for the broker exists.");
        } catch (Exception e) {

            throw new CantSetActorExtraDataException(e, "", "Unhandled error.");
        }
    }
}
