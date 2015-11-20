package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

/**
 * Created by Alejandro Bicelis on 11/19/2015.
 */
public class CashMoneyTransactionHoldProcessorAgent extends FermatAgent {

    private static final int SLEEP = 5000;

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final CashMoneyTransactionHoldManager holdManager;

    public CashMoneyTransactionHoldProcessorAgent(final ErrorManager errorManager, final CashMoneyTransactionHoldManager holdManager) {
        this.errorManager = errorManager;
        this.holdManager = holdManager;

        this.agentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    process();
            }
        });

    }

    /**
     * TransactionAgent Interface implementation.
     */
    @Override
    public void start() {
        this.agentThread.start();
        this.status = AgentStatus.STARTED;
    }

    @Override
    public void stop() {
        if(isRunning())
            this.agentThread.interrupt();
        this.status = AgentStatus.STOPPED;
    }

    public void process() {
        /**
         * Infinite loop.
         */
        while (isRunning()) {

            /**
             * Sleep for a while.
             */
            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException interruptedException) {
                cleanResources();
                return;
            }

            /**
             * Now I do the main task.
             */
            doTheMainTask();

            /**
             * Check if I have been Interrupted.
             */
            if (agentThread.isInterrupted()) {
                cleanResources();
                return;
            }
        }
    }

    private void doTheMainTask() {
        //TODO: implement processorAgent tasks.
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }

}
