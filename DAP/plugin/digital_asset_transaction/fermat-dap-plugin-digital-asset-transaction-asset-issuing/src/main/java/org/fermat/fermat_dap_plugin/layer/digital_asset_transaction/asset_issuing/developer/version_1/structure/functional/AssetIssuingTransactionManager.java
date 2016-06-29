package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/03/16.
 */
public class AssetIssuingTransactionManager implements AssetIssuingManager {

    //VARIABLE DECLARATION
    private final org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO dao;
    private final CryptoWalletManager manager;

    //CONSTRUCTORS
    public AssetIssuingTransactionManager(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO dao, CryptoWalletManager manager) {
        this.dao = dao;
        this.manager = manager;
    }

    //PUBLIC METHODS

    /**
     * This method will start the issuing, generating the needed
     * amount of digital asset metadata.
     *
     * @param digitalAssetToIssue   The asset which we want to create
     * @param assetsAmount          The amount of asset that we are willing to create
     * @param issuerWalletPk        The issuer wallet public key where the assets will go.
     * @param btcWalletPublicKey    The btc wallet public key where we'll take the bitcoins from.
     * @param blockchainNetworkType The kind of network where this asset will be created.
     * @throws CantIssueDigitalAssetsException In case something went wrong.
     */
    @Override
    public void issueAssets(DigitalAsset digitalAssetToIssue, int assetsAmount, String issuerWalletPk, String btcWalletPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantIssueDigitalAssetsException, InsufficientCryptoFundsException {
        try {
            long balance = manager.loadWallet(btcWalletPublicKey).getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
            long totalAmount = digitalAssetToIssue.getGenesisAmount() * assetsAmount;
            if (balance < totalAmount) {
                throw new InsufficientCryptoFundsException(null, null, null, null);
            }
            dao.startIssuing(digitalAssetToIssue, assetsAmount, blockchainNetworkType, btcWalletPublicKey, issuerWalletPk);
        } catch (Exception e) {
            throw new CantIssueDigitalAssetsException(e);
        }
    }

    /**
     * This method search the number of issued assets by the time it is requested.
     *
     * @param assetPublicKey The public key of the asset that we are issuing
     * @return the number of already issued assets
     * @throws CantExecuteDatabaseOperationException
     */
    @Override
    public int getNumberOfIssuedAssets(String assetPublicKey) throws CantExecuteDatabaseOperationException {
        try {
            return dao.getRecordForAsset(assetPublicKey).getAssetsCompleted();
        } catch (RecordsNotFoundException e) {
            return 0;
        } catch (CantLoadTableToMemoryException | InvalidParameterException | CantGetDigitalAssetFromLocalStorageException e) {
            throw new CantExecuteDatabaseOperationException(e);
        }
    }

    /**
     * The issuing status for the process of publishing this asset.
     *
     * @param assetPublicKey The public key of the asset that we are issuing
     * @return The actual issuing status for the issuing process.
     * @throws CantExecuteDatabaseOperationException
     */
    @Override
    public IssuingStatus getIssuingStatus(String assetPublicKey) throws CantExecuteDatabaseOperationException {
        try {
            return dao.getRecordForAsset(assetPublicKey).getStatus();
        } catch (RecordsNotFoundException e) {
            return IssuingStatus.NOT_PUBLISHED;
        } catch (CantLoadTableToMemoryException | InvalidParameterException | CantGetDigitalAssetFromLocalStorageException e) {
            throw new CantExecuteDatabaseOperationException(e);
        }
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
