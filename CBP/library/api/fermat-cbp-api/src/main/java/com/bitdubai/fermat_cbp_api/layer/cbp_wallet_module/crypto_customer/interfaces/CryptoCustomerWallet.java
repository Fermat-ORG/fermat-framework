package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetCurrentIndexSumaryForCurrenciesOfInterestException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetNegotiationsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CouldNotStartNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerIdentityListException;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by nelson on 22/09/15.
 */
public interface CryptoCustomerWallet {

    /**
     * Return as much as "max" results from the list of Negotiation's Basic Info in this wallet waiting for the broker response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Negotiation Basic Info
     */
    Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForBroker(int max, int offset) throws CantGetNegotiationsWaitingForBrokerException;

    /**
     * Return as much as "max" results from the list of Negotiation's Basic Info in this wallet waiting for my response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Negotiation Basic Info
     */
    Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForCustomer(int max, int offset) throws CantGetNegotiationsWaitingForCustomerException;

    /**
     * return the list of brokers connected with the crypto customer
     *
     * @param customerId the crypto customer id
     * @return the list of crypto brokers
     */
    Collection<CryptoBrokerIdentity> getListOfConnectedBrokers(UUID customerId) throws CantGetCryptoBrokerListException;

    /**
     * @return list of identities associated with this wallet
     */
    Collection<CryptoCustomerIdentity> getListOfIdentities() throws CantGetCryptoCustomerIdentityListException;

    /**
     * @return a summary of the current market rate for the different currencies the customer is interested
     */
    Collection<IndexInfoSummary> getCurrentIndexSummaryForCurrenciesOfInterest() throws CantGetCurrentIndexSumaryForCurrenciesOfInterestException;

    /**
     * associate an Identity to this wallet
     *
     * @param customerId the Crypto Customer ID who is going to be associated with this wallet
     * @return true if the association was successful false otherwise
     */
    boolean associateIdentity(UUID customerId);

    /**
     * Start a new negotiation with a crypto broker
     *
     * @param customerId the crypto customer ID
     * @param brokerId   the crypto broker ID
     * @param clauses    the initial and mandatory clauses to start a negotiation
     * @return true if the association was successful false otherwise
     */
    boolean startNegotiation(UUID customerId, UUID brokerId, Collection<ClauseInformation> clauses) throws CouldNotStartNegotiationException;

    /**
     * Confirm the given negotiation to create a contract
     *
     * @param negotiation the negotiation information
     */
    CustomerBrokerNegotiationInformation confirmNegotiation(CustomerBrokerNegotiationInformation negotiation);

    /**
     * Cancel a negotiation
     *
     * @param negotiation the negotiation ID
     * @param reason      the reason to cancel
     */
    CustomerBrokerNegotiationInformation cancelNegotiation(CustomerBrokerNegotiationInformation negotiation, String reason);

    /**
     * Add a new clause to the negotiation
     *
     * @param negotiation the negotiation where is going the clause is going to be added
     * @param clause      the clause to be added
     * @return the {@link CustomerBrokerNegotiationInformation} with added clause
     */
    CustomerBrokerNegotiationInformation addClause(CustomerBrokerNegotiationInformation negotiation, ClauseInformation clause);

    /**
     * modify the data of a clause's negotiation
     *
     * @param negotiation the negotiation with the clause to be modified
     * @param clause      the modified clause
     * @return the {@link CustomerBrokerNegotiationInformation} with modified clause
     */
    CustomerBrokerNegotiationInformation changeClause(CustomerBrokerNegotiationInformation negotiation, ClauseInformation clause);
}
