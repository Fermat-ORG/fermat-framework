package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.ArrayList;
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
public abstract class AbstractPlugin implements Plugin, Service {

    private Map<AddonVersionReference , AbstractAddon > addons ;
    private Map<PluginVersionReference, AbstractPlugin> plugins;

    private final PluginVersionReference pluginVersionReference;
    protected        ServiceStatus          serviceStatus;

    /**
     * Default constructor assigning version 1.
     */
    public AbstractPlugin() {

        this.pluginVersionReference = new PluginVersionReference(new Version("1.0.0"));

        instantiatePlugin();
    }

    public AbstractPlugin(final PluginVersionReference pluginVersionReference) {

        this.pluginVersionReference = pluginVersionReference;

        instantiatePlugin();
    }

    private void instantiatePlugin() {
        this.addons  = new HashMap<>();
        this.plugins = new HashMap<>();

        serviceStatus = ServiceStatus.CREATED;
    }

    public final void addAddonReference(final AddonVersionReference addonReference,
                                        final AbstractAddon         addon         ) {

        addons.put(addonReference, addon);
    }

    public final void addPluginReference(final PluginVersionReference pluginReference,
                                         final AbstractPlugin         plugin         ) {

        plugins.put(pluginReference, plugin);
    }

    protected final AbstractAddon getAddonReference(final AddonVersionReference addonReference) {

        return addons.get(addonReference);
    }

    protected final AbstractPlugin getPluginReference(final PluginVersionReference pluginReference) {

        return plugins.get(pluginReference);
    }

    public final PluginVersionReference getPluginVersionReference() {
        return pluginVersionReference;
    }

    @Override
    public final ServiceStatus getStatus() {
        return serviceStatus;
    }

    public final boolean isStarted() {
        return serviceStatus == ServiceStatus.STARTED;
    }

    public final boolean isCreated() {
        return serviceStatus == ServiceStatus.CREATED;
    }

    public final boolean isStopped() {
        return serviceStatus == ServiceStatus.STOPPED;
    }

    public final boolean isPaused() {
        return serviceStatus == ServiceStatus.PAUSED;
    }

    public List<AddonVersionReference > getNeededAddonReferences() {
        return new ArrayList<>();
    }

    public List<PluginVersionReference> getNeededPluginReferences() {
        return new ArrayList<>();
    }

    public List<DevelopersUtilReference> getAvailableDeveloperUtils() {
        return new ArrayList<>();
    }

    public FeatureForDevelopers getFeatureForDevelopers(final DevelopersUtilReference developersUtilReference) throws CantGetFeatureForDevelopersException {
        return null;
    }

    protected void validateAndAssignReferences() {

    }

    public abstract void setId(UUID pluginId);

}
