package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import java.util.UUID;

/**
 * Created by franklin on 31/03/16.
 */
public interface GroupMember {
    /**
     * Returns the group id that representing the id of the group to which the user belongs
     *
     * @return groupId
     */
    UUID getGroupMemberId();

    /**
     * Returns the group id that representing the id of the group to which the user belongs
     *
     * @return groupId
     */
    UUID getGroupId();

    /**
     * The method <code>getActorPublicKey</code> gives us the public key of the represented a Actor
     *
     * @return the publicKey
     */
    String getActorPublicKey();

    /**
     * The method <code>getActorAlias</code> gives us the public key of the represented a Actor
     *
     * @return the alias
     */
    String getActorAlias();
}
