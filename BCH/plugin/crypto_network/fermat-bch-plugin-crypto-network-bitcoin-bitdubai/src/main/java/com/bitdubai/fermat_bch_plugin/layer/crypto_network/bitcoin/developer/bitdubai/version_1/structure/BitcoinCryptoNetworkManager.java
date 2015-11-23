package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.enums.CryptoVaults;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database.BitcoinCryptoNetworkDatabaseDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.BlockchainException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.util.TransactionProtocolData;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.UTXO;
import org.bitcoinj.core.UTXOProvider;
import org.bitcoinj.core.UTXOProviderException;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.store.UnreadableWalletException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptonetwork.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkManager</code>
 * Starts the monitoring agent that will listen to transactions. Based on the passed public Keys from the network type
 * it will activate a different agent to listen to that network.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 13/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BitcoinCryptoNetworkManager implements TransactionProtocolManager, UTXOProvider {

    /**
     * BitcoinJ wallet where I'm storing the public keys and transactions
     */
    private final String WALLET_FILENAME = "/data/data/com.bitdubai.fermat/files/wallet_";

    /**
     * UTXO Provider interface variables
     */
    BlockchainNetworkType utxoProviderNetworkParameter;

    /**
     * class variables
     */
    BitcoinCryptoNetworkMonitor bitcoinCryptoNetworkMonitor;
    File walletFile;

    /**
     * List of running agents per network
     */
    HashMap<BlockchainNetworkType, BitcoinCryptoNetworkMonitor> runningAgents;

    /**
     * Platform variables
     */
    EventManager eventManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    BitcoinCryptoNetworkDatabaseDao bitcoinCryptoNetworkDatabaseDao;
    UUID pluginId;

    /**
     * Constructor
     * @param eventManager
     * @param pluginDatabaseSystem
     */
    public BitcoinCryptoNetworkManager(EventManager eventManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;

        runningAgents = new HashMap<>();
    }

    /**
     * Monitor the bitcoin network with the passes Key Lists.
     * @param blockchainNetworkTypes
     * @param keyList
     */
    public void monitorNetworkFromKeyList(CryptoVaults cryptoVault, List<BlockchainNetworkType> blockchainNetworkTypes, List<ECKey> keyList) throws CantStartAgentException {
        /**
         * This method will be called from agents from the Vaults. New keys may be added on each call or not.
         */
        try {
            getDao().updateCryptoVaultsStatistics(cryptoVault, keyList.size());
        } catch (CantExecuteDatabaseOperationException e) {
            //If stats where not updated, I will just continue.
            e.printStackTrace();
        }

        /**
         * For each network that is active to be monitored I will...
         */
        for (BlockchainNetworkType blockchainNetworkType : blockchainNetworkTypes){

            /**
             * load (if any) existing wallet.
             */
            Wallet wallet = getWallet(blockchainNetworkType, keyList);


            /**
             * add new keys (if any).
             */
            boolean isWalletReset = false;
            if (areNewKeysAdded(wallet, keyList)){
                wallet.importKeys(keyList);
                try {
                    wallet.saveToFile(walletFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /**
                 * I do not need to reset the wallet because I will
                 * always be importing fresh (unused) keys.
                 */
                isWalletReset = true;
            }

            /**
             * If the agent for this network is already running...
             */
            if (isAgentRunning(blockchainNetworkType)){
                /**
                 * and the wallet was reseted because new keys were added
                 */
                if (isWalletReset){
                    BitcoinCryptoNetworkMonitor bitcoinCryptoNetworkMonitor = runningAgents.get(blockchainNetworkType);
                    bitcoinCryptoNetworkMonitor.stop();
                    runningAgents.remove(blockchainNetworkType);


                    /**
                     * once the agent is stoped, I will restart it with the new wallet.
                     */
                    File walletFilename = new File(WALLET_FILENAME + blockchainNetworkType.getCode());
                    bitcoinCryptoNetworkMonitor = new BitcoinCryptoNetworkMonitor(this.pluginDatabaseSystem, pluginId, wallet, walletFilename);
                    runningAgents.put(blockchainNetworkType, bitcoinCryptoNetworkMonitor);

                    bitcoinCryptoNetworkMonitor.start();
                }
            } else {
                /**
                 * If the agent for the network is not running, I will start a new one.
                 */
                File walletFilename = new File(WALLET_FILENAME + blockchainNetworkType.getCode());
                BitcoinCryptoNetworkMonitor bitcoinCryptoNetworkMonitor = new BitcoinCryptoNetworkMonitor(this.pluginDatabaseSystem, pluginId, wallet, walletFilename);
                runningAgents.put(blockchainNetworkType, bitcoinCryptoNetworkMonitor);

                bitcoinCryptoNetworkMonitor.start();
            }

            /**
             * I will update the detailed stats table with the keys that are imported in the wallet.
             */
            List<ECKey> importedKEys = wallet.getImportedKeys();
            updateDetailedCryptoStats(cryptoVault, blockchainNetworkType, importedKEys);
        }
    }

    /**
     * Updates the detailed stats with the passed information
     * @param cryptoVault
     * @param blockchainNetworkType
     * @param keyList
     */
    private void updateDetailedCryptoStats(CryptoVaults cryptoVault, BlockchainNetworkType blockchainNetworkType, List<ECKey> keyList) {
        try {
            getDao().deleteDetailedCryptoStats(cryptoVault, blockchainNetworkType);
            getDao().updateDetailedCryptoStats(cryptoVault, blockchainNetworkType, keyList);
        } catch (CantExecuteDatabaseOperationException e) {
            /**
             * if the stats are not updated, I can continue anyway.
             */
            e.printStackTrace();
        }
    }

    /**
     * Will create the wallet Object. I will try to load it from disk from a previous execution
     * by forming the name wallet_[NETWORK]. If it doesn't exists, then I will create a new object for this network.
     * @return
     */
    private Wallet getWallet(BlockchainNetworkType blockchainNetworkType, @Nullable List<ECKey> keyList){
        Wallet wallet;
        String fileName = WALLET_FILENAME + blockchainNetworkType.getCode();
        walletFile = new File(fileName);
        try {
            wallet  =Wallet.loadFromFile(walletFile);
        } catch (UnreadableWalletException e) {
            /**
             * If I couldn't load the wallet from file, I'm assuming is a new wallet and I will create it.
             * I'm creating it by importing the keys sent by the vault.
             */
            //wallet = Wallet.fromKeys(BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType), keyList);

            wallet = new Wallet(BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType));
            wallet.importKeys(keyList);

            /**
             * Will set the autosave information and save it.
             */
            wallet.autosaveToFile(walletFile, 1, TimeUnit.SECONDS, null);
            try {
                wallet.saveToFile(walletFile);
            } catch (IOException e1) {
                e1.printStackTrace(); // I will continue because the key addition will trigger an autosave anyway.
            }
        }
        return wallet;
    }

    /**
     * Will compare the keys already saved in the wallet loaded from disk against the list passed by the vault.
     * If there are additions, it will return true.
     * If they are no new keys, will return false.
     * @param wallet
     * @param keys
     * @return
     */
    private boolean areNewKeysAdded(Wallet wallet, List<ECKey> keys){
        List<ECKey> walletKeys = wallet.getImportedKeys();
        /**
         * I remove from the passed list, everything is already saved in the wallet-
         */
        keys.removeAll(walletKeys);

        /**
         * If there are still keys, then we have new ones.
         */
        if (keys.size() >0)
            return true;
        else
            return false;
    }

    /**
     * Verifies if for the passed network type, an Agent is already running.
     * @param blockchainNetworkType
     * @return
     */
    private boolean isAgentRunning(BlockchainNetworkType blockchainNetworkType){
        if (runningAgents.get(blockchainNetworkType) == null)
            return false;
        else
            return true;
    }

    /**
     * instantiate if needed the dao object to access the database
     * @return
     */
    private BitcoinCryptoNetworkDatabaseDao getDao(){
        if (bitcoinCryptoNetworkDatabaseDao == null)
            bitcoinCryptoNetworkDatabaseDao = new BitcoinCryptoNetworkDatabaseDao(this.pluginId, this.pluginDatabaseSystem);
        return bitcoinCryptoNetworkDatabaseDao;
    }

    /**
     * TransactionProtocolManager interface implementations
     */

    /**
     * Confirms the reception of a transaction.
     * This will change the ProtocolStatus of a transaction from ToBeNotified to NoActionRequired
     * @param transactionID
     * @throws CantConfirmTransactionException
     */
    @Override
    public void confirmReception(UUID transactionID) throws CantConfirmTransactionException {
        try {
            getDao().confirmReception(transactionID);
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantConfirmTransactionException(CantConfirmTransactionException.DEFAULT_MESSAGE, e, "Crypto Network issue confirming transaction.", "database issue");
        }
    }

    /**
     * Gets the list of pending transactions, which are marked as Pending_NOTIFIED
     * @param specialist
     * @return
     * @throws CantDeliverPendingTransactionsException
     */
    @Override
    public List<com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        /**
         * the list to return
         */
        List<com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction> transactionList = new ArrayList<>();

        /**
         * Will get all the pendingCryptoTransactions data
         */
        try {
            for (TransactionProtocolData transactionProtocolData : getPendingTransactionProtocolData()){
                com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction transaction;
                /**
                 * I create the transaction protocol object and fill it with the data
                 */
                transaction = new com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction(
                        transactionProtocolData.getTransactionId(),
                        transactionProtocolData.getCryptoTransaction(),
                        transactionProtocolData.getAction(),
                        transactionProtocolData.getTimestamp());
                /**
                 * and Add it to the list
                 */
                transactionList.add(transaction);

                /**
                 * Will set the Protocol Status of this transaction to Sending Notified.
                 */
                getDao().setTransactionProtocolStatus(transaction.getTransactionID(), ProtocolStatus.SENDING_NOTIFIED);
            }
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantDeliverPendingTransactionsException(CantDeliverPendingTransactionsException.DEFAULT_MESSAGE, e, "database error getting the pending transactions.", "database issue");
        }

        return transactionList;
    }

    /**
     * Gets the pending transaction data
     * @return
     */
    private List<TransactionProtocolData> getPendingTransactionProtocolData() throws CantExecuteDatabaseOperationException {
        return getDao().getPendingTransactionProtocolData();
    }

    /**
     * Gets the CryptoTransaction list that matches the specified hash
     * @param txHash
     * @return
     * @throws CantGetCryptoTransactionException
     */
    public List<CryptoTransaction> getGenesisTransaction(String txHash) throws CantGetCryptoTransactionException {
        try {
            return getDao().getIncomingCryptoTransaction(txHash);
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, e, "database operation issue.", "database error");
        }
    }

    /**
     * Broadcast a well formed, commited and signed transaction into the specified network
     * @param blockchainNetworkType
     * @param tx
     * @throws CantBroadcastTransactionException
     */
    public void broadcastTransaction(BlockchainNetworkType blockchainNetworkType, Transaction tx) throws CantBroadcastTransactionException {
        runningAgents.get(blockchainNetworkType).broadcastTransaction(tx);
    }


    /**
     * Gets the UTXO provider from the CryptoNetwork on the specified Network
     * @param blockchainNetworkType
     * @return
     */
    public UTXOProvider getUTXOProvider(BlockchainNetworkType blockchainNetworkType) {
        this.utxoProviderNetworkParameter = blockchainNetworkType;
        return this;
    }

    /**
     * Implementation of UTXOProvider interface. Calculates all the UTXO available which outputs are send to the specified address
     * @param addresses
     * @return
     * @throws UTXOProviderException
     */
    @Override
    public List<UTXO> getOpenTransactionOutputs(List<Address> addresses) throws UTXOProviderException {
        /**
         * load the wallet from the passed network. The network type was defined when the UTXO provider was set.
         */
        Wallet wallet = this.getWallet(utxoProviderNetworkParameter, null);
        List<UTXO> utxoList = new ArrayList<>();

        /**
         * I will get all the outputs that are mine to spent.
         */
        for (TransactionOutput output : wallet.calculateAllSpendCandidates()){
            for (Address address : addresses){
                /**
                 * and if one of them matches the passed address, then I will convert it to an UTXO and add it to the list.
                 */
                if (output.getAddressFromP2PKHScript(RegTestParams.get()) == address){
                    UTXO utxo = new UTXO(   output.getHash(),
                                            output.getIndex(),
                                            output.getValue(),
                                            output.getParentTransactionDepthInBlocks(),
                                            output.getParentTransaction().isCoinBase(),
                                            output.getScriptPubKey(),
                                            address.toString());
                    utxoList.add(utxo);
                }
            }
        }
        return utxoList;
    }


    /**
     * Access the store blockchain and get its height
     * @return
     * @throws UTXOProviderException
     */
    @Override
    public int getChainHeadHeight() throws UTXOProviderException {
        try {
            /**
             * instantiates a blockchain that will load it from file.
             */
            BitcoinCryptoNetworkBlockChain blockChain = new BitcoinCryptoNetworkBlockChain(BitcoinNetworkSelector.getNetworkParameter(utxoProviderNetworkParameter), null);
            /**
             * get its height.
             */
            return blockChain.getBlockChain().getBestChainHeight();
        } catch (BlockchainException e) {
            throw new UTXOProviderException("There was an error loading the blockchain.", e);
        }
    }

    /**
     * returns the nertwork parameter defined for this UTXO provider
     * @return
     */
    @Override
    public NetworkParameters getParams() {
        return BitcoinNetworkSelector.getNetworkParameter(utxoProviderNetworkParameter);
    }

    /**
     * Gets the specified bitcoin transaction
     * @param transactionHash
     * @return
     */
    public Transaction getBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, String transactionHash) {
        Wallet wallet = getWallet(blockchainNetworkType, null);
        Sha256Hash sha256Hash = Sha256Hash.wrap(transactionHash);
        Transaction transaction = wallet.getTransaction(sha256Hash);
        return transaction;
    }

    /**
     * Will get the CryptoTransaction directly from the blockchain by requesting it to a peer.
     * If the transaction is not part of any of our vaults, we will ask it to a connected peer to retrieve it.
     * @param txHash the Hash of the transaction we are going to look for.
     * @param blockHash the Hash of block where this transaction was stored..
     * @return a CryptoTransaction with the information of the transaction.
     * @throws CantGetCryptoTransactionException
     */

    public CryptoTransaction getCryptoTransactionFromBlockChain(String txHash, String blockHash) throws CantGetCryptoTransactionException {
        return null;
    }
}
