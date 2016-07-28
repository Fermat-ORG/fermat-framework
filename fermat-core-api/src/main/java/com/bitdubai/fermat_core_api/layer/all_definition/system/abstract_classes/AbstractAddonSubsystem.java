package com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAddonDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterDeveloperException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.DeveloperNotFoundException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The abstract class <code>AbstractAddonSubsystem</code>
 * contains all the basic functionality of an addon Subsystem class.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractAddonSubsystem {

    private final ConcurrentHashMap<AddonDeveloperReference, AbstractAddonDeveloper> developers;

    private final AddonReference addonReference;

    public AbstractAddonSubsystem(final AddonReference addonReference) {

        this.addonReference = addonReference;

        this.developers = new ConcurrentHashMap<>();
    }

    /**
     * Throw the method <code>registerDeveloper</code> you can add new developers to the addon subsystem.
     * Here we'll corroborate too that the developer is not added twice.
     *
     * @param addonDeveloper addonDeveloper in-self.
     * @throws CantRegisterDeveloperException if something goes wrong.
     */
    protected final void registerDeveloper(final AbstractAddonDeveloper addonDeveloper) throws CantRegisterDeveloperException {

        AddonDeveloperReference addonDeveloperReference = addonDeveloper.getAddonDeveloperReference();

        addonDeveloperReference.setAddonReference(this.addonReference);

        try {

            if (developers.containsKey(addonDeveloperReference))
                throw new CantRegisterDeveloperException(addonDeveloperReference.toString(), "developer already exists for this addon.");

            addonDeveloper.start();

            developers.put(
                    addonDeveloperReference,
                    addonDeveloper
            );

        } catch (final CantStartAddonDeveloperException e) {

            throw new CantRegisterDeveloperException(e, addonDeveloperReference.toString(), "Error trying to start the developer.");
        }

    }

    public final AbstractAddonDeveloper getDeveloperByReference(final AddonDeveloperReference addonDeveloperReference) throws DeveloperNotFoundException {

        if (developers.containsKey(addonDeveloperReference)) {
            return developers.get(addonDeveloperReference);
        } else {

            throw new DeveloperNotFoundException(addonDeveloperReference.toString(), "developer not found in the specified addon subsystem.");
        }
    }

    public AddonReference getAddonReference() {
        return addonReference;
    }

    public final void fillVersions(final ConcurrentHashMap<AddonVersionReference, AbstractAddon> versions) {

        for (ConcurrentHashMap.Entry<AddonDeveloperReference, AbstractAddonDeveloper> developer : developers.entrySet())
            versions.putAll(developer.getValue().listVersions());

    }

    public abstract void start() throws CantStartSubsystemException;

}
