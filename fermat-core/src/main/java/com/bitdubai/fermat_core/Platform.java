package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.CantReportCriticalStartingProblemException;
import com.bitdubai.fermat_api.CantStartPlatformException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetAddonException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealWithDatabaseManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealsWithLogManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPlatformExceptionSeverity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class <code>com.bitdubai.fermat_core.Platform</code> .
 * start all the components of the system and manage it.
 * <p/>
 * Created by ciencias on 20/01/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 24/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class Platform implements Serializable {

    private final Map<Plugins, Plugin> dealsWithDatabaseManagersPlugins;
    private final Map<Plugins, Plugin> dealsWithLogManagersPlugins;
    private final Map<Addons, Addon> dealsWithDatabaseManagersAddons;
    private final Map<Addons, Addon> dealsWithLogManagersAddons;

    /**
     * Represent the corePlatformContext
     */
    private CorePlatformContext corePlatformContext;

    private FermatSystem fermatSystem;

    public void setFermatSystem(FermatSystem fermatSystem) {
        this.fermatSystem = fermatSystem;
    }

    /**
     * Constructor
     */
    public Platform() {
        corePlatformContext = new CorePlatformContext();

        dealsWithDatabaseManagersPlugins = new ConcurrentHashMap<>();
        dealsWithLogManagersPlugins = new ConcurrentHashMap<>();
        dealsWithDatabaseManagersAddons = new ConcurrentHashMap<>();
        dealsWithLogManagersAddons = new ConcurrentHashMap<>();

    }

    /**
     * Method that start the platform
     *
     * @throws CantStartPlatformException
     * @throws CantReportCriticalStartingProblemException
     */
    public void start() throws CantStartPlatformException, CantReportCriticalStartingProblemException {

        initializePlugins();

        for (Addons registeredDescriptor : corePlatformContext.getRegisteredAddonskeys())
            checkAddonForDeveloperInterfaces(registeredDescriptor);

        for (Plugins registeredDescriptor : corePlatformContext.getRegisteredPluginskeys())
            checkPluginForDeveloperInterfaces(registeredDescriptor);

        // Set Actor developer managers
        ((DealWithDatabaseManagers) corePlatformContext.getPlugin(Plugins.BITDUBAI_DEVELOPER_MODULE)).setDatabaseManagers(dealsWithDatabaseManagersPlugins, dealsWithDatabaseManagersAddons);
        ((DealsWithLogManagers) corePlatformContext.getPlugin(Plugins.BITDUBAI_DEVELOPER_MODULE)).setLogManagers(dealsWithLogManagersPlugins, dealsWithLogManagersAddons);
    }

    private void initializePlugins() throws CantStartPlatformException {

        boolean CBP = true;
        boolean CCP = true;
        boolean DAP = true;
        boolean PIP = true;
        boolean WPD = true;

        // addons initializing

        final List<AddonVersionReference> addonsToInstantiate = new ArrayList<>();
        final Map<PluginVersionReference, Plugins> pluginsToInstantiate = new HashMap<>();

        addonsToInstantiate.add(ref(Platforms.PLUG_INS_PLATFORM   , Layers.PLATFORM_SERVICE, Addons.ERROR_MANAGER));
        addonsToInstantiate.add(ref(Platforms.PLUG_INS_PLATFORM   , Layers.PLATFORM_SERVICE, Addons.PLATFORM_INFO));

        if (PIP) {
            pluginsToInstantiate.put(ref(Platforms.PLUG_INS_PLATFORM, Layers.SUB_APP_MODULE , Plugins.DEVELOPER        ), Plugins.BITDUBAI_DEVELOPER_MODULE);
            pluginsToInstantiate.put(ref(Platforms.PLUG_INS_PLATFORM, Layers.SUB_APP_MODULE , Plugins.NOTIFICATION     ), Plugins.BITDUBAI_MIDDLEWARE_NOTIFICATION);

            pluginsToInstantiate.put(ref(Platforms.PLUG_INS_PLATFORM, Layers.ENGINE         , Plugins.DESKTOP_RUNTIME  ), Plugins.BITDUBAI_DESKTOP_RUNTIME);
            pluginsToInstantiate.put(ref(Platforms.PLUG_INS_PLATFORM, Layers.ENGINE         , Plugins.SUB_APP_RUNTIME), Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);

            pluginsToInstantiate.put(ref(Platforms.PLUG_INS_PLATFORM, Layers.NETWORK_SERVICE, Plugins.SUB_APP_RESOURCES), Plugins.BITDUBAI_SUBAPP_RESOURCES_NETWORK_SERVICE);
        }

        if (CCP) {
            pluginsToInstantiate.put(ref(Platforms.CRYPTO_CURRENCY_PLATFORM, Layers.IDENTITY       , Plugins.INTRA_WALLET_USER     ), Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION);

            pluginsToInstantiate.put(ref(Platforms.CRYPTO_CURRENCY_PLATFORM, Layers.TRANSACTION    , Plugins.INCOMING_EXTRA_USER   ), Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION);
            pluginsToInstantiate.put(ref(Platforms.CRYPTO_CURRENCY_PLATFORM, Layers.TRANSACTION    , Plugins.INCOMING_INTRA_USER   ), Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION);

            pluginsToInstantiate.put(ref(Platforms.CRYPTO_CURRENCY_PLATFORM, Layers.DESKTOP_MODULE , Plugins.WALLET_MANAGER        ), Plugins.BITDUBAI_WPD_WALLET_MANAGER_DESKTOP_MODULE);

            pluginsToInstantiate.put(ref(Platforms.CRYPTO_CURRENCY_PLATFORM, Layers.SUB_APP_MODULE , Plugins.INTRA_WALLET_USER     ), Plugins.BITDUBAI_INTRA_USER_FACTORY_MODULE);

            pluginsToInstantiate.put(ref(Platforms.CRYPTO_CURRENCY_PLATFORM, Layers.WALLET_MODULE  , Plugins.CRYPTO_WALLET         ), Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE);

        }

        if (WPD) {
            pluginsToInstantiate.put(ref(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.ENGINE         , Plugins.WALLET_RUNTIME   ), Plugins.BITDUBAI_WALLET_RUNTIME_MODULE);

            pluginsToInstantiate.put(ref(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.IDENTITY       , Plugins.PUBLISHER        ), Plugins.BITDUBAI_WPD_PUBLISHER_IDENTITY);

            pluginsToInstantiate.put(ref(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.MIDDLEWARE     , Plugins.WALLET_FACTORY   ), Plugins.BITDUBAI_WPD_WALLET_FACTORY_MIDDLEWARE);
            pluginsToInstantiate.put(ref(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.MIDDLEWARE     , Plugins.WALLET_PUBLISHER ), Plugins.BITDUBAI_WPD_WALLET_PUBLISHER_MIDDLEWARE);
            pluginsToInstantiate.put(ref(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.MIDDLEWARE     , Plugins.WALLET_STORE     ), Plugins.BITDUBAI_WPD_WALLET_STORE_MIDDLEWARE);
            pluginsToInstantiate.put(ref(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.MIDDLEWARE     , Plugins.WALLET_SETTINGS  ), Plugins.BITDUBAI_WPD_WALLET_SETTINGS_MIDDLEWARE);

            pluginsToInstantiate.put(ref(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.NETWORK_SERVICE, Plugins.WALLET_RESOURCES ), Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE);
            pluginsToInstantiate.put(ref(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.NETWORK_SERVICE, Plugins.WALLET_COMMUNITY ), Plugins.BITDUBAI_WALLET_COMMUNITY_NETWORK_SERVICE);
            pluginsToInstantiate.put(ref(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.NETWORK_SERVICE, Plugins.WALLET_STORE), Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE);
            pluginsToInstantiate.put(ref(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.NETWORK_SERVICE, Plugins.WALLET_STATISTICS), Plugins.BITDUBAI_WALLET_STATISTICS_NETWORK_SERVICE);

            pluginsToInstantiate.put(ref(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.SUB_APP_MODULE , Plugins.WALLET_FACTORY), Plugins.BITDUBAI_WPD_WALLET_FACTORY_SUB_APP_MODULE);
            pluginsToInstantiate.put(ref(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.SUB_APP_MODULE , Plugins.WALLET_STORE     ), Plugins.BITDUBAI_WPD_WALLET_STORE_SUB_APP_MODULE);
            pluginsToInstantiate.put(ref(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.SUB_APP_MODULE , Plugins.WALLET_PUBLISHER), Plugins.BITDUBAI_WPD_WALLET_PUBLISHER_SUB_APP_MODULE);
        }

        if (DAP) {

            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.NETWORK_SERVICE          , Plugins.ASSET_TRANSMISSION     )   ,   Plugins.BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE);

            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.DIGITAL_ASSET_TRANSACTION, Plugins.ASSET_APPROPRIATION    )   ,   Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION);
            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.DIGITAL_ASSET_TRANSACTION, Plugins.ASSET_DISTRIBUTION     )   ,   Plugins.BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION);
            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.DIGITAL_ASSET_TRANSACTION, Plugins.ASSET_ISSUING          )   ,   Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION);
            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.DIGITAL_ASSET_TRANSACTION, Plugins.ASSET_RECEPTION        )   ,   Plugins.BITDUBAI_ASSET_RECEPTION_TRANSACTION);
            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.DIGITAL_ASSET_TRANSACTION, Plugins.ASSET_RECEPTION        )   ,   Plugins.BITDUBAI_ISSUER_REDEMPTION_TRANSACTION);
            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.DIGITAL_ASSET_TRANSACTION, Plugins.REDEEM_POINT_REDEMPTION)   ,   Plugins.BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION);
            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.DIGITAL_ASSET_TRANSACTION, Plugins.USER_REDEMPTION        )   ,   Plugins.BITDUBAI_USER_REDEMPTION_TRANSACTION);

            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.WALLET_MODULE            , Plugins.ASSET_ISSUER           )   ,   Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE);
            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.WALLET_MODULE            , Plugins.ASSET_USER             )   ,   Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE);
            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.WALLET_MODULE            , Plugins.REDEEM_POINT           )   ,   Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE);

            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.SUB_APP_MODULE           , Plugins.ASSET_FACTORY          )   ,   Plugins.BITDUBAI_ASSET_FACTORY_MODULE);
            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.SUB_APP_MODULE           , Plugins.ASSET_ISSUER_COMMUNITY )   ,   Plugins.BITDUBAI_DAP_ASSET_ISSUER_COMMUNITY_SUB_APP_MODULE);
            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.SUB_APP_MODULE           , Plugins.ASSET_USER_COMMUNITY   )   ,   Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE);
            pluginsToInstantiate.put(ref(Platforms.DIGITAL_ASSET_PLATFORM, Layers.SUB_APP_MODULE           , Plugins.REDEEM_POINT_COMMUNITY )   ,   Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE);

        }

        if(CBP){
            pluginsToInstantiate.put(ref(Platforms.CRYPTO_BROKER_PLATFORM, Layers.SUB_APP_MODULE, Plugins.CRYPTO_BROKER_IDENTITY  ), Plugins.BITDUBAI_CBP_CRYPTO_BROKER_IDENTITY_SUB_APP_MODULE);
            pluginsToInstantiate.put(ref(Platforms.CRYPTO_BROKER_PLATFORM, Layers.SUB_APP_MODULE, Plugins.CRYPTO_CUSTOMER_IDENTITY), Plugins.BITDUBAI_CBP_CRYPTO_CUSTOMER_IDENTITY_SUB_APP_MODULE);

            pluginsToInstantiate.put(ref(Platforms.CRYPTO_BROKER_PLATFORM, Layers.WALLET_MODULE , Plugins.CRYPTO_BROKER           ), Plugins.BITDUBAI_CBP_CRYPTO_BROKER_WALLET_MODULE);
            pluginsToInstantiate.put(ref(Platforms.CRYPTO_BROKER_PLATFORM, Layers.WALLET_MODULE , Plugins.CRYPTO_CUSTOMER         ), Plugins.BITDUBAI_CBP_CRYPTO_CUSTOMER_WALLET_MODULE);
        }

        for (AddonVersionReference avr :addonsToInstantiate)
            startEachAddon(avr);

        for (Map.Entry<PluginVersionReference, Plugins> pvr : pluginsToInstantiate.entrySet())
            startEachPlugin(pvr.getKey(), pvr.getValue());

        System.out.println("soy BITDUBAI_INTRA_USER_FACTORY_MODULE: "+corePlatformContext.getPlugin(Plugins.BITDUBAI_INTRA_USER_FACTORY_MODULE));

        List<Map.Entry<Plugins, Long>> list = new LinkedList<>(pluginsStartUpTime.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Plugins, Long>>() {
            public int compare(Map.Entry<Plugins, Long> o1, Map.Entry<Plugins, Long> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        System.out.println("************************************************************************");
        System.out.println("---- Lista de Tiempos de Start-Up de Plugins order by Start-Up time ----");
        System.out.println("************************************************************************");
        for (Map.Entry<Plugins, Long> entry : list) {
            System.out.println(entry.getKey().toString() + " - Start-Up time: " + entry.getValue() / 1000 + " seconds / "+ entry.getValue() + " millis.");
        }
        System.out.println("************************************************************************");

    }

    private void startEachAddon(final AddonVersionReference addonVersionReference) {

        try {

            Addon addon = fermatSystem.getAddon(addonVersionReference);

            corePlatformContext.registerAddon(
                    addon,
                    addonVersionReference.getAddonDeveloperReference().getAddonReference().getAddon()
            );

        } catch (Exception e) {

            reportUnexpectedError(UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
        }
    }

    private void startEachPlugin(final PluginVersionReference pluginVersionReference,
                                 final Plugins                descriptor            ) {


        long init = System.currentTimeMillis();

        try {

            Plugin plugin = fermatSystem.startAndGetPluginVersion(pluginVersionReference);
            corePlatformContext.registerPlugin(plugin, descriptor);

        } catch (Exception e) {

            reportUnexpectedError(UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
        }

        long end = System.currentTimeMillis();

        pluginsStartUpTime.put(descriptor, end - init);
    }

    private AddonVersionReference ref(Platforms platform, Layers layer, Addons fermatAddonsEnum) {

        return new AddonVersionReference(
                platform,
                layer,
                fermatAddonsEnum,
                Developers.BITDUBAI,
                new Version()
        );
    }

    private PluginVersionReference ref(Platforms platform, Layers layer, Plugins fermatPluginsEnum) {

        return new PluginVersionReference(
                platform,
                layer,
                fermatPluginsEnum,
                Developers.BITDUBAI,
                new Version()
        );
    }

    Map<Plugins, Long> pluginsStartUpTime = new HashMap<>();

    private void reportUnexpectedError(UnexpectedPlatformExceptionSeverity severity, Exception e) {
        try {
            ErrorManager errorManager = (ErrorManager) fermatSystem.getAddon(ref(Platforms.PLUG_INS_PLATFORM, Layers.PLATFORM_SERVICE, Addons.ERROR_MANAGER));
            errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, severity, e);
        } catch (CantGetAddonException | VersionNotFoundException z) {
            System.out.println("can't get error manager");
            System.out.println(z);
        } catch (Exception z) {
            System.out.println("unhandled exception");
            System.out.println(z.toString());
        }
    }

    private void checkAddonForDeveloperInterfaces(final Addons descriptor) {

        Addon addon = corePlatformContext.getAddon(descriptor);

        if (addon == null)
            return;

        if (addon instanceof DatabaseManagerForDevelopers)
            dealsWithDatabaseManagersAddons.put(descriptor, addon);

        if (addon instanceof LogManagerForDevelopers)
            dealsWithLogManagersAddons.put(descriptor, addon);
    }

    private void checkPluginForDeveloperInterfaces(final Plugins descriptor) {

        Plugin plugin = corePlatformContext.getPlugin(descriptor);

        if (plugin == null)
            return;

        if (plugin instanceof DatabaseManagerForDevelopers)
            dealsWithDatabaseManagersPlugins.put(descriptor, plugin);

        if (plugin instanceof LogManagerForDevelopers)
            dealsWithLogManagersPlugins.put(descriptor, plugin);

    }

    /**
     * Get the CorePlatformContext
     *
     * @return CorePlatformContext
     */
    public CorePlatformContext getCorePlatformContext() {
        return corePlatformContext;
    }
}
