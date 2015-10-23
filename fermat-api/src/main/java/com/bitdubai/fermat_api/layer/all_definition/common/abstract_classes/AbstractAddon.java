package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatAddonsEnum;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
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

    private final Map<AddonVersionReference, AbstractAddon> addons;

    private final AddonVersionReference addonVersionReference  ;

    public AbstractAddon(final AddonVersionReference addonVersionReference) {

        this.addons  = new ConcurrentHashMap<>();
        this.addonVersionReference = addonVersionReference;
    }

    public final void addAddonReference(final AddonVersionReference addonReference,
                                        final AbstractAddon         addon         ) {

        addons.put(addonReference, addon);
    }


    protected final AbstractAddon getAddonReference(final AddonVersionReference addonReference) {

        return addons.get(addonReference);
    }

    public final AddonVersionReference getAddonVersionReference() {
        return addonVersionReference;
    }

    public abstract List<AddonVersionReference> getNeededAddonReferences();

    public abstract void setId(UUID pluginId);
}
