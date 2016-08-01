package com.bitdubai.fermat_cht_api.layer.middleware.utils;

import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.GroupMember;

import java.util.UUID;

/**
 * Created by franklin on 31/03/16.
 */
public class GroupMemberImpl implements GroupMember {
    private final UUID groupMemberId;
    private final UUID groupId;
    private final String publicKeyActor;
    private final String alias;

    public GroupMemberImpl(UUID groupMemberId, UUID groupId, String publicKeyActor, String alias) {
        this.groupMemberId = groupMemberId;
        this.groupId = groupId;
        this.publicKeyActor = publicKeyActor;
        this.alias = alias;
    }

    /**
     * Returns the group id that representing the id of the group to which the user belongs
     *
     * @return groupId
     */
    @Override
    public UUID getGroupMemberId() {
        return groupMemberId;
    }

    /**
     * Returns the group id that representing the id of the group to which the user belongs
     *
     * @return groupId
     */
    @Override
    public UUID getGroupId() {
        return groupId;
    }

    /**
     * The method <code>getActorPublicKey</code> gives us the public key of the represented a Actor
     *
     * @return the publicKey
     */
    @Override
    public String getActorPublicKey() {
        return publicKeyActor;
    }

    /**
     * The method <code>getActorAlias</code> gives us the public key of the represented a Actor
     *
     * @return the alias
     */
    @Override
    public String getActorAlias() {
        return alias;
    }

}
