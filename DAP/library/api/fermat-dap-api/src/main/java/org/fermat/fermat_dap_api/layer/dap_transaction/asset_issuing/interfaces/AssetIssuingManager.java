package org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;

import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 * Updated by Víctor Mars (marsvicam@gmail.com) on 09/03/16
 */
public interface AssetIssuingManager {//extends FermatManager {

    /**
     * This method will start the issuing, generating the needed
     * amount of digital asset metadata.
     *
     * @param digitalAssetToIssue   The asset which we want to create
     * @param assetsAmount          The amount of asset that we are willing to create
     * @param issuerWalletPk        The issuer wallet public key where the assets will go.
     * @param btcWalletPublicKey    The btc wallet public key where we'll take the bitcoins from.
     * @param blockchainNetworkType The kind of network where this asset will be created.
     * @throws org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException In case something went wrong.
     */
    void issueAssets(org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset digitalAssetToIssue,
                     int assetsAmount,
                     String issuerWalletPk,
                     String btcWalletPublicKey,
                     BlockchainNetworkType blockchainNetworkType) throws org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException, InsufficientCryptoFundsException;

    /**
     * This method search the number of issued assets by the time it is requested.
     *
     * @param assetPublicKey The public key of the asset that we are issuing
     * @return the number of already issued assets
     * @throws CantExecuteDatabaseOperationException
     */
    int getNumberOfIssuedAssets(String assetPublicKey) throws CantExecuteDatabaseOperationException;

    /**
     * The issuing status for the process of publishing this asset.
     *
     * @param assetPublicKey The public key of the asset that we are issuing
     * @return The actual issuing status for the issuing process.
     * @throws CantExecuteDatabaseOperationException
     */
    org.fermat.fermat_dap_api.layer.all_definition.enums.IssuingStatus getIssuingStatus(String assetPublicKey) throws CantExecuteDatabaseOperationException;

}
