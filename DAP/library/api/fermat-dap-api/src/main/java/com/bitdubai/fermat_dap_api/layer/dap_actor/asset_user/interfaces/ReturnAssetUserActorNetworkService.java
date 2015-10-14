package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;

/**
 * Created by Nerio on 13/10/15.
 */

public interface ReturnAssetUserActorNetworkService {

    ActorAssetUser creatActorAssetUser(String assetUserActorPublicKey, String assetUserActorName, byte[] assetUserActorprofileImage, Location assetUserActorlocation) throws CantCreateAssetUserActorException;

}
