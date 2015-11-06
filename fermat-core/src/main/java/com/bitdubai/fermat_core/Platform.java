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

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.OperativeSystems;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.LayerReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealWithDatabaseManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealsWithLogManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformLayers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.ObjectSizeFetcher;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.DealsWithCryptoTransmissionNetworkService;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.DealsWithBitcoinNetwork;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.DealsWithCryptoBrokerIdentities;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.DealsWithCryptoCustomerIdentities;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.DealsWithCCPIntraWalletUsers;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.DealsWithCryptoPaymentRequestNetworkService;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.DealsWithCryptoPayment;
import com.bitdubai.fermat_core.layer.cbp.identity.CBPIdentityLayer;
import com.bitdubai.fermat_core.layer.cbp.sub_app_module.CBPSubAppModuleLayer;
import com.bitdubai.fermat_core.layer.ccm.actor.CCMActorLayer;
import com.bitdubai.fermat_core.layer.dap_actor_network_service.DAPActorNetworkServiceLayer;
import com.bitdubai.fermat_core.layer.dap_network_service.DAPNetworkServiceLayer;
import com.bitdubai.fermat_core.layer.dap_sub_app_module.DAPSubAppModuleLayer;
import com.bitdubai.fermat_core.layer.dap_wallet.DAPWalletLayer;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.interfaces.DealsWithOutgoingIntraActor;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_core.layer.wpd.actor.WPDActorLayer;
import com.bitdubai.fermat_core.layer.wpd.desktop_module.WPDDesktopModuleLayer;
import com.bitdubai.fermat_core.layer.wpd.engine.WPDEngineLayer;
import com.bitdubai.fermat_core.layer.wpd.identity.WPDIdentityLayer;
import com.bitdubai.fermat_core.layer.wpd.middleware.WPDMiddlewareLayer;
import com.bitdubai.fermat_core.layer.wpd.network_service.WPDNetworkServiceLayer;
import com.bitdubai.fermat_core.layer.wpd.sub_app_module.WPDSubAppModuleLayer;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces.AssetIssuerActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces.DealsWithAssetIssuerActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.interfaces.AssetRedeemPointActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.interfaces.DealsWithAssetRedeemPointActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.DealsWithAssetIssuerWalletSubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.DealsWithAssetRedeemPointWalleSubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.DealsWithAssetUserWalletSubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.DealsWithAssetIssuerCommunitySubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.DealsWithAssetUserCommunitySubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces.DealsWithRedeemPointCommunitySubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces.RedeemPointCommunitySubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_reception.interfaces.AssetReceptionManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_reception.interfaces.DealsWithAssetReception;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.DealsWithAssetRedeemPointWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.DealsWithAssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.DealsWithAssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DealsWithAssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProjectManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.DealsWithWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettingsManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.DealsWithWalletModuleCryptoWallet;

import com.bitdubai.fermat_api.layer.osa_android.location_system.DealsWithDeviceLocation;

import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.DealsWithCryptoAddressesNetworkService;

import com.bitdubai.fermat_core.layer.dap_actor.DAPActorLayer;
import com.bitdubai.fermat_core.layer.dap_identity.DAPIdentityLayer;
import com.bitdubai.fermat_core.layer.dap_middleware.DAPMiddlewareLayer;
import com.bitdubai.fermat_core.layer.dap_module.DAPModuleLayer;
import com.bitdubai.fermat_core.layer.dap_transaction.DAPTransactionLayer;
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
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEventMonitor;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.IntraWalletUserManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.DealsWithCCPIdentityIntraWalletUser;
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
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.DealsWithIntraUsersModule;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;

import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.DealsWithWalletPublisherModule;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.WalletPublisherModuleManager;

import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.DealsWithWalletStoreModule;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.DealsWithIntraUsersNetworkService;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;

import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.DealsWithWalletResources;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesInstalationManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_statistics.interfaces.DealsWithWalletStatisticsNetworkService;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_statistics.interfaces.WalletStatisticsManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.DealsWithWalletStoreNetworkService;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_extra_user.DealsWithOutgoingExtraUser;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_extra_user.OutgoingExtraUserManager;

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
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.DealsWithExtraUsers;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_core.layer.cry_crypto_router.CryptoRouterLayer;
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
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.DealsWithPlatformInfo;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfoManager;
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

    private Map<Platforms, AbstractPlatform> platformsMap;

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

        platformsMap = new ConcurrentHashMap<>();
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

        //TODO: NO LO SIGAN PONIENDO QUE ME TIRA ABAJO LA APP. CUANDO LO SUBAN SAQUENLO COMO YO SACO LA CAPA P2P. Gracias XXOO Mati.
