package com.bitdubai.fermat_api.layer.all_definition.common.system.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference</code>
 * haves all the information of a Addon Version Reference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class AddonVersionReference implements Serializable {

    private static final String KEY_SEPARATOR = "+";

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private AddonDeveloperReference addonDeveloperReference;
    private Version version;

    public AddonVersionReference(final Version version) {

        this.version = version;
    }

    public AddonVersionReference() {
    }

    public AddonVersionReference(final AddonDeveloperReference addonDeveloperReference,
                                 final Version version) {

        this.addonDeveloperReference = addonDeveloperReference;
        this.version = version;
    }

    public AddonVersionReference(final Platforms platform,
                                 final Layers layer,
                                 final Addons addonEnum,
                                 final Developers developer,
                                 final Version version) {

        PlatformReference platformReference = new PlatformReference(platform);
        LayerReference layerReference = new LayerReference(platformReference, layer);
        AddonReference addonReference = new AddonReference(layerReference, addonEnum);

        this.addonDeveloperReference = new AddonDeveloperReference(addonReference, developer);
        this.version = version;
    }

    public final Version getVersion() {
        return version;
    }

    public final AddonDeveloperReference getAddonDeveloperReference() {
        return addonDeveloperReference;
    }

    public final void setAddonDeveloperReference(AddonDeveloperReference addonDeveloperReference) {
        this.addonDeveloperReference = addonDeveloperReference;
    }

    public static AddonVersionReference getByKey(final String key) throws InvalidParameterException {

        String[] keySplit = key.split(Pattern.quote(KEY_SEPARATOR));

        if (keySplit.length != 5)
            throw new InvalidParameterException(new StringBuilder().append("Key: ").append(key).toString(), new StringBuilder().append("This key should respect the separation pattern using \"").append(KEY_SEPARATOR).append("\"").toString());

        final String platformString = keySplit[0];
        final String layerString = keySplit[1];
        final String addonEnumString = keySplit[2];
        final String developerString = keySplit[3];
        final String versionString = keySplit[4];

        final Platforms platform = Platforms.getByCode(platformString);
        final Layers layer = Layers.getByCode(layerString);
        final Addons addon = Addons.getByKey(addonEnumString);
        final Developers developer = Developers.getByCode(developerString);
        final Version version = new Version(versionString);

        return new AddonVersionReference(platform, layer, addon, developer, version);
    }

    public String toKey() {

        AddonReference addonReference = addonDeveloperReference.getAddonReference();
        LayerReference layerReference = addonReference.getLayerReference();
        PlatformReference platformReference = layerReference.getPlatformReference();

        Platforms platform = platformReference.getPlatform();
        Layers layer = layerReference.getLayer();
        Addons addon = addonReference.getAddon();
        Developers developer = addonDeveloperReference.getDeveloper();

        return platform.getCode() + KEY_SEPARATOR +
                layer.getCode() + KEY_SEPARATOR +
                addon.getCode() + KEY_SEPARATOR +
                developer.getCode() + KEY_SEPARATOR +
                version.toString();
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddonVersionReference)) return false;

        AddonVersionReference that = (AddonVersionReference) o;

        return version.equals(that.version) &&
                ((addonDeveloperReference == null && that.getAddonDeveloperReference() == null) || (addonDeveloperReference != null && addonDeveloperReference.equals(that.getAddonDeveloperReference())));
    }


    public Platforms getPlatform() {
        return addonDeveloperReference.getAddonReference().getLayerReference().getPlatformReference().getPlatform();
    }

    public Layers getLayers() {
        return addonDeveloperReference.getAddonReference().getLayerReference().getLayer();
    }

    public Addons getAddon() {
        return addonDeveloperReference.getAddonReference().getAddon();
    }

    public Developers getDeveloper() {
        return addonDeveloperReference.getDeveloper();

    }

    @Override
    public final int hashCode() {
        int c = 0;
        if (addonDeveloperReference != null)
            c += addonDeveloperReference.hashCode();
        if (version != null)
            c += version.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("AddonVersionReference{")
                .append("addonDeveloperReference=").append(addonDeveloperReference)
                .append(", version=").append(version)
                .append('}').toString();
    }

    public final String toString2() {
        return new StringBuilder()
                .append("AddonVersionReference{")
                .append("platform=").append(addonDeveloperReference.getAddonReference().getLayerReference().getPlatformReference().getPlatform())
                .append(", layer=").append(addonDeveloperReference.getAddonReference().getLayerReference().getLayer())
                .append(", addon=").append(addonDeveloperReference.getAddonReference().getAddon())
                .append(", developer=").append(addonDeveloperReference.getDeveloper())
                .append(", version=").append(version).append('}').toString();
    }

    public final String toString3() {
        return new StringBuilder()
                .append("Addon{")
                .append("").append(addonDeveloperReference.getAddonReference().getLayerReference().getPlatformReference().getPlatform())
                .append(", ").append(addonDeveloperReference.getAddonReference().getLayerReference().getLayer())
                .append(", ").append(addonDeveloperReference.getAddonReference().getAddon())
                .append('}').toString();
    }

}
