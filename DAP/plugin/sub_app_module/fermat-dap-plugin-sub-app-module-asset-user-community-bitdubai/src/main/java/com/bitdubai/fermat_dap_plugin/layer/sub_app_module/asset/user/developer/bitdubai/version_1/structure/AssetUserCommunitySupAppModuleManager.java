package com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;

import java.util.List;

/**
 * Created by Nerio on 13/10/15.
 */
public class AssetUserCommunitySupAppModuleManager {

    ActorAssetUserManager actorAssetUserManager;

    public AssetUserCommunitySupAppModuleManager(ActorAssetUserManager actorAssetUserManager) {
        this.actorAssetUserManager = actorAssetUserManager;
    }

    public List<ActorAssetUser> getAllActorAssetUserRegistered() throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException {
            return actorAssetUserManager.getAllAssetUserActorInTableRegistered();
    }

}
