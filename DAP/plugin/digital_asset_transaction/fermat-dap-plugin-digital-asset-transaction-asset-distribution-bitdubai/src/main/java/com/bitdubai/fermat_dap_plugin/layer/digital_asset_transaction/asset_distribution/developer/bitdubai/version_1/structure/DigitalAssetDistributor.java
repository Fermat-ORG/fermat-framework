package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.DAPException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetGenesisTransactionException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendDigitalAssetMetadataException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistsTransactionUUIDException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetSwap;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.exceptions.CantDeliverDigitalAssetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.exceptions.CantGetActorAssetIssuerException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/09/15.
 */
public class DigitalAssetDistributor extends AbstractDigitalAssetSwap {

    //ActorAssetIssuerManager actorAssetIssuerManager;
    ActorAssetIssuer actorAssetIssuer;
    //AssetVaultManager assetVaultManager;
    ErrorManager errorManager;
    final String LOCAL_STORAGE_PATH="digital-asset-distribution/";
    String digitalAssetFileStoragePath;
    //String digitalAssetMetadataFileStoragePath;
    AssetDistributionDao assetDistributionDao;
    DigitalAssetDistributionVault digitalAssetDistributionVault;
    //BitcoinNetworkManager bitcoinNetworkManager;

    public DigitalAssetDistributor(/*AssetVaultManager assetVaultManager,*/ ErrorManager errorManager, UUID pluginId, PluginFileSystem pluginFileSystem) throws CantExecuteDatabaseOperationException {
        super(/*assetVaultManager,*/  pluginId, pluginFileSystem);
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

    public void setActorAssetIssuerManager(ActorAssetIssuerManager actorAssetIssuerManager) throws CantGetActorAssetIssuerException {
        try {
            this.actorAssetIssuer=actorAssetIssuerManager.getActorAssetIssuer();
        } catch (CantGetAssetIssuerActorsException exception) {
            throw new CantGetActorAssetIssuerException(exception, "Setting the Actor Asset Issuer", "Getting the Actor Asset Issuer");
        }
    }

    /**
     * This method check if the DigitalAssetMetadata remains with not modifications
     * */
    public void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws CantDeliverDigitalAssetException {
        try{
            String genesisTransactionFromDigitalAssetMetadata=digitalAssetMetadata.getGenesisTransaction();
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_HASH,genesisTransactionFromDigitalAssetMetadata);
            String digitalAssetMetadataHash=digitalAssetMetadata.getDigitalAssetHash();
            List<CryptoTransaction> cryptoTransactionList = bitcoinNetworkManager.getGenesisTransaction(genesisTransactionFromDigitalAssetMetadata);
            if(cryptoTransactionList==null||cryptoTransactionList.isEmpty()){
                throw new CantGetGenesisTransactionException(CantGetGenesisTransactionException.DEFAULT_MESSAGE,null,"Getting the genesis transaction from Crypto Network","The crypto transaction received is null");
            }
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
            //First, I going to persist in database the basic information about digitalAssetMetadata
            System.out.println("ASSET DISTRIBUTION begins for "+actorAssetUser.getPublicKey());
            persistDigitalAsset(digitalAssetMetadata, actorAssetUser);
            System.out.println("ASSET DISTRIBUTION begins for persisted");
            //Now, I'll check is Hash wasn't modified
            //checkDigitalAssetMetadata(digitalAssetMetadata);
            DigitalAsset digitalAsset=digitalAssetMetadata.getDigitalAsset();
            System.out.println("ASSET DISTRIBUTION Digital Asset genesis address: "+digitalAsset.getGenesisAddress());
            String genesisTransaction=digitalAssetMetadata.getGenesisTransaction();
            System.out.println("ASSET DISTRIBUTION Genesis transaction:"+genesisTransaction);
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_AVAILABLE_BALANCE,genesisTransaction);
            if(!isAvailableBalanceInAssetVault(digitalAsset.getGenesisAmount(), genesisTransaction)){
                System.out.println("ASSET DISTRIBUTION The Available balance in asset vault is insufficient - genesisAmount:"+digitalAsset.getGenesisAmount());
                throw new CantDeliverDigitalAssetException("The Available balance in asset vault is incorrect");
            }
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.AVAILABLE_BALANCE_CHECKED,genesisTransaction);
            DigitalAssetContract digitalAssetContract=digitalAsset.getContract();
            System.out.println("ASSET DISTRIBUTION Digital Asset contract: "+digitalAssetContract);
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_CONTRACT,genesisTransaction);
            if(!isValidContract(digitalAssetContract)){
                System.out.println("ASSET DISTRIBUTION The contract is not valid");
                throw new CantDeliverDigitalAssetException("The DigitalAsset Contract is not valid, the expiration date has passed");
            }
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CONTRACT_CHECKED,genesisTransaction);
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_HASH,genesisTransaction);
            if(!isDigitalAssetHashValid(digitalAssetMetadata)){
                System.out.println("ASSET DISTRIBUTION The DAM Hash is not valid");
                throw new CantDeliverDigitalAssetException("The DigitalAsset hash is not valid");
            }
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.HASH_CHECKED,genesisTransaction);
            System.out.println("ASSET DISTRIBUTION set debit in asset issuer wallet:" + genesisTransaction);
            digitalAssetDistributionVault.setDigitalAssetMetadataAssetIssuerWalletDebit(digitalAssetMetadata, this.cryptoTransaction, BalanceType.AVAILABLE);
            System.out.println("ASSET DISTRIBUTION Begins the deliver to an remote actor");
            deliverToRemoteActor(digitalAssetMetadata, actorAssetUser);
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
        } catch (CantSendDigitalAssetMetadataException exception) {
            throw new CantDeliverDigitalAssetException(exception, "Delivering digital assets", "There is an error delivering the digital asset through the network layer");
        } catch (DAPException exception) {
            throw new CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Generic DAP Exception");
        } catch (CantGetTransactionsException exception) {
            throw new CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot get the genesis transaction from crypto network");
        } catch (CantLoadWalletException exception) {
            throw new CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot load Asset issuer wallet");
        } catch (CantRegisterDebitException exception) {
            throw new CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot register a debit in Asset Issuer Wallet");
        }
    }



    private void deliverToRemoteActor(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser remoteActorAssetUser)throws CantSendDigitalAssetMetadataException{
        String genesisTransaction;
        try {
            System.out.println("ASSET DISTRIBUTION Preparing delivering to remote actor");
            genesisTransaction=digitalAssetMetadata.getGenesisTransaction();
            System.out.println("ASSET DISTRIBUTION Delivering genesis transaction "+genesisTransaction);
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.DELIVERING, genesisTransaction);
            System.out.println("ASSET DISTRIBUTION Sender Actor name "+this.actorAssetIssuer.getName());
            System.out.println("ASSET DISTRIBUTION Before deliver - remote asset user ");
            this.assetTransmissionNetworkServiceManager.sendDigitalAssetMetadata(this.actorAssetIssuer,remoteActorAssetUser,digitalAssetMetadata);
        } catch (CantExecuteQueryException exception) {
            throw new CantSendDigitalAssetMetadataException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE,exception,"Delivering Digital Asset Metadata to Remote Actor", "There is an error executing a query in database");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantSendDigitalAssetMetadataException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE,exception,"Delivering Digital Asset Metadata to Remote Actor", "The database return an unexpected result");
        }

    }

    public void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser actorAssetUser) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException {
        System.out.println("ASSET DISTRIBUTION Persist DAM: " + digitalAssetMetadata.getGenesisTransaction());
        setDigitalAssetLocalFilePath(digitalAssetMetadata);
        CryptoAddress cryptoAddress=actorAssetUser.getCryptoAddress();
        String actorAddress;
        if(cryptoAddress==null){
            actorAddress="UNDEFINED";
            System.out.println("ASSET DISTRIBUTION Harcoding Actor address because is null" );
        }else{
            actorAddress=cryptoAddress.getAddress();
        }
        this.assetDistributionDao.persistDigitalAsset(
                digitalAssetMetadata.getGenesisTransaction(),
                this.digitalAssetFileStoragePath,
                digitalAssetMetadata.getDigitalAssetHash(),
                actorAssetUser.getPublicKey(),
                actorAddress);
        System.out.println("ASSET DISTRIBUTION registered in database");
        persistInLocalStorage(digitalAssetMetadata);
    }

    @Override
    public void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, String senderId) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException {
        //To implement
    }


    public void persistInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException {
        //DigitalAsset Path structure: digital-asset-distribution/hash/digital-asset.xml
        //DigitalAssetMetadata Path structure: digital-asset-distribution/hash/digital-asset-metadata.xml
        //TODO: create an UUID for this asset and persists in database
        try{
            UUID distributionId=UUID.randomUUID();
            System.out.println("ASSET DISTRIBUTION Internal Id: "+distributionId);
            this.assetDistributionDao.persistDistributionId(digitalAssetMetadata.getGenesisTransaction(),distributionId);
            this.digitalAssetDistributionVault.persistDigitalAssetMetadataInLocalStorage(digitalAssetMetadata, distributionId.toString());
        } catch (CantPersistsTransactionUUIDException exception) {
            throw new CantCreateDigitalAssetFileException(exception, "Persisting Internal distribution id", "Cannot update the internal Id by genesis transaction");
        }
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
            System.out.println("ASSET DISTRIBUTION DistributionMap size:"+digitalAssetsToDistribute.size());
            for(Map.Entry<DigitalAssetMetadata, ActorAssetUser> entry: digitalAssetsToDistribute.entrySet()){
                DigitalAssetMetadata digitalAssetMetadata=entry.getKey();
                ActorAssetUser actorAssetUser=entry.getValue();
                //Deliver one DigitalAsset
                System.out.println("ASSET DISTRIBUTION DAM-Hash:"+digitalAssetMetadata.getDigitalAssetHash());
                System.out.println("ASSET DISTRIBUTION ActorAssetUser - PublicKey:"+actorAssetUser.getPublicKey());
                System.out.println("ASSET DISTRIBUTION ActorAssetUser - Name:"+actorAssetUser.getName());
                deliverDigitalAssetToRemoteDevice(digitalAssetMetadata, actorAssetUser);
            }
        } catch(CantDeliverDigitalAssetException exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        } catch(Exception exception){
            throw new CantDistributeDigitalAssetsException(exception, "Distributing Assets", "Unexpected exception");
        }

    }
}
