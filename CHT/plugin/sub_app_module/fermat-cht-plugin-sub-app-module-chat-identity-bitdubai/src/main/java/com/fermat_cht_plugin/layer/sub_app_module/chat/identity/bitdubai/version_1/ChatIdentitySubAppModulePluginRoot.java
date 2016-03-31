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
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantCreateNewChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantGetChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantUpdateChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentityManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatIdentityModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * FERMAT-ORG
 * Developed by Lozadaa on 30/03/16.
 */


public class ChatIdentitySubAppModulePluginRoot extends AbstractPlugin implements ChatIdentityModuleManager {

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CHAT_IDENTITY)
    private  ChatIdentityManager identityManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    public ChatIdentitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    @Override
    public SettingsManager<ChatPreferenceSettings> getSettingsManager() {
        return null;
    }

    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }

    public List<ChatIdentity> getIdentityAssetUsersFromCurrentDeviceUser() throws CantListChatIdentityException {
        return null;
    }

    @Override
    public ChatIdentity getIdentityAssetUser() throws CantGetChatIdentityException {
        return null;
    }

    @Override
    public ChatIdentity createNewIdentityChat(String alias, byte[] profileImage) throws CantCreateNewChatIdentityException {
        return null;
    }

    @Override
    public void updateIdentityChat(String identityPublicKey, String identityAlias, byte[] profileImage) throws CantUpdateChatIdentityException {

    }
}