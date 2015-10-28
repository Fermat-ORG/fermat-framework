package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.enums.OperativeSystems;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.MissingReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractAddon</code>
 * contains the basic functionality of a Fermat Addon.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractAddon implements Addon, Service {

    private final Map<AddonVersionReference, AbstractAddon> addons;

    private final AddonVersionReference addonVersionReference;
    private final boolean               dealsWithOsContext   ;
    private final OperativeSystems      operativeSystem      ;
    private       Object                osContext            ;

    protected     ServiceStatus         serviceStatus        ;

    public AbstractAddon(final AddonVersionReference addonVersionReference) {

        this.addons  = new ConcurrentHashMap<>();
        this.addonVersionReference = addonVersionReference;
        this.dealsWithOsContext    = false;
        this.operativeSystem       = null;
    }

    public AbstractAddon(final AddonVersionReference addonVersionReference,
                         final OperativeSystems      operativeSystem      ) {

        this.addons  = new ConcurrentHashMap<>();
        this.addonVersionReference = addonVersionReference;
        this.dealsWithOsContext    = false;
        this.operativeSystem       = operativeSystem;
    }

    public final void addAddonReference(final AddonVersionReference addonReference,
                                        final AbstractAddon         addon         ) {

        addons.put(addonReference, addon);
    }


    protected final AbstractAddon getAddonReference(final AddonVersionReference addonReference) {

        return addons.get(addonReference);
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

    public List<AddonVersionReference> getNeededAddonReferences() {
        return new ArrayList<>();
    }

    public List<DevelopersUtilReference> getAvailableDeveloperUtils() {
        return new ArrayList<>();
    };

    public FeatureForDevelopers getFeatureForDevelopers(final DevelopersUtilReference developersUtilReference) throws CantGetFeatureForDevelopersException {
        return null;
    }

    protected void validateAndAssignReferences() throws MissingReferencesException {

    };

}
