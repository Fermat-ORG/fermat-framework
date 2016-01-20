package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantDeriveNewKeysException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/09/15.
 */
public class AssetIssuingTransactionManager implements AssetIssuingManager {

    private DigitalAssetCryptoTransactionFactory digitalAssetCryptoTransactionFactory;
    private AssetIssuingTransactionDao assetIssuingTransactionDao;
    private AssetVaultManager assetVaultManager;

    //ActorAssetIssuerManager actorAssetIssuerManager;

    public AssetIssuingTransactionManager(UUID pluginId,
                                          CryptoVaultManager cryptoVaultManager,
                                          BitcoinWalletManager bitcoinWalletManager,
                                          PluginFileSystem pluginFileSystem,
                                          ErrorManager errorManager,
                                          AssetVaultManager assetVaultManager,
                                          CryptoAddressBookManager cryptoAddressBookManager,
                                          OutgoingIntraActorManager outgoingIntraActorManager,
                                          AssetIssuerWalletManager assetIssuerWalletManager,
                                          DigitalAssetIssuingVault digitalAssetIssuingVault,
                                          AssetIssuingTransactionDao assetIssuingTransactionDao,
                                          ActorAssetIssuerManager actorAssetIssuerManager,
                                          IntraWalletUserIdentityManager intraWalletUserIdentityManager) throws CantSetObjectException, CantExecuteDatabaseOperationException {

        digitalAssetCryptoTransactionFactory = new DigitalAssetCryptoTransactionFactory(pluginId,
                cryptoVaultManager,
                bitcoinWalletManager,
                pluginFileSystem,
                assetVaultManager,
                cryptoAddressBookManager,
                outgoingIntraActorManager,
                assetIssuerWalletManager,
                errorManager,
                intraWalletUserIdentityManager,
                actorAssetIssuerManager);
        this.assetVaultManager = assetVaultManager;
        setDigitalAssetMetadataVault(digitalAssetIssuingVault);
        setAssetIssuingTransactionDao(assetIssuingTransactionDao);
    }


    @Override
    public void issueAssets(DigitalAsset digitalAssetToIssue, int assetsAmount, String walletPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantIssueDigitalAssetsException {
        try {
            checkKeyCount(assetsAmount);
            this.digitalAssetCryptoTransactionFactory.issueDigitalAssets(digitalAssetToIssue, assetsAmount, walletPublicKey, blockchainNetworkType);
        } catch (CantIssueDigitalAssetsException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Creating a Digital Asset Transaction", "Check the cause");
        } catch (CantDeliverDigitalAssetToAssetWalletException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Creating a Digital Asset Transaction", "Cannot deliver the digital asset to the asset wallet");
        } catch (Exception exception) {
            throw new CantIssueDigitalAssetsException(FermatException.wrapException(exception), "Issuing the Digital Asset required amount", "Unexpected Exception");
        }
    }

    private void checkKeyCount(int assetsAmount) throws CantDeriveNewKeysException {
        int keyCount = assetVaultManager.getAvailableKeyCount();
        if (keyCount < assetsAmount) {
            System.out.println("ASSET ISSUING - DERIVING MORE KEYS!");
            int difference = assetsAmount - keyCount;
            assetVaultManager.deriveKeys(Plugins.ASSET_ISSUING, difference);
        }
    }

    @Override
    public void issuePendingDigitalAssets(String publicKey) {
        this.digitalAssetCryptoTransactionFactory.issuePendingDigitalAssets(publicKey);
    }


    public void setDigitalAssetMetadataVault(DigitalAssetIssuingVault digitalAssetIssuingVault) throws CantSetObjectException {
        this.digitalAssetCryptoTransactionFactory.setDigitalAssetIssuingVault(digitalAssetIssuingVault);
    }

    public void setAssetIssuingTransactionDao(AssetIssuingTransactionDao assetIssuingTransactionDao) throws CantSetObjectException {
        if (assetIssuingTransactionDao == null) {
            throw new CantSetObjectException("assetIssuingTransactionDao is null");
        }
        this.assetIssuingTransactionDao = assetIssuingTransactionDao;
        this.digitalAssetCryptoTransactionFactory.setAssetIssuingTransactionDao(assetIssuingTransactionDao);
    }

    @Override
    public void confirmReception(/*UUID transactionID*/String genesisTransaction) throws CantConfirmTransactionException {
        try {
            this.assetIssuingTransactionDao.confirmReception(genesisTransaction);
        } catch (CantExecuteQueryException exception) {
            throw new CantConfirmTransactionException(CantExecuteQueryException.DEFAULT_MESSAGE, exception, "Confirming Reception", "Cannot execute query");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantConfirmTransactionException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE, exception, "Confirming Reception", "The database returns more than one valid result");
        }
    }

    @Override
    public int getNumberOfIssuedAssets(String assetPublicKey) throws CantExecuteDatabaseOperationException {
        try {
            return this.digitalAssetCryptoTransactionFactory.getNumberOfIssuedAssets(assetPublicKey);
        } catch (CantCheckAssetIssuingProgressException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Getting the number of issued assets", "Cannot check the asset issuing progress");
        }
    }

    @Override
    public IssuingStatus getIssuingStatus(String assetPublicKey) throws CantExecuteDatabaseOperationException {
        try {
            String issuingStatusCode = this.assetIssuingTransactionDao.getIssuingStatusByPublicKey(assetPublicKey);
            return IssuingStatus.getByCode(issuingStatusCode);
        } catch (CantCheckAssetIssuingProgressException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Getting the Issuing status", "Cannot check the Asset Issuing progress");
        } catch (InvalidParameterException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Getting the Issuing status", "Cannot invalid parameter in IssuingStatus enum");
        }

    }
}
