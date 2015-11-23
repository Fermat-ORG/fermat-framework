package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantAssetIssuerActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantConnectToAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantCreateActorAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantRegisterActorAssetIssuerException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface ActorAssetIssuerManager extends FermatManager {

    /**
     * The method <code>getActorByPublicKey</code> shows the information associated with the actorPublicKey
     *
     * @param actorPublicKey                     The public key of the Asset Actor Issuer
     * @return                                   THe information associated with the actorPublicKey.
     * @throws CantGetAssetIssuerActorsException
     * @throws CantAssetIssuerActorNotFoundException
     */
    ActorAssetIssuer getActorByPublicKey(String actorPublicKey) throws CantGetAssetIssuerActorsException, CantAssetIssuerActorNotFoundException;

    /**
     * The method <code>createActorAssetIssuerFactory</code> create Actor by a Identity
     *
     * @param assetIssuerActorPublicKey                 Referred to the Identity publicKey
     * @param assetIssuerActorName                      Referred to the Identity Alias
     * @param assetIssuerActorprofileImage              Referred to the Identity profileImage
     * @throws CantCreateActorAssetIssuerException
     */
    void createActorAssetIssuerFactory(String assetIssuerActorPublicKey, String assetIssuerActorName, byte[] assetIssuerActorprofileImage) throws CantCreateActorAssetIssuerException;

    /**
     * The method <code>getActorPublicKey</code> get All Information about Actor
     *
     * @throws CantGetAssetIssuerActorsException
     */
    ActorAssetIssuer getActorAssetIssuer() throws CantGetAssetIssuerActorsException;

    /**
     * The method <code>getAllAssetIssuerActorInTableRegistered</code> get All Actors Registered in Actor Network Service
     * and used in Sub App Community
     *
     * @throws CantGetAssetIssuerActorsException
     */
    List<ActorAssetIssuer> getAllAssetIssuerActorInTableRegistered() throws CantGetAssetIssuerActorsException;

    /**
     * The method <code>getAllAssetIssuerActorConnected</code> receives All Actors with have CryptoAddress in BD
     *
     * @throws CantGetAssetIssuerActorsException
     */
    List<ActorAssetIssuer> getAllAssetIssuerActorConnected() throws CantGetAssetIssuerActorsException;

    /**
     * The method <code>connectToActorAssetRedeemPoint</code> Stablish Connection
     * with Issuer (Requester) and Lists Redeem Point by associate
     *
     * @throws CantConnectToAssetIssuerException
     */
    void connectToActorAssetIssuer(ActorAssetRedeemPoint requester, List<ActorAssetIssuer> actorAssetIssuers) throws CantConnectToAssetIssuerException;
}
