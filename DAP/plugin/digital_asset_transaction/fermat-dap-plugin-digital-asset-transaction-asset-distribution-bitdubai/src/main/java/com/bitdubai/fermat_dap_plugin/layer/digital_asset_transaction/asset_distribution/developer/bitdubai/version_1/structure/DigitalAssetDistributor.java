package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.exceptions.CantGetGenesisTransactionException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.DigitalAssetSwap;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.exceptions.CantDeliverDigitalAssetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/09/15.
 */
public class DigitalAssetDistributor extends DigitalAssetSwap {

    AssetVaultManager assetVaultManager;
    ErrorManager errorManager;
    final String LOCAL_STORAGE_PATH="digital-asset-distribution/";
    String digitalAssetFileStoragePath;
    //String digitalAssetMetadataFileStoragePath;
    AssetDistributionDao assetDistributionDao;
    DigitalAssetDistributionVault digitalAssetDistributionVault;

    public DigitalAssetDistributor(AssetVaultManager assetVaultManager, ErrorManager errorManager, UUID pluginId, PluginFileSystem pluginFileSystem) throws CantExecuteDatabaseOperationException {
        super(assetVaultManager,  pluginId, pluginFileSystem);
        this.errorManager=errorManager;
    }

    public void setAssetDistributionDao(AssetDistributionDao assetDistributionDao)throws CantSetObjectException{
        if(assetDistributionDao==null){
            throw new CantSetObjectException("assetDistributionDao is null");
        }
        this.assetDistributionDao=assetDistributionDao;
    }

    public void setDigitalAssetDistributionVault(DigitalAssetDistributionVault digitalAssetDistributionVault) throws CantSetObjectException {
        if(digitalAssetDistributionVault ==null){
            throw new CantSetObjectException("digitalAssetDistributionVault is null");
        }
        this.digitalAssetDistributionVault = digitalAssetDistributionVault;
    }

    public void setWalletPublicKey(String walletPublicKey) throws CantSetObjectException {
        this.digitalAssetDistributionVault.setWalletPublicKey(walletPublicKey);
    }

