package com.bitdubai.fermat_api.layer.all_definition.common.system.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;

import java.io.Serializable;

/**
 * The class <code>AddonDeveloperReference</code>
 * haves all the information of a Addon Developer Reference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class AddonDeveloperReference implements Serializable {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private AddonReference addonReference;
    private Developers developer;

    public AddonDeveloperReference(final Developers developer) {

        this.developer = developer;
    }

    public AddonDeveloperReference() {
    }

    public AddonDeveloperReference(final AddonReference addonReference,
                                   final Developers developer) {

        this.addonReference = addonReference;
        this.developer = developer;
    }

    public final Developers getDeveloper() {
        return developer;
    }

    public final AddonReference getAddonReference() {
        return addonReference;
    }

    public final void setAddonReference(AddonReference addonReference) {
        this.addonReference = addonReference;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddonDeveloperReference)) return false;

        AddonDeveloperReference that = (AddonDeveloperReference) o;

        return developer.equals(that.developer) &&
                ((addonReference == null && that.getAddonReference() == null) || (addonReference != null && addonReference.equals(that.getAddonReference())));
    }

    @Override
    public final int hashCode() {
        int c = 0;
        if (developer != null)
            c += developer.hashCode();
        if (addonReference != null)
            c += addonReference.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("AddonDeveloperReference{")
                .append("addonReference=").append(addonReference)
                .append(", developer=").append(developer)
                .append('}').toString();
    }
}
