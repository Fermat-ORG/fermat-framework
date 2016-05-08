package com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraUserWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.exceptions.CantLoadLoginsFileException;
import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleManagerImpl;
import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.utils.IntraUserSettings;
import com.bitdubai.fermat_pip_api.layer.actor.exception.CantGetLogTool;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This plug-in provides the methods for the Intra Users sub app.
 * To manage Intra User information and intra users connections
 * Created by loui on 22/02/15.
 * Modified by Natalia Cortez on 11/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class IntraWalletUserModulePluginRoot extends AbstractModule<IntraUserWalletSettings,ActiveActorIdentityInformation> implements
        LogManagerForDevelopers{


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.INTRA_WALLET_USER)
    private IntraUserManager intraUserNertwokServiceManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.INTRA_WALLET_USER)
    private IntraWalletUserIdentityManager intraWalletUserIdentityManager;


    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.INTRA_WALLET_USER)
    private IntraWalletUserActorManager intraWalletUserManager;

    private IntraWalletUserIdentity intraWalletUser;


    private static String INTRA_USER_LOGIN_FILE_NAME = "intraUsersLogin";

    private String intraUserLoggedPublicKey;

    private PluginTextFile intraUserLoginXml;

    private IntraUserSettings intraUserSettings = new IntraUserSettings();
    private String appPublicKey;


    public IntraWalletUserModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /**
     * DealsWithLogger interface member variable
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * IntraUserModuleManager Interface implementation.
     */




    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraWalletUserModulePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleInformation");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleLoginIdentity");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleSearch");

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
        try {

            /**
             * I will check the current values and update the LogLevel in those which is different
             */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (IntraWalletUserModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    IntraWalletUserModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    IntraWalletUserModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    IntraWalletUserModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + IntraWalletUserModulePluginRoot.newLoggingLevel, "Check the cause");
            this.errorManager.reportUnexpectedPluginException(Plugins.INTRA_WALLET_USER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }

    }


    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;

            /**
             * Get from xml file the last intra user logged
             */

            /**
             * load file content
             */

            loadSettingsFile();


            /**
             * get last logged intra user
             */
            this.intraUserLoggedPublicKey = intraUserSettings.getLoggedInPublicKey();

        } catch (CantLoadLoginsFileException e) {
            throw new CantStartPluginException("Error load logins xml file", e);
        } catch (Exception e) {
            throw new CantStartPluginException("Unknown Error", e);
        }

    }

    /**
     * private methods
     */

    private void loadSettingsFile() throws CantLoadLoginsFileException {

        try {
            /**
             *  I check if the file containing  the wallets settings  already exists or not.
             * If not exists I created it.
             * * *
             */

            intraUserLoginXml = pluginFileSystem.getTextFile(pluginId, pluginId.toString(), INTRA_USER_LOGIN_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            /**
             * Now I read the content of the file and place it in memory.
             */
            intraUserLoginXml.loadFromMedia();

            String xml = intraUserLoginXml.getContent();

            intraUserSettings = (IntraUserSettings) XMLParser.parseXML(xml, intraUserSettings);


        } catch (FileNotFoundException fileNotFoundException) {
            /**
             * If the file did not exist it is not a problem. It only means this is the first time this plugin is running.
             *
             * I will create the file now, with an empty content so that when a new wallet is added we wont have to deal
             * with this file not existing again.
             * * * * *
             */

            try {
                intraUserLoginXml = pluginFileSystem.createTextFile(pluginId, pluginId.toString(), INTRA_USER_LOGIN_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            } catch (CantCreateFileException cantCreateFileException) {
                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                throw new CantLoadLoginsFileException(CantLoadLoginsFileException.DEFAULT_MESSAGE, cantCreateFileException, null, null);
            }

            try {
                /**
                 * make default xml structure
                 */

                intraUserLoginXml.setContent(XMLParser.parseObject(intraUserSettings));

                intraUserLoginXml.setContent(XMLParser.parseObject(intraUserSettings));

//                /**
//                 * If I can not save this file, then this plugin shouldn't be running at all.
//                 */
//                throw new CantLoadLoginsFileException(CantLoadLoginsFileException.DEFAULT_MESSAGE, cantPersistFileException, null, null);
            }catch (Exception e){
                throw new CantLoadLoginsFileException(CantLoadLoginsFileException.DEFAULT_MESSAGE, e, null, null);
            }
        } catch (CantLoadFileException | CantCreateFileException e) {

            /**
             * In this situation we might have a corrupted file we can not read. For now the only thing I can do is
             * to prevent the plug-in from running.
             *
             * In the future there should be implemented a method to deal with this situation.
             * * * *
             */
            throw new CantLoadLoginsFileException(CantLoadLoginsFileException.DEFAULT_MESSAGE, e, null, null);
        } catch (Exception ex) {
            throw new CantLoadLoginsFileException(CantLoadLoginsFileException.DEFAULT_MESSAGE, FermatException.wrapException(ex), null, null);
        }
    }


    private IntraUserModuleManagerImpl intraUserModuleManager;

    @Override
    public ModuleManager<IntraUserWalletSettings, ActiveActorIdentityInformation> getModuleManager() throws CantGetModuleManagerException {
        if(intraUserModuleManager==null){
            intraUserModuleManager = new IntraUserModuleManagerImpl(pluginFileSystem,pluginId,intraUserLoginXml,intraWalletUser,intraWalletUserIdentityManager,intraWalletUserManager,intraUserNertwokServiceManager,errorManager,intraUserLoggedPublicKey);
        }
        return intraUserModuleManager;
    }
}
