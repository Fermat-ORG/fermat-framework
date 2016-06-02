package org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.functional;

import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetMovementType;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetMovement;

import java.util.Date;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetMovementImpl implements AssetMovement {
    //VARIABLE DECLARATION

    private DAPActor actorFrom;
    private DAPActor actorTo;
    private Date timestamp;
    private AssetMovementType type;

    //CONSTRUCTORS
    public AssetMovementImpl(DAPActor actorFrom, DAPActor actorTo, Date timestamp, AssetMovementType type) {
        this.actorFrom = actorFrom;
        this.actorTo = actorTo;
        this.timestamp = timestamp;
        this.type = type;
    }

    public AssetMovementImpl() {
    }

    //PUBLIC METHODS
    @Override
    public DAPActor actorFrom() {
        return actorFrom;
    }

    @Override
    public DAPActor actorTo() {
        return actorTo;
    }

    @Override
    public Date when() {
        return timestamp;
    }

    @Override
    public AssetMovementType getType() {
        return type;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    public void setActorFrom(DAPActor actorFrom) {
        this.actorFrom = actorFrom;
    }

    public void setActorTo(DAPActor actorTo) {
        this.actorTo = actorTo;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(AssetMovementType type) {
        this.type = type;
    }

    //INNER CLASSES
}
