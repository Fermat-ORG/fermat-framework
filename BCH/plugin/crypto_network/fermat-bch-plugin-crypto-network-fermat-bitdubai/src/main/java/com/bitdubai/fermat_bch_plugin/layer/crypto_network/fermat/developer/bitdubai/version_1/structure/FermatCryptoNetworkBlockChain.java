package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.exceptions.BlockchainException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.util.FermatBlockchainNetworkSelector;


import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.DownloadProgressTracker;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.store.SPVBlockStore;

import java.io.File;
import java.io.Serializable;

/**
 * Created by rodrigo on 6/22/16.
 */
public class FermatCryptoNetworkBlockChain extends DownloadProgressTracker implements Serializable {

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
    private final Context context;
    PluginFileSystem pluginFileSystem;


    /**
     * Constructor
     */
    public FermatCryptoNetworkBlockChain(PluginFileSystem pluginFileSystem, NetworkParameters networkParameters, Wallet wallet, Context context) throws BlockchainException {
        this.pluginFileSystem = pluginFileSystem;
        this.wallet = wallet;
        this.context = context;
        this.networkParameters= this.context.getParams();

        this.BLOCKCHAIN_NETWORK_TYPE = FermatBlockchainNetworkSelector.getBlockchainNetworkType(this.networkParameters);
        this.BLOCKCHAIN_PATH = pluginFileSystem.getAppPath();
        this.BLOCKCHAIN_FILENAME = "fermat_Blockchain_" + BLOCKCHAIN_NETWORK_TYPE.getCode();
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
         * I create the blockstore.
         */
        try {
            blockStore = new SPVBlockStore(context.getParams(), blockChainFile);
        } catch (Exception e) {
            /**
             * If there is an error saving it to file, I will save it to memory
             */
            initializeInMemory();
            System.out.println("*** Fermat Crypto Network Warning, error creating file to store blockchain, will save it to memory.");
            System.out.println("*** Fermat Crypto Network: " + e.toString());
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
}
