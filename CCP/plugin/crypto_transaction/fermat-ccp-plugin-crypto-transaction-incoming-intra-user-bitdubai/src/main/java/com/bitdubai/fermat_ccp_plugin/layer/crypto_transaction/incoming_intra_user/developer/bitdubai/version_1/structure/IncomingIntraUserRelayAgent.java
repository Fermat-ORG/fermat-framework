package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantGetCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAccessTransactionsException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantExecuteTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantGetTransactionsException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.TransactionCompleteInformation;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

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
    private BitcoinWalletManager      bitcoinWalletManager;
    private CryptoAddressBookManager  cryptoAddressBookManager;
    private IncomingIntraUserRegistry registry;



    public IncomingIntraUserRelayAgent(ErrorManager              errorManager,
                                       EventManager              eventManager,
                                       BitcoinWalletManager      bitcoinWalletManager,
                                       CryptoAddressBookManager  cryptoAddressBookManager,
                                       IncomingIntraUserRegistry registry) {

        this.registry                 = registry;
        this.errorManager             = errorManager;
        this.eventManager             = eventManager;
        this.bitcoinWalletManager     = bitcoinWalletManager;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
    }

    public void start() throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIncomingIntraUserRelayAgentException {
        this.relayAgent = new RelayAgent(bitcoinWalletManager, cryptoAddressBookManager, errorManager,eventManager, registry);
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
        private final BitcoinWalletManager          bitcoinWalletManager;
        private final CryptoAddressBookManager      cryptoAddressBookManager;
        private final IncomingIntraUserRegistry registry;
        private com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserTransactionHandler transactionHandler;

        private static final int SLEEP_TIME = 5000;

        public RelayAgent(final BitcoinWalletManager bitcoinWalletManager, final CryptoAddressBookManager cryptoAddressBookManager, final ErrorManager errorManager,EventManager eventManager, final IncomingIntraUserRegistry registry){
            this.registry                 = registry;
            this.errorManager             = errorManager;
            this.eventManager             = eventManager;
            this.bitcoinWalletManager     = bitcoinWalletManager;
            this.cryptoAddressBookManager = cryptoAddressBookManager;
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
            this.transactionHandler = new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserTransactionHandler(this.eventManager,this.bitcoinWalletManager,this.cryptoAddressBookManager);
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
                    } catch (IncomingIntraUserCantExecuteTransactionException | CantLoadWalletException | CantGetCryptoAddressBookRecordException | IncomingIntraUserCantAccessTransactionsException e) {
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
