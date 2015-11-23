package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
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
public interface AssetRedeemPointActorNetworkServiceManager extends FermatManager {
    /**
     * Register the ActorAssetUser in the cloud server like online
     *
     * @param actorAssetRedeemPointToRegister
     * @throws CantRegisterActorAssetRedeemPointException
     */
    public void registerActorAssetRedeemPoint(ActorAssetRedeemPoint actorAssetRedeemPointToRegister) throws CantRegisterActorAssetRedeemPointException;

    /**
     * Get the content of the list previously requested, this method have to call after the
     * the notification event is receive <code>Nombre del Evento</>
     *
     * @return List<ActorAssetRedeemPoint>
     */
    public List<ActorAssetRedeemPoint> getListActorAssetRedeemPointRegistered() throws CantRequestListActorAssetRedeemPointRegisteredException;


}
