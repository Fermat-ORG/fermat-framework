package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantUpdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.ListsForStatusSale;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerContractSaleDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Collection;

/**
 * Created by angel on 10/12/15.
 */

public class CustomerBrokerSaleManager implements CustomerBrokerContractSaleManager {

    private CustomerBrokerContractSaleDao customerBrokerContractSaleDao;
    private final ErrorManager errorManager;
    private final PluginVersionReference pluginVersionReference;

    public CustomerBrokerSaleManager(
        final CustomerBrokerContractSaleDao customerBrokerContractSaleDao,
        final ErrorManager errorManager,
        final PluginVersionReference pluginVersionReference
    ){
        this.customerBrokerContractSaleDao = customerBrokerContractSaleDao;
        this.errorManager = errorManager;
        this.pluginVersionReference = pluginVersionReference;
    }

    @Override
    public Collection<CustomerBrokerContractSale> getAllCustomerBrokerContractSale() throws CantGetListCustomerBrokerContractSaleException {
        try{
            return this.customerBrokerContractSaleDao.getAllCustomerBrokerContractSale();
        } catch (CantGetListCustomerBrokerContractSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListCustomerBrokerContractSaleException(e.getMessage(), e, "", "Failed to get records database");
        }
    }

    @Override
    public CustomerBrokerContractSale getCustomerBrokerContractSaleForContractId(String contractId) throws CantGetListCustomerBrokerContractSaleException {
        try{
            return this.customerBrokerContractSaleDao.getCustomerBrokerSaleContractForcontractID(contractId);
        } catch (CantGetListCustomerBrokerContractSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListCustomerBrokerContractSaleException(e.getMessage(), e, "", "Failed to get records database");
        }
    }

    @Override
    public Collection<CustomerBrokerContractSale> getCustomerBrokerContractSaleForStatus(ContractStatus status) throws CantGetListCustomerBrokerContractSaleException {
        try{
            return this.customerBrokerContractSaleDao.getCustomerBrokerContractSaleForStatus(status);
        } catch (CantGetListCustomerBrokerContractSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListCustomerBrokerContractSaleException(e.getMessage(), e, "", "Failed to get records database");
        }
    }

    @Override
    public ListsForStatusSale getCustomerBrokerContractHistory() throws CantGetListCustomerBrokerContractSaleException {
        try{
            return this.customerBrokerContractSaleDao.getCustomerBrokerContractHistory();
        } catch (CantGetListCustomerBrokerContractSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListCustomerBrokerContractSaleException(e.getMessage(), e, "", "Failed to get records database");
        }
    }

    @Override
    public CustomerBrokerContractSale createCustomerBrokerContractSale(CustomerBrokerContractSale contract) throws CantCreateCustomerBrokerContractSaleException {
        try{
            return this.customerBrokerContractSaleDao.createCustomerBrokerSaleContract(contract);
        } catch (CantCreateCustomerBrokerContractSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateCustomerBrokerContractSaleException(e.getMessage(), e, "", "Cant Create Customer Broker Contract Sale");
        }
    }

    @Override
    public void updateStatusCustomerBrokerSaleContractStatus(String contractId, ContractStatus status) throws CantUpdateCustomerBrokerContractSaleException {
        try{
            this.customerBrokerContractSaleDao.updateStatusCustomerBrokerSaleContract(contractId, status);
        } catch (CantUpdateCustomerBrokerContractSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerContractSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Contract Sale");
        }
    }

    @Override
    public void updateContractNearExpirationDatetime(String contractId, Boolean status) throws CantUpdateCustomerBrokerContractSaleException {
        try{
            this.customerBrokerContractSaleDao.updateContractNearExpirationDatetime(contractId, status);
        } catch (CantUpdateCustomerBrokerContractSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerContractSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Contract Sale");
        }
    }

    @Override
    public void cancelContract(String contractId, String reason)  throws CantUpdateCustomerBrokerContractSaleException {
        try{
            this.customerBrokerContractSaleDao.cancelContract(contractId, reason);
        } catch (CantUpdateCustomerBrokerContractSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerContractSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Contract Sale");
        }
    }

}
