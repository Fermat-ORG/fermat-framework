package com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;

import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer._2_os.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantEncryptException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;

import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._11_world.CantCreateCryptoWalletException;

import com.bitdubai.fermat_api.layer._11_world.World;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.APIException;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.createwallet.CreateWallet;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.createwallet.CreateWalletResponse;
import com.bitdubai.fermat_api.layer._11_world.CryptoWalletManager;
import  com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.BlockchainInfoWallet;


import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by loui on 12/03/15.
 */

/**
 * Blockchain Plugin. 
 */


public class BlockchainInfoWorldPluginRoot implements CryptoWalletManager,Service, World,DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, DealsWithPluginDatabaseSystem, Plugin{

    private String WALLETS_ID_FILE_NAME  = "wallets_ids";

    /**
     * CryptoWalletManager Interface member variables.
     */
    private String password = "";
    private String apiCode = "91c646ef-c3fd-4dd0-9dc9-eba5c5600549";
    private String privateKey ="";

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

            /**
             * Read file with wallets ids
             * instance class BlockchainInfoBitcoinWallet, one for each wallet id
             */
            try{
                  // TODO: El folder no puede ser wallets_data, debe ser un hash del UUID del plugin. En general cada plugin guarda archivos en un folder propio. Busca como se calcula en java un hash 256 de manera standard y eso usamos para el nombre del folder. El que debe hashear el nombre es el PluginFileSystem, no cada plugin individualmente.

                PluginTextFile layoutFile = pluginFileSystem.getTextFile(pluginId, String.valueOf(pluginId.hashCode()), WALLETS_ID_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                String[] walletsIds = layoutFile.getContent().split(";");

                for (int j = 0; j < walletsIds.length - 1; j++) {

                    BlockchainInfoWallet blockchainInfoWallet = new BlockchainInfoWallet(this.pluginId, UUID.fromString(walletsIds[j].toString()));

                    blockchainInfoWallet.setPluginFileSystem(this.pluginFileSystem);
                    blockchainInfoWallet.setPluginDatabaseSystem(this.pluginDatabaseSystem);

                    blockchainInfoWallet.setApiKey(this.apiCode);
                    blockchainInfoWallet.start();
                }

            }
            catch (FileNotFoundException e) {
                try{
                    /**
                     * if not exist create it
                     */
                    PluginTextFile layoutFile = pluginFileSystem.createTextFile(pluginId, String.valueOf(pluginId.hashCode()), WALLETS_ID_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                    layoutFile.setContent("");
                    layoutFile.persistToMedia();
                } catch (CantPersistFileException ex) {
                    System.err.println("CantPersistFileException: " + ex.getMessage());
                    e.printStackTrace();
                }

            }
// TODO:NATALIA tenemos que estandarizar lo que se hace en el catch para logear los errores , mientras no este claro, fijate lo que se hace en la clase Platform y hace lo mismo. En este caso si el archivo no existe deberia crearlo.


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
// TODO: El folder no puede ser wallets_data, debe ser un hash del UUID del plugin. En general cada plugin guarda archivos en un folder propio. Busca como se calcula en java un hash 256 de manera standard y eso usamos para el nombre del folder. El que debe hashear el nombre es el PluginFileSystem, no cada plugin individualmente.

                layoutFile = pluginFileSystem.createTextFile(pluginId,String.valueOf(pluginId.hashCode()), WALLETS_ID_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
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

                layoutFile = pluginFileSystem.createTextFile(pluginId, String.valueOf(pluginId.hashCode()), walletId.toString() + ".txt", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

                layoutFile.setContent(walletAddress + ";" + walletGuid + ";" + walletLink + ";" + this.privateKey + ";" + this.password);
                layoutFile.persistToMedia();

            } catch (APIException e) {
                System.err.println("APIException: " + e.getMessage());
                e.printStackTrace();}
            catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
                e.printStackTrace();
            }
                catch (CantPersistFileException e) {
                System.err.println("CantPersistFileException: " + e.getMessage());
                e.printStackTrace();}

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

        }

        /**
         * DealsWithPluginIdentity methods implementation.
         */

        @Override
        public void setId(UUID pluginId) {
            this.pluginId = pluginId;
        }



    }
/* hash string
import java.security.MessageDigest;

MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
messageDigest.update(stringToEncrypt.getBytes());
String encryptedString = new String(messageDigest.digest());
 */