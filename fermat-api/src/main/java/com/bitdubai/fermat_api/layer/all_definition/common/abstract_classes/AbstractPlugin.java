package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantAssignReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantCollectReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantListNeededReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.IncompatibleReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPlugin</code>
 * contains the basic functionality of a Fermat Plugin.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractPlugin implements Plugin, Service {

    private final ConcurrentHashMap<AddonVersionReference, Field> addonNeededReferences;
    private final ConcurrentHashMap<PluginVersionReference, Field> pluginNeededReferences;

    private boolean referencesCollected;

    private final PluginVersionReference pluginVersionReference;
    protected     ServiceStatus          serviceStatus         ;

    public AbstractPlugin(final PluginVersionReference pluginVersionReference) {

        this.pluginVersionReference = pluginVersionReference;

        this.addonNeededReferences  = new ConcurrentHashMap<>();
        this.pluginNeededReferences = new ConcurrentHashMap<>();
        this.referencesCollected    = false;
        this.serviceStatus          = ServiceStatus.CREATED;
    }

    public final PluginVersionReference getPluginVersionReference() {
        return pluginVersionReference;
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

    public List<DevelopersUtilReference> getAvailableDeveloperUtils() {
        return new ArrayList<>();
    }

    public FeatureForDevelopers getFeatureForDevelopers(final DevelopersUtilReference developersUtilReference) throws CantGetFeatureForDevelopersException {
        return null;
    }

    public abstract void setId(final UUID pluginId);


    public final List<AddonVersionReference> getNeededAddons() throws CantListNeededReferencesException {

        try {

            if (!this.referencesCollected)
                collectReferences();

        } catch(CantCollectReferencesException e) {

            throw new CantListNeededReferencesException(
                    e,
                    this.getPluginVersionReference().toString(),
                    "There was problems trying to detect the references."
            );
        }

        List<AddonVersionReference> references = new ArrayList<>();

        for (final Map.Entry<AddonVersionReference, Field> neededReference : addonNeededReferences.entrySet())
            references.add(neededReference.getKey());

        return references;
    }

    public final List<PluginVersionReference> getNeededPlugins() throws CantListNeededReferencesException {

        try {

            if (!this.referencesCollected)
                collectReferences();

        } catch(CantCollectReferencesException e) {

            throw new CantListNeededReferencesException(
                    e,
                    this.getPluginVersionReference().toString(),
                    "There was problems trying to detect the references."
            );
        }

        List<PluginVersionReference> references = new ArrayList<>();

        for (final Map.Entry<PluginVersionReference, Field> neededReference : pluginNeededReferences.entrySet())
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

                    if (a instanceof NeededPluginReference) {
                        NeededPluginReference pluginReference = (NeededPluginReference) a;

                        PluginVersionReference pvr = new PluginVersionReference(
                                pluginReference.operativeSystem(),
                                pluginReference.platform(),
                                pluginReference.layer(),
                                pluginReference.plugin(),
                                pluginReference.developer(),
                                new Version(pluginReference.version())
                        );

                        this.pluginNeededReferences.put(pvr, f);
                    }

                }
            }

            this.referencesCollected = true;

        } catch (final Exception e) {

            throw new CantCollectReferencesException(
                    e,
                    this.getPluginVersionReference().toString(),
                    "Error listing needed references for the plugin."
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
                        "Plugin receiving: " + this.pluginVersionReference + " ---- Given addon: " + avr.toString(),
                        "The plugin doesn't need the given reference."
                );
            }

            final Class<?> refManager = field.getDeclaringClass();

            if(refManager.isAssignableFrom(abstractAddon.getClass())) {
                field.setAccessible(true);
                field.set(this, refManager.cast(abstractAddon));
            } else {
                throw new IncompatibleReferenceException(
                        "classExpected: "+refManager.getName() + " --- classReceived: " + abstractAddon.getClass().getName(),
                        ""
                );
            }

        } catch (final IllegalAccessException e) {

            throw new CantAssignReferenceException(
                    e,
                    "Working plugin: "+this.getPluginVersionReference().toString()+ " +++++ Reference to assign: "+ avr.toString(),
                    "Error assigning references for the plugin."
            );
        }
    }

    public final void assignPluginReference(final AbstractPlugin abstractPlugin) throws CantAssignReferenceException   ,
                                                                                        IncompatibleReferenceException {

        final PluginVersionReference pvr = abstractPlugin.getPluginVersionReference();

        try {

            final Field field = this.pluginNeededReferences.get(pvr);

            if (field == null) {
                throw new CantAssignReferenceException(
                        "Plugin receiving: " + this.pluginVersionReference + " ---- Given plugin: " + pvr.toString(),
                        "The plugin doesn't need the given reference."
                );
            }
            final Class<?> refManager = field.getDeclaringClass();

            if(refManager.isAssignableFrom(abstractPlugin.getClass())) {
                field.setAccessible(true);
                field.set(this, refManager.cast(abstractPlugin));
            } else {
                throw new IncompatibleReferenceException(
                        "classExpected: "+refManager.getName() + " --- classReceived: " + abstractPlugin.getClass().getName(),
                        ""
                );
            }

        } catch (final IllegalAccessException e) {

            throw new CantAssignReferenceException(
                    e,
                    "Working plugin: "+this.getPluginVersionReference().toString()+ " +++++ Reference to assign: "+ pvr.toString(),
                    "Error assigning references for the plugin."
            );
        }
    }
}
