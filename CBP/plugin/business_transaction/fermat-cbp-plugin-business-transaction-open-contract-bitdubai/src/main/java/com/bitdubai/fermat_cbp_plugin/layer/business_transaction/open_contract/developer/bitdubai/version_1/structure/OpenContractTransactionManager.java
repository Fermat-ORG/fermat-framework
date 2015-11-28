package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.OpenContractStatus;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContractManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.TransactionTransmissionManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/11/15.
 */
public class OpenContractTransactionManager implements OpenContractManager{

    /**
     * Represents the contract Type, this type will use to select the actors involved in the
     * contract generation and the network service communication
     */
    ContractType contractType;

    /**
     * Represents the purchase contract
     */
    private CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;

    /**
     * Represents the sale contract
     */
    private CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    /**
     * Represents the purchase negotiation
     */
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /**
     * Represents the sale negotiation
     */
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    /**
     * Represents the negotiation ID.
     */
    //private String negotiationId;

    /**
     * Represents the transaction transmission manager
     */
    private TransactionTransmissionManager transactionTransmissionManager;

    public OpenContractTransactionManager(
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            TransactionTransmissionManager transactionTransmissionManager){

        customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        customerBrokerContractSaleManager=customerBrokerContractSaleManager;
        customerBrokerPurchaseNegotiationManager=customerBrokerPurchaseNegotiationManager;
        customerBrokerSaleNegotiationManager=customerBrokerSaleNegotiationManager;
        transactionTransmissionManager=transactionTransmissionManager;

    }


    @Override
    public OpenContractStatus getOpenContractStatus(String negotiationId) {
        return null;
    }

    private boolean isNegotiationClosed(String negotiationId){
        switch (this.contractType){

            case PURCHASE:
                //TODO: to implement
                //customerBrokerPurchaseNegotiationManager.getNegotiations(NegotiationStatus.CLOSED);
                break;
            case SALE:
                //TODO: to implement
                break;

        }
        return false;
    }

    private void openContract(String negotiationId){

    }

    @Override
    public void openSaleContract(String negotiationId) {
        contractType=contractType.SALE;
        openContract(negotiationId);
    }

    @Override
    public void openPurchaseContract(String negotiationId) {
        contractType=contractType.PURCHASE;
        openContract(negotiationId);
    }

}
