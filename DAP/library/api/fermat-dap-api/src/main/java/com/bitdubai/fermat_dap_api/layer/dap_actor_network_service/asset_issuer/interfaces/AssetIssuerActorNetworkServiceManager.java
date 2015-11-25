package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantRegisterActorAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantRequestListActorAssetIssuerRegisteredException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantSendMessageException;

import java.util.List;

/**
 * Created by franklin on 15/10/15.
 */
public interface AssetIssuerActorNetworkServiceManager extends FermatManager {
    /**
     * Register the ActorAssetUser in the cloud server like online
     *
     * @param actorAssetIssuerToRegister
     * @throws CantRegisterActorAssetIssuerException
     */
    public void registerActorAssetIssuer(ActorAssetIssuer actorAssetIssuerToRegister) throws CantRegisterActorAssetIssuerException;

    /**
     *
     * @param dapActor who send the message
     * @param actorAssetIssuerDestination who recibe the message
     * @param msjContent  the message content
     *
     * @throws CantSendMessageException
     */
    public void sendMessage(DAPActor dapActor, ActorAssetIssuer actorAssetIssuerDestination, String msjContent)  throws CantSendMessageException;

    /**
     * Request the list of the actorAssetUser register in the server
     *
     * @throws CantRequestListActorAssetIssuerRegisteredException
     */
    public void requestListActorAssetIssuerRegistered()  throws CantRequestListActorAssetIssuerRegisteredException;

    /**
     * Get the content of the list previously requested, this method have to call after the
     * the notification event is receive <code>Nombre del Evento</>
     *
     * @return List<ActorAssetUser>
     */
    public List<ActorAssetIssuer> getListActorAssetIssuerRegistered() throws CantRequestListActorAssetIssuerRegisteredException;

}
