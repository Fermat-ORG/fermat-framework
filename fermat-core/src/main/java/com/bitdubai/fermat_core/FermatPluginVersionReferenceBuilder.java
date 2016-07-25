package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.LayerReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.regex.Pattern;

/**
 * The class <code>com.bitdubai.fermat_core.FermatPluginVersionReferenceBuilder</code>
 * contains all the logic to build the plugin version reference of fermat plug-ins.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/12/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class FermatPluginVersionReferenceBuilder {

    private static final String KEY_SEPARATOR = "+";

    public static PluginVersionReference getByKey(final String key) throws InvalidParameterException {

        String[] keySplit = key.split(Pattern.quote(KEY_SEPARATOR));

        if (keySplit.length != 5)
            throw new InvalidParameterException(new StringBuilder().append("Key: ").append(key).toString(), new StringBuilder().append("This key should respect the separation pattern using \"").append(KEY_SEPARATOR).append("\"").toString());

        final String platformString = keySplit[0];
        final String layerString = keySplit[1];
        final String pluginEnumString = keySplit[2];
        final String developerString = keySplit[3];
        final String versionString = keySplit[4];

        final Platforms platform = Platforms.getByCode(platformString);
        final Layers layer = Layers.getByCode(layerString);
        final FermatPluginsEnum pluginEnum = FermatPluginsEnumSelector.getByPlatformAndCode(platform, pluginEnumString);
        final Developers developer = Developers.getByCode(developerString);
        final Version version = new Version(versionString);

        return new PluginVersionReference(platform, layer, pluginEnum, developer, version);
    }

    public static String toKey(final PluginVersionReference pluginVersionReference) {

        PluginDeveloperReference pluginDeveloperReference = pluginVersionReference.getPluginDeveloperReference();

        PluginReference pluginReference = pluginDeveloperReference.getPluginReference();
        LayerReference layerReference = pluginReference.getLayerReference();
        PlatformReference platformReference = layerReference.getPlatformReference();

        Platforms platform = platformReference.getPlatform();
        Layers layer = layerReference.getLayer();
        FermatPluginsEnum plugin = pluginReference.getPlugin();
        Developers developer = pluginDeveloperReference.getDeveloper();

        return platform.getCode() + KEY_SEPARATOR +
                layer.getCode() + KEY_SEPARATOR +
                plugin.getCode() + KEY_SEPARATOR +
                developer.getCode() + KEY_SEPARATOR +
                pluginVersionReference.getVersion().toString();
    }

}
