package com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.exceptions.CantCloseContractException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.interfaces.CloseContractManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.exceptions.CantOpenContractException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContractManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.common.enums.TransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.interfaces.CustomerBrokerPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.database.UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.exceptions.MissingCustomerBrokerPurchaseDataException;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.utils.CustomerBrokerPurchaseImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * The Class <code>UserLevelBusinessTransactionCustomerBrokerPurchaseMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 11.12.15
 */
public class UserLevelBusinessTransactionCustomerBrokerPurchaseMonitorAgent implements Agent {
    //TODO: Documentar y manejo de excepciones.

    private Thread agentThread;
    private final ErrorManager errorManager;
    private final CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;
    private final UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao;
    private final OpenContractManager openContractManager;
    private final CloseContractManager closeContractManager;
    private final CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;

    public UserLevelBusinessTransactionCustomerBrokerPurchaseMonitorAgent(ErrorManager errorManager,
                                                                          CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
                                                                          PluginDatabaseSystem pluginDatabaseSystem,
                                                                          UUID pluginId,
                                                                          OpenContractManager openContractManager,
                                                                          CloseContractManager closeContractManager,
                                                                          CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager) {

        this.errorManager                                                  = errorManager;
        this.customerBrokerPurchaseNegotiationManager                      = customerBrokerPurchaseNegotiationManager;
        this.openContractManager                                           = openContractManager;
        this.closeContractManager                                          = closeContractManager;
        this.customerBrokerContractPurchaseManager                         = customerBrokerContractPurchaseManager;
        this.userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao = new UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao(pluginDatabaseSystem, pluginId);
    }
    @Override
    public void start() throws CantStartAgentException {
        Logger LOG = Logger.getGlobal();
        LOG.info("Customer Broker Purchase monitor agent starting");

        final MonitorAgent monitorAgent = new MonitorAgent(errorManager);

        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
    private final class MonitorAgent implements Runnable {

        private final ErrorManager errorManager;
        public final int SLEEP_TIME = 5000;
        int iterationNumber = 0;
        boolean threadWorking;
        CustomerBrokerPurchaseImpl customerBrokerPurchase = null;
        UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao;

        public MonitorAgent(final ErrorManager errorManager) {

            this.errorManager = errorManager;
        }

        @Override
        public void run() {
            threadWorking = true;
            while (threadWorking) {
                /**
                 * Increase the iteration counter
                 */
                iterationNumber++;
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    return;
                }

                /**
                 * now I will check if there are pending transactions to raise the event
                 */
                try {
                    doTheMainTask();
                } catch (Exception e) {
                    errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_PURCHASE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }

            }
        }

        private void doTheMainTask(){
            try {
                //Se verifica el cierre de la negociacion
                for (CustomerBrokerPurchaseNegotiation records : customerBrokerPurchaseNegotiationManager.getNegotiationsByStatus(NegotiationStatus.CLOSED))
                {
                    //Buscar que la transaccion no se encuentre ya registrada
                    if(userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(null).isEmpty()) {
                        customerBrokerPurchase = new CustomerBrokerPurchaseImpl(records.getNegotiationId().toString(),
                                records.getNegotiationId().toString(),
                                0, null, null, TransactionStatus.IN_PROCESS, null, null, null);

                        userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.saveCustomerBrokerPurchaseTransactionData(customerBrokerPurchase);
                    }
                }
                //Se crea la business transaction
                for(CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(null)) //IN_PROCESS
                {
                    CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerPurchase.getTransactionId()));
                    //Registra el Open Contract siempre y cuando el Transaction_Status de la Transaction Customer Broker Purchase este IN_PROCESS
                    openContractManager.openPurchaseContract(customerBrokerPurchaseNegotiation, null);
                    //Actualiza el Transaction_Status de la Transaction Customer Broker Purchase a IN_OPEN_CONTRACT
                    customerBrokerPurchase.setTransactionStatus(TransactionStatus.IN_OPEN_CONTRACT);
                    userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.saveCustomerBrokerPurchaseTransactionData(customerBrokerPurchase);
                }
                for(CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(null)) //IN_OPEN_CONTRACT
                {
                    for(CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.PENDING_PAYMENT))
                    {
                        if (customerBrokerPurchase.getTransactionId() == customerBrokerContractPurchase.getNegotiatiotId())
                        {
                            customerBrokerPurchase.setTransactionStatus(TransactionStatus.IN_CONTRACT_SUBMIT);
                            userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.saveCustomerBrokerPurchaseTransactionData(customerBrokerPurchase);
                        }
                    }
                }
                //Se verifica las condiciones del contrato y el status
                for(CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(null)) //IN_CONTRACT_SUBMIT
                {
                    for(CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.PENDING_PAYMENT))
                    {
                        if (customerBrokerPurchase.getTransactionId() == customerBrokerContractPurchase.getNegotiatiotId())
                        {
                            //Si la fecha del contracto se acerca al dia y 2 horas antes de vencerse debo de elevar un evento de notificacion siempre y cuando el ContractStatus sea igual a PENDING_PAYMENT

                        }
                    }
                }
                //Se sigue verificando el estatus del contrato hasta que se consiga la realización de un pago
                for(CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(null)) //IN_CONTRACT_SUBMIT
                {
                    for(CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.PAYMENT_SUBMIT))
                    {
                        if (customerBrokerPurchase.getTransactionId() == customerBrokerContractPurchase.getNegotiatiotId())
                        {
                            //Si se detecta la realización de un pago se procede actulizar el estatus de la transacción y a monitorear la llegada de la mercadería.
                            customerBrokerPurchase.setTransactionStatus(TransactionStatus.IN_PAYMENT_SUBMIT);
                            userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.saveCustomerBrokerPurchaseTransactionData(customerBrokerPurchase);
                        }
                    }
                }
                for(CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(null)) //IN_PAYMENT_SUBMIT
                {
                    for(CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.PENDING_MERCHANDISE))
                    {
                        if (customerBrokerPurchase.getTransactionId() == customerBrokerContractPurchase.getNegotiatiotId())
                        {
                            customerBrokerPurchase.setTransactionStatus(TransactionStatus.IN_PENDING_MERCHANDISE);
                            userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.saveCustomerBrokerPurchaseTransactionData(customerBrokerPurchase);
                        }
                    }
                }
                for(CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(null)) //IN_PENDING_MERCHANDISE
                {
                    for(CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.PENDING_MERCHANDISE))
                    {
                        if (customerBrokerPurchase.getTransactionId() == customerBrokerContractPurchase.getNegotiatiotId())
                        {
                            //Si se acerca la tiempo límite para recibir la mercadería y esta no ha sido registrada como recibida, se eleva un evento de notificación
                        }
                    }
                }
                for(CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(null)) //IN_PENDING_MERCHANDISE
                {

                    for(CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.MERCHANDISE_SUBMIT))
                    {
                        if (customerBrokerPurchase.getTransactionId() == customerBrokerContractPurchase.getNegotiatiotId())
                        {
                            customerBrokerPurchase.setTransactionStatus(TransactionStatus.IN_MERCHANDISE_SUBMIT);
                            userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.saveCustomerBrokerPurchaseTransactionData(customerBrokerPurchase);
                        }
                    }
                }
                for(CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(null)) //IN_MERCHANDISE_SUBMIT
                {
                    //Comienzo a recorrer todas las transacciones que esten en Transaction_Status IN_MERCHANDISE_SUBMIT
                    //Registra el Close Contract siempre y cuando el Transaction_Status de la Transaction Customer Broker Purchase este IN_MERCHANDISE_SUBMIT
                    for(CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.MERCHANDISE_SUBMIT))
                    {
                        if (customerBrokerPurchase.getTransactionId() == customerBrokerContractPurchase.getNegotiatiotId())
                        {
                            closeContractManager.closePurchaseContract(customerBrokerContractPurchase.getContractId());
                        }
                    }
                }
                for(CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(null)) //IN_MERCHANDISE_SUBMIT
                {
                    for(CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.COMPLETED))
                    {
                        customerBrokerPurchase.setTransactionStatus(TransactionStatus.COMPLETED);
                        userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.saveCustomerBrokerPurchaseTransactionData(customerBrokerPurchase);
                    }
                }
            } catch (CantGetListPurchaseNegotiationsException e) {
                e.printStackTrace();
            } catch (DatabaseOperationException e) {
                e.printStackTrace();
            } catch (MissingCustomerBrokerPurchaseDataException e) {
                e.printStackTrace();
            } catch (CantOpenContractException e) {
                e.printStackTrace();
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            } catch (CantGetListCustomerBrokerContractPurchaseException e) {
                e.printStackTrace();
            } catch (CantCloseContractException e) {
                e.printStackTrace();
            }
        }
    }
}
