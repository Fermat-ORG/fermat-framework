package com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces;


import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantAssetRedeemPointActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantCreateActorRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface ActorAssetRedeemPointManager {

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
    List<ActorAssetRedeemPoint> getAllAssetRedeemPointActorRegistered() throws CantGetAssetRedeemPointActorsException;

    /**
     * The method <code>getAllAssetIssuerActorConnected</code> receives All Actors with have CryptoAddress in BD
     *
     * @throws CantGetAssetRedeemPointActorsException
     */
    List<ActorAssetRedeemPoint> getAllRedeemPointActorConnected() throws CantGetAssetRedeemPointActorsException;

    /**
     * The method <code>createActorAssetUserFactory</code> create Actor in Actor Network Service
     *
     * @throws CantCreateActorRedeemPointException
     */
    ActorAssetRedeemPoint createActorAssetRedeemPointFactory(String assetRedeemPointActorPublicKey, String assetRedeemPointActorName, byte[] assetRedeemPointActorprofileImage, Location assetRedeemPointActorlocation) throws CantCreateActorRedeemPointException;

}
