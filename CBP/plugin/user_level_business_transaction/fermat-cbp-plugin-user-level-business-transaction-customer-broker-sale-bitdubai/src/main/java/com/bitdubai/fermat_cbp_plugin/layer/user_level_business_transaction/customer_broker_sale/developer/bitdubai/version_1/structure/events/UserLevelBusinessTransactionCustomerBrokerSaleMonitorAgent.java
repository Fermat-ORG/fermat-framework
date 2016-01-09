package com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
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
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndexManager;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.database.UserLevelBusinessTransactionCustomerBrokerSaleConstants;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.database.UserLevelBusinessTransactionCustomerBrokerSaleDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.exceptions.MissingCustomerBrokerSaleDataException;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.utils.CustomerBrokerSaleImpl;
import com.bitdubai.fermat_pip_api.layer.module.notification.interfaces.NotificationManagerMiddleware;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

/**
 * The Class <code>UserLevelBusinessTransactionCustomerBrokerSaleMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 15.12.15
 */
public class UserLevelBusinessTransactionCustomerBrokerSaleMonitorAgent implements Agent {
    //TODO: Documentar y manejo de excepciones.
    private Thread agentThread;
    private final ErrorManager errorManager;
    private final CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;
    private final UserLevelBusinessTransactionCustomerBrokerSaleDatabaseDao userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao;
    private final OpenContractManager openContractManager;
    private final CloseContractManager closeContractManager;
    private final CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    private final FiatIndexManager fiatIndexManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final BankMoneyRestockManager bankMoneyRestockManager;
    private final CashMoneyRestockManager cashMoneyRestockManager;
    private final CryptoMoneyRestockManager cryptoMoneyRestockManager;
    private final NotificationManagerMiddleware notificationManagerMiddleware;
    private CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread;
    private CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting;

