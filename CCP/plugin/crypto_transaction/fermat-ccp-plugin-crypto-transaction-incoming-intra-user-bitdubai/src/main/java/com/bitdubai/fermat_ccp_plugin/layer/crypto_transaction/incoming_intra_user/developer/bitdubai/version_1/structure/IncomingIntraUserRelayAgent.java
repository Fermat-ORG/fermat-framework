package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantGetCryptoAddressBookRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAccessTransactionsException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantExecuteTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantGetTransactionsException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.TransactionCompleteInformation;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by eze on 2015.09.04..
 */
public class IncomingIntraUserRelayAgent {

    private Thread                    agentThread;
    private RelayAgent                relayAgent;
    private ErrorManager              errorManager;
    private EventManager              eventManager;
    private CryptoWalletManager cryptoWalletManager;
    private CryptoAddressBookManager  cryptoAddressBookManager;
    private IncomingIntraUserRegistry registry;
    private  CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;
    private Broadcaster broadcaster;
    private BitcoinLossProtectedWalletManager lossProtectedWalletManager;



    public IncomingIntraUserRelayAgent(ErrorManager              errorManager,
                                       EventManager              eventManager,
                                       CryptoWalletManager cryptoWalletManager,
                                       CryptoAddressBookManager  cryptoAddressBookManager,
                                       IncomingIntraUserRegistry registry,
                                       CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager,
                                       Broadcaster broadcaster,
                                       BitcoinLossProtectedWalletManager lossProtectedWalletManager) {

        this.registry                 = registry;
        this.errorManager             = errorManager;
        this.eventManager             = eventManager;
        this.cryptoWalletManager = cryptoWalletManager;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
        this.broadcaster               = broadcaster;
        this.lossProtectedWalletManager  = lossProtectedWalletManager;

    }

    public void start() throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIncomingIntraUserRelayAgentException {
        this.relayAgent = new RelayAgent(cryptoWalletManager, cryptoAddressBookManager, errorManager,eventManager, registry,cryptoTransmissionNetworkServiceManager,broadcaster,lossProtectedWalletManager);
        try {
            this.relayAgent.initialize();
            this.agentThread = new Thread(this.relayAgent);
            this.agentThread.start();
        }
        catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIncomingIntraUserRelayAgentException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIncomingIntraUserRelayAgentException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "You should inspect the cause");
        }
    }

    public boolean isRunning(){
        return this.relayAgent != null && this.relayAgent.isRunning();
    }

    public void stop()  {
        if(isRunning())
            this.relayAgent.stop();
    }


    private static class RelayAgent implements Runnable  {

        private AtomicBoolean running = new AtomicBoolean(false);

        private final ErrorManager                  errorManager;
        private final EventManager                  eventManager;
        private final CryptoWalletManager cryptoWalletManager;
        private final CryptoAddressBookManager      cryptoAddressBookManager;
        private final IncomingIntraUserRegistry registry;
        private final CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;
        private final Broadcaster broadcaster;
        private final BitcoinLossProtectedWalletManager lossProtectedWalletManager;

        private com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserTransactionHandler transactionHandler;

        private static final int SLEEP_TIME = 10000;

        public RelayAgent(final CryptoWalletManager cryptoWalletManager, final CryptoAddressBookManager cryptoAddressBookManager, final ErrorManager errorManager,EventManager eventManager, final IncomingIntraUserRegistry registry, final CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager,Broadcaster broadcaster,BitcoinLossProtectedWalletManager lossProtectedWalletManager){
            this.registry                 = registry;
            this.errorManager             = errorManager;
            this.eventManager             = eventManager;
            this.cryptoWalletManager = cryptoWalletManager;
            this.cryptoAddressBookManager = cryptoAddressBookManager;
            this.cryptoTransmissionNetworkServiceManager  = cryptoTransmissionNetworkServiceManager;
            this.broadcaster             =  broadcaster;
            this.lossProtectedWalletManager = lossProtectedWalletManager;

        }

        public boolean isRunning(){
            return running.get();
        }

        public void stop(){
            running.set(false);
        }

        /**
         * MonitorAgent interface implementation.
         */
        private void initialize () {
            this.transactionHandler = new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserTransactionHandler(this.eventManager,this.cryptoWalletManager,this.cryptoAddressBookManager,cryptoTransmissionNetworkServiceManager, this.broadcaster,lossProtectedWalletManager);
        }

        /**
         * Runnable Interface implementation.
         */
        @Override
        public void run() {

            running.set(true);

            /**
             * Infinite loop.
             */
            while (running.get()) {
                /**
                 * Sleep for a while.
                 */
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                    break;
                }

                /**
                 * Now I do the main task.
                 */
                doTheMainTask();

                /**
                 * Check if I have been Interrupted.
                 */
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            cleanResources();
        }

        private void doTheMainTask() {
            try {
                List<TransactionCompleteInformation> responsibleTransactionList = new ArrayList<>();
                responsibleTransactionList.addAll(registry.getResponsibleTBATransactions());
                if (responsibleTransactionList.isEmpty())
                    return;
                System.out.println("TTF - INTRA USER RELAY: " + responsibleTransactionList.size() + " TRANSACTION(s) TO BE APPLIED");
                for (TransactionCompleteInformation transaction : responsibleTransactionList) {
                    try {
                        transactionHandler.handleTransaction(transaction);
                        registry.setToApplied(transaction.getCryptoTransaction().getTransactionID());
                        System.out.println("TTF - INTRA USER RELAY: TRANSACTION APPLIED");
                    } catch (IncomingIntraUserCantExecuteTransactionException | CantLoadWalletsException | CantGetCryptoAddressBookRecordException | IncomingIntraUserCantAccessTransactionsException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    }
                }
            } catch (IncomingIntraUserCantGetTransactionsException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            }
        }

        private void cleanResources() {

        }
    }
}
