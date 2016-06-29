package org.fermat.fermat_dap_android_wallet_asset_user.v2.models;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 3/30/16.
 */
public class AssetUserNegotiation implements Serializable {
    private AssetNegotiation assetNegotiation;

    private UUID id;
    private long amount;

    public AssetUserNegotiation(AssetNegotiation assetNegotiation) {
        this.assetNegotiation = assetNegotiation;

        setId(assetNegotiation.getNegotiationId());
        setAmount(assetNegotiation.getAmountPerUnity());
    }

    public AssetUserNegotiation() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}