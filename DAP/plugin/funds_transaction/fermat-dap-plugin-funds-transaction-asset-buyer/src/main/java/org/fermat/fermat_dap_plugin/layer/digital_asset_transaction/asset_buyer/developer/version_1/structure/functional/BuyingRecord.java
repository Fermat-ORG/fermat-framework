package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public final class BuyingRecord {

    //VARIABLE DECLARATION
    private UUID recordId;
    private DigitalAssetMetadata metadata;
    private ActorAssetUser seller;
    private AssetSellStatus status;
    private DraftTransaction buyerTransaction;
    private DraftTransaction sellerTransaction;
    private UUID negotiationId;
    private CryptoAddress sellerCryptoAddress;
    private CryptoAddress buyerCryptoAddress;
    private UUID outgoingId;

    //CONSTRUCTORS

    public BuyingRecord(UUID recordId, DigitalAssetMetadata metadata, ActorAssetUser seller, AssetSellStatus status, DraftTransaction buyerTransaction, DraftTransaction sellerTransaction, UUID negotiationId, CryptoAddress sellerCryptoAddress, CryptoAddress buyerCryptoAddress, String outgoingId) {
        this.recordId = recordId;
        this.metadata = metadata;
        this.seller = seller;
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
        this.negotiationId = negotiationId;
        this.sellerCryptoAddress = sellerCryptoAddress;
        this.buyerCryptoAddress = buyerCryptoAddress;
        this.outgoingId = Validate.isValidString(outgoingId) ? UUID.fromString(outgoingId) : null;
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

    public ActorAssetUser getSeller() {
        return seller;
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

    public UUID getNegotiationId() {
        return negotiationId;
    }

    public CryptoAddress getSellerCryptoAddress() {
        return sellerCryptoAddress;
    }

    public UUID getOutgoingId() {
        return outgoingId;
    }

    public CryptoAddress getBuyerCryptoAddress() {
        return buyerCryptoAddress;
    }

    //INNER CLASSES
}
