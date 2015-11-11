package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantDeleteCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantupdateCustomerBrokerSaleException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerSaleContractManager {

    List<CustomerBrokerSale> getAllCustomerBrokerSaleFromCurrentDeviceUser() throws CantGetListCustomerBrokerSaleException;

    CustomerBrokerSale getCustomerBrokerSaleForContractId(final UUID ContractId) throws CantGetListCustomerBrokerSaleException;

    DatabaseTableRecord getCustomerBrokerSaleContractTable();

    public CustomerBrokerSale createCustomerBrokerSale(
            String publicKeyCustomer,
            String publicKeyBroker,
            Float merchandiseAmount,
            CurrencyType merchandiseCurrency,
            Float referencePrice,
            ReferenceCurrency referenceCurrency,
            Float paymentAmount,
            CurrencyType paymentCurrency,
            long paymentExpirationDate,
            long merchandiseDeliveryExpirationDate) throws CantCreateCustomerBrokerSaleException;

    public void updateCustomerBrokerSale(UUID contractId, ContractStatus status) throws CantupdateCustomerBrokerSaleException;

    void deleteCustomerBrokerSale(UUID contractID) throws CantDeleteCustomerBrokerSaleException;
}
