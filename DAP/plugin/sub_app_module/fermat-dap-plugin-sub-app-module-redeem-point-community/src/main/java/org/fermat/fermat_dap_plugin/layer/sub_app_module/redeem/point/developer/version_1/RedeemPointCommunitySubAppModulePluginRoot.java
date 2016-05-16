package org.fermat.fermat_dap_plugin.layer.sub_app_module.redeem.point.developer.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededIndirectPluginReferences;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.moduleManagerInterfacea;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;


import org.fermat.fermat_dap_api.layer.dap_actor_network_service.redeem_point.interfaces.AssetRedeemPointActorNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentityManager;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.exceptions.CantGetSupAppRedeemPointModuleException;
import org.fermat.fermat_dap_plugin.layer.sub_app_module.redeem.point.developer.version_1.structure.RedeemPointCommunitySupAppModuleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * TODO explain here the main functionality of the plug-in.
 * <p/>
 * Created by Nerio on 13/10/15.
 */
@NeededIndirectPluginReferences(indirectReferences = {
        @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.INCOMING_EXTRA_USER),
        @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.INCOMING_INTRA_USER)
})
@PluginInfo(difficulty = PluginInfo.Dificulty.LOW,
        maintainerMail = "marsvicam@gmail.com",
        createdBy = "nindriago",
        layer = Layers.SUB_APP_MODULE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.REDEEM_POINT)
