package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletBasicWallet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 30/04/15.
 */
public class BitcoinWalletBasicWalletPluginRoot implements BitcoinWalletManager, Service, Plugin {

    BitcoinWallet bitcoinWallet = new BitcoinWalletBasicWallet();
    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();


    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
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
    public void stop() {


        /**
         * I will remove all the event listeners registered with the event manager.
         */

        listenersAdded.clear();
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    //TODO: COMPLETAR
    @Override
    public void createWallet(UUID walletId) throws CantCreateWalletException {
        /*
         * El wallet manager va a llamar este método con un UUID.
         * La idea es que el plug-in root creer un UUID interno y lo asocie a este UUID que recibió
         * Luego creoa una BitcoinWalletBasicWallet y llama al método create() de esa wallet pasándole este
         * UUID interno.
         */
    }

    @Override
    public BitcoinWallet loadWallet(UUID walletId) throws CantLoadWalletException {
        return this.bitcoinWallet;
        /* TODO:
         * Este método va a buscar el UUID que le pasan en el archivo que mantiene las referencias
         * Toma el UUID asociado a este ID que le pasaron y crea una BitcoinWalletBasicWallet. A diferencia del
         * create este método va a llamar al initialize de la BitcoinWalletBasicWallet.
         */
    }

}

