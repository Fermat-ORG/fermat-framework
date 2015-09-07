package com.bitdubai.fermat_dap_api.asset_issuing.interfaces;

import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CantCreateDigitalAssetTransactionException;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CantIssueDigitalAsset;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
public interface AssetIssuingManager {

    /*void createDigitalAsset(String publicKey, String name,
                            String description,
                            List<Resource> resources,
                            DigitalAssetContract contract,
                            long genesisAmount) throws CantCreateDigitalAssetTransactionException;*/
    void issueAsset(DigitalAsset digitalAssetToIssue) throws CantIssueDigitalAsset;
    /*void setActors(String deliveredByActorPublicKey,
                        Actors deliveredByType,
                        String deliveredToActorPublicKey,
                        Actors deliveredToType) throws CantSetObjectException;
    void setWallet(String walletPublicKey, ReferenceWallet walletType) throws CantSetObjectException;*/

}
