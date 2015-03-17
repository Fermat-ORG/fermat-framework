package com.bitdubai.fermat_core.layer._7_world.blockchain_info.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer._2_os.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginDataFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._7_world.CantCreateCryptoWalletException;
import com.bitdubai.fermat_api.layer._7_world.CryptoWallet;
import com.bitdubai.fermat_api.layer._7_world.World;
import com.bitdubai.fermat_core.layer._7_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.APIException;
import com.bitdubai.fermat_core.layer._7_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.createwallet.CreateWallet;
import com.bitdubai.fermat_core.layer._7_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.createwallet.CreateWalletResponse;
import com.bitdubai.fermat_api.layer._7_world.CryptoWalletManager;
import com.bitdubai.fermat_core.layer._7_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Address;
import com.bitdubai.fermat_core.layer._7_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.PaymentResponse;
import com.bitdubai.fermat_core.layer._7_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Wallet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 12/03/15.
 */

/**
 * Blockchain Plugin. 
 */

public class BlockchainInfoWorldPluginRoot implements CryptoWallet, CryptoWalletManager,Service, World,DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin{

    private String password = "bitdubait456";
    private String apiCode = "91c646ef-c3fd-4dd0-9dc9-eba5c5600549";
    private String walletAddress ="1MZi8XDCwkLhrRrbSfz3RbzToZuTHZmWAu";
    private String walletGuid ="cbdd0995-3770-42c6-9c59-a5e20d2c9273";
    /**
     * Service Interface member variables. 
     */
    
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();


    /**
     * UsesFileSystem Interface member variable
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithEvents Interface member variables. 
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables. 
     */
    UUID pluginId;
    
    @Override
    public void start() {
        /**
         * I will initialize the handling od platform events. 
         */
        
        EventListener eventListener;
        EventHandler eventHandler;
        
        this.serviceStatus = ServiceStatus.STARTED;
        
    }
    
    @Override
    public void pause() {
        
        this.serviceStatus = ServiceStatus.PAUSED;
        
    }
    
    @Override
    public void resume() {
        
        this.serviceStatus = ServiceStatus.STARTED;
        
    }
    
    @Override
    public void stop(){

        /**
         * I will remove all the event listeners registered with the event manager.
         */
        
        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }
        
        listenersAdded.clear();
        
        this.serviceStatus = ServiceStatus.STOPPED;
    }
    
    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;        
    }

    /**
     * UsesFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;        
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     *DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    //TODO : NATALIA: Este metodo no va aca, y no es esta la clase que implementa esta interface, sino el plugin Root. sino, como desde el Fragmento accederias a una referencia a esta clase?
    // TODO: NATALIA: Lo que actaulmente tenes implementado aca deberia ir en el constructor de esta clase.
    public void createWallet (CryptoCurrency cryptoCurrency, UUID walletId ) throws CantCreateCryptoWalletException {
        //save wallet guid, address and link in a binary file on disk
        try{
            CreateWalletResponse response =  CreateWallet.create(password,apiCode);
            String  walletAddress = response.getAddress();
            String  walletGuid = response.getIdentifier();
            String walletLink = response.getLink();

            PluginDataFile layoutFile;
            layoutFile = pluginFileSystem.createDataFile(pluginId, walletId.toString(), "wallet_address.txt", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            layoutFile.setContent(walletAddress + ";" + walletGuid + ";" + walletLink);
            layoutFile.persistToMedia();

        } catch (APIException e) {
            e.printStackTrace();}
        catch (IOException e) {
            e.printStackTrace();}
        catch (CantPersistFileException e) {
            e.printStackTrace();}

    }

    public long getWalletBalance(CryptoCurrency cryptoCurrency){
        long balance = 0;
        try{
            Wallet wallet = new Wallet(walletGuid, password);
            wallet.setApiCode(apiCode);
            balance = wallet.getBalance();


        } catch (APIException e) {
            e.printStackTrace();}
        catch (IOException e) {
            e.printStackTrace();}
        return balance;
    }

    public long getAddressBalance(CryptoAddress cryptoAddress){
        long addressBalance = 0;
        try{
            Wallet wallet = new Wallet(walletGuid, password);
            wallet.setApiCode(apiCode);
            // get an address from your wallet and include only transactions with up to 3
            // confirmations in the balance
            Address addr = wallet.getAddress(walletAddress, 3);
            addressBalance =  addr.getBalance();

        } catch (APIException e) {
            e.printStackTrace();}
        catch (IOException e) {
            e.printStackTrace();}
        return addressBalance;
    }

    public void sendCrypto (CryptoCurrency cryptoCurrency, long amount, CryptoAddress cryptoAddress){
        // send 0.2 bitcoins with a custom fee of 0.01 BTC and a note
        // public notes require a minimum transaction size of 0.005 BTC
        try{
            Wallet wallet = new Wallet(walletGuid, password);
            PaymentResponse payment = wallet.send("1dice6YgEVBf88erBFra9BHf6ZMoyvG88", amount, null,null, "");

        } catch (APIException e) {
            e.printStackTrace();}
        catch (IOException e) {
            e.printStackTrace();}

    }
}
