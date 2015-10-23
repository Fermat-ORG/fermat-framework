package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantRegisterDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartPluginDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.DeveloperNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.DeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPluginSubsystem</code>
 * contains all the basic functionality of a plugin Subsystem class.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractPluginSubsystem {

    private final Map<DeveloperReference, AbstractPluginDeveloper> developers;

    private final PluginReference pluginReference;

    public AbstractPluginSubsystem(final PluginReference pluginReference) {

        this.pluginReference = pluginReference;

        this.developers = new ConcurrentHashMap<>();
    }

    /**
     * Throw the method <code>registerDeveloper</code> you can add new developers to the plugin subsystem.
     * Here we'll corroborate too that the developer is not added twice.
     *
     * @param pluginDeveloper  pluginDeveloper in-self.
     *
     * @throws CantRegisterDeveloperException if something goes wrong.
     */
    protected final void registerDeveloper(final AbstractPluginDeveloper pluginDeveloper) throws CantRegisterDeveloperException {

        DeveloperReference developerReference = pluginDeveloper.getDeveloperReference();

        developerReference.setPluginReference(this.pluginReference);

        try {

            if(developers.containsKey(developerReference))
                throw new CantRegisterDeveloperException(developerReference.toString(), "developer already exists for this plugin.");

            pluginDeveloper.start();

            developers.put(
                    developerReference,
                    pluginDeveloper
            );

        } catch (final CantStartPluginDeveloperException e) {

            throw new CantRegisterDeveloperException(e, developerReference.toString(), "Error trying to start the developer.");
        }

    }

    public final AbstractPluginDeveloper getDeveloperByReference(final DeveloperReference developerReference) throws DeveloperNotFoundException {

        if (developers.containsKey(developerReference)) {
            return developers.get(developerReference);
        } else {

            throw new DeveloperNotFoundException(developerReference.toString(), "developer not found in the specified plugin subsystem.");
        }
    }

    public PluginReference getPluginReference() {
        return pluginReference;
    }

    public abstract void start() throws CantStartSubsystemException;

}
