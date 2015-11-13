package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantAssignReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetPluginIdException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantListNeededReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantPausePluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantResumePluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAddonException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginIdsManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStopPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CyclicalRelationshipFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.IncompatibleReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.UnexpectedServiceStatusException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;

import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_core.FermatPluginManager</code>
 * centralizes all service actions of the plugins in fermat.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public final class FermatPluginManager {

    private final FermatSystemContext systemContext;
    private final FermatAddonManager  addonManager ;

    private       FermatPluginIdsManager  pluginIdsManager;

    public FermatPluginManager(final FermatSystemContext systemContext,
                               final FermatAddonManager  addonManager ) {

        this.addonManager  = addonManager ;
        this.systemContext = systemContext;
    }

    private FermatPluginIdsManager getPluginIdsManager() throws CantStartPluginIdsManagerException {

        if (this.pluginIdsManager != null) {

            return this.pluginIdsManager;

        } else {
            try {

                final AddonVersionReference platformFileSystemReference = new AddonVersionReference(
                        Platforms .OPERATIVE_SYSTEM_API,
                        Layers    .SYSTEM              ,
                        Addons    .PLATFORM_FILE_SYSTEM,
                        Developers.BITDUBAI            ,
                        new Version()
                );

                final PlatformFileSystem platformFileSystem = (PlatformFileSystem) addonManager.startAddonAndReferences(platformFileSystemReference);

                this.pluginIdsManager = new FermatPluginIdsManager(platformFileSystem);

                return  this.pluginIdsManager;

            } catch (final CantStartAddonException  |
                           VersionNotFoundException e) {

                throw new CantStartPluginIdsManagerException(e, "Problem trying to get a platform file system addon.", "Platform not initialized?");
            }
        }
    }

    public final AbstractPlugin startPluginAndReferences(final PluginVersionReference pluginVersionReference) throws CantStartPluginException ,
                                                                                                                     VersionNotFoundException {

        try {

            final FermatPluginIdsManager pluginIdsManager = getPluginIdsManager();

            final AbstractPlugin abstractPlugin = systemContext.getPluginVersion(pluginVersionReference);

            if (abstractPlugin.isStarted())
                return abstractPlugin;

            final List<AddonVersionReference> neededAddons = abstractPlugin.getNeededAddons();

            for (final AddonVersionReference avr : neededAddons) {
                AbstractAddon reference = addonManager.startAddonAndReferences(avr);
                abstractPlugin.assignAddonReference(reference);
            }

            final List<PluginVersionReference> neededPlugins = abstractPlugin.getNeededPlugins();

            for (final PluginVersionReference pvr : neededPlugins) {

                AbstractPlugin reference = systemContext.getPluginVersion(pvr);

                compareReferences(pluginVersionReference, pvr, reference.getNeededPlugins());

                startPluginAndReferences(pvr);

                abstractPlugin.assignPluginReference(reference);
            }

            abstractPlugin.setId(pluginIdsManager.getPluginId(pluginVersionReference));

            startPlugin(abstractPlugin);

            return abstractPlugin;
        } catch (CantListNeededReferencesException e) {

            throw new CantStartPluginException(e, pluginVersionReference.toString3(), "Error listing references for the plugin.");
        } catch(CantAssignReferenceException   |
                IncompatibleReferenceException |
                CantStartAddonException        e) {

            throw new CantStartPluginException(e, pluginVersionReference.toString3(), "Error assigning references for the plugin.");
        } catch(final CantStartPluginIdsManagerException e) {

            throw new CantStartPluginException(e, pluginVersionReference.toString3(), "Error trying to get the pluginIdsManager.");
        } catch(final CantGetPluginIdException e) {

            throw new CantStartPluginException(e, pluginVersionReference.toString3(), "Error trying to set the plugin id.");
        } catch (final CyclicalRelationshipFoundException e) {

            throw new CantStartPluginException(e, pluginVersionReference.toString3(), "Cyclical References found for the plugin.");
        }
    }

    public final void startPlugin(final PluginVersionReference pluginVersionReference) throws CantStartPluginException ,
                                                                                              VersionNotFoundException {

        final AbstractPlugin abstractPlugin = systemContext.getPluginVersion(pluginVersionReference);

        startPlugin(abstractPlugin);

    }

    public final void startPlugin(final AbstractPlugin abstractPlugin) throws CantStartPluginException {

        if (abstractPlugin.isStarted())
            return;

        try {
            abstractPlugin.start();
        } catch (com.bitdubai.fermat_api.CantStartPluginException e) {

            throw new CantStartPluginException(
                    e,
                    abstractPlugin.getPluginVersionReference().toString3(),
                    "There was a captured problem during the plugin start."
            );
        } catch (Exception e) {

            throw new CantStartPluginException(
                    e,
                    abstractPlugin.getPluginVersionReference().toString(),
                    "Unhandled exception trying to start the plugin."
            );
        }

    }

    public final void stopPlugin(final PluginVersionReference pluginVersionReference) throws CantStopPluginException          ,
                                                                                             VersionNotFoundException         ,
                                                                                             UnexpectedServiceStatusException {

        AbstractPlugin abstractPlugin = systemContext.getPluginVersion(pluginVersionReference);

        stopPlugin(abstractPlugin);

    }

    public final void stopPlugin(final AbstractPlugin abstractPlugin) throws CantStopPluginException          ,
                                                                             UnexpectedServiceStatusException {

        if (!abstractPlugin.isStarted()) {
            throw new UnexpectedServiceStatusException(
                    "Service Status: "+abstractPlugin.getStatus()+" || "+abstractPlugin.getPluginVersionReference().toString(),
                    "The plugin cannot be stopped because is not started."
            );
        }

        try {

            abstractPlugin.stop();

        } catch (Exception e) {
            throw new CantStopPluginException(e, abstractPlugin.toString(), "Unhandled exception trying to stop the plugin.");
        }
    }

    public final void pausePlugin(final PluginVersionReference pluginVersionReference) throws CantPausePluginException         ,
                                                                                              VersionNotFoundException         ,
                                                                                              UnexpectedServiceStatusException {

        AbstractPlugin abstractPlugin = systemContext.getPluginVersion(pluginVersionReference);

        pausePlugin(abstractPlugin);

    }

    public final void pausePlugin(final AbstractPlugin abstractPlugin) throws CantPausePluginException         ,
                                                                              UnexpectedServiceStatusException {

        if (!abstractPlugin.isStarted()) {
            throw new UnexpectedServiceStatusException(
                    "Service Status: "+abstractPlugin.getStatus()+" || "+abstractPlugin.getPluginVersionReference().toString(),
                    "The plugin cannot be paused because is not started."
            );
        }

        try {

            abstractPlugin.pause();

        } catch (Exception e) {
            throw new CantPausePluginException(e, abstractPlugin.toString(), "Unhandled exception trying to pause the plugin.");
        }
    }

    public final void resumePlugin(final PluginVersionReference pluginVersionReference) throws CantResumePluginException        ,
                                                                                               VersionNotFoundException         ,
                                                                                               UnexpectedServiceStatusException {

        AbstractPlugin abstractPlugin = systemContext.getPluginVersion(pluginVersionReference);

        resumePlugin(abstractPlugin);

    }

    public final void resumePlugin(final AbstractPlugin abstractPlugin) throws CantResumePluginException         ,
            UnexpectedServiceStatusException {

        if (!abstractPlugin.isPaused()) {
            throw new UnexpectedServiceStatusException(
                    "Service Status: "+abstractPlugin.getStatus()+" || "+abstractPlugin.getPluginVersionReference().toString(),
                    "The plugin cannot be resumed because is not paused."
            );
        }

        try {

            abstractPlugin.pause();

        } catch (Exception e) {
            throw new CantResumePluginException(e, abstractPlugin.toString(), "Unhandled exception trying to resume the plugin.");
        }
    }

    /**
     * Throw the method <code>compareReferences</code> you can check if there is a cyclical relationship between a plugin version and its references.
     *
     * @param referenceAnalyzing       reference that we're watching.
     * @param subReferenceAnalyzed     reference of the reference that we're watching.
     * @param subReferenceReferences   sub-references of that reference.
     *
     * @return boolean indicating if its all ok, only false is shown. if there is a cyclical relationship found is thrown an exception.
     *
     * @throws CyclicalRelationshipFoundException if exists a cyclical redundancy.
     */
    private boolean compareReferences(final PluginVersionReference       referenceAnalyzing    ,
                                      final PluginVersionReference       subReferenceAnalyzed  ,
                                      final List<PluginVersionReference> subReferenceReferences) throws CyclicalRelationshipFoundException {


        for (final PluginVersionReference ref2 : subReferenceReferences) {
            if (referenceAnalyzing.equals(ref2))
                throw new CyclicalRelationshipFoundException(
                        "Comparing: " + referenceAnalyzing.toString3() + "\n with: " + subReferenceAnalyzed.toString3(),
                        "Cyclical relationship found."
                );
        }

        return false;
    }
}
