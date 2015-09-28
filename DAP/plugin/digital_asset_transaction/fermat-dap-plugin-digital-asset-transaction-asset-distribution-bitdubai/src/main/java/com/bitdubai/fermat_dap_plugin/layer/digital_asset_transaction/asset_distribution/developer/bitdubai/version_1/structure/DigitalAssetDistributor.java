package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.exceptions.CantDeliverDigitalAssetException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/09/15.
 */
public class DigitalAssetDistributor {

    AssetVaultManager assetVaultManager;
    ErrorManager errorManager;

    public DigitalAssetDistributor(AssetVaultManager assetVaultManager, ErrorManager errorManager){
        this.assetVaultManager=assetVaultManager;
        this.errorManager=errorManager;
    }


    /**
     * This method check if the DigitalAssetMetadata remains with not modifications
     * */
    private void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws CantDeliverDigitalAssetException {
        //TODO: get transactionHash from AssetVault
        CryptoTransaction cryptoTransaction = null;
        //This won't work until I can get the CryptoTransaction from AssetVault
        String op_ReturnFromAssetVault=cryptoTransaction.getOp_Return();
        String digitalAssetMetadataHash=digitalAssetMetadata.getDigitalAssetHash();
        if(!digitalAssetMetadataHash.equals(op_ReturnFromAssetVault)){
            throw new CantDeliverDigitalAssetException("Cannot deliver Digital Asset because the " +
                    "Hash was modified:\n" +
                    "Op_return:"+op_ReturnFromAssetVault+"\n" +
                    "digitalAssetMetadata:"+digitalAssetMetadata);
        }

    }

    /**
     * This method will deliver the DigitalAssetMetadata to ActorAssetUser
     * */
    private void deliverDigitalAssetToRemoteDevice(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser actorAssetUser) throws CantDeliverDigitalAssetException {
        //First, I'll check is Hash wasn't modified
        checkDigitalAssetMetadata(digitalAssetMetadata);
    }

    public void distributeAssets(HashMap<DigitalAssetMetadata, ActorAssetUser> digitalAssetsToDistribute) throws CantDistributeDigitalAssetsException {
        //TODO: implement this
        try{
            for(Map.Entry<DigitalAssetMetadata, ActorAssetUser> entry: digitalAssetsToDistribute.entrySet()){
                DigitalAssetMetadata digitalAssetMetadata=entry.getKey();
                ActorAssetUser actorAssetUser=entry.getValue();
                //Deliver one DigitalAsset
                deliverDigitalAssetToRemoteDevice(digitalAssetMetadata, actorAssetUser);
            }
        } catch(CantDeliverDigitalAssetException exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }

    }
}