public class RedeemPointCommunitySubAppModulePluginRoot extends AbstractModule<RedeemPointSettings, ActiveActorIdentityInformation> implements
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.REDEEM_POINT)
    RedeemPointIdentityManager redeemPointIdentityManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    ActorAssetUserManager actorAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.REDEEM_POINT)
    ActorAssetRedeemPointManager actorAssetRedeemPointManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET_MODULE, plugin = Plugins.ASSET_USER)
    AssetUserWalletSubAppModuleManager assetUserWalletSubAppModuleManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.REDEEM_POINT)
    AssetRedeemPointActorNetworkServiceManager assetRedeemPointActorNetworkServiceManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    private SettingsManager<RedeemPointSettings> settingsManager;
    RedeemPointCommunitySupAppModuleManager redeemPointCommunitySupAppModuleManager;
    BlockchainNetworkType blockchainNetworkType;

    private String appPublicKey;

    public RedeemPointCommunitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("AssetIssuerCommunitySubAppModulePluginRoot");
        returnedClasses.add("AssetIssuerCommunitySupAppModuleManager");

        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (RedeemPointCommunitySubAppModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                RedeemPointCommunitySubAppModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                RedeemPointCommunitySubAppModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                RedeemPointCommunitySubAppModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split(Pattern.quote("$"));
            return RedeemPointCommunitySubAppModulePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    @moduleManagerInterfacea(moduleManager = RedeemPointCommunitySupAppModuleManager.class)
    public ModuleManager<RedeemPointSettings, ActiveActorIdentityInformation> getModuleManager() throws CantGetModuleManagerException {
        try {
            logManager.log(RedeemPointCommunitySubAppModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Redeem Point Sup AppModule instantiation started...", null, null);

            if (redeemPointCommunitySupAppModuleManager == null) {
                redeemPointCommunitySupAppModuleManager = new RedeemPointCommunitySupAppModuleManager(
                        pluginId,
                        pluginFileSystem,
                        logManager,
                        errorManager,
                        eventManager,
                        redeemPointIdentityManager,
                        actorAssetUserManager,
                        actorAssetRedeemPointManager,
                        assetUserWalletSubAppModuleManager,
                        assetRedeemPointActorNetworkServiceManager,
                        broadcaster,
                        this);
            }

            logManager.log(RedeemPointCommunitySubAppModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Redeem Point Sup AppModule instantiation finished successfully.", null, null);

            return redeemPointCommunitySupAppModuleManager;

        } catch (final Exception e) {
            throw new CantGetModuleManagerException(CantGetSupAppRedeemPointModuleException.DEFAULT_MESSAGE, FermatException.wrapException(e).toString());
        }
    }

//    @Override
//    public List<RedeemPointActorRecord> getAllActorAssetRedeemPointRegistered() throws CantGetAssetRedeemPointActorsException {
//        List<ActorAssetRedeemPoint> list = null;
//        List<RedeemPointActorRecord> actorAssetRedeemPoints = null;
//
//        try {
//            list = assetRedeemPointActorNetworkServiceManager.getListActorAssetRedeemPointRegistered();
//            if (list != null && list.size() > 0)
//                actorAssetRedeemPointManager.createActorAssetRedeemPointRegisterInNetworkService(list);
//        } catch (CantRequestListActorAssetRedeemPointRegisteredException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetAssetRedeemPointActorsException("Service not available, try in a few minutes.", null, "Problems with the Web Service.", null);
//        } catch (CantCreateActorRedeemPointException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//        }
//
//        if (list != null) {
//            actorAssetRedeemPoints = new ArrayList<>();
//            actorAssetRedeemPointManager.updateOfflineRedeemPointRegisterInNetworkService(list);
//
//            try {
//                BlockchainNetworkType blockchainNetworkType = assetUserWalletSubAppModuleManager.getSelectedNetwork();
//                for (ActorAssetRedeemPoint actorAssetRedeemPoint : actorAssetRedeemPointManager.getAllAssetRedeemPointActorInTableRegistered(blockchainNetworkType)) {
//                    if (Objects.equals(actorAssetRedeemPoint.getType().getCode(), Actors.DAP_ASSET_REDEEM_POINT.getCode())) {
//                        RedeemPointActorRecord redeemPointActorRecord = (RedeemPointActorRecord) actorAssetRedeemPoint;
//                        actorAssetRedeemPoints.add(redeemPointActorRecord);
//                    }
//                }
//            } catch (CantGetAssetRedeemPointActorsException e) {
//                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                e.printStackTrace();
//            }
//        }
//        return actorAssetRedeemPoints;
//    }
//
//    @Override
//    public List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointConnected() throws CantGetAssetRedeemPointActorsException {
//        try {
//            return actorAssetRedeemPointManager.getAllRedeemPointActorConnected(assetUserWalletSubAppModuleManager.getSelectedNetwork());
//        } catch (CantGetAssetRedeemPointActorsException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw e;
//        }
//    }
//
//    @Override
//    public DAPConnectionState getActorRedeemRegisteredDAPConnectionState(String actorAssetPublicKey) throws CantGetAssetRedeemPointActorsException {
//        blockchainNetworkType = assetUserWalletSubAppModuleManager.getSelectedNetwork();
//
//        return actorAssetRedeemPointManager.getActorRedeemPointRegisteredDAPConnectionState(actorAssetPublicKey, blockchainNetworkType);
//    }
//
//    @Override
//    public void connectToActorAssetRedeemPoint(ActorAssetUser requester, List<ActorAssetRedeemPoint> actorAssetRedeemPoint) throws CantConnectToActorAssetException {
//        blockchainNetworkType = assetUserWalletSubAppModuleManager.getSelectedNetwork();
//
//        ActorAssetUser actorAssetUser;
//        //TODO Actor Asset User de BD Local
//        try {
//            actorAssetUser = actorAssetUserManager.getActorAssetUser();
//
//            if (actorAssetUser != null)
//                actorAssetUserManager.connectToActorAssetRedeemPoint(actorAssetUser, actorAssetRedeemPoint, blockchainNetworkType);
//            else
//                throw new CantConnectToActorAssetException(CantConnectToActorAssetException.DEFAULT_MESSAGE, null, "THERE WAS AN ERROR GET ACTOR ASSET USER.", null);
//
//        } catch (CantGetAssetUserActorsException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantConnectToActorAssetException(CantConnectToActorAssetException.DEFAULT_MESSAGE, e, "THERE WAS AN ERROR GET ACTOR ASSET USER.", null);
//        } catch (CantConnectToActorAssetRedeemPointException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantConnectToActorAssetException(CantConnectToActorAssetException.DEFAULT_MESSAGE, e, "THERE WAS AN ERROR CONNECTING TO REDEEM POINT.", null);
//        }
//    }
//
//    @Override
//    public RedeemPointIdentity getActiveAssetRedeemPointIdentity() throws CantGetIdentityRedeemPointException {
//        try {
//            return redeemPointIdentityManager.getIdentityAssetRedeemPoint();
//        } catch (CantGetRedeemPointIdentitiesException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetIdentityRedeemPointException(e);
//        }
//    }
//
//    @Override
//    public ActorAssetRedeemPoint getActorRedeemPoint(String actorPublicKey) throws CantGetAssetRedeemPointActorsException, CantAssetRedeemPointActorNotFoundException {
//        try {
//            return actorAssetRedeemPointManager.getActorByPublicKey(actorPublicKey, assetUserWalletSubAppModuleManager.getSelectedNetwork());
//        } catch (CantGetAssetRedeemPointActorsException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetAssetRedeemPointActorsException(CantGetAssetRedeemPointActorsException.DEFAULT_MESSAGE,e,"THERE WAS AN ERROR GETTING ACTOR REDEEM POINT",null);
//        } catch (CantAssetRedeemPointActorNotFoundException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantAssetRedeemPointActorNotFoundException(CantAssetRedeemPointActorNotFoundException.DEFAULT_MESSAGE,e,"ACTOR REDEEM POINT NOT FOUND",null);
//        }
//    }
//
//    @Override
//    public void askActorAssetRedeemForConnection(List<ActorAssetRedeemPoint> actorAssetRedeem) throws CantAskConnectionActorAssetException, CantRequestAlreadySendActorAssetException {
//        try {
//            ActorAssetUser actorAssetUser = actorAssetUserManager.getActorAssetUser();
//            if (actorAssetUser != null) {
//            blockchainNetworkType = assetUserWalletSubAppModuleManager.getSelectedNetwork();
//
//                for (ActorAssetRedeemPoint assetRedeemPoint : actorAssetRedeem) {
//                    this.actorAssetRedeemPointManager.askActorAssetRedeemForConnection(
//                            actorAssetUser.getActorPublicKey(),
//                            assetRedeemPoint.getName(),
//                            assetRedeemPoint.getActorPublicKey(),
//                            assetRedeemPoint.getProfileImage(),
//                            blockchainNetworkType);
//
//                    if (this.actorAssetRedeemPointManager.getActorRedeemPointRegisteredDAPConnectionState(assetRedeemPoint.getActorPublicKey(), blockchainNetworkType) != DAPConnectionState.REGISTERED_REMOTELY) {
//                        System.out.println("The User you are trying to connect with is not connected" +
//                                "so we send the message to the assetRedeemPointActorNetworkService");
//                        this.assetRedeemPointActorNetworkServiceManager.askConnectionActorAsset(
//                                actorAssetUser.getActorPublicKey(),
//                                actorAssetUser.getName(),
//                                Actors.DAP_ASSET_USER,
//                                assetRedeemPoint.getActorPublicKey(),
//                                assetRedeemPoint.getName(),
//                                Actors.DAP_ASSET_REDEEM_POINT,
//                                actorAssetUser.getProfileImage(),
//                                blockchainNetworkType);
//                    } else {
//                        this.assetRedeemPointActorNetworkServiceManager.acceptConnectionActorAsset(actorAssetUser.getActorPublicKey(), assetRedeemPoint.getActorPublicKey());
//                        System.out.println("The actor asset Redeem Point is connected");
//                    }
//                }
//            }
//            else
//            {
//                throw new CantConnectToActorAssetException(CantConnectToActorAssetException.DEFAULT_MESSAGE, null, "There was an error connecting to users. No identity", null);
//            }
//        } catch (CantAskConnectionActorAssetException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantAskConnectionActorAssetException("", e, "", "");
//        } catch (CantRequestAlreadySendActorAssetException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantAskConnectionActorAssetException("", e, "", "actor ASSET ISSUER request already send");
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantAskConnectionActorAssetException("CAN'T ASK ACTOR ASSET ISSUER CONNECTION", FermatException.wrapException(e), "", "unknown exception");
//        }
//    }
//
//    @Override
//    public void acceptActorAssetRedeem(String actorAssetRedeemInPublicKey, ActorAssetRedeemPoint actorAssetRedeemToAdd) throws CantAcceptActorAssetUserException {
//        try {
//
//            if (actorAssetRedeemToAdd.getType().getCode().equals(Actors.DAP_ASSET_USER.getCode())) {
//                this.actorAssetUserManager.acceptActorAssetUser(actorAssetRedeemInPublicKey, actorAssetRedeemToAdd.getActorPublicKey());
//            } else {
//                this.actorAssetRedeemPointManager.acceptActorAssetRedeem(actorAssetRedeemInPublicKey, actorAssetRedeemToAdd.getActorPublicKey());
//            }
//
//            this.assetRedeemPointActorNetworkServiceManager.acceptConnectionActorAsset(actorAssetRedeemInPublicKey, actorAssetRedeemToAdd.getActorPublicKey());
//
//        } catch (CantAcceptActorAssetUserException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantAcceptActorAssetUserException("CAN'T ACCEPT ACTOR REDEEM POINT CONNECTION - KEY " + actorAssetRedeemToAdd.getActorPublicKey(), e, "", "");
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantAcceptActorAssetUserException("CAN'T ACCEPT ACTOR REDEEM POINT CONNECTION - KEY " + actorAssetRedeemToAdd.getActorPublicKey(), FermatException.wrapException(e), "", "unknown exception");
//        }
//    }
//
//    @Override
//    public void denyConnectionActorAssetRedeem(String actorAssetRedeemLoggedInPublicKey, ActorAssetRedeemPoint actorAssetRedeemToReject) throws CantDenyConnectionActorAssetException {
//        try {
//            if (actorAssetRedeemToReject.getType().getCode().equals(Actors.DAP_ASSET_USER.getCode())) {
//                this.actorAssetUserManager.acceptActorAssetUser(actorAssetRedeemLoggedInPublicKey, actorAssetRedeemToReject.getActorPublicKey());
//            } else {
//                this.actorAssetRedeemPointManager.denyConnectionActorAssetRedeem(actorAssetRedeemLoggedInPublicKey, actorAssetRedeemToReject.getActorPublicKey());
//            }
//
//            this.assetRedeemPointActorNetworkServiceManager.denyConnectionActorAsset(actorAssetRedeemLoggedInPublicKey, actorAssetRedeemToReject.getActorPublicKey());
//
//        } catch (CantDenyConnectionActorAssetException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantDenyConnectionActorAssetException("CAN'T DENY ACTOR REDEEM POINT CONNECTION - KEY:" + actorAssetRedeemToReject.getActorPublicKey(), e, "", "");
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantDenyConnectionActorAssetException("CAN'T DENY ACTOR REDEEM POINT CONNECTION - KEY:" + actorAssetRedeemToReject.getActorPublicKey(), FermatException.wrapException(e), "", "unknown exception");
//        }
//    }
//
//    @Override
//    public void disconnectToActorAssetRedeemPoint(ActorAssetRedeemPoint redeemPoint) throws CantDisconnectAssetActorException {
//        try {
//
//            actorAssetRedeemPointManager.disconnectToActorAssetRedeemPoint(redeemPoint.getActorPublicKey(), assetUserWalletSubAppModuleManager.getSelectedNetwork());
//
//            this.assetRedeemPointActorNetworkServiceManager.disconnectConnectionActorAsset(redeemPoint.getActorPublicKey(), redeemPoint.getActorPublicKey());
//
//        } catch (CantDisconnectAssetActorException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantDisconnectAssetActorException("CAN'T DISCONNECT ACTOR REDEEM POINT CONNECTION - KEY:" + redeemPoint.getActorPublicKey(), FermatException.wrapException(e), "", "unknown exception");
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantDisconnectAssetActorException("CAN'T DISCONNECT ACTOR REDEEM POINT CONNECTION - KEY:" + redeemPoint.getActorPublicKey(), FermatException.wrapException(e), "", "unknown exception");
//        }
//    }
//
//    @Override
//    public void cancelActorAssetRedeem(String actorAssetRedeemToCancelPublicKey) throws CantCancelConnectionActorAssetException {
//        try {
//
//            this.actorAssetRedeemPointManager.cancelActorAssetRedeem(actorAssetRedeemToCancelPublicKey);
//
////            this.assetRedeemPointActorNetworkServiceManager.cancelConnectionActorAsset(actorAssetRedeemLoggedInPublicKey, actorAssetRedeemToCancelPublicKey);
//
//        } catch (CantCancelConnectionActorAssetException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantCancelConnectionActorAssetException("CAN'T CANCEL ACTOR REDEEM POINT CONNECTION - KEY:" + actorAssetRedeemToCancelPublicKey, e, "", "");
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantCancelConnectionActorAssetException("CAN'T CANCEL ACTOR REDEEM POINT CONNECTION - KEY:" + actorAssetRedeemToCancelPublicKey, FermatException.wrapException(e), "", "unknown exception");
//        }
//    }
//
//    @Override
//    public List<ActorAssetRedeemPoint> getWaitingYourConnectionActorAssetRedeem(String actorAssetRedeemLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException {
//        try {
//            List<DAPActor> dapActor;
//            List<ActorAssetRedeemPoint> actorAssetRedeemPoints = new ArrayList<>();
//
//            dapActor = this.actorAssetUserManager.getWaitingYourConnectionActorAssetUser(actorAssetRedeemLoggedInPublicKey, max, offset);
//
//            if (dapActor.size() <= 0)
//                dapActor = this.actorAssetRedeemPointManager.getWaitingYourConnectionActorAssetRedeem(actorAssetRedeemLoggedInPublicKey, max, offset);
//
//            //TODO Mejorar Implementacion para tener informacion mas completa
//            for (DAPActor record : dapActor) {
//                actorAssetRedeemPoints.add((new RedeemPointActorRecord (
//                        record.getActorPublicKey(),
//                        record.getName(),
//                        null,
//                        (double) 0,
//                        (double) 0,
//                        null,
//                        (long)  0,
//                        (long)  0,
//                        record.getType(),
//                        null,
//                        record.getProfileImage())));
//            }
//
//            return  actorAssetRedeemPoints;
//        } catch (CantGetActorAssetWaitingException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR REDEEM POINT WAITING YOUR ACCEPTANCE", e, "", "");
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR REDEEM POINT WAITING YOUR ACCEPTANCE", FermatException.wrapException(e), "", "unknown exception");
//        }
//    }
//
//    @Override
//    public List<ActorAssetRedeemPoint> getWaitingTheirConnectionActorAssetRedeem(String actorAssetRedeemLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException {
//        try {
//            List<DAPActor> dapActor;
//            List<ActorAssetRedeemPoint> actorAssetRedeemPoints = new ArrayList<>();
//
//            dapActor = this.actorAssetUserManager.getWaitingTheirConnectionActorAssetUser(actorAssetRedeemLoggedInPublicKey, max, offset);
//
//            if (dapActor.size() <= 0)
//                dapActor = this.actorAssetRedeemPointManager.getWaitingTheirConnectionActorAssetRedeem(actorAssetRedeemLoggedInPublicKey, max, offset);
//
//            //TODO Mejorar Implementacion para tener informacion mas completa
//            for (DAPActor record : dapActor) {
//                actorAssetRedeemPoints.add((new RedeemPointActorRecord (
//                        record.getActorPublicKey(),
//                        record.getName(),
//                        null,
//                        (double) 0,
//                        (double) 0,
//                        null,
//                        (long)  0,
//                        (long)  0,
//                        record.getType(),
//                        null,
//                        record.getProfileImage())));
//            }
//
//            return  actorAssetRedeemPoints;
//        } catch (CantGetActorAssetWaitingException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR REDEEM POINT WAITING THEIR ACCEPTANCE", e, "", "Error on ACTOR REDEEM POINT MANAGER");
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR REDEEM POINT WAITING THEIR ACCEPTANCE", FermatException.wrapException(e), "", "unknown exception");
//        }
//    }
//
//    @Override
//    public ActorAssetRedeemPoint getLastNotification(String actorAssetUserInPublicKey) throws CantGetActorAssetNotificationException {
//        try {
//            return this.actorAssetRedeemPointManager.getLastNotificationActorAssetRedeem(actorAssetUserInPublicKey);
//        } catch (CantGetActorAssetNotificationException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetActorAssetNotificationException("CAN'T GET ACTOR REDEEM POINT LAST NOTIFICATION", e, "", "Error on ACTOR ASSET Manager");
//        }
//    }
//
//    @Override
//    public int getWaitingYourConnectionActorAssetRedeemCount() {
//        //TODO: falta que este metodo que devuelva la cantidad de request de conexion que tenes
//        try {
//            int countActor;
//
//            if (getActiveAssetRedeemPointIdentity() != null) {
//                countActor = actorAssetUserManager.getWaitingYourConnectionActorAssetUser(getActiveAssetRedeemPointIdentity().getPublicKey(), 100, 0).size();
//
//                if (countActor <= 0) {
//                    countActor = getWaitingYourConnectionActorAssetRedeem(getActiveAssetRedeemPointIdentity().getPublicKey(), 100, 0).size();
//                }
//                return countActor;
//
//            }
//
//        } catch (CantGetActorAssetWaitingException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            e.printStackTrace();
//        } catch (CantGetIdentityRedeemPointException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            e.printStackTrace();
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    @Override
//    public SettingsManager<RedeemPointSettings> getSettingsManager() {
//        if (this.settingsManager != null)
//            return this.settingsManager;
//
//        this.settingsManager = new SettingsManager<>(
//                pluginFileSystem,
//                pluginId
//        );
//
//        return this.settingsManager;
//    }
//
//    @Override
//    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
//        return redeemPointIdentityManager.getSelectedActorIdentity();
//    }
//
//    @Override
//    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
//        redeemPointIdentityManager.createNewRedeemPoint(name, profile_img);
//    }
//
//    @Override
//    public void setAppPublicKey(String publicKey) {
//        this.appPublicKey = publicKey;
//    }
//
//    @Override
//    public int[] getMenuNotifications() {
//        int[] notifications = new int[5];
//        try {
//            if (getSelectedActorIdentity() != null) {
//                notifications[2] = actorAssetUserManager.getWaitingYourConnectionActorAssetUser(getSelectedActorIdentity().getPublicKey(), 99, 0).size();
//
//                if (notifications[2] <= 0) {
//                    notifications[2] = actorAssetRedeemPointManager.getWaitingYourConnectionActorAssetRedeem(getSelectedActorIdentity().getPublicKey(), 99, 0).size();
//                }
//            } else {
//                notifications[2] = 0;
//            }
//        } catch (CantGetActorAssetWaitingException | CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            e.printStackTrace();
//        }
//        return notifications;
//    }
}
