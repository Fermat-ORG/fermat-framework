package org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantAssetIssuerActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantConnectToActorAssetRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAskConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCancelConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDenyConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantRequestAlreadySendActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.AssetIssuerSettings;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nerio on 13/10/15.
 */
public interface AssetIssuerCommunitySubAppModuleManager extends ModuleManager, ModuleSettingsImpl<AssetIssuerSettings>, Serializable {

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

    void cancelActorAssetIssuer(ActorAssetIssuer actorAssetToCancel) throws CantCancelConnectionActorAssetException;

    List<ActorAssetIssuer> getWaitingYourConnectionActorAssetIssuer(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException;

    List<ActorAssetIssuer> getWaitingTheirConnectionActorAssetIssuer(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException;

    ActorAssetIssuer getLastNotification(String actorAssetIssuerPublicKey) throws CantGetActorAssetNotificationException;

    int getWaitingYourConnectionActorAssetIssuerCount();
}