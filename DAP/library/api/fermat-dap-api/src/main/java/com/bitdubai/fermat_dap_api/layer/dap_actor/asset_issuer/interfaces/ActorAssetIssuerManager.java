package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantConnectToAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantCreateActorAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantConnectToActorAssetRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface ActorAssetIssuerManager {

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
     * The method <code>registerActorInActorNetowrkSerice</code> Register or Add Actor a Lst in
     * Actor Network Service
     *
     * @throws CantCreateActorAssetIssuerException
     */
    void registerActorInActorNetowrkSerice() throws CantCreateActorAssetIssuerException;

    /**
     * The method <code>connectToActorAssetRedeemPoint</code> Stablish Connection
     * with Issuer (Requester) and Lists Redeem Point by associate
     *
     * @throws CantConnectToAssetIssuerException
     */
    void connectToActorAssetIssuer(ActorAssetRedeemPoint requester, List<ActorAssetIssuer> actorAssetIssuers) throws CantConnectToAssetIssuerException;

    /**
     * The method <code>createActorAssetUserFactory</code> create Actor in Actor Network Service
     *
     * @throws CantCreateActorAssetIssuerException
     */
//    ActorAssetIssuer createActorAssetIssuerFactory(String assetIssuerActorPublicKey, String assetIssuerActorName, byte[] assetIssuerActorprofileImage, Location assetIssuerActorlocation) throws CantCreateActorAssetIssuerException;

}
