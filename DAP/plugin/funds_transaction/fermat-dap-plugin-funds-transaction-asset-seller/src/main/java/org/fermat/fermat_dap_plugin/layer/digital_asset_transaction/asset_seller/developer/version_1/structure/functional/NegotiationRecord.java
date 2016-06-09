package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.structure.functional;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.AssetSellerDigitalAssetTransactionPluginRoot;

import java.util.Date;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public class NegotiationRecord {

    //VARIABLE DECLARATION
    private AssetNegotiation negotiation;
    private AssetSellStatus negotiationStatus;
    private ActorAssetUser buyer;
    private long startTime;

    //CONSTRUCTORS

    public NegotiationRecord(AssetNegotiation negotiation, AssetSellStatus negotiationStatus, long startTime, ActorAssetUser buyer) {
        this.negotiation = negotiation;
        this.negotiationStatus = negotiationStatus;
        this.startTime = startTime;
        this.buyer = buyer;
    }

    //PUBLIC METHODS

    public AssetNegotiation getNegotiation() {
        return negotiation;
    }

    public AssetSellStatus getNegotiationStatus() {
        return negotiationStatus;
    }

    public Date getStartTime() {
        return new Date(startTime);
    }

    public boolean isExpired() {
        return new Date().after(new Date(startTime + AssetSellerDigitalAssetTransactionPluginRoot.SELL_TIMEOUT));
    }

    public ActorAssetUser getBuyer() {
        return buyer;
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
