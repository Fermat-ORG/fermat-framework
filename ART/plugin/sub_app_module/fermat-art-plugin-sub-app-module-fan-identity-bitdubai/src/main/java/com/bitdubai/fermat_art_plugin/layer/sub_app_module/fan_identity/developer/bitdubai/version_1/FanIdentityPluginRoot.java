package com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.logging.ErrorManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/03/16.
 */
public class FanIdentityPluginRoot extends AbstractPlugin {
    /**
     * Default constructor
     */
    public FanIdentityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }
    @NeededAddonReference(platform = Platforms.ART_PLATFORM, layer = Layers.WALLET_MODULE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;


}
