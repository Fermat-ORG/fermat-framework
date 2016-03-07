package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

import java.util.Date;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public class NegotiationRecord {

    //VARIABLE DECLARATION
    private AssetNegotiation negotiation;
    private AssetSellStatus negotiationStatus;
    private ActorAssetUser seller;
    private Date timeStamp;
    private String btcWalletPublicKey;

    //CONSTRUCTORS
    public NegotiationRecord(AssetNegotiation negotiation, AssetSellStatus negotiationStatus, ActorAssetUser seller, Date timeStamp, String btcWalletPk) {
        this.negotiation = negotiation;
        this.negotiationStatus = negotiationStatus;
        this.seller = seller;
        this.timeStamp = timeStamp;
        this.btcWalletPublicKey = btcWalletPk;
    }

    //PUBLIC METHODS

    public AssetNegotiation getNegotiation() {
        return negotiation;
    }

    public AssetSellStatus getNegotiationStatus() {
        return negotiationStatus;
    }

    public ActorAssetUser getSeller() {
        return seller;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setNegotiation(AssetNegotiation negotiation) {
        this.negotiation = negotiation;
    }

    public void setNegotiationStatus(AssetSellStatus negotiationStatus) {
        this.negotiationStatus = negotiationStatus;
    }

    public void setSeller(ActorAssetUser seller) {
        this.seller = seller;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getBtcWalletPublicKey() {
        return btcWalletPublicKey;
    }

    public void setBtcWalletPublicKey(String btcWalletPublicKey) {
        this.btcWalletPublicKey = btcWalletPublicKey;
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
