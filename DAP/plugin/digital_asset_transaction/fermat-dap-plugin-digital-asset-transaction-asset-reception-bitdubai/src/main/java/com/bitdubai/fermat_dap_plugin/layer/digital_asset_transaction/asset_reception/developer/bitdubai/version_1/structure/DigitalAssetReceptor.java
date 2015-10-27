package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetGenesisTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetSwap;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.exceptions.CantReceiveDigitalAssetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.database.AssetReceptionDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/10/15.
 */
public class DigitalAssetReceptor extends AbstractDigitalAssetSwap {

    ErrorManager errorManager;
    final String LOCAL_STORAGE_PATH="digital-asset-reception/";
    String digitalAssetFileStoragePath;
    //String digitalAssetMetadataFileStoragePath;
    AssetReceptionDao assetReceptionDao;

    DigitalAssetReceptionVault digitalAssetReceptionVault;
    AssetVaultManager assetVaultManager;

    public DigitalAssetReceptor(AssetVaultManager assetVaultManager, ErrorManager errorManager, UUID pluginId, PluginFileSystem pluginFileSystem) throws CantExecuteDatabaseOperationException {
        super(assetVaultManager,  pluginId, pluginFileSystem);
        this.errorManager=errorManager;
    }

    public void setAssetReceptionDao(AssetReceptionDao assetReceptionDao)throws CantSetObjectException{
        if(assetReceptionDao==null){
            throw new CantSetObjectException("assetReceptionDao is null");
        }
        this.assetReceptionDao=assetReceptionDao;
    }

    public void setDigitalAssetReceptionVault(DigitalAssetReceptionVault digitalAssetReceptionVault) throws CantSetObjectException {
        if(digitalAssetReceptionVault==null){
            throw new CantSetObjectException("DigitalAssetReceptionVault is null");
        }
        this.digitalAssetReceptionVault=digitalAssetReceptionVault;
    }

    public void receiveDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata, String senderId) throws CantReceiveDigitalAssetException {
        try{
            persistDigitalAsset(digitalAssetMetadata, senderId);
            checkDigitalAssetMetadata(digitalAssetMetadata);
            //TODO: Send message to issuer from asset transmission
        } catch (CantPersistDigitalAssetException e) {
            e.printStackTrace();
        } catch (CantCreateDigitalAssetFileException e) {
            e.printStackTrace();
        }

    }

    public void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, String senderId) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException {
        setDigitalAssetLocalFilePath(digitalAssetMetadata);
        this.assetReceptionDao.persistDigitalAsset(
                digitalAssetMetadata.getGenesisTransaction(),
                this.digitalAssetFileStoragePath,
                digitalAssetMetadata.getDigitalAssetHash(),
                senderId);
        persistInLocalStorage(digitalAssetMetadata);
    }

    /**
     * This method check if the DigitalAssetMetadata remains with not modifications
     * */
    public void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws CantReceiveDigitalAssetException {
        try{
            String genesisTransactionFromDigitalAssetMetadata=digitalAssetMetadata.getGenesisTransaction();
            this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(DistributionStatus.CHECKING_HASH, genesisTransactionFromDigitalAssetMetadata);
            String digitalAssetMetadataHash=digitalAssetMetadata.getDigitalAssetHash();
            List<CryptoTransaction> cryptoTransactionList = bitcoinNetworkManager.getGenesisTransaction(digitalAssetMetadata.getGenesisTransaction());
            if(cryptoTransactionList==null||cryptoTransactionList.isEmpty()){
                throw new CantGetGenesisTransactionException(CantGetGenesisTransactionException.DEFAULT_MESSAGE,null,"Getting the genesis transaction from Crypto Network","The crypto transaction received is null");
            }
            this.cryptoTransaction=cryptoTransactionList.get(0);
            String op_ReturnFromAssetVault=cryptoTransaction.getOp_Return();
            if(!digitalAssetMetadataHash.equals(op_ReturnFromAssetVault)){
                throw new CantReceiveDigitalAssetException("Cannot receive Digital Asset because the " +
                        "Hash was modified:\n" +
                        "Op_return:"+op_ReturnFromAssetVault+"\n" +
                        "digitalAssetMetadata:"+digitalAssetMetadata);
            }
        } catch (CantGetGenesisTransactionException exception) {
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