//        System.err.println("Tamaño de Platform en runtime es: " + (RamUsageEstimator.sizeOf(this)/1024/1024) + " MB");
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
            corePlatformContext.registerPlatformLayer(new MiddlewareLayer(), PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER);
            corePlatformContext.registerPlatformLayer(new ModuleLayer(), PlatformLayers.BITDUBAI_MODULE_LAYER);
            corePlatformContext.registerPlatformLayer(new AgentLayer(), PlatformLayers.BITDUBAI_AGENT_LAYER);
            corePlatformContext.registerPlatformLayer(new ActorLayer(), PlatformLayers.BITDUBAI_PIP_ACTOR_LAYER);
            corePlatformContext.registerPlatformLayer(new com.bitdubai.fermat_core.layer.pip_module.ModuleLayer(), PlatformLayers.BITDUBAI_PIP_MODULE_LAYER);
            corePlatformContext.registerPlatformLayer(new com.bitdubai.fermat_core.layer.pip_network_service.NetworkServiceLayer(), PlatformLayers.BITDUBAI_PIP_NETWORK_SERVICE_LAYER);
            corePlatformContext.registerPlatformLayer(new RequestServiceLayer(), PlatformLayers.BITDUBAI_REQUEST_LAYER);
            corePlatformContext.registerPlatformLayer(new com.bitdubai.fermat_core.layer.pip_Identity.IdentityLayer(), PlatformLayers.BITDUBAI_PIP_IDENTITY_LAYER);
            corePlatformContext.registerPlatformLayer(new IdentityLayer(), PlatformLayers.BITDUBAI_IDENTITY_LAYER);
            corePlatformContext.registerPlatformLayer(new EngineLayer(), PlatformLayers.BITDUBAI_ENGINE_LAYER);

            // Init CCM Layers
            corePlatformContext.registerPlatformLayer(new CCMActorLayer(), PlatformLayers.BITDUBAI_CCM_ACTOR_LAYER);


            // Init DAP Layers
            corePlatformContext.registerPlatformLayer(new DAPActorLayer(), PlatformLayers.BITDUBAI_DAP_ACTOR_LAYER);
            corePlatformContext.registerPlatformLayer(new DAPActorNetworkServiceLayer(), PlatformLayers.BITDUBAI_DAP_ACTOR_NETWORK_SERVICE_LAYER);
            corePlatformContext.registerPlatformLayer(new DAPIdentityLayer(), PlatformLayers.BITDUBAI_DAP_IDENTITY_LAYER);
            corePlatformContext.registerPlatformLayer(new DAPMiddlewareLayer(), PlatformLayers.BITDUBAI_DAP_MIDDLEWARE_LAYER);
            corePlatformContext.registerPlatformLayer(new DAPModuleLayer(), PlatformLayers.BITDUBAI_DAP_MODULE_LAYER);
            corePlatformContext.registerPlatformLayer(new DAPSubAppModuleLayer(), PlatformLayers.BITDUBAI_DAP_SUB_APP_MODULE_LAYER);
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

            //Init CBP Layers
            corePlatformContext.registerPlatformLayer(new CBPIdentityLayer(), PlatformLayers.BITDUBAI_CBP_IDENTITY_LAYER);
            corePlatformContext.registerPlatformLayer(new CBPSubAppModuleLayer(),PlatformLayers.BITDUBAI_CBP_SUB_APP_MODULE_LAYER);
            //End CBP Layers

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
             * Addon PlatformInfoPlatformServiceFileData
             * -----------------------------
             *
             * Give the PlatformInfoPlatformServiceFileData Manager access to the File System so it can load and save user information from
             * persistent media.
             */
            Service platformInfo = (Service) ((PlatformServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PLATFORM_SERVICE_LAYER)).getPlatformInfo();
            ((DealsWithPlatformFileSystem) platformInfo).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());

            corePlatformContext.registerAddon((Addon) platformInfo, Addons.PLATFORM_INFO);
            platformInfo.start();

        } catch (CantStartPluginException cantStartPluginException) {

            LOG.log(Level.SEVERE, cantStartPluginException.getLocalizedMessage());
            throw new CantStartPlatformException(CantStartPlatformException.DEFAULT_MESSAGE, cantStartPluginException, "", "");
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


             /* flag temporal para desactivar plugins que tarde demasiado en inicializar,
            y asi poder trabajar en otras partes del sistema de forma relativamente rapida */
            boolean BCH = true;
            boolean BNK = true;
            boolean BNP = true;
            boolean CBP = true;
            boolean CCM = false;
            boolean CCP = true;
            boolean CRY = true;
            boolean CSH = true;
            boolean DAP = true; /* DAP no da errores al iniciar, si la desactivas enviar mensaje a Rodrigo por favor*/
            boolean DMP = true;//DOBLEMENTE TEMPORAL
            boolean MKT = true;
            boolean OSA = true;
            boolean P2P = true;
            boolean PIP = true;
            boolean SHP = true;
            boolean WPD = true;

            //TODO: Esto va acá porque es necesario para que se le pase la instancia a otros plugins
            final FermatSystem fermatSystem = new FermatSystem(osContext, OperativeSystems.ANDROID);;
            try {
                fermatSystem.start();
            } catch (FermatException e) {
                System.err.println(e.toString());
                System.out.println(e.getPossibleReason());
                System.out.println(e.getFormattedContext());
                System.out.println(e.getFormattedTrace());
            }


            //--------------------------------

            if (P2P) {
           /*
            * Plugin Web Socket Communication Cloud Client
            * -----------------------------
            */
                Plugin wsCommunicationCloudClient = ((CommunicationLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_COMMUNICATION_LAYER)).getWsCommunicationCloudClientPlugin();
                injectPluginReferencesAndStart(wsCommunicationCloudClient, Plugins.BITDUBAI_WS_COMMUNICATION_CLIENT_CHANNEL);

           /*
            * Plugin Web Socket Communication Cloud Server
            * -----------------------------
            */
                Plugin wsCommunicationCloudServer = ((CommunicationLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_COMMUNICATION_LAYER)).getWsCommunicationCloudServerPlugin();
                injectPluginReferencesAndStart(wsCommunicationCloudServer, Plugins.BITDUBAI_WS_COMMUNICATION_CLOUD_SERVER);

            }

            if (PIP) {
           /*
            * Plugin Actor Developer
            * -----------------------------
            */
                Plugin actorDeveloper = ((ActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_ACTOR_LAYER)).getActorDeveloper();
                injectPluginReferencesAndStart(actorDeveloper, Plugins.BITDUBAI_ACTOR_DEVELOPER);

           /*
            * Plugin Developer Module
            * -----------------------------
            */
                Plugin developerModule = ((com.bitdubai.fermat_core.layer.pip_module.ModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_MODULE_LAYER)).getmDeveloperModule();
                injectPluginReferencesAndStart(developerModule, Plugins.BITDUBAI_DEVELOPER_MODULE);




           /*
            * Plugin Intra User PIP
            * -------------------------------
            */
                Plugin intraUser = ((ActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_ACTOR_LAYER)).getActorDeveloper();
                injectPluginReferencesAndStart(intraUser, Plugins.BITDUBAI_USER_INTRA_USER);

           /*
            * Plugin Desktop runtime PIP
            * -----------------------------
            */
                Plugin desktopRuntimePlugin = ((EngineLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_ENGINE_LAYER)).getmDesktopRuntimePlugin();
                injectPluginReferencesAndStart(desktopRuntimePlugin, Plugins.BITDUBAI_DESKTOP_RUNTIME);

            /*
             * Plugin Wallet Runtime
             * -----------------------------
             */
                Plugin subAppResourcesNetworkService = ((com.bitdubai.fermat_core.layer.pip_network_service.NetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_NETWORK_SERVICE_LAYER)).getSubAppResources();
                injectPluginReferencesAndStart(subAppResourcesNetworkService, Plugins.BITDUBAI_SUBAPP_RESOURCES_NETWORK_SERVICE);

            /*
             * Plugin Developer Identity
             * -----------------------------
             */
                Plugin developerIdentity = ((com.bitdubai.fermat_core.layer.pip_Identity.IdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_IDENTITY_LAYER)).getDeveloperIdentity();
                injectPluginReferencesAndStart(developerIdentity, Plugins.BITDUBAI_DEVELOPER_IDENTITY);

            }

            Plugin cryptoAddressBookCrypto = ((CryptoLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CRYPTO_LAYER)).getCryptoAddressBook();
            injectPluginReferencesAndStart(cryptoAddressBookCrypto, Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK);

            if (CRY) {
           /*
            * Plugin Bitcoin Crypto Network
            * -----------------------------
            */
                /**
                 * disabled since we are migrating back to the new crypto network plugin
                 */
                //Plugin cryptoNetwork = ((CryptoNetworkLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CRYPTO_NETWORK_LAYER)).getCryptoNetwork(CryptoNetworks.BITCOIN);
                //injectPluginReferencesAndStart(cryptoNetwork, Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK);

                Plugin cryptoNetwork2 = ((CryptoNetworkLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CRYPTO_NETWORK_LAYER)).getCryptoNetwork(CryptoNetworks.BITCOIN2);
                injectPluginReferencesAndStart(cryptoNetwork2, Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK2);

           /*
            *  Plugin Crypto Address Book         *
            * ------------------------------------*
            */


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

            }

            /*
            * Plugin Wallet Manager Middleware
            * ----------------------------------
            */
            Plugin walletManagerMiddleware = ((WPDMiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_MIDDLEWARE_LAYER)).getWalletManagerPlugin();
            injectPluginReferencesAndStart(walletManagerMiddleware, Plugins.BITDUBAI_WPD_WALLET_MANAGER_MIDDLEWARE);



            if (CCP) {

                try {

                    PlatformReference platformReference = new PlatformReference(Platforms.BLOCKCHAINS);

                    LayerReference layerReference = new LayerReference(platformReference, Layers.MIDDLEWARE);

                    PluginVersionReference cryptoAddressesMiddlewareVersionReference = newVersionReference(layerReference, Plugins.CRYPTO_ADDRESSES);

                    platformReference = new PlatformReference(Platforms.CRYPTO_CURRENCY_PLATFORM);

                    layerReference = new LayerReference(platformReference, Layers.BASIC_WALLET);

                    Plugin bitcoinWalletBasicWallet = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.BITCOIN_WALLET));
                    injectPluginReferencesAndStart(bitcoinWalletBasicWallet, Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET);

                    layerReference = new LayerReference(platformReference, Layers.NETWORK_SERVICE);

                    Plugin cryptoPaymentRequestNetworkService = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.CRYPTO_PAYMENT_REQUEST));
                    injectLayerReferences(cryptoPaymentRequestNetworkService);
                    injectPluginReferencesAndStart(cryptoPaymentRequestNetworkService, Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE);


                    Plugin intraUserNetworkService = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.INTRA_WALLET_USER));
                    injectLayerReferences(intraUserNetworkService);
                    injectPluginReferencesAndStart(intraUserNetworkService, Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE);

                    Plugin cryptoTransmissionNetworkService = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.CRYPTO_TRANSMISSION));
                    injectLayerReferences(cryptoTransmissionNetworkService);
                    injectPluginReferencesAndStart(cryptoTransmissionNetworkService, Plugins.BITDUBAI_CCP_CRYPTO_CRYPTO_TRANSMISSION_NETWORK_SERVICE);

                   Plugin cryptoAddressesNetworkService = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.CRYPTO_ADDRESSES));
                   injectPluginReferencesAndStart(cryptoAddressesNetworkService, Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE);

