package com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.mocks.ChatMock;
import com.bitdubai.fermat_cht_api.layer.middleware.mocks.MessageMock;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1.structure.ChatSupAppModuleManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by franklin on 06/01/16.
 */
public class ChatSupAppModulePluginRoot extends AbstractPlugin implements
        LogManagerForDevelopers,
        ChatModuleManager {

    private ChatManager chatManager;

    public ChatSupAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.CHAT_MIDDLEWARE)
    private com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatManager chatMiddlewareManager;

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

    }

    @Override
    public ChatManager getChatManager() throws CHTException {
        try {
            if (chatManager == null) {
                chatManager = new com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1.structure.ChatSupAppModuleManager(chatMiddlewareManager);
            }
            return chatManager;
        }catch (Exception e) {
            throw new CHTException(FermatException.wrapException(e));
        }
    }

    /**
     * Through the method <code>getSettingsManager</code> we can get a settings manager for the specified
     * settings class parametrized.
     *
     * @return a new instance of the settings manager for the specified fermat settings object.
     */
    @Override
    public SettingsManager<FermatSettings> getSettingsManager() {
        return null;
    }

    /**
     * Through the method <code>getSelectedActorIdentity</code> we can get the selected actor identity.
     *
     * @return an instance of the selected actor identity.
     * @throws CantGetSelectedActorIdentityException if something goes wrong.
     */
    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        return null;
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
         * Init the plugin manager
         */
        chatManager=new ChatSupAppModuleManager(chatMiddlewareManager);
        //TODO: This method is only for testing, please, comment it when the test is finish, thanks.
        //testMethod();

    }

    private void testMethod(){
        try{
            Chat testChat=new ChatMock();
            Message testMessage=new MessageMock(UUID.fromString("52d7fab8-a423-458f-bcc9-49cdb3e9ba8f"));
            testMessage.setMessage("Sending from Sub App Module: We are FEEEERMAAAT!!");
            chatManager.saveChat(testChat);
            chatManager.saveMessage(testMessage);
        } catch (Exception e){
            System.out.println("Exception in Chat Module test: "+e);
            e.printStackTrace();
        }
    }
}
