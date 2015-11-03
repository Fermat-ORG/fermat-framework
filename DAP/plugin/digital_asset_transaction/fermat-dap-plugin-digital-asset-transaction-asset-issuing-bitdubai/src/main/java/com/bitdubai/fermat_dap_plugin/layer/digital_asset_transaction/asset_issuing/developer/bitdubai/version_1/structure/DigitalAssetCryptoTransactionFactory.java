package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBalance;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
//import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.CantGetOutgoingIntraUserTransactionManagerException;
//import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.OutgoingIntraUserCantSendFundsExceptions;
//import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.OutgoingIntraUserInsufficientFundsException;
//import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.interfaces.OutgoingIntraUserManager;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetBalanceException;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;

import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantGetGenesisAddressException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantSendGenesisAmountException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CryptoWalletBalanceInsufficientException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCreateDigitalAssetTransactionException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantIssueDigitalAssetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisAddressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistsTransactionUUIDException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

//import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.CantGetOutgoingIntraUserTransactionManagerException;
//import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.OutgoingIntraUserCantSendFundsExceptions;
//import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.OutgoingIntraUserInsufficientFundsException;
//import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.interfaces.OutgoingIntraUserManager;
//import CantGetOutgoingIntraActorTransactionManagerException;
//import OutgoingIntraActorCantSendFundsExceptions;
//import OutgoingIntraActorInsufficientFundsException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/09/15.
 */
public class DigitalAssetCryptoTransactionFactory implements DealsWithErrors{

    int assetsAmount;
    private AssetIssuingTransactionDao assetIssuingTransactionDao;
    AssetVaultManager assetVaultManager;
    BitcoinWalletBalance bitcoinWalletBalance;
    BlockchainNetworkType blockchainNetworkType;
    CryptoVaultManager cryptoVaultManager;
    BitcoinWalletManager bitcoinWalletManager;
    CryptoAddressBookManager cryptoAddressBookManager;
    String digitalAssetFileName;
    String digitalAssetFileStoragePath;
    String digitalAssetLocalFilePath;
    DigitalAssetIssuingVault digitalAssetIssuingVault;
    DigitalAsset digitalAsset;
    ErrorManager errorManager;
    private final int MINIMAL_DIGITAL_ASSET_TO_GENERATE_AMOUNT=1;
    OutgoingIntraActorManager outgoingIntraActorManager;
    final String LOCAL_STORAGE_PATH="digital-asset-issuing/";
    PluginFileSystem pluginFileSystem;
    UUID pluginId;
    String walletPublicKey;
    String actorToPublicKey;
    //This flag must be used to select the way to send bitcoins from this plugin
    boolean SEND_BTC_FROM_CRYPTO_VAULT =false;
    long genesisAmount=100000;
//TODO: delete this useless object in production, I'm using it just for testing
    Logger LOG = Logger.getGlobal();

