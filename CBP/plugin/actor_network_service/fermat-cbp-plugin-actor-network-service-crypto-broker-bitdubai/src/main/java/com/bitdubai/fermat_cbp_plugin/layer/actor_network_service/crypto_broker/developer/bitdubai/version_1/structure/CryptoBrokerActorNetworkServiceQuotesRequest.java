package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.adapters.CurrencyTypeHierarchyAdapter;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ProtocolState;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerQuote;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.enums.MessageTypes;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerActorNetworkServiceQuotesRequest</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/02/2016.
 */
public class CryptoBrokerActorNetworkServiceQuotesRequest extends NetworkServiceMessage implements CryptoBrokerExtraData<CryptoBrokerQuote> {

    private final UUID requestId;
    private final String requesterPublicKey;
    private final Actors requesterActorType;
    private final String cryptoBrokerPublicKey;
    private final long updateTime;
    private final RequestType type;
    private final ProtocolState state;
    private final List<CryptoBrokerQuote> quotes;

    public CryptoBrokerActorNetworkServiceQuotesRequest(final UUID requestId,
                                                        final String requesterPublicKey,
                                                        final Actors requesterActorType,
                                                        final String cryptoBrokerPublicKey,
                                                        final long updateTime,
                                                        final List<CryptoBrokerQuote> quotes,
                                                        final RequestType type,
                                                        final ProtocolState state) {

        super(MessageTypes.QUOTES_REQUEST);

        this.requestId = requestId;
        this.requesterPublicKey = requesterPublicKey;
        this.requesterActorType = requesterActorType;
        this.cryptoBrokerPublicKey = cryptoBrokerPublicKey;
        this.updateTime = updateTime;
        this.quotes = quotes;
        this.type = type;
        this.state = state;
    }

    @Override
    public String toJson() {

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Currency.class, new CurrencyTypeHierarchyAdapter())
                .create();
        return gson.toJson(this);
    }

    public static CryptoBrokerActorNetworkServiceQuotesRequest fromJson(String jsonMessage) {

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Currency.class, new CurrencyTypeHierarchyAdapter())
                .create();

        return gson.fromJson(jsonMessage, CryptoBrokerActorNetworkServiceQuotesRequest.class);
    }


    @Override
    public UUID getRequestId() {
        return requestId;
    }

    @Override
    public String getRequesterPublicKey() {
        return requesterPublicKey;
    }

    @Override
    public Actors getRequesterActorType() {
        return requesterActorType;
    }

    @Override
    public String getCryptoBrokerPublicKey() {
        return cryptoBrokerPublicKey;
    }

    @Override
    public long getUpdateTime() {
        return updateTime;
    }

    @Override
    public List<CryptoBrokerQuote> listInformation() {
        return quotes;
    }

    public RequestType getType() {
        return type;
    }

    public ProtocolState getState() {
        return state;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("CryptoBrokerActorNetworkServiceQuotesRequest{")
                .append("requestId=").append(requestId)
                .append(", requesterPublicKey='").append(requesterPublicKey)
                .append('\'')
                .append(", requesterActorType=").append(requesterActorType)
                .append(", cryptoBrokerPublicKey='").append(cryptoBrokerPublicKey)
                .append('\'')
                .append(", updateTime=").append(updateTime)
                .append(", quotes=").append(quotes)
                .append(", type=").append(type)
                .append(", state=").append(state)
                .append('}').toString();
    }
}
