package com.bitdubai.fermat_dap_api.asset_issuing.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CantCreateDigitalAssetException;
import com.bitdubai.fermat_dap_api.exceptions.CantSetObjectException;

import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
public interface AssetIssuingManager {

    void createDigitalAsset(String publicKey, String name,
                            String description,
                            List<Resource> resources,
                            DigitalAssetContract contract,
                            long genesisAmount) throws CantCreateDigitalAssetException;
    void setActors(String deliveredByActorPublicKey,
                        Actors deliveredByType,
                        String deliveredToActorPublicKey,
                        Actors deliveredToType) throws CantSetObjectException;
    void setWallet(String walletPublicKey, ReferenceWallet walletType) throws CantSetObjectException;

}
