package org.fermat.fermat_dap_plugin.layer.sub_app_module.asset.issuer.developer.version_1.structure;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantAssetIssuerActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;

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
