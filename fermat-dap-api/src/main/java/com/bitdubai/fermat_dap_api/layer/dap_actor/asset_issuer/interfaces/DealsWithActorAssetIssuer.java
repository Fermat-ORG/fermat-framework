package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Nerio on 07/09/15.
 */
public interface DealsWithActorAssetIssuer {

    void setActorAssetIssuerManager(ActorAssetIssuerManager actorAssetIssuerManager) throws CantSetObjectException;

}
