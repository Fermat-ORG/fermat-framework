package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.BalanceType;
//import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.CantGetOutgoingIntraUserTransactionManagerException;
//import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.OutgoingIntraUserCantSendFundsExceptions;
//import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.OutgoingIntraUserInsufficientFundsException;
//import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.interfaces.OutgoingIntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.exceptions.GetNewCryptoAddressException;
//import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
//import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions;
//import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CoultNotCreateCryptoTransaction;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantSendGenesisAmountException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCreateDigitalAssetTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CryptoWalletBalanceInsufficientException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantGetGenesisAddressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantIssueDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisAddressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisTransactionException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/09/15.
 */
public class DigitalAssetCryptoTransactionFactory implements DealsWithErrors{

    private AssetIssuingTransactionDao assetIssuingTransactionDao;
    AssetVaultManager assetVaultManager;
    String digitalAssetFileName;
    String digitalAssetFileStoragePath;
    CryptoVaultManager cryptoVaultManager;
    CryptoWallet cryptoWallet;
    CryptoAddressBookManager cryptoAddressBookManager;
    DigitalAsset digitalAsset;
    ErrorManager errorManager;
    PluginFileSystem pluginFileSystem;
    UUID pluginId;
    TransactionStatus transactionStatus;
    final String LOCAL_STORAGE_PATH="digital-asset-issuing/";
    String digitalAssetLocalFilePath;
    int assetsAmount;
    BlockchainNetworkType blockchainNetworkType;
    String walletPublicKey;
    DigitalAssetMetadataVault digitalAssetMetadataVault;
    OutgoingIntraActorManager outgoingIntraActorManager;
    private final int MINIMAL_DIGITAL_ASSET_TO_GENERATE_AMOUNT=1;

    Logger LOG = Logger.getGlobal();

    public DigitalAssetCryptoTransactionFactory(UUID pluginId,
                                                CryptoVaultManager cryptoVaultManager,
                                                CryptoWallet cryptoWallet,
                                                PluginDatabaseSystem pluginDatabaseSystem,
                                                PluginFileSystem pluginFileSystem,
                                                AssetVaultManager assetVaultManager,
                                                CryptoAddressBookManager cryptoAddressBookManager,
                                                OutgoingIntraActorManager outgoingIntraActorManager) throws CantSetObjectException, CantExecuteDatabaseOperationException {

        this.cryptoVaultManager=cryptoVaultManager;
        this.cryptoWallet=cryptoWallet;
        this.pluginFileSystem=pluginFileSystem;
        this.pluginId=pluginId;
        this.assetVaultManager=assetVaultManager;
        this.cryptoAddressBookManager=cryptoAddressBookManager;
        this.outgoingIntraActorManager = outgoingIntraActorManager;
        assetIssuingTransactionDao=new AssetIssuingTransactionDao(pluginDatabaseSystem,pluginId);

    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager=errorManager;
    }

