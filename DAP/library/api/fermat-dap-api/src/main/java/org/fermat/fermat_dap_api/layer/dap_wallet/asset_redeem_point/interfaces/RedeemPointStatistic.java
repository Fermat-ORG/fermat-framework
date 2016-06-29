package org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/01/16.
 * <p/>
 * This class is a wrapper for all the statistic fields that stores the redeem point.
 */
public interface RedeemPointStatistic extends Serializable {

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
     * The {@link org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser} that redeemed the asset,
     * if this method doesn't find any user on {@link ActorAssetUserManager}
     * then it will return a fake user with public key and unknown name.
     *
     * @return {@link org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser} instance.
     */
    org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser userThatRedeemed();

    /**
     * The exact time when that redemption happen.
     *
     * @return {@link Date} instance.
     */
    Date redemptionTime();
}
