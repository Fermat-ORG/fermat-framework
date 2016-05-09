package org.fermat.fermat_dap_api.layer.all_definition.digital_asset;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public class AssetNegotiation implements Serializable {

    //VARIABLE DECLARATION
    /**
     * An unique UUID which will identified this object.
     */
    private UUID negotiationId;

    {
        negotiationId = UUID.randomUUID();
    }

    private long totalAmount;

    /**
     * The amount that cost each asset, represented in satoshis.
     */
    private long amountPerUnity;
    /**
     * The quantity to buy, when starting the transaction the seller
     * offers an amount, and then the buyer answers with another amount which could be the same or less.
     */
    private int quantityToBuy;

    /**
     * The asset that is being offered.
     */
    private DigitalAsset assetToOffer;

    /**
     * The network where we are doing this negotiation.
     */
    private BlockchainNetworkType networkType;

    {
        networkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
    }
    //CONSTRUCTORS

    public AssetNegotiation() {
    }

    public AssetNegotiation(UUID negotiationId, long amountPerUnity, int quantityToBuy, DigitalAsset assetToOffer, BlockchainNetworkType networkType) {
        this.negotiationId = negotiationId;
        this.amountPerUnity = amountPerUnity;
        this.quantityToBuy = quantityToBuy;
        this.assetToOffer = assetToOffer;
        this.networkType = networkType;
    }

    public AssetNegotiation(long amountPerUnity, int quantityToBuy, DigitalAsset assetToOffer, BlockchainNetworkType networkType) {
        this.amountPerUnity = amountPerUnity;
        this.quantityToBuy = quantityToBuy;
        this.assetToOffer = assetToOffer;
        this.networkType = networkType;
        this.totalAmount = amountPerUnity * quantityToBuy;
    }

    public AssetNegotiation(long totalAmount, long amountPerUnity, int quantityToBuy, DigitalAsset assetToOffer, BlockchainNetworkType networkType) {
        this.totalAmount = totalAmount;
        this.amountPerUnity = amountPerUnity;
        this.quantityToBuy = quantityToBuy;
        this.assetToOffer = assetToOffer;
        this.networkType = networkType;
    }

    public AssetNegotiation(UUID negotiationId, long totalAmount, long amountPerUnity, int quantityToBuy, DigitalAsset assetToOffer, BlockchainNetworkType networkType) {
        this.negotiationId = negotiationId;
        this.totalAmount = totalAmount;
        this.amountPerUnity = amountPerUnity;
        this.quantityToBuy = quantityToBuy;
        this.assetToOffer = assetToOffer;
        this.networkType = networkType;
    }

    //PUBLIC METHODS

    /**
     * This method will return the total amount that these assets worth in crypto currency.
     *
     * @return
     */
    public long getRealValue() {
        return quantityToBuy * assetToOffer.getGenesisAmount();
    }

    /**
     * This method will return the total amount that I am asking for all
     * these assets.
     *
     * @return the amount in satoshis.
     */
    public long getRequestedValue() {
        return quantityToBuy * amountPerUnity;
    }

    /**
     * This is a like-equals method. Which will let us know if the passed deal is the same
     * as the deal that we made.
     *
     * @param assetNegotiation The new negotiation to be compared.
     * @return {@code true} if its the same deal or {@code false} if my deal got changed.
     */
    public boolean isSameDeal(AssetNegotiation assetNegotiation) {
        if (!this.getAssetToOffer().equals(assetNegotiation.getAssetToOffer())) return false;
        if (this.getAmountPerUnity() != assetNegotiation.getAmountPerUnity()) return false;
        if (this.getTotalAmount() != assetNegotiation.getTotalAmount()) return false;
        return this.getQuantityToBuy() == assetNegotiation.getQuantityToBuy();
    }

    @Override
    public String toString() {
        return "AssetNegotiation{" +
                "negotiationId=" + negotiationId +
                ", amountPerUnity=" + amountPerUnity +
                ", quantityToBuy=" + quantityToBuy +
                ", assetToOffer=" + assetToOffer +
                ", networkType=" + networkType +
                '}';
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public UUID getNegotiationId() {
        return negotiationId;
    }

    public long getAmountPerUnity() {
        return amountPerUnity;
    }

    public int getQuantityToBuy() {
        return quantityToBuy;
    }

    public DigitalAsset getAssetToOffer() {
        return assetToOffer;
    }

    public BlockchainNetworkType getNetworkType() {
        return networkType;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    //INNER CLASSES
}
