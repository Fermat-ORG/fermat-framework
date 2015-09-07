package com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CantCreateDigitalAssetTransactionException;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CantIssueDigitalAsset;
import com.bitdubai.fermat_dap_api.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.structure.DigitalAssetCryptoTransactionFactory;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
public class AssetIssuingTransactionPluginRoot implements AssetIssuingManager, DatabaseManagerForDevelopers, DealsWithCryptoVault, DealsWithEvents, DealsWithErrors,  DealsWithPluginDatabaseSystem, Plugin, Service {

    //CryptoAddressBookManager cryptoAddressBookManager;
    CryptoWallet cryptoWallet;
    CryptoVaultManager cryptoVaultManager;
    DigitalAssetCryptoTransactionFactory digitalAssetCryptoTransactionFactory;
    ErrorManager errorManager;
    EventManager eventManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;

    //TODO: Delete this log object
    Logger LOG = Logger.getGlobal();

    @Override
    public void setId(UUID pluginId) {
        this.pluginId=pluginId;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return null;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem=pluginDatabaseSystem;
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager=errorManager;
    }

    @Override
    public void setEventManager(EventManager eventManager) {


        this.eventManager=eventManager;

    }

    @Override
    public void start() throws CantStartPluginException {
        //delete this
        printSomething("Starting plugin");
        //TODO: implement this method
        try{
            this. digitalAssetCryptoTransactionFactory=new DigitalAssetCryptoTransactionFactory(this.cryptoVaultManager/*, this.cryptoAddressBookManager*/);

        }catch(CantSetObjectException exception){
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception,"Starting Asset Issuing plugin", "CryptoVaultManager is null");
        }catch(Exception exception){
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE,FermatException.wrapException(exception),"Starting Asset Issuing plugin", "Unexpected exception");
        }

    }

    @Override
    public void pause() {
//TODO: implement this method
    }

    @Override
    public void resume() {
//TODO: implement this method
    }

    @Override
    public void stop() {
//TODO: implement this method
    }

    @Override
    public ServiceStatus getStatus() {
        return null;
    }

    //TODO: DELETE THIS USELESS METHOD
    private void printSomething(String information){
        LOG.info("ASSET_ISSUING: "+information);
    }

    @Override
    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager=cryptoVaultManager;
    }

    @Override
    public void issueAsset(DigitalAsset digitalAssetToIssue) throws CantIssueDigitalAsset {

        try {
            this.digitalAssetCryptoTransactionFactory.createDigitalAssetCryptoTransaction(digitalAssetToIssue);
        } catch (CantCreateDigitalAssetTransactionException exception) {
            throw new CantIssueDigitalAsset(exception, "Creating a Digital Asster Transaction", "Check the cause");
        } catch(Exception exception){
            throw new CantIssueDigitalAsset(FermatException.wrapException(exception), "Issuing a Digital Asset Transaction", "Unexpected Exception");
        }

    }
    /*@Override
    public void createDigitalAsset(String publicKey, String name, String description, List<Resource> resources, DigitalAssetContract digitalAssetContract, long genesisAmount) throws CantCreateDigitalAssetTransactionException {

        try{

            DigitalAsset newDigitalAsset=new DigitalAsset();
            newDigitalAsset.setPublicKey(publicKey);
            newDigitalAsset.setName(name);
            newDigitalAsset.setDescription(description);
            newDigitalAsset.setResources(resources);
            newDigitalAsset.setContract(digitalAssetContract);
            newDigitalAsset.setGenesisAmount(genesisAmount);

            this.digitalAssetCryptoTransactionFactory.createDigitalAssetCryptoTransaction(newDigitalAsset);

        }catch(Exception exception){

            throw new CantCreateDigitalAssetTransactionException(FermatException.wrapException(exception), "Creating a new Digital Asset Transaction", "Unexpected Exception");

        }

    }*/

    /*@Override
    public void setActors(String deliveredByActorPublicKey, Actors deliveredByType, String deliveredToActorPublicKey, Actors deliveredToType) throws CantSetObjectException {

        if(deliveredByActorPublicKey==null||deliveredToActorPublicKey==null||deliveredByType==null||deliveredToType==null){
            throw new CantSetObjectException("Actor component is null");
        }
        this.digitalAssetCryptoTransactionFactory.setActors(deliveredByActorPublicKey, deliveredByType, deliveredToActorPublicKey, deliveredToType);

    }

    @Override
    public void setWallet(String walletPublicKey, ReferenceWallet walletType) throws CantSetObjectException {
        if(walletPublicKey==null||walletType==null){
            throw new CantSetObjectException("Wallet component is null");
        }
        this.digitalAssetCryptoTransactionFactory.setWallet( walletPublicKey, walletType);
    }

    @Override
    public void setCryptoAddressBookManager(CryptoAddressBookManager cryptoAddressBookManager) {
        this.cryptoAddressBookManager=cryptoAddressBookManager;
    }*/
    @Override
    public void setCryptoWallet(CryptoWallet cryptoWallet){

        this.cryptoWallet=cryptoWallet;
    }

}
