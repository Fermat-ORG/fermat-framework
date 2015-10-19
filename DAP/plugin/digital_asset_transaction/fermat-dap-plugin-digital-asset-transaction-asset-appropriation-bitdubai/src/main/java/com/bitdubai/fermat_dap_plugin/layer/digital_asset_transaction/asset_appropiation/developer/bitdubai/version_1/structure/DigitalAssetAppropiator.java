package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.DAPException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropiation.exceptions.CantAppropiateDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.DigitalAssetSwap;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by frank on 18/10/15.
 */
public class DigitalAssetAppropiator extends DigitalAssetSwap {

    ErrorManager errorManager;
    final String LOCAL_STORAGE_PATH = "digital-asset-appropiation/";
    String digitalAssetFileStoragePath;
    //TODO uncomment when database classes have been created
    //AssetAppropiationDao assetDistributionDao;
    DigitalAssetAppropiatorVault digitalAssetAppropiatorVault;

    public DigitalAssetAppropiator(AssetVaultManager assetVaultManager, ErrorManager errorManager, UUID pluginId, PluginFileSystem pluginFileSystem) throws CantExecuteDatabaseOperationException {
        super(assetVaultManager, pluginId, pluginFileSystem);
        this.errorManager = errorManager;
    }

    //TODO uncomment when database classes have been created
    /*public void setAssetAppropiatorDao(AssetAppropiatorDao assetAppropiatorDao)throws CantSetObjectException {
        if(assetAppropiatorDao == null){
            throw new CantSetObjectException("assetAppropiatorDao is null");
        }
        this.assetAppropiatorDao = assetAppropiatorDao;
    }*/

    /*public void setDigitalAssetAppropiationVault(DigitalAssetAppropiationVault digitalAssetAppropiationVault) throws CantSetObjectException {
        if(digitalAssetAppropiationVault ==null){
            throw new CantSetObjectException("digitalAssetAppropiationVault is null");
        }
        this.digitalAssetDistributionVault = digitalAssetDistributionVault;
    }*/

    @Override
    public void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws DAPException {
        try{
            String genesisTransactionFromDigitalAssetMetadata=digitalAssetMetadata.getGenesisTransaction();
            //TODO uncomment when database classes have been created
            //this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_HASH,genesisTransactionFromDigitalAssetMetadata);
            String digitalAssetMetadataHash=digitalAssetMetadata.getDigitalAssetHash();
            CryptoTransaction cryptoTransaction = /*assetVaultManager.getGenesisTransaction(digitalAssetMetadataHash)*/null;
            //This won't work until I can get the CryptoTransaction from AssetVault
            String op_ReturnFromAssetVault=cryptoTransaction.getOp_Return();
            if(!digitalAssetMetadataHash.equals(op_ReturnFromAssetVault)){
                /*throw new CantDeliverDigitalAssetException("Cannot deliver Digital Asset because the " +
                        "Hash was modified:\n" +
                        "Op_return:"+op_ReturnFromAssetVault+"\n" +
                        "digitalAssetMetadata:"+digitalAssetMetadata);*/
            }
            //TODO uncomment when database classes have been created
            //this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.HASH_CHECKED, genesisTransactionFromDigitalAssetMetadata);
        } catch (Exception e) { //TODO manage specific exceptions
        }/*catch (CantGetGenesisTransactionException exception) {
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
        }*/
    }

    @Override
    public void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser actorAssetUser) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException {

    }

    @Override
    public void persistInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException {

    }

    @Override
    public void setDigitalAssetLocalFilePath(DigitalAssetMetadata digitalAssetMetadata) {

    }

    public void setDigitalAssetDistributionVault(DigitalAssetAppropiatorVault digitalAssetAppropiatorVault) throws CantSetObjectException {
        if(digitalAssetAppropiatorVault ==null){
            throw new CantSetObjectException("digitalAssetAppropiatorVault is null");
        }
        this.digitalAssetAppropiatorVault = digitalAssetAppropiatorVault;
    }

    public void appropiateAssets(HashMap<DigitalAssetMetadata, ActorAssetUser> digitalAssetsToAppropiate) throws CantAppropiateDigitalAssetsException {
        try {
            for(Map.Entry<DigitalAssetMetadata, ActorAssetUser> entry: digitalAssetsToAppropiate.entrySet()) {
                //TODO appropiate assets function
                //DigitalAssetMetadata digitalAssetMetadata=entry.getKey();
                //ActorAssetUser actorAssetUser=entry.getValue();
                //Deliver one DigitalAsset
                //deliverDigitalAssetToRemoteDevice(digitalAssetMetadata, actorAssetUser);
            }
        } catch(Exception e) { //TODO catch specific exceptions
            throw new CantAppropiateDigitalAssetsException(e, "", "");
        }
    }
}
