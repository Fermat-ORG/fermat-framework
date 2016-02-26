package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models;

import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/24/16.
 */
public class Asset {
    private DigitalAsset digitalAsset;

    private byte[] image;
    private String name;

    public Asset(String name) {
        this.name = name;
    }

    public Asset(DigitalAsset digitalAsset) {
        this.digitalAsset = digitalAsset;
        setImage(digitalAsset.getResources().get(0).getResourceBinayData());
        setName(digitalAsset.getName());
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