// temporal
                    Plugin cryptoAddressesMiddleware = fermatSystem.getPluginVersion(cryptoAddressesMiddlewareVersionReference);
                    injectPluginReferencesAndStart(cryptoAddressesMiddleware, Plugins.CRYPTO_ADDRESSES__MIDDLEWARE_TEMP);

                    layerReference = new LayerReference(platformReference, Layers.MIDDLEWARE);

                    Plugin walletContactsMiddleware = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.WALLET_CONTACTS));
                    injectPluginReferencesAndStart(walletContactsMiddleware, Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE);

                    layerReference = new LayerReference(platformReference, Layers.REQUEST);

                    Plugin cryptoPaymentRequest = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.CRYPTO_PAYMENT_REQUEST));
                    injectPluginReferencesAndStart(cryptoPaymentRequest, Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST);

                    layerReference = new LayerReference(platformReference, Layers.IDENTITY);
                    Plugin ccpIntraWalletUserIdentity = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.INTRA_WALLET_USER));
                    injectPluginReferencesAndStart(ccpIntraWalletUserIdentity, Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY);

                    layerReference = new LayerReference(platformReference, Layers.TRANSACTION);
                    Plugin outgoingIntraActorPlugin = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.OUTGOING_INTRA_ACTOR));
                    injectPluginReferencesAndStart(outgoingIntraActorPlugin, Plugins.BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION);

                    Plugin outgoingExtraUserPlugin = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.OUTGOING_EXTRA_USER));
                    injectPluginReferencesAndStart(outgoingExtraUserPlugin, Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION);

                    Plugin incomingExtraUserTransaction = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.INCOMING_EXTRA_USER));
                    injectPluginReferencesAndStart(incomingExtraUserTransaction, Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION);

                    Plugin incomingIntraUserTransaction = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.INCOMING_INTRA_USER));
                    injectPluginReferencesAndStart(incomingIntraUserTransaction, Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION);

                    layerReference = new LayerReference(platformReference, Layers.ACTOR);
                    Plugin extraUser = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.EXTRA_WALLET_USER));
                    injectPluginReferencesAndStart(extraUser, Plugins.BITDUBAI_ACTOR_EXTRA_USER);

                    Plugin intraUserActor = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.INTRA_WALLET_USER));
                    injectPluginReferencesAndStart(intraUserActor, Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_ACTOR);

                    layerReference = new LayerReference(platformReference, Layers.SUB_APP_MODULE);
                    Plugin intraUserModule = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.INTRA_WALLET_USER));
                    injectPluginReferencesAndStart(intraUserModule, Plugins.BITDUBAI_INTRA_USER_FACTORY_MODULE);

                    layerReference = new LayerReference(platformReference, Layers.WALLET_MODULE);
                    Plugin cryptoWalletWalletModule = fermatSystem.getPluginVersion(newVersionReference(layerReference, Plugins.CRYPTO_WALLET));
                    injectPluginReferencesAndStart(cryptoWalletWalletModule, Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (e instanceof FermatException) {
                        System.out.println(e);
                    }
                }

                if (WPD) {

           /*
            * Plugin Publisher Identity
            * -----------------------------
            */
                    Plugin publisherIdentity = ((WPDIdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_IDENTITY_LAYER)).getPublisherIdentity();
                    injectPluginReferencesAndStart(publisherIdentity, Plugins.BITDUBAI_WPD_PUBLISHER_IDENTITY);

           /*
            * Plugin Wallet Manager
            * -----------------------------
            */
                    Plugin walletManager = ((WPDDesktopModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_DESKTOP_MODULE_LAYER)).getWalletManager();
                    injectPluginReferencesAndStart(walletManager, Plugins.BITDUBAI_WPD_WALLET_MANAGER_DESKTOP_MODULE);

           /*
            * Plugin Wallet Factory Middleware
            * ----------------------------------
            */
                    Plugin walletFactoryMiddleware = ((WPDMiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_MIDDLEWARE_LAYER)).getWalletFactoryPlugin();
                    injectPluginReferencesAndStart(walletFactoryMiddleware, Plugins.BITDUBAI_WPD_WALLET_FACTORY_MIDDLEWARE);



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

           /*
            * Plugin Wallet settings Middleware
            * ----------------------------------
            */
                    Plugin walletSettingsMiddleware = ((WPDMiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_WPD_MIDDLEWARE_LAYER)).getWalletSettingsPlugin();
                    injectPluginReferencesAndStart(walletSettingsMiddleware, Plugins.BITDUBAI_WPD_WALLET_SETTINGS_MIDDLEWARE);

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

                }
            /*
            * Plugin Extra User Actor
            * -------------------------------
//            */
//                Plugin extraUser = ((CryptoVaultLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_ACTOR_LAYER)).getmActorExtraUser();
//                injectPluginReferencesAndStart(extraUser, Plugins.BITDUBAI_ACTOR_EXTRA_USER);

           /*
            * Plugin Intra User Actor
            * -----------------------------
            */
