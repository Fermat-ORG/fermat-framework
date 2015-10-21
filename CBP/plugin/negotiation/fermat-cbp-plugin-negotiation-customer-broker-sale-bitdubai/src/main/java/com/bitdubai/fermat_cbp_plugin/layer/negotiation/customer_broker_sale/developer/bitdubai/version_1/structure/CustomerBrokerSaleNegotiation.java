package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.interfaces.CustomerBrokerSale;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by angel on 19/10/15.
 */
public class CustomerBrokerSaleNegotiation implements CustomerBrokerSale {

    private UUID   negotiationId;
    private String publicKeyCustomer;
    private String publicKeyBroker;
    private long   startDataTime;
    private String statusNegotiation;

    public CustomerBrokerSaleNegotiation(
            UUID negotiationId,
            String publicKeyCustomer,
            String publicKeyBroker,
            long startDataTime,
            String statusNegotiation
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
    public NegotiationStatus getStatus() throws InvalidParameterException {
        return NegotiationStatus.getByCode(this.statusNegotiation);
    }

    @Override
    public Collection<Clause> getClauses() {
        return null;
    }
}
