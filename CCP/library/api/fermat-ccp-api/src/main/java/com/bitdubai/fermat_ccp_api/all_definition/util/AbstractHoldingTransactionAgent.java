package com.bitdubai.fermat_ccp_api.all_definition.util;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.concurrent.TimeUnit;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/07/16.
 */
public abstract class AbstractHoldingTransactionAgent <T extends AbstractPlugin>
        extends AbstractAgent {

    protected T pluginRoot;

    /**
     * Default constructor with parameters
     * @param sleepTime
     * @param timeUnit
     * @param initDelayTime
     * @param pluginRoot
     */
    public AbstractHoldingTransactionAgent(
            long sleepTime,
            TimeUnit timeUnit,
            long initDelayTime,
            T pluginRoot) {
        super(sleepTime, timeUnit, initDelayTime);
        this.pluginRoot = pluginRoot;
    }

    @Override
    protected Runnable agentJob() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                doTheMainTask();
            }
        };
        return runnable;
    }

    @Override
    protected void onErrorOccur() {
        pluginRoot.reportError(
                UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                new Exception(this.getClass().getName()+" Error"));
    }

    /**
     * This method must implement the main method of the agent.
     */
    protected abstract void doTheMainTask();
}