    public DigitalAssetCryptoTransactionFactory(UUID pluginId,
                                                CryptoVaultManager cryptoVaultManager,
                                                BitcoinWalletManager bitcoinWalletManager,
                                                PluginDatabaseSystem pluginDatabaseSystem,
                                                PluginFileSystem pluginFileSystem,
                                                AssetVaultManager assetVaultManager,
                                                CryptoAddressBookManager cryptoAddressBookManager,
                                                OutgoingIntraActorManager outgoingIntraActorManager) throws CantSetObjectException, CantExecuteDatabaseOperationException {

        this.cryptoVaultManager=cryptoVaultManager;
        this.bitcoinWalletManager=bitcoinWalletManager;
        this.pluginFileSystem=pluginFileSystem;
        this.pluginId=pluginId;
        this.assetVaultManager=assetVaultManager;
        this.cryptoAddressBookManager=cryptoAddressBookManager;
        this.outgoingIntraActorManager = outgoingIntraActorManager;
        /**
         * Warning: this message is only for testing, TODO: delete on production
         */
        String message;
        if(SEND_BTC_FROM_CRYPTO_VAULT){
            message="Crypto Vault";
        }else{
            message="Outgoing Intra Actor";
        }
        System.out.println("ASSET ISSUING DigitalAssetCryptoTransactionFactory will send the BTC from "+message+"\n" +
                "the current value of SEND_BTC_FROM_CRYPTO_VAULT is "+ SEND_BTC_FROM_CRYPTO_VAULT);
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

    public void setDigitalAssetIssuingVault(DigitalAssetIssuingVault digitalAssetIssuingVault)throws CantSetObjectException{
        if(digitalAssetIssuingVault ==null){
            throw new CantSetObjectException("digitalAssetIssuingVault is null");
        }
        this.digitalAssetIssuingVault = digitalAssetIssuingVault;
    }

    public void setAssetIssuingTransactionDao(AssetIssuingTransactionDao assetIssuingTransactionDao) throws CantSetObjectException {
        this.assetIssuingTransactionDao=assetIssuingTransactionDao;
    }

    public int getNumberOfIssuedAssets(String digitalAssetPublicKey) throws CantCheckAssetIssuingProgressException {
        return this.assetIssuingTransactionDao.getNumberOfIssuedAssets(digitalAssetPublicKey);
    }

    public void setWalletPublicKey(String walletPublicKey)throws ObjectNotSetException{
        if(walletPublicKey==null){
            throw new ObjectNotSetException("walletPublicKey is null");
        }
        this.walletPublicKey=walletPublicKey;
    }

    public void setActorAssetIssuerManager(ActorAssetIssuerManager actorAssetIssuerManager) throws CantSetObjectException {
        try {
            this.actorToPublicKey=actorAssetIssuerManager.getActorAssetIssuer().getPublicKey();
            if(this.actorToPublicKey==null){
                this.actorToPublicKey="actorPublicKeyNotFound";
            }
            System.out.println("ASSET ISSUING Actor Asset Issuer public key "+actorToPublicKey);
        } catch (CantGetAssetIssuerActorsException exception) {
            throw new CantSetObjectException(exception, "Setting the actor asset issuer manager","Cannot get the actor asset issuer manager");
        }

    }

    /**
     * This method checks that every object in Digital asset is set.
     * @throws ObjectNotSetException
     * @throws CantIssueDigitalAssetException
     */
    private void areObjectsSettled() throws ObjectNotSetException, CantIssueDigitalAssetException {

        LOG.info("ASSET ISSUING Digital Asset begins check");
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
        //LOG.info("MAP_TO check identity");
        if(this.digitalAsset.getIdentityAssetIssuer()==null){
            throw new ObjectNotSetException("Digital Asset Identity is not set");
        }
        LOG.info("ASSET ISSUING Digital Asset check ended");

    }

    /**
     * This method checks that the Digital Asset publicKey is registered in Database
     * @param publicKey
     * @throws CantIssueDigitalAssetException
     */
    private void isPublicKeyInDatabase(String publicKey) throws CantIssueDigitalAssetException {
        try{
            if(this.assetIssuingTransactionDao.isPublicKeyUsed(publicKey)){
                throw new CantIssueDigitalAssetException("The public key "+publicKey+" is already registered in database");
            }
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantIssueDigitalAssetException(exception, "Checking the asset publicKey in database", "Unexpected results");
        } catch (CantCheckAssetIssuingProgressException exception) {
            throw new CantIssueDigitalAssetException(exception, "Checking the asset publicKey in database", "Cannot check the publicKey in database");
        }

    }

    /**
     * This method persists the digital asset in database
     * @throws CantPersistDigitalAssetException
     */
    private void persistFormingGenesisDigitalAsset() throws CantPersistDigitalAssetException {
        this.assetIssuingTransactionDao.persistDigitalAsset(
                digitalAsset.getPublicKey(),
                this.digitalAssetFileStoragePath,
                this.assetsAmount,
                this.blockchainNetworkType,
                this.walletPublicKey);
    }

    /**
     * This method gets the genesis address from asset vault
     * @return
     * @throws CantGetGenesisAddressException
     */
    private CryptoAddress requestGenesisAddress() throws CantGetGenesisAddressException{
        try {
            CryptoAddress genesisAddress = this.assetVaultManager.getNewAssetVaultCryptoAddress(this.blockchainNetworkType);
            LOG.info("ASSET ISSUING GENESIS ADDRESS GENERATED:"+genesisAddress.getAddress());
            return genesisAddress;
        } catch (GetNewCryptoAddressException exception) {
            throw new CantGetGenesisAddressException(exception, "Requesting a genesis address","Cannot get a new crypto address from asset vault");
        }
    }

    /**
     * This method checks if is available balance in the selected crypto wallet by wallet Public key
     * @throws CryptoWalletBalanceInsufficientException
     * @throws CantGetBalanceException
     */
    private void checkCryptoWalletBalance() throws CantLoadWalletException, CantCalculateBalanceException, CryptoWalletBalanceInsufficientException {

        if(this.bitcoinWalletBalance==null){
            BitcoinWalletWallet bitcoinWalletWallet= this.bitcoinWalletManager.loadWallet(this.walletPublicKey);
            this.bitcoinWalletBalance=bitcoinWalletWallet.getBalance(BalanceType.AVAILABLE);
        }
        long bitcoinWalletAvailableBalance=bitcoinWalletBalance.getBalance();
        long digitalAssetGenesisAmount=this.digitalAsset.getGenesisAmount();
        if(digitalAssetGenesisAmount>bitcoinWalletAvailableBalance){
            throw new CryptoWalletBalanceInsufficientException("The current balance in Wallet "+this.walletPublicKey+" is "+bitcoinWalletAvailableBalance+" the amount needed is "+digitalAssetGenesisAmount);
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

    /**
     * This method gets the selected digital asset from local storage
     * @throws CantGetDigitalAssetFromLocalStorageException
     */
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

    /**
     * This method persists the digital asset in local storage
     * @throws CantCreateDigitalAssetFileException
     */
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

    /**
     * This method generate a random UUID to identify the asset issuing process in database. The value cannot be repeated
     * @return
     * @throws CantExecuteQueryException
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantPersistDigitalAssetException
     */
    private UUID generateTransactionUUID() throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException, CantPersistDigitalAssetException {
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

    /**
     * This method issue multiple digital assets
     * @param digitalAsset
     * @param assetsAmount
     * @param walletPublicKey
     * @param blockchainNetworkType
     * @throws CantIssueDigitalAssetsException
     * @throws CantDeliverDigitalAssetToAssetWalletException
     */
    public void issueDigitalAssets(DigitalAsset digitalAsset, int assetsAmount, String walletPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantIssueDigitalAssetsException, CantDeliverDigitalAssetToAssetWalletException {

        this.digitalAsset=digitalAsset;
        this.assetsAmount=assetsAmount;
        int counter=0;
        //Check if digital asset is complete
        try {
            setBlockchainNetworkType(blockchainNetworkType);
            setWalletPublicKey(walletPublicKey);
            this.digitalAssetIssuingVault.setWalletPublicKey(walletPublicKey);
            if(assetsAmount<MINIMAL_DIGITAL_ASSET_TO_GENERATE_AMOUNT){
                throw new ObjectNotSetException("The assetsAmount "+assetsAmount+" is lower that "+MINIMAL_DIGITAL_ASSET_TO_GENERATE_AMOUNT);
            }
            LOG.info("ASSET ISSUING Digital Asset CHECK Objects");
            areObjectsSettled();
            //FOR TESTING
            this.genesisAmount=this.digitalAsset.getGenesisAmount();
            if(!SEND_BTC_FROM_CRYPTO_VAULT){
                isPublicKeyInDatabase(this.digitalAsset.getPublicKey());
            }
            //Persist the digital asset in local storage
            //LOG.info("MAP_persist: ");
            persistInLocalStorage();
            LOG.info("ASSET ISSUING Digital Asset persist: " + digitalAssetFileStoragePath + "/" + digitalAssetFileName);
            //Persist the digital asset in database
            if(SEND_BTC_FROM_CRYPTO_VAULT){
                if(!this.assetIssuingTransactionDao.isPublicKeyUsed(this.digitalAsset.getPublicKey())){
                    persistFormingGenesisDigitalAsset();
                }
            }else{
                persistFormingGenesisDigitalAsset();
            }

            LOG.info("ASSET ISSUING persisted in database");
            /**
             * En este punto debemos generar las Transacciones de cada uno de los digital Assets, pero
             * debo estudiar la mejor forma de hacerlo, creando un thread adicional, podría hacerlo a través de una clase interna a esta
             * o un método que se encargue de generar los Assets pendientes. Me inclino por la primera opción.
             * Sería bueno preguntarlo en una reunión con el equipo. Mientras tanto, lo voy a implementar en un while
             * */
            while(counter<assetsAmount){
                LOG.info("ASSET ISSUING Asset number " + counter);
                createDigitalAssetCryptoTransaction();
                counter++;
                //this.assetIssuingTransactionDao.updateAssetsGeneratedCounter(this.digitalAsset.getPublicKey(), counter);
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
        } /*catch (CantCheckAssetIssuingProgressException exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,exception);
        }*/ catch (CantSetObjectException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Issuing "+assetsAmount+" Digital Assets","The wallet public key is probably null");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Issuing "+assetsAmount+" Digital Assets - Asset number "+counter,"Unexpected results in database");
        } catch (CantCheckAssetIssuingProgressException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Issuing "+assetsAmount+" Digital Assets - Asset number "+counter,"Cannot check the asset issuing progress");
        }
    }

    /**
     * This method issue unfinished or interrupted digital assets
     * @param publicKey
     */
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

    /**
     * This method checks if the digital Asset metadata is complete and ready to issue
     * @param digitalAsset
     * @param digitalAssetMetadata
     * @return
     * @throws CantCheckAssetIssuingProgressException
     */
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
            /*String genesisTransaction=digitalAssetMetadata.getGenesisTransaction();
            if(genesisTransaction==null||genesisTransaction.equals("")){
                return false;
            }*/
            return true;
        } catch (ObjectNotSetException e) {
            return false;
        } catch (CantIssueDigitalAssetException exception) {
            throw new CantCheckAssetIssuingProgressException(exception,"Checking if digital asset is complete", "Unexpected results in database");
        }
    }

    /**
     * This is a test method, this process is changing.
     * @param transactionId
     */
    private void issueUnfinishedDigitalAsset(String transactionId){
        /***
         *Este método debe verificar el estatus de cada Asset y proceder de acuerdo a cada uno de ellos.
         * El objetivo es finalizar los digital assets, ya persistidos en base de datos, pero sin emitir.
         */
        //TODO: to improve
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
                String digitalAssetHash=getDigitalAssetHash(digitalAssetMetadata, transactionId);
                sendBitcoins(cryptoAssetGenesisAddress, digitalAssetHash, transactionId);
                issueUnfinishedDigitalAsset(transactionId);
            }
            if(digitalAssetTransactionStatus==TransactionStatus.SENDING_CRYPTO){
                digitalAssetPublicKey=this.assetIssuingTransactionDao.getDigitalAssetPublicKeyById(transactionId);
                setDigitalAssetLocalFilePath(digitalAssetPublicKey);
                getDigitalAssetFileFromLocalStorage();
                String digitalAssetGenesisAddress=this.assetIssuingTransactionDao.getDigitalAssetGenesisAddressById(transactionId);
                CryptoAddress cryptoAssetGenesisAddress=new CryptoAddress();
                cryptoAssetGenesisAddress.setAddress(digitalAssetGenesisAddress);
                setDigitalAssetGenesisAddress(transactionId, cryptoAssetGenesisAddress);
                DigitalAssetMetadata digitalAssetMetadata=new DigitalAssetMetadata(this.digitalAsset);
                String digitalAssetHashFromDatabase=this.assetIssuingTransactionDao.getDigitalAssetHashById(transactionId);
                String digitalAssetHash=digitalAssetMetadata.getDigitalAssetHash();
                if(!digitalAssetHash.equals(digitalAssetHashFromDatabase)){
                    this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionId, TransactionStatus.ISSUED_FAILED);
                    throw new CantIssueDigitalAssetException("The hash recorded in database for this DigitalAsset "+transactionId+" is not equal to the generated:\n" +
                            "Hash from database: "+digitalAssetHashFromDatabase+"\n" +
                            "hash from DigitalAssetMetadada: "+digitalAssetHash);
                }
                String genesisTransaction=this.assetIssuingTransactionDao.getDigitalAssetGenesisTransactionById(transactionId);
                digitalAssetMetadata=setDigitalAssetGenesisTransaction(transactionId, genesisTransaction, digitalAssetMetadata);
                //We kept the DigitalAssetMetadata in DAMVault
                saveDigitalAssetMetadataInVault(digitalAssetMetadata, transactionId);
            }
        } catch (CantIssueDigitalAssetException | CantSendGenesisAmountException | CantGetGenesisAddressException | CantPersistsGenesisTransactionException | CantExecuteQueryException | CantPersistsGenesisAddressException | CantCheckAssetIssuingProgressException | CantGetDigitalAssetFromLocalStorageException | UnexpectedResultReturnedFromDatabaseException exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        } catch (CantDeliverDigitalAssetToAssetWalletException e) {
            e.printStackTrace();
        } catch (CantCreateDigitalAssetFileException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method persists the genesis address in database
     * @param transactionId
     * @param genesisAddress
     * @throws CantPersistsGenesisAddressException
     */
    private void persistsGenesisAddress(String transactionId, String genesisAddress) throws CantPersistsGenesisAddressException {
        this.assetIssuingTransactionDao.persistGenesisAddress(transactionId, genesisAddress);
    }

    /**
     * This method sets the genesis address in database
     * @param transactionID
     * @param genesisAddress
     * @throws CantExecuteQueryException
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private void setDigitalAssetGenesisAddress(String transactionID, CryptoAddress genesisAddress) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        this.digitalAsset.setGenesisAddress(genesisAddress);
        this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionID, TransactionStatus.GENESIS_SETTLED);
    }

    /*private CryptoTransaction requestGenesisTransaction(CryptoAddress genesisAddress) throws CoultNotCreateCryptoTransaction {
        CryptoTransaction genesisTransaction = this.cryptoVaultManager.generateDraftCryptoTransaction(genesisAddress, this.digitalAsset.getGenesisAmount());
        return genesisTransaction;
    }*/

    /**
     * This method sets the genesis transaction in a digital asset metadata
     * @param transactionID
     * @param genesisTransaction
     * @param digitalAssetMetadata
     * @return
     * @throws CantPersistsGenesisTransactionException
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private DigitalAssetMetadata setDigitalAssetGenesisTransaction(String transactionID, String genesisTransaction, DigitalAssetMetadata digitalAssetMetadata) throws CantPersistsGenesisTransactionException, UnexpectedResultReturnedFromDatabaseException {
        digitalAssetMetadata.setGenesisTransaction(genesisTransaction);
        this.assetIssuingTransactionDao.persistGenesisTransaction(transactionID, genesisTransaction);
        return digitalAssetMetadata;
    }

    private void getDigitalAssetGenesisAddressByUUID(String transactionId) throws CantPersistsGenesisAddressException, CantGetGenesisAddressException {
        CryptoAddress genesisAddress= requestGenesisAddress();
        persistsGenesisAddress(transactionId, genesisAddress.getAddress());
    }

    /**
     * This method persists the digital asset metadata in plugin vault
     * @param digitalAssetMetadata
     * @param transactionId
     * @throws CantCheckAssetIssuingProgressException
     * @throws CantDeliverDigitalAssetToAssetWalletException
     * @throws CantExecuteQueryException
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantCreateDigitalAssetFileException
     */
    private void saveDigitalAssetMetadataInVault(DigitalAssetMetadata digitalAssetMetadata, String transactionId) throws CantCheckAssetIssuingProgressException, CantDeliverDigitalAssetToAssetWalletException, CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException, CantCreateDigitalAssetFileException {
        if(!isDigitalAssetComplete(digitalAsset, digitalAssetMetadata)){
            throw new CantDeliverDigitalAssetToAssetWalletException("Cannot deliver the digital asset - is not complete:"+digitalAssetMetadata);
        }
        if(SEND_BTC_FROM_CRYPTO_VAULT){
            this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionId, TransactionStatus.ISSUING);
        }else{
            this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionId, TransactionStatus.ISSUING);
        }
        this.digitalAssetIssuingVault.persistDigitalAssetMetadataInLocalStorage(digitalAssetMetadata, transactionId);
    }

