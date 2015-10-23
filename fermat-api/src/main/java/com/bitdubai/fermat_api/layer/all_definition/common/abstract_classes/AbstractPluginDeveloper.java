package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartPluginDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.DeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.VersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPluginDeveloper</code>
 * haves all the main functionality of a plugin developer.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public abstract class AbstractPluginDeveloper {

    private final Map<VersionReference, AbstractPlugin> versions;

    private final DeveloperReference developerReference;

    /**
     * default bitdubai constructor (remove in the future).
     * assigns by default developer bitdubai
     * by defaults creates the first version of a plugin.
     *
     * @param plugin abstract plugin instance.
     */
    public AbstractPluginDeveloper(final AbstractPlugin plugin) {

        this.versions = new ConcurrentHashMap<>();

        this.versions.put(new VersionReference(new Version("1.0.0")), plugin);

        this.developerReference = new DeveloperReference(Developers.BITDUBAI);
    }

    /**
     * normal constructor with params.
     * assigns a developer to the plugin developer class
     *
     * @param developer an element of developers enum.
     */
    public AbstractPluginDeveloper(final Developers developer) {

        this.developerReference = new DeveloperReference(developer);

        this.versions = new ConcurrentHashMap<>();
    }

    /**
     * normal constructor with params.
     * assigns a developer to the plugin developer class
     *
     * @param developerReference a directly built developer reference.
     */
    public AbstractPluginDeveloper(final DeveloperReference developerReference) {

        this.developerReference = developerReference;

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

        VersionReference versionReference = abstractPlugin.getVersionReference();

        versionReference.setDeveloperReference(this.developerReference);

        if(versions.containsKey(versionReference))
            throw new CantRegisterVersionException(versionReference.toString(), "version already exists for this plugin developer.");

        versions.put(
                versionReference,
                abstractPlugin
        );

    }

    public final AbstractPlugin getPluginByVersion(final VersionReference versionReference) throws VersionNotFoundException {
        if (versions.containsKey(versionReference)) {
            return versions.get(versionReference);
        } else {

            throw new VersionNotFoundException(versionReference.toString(), "version not found in the specified plugin developer.");
        }
    }

    public final DeveloperReference getDeveloperReference() {
        return developerReference;
    }

    public abstract void start() throws CantStartPluginDeveloperException;

}
