package com.bitdubai.fermat_api.layer.modules;

import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import java.util.UUID;

/**
 * Created by mati on 2016.04.21..
 */
public class ModuleManagerImpl<Z extends FermatSettings> {

    protected final UUID pluginId;
    protected final PluginFileSystem pluginFileSystem;
    private SettingsManager<Z> settingsManager;

    public ModuleManagerImpl(PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    /**
     * Through the method <code>getSettingsManager</code> we can get a settings manager for the specified
     * settings class parametrized.
     *
     * @return a new instance of the settings manager for the specified fermat settings object.
     */
    @Deprecated
    public final SettingsManager<Z> getSettingsManager(){
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );
        return this.settingsManager;
    }


    public final void persistSettings(String publicKey,final Z settings) throws CantPersistSettingsException {
        getSettingsManager().persistSettings(publicKey, settings);
    }

    public final Z loadAndGetSettings(String publicKey) throws CantGetSettingsException, SettingsNotFoundException {
        return getSettingsManager().loadAndGetSettings(publicKey);
    }

}
