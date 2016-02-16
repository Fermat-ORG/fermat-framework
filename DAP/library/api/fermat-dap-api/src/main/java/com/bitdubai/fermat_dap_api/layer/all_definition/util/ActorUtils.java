package com.bitdubai.fermat_dap_api.layer.all_definition.util;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public final class ActorUtils {

    //VARIABLE DECLARATION

    //CONSTRUCTORS
    private ActorUtils() {
        throw new AssertionError("NO INSTANCES!!");
    }

    //PUBLIC METHODS
    public static DAPActor constructActorFromIdentity(final ActiveActorIdentityInformation identity) {
        return new DAPActor() {
            @Override
            public String getActorPublicKey() {
                return identity.getPublicKey();
            }

            @Override
            public String getName() {
                return identity.getAlias();
            }

            @Override
            public byte[] getProfileImage() {
                return identity.getImage();
            }
        };
    }

    public static Actors getActorType(DAPActor dapActor) {
        if (dapActor instanceof ActorAssetIssuer) {
            return Actors.DAP_ASSET_ISSUER;
        }
        if (dapActor instanceof ActorAssetRedeemPoint) {
            return Actors.DAP_ASSET_REDEEM_POINT;
        }
        if (dapActor instanceof ActorAssetUser) {
            return Actors.DAP_ASSET_USER;
        }
        return null;
    }

    public static PlatformComponentType getPlatformComponentType(DAPActor dapActor) {
        if (dapActor instanceof ActorAssetIssuer) {
            return PlatformComponentType.ACTOR_ASSET_ISSUER;
        }
        if (dapActor instanceof ActorAssetRedeemPoint) {
            return PlatformComponentType.ACTOR_ASSET_REDEEM_POINT;
        }
        if (dapActor instanceof ActorAssetUser) {
            return PlatformComponentType.ACTOR_ASSET_USER;
        }
        return null;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
