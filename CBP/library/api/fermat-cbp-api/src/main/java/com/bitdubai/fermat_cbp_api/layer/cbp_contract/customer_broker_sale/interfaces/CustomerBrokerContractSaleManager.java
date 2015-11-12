package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantDeleteCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantupdateCustomerBrokerContractSaleException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerContractSaleManager {

    List<CustomerBrokerContractSale> getAllCustomerBrokerContractSaleFromCurrentDeviceUser() throws CantGetListCustomerBrokerContractSaleException;

    CustomerBrokerContractSale getCustomerBrokerContractSaleForContractId(final UUID ContractId) throws CantGetListCustomerBrokerContractSaleException;

    DatabaseTableRecord getCustomerBrokerContractSaleTable();

    public CustomerBrokerContractSale createCustomerBrokerContractSale(
            String publicKeyCustomer,
            String publicKeyBroker,
            Float merchandiseAmount,
            CurrencyType merchandiseCurrency,
            Float referencePrice,
            ReferenceCurrency referenceCurrency,
            Float paymentAmount,
            CurrencyType paymentCurrency,
            long paymentExpirationDate,
            long merchandiseDeliveryExpirationDate) throws CantCreateCustomerBrokerContractSaleException;

    public void updateCustomerBrokerContractSale(UUID contractId, ContractStatus status) throws CantupdateCustomerBrokerContractSaleException;

    void deleteCustomerBrokerContractSale(UUID contractID) throws CantDeleteCustomerBrokerContractSaleException;
}
