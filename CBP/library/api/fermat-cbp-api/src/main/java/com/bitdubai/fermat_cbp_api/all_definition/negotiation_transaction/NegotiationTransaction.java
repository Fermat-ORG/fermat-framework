package com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.11.15.
 */
public interface NegotiationTransaction {

    /**
     * The method <code>getTransactionId</code> returns the transaction id of the negotiation transaction
     *
     * @return an UUID the transaction id of the negotiation transaction
     */
    UUID getTransactionId();

    /**
     * The method <code>getNegotiationId</code> returns the negotiation id of the negotiation transaction
     *
     * @return an UUID the negotiation id of the negotiation
     */
    UUID getNegotiationId();

    /**
     * The method <code>getPublicKeyBroker</code> returns the public key of the negotiation transaction
     *
     * @return an String the public key of the crypto broker
     */
    String getPublicKeyBroker();

    /**
     * The method <code>getPublicKeyCustomer</code> returns the public key of the negotiation transaction
     *
     * @return an String the public key of the crypto broker
     */
    String getPublicKeyCustomer();

    /**
     * The method <code>getStatusTransaction</code> returns the status of the negotiation transaction
     *
     * @return an NegotiationTransactionStatus the status of the negotiation transaction
     */
    NegotiationTransactionStatus getStatusTransaction();

    /**
     * The method <code>getNegotiationType</code> returns the Negotiation type of the negotiation transaction
     *
     * @return an NegotiationType the Negotiation type of the negotiation transaction
     */
    NegotiationType getNegotiationType();

    /**
     * The method <code>getNegotiationXML</code> returns the xml of the negotiation relationship with negotiation transaction
     *
     * @return an String the xml of negotiation
     */
    String getNegotiationXML();

    /**
     * The method <code>getTimestamp</code> returns the time stamp of the negotiation transaction
     *
     * @return an Long the time stamp of the negotiation transaction
     */
    long getTimestamp();
}
