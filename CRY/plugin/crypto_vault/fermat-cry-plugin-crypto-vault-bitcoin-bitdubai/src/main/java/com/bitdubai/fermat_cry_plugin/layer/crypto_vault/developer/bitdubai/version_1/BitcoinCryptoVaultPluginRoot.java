package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer.pip_user.device_user.DealsWithDeviceUsers;
import com.bitdubai.fermat_api.layer.pip_user.device_user.DeviceUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantCreateCryptoWalletException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.event_handlers.BitcoinCoinsReceivedEventHandler;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.events.TransactionNotificationAgent;

import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.Wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 08/06/15.
 */
public class BitcoinCryptoVaultPluginRoot implements CryptoVaultManager, DealsWithEvents, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithDeviceUsers, DealsWithPluginFileSystem, Plugin, Service {

    /**
     * BitcoinCryptoVaultPluginRoot member variables
     */
    BitcoinCryptoVault vault;
    UUID userId;
    TransactionNotificationAgent transactionNotificationAgent;


    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * DealsWithErrors interface member variable
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem interface member variable
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    Database database;

    /**
     * DealsWithDeviceUsers interface member variable
     */
    DeviceUserManager deviceUserManager;

    /**
     * DealsWithPluginFileSystem interface member variable
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Service interface implementation
     * @return
     */
    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
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
     * DealsWithPlugIndatabaseSystem interace implementation
     * @param pluginDatabaseSystem
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager) {
        this.deviceUserManager = deviceUserManager;
    }

    /**
     * DealsWithPluginFileSystem interface implementation
     * @param pluginFileSystem
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public void start() throws CantStartPluginException {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */
        EventListener eventListener;
        EventHandler eventHandler;

        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_IDENTIFIED);
        eventHandler = new BitcoinCoinsReceivedEventHandler();
        ((BitcoinCoinsReceivedEventHandler) eventHandler).setCryptoVaultManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        /**
         * I get the userId from the deviceUserManager
         */
        userId = deviceUserManager.getLoggedInUser().getId();


        /**
         * I will try to open the database first, if it doesn't exists, then I create it
         */
        try {
            database = pluginDatabaseSystem.openDatabase(pluginId, userId.toString());
        }  catch (DatabaseNotFoundException e) {
            /**
             * The database doesn't exists, lets create it.
             */
            try {
                database = pluginDatabaseSystem.createDatabase(pluginId, userId.toString());
            } catch (CantCreateDatabaseException e1) {
                /**
                 * something went wrong creatig the db, I can't handle this.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantStartPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT);
            }
        } catch (CantOpenDatabaseException e) {
            /**
             * the database exists, but I cannot open it! I cannot handle this.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT);
        }
        /**
         * I will start the loading creation of the wallet from the user Id
         */
        try {
            vault = new BitcoinCryptoVault(this.userId);
            vault.setErrorManager(errorManager);
            vault.setPluginDatabaseSystem(pluginDatabaseSystem);
            vault.setDatabase(this.database);
            vault.setPluginFileSystem(pluginFileSystem);
            vault.setPluginId(pluginId);

            vault.loadOrCreateVault();
        } catch (CantCreateCryptoWalletException e) {
            /**
             * If I couldnt create the Vault, I cant go on.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT);
        }


        /**
         * now I will start the TransactionNotificationAgent to monitor
         */
        transactionNotificationAgent = new TransactionNotificationAgent(pluginDatabaseSystem, errorManager, pluginId, userId);
        try {
            transactionNotificationAgent.start();
        } catch (CantStartAgentException e) {
            /**
             * If I couldn't start the agent, I still will continue with the vault
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }


        /**
         * the service is started.
         */
        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * Service interface implementation
     */
    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;
    }

    /**
     * Service interface implementation
     */
    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * Service interface implementation
     */
    @Override
    public void stop() {

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();

        /**
         * I will also stop the Notification Agent
         */
        transactionNotificationAgent.stop();

        this.serviceStatus = ServiceStatus.STOPPED;
    }


    /**
     * CryptoVaultManager interface implementation
     */
    @Override
    public void connectToBitcoin() {
        try {
            vault.connectVault();
        } catch (CantStartAgentException e) {
            e.printStackTrace();
        }
    }

    /**
     * CryptoVaultManager interface implementation
     */
    @Override
    public void disconnectFromBitcoin() {
        vault.disconnectVault();

    }

    /**
     * CryptoVaultManager interface implementation
     */
    @Override
    public CryptoAddress getAddress() {
        return vault.getAddress();
    }

    /**
     * CryptoVaultManager interface implementation
     */
    @Override
    public List<CryptoAddress> getAddresses(int amount) {
        List<CryptoAddress> addresses = new ArrayList<CryptoAddress>();
        for (int i=0; i < amount; i++){
            addresses.add(getAddress());
        }
        return addresses;
    }

    @Override
    public void sendBitcoins(UUID walletId, UUID FermatTrId, CryptoAddress addressTo, long satothis) throws com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InsufficientMoneyException{
        vault.sendBitcoins(FermatTrId, addressTo, satothis);
    }

    @Override
    public TransactionProtocolManager<CryptoTransaction> getTransactionManager() {
        return vault;
    }
}