    /**
     * This method register the genesis address in crypto address book.
     * @param genesisAddress
     * @throws CantRegisterCryptoAddressBookRecordException
     */
    private void registerGenesisAddressInCryptoAddressBook(CryptoAddress genesisAddress) throws CantRegisterCryptoAddressBookRecordException {
        //TODO: solicitar los publickeys de los actors, la publicKey de la wallet
        //I'm gonna harcode the actors publicKey
        this.cryptoAddressBookManager.registerCryptoAddress(genesisAddress,
                "testDeliveredByActorPublicKey",
                Actors.INTRA_USER,
                this.actorToPublicKey,
                Actors.DAP_ASSET_ISSUER,
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

    /**
     * This method is a short way to send bitcoins, this is ONLY for testing, please, don't use it in production
     * @param genesisAddress
     * @param digitalAssetHash
     * @param transactionId
     */
    private void sendBitcoinsFromCryptoVault(CryptoAddress genesisAddress, String digitalAssetHash, String transactionId, DigitalAssetMetadata digitalAssetMetadata) throws CantSendGenesisAmountException, CantPersistsGenesisTransactionException {
        //String genesisTransaction="d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43";
        String genesisTransaction="empty-genesis-transaction";
        try {
            //this.assetVaultManager.sendBitcoinAssetToUser(genesisTransaction, genesisAddress);
            this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionId, TransactionStatus.SENDING_CRYPTO);
            this.assetIssuingTransactionDao.updateDigitalAssetCryptoStatusByTransactionHash(digitalAssetHash, CryptoStatus.PENDING_SUBMIT);
            UUID internalUUID=UUID.randomUUID();
            genesisTransaction=this.cryptoVaultManager.sendBitcoins(this.walletPublicKey, internalUUID , genesisAddress, this.genesisAmount, digitalAssetHash);
            System.out.println("ASSET ISSUING genesis transaction from Crypto Vault "+genesisTransaction);
            this.assetIssuingTransactionDao.updateTransactionProtocolStatusByTransactionId(transactionId, ProtocolStatus.TO_BE_NOTIFIED);
            digitalAssetMetadata.setGenesisTransaction(genesisTransaction);
            UUID outgoingId=UUID.randomUUID();
            System.out.println("ASSET ISSUING mock outgoing UUID "+outgoingId);
            this.assetIssuingTransactionDao.persistOutgoingIntraActorUUID(transactionId, outgoingId);
            assetIssuingTransactionDao.persistGenesisTransaction(outgoingId.toString(), genesisTransaction);
            saveDigitalAssetMetadataInVault(digitalAssetMetadata, transactionId);
            //digitalAssetIssuingVault.setGenesisTransaction(transactionId, genesisTransaction);
        }  catch (InsufficientCryptoFundsException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount from Asset vault", "Insufficient funds");
        } catch (InvalidSendToAddressException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount from Asset vault", "Genesis address invalid: "+genesisAddress.getAddress());
        } catch (CouldNotSendMoneyException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount from Asset vault", "Cannot send money");
        } catch (CryptoTransactionAlreadySentException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount from Asset vault", "The genesis transaction "+genesisTransaction+" is used");
        } catch (CantExecuteQueryException exception) {
            throw new CantPersistsGenesisTransactionException(exception, "Sending the genesis amount from Asset vault", "Cannot execute a query");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantPersistsGenesisTransactionException(exception, "Sending the genesis amount from Asset vault", "Unexpected result from database");
        } catch (CantCheckAssetIssuingProgressException exception) {
            throw new CantPersistsGenesisTransactionException(exception, "Sending the genesis amount from Asset vault", "Cannot check the asset issuing progress");
        }  catch (CantDeliverDigitalAssetToAssetWalletException exception) {
            throw new CantPersistsGenesisTransactionException(exception, "Sending the genesis amount from Asset vault", "Cannot deliver Digital Asset to issuer wallet");
        } catch (CantCreateDigitalAssetFileException exception) {
            throw new CantPersistsGenesisTransactionException(exception, "Sending the genesis amount from Asset vault", "Cannot persists Genesis Transaction");
        } catch (CantPersistsTransactionUUIDException exception) {
            throw new CantPersistsGenesisTransactionException(exception, "Sending the genesis amount from Asset vault", "Cannot persists UUID from 'OIA'");
        }
    }

    /**
     * This method send the genesis amount from the vault to the asset vault. This process is done by Outgoing Intra actor plugin
     * @param genesisAddress
     * @param digitalAssetHash
     * @param transactionId
     * @return
     * @throws CantSendGenesisAmountException
     * @throws CantPersistsGenesisTransactionException
     */
    private void sendBitcoins(CryptoAddress genesisAddress, String digitalAssetHash, String transactionId) throws CantSendGenesisAmountException, CantPersistsGenesisTransactionException {
        try {
            this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionId, TransactionStatus.SENDING_CRYPTO);
            this.assetIssuingTransactionDao.updateDigitalAssetCryptoStatusByTransactionHash(digitalAssetHash, CryptoStatus.PENDING_SUBMIT);
            //this.cryptoVaultManager.sendBitcoins(this.digitalAsset.getPublicKey(), genesisTransaction, genesisAddress, genesisAmount);
            //TODO: Send btc through outgoing intra user
            //genesisTransaction.setOp_Return(digitalAssetHash);
            //this.cryptoVaultManager.sendBitcoins(genesisTransaction);
            //TODO: this genesisTransaction is hardcoded for now. Waiting for a better way to get the genesisTransaction
            //I will remove the genesisTransaction from this method, now I cannot get this parameter in a synchrony way
            //String genesisTransaction="d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43";
            //this.assetIssuingTransactionDao.persistGenesisTransaction(transactionId, genesisTransaction);
            UUID outgoingId=this.outgoingIntraActorManager.getTransactionManager().sendCrypto(this.walletPublicKey,
                    genesisAddress,
                    this.digitalAsset.getGenesisAmount(),
                    digitalAssetHash,
                    this.digitalAsset.getDescription(),
                    "senderPublicKey",
                    this.actorToPublicKey,
                    Actors.INTRA_USER,
                    Actors.DAP_ASSET_ISSUER,
                    ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);
            this.assetIssuingTransactionDao.persistOutgoingIntraActorUUID(transactionId, outgoingId);
            this.assetIssuingTransactionDao.updateTransactionProtocolStatusByTransactionId(transactionId, ProtocolStatus.TO_BE_NOTIFIED);
            //return genesisTransaction;
        }  catch (CantExecuteQueryException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "Cannot update the database: "+transactionId);
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "Unexpected result reading database: "+transactionId);
        } catch (OutgoingIntraActorInsufficientFundsException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "The balance is insufficient to deliver the genesis amount: "+this.digitalAsset.getGenesisAmount());
        } catch (CantGetOutgoingIntraActorTransactionManagerException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "Asset Issuing plugin cannot get the OutgoingIntraUserTransactionManager");
        } catch (OutgoingIntraActorCantSendFundsExceptions exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "The sendToAddress is invalid: "+genesisAddress);
        } catch (CantCheckAssetIssuingProgressException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "Cannot update the transaction CryptoStatus");
        } catch (CantPersistsTransactionUUIDException exception) {
            throw new CantSendGenesisAmountException(exception, "Sending the genesis amount to Asset Wallet", "Cannot persists Transaction UUID");
        }
    }

    /**
     * This method create a Digital asset transaction. This process includes the Digital asset metadata creation to the crypto currency sending process.
     * @throws CantCreateDigitalAssetTransactionException
     * @throws CantIssueDigitalAssetException
     * @throws CantDeliverDigitalAssetToAssetWalletException
     */
    private void createDigitalAssetCryptoTransaction() throws CantCreateDigitalAssetTransactionException, CantIssueDigitalAssetException, CantDeliverDigitalAssetToAssetWalletException {

        DigitalAssetMetadata digitalAssetMetadata=null;
        try{
            //Asign internal UUID
            UUID transactionUUID=generateTransactionUUID();
            String transactionId=transactionUUID.toString();
            //Check the available balance
            if(!SEND_BTC_FROM_CRYPTO_VAULT){
                checkCryptoWalletBalance();
            }
            //Request the genesisAddress
            CryptoAddress genesisAddress= requestGenesisAddress();
            //persist the genesisAddress in database
            persistsGenesisAddress(transactionId,genesisAddress.getAddress());
            //Register genesisAddress in AddressBook
            registerGenesisAddressInCryptoAddressBook(genesisAddress);
            //Set the genesisAddres genesisAddress
            setDigitalAssetGenesisAddress(transactionId, genesisAddress);
            //create the digitalAssetMetadata
            digitalAssetMetadata=new DigitalAssetMetadata(this.digitalAsset);
            //Get the digital asset metadata hash
            String digitalAssetHash=getDigitalAssetHash(digitalAssetMetadata, transactionId);
            //LOG.info("MAP_DIGITAL ASSET FULL: "+this.digitalAsset);
            LOG.info("ASSET ISSUING Digital Asset Metadata Hash: " + digitalAssetHash);
            //BTC Sending
            /*String genesisTransaction=*/
            if(SEND_BTC_FROM_CRYPTO_VAULT){
                sendBitcoinsFromCryptoVault(genesisAddress, digitalAssetHash, transactionId, digitalAssetMetadata);
            }else{
                sendBitcoins(genesisAddress, digitalAssetHash, transactionId);
                //digitalAssetMetadata=setDigitalAssetGenesisTransaction(transactionId, genesisTransaction, digitalAssetMetadata);
                //We kept the DigitalAssetMetadata in DAMVault
                saveDigitalAssetMetadataInVault(digitalAssetMetadata, transactionId);
            }



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
        } catch (CantCreateDigitalAssetFileException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Issuing a new Digital Asset", "Cannot kept the DigitalAssetMetadata in DigitalAssetIssuingVault");
        } catch (CantCheckAssetIssuingProgressException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception,"Cannot deliver the digital asset:" + digitalAssetMetadata,"Unexpected result in database");
        } catch (CantCalculateBalanceException | CantLoadWalletException exception) {
            throw new CantIssueDigitalAssetException(exception, "Issuing a new Digital Asset", "Cannot get the wallet available balance");
        } catch (CryptoWalletBalanceInsufficientException exception) {
            throw new CantIssueDigitalAssetException(exception, "Issuing a new Digital Asset", "The crypto balance is insufficient");
        }

    }

}
