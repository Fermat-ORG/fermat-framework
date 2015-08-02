package com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;

import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;

import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetLogTool;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedAddonsExceptionSeverity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by loui on 22/02/15.
 */

/**
 * This plug-in manages a registry of known extra users..
 */

public class IntraUserModulePluginRoot implements  DealsWithErrors, DealsWithLogger, LogManagerForDevelopers, DealsWithPluginDatabaseSystem,  Plugin, Service  {



    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();


    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;



    /**
     *DealsWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealWithEvents Interface implementation.
     */


    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraUserModulePluginRoot");

        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * Modify by Manuel on 25/07/2015
         * I will wrap all this method within a try, I need to catch any generic java Exception
         */
        try{

            /**
             * I will check the current values and update the LogLevel in those which is different
             */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (IntraUserModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    IntraUserModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    IntraUserModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    IntraUserModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        }catch (Exception exception){
            FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: "+ IntraUserModulePluginRoot.newLoggingLevel ,"Check the cause");
            this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
        }

    }

    /**
     * Static method to get the logging level from any class under root.
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className){
        try{
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return IntraUserModulePluginRoot.newLoggingLevel.get(correctedClass[0]);

        } catch (Exception exception){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }



    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {

        this.serviceStatus = ServiceStatus.STARTED;



    }

    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;

    }

    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {

        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }


    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
