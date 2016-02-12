package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetExtraDataActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.QuotesExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingQuotesRequestsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerQuote;
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
    private CryptoCustomerActorDao cryptoCustomerActorDao;

    public ActorCustomerExtraDataEventActions(CryptoBrokerManager cryptoBrokerANSManager, CryptoCustomerActorDao cryptoCustomerActorDao){
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.cryptoCustomerActorDao = cryptoCustomerActorDao;
    }

    public void handleNewsEvent(){
        try {
            this.setExtraData();
        } catch (CantGetExtraDataActorException e) {

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
        } catch (CantUpdateActorExtraDataException | CantCreateNewActorExtraDataException | CantListPendingQuotesRequestsException ignore) {
        }
    }
}
