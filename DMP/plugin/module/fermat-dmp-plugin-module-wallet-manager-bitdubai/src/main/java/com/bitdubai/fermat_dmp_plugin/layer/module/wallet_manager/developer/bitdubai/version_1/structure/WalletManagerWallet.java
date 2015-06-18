package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.NicheWalletType;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.Wallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletStatus;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantPersistWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.OpenFailedException;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;

import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.WalletCreatedEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.WalletWentOnlineEvent;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;


import java.util.UUID;

/**
 * Created by ciencias on 25.01.15.
 */
public class WalletManagerWallet implements Wallet, DealsWithEvents, DealsWithPluginFileSystem, DealsWithErrors, DealsWithPluginIdentity {

    UUID walletId;
    String walletName = "";
    NicheWalletType nicheWalletType;
    WalletStatus status;

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;


    /**
     * This method is to be used for creating a new wallet.
     */

    public void createWallet(NicheWalletType nicheWalletType) throws CantCreateWalletException {
        this.walletId = UUID.randomUUID();
        this.status = WalletStatus.CLOSED;
        this.nicheWalletType = nicheWalletType;

        try {
            persist();
        }
        catch (CantPersistWalletException cantPersistWalletException)
        {
            /**
             * This is bad, but lets handle it...
             */
            System.err.println("CantPersistWalletException: " + cantPersistWalletException.getMessage());
            cantPersistWalletException.printStackTrace();
            throw new CantCreateWalletException();
        }

        /**
         * Now I fire the Wallet Created  event.
         */

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_CREATED);
        ((WalletCreatedEvent) platformEvent).setWalletId(this.walletId);
        platformEvent.setSource(EventSource.MODULE_WALLET_MANAGER_PLUGIN);
        eventManager.raiseEvent(platformEvent);

    }


    /**
     * This method is to be used to load a saved wallet and to put it online.
     */

    public void loadWallet (UUID walletId) throws CantLoadWalletException {

        this.walletId = walletId;

        try {
            load();
            this.changeToOnlineStatus();
        }
        catch (CantLoadWalletException cantLoadWalletException)
        {
            /**
             * This is bad, but lets handle it...
             */
            System.err.println("CantLoadWalletException: " + cantLoadWalletException.getMessage());
            cantLoadWalletException.printStackTrace();
            throw new CantLoadWalletException();
        }

    }

    @Override
    public UUID getId() {
        return this.walletId ;
    }

    @Override
    public String getWalletName() {
        return this.walletName;
    }

    @Override
    public NicheWalletType getNicheWalletType() {
        return this.nicheWalletType;
    }

    @Override
    public WalletStatus getStatus() {
        return this.status;
    }

    @Override
    public void open() throws OpenFailedException {


        // TODO: Raise event signaling this wallet was opened.

        //if (this.password != password) {
        //    throw new OpenFailedException();
        //}
        //else
        {
            this.status = WalletStatus.OPEN;
        }
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
     * Private methods implementation.
     */

    private void changeToOnlineStatus(){

        this.status = WalletStatus.ONLINE;

        /**
         * Now I fire the ONLINE event.
         */

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_WENT_ONLINE);
        ((WalletWentOnlineEvent) platformEvent).setWalletId(this.walletId);
        platformEvent.setSource(EventSource.MODULE_WALLET_MANAGER_PLUGIN);
        eventManager.raiseEvent(platformEvent);

    }


    private void persist() throws CantPersistWalletException {
/*
        PluginFile file = this.pluginFileSystem.createFile(
                pluginId,
                DeviceDirectory.LOCAL_WALLETS.getName(),
                this.walletId.toString(),
                FilePrivacy.PRIVATE,
                FileLifeSpan.PERMANENT
        );

        file.setContent(this.walletName + ";" + this.nicheWalletType.getTypeName());

        try {
            file.persistToMedia();
        }
        catch (CantPersistFileException cantPersistFileException) {
*/
            /**
             * This is bad, but lets handle it...
             */
/*
            System.err.println("CantPersistFileException: " + cantPersistFileException.getMessage());
            cantPersistFileException.printStackTrace();
            throw new CantPersistWalletException();
        }
*/
    }

    private void load() throws CantLoadWalletException {
/*
        try {

            PluginFile file = this.pluginFileSystem.getFile(
                    pluginId,
                    DeviceDirectory.LOCAL_WALLETS.getName(),
                    this.walletId.toString(),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadToMemory();
            String[] values = file.getContent().split(";", -1);

            this.walletName = values[0];
            this.nicheWalletType = NicheWalletType.getTypeByName(values[1]);

        }
        catch (FileNotFoundException |CantLoadFileException ex)
        {
*/
        /**
         * This is bad, but lets handle it...
         */
  /*
            System.err.println("FileNotFoundException or CantLoadFileException: " + ex.getMessage());
            ex.printStackTrace();
            throw new CantLoadWalletException();
        }
*/
    }


    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

}
