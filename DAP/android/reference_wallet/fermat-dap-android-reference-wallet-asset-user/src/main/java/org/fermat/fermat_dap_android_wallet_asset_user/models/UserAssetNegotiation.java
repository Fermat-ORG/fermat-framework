package org.fermat.fermat_dap_android_wallet_asset_user.models;

import java.io.Serializable;
import java.util.UUID;

/**
 * Jinmy Bohorquez on 23/02/16.
 */
public class UserAssetNegotiation implements Serializable {

    private UUID negotiationId;
    private long totalAmmount;
    private long ammountPerUnit;
    private int quantityToBuy;

    public UserAssetNegotiation() {
    }


    public UserAssetNegotiation(UUID negotiationId, long ammountPerUnit, int quantityToBuy) {
        this.negotiationId = negotiationId;
        this.ammountPerUnit = ammountPerUnit;
        this.quantityToBuy = quantityToBuy;
        //this.digitalAssetPublicKey = digitalAssetPublicKey;

    }

    public UserAssetNegotiation(long ammountPerUnit, int quantityToBuy) {
        this.ammountPerUnit = ammountPerUnit;
        this.quantityToBuy = quantityToBuy;
        //this.digitalAssetPublicKey = digitalAssetPublicKey;
        this.totalAmmount = ammountPerUnit * quantityToBuy;
    }

    public UserAssetNegotiation(long totalAmmount, long ammountPerUnit, int quantityToBuy) {
        this.totalAmmount = totalAmmount;
        this.ammountPerUnit = ammountPerUnit;
        this.quantityToBuy = quantityToBuy;
        //this.digitalAssetPublicKey = digitalAssetPublicKey;

    }

    public UserAssetNegotiation(UUID negotiationId, long totalAmmount, long ammountPerUnit, int quantityToBuy) {
        this.negotiationId = negotiationId;
        this.totalAmmount = totalAmmount;
        this.ammountPerUnit = ammountPerUnit;
        this.quantityToBuy = quantityToBuy;
        //this.digitalAssetPublicKey = digitalAssetPublicKey;

    }

    public UUID getNegotiationId() {
        return negotiationId;
    }

    public long getTotalAmmount() {
        return totalAmmount;
    }

    public long getAmmountPerUnit() {
        return ammountPerUnit;
    }

    public int getQuantityToBuy() {
        return quantityToBuy;
    }

    /*public String getDigitalAssetPublicKey() {
        return digitalAssetPublicKey;
    }
    public void setDigitalAssetPublicKey(String digitalAssetPublicKey) {
        this.digitalAssetPublicKey = digitalAssetPublicKey;
    }*/

    public void setNegotiationId(UUID negotiationId) {
        this.negotiationId = negotiationId;
    }

    public void setTotalAmmount(long totalAmmount) {
        this.totalAmmount = totalAmmount;
    }

    public void setAmmountPerUnit(long ammountPerUnit) {
        this.ammountPerUnit = ammountPerUnit;
    }

    public void setQuantityToBuy(int quantityToBuy) {
        this.quantityToBuy = quantityToBuy;
    }


}
