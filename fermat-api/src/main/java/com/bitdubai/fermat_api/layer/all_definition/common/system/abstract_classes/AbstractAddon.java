package com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededOsContext;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantAssignOsContextException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantAssignReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantCollectReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantListNeededReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.IncompatibleOsContextException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.IncompatibleReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>AbstractAddon</code>
 * contains the basic functionality of a Fermat Addon.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractAddon implements Addon, Service {

    private final Map<AddonVersionReference, Field> addonNeededReferences;

    private Field osContextField;
    private boolean referencesCollected;

    private final AddonVersionReference addonVersionReference;

    protected ServiceStatus serviceStatus;

    public AbstractAddon(final AddonVersionReference addonVersionReference) {

        this.addonVersionReference = addonVersionReference;

        this.addonNeededReferences = new ConcurrentHashMap<>();
        this.referencesCollected = false;
        this.serviceStatus = ServiceStatus.CREATED;
    }

    public final AddonVersionReference getAddonVersionReference() {
        return addonVersionReference;
    }

    public final boolean isDealsWithOsContext() {
        return osContextField != null;
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

    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    public List<DevelopersUtilReference> getAvailableDeveloperUtils() {
        return new ArrayList<>();
    }

    public FeatureForDevelopers getFeatureForDevelopers(final DevelopersUtilReference developersUtilReference) throws CantGetFeatureForDevelopersException {
        return null;
    }

    public final List<AddonVersionReference> getNeededAddons() throws CantListNeededReferencesException {

        try {

            if (!this.referencesCollected)
                collectReferences();

        } catch (CantCollectReferencesException e) {

            throw new CantListNeededReferencesException(
                    e,
                    this.getAddonVersionReference().toString(),
                    "There was problems trying to detect the references."
            );
        }

        List<AddonVersionReference> references = new ArrayList<>();

        for (final Map.Entry<AddonVersionReference, Field> neededReference : addonNeededReferences.entrySet())
            references.add(neededReference.getKey());

        return references;
    }

    private void collectReferences() throws CantCollectReferencesException {

        try {

            for (final Field f : this.getClass().getDeclaredFields()) {

                for (final Annotation a : f.getDeclaredAnnotations()) {
                    if (a instanceof NeededAddonReference) {
                        NeededAddonReference addonReference = (NeededAddonReference) a;

                        AddonVersionReference avr = new AddonVersionReference(
                                addonReference.platform(),
                                addonReference.layer(),
                                addonReference.addon(),
                                addonReference.developer(),
                                new Version(addonReference.version())
                        );

                        this.addonNeededReferences.put(avr, f);
                    }

                    if (a instanceof NeededOsContext)
                        this.osContextField = f;

                }
            }

            this.referencesCollected = true;

        } catch (final Exception e) {

            throw new CantCollectReferencesException(
                    e,
                    this.getAddonVersionReference().toString(),
                    "Error listing needed references for the addon."
            );
        }
    }

    public final void assignAddonReference(final AddonVersionReference avr,
                                           final FermatManager fermatManager) throws CantAssignReferenceException,
            IncompatibleReferenceException {

        try {

            final Field field = this.addonNeededReferences.get(avr);

            if (field == null) {
                throw new CantAssignReferenceException(
                        new StringBuilder().append("Addon receiving: ").append(this.addonVersionReference).append(" ---- Given addon: ").append(avr.toString()).toString(),
                        "The addon doesn't need the given reference."
                );
            }

            if (fermatManager == null) {
                throw new CantAssignReferenceException(
                        new StringBuilder().append("Addon receiving: ").append(this.addonVersionReference).append(" ---- Given addon is null. ").append(avr.toString()).toString(),
                        "Please check the given addon."
                );
            }

            final Class<?> refManager = field.getType();

            if (refManager.isAssignableFrom(fermatManager.getClass())) {
                field.setAccessible(true);
                field.set(this, refManager.cast(fermatManager));

                this.addonNeededReferences.remove(avr);

            } else {
                throw new IncompatibleReferenceException(
                        new StringBuilder()
                                .append("Working addon: ")
                                .append(this.getAddonVersionReference().toString3())
                                .append(" ---- classExpected: ").append(refManager.getName())
                                .append(" --- classReceived: ").append(fermatManager.getClass().getName())
                                .toString(),
                        ""
                );
            }

        } catch (final IllegalAccessException e) {

            throw new CantAssignReferenceException(
                    e,
                    new StringBuilder().append("Working addon: ").append(this.getAddonVersionReference().toString3()).append(" +++++ Reference to assign: ").append(avr.toString()).toString(),
                    "Error assigning references for the addon."
            );
        }
    }

    public final void assignOsContext(final Object object) throws CantAssignOsContextException,
            IncompatibleOsContextException {

        try {

            final Class<?> osContextClass = this.osContextField.getType();

            if (osContextClass.isAssignableFrom(object.getClass())) {
                this.osContextField.setAccessible(true);
                this.osContextField.set(this, osContextClass.cast(object));

                this.osContextField = null;

            } else {
                throw new IncompatibleOsContextException(
                        new StringBuilder().append("Working addon: ").append(this.getAddonVersionReference().toString3()).append(" ---- classExpected: ").append(osContextClass.getName()).append(" --- classReceived: ").append(object.getClass().getName()).toString(),
                        ""
                );
            }

        } catch (final IllegalAccessException e) {

            throw new CantAssignOsContextException(
                    e,
                    new StringBuilder().append("Working addon: ").append(this.getAddonVersionReference().toString3()).append(" +++++ Os Context to assign: ").append(object.toString()).toString(),
                    "Error assigning references for the addon."
            );
        }
    }

}
