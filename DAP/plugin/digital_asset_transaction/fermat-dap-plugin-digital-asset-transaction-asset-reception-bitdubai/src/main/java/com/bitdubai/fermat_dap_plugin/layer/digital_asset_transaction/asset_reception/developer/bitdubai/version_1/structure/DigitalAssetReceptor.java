package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.DAPException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.ReceptionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistsTransactionUUIDException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetSwap;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.exceptions.CantReceiveDigitalAssetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.database.AssetReceptionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;


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
    DistributionStatus distributionStatus;
    BitcoinNetworkManager bitcoinNetworkManager;

    DigitalAssetReceptionVault digitalAssetReceptionVault;
    //AssetVaultManager assetVaultManager;

    public DigitalAssetReceptor(/*AssetVaultManager assetVaultManager,*/ ErrorManager errorManager, UUID pluginId, PluginFileSystem pluginFileSystem, BitcoinNetworkManager bitcoinNetworkManager) throws CantExecuteDatabaseOperationException {
        super(/*assetVaultManager,*/  pluginId, pluginFileSystem);
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.setBitcoinCryptoNetworkManager(this.bitcoinNetworkManager);

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
            DigitalAsset digitalAsset=digitalAssetMetadata.getDigitalAsset();
            String genesisTransaction=digitalAssetMetadata.getGenesisTransaction();
            DigitalAssetContract digitalAssetContract=digitalAsset.getContract();
            this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.CHECKING_CONTRACT, genesisTransaction);
            if(!isValidContract(digitalAssetContract)){
                System.out.println("ASSET RECEPTION The contract is not valid");
                this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.REJECTED_BY_CONTRACT, genesisTransaction);
                return;
                //I don't want to throw this exception right now, I need to inform to issuer the asset condition
                //throw new CantReceiveDigitalAssetException("The DigitalAsset Contract is not valid, the expiration date has passed");
            }
            this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.CONTRACT_CHECKED, genesisTransaction);
            this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.CHECKING_HASH, genesisTransaction);
            if(!isDigitalAssetHashValid(digitalAssetMetadata)){
                System.out.println("ASSET RECEPTION The DAM Hash is not valid");
                this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.REJECTED_BY_HASH, genesisTransaction);
                return;
                //throw new CantReceiveDigitalAssetException("The DigitalAsset hash is not valid");
            }
            this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.HASH_CHECKED, genesisTransaction);
            this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.ASSET_ACCEPTED, genesisTransaction);
        } catch (CantPersistDigitalAssetException exception) {
            throw new CantReceiveDigitalAssetException(exception, "Receiving Digital Asset Metadata", "Cannot persist Digital Asset Metadata");
        } catch (CantCreateDigitalAssetFileException exception) {
            throw new CantReceiveDigitalAssetException(exception, "Receiving Digital Asset Metadata", "Cannot create Digital Asset Metadata file in local storage");
        } catch (CantExecuteQueryException exception) {
            throw new CantReceiveDigitalAssetException(exception, "Receiving Digital Asset Metadata", "There is a error executing a database operation");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantReceiveDigitalAssetException(exception, "Receiving Digital Asset Metadata", "Unexpected result in databse");
        } catch (CantGetCryptoTransactionException exception) {
            throw new CantReceiveDigitalAssetException(exception, "Receiving Digital Asset Metadata", "Cannot get the genesis transaction from crypto network");
        } catch (DAPException exception) {
            throw new CantReceiveDigitalAssetException(exception, "Receiving Digital Asset Metadata", "Unexpected DAP exception");
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
     * This method check if the DigitalAssetMetadata remains with no modifications
     * */
    public void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws CantReceiveDigitalAssetException {
        try{
            String genesisTransactionFromDigitalAssetMetadata=digitalAssetMetadata.getGenesisTransaction();
            this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.CHECKING_HASH, genesisTransactionFromDigitalAssetMetadata);
            String digitalAssetMetadataHash=digitalAssetMetadata.getDigitalAssetHash();
            List<CryptoTransaction> cryptoTransactionList = bitcoinNetworkManager.getCryptoTransaction(digitalAssetMetadata.getGenesisTransaction());
            if(cryptoTransactionList==null||cryptoTransactionList.isEmpty()){
                throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE,null,"Getting the genesis transaction from Crypto Network","The crypto transaction received is null");
            }
            this.cryptoTransaction=cryptoTransactionList.get(0);
            String op_ReturnFromAssetVault=cryptoTransaction.getOp_Return();
            if(!digitalAssetMetadataHash.equals(op_ReturnFromAssetVault)){
                throw new CantReceiveDigitalAssetException("Cannot receive Digital Asset because the " +
                        "Hash was modified:\n" +
                        "Op_return:"+op_ReturnFromAssetVault+"\n" +
                        "digitalAssetMetadata:"+digitalAssetMetadata);
            }
        } catch (CantGetCryptoTransactionException exception) {
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
        //this.assetDistributionDao.persistDigitalAsset(digitalAssetMetadata.getGenesisTransaction(), this.digitalAssetFileStoragePath, digitalAssetMetadata.getDigitalAssetHash(), actorAssetUser.getActorPublicKey());
        persistInLocalStorage(digitalAssetMetadata);
    }

    @Override
    public void persistInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException {
        UUID receptionId=UUID.randomUUID();
        try {
            this.assetReceptionDao.persistReceptionId(digitalAssetMetadata.getGenesisTransaction(), receptionId);
            this.digitalAssetReceptionVault.persistDigitalAssetMetadataInLocalStorage(digitalAssetMetadata, receptionId.toString());
        } catch (CantPersistsTransactionUUIDException exception) {
            throw new CantCreateDigitalAssetFileException(exception,"Persisting Receiving digital asset metadata","Cannot persists internal id in database");
        }

    }

    @Override
    public void setDigitalAssetLocalFilePath(DigitalAssetMetadata digitalAssetMetadata) {
        this.digitalAssetFileStoragePath=this.LOCAL_STORAGE_PATH+"/"+digitalAssetMetadata.getDigitalAssetHash();
        this.digitalAssetReceptionVault.setDigitalAssetLocalFilePath(this.digitalAssetFileStoragePath);
    }

}
