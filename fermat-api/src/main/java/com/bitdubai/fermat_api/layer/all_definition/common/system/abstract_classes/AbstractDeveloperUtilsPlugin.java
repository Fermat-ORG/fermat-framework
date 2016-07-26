package com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetDeveloperToolException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.DeveloperTool;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The abstract class <code>AbstractDeveloperUtilsPlugin</code>
 * contains all the basic functionality of a developer utils plugin of fermat.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public abstract class AbstractDeveloperUtilsPlugin<T extends DeveloperTool> extends AbstractPlugin {

    private final Map<AddonVersionReference, AbstractAddon> availableAddonReferences;
    private final Map<PluginVersionReference, AbstractPlugin> availablePluginReferences;

    private final DevelopersUtilReference developersUtilReference;

    public AbstractDeveloperUtilsPlugin(final PluginVersionReference pluginVersionReference,
                                        final DevelopersUtilReference developersUtilReference) {
        super(pluginVersionReference);

        this.developersUtilReference = developersUtilReference;

        this.availableAddonReferences = new ConcurrentHashMap<>();
        this.availablePluginReferences = new ConcurrentHashMap<>();
    }

    public final void registerAvailableAddon(final AddonVersionReference addonVersionReference,
                                             final AbstractAddon abstractAddon) {

        availableAddonReferences.put(addonVersionReference, abstractAddon);
    }

    protected final List<AddonVersionReference> listAvailableAddons() {
        return Arrays.asList((AddonVersionReference[]) availableAddonReferences.keySet().toArray());
    }

    public final void registerAvailablePlugin(final PluginVersionReference pluginVersionReference,
                                              final AbstractPlugin abstractPlugin) {

        availablePluginReferences.put(pluginVersionReference, abstractPlugin);
    }

    protected final List<PluginVersionReference> listAvailablePlugins() {
        return Arrays.asList((PluginVersionReference[]) availablePluginReferences.keySet().toArray());
    }

    protected final FeatureForDevelopers getFeatureForDevelopers(final AddonVersionReference addonVersionReference) throws CantGetFeatureForDevelopersException {

        return availableAddonReferences.get(addonVersionReference).getFeatureForDevelopers(this.developersUtilReference);
    }

    protected final FeatureForDevelopers getFeatureForDevelopers(final PluginVersionReference pluginVersionReference) throws CantGetFeatureForDevelopersException {

        return availablePluginReferences.get(pluginVersionReference).getFeatureForDevelopers(this.developersUtilReference);
    }

    public final DevelopersUtilReference getDevelopersUtilReference() {
        return developersUtilReference;
    }

    public abstract T getDeveloperTool() throws CantGetDeveloperToolException;

}
