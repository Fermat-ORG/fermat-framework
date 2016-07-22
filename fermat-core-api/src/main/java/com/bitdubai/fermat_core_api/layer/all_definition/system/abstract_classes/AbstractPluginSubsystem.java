package com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes;

import com.bitdubai.fermat_api.FermatContext;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.DeveloperPluginInterface;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.PluginDeveloperReferenceInterface;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterDeveloperException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.DeveloperNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The abstract class <code>AbstractPluginSubsystem</code>
 * contains all the basic functionality of a plugin Subsystem class.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractPluginSubsystem {

    private final Map<PluginDeveloperReferenceInterface, DeveloperPluginInterface> developers;

    private final PluginReference pluginReference;
    private FermatContext fermatContext;

    public AbstractPluginSubsystem(final PluginReference pluginReference) {

        this.pluginReference = pluginReference;

        this.developers = new ConcurrentHashMap<>();
    }

    public AbstractPluginSubsystem(PluginReference pluginReference, FermatContext fermatContext) {
        this.fermatContext = fermatContext;
        this.pluginReference = pluginReference;
        this.developers = new ConcurrentHashMap<>();
    }

    public Collection<DeveloperPluginInterface> getDevelopers() {
        return developers.values();
    }

    /**
     * Through the method <code>registerDeveloper</code> you can add new developers to the plugin subsystem.
     * Here we'll corroborate too that the developer is not added twice.
     *
     * @param pluginDeveloper pluginDeveloper in-self.
     * @throws CantRegisterDeveloperException if something goes wrong.
     */
    protected final void registerDeveloper(final DeveloperPluginInterface pluginDeveloper) throws CantRegisterDeveloperException {

        PluginDeveloperReferenceInterface pluginDeveloperReference = pluginDeveloper.getPluginDeveloperReference();

        pluginDeveloperReference.setPluginReference(this.pluginReference);

        try {

            if (developers.containsKey(pluginDeveloperReference))
                throw new CantRegisterDeveloperException(pluginDeveloperReference.toString(), "developer already exists for this plugin.");

            pluginDeveloper.start();

            developers.put(
                    pluginDeveloperReference,
                    pluginDeveloper
            );

        } catch (final CantStartPluginDeveloperException e) {

            throw new CantRegisterDeveloperException(e, pluginDeveloperReference.toString(), "Error trying to start the developer.");
        }
    }

    protected final void registerDeveloperMati(final DeveloperPluginInterface pluginDeveloper) throws CantRegisterDeveloperException {
        PluginDeveloperReferenceInterface pluginDeveloperReference = pluginDeveloper.getPluginDeveloperReference();
        pluginDeveloperReference.setPluginReference(this.pluginReference);
        try {

            if (developers.containsKey(pluginDeveloperReference))
                throw new CantRegisterDeveloperException(pluginDeveloperReference.toString(), "developer already exists for this plugin.");

            pluginDeveloper.start();

            developers.put(
                    pluginDeveloperReference,
                    pluginDeveloper
            );

        } catch (final CantStartPluginDeveloperException e) {

            throw new CantRegisterDeveloperException(e, pluginDeveloperReference.toString(), "Error trying to start the developer.");
        }
    }

    protected void registerDeveloperMati(String pluginName) throws CantRegisterDeveloperException {
        DeveloperPluginInterface developerPluginInterface = (DeveloperPluginInterface) getFermatContext().loadObject(pluginName);
        developerPluginInterface.setFermatContext(getFermatContext());
        registerDeveloperMati(developerPluginInterface);
    }


    public final DeveloperPluginInterface getDeveloperByReference(final PluginDeveloperReference pluginDeveloperReference) throws DeveloperNotFoundException {
        if (developers.containsKey(pluginDeveloperReference)) {
            return developers.get(pluginDeveloperReference);
        } else {
            throw new DeveloperNotFoundException(pluginDeveloperReference.toString(), "developer not found in the specified plugin subsystem.");
        }

    }

    public final void fillVersions(final ConcurrentHashMap<PluginVersionReference, AbstractPlugin> versions) {
        for (ConcurrentHashMap.Entry<PluginDeveloperReferenceInterface, DeveloperPluginInterface> developer : developers.entrySet()) {
            try {
                versions.putAll(developer.getValue().listVersions());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public final void fillVersionsMati(final List<PluginVersionReference> versions) {
        for (ConcurrentHashMap.Entry<PluginDeveloperReferenceInterface, DeveloperPluginInterface> developer : developers.entrySet()) {
            try {
                versions.addAll(developer.getValue().listVersionsMati());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public PluginReference getPluginReference() {
        return pluginReference;
    }

    public abstract void start() throws CantStartSubsystemException;

    public FermatContext getFermatContext() {
        return fermatContext;
    }


}
