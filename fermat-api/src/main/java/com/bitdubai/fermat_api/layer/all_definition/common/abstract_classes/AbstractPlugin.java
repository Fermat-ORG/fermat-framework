package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.*;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPlugin</code>
 * contains the basic functionality of a Fermat Plugin.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractPlugin {

    private Map<FermatAddonsEnum , AbstractAddon > addons ;
    private Map<FermatPluginsEnum, AbstractPlugin> plugins;

    public AbstractPlugin() {

        this.addons  = new HashMap<>();
        this.plugins = new HashMap<>();
    }

    public final void addAddonReference(final FermatAddonsEnum descriptor,
                                        final AbstractAddon    addon     ) {

        addons.put(descriptor, addon);
    }

    public final void addPluginReference(final FermatPluginsEnum descriptor,
                                         final AbstractPlugin    plugin    ) {

        plugins.put(descriptor, plugin);
    }

    protected final AbstractAddon getAddonReference(final FermatAddonsEnum descriptor) {

        return addons.get(descriptor);
    }

    protected final AbstractPlugin getPluginReference(final FermatPluginsEnum descriptor) {

        return plugins.get(descriptor);
    }

    public abstract List<AddonReference> getNeededAddonReferences();

    public abstract List<PluginReference> getNeededPluginReferences();

    public abstract void setId(UUID pluginId);

}
