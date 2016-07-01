package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.FermatContext;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.moduleManagerInterfacea;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetAddonException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetErrorManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetResourcesManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetRuntimeManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAllRegisteredPlatformsException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ErrorManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ModuleManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ResourcesManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.RuntimeManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.resources.ResourcesManager;
//import com.bitdubai.fermat_art_core.ARTPlatform;
import com.bitdubai.fermat_bch_core.BCHPlatform;
import com.bitdubai.fermat_bnk_core.BNKPlatform;
import com.bitdubai.fermat_cbp_core.CBPPlatform;
import com.bitdubai.fermat_ccp_core.CCPPlatform;
import com.bitdubai.fermat_cer_core.CERPlatform;
import com.bitdubai.fermat_cht_core.CHTPlatform;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPlatformException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartAddonException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSystemException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.PlatformNotFoundException;
import com.bitdubai.fermat_csh_core.CSHPlatform;
import com.bitdubai.fermat_p2p_core.P2PPlatform;
import com.bitdubai.fermat_pip_core.PIPPlatform;
//import com.bitdubai.fermat_tky_core.TKYPlatform;
import com.bitdubai.fermat_wpd_core.WPDPlatform;

import org.fermat.fermat_dap_core.DAPPlatform;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_core.FermatSystem</code>
 * starts all the component of the platform and manage it.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public final class FermatSystem {

    private static volatile FermatSystem INSTANCE = null;
    private FermatContext fermatContext;

    private FermatSystemContext fermatSystemContext;
    private FermatAddonManager fermatAddonManager;
    private FermatPluginManager fermatPluginManager;
    public boolean isStarted;

    private static void createInstance() {
        if (INSTANCE == null)
            INSTANCE = new FermatSystem();
    }

    public static FermatSystem getInstance() {
        synchronized (FermatSystem.class) {
            if (INSTANCE == null) createInstance();
        }

        return INSTANCE;
    }

    private FermatSystem() {

    }

    /**
     * Here we start all the platforms of Fermat, one by one.
     * OSA Platform will be needed for the starting of fermat system class, this way we ensure that it will be a osa platform registered.
     *
     * @param osContext   operative system context instance.
     * @param osaPlatform OSA Platform instance.
     * @throws CantStartSystemException if something goes wrong.
     */
    public void start(final Object osContext,
                      final AbstractPlatform osaPlatform) throws CantStartSystemException {

        this.fermatSystemContext = new FermatSystemContext(osContext, fermatContext);
        this.fermatAddonManager = new FermatAddonManager(fermatSystemContext);
        this.fermatPluginManager = new FermatPluginManager(fermatSystemContext, fermatAddonManager, fermatContext);

        try {

            this.registerOsaPlatform(osaPlatform);

        } catch (final CantRegisterPlatformException e) {

            throw new CantStartSystemException(e, "", "Error registering OSA Platform.");
        }

        try {


           // fermatSystemContext.registerPlatform(new ARTPlatform());
            fermatSystemContext.registerPlatform(new BCHPlatform(fermatContext));
            fermatSystemContext.registerPlatform(new BNKPlatform(fermatContext));
            fermatSystemContext.registerPlatform(new CBPPlatform());
            fermatSystemContext.registerPlatform(new CCPPlatform());
            fermatSystemContext.registerPlatform(new CERPlatform());
            fermatSystemContext.registerPlatform(new CHTPlatform());
            fermatSystemContext.registerPlatform(new CSHPlatform());
            fermatSystemContext.registerPlatform(new DAPPlatform());
            fermatSystemContext.registerPlatform(new P2PPlatform());
            fermatSystemContext.registerPlatform(new PIPPlatform(fermatContext));
           // fermatSystemContext.registerPlatform(new TKYPlatform());
            fermatSystemContext.registerPlatform(new WPDPlatform());

        } catch(CantRegisterPlatformException e) {
            throw new CantStartSystemException(e, "", "There was a problem registering a Platform.");
        } catch (Exception e) {

            throw new CantStartSystemException(e, "", "Unhandled Exception.");
        }
    }

    /**
     * Through the method <code>registerOsaPlatform</code> we validate minimally that we're dealing with an osaPlatform.
     *
     * @param osaPlatform instance of osa platform.
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
     * @return an instance of the requested resources manager.
     * @throws CantGetResourcesManagerException  if something goes wrong.
     * @throws ResourcesManagerNotFoundException if we can't find the requested resources manager.
     */
    public final ResourcesManager getResourcesManager(final PluginVersionReference pluginVersionReference) throws CantGetResourcesManagerException,
            ResourcesManagerNotFoundException {

        try {

            final FermatManager resourcesManager = fermatPluginManager.startPluginAndReferences(pluginVersionReference);

            if (resourcesManager instanceof ResourcesManager)
                return (ResourcesManager) resourcesManager;
            else
                throw new CantGetResourcesManagerException(pluginVersionReference.toString3(), "The plugin version requested not implements resources manager interface.");

        } catch (final VersionNotFoundException e) {

            throw new ResourcesManagerNotFoundException(e, pluginVersionReference.toString3(), "The resources manager cannot be found.");
        } catch (final Exception e) {

            throw new CantGetResourcesManagerException(e, pluginVersionReference.toString3(), "Unhandled error.");
        }
    }

    public final ResourcesManager getResourcesManager2(final PluginVersionReference pluginVersionReference) throws CantGetResourcesManagerException,
            ResourcesManagerNotFoundException {

        try {

            final FermatManager resourcesManager = fermatPluginManager.getPlugin(pluginVersionReference);

            if (resourcesManager instanceof ResourcesManager)
                return (ResourcesManager) resourcesManager;
            else
                throw new CantGetResourcesManagerException(pluginVersionReference.toString3(), "The plugin version requested not implements resources manager interface.");

        } catch (final VersionNotFoundException e) {

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
     * @return an instance of the requested module manager.
     * @throws CantGetModuleManagerException  if something goes wrong.
     * @throws ModuleManagerNotFoundException if we can't find the requested module manager.
     */
    public final ModuleManager getModuleManager(final PluginVersionReference pluginVersionReference) throws CantGetModuleManagerException,
            ModuleManagerNotFoundException {

        try {

            final FermatManager moduleManager = fermatPluginManager.startPluginAndReferences(pluginVersionReference);

            if (moduleManager instanceof AbstractModule)
                return ((AbstractModule) moduleManager).getModuleManager();
            else if (moduleManager instanceof ModuleManager) {
                return (ModuleManager) moduleManager;
            } else
                throw new CantGetModuleManagerException(pluginVersionReference.toString3(), "The plugin version requested not implements module manager interface.");

        } catch (final VersionNotFoundException e) {

            throw new ModuleManagerNotFoundException(e, pluginVersionReference.toString3(), "The module manager cannot be found.");
        } catch (final CantGetModuleManagerException e) {

            throw e;
        } catch (final Exception e) {

            throw new CantGetModuleManagerException(e, pluginVersionReference.toString3(), "Unhandled error.");
        }
    }

    /**
     * Through the method <code>getModuleManager</code> the graphic interface can access to the modules of
     * its sub-apps and wallets.
     *
     * @param pluginVersionReference plugin version reference data.
     * @return an instance of the requested module manager.
     * @throws CantGetModuleManagerException  if something goes wrong.
     * @throws ModuleManagerNotFoundException if we can't find the requested module manager.
     */
    public final ModuleManager getModuleManager2(final PluginVersionReference pluginVersionReference) throws CantGetModuleManagerException,
            ModuleManagerNotFoundException {

        try {

            final FermatManager moduleManager = fermatPluginManager.getPlugin(pluginVersionReference);

            if (moduleManager instanceof AbstractModule)
                return ((AbstractModule) moduleManager).getModuleManager();
            else {
                throw new CantGetModuleManagerException(pluginVersionReference.toString3(), "The plugin version requested not implements module manager interface.");
            }

        } catch (final VersionNotFoundException e) {

            throw new ModuleManagerNotFoundException(e, pluginVersionReference.toString3(), "The module manager cannot be found.");
        } catch (final CantGetModuleManagerException e) {

            throw e;
        } catch (final Exception e) {

            throw new CantGetModuleManagerException(e, pluginVersionReference.toString3(), "Unhandled error.");
        }
    }

    public final Class<ModuleManager> getModuleManager3(final PluginVersionReference pluginVersionReference) throws CantGetModuleManagerException,
            ModuleManagerNotFoundException {
        FermatManager moduleManager = null;
        try {

            moduleManager = fermatPluginManager.getPlugin(pluginVersionReference);

            if (moduleManager instanceof AbstractModule)
                return (Class<ModuleManager>) ((AbstractModule) moduleManager).getModuleManager().getClass();
            else {
                Method method = moduleManager.getClass().getMethod("getModuleManager");
                if (method != null) {
                    return method.getAnnotation(moduleManagerInterfacea.class).moduleManager();
                }
                throw new CantGetModuleManagerException(pluginVersionReference.toString3(), "The plugin version requested not implements module manager interface.");
            }

        } catch (final VersionNotFoundException e) {

            throw new ModuleManagerNotFoundException(e, pluginVersionReference.toString3(), "The module manager cannot be found.");
        } catch (final CantGetModuleManagerException e) {
            try {
                Method method = moduleManager.getClass().getMethod("getModuleManager");
                if (method != null) {
                    return method.getAnnotation(moduleManagerInterfacea.class).moduleManager();
                }
            } catch (Exception e1) {
                throw e;
            }

        } catch (final Exception e) {

            throw new CantGetModuleManagerException(e, pluginVersionReference.toString3(), "Unhandled error.");
        }
        return null;
    }


    /**
     * Through the method <code>getRuntimeManager</code> the graphic interface can access to the runtime managers of fermat.
     *
     * @param pluginVersionReference plugin version reference data.
     * @return an instance of the requested runtime manager.
     * @throws CantGetRuntimeManagerException  if something goes wrong.
     * @throws RuntimeManagerNotFoundException if we can't find the requested runtime manager.
     */
    public final RuntimeManager getRuntimeManager(final PluginVersionReference pluginVersionReference) throws RuntimeManagerNotFoundException,
            CantGetRuntimeManagerException {

        try {

            final FermatManager runtimeManager = fermatPluginManager.startPluginAndReferences(pluginVersionReference);

            if (runtimeManager instanceof RuntimeManager)
                return (RuntimeManager) runtimeManager;
            else
                throw new CantGetRuntimeManagerException(pluginVersionReference.toString3(), "The plugin version requested not implements runtime manager interface.");

        } catch (final VersionNotFoundException e) {

            throw new RuntimeManagerNotFoundException(e, pluginVersionReference.toString3(), "The runtime manager cannot be found.");
        } catch (final CantGetRuntimeManagerException e) {

            throw e;
        } catch (final Exception e) {

            throw new CantGetRuntimeManagerException(e, pluginVersionReference.toString3(), "Unhandled error.");
        }
    }

    /**
     * Through the method <code>getRuntimeManager</code> the graphic interface can access to the runtime managers of fermat.
     *
     * @param pluginVersionReference plugin version reference data.
     * @return an instance of the requested runtime manager.
     * @throws CantGetRuntimeManagerException  if something goes wrong.
     * @throws RuntimeManagerNotFoundException if we can't find the requested runtime manager.
     */
    public final RuntimeManager getRuntimeManager2(final PluginVersionReference pluginVersionReference) throws RuntimeManagerNotFoundException,
            CantGetRuntimeManagerException {

        try {

            final FermatManager runtimeManager = fermatPluginManager.getPlugin(pluginVersionReference);

            if (runtimeManager instanceof RuntimeManager)
                return (RuntimeManager) runtimeManager;
            else
                throw new CantGetRuntimeManagerException(pluginVersionReference.toString3(), "The plugin version requested not implements runtime manager interface.");

        } catch (final VersionNotFoundException e) {

            throw new RuntimeManagerNotFoundException(e, pluginVersionReference.toString3(), "The runtime manager cannot be found.");
        } catch (final CantGetRuntimeManagerException e) {

            throw e;
        } catch (final Exception e) {

            throw new CantGetRuntimeManagerException(e, pluginVersionReference.toString3(), "Unhandled error.");
        }
    }

    /**
     * Through the method <code>getErrorManager</code> the graphic interface can access to the error managers of fermat.
     *
     * @param addonVersionReference addon version reference data.
     * @return an instance of the requested error manager.
     * @throws CantGetErrorManagerException  if something goes wrong.
     * @throws ErrorManagerNotFoundException if we can't find the requested error manager.
     */
    public final ErrorManager getErrorManager(final AddonVersionReference addonVersionReference) throws ErrorManagerNotFoundException,
            CantGetErrorManagerException {

        try {

            final FermatManager errorManager = fermatAddonManager.startAddonAndReferences(addonVersionReference);

            if (errorManager instanceof ErrorManager)
                return (ErrorManager) errorManager;
            else
                throw new CantGetModuleManagerException(addonVersionReference.toString3(), "The addon version requested not implements error manager interface.");


        } catch (CantStartAddonException e) {

            throw new CantGetErrorManagerException(e, addonVersionReference.toString3(), "The addon cannot be started.");
        } catch (Exception e) {

            throw new CantGetErrorManagerException(e, addonVersionReference.toString3(), "Unhandled error.");
        }
    }

    // TODO TEMPORAL METHOD UNTIL ALL THE ADD-ONS COULD BE REQUESTED BY ITS OWN METHODS.
    @Deprecated
    public final FermatManager startAndGetAddon(final AddonVersionReference addonVersionReference) throws VersionNotFoundException,
            CantGetAddonException {

        try {

            return fermatAddonManager.startAddonAndReferences(addonVersionReference);

        } catch (CantStartAddonException e) {

            throw new CantGetAddonException(e, addonVersionReference.toString3(), "The addon cannot be started.");
        } catch (Exception e) {

            throw new CantGetAddonException(e, addonVersionReference.toString3(), "Unhandled error.");
        }
    }

    // TODO TEMPORAL METHOD UNTIL ALL THE PLUG-INS COULD BE REQUESTED THROUGH GET RESOURCES MANAGER OR GET MODULE MANAGER METHODS.
    @Deprecated
    public final FermatManager startAndGetPluginVersion(final PluginVersionReference pluginVersionReference) throws VersionNotFoundException,
            CantStartPluginException {

        return fermatPluginManager.startPluginAndReferences(pluginVersionReference);
    }

    public final FermatManager getPlugin(final PluginVersionReference pluginVersionReference) throws VersionNotFoundException,
            CantStartPluginException {

        return fermatPluginManager.getPlugin(pluginVersionReference);
    }


    // TODO THINK ABOUT THIS.
    @Deprecated
    public final void startAllRegisteredPlatforms() throws CantStartAllRegisteredPlatformsException {
        final ConcurrentHashMap<AddonVersionReference, AbstractAddon> addonList = this.fermatSystemContext.listAddonVersions();
        final ConcurrentHashMap<PluginVersionReference, AbstractPlugin> pluginList = this.fermatSystemContext.listPluginVersions();

        for(final ConcurrentHashMap.Entry<AddonVersionReference, AbstractAddon> addon : addonList.entrySet()) {
            try {
                fermatAddonManager.startAddonAndReferences(addon.getValue());
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }

        try {

            List<AbstractPlugin> list = fermatSystemContext.getPlatform(new PlatformReference(Platforms.COMMUNICATION_PLATFORM)).getPlugins();
            for (AbstractPlugin abstractPlugin : list) {
                fermatPluginManager.startPluginAndReferences(abstractPlugin);
                System.out.println("---------------------------------------------\n");
                System.out.println("Cloud client starting");
                System.out.println("---------------------------------------------\n");
            }
        } catch (PlatformNotFoundException e) {
            e.printStackTrace();
        } catch (CantStartPluginException e) {
            e.printStackTrace();
        }
        for (ConcurrentHashMap.Entry<PluginVersionReference, AbstractPlugin> plugin : pluginList.entrySet()) {
            try {
                fermatPluginManager.startPluginAndReferences(plugin.getKey());
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }

        this.isStarted = true;
    }

    public final void startAllRegisteredPlatformsMati() throws CantStartAllRegisteredPlatformsException {
        final ConcurrentHashMap<AddonVersionReference, AbstractAddon> addonList = this.fermatSystemContext.listAddonVersions();
        //map of pluginVersionReference and the plugin enum code
        final List<PluginVersionReference> pluginList = this.fermatSystemContext.listPluginVersionsMati();
        System.out.println("---------------------------------------------\n");
        System.out.println("Starting addons");
        System.out.println("---------------------------------------------\n");
        for (final ConcurrentHashMap.Entry<AddonVersionReference, AbstractAddon> addon : addonList.entrySet()) {
            try {
                fermatAddonManager.startAddonAndReferences(addon.getValue());
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }

        try {

            List<AbstractPlugin> list = fermatSystemContext.getPlatform(new PlatformReference(Platforms.COMMUNICATION_PLATFORM)).getPlugins();
            for (AbstractPlugin abstractPlugin : list) {
                System.out.println("---------------------------------------------\n");
                System.out.println("Cloud client starting");
                System.out.println("---------------------------------------------\n");
                fermatPluginManager.startPluginAndReferences(abstractPlugin);
            }
        } catch (PlatformNotFoundException e) {
            e.printStackTrace();
        } catch (CantStartPluginException e) {
            e.printStackTrace();
        }

        for (PluginVersionReference pluginVersionReference : pluginList) {
            try {
                fermatPluginManager.startPluginAndReferences(pluginVersionReference);
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
//        for(ConcurrentHashMap.Entry<PluginVersionReference, String> plugin : pluginList.entrySet()) {
//            try {
//                fermatPluginManager.startPluginAndReferences(plugin.getKey());
//            } catch (Exception e) {
//                System.err.println(e.toString());
//            }
//        }

        this.isStarted = true;
    }

    public void setFermatContext(FermatContext fermatContext) {
        this.fermatContext = fermatContext;
    }
}
