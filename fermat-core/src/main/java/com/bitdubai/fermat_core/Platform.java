/*
 * @#Platform.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.*;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;

import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealWithDatabaseManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealsWithLogManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformLayers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.DealsWithCryptoTransmissionNetworkService;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.DealsWithBitcoinNetwork;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.DealsWithCCPIntraWalletUsers;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.DealsWithCryptoPaymentRequestNetworkService;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.DealsWithCryptoPayment;
import com.bitdubai.fermat_core.layer.ccp.actor.CCPActorLayer;
import com.bitdubai.fermat_core.layer.ccp.request.CCPRequestLayer;
import com.bitdubai.fermat_core.layer.dap_actor_network_service.DAPActorNetworServiceLayer;
import com.bitdubai.fermat_core.layer.dap_network_service.DAPNetworkServiceLayer;
import com.bitdubai.fermat_core.layer.dap_wallet.DAPWalletLayer;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.interfaces.DealsWithOutgoingIntraActor;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_core.layer.wpd.actor.WPDActorLayer;
import com.bitdubai.fermat_core.layer.wpd.desktop_module.WPDDesktopModuleLayer;
import com.bitdubai.fermat_core.layer.wpd.engine.WPDEngineLayer;
import com.bitdubai.fermat_core.layer.wpd.identity.WPDIdentityLayer;
import com.bitdubai.fermat_core.layer.wpd.middleware.WPDMiddlewareLayer;
import com.bitdubai.fermat_core.layer.wpd.network_service.WPDNetworkServiceLayer;
import com.bitdubai.fermat_core.layer.wpd.sub_app_module.WPDSubAppModuleLayer;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.DealsWithAssetIssuerWalletSubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_reception.interfaces.AssetReceptionManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_reception.interfaces.DealsWithAssetReception;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.DealsWithAssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.DealsWithAssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DealsWithAssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProjectManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.DealsWithWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettingsManager;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.DealsWithWalletModuleCryptoWallet;
import com.bitdubai.fermat_api.layer.osa_android.location_system.DealsWithDeviceLocation;

import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.DealsWithCryptoAddressesNetworkService;
import com.bitdubai.fermat_core.layer.ccp.identity.CCPIdentityLayer;
import com.bitdubai.fermat_core.layer.ccp.middleware.CCPMiddlewareLayer;
import com.bitdubai.fermat_core.layer.ccp.network_service.CCPNetworkServiceLayer;
import com.bitdubai.fermat_core.layer.ccp.transaction.CCPTransactionLayer;

import com.bitdubai.fermat_core.layer.dap_actor.DAPActorLayer;
import com.bitdubai.fermat_core.layer.dap_identity.DAPIdentityLayer;
import com.bitdubai.fermat_core.layer.dap_middleware.DAPMiddlewareLayer;
import com.bitdubai.fermat_core.layer.dap_module.DAPModuleLayer;
import com.bitdubai.fermat_core.layer.dap_transaction.DAPTransactionLayer;
import com.bitdubai.fermat_core.layer.dmp_wallet_module.WalletModuleLayer;
import com.bitdubai.fermat_core.layer.pip_engine.EngineLayer;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.DealsWithAssetVault;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.DealsWithAssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces.DealsWithModuleAseetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.DealsWithAssetIssuerWallet;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.DealsWithWsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.DealsWithActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.DealsWithActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.DealsWithActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.DealsWithIdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.DealsWithIdentityAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.DealsWithIdentityAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentityManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.DealsWithAssetIssuing;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.interfaces.AssetDistributionManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.interfaces.DealsWithAssetDistribution;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEventMonitor;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.IntraWalletUserManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.DealsWithCCPIntraWalletUser;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.DealsWithWalletContacts;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.DealsWithWalletFactory;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.DealsWithWalletManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.interfaces.DealsWithWalletPublisherMiddlewarePlugin;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewarePlugin;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces.DealsWithWalletStoreMiddleware;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces.WalletStoreManager;
;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.DealsWithIntraUsersModule;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserModuleManager;

import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.DealsWithWalletPublisherModule;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.WalletPublisherModuleManager;

import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.DealsWithWalletStoreModule;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.DealsWithIntraUsersNetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.DealsWithWalletResources;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesInstalationManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_statistics.interfaces.DealsWithWalletStatisticsNetworkService;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_statistics.interfaces.WalletStatisticsManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.DealsWithWalletStoreNetworkService;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.DealsWithOutgoingExtraUser;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
import com.bitdubai.fermat_api.layer.osa_android.DataBaseSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.FileSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.LocationSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.LoggerSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_core.layer.dmp_request.RequestServiceLayer;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.DealsWithCommunicationLayerManager;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.DealsWithToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DealsWithIdentityDesigner;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentityManager;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DealsWithDeveloperIdentity;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentityManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.interfaces.DealsWithPublisherIdentity;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.interfaces.PublisherIdentityManager;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.DealsWithIdentityTranslator;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentityManager;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.DealsWithDeveloperModule;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.DeveloperModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.interfaces.DealsWithExtraUsers;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_core.layer.cry_crypto_router.CryptoRouterLayer;
import com.bitdubai.fermat_core.layer.dmp_basic_wallet.BasicWalletLayer;
import com.bitdubai.fermat_core.layer.dmp_transaction.TransactionLayer;
import com.bitdubai.fermat_core.layer.pip_actor.ActorLayer;
import com.bitdubai.fermat_core.layer.dmp_identity.IdentityLayer;
import com.bitdubai.fermat_core.layer.pip_platform_service.PlatformServiceLayer;

import com.bitdubai.fermat_core.layer.all_definition.DefinitionLayer;
import com.bitdubai.fermat_core.layer.pip_hardware.HardwareLayer;
import com.bitdubai.fermat_core.layer.pip_user.UserLayer;
import com.bitdubai.fermat_core.layer.pip_license.LicenseLayer;
import com.bitdubai.fermat_core.layer.dmp_world.WorldLayer;
import com.bitdubai.fermat_core.layer.cry_crypto_network.CryptoNetworkLayer;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.DealsWithCryptoAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_network.CryptoNetworks;
import com.bitdubai.fermat_core.layer.cry_cypto_vault.CryptoVaultLayer;
import com.bitdubai.fermat_core.layer.cry_crypto_module.CryptoLayer;
import com.bitdubai.fermat_core.layer.p2p_communication.CommunicationLayer;
import com.bitdubai.fermat_core.layer.dmp_network_service.NetworkServiceLayer;
import com.bitdubai.fermat_core.layer.dmp_middleware.MiddlewareLayer;
import com.bitdubai.fermat_core.layer.dmp_module.ModuleLayer;
import com.bitdubai.fermat_core.layer.dmp_agent.AgentLayer;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.DealsWithBitcoinCryptoNetwork;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.DealsWithIncomingCrypto;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.DealsWithPlatformInfo;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfoManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DealsWithDeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class <code>com.bitdubai.fermat_core.Platform</code> start all
 * component of the platform and manage it
 * <p/>
 * <p/>
 * Created by ciencias on 20/01/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 24/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class Platform implements Serializable {

    /**
     * Represent the Logger
     */
    private static Logger LOG = Logger.getGlobal();

    /**
     * Represent the dealsWithDatabaseManagersPlugins
     */
    private Map<Plugins, Plugin> dealsWithDatabaseManagersPlugins;

    /**
     * Represent the dealsWithLogManagersPlugins
     */
    private Map<Plugins, Plugin> dealsWithLogManagersPlugins;

    /**
     * Represent the dealsWithDatabaseManagersAddons
     */
    private Map<Addons, Addon> dealsWithDatabaseManagersAddons;

    /**
     * Represent the dealsWithLogManagersAddons
     */
    private Map<Addons, Addon> dealsWithLogManagersAddons;

    /**
     * Represent the pluginsIdentityManager
     */
    private PluginsIdentityManager pluginsIdentityManager;

    /**
     * Represent the corePlatformContext
     */
    private CorePlatformContext corePlatformContext;

    /**
     * Represent the osContext
     */
    private Object osContext;

    /**
     * Represent the fileSystemOs
     */
    private FileSystemOs fileSystemOs;

    /**
     * Represent the databaseSystemOs
     */
    private DataBaseSystemOs databaseSystemOs;

    /**
     * Represent the locationSystemOs
     */
    private LocationSystemOs locationSystemOs;

    /**
     * Represent the loggerSystemOs
     */
    private LoggerSystemOs loggerSystemOs;


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
     * Somebody is starting the com.bitdubai.platform. The com.bitdubai.platform is portable. That somebody is OS dependent and has access to
     * the OS. I have to transport a reference to that somebody to the OS subsystem in other to allow it to access
     * the OS through this reference.
     */
    public void setOsContext(Object osContext) {
        this.osContext = osContext;
    }

    /**
     * An unresolved bug in either Android or Gradle does not allow us to create the os object on a library outside the
     * main module. While this situation persists, we will create it inside the wallet package and receive it throw this
     * method.
     */
    public void setFileSystemOs(FileSystemOs fileSystemOs) {
        this.fileSystemOs = fileSystemOs;
    }

    /**
     * Set the DataBaseSystemOs
     *
     * @param databaseSystemOs
     */
    public void setDataBaseSystemOs(DataBaseSystemOs databaseSystemOs) {
        this.databaseSystemOs = databaseSystemOs;
    }

    /**
     * Set the LocationSystemOs
     *
     * @param locationSystemOs
     */
    public void setLocationSystemOs(LocationSystemOs locationSystemOs) {
        this.locationSystemOs = locationSystemOs;
    }

    /**
     * Set the LoggerSystemOs
     *
     * @param loggerSystemOs
     */
    public void setLoggerSystemOs(LoggerSystemOs loggerSystemOs) {
        this.loggerSystemOs = loggerSystemOs;
    }


    /**
     * Method that start the platform
     *
     * @throws CantStartPlatformException
     * @throws CantReportCriticalStartingProblemException
     */
    public void start() throws CantStartPlatformException, CantReportCriticalStartingProblemException {

        LOG.info("Platform - start()");

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * ------------------------------------------------------------------------------------------------------------*
         * Layers initialization                                                                                       *
         * ------------------------------------------------------------------------------------------------------------*
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        initializePlatformLayers();


        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * ------------------------------------------------------------------------------------------------------------*
         * Addons initialization                                                                                       *
         * ------------------------------------------------------------------------------------------------------------*
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        initializeAddons();

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * ------------------------------------------------------------------------------------------------------------*
         * Plugin initialization                                                                                       *
         * ------------------------------------------------------------------------------------------------------------*
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        initializePlugins();

        /*
         * Check addon for developer interfaces
         */
        for (Addons registeredDescriptor : corePlatformContext.getRegisteredAddonskeys()) {
            checkAddonForDeveloperInterfaces(registeredDescriptor);
        }

        /*
         * Check plugin for developer interfaces
         */
        for (Plugins registeredDescriptor : corePlatformContext.getRegisteredPluginskeys()) {
            checkPluginForDeveloperInterfaces(registeredDescriptor);
        }


        /**
         * Set Actor developer like manager
         */

        ((DealWithDatabaseManagers) corePlatformContext.getPlugin(Plugins.BITDUBAI_DEVELOPER_MODULE)).setDatabaseManagers(dealsWithDatabaseManagersPlugins, dealsWithDatabaseManagersAddons);
        ((DealsWithLogManagers) corePlatformContext.getPlugin(Plugins.BITDUBAI_DEVELOPER_MODULE)).setLogManagers(dealsWithLogManagersPlugins, dealsWithLogManagersAddons);

    }

    /**
     * Method tha initialize all platform layer component
     */
    private void initializePlatformLayers() throws CantReportCriticalStartingProblemException {

        LOG.info("Platform - initializing Platform Layers ...");

        /**
         * Here I will be starting all the platforms layers. It is required that none of them fails. That does not mean
         * that a layer will have at least one service to offer. It depends on each layer. If one believes its lack of
         * services prevent the whole platform to start, then it will throw an exception that will effectively prevent
         * the platform to start.
         */
        try {


            /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
             * ------------------------------------------------------------------------------------------------------------*
             * Critical Layer initialization                                                                               *
             * ------------------------------------------------------------------------------------------------------------*
             * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

            /*
             * Create and star Definition Layer
             */
            PlatformLayer mDefinitionLayer = new DefinitionLayer();
            mDefinitionLayer.start();

            /*
             * Create and star Platform Service Layer
             */
            PlatformLayer mPlatformServiceLayer = new PlatformServiceLayer();
            mPlatformServiceLayer.start();


            /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
             * ------------------------------------------------------------------------------------------------------------*
             * Other Layer initialization                                                                                  *
             * ------------------------------------------------------------------------------------------------------------*
             * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

            /*
             * Created and register into the platform context
             */

            //corePlatformContext.registerPlatformLayer(new OsLayer(),              PlatformLayers.BITDUBAI_OS_LAYER);  Due to an Android bug is not possible to handle this here.
            corePlatformContext.registerPlatformLayer(new HardwareLayer(), PlatformLayers.BITDUBAI_HARDWARE_LAYER);
            corePlatformContext.registerPlatformLayer(new UserLayer(), PlatformLayers.BITDUBAI_USER_LAYER);
            corePlatformContext.registerPlatformLayer(new LicenseLayer(), PlatformLayers.BITDUBAI_LICENSE_LAYER);
            corePlatformContext.registerPlatformLayer(new WorldLayer(), PlatformLayers.BITDUBAI_WORLD_LAYER);
            corePlatformContext.registerPlatformLayer(new CryptoNetworkLayer(), PlatformLayers.BITDUBAI_CRYPTO_NETWORK_LAYER);
            corePlatformContext.registerPlatformLayer(new CryptoVaultLayer(), PlatformLayers.BITDUBAI_CRYPTO_VAULT_LAYER);
            corePlatformContext.registerPlatformLayer(new CryptoLayer(), PlatformLayers.BITDUBAI_CRYPTO_LAYER);
            corePlatformContext.registerPlatformLayer(new CryptoRouterLayer(), PlatformLayers.BITDUBAI_CRYPTO_ROUTER_LAYER);
            corePlatformContext.registerPlatformLayer(new CommunicationLayer(), PlatformLayers.BITDUBAI_COMMUNICATION_LAYER);
            corePlatformContext.registerPlatformLayer(new NetworkServiceLayer(), PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER);
            corePlatformContext.registerPlatformLayer(new TransactionLayer(), PlatformLayers.BITDUBAI_TRANSACTION_LAYER);
            corePlatformContext.registerPlatformLayer(new MiddlewareLayer(), PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER);
            corePlatformContext.registerPlatformLayer(new ModuleLayer(), PlatformLayers.BITDUBAI_MODULE_LAYER);
            corePlatformContext.registerPlatformLayer(new AgentLayer(), PlatformLayers.BITDUBAI_AGENT_LAYER);
            corePlatformContext.registerPlatformLayer(new BasicWalletLayer(), PlatformLayers.BITDUBAI_BASIC_WALLET_LAYER);
            corePlatformContext.registerPlatformLayer(new WalletModuleLayer(), PlatformLayers.BITDUBAI_WALLET_MODULE_LAYER);
            corePlatformContext.registerPlatformLayer(new ActorLayer(), PlatformLayers.BITDUBAI_PIP_ACTOR_LAYER);
            corePlatformContext.registerPlatformLayer(new com.bitdubai.fermat_core.layer.pip_module.ModuleLayer(), PlatformLayers.BITDUBAI_PIP_MODULE_LAYER);
            corePlatformContext.registerPlatformLayer(new com.bitdubai.fermat_core.layer.pip_network_service.NetworkServiceLayer(), PlatformLayers.BITDUBAI_PIP_NETWORK_SERVICE_LAYER);
            corePlatformContext.registerPlatformLayer(new RequestServiceLayer(), PlatformLayers.BITDUBAI_REQUEST_LAYER);
            corePlatformContext.registerPlatformLayer(new com.bitdubai.fermat_core.layer.pip_Identity.IdentityLayer(), PlatformLayers.BITDUBAI_PIP_IDENTITY_LAYER);
            corePlatformContext.registerPlatformLayer(new IdentityLayer(), PlatformLayers.BITDUBAI_IDENTITY_LAYER);
            corePlatformContext.registerPlatformLayer(new EngineLayer(), PlatformLayers.BITDUBAI_ENGINE_LAYER);

            // Init CCP Layers
            corePlatformContext.registerPlatformLayer(new CCPActorLayer(), PlatformLayers.BITDUBAI_CCP_ACTOR_LAYER);
            corePlatformContext.registerPlatformLayer(new CCPIdentityLayer(), PlatformLayers.BITDUBAI_CCP_IDENTITY_LAYER);
            corePlatformContext.registerPlatformLayer(new CCPMiddlewareLayer(), PlatformLayers.BITDUBAI_CCP_MIDDLEWARE_LAYER);
            corePlatformContext.registerPlatformLayer(new CCPNetworkServiceLayer(), PlatformLayers.BITDUBAI_CCP_NETWORK_SERVICE_LAYER);
            corePlatformContext.registerPlatformLayer(new CCPRequestLayer(), PlatformLayers.BITDUBAI_CCP_REQUEST_LAYER);
            corePlatformContext.registerPlatformLayer(new CCPTransactionLayer(), PlatformLayers.BITDUBAI_CCP_TRANSACTION_LAYER);
            // End  CCP Layers

            // Init DAP Layers
            corePlatformContext.registerPlatformLayer(new DAPActorLayer(), PlatformLayers.BITDUBAI_DAP_ACTOR_LAYER);
            corePlatformContext.registerPlatformLayer(new DAPActorNetworServiceLayer(), PlatformLayers.BITDUBAI_DAP_ACTOR_NETWORK_SERVICE_LAYER);
            corePlatformContext.registerPlatformLayer(new DAPIdentityLayer(), PlatformLayers.BITDUBAI_DAP_IDENTITY_LAYER);
            corePlatformContext.registerPlatformLayer(new DAPMiddlewareLayer(), PlatformLayers.BITDUBAI_DAP_MIDDLEWARE_LAYER);
            corePlatformContext.registerPlatformLayer(new DAPModuleLayer(), PlatformLayers.BITDUBAI_DAP_MODULE_LAYER);
            corePlatformContext.registerPlatformLayer(new DAPNetworkServiceLayer(), PlatformLayers.BITDUBAI_DAP_NETWORK_SERVICE_LAYER);
            corePlatformContext.registerPlatformLayer(new DAPTransactionLayer(), PlatformLayers.BITDUBAI_DAP_TRANSACTION_LAYER);
            corePlatformContext.registerPlatformLayer(new DAPWalletLayer(), PlatformLayers.BITDUBAI_DAP_WALLET_LAYER);
            // End  DAP Layers

            // Init WPD Layers
            corePlatformContext.registerPlatformLayer(new WPDActorLayer(), PlatformLayers.BITDUBAI_WPD_ACTOR_LAYER);
            corePlatformContext.registerPlatformLayer(new WPDDesktopModuleLayer(), PlatformLayers.BITDUBAI_WPD_DESKTOP_MODULE_LAYER);
            corePlatformContext.registerPlatformLayer(new WPDEngineLayer(), PlatformLayers.BITDUBAI_WPD_ENGINE_LAYER);
            corePlatformContext.registerPlatformLayer(new WPDIdentityLayer(), PlatformLayers.BITDUBAI_WPD_IDENTITY_LAYER);
            corePlatformContext.registerPlatformLayer(new WPDMiddlewareLayer(), PlatformLayers.BITDUBAI_WPD_MIDDLEWARE_LAYER);
            corePlatformContext.registerPlatformLayer(new WPDNetworkServiceLayer(), PlatformLayers.BITDUBAI_WPD_NETWORK_SERVICE_LAYER);
            corePlatformContext.registerPlatformLayer(new WPDSubAppModuleLayer(), PlatformLayers.BITDUBAI_WPD_SUB_APP_MODULE_LAYER);
            // End  WPD Layers

            /*
             * Start all other platform layers
             */
            for (PlatformLayers key : corePlatformContext.getRegisteredPlatformLayerskeys()) {

                /*
                 * Get the platformLayer  by key
                 */
                PlatformLayer platformLayer = corePlatformContext.getPlatformLayer(key);

                /*
                 * If not null
                 */
                if (platformLayer != null) {

                    /*
                     * Start the layer
                     */
                    platformLayer.start();
                }
            }

            /*
             * Register the critical layer after the other layer are started
             * to prevent start again the critical layer in the previous code
             */
            corePlatformContext.registerPlatformLayer(mDefinitionLayer, PlatformLayers.BITDUBAI_DEFINITION_LAYER);
            corePlatformContext.registerPlatformLayer(mPlatformServiceLayer, PlatformLayers.BITDUBAI_PLATFORM_SERVICE_LAYER);

        } catch (CantStartLayerException cantStartLayerException) {

            LOG.log(Level.SEVERE, cantStartLayerException.getLocalizedMessage());

            /**
             * At this point the platform not only can not be started but also the problem can not be reported. That is
             * the reason why I am going to throw a special exception in order to alert the situation to whoever is running
             * the GUI. In this way it can alert the end user of what is going on and provide them with some information.
             * * * *
             */
            throw new CantReportCriticalStartingProblemException();
        }

    }

    /**
     * Method tha initialize all addons component
     */
    private void initializeAddons() throws CantStartPlatformException {

        LOG.info("Platform - initializing Addons ...");

        try {

            /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
             * ------------------------------------------------------------------------------------------------------------*
             * Critical Addons initialization                                                                              *
             * ------------------------------------------------------------------------------------------------------------*
             * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

            /*
             * Addon Error Manager
             * -------------------
             */
            Service errorManager = (Service) ((PlatformServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PLATFORM_SERVICE_LAYER)).getErrorManager();
            ((DealsWithPlatformDatabaseSystem) errorManager).setPlatformDatabaseSystem(databaseSystemOs.getPlatformDatabaseSystem());
            corePlatformContext.registerAddon((Addon) errorManager, Addons.ERROR_MANAGER);
            errorManager.start();


            /**
             * The event monitor is intended to handle exceptions on listeners, in order to take appropiate action.
             */
            PlatformFermatEventMonitor eventMonitor = new PlatformFermatEventMonitor((ErrorManager) errorManager);

            /*
             * Addon Event Manager
             * -------------------
             */
            Service eventManager = (Service) ((PlatformServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PLATFORM_SERVICE_LAYER)).getEventManager();
            ((DealsWithEventMonitor) eventManager).setFermatEventMonitor(eventMonitor);
            corePlatformContext.registerAddon((Addon) eventManager, Addons.EVENT_MANAGER);
            eventManager.start();


            /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
             * ------------------------------------------------------------------------------------------------------------*
             * Other addons initialization                                                                                 *
             * ------------------------------------------------------------------------------------------------------------*
             * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

            /*
             * Addon Remote Device Manager
             * ---------------------------
             *
             * Give the Remote Device Manager access to the File System so it can load and save user information from
             * persistent media.
             */
            Service remoteDevice = (Service) ((HardwareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_HARDWARE_LAYER)).getRemoteDeviceManager();
            ((DealsWithPlatformFileSystem) remoteDevice).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());
            ((DealsWithEvents) remoteDevice).setEventManager((EventManager) eventManager);
            corePlatformContext.registerAddon((Addon) remoteDevice, Addons.REMOTE_DEVICE);

            /*
             * Addon Local Device Manager
             * -----------------------------
             *
             * Give the Local Device Manager access to the File System so it can load and save user information from
             * persistent media.
             */
            Service localDevice = (Service) ((HardwareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_HARDWARE_LAYER)).getLocalDeviceManager();
            ((DealsWithPlatformFileSystem) localDevice).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());
            ((DealsWithEvents) localDevice).setEventManager((EventManager) eventManager);
            corePlatformContext.registerAddon((Addon) localDevice, Addons.LOCAL_DEVICE);

            /*
             * Addon User Manager
             * -----------------------------
             *
             * Give the User Manager access to the File System so it can load and save user information from
             * persistent media.
             */
            Service deviceUser = (Service) ((UserLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_USER_LAYER)).getDeviceUser();
            ((DealsWithPlatformFileSystem) deviceUser).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());
            ((DealsWithPlatformDatabaseSystem) deviceUser).setPlatformDatabaseSystem(databaseSystemOs.getPlatformDatabaseSystem());
            ((DealsWithEvents) deviceUser).setEventManager((EventManager) eventManager);
            ((DealsWithErrors) deviceUser).setErrorManager((ErrorManager) errorManager);
            corePlatformContext.registerAddon((Addon) deviceUser, Addons.DEVICE_USER);

             /*
             * Addon PlatformInfo
             * -----------------------------
             *
             * Give the PlatformInfo Manager access to the File System so it can load and save user information from
             * persistent media.
             */
            Service platformInfo = (Service) ((PlatformServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PLATFORM_SERVICE_LAYER)).getPlatformInfo();
            ((DealsWithPlatformFileSystem) platformInfo).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());

            corePlatformContext.registerAddon((Addon) platformInfo, Addons.PLATFORM_INFO);
            platformInfo.start();

        } catch (CantStartPluginException cantStartPluginException) {

            LOG.log(Level.SEVERE, cantStartPluginException.getLocalizedMessage());
            throw new CantStartPlatformException();
        }

    }

    /**
     * Method tha initialize all addons component
     */
    private void initializePlugins() throws CantStartPlatformException {

        LOG.info("Platform - initializing Plugins ...");

        try {

            /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
             * ------------------------------------------------------------------------------------------------------------*
             * Critical Plugins initialization                                                                             *
             * ------------------------------------------------------------------------------------------------------------*
             * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

            /*
             * PluginsIdentityManager
             * -----------------------
             *
             * Initialize the Plugin Identity Manager, the one who assigns identities to each plug in.
             */
            pluginsIdentityManager = new PluginsIdentityManager(fileSystemOs.getPlatformFileSystem());

            /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
             * ------------------------------------------------------------------------------------------------------------*
             * Other Plugins initialization                                                                                *
             * ------------------------------------------------------------------------------------------------------------*
             * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

            /*
             * Plugin Cloud Server Communication
             * -----------------------------
             */
            //Plugin cloudServerCommunication = ((CommunicationLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_COMMUNICATION_LAYER)).getCloudServerPlugin();
            //injectPluginReferencesAndStart(cloudServerCommunication, Plugins.BITDUBAI_CLOUD_SERVER_COMMUNICATION);

            /*
             * Plugin Cloud Client Communication
             * -----------------------------
             */
            //Plugin cloudCommunication = ((CommunicationLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_COMMUNICATION_LAYER)).getCloudPlugin();
            //injectPluginReferencesAndStart(cloudCommunication, Plugins.BITDUBAI_CLOUD_CHANNEL);


            /*
             * Plugin Web Socket Communication Cloud Server
             * -----------------------------
             */
            Plugin wsCommunicationCloudServer = ((CommunicationLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_COMMUNICATION_LAYER)).getWsCommunicationCloudServerPlugin();
            injectPluginReferencesAndStart(wsCommunicationCloudServer, Plugins.BITDUBAI_WS_COMMUNICATION_CLOUD_SERVER);

             /* flag temporal para desactivar plugins que tarde demasiado en inicializar,
            y asi poder trabajar en otras partes del sistema de forma relativamente rapida */
            boolean activatePlugin = true;
            if (activatePlugin) {
                /*
                 * Plugin Web Socket Communication Cloud Client
                 * -----------------------------
                 */
                Plugin wsCommunicationCloudClient = ((CommunicationLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_COMMUNICATION_LAYER)).getWsCommunicationCloudClientPlugin();
                injectPluginReferencesAndStart(wsCommunicationCloudClient, Plugins.BITDUBAI_WS_COMMUNICATION_CLIENT_CHANNEL);

                 /*
                 * Plugin CCP Crypto Payment Request Network Service
                 * -----------------------------
                 */
                Plugin cryptoPaymentRequestNetworkService = ((CCPNetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CCP_NETWORK_SERVICE_LAYER)).getCryptoPaymentRequestPlugin();
                injectLayerReferences(cryptoPaymentRequestNetworkService);
                injectPluginReferencesAndStart(cryptoPaymentRequestNetworkService, Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE);

                /*
                 * Plugin CCP Crypto Trasmission Network Service
                 * -----------------------------
                 */
                Plugin cryptoTransmissionNetworkService = ((CCPNetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CCP_NETWORK_SERVICE_LAYER)).getCryptoTransmissionPlugin();
                injectLayerReferences(cryptoTransmissionNetworkService);
                injectPluginReferencesAndStart(cryptoTransmissionNetworkService, Plugins.BITDUBAI_CCP_CRYPTO_CRYPTO_TRANSMISSION_NETWORK_SERVICE);

                /*
                 * Plugin Template Network Service
                 * -----------------------------
                 *
                Plugin templateNetworkService = ((NetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER)).getTemplate();
                injectLayerReferences(templateNetworkService);
                injectPluginReferencesAndStart(templateNetworkService, Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE);
                */
            }

//            /*
//            * Plugin Asset User Actor Network Service
//            * ----------------------------------------
//            */
//
//            Plugin assetUserActorNetworkService = ((DAPActorNetworServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_ACTOR_NETWORK_SERVICE_LAYER)).getAssetUserActorNetworService();
//            injectLayerReferences(assetUserActorNetworkService);
//            injectPluginReferencesAndStart(assetUserActorNetworkService, Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE);
//
//
//            /*
//            * Plugin Asset Transmission Network Service
//            * ----------------------------------------
//            */
//
//            Plugin assetTransmissionNetworkService = ((DAPNetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_NETWORK_SERVICE_LAYER)).getAssetTransmissionNetworService();
//            injectLayerReferences(assetTransmissionNetworkService);
//            injectPluginReferencesAndStart(assetTransmissionNetworkService, Plugins.BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE);


            /*
             * Plugin Blockchain Info World
             * -----------------------------
             */
            // Plugin blockchainInfoWorld = ((WorldLayer)  mWorldLayer).getBlockchainInfo();
            //injectLayerReferences(blockchainInfoWorld);
            // injectPluginReferencesAndStart(blockchainInfoWorld, Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD);

            /*
             * Plugin Shape Shift World
             * -----------------------------
             */
            // Plugin shapeShiftWorld = ((WorldLayer)  mWorldLayer).getShapeShift();
            //injectLayerReferences(shapeShiftWorld);
            // injectPluginReferencesAndStart(shapeShiftWorld, Plugins.BITDUBAI_SHAPE_SHIFT_WORLD);

            /*
             * Plugin Coinapult World
             * -----------------------------
             */
            // Plugin coinapultWorld = ((WorldLayer)  mWorldLayer).getCoinapult();
            //injectLayerReferences(coinapultWorld);
            // injectPluginReferencesAndStart(coinapultWorld, Plugins.BITDUBAI_COINAPULT_WORLD);

            /*
             * Plugin Coinbase World
             * -----------------------------
             */
            // Plugin coinbaseWorld = ((WorldLayer)  mWorldLayer).getCoinbase();
            //injectLayerReferences(coinbaseWorld);
            // injectPluginReferencesAndStart(coinbaseWorld, Plugins.BITDUBAI_COINBASE_WORLD);

            /*
             * Plugin Location World
             * -----------------------------
             */
            // Plugin locationWorld = ((WorldLayer)  mWorldLayer).getLocation();
            //injectLayerReferences(locationWorld);
            // injectPluginReferencesAndStart(locationWorld, Plugins.BITDUBAI_LOCATION_WORLD);

            /*
             * Plugin Crypto Index World
             * -----------------------------
             */
            // Plugin cryptoIndexWorld = ((WorldLayer)  mWorldLayer).getCryptoIndex();
            //injectLayerReferences(cryptoIndexWorld);
            // injectPluginReferencesAndStart(cryptoIndexWorld, Plugins.BITDUBAI_CRYPTO_INDEX);

            /*
             * Plugin Actor Developer
             * -----------------------------
             */
            Plugin actorDeveloper = ((ActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_ACTOR_LAYER)).getmActorDeveloper();
            injectPluginReferencesAndStart(actorDeveloper, Plugins.BITDUBAI_ACTOR_DEVELOPER);

            /*
             * Plugin Developer Module
             * -----------------------------
             */
            Plugin developerModule = ((com.bitdubai.fermat_core.layer.pip_module.ModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_MODULE_LAYER)).getmDeveloperModule();
            injectPluginReferencesAndStart(developerModule, Plugins.BITDUBAI_DEVELOPER_MODULE);

            /*
             * Plugin Extra User
             * -------------------------------
             */
            Plugin extraUser = ((ActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_ACTOR_LAYER)).getmActorExtraUser();
            injectPluginReferencesAndStart(extraUser, Plugins.BITDUBAI_ACTOR_EXTRA_USER);

             /*
             * Plugin Intra User PIP
             * -------------------------------
             */
            Plugin intraUser = ((ActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_ACTOR_LAYER)).getmActorIntraUser();
            injectPluginReferencesAndStart(intraUser, Plugins.BITDUBAI_USER_INTRA_USER);


           /*
             * Plugin Desktop runtime PIP
             * -----------------------------
             */
            Plugin desktopRuntimePlugin = ((EngineLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_ENGINE_LAYER)).getmDesktopRuntimePlugin();
            injectPluginReferencesAndStart(desktopRuntimePlugin, Plugins.BITDUBAI_DESKTOP_RUNTIME);

            /*
             * Plugin Bitcoin Crypto Network
             * -----------------------------
             */
            Plugin cryptoNetwork = ((CryptoNetworkLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CRYPTO_NETWORK_LAYER)).getCryptoNetwork(CryptoNetworks.BITCOIN);
            injectPluginReferencesAndStart(cryptoNetwork, Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK);

            Plugin cryptoNetwork2 = ((CryptoNetworkLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CRYPTO_NETWORK_LAYER)).getCryptoNetwork(CryptoNetworks.BITCOIN2);
            injectPluginReferencesAndStart(cryptoNetwork2, Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK2);

            /*
             *  Plugin Crypto Address Book         *
             * ------------------------------------*
             */
            Plugin cryptoAddressBookCrypto = ((CryptoLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CRYPTO_LAYER)).getCryptoAddressBook();
            injectPluginReferencesAndStart(cryptoAddressBookCrypto, Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK);

            /*
             * Plugin Bank Notes Network Service
             * -----------------------------
             */
            Plugin bankNotesNetworkService = ((NetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER)).getBankNotesPlugin();
            injectPluginReferencesAndStart(bankNotesNetworkService, Plugins.BITDUBAI_BANK_NOTES_NETWORK_SERVICE);

            /*
             * Plugin Wallet Resources Network Service
             * -----------------------------
             */
            Plugin walletResourcesNetworkService = ((WPDNetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_NETWORK_SERVICE_LAYER)).getWalletResources();
            injectPluginReferencesAndStart(walletResourcesNetworkService, Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE);

            /*
             * Plugin Wallet Community Network Service
             * -----------------------------
             */
            Plugin walletCommunityNetworkService = ((WPDNetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_NETWORK_SERVICE_LAYER)).getWalletCommunity();
            injectPluginReferencesAndStart(walletCommunityNetworkService, Plugins.BITDUBAI_WALLET_COMMUNITY_NETWORK_SERVICE);

            /*
             * Plugin Wallet Store Network Service
             * -----------------------------
             */
            Plugin walletStoreNetworkService = ((WPDNetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_NETWORK_SERVICE_LAYER)).getWalletStore();
            injectLayerReferences(walletStoreNetworkService);
            injectPluginReferencesAndStart(walletStoreNetworkService, Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE);

            /*
             * Plugin Wallet Statistics Network Service
             * -----------------------------
             */
            Plugin walletStatisticsNetworkService = ((WPDNetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_NETWORK_SERVICE_LAYER)).getWalletStatistics();
            injectPluginReferencesAndStart(walletStatisticsNetworkService, Plugins.BITDUBAI_WALLET_STATISTICS_NETWORK_SERVICE);

             /*
             * Plugin Crypto Addresses Network Service
             * -----------------------------
             */
            Plugin cryptoAddressesNetworkService = ((CCPNetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CCP_NETWORK_SERVICE_LAYER)).getCryptoAddressesPlugin();
            injectPluginReferencesAndStart(cryptoAddressesNetworkService, Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE);
            
            /*
             * Plugin App Runtime Middleware
             * -------------------------------
             */
            Plugin appRuntimeMiddleware = ((MiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER)).getAppRuntimePlugin();
            injectPluginReferencesAndStart(appRuntimeMiddleware, Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);

            /*
             * Plugin Bank Notes Middleware
             * -----------------------------
             */
            Plugin bankNotesMiddleware = ((MiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER)).getBankNotesPlugin();
            injectPluginReferencesAndStart(bankNotesMiddleware, Plugins.BITDUBAI_BANK_NOTES_MIDDLEWARE);

            /*
             * Plugin Bitcoin Wallet Basic Wallet
             * -----------------------------
             */
            Plugin bitcoinWalletBasicWallet = ((BasicWalletLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_BASIC_WALLET_LAYER)).getBitcoinWallet();
            injectPluginReferencesAndStart(bitcoinWalletBasicWallet, Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET);

            /*
             * Plugin Discount Wallet Basic Wallet
             * -----------------------------
             */
            //Plugin discountWalletBasicWallet = ((BasicWalletLayer) mBasicWalletLayer).getDiscountWallet();
            //injectPluginReferencesAndStart(discountWalletBasicWallet, Plugins.BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET);

            /*
             * Plugin Wallet Contacts Middleware
             * ----------------------------------
             */
            Plugin walletContactsMiddleware = ((CCPMiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CCP_MIDDLEWARE_LAYER)).getWalletContactsPlugin();
            injectPluginReferencesAndStart(walletContactsMiddleware, Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE);

            /*
             * Plugin Wallet Factory Middleware
             * ----------------------------------
             */
            Plugin walletFactoryMiddleware = ((WPDMiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_MIDDLEWARE_LAYER)).getWalletFactoryPlugin();
            injectPluginReferencesAndStart(walletFactoryMiddleware, Plugins.BITDUBAI_WPD_WALLET_FACTORY_MIDDLEWARE);


            /*
             * Plugin Wallet Manager Middleware
             * ----------------------------------
             */
            Plugin walletManagerMiddleware = ((WPDMiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_MIDDLEWARE_LAYER)).getWalletManagerPlugin();
            injectPluginReferencesAndStart(walletManagerMiddleware, Plugins.BITDUBAI_WPD_WALLET_MANAGER_MIDDLEWARE);


            /*
             * Plugin Wallet Publisher Middleware
             * ----------------------------------
             */
            Plugin walletPublisherMiddleware = ((WPDMiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_MIDDLEWARE_LAYER)).getWalletPublisherPlugin();
            injectPluginReferencesAndStart(walletPublisherMiddleware, Plugins.BITDUBAI_WPD_WALLET_PUBLISHER_MIDDLEWARE);

            /*
             * Plugin Wallet Store Middleware
             * ----------------------------------
             */

            Plugin walletStoreMiddleware = ((WPDMiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_MIDDLEWARE_LAYER)).getWalletStorePlugin();
            injectPluginReferencesAndStart(walletStoreMiddleware, Plugins.BITDUBAI_WPD_WALLET_STORE_MIDDLEWARE);

            /*
             * Plugin CCP Crypto Payment Request
             * ----------------------------------
             */
            Plugin cryptoPaymentRequest = ((CCPRequestLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CCP_REQUEST_LAYER)).getCryptoPaymentPlugin();
            injectPluginReferencesAndStart(cryptoPaymentRequest, Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST);

            /*
             * Plugin Wallet Store Module
             * ----------------------------------
             */

            Plugin walletStoreModule = ((WPDSubAppModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_SUB_APP_MODULE_LAYER)).getWalletStore();
            injectPluginReferencesAndStart(walletStoreModule, Plugins.BITDUBAI_WPD_WALLET_STORE_SUB_APP_MODULE);

            /*
             * Plugin Wallet factory
             * -----------------------------
             */
            Plugin walletFactoryModule = ((WPDSubAppModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_SUB_APP_MODULE_LAYER)).getWalletFactory();
            injectPluginReferencesAndStart(walletFactoryModule, Plugins.BITDUBAI_WPD_WALLET_FACTORY_SUB_APP_MODULE);

            /*
             * Plugin Wallet publisher Module
             * ----------------------------------
             */
            Plugin walletPublisherModule = ((WPDSubAppModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_SUB_APP_MODULE_LAYER)).getWalletPublisher();
            injectPluginReferencesAndStart(walletPublisherModule, Plugins.BITDUBAI_WPD_WALLET_PUBLISHER_SUB_APP_MODULE);

            /**
             *
             * Plugin notification
             *
             */
            Plugin notification = ((ModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MODULE_LAYER)).getNotification();
            injectPluginReferencesAndStart(notification, Plugins.BITDUBAI_MIDDLEWARE_NOTIFICATION);

              /*
             * Plugin Wallet Navigation Structure Middleware
             * ----------------------------------
             */
            Plugin walletNavigationStructureMiddleware = ((MiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER)).getWalletNavigationStructurePlugin();
            injectPluginReferencesAndStart(walletNavigationStructureMiddleware, Plugins.BITDUBAI_WALLET_NAVIGATION_STRUCTURE_MIDDLEWARE);

             /*
             * Plugin Wallet settings Middleware
             * ----------------------------------
             */
            Plugin walletSettingsMiddleware = ((WPDMiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_MIDDLEWARE_LAYER)).getWalletSettingsPlugin();
            injectPluginReferencesAndStart(walletSettingsMiddleware, Plugins.BITDUBAI_WPD_WALLET_SETTINGS_MIDDLEWARE);


//            /*
//             * Plugin Intra User Actor
//             * -----------------------------
//             */
//            Plugin intraUserActor = ((com.bitdubai.fermat_core.layer.dmp_actor.ActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_ACTOR_LAYER)).getActorIntraUser();
//            injectPluginReferencesAndStart(intraUserActor, Plugins.BITDUBAI_INTRA_USER_ACTOR);


            /*
             * Plugin Bitcoin Crypto Vault
             * ----------------------------------
             */
            Plugin bitcoinCryptoVault = ((CryptoVaultLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CRYPTO_VAULT_LAYER)).getmBitcoin();
            injectPluginReferencesAndStart(bitcoinCryptoVault, Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT);

            /*
             * Plugin Assets Crypto Vault
             * ----------------------------------
             */
            Plugin assetsCryptoVault = ((CryptoVaultLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CRYPTO_VAULT_LAYER)).getmAssetsVault();
            injectPluginReferencesAndStart(assetsCryptoVault, Plugins.BITDUBAI_ASSETS_CRYPTO_VAULT);

            /*
             * Plugin Incoming Crypto Crypto Router
             * ----------------------------------
             */
            Plugin incomingCryptoTransaction = ((CryptoRouterLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CRYPTO_ROUTER_LAYER)).getIncomingCrypto();
            injectPluginReferencesAndStart(incomingCryptoTransaction, Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION);

            /*
             * Plugin Incoming Extra User Transaction
             * ----------------------------------
             */
            Plugin incomingExtraUserTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getIncomingExtraUserPlugin();
            injectPluginReferencesAndStart(incomingExtraUserTransaction, Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION);
            //System.out.println("EXTRA USER START SUCCESS");

            /*
             * Plugin Intra User Identity
             * -----------------------------
             */
            Plugin ccpIntraWalletUserIdentity = ((CCPIdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CCP_IDENTITY_LAYER)).getIntraWalletUserPlugin();
            injectPluginReferencesAndStart(ccpIntraWalletUserIdentity, Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY);

            /*
             * Plugin Incoming Intra User Transaction
             * ----------------------------------
             */
            Plugin incomingIntraUserTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getIncomingIntraUserPlugin();
            injectPluginReferencesAndStart(incomingIntraUserTransaction, Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION);

            /*
             * Plugin Inter Wallet Transaction
             * ----------------------------------
             */
            Plugin interWalletTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getInterWalletPlugin();
            injectPluginReferencesAndStart(interWalletTransaction, Plugins.BITDUBAI_INTER_WALLET_TRANSACTION);

            /*
             * Plugin Outgoing Extra User Transaction
             * ----------------------------------
             */
            Plugin outgoingExtraUserTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getOutgoingExtraUserPlugin();
            injectPluginReferencesAndStart(outgoingExtraUserTransaction, Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION);

            /*
             * Plugin Outgoing Device user Transaction
             * ----------------------------------
             */
            Plugin outgoingDeviceUserTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getOutgoingDeviceUserPlugin();
            injectPluginReferencesAndStart(outgoingDeviceUserTransaction, Plugins.BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION);

            /*
             * Plugin CCP Outgoing Intra Actor Transaction
             * ----------------------------------
             */
            Plugin outgoingIntraActorPlugin = ((CCPTransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CCP_TRANSACTION_LAYER)).getOutgoingIntraActorPlugin();
            injectPluginReferencesAndStart(outgoingIntraActorPlugin, Plugins.BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION);

            /*
             * Plugin Incoming Device user Transaction
             * ----------------------------------
             */
            Plugin incomingDeviceUserTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getIncomingDeviceUserPlugin();
            injectPluginReferencesAndStart(incomingDeviceUserTransaction, Plugins.BITDUBAI_INCOMING_DEVICE_USER_TRANSACTION);

            /*
             * Plugin Crypto Loss Protected Wallet Niche Type Wallet
             * ----------------------------------
             */
            //TODO lo comente porque la variable cryptoLossProtectedWalletWalletModule es null y da error al inicializar la APP (Natalia)
            //Plugin cryptoLossProtectedWalletWalletModule = ((WalletModuleLayer) mWalletModuleLayer).getmCryptoLossProtectedWallet();
            //injectPluginReferencesAndStart(cryptoLossProtectedWalletWalletModule, Plugins.BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE);

            /*
             * Plugin crypto Wallet Niche Type Wallet
             * ----------------------------------
             */
            Plugin cryptoWalletWalletModule = ((WalletModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WALLET_MODULE_LAYER)).getmCryptoWallet();
            injectPluginReferencesAndStart(cryptoWalletWalletModule, Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE);

             /*
             * Plugin Discount Wallet Niche Type Wallet
             * ----------------------------------
             */
            Plugin discountWalletWalletModule = ((WalletModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WALLET_MODULE_LAYER)).getmDiscountWallet();
            injectPluginReferencesAndStart(discountWalletWalletModule, Plugins.BITDUBAI_DISCOUNT_WALLET_WALLET_MODULE);

            /*
             * Plugin Fiat Over Crypto Loss Protected Wallet Wallet Niche Type Wallet
             * ----------------------------------
             */
            //TODO lo comente porque la variable fiatOverCryptoLossProtectedWalletWalletModule es null  y da error al levantar la APP (Natalia)
            //Plugin fiatOverCryptoLossProtectedWalletWalletModule = ((WalletModuleLayer) mWalletModuleLayer).getmFiatOverCryptoLossProtectedWallet();
            //injectPluginReferencesAndStart(fiatOverCryptoLossProtectedWalletWalletModule, Plugins.BITDUBAI_FIAT_OVER_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE);

            /*
             * Plugin Multi account Wallet Niche Type Wallet
             * ----------------------------------
             */
            Plugin multiAccountWalletWalletModule = ((WalletModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WALLET_MODULE_LAYER)).getmMultiAccountWallet();
            injectPluginReferencesAndStart(multiAccountWalletWalletModule, Plugins.BITDUBAI_MULTI_ACCOUNT_WALLET_WALLET_MODULE);

            /*
             * Plugin Bank Notes Wallet Niche Type Wallet
             * ----------------------------------
             */
            Plugin bankNotesWalletWalletModule = ((WalletModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WALLET_MODULE_LAYER)).getmBankNotesWallet();
            injectPluginReferencesAndStart(bankNotesWalletWalletModule, Plugins.BITDUBAI_BANK_NOTES_WALLET_WALLET_MODULE);

            /*
             * Plugin Wallet Manager
             * -----------------------------
             */
            Plugin walletManager = ((WPDDesktopModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_DESKTOP_MODULE_LAYER)).getWalletManager();
            injectPluginReferencesAndStart(walletManager, Plugins.BITDUBAI_WPD_WALLET_MANAGER_DESKTOP_MODULE);

            /*
             * Plugin Wallet Runtime
             * -----------------------------
             */
            Plugin walletRuntime = ((ModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MODULE_LAYER)).getWalletRuntime();
            injectPluginReferencesAndStart(walletRuntime, Plugins.BITDUBAI_WALLET_RUNTIME_MODULE);

            /*
             * Plugin Wallet Runtime
             * -----------------------------
             */
            Plugin subAppResourcesNetworkService = ((com.bitdubai.fermat_core.layer.pip_network_service.NetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_NETWORK_SERVICE_LAYER)).getSubAppResources();
            injectPluginReferencesAndStart(subAppResourcesNetworkService, Plugins.BITDUBAI_SUBAPP_RESOURCES_NETWORK_SERVICE);

            /**
             * Plugin Crypto Loss Protected Wallet Niche Type Wallet
             * ----------------------------------
             */
            //TODO lo comente porque la variable cryptoLossProtectedWalletWalletModule es null y da error al inicializar la APP (Natalia)
            //Plugin cryptoLossProtectedWalletWalletModule = ((WalletModuleLayer) mWalletModuleLayer).getmCryptoLossProtectedWallet();
            //injectPluginReferencesAndStart(cryptoLossProtectedWalletWalletModule, Plugins.BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE);

            /*
             * Plugin Wallet factory
             * -----------------------------
             */
            //  Plugin walletFactoryModule =  ((ModuleLayer) mModuleLayer).getWalletFactory();
            //  injectPluginReferencesAndStart(walletFactoryModule, Plugins.BITDUBAI_WPD_WALLET_FACTORY_SUB_APP_MODULE);

            if (activatePlugin) {
                 /*
                 * Plugin Intra User NetWorkService
                 * -----------------------------
                 */
                Plugin intraUserNetworkService = ((NetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER)).getIntraUser();
                injectLayerReferences(intraUserNetworkService);
                injectPluginReferencesAndStart(intraUserNetworkService, Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE);
            }

            /*
             * Plugin Intra User Actor
             * -----------------------------
             */
            Plugin intraUserActor = ((CCPActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CCP_ACTOR_LAYER)).getIntraWalletUserPlugin();
            injectPluginReferencesAndStart(intraUserActor, Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_ACTOR);


            /*
             * Plugin Developer Identity
             * -----------------------------
             */
            Plugin developerIdentity = ((com.bitdubai.fermat_core.layer.pip_Identity.IdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_IDENTITY_LAYER)).getDeveloperIdentity();
            injectPluginReferencesAndStart(developerIdentity, Plugins.BITDUBAI_DEVELOPER_IDENTITY);

            /*
             * Plugin Publisher Identity
             * -----------------------------
             */
            Plugin publisherIdentity = ((WPDIdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_IDENTITY_LAYER)).getPublisherIdentity();
            injectPluginReferencesAndStart(publisherIdentity, Plugins.BITDUBAI_WPD_PUBLISHER_IDENTITY);

            /*
             * Plugin Translator Identity
             * -----------------------------
             */
            Plugin translatorIdentity = ((IdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_IDENTITY_LAYER)).getTranslatorIdentity();
            injectPluginReferencesAndStart(translatorIdentity, Plugins.BITDUBAI_TRANSLATOR_IDENTITY);


            /*
             * Plugin Designer Identity
             * -----------------------------
             */
            Plugin designerIdentity = ((IdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_IDENTITY_LAYER)).getDesignerIdentity();
            injectPluginReferencesAndStart(designerIdentity, Plugins.BITDUBAI_DESIGNER_IDENTITY);



            /*
             * Plugin Intra User Module
             * -----------------------------
             */
            Plugin intraUserModule = ((ModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MODULE_LAYER)).getIntraUser();
            injectPluginReferencesAndStart(intraUserModule, Plugins.BITDUBAI_INTRA_USER_FACTORY_MODULE);

            /*
             * Plugin Request
             * -----------------------------
             */
            Plugin moneyRequest = ((RequestServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_REQUEST_LAYER)).getMoney();
            injectPluginReferencesAndStart(moneyRequest, Plugins.BITDUBAI_REQUEST_MONEY_REQUEST);

            if(true) {
                /*
            * Plugin Asset User Actor Network Service
            * ----------------------------------------
            */

                Plugin assetUserActorNetworkService = ((DAPActorNetworServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_ACTOR_NETWORK_SERVICE_LAYER)).getAssetUserActorNetworService();
                injectLayerReferences(assetUserActorNetworkService);
                injectPluginReferencesAndStart(assetUserActorNetworkService, Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE);


           /*
            * Plugin Asset Transmission Network Service
            * ----------------------------------------
            */

                Plugin assetTransmissionNetworkService = ((DAPNetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_NETWORK_SERVICE_LAYER)).getAssetTransmissionNetworService();
                injectLayerReferences(assetTransmissionNetworkService);
                injectPluginReferencesAndStart(assetTransmissionNetworkService, Plugins.BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE);

               /*
                * Plugin Asset Wallet Asset Issuer
                * -----------------------------
                */
                Plugin assetWalletIssuer = ((DAPWalletLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_WALLET_LAYER)).getPluginAssetWalletIssuer();
                injectPluginReferencesAndStart(assetWalletIssuer, Plugins.BITDUBAI_ASSET_WALLET_ISSUER);
           /*
            * Plugin Asset Wallet Asset Issuer
            * -----------------------------
            */
                Plugin assetWalletUser = ((DAPWalletLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_WALLET_LAYER)).getPluginAssetWalletUser();
                injectPluginReferencesAndStart(assetWalletUser, Plugins.BITDUBAI_DAP_ASSET_USER_WALLET);

                /*
                 * Plugin Asset Issuing Transaction
                 * -----------------------------
                 */
                Plugin assetIssuingTransaction = ((DAPTransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_TRANSACTION_LAYER)).getAssetIssuingPlugin();
                injectPluginReferencesAndStart(assetIssuingTransaction, Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION);

                /*
                 * Plugin Asset Distribution Transaction
                 * -----------------------------
                 */
                Plugin assetDistributionTransaction = ((DAPTransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_TRANSACTION_LAYER)).getAssetDistributionPlugin();
                injectPluginReferencesAndStart(assetDistributionTransaction, Plugins.BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION);

                /*
                 * Plugin Asset Reception Transaction
                 * -----------------------------
                 */
                Plugin assetReceptionTransaction = ((DAPTransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_TRANSACTION_LAYER)).getAssetDistributionPlugin();
                injectPluginReferencesAndStart(assetReceptionTransaction, Plugins.BITDUBAI_ASSET_RECEPTION_TRANSACTION);

               /*
                * Plugin Asset Issuer Actor Layer
                * -------------------------
                */
                Plugin assetIssuerActorLayer = ((DAPActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_ACTOR_LAYER)).getAssetIssuerActor();
                injectPluginReferencesAndStart(assetIssuerActorLayer, Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR);

               /*
                * Plugin Asset User Actor Layer
                * -------------------------
                */
                Plugin assetUserActorLayer = ((DAPActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_ACTOR_LAYER)).getAssetUserActor();
                injectPluginReferencesAndStart(assetUserActorLayer, Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR);

               /*
                * Plugin Redeem Point Actor Layer
                * -------------------------
                */
                Plugin redeemPointActorLayer = ((DAPActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_ACTOR_LAYER)).getRedeemPointActor();
                injectPluginReferencesAndStart(redeemPointActorLayer, Plugins.BITDUBAI_DAP_REDEEM_POINT_ACTOR);

           /*
            * Plugin Asset Issuer Identity Layer
            * -------------------------
            */
                Plugin assetIssuerIdentityLayer = ((DAPIdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_IDENTITY_LAYER)).getAssetIssuerIdentity();
                injectPluginReferencesAndStart(assetIssuerIdentityLayer, Plugins.BITDUBAI_DAP_ASSET_ISSUER_IDENTITY);

               /*
                * Plugin Asset User Identity Layer
                * -------------------------
                */
                Plugin assetUserIdentityLayer = ((DAPIdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_IDENTITY_LAYER)).getAssetUserIdentity();
                injectPluginReferencesAndStart(assetUserIdentityLayer, Plugins.BITDUBAI_DAP_ASSET_USER_IDENTITY);

               /*
                * Plugin Redeem Point Identity Layer
                * -------------------------
                */
                Plugin redeemPointIdentityLayer = ((DAPIdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_IDENTITY_LAYER)).getRedeemPointIdentity();
                injectPluginReferencesAndStart(redeemPointIdentityLayer, Plugins.BITDUBAI_DAP_REDEEM_POINT_IDENTITY);

                /*
                 * Plugin Asset Factory Middleware
                 * -----------------------------
                 */
                Plugin assetFactortMiddleware = ((DAPMiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_MIDDLEWARE_LAYER)).getPluginAssetFactory();
                injectPluginReferencesAndStart(assetFactortMiddleware, Plugins.BITDUBAI_ASSET_FACTORY);

                 /*
                 * Plugin Asset Factory Module
                 * -----------------------------
                 */
                Plugin assetFactoryModlue = ((DAPModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_MODULE_LAYER)).getPluginAssetFactoryModule();
                injectPluginReferencesAndStart(assetFactoryModlue, Plugins.BITDUBAI_ASSET_FACTORY_MODULE);

                 /*
                 * Plugin Asset Issuer Wallet Module
                 * -----------------------------
                 */
                Plugin assetIssuerWalletModule = ((DAPModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_MODULE_LAYER)).getPluginModuleAssetIssuerWallet();
                injectPluginReferencesAndStart(assetIssuerWalletModule, Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE);
            }

        } catch (CantInitializePluginsManagerException cantInitializePluginsManagerException) {

            LOG.log(Level.SEVERE, cantInitializePluginsManagerException.getLocalizedMessage());
            throw new CantStartPlatformException();
        }


        List<Map.Entry<Plugins, Long>> list = new LinkedList<>(pluginsStartUpTime.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Plugins, Long>>() {
            public int compare(Map.Entry<Plugins, Long> o1, Map.Entry<Plugins, Long> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        System.out.println("************************************************************************");
        System.out.println("---- Lista de Tiempos de Start-Up de Plugins order by Start-Up time ----");
        System.out.println("************************************************************************");
        for (Map.Entry<Plugins, Long> entry : list) {
            System.out.println(entry.getKey().toString() + " - Start-Up time: " + entry.getValue() / 1000 + " seconds.");
        }
        System.out.println("************************************************************************");

    }

    Map<Plugins, Long> pluginsStartUpTime = new HashMap<>();

    /**
     * Method that inject all references to another plugin that required a
     * plugin to work
     *
     * @param plugin     whom I'll start
     * @param descriptor descriptor of this plugin
     */
    private void injectPluginReferencesAndStart(Plugin plugin, Plugins descriptor) {

        long init = System.currentTimeMillis();

        /*
         * Get the error manager instance
         */
        ErrorManager errorManager = (ErrorManager) corePlatformContext.getAddon(Addons.ERROR_MANAGER);

        try {
            if (plugin instanceof DealsWithBitcoinWallet) {
                ((DealsWithBitcoinWallet) plugin).setBitcoinWalletManager((BitcoinWalletManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET));
            }

            if (plugin instanceof DealsWithBitcoinCryptoNetwork) {
                ((DealsWithBitcoinCryptoNetwork) plugin).setBitcoinCryptoNetworkManager((BitcoinCryptoNetworkManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK));
            }

            if (plugin instanceof DealsWithBitcoinNetwork) {
                ((DealsWithBitcoinNetwork) plugin).setBitcoinNetworkManager((BitcoinNetworkManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK2));
            }

            if (plugin instanceof DealsWithCryptoVault) {
                ((DealsWithCryptoVault) plugin).setCryptoVaultManager((CryptoVaultManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT));
            }

            if (plugin instanceof DealsWithAssetVault) {
                ((DealsWithAssetVault) plugin).setAssetVaultManager((AssetVaultManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_ASSETS_CRYPTO_VAULT));
            }

            if (plugin instanceof DealsWithDeveloperModule) {
                ((DealsWithDeveloperModule) plugin).setDeveloperModuleManager((DeveloperModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DEVELOPER_MODULE));
            }

            if (plugin instanceof DealsWithErrors) {
                ((DealsWithErrors) plugin).setErrorManager(errorManager);
            }

            if (plugin instanceof DealsWithEvents) {
                ((DealsWithEvents) plugin).setEventManager((EventManager) corePlatformContext.getAddon(Addons.EVENT_MANAGER));
            }

            if (plugin instanceof DealsWithExtraUsers) {
                ((DealsWithExtraUsers) plugin).setExtraUserManager((ExtraUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_ACTOR_EXTRA_USER));
            }

            if (plugin instanceof DealsWithLogger) {
                ((DealsWithLogger) plugin).setLogManager(loggerSystemOs.getLoggerManager());
            }

            if (plugin instanceof DealsWithDeviceLocation) {
                ((DealsWithDeviceLocation) plugin).setLocationManager(locationSystemOs.getLocationSystem());
            }

            if (plugin instanceof DealsWithWalletModuleCryptoWallet) {
                ((DealsWithWalletModuleCryptoWallet) plugin).setWalletModuleCryptoWalletManager((CryptoWalletManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE));
            }

            if (plugin instanceof DealsWithOutgoingExtraUser) {
                ((DealsWithOutgoingExtraUser) plugin).setOutgoingExtraUserManager((OutgoingExtraUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION));
            }

            if (plugin instanceof DealsWithPluginFileSystem) {
                ((DealsWithPluginFileSystem) plugin).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
            }

            if (plugin instanceof DealsWithPluginDatabaseSystem) {
                ((DealsWithPluginDatabaseSystem) plugin).setPluginDatabaseSystem(databaseSystemOs.getPluginDatabaseSystem());
            }

            if (plugin instanceof DealsWithToolManager) {
                ((DealsWithToolManager) plugin).setToolManager((ToolManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_ACTOR_DEVELOPER));
            }

            if (plugin instanceof DealsWithCryptoAddressBook) {
                ((DealsWithCryptoAddressBook) plugin).setCryptoAddressBookManager((CryptoAddressBookManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK));
            }

            if (plugin instanceof DealsWithWalletContacts) {
                ((DealsWithWalletContacts) plugin).setWalletContactsManager((WalletContactsManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE));
            }

            if (plugin instanceof DealsWithWalletFactory) {
                ((DealsWithWalletFactory) plugin).setWalletFactoryProjectManager((WalletFactoryProjectManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WPD_WALLET_FACTORY_MIDDLEWARE));
            }

            if (plugin instanceof DealsWithWalletManager) {
                ((DealsWithWalletManager) plugin).setWalletManagerManager((WalletManagerManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WPD_WALLET_MANAGER_MIDDLEWARE));
            }

            if (plugin instanceof DealsWithWalletPublisherModule) {
                ((DealsWithWalletPublisherModule) plugin).setWalletPublisherManager((WalletPublisherModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WPD_WALLET_PUBLISHER_SUB_APP_MODULE));
            }

            if (plugin instanceof DealsWithWalletResources) {
                ((DealsWithWalletResources) plugin).setWalletResourcesManager((WalletResourcesInstalationManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE));
            }

            if (plugin instanceof DealsWithWalletStatisticsNetworkService) {
                ((DealsWithWalletStatisticsNetworkService) plugin).setWalletStatisticsManager((WalletStatisticsManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_STATISTICS_NETWORK_SERVICE));
            }

            if (plugin instanceof DealsWithWalletStoreMiddleware) {
                ((DealsWithWalletStoreMiddleware) plugin).setWalletStoreManager((WalletStoreManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WPD_WALLET_STORE_MIDDLEWARE));
            }

            if (plugin instanceof DealsWithWalletStoreNetworkService) {
                ((DealsWithWalletStoreNetworkService) plugin).setWalletStoreManager((com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletStoreManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE));
            }

            if (plugin instanceof DealsWithIncomingCrypto) {
                ((DealsWithIncomingCrypto) plugin).setIncomingCryptoManager((IncomingCryptoManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION));
            }
            if (plugin instanceof DealsWithDeviceUser) {
                ((DealsWithDeviceUser) plugin).setDeviceUserManager((DeviceUserManager) corePlatformContext.getAddon(Addons.DEVICE_USER));
            }
            if (plugin instanceof DealsWithWalletStoreModule) {
                ((DealsWithWalletStoreModule) plugin).setWalletStoreModuleManager((WalletStoreModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WPD_WALLET_STORE_SUB_APP_MODULE));
            }

            if (plugin instanceof DealsWithPlatformInfo) {
                ((DealsWithPlatformInfo) plugin).setPlatformInfoManager((PlatformInfoManager) corePlatformContext.getAddon(Addons.PLATFORM_INFO));
            }

            if (plugin instanceof DealsWithWalletPublisherMiddlewarePlugin) {
                ((DealsWithWalletPublisherMiddlewarePlugin) plugin).setWalletPublisherMiddlewarePlugin((WalletPublisherMiddlewarePlugin) corePlatformContext.getPlugin(Plugins.BITDUBAI_WPD_WALLET_PUBLISHER_MIDDLEWARE));
            }

            if (plugin instanceof DealsWithCCPIntraWalletUsers) {
                ((DealsWithCCPIntraWalletUsers) plugin).setIntraWalletUserManager((IntraWalletUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_ACTOR));
            }

            if (plugin instanceof DealsWithIntraUsersModule) {
                ((DealsWithIntraUsersModule) plugin).setIntraUserModuleManager((IntraUserModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_INTRA_USER_FACTORY_MODULE));
            }

            if (plugin instanceof DealsWithIdentityDesigner) {
                ((DealsWithIdentityDesigner) plugin).setDesignerIdentityManager((DesignerIdentityManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DESIGNER_IDENTITY));
            }

            if (plugin instanceof DealsWithCCPIntraWalletUser) {
                ((DealsWithCCPIntraWalletUser) plugin).setIntraUserManager((com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY));
            }

            if (plugin instanceof DealsWithDeveloperIdentity) {
                ((DealsWithDeveloperIdentity) plugin).setDeveloperIdentityManager((DeveloperIdentityManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DEVELOPER_IDENTITY));
            }

            if (plugin instanceof DealsWithPublisherIdentity) {
                ((DealsWithPublisherIdentity) plugin).setPublisherIdentityManager((PublisherIdentityManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WPD_PUBLISHER_IDENTITY));
            }

            if (plugin instanceof DealsWithIdentityTranslator) {
                ((DealsWithIdentityTranslator) plugin).setTranslatorIdentityManager((TranslatorIdentityManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_TRANSLATOR_IDENTITY));
            }

            if (plugin instanceof DealsWithIntraUsersNetworkService) {
                ((DealsWithIntraUsersNetworkService) plugin).setIntraUserNetworkServiceManager((IntraUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE));
            }

            if (plugin instanceof DealsWithWalletSettings) {
                ((DealsWithWalletSettings) plugin).setWalletSettingsManager((WalletSettingsManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WPD_WALLET_SETTINGS_MIDDLEWARE));
            }

            if (plugin instanceof DealsWithAssetIssuing) {
                ((DealsWithAssetIssuing) plugin).setAssetIssuingManager((AssetIssuingManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION));
            }

            if (plugin instanceof DealsWithAssetDistribution) {
                ((DealsWithAssetDistribution) plugin).setAssetDistributionManager((AssetDistributionManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION));
            }

            if (plugin instanceof DealsWithAssetReception) {
                ((DealsWithAssetReception) plugin).setAssetReceptionManager((AssetReceptionManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_ASSET_RECEPTION_TRANSACTION));
            }

            if (plugin instanceof DealsWithActorAssetIssuer) {
                ((DealsWithActorAssetIssuer) plugin).setActorAssetIssuerManager((ActorAssetIssuerManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR));
            }

            if (plugin instanceof DealsWithActorAssetUser) {
                ((DealsWithActorAssetUser) plugin).setActorAssetUserManager((ActorAssetUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR));
            }

            if (plugin instanceof DealsWithActorAssetRedeemPoint) {
                ((DealsWithActorAssetRedeemPoint) plugin).setActorAssetRedeemPointManager((ActorAssetRedeemPointManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_REDEEM_POINT_ACTOR));
            }

            if (plugin instanceof DealsWithIdentityAssetIssuer) {
                ((DealsWithIdentityAssetIssuer) plugin).setIdentityAssetIssuerManager((IdentityAssetIssuerManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_ISSUER_IDENTITY));
            }

            if (plugin instanceof DealsWithIdentityAssetUser) {
                ((DealsWithIdentityAssetUser) plugin).setIdentityAssetUserManager((IdentityAssetUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_USER_IDENTITY));
            }

            if (plugin instanceof DealsWithAssetUserActorNetworkServiceManager) {
                ((DealsWithAssetUserActorNetworkServiceManager) plugin).setAssetUserActorNetworkServiceManager((AssetUserActorNetworkServiceManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE));
            }

            if (plugin instanceof DealsWithAssetTransmissionNetworkServiceManager) {
                ((DealsWithAssetTransmissionNetworkServiceManager) plugin).setAssetTransmissionNetworkServiceManager((AssetTransmissionNetworkServiceManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE));
            }

            if (plugin instanceof DealsWithIdentityAssetRedeemPoint) {
                ((DealsWithIdentityAssetRedeemPoint) plugin).setRedeemPointIdentityManager((RedeemPointIdentityManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_REDEEM_POINT_IDENTITY));
            }

            if (plugin instanceof DealsWithAssetFactory) {
                ((DealsWithAssetFactory) plugin).setAssetFactoryManager((AssetFactoryManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_ASSET_FACTORY));
            }
            if (plugin instanceof DealsWithModuleAseetFactory) {
                ((DealsWithModuleAseetFactory) plugin).setAssetFactoryModuleManager((AssetFactoryModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_ASSET_FACTORY_MODULE));
            }
            if (plugin instanceof DealsWithAssetIssuerWalletSubAppModule) {
                ((DealsWithAssetIssuerWalletSubAppModule) plugin).setWalletAssetIssuerManager((AssetIssuerWalletSupAppModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE));
            }
            if (plugin instanceof DealsWithAssetIssuerWallet) {
                ((DealsWithAssetIssuerWallet) plugin).setAssetIssuerManager((AssetIssuerWalletManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_ASSET_WALLET_ISSUER));
            }
            if (plugin instanceof DealsWithAssetUserWallet) {
                ((DealsWithAssetUserWallet) plugin).setAssetUserManager((AssetUserWalletManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET));
            }

            if (plugin instanceof DealsWithWsCommunicationsCloudClientManager) {
                ((DealsWithWsCommunicationsCloudClientManager) plugin).setWsCommunicationsCloudClientConnectionManager((WsCommunicationsCloudClientManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WS_COMMUNICATION_CLIENT_CHANNEL));
            }
            if (plugin instanceof DealsWithOutgoingIntraActor) {
                ((DealsWithOutgoingIntraActor) plugin).setOutgoingIntraActorManager((OutgoingIntraActorManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION));
            }

            if (plugin instanceof DealsWithCryptoAddressesNetworkService) {
                ((DealsWithCryptoAddressesNetworkService) plugin).setCryptoAddressesManager((CryptoAddressesManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE));
            }

            if (plugin instanceof DealsWithCryptoPayment) {
                ((DealsWithCryptoPayment) plugin).setCryptoPaymentManager((CryptoPaymentManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST));
            }

            if (plugin instanceof DealsWithCryptoPaymentRequestNetworkService) {
                ((DealsWithCryptoPaymentRequestNetworkService) plugin).setCryptoPaymentRequestManager((CryptoPaymentRequestManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE));
            }

            if (plugin instanceof DealsWithCryptoTransmissionNetworkService) {
                ((DealsWithCryptoTransmissionNetworkService) plugin).setCryptoTransmissionNetworkService((CryptoTransmissionNetworkServiceManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CCP_CRYPTO_CRYPTO_TRANSMISSION_NETWORK_SERVICE));
            }

            /*
             * Register the plugin into the platform context
             */
            corePlatformContext.registerPlugin(plugin, descriptor);

            /*
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */
            plugin.setId(pluginsIdentityManager.getPluginId(descriptor));

            /*
             * Start the plugin service
             */
            ((Service) plugin).start();


        } catch (CantStartPluginException cantStartPluginException) {

            /**
             * This plugin wont disable the whole platform, so I will allow the Platform to start even if this one
             * will be disabled. In the future, I will re-try the start of plugins that are not starting at once.
             */
            errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, cantStartPluginException);

        } catch (PluginNotRecognizedException pluginNotRecognizedException) {

            /**
             * This plugin wont disable the whole platform, so I will allow the Platform to start even if this one
             * will be disabled. In the future, I will re-try the start of plugins that are not starting at once.
             */
            errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, pluginNotRecognizedException);

        } catch (Exception e) {

            /**
             * This plugin wont disable the whole platform, so I will allow the Platform to start even if this one
             * will be disabled. In the future, I will re-try the start of plugins that are not starting at once.
             */
            errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
        }

        long end = System.currentTimeMillis();

        pluginsStartUpTime.put(descriptor, end - init);
    }

    /**
     * This method is responsible to inject PlatformLayer referent object, since in special cases some plugin interact
     * directly with a layer instance. For example in the case of Network Services
     * <p/>
     * NOTE: This method should always call before @see Platform#injectPluginReferencesAndStart(Plugin, Plugins)
     * always and when it is required by the plugin
     *
     * @param plugin
     */
    private void injectLayerReferences(Plugin plugin) throws CantStartPlatformException {

        try {

            /*
             * Validate if this plugin required the Communication Layer instance
             */
            if (plugin instanceof DealsWithCommunicationLayerManager) {

                /*
                 * Inject the instance
                 */
                ((DealsWithCommunicationLayerManager) plugin).setCommunicationLayerManager((CommunicationLayerManager) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_COMMUNICATION_LAYER));

            }


        } catch (Exception exception) {
            LOG.log(Level.SEVERE, exception.getLocalizedMessage());
            throw new CantStartPlatformException();
        }
    }

    /**
     * Method that check addons for developer interfaces
     *
     * @param descriptor
     */
    private void checkAddonForDeveloperInterfaces(final Addons descriptor) {

        /*
         * Get the addon by description
         */
        Addon addon = corePlatformContext.getAddon(descriptor);

        /*
         * Validate is not null
         */
        if (addon == null) {
            return;
        }

        /*
         * Validate if is instance of DatabaseManagerForDevelopers
         */
        if (addon instanceof DatabaseManagerForDevelopers) {

            /*
             * Put into dealsWithDatabaseManagersAddons
             */
            dealsWithDatabaseManagersAddons.put(descriptor, addon);
        }

        /*
         * Validate if is instance of LogManagerForDevelopers
         */
        if (addon instanceof LogManagerForDevelopers) {

            /*
             * Put into dealsWithLogManagersAddons
             */
            dealsWithLogManagersAddons.put(descriptor, addon);
        }
    }

    /**
     * Method that check plugin for developer interfaces
     *
     * @param descriptor
     */
    private void checkPluginForDeveloperInterfaces(final Plugins descriptor) {

        /*
         * Get the plugin by description
         */
        Plugin plugin = corePlatformContext.getPlugin(descriptor);

        /*
         * Validate is not null
         */
        if (plugin == null) {
            return;
        }

        /*
         * Validate if is instance of DatabaseManagerForDevelopers
         */
        if (plugin instanceof DatabaseManagerForDevelopers) {

            /*
             * Put into dealsWithDatabaseManagersPlugins
             */
            dealsWithDatabaseManagersPlugins.put(descriptor, plugin);
        }

        /*
         * Validate if is instance of LogManagerForDevelopers
         */
        if (plugin instanceof LogManagerForDevelopers) {

            /*
             * Put into dealsWithDatabaseManagersPlugins
             */
            dealsWithLogManagersPlugins.put(descriptor, plugin);
        }

    }

    /**
     * Get the CorePlatformContext
     *
     * @return CorePlatformContext
     */
    public CorePlatformContext getCorePlatformContext() {
        return corePlatformContext;
        // Luis: TODO: Este metodo debe ser removido y lo que se debe devolver es un context con referencias a los plugins que la interfaz grafica puede acceder, no a todos los que existen como esta ahora mismo.
    }

    /**
     * Get the plugin reference from the context
     *
     * @param key
     * @return Plugin
     */
    public Plugin getPlugin(Plugins key) {
        return corePlatformContext.getPlugin(key);
    }
}
