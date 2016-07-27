package com.bitdubai.fermat_api.layer.all_definition.common.system.utils;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.PluginDeveloperReferenceInterface;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;

import java.io.Serializable;

/**
 * The class <code>PluginDeveloperReference</code>
 * haves all the information of a Plugin Developer Reference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class PluginDeveloperReference implements PluginDeveloperReferenceInterface, Serializable {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private PluginReference pluginReference;
    private Developers developer;

    public PluginDeveloperReference(final Developers developer) {

        this.developer = developer;
    }

    public PluginDeveloperReference(final PluginReference pluginReference,
                                    final Developers developer) {

        this.pluginReference = pluginReference;
        this.developer = developer;
    }

    public PluginDeveloperReference() {
    }

    public final Developers getDeveloper() {
        return developer;
    }

    public final PluginReference getPluginReference() {
        return pluginReference;
    }

    public final void setPluginReference(final PluginReference pluginReference) {
        this.pluginReference = pluginReference;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PluginDeveloperReference)) return false;

        PluginDeveloperReference that = (PluginDeveloperReference) o;

        return developer.equals(that.developer) &&
                ((pluginReference == null && that.getPluginReference() == null) || (pluginReference != null && pluginReference.equals(that.getPluginReference())));
    }

    @Override
    public final int hashCode() {
        int c = 0;
        if (developer != null) {
            c += developer.hashCode();
            if (pluginReference != null)
                c += pluginReference.hashCode();
        } else {
            System.err.println("PluginDeveloperReference, developer null");
        }
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("PluginDeveloperReference{")
                .append("pluginReference=").append(pluginReference)
                .append(", developer=").append(developer)
                .append('}').toString();
    }
}
