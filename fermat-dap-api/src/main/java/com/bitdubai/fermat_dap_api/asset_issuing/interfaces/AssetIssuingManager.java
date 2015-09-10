package com.bitdubai.fermat_dap_api.asset_issuing.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionSender;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CantIssueDigitalAssetException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
public interface AssetIssuingManager extends TransactionSender<CryptoTransaction> {

    /*void createDigitalAsset(String publicKey, String name,
                            String description,
                            List<Resource> resources,
                            DigitalAssetContract contract,
                            long genesisAmount) throws CantCreateDigitalAssetTransactionException;*/
    void issueAsset(DigitalAsset digitalAssetToIssue) throws CantIssueDigitalAssetException;
    void setCryptoWallet(CryptoWallet cryptoWallet);
    /*void setActors(String deliveredByActorPublicKey,
                        Actors deliveredByType,
                        String deliveredToActorPublicKey,
                        Actors deliveredToType) throws CantSetObjectException;
    void setWallet(String walletPublicKey, ReferenceWallet walletType) throws CantSetObjectException;*/

}
