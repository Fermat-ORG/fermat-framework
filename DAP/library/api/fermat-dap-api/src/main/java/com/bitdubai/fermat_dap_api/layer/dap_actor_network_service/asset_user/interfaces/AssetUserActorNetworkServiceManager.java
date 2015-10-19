package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantConnecToException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestListActorAssetUserRegisteredException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantSendMessageException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.RequestedListNotReadyRecevivedException;

import java.util.List;

/**
 * Created by root on 07/10/15.
 */
public interface AssetUserActorNetworkServiceManager {

    /**
     * Register the ActorAssetUser in the cloud server like online
     *
     * @param actorAssetUserToRegister
     * @throws CantRegisterActorAssetUserException
     */
    public void registerActorAssetUser(ActorAssetUser actorAssetUserToRegister) throws CantRegisterActorAssetUserException;

    /**
     *
     * @param actorAssetUserSender who send the message
     * @param actorAssetUserDestination who recibe the message
     * @param msjContent  the message content
     *
     * @throws CantSendMessageException
     */
    public void sendMessage(ActorAssetUser actorAssetUserSender, ActorAssetUser actorAssetUserDestination, String msjContent)  throws CantSendMessageException;

    /**
     * Request the list of the actorAssetUser register in the server
     *
     * @throws CantRequestListActorAssetUserRegisteredException
     */
    public void requestListActorAssetUserRegistered()  throws CantRequestListActorAssetUserRegisteredException;

    /**
     * Get the content of the list previously requested, this method have to call after the
     * the notification event is receive <code>Nombre del Evento</>
     *
     * @return List<ActorAssetUser>
     */
    public List<ActorAssetUser> getListActorAssetUserRegistered() throws RequestedListNotReadyRecevivedException;

}
