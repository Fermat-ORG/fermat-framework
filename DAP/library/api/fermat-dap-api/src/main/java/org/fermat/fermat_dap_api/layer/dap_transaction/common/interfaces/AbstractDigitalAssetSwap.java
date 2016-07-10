package org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;

import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/10/15.
 */
public abstract class AbstractDigitalAssetSwap implements DigitalAssetSwap {

    protected AssetVaultManager assetVaultManager;
    protected BlockchainManager<ECKey, Transaction> bitcoinNetworkManager;
    protected PluginFileSystem pluginFileSystem;
    protected UUID pluginId;
    protected org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager;
    protected CryptoTransaction cryptoTransaction;

    public AbstractDigitalAssetSwap(UUID pluginId,
                                    PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    public void setAssetTransmissionNetworkServiceManager(org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager) {
        this.assetTransmissionNetworkServiceManager = assetTransmissionNetworkServiceManager;
    }

    public void setBitcoinCryptoNetworkManager(BlockchainManager<ECKey, Transaction> bitcoinNetworkManager) {
        this.bitcoinNetworkManager = bitcoinNetworkManager;
    }

    public void setAssetVaultManager(AssetVaultManager assetVaultManager) {
        this.assetVaultManager = assetVaultManager;
    }

    public abstract void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

    public boolean isDigitalAssetHashValid(DigitalAssetMetadata digitalAssetMetadata) throws CantGetCryptoTransactionException, org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {
        return org.fermat.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification.isDigitalAssetHashValid(bitcoinNetworkManager, digitalAssetMetadata);
    }

    public CryptoTransaction foundCryptoTransaction(DigitalAssetMetadata digitalAssetMetadata) throws CantGetCryptoTransactionException {
        return org.fermat.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification.foundCryptoTransaction(bitcoinNetworkManager, digitalAssetMetadata, null, null);
    }

    public boolean isAvailableBalanceInAssetVault(long genesisAmount, String genesisTransaction) {
        //I will hardcode this control for testing
        System.out.println("ASSET DISTRIBUTION OR RECEPTION Check available balance is hardcoded");
        return true;
        /*long availableBalanceForTransaction=this.assetVaultManager.getAvailableBalanceForTransaction(genesisTransaction);
        return availableBalanceForTransaction<genesisAmount;*/
    }

    public boolean isValidContract(org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract digitalAssetContract) {
        return org.fermat.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification.isValidContract(digitalAssetContract);
    }

    public abstract void persistInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException;

    public abstract void setDigitalAssetLocalFilePath(DigitalAssetMetadata digitalAssetMetadata);
}
