package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetSwap;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.exceptions.CantReceiveDigitalAssetException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/10/15.
 */
public class DigitalAssetReceptor extends AbstractDigitalAssetSwap {

    ErrorManager errorManager;
    final String LOCAL_STORAGE_PATH="digital-asset-reception/";
    String digitalAssetFileStoragePath;
    //String digitalAssetMetadataFileStoragePath;
    //AssetReceptionDao assetReceptionnDao;

    DigitalAssetReceptionVault digitalAssetReceptionVault;
    AssetVaultManager assetVaultManager;

    public DigitalAssetReceptor(AssetVaultManager assetVaultManager, ErrorManager errorManager, UUID pluginId, PluginFileSystem pluginFileSystem) throws CantExecuteDatabaseOperationException {
        super(assetVaultManager,  pluginId, pluginFileSystem);
        this.errorManager=errorManager;
    }

    //TODO set DAO

    public void setDigitalAssetReceptionVault(DigitalAssetReceptionVault digitalAssetReceptionVault) throws CantSetObjectException {
        if(digitalAssetReceptionVault==null){
            throw new CantSetObjectException("DigitalAssetReceptionVault is null");
        }
        this.digitalAssetReceptionVault=digitalAssetReceptionVault;
    }

    /**
     * This method check if the DigitalAssetMetadata remains with not modifications
     * */
    public void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws CantReceiveDigitalAssetException {
        try{
            String genesisTransactionFromDigitalAssetMetadata=digitalAssetMetadata.getGenesisTransaction();
            //this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_HASH,genesisTransactionFromDigitalAssetMetadata);
            String digitalAssetMetadataHash=digitalAssetMetadata.getDigitalAssetHash();
            CryptoTransaction cryptoTransaction = /*this.assetVaultManager.getGenesisTransaction(digitalAssetMetadataHash)*/null;
            //This won't work until I can get the CryptoTransaction from AssetVault
            String op_ReturnFromAssetVault=cryptoTransaction.getOp_Return();
            if(!digitalAssetMetadataHash.equals(op_ReturnFromAssetVault)){
                throw new CantReceiveDigitalAssetException("Cannot receive Digital Asset because the " +
                        "Hash was modified:\n" +
                        "Op_return:"+op_ReturnFromAssetVault+"\n" +
                        "digitalAssetMetadata:"+digitalAssetMetadata);
            }
            //this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.HASH_CHECKED,genesisTransactionFromDigitalAssetMetadata);
        } catch(Exception e) {
            //TODO: Eliminate this catch, this is only for compilation
            throw new CantReceiveDigitalAssetException(e,
                    "Delivering the Digital Asset \n"+digitalAssetMetadata,
                    "Unexpected result in database");
        /*catch (CantGetGenesisTransactionException exception) {
            throw new CantReceiveDigitalAssetException(exception,
                    "Receiving the Digital Asset \n"+digitalAssetMetadata,
                    "Cannot get the genesis transaction from Asset vault");
        } catch (CantExecuteQueryException exception) {
            throw new CantReceiveDigitalAssetException(exception,
                    "Delivering the Digital Asset \n"+digitalAssetMetadata,
                    "Cannot execute a database operation");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantReceiveDigitalAssetException(exception,
                    "Delivering the Digital Asset \n"+digitalAssetMetadata,
                    "Unexpected result in database");
        }*/
        }
    }

    public void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser actorAssetUser) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException {
        setDigitalAssetLocalFilePath(digitalAssetMetadata);
        //this.assetDistributionDao.persistDigitalAsset(digitalAssetMetadata.getGenesisTransaction(), this.digitalAssetFileStoragePath, digitalAssetMetadata.getDigitalAssetHash(), actorAssetUser.getPublicKey());
        persistInLocalStorage(digitalAssetMetadata);
    }

    @Override
    public void persistInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException {
        //TODO: I need to asing an internal UUID and persist it in database, for now this id is null
        this.digitalAssetReceptionVault.persistDigitalAssetMetadataInLocalStorage(digitalAssetMetadata,null);
    }

    @Override
    public void setDigitalAssetLocalFilePath(DigitalAssetMetadata digitalAssetMetadata) {
        this.digitalAssetFileStoragePath=this.LOCAL_STORAGE_PATH+"/"+digitalAssetMetadata.getDigitalAssetHash();
        this.digitalAssetReceptionVault.setDigitalAssetLocalFilePath(this.digitalAssetFileStoragePath);
    }

}