    public UserLevelBusinessTransactionCustomerBrokerSaleMonitorAgent(ErrorManager errorManager,
                                                                      CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
                                                                      PluginDatabaseSystem pluginDatabaseSystem,
                                                                      UUID pluginId,
                                                                      OpenContractManager openContractManager,
                                                                      CloseContractManager closeContractManager,
                                                                      CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
                                                                      FiatIndexManager fiatIndexManager,
                                                                      CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                                      BankMoneyRestockManager bankMoneyRestockManager,
                                                                      CashMoneyRestockManager cashMoneyRestockManager,
                                                                      CryptoMoneyRestockManager cryptoMoneyRestockManager,
                                                                      NotificationManagerMiddleware notificationManagerMiddleware) {

        this.errorManager                                              = errorManager;
        this.customerBrokerSaleNegotiationManager                      = customerBrokerSaleNegotiationManager;
        this.openContractManager                                       = openContractManager;
        this.closeContractManager                                      = closeContractManager;
        this.customerBrokerContractSaleManager                         = customerBrokerContractSaleManager;
        this.fiatIndexManager                                          = fiatIndexManager;
        this.cryptoBrokerWalletManager                                 = cryptoBrokerWalletManager;
        this.bankMoneyRestockManager                                   = bankMoneyRestockManager;
        this.cashMoneyRestockManager                                   = cashMoneyRestockManager;
        this.cryptoMoneyRestockManager                                 = cryptoMoneyRestockManager;
        this.notificationManagerMiddleware                             = notificationManagerMiddleware;

        this.userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao = new UserLevelBusinessTransactionCustomerBrokerSaleDatabaseDao(pluginDatabaseSystem, pluginId);
        try {
            //TODO:Revisar este caso CryptoBrokerWalletAssociatedSetting va a devolver varios registros.
            this.cryptoBrokerWalletSettingSpread     = cryptoBrokerWalletManager.loadCryptoBrokerWallet("walletPublicKeyTest").getCryptoWalletSetting().getCryptoBrokerWalletSpreadSetting();
            if (!cryptoBrokerWalletManager.loadCryptoBrokerWallet("walletPublicKeyTest").getCryptoWalletSetting().getCryptoBrokerWalletAssociatedSettings().isEmpty())
            {
                this.cryptoBrokerWalletAssociatedSetting = cryptoBrokerWalletManager.loadCryptoBrokerWallet("walletPublicKeyTest").getCryptoWalletSetting().getCryptoBrokerWalletAssociatedSettings().get(0);
            }
        } catch (CantGetCryptoBrokerWalletSettingException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CryptoBrokerWalletNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }
    @Override
    public void start() throws CantStartAgentException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("Customer Broker Sale monitor agent starting");

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
        public final int DELAY_HOURS = 2;
        int iterationNumber = 0;
        boolean threadWorking;
        private BigDecimal priceReference = null;
        private BigDecimal amount         = null;
        private String bankAccount        = null;
        private FiatCurrency fiatCurrency = null;
        CustomerBrokerSaleImpl customerBrokerSale = null;
        //UserLevelBusinessTransactionCustomerBrokerSaleDatabaseDao userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao;

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
                    errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }

            }
        }

        private void doTheMainTask(){
            try {
                CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread = cryptoBrokerWalletManager.loadCryptoBrokerWallet("walletPublicKeyTest").getCryptoWalletSetting().getCryptoBrokerWalletSpreadSetting();
                //TODO:Revisar este caso CryptoBrokerWalletAssociatedSetting va a devolver varios registros.
                if (!cryptoBrokerWalletManager.loadCryptoBrokerWallet("walletPublicKeyTest").getCryptoWalletSetting().getCryptoBrokerWalletAssociatedSettings().isEmpty()) {
                    CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting = cryptoBrokerWalletManager.loadCryptoBrokerWallet("walletPublicKeyTest").getCryptoWalletSetting().getCryptoBrokerWalletAssociatedSettings().get(0);
                }
                //Se verifica el cierre de la negociacion
                for (CustomerBrokerSaleNegotiation records : customerBrokerSaleNegotiationManager.getNegotiationsByStatus(NegotiationStatus.CLOSED))
                {
                    //Buscar que la transaccion no se encuentre ya registrada
                    if(userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(getFilterTable(records.getNegotiationId().toString(), UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_ID_COLUMN_NAME)).isEmpty()) {
                        customerBrokerSale = new CustomerBrokerSaleImpl(records.getNegotiationId().toString(),
                                records.getNegotiationId().toString(),
                                0, null, null, TransactionStatus.IN_PROCESS, null, null, null);

                        userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.saveCustomerBrokerSaleTransactionData(customerBrokerSale);
                    }
                }
                //Se crea la business transaction
                for(CustomerBrokerSale customerBrokerSale : userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(getFilterTable(TransactionStatus.IN_PROCESS.getCode(), UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_PROCESS
                {
                    CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerSale.getTransactionId()));
                    //Registra el Open Contract siempre y cuando el Transaction_Status de la Transaction Customer Broker Sale este IN_PROCESS
                    openContractManager.openSaleContract(customerBrokerSaleNegotiation, fiatIndexManager.getCurrentIndex(fiatIndexManager.getReferenceCurrency()));
                    //Actualiza el Transaction_Status de la Transaction Customer Broker Sale a IN_OPEN_CONTRACT
                    customerBrokerSale.setTransactionStatus(TransactionStatus.IN_OPEN_CONTRACT);
                    userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.saveCustomerBrokerSaleTransactionData(customerBrokerSale);
                }
                for(CustomerBrokerSale customerBrokerSale : userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(getFilterTable(TransactionStatus.IN_OPEN_CONTRACT.getCode(), UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_OPEN_CONTRACT
                {
                    for(CustomerBrokerContractSale customerBrokerContractSale : customerBrokerContractSaleManager.getCustomerBrokerContractSaleForStatus(ContractStatus.PENDING_PAYMENT))
                    {
                        if (customerBrokerSale.getTransactionId() == customerBrokerContractSale.getNegotiatiotId())
                        {
                            customerBrokerSale.setTransactionStatus(TransactionStatus.IN_CONTRACT_SUBMIT);
                            userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.saveCustomerBrokerSaleTransactionData(customerBrokerSale);
                        }
                    }
                }
                //Se verifica las condiciones del contrato y el status
                for(CustomerBrokerSale customerBrokerSale : userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(getFilterTable(TransactionStatus.IN_CONTRACT_SUBMIT.getCode(), UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_CONTRACT_SUBMIT
                {
                    for(CustomerBrokerContractSale customerBrokerContractSale : customerBrokerContractSaleManager.getCustomerBrokerContractSaleForStatus(ContractStatus.PENDING_PAYMENT))
                    {
                        if (customerBrokerSale.getTransactionId() == customerBrokerContractSale.getNegotiatiotId())
                        {
                            //Si la fecha del contracto se acerca al dia y 2 horas antes de vencerse debo de elevar un evento de notificacion siempre y cuando el ContractStatus sea igual a PENDING_PAYMENT
                            Date date = null;
                            long timeStampToday =  ((customerBrokerContractSale.getDateTime() - date.getTime()) / 60) / 60;
                            if (timeStampToday <= DELAY_HOURS)
                            {
                                customerBrokerContractSaleManager.updateContractNearExpirationDatetime(customerBrokerContractSale.getContractId(), true);
                                notificationManagerMiddleware.addPopUpNotification(EventSource.BUSINESS_TRANSACTION_OPEN_CONTRACT, "Review Negotiation");
                            }
                        }
                    }
                }
                //Se sigue verificando el estatus del contrato hasta que se consiga la realización de un pago
                for(CustomerBrokerSale customerBrokerSale : userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(getFilterTable(TransactionStatus.IN_CONTRACT_SUBMIT.getCode(), UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_CONTRACT_SUBMIT
                {
                    for(CustomerBrokerContractSale customerBrokerContractSale : customerBrokerContractSaleManager.getCustomerBrokerContractSaleForStatus(ContractStatus.PAYMENT_SUBMIT))
                    {
                        if (customerBrokerSale.getTransactionId() == customerBrokerContractSale.getNegotiatiotId())
                        {
                            //Si se detecta la realización de un pago se procede actulizar el estatus de la transacción y a monitorear la llegada de la mercadería.
                            //Se verifica si el broker configuró procesar Restock de manera automática
                            int sw = 0;
                            if (cryptoBrokerWalletSettingSpread.getRestockAutomatic())
                            {
                                //Recorrer las clausulas del contrato
                                CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(customerBrokerContractSale.getNegotiatiotId()));
                                for (ContractClause contractClause : customerBrokerContractSale.getContractClause())
                                {
                                    if (contractClause.getType().getCode() == ContractClauseType.CRYPTO_TRANSFER.getCode())
                                    {
                                        sw = 1;
                                    }
                                    if (contractClause.getType().getCode() == ContractClauseType.BANK_TRANSFER.getCode())
                                    {
                                        sw = 2;
                                    }
                                    if (contractClause.getType().getCode() == ContractClauseType.CASH_DELIVERY  .getCode() && contractClause.getType().getCode() == ContractClauseType.CASH_ON_HAND.getCode())
                                    {
                                        sw = 3;
                                    }
                                }
                                //TODO: Datos de amount, priceReference, CryptoCurrency, FiatCurrency, cbpWalletPublicKey, bnkWalletPublicKey, cshWalletPublicKey, cryWalletPublicKey de donde sale
                                //
                                for (Clause clause : customerBrokerSaleNegotiation.getClauses())
                                {
                                    if (clause.getType().getCode() == ClauseType.EXCHANGE_RATE.getCode())
                                    {
                                        priceReference = new BigDecimal(clause.getValue());
                                    }
                                    if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY_QUANTITY.getCode())
                                    {
                                        amount = new BigDecimal(clause.getValue());
                                    }
                                    if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY_QUANTITY.getCode())
                                    {
                                        amount = new BigDecimal(clause.getValue());
                                    }
                                    if (clause.getType().getCode() == ClauseType.BROKER_BANK_ACCOUNT.getCode())
                                    {
                                        bankAccount = clause.getValue();
                                    }
                                    if (clause.getType().getCode() == ClauseType.BROKER_CURRENCY.getCode())
                                    {
                                        fiatCurrency = FiatCurrency.valueOf(clause.getValue());
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
                                            OriginTransaction.RESTOCK_AUTOMATIC);
                                }
                                //
                                if (sw == 2) {
                                    bankMoneyRestockManager.createTransactionRestock(customerBrokerContractSale.getPublicKeyBroker(),
                                            fiatCurrency,
                                            "walletPublicKey",
                                            "walletPublicKey",
                                            bankAccount,
                                            amount,
                                            "RESTOCK AUTOMATIC",
                                            priceReference,
                                            OriginTransaction.RESTOCK_AUTOMATIC);
                                }
                                //
                                if (sw == 3) {
                                    cashMoneyRestockManager.createTransactionRestock(customerBrokerContractSale.getPublicKeyBroker(),
                                            fiatCurrency,
                                            "walletPublicKey",
                                            "walletPublicKey",
                                            "cashReference",
                                            amount,
                                            "memo",
                                            priceReference,
                                            OriginTransaction.RESTOCK_AUTOMATIC);
                                }
                            }
                            customerBrokerSale.setTransactionStatus(TransactionStatus.IN_PAYMENT_SUBMIT);
                            userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.saveCustomerBrokerSaleTransactionData(customerBrokerSale);
                        }
                    }
                }
                for(CustomerBrokerSale customerBrokerSale : userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(getFilterTable(TransactionStatus.IN_PAYMENT_SUBMIT.getCode(), UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_PAYMENT_SUBMIT
                {
                    for(CustomerBrokerContractSale customerBrokerContractSale : customerBrokerContractSaleManager.getCustomerBrokerContractSaleForStatus(ContractStatus.PENDING_MERCHANDISE))
                    {
                        if (customerBrokerSale.getTransactionId() == customerBrokerContractSale.getNegotiatiotId())
                        {
                            customerBrokerSale.setTransactionStatus(TransactionStatus.IN_PENDING_MERCHANDISE);
                            userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.saveCustomerBrokerSaleTransactionData(customerBrokerSale);
                        }
                    }
                }
                for(CustomerBrokerSale customerBrokerSale : userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(getFilterTable(TransactionStatus.IN_PENDING_MERCHANDISE.getCode(), UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_PENDING_MERCHANDISE
                {
                    for(CustomerBrokerContractSale customerBrokerContractSale : customerBrokerContractSaleManager.getCustomerBrokerContractSaleForStatus(ContractStatus.PENDING_MERCHANDISE))
                    {
                        if (customerBrokerSale.getTransactionId() == customerBrokerContractSale.getNegotiatiotId())
                        {
                            //Si se acerca la tiempo límite para recibir la mercadería y esta no ha sido registrada como recibida, se eleva un evento de notificación
                            Date date = null;
                            long timeStampToday =  ((customerBrokerContractSale.getDateTime() - date.getTime()) / 60) / 60;
                            if (timeStampToday <= DELAY_HOURS)
                            {
                                customerBrokerContractSaleManager.updateContractNearExpirationDatetime(customerBrokerContractSale.getContractId(), true);
                                notificationManagerMiddleware.addPopUpNotification(EventSource.BUSINESS_TRANSACTION_OPEN_CONTRACT, "Review Negotiation");
                            }
                        }
                    }
                }
                for(CustomerBrokerSale customerBrokerSale : userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(getFilterTable(TransactionStatus.IN_PENDING_MERCHANDISE.getCode(), UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_PENDING_MERCHANDISE
                {

                    for(CustomerBrokerContractSale customerBrokerContractSale : customerBrokerContractSaleManager.getCustomerBrokerContractSaleForStatus(ContractStatus.MERCHANDISE_SUBMIT))
                    {
                        if (customerBrokerSale.getTransactionId() == customerBrokerContractSale.getNegotiatiotId())
                        {
                            customerBrokerSale.setTransactionStatus(TransactionStatus.IN_MERCHANDISE_SUBMIT);
                            userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.saveCustomerBrokerSaleTransactionData(customerBrokerSale);
                        }
                    }
                }
                for(CustomerBrokerSale customerBrokerSale : userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(getFilterTable(TransactionStatus.IN_MERCHANDISE_SUBMIT.getCode(), UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_MERCHANDISE_SUBMIT
                {
                    //Comienzo a recorrer todas las transacciones que esten en Transaction_Status IN_MERCHANDISE_SUBMIT
                    //Registra el Close Contract siempre y cuando el Transaction_Status de la Transaction Customer Broker Sale este IN_MERCHANDISE_SUBMIT
                    for(CustomerBrokerContractSale customerBrokerContractSale : customerBrokerContractSaleManager.getCustomerBrokerContractSaleForStatus(ContractStatus.MERCHANDISE_SUBMIT))
                    {
                        if (customerBrokerSale.getTransactionId() == customerBrokerContractSale.getNegotiatiotId())
                        {
                            closeContractManager.closeSaleContract(customerBrokerContractSale.getContractId());
                        }
                    }
                }
                for(CustomerBrokerSale customerBrokerSale : userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.getCustomerBrokerSales(getFilterTable(TransactionStatus.IN_MERCHANDISE_SUBMIT.getCode(), UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_STATUS_COLUMN_NAME))) //IN_MERCHANDISE_SUBMIT
                {
                    for(CustomerBrokerContractSale customerBrokerContractSale : customerBrokerContractSaleManager.getCustomerBrokerContractSaleForStatus(ContractStatus.COMPLETED))
                    {
                        customerBrokerSale.setTransactionStatus(TransactionStatus.COMPLETED);
                        userLevelBusinessTransactionCustomerBrokerSaleDatabaseDao.saveCustomerBrokerSaleTransactionData(customerBrokerSale);
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
            } catch (CantGetIndexException e) {
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
                errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_SALE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);;
            }
        }

        private DatabaseTableFilter getFilterTable(final String valueFilter, final String columnValue)
        {
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
    }
}
