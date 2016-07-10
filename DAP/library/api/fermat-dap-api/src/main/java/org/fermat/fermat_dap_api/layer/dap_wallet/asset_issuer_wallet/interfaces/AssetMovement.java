package org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public interface AssetMovement extends Serializable {
    DAPActor actorFrom();

    DAPActor actorTo();

    Date when();

    org.fermat.fermat_dap_api.layer.all_definition.enums.AssetMovementType getType();
}
