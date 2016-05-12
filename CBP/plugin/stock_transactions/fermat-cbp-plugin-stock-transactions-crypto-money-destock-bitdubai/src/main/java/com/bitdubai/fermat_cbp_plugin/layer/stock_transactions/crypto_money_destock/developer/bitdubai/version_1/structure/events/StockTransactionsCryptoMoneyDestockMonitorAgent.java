package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CryptoMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockBalance;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.StockTransactionCryptoMoneyDestockFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.StockTransactionCryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.utils.CryptoTransactionParametersWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.utils.WalletTransactionWrapper;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransactionManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;


/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class StockTransactionsCryptoMoneyDestockMonitorAgent extends FermatAgent {
    //TODO: Documentar y manejo de excepciones.
    //TODO: Manejo de Eventos

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final StockTransactionCryptoMoneyDestockManager stockTransactionCryptoMoneyDestockManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final CryptoHoldTransactionManager cryptoHoldTransactionManager;
    private final StockTransactionCryptoMoneyDestockFactory stockTransactionCryptoMoneyDestockFactory;
    private UUID pluginId;

    public final int SLEEP_TIME = 5000;

    public StockTransactionsCryptoMoneyDestockMonitorAgent(ErrorManager errorManager,
                                                           StockTransactionCryptoMoneyDestockManager stockTransactionCryptoMoneyDestockManager,
                                                           CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                           CryptoHoldTransactionManager cryptoHoldTransactionManager,
                                                           PluginDatabaseSystem pluginDatabaseSystem,
                                                           UUID pluginId) {

        this.errorManager = errorManager;
        this.stockTransactionCryptoMoneyDestockManager = stockTransactionCryptoMoneyDestockManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.cryptoHoldTransactionManager = cryptoHoldTransactionManager;
        this.stockTransactionCryptoMoneyDestockFactory = new StockTransactionCryptoMoneyDestockFactory(pluginDatabaseSystem, pluginId);
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
        LOG.info("Bank Money Restock Transaction monitor agent starting");

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
//        int iterationNumber = 0;
//        boolean threadWorking;
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
//                    errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                }
//
//            }
//        }
//    }
    private void doTheMainTask() {
        try {
            // I define the filter to null for all
            for (CryptoMoneyTransaction cryptoMoneyTransaction : stockTransactionCryptoMoneyDestockFactory.getCryptoMoneyTransactionList(null)) {

                cryptoMoneyTransaction.setCbpWalletPublicKey("walletPublicKeyTest"); //TODO:Solo para testear
                final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(cryptoMoneyTransaction.getCbpWalletPublicKey());
                final StockBalance stockBalance = cryptoBrokerWallet.getStockBalance();

                switch (cryptoMoneyTransaction.getTransactionStatus()) {
                    //TODO: Hacer un case que vea el status REJECTED para reversar la operacion en la wallet, si es credito se hace un debito, si debito se hace un credito
                    case INIT_TRANSACTION:
                        cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);
                        stockTransactionCryptoMoneyDestockFactory.saveCryptoMoneyDestockTransactionData(cryptoMoneyTransaction);
                        break;
                    case IN_WALLET:
                        try {
                            WalletTransactionWrapper walletTransactionRecordBook = new WalletTransactionWrapper(
                                    UUID.randomUUID(),
                                    cryptoMoneyTransaction.getCryptoCurrency(),
                                    BalanceType.BOOK,
                                    TransactionType.DEBIT,
                                    MoneyType.CRYPTO,
                                    cryptoMoneyTransaction.getCbpWalletPublicKey(),
                                    cryptoMoneyTransaction.getActorPublicKey(),
                                    cryptoMoneyTransaction.getAmount(),
                                    new Date().getTime() / 1000,
                                    cryptoMoneyTransaction.getConcept(),
                                    cryptoMoneyTransaction.getPriceReference(),
                                    cryptoMoneyTransaction.getOriginTransaction(),
                                    cryptoMoneyTransaction.getOriginTransactionId(),
                                    false);

                            WalletTransactionWrapper walletTransactionRecordAvailable = new WalletTransactionWrapper(
                                    UUID.randomUUID(),
                                    cryptoMoneyTransaction.getCryptoCurrency(),
                                    BalanceType.AVAILABLE,
                                    TransactionType.DEBIT,
                                    MoneyType.CRYPTO,
                                    cryptoMoneyTransaction.getCbpWalletPublicKey(),
                                    cryptoMoneyTransaction.getActorPublicKey(),
                                    cryptoMoneyTransaction.getAmount(),
                                    new Date().getTime() / 1000,
                                    cryptoMoneyTransaction.getConcept(),
                                    cryptoMoneyTransaction.getPriceReference(),
                                    cryptoMoneyTransaction.getOriginTransaction(),
                                    cryptoMoneyTransaction.getOriginTransactionId(),
                                    false);

                            stockBalance.debit(walletTransactionRecordBook, BalanceType.BOOK);
                            stockBalance.debit(walletTransactionRecordAvailable, BalanceType.AVAILABLE);
                            cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_UNHOLD);
                            stockTransactionCryptoMoneyDestockFactory.saveCryptoMoneyDestockTransactionData(cryptoMoneyTransaction);

                        } catch (FermatException e) {
                            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_MONEY_DESTOCK,
                                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                        }
                        cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_UNHOLD);
                        stockTransactionCryptoMoneyDestockFactory.saveCryptoMoneyDestockTransactionData(cryptoMoneyTransaction);
                        break;
                    case IN_UNHOLD:
                        CryptoTransactionParametersWrapper cryptoTransactionParametersWrapper = new CryptoTransactionParametersWrapper(
                                cryptoMoneyTransaction.getTransactionId(),
                                cryptoMoneyTransaction.getCryptoCurrency(),
                                cryptoMoneyTransaction.getCryWalletPublicKey(),
                                cryptoMoneyTransaction.getActorPublicKey(),
                                cryptoMoneyTransaction.getAmount(),
                                cryptoMoneyTransaction.getMemo(),
                                pluginId.toString(),
                                BlockchainNetworkType.getDefaultBlockchainNetworkType()); //TODO: Debe ser persitido en la base de datos de Stock/Restock

                        cryptoHoldTransactionManager.createCryptoHoldTransaction(cryptoTransactionParametersWrapper);
                        cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_EJECUTION);
                        stockTransactionCryptoMoneyDestockFactory.saveCryptoMoneyDestockTransactionData(cryptoMoneyTransaction);
                        break;
                    case IN_EJECUTION:
                        CryptoTransactionStatus cryptoTransactionStatus = cryptoHoldTransactionManager.getCryptoHoldTransactionStatus(cryptoMoneyTransaction.getTransactionId());
                        if (cryptoTransactionStatus != null) {
                            if (Objects.equals(CryptoTransactionStatus.CONFIRMED.getCode(), cryptoTransactionStatus.getCode())) {
                                cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                                stockTransactionCryptoMoneyDestockFactory.saveCryptoMoneyDestockTransactionData(cryptoMoneyTransaction);
                            }
                            if (Objects.equals(CryptoTransactionStatus.REJECTED.getCode(), cryptoTransactionStatus.getCode())) {
                                cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.REJECTED);
                                stockTransactionCryptoMoneyDestockFactory.saveCryptoMoneyDestockTransactionData(cryptoMoneyTransaction);
                            }
                        }
                        break;
                    case REJECTED:
                        WalletTransactionWrapper walletTransactionRecord = new WalletTransactionWrapper(
                                UUID.randomUUID(),
                                cryptoMoneyTransaction.getCryptoCurrency(),
                                BalanceType.AVAILABLE,
                                TransactionType.DEBIT,
                                MoneyType.CRYPTO,
                                cryptoMoneyTransaction.getCbpWalletPublicKey(),
                                cryptoMoneyTransaction.getActorPublicKey(),
                                cryptoMoneyTransaction.getAmount(),
                                new Date().getTime() / 1000,
                                cryptoMoneyTransaction.getConcept(),
                                cryptoMoneyTransaction.getPriceReference(),
                                cryptoMoneyTransaction.getOriginTransaction(),
                                cryptoMoneyTransaction.getOriginTransactionId(),
                                false);

                        stockBalance.debit(walletTransactionRecord, BalanceType.BOOK);
                        stockBalance.debit(walletTransactionRecord, BalanceType.AVAILABLE);
                        cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                        stockTransactionCryptoMoneyDestockFactory.saveCryptoMoneyDestockTransactionData(cryptoMoneyTransaction);
                        break;
                }
            }
        } catch (FermatException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_MONEY_DESTOCK,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }
}
