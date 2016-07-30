package com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeChat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.GroupMember;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;

import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
public interface ChatMetadata {

    UUID getChatId();

    UUID getObjectId();

    PlatformComponentType getLocalActorType();

    String getLocalActorPublicKey();

    PlatformComponentType getRemoteActorType();

    String getRemoteActorPublicKey();

    String getChatName();

    ChatMessageStatus getChatMessageStatus();

    MessageStatus getMessageStatus();

    String getDate();

    UUID getMessageId();

    String getMessage();

    DistributionStatus getDistributionStatus();

    TypeChat getTypeChat();

    List<GroupMember> getGroupMembers();

    String toJson();

}
