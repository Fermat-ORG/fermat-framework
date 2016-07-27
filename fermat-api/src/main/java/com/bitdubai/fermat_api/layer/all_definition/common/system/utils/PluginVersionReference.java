package com.bitdubai.fermat_api.layer.all_definition.common.system.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.io.Serializable;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference</code>
 * haves all the information of a Plugin Version Reference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 * Updated Matias Furszfer
 */
public class PluginVersionReference implements Serializable {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private PluginDeveloperReference pluginDeveloperReference;
    private Version version;

    public PluginVersionReference(final Version version) {

        this.version = version;
    }

    public PluginVersionReference(final PluginDeveloperReference pluginDeveloperReference,
                                  final Version version) {

        this.pluginDeveloperReference = pluginDeveloperReference;
        this.version = version;
    }

    public PluginVersionReference() {
    }

    public PluginVersionReference(final Platforms platform,
                                  final Layers layer,
                                  final FermatPluginsEnum pluginEnum,
                                  final Developers developer,
                                  final Version version) {

        PlatformReference platformReference = new PlatformReference(platform);
        LayerReference layerReference = new LayerReference(platformReference, layer);
        PluginReference pluginReference = new PluginReference(layerReference, pluginEnum);

        this.pluginDeveloperReference = new PluginDeveloperReference(pluginReference, developer);
        this.version = version;
    }

    public static PluginVersionReference PluginVersionReferenceFactory(String platformCode, String layerCode, String pluginsCode, String developerCode, String version) throws InvalidParameterException {
        return new PluginVersionReference(Platforms.getByCode(platformCode), Layers.getByCode(layerCode), Plugins.getByCode(pluginsCode), Developers.getByCode(developerCode), new Version());
    }

    public final Version getVersion() {
        return version;
    }

    public final PluginDeveloperReference getPluginDeveloperReference() {
        return pluginDeveloperReference;
    }

    public final void setPluginDeveloperReference(final PluginDeveloperReference pluginDeveloperReference) {
        this.pluginDeveloperReference = pluginDeveloperReference;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PluginVersionReference)) return false;

        PluginVersionReference that = (PluginVersionReference) o;

        return version.equals(that.version) &&
                ((pluginDeveloperReference == null && that.getPluginDeveloperReference() == null) || (pluginDeveloperReference != null && pluginDeveloperReference.equals(that.getPluginDeveloperReference())));
    }

    public Platforms getPlatform() {
        return pluginDeveloperReference.getPluginReference().getLayerReference().getPlatformReference().getPlatform();
    }

    public Layers getLayers() {
        return pluginDeveloperReference.getPluginReference().getLayerReference().getLayer();
    }

    public FermatPluginsEnum getPlugins() {
        return pluginDeveloperReference.getPluginReference().getPlugin();
    }

    public Developers getDeveloper() {
        return pluginDeveloperReference.getDeveloper();

    }


    @Override
    public final int hashCode() {
        int c = 0;
        if (pluginDeveloperReference != null)
            c += pluginDeveloperReference.hashCode();
        if (version != null)
            c += version.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public final String toString() {
        return new StringBuilder()
                .append("PluginVersionReference{")
                .append("pluginDeveloperReference=").append(pluginDeveloperReference)
                .append(", version=").append(version)
                .append("}").toString();
    }

    public final String toString2() {
        return new StringBuilder()
                .append("PluginVersionReference{")
                .append("platform=").append(pluginDeveloperReference.getPluginReference().getLayerReference().getPlatformReference().getPlatform())
                .append(", layer=").append(pluginDeveloperReference.getPluginReference().getLayerReference().getLayer())
                .append(", plugin=").append(pluginDeveloperReference.getPluginReference().getPlugin())
                .append(", developer=").append(pluginDeveloperReference.getDeveloper())
                .append(", version=").append(version)
                .append('}').toString();
    }

    public final String toString3() {
        return new StringBuilder()
                .append("Plugin{")
                .append(pluginDeveloperReference.getPluginReference().getLayerReference().getPlatformReference().getPlatform())
                .append(", ")
                .append(pluginDeveloperReference.getPluginReference().getLayerReference().getLayer())
                .append(", ")
                .append(pluginDeveloperReference.getPluginReference().getPlugin())
                .append("}").toString();
    }
}
