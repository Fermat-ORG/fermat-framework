package com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAddonDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>AbstractPluginDeveloper</code>
 * haves all the main functionality of a plugin developer.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public abstract class AbstractAddonDeveloper {

    private final ConcurrentHashMap<AddonVersionReference, AbstractAddon> versions;

    private final AddonDeveloperReference addonDeveloperReference;

    /**
     * Constructor with params.
     * assigns a developer to the addon developer class
     *
     * @param addonDeveloperReference a directly built developer reference.
     */
    public AbstractAddonDeveloper(final AddonDeveloperReference addonDeveloperReference) {

        this.addonDeveloperReference = addonDeveloperReference;

        this.versions = new ConcurrentHashMap<>();
    }

    /**
     * Through the method <code>registerVersion</code> you can add new versions to the addon developer.
     * Here we'll corroborate too that the version is not added twice.
     *
     * @param abstractAddon addon in-self.
     * @throws CantRegisterVersionException if something goes wrong.
     */
    protected final void registerVersion(final AbstractAddon abstractAddon) throws CantRegisterVersionException {

        AddonVersionReference addonVersionReference = abstractAddon.getAddonVersionReference();

        addonVersionReference.setAddonDeveloperReference(this.addonDeveloperReference);

        if (versions.putIfAbsent(addonVersionReference, abstractAddon) != null)
            throw new CantRegisterVersionException(addonVersionReference.toString3(), "Version already exists for this addon developer.");
    }

    /**
     * Through the method <code>getAddonByVersion</code> you can get a specific version of the addon.
     *
     * @param addonVersionReference addon reference.
     * @throws VersionNotFoundException if something goes wrong.
     */
    public final AbstractAddon getAddonByVersion(final AddonVersionReference addonVersionReference) throws VersionNotFoundException {

        if (versions.containsKey(addonVersionReference)) {
            return versions.get(addonVersionReference);
        } else {

            throw new VersionNotFoundException(addonVersionReference.toString3(), "version not found in the specified addon developer.");
        }
    }

    public final ConcurrentHashMap<AddonVersionReference, AbstractAddon> listVersions() {

        return versions;
    }

    public final AddonDeveloperReference getAddonDeveloperReference() {
        return addonDeveloperReference;
    }

    public abstract void start() throws CantStartAddonDeveloperException;

}
