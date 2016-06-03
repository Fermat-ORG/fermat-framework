package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.mocks;

import org.fermat.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/10/15.
 */
public class MockDigitalAssetMetadataForTesting extends DigitalAssetMetadata {

    public MockDigitalAssetMetadataForTesting() throws CantDefineContractPropertyException {
        setDigitalAsset(new MockDigitalAssetForTesting());
//        setGenesisTransaction("d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43");
        String hash = getDigitalAssetHash();
        //System.out.println("DAM Hash: "+hash);
    }

}
