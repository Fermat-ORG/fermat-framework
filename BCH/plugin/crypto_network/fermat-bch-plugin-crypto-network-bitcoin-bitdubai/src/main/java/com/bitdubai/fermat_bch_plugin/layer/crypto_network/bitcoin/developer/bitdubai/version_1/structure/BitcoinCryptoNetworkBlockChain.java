package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.BlockchainException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.util.BitcoinBlockchainNetworkSelector;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.CheckpointManager;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.DownloadProgressTracker;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.store.SPVBlockStore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptonetwork.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkBlockChain</code>
 * holds the downloaded blockchain by saving the block headers.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 09/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BitcoinCryptoNetworkBlockChain extends DownloadProgressTracker implements Serializable {

    /**
     * Classes variables
     */
    final boolean isReset;
    private BlockChain blockChain;
    private BlockStore blockStore;
    private Wallet wallet;
    private NetworkParameters networkParameters;
    private final String BLOCKCHAIN_FILENAME;
    private final String BLOCKCHAIN_PATH;
    private final String CHECKPOINT_FILENAME;
    private final BlockchainNetworkType BLOCKCHAIN_NETWORK_TYPE;
    private final Context context;
    PluginFileSystem pluginFileSystem;


    /**
     * Constructor
     */
    public BitcoinCryptoNetworkBlockChain(boolean isReset, PluginFileSystem pluginFileSystem, NetworkParameters networkParameters, Wallet wallet, Context context) throws BlockchainException {
        this.isReset = isReset;
        this.pluginFileSystem = pluginFileSystem;
        this.wallet = wallet;
        this.context = context;
        this.networkParameters= this.context.getParams();

        this.BLOCKCHAIN_NETWORK_TYPE = BitcoinBlockchainNetworkSelector.getBlockchainNetworkType(this.networkParameters);
        this.BLOCKCHAIN_PATH = pluginFileSystem.getAppPath();
        this.BLOCKCHAIN_FILENAME = "bitcoin_Blockchain_" + BLOCKCHAIN_NETWORK_TYPE.getCode();
        this.CHECKPOINT_FILENAME = "checkpoints-" + BLOCKCHAIN_NETWORK_TYPE.getCode();


        /**
         * initialize the objects
         */
        try {
            initialize();
        } catch (BlockStoreException e) {
            throw new BlockchainException(BlockchainException.DEFAULT_MESSAGE, e, "Could not create blockchain to store block headers.", "NetworkType:" + BLOCKCHAIN_NETWORK_TYPE.getCode());
        }
    }

    /**
     * Gets the blockChain object
     * @return
     */
    public BlockChain getBlockChain() {
        return blockChain;
    }

    /**
     * deletes the blockchain
     */
    public void deleteBlockchain(){
        File blockChainFile = new File(BLOCKCHAIN_PATH, BLOCKCHAIN_FILENAME);
        blockChainFile.delete();
    }
    /**
     * Initializes the blockchain and blockstore objects.
     * @throws BlockStoreException if something went wrong and I can't create the blockchain
     */
    private void initialize() throws BlockStoreException {
        /**
         * I will define the SPV blockstore were I will save the blockchain.
         * I will be saving the file under the network type I'm being created for.
         */
        File blockChainFile = new File(BLOCKCHAIN_PATH, BLOCKCHAIN_FILENAME);

        /**
         * I will verify in the blockchain file already exists.
         * to set a boolean variable and decide later If I will add checkpoints.
         */
        boolean firstTime = true;
        if (blockChainFile.exists()){
            firstTime = false;

            // if this is regTest I will delete the blockstore to download it again.
            if (BLOCKCHAIN_NETWORK_TYPE == BlockchainNetworkType.REG_TEST)
                blockChainFile.delete();
        }

        /**
         * If a reset happened, we delete and set as first time to use the checkpoints.
         */
        if (isReset){
            firstTime = true;
            blockChainFile.delete();
        }


        /**
         * I create the blockstore.
         */
        try {
            blockStore = new SPVBlockStore(context.getParams(), blockChainFile);
        } catch (Exception e) {
            /**
             * If there is an error saving it to file, I will save it to memory
             */
            initializeInMemory();
            System.out.println("*** Crypto Network Warning, error creating file to store blockchain, will save it to memory.");
            System.out.println("*** Crypto Network: " + e.toString());
        }

        /**
         * I will load the checkpoints for this network, if this is the initialization of the blockchain and
         * the checkpoint exists.
         */
        try {
            if (firstTime){
                switch (BLOCKCHAIN_NETWORK_TYPE){
                    case TEST_NET:
                        loadCheckpoint("2016-07-13 00:00:43");
                        break;
                    case PRODUCTION:
                        loadCheckpoint("2016-06-30 22:11:25");
                        break;
                }
            }
        } catch (IOException e) {
            // if there are no checkpoints, then I will continue
            System.out.println("***CryptoNetwork*** no checkpoint founds for network type " + BLOCKCHAIN_NETWORK_TYPE.getCode());
        }

        /**
         * I initialize the blockchain object
         */
        blockChain = new BlockChain(context, wallet, blockStore);
    }

    private void initializeInMemory() throws BlockStoreException {
        blockStore = new MemoryBlockStore(context.getParams());
        blockChain = new BlockChain(context, wallet, blockStore);
    }

    /**
     * If there are checkpoints for this network type, then I will load them to the blockchain
     */
    private void loadCheckpoint(String dateTime) throws BlockStoreException, IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(CHECKPOINT_FILENAME);

        if (inputStream != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = null;
            try {
                date = format.parse(dateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long millis = date.getTime();


            CheckpointManager.checkpoint(networkParameters, inputStream, blockStore, millis);
            System.out.println("*** Crypto Network *** Checkpoint loaded for network " + BLOCKCHAIN_NETWORK_TYPE.getCode());
        }

    }
}
