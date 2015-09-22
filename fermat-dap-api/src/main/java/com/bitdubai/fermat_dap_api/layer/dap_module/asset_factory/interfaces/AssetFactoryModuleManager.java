package com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.DealsWithAssetFactory;

/**
 * Created by franklin on 11/09/15.
 */
public interface AssetFactoryModuleManager {
    //Implementa solo los metodos que utiliza la sup app
    IdentityAssetIssuer getLoggedIdentityAssetIssuer();

    void saveAssetFactory(AssetFactory assetFactory);

    void removeAssetFactory(String publicKey);

}
