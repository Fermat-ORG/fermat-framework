package com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._11_world.crypto_index.CryptoIndexManager;
import com.bitdubai.fermat_api.layer._11_world.crypto_index.DealsWithCryptoIndex;
import com.bitdubai.fermat_api.layer._12_middleware.Middleware;
import com.bitdubai.fermat_api.layer._12_middleware.WalletManager;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.Wallet;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CantInitializeWalletException;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.*;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.WalletCreatedEvent;
import com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1.event_handlers.WalletCreatedEventHandler;
import com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1.exceptions.CantStartWalletException;
import com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1.interfaces.WalletService;
import com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1.structure.MiddlewareWallet;

import java.util.*;

/**
 * Created by ciencias on 20.01.15.
 */

/**
 * This plug-in handles the relationship between fiat currency an crypto currency. In this way the en user can be using 
 * fiat over crypto.
 */

public class WalletMiddlewarePluginRoot implements Service, WalletManager , Middleware, DealsWithCryptoIndex, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, DealsWithPluginDatabaseSystem, Plugin {

    
    /**
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;
    private List<EventListener> listenersAdded = new ArrayList<>();
    
    /**
     * WalletManager Interface member variables.
     */
    private final String WALLET_IDS_FILE_NAME = "walletsIds";

    private Wallet currentWallet;
    private Map<UUID, UUID> walletIds =  new HashMap();

    /**
     * DealsWithCryptoIndex Interface member variables.
     */
    private CryptoIndexManager cryptoIndexManager;
    
    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    private PluginFileSystem pluginFileSystem;
    
    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;
    
    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    private UUID pluginId;

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
            walletIdsFile = pluginFileSystem.getTextFile(pluginId, "", WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            
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
            catch (CantLoadFileException CantLoadFileException) {
                
                /**
                 * In this situation we might have a corrupted file we can not read. For now the only thing I can do is
                 * to prevent the plug-in from running.
                 * 
                 * In the future there should be implemented a method to deal with this situation.
                 * * * * 
                 */
                System.err.println("CantLoadFileException: " + CantLoadFileException.getMessage());
                CantLoadFileException.printStackTrace();
                throw new CantStartPluginException(Plugins.WALLET_MIDDLEWARE);
            }
        }
        catch (FileNotFoundException e) {
            /**
             * If the file did not exist it is not a problem. It only means this is the first time this plugin is running.
             * 
             * I will create the file now, with an empty content so that when a new wallet is added we wont have to deal
             * with this file not existing again.
             * * * * * 
             */
            walletIdsFile = pluginFileSystem.createTextFile(pluginId, "", WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            
            try {
                walletIdsFile.persistToMedia();
            }
            catch (CantPersistFileException cantPersistFileException ) {
                
                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                System.err.println("CantPersistFileException: " + cantPersistFileException.getMessage());
                cantPersistFileException.printStackTrace();
                throw new CantStartPluginException(Plugins.WALLET_MIDDLEWARE);
            }
        }
        
        /**
         * I will initialize the handling of the platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

        eventListener = eventManager.getNewListener(EventType.WALLET_CREATED);
        eventHandler = new WalletCreatedEventHandler();
        ((WalletCreatedEventHandler) eventHandler).setWalletmanager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

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
     * WalletManager methods implementation.
     */

    @Override
    public void createWallet(UUID walletId) throws CantCreateWalletException {

        /**
         * The first step is to create a new wallet within the plugin.
         */
        MiddlewareWallet newWallet = new MiddlewareWallet(this.pluginId);
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

        PluginTextFile walletIdsFile;
        walletIdsFile = pluginFileSystem.createTextFile(pluginId, "", WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        
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
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_CREATED);
        ((WalletCreatedEvent) platformEvent).setWalletId(walletId);
        platformEvent.setSource(EventSource.MIDDLEWARE_WALLET_PLUGIN);
        eventManager.raiseEvent(platformEvent);
    }

    @Override
    public void loadWallet(UUID walletId) throws CantLoadWalletException{

        /**
         * Finally I fire the event announcing the new wallet was created.
         */
        this.currentWallet = new MiddlewareWallet(walletId);
        ((DealsWithPluginDatabaseSystem) this.currentWallet).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithEvents) this.currentWallet).setEventManager(this.eventManager);
        ((DealsWithCryptoIndex) this.currentWallet).setCryptoIndexManager(this.cryptoIndexManager);
     
        try {
            ((WalletService) this.currentWallet).start();
        }
        catch (CantStartWalletException cantStartWalletException){
            /**
             * If I can not start the wallet, then this method fails.
             */
            System.err.println("CantStartWalletException: " + cantStartWalletException.getMessage());
            cantStartWalletException.printStackTrace();
            throw new CantLoadWalletException();
        }
    }

    @Override
    public Wallet getCurrentWallet() {
        return this.currentWallet;
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

    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


}
