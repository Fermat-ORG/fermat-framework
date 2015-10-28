package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.enums.OperativeSystems;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantAssignReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantListNeededReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatAddonManager;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractAddon</code>
 * contains the basic functionality of a Fermat Addon.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractAddon implements Addon, Service {

    protected final ConcurrentHashMap<AddonVersionReference, Field> addonNeededReferences = new ConcurrentHashMap<>();

    private final AddonVersionReference addonVersionReference;
    private final boolean               dealsWithOsContext   ;
    private final OperativeSystems      operativeSystem      ;
    private       Object                osContext            ;

    protected     ServiceStatus         serviceStatus        ;

    public AbstractAddon(final AddonVersionReference addonVersionReference) {

        this.addonVersionReference = addonVersionReference;
        this.dealsWithOsContext    = false;
        this.operativeSystem       = OperativeSystems.INDIFFERENT;
    }

    public AbstractAddon(final AddonVersionReference addonVersionReference,
                         final OperativeSystems      operativeSystem      ) {

        this.addonVersionReference = addonVersionReference;
        this.dealsWithOsContext    = false;
        this.operativeSystem       = operativeSystem;
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

    public final ConcurrentHashMap<AddonVersionReference, Class<? extends FermatAddonManager>> getNeededAddons() throws CantListNeededReferencesException {

        try {

            final ConcurrentHashMap<AddonVersionReference, Class<? extends FermatAddonManager>> neededAddons = new ConcurrentHashMap<>();

            final Class<? extends AbstractAddon> cl = this.getClass();

            for (final Field f : cl.getDeclaredFields()) {

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

                        neededAddons.put(avr, addonReference.referenceManagerClass());

                        this.addonNeededReferences.put(avr, f);
                    }
                }
            }
            return neededAddons;

        } catch (final Exception e) {

            throw new CantListNeededReferencesException(
                    e,
                    this.getAddonVersionReference().toString(),
                    "Error listing needed references for the addon."
            );
        }
    }

    public final void assignAddonReference(final AddonVersionReference          avr          ,
                                           final Class<? extends FermatAddonManager> referenceMger,
                                           final AbstractAddon                  abstractAddon) throws CantAssignReferenceException {

        try {

            Field field = this.addonNeededReferences.get(avr);
            field.setAccessible(true);
            field.set(this, referenceMger.cast(abstractAddon));

        } catch (IllegalAccessException e) {

            throw new CantAssignReferenceException(
                    e,
                    "Working addon: "+this.getAddonVersionReference().toString()+ " +++++ Reference to assign: "+ avr.toString(),
                    "Error assigning references for the addon."
            );
        }
    }

}
