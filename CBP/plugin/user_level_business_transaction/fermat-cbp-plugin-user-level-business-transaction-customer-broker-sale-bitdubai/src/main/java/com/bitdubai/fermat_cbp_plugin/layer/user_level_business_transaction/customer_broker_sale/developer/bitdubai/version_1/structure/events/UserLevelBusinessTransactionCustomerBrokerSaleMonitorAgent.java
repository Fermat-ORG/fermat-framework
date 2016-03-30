package com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.exceptions.CantCloseContractException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.interfaces.CloseContractManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.exceptions.CantOpenContractException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContractManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantUpdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.exceptions.CantCreateBankMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.interfaces.BankMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.exceptions.CantCreateCashMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.interfaces.CashMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.exceptions.CantCreateCryptoMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.interfaces.CryptoMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.common.enums.TransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_sale.interfaces.CustomerBrokerSale;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.database.UserLevelBusinessTransactionCustomerBrokerSaleConstants;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.database.UserLevelBusinessTransactionCustomerBrokerSaleDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.exceptions.MissingCustomerBrokerSaleDataException;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.structure.UserLevelBusinessTransactionCustomerBrokerSaleManager;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.utils.CustomerBrokerSaleImpl;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.all_definition.utils.CurrencyPairImpl;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_pip_api.layer.module.notification.interfaces.NotificationManagerMiddleware;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;


/**
 * The Class <code>UserLevelBusinessTransactionCustomerBrokerSaleMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 15.12.15
 */
public class UserLevelBusinessTransactionCustomerBrokerSaleMonitorAgent extends FermatAgent {
    //TODO: Documentar y manejo de excepciones.
    private Thread agentThread;
    private final ErrorManager errorManager;
    private final CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;
    private final UserLevelBusinessTransactionCustomerBrokerSaleDatabaseDao userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao;
    private final OpenContractManager openContractManager;
    private final CloseContractManager closeContractManager;
    private final CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    private final CurrencyExchangeProviderFilterManager currencyExchangeRateProviderFilter;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final BankMoneyRestockManager bankMoneyRestockManager;
    private final CashMoneyRestockManager cashMoneyRestockManager;
    private final CryptoMoneyRestockManager cryptoMoneyRestockManager;
    private final NotificationManagerMiddleware notificationManagerMiddleware;
    private CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread;
    private CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting;
    private UserLevelBusinessTransactionCustomerBrokerSaleManager userLevelBusinessTransactionCustomerBrokerSaleManager;
    private Broadcaster broadcaster;

    public final int SLEEP_TIME = 5000;
    public final int DELAY_HOURS = 2;
    public final int TIME_BETWEEN_NOTIFICATIONS = 600000; //10min
    private long lastNotificationTime = 0;
    private BigDecimal priceReference = null;
    private BigDecimal amount = null;
    private String bankAccount = null;
    private FiatCurrency fiatCurrency = null;
    CustomerBrokerSaleImpl customerBrokerSale = null;

