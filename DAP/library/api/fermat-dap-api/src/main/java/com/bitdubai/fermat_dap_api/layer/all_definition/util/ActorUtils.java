package com.bitdubai.fermat_dap_api.layer.all_definition.util;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;

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

    public static DAPActor getActorFromPublicKey(String actorPublicKey, Actors actorType, ActorAssetUserManager userManager, ActorAssetRedeemPointManager redeemPointManager, ActorAssetIssuerManager issuerManager) {
        try {
            switch (actorType) {
                case DAP_ASSET_ISSUER:
                    return issuerManager.getActorByPublicKey(actorPublicKey);
                case DAP_ASSET_USER:
                    return userManager.getActorByPublicKey(actorPublicKey);
                case DAP_ASSET_REDEEM_POINT:
                    return redeemPointManager.getActorByPublicKey(actorPublicKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void storeDAPActor(DAPActor actorToStore, ActorAssetUserManager userManager, ActorAssetRedeemPointManager redeemPointManager, ActorAssetIssuerManager issuerManager) throws DAPException {
        try {
            if (actorToStore instanceof ActorAssetIssuer) {
                issuerManager.createActorAssetIssuerRegisterInNetworkService((ActorAssetIssuer) actorToStore);
            }
            if (actorToStore instanceof ActorAssetRedeemPoint) {
                redeemPointManager.saveRegisteredActorRedeemPoint((ActorAssetRedeemPoint) actorToStore);
            }
            if (actorToStore instanceof ActorAssetUser) {
                userManager.createActorAssetUserRegisterInNetworkService((ActorAssetUser) actorToStore);
            }
        } catch (Exception e) {
            throw new DAPException(e);
        }
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
