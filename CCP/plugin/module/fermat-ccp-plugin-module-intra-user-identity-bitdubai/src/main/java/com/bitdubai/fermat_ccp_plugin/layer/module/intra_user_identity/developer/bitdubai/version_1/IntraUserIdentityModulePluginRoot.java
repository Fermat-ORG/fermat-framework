package com.bitdubai.fermat_ccp_plugin.layer.module.intra_user_identity.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;

import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantCreateNewIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantDeleteIdentityException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantUpdateIdentityException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraUserIdentitySettings;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantCreateNewIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantDeleteIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantGetIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantListIntraUsersIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantUpdateIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.interfaces.IntraUserIdentityModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.interfaces.IntraUserModuleIdentity;
import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user_identity.developer.bitdubai.version_1.structure.IntraUserIdentityModule;
import com.bitdubai.fermat_pip_api.layer.actor.exception.CantGetLogTool;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This plug-in provides the methods for the Intra Users Identity sub app.
 * To manage Intra User information and intra users connections
 * Created by Natalia Cortez on 05/01/16.
 *
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class IntraUserIdentityModulePluginRoot extends AbstractPlugin implements
        LogManagerForDevelopers,
        IntraUserIdentityModuleManager {


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;


    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.INTRA_WALLET_USER)
    private IntraWalletUserIdentityManager intraWalletUserIdentityManager;



    public IntraUserIdentityModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /**
     * DealsWithLogger interface member variable
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();


    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.module.intra_user_identity.developer.bitdubai.version_1.IntraUserIdentityModulePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.module.intra_user_identity.developer.bitdubai.version_1.structure.IntraUserIdentityModule");


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
                if (IntraUserIdentityModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    IntraUserIdentityModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    IntraUserIdentityModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    IntraUserIdentityModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + IntraUserIdentityModulePluginRoot.newLoggingLevel, "Check the cause");
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



        } catch (Exception e) {
            throw new CantStartPluginException("Unknown Error", e);
        }

    }

    /**
     * IntraUserIdentityModuleManagerInterface implementation.
     */



    @Override
    public List<IntraUserModuleIdentity> getAllIntraWalletUsersFromCurrentDeviceUser() throws CantListIntraUsersIdentityException {
        try {

            List<IntraUserModuleIdentity> intraUserModuleIdentityList = new ArrayList<>();

            List<IntraWalletUserIdentity> intraWalletUserIdentityList = intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser();

            for (IntraWalletUserIdentity intraUser : intraWalletUserIdentityList) {

                intraUserModuleIdentityList.add(new IntraUserIdentityModule(intraUser.getAlias(),intraUser.getPhrase(),intraUser.getPublicKey(),intraUser.getImage(),intraUser.getActorType()));
            }

            return intraUserModuleIdentityList;

        } catch (CantListIntraWalletUsersException e) {
            throw new CantListIntraUsersIdentityException(CantListIntraUsersIdentityException.DEFAULT_MESSAGE,e,"","Identity plugin error");
        }
        catch (Exception e) {
            throw new CantListIntraUsersIdentityException(CantListIntraUsersIdentityException.DEFAULT_MESSAGE,FermatException.wrapException(e),"","Unknown error");
        }
    }

    @Override
    public IntraUserModuleIdentity createNewIntraWalletUser(String alias, String phrase, byte[] profileImage) throws CantCreateNewIntraUserIdentityException {
        try {
            IntraWalletUserIdentity intraWalletUserIdentity =  intraWalletUserIdentityManager.createNewIntraWalletUser(alias, phrase,  profileImage);

            return new IntraUserIdentityModule( alias,  phrase,intraWalletUserIdentity.getPublicKey(), profileImage,intraWalletUserIdentity.getActorType());

        } catch (CantCreateNewIntraWalletUserException e) {
            throw new CantCreateNewIntraUserIdentityException(CantCreateNewIntraUserIdentityException.DEFAULT_MESSAGE,e,"","Identity plugin error");
        }
        catch (Exception e) {
            throw new CantCreateNewIntraUserIdentityException(CantCreateNewIntraUserIdentityException.DEFAULT_MESSAGE,FermatException.wrapException(e),"","Unknown error");
        }
    }

    @Override
    public IntraUserModuleIdentity createNewIntraWalletUser(String alias, byte[] profileImage) throws CantCreateNewIntraUserIdentityException {
        try {
            IntraWalletUserIdentity intraWalletUserIdentity =  intraWalletUserIdentityManager.createNewIntraWalletUser(alias, profileImage);

            return new IntraUserIdentityModule( alias, "",intraWalletUserIdentity.getPublicKey(), profileImage,intraWalletUserIdentity.getActorType());

        } catch (CantCreateNewIntraWalletUserException e) {
            throw new CantCreateNewIntraUserIdentityException(CantCreateNewIntraUserIdentityException.DEFAULT_MESSAGE,e,"","Identity plugin error");
        }
        catch (Exception e) {
            throw new CantCreateNewIntraUserIdentityException(CantCreateNewIntraUserIdentityException.DEFAULT_MESSAGE,FermatException.wrapException(e),"","Unknown error");
        }
    }

    @Override
    public boolean hasIntraUserIdentity() throws CantGetIntraUserIdentityException {
        try {
            return intraWalletUserIdentityManager.hasIntraUserIdentity();

        } catch (CantListIntraWalletUsersException e) {
            throw new CantGetIntraUserIdentityException(CantGetIntraUserIdentityException.DEFAULT_MESSAGE,e,"","Identity plugin error");
        }
        catch (Exception e) {
            throw new CantGetIntraUserIdentityException(CantGetIntraUserIdentityException.DEFAULT_MESSAGE,FermatException.wrapException(e),"","Unknown error");
        }

    }

    @Override
    public void updateIntraUserIdentity(String identityPublicKey, String identityAlias, String phrase, byte[] profileImage) throws CantUpdateIntraUserIdentityException {

        try
        {
            intraWalletUserIdentityManager.updateIntraUserIdentity(identityPublicKey, identityAlias,  phrase, profileImage);


        } catch (CantUpdateIdentityException e) {
            throw new CantUpdateIntraUserIdentityException(CantUpdateIntraUserIdentityException.DEFAULT_MESSAGE,e,"","Identity plugin error");
        }
        catch (Exception e) {
            throw new CantUpdateIntraUserIdentityException(CantUpdateIntraUserIdentityException.DEFAULT_MESSAGE,FermatException.wrapException(e),"","Unknown error");
        }
    }

    @Override
    public void deleteIntraUserIdentity(String identityPublicKey) throws CantDeleteIntraUserIdentityException {
        try
        {
            intraWalletUserIdentityManager.deleteIntraUserIdentity(identityPublicKey);
        } catch (CantDeleteIdentityException e) {
            throw new CantDeleteIntraUserIdentityException(CantDeleteIntraUserIdentityException.DEFAULT_MESSAGE,e,"","Identity plugin error");
        }
        catch (Exception e) {
            throw new CantDeleteIntraUserIdentityException(CantDeleteIntraUserIdentityException.DEFAULT_MESSAGE,FermatException.wrapException(e),"","Unknown error");
        }
    }



    /**
     * SettingsManager implementation.
     * */



    private SettingsManager<IntraUserIdentitySettings> settingsManager;

    @Override
    public SettingsManager<IntraUserIdentitySettings> getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