    /**
     * This method check if the DigitalAssetMetadata remains with not modifications
     * */
    public void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws CantDeliverDigitalAssetException {
        try{
            String genesisTransactionFromDigitalAssetMetadata=digitalAssetMetadata.getGenesisTransaction();
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_HASH,genesisTransactionFromDigitalAssetMetadata);
            String digitalAssetMetadataHash=digitalAssetMetadata.getDigitalAssetHash();
            CryptoTransaction cryptoTransaction = assetVaultManager.getGenesisTransaction(digitalAssetMetadataHash);
            //This won't work until I can get the CryptoTransaction from AssetVault
            String op_ReturnFromAssetVault=cryptoTransaction.getOp_Return();
            if(!digitalAssetMetadataHash.equals(op_ReturnFromAssetVault)){
                throw new CantDeliverDigitalAssetException("Cannot deliver Digital Asset because the " +
                        "Hash was modified:\n" +
                        "Op_return:"+op_ReturnFromAssetVault+"\n" +
                        "digitalAssetMetadata:"+digitalAssetMetadata);
            }
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.HASH_CHECKED, genesisTransactionFromDigitalAssetMetadata);
        } catch (CantGetGenesisTransactionException exception) {
            throw new CantDeliverDigitalAssetException(exception,
                    "Delivering the Digital Asset \n"+digitalAssetMetadata,
                    "Cannot get the genesis transaction from Asset vault");
        } catch (CantExecuteQueryException exception) {
            throw new CantDeliverDigitalAssetException(exception,
                    "Delivering the Digital Asset \n"+digitalAssetMetadata,
                    "Cannot execute a database operation");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantDeliverDigitalAssetException(exception,
                    "Delivering the Digital Asset \n"+digitalAssetMetadata,
                    "Unexpected result in database");
        }
    }

    /**
     * This method will deliver the DigitalAssetMetadata to ActorAssetUser
     * */
    private void deliverDigitalAssetToRemoteDevice(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser actorAssetUser) throws CantDeliverDigitalAssetException {
        try{
            //First, I'll check is Hash wasn't modified
            checkDigitalAssetMetadata(digitalAssetMetadata);
            //Now, I going to persist in database the basic information about digitalAssetMetadata
            persistDigitalAsset(digitalAssetMetadata, actorAssetUser);
            DigitalAsset digitalAsset=digitalAssetMetadata.getDigitalAsset();
            String genesisTransaction=digitalAssetMetadata.getGenesisTransaction();
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_AVAILABLE_BALANCE,genesisTransaction);
            if(!isAvailableBalanceInAssetVault(digitalAsset.getGenesisAmount(), genesisTransaction)){
                throw new CantDeliverDigitalAssetException("The Available balance ins asset vault is incorrect");
            }
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.AVAILABLE_BALANCE_CHECKED,genesisTransaction);
            DigitalAssetContract digitalAssetContract=digitalAsset.getContract();
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_CONTRACT,genesisTransaction);
            if(!isValidContract(digitalAssetContract)){
                throw new CantDeliverDigitalAssetException("The DigitalAsset Contract is not valid, the expiration date has passed");
            }
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CONTRACT_CHECKED,genesisTransaction);
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_HASH,genesisTransaction);
            if(!isDigitalAssetHashValid(digitalAssetMetadata)){
                throw new CantDeliverDigitalAssetException("The DigitalAsset hash is not valid");
            }
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.HASH_CHECKED,genesisTransaction);
            deliverToRemoteActor(digitalAssetMetadata, actorAssetUser.getPublicKey());
        } catch (CantPersistDigitalAssetException exception) {
            throw new CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot persist digital asset into database");
        } catch (CantCreateDigitalAssetFileException exception) {
            throw new CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot persist digital asset into local storage");
        } catch (CantGetGenesisTransactionException exception) {
            throw new CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot get the genesisTransaction from Asset Vault");
        } catch (CantExecuteQueryException exception) {
            throw new CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot execute a database operation");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Unexpected result in database");
        }
    }



    private void deliverToRemoteActor(DigitalAssetMetadata digitalAssetMetadata, String remoteActorPublicKey){
        String genesisTransaction=digitalAssetMetadata.getGenesisTransaction();
        try {
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.DELIVERING,genesisTransaction);
        } catch (CantExecuteQueryException e) {
            e.printStackTrace();
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            e.printStackTrace();
        }
        //TODO: send through Asset Transmission plugin
    }

    public void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser actorAssetUser) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException {
        setDigitalAssetLocalFilePath(digitalAssetMetadata);
        this.assetDistributionDao.persistDigitalAsset(digitalAssetMetadata.getGenesisTransaction(), this.digitalAssetFileStoragePath, digitalAssetMetadata.getDigitalAssetHash(), actorAssetUser.getPublicKey());
        persistInLocalStorage(digitalAssetMetadata);
    }


    public void persistInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException {
        //DigitalAsset Path structure: digital-asset-distribution/hash/digital-asset.xml
        //DigitalAssetMetadata Path structure: digital-asset-distribution/hash/digital-asset-metadata.xml
        this.digitalAssetDistributionVault.persistDigitalAssetMetadataInLocalStorage(digitalAssetMetadata);

    }

    public void setDigitalAssetLocalFilePath(DigitalAssetMetadata digitalAssetMetadata){
        //this.digitalAssetFileName=digitalAssetMetadata.getDigitalAssetHash()+".xml";
        this.digitalAssetFileStoragePath=this.LOCAL_STORAGE_PATH+"/"+digitalAssetMetadata.getDigitalAssetHash();
        this.digitalAssetDistributionVault.setDigitalAssetLocalFilePath(this.digitalAssetFileStoragePath);
        //this.digitalAssetFileName=digitalAssetMetadata.getDigitalAssetHash()+".xml";
        //this.digitalAssetFileStoragePath=this.LOCAL_STORAGE_PATH+"/"+digitalAssetFileName;
    }


    public void distributeAssets(HashMap<DigitalAssetMetadata, ActorAssetUser> digitalAssetsToDistribute) throws CantDistributeDigitalAssetsException {
        try{
            for(Map.Entry<DigitalAssetMetadata, ActorAssetUser> entry: digitalAssetsToDistribute.entrySet()){
                DigitalAssetMetadata digitalAssetMetadata=entry.getKey();
                ActorAssetUser actorAssetUser=entry.getValue();
                //Deliver one DigitalAsset
                deliverDigitalAssetToRemoteDevice(digitalAssetMetadata, actorAssetUser);
            }
        } catch(CantDeliverDigitalAssetException exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }

    }
}
