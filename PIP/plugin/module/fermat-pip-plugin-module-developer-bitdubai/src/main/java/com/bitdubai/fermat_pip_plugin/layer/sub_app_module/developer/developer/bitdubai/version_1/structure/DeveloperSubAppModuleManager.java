package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.basic_classes.BasicSubAppSettings;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetDataBaseToolException;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetLogToolException;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.DatabaseTool;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.LogTool;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.ToolManager;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nerio on 25/5/2016.
 */
public class DeveloperSubAppModuleManager extends ModuleManagerImpl<BasicSubAppSettings> implements ToolManager {

    private ConcurrentHashMap<PluginVersionReference, Plugin> databaseManagersOnPlugins;
    private ConcurrentHashMap<AddonVersionReference, Addon> databaseManagersOnAddons ;

    private ConcurrentHashMap<PluginVersionReference, Plugin> logManagersOnPlugins;
    private ConcurrentHashMap<AddonVersionReference , Addon> logManagersOnAddons ;

    String appPublicKey;

    public DeveloperSubAppModuleManager(
            ConcurrentHashMap<PluginVersionReference, Plugin> databaseManagersOnPlugins,
            ConcurrentHashMap<AddonVersionReference, Addon> databaseManagersOnAddons,
            ConcurrentHashMap<PluginVersionReference, Plugin> logManagersOnPlugins,
            ConcurrentHashMap<AddonVersionReference, Addon> logManagersOnAddons,
            PluginFileSystem pluginFileSystem,
            UUID pluginId) {

        super(pluginFileSystem, pluginId);

        this.databaseManagersOnPlugins = databaseManagersOnPlugins;
        this.databaseManagersOnAddons = databaseManagersOnAddons;

        this.logManagersOnPlugins = logManagersOnPlugins;
        this.logManagersOnAddons = logManagersOnAddons;
    }

    @Override
    public DatabaseTool getDatabaseTool() throws CantGetDataBaseToolException {

        return new DeveloperModuleDatabaseTool(
                this.databaseManagersOnPlugins,
                this.databaseManagersOnAddons
        );
    }

    @Override
    public LogTool getLogTool() throws CantGetLogToolException {

        return new DeveloperModuleLogTool(
                this.logManagersOnPlugins,
                this.logManagersOnAddons
        );
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {
        this.appPublicKey = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
