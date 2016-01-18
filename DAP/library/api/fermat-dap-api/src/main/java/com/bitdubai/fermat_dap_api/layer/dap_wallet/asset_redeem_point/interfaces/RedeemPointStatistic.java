package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/01/16.
 * <p/>
 * This class is a wrapper for all the statistic fields that stores the redeem point.
 */
public interface RedeemPointStatistic {

    /**
     * An unique identifier for every statistic entry. Created because an user can
     * redeem the same asset multiple times in a specific redeem point.
     *
     * @return {@link UUID} instance.
     */
    UUID statisticId();

    /**
     * The asset public key associated with the redemption.
     * If you want to get all the remaining information for that asset
     * you'd have to use {@link AssetRedeemPointWallet#getDigitalAssetMetadata(String)}.
     *
     * @return {@link String}
     */
    String assetPublicKey();

    /**
     * The user public key associated with the redemption.
     * If you want to get all the remaining information for that asset
     * you'd have to use {@link com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager#getActorByPublicKey(String)}.
     *
     * @return
     */
    String userPublicKey();

    /**
     * The exact time when that redemption happen.
     *
     * @return {@link Date} instance.
     */
    Date redemptionTime();
}
