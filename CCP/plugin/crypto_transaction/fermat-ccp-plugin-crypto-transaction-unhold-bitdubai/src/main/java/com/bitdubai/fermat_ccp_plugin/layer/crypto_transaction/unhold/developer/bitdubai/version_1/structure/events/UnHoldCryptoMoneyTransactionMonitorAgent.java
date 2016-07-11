package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.unhold.interfaces.CryptoUnholdTransaction;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.database.UnHoldCryptoMoneyTransactionDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.exceptions.MissingUnHoldCryptoDataException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.structure.UnHoldCryptoMoneyTransactionManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.utils.CryptoWalletTransactionRecordImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * The Class <code>UnHoldCryptoMoneyTransactionMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 23/11/15.
 */
public class UnHoldCryptoMoneyTransactionMonitorAgent extends FermatAgent {
    //TODO: Documentar y manejo de excepciones
    //TODO: Manejo de Eventos

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final UnHoldCryptoMoneyTransactionManager unHoldCryptoMoneyTransactionManager;
    private final CryptoWalletManager cryptoWalletManager;

    public final int SLEEP_TIME = 5000;


    public UnHoldCryptoMoneyTransactionMonitorAgent(ErrorManager errorManager,
                                                    UnHoldCryptoMoneyTransactionManager unHoldCryptoMoneyTransactionManager,
                                                    CryptoWalletManager cryptoWalletManager) {

        this.errorManager                        = errorManager;
        this.unHoldCryptoMoneyTransactionManager = unHoldCryptoMoneyTransactionManager;
        this.cryptoWalletManager = cryptoWalletManager;

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
        LOG.info("UnHold Crypto Transaction monitor agent starting");

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
                return UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_STATUS_COLUMN_NAME;
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
            //I comment this line, is not used right now
            //long availableBalance = 0;
            for (CryptoUnholdTransaction cryptoUnholdTransaction : unHoldCryptoMoneyTransactionManager.getUnHoldCryptoMoneyTransactionList(filter)){
                //TODO: Buscar el saldo disponible en la Bitcoin Wallet
                //I comment this line, is not used right now
                //availableBalance = cryptoWalletManager.loadWallet(cryptoUnholdTransaction.getPublicKeyWallet()).getBalance(BalanceType.AVAILABLE).getBalance(cryptoUnholdTransaction.getBlockchainNetworkType());

                //TODO: Este if esta mal, el availableBalance no tiene nada que ver con permitir o no un unhold, pues un unhold es un credito.
                //if(availableBalance >= cryptoUnholdTransaction.getAmount()) {
                    //TODO: llamar metodo Hold de la wallet que se implementara luego que se pruebe las wallet CSH y BNK;

                long total = 0;

                FeeOrigin feeOrigin = cryptoUnholdTransaction.getFeeOrigin();
                if(feeOrigin==null){
                    feeOrigin = FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS;
                }

                long longBitcoinFee = cryptoUnholdTransaction.getFee();
                if(longBitcoinFee<BitcoinFee.SLOW.getFee()){
                    longBitcoinFee = BitcoinFee.SLOW.getFee();
                }

                if( feeOrigin.equals(FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS))
                    total = (long)cryptoUnholdTransaction.getAmount() + cryptoUnholdTransaction.getFee();
                else
                    total = (long)cryptoUnholdTransaction.getAmount() - cryptoUnholdTransaction.getFee();

                CryptoAddress cryptoAddress = new CryptoAddress(cryptoUnholdTransaction.getPublicKeyActor(), CryptoCurrency.BITCOIN);
                    CryptoWalletTransactionRecordImpl bitcoinWalletTransactionRecord = new CryptoWalletTransactionRecordImpl(UUID.randomUUID(),
                            cryptoUnholdTransaction.getTransactionId(),
                            cryptoUnholdTransaction.getPublicKeyActor(),
                            cryptoUnholdTransaction.getPublicKeyActor(),
                            Actors.CBP_CRYPTO_BROKER,
                            Actors.CBP_CRYPTO_BROKER,
                            cryptoUnholdTransaction.getTransactionId().toString(), //Hash
                            cryptoAddress, //addressFrom
                            cryptoAddress, //addressTo
                            (long)cryptoUnholdTransaction.getAmount(),
                            new Date().getTime(),
                            cryptoUnholdTransaction.getMemo(),
                            cryptoUnholdTransaction.getBlockchainNetworkType(), cryptoUnholdTransaction.getCurrency(),
                            longBitcoinFee,
                            feeOrigin, total);

                    cryptoWalletManager.loadWallet(cryptoUnholdTransaction.getPublicKeyWallet()).getBalance(BalanceType.AVAILABLE).credit(bitcoinWalletTransactionRecord);
                    cryptoUnholdTransaction.setStatus(CryptoTransactionStatus.CONFIRMED);
                    cryptoUnholdTransaction.setTimestampAcknowledged(new Date().getTime() / 1000);
                    unHoldCryptoMoneyTransactionManager.saveUnHoldCryptoMoneyTransactionData(cryptoUnholdTransaction);
                //}else{
                //    cryptoUnholdTransaction.setStatus(CryptoTransactionStatus.REJECTED);
                //    cryptoUnholdTransaction.setTimestampConfirmedRejected(new Date().getTime() / 1000);
                //    cryptoUnholdTransaction.setMemo("REJECTED AVAILABLE BALANCE");
                //    cryptoUnholdTransaction.setBlockChainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                //    unHoldCryptoMoneyTransactionManager.saveUnHoldCryptoMoneyTransactionData(cryptoUnholdTransaction);
                //}
            }
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_UNHOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_UNHOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (MissingUnHoldCryptoDataException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_UNHOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantLoadWalletsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_UNHOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } /*catch (CantCalculateBalanceException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_UNHOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }*/ catch (CantRegisterCreditException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_UNHOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_UNHOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }
}
