package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGetCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGetListCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerClose;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerCloseManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 22.12.15.
 */
public class CustomerBrokerCloseManagerImpl implements CustomerBrokerCloseManager {

    @Override
    public void createCustomerBrokerNewPurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation)
            throws CantCreateCustomerBrokerContractPurchaseException{

    }

    @Override
    public void createCustomerBrokerNewSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation)
            throws CantCreateCustomerBrokerContractSaleException{

    }

    @Override
    public CustomerBrokerClose getCustomerBrokerNewNegotiationTranasction(UUID transactionId)
            throws CantGetCustomerBrokerCloseNegotiationTransactionException{
        return  null;
    }

    @Override
    public List<CustomerBrokerClose> getAllCustomerBrokerNewNegotiationTranasction()
            throws CantGetListCustomerBrokerCloseNegotiationTransactionException{
        return  null;
    }
}
