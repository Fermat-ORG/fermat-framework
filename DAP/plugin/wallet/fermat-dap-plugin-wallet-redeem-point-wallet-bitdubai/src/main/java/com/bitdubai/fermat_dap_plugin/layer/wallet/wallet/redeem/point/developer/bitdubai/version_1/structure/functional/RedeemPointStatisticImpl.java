package com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.RedeemPointStatistic;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/01/16.
 */
public class RedeemPointStatisticImpl implements RedeemPointStatistic {

    //VARIABLE DECLARATION

    private UUID statisticId;
    private String assetPk;
    private String userPk;
    private Date redemptionDate;

    //CONSTRUCTORS


    public RedeemPointStatisticImpl() {
    }

    public RedeemPointStatisticImpl(UUID statisticId, String assetPk, String userPk, Date redemptionDate) {
        this.statisticId = statisticId;
        this.assetPk = assetPk;
        this.userPk = userPk;
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
     * The asset public key associated with the redemption.
     * If you want to get all the remaining information for that asset
     * you'd have to use {@link AssetRedeemPointWallet#getDigitalAssetMetadata(String)}.
     *
     * @return {@link String}
     */
    @Override
    public String assetPublicKey() {
        return assetPk;
    }

    /**
     * The user public key associated with the redemption.
     * If you want to get all the remaining information for that asset
     * you'd have to use {@link ActorAssetUserManager#getActorByPublicKey(String)}.
     *
     * @return
     */
    @Override
    public String userPublicKey() {
        return userPk;
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

    public void setAssetPk(String assetPk) {
        this.assetPk = assetPk;
    }

    public void setUserPk(String userPk) {
        this.userPk = userPk;
    }

    public void setRedemptionDate(long redemptionDate) {
        this.redemptionDate = new Date(redemptionDate);
    }

    public void setStatisticId(String statisticId) {
        this.statisticId = UUID.fromString(statisticId);
    }

    //INNER CLASSES
}
