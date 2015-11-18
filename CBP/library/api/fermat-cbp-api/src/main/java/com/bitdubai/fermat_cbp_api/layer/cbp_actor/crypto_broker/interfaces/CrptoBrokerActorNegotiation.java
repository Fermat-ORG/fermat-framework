package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantGetListClauseException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by angel on 17/11/15.
 */
public interface CrptoBrokerActorNegotiation{

    String getCustomerPublicKey();

    String getBrokerPublicKey();

    UUID getNegotiationId();

    long getStartDate();

    NegotiationStatus getStatus();

    void setStatus(NegotiationStatus status);

    Collection<Clause> getClauses() throws CantGetListClauseException;
}
