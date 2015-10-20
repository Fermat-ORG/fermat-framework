package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ReturnAssetUserActorNetworkService;

/**
 * Created by Nerio on 13/10/15.
 */
public class AssetUserANS implements ReturnAssetUserActorNetworkService {

    @Override
    public ActorAssetUser creatActorAssetUser(String assetUserActorPublicKey, String assetUserActorName, byte[] assetUserActorprofileImage, Location assetUserActorlocation) throws CantCreateAssetUserActorException {

        AssetUserActorRecord assetUserActorRecord = new AssetUserActorRecord(assetUserActorName,assetUserActorPublicKey, assetUserActorprofileImage, assetUserActorlocation);

        return assetUserActorRecord;
    }
}
