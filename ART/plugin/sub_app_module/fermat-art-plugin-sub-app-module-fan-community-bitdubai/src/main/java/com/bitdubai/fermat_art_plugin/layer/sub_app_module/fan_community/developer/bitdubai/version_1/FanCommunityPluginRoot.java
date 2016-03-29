package com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_community.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces.FanActorConnectionManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/03/16.
 */
public class FanCommunityPluginRoot extends AbstractPlugin {
    @NeededPluginReference(platform = Platforms.ART_PLATFORM, layer = Layers.IDENTITY,plugin = Plugins.ARTIST_IDENTITY)
    private FanActorConnectionManager fanActorConnectionManager;
    /**
     * Default constructor
     */
    public FanCommunityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }
}
