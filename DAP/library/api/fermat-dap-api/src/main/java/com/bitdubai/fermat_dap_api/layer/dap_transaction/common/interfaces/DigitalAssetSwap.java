package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.DAPException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetGenesisTransactionException;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/10/15.
 */
public abstract class DigitalAssetSwap {

    AssetVaultManager assetVaultManager;
    PluginFileSystem pluginFileSystem;
    UUID pluginId;
    public AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager;

    public DigitalAssetSwap(AssetVaultManager assetVaultManager,
                                   UUID pluginId,
                                   PluginFileSystem pluginFileSystem) throws CantExecuteDatabaseOperationException {
        this.assetVaultManager=assetVaultManager;
        this.pluginFileSystem=pluginFileSystem;
        this.pluginId=pluginId;
    }

    public void setAssetTransmissionNetworkServiceManager(AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager){
        this.assetTransmissionNetworkServiceManager=assetTransmissionNetworkServiceManager;
    }

    public abstract void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws DAPException;

    public boolean isDigitalAssetHashValid(DigitalAssetMetadata digitalAssetMetadata) throws CantGetGenesisTransactionException {
        String digitalAssetMetadataHash=digitalAssetMetadata.getDigitalAssetHash();
        CryptoTransaction cryptoTransaction=/*this.assetVaultManager.getGenesisTransaction(digitalAssetMetadataHash)*/null;
        String hashFromCryptoTransaction=cryptoTransaction.getOp_Return();
        return digitalAssetMetadataHash.equals(hashFromCryptoTransaction);
    }

    public abstract void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser actorAssetUser) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException;

    public boolean isAvailableBalanceInAssetVault(long genesisAmount, String genesisTransaction){
        long availableBalanceForTransaction=this.assetVaultManager.getAvailableBalanceForTransaction(genesisTransaction);
        return availableBalanceForTransaction<genesisAmount;
    }

    public boolean isValidContract(DigitalAssetContract digitalAssetContract){
        //For now, we going to check, only, the expiration date
        ContractProperty contractProperty=digitalAssetContract.getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE);
        Timestamp expirationDate= (Timestamp) contractProperty.getValue();
        Date date= new Date();
        return expirationDate.after(new Timestamp(date.getTime()));
    }

    public abstract void persistInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException;

    public abstract void setDigitalAssetLocalFilePath(DigitalAssetMetadata digitalAssetMetadata);
}
