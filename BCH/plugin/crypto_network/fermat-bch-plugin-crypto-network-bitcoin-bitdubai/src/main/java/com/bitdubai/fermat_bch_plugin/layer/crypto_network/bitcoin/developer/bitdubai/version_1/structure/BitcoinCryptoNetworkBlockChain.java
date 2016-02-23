package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.BlockchainException;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.CheckpointManager;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.store.SPVBlockStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.print.attribute.standard.DateTimeAtCompleted;

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
public class BitcoinCryptoNetworkBlockChain implements Serializable{

    /**
     * Classes variables
     */
    private BlockChain blockChain;
    private BlockStore blockStore;
    private Wallet wallet;
    private NetworkParameters networkParameters;
    private final String BLOCKCHAIN_FILENAME;
    private final String BLOCKCHAIN_PATH;
    private final String CHECKPOINT_FILENAME;
    private final BlockchainNetworkType BLOCKCHAIN_NETWORK_TYPE;
    PluginFileSystem pluginFileSystem;


    /**
     * Constructor
     */
    public BitcoinCryptoNetworkBlockChain(PluginFileSystem pluginFileSystem, NetworkParameters networkParameters, Wallet wallet) throws BlockchainException {
        this.pluginFileSystem = pluginFileSystem;
        this.networkParameters= networkParameters;
        this.wallet = wallet;

        this.BLOCKCHAIN_NETWORK_TYPE = BitcoinNetworkSelector.getBlockchainNetworkType(this.networkParameters);
        this.BLOCKCHAIN_PATH = pluginFileSystem.getAppPath();
        this.BLOCKCHAIN_FILENAME = "bitcoin_Blockchain_" + BLOCKCHAIN_NETWORK_TYPE.getCode();
        this.CHECKPOINT_FILENAME = "checkpoints-" + BLOCKCHAIN_NETWORK_TYPE.getCode();

        /**
         * initialize the objects
         */
        try {
            initialize(false);
        } catch (BlockStoreException e) {
            throw new BlockchainException(BlockchainException.DEFAULT_MESSAGE, e, "Could not create blockchain to store block headers.", null);
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
     * Initializes the blockchain and blockstore objects.
     * @param withError since I'm using this recursively, I will use this parameter to avoid a loop.
     * @throws BlockStoreException if something went wrong and I can't create the blockchain
     */
    private void initialize(boolean withError) throws BlockStoreException {
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
        if (blockChainFile.exists())
            firstTime = false;

        /**
         * If this is the RegTest Network, I will delete any previous blockstore
         * Since this blockchain will be very small, I will rebuild it each time.
         */
        if (BLOCKCHAIN_NETWORK_TYPE == BlockchainNetworkType.REG_TEST){
            if (blockChainFile.exists())
                blockChainFile.delete();
        }

        /**
         * I create the blockstore.
         */
        try {
            blockStore = new SPVBlockStore(networkParameters, blockChainFile);
        } catch (Exception e) {
            /**
             * If there is an error saving it to file, I will save it to memory
             */
            blockStore = new MemoryBlockStore(this.networkParameters);
            System.out.println("*** Crypto Network Warning, error creating file to store blockchain, will save it to memory.");
            System.out.println("*** Crypto Network: " + e.toString());


        }

        /**
         * I will load the checkpoints for this network, if this is the initialization of the blockchain and
         * the checkpoint exists.
         */
        try {
            if (firstTime)
                loadCheckpoint();
        } catch (IOException e) {
            // if there are no checkpoints, then I will continue
            e.printStackTrace();
        }

        /**
         * I initialize the blockchain object
         */
        try{
            blockChain = new BlockChain(this.networkParameters, wallet, blockStore);
        } catch (Exception e){
            if (withError)
                throw new BlockStoreException(e);
            /**
             * In case we have an issue like a corrupted blockstore, will delete the blockchain file
             */
            blockChainFile.delete();
            initialize(true);
        }
    }

    /**
     * If there are checkpoints for this network type, then I will load them to the blockchain
     */
    private void loadCheckpoint() throws BlockStoreException, IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(CHECKPOINT_FILENAME);

        if (inputStream != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = null;
            try {
                date = format.parse("2016-01-05 01:24:22");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long millis = date.getTime();


            CheckpointManager.checkpoint(networkParameters, inputStream, blockStore, millis);
            System.out.println("*** Crypto Network *** Checkpoint loaded for network " + BLOCKCHAIN_NETWORK_TYPE.getCode());
        }

    }
}
