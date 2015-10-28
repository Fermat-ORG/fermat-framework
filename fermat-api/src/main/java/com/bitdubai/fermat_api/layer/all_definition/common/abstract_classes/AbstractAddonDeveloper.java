package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartAddonDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPluginDeveloper</code>
 * haves all the main functionality of a plugin developer.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public abstract class AbstractAddonDeveloper {

    private final Map<AddonVersionReference, AbstractAddon> versions;

    private final AddonDeveloperReference addonDeveloperReference;

    /**
     * normal constructor with params.
     * assigns a developer to the addon developer class
     *
     * @param addonDeveloperReference a directly built developer reference.
     */
    public AbstractAddonDeveloper(final AddonDeveloperReference addonDeveloperReference) {

        this.addonDeveloperReference = addonDeveloperReference;

        this.versions = new ConcurrentHashMap<>();
    }

    /**
     * Throw the method <code>registerVersion</code> you can add new versions to the addon developer.
     * Here we'll corroborate too that the version is not added twice.
     *
     * @param abstractAddon  addon in-self.
     *
     * @throws CantRegisterVersionException if something goes wrong.
     */
    protected final void registerVersion(final AbstractAddon abstractAddon) throws CantRegisterVersionException {

        AddonVersionReference addonVersionReference = abstractAddon.getAddonVersionReference();

        addonVersionReference.setAddonDeveloperReference(this.addonDeveloperReference);

        if(versions.containsKey(addonVersionReference))
            throw new CantRegisterVersionException(addonVersionReference.toString(), "version already exists for this addon developer.");

        versions.put(
                addonVersionReference,
                abstractAddon
        );

    }

    public final AbstractAddon getAddonByVersion(final AddonVersionReference addonVersionReference) throws VersionNotFoundException {
        if (versions.containsKey(addonVersionReference)) {
            return versions.get(addonVersionReference);
        } else {

            throw new VersionNotFoundException(addonVersionReference.toString(), "version not found in the specified addon developer.");
        }
    }

    public final AddonDeveloperReference getAddonDeveloperReference() {
        return addonDeveloperReference;
    }

    public abstract void start() throws CantStartAddonDeveloperException;

}
