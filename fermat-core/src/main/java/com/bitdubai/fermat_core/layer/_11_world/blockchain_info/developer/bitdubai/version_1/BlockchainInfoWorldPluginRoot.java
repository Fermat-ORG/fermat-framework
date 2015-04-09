package com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._11_world.Agent;
import com.bitdubai.fermat_api.layer._11_world.blockchain_info.exceptions.CantStartBlockchainInfoWallet;
import com.bitdubai.fermat_api.layer._11_world.blockchain_info.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;

import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;

import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer._2_os.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;

import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._11_world.CantCreateCryptoWalletException;

import com.bitdubai.fermat_api.layer._11_world.World;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.BlockchainInfoIncomingCryptoMonitorAgent;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.APIException;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.createwallet.CreateWallet;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.createwallet.CreateWalletResponse;
import com.bitdubai.fermat_api.layer._11_world.CryptoWalletManager;
import  com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.BlockchainInfoWallet;


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


public class BlockchainInfoWorldPluginRoot implements Service,CryptoWalletManager, World,DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, DealsWithPluginDatabaseSystem, Plugin{


        /**
         * Service Interface member variables.
         */

        ServiceStatus serviceStatus = ServiceStatus.CREATED;


        List<EventListener> listenersAdded = new ArrayList<>();

        /**
         * CryptoWalletManager Interface member variables.
         */
        final String WALLET_IDS_FILE_NAME = "walletsIds";
        private String password = "";
        private String apiCode = "91c646ef-c3fd-4dd0-9dc9-eba5c5600549";
        private String privateKey ="";

        /**
         * UsesFileSystem Interface member variable
         */
        PluginFileSystem pluginFileSystem;

        /**
         * DealsWithEvents Interface member variables.
         */
        EventManager eventManager;

        /**
         * DealsWithEvents Interface member variables.
         */
        ErrorManager errorManager;

        /**
         * Plugin Interface member variables.
         */
        UUID pluginId;


    @Override
        public void start() throws CantStartPluginException {

            /**
             * Check if this is the first time this plugin starts. To do so I check if the file containing all the wallets
             * ids managed by this plug-in already exists or not.
             * * *
             */

            PluginTextFile walletIdsFile;
            
            try{

            try{
                 walletIdsFile = pluginFileSystem.getTextFile(pluginId, pluginId.toString(), WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            }
            catch (CantCreateFileException cantCreateFileException) {
                /**
                 * This really should never happen. But if it does...
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);
                throw new CantStartPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD);
            }
             try {
                    walletIdsFile.loadFromMedia();
                    String[] stringWalletIds = walletIdsFile.getContent().split(";");

                    /**
                     * Read file with wallets ids
                     * instance class BlockchainInfoBitcoinWallet, one for each wallet id
                     */
                    for (int j = 0; j < stringWalletIds.length -1; j++) {

                        BlockchainInfoWallet blockchainInfoWallet = new BlockchainInfoWallet(this.pluginId, UUID.fromString(stringWalletIds[j].toString()), this.errorManager);

                        blockchainInfoWallet.setPluginFileSystem(this.pluginFileSystem);
                        blockchainInfoWallet.setPluginDatabaseSystem(this.pluginDatabaseSystem);

                        blockchainInfoWallet.setApiKey(this.apiCode);

                        try{
                            blockchainInfoWallet.start();
                        }
                        catch (CantStartBlockchainInfoWallet e) {
                            System.err.println("CantStartBlockchainInfoWallet: " + e.getMessage());
                            e.printStackTrace();
                            throw new CantStartPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD);
                        }



                    }
                }
                catch (CantLoadFileException cantLoadFileException) {

                    /**
                     * In this situation we might have a corrupted file we can not read. For now the only thing I can do is
                     * to prevent the plug-in from running.

                     */
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadFileException);
                    throw new CantStartPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD);
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
                try{
                    walletIdsFile = pluginFileSystem.createTextFile(pluginId, pluginId.toString(), WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                }  catch (CantCreateFileException cantCreateFileException) {
                    /**
                     * This really should never happen. But if it does...
                     */
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);
                    throw new CantStartPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD);
                }

                try {
                    walletIdsFile.persistToMedia();
                }
                catch (CantPersistFileException cantPersistFileException ) {

                    /**
                     * If I can not save this file, then this plugin shouldn't be running at all.
                     */
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantPersistFileException);
                    throw new CantStartPluginException(Plugins.BITDUBAI_DISCOUNT_WALLET_MIDDLEWARE);
                }
            }

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
         * CryptoWalletManager methods implementation.
         */

        @Override
        public void createWallet (CryptoCurrency cryptoCurrency) throws CantCreateCryptoWalletException {

            try{
                //save wallet id in a file
                UUID walletId = UUID.randomUUID();
                PluginTextFile layoutFile;

                layoutFile = pluginFileSystem.createTextFile(pluginId, pluginId.toString(), WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                layoutFile.setContent(";" + walletId.toString());
                layoutFile.persistToMedia();

                //TODO:generated strong privateKey and past to param
                //
                privateKey = UUID.randomUUID().toString();
                password = UUID.randomUUID().toString();

                CreateWalletResponse response =  CreateWallet.create(this.password,this.apiCode,this.privateKey);
                String  walletAddress = response.getAddress();
                String  walletGuid = response.getIdentifier();
                String walletLink = response.getLink();
                //save wallet guid, address and link in a binary file on disk

                    layoutFile = pluginFileSystem.createTextFile(pluginId, pluginId.toString(), walletId.toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

                layoutFile.setContent(walletAddress + ";" + walletGuid + ";" + walletLink + ";" + this.privateKey + ";" + this.password);
                layoutFile.persistToMedia();

            } catch (IOException|APIException e) {
                /**
                 * If I can not create the  the new wallet in BlockChain Api , then this method fails.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCreateCryptoWalletException();
            }
            catch (CantPersistFileException cantPersistFileException) {

                    /**
                     * If I can not save the id of the new wallet created, then this method fails.
                     */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantPersistFileException);
                    throw new CantCreateCryptoWalletException();

            }  catch (CantCreateFileException cantCreateFileException) {
                /**
                 * This really should never happen. But if it does...
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);
                throw new CantCreateCryptoWalletException();
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
         * DealsWithPluginDatabaseSystem Interface member variable
         */
        PluginDatabaseSystem pluginDatabaseSystem;

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
                this.errorManager=errorManager;
        }

        /**
         * DealsWithPluginIdentity methods implementation.
         */

        @Override
        public void setId(UUID pluginId) {
            this.pluginId = pluginId;
        }



    }
