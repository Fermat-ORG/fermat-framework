package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.util.UUID;

/**
 * Created by rodrigo on 8/17/15.
 */
public class WalletFactoryProjectSkin extends Skin  implements DealsWithPluginFileSystem{

    UUID pluginId;

    /**
     * DealsWithPluginFileSystem interface variable and implementation
     */
    PluginFileSystem pluginFileSystem;
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * Constructor
     * @param pluginFileSystem
     */
    public WalletFactoryProjectSkin(PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    public void saveToDisk() throws FileNotFoundException, CantCreateFileException {
        pluginFileSystem.getTextFile(pluginId, this.getId().toString(), this.getName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
    }
}
