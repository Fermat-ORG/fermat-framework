package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.DAPException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendDigitalAssetMetadataException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistsTransactionUUIDException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetSwap;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions.CantRedeemDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.database.UserRedemptionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 02/11/15.
 */
public class UserRedemptionRedeemer extends AbstractDigitalAssetSwap {

    //ActorAssetIssuerManager actorAssetIssuerManager;
    ActorAssetUser actorAssetUser;
    //AssetVaultManager assetVaultManager;
    ErrorManager errorManager;
    final String LOCAL_STORAGE_PATH="user-redemption/";
    String digitalAssetFileStoragePath;
    //String digitalAssetMetadataFileStoragePath;
    UserRedemptionDao userRedemptionDao;
    DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault;
    //BitcoinNetworkManager bitcoinNetworkManager;

    public UserRedemptionRedeemer(/*AssetVaultManager assetVaultManager,*/ ErrorManager errorManager, UUID pluginId, PluginFileSystem pluginFileSystem) throws CantExecuteDatabaseOperationException {
        super(/*assetVaultManager,*/  pluginId, pluginFileSystem);
        this.errorManager=errorManager;
    }

    public void setUserRedemptionDao(UserRedemptionDao userRedemptionDao)throws CantSetObjectException {
        if(userRedemptionDao ==null){
            throw new CantSetObjectException("userRedemptionDao is null");
        }
        this.userRedemptionDao = userRedemptionDao;
    }

    public void setDigitalAssetUserRedemptionVault(DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault) throws CantSetObjectException {
        if(digitalAssetUserRedemptionVault ==null){
            throw new CantSetObjectException("digitalAssetUserRedemptionVault is null");
        }
        this.digitalAssetUserRedemptionVault = digitalAssetUserRedemptionVault;
    }

