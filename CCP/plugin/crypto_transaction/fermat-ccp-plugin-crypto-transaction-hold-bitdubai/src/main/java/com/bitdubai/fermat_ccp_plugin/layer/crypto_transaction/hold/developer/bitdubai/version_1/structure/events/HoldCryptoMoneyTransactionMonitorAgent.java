package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransaction;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.database.HoldCryptoMoneyTransactionDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.exceptions.MissingHoldCryptoDataException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.structure.HoldCryptoMoneyTransactionManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.utils.BitcoinWalletTransactionRecordImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * The Class <code>HoldCryptoMoneyTransactionMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 23/11/15.
 */
public class HoldCryptoMoneyTransactionMonitorAgent extends FermatAgent {
    //TODO: Documentar y manejo de excepciones
    //TODO: Manejo de Eventos

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final HoldCryptoMoneyTransactionManager holdCryptoMoneyTransactionManager;
    private final BitcoinWalletManager bitcoinWalletManager;

    public final int SLEEP_TIME = 5000;


    public HoldCryptoMoneyTransactionMonitorAgent(ErrorManager errorManager,
                                                  HoldCryptoMoneyTransactionManager holdCryptoMoneyTransactionManager,
                                                  BitcoinWalletManager bitcoinWalletManager) {

        this.errorManager                      = errorManager;
        this.holdCryptoMoneyTransactionManager = holdCryptoMoneyTransactionManager;
        this.bitcoinWalletManager              = bitcoinWalletManager;

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
        LOG.info("Hold Crypto Transaction monitor agent starting");

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
//                    errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                }
//
//            }
//        }
//    }

    private void doTheMainTask(){
        // I define the filter
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
                return HoldCryptoMoneyTransactionDatabaseConstants.HOLD_STATUS_COLUMN_NAME;
            }

            @Override
            public String getValue() {
                return CryptoTransactionStatus.ACKNOWLEDGED.getCode();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };

        try {
            long availableBalance = 0;
            for (CryptoHoldTransaction cryptoHoldTransaction : holdCryptoMoneyTransactionManager.getHoldCryptoMoneyTransactionList(filter)){
                //TODO: Buscar el saldo disponible en la Bitcoin Wallet
                availableBalance = bitcoinWalletManager.loadWallet(cryptoHoldTransaction.getPublicKeyWallet()).getBalance(BalanceType.AVAILABLE).getBalance();

                if(availableBalance >= cryptoHoldTransaction.getAmount()) {
                    //TODO: llamar metodo Hold de la wallet que se implementara luego que se pruebe las wallet CSH y BNK;
                    CryptoAddress cryptoAddress = new CryptoAddress(cryptoHoldTransaction.getPublicKeyActor(), CryptoCurrency.BITCOIN);
                    BitcoinWalletTransactionRecordImpl bitcoinWalletTransactionRecord = new BitcoinWalletTransactionRecordImpl(UUID.randomUUID(),
                            cryptoHoldTransaction.getTransactionId(),
                            cryptoHoldTransaction.getPublicKeyActor(),
                            cryptoHoldTransaction.getPublicKeyActor(),
                            Actors.CBP_CRYPTO_BROKER,
                            Actors.CBP_CRYPTO_BROKER,
                            cryptoHoldTransaction.getTransactionId().toString(), //Hash
                            cryptoAddress, //addressFrom
                            cryptoAddress, //addressTo
                            (long)cryptoHoldTransaction.getAmount(),
                            new Date().getTime() / 1000,
                            "HOLD",
                            cryptoHoldTransaction.getBlockchainNetworkType()); //TODO:Esto debe venir en la transaccion que a su vez se le debe pasar desde la Crypto Broker Wallet

                    bitcoinWalletManager.loadWallet(cryptoHoldTransaction.getPublicKeyWallet()).getBalance(BalanceType.AVAILABLE).credit(bitcoinWalletTransactionRecord);
                    cryptoHoldTransaction.setStatus(CryptoTransactionStatus.CONFIRMED);
                    cryptoHoldTransaction.setTimestampAcknowledged(new Date().getTime() / 1000);
                    holdCryptoMoneyTransactionManager.saveHoldCryptoMoneyTransactionData(cryptoHoldTransaction);
                }else{
                    cryptoHoldTransaction.setStatus(CryptoTransactionStatus.REJECTED);
                    cryptoHoldTransaction.setMemo("REJECTED AVAILABLE BALANCE");
                    cryptoHoldTransaction.setTimestampConfirmedRejected(new Date().getTime() / 1000);
                    holdCryptoMoneyTransactionManager.saveHoldCryptoMoneyTransactionData(cryptoHoldTransaction);
                }
            }
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (MissingHoldCryptoDataException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantCalculateBalanceException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantRegisterCreditException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }
}
