package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.BlockchainNetworkType;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InsufficientMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantSendGenesisAmountException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCreateDigitalAssetTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CryptoWalletBalanceInsufficientException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisAddressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisTransactionException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/09/15.
 */
public class DigitalAssetCryptoTransactionFactory implements DealsWithErrors{

    private AssetIssuingTransactionDao assetIssuingTransactionDao;
    String digitalAssetFileName;
    String digitalAssetFileStoragePath;
    CryptoVaultManager cryptoVaultManager;
    CryptoWallet cryptoWallet;
    DigitalAsset digitalAsset;
    ErrorManager errorManager;
    PluginFileSystem pluginFileSystem;
    UUID pluginId;
    TransactionStatus transactionStatus;
    final String LOCAL_STORAGE_PATH="digital-asset/";
    String digitalAssetLocalFilePath;
    int assetsAmount;
    BlockchainNetworkType blockchainNetworkType;
    private final int MINIMAL_DIGITAL_ASSET_TO_GENERATE_AMOUNT=1;

    Logger LOG = Logger.getGlobal();

    public DigitalAssetCryptoTransactionFactory(UUID pluginId, CryptoVaultManager cryptoVaultManager, CryptoWallet cryptoWallet, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem/*, CryptoAddressBookManager cryptoAddressBookManager*/) throws CantSetObjectException, CantExecuteDatabaseOperationException {

        this.cryptoVaultManager=cryptoVaultManager;
        this.cryptoWallet=cryptoWallet;
        this.pluginFileSystem=pluginFileSystem;
        this.pluginId=pluginId;
        //this.pluginDatabaseSystem=pluginDatabaseSystem;
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

    private void areObjectsSettled() throws ObjectNotSetException{

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

    /*private void checkGenesisAmount() throws ObjectNotSetException{
        long digitalAssetGenesisAmount=this.digitalAsset.getGenesisAmount();
        long calculatedDigitalAssetGenesisAmount=calculateGenesisAmount();
        if(calculatedDigitalAssetGenesisAmount!=digitalAssetGenesisAmount){
            throw new ObjectNotSetException("The Genesis Amount set in Digital Asset is incorrect: '"+digitalAssetGenesisAmount+"' is set in object, Asset Issuing plugin calculates in '"+calculatedDigitalAssetGenesisAmount+"'");
        }
    }*/

    /*private long calculateGenesisAmount(){
        int digitalAssetQuantity=this.digitalAsset.getQuantity();
        long digitalAssetTransactionFee=this.digitalAsset.getTransactionFee();
        long digitalAssetUnitValue=this.digitalAsset.getUnitValue();
        long genesisAmount=((long)digitalAssetQuantity*digitalAssetUnitValue)+(digitalAssetQuantity*digitalAssetTransactionFee);
        return genesisAmount;
    }*/

    private void persistFormingGenesisDigitalAsset() throws CantPersistDigitalAssetException {
        this.assetIssuingTransactionDao.persistDigitalAsset(digitalAsset.getPublicKey(), this.digitalAssetFileStoragePath, this.assetsAmount, this.blockchainNetworkType);
    }

    private String requestHashGenesisTransaction(){
        //this.cryptoVaultManager.getTransactionManager().confirmReception();
        CryptoAddress genesisAddress = this.cryptoVaultManager.getAddress();
        return genesisAddress.getAddress();
    }

    private void checkCryptoWalletBalance() throws CryptoWalletBalanceInsufficientException, CantGetBalanceException {

        String digitalAssetPublicKey=this.digitalAsset.getPublicKey();
        long digitalAssetGenesisAmount=this.digitalAsset.getGenesisAmount();
        //TODO: buscar una nueva forma de hacer esto
        //long cryptoWalletBalance=this.cryptoWallet.getAvailableBalance(digitalAssetPublicKey);

        long cryptoWalletBalance=digitalAssetGenesisAmount;
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
            PluginTextFile digitalAssetFile=this.pluginFileSystem.createTextFile(this.pluginId, this.digitalAssetFileStoragePath, this.digitalAssetFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            digitalAssetFile.setContent(digitalAssetInnerXML);

        }catch(CantCreateFileException exception){
            throw new CantCreateDigitalAssetFileException(exception,"Persisting Digital Asset in local storage","Can't create '"+this.digitalAssetLocalFilePath+this.digitalAssetFileName+"' file");
        }
    }

    private UUID generateTransactionUUID() throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        //Esto lo podría usar para identificación interna del Asset
        UUID transactionUUID=UUID.randomUUID();
        try {
            if(this.assetIssuingTransactionDao.isTransactionIdUsed(transactionUUID)){
                generateTransactionUUID();
            }
        } catch (CantCheckAssetIssuingProgressException| UnexpectedResultReturnedFromDatabaseException exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
        this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionUUID.toString(), TransactionStatus.FORMING_GENESIS);
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

    public void issueDigitalAssets(DigitalAsset digitalAsset, int assetsAmount, BlockchainNetworkType blockchainNetworkType)throws CantIssueDigitalAssetsException{

        this.digitalAsset=digitalAsset;
        this.assetsAmount=assetsAmount;
        int counter=0;
        //Primero chequeamos si el asset está completo
        try {
            setBlockchainNetworkType(blockchainNetworkType);
            if(assetsAmount<MINIMAL_DIGITAL_ASSET_TO_GENERATE_AMOUNT){
                throw new ObjectNotSetException("The assetsAmount "+assetsAmount+" is lower that "+MINIMAL_DIGITAL_ASSET_TO_GENERATE_AMOUNT);
            }
            LOG.info("MAP_CHECK Objects");
            areObjectsSettled();
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
        }

    }

    public void issuePendingDigitalAssets(String publicKey){

        try {
            //Primero, trato de emitir assets, ya persistidos en archivo, pero no emitidos hasta el momento.
            //int pendingDigitalAssetsAmount=this.assetIssuingTransactionDao.getNumberOfPendingAssets(publicKey);
            List<String> pendingDigitalAssetsTransactionIdList=this.assetIssuingTransactionDao.getPendingDigitalAssetsTransactionIdByPublicKey(publicKey);
            for(String pendingDigitalAssetsTransactionId: pendingDigitalAssetsTransactionIdList){
                issueUnfinishedDigitalAsset(pendingDigitalAssetsTransactionId);
            }
            //Ahora emito los assets nuevos.
            //Llamamos a la factory de digital Assets
            //createDigitalAssetCryptoTransaction();
            //Emitimos el asset, ¿a donde? Por definir
        } catch (CantCheckAssetIssuingProgressException  exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
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
                setDigitalAssetGenesisAddress(transactionId, digitalAssetGenesisAddress);
                DigitalAssetMetadata digitalAssetMetadata=new DigitalAssetMetadata(this.digitalAsset);
                setDigitalAssetGenesisTransaction(transactionId, digitalAssetMetadata);
                issueDigitalAssetToAssetWallet(transactionId, digitalAssetMetadata);
            }
            //TODO: caso SENDING BITCOINS
        } catch (CantPersistsGenesisTransactionException | CantExecuteQueryException | CantPersistsGenesisAddressException | CantCheckAssetIssuingProgressException | CantGetDigitalAssetFromLocalStorageException | UnexpectedResultReturnedFromDatabaseException exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }

    private void persistsGenesisAddress(String transactionId, String genesisAddress) throws CantPersistsGenesisAddressException {
        this.assetIssuingTransactionDao.persistGenesisAddress(transactionId, genesisAddress);
    }

    private void setDigitalAssetGenesisAddress(String transactionID, String genesisAddress) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        CryptoAddress cryptoGenesisAddress=new CryptoAddress();
        cryptoGenesisAddress.setAddress(genesisAddress);
        this.digitalAsset.setGenesisAddress(cryptoGenesisAddress);
        this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionID, TransactionStatus.GENESIS_SETTLED);
    }

