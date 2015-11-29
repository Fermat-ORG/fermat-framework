package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.OpenContractStatus;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.exceptions.CantOpenContractException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContractManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndexManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/11/15.
 */
public class OpenContractTransactionManager implements OpenContractManager{

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

    FiatIndexManager fiatIndexManager;

    /**
     * Represents the negotiation ID.
     */
    //private String negotiationId;

    /**
     * Represents the transaction transmission manager
     */
    private TransactionTransmissionManager transactionTransmissionManager;

    public OpenContractTransactionManager(
            //CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            //CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            //CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
            //CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            FiatIndexManager fiatIndexManager,
            TransactionTransmissionManager transactionTransmissionManager){

        this.customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager=customerBrokerContractSaleManager;
        this.customerBrokerPurchaseNegotiationManager=customerBrokerPurchaseNegotiationManager;
        this.customerBrokerSaleNegotiationManager=customerBrokerSaleNegotiationManager;
        this.fiatIndexManager=fiatIndexManager;
        this.transactionTransmissionManager=transactionTransmissionManager;

    }


    @Override
    public OpenContractStatus getOpenContractStatus(String negotiationId) {
        return null;
    }

    @Override
    public void openSaleContract(String negotiationId) throws CantOpenContractException{
        OpenContractBrokerContractManager openContractCustomerContractManager=new OpenContractBrokerContractManager(
                customerBrokerContractSaleManager,
                customerBrokerSaleNegotiationManager,
                fiatIndexManager,
                transactionTransmissionManager);
        openContractCustomerContractManager.openContract(negotiationId);
        //openContract(negotiationId);
    }

    @Override
    public void openPurchaseContract(String negotiationId) throws CantOpenContractException{
        OpenContractCustomerContractManager openContractCustomerContractManager =new OpenContractCustomerContractManager(
                customerBrokerContractPurchaseManager,
                customerBrokerPurchaseNegotiationManager,
                fiatIndexManager,
                transactionTransmissionManager);
        openContractCustomerContractManager.openContract(negotiationId);

        //openContract(negotiationId);
    }

}
