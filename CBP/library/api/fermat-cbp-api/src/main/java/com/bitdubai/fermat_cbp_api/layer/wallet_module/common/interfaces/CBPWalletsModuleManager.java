package com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListLocationsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListLocationsSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCancelNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractHistoryException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationInformationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CouldNotCancelNegotiationException;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by nelson on 21/11/15.
 */
public interface CBPWalletsModuleManager<T extends FermatSettings, S extends ActiveActorIdentityInformation>
        extends ModuleManager<T, S>, ModuleSettingsImpl<T>, Serializable {

    /**
     * Cancel a current negotiation
     *
     * @param negotiation the negotiation to cancel
     * @param reason      the reason to cancel
     */
    CustomerBrokerNegotiationInformation cancelNegotiation(CustomerBrokerNegotiationInformation negotiation, String reason) throws CouldNotCancelNegotiationException, CantCancelNegotiationException;

    /**
     * Return as much as "max" results from the list of Contract Basic Info in this wallet,
     * starting from the "offset" result given the "status" I summit
     *
     * @param status the contract status
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Contract Basic Info
     */
    Collection<ContractBasicInformation> getContractsHistory(ContractStatus status, int max, int offset) throws CantGetContractHistoryException, CantGetListCustomerBrokerContractPurchaseException;

    /**
     * Return as much as "max" results from the list of Contract Basic Info in this wallet waiting for my response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Contract Basic Info
     */
    Collection<ContractBasicInformation> getContractsWaitingForBroker(int max, int offset) throws CantGetContractsWaitingForBrokerException;

    /**
     * Return as much as "max" results from the list of Contract Basic Info in this wallet waiting for the Customer's response,
     * starting from the "offset" result
     *
     * @param max    the max quantity of results
     * @param offset the start point for the results
     * @return the list of Contract Basic Info
     */
    Collection<ContractBasicInformation> getContractsWaitingForCustomer(int max, int offset) throws CantGetContractsWaitingForCustomerException;

    /**
     * Returns a Collection of the clauses of a negotiation, as per given by the customerBrokerPurchaseNegotiationManager
     *
     * @param negotiationId the ID of the negotiation
     */
    Collection<Clause> getNegotiationClausesFromNegotiationId(UUID negotiationId) throws CantGetListClauseException;

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
     * Return the negotiation information for the given ID
     *
     * @param negotiationID the negotiation's ID
     * @return the negotiation information
     */
    CustomerBrokerNegotiationInformation getNegotiationInformation(UUID negotiationID) throws CantGetNegotiationInformationException;

    /**
     * @return Collection<NegotiationLocations>
     */
    Collection<NegotiationLocations> getAllLocations(NegotiationType negotiationType) throws CantGetListLocationsSaleException, CantGetListLocationsPurchaseException;


    /**
     * Returns a completionDate in which a specific status was achieved for a specific contract
     *
     * @param contractHash
     * @param contractStatus
     * @param paymentMethod
     * @return a completionDate in which a specific status was achieved for a specific contract
     */
    long getCompletionDateForContractStatus(String contractHash, ContractStatus contractStatus, String paymentMethod);

}
