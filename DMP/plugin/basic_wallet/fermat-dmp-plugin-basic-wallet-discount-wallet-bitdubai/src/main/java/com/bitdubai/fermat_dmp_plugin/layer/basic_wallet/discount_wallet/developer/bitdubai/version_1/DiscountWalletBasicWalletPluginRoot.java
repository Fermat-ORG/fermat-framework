package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.CryptoIndexManager;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.DealsWithCryptoIndex;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.discount_wallet.interfaces.DiscountWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.discount_wallet.interfaces.DiscountWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.discount_wallet.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.osa_android.file_system.*;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.event_handlers.WalletCreatedEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.exceptions.CantInitializeWalletException;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.exceptions.CantStartWalletServiceException;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.interfaces.WalletService;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.structure.BasicWalletDiscountWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.discount_wallet.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.WalletCreatedEvent;

import java.util.*;

/**
 * Created by ciencias on 20.01.15.
 */

/**
 * This plug-in handles the relationship between fiat currency an crypto currency. In this way the en user can be using 
 * fiat over crypto.
 */

public class DiscountWalletBasicWalletPluginRoot implements DealsWithCryptoIndex, DealsWithEvents, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, DiscountWalletManager, Plugin, Service {

    

    /**
     * WalletManager Interface member variables.
     */
    private final String WALLET_IDS_FILE_NAME = "walletsIds";

    private DiscountWallet currentDiscountWallet;
    private Map<UUID, UUID> walletIds =  new HashMap();

    /**
     * DealsWithCryptoIndex Interface member variables.
     */
    private CryptoIndexManager cryptoIndexManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * DealWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    private PluginFileSystem pluginFileSystem;


    /**
     * Plugin Interface member variables.
     */
    private UUID pluginId;

    /**
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {

        /**
         * Check if this is the first time this plugin starts. To do so I check if the file containing all the wallets
         * ids managed by this plug-in already exists or not.
         * * * 
         */
        PluginTextFile walletIdsFile;
        