    public UserLevelBusinessTransactionCustomerBrokerSaleMonitorAgent(ErrorManager errorManager,
                                                                      CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
                                                                      PluginDatabaseSystem pluginDatabaseSystem,
                                                                      UUID pluginId,
                                                                      OpenContractManager openContractManager,
                                                                      CloseContractManager closeContractManager,
                                                                      CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
                                                                      CurrencyExchangeProviderFilterManager currencyExchangeRateProviderFilter,
                                                                      CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                                      BankMoneyRestockManager bankMoneyRestockManager,
                                                                      CashMoneyRestockManager cashMoneyRestockManager,
                                                                      CryptoMoneyRestockManager cryptoMoneyRestockManager,
                                                                      NotificationManagerMiddleware notificationManagerMiddleware,
                                                                      UserLevelBusinessTransactionCustomerBrokerSaleManager userLevelBusinessTransactionCustomerBrokerSaleManager, Broadcaster broadcaster) {

        this.errorManager = errorManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.openContractManager = openContractManager;
        this.closeContractManager = closeContractManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.currencyExchangeRateProviderFilter = currencyExchangeRateProviderFilter;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.bankMoneyRestockManager = bankMoneyRestockManager;
        this.cashMoneyRestockManager = cashMoneyRestockManager;
        this.cryptoMoneyRestockManager = cryptoMoneyRestockManager;
        this.notificationManagerMiddleware = notificationManagerMiddleware;
        this.userLevelBusinessTransactionCustomerBrokerSaleManager = userLevelBusinessTransactionCustomerBrokerSaleManager;
        this.broadcaster = broadcaster;
        this.userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao = new UserLevelBusinessTransactionCustomerBrokerSaleDatabaseDao(pluginDatabaseSystem, pluginId);
        try {
            //TODO:Revisar este caso CryptoBrokerWalletAssociatedSetting va a devolver varios registros.
            this.cryptoBrokerWalletSettingSpread = cryptoBrokerWalletManager.loadCryptoBrokerWallet("walletPublicKeyTest").getCryptoWalletSetting().getCryptoBrokerWalletSpreadSetting();
            if (!cryptoBrokerWalletManager.loadCryptoBrokerWallet("walletPublicKeyTest").getCryptoWalletSetting().getCryptoBrokerWalletAssociatedSettings().isEmpty()) {
                this.cryptoBrokerWalletAssociatedSetting = cryptoBrokerWalletManager.loadCryptoBrokerWallet("walletPublicKeyTest").getCryptoWalletSetting().getCryptoBrokerWalletAssociatedSettings().get(0);
            }
        } catch (CantGetCryptoBrokerWalletSettingException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CryptoBrokerWalletNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

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
        LOG.info("Customer Broker Sale monitor agent starting");

//        final MonitorAgent monitorAgent = new MonitorAgent(errorManager);

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
//        private BigDecimal priceReference = null;
//        private BigDecimal amount         = null;
//        private String bankAccount        = null;
//        private FiatCurrency fiatCurrency = null;
//        CustomerBrokerSaleImpl customerBrokerSale = null;
//        //UserLevelBusinessTransactionCustomerBrokerSaleDatabaseDao userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao;
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
//                    errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                }
//
//            }
//        }
//
//    }
    private void doTheMainTask() {
        try {
            final String transactionStatusColumnName = UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_STATUS_COLUMN_NAME;
            List<CustomerBrokerSale> customerBrokerSales;
            // TODO: Esto es provisorio. hay que obtenerlo del Wallet Manager de WPD hasta que matias haga los cambios para que no sea necesario enviar esto
            //esta publicKey es la usada en la clase FermatAppConnectionManager y en los navigationStructure de las wallets y subapps
            final String brokerWalletPublicKey = "crypto_broker_wallet";


            CryptoBrokerWalletSetting walletSettings = cryptoBrokerWalletManager.loadCryptoBrokerWallet("walletPublicKeyTest").getCryptoWalletSetting();
            CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread = walletSettings.getCryptoBrokerWalletSpreadSetting();

            //TODO:Revisar este caso CryptoBrokerWalletAssociatedSetting va a devolver varios registros.
            if (!walletSettings.getCryptoBrokerWalletAssociatedSettings().isEmpty()) {
                CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting = walletSettings.getCryptoBrokerWalletAssociatedSettings().get(0);
            }

            /**
             * NegotiationStatus.CLOSED -> TransactionStatus.IN_PROCESS
             */
            Collection<CustomerBrokerSaleNegotiation> negotiationsClosed = customerBrokerSaleNegotiationManager.getNegotiationsByStatus(NegotiationStatus.CLOSED);
            for (CustomerBrokerSaleNegotiation records : negotiationsClosed) {
                String negotiationId = records.getNegotiationId().toString(); //Buscar que la transaccion no se encuentre ya registrada

                DatabaseTableFilter filterTable = getFilterTable(negotiationId, UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_ID_COLUMN_NAME);
                customerBrokerSales = userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(filterTable);

                if (customerBrokerSales.isEmpty()) {
                    customerBrokerSale = new CustomerBrokerSaleImpl(negotiationId, negotiationId, 0, null, null, TransactionStatus.IN_PROCESS, null, null, null);
                    userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.saveCustomerBrokerSaleTransactionData(customerBrokerSale);
                }
            }

            /**
             * IN_PROCESS -> IN_OPEN_CONTRACT:
             *
             * Registra el Open Contract siempre y cuando el Transaction Status de la CustomerBrokerSale este IN_PROCESS
             * Se obtiene el customerCurrency de la negociacion para obtener el marketExchangeRate de ese currency vs. USD
             *
             * Se envia un Broadcast para actualizar la UI y enviar una notificacion
             */
            //Se crea la business transaction
            DatabaseTableFilter filterTable = getFilterTable(TransactionStatus.IN_PROCESS.getCode(), transactionStatusColumnName);
            customerBrokerSales = userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(filterTable);

            for (CustomerBrokerSale customerBrokerSale : customerBrokerSales) {
                CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation = customerBrokerSaleNegotiationManager.
                        getNegotiationsByNegotiationId(UUID.fromString(customerBrokerSale.getTransactionId()));

                //Registra el Open Contract siempre y cuando el Transaction_Status de la Transaction Customer Broker Sale este IN_PROCESS
                //Find the negotiation's customerCurrency, to find the marketExchangeRate of that currency vs. USD
                String customerCurrency = "";
                for (Clause clause : customerBrokerSaleNegotiation.getClauses())
                    if (clause.getType() == ClauseType.CUSTOMER_CURRENCY)
                        customerCurrency = clause.getValue();

                float marketExchangeRate = 1;
                if (customerCurrency.isEmpty()) {
                    try {
                        marketExchangeRate = getMarketExchangeRate(customerCurrency);
                    } catch (CantGetExchangeRateException e) {
                        marketExchangeRate = 1;
                    }
                }
                openContractManager.openSaleContract(customerBrokerSaleNegotiation, marketExchangeRate);

                //Actualiza el Transaction_Status de la Transaction Customer Broker Sale a IN_OPEN_CONTRACT
                customerBrokerSale.setTransactionStatus(TransactionStatus.IN_OPEN_CONTRACT);
                userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.saveCustomerBrokerSaleTransactionData(customerBrokerSale);
                broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, brokerWalletPublicKey, CBPBroadcasterConstants.CBW_NEW_CONTRACT_NOTIFICATION);
                broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_CONTRACT_UPDATE_VIEW);
            }

            /**
             * IN_OPEN_CONTRACT -> IN_CONTRACT_SUBMIT:
             *
             * Se debe enviar un Broadcast para actualizar la UI
             */
            filterTable = getFilterTable(TransactionStatus.IN_OPEN_CONTRACT.getCode(), transactionStatusColumnName);
            customerBrokerSales = userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(filterTable);
            Collection<CustomerBrokerContractSale> contractSalesPendingPayment = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForStatus(ContractStatus.PENDING_PAYMENT);

            for (CustomerBrokerSale customerBrokerSale : customerBrokerSales){
                for (CustomerBrokerContractSale customerBrokerContractSale : contractSalesPendingPayment) {
                    if (Objects.equals(customerBrokerSale.getTransactionId(), customerBrokerContractSale.getNegotiatiotId())) {
                        customerBrokerSale.setTransactionStatus(TransactionStatus.IN_CONTRACT_SUBMIT);
                        userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.saveCustomerBrokerSaleTransactionData(customerBrokerSale);
                        broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_CONTRACT_UPDATE_VIEW);
                    }
                }
            }

            /**
             * IN_CONTRACT_SUBMIT -> Update Contract Expiration Time and notify:
             *
             * Si la fecha del contracto se acerca al dia y 2 horas antes de vencerse debo de elevar un evento de notificacion
             * siempre y cuando el ContractStatus sea igual a PENDING_PAYMENT
             */
            filterTable = getFilterTable(TransactionStatus.IN_CONTRACT_SUBMIT.getCode(), transactionStatusColumnName);
            customerBrokerSales = userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(filterTable);

            for (CustomerBrokerSale customerBrokerSale : customerBrokerSales) {
                for (CustomerBrokerContractSale customerBrokerContractSale : contractSalesPendingPayment) {
                    if (Objects.equals(customerBrokerSale.getTransactionId(), customerBrokerContractSale.getNegotiatiotId())) {
                        // Si la fecha del contracto se acerca al dia y 2 horas antes de vencerse debo de elevar un evento
                        // de notificacion siempre y cuando el ContractStatus sea igual a PENDING_PAYMENT

                        Date date = new Date();
                        long timeStampToday = ((customerBrokerContractSale.getDateTime() - (date != null ? date.getTime() : 0)) / 3600000);
                        if (timeStampToday <= DELAY_HOURS) {
                            customerBrokerContractSaleManager.updateContractNearExpirationDatetime(customerBrokerContractSale.getContractId(), true);
                            // TODO: Esto es provisorio. hay que obtenerlo del Wallet Manager de WPD hasta que matias haga los cambios para que no sea necesario enviar esto
                            //esta publicKey es la usada en la clase FermatAppConnectionManager y en los navigationStructure de las wallets y subapps

                            if (new Date().getTime() - lastNotificationTime > TIME_BETWEEN_NOTIFICATIONS) {
                                lastNotificationTime = new Date().getTime();
                                broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, brokerWalletPublicKey, CBPBroadcasterConstants.CBW_CONTRACT_EXPIRATION_NOTIFICATION);
                                broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_CONTRACT_UPDATE_VIEW);
                            }
                        }
                    }
                }
            }

            /**
             * IN_CONTRACT_SUBMIT -> IN_PAYMENT_SUBMIT:
             *
             * Se sigue verificando el estatus del contrato hasta que se consiga la realización de un pago.
             * Si se detecta la realización de un pago se procede actulizar el estatus de la transacción y a monitorear la llegada de la mercadería.
             * Se verifica si el broker configuró procesar Restock de manera automática.
             *
             * Se debe enviar un Broadcast para actualizar la UI
             */
            filterTable = getFilterTable(TransactionStatus.IN_CONTRACT_SUBMIT.getCode(), transactionStatusColumnName);
            customerBrokerSales = userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(filterTable);
            Collection<CustomerBrokerContractSale> contractSalePaymentSubmit = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForStatus(ContractStatus.PAYMENT_SUBMIT);

            for (CustomerBrokerSale customerBrokerSale : customerBrokerSales){
                for (CustomerBrokerContractSale customerBrokerContractSale : contractSalePaymentSubmit) {
                    if (Objects.equals(customerBrokerSale.getTransactionId(), customerBrokerContractSale.getNegotiatiotId())) {
                        // Si se detecta la realización de un pago se procede actulizar el estatus de la transacción
                        // y a monitorear la llegada de la mercadería.
                        // Se verifica si el broker configuró procesar Restock de manera automática

                        int sw = 0;
                        if (cryptoBrokerWalletSettingSpread.getRestockAutomatic()) {
                            //Recorrer las clausulas del contrato
                            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractSale.getNegotiatiotId()));
                            for (ContractClause contractClause : customerBrokerContractSale.getContractClause()) {
                                if (contractClause.getType() == ContractClauseType.CRYPTO_TRANSFER)
                                    sw = 1;
                                else if (contractClause.getType() == ContractClauseType.BANK_TRANSFER)
                                    sw = 2;
                                else if (contractClause.getType() == ContractClauseType.CASH_DELIVERY || contractClause.getType() == ContractClauseType.CASH_ON_HAND)
                                    sw = 3;
                            }

                            final NumberFormat instance = NumberFormat.getInstance();
                            for (Clause clause : customerBrokerSaleNegotiation.getClauses()) {
                                switch (clause.getType()) {
                                    case EXCHANGE_RATE:
                                        priceReference = new BigDecimal(instance.parse(clause.getValue()).doubleValue());
                                        break;
                                    case BROKER_CURRENCY_QUANTITY:
                                        amount = new BigDecimal(instance.parse(clause.getValue()).doubleValue());
                                        break;
                                    case BROKER_BANK_ACCOUNT:
                                        bankAccount = getAccountNumberFromClause(clause);
                                        break;
                                    case BROKER_CURRENCY:
                                        fiatCurrency = FiatCurrency.getByCode(clause.getValue());
                                        break;
                                }
                            }

                            if (sw == 1) {
                                cryptoMoneyRestockManager.createTransactionRestock(customerBrokerContractSale.getPublicKeyBroker(),
                                        CryptoCurrency.BITCOIN,
                                        "walletPublicKey",
                                        "walletPublicKey",
                                        amount,
                                        "RESTOCK AUTOMATIC",
                                        priceReference,
                                        OriginTransaction.RESTOCK_AUTOMATIC,
                                        customerBrokerContractSale.getContractId(),
                                        BlockchainNetworkType.getDefaultBlockchainNetworkType()); //TODO: Revisar de donde saco esto

                            } else if (sw == 2) {
                                bankMoneyRestockManager.createTransactionRestock(customerBrokerContractSale.getPublicKeyBroker(),
                                        fiatCurrency,
                                        "walletPublicKey",
                                        "walletPublicKey",
                                        bankAccount,
                                        amount,
                                        "RESTOCK AUTOMATIC",
                                        priceReference,
                                        OriginTransaction.RESTOCK_AUTOMATIC,
                                        customerBrokerContractSale.getContractId());

                            } else if (sw == 3) {
                                cashMoneyRestockManager.createTransactionRestock(customerBrokerContractSale.getPublicKeyBroker(),
                                        fiatCurrency,
                                        "walletPublicKey",
                                        "walletPublicKey",
                                        "cashReference",
                                        amount,
                                        "memo",
                                        priceReference,
                                        OriginTransaction.RESTOCK_AUTOMATIC,
                                        customerBrokerContractSale.getContractId());
                            }
                        }
                        customerBrokerSale.setTransactionStatus(TransactionStatus.IN_PAYMENT_SUBMIT);
                        userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.saveCustomerBrokerSaleTransactionData(customerBrokerSale);
                        broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_CONTRACT_UPDATE_VIEW);
                        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, brokerWalletPublicKey, CBPBroadcasterConstants.CBW_CONTRACT_CUSTOMER_SUBMITTED_PAYMENT_NOTIFICATION);
                    }
                }
            }

            /**
             * IN_PAYMENT_SUBMIT -> IN_PENDING_MERCHANDISE:
             *
             * Se debe enviar un Broadcast para actualizar la UI
             */
            filterTable = getFilterTable(TransactionStatus.IN_PAYMENT_SUBMIT.getCode(), transactionStatusColumnName);
            customerBrokerSales = userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(filterTable);
            Collection<CustomerBrokerContractSale> contractSalesPendingMerchandise = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForStatus(ContractStatus.PENDING_MERCHANDISE);

            for (CustomerBrokerSale customerBrokerSale : customerBrokerSales) {
                for (CustomerBrokerContractSale customerBrokerContractSale : contractSalesPendingMerchandise) {
                    if (Objects.equals(customerBrokerSale.getTransactionId(), customerBrokerContractSale.getNegotiatiotId())) {
                        customerBrokerSale.setTransactionStatus(TransactionStatus.IN_PENDING_MERCHANDISE);
                        userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.saveCustomerBrokerSaleTransactionData(customerBrokerSale);
                        broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_CONTRACT_UPDATE_VIEW);
                    }
                }
            }

            /**
             * IN_PENDING_MERCHANDISE -> Update Contract Expiration Time and notify:
             *
             * Si se acerca la tiempo límite para recibir la mercadería y esta no ha sido registrada como recibida,
             * se eleva un evento de notificación
             */
            filterTable = getFilterTable(TransactionStatus.IN_PENDING_MERCHANDISE.getCode(), transactionStatusColumnName);
            customerBrokerSales = userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(filterTable);

            for (CustomerBrokerSale customerBrokerSale : customerBrokerSales) {
                for (CustomerBrokerContractSale customerBrokerContractSale : contractSalesPendingMerchandise) {
                    if (Objects.equals(customerBrokerSale.getTransactionId(), customerBrokerContractSale.getNegotiatiotId())) {
                        //Si se acerca la tiempo límite para recibir la mercadería y esta no ha sido registrada como recibida,
                        // se eleva un evento de notificación
                        Date date = new Date();
                        long timeStampToday = ((customerBrokerContractSale.getDateTime() - (date != null ? date.getTime() : 0)) / 3600000);
                        if (timeStampToday <= DELAY_HOURS) {
                            customerBrokerContractSaleManager.updateContractNearExpirationDatetime(customerBrokerContractSale.getContractId(), true);
                            // TODO: Esto es provisorio. hay que obtenerlo del Wallet Manager de WPD hasta que matias haga los cambios para que no sea necesario enviar esto
                            //esta publicKey es la usada en la clase FermatAppConnectionManager y en los navigationStructure de las wallets y subapps

                            if (new Date().getTime() - lastNotificationTime > TIME_BETWEEN_NOTIFICATIONS) {
                                lastNotificationTime = new Date().getTime();
                                broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, brokerWalletPublicKey, CBPBroadcasterConstants.CBW_CONTRACT_EXPIRATION_NOTIFICATION);
                                broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_CONTRACT_UPDATE_VIEW);
                            }
                        }
                    }
                }
            }

            /**
             * IN_PENDING_MERCHANDISE -> MERCHANDISE_SUBMIT:
             *
             * Se debe enviar un Broadcast para actualizar la UI
             */
            filterTable = getFilterTable(TransactionStatus.IN_PENDING_MERCHANDISE.getCode(), transactionStatusColumnName);
            customerBrokerSales = userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(filterTable);
            Collection<CustomerBrokerContractSale> contractSalesMerchandiseSubmit = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForStatus(ContractStatus.MERCHANDISE_SUBMIT);

            for (CustomerBrokerSale customerBrokerSale : customerBrokerSales){
                for (CustomerBrokerContractSale customerBrokerContractSale : contractSalesMerchandiseSubmit) {
                    if (Objects.equals(customerBrokerSale.getTransactionId(), customerBrokerContractSale.getNegotiatiotId())) {
                        customerBrokerSale.setTransactionStatus(TransactionStatus.IN_MERCHANDISE_SUBMIT);
                        userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.saveCustomerBrokerSaleTransactionData(customerBrokerSale);
                        broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_CONTRACT_UPDATE_VIEW);
                    }
                }
            }

            /**
             * IN_MERCHANDISE_SUBMIT -> Create Close Contract transaction:
             *
             * Comienzo a recorrer todas las transacciones que esten en Transaction Status IN_MERCHANDISE_SUBMIT
             * Se registra el Close Contract siempre y cuando el Transaction Status de la CustomerBrokerSale este IN_MERCHANDISE_SUBMIT
             */
            filterTable = getFilterTable(TransactionStatus.IN_MERCHANDISE_SUBMIT.getCode(), transactionStatusColumnName);
            customerBrokerSales = userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(filterTable);

            for (CustomerBrokerSale customerBrokerSale : customerBrokerSales) {
                for (CustomerBrokerContractSale customerBrokerContractSale : contractSalesMerchandiseSubmit) {
                    if (Objects.equals(customerBrokerSale.getTransactionId(), customerBrokerContractSale.getNegotiatiotId())) {
                        closeContractManager.closeSaleContract(customerBrokerContractSale.getContractId());
                    }
                }
            }

            /**
             * IN_MERCHANDISE_SUBMIT -> COMPLETED
             *
             * Se debe enviar un Broadcast para actualizar la UI
             */
            filterTable = getFilterTable(TransactionStatus.IN_MERCHANDISE_SUBMIT.getCode(), transactionStatusColumnName);
            customerBrokerSales = userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(filterTable);
            Collection<CustomerBrokerContractSale> contractSalesCompleted = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForStatus(ContractStatus.COMPLETED);

            for (CustomerBrokerSale customerBrokerSale : customerBrokerSales) {
                for (CustomerBrokerContractSale customerBrokerContractSale : contractSalesCompleted) {
                    if (Objects.equals(customerBrokerSale.getTransactionId(), customerBrokerContractSale.getNegotiatiotId())) {
                        customerBrokerSale.setTransactionStatus(TransactionStatus.COMPLETED);
                        userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.saveCustomerBrokerSaleTransactionData(customerBrokerSale);
                        broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_CONTRACT_UPDATE_VIEW);
                        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, brokerWalletPublicKey, CBPBroadcasterConstants.CBW_CONTRACT_COMPLETED_NOTIFICATION);
                    }
                }
            }

        } catch (CantGetListSaleNegotiationsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (MissingCustomerBrokerSaleDataException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantOpenContractException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetListCustomerBrokerContractSaleException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantCloseContractException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetCryptoBrokerWalletSettingException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CryptoBrokerWalletNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantCreateCryptoMoneyRestockException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantCreateBankMoneyRestockException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantCreateCashMoneyRestockException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetListClauseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantUpdateCustomerBrokerContractSaleException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

    }

    private String getAccountNumberFromClause(Clause clause) {
        /* The account Account data that come from the clause have this format*/
        String clauseValue = clause.getValue();
        String[] split = clauseValue.split("\\D+:\\s*");
        return split.length == 1 ? split[0] : split[1];
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

    private float getMarketExchangeRate(String customerCurrency) throws CantGetExchangeRateException {
        //Find out if customerCurrency parameter is a FiatCurrency or a CryptoCurrency
        Currency currency = null;
        try {
            if (FiatCurrency.codeExists(customerCurrency))
                currency = FiatCurrency.getByCode(customerCurrency);
            else if (CryptoCurrency.codeExists(customerCurrency))
                currency = CryptoCurrency.getByCode(customerCurrency);
        } catch (Exception e) {
            throw new CantGetExchangeRateException();
        }

        if (currency == null)
            throw new CantGetExchangeRateException();


        CurrencyPair currencyPair = new CurrencyPairImpl(currency, FiatCurrency.US_DOLLAR);


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
