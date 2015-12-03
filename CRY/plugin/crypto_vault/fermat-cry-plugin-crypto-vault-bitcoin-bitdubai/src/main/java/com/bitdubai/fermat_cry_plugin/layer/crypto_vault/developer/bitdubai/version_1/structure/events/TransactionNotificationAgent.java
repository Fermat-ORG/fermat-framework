package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_api.layer.definition.enums.EventType;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinPlatformCryptoVaultPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.utils.EventsSelector;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.utils.TransactionTypeAndCryptoStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultTransactionNotificationAgent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.TransactionProtocolAgentMaxIterationsReachedException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;

import java.util.List;

/**
 * Created by rodrigo on 2015.06.18..
 * Modified by lnacosta (laion.cj91@gmail.com) on 15/10/2015.
 */
public class TransactionNotificationAgent extends FermatAgent {

    private static final long   SLEEP_TIME           = CryptoVaultTransactionNotificationAgent.AGENT_SLEEP_TIME    ;
    private static final int    ITERATIONS_THRESHOLD = CryptoVaultTransactionNotificationAgent.ITERATIONS_THRESHOLD;

    private int iteration = 0;

    private Thread   agentThread;

    private final LogManager   logManager  ;
    private final EventManager eventManager;
    private final ErrorManager errorManager;
    private final Database     database    ;

    /**
     * Constructor with final params...
     */
    public TransactionNotificationAgent(final EventManager eventManager,
                                        final ErrorManager errorManager,
                                        final LogManager   logManager  ,
                                        final Database     database    ){

        this.eventManager = eventManager;
        this.errorManager = errorManager;
        this.logManager   = logManager  ;
        this.database     = database    ;

        this.agentThread = new Thread(new Runnable() {
            @Override
            public void run() {

                logManager.log(BitcoinPlatformCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Transaction Protocol Notification Agent: running...", null, null);

                while (isRunning()) {

                    try {
                        agentThread.sleep(SLEEP_TIME);
                    } catch (InterruptedException interruptedException) {
                        return;
                    }

                    // Now we check if there are pending transactions to raise the events.
                    doTheMainTask();
                }
            }
        });
    }

    @Override
    public void start() throws CantStartAgentException {
        //here we start the thread to the transaction notification agent.
        this.agentThread.start();
        this.status = AgentStatus.STARTED;
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
        this.status = AgentStatus.STOPPED;
    }

    private void doTheMainTask() {

        try {

            // Increase the iteration counter
            iteration++;

            logManager.log(BitcoinPlatformCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iteration, null, null);

            // TO CONTROL WHAT TYPE OF EVENTS I WILL RAISE, MODIFY THE CLASS EVENT SELECTOR, AND SET THE EVENT OR A NULL.
            // NULL WILL NOT RAISE EVENTS.
            CryptoVaultDatabaseActions db = new CryptoVaultDatabaseActions(database, eventManager);

            List<TransactionTypeAndCryptoStatus> list = db.listTransactionTypeAndCryptoStatusToBeNotified();

            for (TransactionTypeAndCryptoStatus ttacs : list) {
                CryptoTransactionType type = ttacs.getTransactionType();
                CryptoStatus cryptoStatus = ttacs.getCryptoStatus();
                EventType eventType = EventsSelector.getEventType(type, cryptoStatus);

                if (eventType != null) {
                    logManager.log(BitcoinPlatformCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Found transactions pending to be notified in " + cryptoStatus.name() + " Status! Raising " + eventType.name() + " event.", null, null);

                    raiseEvent(eventType);

                    logManager.log(BitcoinPlatformCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "No other plugin is consuming Vault transactions.", "Transaction Protocol Notification Agent: iteration number " + iteration + " without other plugins consuming transaction.", null);
                    if (ITERATIONS_THRESHOLD < this.iteration) {
                        throw new TransactionProtocolAgentMaxIterationsReachedException("The max limit configured for the Transaction Protocol Agent has been reached.", null, "Iteration Limit: " + ITERATIONS_THRESHOLD, "Notify developer.");
                    }
                }
            }

            // there are no transactions pending. I will reset the counter to 0.
            if (!list.isEmpty()) {
                db.updateTransactionProtocolStatus(false);
                this.iteration = 0;
            } else {
                this.iteration = db.updateTransactionProtocolStatus(true);
            }

        } catch(Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    private void raiseEvent(EventType eventType) {
        FermatEvent event = eventManager.getNewEvent(eventType);
        event.setSource(BitcoinPlatformCryptoVaultPluginRoot.EVENT_SOURCE);
        eventManager.raiseEvent(event);
    }
}
