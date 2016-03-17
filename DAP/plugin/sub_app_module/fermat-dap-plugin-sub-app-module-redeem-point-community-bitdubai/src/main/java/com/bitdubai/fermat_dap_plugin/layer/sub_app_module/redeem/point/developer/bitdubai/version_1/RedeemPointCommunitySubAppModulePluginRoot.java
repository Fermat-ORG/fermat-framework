package com.bitdubai.fermat_dap_plugin.layer.sub_app_module.redeem.point.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededIndirectPluginReferences;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.exceptions.CantConnectToActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.exceptions.CantDisconnectAssetActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.RedeemPointActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantAssetRedeemPointActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantConnectToActorAssetRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantCreateActorRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAskConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCancelConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDenyConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantRequestAlreadySendActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantRequestListActorAssetRedeemPointRegisteredException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.interfaces.AssetRedeemPointActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantGetRedeemPointIdentitiesException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentityManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces.RedeemPointCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TODO explain here the main functionality of the plug-in.
 * <p/>
 * Created by Nerio on 13/10/15.
 */
@NeededIndirectPluginReferences(indirectReferences = {
        @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.INCOMING_EXTRA_USER),
        @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.INCOMING_INTRA_USER)
})
public class RedeemPointCommunitySubAppModulePluginRoot extends AbstractPlugin implements
        RedeemPointCommunitySubAppModuleManager {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    ErrorManager errorManager;

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

    private SettingsManager<RedeemPointSettings> settingsManager;

    BlockchainNetworkType blockchainNetworkType;

    private String appPublicKey;

    public RedeemPointCommunitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public List<RedeemPointActorRecord> getAllActorAssetRedeemPointRegistered() throws CantGetAssetRedeemPointActorsException {
        List<ActorAssetRedeemPoint> list = null;
        List<RedeemPointActorRecord> actorAssetRedeemPoints = null;

        try {
            list = assetRedeemPointActorNetworkServiceManager.getListActorAssetRedeemPointRegistered();
            if (list != null && list.size() > 0)
                actorAssetRedeemPointManager.createActorAssetRedeemPointRegisterInNetworkService(list);
        } catch (CantRequestListActorAssetRedeemPointRegisteredException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAssetRedeemPointActorsException("Service not available, try in a few minutes.", null, "Problems with the Web Service.", null);
        } catch (CantCreateActorRedeemPointException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

        if (list != null) {
            actorAssetRedeemPoints = new ArrayList<>();
            actorAssetRedeemPointManager.updateOfflineRedeemPointRegisterInNetworkService(list);

            try {
                BlockchainNetworkType blockchainNetworkType = assetUserWalletSubAppModuleManager.getSelectedNetwork();
                for (ActorAssetRedeemPoint actorAssetRedeemPoint : actorAssetRedeemPointManager.getAllAssetRedeemPointActorInTableRegistered(blockchainNetworkType)) {
                    if (Objects.equals(actorAssetRedeemPoint.getType().getCode(), Actors.DAP_ASSET_REDEEM_POINT.getCode())) {
                        RedeemPointActorRecord redeemPointActorRecord = (RedeemPointActorRecord) actorAssetRedeemPoint;
                        actorAssetRedeemPoints.add(redeemPointActorRecord);
                    }
                }
            } catch (CantGetAssetRedeemPointActorsException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                e.printStackTrace();
            }
        }
        return actorAssetRedeemPoints;
    }

    @Override
    public List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointConnected() throws CantGetAssetRedeemPointActorsException {
        try {
            return actorAssetRedeemPointManager.getAllRedeemPointActorConnected(assetUserWalletSubAppModuleManager.getSelectedNetwork());
        } catch (CantGetAssetRedeemPointActorsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public DAPConnectionState getActorRedeemRegisteredDAPConnectionState(String actorAssetPublicKey) throws CantGetAssetRedeemPointActorsException {
        blockchainNetworkType = assetUserWalletSubAppModuleManager.getSelectedNetwork();

        return actorAssetRedeemPointManager.getActorRedeemPointRegisteredDAPConnectionState(actorAssetPublicKey, blockchainNetworkType);
    }

    @Override
    public void connectToActorAssetRedeemPoint(ActorAssetUser requester, List<ActorAssetRedeemPoint> actorAssetRedeemPoint) throws CantConnectToActorAssetException {
        blockchainNetworkType = assetUserWalletSubAppModuleManager.getSelectedNetwork();

        ActorAssetUser actorAssetUser;
        //TODO Actor Asset User de BD Local
        try {
            actorAssetUser = actorAssetUserManager.getActorAssetUser();

            if (actorAssetUser != null)
                actorAssetUserManager.connectToActorAssetRedeemPoint(actorAssetUser, actorAssetRedeemPoint, blockchainNetworkType);
            else
                throw new CantConnectToActorAssetException(CantConnectToActorAssetException.DEFAULT_MESSAGE, null, "THERE WAS AN ERROR GET ACTOR ASSET USER.", null);

        } catch (CantGetAssetUserActorsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConnectToActorAssetException(CantConnectToActorAssetException.DEFAULT_MESSAGE, e, "THERE WAS AN ERROR GET ACTOR ASSET USER.", null);
        } catch (CantConnectToActorAssetRedeemPointException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConnectToActorAssetException(CantConnectToActorAssetException.DEFAULT_MESSAGE, e, "THERE WAS AN ERROR CONNECTING TO REDEEM POINT.", null);
        }
    }

    @Override
    public RedeemPointIdentity getActiveAssetRedeemPointIdentity() throws CantGetIdentityRedeemPointException {
        try {
            return redeemPointIdentityManager.getIdentityAssetRedeemPoint();
        } catch (CantGetRedeemPointIdentitiesException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetIdentityRedeemPointException(e);
        }
    }

    @Override
    public ActorAssetRedeemPoint getActorRedeemPoint(String actorPublicKey) throws CantGetAssetRedeemPointActorsException, CantAssetRedeemPointActorNotFoundException {
        try {
            return actorAssetRedeemPointManager.getActorByPublicKey(actorPublicKey, assetUserWalletSubAppModuleManager.getSelectedNetwork());
        } catch (CantGetAssetRedeemPointActorsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAssetRedeemPointActorsException(CantGetAssetRedeemPointActorsException.DEFAULT_MESSAGE,e,"THERE WAS AN ERROR GETTING ACTOR REDEEM POINT",null);
        } catch (CantAssetRedeemPointActorNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAssetRedeemPointActorNotFoundException(CantAssetRedeemPointActorNotFoundException.DEFAULT_MESSAGE,e,"ACTOR REDEEM POINT NOT FOUND",null);
        }
    }

    @Override
    public void askActorAssetRedeemForConnection(List<ActorAssetRedeemPoint> actorAssetRedeem) throws CantAskConnectionActorAssetException, CantRequestAlreadySendActorAssetException {
        try {
            ActorAssetUser actorAssetUser = actorAssetUserManager.getActorAssetUser();
            if (actorAssetUser != null) {
            blockchainNetworkType = assetUserWalletSubAppModuleManager.getSelectedNetwork();

                for (ActorAssetRedeemPoint assetRedeemPoint : actorAssetRedeem) {
                    this.actorAssetRedeemPointManager.askActorAssetRedeemForConnection(
                            actorAssetUser.getActorPublicKey(),
                            assetRedeemPoint.getName(),
                            assetRedeemPoint.getActorPublicKey(),
                            assetRedeemPoint.getProfileImage(),
                            blockchainNetworkType);

                    if (this.actorAssetRedeemPointManager.getActorRedeemPointRegisteredDAPConnectionState(assetRedeemPoint.getActorPublicKey(), blockchainNetworkType) != DAPConnectionState.REGISTERED_REMOTELY) {
                        System.out.println("The User you are trying to connect with is not connected" +
                                "so we send the message to the assetRedeemPointActorNetworkService");
                        this.assetRedeemPointActorNetworkServiceManager.askConnectionActorAsset(
                                actorAssetUser.getActorPublicKey(),
                                actorAssetUser.getName(),
                                Actors.DAP_ASSET_USER,
                                assetRedeemPoint.getActorPublicKey(),
                                assetRedeemPoint.getName(),
                                Actors.DAP_ASSET_REDEEM_POINT,
                                actorAssetUser.getProfileImage(),
                                blockchainNetworkType);
                    } else {
                        this.assetRedeemPointActorNetworkServiceManager.acceptConnectionActorAsset(actorAssetUser.getActorPublicKey(), assetRedeemPoint.getActorPublicKey());
                        System.out.println("The actor asset Redeem Point is connected");
                    }
                }
            }
            else
            {
                throw new CantConnectToActorAssetException(CantConnectToActorAssetException.DEFAULT_MESSAGE, null, "There was an error connecting to users. No identity", null);
            }
        } catch (CantAskConnectionActorAssetException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAskConnectionActorAssetException("", e, "", "");
        } catch (CantRequestAlreadySendActorAssetException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAskConnectionActorAssetException("", e, "", "actor ASSET ISSUER request already send");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAskConnectionActorAssetException("CAN'T ASK ACTOR ASSET ISSUER CONNECTION", FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public void acceptActorAssetRedeem(String actorAssetRedeemInPublicKey, String actorAssetRedeemToAddPublicKey) throws CantAcceptActorAssetUserException {
        try {

            this.actorAssetRedeemPointManager.acceptActorAssetRedeem(actorAssetRedeemInPublicKey, actorAssetRedeemToAddPublicKey);

            this.assetRedeemPointActorNetworkServiceManager.acceptConnectionActorAsset(actorAssetRedeemInPublicKey, actorAssetRedeemToAddPublicKey);

        } catch (CantAcceptActorAssetUserException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptActorAssetUserException("CAN'T ACCEPT ACTOR REDEEM POINT CONNECTION - KEY " + actorAssetRedeemToAddPublicKey, e, "", "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptActorAssetUserException("CAN'T ACCEPT ACTOR REDEEM POINT CONNECTION - KEY " + actorAssetRedeemToAddPublicKey, FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public void denyConnectionActorAssetRedeem(String actorAssetRedeemLoggedInPublicKey, String actorAssetRedeemToRejectPublicKey) throws CantDenyConnectionActorAssetException {
        try {

            this.actorAssetRedeemPointManager.denyConnectionActorAssetRedeem(actorAssetRedeemLoggedInPublicKey, actorAssetRedeemToRejectPublicKey);

            this.assetRedeemPointActorNetworkServiceManager.denyConnectionActorAsset(actorAssetRedeemLoggedInPublicKey, actorAssetRedeemToRejectPublicKey);

        } catch (CantDenyConnectionActorAssetException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDenyConnectionActorAssetException("CAN'T DENY ACTOR REDEEM POINT CONNECTION - KEY:" + actorAssetRedeemToRejectPublicKey, e, "", "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDenyConnectionActorAssetException("CAN'T DENY ACTOR REDEEM POINT CONNECTION - KEY:" + actorAssetRedeemToRejectPublicKey, FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public void disconnectToActorAssetRedeemPoint(ActorAssetRedeemPoint redeemPoint) throws CantDisconnectAssetActorException {
        try {

            actorAssetRedeemPointManager.disconnectToActorAssetRedeemPoint(redeemPoint.getActorPublicKey(), assetUserWalletSubAppModuleManager.getSelectedNetwork());

            this.assetRedeemPointActorNetworkServiceManager.disconnectConnectionActorAsset(redeemPoint.getActorPublicKey(), redeemPoint.getActorPublicKey());

        } catch (CantDisconnectAssetActorException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDisconnectAssetActorException("CAN'T DISCONNECT ACTOR REDEEM POINT CONNECTION - KEY:" + redeemPoint.getActorPublicKey(), FermatException.wrapException(e), "", "unknown exception");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDisconnectAssetActorException("CAN'T DISCONNECT ACTOR REDEEM POINT CONNECTION - KEY:" + redeemPoint.getActorPublicKey(), FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public void cancelActorAssetRedeem(String actorAssetRedeemToCancelPublicKey) throws CantCancelConnectionActorAssetException {
        try {

            this.actorAssetRedeemPointManager.cancelActorAssetRedeem(actorAssetRedeemToCancelPublicKey);

//            this.assetRedeemPointActorNetworkServiceManager.cancelConnectionActorAsset(actorAssetRedeemLoggedInPublicKey, actorAssetRedeemToCancelPublicKey);

        } catch (CantCancelConnectionActorAssetException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCancelConnectionActorAssetException("CAN'T CANCEL ACTOR REDEEM POINT CONNECTION - KEY:" + actorAssetRedeemToCancelPublicKey, e, "", "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCancelConnectionActorAssetException("CAN'T CANCEL ACTOR REDEEM POINT CONNECTION - KEY:" + actorAssetRedeemToCancelPublicKey, FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public List<ActorAssetRedeemPoint> getWaitingYourConnectionActorAssetRedeem(String actorAssetRedeemLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException {
        try {

//            for (ActorAssetUser intraUserActor : actorsList) {
//                intraUserList.add(new IntraUserModuleInformation(intraUserActor.getName(),"",intraUserActor.getPublicKey(),intraUserActor.getProfileImage(),intraUserActor.getContactState()));
//            }

            return this.actorAssetRedeemPointManager.getWaitingYourConnectionActorAssetRedeem(actorAssetRedeemLoggedInPublicKey, max, offset);
        } catch (CantGetActorAssetWaitingException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR REDEEM POINT WAITING YOUR ACCEPTANCE", e, "", "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR REDEEM POINT WAITING YOUR ACCEPTANCE", FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public List<ActorAssetRedeemPoint> getWaitingTheirConnectionActorAssetRedeem(String actorAssetRedeemLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException {
        try {
//            List<ActorAssetUser> intraUserList = new ArrayList<ActorAssetUser>();

            //            for (ActorAssetUser intraUserActor : actorsList) {
//                intraUserList.add(new IntraUserModuleInformation(intraUserActor.getName(),"",intraUserActor.getPublicKey(),intraUserActor.getProfileImage(),intraUserActor.getContactState()));
//            }
            return this.actorAssetRedeemPointManager.getWaitingTheirConnectionActorAssetRedeem(actorAssetRedeemLoggedInPublicKey, max, offset);
        } catch (CantGetActorAssetWaitingException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR REDEEM POINT WAITING THEIR ACCEPTANCE", e, "", "Error on ACTOR REDEEM POINT MANAGER");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR REDEEM POINT WAITING THEIR ACCEPTANCE", FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public ActorAssetRedeemPoint getLastNotification(String actorAssetUserInPublicKey) throws CantGetActorAssetNotificationException {
        try {
            return this.actorAssetRedeemPointManager.getLastNotificationActorAssetRedeem(actorAssetUserInPublicKey);
        } catch (CantGetActorAssetNotificationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetActorAssetNotificationException("CAN'T GET ACTOR REDEEM POINT LAST NOTIFICATION", e, "", "Error on ACTOR ASSET Manager");
        }
    }

    @Override
    public int getWaitingYourConnectionActorAssetRedeemCount() {
        //TODO: falta que este metodo que devuelva la cantidad de request de conexion que tenes
        try {

            if (getActiveAssetRedeemPointIdentity() != null) {
                return getWaitingYourConnectionActorAssetRedeem(getActiveAssetRedeemPointIdentity().getPublicKey(), 100, 0).size();
            }

        } catch (CantGetActorAssetWaitingException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            e.printStackTrace();
        } catch (CantGetIdentityRedeemPointException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            e.printStackTrace();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public SettingsManager<RedeemPointSettings> getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return redeemPointIdentityManager.getSelectedActorIdentity();
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        redeemPointIdentityManager.createNewRedeemPoint(name, profile_img);
    }

    @Override
    public void setAppPublicKey(String publicKey) {
        this.appPublicKey = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        int[] notifications = new int[5];
        try {
            if (getSelectedActorIdentity() != null)
                notifications[2] = actorAssetRedeemPointManager.getWaitingYourConnectionActorAssetRedeem(getSelectedActorIdentity().getPublicKey(), 99, 0).size();
            else
                notifications[2] = 0;
        } catch (CantGetActorAssetWaitingException | CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            e.printStackTrace();
        }
        return notifications;
    }
}
