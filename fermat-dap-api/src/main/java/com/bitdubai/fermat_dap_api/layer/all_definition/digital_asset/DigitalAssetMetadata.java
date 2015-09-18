package com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset;

import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;

/**
 * Created by rodrigo on 9/4/15.
 */
public class DigitalAssetMetadata {
    DigitalAsset digitalAsset;
    String hash;

    public DigitalAssetMetadata(DigitalAsset digitalAsset) {
        this.digitalAsset = digitalAsset;
    }

    private String  generateHash(){
        digitalAsset.setState(State.FINAL);
        return CryptoHasher.performSha256(digitalAsset.toString());
    }

    public String getDigitalAssetHash() {
        hash = generateHash();
        return hash;
    }
}
