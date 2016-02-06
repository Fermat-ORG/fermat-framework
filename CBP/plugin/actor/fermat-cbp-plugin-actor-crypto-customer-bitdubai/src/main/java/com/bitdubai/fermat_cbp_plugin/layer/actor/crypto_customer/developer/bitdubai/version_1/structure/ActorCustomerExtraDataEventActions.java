package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetExtraDataActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorQuotes;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CustomerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.QuotesExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 4/02/16.
 */
public class ActorCustomerExtraDataEventActions {

    private CryptoBrokerManager cryptoBrokerANSManager;
    private CryptoCustomerActorConnectionManager cryptoCustomerActorConnectionManager;
    private CryptoCustomerActorDao cryptoCustomerActorDao;

    public ActorCustomerExtraDataEventActions(CryptoBrokerManager cryptoBrokerANSManager, CryptoCustomerActorConnectionManager cryptoCustomerActorConnectionManager, CryptoCustomerActorDao cryptoCustomerActorDao){
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.cryptoCustomerActorConnectionManager = cryptoCustomerActorConnectionManager;
        this.cryptoCustomerActorDao = cryptoCustomerActorDao;
    }

    public void handleNewsEvent(){
        try {
            CryptoBrokerActorExtraData data = new CryptoBrokerActorExtraDataInformation(null);
            // TODO: obtener data el objeto data a traves del NS

            this.setExtraData(data);
        } catch (CantGetExtraDataActorException e) {
            e.printStackTrace();
        }
    }

    public void setExtraData(CryptoBrokerActorExtraData data) throws CantGetExtraDataActorException {
        try {
            Collection<CustomerIdentityWalletRelationship> relationship = this.cryptoCustomerActorDao.getAllCustomerIdentityWalletRelationship();
            for(CustomerIdentityWalletRelationship rel : relationship){
                CryptoCustomerLinkedActorIdentity linkedActorIdentity = new CryptoCustomerLinkedActorIdentity(
                        rel.getCryptoCustomer(),
                        Actors.CBP_CRYPTO_CUSTOMER
                );
                CryptoCustomerActorConnectionSearch search = cryptoCustomerActorConnectionManager.getSearch(linkedActorIdentity);
                search.addConnectionState(ConnectionState.CONNECTED);
                List<CryptoCustomerActorConnection> actorConnections = search.getResult();
                for(CryptoCustomerActorConnection actor : actorConnections){
                    ActorIdentity identity = new ActorExtraDataIdentity(actor.getAlias(), actor.getPublicKey(), actor.getImage());
                    CryptoBrokerActorExtraData dataNS = null;
                    // TODO: Obtener el dataNS desde el ANS
                    Collection<QuotesExtraData> quotes = new ArrayList<>();
                    if(dataNS != null) {
                        for (CryptoBrokerActorQuotes quo : dataNS.quotes()) {
                            QuotesExtraData quote = new QuotesExtraDataInformation(UUID.randomUUID(), quo.getMerchandise(), quo.getPaymentCurrency(), quo.getPrice());
                            quotes.add(quote);
                        }
                    }
                    ActorExtraData actorExtraData = new ActorExtraDataInformation(identity, quotes, null);
                    if(this.cryptoCustomerActorDao.existBrokerExtraData(actor.getPublicKey())){
                        this.cryptoCustomerActorDao.updateCustomerExtraData(actorExtraData);
                    }else {
                        this.cryptoCustomerActorDao.createCustomerExtraData(actorExtraData);
                    }
                }
            }
        } catch (CantGetListCustomerIdentityWalletRelationshipException e) {
            // TODO: manejar las excepciones
        } catch (CantListActorConnectionsException e) {
            // TODO: manejar las excepciones
        } catch (CantCreateNewActorExtraDataException e) {
            // TODO: manejar las excepciones
        } catch (CantUpdateActorExtraDataException e) {
            // TODO: manejar las excepciones
        }
    }
}
