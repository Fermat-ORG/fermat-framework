package com.bitdubai.fermat_dap_api.asset_issuing.interfaces;

import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CantCreateDigitalAssetTransactionException;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CantIssueDigitalAsset;
import com.bitdubai.fermat_dap_api.exceptions.CantSetObjectException;

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
    void setCryptoWallet(CryptoWallet cryptoWallet);
    /*void setActors(String deliveredByActorPublicKey,
                        Actors deliveredByType,
                        String deliveredToActorPublicKey,
                        Actors deliveredToType) throws CantSetObjectException;
    void setWallet(String walletPublicKey, ReferenceWallet walletType) throws CantSetObjectException;*/

}