    private void setDigitalAssetGenesisTransaction(String transactionID, DigitalAssetMetadata digitalAssetMetadata) throws CantPersistsGenesisTransactionException, UnexpectedResultReturnedFromDatabaseException {

        String genesisTransaction=digitalAssetMetadata.getDigitalAssetHash();
        this.digitalAsset.setGenesisTransaction(genesisTransaction);
        this.assetIssuingTransactionDao.persistGenesisTransaction(transactionID, genesisTransaction);

    }

    private void getDigitalAssetGenesisAddressByUUID(String transactionId) throws CantPersistsGenesisAddressException {
        //Solicito la genesisAddress
        String genesisAddress=requestHashGenesisTransaction();
        persistsGenesisAddress(transactionId, genesisAddress);
    }

    private void issueDigitalAssetToAssetWallet(String transactionId, DigitalAssetMetadata digitalAssetMetadata) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionId, TransactionStatus.ISSUING);
        //Todo: por definir con Franklin que hacer aca
        //Establecer protocolo de traspaso de responsabilidad
    }

    private void sendBitcoinsToAssetWallet(String transactionId, UUID genesisTransaction, CryptoAddress genesisAddress, long genesisAmount) throws CantSendGenesisAmountException{
        try {
            this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionId, TransactionStatus.SENDING_BITCOINS);
            this.cryptoVaultManager.sendBitcoins(this.digitalAsset.getPublicKey(), genesisTransaction, genesisAddress, genesisAmount);
        } catch (InsufficientMoneyException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "The balance is insufficient to deliver the genesis amount: "+genesisAmount);
        } catch (InvalidSendToAddressException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "The sendToAddress is invalid: "+genesisTransaction);
        } catch (CouldNotSendMoneyException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "Cannot send the money");
        } catch (CryptoTransactionAlreadySentException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "The crypto transaction is already sent: "+genesisTransaction);
        } catch (CantExecuteQueryException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "Cannot update the database: "+transactionId);
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "Unexpected result reading database: "+genesisTransaction);
        }
    }

    //This method can change in the future, I prefer design an monitor to create Digital Asset.
    private void createDigitalAssetCryptoTransaction() throws CantCreateDigitalAssetTransactionException {

        /**
         * Este método lo usaré para pedir las transacciones de cada digital asset, mucho de lo que estaba en este método ahora pertenece al
         * método issueDigitalAssets.
         */
        try{
            //Primero, asigno un UUID interno
            UUID transactionUUID=generateTransactionUUID();
            String transactionId=transactionUUID.toString();
            //Solicito la genesisAddress
            String genesisAddress=requestHashGenesisTransaction();
            //La persisto en base de datos
            persistsGenesisAddress(transactionId,genesisAddress);
            //Le asigno al Digital Asset la genesisAddress
            setDigitalAssetGenesisAddress(transactionId,genesisAddress);
            //Creo el digitalAssetMetadata
            DigitalAssetMetadata digitalAssetMetadata=new DigitalAssetMetadata(this.digitalAsset);
            //Creo el hash y se lo asigno al digitalAsset
            setDigitalAssetGenesisTransaction(transactionId,digitalAssetMetadata);

        } catch (CantPersistsGenesisAddressException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception,"Issuing a new Digital Asset","Cannot persists the Digital Asset genesis Address in database");
        } catch (CantExecuteQueryException |UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception,"Issuing a new Digital Asset","Cannot update the Digital Asset Transaction Status in database");
        } catch (CantPersistsGenesisTransactionException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception,"Issuing a new Digital Asset","Cannot persists the Digital Asset genesis transaction in database");
        }


    }

}
