package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CashMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockBalance;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyDestockFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.utils.CashTransactionParametersWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.utils.WalletTransactionWrapper;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransactionManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;


/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class StockTransactionsCashMoneyDestockMonitorAgent extends FermatAgent {
    //TODO: Manejo de Eventos

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final CashUnholdTransactionManager cashUnholdTransactionManager;
    private final StockTransactionCashMoneyDestockFactory stockTransactionCashMoneyDestockFactory;
    private UUID pluginId;
    public final int SLEEP_TIME = 5000;

    public StockTransactionsCashMoneyDestockMonitorAgent(ErrorManager errorManager,
                                                         CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                         CashUnholdTransactionManager cashUnholdTransactionManager,
                                                         PluginDatabaseSystem pluginDatabaseSystem,
                                                         UUID pluginId) {

        this.errorManager = errorManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.cashUnholdTransactionManager = cashUnholdTransactionManager;
        stockTransactionCashMoneyDestockFactory = new StockTransactionCashMoneyDestockFactory(pluginDatabaseSystem, pluginId);
        this.pluginId = pluginId;

        this.agentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    process();
            }
        }, this.getClass().getSimpleName());
    }

    @Override
    public void start() throws CantStartAgentException {
        Logger LOG = Logger.getGlobal();
        LOG.info("Cash Money Destock Transaction monitor agent starting");

        this.agentThread.start();
        this.status = AgentStatus.STARTED;
    }

    @Override
    public void stop() {
        if (isRunning())
            this.agentThread.interrupt();
        this.status = AgentStatus.STOPPED;
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

    private void doTheMainTask() {
        try {
            // I define the filter to null for all
            for (CashMoneyTransaction cashMoneyTransaction : stockTransactionCashMoneyDestockFactory.getCashMoneyTransactionList(null)) {

                cashMoneyTransaction.setCbpWalletPublicKey("walletPublicKeyTest"); //TODO:Solo para testear
                final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(cashMoneyTransaction.getCbpWalletPublicKey());
                final StockBalance stockBalance = cryptoBrokerWallet.getStockBalance();

                switch (cashMoneyTransaction.getTransactionStatus()) {
                    case INIT_TRANSACTION:
                        //Llamar al metodo de la interfaz public del manager de Bank Hold
                        //Luego cambiar el status al registro de la transaccion leido
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);
                        stockTransactionCashMoneyDestockFactory.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
                        break;
                    case IN_WALLET: {
                        //Llamar al metodo de la interfaz public del manager de la wallet CBP
                        //Luego cambiar el status al registro de la transaccion leido
                        //Buscar el regsitro de la transaccion en manager de la wallet si lo consigue entonces le cambia el status de COMPLETED
                        WalletTransactionWrapper walletTransactionRecordBook = new WalletTransactionWrapper(
                                UUID.randomUUID(),
                                cashMoneyTransaction.getFiatCurrency(),
                                BalanceType.BOOK,
                                TransactionType.DEBIT,
                                MoneyType.CASH_DELIVERY,
                                cashMoneyTransaction.getCbpWalletPublicKey(),
                                cashMoneyTransaction.getActorPublicKey(),
                                cashMoneyTransaction.getAmount(),
                                new Date().getTime() / 1000,
                                cashMoneyTransaction.getConcept(),
                                cashMoneyTransaction.getPriceReference(),
                                cashMoneyTransaction.getOriginTransaction(),
                                cashMoneyTransaction.getOriginTransactionId(),
                                false);

                        WalletTransactionWrapper walletTransactionRecordAvailable = new WalletTransactionWrapper(
                                UUID.randomUUID(),
                                cashMoneyTransaction.getFiatCurrency(),
                                BalanceType.AVAILABLE,
                                TransactionType.DEBIT,
                                MoneyType.CASH_DELIVERY,
                                cashMoneyTransaction.getCbpWalletPublicKey(),
                                cashMoneyTransaction.getActorPublicKey(),
                                cashMoneyTransaction.getAmount(),
                                new Date().getTime() / 1000,
                                cashMoneyTransaction.getConcept(),
                                cashMoneyTransaction.getPriceReference(),
                                cashMoneyTransaction.getOriginTransaction(),
                                cashMoneyTransaction.getOriginTransactionId(),
                                false);

                        stockBalance.debit(walletTransactionRecordBook, BalanceType.BOOK);
                        stockBalance.debit(walletTransactionRecordAvailable, BalanceType.AVAILABLE);
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_UNHOLD);
                        stockTransactionCashMoneyDestockFactory.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
                    }
                    break;
                    case IN_UNHOLD:
                        //Llamar al metodo de la interfaz public del manager de la wallet CBP
                        //Luego cambiar el status al registro de la transaccion leido
                        //Buscar el regsitro de la transaccion en manager de Bank Hold y si lo consigue entonces le cambia el status de IN_WALLET y hace el credito
                        CashTransactionParametersWrapper cashTransactionParametersWrapper = new CashTransactionParametersWrapper(
                                cashMoneyTransaction.getTransactionId(),
                                cashMoneyTransaction.getFiatCurrency(),
                                cashMoneyTransaction.getCashWalletPublicKey(),
                                cashMoneyTransaction.getActorPublicKey(),
                                cashMoneyTransaction.getAmount(),
                                cashMoneyTransaction.getMemo(),
                                pluginId.toString());

                        if (!cashUnholdTransactionManager.isTransactionRegistered(cashMoneyTransaction.getTransactionId()))
                            cashUnholdTransactionManager.createCashUnholdTransaction(cashTransactionParametersWrapper);

                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_EJECUTION);
                        stockTransactionCashMoneyDestockFactory.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
                        break;
                    case IN_EJECUTION:
                        CashTransactionStatus castTransactionStatus = cashUnholdTransactionManager.getCashUnholdTransactionStatus(cashMoneyTransaction.getTransactionId());

                        if (CashTransactionStatus.CONFIRMED == castTransactionStatus) {
                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                            stockTransactionCashMoneyDestockFactory.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
                        }
                        if (CashTransactionStatus.REJECTED == castTransactionStatus) {
                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.REJECTED);
                            stockTransactionCashMoneyDestockFactory.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
                        }
                        break;
                    case REJECTED: {
                        WalletTransactionWrapper walletTransactionRecordBook = new WalletTransactionWrapper(
                                UUID.randomUUID(),
                                cashMoneyTransaction.getFiatCurrency(),
                                BalanceType.BOOK,
                                TransactionType.CREDIT,
                                MoneyType.CASH_DELIVERY,
                                cashMoneyTransaction.getCbpWalletPublicKey(),
                                cashMoneyTransaction.getActorPublicKey(),
                                cashMoneyTransaction.getAmount(),
                                new Date().getTime() / 1000,
                                cashMoneyTransaction.getConcept(),
                                cashMoneyTransaction.getPriceReference(),
                                cashMoneyTransaction.getOriginTransaction(),
                                cashMoneyTransaction.getOriginTransactionId(),
                                false);

                        WalletTransactionWrapper walletTransactionRecordAvailable = new WalletTransactionWrapper(
                                UUID.randomUUID(),
                                cashMoneyTransaction.getFiatCurrency(),
                                BalanceType.AVAILABLE,
                                TransactionType.CREDIT,
                                MoneyType.CASH_DELIVERY,
                                cashMoneyTransaction.getCbpWalletPublicKey(),
                                cashMoneyTransaction.getActorPublicKey(),
                                cashMoneyTransaction.getAmount(),
                                new Date().getTime() / 1000,
                                cashMoneyTransaction.getConcept(),
                                cashMoneyTransaction.getPriceReference(),
                                cashMoneyTransaction.getOriginTransaction(),
                                cashMoneyTransaction.getOriginTransactionId(),
                                false);

                        stockBalance.credit(walletTransactionRecordBook, BalanceType.BOOK);
                        stockBalance.credit(walletTransactionRecordAvailable, BalanceType.AVAILABLE);
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                        stockTransactionCashMoneyDestockFactory.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
                        break;
                    }
                }
            }
        } catch (FermatException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }
}
