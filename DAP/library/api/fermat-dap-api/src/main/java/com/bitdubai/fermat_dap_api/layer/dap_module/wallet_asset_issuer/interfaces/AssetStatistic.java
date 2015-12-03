package com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetCurrentStatus;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;

import java.util.Date;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 01/12/15.
 *
 * @since version 1.0
 */
public interface AssetStatistic {
    /**
     * This method returns the public key associated with the asset for this statistic, even
     * when this public key is not very useful for the statistic we'll have it in this object
     * in case we need to use it in the future.
     *
     * @return {@link String} with the public key of the asset.
     */
    String assetPublicKey();

    /**
     * This method search for user that owns this asset.
     *
     * @return {@link ActorAssetUser} that owns the asset or {@code null} if
     * this asset has not been delivered yet.
     */
    ActorAssetUser getAssetOwner();

    /**
     * This method returns the date corresponding to the moment when this asset
     * was sent to an user.
     *
     * @return {@link Date instance}
     */
    Date getDistributionDate();

    /**
     * This method returns the date corresponding to the moment when this asset
     * was used to the user it was distributed.
     *
     * @return {@link Date instance} or null if the asset is unused.
     */
    Date getAssetUsedDate();

    /**
     * This method returns the current status of the Asset, for version 1.0 the only
     * possible statuses are redeemed, appropriated or unused.
     *
     * @return {@link AssetCurrentStatus} that represents what has been done with this asset.
     */
    AssetCurrentStatus getStatus();

    /**
     * This method search for the redeem point where this asset has been redeemed.
     * If the status of this asset is not redeemed, this method will always return null.
     *
     * @return {@link ActorAssetRedeemPoint} instance if the asset has been redeemed, otherwise null.
     */
    ActorAssetRedeemPoint getRedeemPoint();

    /**
     * This method search for the asset name that is associated with this statistic so we can
     * group all the assets with the same name and have a global statistic.
     *
     * @return {@link String} with the asset's name.
     */
    String getAssetName();
}
