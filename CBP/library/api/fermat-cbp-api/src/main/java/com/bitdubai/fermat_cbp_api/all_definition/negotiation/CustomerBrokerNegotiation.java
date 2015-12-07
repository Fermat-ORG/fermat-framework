package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

/**
 * Created by jorge on 12-10-2015.
 * Update by Angel on 29/11/2015
 */

public interface CustomerBrokerNegotiation extends Negotiation {
    String getCustomerPublicKey();
    String getBrokerPublicKey();
}
