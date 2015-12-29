package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSubmitMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.exceptions.CantOpenContractException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContractManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

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

    private OpenContractBusinessTransactionDao openContractBusinessTransactionDao;

    /**
     * Represents the purchase negotiation
     */
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /**
     * Represents the sale negotiation
     */
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    //FiatIndexManager fiatIndexManager;

    /**
     * Represents the negotiation ID.
     */
    //private String negotiationId;

    /**
     * Represents the transaction transmission manager
     */
    private TransactionTransmissionManager transactionTransmissionManager;

    /**
     * Represents the errorManager
     */
    private ErrorManager errorManager;

    public OpenContractTransactionManager(
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            TransactionTransmissionManager transactionTransmissionManager,
            OpenContractBusinessTransactionDao openContractBusinessTransactionDao,
            ErrorManager errorManager){

        this.customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager=customerBrokerContractSaleManager;
        this.transactionTransmissionManager=transactionTransmissionManager;
        this.openContractBusinessTransactionDao=openContractBusinessTransactionDao;
        this.errorManager=errorManager;

    }


    @Override
    public ContractTransactionStatus getOpenContractStatus(String negotiationId) throws
            UnexpectedResultReturnedFromDatabaseException {
        try{
            ObjectChecker.checkArgument(negotiationId, "The negotiationId argument is null");
            return this.openContractBusinessTransactionDao.getContractTransactionStatusByNegotiationId(negotiationId);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    "Cannot check a null contractHash/Id");
        }
    }

    @Override
    public void openSaleContract(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation,
                                 FiatIndex fiatIndex) throws CantOpenContractException{
        OpenContractBrokerContractManager openContractCustomerContractManager=new OpenContractBrokerContractManager(
                customerBrokerContractSaleManager,
                transactionTransmissionManager,
                openContractBusinessTransactionDao);
        try {
            Object[] arguments={customerBrokerSaleNegotiation, fiatIndex};
            ObjectChecker.checkArguments(arguments);
            openContractCustomerContractManager.openContract(customerBrokerSaleNegotiation, fiatIndex);
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            throw new CantOpenContractException(e,"Creating a new contract","Unexpected result from database");
        } catch (ObjectNotSetException e) {
            throw new CantOpenContractException(e,
                    "Creating Open Contract Business Transaction",
                    "Invalid input to this manager");
        }
        //openContract(negotiationId);
    }

    @Override
    public void openPurchaseContract(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation,
                                     FiatIndex fiatIndex) throws CantOpenContractException{
        OpenContractCustomerContractManager openContractCustomerContractManager =new OpenContractCustomerContractManager(
                customerBrokerContractPurchaseManager,
                transactionTransmissionManager,
                openContractBusinessTransactionDao);
        try {
            Object[] arguments={customerBrokerPurchaseNegotiation, fiatIndex};
            ObjectChecker.checkArguments(arguments);
            openContractCustomerContractManager.openContract(customerBrokerPurchaseNegotiation, fiatIndex);
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            throw new CantOpenContractException(e,"Creating a new contract","Unexpected result from database");
        } catch (ObjectNotSetException e) {
            throw new CantOpenContractException(e,
                    "Creating Open Contract Business Transaction",
                    "Invalid input to this manager");
        }

        //openContract(negotiationId);
    }

}
