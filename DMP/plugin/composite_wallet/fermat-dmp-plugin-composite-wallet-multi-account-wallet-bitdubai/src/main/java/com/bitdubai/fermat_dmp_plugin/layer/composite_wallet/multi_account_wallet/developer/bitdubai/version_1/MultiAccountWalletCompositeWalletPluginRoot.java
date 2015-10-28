package com.bitdubai.fermat_dmp_plugin.layer.composite_wallet.multi_account_wallet.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.CryptoIndexManager;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.DealsWithCryptoIndex;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.interfaces.DiscountWallet;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.*;

/**
 * Created by ciencias on 20.01.15.
 */

/**
 * This plug-in handles the relationship between fiat currency an crypto currency. In this way the en user can be using 
 * fiat over crypto.
 */

public class MultiAccountWalletCompositeWalletPluginRoot implements Service,  DealsWithCryptoIndex, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, DealsWithPluginDatabaseSystem, Plugin {

    
    /**
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();
    
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
