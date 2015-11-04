package com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantAssetIssuerActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;

import java.util.List;

/**
 * Created by Nerio on 13/10/15.
 */
public class AssetIssuerCommunitySupAppModuleManager {

    ActorAssetIssuerManager actorAssetIssuerManager;

    public AssetIssuerCommunitySupAppModuleManager(ActorAssetIssuerManager actorAssetIssuerManager) {
        this.actorAssetIssuerManager = actorAssetIssuerManager;
    }

    public List<ActorAssetIssuer> getAllAssetIssuerActorRegistered() throws CantGetAssetIssuerActorsException, CantAssetIssuerActorNotFoundException {
        return actorAssetIssuerManager.getAllAssetIssuerActorInTableRegistered();
    }
}
