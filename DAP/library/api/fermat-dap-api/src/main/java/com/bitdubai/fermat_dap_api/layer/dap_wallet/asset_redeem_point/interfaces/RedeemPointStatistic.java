package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

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
     * The {@link DigitalAssetMetadata} associated with the redemption.
     *
     * @return {@link DigitalAssetMetadata} instance.
     */
    DigitalAssetMetadata assetRedeemed();

    /**
     * The {@link ActorAssetUser} that redeemed the asset,
     * if this method doesn't find any user on {@link com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager}
     * then it will return a fake user with public key and unknown name.
     *
     * @return {@link ActorAssetUser} instance.
     */
    ActorAssetUser userThatRedeemed();

    /**
     * The exact time when that redemption happen.
     *
     * @return {@link Date} instance.
     */
    Date redemptionTime();
}
