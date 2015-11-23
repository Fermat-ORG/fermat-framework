package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.WalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCurrentIndexSummaryForCurrenciesOfInterestException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CouldNotStartNegotiationException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by nelson on 22/09/15.
 */
public interface CryptoCustomerWallet extends WalletManager {

    /**
     * associate an Identity to this wallet
     *
     * @param customerId the Crypto Customer ID who is going to be associated with this wallet
     * @return true if the association was successful false otherwise
     */
    boolean associateIdentity(UUID customerId);

    /**
     * Cancel a negotiation
     *
     * @param negotiation the negotiation ID
     * @param reason      the reason to cancel
     */
    CustomerBrokerNegotiationInformation cancelNegotiation(CustomerBrokerNegotiationInformation negotiation, String reason);

    /**
     * Confirm the given negotiation to create a contract
     *
     * @param negotiation the negotiation information
     */
    CustomerBrokerNegotiationInformation confirmNegotiation(CustomerBrokerNegotiationInformation negotiation);

    /**
     * @return a summary of the current market rate for the different currencies the customer is interested
     */
    Collection<IndexInfoSummary> getCurrentIndexSummaryForCurrenciesOfInterest() throws CantGetCurrentIndexSummaryForCurrenciesOfInterestException;

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
     * Start a new negotiation with a crypto broker
     *
     * @param customerId the crypto customer ID
     * @param brokerId   the crypto broker ID
     * @param clauses    the initial and mandatory clauses to start a negotiation
     * @return true if the association was successful false otherwise
     */
    boolean startNegotiation(UUID customerId, UUID brokerId, Collection<ClauseInformation> clauses) throws CouldNotStartNegotiationException;
}
