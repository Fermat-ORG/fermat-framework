package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces;

import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.BlockchainNetworkType;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
public interface AssetIssuingManager /*extends TransactionProtocolManager<CryptoTransaction>*/ {

    /*void createDigitalAsset(String publicKey, String name,
                            String description,
                            List<Resource> resources,
                            DigitalAssetContract contract,
                            long genesisAmount) throws CantCreateDigitalAssetTransactionException;*/
    void issueAssets(DigitalAsset digitalAssetToIssue, int assetsAmount, BlockchainNetworkType blockchainNetworkType) throws CantIssueDigitalAssetsException;
    void issuePendingDigitalAssets(String publicKey);
    void setCryptoWallet(CryptoWallet cryptoWallet);
    /*void setActors(String deliveredByActorPublicKey,
                        Actors deliveredByType,
                        String deliveredToActorPublicKey,
                        Actors deliveredToType) throws CantSetObjectException;
    void setWallet(String walletPublicKey, ReferenceWallet walletType) throws CantSetObjectException;*/

}
