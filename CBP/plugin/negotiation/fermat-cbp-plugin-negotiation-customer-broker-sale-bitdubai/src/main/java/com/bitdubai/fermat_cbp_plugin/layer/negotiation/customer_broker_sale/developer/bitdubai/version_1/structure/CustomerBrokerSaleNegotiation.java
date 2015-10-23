package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.interfaces.CustomerBrokerSale;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantAddNewClausesException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantUpdateClausesException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by angel on 19/10/15.
 */
public class CustomerBrokerSaleNegotiation implements CustomerBrokerSale {

    private final UUID   negotiationId;
    private final String publicKeyCustomer;
    private final String publicKeyBroker;
    private final long   startDataTime;
    private NegotiationStatus statusNegotiation;

    public CustomerBrokerSaleNegotiation(
            UUID negotiationId,
            String publicKeyCustomer,
            String publicKeyBroker,
            long startDataTime,
            NegotiationStatus statusNegotiation
    ){
        this.negotiationId = negotiationId;
        this.publicKeyCustomer = publicKeyCustomer;
        this.publicKeyBroker = publicKeyBroker;
        this.startDataTime = startDataTime;
        this.statusNegotiation = statusNegotiation;
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

    }

    @Override
    public Collection<Clause> getClauses() {
        return null;
    }

    @Override
    public Clause addNewBrokerClause(ClauseType type, String value) throws CantAddNewClausesException {
        return null;
    }

    @Override
    public Clause addNewCustomerClause(ClauseType type, String value) throws CantAddNewClausesException {
        return null;
    }

    @Override
    public Clause modifyClause(Clause clause, String value) {
        return null;
    }

    @Override
    public Clause modifyClauseStatus(Clause clause, ClauseStatus status) throws CantUpdateClausesException {
        return null;
    }

    @Override
    public ClauseType getNextClauseType() {
        return null;
    }
}