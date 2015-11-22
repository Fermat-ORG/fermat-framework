package com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantAssetRedeemPointActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantConnectToActorAssetRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantCreateActorRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface ActorAssetRedeemPointManager {

    /**
     * The method <code>getActorByPublicKey</code> shows the information associated with the actorPublicKey
     *
     * @param actorPublicKey                    The public key of the Asset Actor Redeem Point
     * @return                                  The information associated with the actorPublicKey.
     * @throws CantGetAssetRedeemPointActorsException
     * @throws CantAssetRedeemPointActorNotFoundException
     */
    ActorAssetRedeemPoint getActorByPublicKey(String actorPublicKey) throws CantGetAssetRedeemPointActorsException, CantAssetRedeemPointActorNotFoundException;

    /**
     * The method <code>createActorAssetRedeemPointFactory</code> create Actor by a Identity
     *
     * @param assetRedeemPointActorPublicKey                Referred to the Identity publicKey
     * @param assetRedeemPointActorName                     Referred to the Identity Alias
     * @param assetRedeemPointActorprofileImage             Referred to the Identity profileImage
     * @throws CantCreateActorRedeemPointException
     */
    void createActorAssetRedeemPointFactory(String assetRedeemPointActorPublicKey, String assetRedeemPointActorName, byte[] assetRedeemPointActorprofileImage) throws CantCreateActorRedeemPointException;

    /**
     * The method <code>getActorAssetRedeemPoint</code> get All Information about Actor
     *
     * @throws CantGetAssetRedeemPointActorsException
     */
    ActorAssetRedeemPoint getActorAssetRedeemPoint() throws CantGetAssetRedeemPointActorsException;

    /**
     * The method <code>getAllAssetUserActorRegistered</code> get All Actors Registered in Actor Network Service
     * and used in Sub App Community
     *
     * @throws CantGetAssetRedeemPointActorsException
     */
    List<ActorAssetRedeemPoint> getAllAssetRedeemPointActorInTableRegistered() throws CantGetAssetRedeemPointActorsException;

    /**
     * The method <code>getAllAssetIssuerActorConnected</code> receives All Actors with have CryptoAddress in BD
     *
     * @throws CantGetAssetRedeemPointActorsException
     */
    List<ActorAssetRedeemPoint> getAllRedeemPointActorConnected() throws CantGetAssetRedeemPointActorsException;

    /**
     * The method <code>connectToActorAssetUser</code> Stablish Connection
     * with Issuer (Requester) and Lists Users for get a CryptoAdress (Delivered)
     *
     * @throws CantConnectToActorAssetRedeemPointException
     */
    void connectToActorAssetRedeemPoint(ActorAssetUser requester, List<ActorAssetRedeemPoint> actorAssetRedeemPoints) throws CantConnectToActorAssetRedeemPointException;
}
