package com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.OperativeSystems;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantAssignReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantCollectReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantListNeededReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.IncompatibleReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FeatureForDevelopers;
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
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractAddon implements Addon, Service {

    private final Map<AddonVersionReference, Field> addonNeededReferences = new ConcurrentHashMap<>();

    private boolean referencesCollected;

    private final AddonVersionReference addonVersionReference;
    private final boolean               dealsWithOsContext   ;
    private final OperativeSystems      operativeSystem      ;
    private       Object                osContext            ;

    protected     ServiceStatus         serviceStatus        ;

    public AbstractAddon(final AddonVersionReference addonVersionReference) {

        this.addonVersionReference = addonVersionReference;
        this.dealsWithOsContext    = false;
        this.operativeSystem       = OperativeSystems.INDIFFERENT;
        this.instantiateAddon();
    }

    public AbstractAddon(final AddonVersionReference addonVersionReference,
                         final OperativeSystems      operativeSystem      ) {

        this.addonVersionReference = addonVersionReference;
        this.operativeSystem       = operativeSystem;
        this.dealsWithOsContext    = !operativeSystem.equals(OperativeSystems.INDIFFERENT);
        this.instantiateAddon();
    }

    private void instantiateAddon() {

        this.referencesCollected = false;
        this.serviceStatus = ServiceStatus.CREATED;
    }

    public final AddonVersionReference getAddonVersionReference() {
        return addonVersionReference;
    }

    public final boolean isDealsWithOsContext() {
        return dealsWithOsContext;
    }

    public final OperativeSystems getOperativeSystem() {
        return operativeSystem;
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

    protected final Object getOsContext() {
        return osContext;
    }

    public final void setOsContext(Object osContext) {
        this.osContext = osContext;
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
    };

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
                                addonReference.operativeSystem(),
                                addonReference.platform(),
                                addonReference.layer(),
                                addonReference.addon(),
                                addonReference.developer(),
                                new Version(addonReference.version())
                        );

                        this.addonNeededReferences.put(avr, f);
                    }
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

    public final void assignAddonReference(final AbstractAddon abstractAddon) throws CantAssignReferenceException   ,
                                                                                     IncompatibleReferenceException {

        final AddonVersionReference avr = abstractAddon.getAddonVersionReference();

        try {

            final Field field = this.addonNeededReferences.get(avr);

            if (field == null) {
                throw new CantAssignReferenceException(
                        "Addon receiving: " + this.addonVersionReference + " ---- Given addon: " + avr.toString(),
                        "The addon doesn't need the given reference."
                );
            }

            final Class<?> refManager = field.getType();

            if(refManager.isAssignableFrom(abstractAddon.getClass())) {
                field.setAccessible(true);
                field.set(this, refManager.cast(abstractAddon));

                System.out.println("Processing Addon: " + this.addonVersionReference.toString2()+
                                    " - >>> Assigned reference: "+avr.toString2());
            } else {
                throw new IncompatibleReferenceException(
                        "classExpected: "+refManager.getName() + " --- classReceived: " + abstractAddon.getClass().getName(),
                        ""
                );
            }

        } catch (final IllegalAccessException e) {

            throw new CantAssignReferenceException(
                    e,
                    "Working addon: "+this.getAddonVersionReference().toString()+ " +++++ Reference to assign: "+ avr.toString(),
                    "Error assigning references for the addon."
            );
        }
    }

}
