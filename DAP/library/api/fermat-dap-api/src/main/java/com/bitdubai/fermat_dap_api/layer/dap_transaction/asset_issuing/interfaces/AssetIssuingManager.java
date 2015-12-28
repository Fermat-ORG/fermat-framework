package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
public interface AssetIssuingManager extends FermatManager {

    /**
     * This method will create and deliver digital assets according to the amount indicated by the assetsAmount argument.
     * The process includes creating the transaction and send the crypto genesisAmount from the bitcoin wallet to wallet asset.
     * */
    void issueAssets(DigitalAsset digitalAssetToIssue,
                     int assetsAmount,
                     String walletPublicKey,
                     BlockchainNetworkType blockchainNetworkType) throws CantIssueDigitalAssetsException;

    /**
     * This method will issue the unfinished digital assets to the asset issuer wallet
     * @param assetPublicKey
     */
    void issuePendingDigitalAssets(String assetPublicKey);

    /**
     * This method must be used to set the crypto wallet to the Asset Issuing plugin
     * @param cryptoWallet
     */
    //void setCryptoWallet(CryptoWallet cryptoWallet);
    /**
     * This method must be used from the Asset Wallet to confirm the DigitalAssetMetadata reception.
     * @param genesisTransaction is a DigitalAssetMetadata parameter.
     * @throws CantConfirmTransactionException
     */
    void confirmReception(String  genesisTransaction)throws CantConfirmTransactionException;

    /**
     * This method return the number of issued assets
     * @param assetPublicKey
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    int getNumberOfIssuedAssets(String assetPublicKey) throws CantExecuteDatabaseOperationException;

    /**
     * This method returns the IssuingStatus from the complete issuing process about a Digital Asset
     * @param assetPublicKey
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    IssuingStatus getIssuingStatus(String assetPublicKey) throws CantExecuteDatabaseOperationException;

}
