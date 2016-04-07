package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

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
    private long forProcess;

    //CONSTRUCTORS
    public NegotiationRecord(AssetNegotiation negotiation, AssetSellStatus negotiationStatus, ActorAssetUser seller, Date timeStamp, String btcWalletPk, long forProcess) {
        this.negotiation = negotiation;
        this.negotiationStatus = negotiationStatus;
        this.seller = seller;
        this.timeStamp = timeStamp;
        this.btcWalletPublicKey = btcWalletPk;
        this.forProcess = forProcess;
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

    public long getForProcess() {
        return forProcess;
    }

    public void setForProcess(long forProcess) {
        this.forProcess = forProcess;
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
