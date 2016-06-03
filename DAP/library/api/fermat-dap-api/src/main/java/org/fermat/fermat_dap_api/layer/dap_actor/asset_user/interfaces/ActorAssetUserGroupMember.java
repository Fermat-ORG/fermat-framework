package org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces;

/**
 * Created by lcampo on 25/11/15.
 */
public interface ActorAssetUserGroupMember {
    /**
     * Returns the group id that representing the id of the group to which the user belongs
     *
     * @return groupId
     */
    String getGroupId();

    /**
     * The method <code>getActorPublicKey</code> gives us the public key of the represented a Actor
     *
     * @return the publicKey
     */
    String getActorPublicKey();
}
