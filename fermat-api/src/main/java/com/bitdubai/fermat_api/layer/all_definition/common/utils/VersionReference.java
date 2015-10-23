package com.bitdubai.fermat_api.layer.all_definition.common.utils;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.utils.VersionReference</code>
 * haves all the information of a Developer Reference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class VersionReference {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private       DeveloperReference developerReference;
    private final Version            version           ;

    public VersionReference(final Version version) {

        this.version = version;
    }

    public VersionReference(final DeveloperReference developerReference,
                            final Version            version           ) {

        this.developerReference = developerReference;
        this.version            = version           ;
    }

    public VersionReference(final Platforms         platform  ,
                            final Layers            layer     ,
                            final FermatPluginsEnum pluginEnum,
                            final Developers        developer ,
                            final Version           version   ){

        PlatformReference platformReference = new PlatformReference(platform);
        LayerReference layerReference = new LayerReference(platformReference, layer);
        PluginReference pluginReference = new PluginReference(layerReference, pluginEnum);

        this.developerReference = new DeveloperReference(pluginReference, developer);;
        this.version            = version           ;
    }

    public final Version getVersion() {
        return version;
    }

    public final DeveloperReference getDeveloperReference() {
        return developerReference;
    }

    public final void setDeveloperReference(final DeveloperReference developerReference) {
        this.developerReference = developerReference;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VersionReference)) return false;

        VersionReference that = (VersionReference) o;

        return version.equals(that.version) &&
                ((developerReference == null && that.getDeveloperReference() == null) || (developerReference != null && developerReference.equals(that.getDeveloperReference())));
    }

    @Override
    public final int hashCode() {
        int c = 0;
        if(developerReference != null)
            c+= developerReference.hashCode();
        c += version.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public String toString() {
        return "VersionReference{" +
                "developerReference=" + developerReference +
                ", version=" + version +
                '}';
    }
}
