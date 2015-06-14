package com.bitdubai.fermat_cry_plugin.layer.cry_1_crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer._12_world.wallet.exceptions.CantInitializeMonitorAgentException;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer._2_os.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.cry_1_crypto_network.bitcoin.BitcoinManager;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.store.SPVBlockStore;

import java.io.File;
import java.util.UUID;

import static com.bitdubai.fermat_cry_plugin.layer.cry_1_crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinNetworkConfiguration.getNetworkConfiguration;

/**
 * Created by rodrigo on 25/05/15.
 * Holds the blockchain data.
 */

class StoredBlockChain implements BitcoinManager, DealsWithErrors, DealsWithPluginFileSystem, DealsWithPluginIdentity{
    /**
     * StoredBlockChain members variables
     */
    Wallet wallet;
    BlockChain chain;
    NetworkParameters networkParameters;
    SPVBlockStore spvStore;
    MemoryBlockStore memoryStore;
    UUID userId;


    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;


    /**
     * DealsWithPluginFileSystem interface member variables
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity interface member variable
     */
    UUID pluginId;


    /**
     * constructor
     * @param wallet the BitcoinJ wallet with the addresses used to listen to the network
     * @param UserID The if of the user requesting the syncronization
     * @throws CantInitializeMonitorAgentException
     */

    public StoredBlockChain (Wallet wallet, UUID UserID) throws CantInitializeMonitorAgentException {
        this.networkParameters = getNetworkConfiguration();
        this.wallet = wallet;
        this.userId = UserID;
    }

    /**
     * creates the blockchain object and the repository
     */
    public void createBlockChain() throws CantInitializeMonitorAgentException {
        try {
            /**
             * I will save the blockchain into disk.
             */
            String blockChainFileName = userId.toString() + ".spvchain";
            PluginBinaryFile chainFile = pluginFileSystem.createBinaryFile(pluginId, pluginId.toString(), blockChainFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            chainFile.persistToMedia();

            spvStore = new SPVBlockStore(this.networkParameters, new File(pluginId.toString(), blockChainFileName));
            chain = new BlockChain(this.networkParameters, this.wallet, spvStore);
        } catch (Exception exception){
            /**
             * in an error occurs, I will try to save it into memory
             */
            memoryStore = new MemoryBlockStore(this.networkParameters);
            try {
                chain = new BlockChain(this.networkParameters, this.wallet, memoryStore);
                System.out.println("Warning. Blockchain saved in memory.");
                /**
                 * if everything fails I will have to throw the exception
                 */
            } catch (BlockStoreException BlockStoreException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, BlockStoreException);
                throw new CantInitializeMonitorAgentException();
            }
        }

    }

    /**
     * ErrorManager interface implementation
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginFileSystem interface implementation
      * @param pluginFileSystem
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
     this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealsWithPluginIdentity interface implementation
     * @param pluginId
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * StoredBlockChain getter
     * @return the blockchain that is saved into disk
     */
    public BlockChain getBlockChain(){
        return chain;
    }
}
