package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetMovementType;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;

import java.util.Date;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public interface AssetMovement {
    DAPActor actorFrom();

    DAPActor actorTo();

    Date when();

    AssetMovementType getType();
}
