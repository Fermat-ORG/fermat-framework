package com.bitdubai.fermat_dap_api.asset_issuing.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CantCreateDigitalAssetException;

import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
public interface AssetIssuingManager {

    void createDigitalAsset(String publicKey, String name, String description, List<Resource> resources, DigitalAssetContract contract, long genesisAmount) throws CantCreateDigitalAssetException;

}
