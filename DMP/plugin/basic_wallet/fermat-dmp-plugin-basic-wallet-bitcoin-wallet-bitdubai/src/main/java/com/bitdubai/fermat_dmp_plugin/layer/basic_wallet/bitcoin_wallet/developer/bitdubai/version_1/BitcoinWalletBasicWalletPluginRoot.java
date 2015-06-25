package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantInitializeBitcoinWalletBasicException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
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
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletBasicWallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by loui on 30/04/15.
 */
public class BitcoinWalletBasicWalletPluginRoot implements BitcoinWalletManager, DealsWithErrors,DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem,Service, Plugin {

    private final String WALLET_IDS_FILE_NAME = "walletsIds";
    private Map<UUID, UUID> walletIds =  new HashMap();
    // No tocar
    BitcoinWalletBasicWallet bitcoinWalletTemporal = new BitcoinWalletBasicWallet(UUID.randomUUID());

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;


    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();


    /**
     * DealsWithErrors interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }


    /**
     * DealsWithPluginIdentity methods implementation.
     */
    @Override
    public void setId (UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException{

        /**
         * Check if this is the first time this plugin starts. To do so I check if the file containing all the wallets
         * ids managed by this plug-in already exists or not.
         * * *
         */
        PluginTextFile walletIdsFile;

        BitcoinWalletBasicWallet bitcoinWallet = new BitcoinWalletBasicWallet(this.pluginId);

        bitcoinWallet.setPluginFileSystem(pluginFileSystem);
        bitcoinWallet.setPluginDatabaseSystem(pluginDatabaseSystem);
        bitcoinWallet.setErrorManager(errorManager);
        try {
            bitcoinWallet.create(UUID.fromString("25428311-deb3-4064-93b2-69093e859871"));
        }
        catch (CantCreateWalletException cantCreateWalletException)
        {

        }


        try {

            try{
                walletIdsFile = pluginFileSystem.getTextFile(pluginId, "", WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            }
            catch (CantCreateFileException cantCreateFileException ) {

                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                System.err.println("cantCreateFileException: " + cantCreateFileException.getMessage());
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);

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
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadFileException);

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
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);
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
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantPersistFileException);
                throw new CantStartPluginException(cantPersistFileException, Plugins.BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET);
            }
        }
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



    @Override
    public BitcoinWallet loadWallet(UUID walletId) throws CantLoadWalletException {

        BitcoinWalletBasicWallet bitcoinWallet = new BitcoinWalletBasicWallet(this.pluginId);
        bitcoinWallet.setErrorManager(this.errorManager);
        bitcoinWallet.setPluginDatabaseSystem(this.pluginDatabaseSystem);
        bitcoinWallet.setPluginFileSystem(pluginFileSystem);


       try {

           //get internal wallet id asociate with this wallet id
            UUID internalWalletId = null;

           Iterator iterator = walletIds.entrySet().iterator();

           while (iterator.hasNext()) {
               Map.Entry mapEntry = (Map.Entry) iterator.next();
              if( mapEntry.getKey().toString().equals(walletId.toString()))
                  internalWalletId = UUID.fromString(mapEntry.getValue().toString());
           }

            //open wallet database
            bitcoinWallet.initialize(internalWalletId);

        }catch(CantInitializeBitcoinWalletBasicException CantInitializeBitcoinWallet)
        {
           errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, CantInitializeBitcoinWallet);
           throw new CantLoadWalletException();
       }


         return bitcoinWallet;
        //return this.bitcoinWalletTemporal;



    }

    @Override
    public void createWallet(UUID walletId) throws CantCreateWalletException {

        BitcoinWalletBasicWallet bitcoinWallet = new BitcoinWalletBasicWallet(this.pluginId);
        bitcoinWallet.setErrorManager(this.errorManager);
        bitcoinWallet.setPluginDatabaseSystem(this.pluginDatabaseSystem);


        try {
            bitcoinWallet.create(walletId);
        }catch(CantCreateWalletException CantInitializeBitcoinWallet)
        {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, CantInitializeBitcoinWallet);
            throw new CantCreateWalletException();
        }
    }
}

