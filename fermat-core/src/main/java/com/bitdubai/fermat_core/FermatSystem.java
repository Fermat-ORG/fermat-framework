package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetAddonException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetErrorManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetResourcesManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetRuntimeManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAddonException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAllRegisteredPlatformsException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartSystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ErrorManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ModuleManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ResourcesManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.RuntimeManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealWithDatabaseManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealsWithLogManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_api.layer.resources.ResourcesManager;
import com.bitdubai.fermat_bch_core.BCHPlatform;
//import com.bitdubai.fermat_cbp_core.CBPPlatform;
import com.bitdubai.fermat_cbp_core.CBPPlatform;
import com.bitdubai.fermat_ccp_core.CCPPlatform;
//import com.bitdubai.fermat_dap_core.DAPPlatform;
import com.bitdubai.fermat_dap_core.DAPPlatform;
import com.bitdubai.fermat_p2p_core.P2PPlatform;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_core.PIPPlatform;
import com.bitdubai.fermat_wpd_core.WPDPlatform;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_core.FermatSystem</code>
 * starts all the component of the platform and manage it.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public final class FermatSystem {

    private FermatSystemContext fermatSystemContext;
    private FermatAddonManager  fermatAddonManager ;
    private FermatPluginManager fermatPluginManager;

    /**
     * Here we start all the platforms of Fermat, one by one.
     * OSA Platform will be needed for the starting of fermat system class, this way we ensure that it will be a osa platform registered.
     *
     * @param osContext      operative system context instance.
     * @param osaPlatform    OSA Platform instance.
     *
     * @throws CantStartSystemException if something goes wrong.
     */
    public final void start(final Object           osContext  ,
                            final AbstractPlatform osaPlatform) throws CantStartSystemException {

        this.fermatSystemContext = new FermatSystemContext(osContext);
        this.fermatAddonManager  = new FermatAddonManager(fermatSystemContext);
        this.fermatPluginManager = new FermatPluginManager(fermatSystemContext, fermatAddonManager);

        try {

            this.registerOsaPlatform(osaPlatform);

        } catch (final CantRegisterPlatformException e) {

            throw new CantStartSystemException(e, "", "Error registering OSA Platform.");
        }

        try {

            fermatSystemContext.registerPlatform(new BCHPlatform());
            fermatSystemContext.registerPlatform(new CBPPlatform());
            fermatSystemContext.registerPlatform(new CCPPlatform());
            fermatSystemContext.registerPlatform(new DAPPlatform());
            fermatSystemContext.registerPlatform(new P2PPlatform());
            fermatSystemContext.registerPlatform(new PIPPlatform());
            fermatSystemContext.registerPlatform(new WPDPlatform());

        } catch(CantRegisterPlatformException e) {

            throw new CantStartSystemException(e, "", "There was a problem registering a Platform.");
        } catch(Exception e) {

            throw new CantStartSystemException(e, "", "Unhandled Exception.");
        }
    }

    /**
     * Through the method <code>registerOsaPlatform</code> we validate minimally that we're dealing with an osaPlatform.
     *
     * @param osaPlatform  instance of osa platform.
     *
     * @throws CantRegisterPlatformException if something goes wrong.
     */
    private void registerOsaPlatform(final AbstractPlatform osaPlatform) throws CantRegisterPlatformException {

        if (osaPlatform == null)
            throw new CantRegisterPlatformException("abstractPlatform=null", "You have pass through parameter an OSA Platform instance.");

        final PlatformReference pr = osaPlatform.getPlatformReference();

        if (pr.getPlatform() != null && pr.getPlatform().equals(Platforms.OPERATIVE_SYSTEM_API))
            fermatSystemContext.registerPlatform(osaPlatform);
        else
            throw new CantRegisterPlatformException(osaPlatform.getPlatformReference().toString(), "Is not referenced like an OSA specific Platform.");

    }

    /**
     * Through the method <code>getResourcesManager</code> the graphic interface can access to the resources manager in fermat.
     *
     * @param pluginVersionReference plugin version reference data.
     *
     * @return an instance of the requested resources manager.
     *
     * @throws CantGetResourcesManagerException   if something goes wrong.
     * @throws ResourcesManagerNotFoundException  if we can't find the requested resources manager.
     */
    public final ResourcesManager getResourcesManager(final PluginVersionReference pluginVersionReference) throws CantGetResourcesManagerException  ,
                                                                                                                  ResourcesManagerNotFoundException {

        try {

            final AbstractPlugin resourcesManager = fermatPluginManager.startPluginAndReferences(pluginVersionReference);

            if (resourcesManager instanceof ResourcesManager)
                return (ResourcesManager) resourcesManager;
            else
                throw new CantGetResourcesManagerException(pluginVersionReference.toString3(), "The plugin version requested not implements resources manager interface.");

        } catch(final VersionNotFoundException e) {

            throw new ResourcesManagerNotFoundException(e, pluginVersionReference.toString3(), "The resources manager cannot be found.");
        } catch (final Exception e) {

            throw new CantGetResourcesManagerException(e, pluginVersionReference.toString3(), "Unhandled error.");
        }
    }

    /**
     * Through the method <code>getModuleManager</code> the graphic interface can access to the modules of
     * its sub-apps and wallets.
     *
     * @param pluginVersionReference plugin version reference data.
     *
     * @return an instance of the requested module manager.
     *
     * @throws CantGetModuleManagerException   if something goes wrong.
     * @throws ModuleManagerNotFoundException  if we can't find the requested module manager.
     */
    public final ModuleManager getModuleManager(final PluginVersionReference pluginVersionReference) throws CantGetModuleManagerException  ,
                                                                                                            ModuleManagerNotFoundException {

        try {

            final AbstractPlugin moduleManager = fermatPluginManager.startPluginAndReferences(pluginVersionReference);

            if (moduleManager instanceof ModuleManager)
                return (ModuleManager) moduleManager;
            else
                throw new CantGetModuleManagerException(pluginVersionReference.toString3(), "The plugin version requested not implements module manager interface.");

        } catch(final VersionNotFoundException e) {

            throw new ModuleManagerNotFoundException(e, pluginVersionReference.toString3(), "The module manager cannot be found.");
        } catch(final CantGetModuleManagerException e) {

            throw e;
        } catch (final Exception e) {

            throw new CantGetModuleManagerException(e, pluginVersionReference.toString3(), "Unhandled error.");
        }
    }

    /**
     * Through the method <code>getRuntimeManager</code> the graphic interface can access to the runtime managers of fermat.
     *
     * @param pluginVersionReference plugin version reference data.
     *
     * @return an instance of the requested runtime manager.
     *
     * @throws CantGetRuntimeManagerException    if something goes wrong.
     * @throws RuntimeManagerNotFoundException   if we can't find the requested runtime manager.
     */
    public final RuntimeManager getRuntimeManager(final PluginVersionReference pluginVersionReference) throws RuntimeManagerNotFoundException,
                                                                                                            CantGetRuntimeManagerException {

        try {

            final AbstractPlugin runtimeManager = fermatPluginManager.startPluginAndReferences(pluginVersionReference);

            if (runtimeManager instanceof RuntimeManager)
                return (RuntimeManager) runtimeManager;
            else
                throw new CantGetRuntimeManagerException(pluginVersionReference.toString3(), "The plugin version requested not implements runtime manager interface.");

        } catch(final VersionNotFoundException e) {

            throw new RuntimeManagerNotFoundException(e, pluginVersionReference.toString3(), "The runtime manager cannot be found.");
        } catch(final CantGetRuntimeManagerException e) {

            throw e;
        } catch (final Exception e) {

            throw new CantGetRuntimeManagerException(e, pluginVersionReference.toString3(), "Unhandled error.");
        }
    }

    /**
     * Through the method <code>getErrorManager</code> the graphic interface can access to the error managers of fermat.
     *
     * @param addonVersionReference addon version reference data.
     *
     * @return an instance of the requested error manager.
     *
     * @throws CantGetErrorManagerException    if something goes wrong.
     * @throws ErrorManagerNotFoundException   if we can't find the requested error manager.
     */
    public final ErrorManager getErrorManager(final AddonVersionReference addonVersionReference) throws ErrorManagerNotFoundException,
                                                                                                        CantGetErrorManagerException {

        try {

            final AbstractAddon errorManager = fermatAddonManager.startAddonAndReferences(addonVersionReference);

            if (errorManager instanceof ErrorManager)
                return (ErrorManager) errorManager;
            else
                throw new CantGetModuleManagerException(addonVersionReference.toString3(), "The addon version requested not implements error manager interface.");


        } catch(CantStartAddonException e) {

            throw new CantGetErrorManagerException(e, addonVersionReference.toString3(), "The addon cannot be started.");
        } catch (Exception e) {

            throw new CantGetErrorManagerException(e, addonVersionReference.toString3(), "Unhandled error.");
        }
    }

    // TODO THINK ABOUT THIS.
    @Deprecated
    public final void startAllRegisteredPlatforms() throws CantStartAllRegisteredPlatformsException {

        final Map<PluginVersionReference, Plugin> dealsWithDatabaseManagersPlugins = new ConcurrentHashMap<>();
        final Map<PluginVersionReference, Plugin> dealsWithLogManagersPlugins = new ConcurrentHashMap<>();
        final Map<AddonVersionReference, Addon> dealsWithDatabaseManagersAddons = new ConcurrentHashMap<>();
        final Map<AddonVersionReference, Addon> dealsWithLogManagersAddons = new ConcurrentHashMap<>();

        final ConcurrentHashMap<AddonVersionReference, AbstractAddon> addonList = this.fermatSystemContext.listAddonVersions();

        final ConcurrentHashMap<PluginVersionReference, AbstractPlugin> pluginList = this.fermatSystemContext.listPluginVersions();


        for(final ConcurrentHashMap.Entry<AddonVersionReference, AbstractAddon> addon : addonList.entrySet()) {

            try {

                fermatAddonManager.startAddonAndReferences(addon.getValue());

                if (addon.getValue() instanceof DatabaseManagerForDevelopers)
                    dealsWithDatabaseManagersAddons.put(addon.getKey(), addon.getValue());

                if (addon.getValue() instanceof LogManagerForDevelopers)
                    dealsWithLogManagersAddons.put(addon.getKey(), addon.getValue());

            } catch (final CantStartAddonException e) {
                System.out.println(e.toString());
                // throw new CantStartAllRegisteredPlatformsException(e, "", "Error starting add-ons or plug-ins during the start of all platforms.");
            } catch (Exception e) {
                System.out.println(e.toString());
                //throw new CantStartAllRegisteredPlatformsException(e, "", "Unhandled Error.");
            }
        }

        for(ConcurrentHashMap.Entry<PluginVersionReference, AbstractPlugin> plugin : pluginList.entrySet()) {

            try {

                fermatPluginManager.startPluginAndReferences(plugin.getValue());

                if (plugin.getValue() instanceof DatabaseManagerForDevelopers)
                    dealsWithDatabaseManagersPlugins.put(plugin.getKey(), plugin.getValue());

                if (plugin.getValue() instanceof LogManagerForDevelopers)
                    dealsWithLogManagersPlugins.put(plugin.getKey(), plugin.getValue());

            } catch (final CantStartPluginException e) {
                System.out.println(e.toString());
                //throw new CantStartAllRegisteredPlatformsException(e, "", "Error starting plug-ins during the start of all platforms.");
            } catch (Exception e) {
                System.out.println(e.toString());
                //throw new CantStartAllRegisteredPlatformsException(e, "", "Unhandled Error.");
            }
        }

        try {
            AbstractPlugin developerModule = startAndGetPluginVersion(new PluginVersionReference(Platforms.PLUG_INS_PLATFORM, Layers.SUB_APP_MODULE, Plugins.DEVELOPER, Developers.BITDUBAI, new Version()));

            ((DealWithDatabaseManagers) developerModule).setDatabaseManagers(dealsWithDatabaseManagersPlugins, dealsWithDatabaseManagersAddons);
            ((DealsWithLogManagers) developerModule).setLogManagers(dealsWithLogManagersPlugins, dealsWithLogManagersAddons);
        } catch (Exception e) {
            System.out.println("************************* ERROR TRYING TO ASSIGN REFERENCES TO THE DEVELOPER SUB_APP_MODULE.");
            System.out.println(e.toString());
        }
    }

    // TODO TEMPORAL METHOD UNTIL ALL THE ADD-ONS COULD BE REQUESTED BY ITS OWN METHODS.
    @Deprecated
    public final AbstractAddon startAndGetAddon(final AddonVersionReference addonVersionReference) throws VersionNotFoundException,
                                                                                                          CantGetAddonException   {

        try {

            return fermatAddonManager.startAddonAndReferences(addonVersionReference);

        } catch(CantStartAddonException e) {

            throw new CantGetAddonException(e, addonVersionReference.toString3(), "The addon cannot be started.");
        } catch (Exception e) {

            throw new CantGetAddonException(e, addonVersionReference.toString3(), "Unhandled error.");
        }
    }

    // TODO TEMPORAL METHOD UNTIL ALL THE PLUG-INS COULD BE REQUESTED THROUGH GET RESOURCES MANAGER OR GET MODULE MANAGER METHODS.
    @Deprecated
    public final AbstractPlugin startAndGetPluginVersion(final PluginVersionReference pluginVersionReference) throws VersionNotFoundException ,
                                                                                                                     CantStartPluginException {

        return fermatPluginManager.startPluginAndReferences(pluginVersionReference);
    }

}
