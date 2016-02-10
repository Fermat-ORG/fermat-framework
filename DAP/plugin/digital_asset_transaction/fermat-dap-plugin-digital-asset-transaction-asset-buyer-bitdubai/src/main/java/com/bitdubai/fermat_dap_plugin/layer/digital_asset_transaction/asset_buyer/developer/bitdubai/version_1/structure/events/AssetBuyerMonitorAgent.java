package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetBuyerMonitorAgent extends FermatAgent {

    //VARIABLE DECLARATION
    private BuyerAgent buyerAgent;
    private ErrorManager errorManager;

    //CONSTRUCTORS

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            buyerAgent = new BuyerAgent();
            Thread agentThread = new Thread(buyerAgent, this.getClass().getSimpleName());
            agentThread.start();
            super.start();
        } catch (Exception e) {
            throw new CantStartAgentException(FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public void stop() throws CantStopAgentException {
        try {
            buyerAgent.stopAgent();
            buyerAgent = null; //RELEASE RESOURCES UNTIL WE START IT AGAIN.
            super.stop();
        } catch (Exception e) {
            throw new CantStopAgentException(FermatException.wrapException(e), null, null);
        }
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES

    private class BuyerAgent implements Runnable {
        private boolean agentRunning;
        private static final int WAIT_TIME = 5 * 1000; //SECONDS

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p/>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            while (agentRunning) {
                try {
                    doTheMainTask();
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.ASSET_BUYER, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                    agentRunning = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    errorManager.reportUnexpectedPluginException(Plugins.ASSET_BUYER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        }

        private void doTheMainTask() {

        }

        public boolean isAgentRunning() {
            return agentRunning;
        }

        public void stopAgent() {
            agentRunning = false;
        }

        public void startAgent() {
            agentRunning = true;
        }

    }
}
