package org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import java.util.List;

/**
 * Created by Nerio on 13/10/15.
 */
public interface AssetIssuerCommunitySubAppModuleManager extends ModuleManager<org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.AssetIssuerSettings, ActiveActorIdentityInformation> {

    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord> getAllActorAssetIssuerRegistered() throws org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;

    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer> getAllActorAssetIssuerConnected() throws org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;

    org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState getActorIssuerRegisteredDAPConnectionState(String actorAssetPublicKey) throws org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;

    org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer getActorIssuer(String actorPublicKey) throws org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException, org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantAssetIssuerActorNotFoundException;

    void connectToActorAssetIssuer(org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint requester, List<org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer> actorAssetIssuers) throws org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantConnectToActorAssetRedeemPointException;

//    List<ActorAssetUser> getAllActorAssetUserRegistered() throws CantGetAssetUserActorsException;
//
//    List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointRegistered() throws CantGetAssetRedeemPointActorsException;

    org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer getActiveAssetIssuerIdentity() throws org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetIssuerException;

    void askActorAssetIssuerForConnection(List<org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer> actorAssetIssuers) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAskConnectionActorAssetException, org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantRequestAlreadySendActorAssetException;

    void acceptActorAssetIssuer(String actorAssetIssuerPublicKey, org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer actorAssetIssuer) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException;

    void denyConnectionActorAssetIssuer(String actorAssetIssuerLoggedPublicKey, org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer actorAssetIssuer) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDenyConnectionActorAssetException;

//    void disconnectActorAssetIssuer(String actorIssuerLoggedPublicKey, String actorAssetIssuerToDisconnectPublicKey) throws CantDisconnectAssetUserActorException;

    void cancelActorAssetIssuer(org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer actorAssetToCancel) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCancelConnectionActorAssetException;

    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer> getWaitingYourConnectionActorAssetIssuer(String actorAssetUserLoggedInPublicKey, int max, int offset) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException;

    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer> getWaitingTheirConnectionActorAssetIssuer(String actorAssetUserLoggedInPublicKey, int max, int offset) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException;

    org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer getLastNotification(String actorAssetIssuerPublicKey) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;

    int getWaitingYourConnectionActorAssetIssuerCount();

}
