package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantRequestListActorAssetRedeemPointRegisteredException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.RequestedListNotReadyRecevivedException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantRegisterActorAssetRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantRegisterActorAssetRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantSendMessageException;

import java.util.List;

/**
 * Created by franklin on 15/10/15.
 */
public interface AssetRedeemPointActorNetworkServiceManager {
    /**
     * Register the ActorAssetUser in the cloud server like online
     *
     * @param actorAssetRedeemPointToRegister
     * @throws CantRegisterActorAssetRedeemPointException
     */
    public void registerActorAssetRedeemPoint(ActorAssetRedeemPoint actorAssetRedeemPointToRegister) throws CantRegisterActorAssetRedeemPointException;

    /**
     *
     * @param actorAssetRedeemPoint who send the message
     * @param actorAssetRedeemDestination who recibe the message
     * @param msjContent  the message content
     *
     * @throws CantSendMessageException
     */
    public void sendMessage(ActorAssetRedeemPoint actorAssetRedeemPoint, ActorAssetRedeemPoint actorAssetRedeemDestination, String msjContent)  throws CantSendMessageException;

    /**
     * Request the list of the actorAssetUser register in the server
     *
     * @throws CantRegisterActorAssetRedeemPointException
     */
    public void requestListActorAssetRedeemPointRegistered()  throws CantRegisterActorAssetRedeemPointException;

    /**
     * Get the content of the list previously requested, this method have to call after the
     * the notification event is receive <code>Nombre del Evento</>
     *
     * @return List<ActorAssetRedeemPoint>
     */
    public List<ActorAssetRedeemPoint> getListActorAssetRedeemPointRegistered() throws CantRequestListActorAssetRedeemPointRegisteredException;


}
