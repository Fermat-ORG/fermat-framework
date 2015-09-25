package com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.ccp_world.Agent;
import com.bitdubai.fermat_api.layer.ccp_world.wallet.exceptions.CantInitializeMonitorAgentException;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.ccp_world.wallet.exceptions.CantStartAgentException;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 * Created by ciencias on 3/19/15.
 */
public class BlockchainInfoIncomingCryptoMonitorAgent implements Agent, DealsWithErrors, DealsWithPluginDatabaseSystem {
    /**
     * DealsWithEvents Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Agent Member Variables.
     */
    Thread agentThread;
    MonitorAgent monitorAgent;

    Database database;
    UUID pluginId;
    UUID walletId;
    /**
     * BlockchainInfo websocket variables
     */
    String walletAddress = "mie9LD4XegqDcisVouLdMD3KugPiQj7FS3";
    WebSocketClient mWebSocketClient = null;
    String wsUri = "ws://ws.blockchain.info/inv/";

    public BlockchainInfoIncomingCryptoMonitorAgent(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, UUID walletId){
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.walletId = walletId;
    }
    /**
     * Agent interface implementation.
     */

    @Override
    public void start() throws CantStartAgentException {

        this.monitorAgent = new MonitorAgent ();

        ((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithErrors) this.monitorAgent).setErrorManager(this.errorManager);

        try {
            ((MonitorAgent) this.monitorAgent).Initialize();
        }
        catch (CantInitializeMonitorAgentException cantInitializeCryptoRegistryException) {
            /**
             * I cant continue if this happens.
             */
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantInitializeCryptoRegistryException);

            throw new CantStartAgentException();
        }

        this.agentThread = new Thread(new MonitorAgent());
        this.agentThread.start();
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    /**
     *DealsWithErrors Interface implementation.
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


    private class MonitorAgent implements DealsWithErrors,DealsWithPluginDatabaseSystem, Runnable  {

        private final int SLEEP_TIME = 5000;
        /**
         * DealsWithEvents Interface member variables.
         */
        ErrorManager errorManager;

        /**
         * DealsWithPluginDatabaseSystem Interface member variables.
         */
        PluginDatabaseSystem pluginDatabaseSystem;


        /**
         * MonitorAgent interface implementation.
         */
        private void Initialize () throws CantInitializeMonitorAgentException {

            /**
             * Here I open the database read the event table and load it to memory.
             */

            /**
             * I will try to open the transactions' database..
             */
            try {
                database = this.pluginDatabaseSystem.openDatabase(pluginId, walletId.toString());
            }
            catch (DatabaseNotFoundException databaseNotFoundException) {

                BlockchainInfoDatabaseFactory databaseFactory = new BlockchainInfoDatabaseFactory();
                databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

                /**
                 * I will create the database where I am going to store the information of this wallet.
                 */
                try {

                    database =  databaseFactory.createDatabase(pluginId, walletId);

                }
                catch (CantCreateDatabaseException cantCreateDatabaseException){

                    /**
                     * The database cannot be created. I can not handle this situation.
                     */
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                    throw new CantInitializeMonitorAgentException();
                }
            }
            catch (CantOpenDatabaseException cantOpenDatabaseException){

                /**
                 * The database exists but cannot be open. I can not handle this situation.
                 */
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeMonitorAgentException();
            }

            /**
             * Connect to BlockchainApi to receive uncommitted transactions.
             */
            //Subscribing to an Address  {"op":"addr_sub", "addr":"$bitcoin_address"}
            connectWebSocket();
            try{
                mWebSocketClient.send("{'op':'addr_sub', 'addr':'"+ walletAddress +"'}");
            }
            catch(Exception e)
            {
                String strError = e.getMessage();
            }


        }

        /**
         *DealsWithErrors Interface implementation.
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
         * Runnable Interface implementation.
         */
        @Override
        public void run() {

            /**
             * Infinite loop.
             */
            while (true == true) {

                /**
                 * Sleep for a while.
                 */
             /*   try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    cleanResources();
                    return;
                }*/

                /**
                 * Now I do the main task.
                 */
                doTheMainTask();

                /**
                 * Check if I have been Interrupted.
                 */
                if (Thread.currentThread().isInterrupted()) {
                    cleanResources();
                    return;
                }
            }
        }

        private void doTheMainTask() {


           /* try {
                // open websocket
                final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI(wsUri));

                // add listener
                clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                    public void handleMessage(String message) {
                        System.out.println(message);
                    }
                });

                // send message to websocket
                clientEndPoint.sendMessage("{'op':'addr_sub', 'addr':'$bitcoin_address'}");

                // wait 5 seconds for messages from websocket
                Thread.sleep(5000);



            } catch (InterruptedException ex) {
                System.err.println("InterruptedException exception: " + ex.getMessage());
            } catch (URISyntaxException ex) {
                System.err.println("URISyntaxException exception: " + ex.getMessage());
            }*/

            /**
             * When recive socket message
             * Save transactions into Database
             */



           /* DatabaseTable incomingCryptoTable = database.getTable(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_NAME);
            DatabaseTableRecord incomingCryptoRecord = incomingCryptoTable.getEmptyRecord();

           UUID creditRecordId = UUID.randomUUID();

            int amount = 0;

            incomingCryptoRecord.setUUIDValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME , creditRecordId);
            incomingCryptoRecord.setLongValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_AMOUNT_COLUMN_NAME, amount);
            incomingCryptoRecord.setStringValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_CRYPTO_ADDRESS_TO_COLUMN_NAME, walletId.toString());
            incomingCryptoRecord.setStringValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_CRYPTO_ADDRESS_FROM_COLUMN_NAME, walletAddress);
            incomingCryptoRecord.setStringValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_STATUS_COLUMN_NAME, "JUST_RECEIVED");
            incomingCryptoRecord.setLongValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_CURRENT_CONFIRMATIONS_COLUMN_NAME,  0);
            incomingCryptoRecord.setLongValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_PREVIOUS_CONFIRMATIONS_COLUMN_NAME,  0);

            try{
                incomingCryptoTable.insertRecord(incomingCryptoRecord);
            }catch(CantInsertRecordException cantInsertRecord)
            {
                /**
                 * I can not solve this situation.
                 */

           // }


        }



        private void cleanResources() {

            /**
             * Disconnect from database and explicitly set all references to null.
             */

        }

        private void connectWebSocket() {
            URI uri;

            try {
                uri = new URI("ws://ws.blockchain.info/inv/");
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return;
            }

            mWebSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                    mWebSocketClient.send("{'op':'addr_sub', 'addr':'"+ walletAddress +"'}");
                }

                @Override
                public void onMessage(String s) {
                    final String message = s;
                 /*   runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //TextView textView = (TextView)findViewById(R.id.messages);
                           // textView.setText(textView.getText() + "\n" + message);
                        }
                    });*/
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    String strClose ="Close connection";
                }

                @Override
                public void onError(Exception e) {
                    String strClose ="Close error";
                }
            };
            mWebSocketClient.connect();
        }

        public void sendMessage(String message) {

            mWebSocketClient.send(message);
        }
    }
}

