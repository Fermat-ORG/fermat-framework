package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_online_payment.interfaces.BrokerAckOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/12/15.
 */
public class BrokerAckOnlinePaymentTransactionManager implements BrokerAckOnlinePaymentManager {

    /**
     * Represents the plugin database DAO
     */
    BrokerAckOnlinePaymentBusinessTransactionDao brokerAckOnlinePaymentBusinessTransactionDao;

    /**
     * Represents the error manager
     */
    ErrorManager errorManager;

    public BrokerAckOnlinePaymentTransactionManager(
            BrokerAckOnlinePaymentBusinessTransactionDao brokerAckOnlinePaymentBusinessTransactionDao,
            ErrorManager errorManager){
        this.brokerAckOnlinePaymentBusinessTransactionDao=brokerAckOnlinePaymentBusinessTransactionDao;
        this.errorManager=errorManager;

    }

    /**
     * This method returns the actual ContractTransactionStatus from a Broker Ack Online Payment
     * Business Transaction by a contract hash/Id.
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    @Override
    public ContractTransactionStatus getContractTransactionStatus(
            String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try{
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            return this.brokerAckOnlinePaymentBusinessTransactionDao.getContractTransactionStatus(
                    contractHash);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    "Cannot check a null contractHash/Id");
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Unexpected Result",
                    "Check the cause");
        }
    }

    /**
     * This method returns the transaction completion date.
     * If returns 0 the transaction is processing.
     * @param contractHash
     * @return
     * @throws CantGetCompletionDateException
     */
    @Override
    public long getCompletionDate(String contractHash) throws CantGetCompletionDateException {

        try{
            ObjectChecker.checkArgument(contractHash, "The contract hash argument is null");
            return this.brokerAckOnlinePaymentBusinessTransactionDao.getCompletionDateByContractHash(
                    contractHash);
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCompletionDateException(
                    e,
                    "Getting completion date",
                    "Unexpected exception from database");
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCompletionDateException(
                    e,
                    "Getting completion date",
                    "The contract hash argument is null");
        }
    }
}
