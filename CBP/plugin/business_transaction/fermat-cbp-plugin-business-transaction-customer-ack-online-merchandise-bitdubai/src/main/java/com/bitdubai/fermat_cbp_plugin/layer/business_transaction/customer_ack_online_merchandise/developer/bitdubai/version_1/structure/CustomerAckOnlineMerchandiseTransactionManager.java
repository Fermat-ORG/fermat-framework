package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_online_merchandise.interfaces.CustomerAckOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.CustomerAckOnlineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDao;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/12/15.
 */
public class CustomerAckOnlineMerchandiseTransactionManager implements CustomerAckOnlineMerchandiseManager {

    /**
     * Represents the plugin database dao.
     */
    CustomerAckOnlineMerchandiseBusinessTransactionDao customerAckOnlineMerchandiseBusinessTransactionDao;

    /**
     * Represents the error manager
     */
    CustomerAckOnlineMerchandisePluginRoot pluginRoot;

    public CustomerAckOnlineMerchandiseTransactionManager(
            CustomerAckOnlineMerchandiseBusinessTransactionDao customerAckOnlineMerchandiseBusinessTransactionDao,
            CustomerAckOnlineMerchandisePluginRoot pluginRoot) {
        this.customerAckOnlineMerchandiseBusinessTransactionDao = customerAckOnlineMerchandiseBusinessTransactionDao;
        this.pluginRoot = pluginRoot;
    }

    @Override
    public ContractTransactionStatus getContractTransactionStatus(
            String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            return this.customerAckOnlineMerchandiseBusinessTransactionDao.getContractTransactionStatus(
                    contractHash);
        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    "Cannot check a null contractHash/Id");
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected Result",
                    "Check the cause");
        }

    }

    /**
     * This method returns the transaction completion date.
     * If returns 0 the transaction is processing.
     *
     * @param contractHash
     * @return
     * @throws CantGetCompletionDateException
     */
    @Override
    public long getCompletionDate(String contractHash) throws CantGetCompletionDateException {
        try {
            ObjectChecker.checkArgument(contractHash, "The contract hash argument is null");
            return this.customerAckOnlineMerchandiseBusinessTransactionDao.getCompletionDateByContractHash(
                    contractHash);
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCompletionDateException(
                    e,
                    "Getting completion date",
                    "Unexpected exception from database");
        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCompletionDateException(
                    e,
                    "Getting completion date",
                    "The contract hash argument is null");
        }
    }
}
