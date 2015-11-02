package com.bitdubai.fermat_api.layer.all_definition.common.system.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;

/**
 * The class <code>PluginDeveloperReference</code>
 * haves all the information of a Plugin Developer Reference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class PluginDeveloperReference {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private PluginReference pluginReference;
    private final Developers      developer      ;

    public PluginDeveloperReference(final Developers developer) {

        this.developer = developer;
    }

    public PluginDeveloperReference(final PluginReference pluginReference,
                                    final Developers      developer      ) {

        this.pluginReference = pluginReference;
        this.developer       = developer      ;
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
        c += developer.hashCode();
        if(pluginReference != null)
            c+= pluginReference.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public String toString() {
        return "PluginDeveloperReference{" +
                "pluginReference=" + pluginReference +
                ", developer=" + developer +
                '}';
    }
}
