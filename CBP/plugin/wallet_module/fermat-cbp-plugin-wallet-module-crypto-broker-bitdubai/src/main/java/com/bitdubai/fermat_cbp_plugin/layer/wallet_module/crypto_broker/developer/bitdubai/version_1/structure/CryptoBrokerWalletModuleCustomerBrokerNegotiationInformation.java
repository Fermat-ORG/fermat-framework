package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Customer and Broker Negotiation Information
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 05/11/15.
 */
public class CryptoBrokerWalletModuleCustomerBrokerNegotiationInformation implements CustomerBrokerNegotiationInformation, Serializable {

    private ActorIdentity customerIdentity;
    private ActorIdentity brokerIdentity;
    private Map<ClauseType, String> summary;
    private Map<ClauseType, ClauseInformation> clauses;
    private NegotiationStatus status;
    private String note;
    private long lastNegotiationUpdateDate;
    private long expirationDatetime;
    private UUID negotiationId;
    private String cancelReason;

    public CryptoBrokerWalletModuleCustomerBrokerNegotiationInformation(CustomerBrokerNegotiationInformation data) {
        customerIdentity = data.getCustomer();
        brokerIdentity = data.getBroker();
        summary = data.getNegotiationSummary();
        clauses = data.getClauses();
        status = data.getStatus();
        note = data.getMemo();
        lastNegotiationUpdateDate = data.getLastNegotiationUpdateDate();
        expirationDatetime = data.getNegotiationExpirationDate();
        negotiationId = data.getNegotiationId();
    }

    public CryptoBrokerWalletModuleCustomerBrokerNegotiationInformation(String customerAlias, NegotiationStatus status) {

        this.customerIdentity = new CryptoBrokerWalletActorIdentity(customerAlias, new byte[0]);
        this.brokerIdentity = new CryptoBrokerWalletActorIdentity("BrokerAlias", new byte[0]);
        this.status = status;

        negotiationId = UUID.randomUUID();

        summary = new HashMap<>();
        clauses = new HashMap<>();
    }

    public CryptoBrokerWalletModuleCustomerBrokerNegotiationInformation(CustomerBrokerSaleNegotiation saleNegotiation, ActorIdentity customer, ActorIdentity broker) {

        customerIdentity = customer;
        brokerIdentity = broker;
        status = saleNegotiation.getStatus();
        note = saleNegotiation.getMemo();
        lastNegotiationUpdateDate = saleNegotiation.getLastNegotiationUpdateDate();
        expirationDatetime = saleNegotiation.getNegotiationExpirationDate();
        negotiationId = saleNegotiation.getNegotiationId();
        cancelReason = saleNegotiation.getCancelReason();

        summary = new HashMap<>();
        clauses = new HashMap<>();

        Collection<Clause> saleNegotiationClauses;
        try {
            saleNegotiationClauses = saleNegotiation.getClauses();
        } catch (CantGetListClauseException e) {
            saleNegotiationClauses = new ArrayList<>();
        }

        for(Clause clause : saleNegotiationClauses){
            clauses.put(clause.getType(), new CryptoBrokerWalletModuleClauseInformation(clause));
        }
    }

    public long getNegotiationExpirationDate() {
        return expirationDatetime;
    }

    @Override
    public void setNegotiationExpirationDate(long expirationDatetime) {
        this.expirationDatetime = expirationDatetime;
    }

    public void setExpirationDatetime(long expirationDatetime) {
        this.expirationDatetime = expirationDatetime;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public ActorIdentity getCustomer() {
        return customerIdentity;
    }

    @Override
    public ActorIdentity getBroker() {
        return brokerIdentity;
    }

    @Override
    public Map<ClauseType, String> getNegotiationSummary() {
        summary.put(ClauseType.CUSTOMER_CURRENCY_QUANTITY, clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue());
        summary.put(ClauseType.CUSTOMER_CURRENCY, clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue());
        summary.put(ClauseType.EXCHANGE_RATE, clauses.get(ClauseType.EXCHANGE_RATE).getValue());
        summary.put(ClauseType.BROKER_CURRENCY, clauses.get(ClauseType.BROKER_CURRENCY).getValue());

        return summary;
    }

    @Override
    public Map<ClauseType, ClauseInformation> getClauses() {
        return clauses;
    }

    @Override
    public NegotiationStatus getStatus() {
        return status;
    }

    @Override
    public UUID getNegotiationId() {
        return negotiationId;
    }

    @Override
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    @Override
    public String getCancelReason() {
        return cancelReason;
    }

    @Override
    public String getMemo() {
        return note;
    }

    @Override
    public void setMemo(String memo) {
        note = memo;
    }

    @Override
    public long getLastNegotiationUpdateDate() {
        return lastNegotiationUpdateDate;
    }

    @Override
    public void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate) {
        this.lastNegotiationUpdateDate = lastNegotiationUpdateDate;
    }

    public void setStatus(NegotiationStatus status) {
        this.status = status;
    }
}
