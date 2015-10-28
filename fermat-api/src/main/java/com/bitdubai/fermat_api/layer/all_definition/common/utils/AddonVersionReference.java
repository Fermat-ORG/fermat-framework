package com.bitdubai.fermat_api.layer.all_definition.common.utils;

import com.bitdubai.fermat_api.layer.all_definition.common.enums.OperativeSystems;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference</code>
 * haves all the information of a Addon Version Reference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class AddonVersionReference {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private       AddonDeveloperReference addonDeveloperReference;
    private final Version                 version                ;

    public AddonVersionReference(final Version version) {

        this.version = version;
    }

    public AddonVersionReference(final AddonDeveloperReference addonDeveloperReference,
                                 final Version                 version                ) {

        this.addonDeveloperReference = addonDeveloperReference;
        this.version                 = version                ;
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

    public AddonVersionReference(final OperativeSystems operativeSystem,
                                 final Platforms platform,
                                 final Layers layer,
                                 final Addons addonEnum,
                                 final Developers developer,
                                 final Version version) {

        PlatformReference platformReference = new PlatformReference(operativeSystem, platform);
        LayerReference layerReference = new LayerReference(platformReference, layer);
        AddonReference addonReference = new AddonReference(layerReference, addonEnum);

        this.addonDeveloperReference = new AddonDeveloperReference(addonReference, developer);
        this.version                 = version;
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddonVersionReference)) return false;

        AddonVersionReference that = (AddonVersionReference) o;

        return version.equals(that.version) &&
                ((addonDeveloperReference == null && that.getAddonDeveloperReference() == null) || (addonDeveloperReference != null && addonDeveloperReference.equals(that.getAddonDeveloperReference())));
    }

    @Override
    public final int hashCode() {
        int c = 0;
        if(addonDeveloperReference != null)
            c+= addonDeveloperReference.hashCode();
        c += version.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public final String toString() {
        return "AddonVersionReference{" +
                "addonDeveloperReference=" + addonDeveloperReference +
                ", version=" + version +
                '}';
    }
}
