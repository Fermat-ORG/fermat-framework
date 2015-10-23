package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.VersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

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
public abstract class AbstractPlugin implements Plugin {

    private Map<AddonReference , AbstractAddon > addons ;
    private Map<PluginReference, AbstractPlugin> plugins;

    private final VersionReference versionReference;

    /**
     * Default constructor assigning version 1.
     */
    public AbstractPlugin() {

        this.versionReference = new VersionReference(new Version("1.0.0"));

        this.addons  = new HashMap<>();
        this.plugins = new HashMap<>();
    }

    public AbstractPlugin(final VersionReference versionReference) {

        this.versionReference = versionReference;

        this.addons  = new HashMap<>();
        this.plugins = new HashMap<>();
    }

    public final void addAddonReference(final AddonReference addonReference,
                                        final AbstractAddon  addon         ) {

        addons.put(addonReference, addon);
    }

    public final void addPluginReference(final PluginReference pluginReference,
                                         final AbstractPlugin  plugin         ) {

        plugins.put(pluginReference, plugin);
    }

    protected final AbstractAddon getAddonReference(final AddonReference addonReference) {

        return addons.get(addonReference);
    }

    protected final AbstractPlugin getPluginReference(final PluginReference pluginReference) {

        return plugins.get(pluginReference);
    }

    public final VersionReference getVersionReference() {
        return versionReference;
    }

    public abstract List<AddonReference> getNeededAddonReferences();

    public abstract List<PluginReference> getNeededPluginReferences();

    public abstract void setId(UUID pluginId);

}
