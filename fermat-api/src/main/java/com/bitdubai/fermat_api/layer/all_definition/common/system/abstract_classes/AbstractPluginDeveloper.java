package com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>AbstractPluginDeveloper</code>
 * haves all the main functionality of a plugin developer.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public abstract class AbstractPluginDeveloper {

    private final Map<PluginVersionReference, AbstractPlugin> versions;

    private final PluginDeveloperReference pluginDeveloperReference;

    /**
     * normal constructor with params.
     * assigns a developer to the plugin developer class
     *
     * @param pluginDeveloperReference a directly built developer reference.
     */
    public AbstractPluginDeveloper(final PluginDeveloperReference pluginDeveloperReference) {

        this.pluginDeveloperReference = pluginDeveloperReference;

        this.versions = new ConcurrentHashMap<>();
    }

    /**
     * Throw the method <code>registerVersion</code> you can add new versions to the plugin developer.
     * Here we'll corroborate too that the version is not added twice.
     *
     * @param abstractPlugin  plugin in-self.
     *
     * @throws CantRegisterVersionException if something goes wrong.
     */
    protected final void registerVersion(final AbstractPlugin abstractPlugin) throws CantRegisterVersionException {

        PluginVersionReference pluginVersionReference = abstractPlugin.getPluginVersionReference();

        pluginVersionReference.setPluginDeveloperReference(this.pluginDeveloperReference);

        if(versions.containsKey(pluginVersionReference))
            throw new CantRegisterVersionException(pluginVersionReference.toString(), "version already exists for this plugin developer.");

        versions.put(
                pluginVersionReference,
                abstractPlugin
        );

    }

    public final AbstractPlugin getPluginByVersion(final PluginVersionReference pluginVersionReference) throws VersionNotFoundException {
        if (versions.containsKey(pluginVersionReference)) {
            return versions.get(pluginVersionReference);
        } else {

            throw new VersionNotFoundException(pluginVersionReference.toString(), "version not found in the specified plugin developer.");
        }
    }

    public final PluginDeveloperReference getPluginDeveloperReference() {
        return pluginDeveloperReference;
    }

    public abstract void start() throws CantStartPluginDeveloperException;

}
