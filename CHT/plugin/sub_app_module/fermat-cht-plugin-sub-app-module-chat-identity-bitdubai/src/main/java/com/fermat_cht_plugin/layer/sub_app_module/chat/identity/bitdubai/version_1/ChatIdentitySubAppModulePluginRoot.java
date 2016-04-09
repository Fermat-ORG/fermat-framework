package com.fermat_cht_plugin.layer.sub_app_module.chat.identity.bitdubai.version_1;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentityManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityPreferenceSettings;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentitySupAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.fermat_cht_plugin.layer.sub_app_module.chat.identity.bitdubai.version_1.exceptions.CantInitializeChatIdentitySupAppModuleManagerException;

/**
 * FERMAT-ORG
 * Developed by Lozadaa on 30/03/16.
 */


public class ChatIdentitySubAppModulePluginRoot extends AbstractPlugin implements ChatIdentitySupAppModuleManager {

    private ChatIdentityModuleManager chatIdentityModuleManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CHAT_IDENTITY_SUP_APP_MODULE)
    private ChatIdentityManager chatIdentityManager;

    public ChatIdentitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public SettingsManager<ChatIdentityPreferenceSettings> getSettingsManager() {
        return null;
    }

    /**
     * Through the method <code>getSelectedActorIdentity</code> we can get the selected actor identity.
     *
     * @return an instance of the selected actor identity.
     * @throws CantGetSelectedActorIdentityException if something goes wrong.
     * @throws ActorIdentityNotSelectedException     if there's no actor identity selected.
     */
    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }


    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        chatIdentityManager.createNewIdentityChat(name, profile_img);
    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }

    /**
     *
     */
    @Override
    public void start(){
        /**
         *
         * Init the plugin manager
         *
         */
        System.out.println("******* Init Chat Sup App Module Identity ******");
        chatIdentityModuleManager = new com.fermat_cht_plugin.layer.sub_app_module.chat.identity.bitdubai.version_1.structure.ChatIdentitySupAppModuleManager(chatIdentityManager);
        //TODO: This method is only for testing, please, comment it when the test is finish, thanks.
        //testMethod();

    }

    @Override
    public ChatIdentityModuleManager getChatIdentityManager() throws CHTException {
        try {
            if (chatIdentityModuleManager == null) {
                chatIdentityModuleManager = new com.fermat_cht_plugin.layer.sub_app_module.chat.identity.bitdubai.version_1.structure.ChatIdentitySupAppModuleManager(chatIdentityManager);            }
            return chatIdentityModuleManager;
        }catch (final Exception e) {
            //throw new CHTException(FermatException.wrapException(e));
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_IDENTITY_SUP_APP_MODULE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeChatIdentitySupAppModuleManagerException(
                    "Trying to create the plugin database - Please, check the cause",e);
        }
    }
}