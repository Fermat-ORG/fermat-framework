package org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.util.Validate;

import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetCurrentStatus;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetMovement;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetStatistic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 02/12/15.
 */
public class AssetStatisticImpl implements AssetStatistic {

    //VARIABLE DECLARATION

    private UUID transactionId;

    private String genesisTransaction;

    {
        genesisTransaction = Validate.DEFAULT_STRING;
    }

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

    private List<AssetMovement> assetMovements;

    {
        assetMovements = new ArrayList<>();
    }
    //CONSTRUCTORS

    public AssetStatisticImpl() {
    }

    public AssetStatisticImpl(UUID transactionId, String genesisTransaction, String assetPublicKey, AssetCurrentStatus status, Date distributionDate, Date usageDate, ActorAssetRedeemPoint redeemPoint, String assetName, ActorAssetUser owner, List<AssetMovement> assetMovements) {
        this.transactionId = transactionId;
        this.genesisTransaction = genesisTransaction;
        this.assetPublicKey = assetPublicKey;
        this.status = status;
        this.distributionDate = distributionDate;
        this.usageDate = usageDate;
        this.redeemPoint = redeemPoint;
        this.assetName = assetName;
        this.owner = owner;
        this.assetMovements = assetMovements;
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    /**
     * The unique ID that identifies this statistic.
     *
     * @return an {@link UUID} instance.
     */
    @Override
    public UUID transactionId() {
        return transactionId;
    }

    /**
     * Even when this is not a value that is going to be shown to the user, this is needed
     * to retrieve the DigitalAssetMetadata from the wallet.
     *
     * @return {@link String} instance representing the transaction hash of the genesis tx
     * associated with the DigitalAssetMetadata.
     */
    @Override
    public String genesisTransaction() {
        return genesisTransaction;
    }

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
        return owner;
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

    /**
     * This method retrieves the list of the movements made for this asset, the actor whom send it
     * and the actor who receives it. And probably most information will be added soon.
     *
     * @return {@link List <AssetMovement>} instance.
     */
    @Override
    public List<AssetMovement> movementHistory() {
        return assetMovements;
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

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public void setGenesisTransaction(String genesisTransaction) {
        this.genesisTransaction = genesisTransaction;
    }

    public void setAssetMovements(List<AssetMovement> assetMovements) {
        this.assetMovements = assetMovements;
    }

    //INNER CLASSES
}
