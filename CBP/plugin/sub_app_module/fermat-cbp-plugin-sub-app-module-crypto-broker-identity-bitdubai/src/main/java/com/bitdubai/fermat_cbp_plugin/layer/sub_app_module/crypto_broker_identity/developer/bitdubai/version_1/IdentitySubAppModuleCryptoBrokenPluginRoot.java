package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_identity.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.exceptions.CantCreateCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.exceptions.CantGetCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.DealsWithCryptoBrokerIdentities;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotCreateCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotUnPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_identity.developer.bitdubai.version_1.structure.CryptoBrokerIdentityInformationImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Angel 16/10/2015
 */

public class IdentitySubAppModuleCryptoBrokenPluginRoot implements CryptoBrokerIdentityModuleManager, DealsWithCryptoBrokerIdentities, DealsWithErrors, DealsWithLogger, LogManagerForDevelopers, Service, Plugin {

    /**
     * Elementos de DealsWithCryptoBrokerIdentities
      */
    private CryptoBrokerIdentityManager identityManager;


    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginIdentity interface member variable
     */
    UUID pluginId;

    /**
     * DealsWithLogManager interface member variable
     */
    LogManager logManager;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();


    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_identity.developer.bitdubai.version_1.IdentitySubAppModuleCryptoBrokenPluginRoot");

        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */
        try {
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (IdentitySubAppModuleCryptoBrokenPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    IdentitySubAppModuleCryptoBrokenPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    IdentitySubAppModuleCryptoBrokenPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    IdentitySubAppModuleCryptoBrokenPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception exception) {
           // this.errorManager.reportUnexpectedPluginException(Plugins., UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }


    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /**
     * DealsWithError interface implementation
     *
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }



    /**
     * PlatformService Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
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

    /**
     * Plugin interface implementation
     *
     * @param uuid
     */
    @Override
    public void setId(UUID uuid) {
        this.pluginId = uuid;
    }



    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return IdentitySubAppModuleCryptoBrokenPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            System.err.println("CantGetLogLevelByClass: " + e.getMessage());
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }


    @Override
    public void setCryptoBrokerIdentityManager(CryptoBrokerIdentityManager cryptoBrokerIdentityManager) {
        this.identityManager = cryptoBrokerIdentityManager;
    }

    public List<CryptoBrokerIdentity> getAllCryptoBrokersFromCurrentDeviceUser() throws CantGetCryptoBrokerIdentityException {
        return this.identityManager.getAllCryptoBrokersFromCurrentDeviceUser();
    }

    @Override
    public CryptoBrokerIdentityInformation createCryptoBrokerIdentity(String cryptoBrokerName, byte[] profileImage) throws CouldNotCreateCryptoBrokerException {
        try {
            CryptoBrokerIdentity identity = this.identityManager.createCryptoBrokerIdentity(cryptoBrokerName,profileImage);
            return converIdentityToInformation(identity);
        } catch (CantCreateCryptoBrokerIdentityException e) {
            throw new CouldNotCreateCryptoBrokerException(CouldNotCreateCryptoBrokerException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public void publishCryptoBrokerIdentity(String cryptoBrokerPublicKey) throws CouldNotPublishCryptoBrokerException {

    }

    @Override
    public void unPublishCryptoBrokerIdentity(String cryptoBrokerPublicKey) throws CouldNotUnPublishCryptoBrokerException {

    }

    @Override
    public List<CryptoBrokerIdentityInformation> getAllCryptoBrokersIdentities(int max, int offset) throws CantGetCryptoBrokerListException {
        try {
            List<CryptoBrokerIdentityInformation> cryptoBrokers = new ArrayList<>();
            for(CryptoBrokerIdentity identity : this.identityManager.getAllCryptoBrokersFromCurrentDeviceUser()){
                cryptoBrokers.add(converIdentityToInformation(identity));
            }
            return cryptoBrokers;
        } catch (CantGetCryptoBrokerIdentityException e) {
            throw new CantGetCryptoBrokerListException(CantGetCryptoBrokerListException.DEFAULT_MESSAGE, e, "","");
        }
    }

    private CryptoBrokerIdentityInformation converIdentityToInformation(final CryptoBrokerIdentity identity){
        return new CryptoBrokerIdentityInformationImpl(identity.getAlias(), identity.getPublicKey(), identity.getProfileImage(), identity.isPublished());
    }






}