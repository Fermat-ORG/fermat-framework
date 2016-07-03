package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;

import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/07/16.
 */
public abstract class AbstractBusinessTransactionAgent
        <T extends AbstractPlugin>
        extends AbstractAgent {

    protected T pluginRoot;
    protected EventManager eventManager;

    /**
     * Default constructor with parameters
     * @param sleepTime
     * @param timeUnit
     * @param initDelayTime
     * @param pluginRoot
     */
    public AbstractBusinessTransactionAgent(
            long sleepTime,
            TimeUnit timeUnit,
            long initDelayTime,
            T pluginRoot,
            EventManager eventManager) {
        super(sleepTime, timeUnit, initDelayTime);
        this.pluginRoot = pluginRoot;
        this.eventManager = eventManager;
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

    /**
     * This method must implement the checked the pending events.
     * @param eventId
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    protected abstract void checkPendingEvent(String eventId)
            throws UnexpectedResultReturnedFromDatabaseException;

    /**
     * This method must implement ack confirmation event.
     * @param contractHash
     */
    protected abstract void raiseAckConfirmationEvent(String contractHash);

    /**
     * This method must implement ack confirmation event.
     * @param contractHash
     */
    protected abstract void raisePaymentConfirmationEvent(String contractHash, MoneyType moneyType);

    /**
     * This method parse a String object to a long object
     *
     * @param stringValue
     *
     * @return
     *
     * @throws InvalidParameterException
     */
    protected double parseToDouble(String stringValue) throws InvalidParameterException {
        if (stringValue == null) {
            throw new InvalidParameterException("Cannot parse a null string value to long");
        } else {
            try {
                return NumberFormat.getInstance().parse(stringValue).doubleValue();
            } catch (Exception exception) {
                throw new InvalidParameterException(
                        InvalidParameterException.DEFAULT_MESSAGE,
                        FermatException.wrapException(exception),
                        "Parsing String object to double",
                        "Cannot parse " + stringValue + " string value to long");
            }
        }
    }

    protected void reportError(Exception e){
        pluginRoot.reportError(
                UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                e);
        e.printStackTrace();
    }
}
