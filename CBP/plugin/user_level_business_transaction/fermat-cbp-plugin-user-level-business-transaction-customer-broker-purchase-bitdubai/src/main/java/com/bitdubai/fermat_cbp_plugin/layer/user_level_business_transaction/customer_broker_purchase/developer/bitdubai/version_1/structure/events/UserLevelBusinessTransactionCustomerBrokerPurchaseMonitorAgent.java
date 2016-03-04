package com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSendNotificationReviewNegotiation;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.exceptions.CantCloseContractException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.interfaces.CloseContractManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.exceptions.CantOpenContractException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContractManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.common.enums.TransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.interfaces.CustomerBrokerPurchase;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.database.UserLevelBusinessTransactionCustomerBrokerPurchaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.database.UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.exceptions.MissingCustomerBrokerPurchaseDataException;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.structure.UserLevelBusinessTransactionCustomerBrokerPurchaseManager;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.utils.CustomerBrokerPurchaseImpl;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.all_definition.utils.CurrencyPairImpl;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * The Class <code>UserLevelBusinessTransactionCustomerBrokerPurchaseMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 11.12.15
 */
public class UserLevelBusinessTransactionCustomerBrokerPurchaseMonitorAgent extends FermatAgent {
    //TODO: Documentar y manejo de excepciones.

    private Thread agentThread;
    private final ErrorManager errorManager;
    private final CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;
    private final UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao;
    private final OpenContractManager openContractManager;
    private final CloseContractManager closeContractManager;
    private final CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    private final CurrencyExchangeProviderFilterManager currencyExchangeRateProviderFilter;
    //private final NotificationManagerMiddleware notificationManagerMiddleware;
    private final UserLevelBusinessTransactionCustomerBrokerPurchaseManager userLevelBusinessTransactionCustomerBrokerPurchaseManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;


    public final int DELAY_HOURS = 2;
    public final int SLEEP_TIME = 5000;
    CustomerBrokerPurchaseImpl customerBrokerPurchase = null;

