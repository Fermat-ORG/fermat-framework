package com.bitdubai.fermat_dap_api.all_definition.digital_asset;

import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.enums.State;

/**
 * Created by rodrigo on 9/4/15.
 */
public class DigitalAssetMetadata {
    DigitalAsset digitalAsset;
    String hash;

    public DigitalAssetMetadata(DigitalAsset digitalAsset) {
        this.digitalAsset = digitalAsset;
        digitalAsset.setState(State.FINAL);
        hash = generateHash();
    }

    String generateHash(){
        return CryptoHasher.performSha256(digitalAsset.toString());
    }

    public String getDigitalAssetHash() {
        return hash;
    }
}
