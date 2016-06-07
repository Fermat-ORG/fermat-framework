package com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import java.io.Serializable;
import java.util.UUID;

public class AndroidCoreSettingsManager extends SettingsManager<AndroidCoreSettings> implements Serializable {


    public AndroidCoreSettingsManager(PluginFileSystem pluginFileSystem, UUID pluginId) {
        super(pluginFileSystem, pluginId);
    }

}