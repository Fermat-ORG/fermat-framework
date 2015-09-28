package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.interfaces.AssetDistributionManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.HashMap;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/09/15.
 */
public class AssetDistributionTransactionManager implements AssetDistributionManager {

    AssetVaultManager assetVaultManager;
    DigitalAssetDistributor digitalAssetDistributor;
    ErrorManager errorManager;

    public AssetDistributionTransactionManager(AssetVaultManager assetVaultManager, ErrorManager errorManager) throws CantSetObjectException {
        setAssetVaultManager(assetVaultManager);
        this.digitalAssetDistributor=new DigitalAssetDistributor(assetVaultManager, errorManager);
    }

    public void setErrorManager(ErrorManager errorManager) throws CantSetObjectException {
        if(errorManager==null){
            throw new CantSetObjectException("ErrorManager is null");
        }
        this.errorManager=errorManager;
    }

    public void setAssetVaultManager(AssetVaultManager assetVaultManager) throws CantSetObjectException{
        if(assetVaultManager==null){
            throw new CantSetObjectException("AssetVaultManager is null");
        }
        this.assetVaultManager=assetVaultManager;
    }

    @Override
    public void distributeAssets(HashMap<DigitalAssetMetadata, ActorAssetUser> digitalAssetsToDistribute) throws CantDistributeDigitalAssetsException {
        this.digitalAssetDistributor.distributeAssets(digitalAssetsToDistribute);
    }
}
