package com.bitdubai.fermat_cht_api.layer.middleware.utils;

import com.bitdubai.fermat_cht_api.all_definition.enums.TypeChat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Group;

import java.util.UUID;

/**
 * Created by franklin on 31/03/16.
 */
public class GroupImpl implements Group {
    private final UUID groupId;
    private final String name;
    private final TypeChat typeChat;

    public GroupImpl(UUID groupId, String name, TypeChat typeChat) {
        this.groupId = groupId;
        this.name = name;
        this.typeChat = typeChat;
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
     * The method <code>getNameGroup</code> gives us the public key of the represented a Actor
     *
     * @return the publicKey
     */
    @Override
    public String getNameGroup() {
        return name;
    }

    /**
     * The method <code>getTypeChat</code> gives us the public key of the represented a Actor
     *
     * @return the typeChat
     */
    @Override
    public TypeChat getTypeChat() {
        return typeChat;
    }
}
