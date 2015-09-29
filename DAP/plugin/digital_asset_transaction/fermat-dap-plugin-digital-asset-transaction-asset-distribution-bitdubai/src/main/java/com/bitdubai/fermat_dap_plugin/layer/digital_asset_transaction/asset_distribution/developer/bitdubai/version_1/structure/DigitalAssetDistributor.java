package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
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
public class DigitalAssetDistributor {

    AssetVaultManager assetVaultManager;
    ErrorManager errorManager;
    final String LOCAL_STORAGE_PATH="digital-asset-distribution/";
    //String digitalAssetLocalFilePath;
    //String digitalAssetFileName;
    String digitalAssetFileStoragePath;
    //String digitalAssetMetadataFileName;
    //String digitalAssetMetadataFileStoragePath;
    AssetDistributionDao assetDistributionDao;
    PluginFileSystem pluginFileSystem;

    public DigitalAssetDistributor(AssetVaultManager assetVaultManager,
                                   ErrorManager errorManager,
                                   UUID pluginId,
                                   PluginDatabaseSystem pluginDatabaseSystem,
                                   PluginFileSystem pluginFileSystem) throws CantExecuteDatabaseOperationException {
        this.assetVaultManager=assetVaultManager;
        this.errorManager=errorManager;
        assetDistributionDao=new AssetDistributionDao(pluginDatabaseSystem,pluginId);
    }


    /**
     * This method check if the DigitalAssetMetadata remains with not modifications
     * */
    private void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws CantDeliverDigitalAssetException {
        //TODO: get transactionHash from AssetVault
        CryptoTransaction cryptoTransaction = null;
        //This won't work until I can get the CryptoTransaction from AssetVault
        String op_ReturnFromAssetVault=cryptoTransaction.getOp_Return();
        String digitalAssetMetadataHash=digitalAssetMetadata.getDigitalAssetHash();
        if(!digitalAssetMetadataHash.equals(op_ReturnFromAssetVault)){
            throw new CantDeliverDigitalAssetException("Cannot deliver Digital Asset because the " +
                    "Hash was modified:\n" +
                    "Op_return:"+op_ReturnFromAssetVault+"\n" +
                    "digitalAssetMetadata:"+digitalAssetMetadata);
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
            checkAvailableBalanceInAssetVault(digitalAsset.getGenesisAmount());
        } catch (CantPersistDigitalAssetException exception) {
            throw new CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot persist digital asset into database");
        }
    }

    private void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser actorAssetUser) throws CantPersistDigitalAssetException {
        this.assetDistributionDao.persistDigitalAsset(digitalAssetMetadata.getGenesisTransaction(),"", digitalAssetMetadata.getDigitalAssetHash(), actorAssetUser.getPublicKey());
    }

    private boolean checkAvailableBalanceInAssetVault(long genesisAmount){
        //TODO: implement this
        return true;
    }

    private boolean isValidContract(DigitalAssetContract digitalAssetContract){
        //TODO: implement this 29/09/2015
        return true;
    }

    //TODO: preguntar al thunder team si sería conveniente persistir en archivo el digitalAssetMetadata
    private void persistInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException {
        //DigitalAsset Path structure: digital-asset-distribution/hash/digital-asset.xml
        //DigitalAssetMetadata Path structure: digital-asset-distribution/hash/digital-asset-metadata.xml
        setDigitalAssetLocalFilePath(digitalAssetMetadata);

    }

    private void setDigitalAssetLocalFilePath(DigitalAssetMetadata digitalAssetMetadata){
        //this.digitalAssetFileName=digitalAssetMetadata.getDigitalAssetHash()+".xml";
        this.digitalAssetFileStoragePath=this.LOCAL_STORAGE_PATH+"/"+digitalAssetMetadata.getDigitalAssetHash();
        //this.digitalAssetFileName=digitalAssetMetadata.getDigitalAssetHash()+".xml";
        //this.digitalAssetFileStoragePath=this.LOCAL_STORAGE_PATH+"/"+digitalAssetFileName;
    }


    public void distributeAssets(HashMap<DigitalAssetMetadata, ActorAssetUser> digitalAssetsToDistribute) throws CantDistributeDigitalAssetsException {
        //TODO: implement this
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
