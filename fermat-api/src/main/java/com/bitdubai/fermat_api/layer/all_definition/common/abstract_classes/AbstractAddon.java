package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatAddonsEnum;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractAddon</code>
 * contains the basic functionality of a Fermat Addon.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractAddon {

    private Map<FermatAddonsEnum , AbstractAddon> addons ;

    private final Version version  ;

    public AbstractAddon(final Version version) {

        this.addons  = new ConcurrentHashMap<>();
        this.version = version;
    }

    public final void addAddonReference(final FermatAddonsEnum descriptor,
                                        final AbstractAddon addon     ) {

        addons.put(descriptor, addon);
    }


    protected final AbstractAddon getAddonReference(final FermatAddonsEnum descriptor) {

        return addons.get(descriptor);
    }

    public final Version getVersion() {
        return version;
    }

    public abstract List<AddonReference> getNeededAddonReferences();

    public abstract void setId(UUID pluginId);
}
