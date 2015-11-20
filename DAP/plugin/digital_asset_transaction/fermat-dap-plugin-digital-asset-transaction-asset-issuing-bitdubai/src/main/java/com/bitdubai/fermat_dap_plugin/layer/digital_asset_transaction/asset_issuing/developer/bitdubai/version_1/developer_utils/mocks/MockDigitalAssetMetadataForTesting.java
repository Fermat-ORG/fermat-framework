package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.mocks;

import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/10/15.
 */
public class MockDigitalAssetMetadataForTesting extends DigitalAssetMetadata {

    public MockDigitalAssetMetadataForTesting() throws CantDefineContractPropertyException {
        setDigitalAsset(new MockDigitalAssetForTesting());
        setGenesisTransaction("d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43");
        String hash=getDigitalAssetHash();
        System.out.println("DAM Hash: "+hash);
    }

}