    private void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType)throws ObjectNotSetException{
        if(blockchainNetworkType==null){
            throw new ObjectNotSetException("The BlockchainNetworkType is null");
        }
        this.blockchainNetworkType=blockchainNetworkType;
    }

    public void setDigitalAssetMetadataVault(DigitalAssetMetadataVault digitalAssetMetadataVault)throws CantSetObjectException{
        if(digitalAssetMetadataVault==null){
            throw new CantSetObjectException("digitalAssetMetadataVault is null");
        }
        this.digitalAssetMetadataVault=digitalAssetMetadataVault;
    }

    private void setDigitalAssetGenesisAmount() throws CantSetObjectException{

        try{
            //TODO: Implement this method with new CryptoVault version
            //CryptoAddress genesisAddress=this.cryptoVaultManager.getAddress();
            //digitalAsset.setGenesisAddress(genesisAddress);
        }catch(Exception exception){
            throw new CantSetObjectException(exception, "Setting GenesisAddress to DigitalAsset","Unexpected exception");
        }

    }

    //This method can be used by a future monitor agent
    public void setTransactionStatus(TransactionStatus transactionStatus) throws ObjectNotSetException{
        if(transactionStatus==null){
            throw new ObjectNotSetException("Transaction Status is null");
        }
        this.transactionStatus=transactionStatus;
    }

    public void setWalletPublicKey(String walletPublicKey)throws ObjectNotSetException{
        if(walletPublicKey==null){
            throw new ObjectNotSetException("walletPublicKey is null");
        }
        this.walletPublicKey=walletPublicKey;
    }

    private void areObjectsSettled() throws ObjectNotSetException, CantIssueDigitalAssetException {

        LOG.info("MAP_inside check");
        if(this.digitalAsset.getContract()==null){
            throw new ObjectNotSetException("Digital Asset Contract is not set");
        }
        if(this.digitalAsset.getResources()==null){
            throw new ObjectNotSetException("Digital Asset Resources is not set");
        }
        if(this.digitalAsset.getDescription()==null){
            throw new ObjectNotSetException("Digital Asset Description is not set");
        }
        if(this.digitalAsset.getName()==null){
            throw new ObjectNotSetException("Digital Asset Name is not set");
        }
        if(this.digitalAsset.getPublicKey()==null){
            throw new ObjectNotSetException("Digital Asset PublicKey is not set");
        }

        if(this.digitalAsset.getState()==null){
            digitalAsset.setState(State.DRAFT);
        }
        LOG.info("MAP_TO check identity");
        //I'm gonna Comment this, this broke the app
        /*if(this.digitalAsset.getIdentityAssetIssuer()==null){
            throw new ObjectNotSetException("Digital Asset Identity is not set");
        }*/
        LOG.info("MAP_ check ended");

    }

    private void isPublicKeyInDatabase(String publicKey) throws CantIssueDigitalAssetException {
        try{
            if(this.assetIssuingTransactionDao.isPublicKeyUsed(publicKey)){
                throw new CantIssueDigitalAssetException("The public key "+publicKey+" is already registred in database");
            }
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantIssueDigitalAssetException(exception, "Checking the asset publicKey in database", "Unexpected results");
        } catch (CantCheckAssetIssuingProgressException exception) {
            throw new CantIssueDigitalAssetException(exception, "Checking the asset publicKey in database", "Cannot check the publicKey in database");
        }

    }

    private void persistFormingGenesisDigitalAsset() throws CantPersistDigitalAssetException {
        this.assetIssuingTransactionDao.persistDigitalAsset(digitalAsset.getPublicKey(), this.digitalAssetFileStoragePath, this.assetsAmount, this.blockchainNetworkType);
    }

    private CryptoAddress requestHashGenesisAddress() throws CantGetGenesisAddressException{
        //this.cryptoVaultManager.getTransactionManager().confirmReception();
        try {
            CryptoAddress genesisAddress = this.assetVaultManager.getNewAssetVaultCryptoAddress(this.blockchainNetworkType);
            return genesisAddress;
        } catch (GetNewCryptoAddressException exception) {
            throw new CantGetGenesisAddressException(exception, "Requesting a genesis address","Cannot get a new crypto address from asset vault");
        }
    }

    private void checkCryptoWalletBalance() throws CryptoWalletBalanceInsufficientException, CantGetBalanceException {

        String digitalAssetPublicKey=this.digitalAsset.getPublicKey();
        long digitalAssetGenesisAmount=this.digitalAsset.getGenesisAmount();
        long cryptoWalletBalance=this.cryptoWallet.getBalance(BalanceType.AVAILABLE, digitalAssetPublicKey);
        //long cryptoWalletBalance=digitalAssetGenesisAmount;
        if(digitalAssetGenesisAmount>cryptoWalletBalance){
            throw new CryptoWalletBalanceInsufficientException("The current balance in Wallet "+digitalAssetPublicKey+" is "+cryptoWalletBalance+" the amount needed is "+digitalAssetGenesisAmount);
        }

    }

    private void setDigitalAssetLocalFilePath(){
        this.digitalAssetFileStoragePath=this.LOCAL_STORAGE_PATH+this.digitalAsset.getPublicKey();
        setDigitalAssetLocalStoragePath();
    }

    private void setDigitalAssetLocalFilePath(String publicKey){
        this.digitalAssetFileStoragePath=this.LOCAL_STORAGE_PATH+publicKey;
        setDigitalAssetLocalStoragePath();
    }

    private void setDigitalAssetLocalStoragePath(){
        this.digitalAssetFileName=this.digitalAsset.getName()+".xml";
        this.digitalAssetLocalFilePath=digitalAssetFileStoragePath+"/"+digitalAssetFileName;
    }

    private void getDigitalAssetFileFromLocalStorage() throws CantGetDigitalAssetFromLocalStorageException{
        try {
            PluginTextFile digitalAssetFile=this.pluginFileSystem.getTextFile(this.pluginId, this.digitalAssetFileStoragePath, this.digitalAssetFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String digitalAssetXMLString=digitalAssetFile.getContent();
            this.digitalAsset= (DigitalAsset) XMLParser.parseXML(digitalAssetXMLString,this.digitalAsset);
        } catch (FileNotFoundException exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot find "+this.digitalAssetLocalFilePath+this.digitalAssetFileName+"' file");
        } catch (CantCreateFileException exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot create "+this.digitalAssetLocalFilePath+this.digitalAssetFileName+"' file");
        }
    }

    private void persistInLocalStorage() throws CantCreateDigitalAssetFileException {
        //Path structure: digital-asset/walletPublicKey/name.xml
        try{
            setDigitalAssetLocalFilePath();
            String digitalAssetInnerXML=digitalAsset.toString();
            PluginTextFile digitalAssetFile=this.pluginFileSystem.createTextFile(this.pluginId, this.digitalAssetFileStoragePath, this.digitalAssetFileName, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
            digitalAssetFile.setContent(digitalAssetInnerXML);
            digitalAssetFile.persistToMedia();
            //test
            /*PluginTextFile TESTdigitalAssetFile=this.pluginFileSystem.getTextFile(this.pluginId, this.digitalAssetFileStoragePath, this.digitalAssetFileName, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
            TESTdigitalAssetFile.loadFromMedia();
            LOG.info("MAP FILE CONTENT:"+TESTdigitalAssetFile.getContent());*/
            //End test
        }catch(CantCreateFileException exception){
            throw new CantCreateDigitalAssetFileException(exception,"Persisting Digital Asset in local storage","Can't create '"+this.digitalAssetLocalFilePath+this.digitalAssetFileName+"' file");
        } catch (CantPersistFileException exception) {
            throw new CantCreateDigitalAssetFileException(exception,"Persisting Digital Asset in local storage","Can't persist '"+this.digitalAssetLocalFilePath+this.digitalAssetFileName+"' file");
        } /*catch (CantLoadFileException e) {
            throw new CantCreateDigitalAssetFileException(e,"TEST","Can't read '"+this.digitalAssetLocalFilePath+this.digitalAssetFileName+"' file");
        } catch (FileNotFoundException e) {
            throw new CantCreateDigitalAssetFileException(e,"TEST","Can't read '"+this.digitalAssetLocalFilePath+this.digitalAssetFileName+"' file");
        }*/
    }

    private UUID generateTransactionUUID() throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException, CantPersistDigitalAssetException {
        //Esto lo podría usar para identificación interna del Asset
        UUID transactionUUID=UUID.randomUUID();
        try {
            if(this.assetIssuingTransactionDao.isTransactionIdUsed(transactionUUID)){
                generateTransactionUUID();
            }
        } catch (CantCheckAssetIssuingProgressException| UnexpectedResultReturnedFromDatabaseException exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
        //this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionUUID.toString(), TransactionStatus.FORMING_GENESIS);
        this.assetIssuingTransactionDao.persistDigitalAssetTransactionId(this.digitalAsset.getPublicKey(), transactionUUID.toString());
        return transactionUUID;
    }

    /*private List<String> getPendingAssets(String publicKey){

        List<String> pendingAssetIssuingTransactionIdList=new ArrayList<>();

        try{
            if(this.assetIssuingTransactionDao.getNumberOfPendingAssets(publicKey)>0){
//TODO: finish this- necesito crear un método en el dao que me devuelva los assets no conluidos
            }
        } catch (CantCheckAssetIssuingProgressException| UnexpectedResultReturnedFromDatabaseException exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }

        return pendingAssetIssuingTransactionIdList;

    }*/

    public void issueDigitalAssets(DigitalAsset digitalAsset, int assetsAmount, String walletPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantIssueDigitalAssetsException, CantDeliverDigitalAssetToAssetWalletException {

        this.digitalAsset=digitalAsset;
        this.assetsAmount=assetsAmount;
        int counter=0;
        //Primero chequeamos si el asset está completo
        try {
            setBlockchainNetworkType(blockchainNetworkType);
            setWalletPublicKey(walletPublicKey);
            if(assetsAmount<MINIMAL_DIGITAL_ASSET_TO_GENERATE_AMOUNT){
                throw new ObjectNotSetException("The assetsAmount "+assetsAmount+" is lower that "+MINIMAL_DIGITAL_ASSET_TO_GENERATE_AMOUNT);
            }
            LOG.info("MAP_CHECK Objects");
            areObjectsSettled();
            isPublicKeyInDatabase(this.digitalAsset.getPublicKey());
            //Persistimos el Asset en archivo
            LOG.info("MAP_persist: ");
            persistInLocalStorage();
            LOG.info("MAP_persist: " + digitalAssetFileStoragePath + "/" + digitalAssetFileName);
            //Persistimos la información del Digital Asset en base de datos
            persistFormingGenesisDigitalAsset();
            LOG.info("MAP_persisted in database");
            /**
             * En este punto debemos generar las Transacciones de cada uno de los digital Assets, pero
             * debo estudiar la mejor forma de hacerlo, creando un thread adicional, podría hacerlo a través de una clase interna a esta
             * o un método que se encargue de generar los Assets pendientes. Me inclino por la primera opción.
             * Sería bueno preguntarlo en una reunión con el equipo. Mientras tanto, lo voy a implementar en un while
             * */

            while(counter<assetsAmount){
                LOG.info("Asset número " + counter);
                createDigitalAssetCryptoTransaction();
                counter++;
            }
        } catch (ObjectNotSetException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Issuing "+assetsAmount+" Digital Assets","Digital Asset object is not complete");
        } catch (CantCreateDigitalAssetFileException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Issuing "+assetsAmount+" Digital Assets","A local storage procedure exception");
        } catch (CantPersistDigitalAssetException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Issuing "+assetsAmount+" Digital Assets","A database procedure exception");
        } catch (CantCreateDigitalAssetTransactionException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Issuing "+assetsAmount+" Digital Assets - Asset number "+counter,"Creating the Digital Asset transaction");
        } catch (CantIssueDigitalAssetException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Issuing "+assetsAmount+" Digital Assets - Asset number "+counter,"The public key is already used");
        }

    }

    public void issuePendingDigitalAssets(String publicKey){

        try {
            List<String> pendingDigitalAssetsTransactionIdList=this.assetIssuingTransactionDao.getPendingDigitalAssetsTransactionIdByPublicKey(publicKey);
            for(String pendingDigitalAssetsTransactionId: pendingDigitalAssetsTransactionIdList){
                issueUnfinishedDigitalAsset(pendingDigitalAssetsTransactionId);
            }
        } catch (CantCheckAssetIssuingProgressException  exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }

    private boolean isDigitalAssetComplete(DigitalAsset digitalAsset, DigitalAssetMetadata digitalAssetMetadata) throws CantCheckAssetIssuingProgressException{
        try {
            areObjectsSettled();
            CryptoAddress genesisAddress=digitalAsset.getGenesisAddress();
            if(genesisAddress==null){
                return false;
            }
            String digitalAssetHash=digitalAssetMetadata.getDigitalAssetHash();
            if(digitalAssetHash==null||digitalAssetHash.equals("")){
                return false;
            }
            String genesisTransaction=digitalAssetMetadata.getGenesisTransaction();
            if(genesisTransaction==null||genesisTransaction.equals("")){
                return false;
            }
            return true;
        } catch (ObjectNotSetException e) {
            return false;
        } catch (CantIssueDigitalAssetException exception) {
            throw new CantCheckAssetIssuingProgressException(exception,"Checking if digital asset is complete", "Unexpected results in database");
        }
    }

    private void issueUnfinishedDigitalAsset(String transactionId){
        /***
         *Este método debe verificar el estatus de cada Asset y proceder de acuerdo a cada uno de ellos.
         * El objetivo es finalizar los digital assets, ya persistidos en base de datos, pero sin emitir.
         */
        String digitalAssetPublicKey;
        try {
            TransactionStatus digitalAssetTransactionStatus=this.assetIssuingTransactionDao.getDigitalAssetTransactionStatus(transactionId);
            //Caso Forming_genesis
            if(digitalAssetTransactionStatus==TransactionStatus.FORMING_GENESIS){
                //FORMING_GENESIS: genesisAddress solicitada pero no presistida en base de datos, Asset persistido en archivo
                //Es necesario leer el archivo para recobrar dicha transacción
                digitalAssetPublicKey=this.assetIssuingTransactionDao.getDigitalAssetPublicKeyById(transactionId);
                setDigitalAssetLocalFilePath(digitalAssetPublicKey);
                //Leemos el archivo que contiene el digitalAsset
                getDigitalAssetFileFromLocalStorage();
                //Obtenemos la digital address y la persisitimos en base de datos
                getDigitalAssetGenesisAddressByUUID(transactionId);
                issueUnfinishedDigitalAsset(transactionId);
            }
            //Caso Genesis_obtained o Genesis_settled
            if(digitalAssetTransactionStatus==TransactionStatus.GENESIS_OBTAINED || digitalAssetTransactionStatus==TransactionStatus.GENESIS_SETTLED || digitalAssetTransactionStatus==TransactionStatus.HASH_SETTLED){
                //Se obtuvo la genesisAddress, se persisitió en bases de datos, se recrea el digital asset desde el archivo y se le setea la genesisAddress desde la base de datos
                digitalAssetPublicKey=this.assetIssuingTransactionDao.getDigitalAssetPublicKeyById(transactionId);
                setDigitalAssetLocalFilePath(digitalAssetPublicKey);
                getDigitalAssetFileFromLocalStorage();
                String digitalAssetGenesisAddress=this.assetIssuingTransactionDao.getDigitalAssetGenesisAddressById(transactionId);
                CryptoAddress cryptoAssetGenesisAddress=new CryptoAddress();
                cryptoAssetGenesisAddress.setAddress(digitalAssetGenesisAddress);
                setDigitalAssetGenesisAddress(transactionId, cryptoAssetGenesisAddress);
                DigitalAssetMetadata digitalAssetMetadata=new DigitalAssetMetadata(this.digitalAsset);
                //CryptoTransaction genesisTransaction=requestGenesisTransaction(cryptoAssetGenesisAddress);
                //setDigitalAssetGenesisTransaction(transactionId,genesisTransaction);
                //Obtengo el hash del digital Asset
                String digitalAssetHash=getDigitalAssetHash(digitalAssetMetadata, transactionId);
                //TODO: finish this
            }
            //TODO: caso SENDING BITCOINS
        } catch (CantGetGenesisAddressException | CantPersistsGenesisTransactionException | CantExecuteQueryException | CantPersistsGenesisAddressException | CantCheckAssetIssuingProgressException | CantGetDigitalAssetFromLocalStorageException | UnexpectedResultReturnedFromDatabaseException exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }

    private void persistsGenesisAddress(String transactionId, String genesisAddress) throws CantPersistsGenesisAddressException {
        this.assetIssuingTransactionDao.persistGenesisAddress(transactionId, genesisAddress);
    }

    private void setDigitalAssetGenesisAddress(String transactionID, CryptoAddress genesisAddress) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        this.digitalAsset.setGenesisAddress(genesisAddress);
        this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionID, TransactionStatus.GENESIS_SETTLED);
    }

    /*private CryptoTransaction requestGenesisTransaction(CryptoAddress genesisAddress) throws CoultNotCreateCryptoTransaction {
        CryptoTransaction genesisTransaction = this.cryptoVaultManager.generateDraftCryptoTransaction(genesisAddress, this.digitalAsset.getGenesisAmount());
        return genesisTransaction;
    }*/

    private void setDigitalAssetGenesisTransaction(String transactionID, String genesisTransaction, DigitalAssetMetadata digitalAssetMetadata) throws CantPersistsGenesisTransactionException, UnexpectedResultReturnedFromDatabaseException, CoultNotCreateCryptoTransaction {

        //this.digitalAsset.setGenesisTransaction(genesisTransaction.getTransactionHash());
        digitalAssetMetadata.setGenesisTransaction(genesisTransaction);
        this.assetIssuingTransactionDao.persistGenesisTransaction(transactionID, genesisTransaction);
    }

    private void getDigitalAssetGenesisAddressByUUID(String transactionId) throws CantPersistsGenesisAddressException, CantGetGenesisAddressException {
        //Solicito la genesisAddress
        CryptoAddress genesisAddress=requestHashGenesisAddress();
        persistsGenesisAddress(transactionId, genesisAddress.getAddress());
    }

    private void deliverDigitalAssetToAssetWallet(String transactionId, DigitalAssetMetadata digitalAssetMetadata, DigitalAsset digitalAsset) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException, CantDeliverDigitalAssetToAssetWalletException {
        this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionId, TransactionStatus.ISSUING);
        try {
            if(!isDigitalAssetComplete(digitalAsset, digitalAssetMetadata)){
                throw new CantDeliverDigitalAssetToAssetWalletException("Cannot deliver the digital asset - is not complete:"+digitalAssetMetadata);
            }
        } catch (CantCheckAssetIssuingProgressException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception,"Cannot deliver the digital asset:" + digitalAssetMetadata,"Unexpected result in database");
        }
        //Todo: por definir con Franklin que hacer aca
        //Establecer protocolo de traspaso de responsabilidad
    }

    private void registerGenesisAddressInCryptoAddressBook(CryptoAddress genesisAddress) throws CantRegisterCryptoAddressBookRecordException{
        //TODO: solicitar los publickeys de los actors, la publicKey de la wallet
        //I'm gonna harcode the actors publicKey
        this.cryptoAddressBookManager.registerCryptoAddress(genesisAddress,
                "testDeliveredByActorPublicKey",
                Actors.INTRA_USER,
                "testDeliveredToActorPublicKey",
                Actors.ASSET_ISSUER,
                Platforms.DIGITAL_ASSET_PLATFORM,
                VaultType.ASSET_VAULT,
                CryptoCurrencyVault.BITCOIN_VAULT.getCode(),
                this.walletPublicKey,
                ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);
    }

    private String getDigitalAssetHash(DigitalAssetMetadata digitalAssetMetadata, String transactionId) throws CantPersistsGenesisTransactionException, UnexpectedResultReturnedFromDatabaseException {
        String digitalAssetHash=digitalAssetMetadata.getDigitalAssetHash();
        this.assetIssuingTransactionDao.persistDigitalAssetHash(transactionId, digitalAssetHash);
        return digitalAssetHash;
    }

    private String sendBitcoins(CryptoAddress genesisAddress, String digitalAssetHash, String transactionId) throws CantSendGenesisAmountException{
        try {
            this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionId, TransactionStatus.SENDING_BITCOINS);
            //this.cryptoVaultManager.sendBitcoins(this.digitalAsset.getPublicKey(), genesisTransaction, genesisAddress, genesisAmount);
            //TODO: Send btc through outgoing intra user
            //genesisTransaction.setOp_Return(digitalAssetHash);
            //this.cryptoVaultManager.sendBitcoins(genesisTransaction);
            //TODO: this genesisTransaction is hardcoded for now. Waiting for a better way to get the genesisTransaction
            String genesisTransaction="d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43";
            this.outgoingIntraActorManager.getTransactionManager().sendCrypto(this.walletPublicKey,
                    genesisAddress,
                    this.digitalAsset.getGenesisAmount(),
                    digitalAssetHash,
                    this.digitalAsset.getDescription(),
                    "senderPublicKey",
                    "receptorPublicKey",
                    Actors.INTRA_USER,
                    Actors.ASSET_ISSUER,
                    ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);
            return genesisTransaction;
        }  catch (CantExecuteQueryException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "Cannot update the database: "+transactionId);
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "Unexpected result reading database: "+transactionId);
        } /*catch (OutgoingIntraUserCantSendFundsExceptions exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "The sendToAddress is invalid: "+genesisAddress);
        } catch (CantGetOutgoingIntraUserTransactionManagerException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "Asset Issuing plugin cannot get the OutgoingIntraUserTransactionManager");
        } catch (OutgoingIntraUserInsufficientFundsException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "The balance is insufficient to deliver the genesis amount: "+this.digitalAsset.getGenesisAmount());
        }*/ catch (OutgoingIntraActorInsufficientFundsException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "The balance is insufficient to deliver the genesis amount: "+this.digitalAsset.getGenesisAmount());
        } catch (CantGetOutgoingIntraActorTransactionManagerException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "Asset Issuing plugin cannot get the OutgoingIntraUserTransactionManager");
        } catch (OutgoingIntraActorCantSendFundsExceptions exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "The sendToAddress is invalid: "+genesisAddress);
        }
    }

    //This method can change in the future, I prefer design an monitor to create Digital Asset.
    private void createDigitalAssetCryptoTransaction() throws CantCreateDigitalAssetTransactionException, CantIssueDigitalAssetException, CantDeliverDigitalAssetToAssetWalletException {

        /**
         * Este método lo usaré para pedir las transacciones de cada digital asset, mucho de lo que estaba en este método ahora pertenece al
         * método issueDigitalAssets.
         */
        try{
            //Primero, asigno un UUID interno
            UUID transactionUUID=generateTransactionUUID();
            String transactionId=transactionUUID.toString();
            //Solicito la genesisAddress
            CryptoAddress genesisAddress=requestHashGenesisAddress();
            //La persisto en base de datos
            persistsGenesisAddress(transactionId,genesisAddress.getAddress());
            //Registro genesisAddress in AddressBook
            registerGenesisAddressInCryptoAddressBook(genesisAddress);
            //Le asigno al Digital Asset la genesisAddress
            setDigitalAssetGenesisAddress(transactionId,genesisAddress);
            //Creo el digitalAssetMetadata
            DigitalAssetMetadata digitalAssetMetadata=new DigitalAssetMetadata(this.digitalAsset);
            //Creo la genesisTransaction y se lo asigno al digitalAsset
            //CryptoTransaction genesisTransaction=requestGenesisTransaction(genesisAddress);
            //setDigitalAssetGenesisTransaction(transactionId, genesisTransaction);
            //Obtengo el hash del digital Asset
            String digitalAssetHash=getDigitalAssetHash(digitalAssetMetadata, transactionId);
            //LOG.info("MAP_DIGITAL ASSET FULL: "+this.digitalAsset);
            LOG.info("MAP_HASH DEL ASSET: " + digitalAssetHash);
            //envío de BTC
            String genesisTransaction=sendBitcoins(genesisAddress, digitalAssetHash, transactionId);
            digitalAssetMetadata.setGenesisTransaction(genesisTransaction);
            //Entregamos el digital asset a la AssetWallet
            deliverDigitalAssetToAssetWallet(transactionId, digitalAssetMetadata, this.digitalAsset);

        } catch (CantPersistsGenesisAddressException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception,"Issuing a new Digital Asset","Cannot persists the Digital Asset genesis Address in database");
        } catch (CantExecuteQueryException |UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception,"Issuing a new Digital Asset","Cannot update the Digital Asset Transaction Status in database");
        } catch (CantPersistsGenesisTransactionException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception,"Issuing a new Digital Asset","Cannot persists the Digital Asset genesis transaction in database");
        } catch (CantRegisterCryptoAddressBookRecordException exception){
            throw new CantCreateDigitalAssetTransactionException(exception,"Issuing a new Digital Asset","Cannot register the Digital Asset genesis transaction in address book");
        } catch (CantGetGenesisAddressException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception,"Issuing a new Digital Asset","Cannot get the Digital Asset genesis address from asset vault");
        } /*catch (CoultNotCreateCryptoTransaction exception) {
            throw new CantCreateDigitalAssetTransactionException(exception,"Issuing a new Digital Asset","Cannot get the Digital Asset genesis transaction from bitcoin vault");
        }*/ catch (CantPersistDigitalAssetException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception,"Issuing a new Digital Asset","Cannot persists the Digital Asset genesis transaction id in database");
        } catch (CantSendGenesisAmountException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception,"Issuing a new Digital Asset","Cannot send the Digital Asset genesis amount");
        }

    }

}
