package com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;

/**
 * Created by rodrigo on 2015.06.25..
 */
public class LoggerManager implements LogManager {

    StringBuilder outputMessage;
    CallerInformationGetter callerInformationGetter;

    /**
     * Constructor
     */
    public LoggerManager() {
        outputMessage = new StringBuilder("");
        callerInformationGetter = new CallerInformationGetter();
    }


    /**
     * executes the log
     *
     * @param logLevel
     * @param minimalLogging
     * @param moderateLogging
     * @param aggressiveLogging
     */
    public void log(LogLevel logLevel, String minimalLogging, String moderateLogging, String aggressiveLogging) {

        /**
         * Minimal logging level includes only the sent message
         */
        if (logLevel == LogLevel.MINIMAL_LOGGING) {
            setMinimalLogging(minimalLogging);
        }

        /**
         * Moderate loggin level logs current class and method information + minimal level.
         */
        if (logLevel == LogLevel.MODERATE_LOGGING) {
            setMinimalLogging(minimalLogging);
            setModerateLogging(moderateLogging);
        }

        /**
         * Agressive logging level includes moderate and minimal + Thread information.
         */
        if (logLevel == LogLevel.AGGRESSIVE_LOGGING) {
            setMinimalLogging(minimalLogging);
            setModerateLogging(moderateLogging);
            setAggressiveLogging(aggressiveLogging);
        }

        /**
         * prints outs the log.
         */
        System.out.println(outputMessage.toString());
        outputMessage.delete(0, outputMessage.capacity() - 1);
    }


    private void setAggressiveLogging(String message) {
        if (message != null) {
            outputMessage.append("Message: ").append(message);
            outputMessage.append(System.lineSeparator());
        }
        for (String property : callerInformationGetter.getCurrentThreadInformation()) {
            outputMessage.append(property);
            outputMessage.append(System.lineSeparator());
        }

        for (String property : callerInformationGetter.getCurrentMethodInformation()) {
            outputMessage.append(property);
            outputMessage.append(System.lineSeparator());
        }
    }

    private void setModerateLogging(String message) {
        if (message != null) {
            outputMessage.append("Message: ").append(message);
            outputMessage.append(System.lineSeparator());
        }
        for (String property : callerInformationGetter.getCurrentMethodInformation()) {
            outputMessage.append(property);
            outputMessage.append(System.lineSeparator());
        }

    }

    private void setMinimalLogging(String message) {
        if (message != null) {
            outputMessage.append("Message: ").append(message);
            outputMessage.append(System.lineSeparator());
        }
    }

    @Override
    public String getOutputMessage() {
        return outputMessage.toString();
    }
}
