package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.exceptions.CantOpenContractException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.AbstractOpenContract;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractPurchaseRecord;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.OpenContractPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDao;

import java.util.Collection;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/11/15.
 */
public class OpenContractCustomerContractManager extends AbstractOpenContract {

    /**
     * Represents the purchase contract
     */
    private CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    private OpenContractPluginRoot pluginRoot;
    OpenContractBusinessTransactionDao openContractBusinessTransactionDao;

    /**
     * Represents the purchase negotiation
     */
    //private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /**
     * Represents the Fiat index.
     */
    //private FiatIndexManager fiatIndexManager;

    /**
     * Represents the transaction transmission manager
     */
    private TransactionTransmissionManager transactionTransmissionManager;

    public OpenContractCustomerContractManager(CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
                                               TransactionTransmissionManager transactionTransmissionManager,
                                               OpenContractBusinessTransactionDao openContractBusinessTransactionDao,
                                               OpenContractPluginRoot pluginRoot) {

        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.openContractBusinessTransactionDao = openContractBusinessTransactionDao;
        this.pluginRoot = pluginRoot;
    }


    public void openContract(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation,
                             float referencePrice) throws
            CantOpenContractException,
            UnexpectedResultReturnedFromDatabaseException {

        contractType = ContractType.PURCHASE;
        try {
            //CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation= findPurchaseNegotiation(negotiationId);
            Collection<Clause> negotiationClauses = customerBrokerPurchaseNegotiation.getClauses();
            ContractPurchaseRecord contractRecord = createPurchaseContractRecord(
                    negotiationClauses,
                    customerBrokerPurchaseNegotiation,
                    referencePrice);
            /*TODO: INICIAR COMO pausado el estado del contrato (la open contract business transaction es la responsable de iniciar el contrato en
              TODO: PENDING_PAYMENT una vez se haya validado el hash y los datos del contrato*/
//            contractRecord.setStatus(ContractStatus.PENDING_PAYMENT);
            contractRecord.setStatus(ContractStatus.PAUSED);
            this.openContractBusinessTransactionDao.persistContractRecord(
                    contractRecord,
                    contractType);
            customerBrokerContractPurchaseManager.createCustomerBrokerContractPurchase(contractRecord);
            this.openContractBusinessTransactionDao.updateContractTransactionStatus(
                    contractRecord.getContractId(),
                    ContractTransactionStatus.PENDING_SUBMIT);
        } catch (CantGetListClauseException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantOpenContractException(exception,
                    "Opening a new contract",
                    "Cannot get the negotiation clauses list");
        } catch (InvalidParameterException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantOpenContractException(exception,
                    "Opening a new contract",
                    "An invalid parameter has detected");
        } catch (CantGetIndexException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantOpenContractException(exception,
                    "Opening a new contract",
                    "Cannot get the fiat index");
        } catch (CantInsertRecordException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantOpenContractException(exception,
                    "Opening a new contract",
                    "Cannot insert the contract record in database");
        } catch (CantCreateCustomerBrokerContractPurchaseException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantOpenContractException(exception,
                    "Opening a new contract",
                    "Cannot create the CustomerBrokerContractPurchase");
        } catch (CantUpdateRecordException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Opening a new contract",
                    "Cannot update ContractTransactionStatus");
        } catch (Exception exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Opening a new contract",
                    "Unexpected Result");
        }

    }

}
