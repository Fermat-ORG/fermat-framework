/*
 * @#CoinapultWorldPluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_world.coinapult.WalletManager;
import com.bitdubai.fermat_api.layer.dmp_world.coinapult.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer.dmp_world.coinapult.exceptions.CantInitializeDbWalletException;
import com.bitdubai.fermat_api.layer.dmp_world.coinapult.exceptions.CantInitializeFileWalletException;
import com.bitdubai.fermat_api.layer.dmp_world.coinapult.exceptions.CantValidateAddressException;
import com.bitdubai.fermat_api.layer.dmp_world.coinapult.wallet.CryptoWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.dmp_world.World;
import com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.enums.States;
import com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.coinapult_http_client.AddressInfo;

import com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.structure.CoinapultWallet;


import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_world.coinapult.developer.bitdubai.version_1.CoinapultWorldPluginRoot</code> represents
 * a coinapult wallet plugin root and wallets manager
 *
 * Created by loui on 12/03/15.
 * Update by Roberto Requena - (rrequena) on 30/04/15.
 * @version 1.0
 */
public class CoinapultWorldPluginRoot  implements  DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, DealsWithPluginDatabaseSystem, WalletManager, Plugin, Service, World {

    /**
     * Represents the name of the file that contains wallets id
     */
    private final String WALLET_IDS_FILE_NAME = "coinapult_wallets_ids";

    /**
     * Represents the status of the service
     */
    private ServiceStatus serviceStatus;

    /**
     * Represents the references to the listeners
     */
    private List<EventListener> listenersAdded;

    /**
     * Represents the references to the coinapult wallets existing in the device
     */
    private Map<String, CoinapultWallet> coinapultWalletsReferences;

    /**
     * DealsWithPluginDatabaseSystem Interface member variable
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * UsesFileSystem Interface member variable
     */
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * DealsWithEvents Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * Represents the Plugin Id.
     */
    private UUID pluginId;

    /**
     * Represents the file
     */
    private PluginTextFile walletsIdsFile;

    /**
     * Constructor
     */
    public  CoinapultWorldPluginRoot(){

        this.listenersAdded = new ArrayList<>();
        this.coinapultWalletsReferences = new HashMap<>();
        this.serviceStatus = ServiceStatus.CREATED;

    }

    /**
     * (non-Javadoc)
     * @see WalletManager#createNewCoinapultWallet(boolean)
     */
    public UUID createNewCoinapultWallet(boolean agree) throws CantCreateWalletException {

        /*
         * Set the BouncyCastleProvider
         */
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);

        /*
         * Generate de new wallet id
         */
        UUID newWalletId = UUID.randomUUID();

        CoinapultWallet newCoinapultWallet = new CoinapultWallet(newWalletId,
                                                                 this.pluginId,
                                                                 this.errorManager,
                                                                 this.eventManager,
                                                                 this.pluginDatabaseSystem,
                                                                 this.pluginFileSystem);

