package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPNetworkService;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantRegisterActorAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantRequestListActorAssetIssuerRegisteredException;

import java.util.List;

/**
 * Created by franklin on 15/10/15.
 */
public interface AssetIssuerActorNetworkServiceManager extends FermatManager, DAPNetworkService {
    /**
     * Register the ActorAssetUser in the cloud server like online
     *
     * @param actorAssetIssuerToRegister
     * @throws CantRegisterActorAssetIssuerException
     */
    void registerActorAssetIssuer(ActorAssetIssuer actorAssetIssuerToRegister) throws CantRegisterActorAssetIssuerException;

    /**
     * Update the ActorAssetUser in the cloud server like online
     *
     * @param actorAssetIssuerToRegister
     * @throws CantRegisterActorAssetIssuerException
     */
    void updateActorAssetIssuer(ActorAssetIssuer actorAssetIssuerToRegister) throws CantRegisterActorAssetIssuerException;

    /**
     * Get the content of the list previously requested, this method have to call after the
     * the notification event is receive <code>Nombre del Evento</>
     *
     * @return List<ActorAssetUser>
     */
    List<ActorAssetIssuer> getListActorAssetIssuerRegistered() throws CantRequestListActorAssetIssuerRegisteredException;

}
