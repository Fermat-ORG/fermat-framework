package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetCurrentStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetStatistic;

import java.util.Date;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 02/12/15.
 */
public class AssetStatisticImpl implements AssetStatistic {

    //VARIABLE DECLARATION
    private String assetPublicKey;

    {
        assetPublicKey = Validate.DEFAULT_STRING;
    }

    private AssetCurrentStatus status;

    {
        status = AssetCurrentStatus.ASSET_CREATED;
    }

    private Date distributionDate;

    private Date usageDate;

    private ActorAssetRedeemPoint redeemPoint;

    private String assetName;

    {
        assetName = Validate.DEFAULT_STRING;
    }

    private ActorAssetUser owner;
    //CONSTRUCTORS

    public AssetStatisticImpl() {
    }

    public AssetStatisticImpl(String assetPublicKey, AssetCurrentStatus status, Date distributionDate, Date usageDate, ActorAssetRedeemPoint redeemPoint, String assetName, ActorAssetUser owner) {
        this.assetPublicKey = assetPublicKey;
        this.status = status;
        this.distributionDate = distributionDate;
        this.usageDate = usageDate;
        this.redeemPoint = redeemPoint;
        this.assetName = assetName;
        this.owner = owner;
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    /**
     * This method returns the public key associated with the asset for this statistic, even
     * when this public key is not very useful for the statistic we'll have it in this object
     * in case we need to use it in the future.
     *
     * @return {@link String} with the public key of the asset.
     */
    @Override
    public String assetPublicKey() {
        return assetPublicKey;
    }

    /**
     * This method search for user that owns this asset.
     *
     * @return {@link ActorAssetUser} with the user that owns this asset
     * or {@code null} if this asset has not been delivered yet.
     */
    @Override
    public ActorAssetUser getOwner() {
        return null;
    }

    /**
     * This method returns the date corresponding to the moment when this asset
     * was sent to an user.
     *
     * @return {@link Date instance}
     */
    @Override
    public Date getDistributionDate() {
        return distributionDate;
    }

    /**
     * This method returns the date corresponding to the moment when this asset
     * was used to the user it was distributed.
     *
     * @return {@link Date instance} or null if the asset is unused.
     */
    @Override
    public Date getAssetUsedDate() {
        return usageDate;
    }

    /**
     * This method returns the current status of the Asset, for version 1.0 the only
     * possible statuses are redeemed, appropriated or unused.
     *
     * @return {@link AssetCurrentStatus} that represents what has been done with this asset.
     */
    @Override
    public AssetCurrentStatus getStatus() {
        return status;
    }

    /**
     * This method search for the redeem point where this asset has been redeemed.
     * If the status of this asset is not redeemed, this method will always return null.
     *
     * @return {@link ActorAssetRedeemPoint} instance if the asset has been redeemed, otherwise null.
     */
    @Override
    public ActorAssetRedeemPoint getRedeemPoint() {
        return redeemPoint;
    }

    /**
     * This method search for the asset name that is associated with this statistic so we can
     * group all the assets with the same name and have a global statistic.
     *
     * @return {@link String} with the asset's name.
     */
    @Override
    public String getAssetName() {
        return assetName;
    }

    public void setOwner(ActorAssetUser owner) {
        this.owner = owner;
    }

    public void setStatus(AssetCurrentStatus status) {
        this.status = status;
    }

    public void setDistributionDate(Date distributionDate) {
        this.distributionDate = distributionDate;
    }

    public void setUsageDate(Date usageDate) {
        this.usageDate = usageDate;
    }

    public void setRedeemPoint(ActorAssetRedeemPoint redeemPoint) {
        this.redeemPoint = redeemPoint;
    }

    public void setAssetPublicKey(String assetPublicKey) {
        this.assetPublicKey = assetPublicKey;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }
    //INNER CLASSES
}
