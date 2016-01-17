package com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.RedeemPointStatistic;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/01/16.
 */
public class RedeemPointStatisticImpl implements RedeemPointStatistic {

    //VARIABLE DECLARATION

    private UUID statisticId;
    private DigitalAssetMetadata assetMetadata;
    private ActorAssetUser actorAssetUser;
    private Date redemptionDate;

    //CONSTRUCTORS


    public RedeemPointStatisticImpl() {
    }

    public RedeemPointStatisticImpl(UUID statisticId, DigitalAssetMetadata assetMetadata, ActorAssetUser actorAssetUser, Date redemptionDate) {
        this.statisticId = statisticId;
        this.assetMetadata = assetMetadata;
        this.actorAssetUser = actorAssetUser;
        this.redemptionDate = redemptionDate;
    }

    //PUBLIC METHODS

    /**
     * An unique identifier for every statistic entry. Created because an user can
     * redeem the same asset multiple times in a specific redeem point.
     *
     * @return {@link UUID} instance.
     */
    @Override
    public UUID statisticId() {
        return statisticId;
    }

    /**
     * The {@link DigitalAssetMetadata} associated with the redemption.
     *
     * @return {@link DigitalAssetMetadata} instance.
     */
    @Override
    public DigitalAssetMetadata assetRedeemed() {
        return assetMetadata;
    }

    /**
     * The {@link ActorAssetUser} that redeemed the asset,
     * if this method doesn't find any user on {@link ActorAssetUserManager}
     * then it will return a fake user with public key and unknown name.
     *
     * @return {@link ActorAssetUser} instance.
     */
    @Override
    public ActorAssetUser userThatRedeemed() {
        return actorAssetUser;
    }


    /**
     * The exact time when that redemption happen.
     *
     * @return {@link Date} instance.
     */
    @Override
    public Date redemptionTime() {
        return redemptionDate;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    public void setRedemptionDate(long redemptionDate) {
        this.redemptionDate = new Date(redemptionDate);
    }

    public void setStatisticId(String statisticId) {
        this.statisticId = UUID.fromString(statisticId);
    }

    public void setActorAssetUser(ActorAssetUser actorAssetUser) {
        this.actorAssetUser = actorAssetUser;
    }

    public void setAssetMetadata(DigitalAssetMetadata assetMetadata) {
        this.assetMetadata = assetMetadata;
    }

    //INNER CLASSES
}