    public void setWalletPublicKey(String walletPublicKey) throws CantSetObjectException {
        this.digitalAssetUserRedemptionVault.setWalletPublicKey(walletPublicKey);
    }

    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) throws CantGetAssetUserActorsException {
        try {
            this.actorAssetUser =actorAssetUserManager.getActorAssetUser();
        } catch (CantAssetUserActorNotFoundException exception) {
            throw new CantGetAssetUserActorsException(CantAssetUserActorNotFoundException.DEFAULT_MESSAGE,exception, "Setting the Actor Asset User", "Getting the Actor Asset User");
        }
    }

    /**
     * This method check if the DigitalAssetMetadata remains with not modifications
     * */
    public void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws CantRedeemDigitalAssetException {
        try{
            String genesisTransactionFromDigitalAssetMetadata=digitalAssetMetadata.getGenesisTransaction();
            this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_HASH, genesisTransactionFromDigitalAssetMetadata);
            String digitalAssetMetadataHash=digitalAssetMetadata.getDigitalAssetHash();
            List<CryptoTransaction> cryptoTransactionList = bitcoinNetworkManager.getGenesisTransaction(genesisTransactionFromDigitalAssetMetadata);
            if(cryptoTransactionList==null||cryptoTransactionList.isEmpty()){
                throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE,null,"Getting the genesis transaction from Crypto Network","The crypto transaction received is null");
            }
            //This won't work until I can get the CryptoTransaction from AssetVault
            String op_ReturnFromAssetVault=cryptoTransaction.getOp_Return();
            if(!digitalAssetMetadataHash.equals(op_ReturnFromAssetVault)){
                throw new CantRedeemDigitalAssetException("Cannot redeem Digital Asset because the " +
                        "Hash was modified:\n" +
                        "Op_return:"+op_ReturnFromAssetVault+"\n" +
                        "digitalAssetMetadata:"+digitalAssetMetadata);
            }
            this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.HASH_CHECKED, genesisTransactionFromDigitalAssetMetadata);
        } catch (CantGetCryptoTransactionException exception) {
            throw new CantRedeemDigitalAssetException(exception,
                    "Delivering the Digital Asset \n"+digitalAssetMetadata,
                    "Cannot get the genesis transaction from Asset vault");
        } catch (CantExecuteQueryException exception) {
            throw new CantRedeemDigitalAssetException(exception,
                    "Delivering the Digital Asset \n"+digitalAssetMetadata,
                    "Cannot execute a database operation");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantRedeemDigitalAssetException(exception,
                    "Delivering the Digital Asset \n"+digitalAssetMetadata,
                    "Unexpected result in database");
        }
    }

    @Override
    public void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser actorAssetUser) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException {
        //To implement
    }

    /**
     * This method will deliver the DigitalAssetMetadata to ActorAssetUser
     * */
    public void deliverDigitalAssetToRemoteDevice(DigitalAssetMetadata digitalAssetMetadata, ActorAssetRedeemPoint actorAssetRedeemPoint) throws CantRedeemDigitalAssetException {
        try{
            //First, I going to persist in database the basic information about digitalAssetMetadata
            System.out.println("ASSET USER REDEMPTION begins for "+actorAssetRedeemPoint.getPublicKey());
            persistDigitalAsset(digitalAssetMetadata, actorAssetRedeemPoint);
            System.out.println("ASSET USER REDEMPTION begins for persisted");
            //Now, I'll check is Hash wasn't modified
            //checkDigitalAssetMetadata(digitalAssetMetadata);
            DigitalAsset digitalAsset=digitalAssetMetadata.getDigitalAsset();
            System.out.println("ASSET USER REDEMPTION Digital Asset genesis address: "+digitalAsset.getGenesisAddress());
            String genesisTransaction=digitalAssetMetadata.getGenesisTransaction();
            System.out.println("ASSET USER REDEMPTION Genesis transaction:"+genesisTransaction);
            this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_AVAILABLE_BALANCE, genesisTransaction);
            if(!isAvailableBalanceInAssetVault(digitalAsset.getGenesisAmount(), genesisTransaction)){
                System.out.println("ASSET USER REDEMPTION The Available balance in asset vault is insufficient - genesisAmount:"+digitalAsset.getGenesisAmount());
                throw new CantRedeemDigitalAssetException("The Available balance in asset vault is incorrect");
            }
            this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.AVAILABLE_BALANCE_CHECKED, genesisTransaction);
            DigitalAssetContract digitalAssetContract=digitalAsset.getContract();
            System.out.println("ASSET USER REDEMPTION Digital Asset contract: "+digitalAssetContract);
            this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_CONTRACT, genesisTransaction);
            if(!isValidContract(digitalAssetContract)){
                System.out.println("ASSET USER REDEMPTION The contract is not valid");
                throw new CantRedeemDigitalAssetException("The DigitalAsset Contract is not valid, the expiration date has passed");
            }
            this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CONTRACT_CHECKED, genesisTransaction);
            this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_HASH, genesisTransaction);
            if(!isDigitalAssetHashValid(digitalAssetMetadata)){
                System.out.println("ASSET USER REDEMPTION The DAM Hash is not valid");
                throw new CantRedeemDigitalAssetException("The DigitalAsset hash is not valid");
            }
            this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.HASH_CHECKED, genesisTransaction);
            System.out.println("ASSET USER REDEMPTION set debit in asset issuer wallet:" + genesisTransaction);
            digitalAssetUserRedemptionVault.setDigitalAssetMetadataAssetIssuerWalletDebit(digitalAssetMetadata, this.cryptoTransaction, BalanceType.AVAILABLE);
            System.out.println("ASSET USER REDEMPTION Begins the deliver to an remote actor");
            deliverToRemoteActor(digitalAssetMetadata, actorAssetRedeemPoint);
        } catch (CantPersistDigitalAssetException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Cannot persist digital asset into database");
        } catch (CantCreateDigitalAssetFileException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Cannot persist digital asset into local storage");
        } catch (CantGetCryptoTransactionException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Cannot get the genesisTransaction from Asset Vault");
        } catch (CantExecuteQueryException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Cannot execute a database operation");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Unexpected result in database");
        } catch (CantSendDigitalAssetMetadataException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "There is an error delivering the digital asset through the network layer");
        } catch (DAPException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Generic DAP Exception");
        } catch (CantGetTransactionsException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Cannot get the genesis transaction from crypto network");
        } catch (CantLoadWalletException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Cannot load Asset issuer wallet");
        } catch (CantRegisterDebitException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Cannot register a debit in Asset Issuer Wallet");
        }
    }



    private void deliverToRemoteActor(DigitalAssetMetadata digitalAssetMetadata, ActorAssetRedeemPoint actorAssetRedeemPoint)throws CantSendDigitalAssetMetadataException{
        String genesisTransaction;
        try {
            System.out.println("ASSET USER REDEMPTION Preparing delivering to remote actor");
            genesisTransaction=digitalAssetMetadata.getGenesisTransaction();
            System.out.println("ASSET USER REDEMPTION Delivering genesis transaction " + genesisTransaction);
            this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.DELIVERING, genesisTransaction);
            System.out.println("ASSET USER REDEMPTION Sender Actor name " + actorAssetRedeemPoint.getName());
            System.out.println("ASSET USER REDEMPTION Before deliver - remote asset user ");
            //TODO: implement the following line with the proper method
            //this.assetTransmissionNetworkServiceManager.sendDigitalAssetMetadata(this.actorAssetUser,remoteActorAssetUser,digitalAssetMetadata);
        } catch (CantExecuteQueryException exception) {
            throw new CantSendDigitalAssetMetadataException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE,exception,"Delivering Digital Asset Metadata to Remote Actor", "There is an error executing a query in database");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantSendDigitalAssetMetadataException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE,exception,"Delivering Digital Asset Metadata to Remote Actor", "The database return an unexpected result");
        }

    }

    public void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, ActorAssetRedeemPoint actorAssetRedeemPoint) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException {
        System.out.println("ASSET USER REDEMPTION Persist DAM: " + digitalAssetMetadata.getGenesisTransaction());
        setDigitalAssetLocalFilePath(digitalAssetMetadata);
        CryptoAddress cryptoAddress=actorAssetRedeemPoint.getCryptoAddress();
        String actorAddress;
        if(cryptoAddress==null){
            actorAddress="UNDEFINED";
            System.out.println("ASSET USER REDEMPTION Harcoding Actor address because is null" );
        }else{
            actorAddress=cryptoAddress.getAddress();
        }
        this.userRedemptionDao.persistDigitalAsset(
                digitalAssetMetadata.getGenesisTransaction(),
                this.digitalAssetFileStoragePath,
                digitalAssetMetadata.getDigitalAssetHash(),
                actorAssetRedeemPoint.getPublicKey(),
                actorAddress);
        System.out.println("ASSET USER REDEMPTION registered in database");
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
            System.out.println("ASSET USER REDEMPTION Internal Id: "+distributionId);
            this.userRedemptionDao.persistRedemptionId(digitalAssetMetadata.getGenesisTransaction(), distributionId);
            this.digitalAssetUserRedemptionVault.persistDigitalAssetMetadataInLocalStorage(digitalAssetMetadata, distributionId.toString());
        } catch (CantPersistsTransactionUUIDException exception) {
            throw new CantCreateDigitalAssetFileException(exception, "Persisting Internal distribution id", "Cannot update the internal Id by genesis transaction");
        }
    }

    public void setDigitalAssetLocalFilePath(DigitalAssetMetadata digitalAssetMetadata){
        //this.digitalAssetFileName=digitalAssetMetadata.getDigitalAssetHash()+".xml";
        this.digitalAssetFileStoragePath=this.LOCAL_STORAGE_PATH+"/"+digitalAssetMetadata.getDigitalAssetHash();
        this.digitalAssetUserRedemptionVault.setDigitalAssetLocalFilePath(this.digitalAssetFileStoragePath);
        //this.digitalAssetFileName=digitalAssetMetadata.getDigitalAssetHash()+".xml";
        //this.digitalAssetFileStoragePath=this.LOCAL_STORAGE_PATH+"/"+digitalAssetFileName;
    }

    /*public void redeemAsset(DigitalAssetMetadata digitalAssetMetadata, ActorAssetRedeemPoint actorAssetRedeemPoint) throws CantDistributeDigitalAssetsException {
        try{
            System.out.println("ASSET USER REDEMPTION DistributionMap size:"+digitalAssetsToDistribute.size());
            for(Map.Entry<DigitalAssetMetadata, ActorAssetUser> entry: digitalAssetsToDistribute.entrySet()){
                DigitalAssetMetadata digitalAssetMetadata=entry.getKey();
                ActorAssetUser actorAssetUser=entry.getValue();
                //Deliver one DigitalAsset
                System.out.println("ASSET USER REDEMPTION DAM-Hash:"+digitalAssetMetadata.getDigitalAssetHash());
                System.out.println("ASSET USER REDEMPTION ActorAssetUser - PublicKey:"+actorAssetUser.getPublicKey());
                System.out.println("ASSET USER REDEMPTION ActorAssetUser - Name:"+actorAssetUser.getName());
                deliverDigitalAssetToRemoteDevice(digitalAssetMetadata, actorAssetUser);
            }
        } catch(CantRedeemDigitalAssetException exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        } catch(Exception exception){
            throw new CantDistributeDigitalAssetsException(exception, "Distributing Assets", "Unexpected exception");
        }

    }*/
}
