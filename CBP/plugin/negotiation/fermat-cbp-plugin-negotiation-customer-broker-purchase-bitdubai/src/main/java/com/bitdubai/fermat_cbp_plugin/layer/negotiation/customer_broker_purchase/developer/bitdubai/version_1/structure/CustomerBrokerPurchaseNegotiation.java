package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseNegotiationDao;

import java.util.Collection;
import java.util.UUID;

/**
 *  Created by angel on 19/10/15.
 */

public class CustomerBrokerPurchaseNegotiation implements CustomerBrokerPurchase {

    private final UUID   negotiationId;
    private final String publicKeyCustomer;
    private final String publicKeyBroker;
    private final long   startDataTime;
    private NegotiationStatus statusNegotiation;

    private CustomerBrokerPurchaseNegotiationDao customerBrokerPurchaseNegotiationDao;

    public CustomerBrokerPurchaseNegotiation(
            UUID   negotiationId,
            String publicKeyCustomer,
            String publicKeyBroker,
            long startDataTime,
            NegotiationStatus statusNegotiation,
            CustomerBrokerPurchaseNegotiationDao customerBrokerPurchaseNegotiationDao
    ){
        this.negotiationId = negotiationId;
        this.publicKeyCustomer = publicKeyCustomer;
        this.publicKeyBroker = publicKeyBroker;
        this.startDataTime = startDataTime;
        this.statusNegotiation = statusNegotiation;
        this.customerBrokerPurchaseNegotiationDao = customerBrokerPurchaseNegotiationDao;
    }

    @Override
    public String getCustomerPublicKey() {
        return this.publicKeyCustomer;
    }

    @Override
    public String getBrokerPublicKey() {
        return this.publicKeyBroker;
    }

    @Override
    public UUID getNegotiationId() {
        return this.negotiationId;
    }

    @Override
    public long getStartDate() {
        return this.startDataTime;
    }

    @Override
    public NegotiationStatus getStatus() {
        return this.statusNegotiation;
    }

    @Override
    public void setStatus(NegotiationStatus status) {
        this.statusNegotiation = status;
    }

    @Override
    public Collection<Clause> getClauses(){
        return this.customerBrokerPurchaseNegotiationDao.getClauses(this.negotiationId);
    }

    @Override
    public Clause addNewBrokerClause(ClauseType type, String value) {
        return this.customerBrokerPurchaseNegotiationDao.addNewClause(this.negotiationId, type, value, this.getBrokerPublicKey());
    }

    @Override
    public Clause addNewCustomerClause(ClauseType type, String value) {
        return this.customerBrokerPurchaseNegotiationDao.addNewClause(this.negotiationId, type, value, this.getCustomerPublicKey());
    }

    @Override
    public Clause modifyClause(Clause clause, String value) {
        return this.customerBrokerPurchaseNegotiationDao.modifyClause(this.negotiationId, clause, value);
    }

    @Override
    public Clause modifyClauseStatus(Clause clause, ClauseStatus status) {
        return this.customerBrokerPurchaseNegotiationDao.modifyClauseStatus(this.negotiationId, clause, status);
    }

    @Override
    public ClauseType getNextClauseType() {
        return this.customerBrokerPurchaseNegotiationDao.getNextClauseType(this.negotiationId);
    }
}
