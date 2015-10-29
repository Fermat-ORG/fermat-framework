package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestListActorAssetUserRegisteredException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantSendCryptoAddressException;

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
     * Request a Crypto Address to the ActorAssetUser
     *
     * @param actorAssetIssuerSender
     * @param actorAssetUserDestination
     * @throws CantRequestCryptoAddressException
     */
    public void requestCryptoAddress(ActorAssetIssuer actorAssetIssuerSender, ActorAssetUser actorAssetUserDestination) throws CantRequestCryptoAddressException;

    /**
     * Send a Crypto Address to the ActorAssetIssuer
     *
     * @param actorAssetUserSender
     * @param actorAssetIssuerDestination
     * @param cryptoAddress
     * @throws CantSendCryptoAddressException
     */
    public void sendCryptoAddress(ActorAssetUser actorAssetUserSender, ActorAssetIssuer actorAssetIssuerDestination, CryptoAddress cryptoAddress) throws CantSendCryptoAddressException;

    /**
     * Get the list of the ActorAssetUser registered
     *
     * @return List<ActorAssetUser>
     */
    public List<ActorAssetUser> getListActorAssetUserRegistered() throws CantRequestListActorAssetUserRegisteredException;

}
