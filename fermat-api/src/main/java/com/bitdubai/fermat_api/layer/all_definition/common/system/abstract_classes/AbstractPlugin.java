package com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantAssignReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantCollectReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantListNeededReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.IncompatibleReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The abstract class <code>AbstractPlugin</code>
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

    protected     UUID                   pluginId              ;

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

    @Override
    public FermatManager getManager() {
        return null;
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

    public final void setId(final UUID pluginId) {
        this.pluginId = pluginId;
    }


    public final List<AddonVersionReference> getNeededAddons() throws CantListNeededReferencesException {

        try {

            if (!this.referencesCollected)
                collectReferences();

        } catch(CantCollectReferencesException e) {

            throw new CantListNeededReferencesException(
                    e,
                    this.getPluginVersionReference().toString3(),
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
                    this.getPluginVersionReference().toString3(),
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
                    this.getPluginVersionReference().toString3(),
                    "Error listing needed references for the plugin."
            );
        }
    }

    public final void assignAddonReference(final AddonVersionReference avr          ,
                                           final FermatManager         fermatManager) throws CantAssignReferenceException   ,
            IncompatibleReferenceException {

        try {

            final Field field = this.addonNeededReferences.get(avr);

            if (field == null) {
                throw new CantAssignReferenceException(
                        "Plugin receiving: " + this.getPluginVersionReference() + " ---- Given addon: " + avr.toString(),
                        "The Plugin doesn't need the given reference."
                );
            }

            final Class<?> refManager = field.getType();

            if(refManager.isAssignableFrom(fermatManager.getClass())) {
                field.setAccessible(true);
                field.set(this, refManager.cast(fermatManager));

                this.addonNeededReferences.remove(avr);

            } else {
                throw new IncompatibleReferenceException(
                        "Working Plugin: "+this.getPluginVersionReference().toString3()+
                                " ---- classExpected: "+refManager.getName() + " --- classReceived: " + fermatManager.getClass().getName(),
                        ""
                );
            }

        } catch (final IllegalAccessException e) {

            throw new CantAssignReferenceException(
                    e,
                    "Working Plugin: "+this.getPluginVersionReference().toString3()+ " +++++ Reference to assign: "+ avr.toString(),
                    "Error assigning references for the Plugin."
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
                        "Plugin receiving: " + this.pluginVersionReference + " ---- Given plugin: " + pvr.toString3(),
                        "The plugin doesn't need the given reference."
                );
            }
            final Class<?> refManager = field.getType();

            if(refManager.isAssignableFrom(abstractPlugin.getClass())) {
                field.setAccessible(true);
                field.set(this, refManager.cast(abstractPlugin));

                this.pluginNeededReferences.remove(pvr);

            } else {
                throw new IncompatibleReferenceException(
                        "Working plugin: "+this.getPluginVersionReference().toString3()+
                        " ------------ classExpected: "+refManager.getName() + " --- classReceived: " + abstractPlugin.getClass().getName(),
                        "Field is not assignable by the given reference (bad definition, different type expected). Check the expected plugin and the defined type."
                );
            }

        } catch (final IllegalAccessException e) {

            throw new CantAssignReferenceException(
                    e,
                    "Working plugin: "+this.getPluginVersionReference().toString3()+ " +++++ Reference to assign: "+ pvr.toString3(),
                    "Error assigning references for the plugin."
            );
        }
    }

    public final void assignPluginReference(final PluginVersionReference pluginVersion,
                                            final FermatManager          fermatManager) throws CantAssignReferenceException   ,
                                                                                               IncompatibleReferenceException {

        try {

            final Field field = this.pluginNeededReferences.get(pluginVersion);

            if (field == null) {
                throw new CantAssignReferenceException(
                        "Plugin receiving: " + this.pluginVersionReference + " ---- Given plugin: " + pluginVersion.toString3(),
                        "The plugin doesn't need the given reference."
                );
            }
            final Class<?> refManager = field.getType();

            if(refManager.isAssignableFrom(fermatManager.getClass())) {
                field.setAccessible(true);
                field.set(this, refManager.cast(fermatManager));

                this.pluginNeededReferences.remove(pluginVersion);

            } else {
                throw new IncompatibleReferenceException(
                        "Working plugin: "+this.getPluginVersionReference().toString3()+
                                " ------------ classExpected: "+refManager.getName() + " --- classReceived: " + fermatManager.getClass().getName(),
                        "Field is not assignable by the given reference (bad definition, different type expected). Check the expected plugin and the defined type."
                );
            }

        } catch (final IllegalAccessException e) {

            throw new CantAssignReferenceException(
                    e,
                    "Working plugin: "+this.getPluginVersionReference().toString3()+ " +++++ Reference to assign: "+ pluginVersion.toString3(),
                    "Error assigning references for the plugin."
            );
        }
    }
}
