package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantGetDeveloperToolException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.DeveloperTool;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginVersionReference;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractDeveloperUtilsPlugin</code>
 * contains all the basic functionality of a developer utils plugin of fermat.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public abstract class AbstractDeveloperUtilsPlugin<T extends DeveloperTool, Z extends FeatureForDevelopers> {

    private final Map<AddonVersionReference , AbstractAddon > addonReferences ;
    private final Map<PluginVersionReference, AbstractPlugin> pluginReferences;

    private final DevelopersUtilReference developersUtilReference;

    public AbstractDeveloperUtilsPlugin(final DevelopersUtilReference developersUtilReference) {

        this.developersUtilReference = developersUtilReference;

        this.addonReferences  = new ConcurrentHashMap<>();
        this.pluginReferences = new ConcurrentHashMap<>();
    }

    public final void registerAvailableAddon(final AddonVersionReference addonVersionReference,
                                             final AbstractAddon         abstractAddon        ) {

        addonReferences.put(addonVersionReference, abstractAddon);
    }

    protected final List<AddonVersionReference> listAvailableAddons() {
        return Arrays.asList((AddonVersionReference[]) addonReferences.keySet().toArray());
    }

    public final void registerAvailablePlugin(final PluginVersionReference pluginVersionReference,
                                              final AbstractPlugin         abstractPlugin        ) {

        pluginReferences.put(pluginVersionReference, abstractPlugin);
    }

    protected final List<PluginVersionReference> listAvailablePlugins() {
        return Arrays.asList((PluginVersionReference[]) pluginReferences.keySet().toArray());
    }

    @SuppressWarnings("unchecked")
    protected final Z getFeatureForDevelopers(final AddonVersionReference addonVersionReference) throws CantGetFeatureForDevelopersException {

        try {

            return (Z) addonReferences.get(addonVersionReference).getFeatureForDevelopers(this.developersUtilReference);

        } catch (ClassCastException e) {

            throw new CantGetFeatureForDevelopersException(e, addonVersionReference.toString(), "Feature for developers is not the expected.");
        }
    }

    @SuppressWarnings("unchecked")
    protected final Z getFeatureForDevelopers(final PluginVersionReference pluginVersionReference) throws CantGetFeatureForDevelopersException {

        try {

            return (Z) pluginReferences.get(pluginVersionReference).getFeatureForDevelopers(this.developersUtilReference);

        } catch (ClassCastException e) {

            throw new CantGetFeatureForDevelopersException(e, pluginVersionReference.toString(), "Feature for developers is not the expected.");
        }
    }

    public final DevelopersUtilReference getDevelopersUtilReference() {
        return developersUtilReference;
    }

    public abstract T getDeveloperTool() throws CantGetDeveloperToolException;

}
