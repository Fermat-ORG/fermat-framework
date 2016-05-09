package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.ListsForStatusPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerContractPurchaseDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Collection;

/**
 * Created by angel on 8/12/15.
 */
public class CustomerBrokerPurchaseManager implements CustomerBrokerContractPurchaseManager {

    private final CustomerBrokerContractPurchaseDao customerBrokerContractPurchaseDao;
    private final ErrorManager errorManager;
    private final PluginVersionReference pluginVersionReference;

    public CustomerBrokerPurchaseManager(
            final CustomerBrokerContractPurchaseDao customerBrokerContractPurchaseDao,
            final ErrorManager errorManager,
            final PluginVersionReference pluginVersionReference
    ){
        this.customerBrokerContractPurchaseDao = customerBrokerContractPurchaseDao;
        this.errorManager = errorManager;
        this.pluginVersionReference = pluginVersionReference;
    }

    @Override
    public Collection<CustomerBrokerContractPurchase> getAllCustomerBrokerContractPurchase() throws CantGetListCustomerBrokerContractPurchaseException {
        try{
            return this.customerBrokerContractPurchaseDao.getAllCustomerBrokerContractPurchase();
        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListCustomerBrokerContractPurchaseException(e.getMessage(), e, "", "Failed to get records database");
        }
    }

    @Override
    public CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseForContractId(String ContractId) throws CantGetListCustomerBrokerContractPurchaseException {
        try{
            return this.customerBrokerContractPurchaseDao.getCustomerBrokerPurchaseContractForcontractID(ContractId);
        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListCustomerBrokerContractPurchaseException(e.getMessage(), e, "", "Failed to get records database");
        }
    }

    @Override
    public Collection<CustomerBrokerContractPurchase> getCustomerBrokerContractPurchaseForStatus(ContractStatus status) throws CantGetListCustomerBrokerContractPurchaseException {
        try{
            return this.customerBrokerContractPurchaseDao.getCustomerBrokerContractPurchaseForStatus(status);
        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListCustomerBrokerContractPurchaseException(e.getMessage(), e, "", "Failed to get records database");
        }
    }

    @Override
    public ListsForStatusPurchase getCustomerBrokerContractHistory() throws CantGetListCustomerBrokerContractPurchaseException{
        try{
            return this.customerBrokerContractPurchaseDao.getCustomerBrokerContractHistory();
        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListCustomerBrokerContractPurchaseException(e.getMessage(), e, "", "Failed to get records database");
        }
    }

    @Override
    public CustomerBrokerContractPurchase createCustomerBrokerContractPurchase(CustomerBrokerContractPurchase contract) throws CantCreateCustomerBrokerContractPurchaseException {
        try{
            return this.customerBrokerContractPurchaseDao.createCustomerBrokerPurchaseContract(contract);
        } catch (CantCreateCustomerBrokerContractPurchaseException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateCustomerBrokerContractPurchaseException(e.getMessage(), e, "", "Cant Create Customer Broker Contract Purchase");
        }
    }

    @Override
    public void updateStatusCustomerBrokerPurchaseContractStatus(String contractId, ContractStatus status) throws CantUpdateCustomerBrokerContractPurchaseException {
        try{
            this.customerBrokerContractPurchaseDao.updateStatusCustomerBrokerPurchaseContract(contractId, status);
        } catch (CantUpdateCustomerBrokerContractPurchaseException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerContractPurchaseException(e.getMessage(), e, "", "Cant Update Customer Broker Contract Purchase");
        }
    }

    @Override
    public void updateContractNearExpirationDatetime(String contractId, Boolean status) throws CantUpdateCustomerBrokerContractPurchaseException {
        try{
            this.customerBrokerContractPurchaseDao.updateContractNearExpirationDatetime(contractId, status);
        } catch (CantUpdateCustomerBrokerContractPurchaseException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerContractPurchaseException(e.getMessage(), e, "", "Cant Update Customer Broker Contract Purchase");
        }
    }

    //ADD YORDIN ALAYN 07.04.16
    @Override
    public void cancelContract(String contractId, String reason)  throws CantUpdateCustomerBrokerContractPurchaseException {
        try{
            this.customerBrokerContractPurchaseDao.cancelContract(contractId, reason);
        } catch (CantUpdateCustomerBrokerContractPurchaseException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerContractPurchaseException(e.getMessage(), e, "", "Cant Update Customer Broker Contract Purchase");
        }
    }

}