        try {

            try{
                walletIdsFile = pluginFileSystem.getTextFile(pluginId, "", WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            }
            catch (CantCreateFileException cantCreateFileException ) {

                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                System.err.println("cantCreateFileException: " + cantCreateFileException.getMessage());
                cantCreateFileException.printStackTrace();
                throw new CantStartPluginException(cantCreateFileException, Plugins.BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET);
            }
            try {
                walletIdsFile.loadFromMedia();
                
                /**
                 * Now I read the content of the file and place it in memory.
                 */
                String[] stringWalletIds = walletIdsFile.getContent().split(";" , -1);
                
                for (String stringWalletId : stringWalletIds ) {

                    if(stringWalletId != "")
                    {
                        /**
                         * Each record in the file has to values: the first is the external id of the wallet, and the
                         * second is the internal id of the wallet.
                         * * *
                         */
                        String[] idPair = stringWalletId.split(",", -1);

                        walletIds.put( UUID.fromString(idPair[0]),  UUID.fromString(idPair[1]));
                        
                        /**
                         * Great, now the wallet list is in memory.
                         */
                    }
                }
            }
            catch (CantLoadFileException cantLoadFileException) {
                
                /**
                 * In this situation we might have a corrupted file we can not read. For now the only thing I can do is
                 * to prevent the plug-in from running.
                 * 
                 * In the future there should be implemented a method to deal with this situation.
                 * * * * 
                 */
                System.err.println("CantLoadFileException: " + cantLoadFileException.getMessage());
                cantLoadFileException.printStackTrace();
                throw new CantStartPluginException(cantLoadFileException, Plugins.BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET);
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            /**
             * If the file did not exist it is not a problem. It only means this is the first time this plugin is running.
             * 
             * I will create the file now, with an empty content so that when a new wallet is added we wont have to deal
             * with this file not existing again.
             * * * * * 
             */

            try{

                walletIdsFile = pluginFileSystem.createTextFile(pluginId, "", WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            }
            catch (CantCreateFileException cantCreateFileException ) {

                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                System.err.println("cantCreateFileException: " + cantCreateFileException.getMessage());
                cantCreateFileException.printStackTrace();
                throw new CantStartPluginException(cantCreateFileException, Plugins.BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET);
            }
            try {
                walletIdsFile.persistToMedia();
            }
            catch (CantPersistFileException cantPersistFileException ) {
                
                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                System.err.println("CantPersistFileException: " + cantPersistFileException.getMessage());
                cantPersistFileException.printStackTrace();
                throw new CantStartPluginException(cantPersistFileException, Plugins.BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET);
            }
        }
        
        /**
         * I will initialize the handling of the platform events.
         */

        FermatEventListener fermatEventListener;
        FermatEventHandler fermatEventHandler;

        fermatEventListener = eventManager.getNewListener(EventType.WALLET_CREATED);
        fermatEventHandler = new WalletCreatedEventHandler();
        ((WalletCreatedEventHandler) fermatEventHandler).setWalletmanager(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

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
    public void stop() {

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }

        listenersAdded.clear();

        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    /**
     * WalletManager methods implementation.
     */

    @Override
    public void createWallet(UUID walletId, FiatCurrency fiatCurrency, CryptoCurrency cryptoCurrency) throws CantCreateWalletException {

        /**
         * The first step is to create a new wallet within the plugin.
         */
        //BasicWalletDiscountWallet newWallet = new BasicWalletDiscountWallet(this.pluginId, fiatCurrency, cryptoCurrency);
        BasicWalletDiscountWallet newWallet = new BasicWalletDiscountWallet(this.pluginId);
        newWallet.setPluginDatabaseSystem(this.pluginDatabaseSystem);
        newWallet.setEventManager(this.eventManager);
        
        try {
            newWallet.initialize();
        }
        catch (CantInitializeWalletException cantInitializeWalletException) {
            /**
             * If I can not initialize the new wallet, that means I can not create it at all.
             */
            System.err.println("CantInitializeWalletException: " + cantInitializeWalletException.getMessage());
            cantInitializeWalletException.printStackTrace();
            throw new CantCreateWalletException();
        }
        
        /**
         * Now I will add this wallet to the list of wallets managed by the plugin.
         */
        walletIds.put(walletId,newWallet.getWalletId());

        PluginTextFile walletIdsFile = null;

        try{
            walletIdsFile = pluginFileSystem.createTextFile(pluginId, "", WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        }
        catch (CantCreateFileException cantCreateFileException ) {

            /**
             * If I can not save this file, then this plugin shouldn't be running at all.
             */
            System.err.println("cantCreateFileException: " + cantCreateFileException.getMessage());
            cantCreateFileException.printStackTrace();
            throw new CantCreateWalletException();
        }

        /**
         * I will generate the file content.
         */
        StringBuilder stringBuilder = new StringBuilder(walletIds.size() * 72);

        Iterator iterator = walletIds.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            stringBuilder.append(pair.getKey().toString() + "," + pair.getValue().toString() + ";");
            iterator.remove();
        }


        /**
         * Now I set the content.
         */
        walletIdsFile.setContent(stringBuilder.toString());

        try{
            walletIdsFile.persistToMedia();
        }
        catch (CantPersistFileException cantPersistFileException) {
            /**
             * If I can not save the id of the new wallet created, then this method fails.
             */
            System.err.println("CantPersistFileException: " + cantPersistFileException.getMessage());
            cantPersistFileException.printStackTrace();
            throw new CantCreateWalletException();
        }

        /**
         * Finally I fire the event announcing the new wallet was created.
         */
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.WALLET_CREATED);
        ((WalletCreatedEvent) fermatEvent).setWalletId(walletId);
        fermatEvent.setSource(EventSource.DISCOUNT_WALLET_BASIC_WALLET_PLUGIN);
        eventManager.raiseEvent(fermatEvent);
    }

    @Override
    public void loadWallet(UUID walletId) throws CantLoadWalletException{

        /**
         * Finally I fire the event announcing the new wallet was created.
         */
        this.currentDiscountWallet = new BasicWalletDiscountWallet(walletId);
        ((DealsWithPluginDatabaseSystem) this.currentDiscountWallet).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithEvents) this.currentDiscountWallet).setEventManager(this.eventManager);
        ((DealsWithCryptoIndex) this.currentDiscountWallet).setCryptoIndexManager(this.cryptoIndexManager);
     
        try {
            ((WalletService) this.currentDiscountWallet).start();
        }
        catch (CantStartWalletServiceException cantStartWalletServiceException){
            /**
             * If I can not start the wallet, then this method fails.
             */
            System.err.println("CantStartWalletException: " + cantStartWalletServiceException.getMessage());
            cantStartWalletServiceException.printStackTrace();
            throw new CantLoadWalletException();
        }
    }

    @Override
    public DiscountWallet getCurrentDiscountWallet() {
        return this.currentDiscountWallet;
    }

    /**
     * DealsWithCryptoIndex Interface member variables.
     */
    @Override
    public void setCryptoIndexManager(CryptoIndexManager cryptoIndexManager) {
        this.cryptoIndexManager = cryptoIndexManager;
    }
    
    /**
     * DealsWithPluginFileSystem Interface implementation.
     */
    
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
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
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


}
