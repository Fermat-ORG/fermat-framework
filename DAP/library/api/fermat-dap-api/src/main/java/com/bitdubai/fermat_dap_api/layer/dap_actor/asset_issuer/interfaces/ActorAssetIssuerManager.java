package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces;


import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantAssetIssuerActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface ActorAssetIssuerManager {

    List<ActorAssetIssuer> getAllAssetIssuerActorRegistered() throws CantGetAssetIssuerActorsException, CantAssetIssuerActorNotFoundException;

}
