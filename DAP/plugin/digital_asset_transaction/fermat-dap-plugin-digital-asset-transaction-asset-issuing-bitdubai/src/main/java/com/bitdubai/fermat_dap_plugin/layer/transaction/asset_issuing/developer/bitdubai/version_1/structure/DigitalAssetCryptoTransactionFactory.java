package com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCreateDigitalAssetTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CryptoWalletBalanceInsufficientException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    //PluginDatabaseSystem pluginDatabaseSystem;
    PluginFileSystem pluginFileSystem;
    UUID pluginId;
    TransactionStatus transactionStatus;
    final String LOCAL_STORAGE_PATH="digital-asset/";
    String digitalAssetLocalFilePath;
    int assetsAmount;

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

        if(this.digitalAsset.getContract()==null){
            throw new ObjectNotSetException("Digital Asset Contract is not set");
        }
        if(this.digitalAsset.getResources()==null){
            throw new ObjectNotSetException("Digital Asset Resources is not set");
        }
        if(this.digitalAsset.getDescription()==null){
            throw new ObjectNotSetException("Digital Asset Description is not set");
        }
        /*long digitalAssetTransactionFee=this.digitalAsset.getTransactionFee();
        if(digitalAssetTransactionFee<MINIMAL_TRANSACTION_FEE){
            throw new ObjectNotSetException("Digital Asset Genesis Transaction Fee is insufficient: "+digitalAssetTransactionFee);
        }
        /*int digitalAssetQuantity=this.digitalAsset.getQuantity();
        if(digitalAssetQuantity<MINIMAL_QUANTITY){
            throw new ObjectNotSetException("Digital Asset quantity is insufficient: "+digitalAssetQuantity);
        }*/
        if(this.digitalAsset.getName()==null){
            throw new ObjectNotSetException("Digital Asset Name is not set");
        }
        if(this.digitalAsset.getPublicKey()==null){
            throw new ObjectNotSetException("Digital Asset PublicKey is not set");
        }
        if(this.digitalAsset.getState()==null){
            //throw new ObjectNotSetException("Digital Asset State is not set");
            digitalAsset.setState(State.DRAFT);
        }
        if(this.digitalAsset.getIdentityAssetIssuer()==null){
            throw new ObjectNotSetException("Digital Asset Identity is not set");
        }
        //checkGenesisAmount();

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

        this.assetIssuingTransactionDao.persistDigitalAsset(digitalAsset.getPublicKey(), this.digitalAssetFileStoragePath, this.assetsAmount);

    }

    private void requestHashGenesisTransaction(){
        //TODO: to implement
        //this.cryptoVaultManager.getTransactionManager().confirmReception();
    }

    private void checkCryptoWalletBalance() throws CryptoWalletBalanceInsufficientException, CantGetBalanceException {

        String digitalAssetPublicKey=this.digitalAsset.getPublicKey();
        long digitalAssetGenesisAmount=this.digitalAsset.getGenesisAmount();
        long cryptoWalletBalance=this.cryptoWallet.getAvailableBalance(digitalAssetPublicKey);

        if(digitalAssetGenesisAmount>cryptoWalletBalance){

            throw new CryptoWalletBalanceInsufficientException("The current balance in Wallet "+digitalAssetPublicKey+" is "+cryptoWalletBalance+" the amount needed is "+digitalAssetGenesisAmount);

        }

    }

    private void setDigitalAssetLocalFilePath(){

        this.digitalAssetFileStoragePath=this.LOCAL_STORAGE_PATH+this.digitalAsset.getPublicKey();
        this.digitalAssetFileName=this.digitalAsset.getName()+".xml";
        this.digitalAssetLocalFilePath=digitalAssetFileStoragePath+"/"+digitalAssetFileName;

    }

    private void persistInLocalStorage() throws CantCreateDigitalAssetFileException {
        //Path structure: digital-asset/walletPublicKey/name.xml
        try{
            setDigitalAssetLocalFilePath();
            String digitalAssetInnerXML=digitalAsset.toString();
            PluginTextFile digitalAssetFile=this.pluginFileSystem.createTextFile(this.pluginId, this.digitalAssetFileStoragePath, this.digitalAssetFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            digitalAssetFile.setContent(digitalAssetInnerXML);

        }catch(CantCreateFileException exception){
            throw new CantCreateDigitalAssetFileException(exception,"Persisting Digital Asset in local storage","Can't create '"+this+digitalAssetLocalFilePath+"' file");
        }catch(Exception exception){
            throw new CantCreateDigitalAssetFileException(exception,"Persisting Digital Asset in local storage","Unexpected Exception");
        }

    }

    private UUID generateTransactionUUID(){
        //Esto lo podría usar para identificación interna del Asset
        UUID transactionUUID=UUID.randomUUID();
        try {
            if(this.assetIssuingTransactionDao.isTransactionIdUsed(transactionUUID)){
                generateTransactionUUID();
            }
        } catch (CantCheckAssetIssuingProgressException| UnexpectedResultReturnedFromDatabaseException exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
        return transactionUUID;
    }

    private List<String> getPendingAssets(String publicKey){

        List<String> pendingAssetIssuingTransactionIdList=new ArrayList<>();

        try{
            if(this.assetIssuingTransactionDao.getNumberOfPendingAssets(publicKey)>0){
//TODO: finish this- necesito crear un método en el dao que me devuelva los assets no conluidos
            }
        } catch (CantCheckAssetIssuingProgressException| UnexpectedResultReturnedFromDatabaseException exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }

        return pendingAssetIssuingTransactionIdList;

    }

    public void issueDigitalAssets(DigitalAsset digitalAsset, int assetsAmount)throws CantIssueDigitalAssetsException{

        this.digitalAsset=digitalAsset;
        this.assetsAmount=assetsAmount;
        //Primero chequeamos si el asset está completo
        try {
            areObjectsSettled();
            //Persistimos el Asset en archivo
            persistInLocalStorage();
            //Persistimos la información del Digital Asset en base de datos
            persistFormingGenesisDigitalAsset();
            /**
             * En este punto debemos generar las Transacciones de cada uno de los digital Assets, pero
             * debo estudiar la mejor forma de hacerlo, creando un thread adicional, podría hacerlo a través de una clase interna a esta
             * o un método que se encargue de generar los Assets pendientes. Me inclino por la primera opción.
             * Sería bueno preguntarlo en una reunión con el equipo.
             * */

        } catch (ObjectNotSetException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Issuing "+assetsAmount+" Digital Assets","Digital Asset object is not complete");
        } catch (CantCreateDigitalAssetFileException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Issuing "+assetsAmount+" Digital Assets","A local storage procedure exception");
        } catch (CantPersistDigitalAssetException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Issuing "+assetsAmount+" Digital Assets","A database procedure exception");
        }

    }

    //Este método podría estar dentro de un thread, podría hacerlo estático para que pueda ser reiniciado apenas arranque el plugin.
    public static void issuePendingDigitalAssets(){

    }

    private void issuePendingDigitalAsset(String publicKey){

        try {
            int pendingDigitalAssetsAmount=this.assetIssuingTransactionDao.getNumberOfPendingAssets(publicKey);
            while(pendingDigitalAssetsAmount>0){
                //Llamamos a la factory de digital Assets
                //Disminuimos la cantidad de Digital Assets a generar
                pendingDigitalAssetsAmount--;
            }
        } catch (CantCheckAssetIssuingProgressException | UnexpectedResultReturnedFromDatabaseException exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }

    //This method can change in the future, I prefer design an monitor to create Digital Asset.
    private void createDigitalAssetCryptoTransactions(DigitalAsset digitalAsset, int assetsAmount) throws CantCreateDigitalAssetTransactionException {

        /**
         * Este método lo usaré para pedir las transacciones de cada digital asset, mucho de lo que estaba en este método ahora pertenece al
         * método issueDigitalAssets.
         */


    }

}