        try {

            /**
             * Initialize the instance
             */
            newCoinapultWallet.initialize();

            /*
             * Create a new account in coinapult with this wallet
             */
            newCoinapultWallet.getCoinapultClient().createAccount();

            /*
             * Activate the new account in coinapult
             */
            newCoinapultWallet.getCoinapultClient().activateAccount(agree);


            /*
             * If empty is the first time
             */
            if ((walletsIdsFile.getContent() == null) &&
                    (walletsIdsFile.getContent() == "")){

                /*
                 * Save the new id on the list file
                 */
                walletsIdsFile.setContent(newWalletId.toString());

            }else{

                /*
                 * Save the new id on the list file with semicolon separator and the old content
                 */
                walletsIdsFile.setContent(newWalletId.toString() + ";" + walletsIdsFile.getContent());
            }

            /*
             * Persist the new content
             */
            walletsIdsFile.persistToMedia();

        } catch (Exception exception) {

            /**
             * the new wallet can not be created
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantCreateWalletException();
        }

        return newWalletId;
    }

    /**
     * (non-Javadoc)
     * @see Service#start()
     * @throws CantStartPluginException
     */
    @Override
    public void start() throws CantStartPluginException {

        try {

            /*
             * get the file with wallets ids
             */
            walletsIdsFile = pluginFileSystem.getTextFile(pluginId, pluginId.toString(), WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

        } catch (FileNotFoundException e) {

            /*
             * The file no exist may be the first time the plugin is running on this device,
             * We need to create the file
             */
            try {

                /*
                 * create the file
                 */
                walletsIdsFile = pluginFileSystem.createTextFile(pluginId, pluginId.toString(), WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

                /*
                 * Persist the file
                 */
                walletsIdsFile.persistToMedia();


            } catch (CantCreateFileException | CantPersistFileException cantCreateFileException) {

                System.out.println(cantCreateFileException);

                /**
                 * the file can not be created or persist
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);
                throw new CantStartPluginException(Plugins.BITDUBAI_COINAPULT_WORLD);

            }


        } catch (CantCreateFileException cantCreateFileException) {

            /**
             * The file exist but can't get it
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);
            throw new CantStartPluginException(Plugins.BITDUBAI_COINAPULT_WORLD);
        }


        /*
         * If file is got it, load it
         */
        if (walletsIdsFile != null){

            try {

                walletsIdsFile.loadFromMedia();

                /*
                 * Get the ids as a list
                 */
                List<String> walletIds;


                if (!walletsIdsFile.getContent().equals("")){

                    walletIds = new ArrayList<>(Arrays.asList(walletsIdsFile.getContent().split(";")));

                    /*
                     * Instantiate a wallet for each id
                     */
                    for (String walletId : walletIds){

                        UUID uuid = UUID.fromString(walletId);
                        CoinapultWallet coinapultWallet = new CoinapultWallet(uuid,
                                                                              this.pluginId,
                                                                              this.errorManager,
                                                                              this.eventManager,
                                                                              this.pluginDatabaseSystem,
                                                                              this.pluginFileSystem);

                        try {

                            coinapultWallet.initialize();
                            coinapultWalletsReferences.put(uuid.toString(), coinapultWallet);
                            //eventManager.raiseEvent(); //lanzar el envento correcto

                        }  catch (CantInitializeDbWalletException | CantInitializeFileWalletException cantInitializeDbWalletException) {

                            /**
                             * Can't initialize this wallet report
                             */
                            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantInitializeDbWalletException);

                        }

                    } //end for

                }//end if


            } catch (CantLoadFileException cantLoadFileException) {
                /**
                 * The file exist but can't load it
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadFileException);
                throw new CantStartPluginException(Plugins.BITDUBAI_COINAPULT_WORLD);
            }

        }


      /* try {
          UUID uuid = createNewCoinapultWallet(true);
           uuid.toString();
        } catch (CantCreateWalletException e) {
            e.printStackTrace();
        } */

        /*
         * if everything is correct
         */
        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * (non-Javadoc)
     * @see Service#pause()
     */
    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;

    }

    /**
     * (non-Javadoc)
     * @see Service#resume()
     */
    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * (non-Javadoc)
     * @see Service#stop()
     */
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

    /**
     * (non-Javadoc)
     * @see Service#getStatus()
     */
    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithPluginFileSystem#setPluginFileSystem(PluginFileSystem)
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /* (non-Javadoc)
    * @see DealsWithPluginDatabaseSystem#setPluginDatabaseSystem()
    */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithEvents#setEventManager(EventManager) )
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithErrors#setErrorManager(ErrorManager)
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * (non-Javadoc)
     * @see Plugin#setId(UUID)
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    /**
     * (non-Javadoc)
     * @see WalletManager#getCoinapultWallet(String)
     */
    public CryptoWallet getCoinapultWallet(String walletId){

        /*
         * If empty return null
         */
        if (coinapultWalletsReferences.isEmpty()){ return null; }

        /*
         * Find the wallet on the references map and return
         */
        return this.coinapultWalletsReferences.get(walletId);

    }

    /**
     * (non-Javadoc)
     * @see WalletManager#validateBitcoinAddress(String, String)
     */
    @Override
    public boolean validateBitcoinAddress(String bitcoinAddress, String walletId) throws CantValidateAddressException{

        if (bitcoinAddress != null && !bitcoinAddress.equals("")){

            /*
             * Obtain the wallet
             */
            CoinapultWallet coinapultWallet = (CoinapultWallet) getCoinapultWallet(walletId);

            try {

                /*
                 * Validate the address with coinapult api
                 */
                AddressInfo.Json res = coinapultWallet.getCoinapultClient().accountAddress(bitcoinAddress);

                //Verifier the status
                if (res.status.equals(States.COINAPULT_ADDRESS_STATE_VALID.toString())){
                    return Boolean.TRUE;
                }

            } catch (Exception exception) {
                /**
                 * Can't validate it
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantValidateAddressException();
            }
        }

        return Boolean.FALSE;
    }


}
