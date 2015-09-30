package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_purchase.exceptions.CantGetRequestListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantCreateBuyRequestException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetContractInformationDetailsException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetContractInformationListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CouldNotAddBrokerAsContactException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CouldNotRemoveBrokerAsContactException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CouldNotUpdateContractInformationException;

import java.util.List;
import java.util.UUID;

/**
 * Created by nelson on 22/09/15.
 */
public interface Wallet {

    /**
     * @return List of the basic info of all Buy Request and Contracts that had made a Customer in this wallet
     */
    List<NegotiationBasicInformation> getAllRequestsAndContracts() throws CantGetContractInformationListException, CantGetRequestListException;

    /**
     * Return as much as "max" results from the list of all Requests and Contracts basic info in this wallet, starting from the "offset" result
     *
     * @return a sub list contracts based on the params
     */
    List<NegotiationBasicInformation> getListOfRequestsAndContracts(int max, int offset) throws CantGetContractInformationListException, CantGetRequestListException;

    /**
     * Return as much as "max" results from the list of Requests and Contracts basic info in this wallet waiting for my response, starting from the "offset" result
     *
     * @param max
     * @param offset
     * @return
     */
    List<NegotiationBasicInformation> getListOfRequestsAndContractsWaitingForMyResponse(int max, int offset) throws CantGetContractInformationListException, CantGetRequestListException;

    /**
     * Return as much as "max" results from the list of Requests and Contracts basic info in this wallet waiting for the broker response, starting from the "offset" result
     *
     * @param max
     * @param offset
     * @return
     */
    List<NegotiationBasicInformation> getListOfRequestsAndContractsWaitingForBrokerResponse(int max, int offset) throws CantGetContractInformationListException, CantGetRequestListException;

    /**
     * @param contractId the ID of the Contract
     * @return all the details of a contract
     */
    CryptoCustomerContractInformation getContractDetails(String contractId) throws CantGetContractInformationDetailsException;

    /**
     * Update the information of the contract
     *
     * @param information the information of the contract
     */
    void updateContractInformation(CryptoCustomerContractInformation information) throws CouldNotUpdateContractInformationException;

    /**
     * @return the Amount and Type of Crypto Currency avaliable for this wallet
     */
    CryptoCurrencyInformation getAvailableBalance();

    /**
     * @return the list of brokers that connected with me and I set has contact for this wallet
     */
    List<CryptoBrokerIdentity> getListOfContacts() throws CantGetCryptoBrokerListException;

    /**
     * @return the list of all the brokers that has connected with me and are outside my list of contacts
     */
    List<CryptoBrokerIdentity> getListOfConnectedBrokers() throws CantGetCryptoBrokerListException;

    /**
     * Add a broker as a contact to this wallet
     *
     * @param brokerId the ID of broker selected from the list of brokers that connected with me
     */
    void addContact(UUID brokerId) throws CouldNotAddBrokerAsContactException;

    /**
     * Remove a broker from the list of contacts of this wallet
     *
     * @param brokerId the ID of the broker to delete
     */
    void removeBrokerFromContacts(UUID brokerId) throws CouldNotRemoveBrokerAsContactException;

    /**
     * Create a Buy Request to a Crypto Broker
     *
     * @param merchandise the merchandise to buy
     * @param amount      the amount of that merchandise
     * @param brokerId    the ID of the broker who is going to recive the request
     */
    void createBuyRequest(String merchandise, float amount, UUID brokerId) throws CantCreateBuyRequestException;

    /**
     * Fire a notification send from the broker to this wallet
     */
    void fireNotification();

    /**
     * associate an Identity to this wallet
     *
     * @param customerId the Crypto Customer ID who is going to be associated with this wallet
     */
    void associateIdentity(String customerId);

    /**
     * @return list of identities associated with this wallet
     */
    List<WalletModuleCryptoCustomer> getListOfIdentities() throws CantGetCryptoCustomerIdentityListException;
}
