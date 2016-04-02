package com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.exceptions.CantDisconnectAssetActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.exceptions.CantConnectToActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.RedeemPointActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantAssetRedeemPointActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAskConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCancelConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDenyConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantRequestAlreadySendActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;

import java.util.List;

/**
 * Created by Nerio on 13/10/15.
 */
public interface RedeemPointCommunitySubAppModuleManager extends ModuleManager<RedeemPointSettings, ActiveActorIdentityInformation> {

//    List<ActorAssetIssuer> getAllActorAssetIssuerRegistered() throws CantGetAssetIssuerActorsException;
//    List<ActorAssetUser> getAllActorAssetUserRegistered() throws CantGetAssetUserActorsException;

    List<RedeemPointActorRecord> getAllActorAssetRedeemPointRegistered() throws CantGetAssetRedeemPointActorsException;

    List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointConnected() throws CantGetAssetRedeemPointActorsException;

    DAPConnectionState getActorRedeemRegisteredDAPConnectionState(String actorAssetPublicKey) throws CantGetAssetRedeemPointActorsException;

    void connectToActorAssetRedeemPoint(ActorAssetUser requester, List<ActorAssetRedeemPoint> actorAssetRedeemPoints) throws CantConnectToActorAssetException;

    RedeemPointIdentity getActiveAssetRedeemPointIdentity() throws CantGetIdentityRedeemPointException;

    ActorAssetRedeemPoint getActorRedeemPoint(String actorPublicKey) throws CantGetAssetRedeemPointActorsException, CantAssetRedeemPointActorNotFoundException;

    void askActorAssetRedeemForConnection(List<ActorAssetRedeemPoint> actorAssetRedeem) throws CantAskConnectionActorAssetException, CantRequestAlreadySendActorAssetException;

    void acceptActorAssetRedeem(String actorAssetRedeemInPublicKey, ActorAssetRedeemPoint actorAssetRedeemToAdd) throws CantAcceptActorAssetUserException;

    void denyConnectionActorAssetRedeem(String actorAssetRedeemLoggedInPublicKey, ActorAssetRedeemPoint actorAssetRedeemToReject) throws CantDenyConnectionActorAssetException;

//    void disconnectActorAssetUser(String intraUserLoggedInPublicKey, String actorAssetUserToDisconnectPublicKey) throws CantDisconnectAssetUserActorException;

    void disconnectToActorAssetRedeemPoint(ActorAssetRedeemPoint redeemPoint) throws CantDisconnectAssetActorException;
//    void disconnectToActorAssetRedeemPoint(String requester, String redeemPoint) throws CantDisconnectAssetUserActorException;

    void cancelActorAssetRedeem(String actorAssetRedeemToCancelPublicKey) throws CantCancelConnectionActorAssetException;

    List<ActorAssetRedeemPoint> getWaitingYourConnectionActorAssetRedeem(String actorAssetRedeemLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException;

    List<ActorAssetRedeemPoint> getWaitingTheirConnectionActorAssetRedeem(String actorAssetRedeemLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException;

    ActorAssetRedeemPoint getLastNotification(String actorAssetUserInPublicKey) throws CantGetActorAssetNotificationException;

    int getWaitingYourConnectionActorAssetRedeemCount();
}
