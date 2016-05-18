package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageType;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public class AssetSellContentMessage implements DAPContentMessage {
    //VARIABLE DECLARATION
    private UUID sellingId;
    private byte[] serializedTransaction;
    private long transactionValue;
    private org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus sellStatus;
    private DigitalAssetMetadata assetMetadata;
    private UUID negotiationId;
    private CryptoAddress sellerCryptoAddress;
    private CryptoAddress buyerCryptoAddress;
    //CONSTRUCTORS

    public AssetSellContentMessage() {
    }

    public AssetSellContentMessage(UUID sellingId, byte[] serializedTransaction, long transactionValue, org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus sellStatus, DigitalAssetMetadata assetMetadata, UUID negotiationId, CryptoAddress sellerCryptoAddress, CryptoAddress buyerCryptoAddress) {
        this.sellingId = sellingId;
        this.serializedTransaction = serializedTransaction;
        this.transactionValue = transactionValue;
        this.sellStatus = sellStatus;
        this.assetMetadata = assetMetadata;
        this.negotiationId = negotiationId;
        this.sellerCryptoAddress = sellerCryptoAddress;
        this.buyerCryptoAddress = buyerCryptoAddress;
    }

    //PUBLIC METHODS

    /**
     * Every content message should have a unique type associate to it.
     *
     * @return {@link DAPMessageType} The message type that corresponds to this content message.
     */
    @Override
    public DAPMessageType messageType() {
        return DAPMessageType.ASSET_SELL;
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public byte[] getSerializedTransaction() {
        return serializedTransaction;
    }

    public org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus getSellStatus() {
        return sellStatus;
    }

    public UUID getSellingId() {
        return sellingId;
    }

    public DigitalAssetMetadata getAssetMetadata() {
        return assetMetadata;
    }

    public UUID getNegotiationId() {
        return negotiationId;
    }

    public long getTransactionValue() {
        return transactionValue;
    }

    public CryptoAddress getSellerCryptoAddress() {
        return sellerCryptoAddress;
    }

    public CryptoAddress getBuyerCryptoAddress() {
        return buyerCryptoAddress;
    }
    //INNER CLASSES
}