//                Plugin intraUserActor = ((CCPActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CCP_ACTOR_LAYER)).getIntraWalletUserPlugin();
//                injectPluginReferencesAndStart(intraUserActor, Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_ACTOR);




            }

            if (CCM)
            {
                  /*
            * Plugin Intra User Actor
            * -----------------------------
            */
                Plugin intraWalletUserActor = ((CCMActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CCM_ACTOR_LAYER)).getIntraWalletUserPlugin();
                injectPluginReferencesAndStart(intraWalletUserActor, Plugins.BITDUBAI_CCM_INTRA_WALLET_USER_ACTOR);

            }

            if (DMP) {


           /*
            * Plugin Bank Notes Network Service
            * -----------------------------
            */
                Plugin bankNotesNetworkService = ((NetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER)).getBankNotesPlugin();
                injectPluginReferencesAndStart(bankNotesNetworkService, Plugins.BITDUBAI_BANK_NOTES_NETWORK_SERVICE);

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
            * Plugin Request
            * -----------------------------
            */
                Plugin moneyRequest = ((RequestServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_REQUEST_LAYER)).getMoney();
                injectPluginReferencesAndStart(moneyRequest, Plugins.BITDUBAI_REQUEST_MONEY_REQUEST);

           /*
            * Plugin Wallet Runtime
            * -----------------------------
            */
                Plugin walletRuntime = ((ModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MODULE_LAYER)).getWalletRuntime();
                injectPluginReferencesAndStart(walletRuntime, Plugins.BITDUBAI_WALLET_RUNTIME_MODULE);

           /*
            * Plugin Inter Wallet Transaction
            * ----------------------------------
            */
//                Plugin interWalletTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getInterWalletPlugin();
                //   injectPluginReferencesAndStart(interWalletTransaction, Plugins.BITDUBAI_INTER_WALLET_TRANSACTION);

           /*
            * Plugin Outgoing Device user Transaction
            * ----------------------------------
            */
                //  Plugin outgoingDeviceUserTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getOutgoingDeviceUserPlugin();
                //    injectPluginReferencesAndStart(outgoingDeviceUserTransaction, Plugins.BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION);

           /*
            * Plugin Incoming Device user Transaction
            * ----------------------------------
            */
                ////        Plugin incomingDeviceUserTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getIncomingDeviceUserPlugin();
                //    injectPluginReferencesAndStart(incomingDeviceUserTransaction, Plugins.BITDUBAI_INCOMING_DEVICE_USER_TRANSACTION);




           /*
            * Plugin notification
            *---------------------
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


            }



            if (DAP) {
            /*
            * Plugin Asset User Actor Network Service
            * ----------------------------------------
            */
                Plugin assetUserActorNetworkService = ((DAPActorNetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_ACTOR_NETWORK_SERVICE_LAYER)).getAssetUserActorNetworService();
                injectLayerReferences(assetUserActorNetworkService);
                injectPluginReferencesAndStart(assetUserActorNetworkService, Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE);

            /*
            * Plugin Asset Issuer Actor Network Service
            * ----------------------------------------
            */
                Plugin assetIssuerActorNetworkService = ((DAPActorNetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_ACTOR_NETWORK_SERVICE_LAYER)).getAssetIssuerActorNetwokService();
                injectLayerReferences(assetIssuerActorNetworkService);
                injectPluginReferencesAndStart(assetIssuerActorNetworkService, Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR_NETWORK_SERVICE);

           /*
            * Plugin Asset Redeem Point Actor Network Service
            * ----------------------------------------
            */
                Plugin assetRedeemPointActorNetworkService = ((DAPActorNetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_ACTOR_NETWORK_SERVICE_LAYER)).getAssetRedeemPointActorNetwokService();
                injectLayerReferences(assetRedeemPointActorNetworkService);
                injectPluginReferencesAndStart(assetRedeemPointActorNetworkService, Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_ACTOR_NETWORK_SERVICE);

           /*
            * Plugin Asset Transmission Network Service
            * ----------------------------------------assetIssuerActorNetworkService
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
            * Plugin Asset Wallet Asset User
            * -----------------------------
            */
                Plugin assetWalletUser = ((DAPWalletLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_WALLET_LAYER)).getPluginAssetWalletUser();
                injectPluginReferencesAndStart(assetWalletUser, Plugins.BITDUBAI_DAP_ASSET_USER_WALLET);

           /*
            * Plugin Asset Wallet Asset Redeem Point
            * -----------------------------
            */
                Plugin assetWalletRedeemPoint = ((DAPWalletLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_WALLET_LAYER)).getPluginAssetRedeemPoint();
                injectPluginReferencesAndStart(assetWalletRedeemPoint, Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET);

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
            * Plugin Asset Issuing TransactionassetWalletRedeemPoint
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
            * Plugin Asset Appropriation Transaction
            * -----------------------------
            */
                Plugin assetAppropriationTransaction = ((DAPTransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_TRANSACTION_LAYER)).getAssetAppropriationPlugin();
                injectPluginReferencesAndStart(assetAppropriationTransaction, Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION);

           /*
            * Plugin Asset Reception Transaction
            * -----------------------------
            */
                Plugin assetReceptionTransaction = ((DAPTransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_TRANSACTION_LAYER)).getAssetReceptionPlugin();
                injectPluginReferencesAndStart(assetReceptionTransaction, Plugins.BITDUBAI_ASSET_RECEPTION_TRANSACTION);

           /*
            * Plugin Redeem Point Redemption Transaction.
            * -----------------------------
            */
                Plugin redeemPointRedemptionTransaction = ((DAPTransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_TRANSACTION_LAYER)).getAssetRedeemPointRedemptionPlugin();
                injectPluginReferencesAndStart(redeemPointRedemptionTransaction, Plugins.BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION);

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

           /*
            * Plugin Asset User Wallet Module
            * -----------------------------
            */
                Plugin assetUserWalletModule = ((DAPModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_MODULE_LAYER)).getPluginModuleAssetUserWallet();
                injectPluginReferencesAndStart(assetUserWalletModule, Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE);

           /*
            * Plugin Asset Redeem Point Wallet Module
            * -----------------------------
            */
                Plugin assetRedeemPointWalletModule = ((DAPModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_MODULE_LAYER)).getPluginModuleAssetRedeemPointrWallet();
                injectPluginReferencesAndStart(assetRedeemPointWalletModule, Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE);

           /*
            * Plugin Asset Issuer Community Sub App Module
            * -----------------------------
            */
                Plugin assetIssuerSubAppModuleCommunity = ((DAPSubAppModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_SUB_APP_MODULE_LAYER)).getAssetIssuerCommunity();
                injectPluginReferencesAndStart(assetIssuerSubAppModuleCommunity, Plugins.BITDUBAI_DAP_ASSET_ISSUER_COMMUNITY_SUB_APP_MODULE);

           /*
            * Plugin Asset User Community Sub App Module
            * -----------------------------
            */
                Plugin assetUserSubAppModuleCommunity = ((DAPSubAppModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_SUB_APP_MODULE_LAYER)).getAssetUserCommunity();
                injectPluginReferencesAndStart(assetUserSubAppModuleCommunity, Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE);

           /*
            * Plugin Redeem Point Community Sub App Module
            * -----------------------------
            */
                Plugin redeemPointSubAppModuleCommunity = ((DAPSubAppModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_DAP_SUB_APP_MODULE_LAYER)).getRedeemPointCommunity();
                injectPluginReferencesAndStart(redeemPointSubAppModuleCommunity, Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE);

            }



            /*
             * Plugin Template Network Service
             * -----------------------------
             *
                Plugin templateNetworkService = ((NetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER)).getTemplate();
                injectLayerReferences(templateNetworkService);
                injectPluginReferencesAndStart(templateNetworkService, Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE);
             */

