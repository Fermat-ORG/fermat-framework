package com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.structure.CallerInformationGetter;

/**
 * Created by rodrigo on 2015.06.25..
 * Modified by lnacosta (laion.cj91@gmail.com) on 05/11/2015.
 */
public final class LoggerSystemAddonRoot extends AbstractAddon implements LogManager {

    private final CallerInformationGetter callerInformationGetter;
    private final StringBuilder           outputMessage          ;

    public LoggerSystemAddonRoot() {
        super(new AddonVersionReference(new Version()));

        callerInformationGetter = new CallerInformationGetter();
        outputMessage           = new StringBuilder("");
    }

    public void log(LogLevel logLevel, String minimalLogging, String moderateLogging, String aggressiveLogging) {

        /**
         * Minimal logging level includes only the sent message
         */
        if (logLevel == LogLevel.MINIMAL_LOGGING){
            setMinimalLogging(minimalLogging);
        }

        /**
         * Moderate loggin level logs current class and method information + minimal level.
         */
        if (logLevel == LogLevel.MODERATE_LOGGING){
            setMinimalLogging(minimalLogging);
            setModerateLogging(moderateLogging);
        }

        /**
         * Agressive logging level includes moderate and minimal + Thread information.
         */
        if (logLevel == LogLevel.AGGRESSIVE_LOGGING){
            setMinimalLogging(minimalLogging);
            setModerateLogging(moderateLogging);
            setAggressiveLogging(aggressiveLogging);
        }

        /**
         * prints outs the log.
         */
        System.out.println(outputMessage.toString());
        outputMessage.delete(0, outputMessage.capacity()-1);
    }


    private void setAggressiveLogging(String message){
        if (message != null) {
            outputMessage.append("Message: ");
            outputMessage.append(message);
            outputMessage.append(System.lineSeparator());
        }
        for (String property : callerInformationGetter.getCurrentThreadInformation()){
            outputMessage.append(property);
            outputMessage.append(System.lineSeparator());
        }

        for (String property : callerInformationGetter.getCurrentMethodInformation()){
            outputMessage.append(property);
            outputMessage.append(System.lineSeparator());
        }
    }

    private void setModerateLogging(String message){
        if (message != null) {
            outputMessage.append("Message: ");
            outputMessage.append(message);
            outputMessage.append(System.lineSeparator());
        }
        for (String property : callerInformationGetter.getCurrentMethodInformation()){
            outputMessage.append(property);
            outputMessage.append(System.lineSeparator());
        }

    }

    private void setMinimalLogging(String message){
        if (message != null) {
            outputMessage.append("Message: ");
            outputMessage.append(message);
            outputMessage.append(System.lineSeparator());
        }
    }

    @Override
    public String getOutputMessage(){
        return outputMessage.toString();
    }
}
