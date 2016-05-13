package com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededIndirectPluginReferences;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededLayerReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantAssignReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantCollectReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantListNeededReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.IncompatibleReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.LayerReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * The abstract class <code>AbstractPlugin</code>
 * contains the basic functionality of a Fermat Plugin.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 * Modified by Matias Furszyfer, todo: tenemos que sacar esos concurrentMaps leon, no sirve que esten así.
 */
public abstract class AbstractPlugin implements FermatManager, Plugin, Service {


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    protected ErrorManager errorManager;


    private final ConcurrentHashMap<AddonVersionReference , Field> addonNeededReferences         ;
    private final ConcurrentHashMap<PluginVersionReference, Field> pluginNeededReferences        ;
    private final ConcurrentHashMap<LayerReference        , Field> layerNeededReferences         ;
    private final CopyOnWriteArrayList<PluginVersionReference>     indirectNeededPluginReferences;

    private boolean referencesCollected;

    protected final PluginVersionReference pluginVersionReference;

    protected volatile ServiceStatus serviceStatus;

    protected          UUID          pluginId     ;

    public AbstractPlugin(final PluginVersionReference pluginVersionReference) {

        this.pluginVersionReference = pluginVersionReference;

        this.addonNeededReferences          = new ConcurrentHashMap   <>();
        this.pluginNeededReferences         = new ConcurrentHashMap   <>();
        this.layerNeededReferences          = new ConcurrentHashMap   <>();
        this.indirectNeededPluginReferences = new CopyOnWriteArrayList<>();

        this.referencesCollected    = false;
        this.serviceStatus          = ServiceStatus.CREATED;
    }



    public final PluginVersionReference getPluginVersionReference() {
        return pluginVersionReference;
    }

    @Override
    public ServiceStatus getStatus() {
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

    public synchronized final void startPlugin() throws CantStartPluginException {
        this.start();
        this.serviceStatus = ServiceStatus.STARTED;

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

    public final List<PluginVersionReference> getNeededIndirectPlugins() throws CantListNeededReferencesException {

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

        return this.indirectNeededPluginReferences;
    }

    private void collectReferences() throws CantCollectReferencesException {

        collectReferences(this.getClass());
    }

    private void collectReferences(Class toCollectClass) throws CantCollectReferencesException {

        // collect superclass references
        if (toCollectClass.getSuperclass() != null)
            collectReferences(toCollectClass.getSuperclass());

        try {

            for (final Annotation a : toCollectClass.getDeclaredAnnotations()) {
                if (a instanceof NeededIndirectPluginReferences) {

                    NeededIndirectPluginReferences neededIndirectPluginReferences = (NeededIndirectPluginReferences) a;

                    for (NeededPluginReference npr : neededIndirectPluginReferences.indirectReferences()) {
                        this.indirectNeededPluginReferences.add(
                                new PluginVersionReference(
                                        npr.platform(),
                                        npr.layer(),
                                        npr.plugin(),
                                        npr.developer(),
                                        new Version(npr.version())
                                )
                        );
                    }

                }
            }

            for (final Field f : toCollectClass.getDeclaredFields()) {

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

                    if (a instanceof NeededLayerReference) {
                        NeededLayerReference layerReference = (NeededLayerReference) a;

                        LayerReference lr = new LayerReference(
                                layerReference.platform(),
                                layerReference.layer()
                        );

                        this.layerNeededReferences.put(lr, f);
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

            if (fermatManager == null) {
                throw new CantAssignReferenceException(
                        "Plugin receiving: " + this.pluginVersionReference + " ---- Given addon is null. "+ avr.toString(),
                        "Please check the given addon."
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

            if (fermatManager == null) {
                throw new CantAssignReferenceException(
                        "Plugin receiving: " + this.pluginVersionReference + " ---- Given plugin is null. "+ pluginVersion.toString3(),
                        "Please check the given plugin."
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

    public final void assignLayerReference(final LayerReference layerReference,
                                           final FermatManager  fermatManager) throws CantAssignReferenceException   ,
            IncompatibleReferenceException {

        try {

            final Field field = this.layerNeededReferences.get(layerReference);

            if (field == null) {
                throw new CantAssignReferenceException(
                        "Plugin receiving: " + this.pluginVersionReference + " ---- Given layer: " + layerReference.toString3(),
                        "The plugin doesn't need the given reference."
                );
            }

            if (fermatManager == null) {
                throw new CantAssignReferenceException(
                        "Plugin receiving: " + this.pluginVersionReference + " ---- Given layer is null. "+ layerReference.toString3(),
                        "Please check the given layer."
                );
            }

            final Class<?> refManager = field.getType();

            if(refManager.isAssignableFrom(fermatManager.getClass())) {
                field.setAccessible(true);
                field.set(this, refManager.cast(fermatManager));

                this.layerNeededReferences.remove(layerReference);

            } else {
                throw new IncompatibleReferenceException(
                        "Working plugin: "+this.getPluginVersionReference().toString3()+
                                " ------------ classExpected: "+refManager.getName() + " --- classReceived: " + fermatManager.getClass().getName(),
                        "Field is not assignable by the given reference (bad definition, different type expected). Check the expected layer and the defined type."
                );
            }

        } catch (final IllegalAccessException e) {

            throw new CantAssignReferenceException(
                    e,
                    "Working plugin: "+this.getPluginVersionReference().toString3()+ " +++++ Reference to assign: "+ layerReference.toString3(),
                    "Error assigning references for the plugin."
            );
        }
    }

    public ErrorManager getErrorManager(){
        return errorManager;
    }

    public void reportError(UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception){
        PluginInfo pluginInfo = getClass().getAnnotation(PluginInfo.class);
        if(pluginInfo!=null) {
            String[] mailTo = new String[]{pluginInfo.maintainerMail()};
            errorManager.reportUnexpectedPluginException(pluginInfo.plugin(),pluginVersionReference.getPlatform(),unexpectedPluginExceptionSeverity,exception,mailTo);
        }else {
            System.err.println("The plugin is not implementing the annotation class,Error in Plugin: "+getClass().getName());
        }
    }


}
