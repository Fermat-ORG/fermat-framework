package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.AssetSellerDigitalAssetTransactionPluginRoot;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public final class SellingRecord {

    //VARIABLE DECLARATION
    private UUID recordId;
    private DigitalAssetMetadata metadata;
    private ActorAssetUser buyer;
    private AssetSellStatus status;
    private DraftTransaction buyerTransaction;
    private DraftTransaction sellerTransaction;
    private String broadcastingTxHash;
    private UUID negotiationId;
    private CryptoAddress sellerCryptoAddress;
    private CryptoAddress buyerCryptoAddress;
    private long startTime;

    //CONSTRUCTORS

    public SellingRecord(UUID recordId, DigitalAssetMetadata metadata, ActorAssetUser buyer, AssetSellStatus status, DraftTransaction buyerTransaction, DraftTransaction sellerTransaction, String broadcastingTxHash, UUID negotiationId, CryptoAddress sellerCryptoAddress, CryptoAddress buyerCryptoAddress, long startTime) {
        this.recordId = recordId;
        this.metadata = metadata;
        this.buyer = buyer;
        this.status = status;
        this.buyerTransaction = buyerTransaction;
        if (buyerTransaction != null) {
            this.buyerTransaction.setBuyerCryptoAddress(buyerCryptoAddress);
            this.buyerTransaction.setSellerCryptoAddress(sellerCryptoAddress);
        }
        this.sellerTransaction = sellerTransaction;
        if (sellerTransaction != null) {
            this.sellerTransaction.setSellerCryptoAddress(sellerCryptoAddress);
            this.sellerTransaction.setBuyerCryptoAddress(buyerCryptoAddress);
        }
        this.broadcastingTxHash = broadcastingTxHash;
        this.negotiationId = negotiationId;
        this.sellerCryptoAddress = sellerCryptoAddress;
        this.buyerCryptoAddress = buyerCryptoAddress;
        this.startTime = startTime;
    }


    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public UUID getRecordId() {
        return recordId;
    }

    public DigitalAssetMetadata getMetadata() {
        return metadata;
    }

    public ActorAssetUser getBuyer() {
        return buyer;
    }

    public AssetSellStatus getStatus() {
        return status;
    }

    public DraftTransaction getBuyerTransaction() {
        return buyerTransaction;
    }

    public DraftTransaction getSellerTransaction() {
        return sellerTransaction;
    }

    public String getBroadcastingTxHash() {
        return broadcastingTxHash;
    }

    public UUID getNegotiationId() {
        return negotiationId;
    }

    public boolean isExpired() {
        return new Date().after(new Date(startTime + AssetSellerDigitalAssetTransactionPluginRoot.SELL_TIMEOUT));
    }

    public Date getStartTime() {
        return new Date(startTime);
    }

    public CryptoAddress getSellerCryptoAddress() {
        return sellerCryptoAddress;
    }

    public CryptoAddress getBuyerCryptoAddress() {
        return buyerCryptoAddress;
    }

    //INNER CLASSES
}