    public UserLevelBusinessTransactionCustomerBrokerPurchaseMonitorAgent(ErrorManager errorManager,
                                                                          CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
                                                                          PluginDatabaseSystem pluginDatabaseSystem,
                                                                          UUID pluginId,
                                                                          OpenContractManager openContractManager,
                                                                          CloseContractManager closeContractManager,
                                                                          CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
                                                                          CurrencyExchangeProviderFilterManager currencyExchangeRateProviderFilter,
                                                                          //NotificationManagerMiddleware notificationManagerMiddleware,
                                                                          UserLevelBusinessTransactionCustomerBrokerPurchaseManager userLevelBusinessTransactionCustomerBrokerPurchaseManager,
                                                                          CryptoBrokerWalletManager cryptoBrokerWalletManager) {

        this.errorManager = errorManager;
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.openContractManager = openContractManager;
        this.closeContractManager = closeContractManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.currencyExchangeRateProviderFilter = currencyExchangeRateProviderFilter;
        //this.notificationManagerMiddleware = notificationManagerMiddleware;
        this.userLevelBusinessTransactionCustomerBrokerPurchaseManager = userLevelBusinessTransactionCustomerBrokerPurchaseManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;

        this.userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao = new UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao(pluginDatabaseSystem, pluginId);

        this.agentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    process();
            }
        }
                , this.getClass().getSimpleName());
    }

    @Override
    public void start() throws CantStartAgentException {
        Logger LOG = Logger.getGlobal();
        LOG.info("Customer Broker Purchase monitor agent starting");

//        final MonitorAgent monitorAgent = new MonitorAgent(errorManager);
//
//        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();
        super.start();
    }

    @Override
    public void stop() throws CantStopAgentException {
        this.agentThread.interrupt();
        super.stop();
    }

    public void process() {

        while (isRunning()) {

            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException interruptedException) {
                cleanResources();
                return;
            }

            doTheMainTask();

            if (agentThread.isInterrupted()) {
                cleanResources();
                return;
            }
        }
    }

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
//    private final class MonitorAgent implements Runnable {
//
//        private final ErrorManager errorManager;
//        public final int SLEEP_TIME = 5000;
//        public final int DELAY_HOURS = 2;
//        int iterationNumber = 0;
//        boolean threadWorking;
//        CustomerBrokerPurchaseImpl customerBrokerPurchase = null;
//        //UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao;
//
//        public MonitorAgent(final ErrorManager errorManager) {
//
//            this.errorManager = errorManager;
//        }
//
//        @Override
//        public void run() {
//            threadWorking = true;
//            while (threadWorking) {
//                /**
//                 * Increase the iteration counter
//                 */
//                iterationNumber++;
//                try {
//                    Thread.sleep(SLEEP_TIME);
//                } catch (InterruptedException interruptedException) {
//                    return;
//                }
//
//                /**
//                 * now I will check if there are pending transactions to raise the event
//                 */
//                try {
//                    doTheMainTask();
//                } catch (Exception e) {
//                    errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_PURCHASE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                }
//
//            }
//        }
//
//    }
    private void doTheMainTask() {
        try {
            //Se verifica el cierre de la negociacion
            for (CustomerBrokerPurchaseNegotiation records : customerBrokerPurchaseNegotiationManager.getNegotiationsByStatus(NegotiationStatus.CLOSED)) {
                //Buscar que la transaccion no se encuentre ya registrada
                if (userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(getFilterTable(records.getNegotiationId().toString(), UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TRANSACTION_ID_COLUMN_NAME)).isEmpty()) {
                    customerBrokerPurchase = new CustomerBrokerPurchaseImpl(records.getNegotiationId().toString(),
                            records.getNegotiationId().toString(),
                            0, null, null, TransactionStatus.IN_PROCESS, null, null, null);

                    userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.saveCustomerBrokerPurchaseTransactionData(customerBrokerPurchase);
                }
            }
            //Se crea la business transaction
            for (CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(getFilterTable(TransactionStatus.IN_PROCESS.getCode(), UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_PROCESS
            {
                CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerPurchase.getTransactionId()));
                //Registra el Open Contract siempre y cuando el Transaction_Status de la Transaction Customer Broker Purchase este IN_PROCESS

                //Find the negotiation's customerCurrency, to find the marketExchangeRate of that currency vs. USD
                String customerCurrency = "";
                for(Clause clause : customerBrokerPurchaseNegotiation.getClauses())
                    if(clause.getType() == ClauseType.CUSTOMER_CURRENCY)
                        customerCurrency = clause.getValue();

                float marketExchangeRate = 1;
                if(customerCurrency != "")
                {
                    try{
                        marketExchangeRate = getMarketExchangeRate(customerCurrency);
                    }catch (CantGetExchangeRateException e) {
                        marketExchangeRate = 1;
                    }
                }
                openContractManager.openPurchaseContract(customerBrokerPurchaseNegotiation, marketExchangeRate);

                //Actualiza el Transaction_Status de la Transaction Customer Broker Purchase a IN_OPEN_CONTRACT
                customerBrokerPurchase.setTransactionStatus(TransactionStatus.IN_OPEN_CONTRACT);
                userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.saveCustomerBrokerPurchaseTransactionData(customerBrokerPurchase);
            }
            for (CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(getFilterTable(TransactionStatus.IN_OPEN_CONTRACT.getCode(), UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_OPEN_CONTRACT
            {
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.PENDING_PAYMENT)) {
                    if (Objects.equals(customerBrokerPurchase.getTransactionId(), customerBrokerContractPurchase.getNegotiatiotId())) {
                        customerBrokerPurchase.setTransactionStatus(TransactionStatus.IN_CONTRACT_SUBMIT);
                        userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.saveCustomerBrokerPurchaseTransactionData(customerBrokerPurchase);
                    }
                }
            }
            //Se verifica las condiciones del contrato y el status
            for (CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(getFilterTable(TransactionStatus.IN_CONTRACT_SUBMIT.getCode(), UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_CONTRACT_SUBMIT
            {
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.PENDING_PAYMENT)) {
                    if (customerBrokerPurchase.getTransactionId() == customerBrokerContractPurchase.getNegotiatiotId()) {
                        //Si la fecha del contracto se acerca al dia y 2 horas antes de vencerse debo de elevar un evento de notificacion siempre y cuando el ContractStatus sea igual a PENDING_PAYMENT
                        Date date = null;
                        long timeStampToday = ((customerBrokerContractPurchase.getDateTime() - (date != null ? date.getTime() : 0)) / 60) / 60;
                        if (timeStampToday <= DELAY_HOURS) {
                            customerBrokerContractPurchaseManager.updateContractNearExpirationDatetime(customerBrokerContractPurchase.getContractId(), true);

                            userLevelBusinessTransactionCustomerBrokerPurchaseManager.notificationReviewNegotiation("crypto_broker_wallet", "Review negotiation", "Review negotiation");
                            //TODO
//                                notificationManagerMiddleware.addPopUpNotification(EventSource.BUSINESS_TRANSACTION_OPEN_CONTRACT, "Review Negotiation");
                        }
                    }
                }
            }
            //Se sigue verificando el estatus del contrato hasta que se consiga la realización de un pago
            for (CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(getFilterTable(TransactionStatus.IN_CONTRACT_SUBMIT.getCode(), UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_CONTRACT_SUBMIT
            {
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.PAYMENT_SUBMIT)) {
                    if (Objects.equals(customerBrokerPurchase.getTransactionId(), customerBrokerContractPurchase.getNegotiatiotId())) {
                        //Si se detecta la realización de un pago se procede actulizar el estatus de la transacción y a monitorear la llegada de la mercadería.
                        customerBrokerPurchase.setTransactionStatus(TransactionStatus.IN_PAYMENT_SUBMIT);
                        userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.saveCustomerBrokerPurchaseTransactionData(customerBrokerPurchase);
                    }
                }
            }
            for (CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(getFilterTable(TransactionStatus.IN_PAYMENT_SUBMIT.getCode(), UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_PAYMENT_SUBMIT
            {
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.PENDING_MERCHANDISE)) {
                    if (customerBrokerPurchase.getTransactionId() == customerBrokerContractPurchase.getNegotiatiotId()) {
                        customerBrokerPurchase.setTransactionStatus(TransactionStatus.IN_PENDING_MERCHANDISE);
                        userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.saveCustomerBrokerPurchaseTransactionData(customerBrokerPurchase);
                    }
                }
            }
            for (CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(getFilterTable(TransactionStatus.IN_PENDING_MERCHANDISE.getCode(), UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_PENDING_MERCHANDISE
            {
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.PENDING_MERCHANDISE)) {
                    if (Objects.equals(customerBrokerPurchase.getTransactionId(), customerBrokerContractPurchase.getNegotiatiotId())) {
                        //Si se acerca la tiempo límite para recibir la mercadería y esta no ha sido registrada como recibida, se eleva un evento de notificación
                        Date date = null;
                        long timeStampToday = ((customerBrokerContractPurchase.getDateTime() - (date != null ? date.getTime() : 0)) / 60) / 60;
                        if (timeStampToday <= DELAY_HOURS) {
                            customerBrokerContractPurchaseManager.updateContractNearExpirationDatetime(customerBrokerContractPurchase.getContractId(), true);
                            userLevelBusinessTransactionCustomerBrokerPurchaseManager.notificationReviewNegotiation("crypto_broker_wallet", "Review negotiation", "Review negotiation");
//                                notificationManagerMiddleware.addPopUpNotification(EventSource.BUSINESS_TRANSACTION_OPEN_CONTRACT, "Review Negotiation");
                        }
                    }
                }
            }
            for (CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(getFilterTable(TransactionStatus.IN_PENDING_MERCHANDISE.getCode(), UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_PENDING_MERCHANDISE
            {

                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.MERCHANDISE_SUBMIT)) {
                    if (customerBrokerPurchase.getTransactionId() == customerBrokerContractPurchase.getNegotiatiotId()) {
                        customerBrokerPurchase.setTransactionStatus(TransactionStatus.IN_MERCHANDISE_SUBMIT);
                        userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.saveCustomerBrokerPurchaseTransactionData(customerBrokerPurchase);
                    }
                }
            }
            for (CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(getFilterTable(TransactionStatus.IN_MERCHANDISE_SUBMIT.getCode(), UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_MERCHANDISE_SUBMIT
            {
                //Comienzo a recorrer todas las transacciones que esten en Transaction_Status IN_MERCHANDISE_SUBMIT
                //Registra el Close Contract siempre y cuando el Transaction_Status de la Transaction Customer Broker Purchase este IN_MERCHANDISE_SUBMIT
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.MERCHANDISE_SUBMIT)) {
                    if (Objects.equals(customerBrokerPurchase.getTransactionId(), customerBrokerContractPurchase.getNegotiatiotId())) {
                        closeContractManager.closePurchaseContract(customerBrokerContractPurchase.getContractId());
                    }
                }
            }
            for (CustomerBrokerPurchase customerBrokerPurchase : userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.getCustomerBrokerPurchases(getFilterTable(TransactionStatus.IN_MERCHANDISE_SUBMIT.getCode(), UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_MERCHANDISE_SUBMIT
            {
                for (CustomerBrokerContractPurchase customerBrokerContractPurchase : customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForStatus(ContractStatus.COMPLETED)) {
                    customerBrokerPurchase.setTransactionStatus(TransactionStatus.COMPLETED);
                    userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao.saveCustomerBrokerPurchaseTransactionData(customerBrokerPurchase);
                }
            }
        } catch (CantSendNotificationReviewNegotiation e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_PURCHASE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetListPurchaseNegotiationsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_PURCHASE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_PURCHASE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (MissingCustomerBrokerPurchaseDataException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_PURCHASE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantOpenContractException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_PURCHASE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_PURCHASE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_PURCHASE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantCloseContractException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_PURCHASE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantUpdateCustomerBrokerContractPurchaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_PURCHASE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_PURCHASE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

    }

    private DatabaseTableFilter getFilterTable(final String valueFilter, final String columnValue) {
        // I define the filter to search for the public Key
        DatabaseTableFilter filter = new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return columnValue;
            }

            @Override
            public String getValue() {
                return valueFilter;
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };
        return filter;
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }





    /* Private methods */

    private float getMarketExchangeRate(String customerCurrency) throws CantGetExchangeRateException
    {
        //Find out if customerCurrency parameter is a FiatCurrency or a CryptoCurrency
        Currency currency = null;
        try {
            if(FiatCurrency.codeExists(customerCurrency))
                currency = FiatCurrency.getByCode(customerCurrency);
            else if(CryptoCurrency.codeExists(customerCurrency))
                currency = CryptoCurrency.getByCode(customerCurrency);
        } catch(Exception e) {
            throw new CantGetExchangeRateException();
        }

        if(currency == null)
            throw new CantGetExchangeRateException();


        CurrencyPair currencyPair = new CurrencyPairImpl(FiatCurrency.US_DOLLAR, currency);


        //Get saved CER providers in broker wallet
        final String publicKeyWalletCryptoBrokerInstall = "walletPublicKeyTest"; //TODO: Quitar este hardcode luego que se implemente la instalacion de la wallet

        try {
            final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(publicKeyWalletCryptoBrokerInstall);
            final CryptoBrokerWalletSetting cryptoWalletSetting = cryptoBrokerWallet.getCryptoWalletSetting();
            final List<CryptoBrokerWalletProviderSetting> providerSettings = cryptoWalletSetting.getCryptoBrokerWalletProviderSettings();

            for (CryptoBrokerWalletProviderSetting providerSetting : providerSettings) {

                UUID providerId = providerSetting.getPlugin();
                CurrencyExchangeRateProviderManager providerReference = currencyExchangeRateProviderFilter.getProviderReference(providerId);
                if (providerReference.isCurrencyPairSupported(currencyPair)) {
                    ExchangeRate currentExchangeRate = providerReference.getCurrentExchangeRate(currencyPair);
                    return (float) currentExchangeRate.getPurchasePrice();
                }
            }
        } catch (Exception e) { /*Continue*/ }

        //Find any CER provider which can obtain the needed currencyPair, regardless of it not being set up in the broker wallet
        try {
            for (CurrencyExchangeRateProviderManager providerReference : currencyExchangeRateProviderFilter.getProviderReferencesFromCurrencyPair(currencyPair)) {
                ExchangeRate currentExchangeRate = providerReference.getCurrentExchangeRate(currencyPair);
                return (float) currentExchangeRate.getPurchasePrice();
            }
        } catch (Exception e) { /*Continue*/ }

        //Can't do nothing more
        throw new CantGetExchangeRateException();
    }
}