//           /*
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
             * Plugin Blockchain Info Index
             * -----------------------------
             */
            // Plugin blockchainInfoWorld = ((WorldLayer)  mWorldLayer).getBlockchainInfo();
            //injectLayerReferences(blockchainInfoWorld);
            // injectPluginReferencesAndStart(blockchainInfoWorld, Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD);

            /*
             * Plugin Shape Shift Index
             * -----------------------------
             */
            // Plugin shapeShiftWorld = ((WorldLayer)  mWorldLayer).getShapeShift();
            //injectLayerReferences(shapeShiftWorld);
            // injectPluginReferencesAndStart(shapeShiftWorld, Plugins.BITDUBAI_SHAPE_SHIFT_WORLD);

            /*
             * Plugin Coinapult Index
             * -----------------------------
             */
            // Plugin coinapultWorld = ((WorldLayer)  mWorldLayer).getCoinapult();
            //injectLayerReferences(coinapultWorld);
            // injectPluginReferencesAndStart(coinapultWorld, Plugins.BITDUBAI_COINAPULT_WORLD);

            /*
             * Plugin Coinbase Index
             * -----------------------------
             */
            // Plugin coinbaseWorld = ((WorldLayer)  mWorldLayer).getCoinbase();
            //injectLayerReferences(coinbaseWorld);
            // injectPluginReferencesAndStart(coinbaseWorld, Plugins.BITDUBAI_COINBASE_WORLD);

            /*
             * Plugin Location Index
             * -----------------------------
             */
            // Plugin locationWorld = ((WorldLayer)  mWorldLayer).getLocation();
            //injectLayerReferences(locationWorld);
            // injectPluginReferencesAndStart(locationWorld, Plugins.BITDUBAI_LOCATION_WORLD);

            /*
             * Plugin Crypto Index Index
             * -----------------------------
             */
            // Plugin cryptoIndexWorld = ((WorldLayer)  mWorldLayer).getCryptoIndex();
            //injectLayerReferences(cryptoIndexWorld);
            // injectPluginReferencesAndStart(cryptoIndexWorld, Plugins.BITDUBAI_CRYPTO_INDEX);


            /*
             * Plugin Discount Wallet Basic Wallet
             * -----------------------------
             */
            //Plugin discountWalletBasicWallet = ((BasicWalletLayer) mBasicWalletLayer).getDiscountWallet();
            //injectPluginReferencesAndStart(discountWalletBasicWallet, Plugins.BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET);

           /*
            * Plugin Intra User Actor
            * -----------------------------
            */
            //   Plugin intraUserActor = ((com.bitdubai.fermat_core.layer.dmp_actor.CryptoVaultLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_ACTOR_LAYER)).getActorIntraUser();
            //injectPluginReferencesAndStart(intraUserActor, Plugins.BITDUBAI_INTRA_USER_ACTOR);

            /*
             * Plugin Crypto Loss Protected Wallet Niche Type Wallet
             * ----------------------------------
             */
            //TODO lo comente porque la variable cryptoLossProtectedWalletWalletModule es null y da error al inicializar la APP (Natalia)
            //Plugin cryptoLossProtectedWalletWalletModule = ((WalletModuleLayer) mWalletModuleLayer).getmCryptoLossProtectedWallet();
            //injectPluginReferencesAndStart(cryptoLossProtectedWalletWalletModule, Plugins.BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE);

            /*
             * Plugin Fiat Over Crypto Loss Protected Wallet Wallet Niche Type Wallet
             * ----------------------------------
             */
            //TODO lo comente porque la variable fiatOverCryptoLossProtectedWalletWalletModule es null  y da error al levantar la APP (Natalia)
            //Plugin fiatOverCryptoLossProtectedWalletWalletModule = ((WalletModuleLayer) mWalletModuleLayer).getmFiatOverCryptoLossProtectedWallet();
            //injectPluginReferencesAndStart(fiatOverCryptoLossProtectedWalletWalletModule, Plugins.BITDUBAI_FIAT_OVER_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE);

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
//              Plugin walletFactoryModule =  ((ModuleLayer) mModuleLayer).getWalletFactory();
//              injectPluginReferencesAndStart(walletFactoryModule, Plugins.BITDUBAI_WPD_WALLET_FACTORY_SUB_APP_MODULE);

            if(CBP){
                Plugin cryptoBrokerIdentity = ((CBPIdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CBP_IDENTITY_LAYER)).getCryptoBrokerIdentity();
                injectPluginReferencesAndStart(cryptoBrokerIdentity, Plugins.BITDUBAI_CBP_CRYPTO_BROKER_IDENTITY);


                Plugin cryptoCustomerIdentity = ((CBPIdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CBP_IDENTITY_LAYER)).getCryptoCustomerIdentity();
                injectPluginReferencesAndStart(cryptoCustomerIdentity, Plugins.BITDUBAI_CBP_CRYPTO_CUSTOMER_IDENTITY);

                Plugin cryptoBrokerIdentitySubAppModule = ((CBPSubAppModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CBP_SUB_APP_MODULE_LAYER)).getCryptoBrokerIdentity();
                injectPluginReferencesAndStart(cryptoBrokerIdentitySubAppModule, Plugins.BITDUBAI_CBP_CRYPTO_BROKER_IDENTITY_SUB_APP_MODULE);

                Plugin cryptoCustomerIdentitySubAppModule = ((CBPSubAppModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CBP_SUB_APP_MODULE_LAYER)).getCryptoCustomerIdentity();
                injectPluginReferencesAndStart(cryptoCustomerIdentitySubAppModule, Plugins.BITDUBAI_CBP_CRYPTO_CUSTOMER_IDENTITY_SUB_APP_MODULE);
            }

        } catch (CantInitializePluginsManagerException cantInitializePluginsManagerException) {
            LOG.log(Level.SEVERE, cantInitializePluginsManagerException.getLocalizedMessage());
            throw new CantStartPlatformException(CantStartPlatformException.DEFAULT_MESSAGE,cantInitializePluginsManagerException, "","");
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
        System.out.println("************************************************************************");

        System.out.println("--------------- Lista de Tamaños en Start-Up de Plugins ---------------");
        System.out.println("************************************************************************");
        for (Map.Entry<Plugins, String> entry : pluginsSizeReport.entrySet()) {
            System.out.println(entry.getKey().toString() + " - Start-Up Size: " + entry.getValue() +".");
        }
        System.out.println("************************************************************************");

    }

    private PluginVersionReference newVersionReference(LayerReference layer, Plugins fermatPluginsEnum) {

        return new PluginVersionReference(
                layer.getPlatformReference().getPlatform(),
                layer.getLayer(),
                fermatPluginsEnum,
                Developers.BITDUBAI,
                new Version()
        );
    }

    Map<Plugins, Long> pluginsStartUpTime = new HashMap<>();
    Map<Plugins, String> pluginsSizeReport = new HashMap<>();

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

            if (plugin instanceof DealsWithCCPIdentityIntraWalletUser) {
                ((DealsWithCCPIdentityIntraWalletUser) plugin).setIdentityIntraUserManager((com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY));
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

            if (plugin instanceof DealsWithAssetIssuerActorNetworkServiceManager) {
                ((DealsWithAssetIssuerActorNetworkServiceManager) plugin).setAssetIssuerActorNetworkServiceManager((AssetIssuerActorNetworkServiceManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR_NETWORK_SERVICE));
            }

            if (plugin instanceof DealsWithAssetUserActorNetworkServiceManager) {
                ((DealsWithAssetUserActorNetworkServiceManager) plugin).setAssetUserActorNetworkServiceManager((AssetUserActorNetworkServiceManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE));
            }

            if (plugin instanceof DealsWithAssetRedeemPointActorNetworkServiceManager) {
                ((DealsWithAssetRedeemPointActorNetworkServiceManager) plugin).setAssetRedeemPointActorNetworkServiceManager((AssetRedeemPointActorNetworkServiceManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_ACTOR_NETWORK_SERVICE));
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

            if (plugin instanceof DealsWithAssetIssuerCommunitySubAppModule) {
                ((DealsWithAssetIssuerCommunitySubAppModule) plugin).setAssetIssuerCommunitySubAppModuleManager((AssetIssuerCommunitySubAppModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_ISSUER_COMMUNITY_SUB_APP_MODULE));
            }

            if (plugin instanceof DealsWithAssetUserCommunitySubAppModule) {
                ((DealsWithAssetUserCommunitySubAppModule) plugin).setAssetIssuerCommunitySubAppModuleManager((AssetUserCommunitySubAppModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE));
            }

            if (plugin instanceof DealsWithRedeemPointCommunitySubAppModule) {
                ((DealsWithRedeemPointCommunitySubAppModule) plugin).setAssetIssuerCommunitySubAppModuleManager((RedeemPointCommunitySubAppModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE));
            }

            if (plugin instanceof DealsWithAssetIssuerWallet) {
                ((DealsWithAssetIssuerWallet) plugin).setAssetIssuerManager((AssetIssuerWalletManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_ASSET_WALLET_ISSUER));
            }
            if (plugin instanceof DealsWithAssetUserWallet) {
                ((DealsWithAssetUserWallet) plugin).setAssetUserManager((AssetUserWalletManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET));
            }
            if (plugin instanceof DealsWithAssetRedeemPointWallet) {
                ((DealsWithAssetRedeemPointWallet) plugin).setAssetReddemPointManager((AssetRedeemPointWalletManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET));
            }
            if (plugin instanceof DealsWithAssetIssuerWalletSubAppModule) {
                ((DealsWithAssetIssuerWalletSubAppModule) plugin).setWalletAssetIssuerManager((AssetIssuerWalletSupAppModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE));
            }
            if (plugin instanceof DealsWithAssetUserWalletSubAppModule) {
                ((DealsWithAssetUserWalletSubAppModule) plugin).setWalletAssetUserManager((AssetUserWalletSubAppModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE));
            }
            if (plugin instanceof DealsWithAssetRedeemPointWalleSubAppModule) {
                ((DealsWithAssetRedeemPointWalleSubAppModule) plugin).setWalletAssetRedeemPointManager((AssetRedeemPointWalletSubAppModule) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE));
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

            if (plugin instanceof DealsWithCryptoBrokerIdentities){
                ((DealsWithCryptoBrokerIdentities) plugin).setCryptoBrokerIdentityManager((CryptoBrokerIdentityManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CBP_CRYPTO_BROKER_IDENTITY));
            }

            if (plugin instanceof DealsWithCryptoCustomerIdentities){
                ((DealsWithCryptoCustomerIdentities) plugin).setCryptoCustomerIdentityManager((CryptoCustomerIdentityManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CBP_CRYPTO_CUSTOMER_IDENTITY));
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
        pluginsSizeReport.put(descriptor, ObjectSizeFetcher.sizeOf(plugin));
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
            throw new CantStartPlatformException(CantStartPlatformException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "", "");
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

    private void getPlugin() {

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
