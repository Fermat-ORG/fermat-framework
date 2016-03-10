package com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantAssetIssuerActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantConnectToActorAssetRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAskConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCancelConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDenyConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantRequestAlreadySendActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.AssetIssuerSettings;

import java.util.List;

/**
 * Created by Nerio on 13/10/15.
 */
public interface AssetIssuerCommunitySubAppModuleManager extends ModuleManager<AssetIssuerSettings, ActiveActorIdentityInformation> {

    List<AssetIssuerActorRecord> getAllActorAssetIssuerRegistered() throws CantGetAssetIssuerActorsException;

    List<ActorAssetIssuer> getAllActorAssetIssuerConnected() throws CantGetAssetIssuerActorsException;

    DAPConnectionState getActorIssuerRegisteredDAPConnectionState(String actorAssetPublicKey) throws CantGetAssetIssuerActorsException;

    ActorAssetIssuer getActorIssuer(String actorPublicKey) throws CantGetAssetIssuerActorsException, CantAssetIssuerActorNotFoundException;

    void connectToActorAssetIssuer(ActorAssetRedeemPoint requester, List<ActorAssetIssuer> actorAssetIssuers) throws CantConnectToActorAssetRedeemPointException;

//    List<ActorAssetUser> getAllActorAssetUserRegistered() throws CantGetAssetUserActorsException;
//
//    List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointRegistered() throws CantGetAssetRedeemPointActorsException;

    IdentityAssetIssuer getActiveAssetIssuerIdentity() throws CantGetIdentityAssetIssuerException;

    void askActorAssetIssuerForConnection(List<ActorAssetIssuer> actorAssetIssuers) throws CantAskConnectionActorAssetException, CantRequestAlreadySendActorAssetException;

    void acceptActorAssetIssuer(String actorAssetIssuerPublicKey, ActorAssetIssuer actorAssetIssuer) throws CantAcceptActorAssetUserException;

    void denyConnectionActorAssetIssuer(String actorAssetIssuerLoggedPublicKey, ActorAssetIssuer actorAssetIssuer) throws CantDenyConnectionActorAssetException;

//    void disconnectActorAssetIssuer(String actorIssuerLoggedPublicKey, String actorAssetIssuerToDisconnectPublicKey) throws CantDisconnectAssetUserActorException;

    void cancelActorAssetIssuer(String actorAssetIssuerLoggedPublicKey, ActorAssetIssuer actorAssetToCancel) throws CantCancelConnectionActorAssetException;

    List<ActorAssetIssuer> getWaitingYourConnectionActorAssetIssuer(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException;

    List<ActorAssetIssuer> getWaitingTheirConnectionActorAssetIssuer(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException;

    ActorAssetIssuer getLastNotification(String actorAssetIssuerPublicKey) throws CantGetActorAssetNotificationException;

    int getWaitingYourConnectionActorAssetIssuerCount();

}
