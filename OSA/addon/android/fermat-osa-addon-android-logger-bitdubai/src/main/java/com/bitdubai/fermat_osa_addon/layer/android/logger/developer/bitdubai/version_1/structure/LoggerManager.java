package com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.developer.LogLevel;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;

import java.util.Map;
import java.util.UUID;

/**
 * Created by rodrigo on 2015.06.25..
 */
public class LoggerManager implements LogManagerForDevelopers {
    LogLevel logLevel;
    StringBuilder outputMessage;
    CallerInformationGetter callerInformationGetter;
    String message;
    UUID pluginId;

    /**
     * Constructor
     * @param logLevel
     */
    public LoggerManager(LogLevel logLevel) {
        this.logLevel = logLevel;
        outputMessage = new StringBuilder("");
        callerInformationGetter = new CallerInformationGetter();
    }

    /**
     * sets the new log level
     * @param logLevel
     */
    public void setLogLevel(LogLevel logLevel){
        this.logLevel = logLevel;
    }


    /**
     * executes the log
     * @param message
     */
    public void Log(String message){

        this.message = message;

        /**
         * Moderate loggin level logs current class and method information + minimal level.
         */
        if (this.logLevel == LogLevel.MODERATE_LOGGING){
            setModerateLogging();
            setMinimalLogging();
        }

        /**
         * Agressive logging level includes moderate and minimal + Thread information.
         */
        if (this.logLevel == LogLevel.AGGRESSIVE_LOGGING){
            setAggressiveLogging();
            setModerateLogging();
            setMinimalLogging();
        }

        /**
         * Minimal logging level includes only the sent message
         */
        if (this.logLevel == LogLevel.MINIMAL_LOGGING){
            setMinimalLogging();
        }

        /**
         * prints outs the log.
         */
        System.out.println(outputMessage.toString());
        outputMessage.delete(0, outputMessage.capacity()-1);
    }


    private void setAggressiveLogging(){
        for (String property : callerInformationGetter.getCurrentThreadInformation()){
            outputMessage.append(property);
            outputMessage.append(System.lineSeparator());
        }
    }

    private void setModerateLogging(){
        for (String property : callerInformationGetter.getCurrentMethodInformation()){
            outputMessage.append(property);
            outputMessage.append(System.lineSeparator());
        }

    }

    private void setMinimalLogging(){
        outputMessage.append("Message:" + message);
        outputMessage.append(System.lineSeparator());
    }

    /**
     * LogManagerForDevelopers interface implementation
     * @return the actual loggin level
     */
    @Override
    public LogLevel getLoggingLevel() {
        return this.logLevel;
    }

    /**
     * LogManagerForDevelopers interface implementation
     * @param newLoggingLevel
     */
    @Override
    public void changeLoggingLevel(LogLevel newLoggingLevel) {
        this.logLevel = newLoggingLevel;

    }

    /**
     * LogManagerForDevelopers interface implementation
     * @param pluginId
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    public UUID getPluginId(){
        return this.pluginId;
    }


    public String getOutputMessage(){
        return outputMessage.toString();
    }
}
